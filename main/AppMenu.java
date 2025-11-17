package main;

import entities.Propiedad;
import entities.EscrituraNotarial;
import service.PropiedadService;
import service.EscrituraNotarialService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// Gonza: Menú de consola principal
public class AppMenu {

    public static void main(String[] args) {
        try {
            // OJO: PropiedadService y EscrituraNotarialService deben usar DatabaseConnection.getConnection()
            PropiedadService propiedadService = new PropiedadService();
            EscrituraNotarialService escrituraService = new EscrituraNotarialService();
            Scanner sc = new Scanner(System.in);

            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero(sc, "Elija una opción: ");

                switch (opcion) {
                    case 1 -> agregarPropiedad(sc, propiedadService);
                    case 2 -> verPropiedades(propiedadService);
                    case 3 -> modificarPropiedad(sc, propiedadService);
                    case 4 -> borrarPropiedad(sc, propiedadService);
                    case 5 -> buscarPropiedadPorPadron(sc, propiedadService);

                    case 6 -> agregarEscritura(sc, escrituraService);
                    case 7 -> verEscrituras(escrituraService);
                    case 8 -> modificarEscritura(sc, escrituraService);
                    case 9 -> borrarEscritura(sc, escrituraService);

                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida, intente nuevamente.");
                }

            } while (opcion != 0);

            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMenu() {
        System.out.println("===== MENÚ PRINCIPAL =====");
        System.out.println("1. Agregar propiedad");
        System.out.println("2. Ver propiedades");
        System.out.println("3. Modificar propiedad");
        System.out.println("4. Borrar propiedad");
        System.out.println("5. Buscar propiedad por padrón catastral");
        System.out.println("6. Agregar escritura notarial");
        System.out.println("7. Ver escrituras notariales");
        System.out.println("8. Modificar escritura notarial");
        System.out.println("9. Borrar escritura notarial");
        System.out.println("0. Salir");
    }

    // -------- Lectura con validación --------

    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Debe ingresar un número entero. Intente nuevamente.");
            }
        }
    }

    private static long leerLong(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine();
            try {
                return Long.parseLong(linea);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Debe ingresar un número entero largo. Intente nuevamente.");
            }
        }
    }

    private static BigDecimal leerBigDecimal(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine();
            try {
                return new BigDecimal(linea.replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("⚠ Debe ingresar un número válido. Intente nuevamente.");
            }
        }
    }

    private static String leerTextoObligatorio(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine();
            if (texto.trim().isEmpty()) {
                System.out.println("⚠ El campo no puede estar vacío. Intente nuevamente.");
            } else {
                return texto.trim();
            }
        }
    }

    // para observaciones u otros campos opcionales
    private static String leerTextoOpcional(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static LocalDate leerFecha(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (YYYY-MM-DD): ");
            String texto = sc.nextLine().trim();
            try {
                return LocalDate.parse(texto);
            } catch (Exception e) {
                System.out.println("⚠ Fecha inválida. Use formato YYYY-MM-DD.");
            }
        }
    }

    private static Propiedad.Destino leerDestino(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (RES/COM): ");
            String texto = sc.nextLine().trim().toUpperCase();
            try {
                return Propiedad.Destino.valueOf(texto);
            } catch (Exception e) {
                System.out.println("⚠ Destino inválido. Debe ser RES o COM.");
            }
        }
    }

    // -------- PROPIEDADES --------

    private static void agregarPropiedad(Scanner sc, PropiedadService service) {
        try {
            String padron = leerTextoObligatorio(sc, "Padrón catastral: ");
            String direccion = leerTextoObligatorio(sc, "Dirección: ");
            BigDecimal superficie = leerBigDecimal(sc, "Superficie en m2: ");
            Propiedad.Destino destino = leerDestino(sc, "Destino");
            int antiguedad = leerEntero(sc, "Antigüedad (años): ");

            Propiedad p = new Propiedad();
            p.setId(null);
            p.setEliminado(false);
            p.setPadronCatastral(padron);
            p.setDireccion(direccion);
            p.setSuperficieM2(superficie);
            p.setDestino(destino);
            p.setAntiguedad(antiguedad);
            p.setEscrituraNotarial(null); // se podrá asociar después

            service.insertar(p);
            System.out.println("✔ Propiedad agregada con ID: " + p.getId());
        } catch (Exception e) {
            System.out.println("❌ Error al agregar propiedad: " + e.getMessage());
        }
    }

    private static void verPropiedades(PropiedadService service) {
        try {
            List<Propiedad> lista = service.getAll();
            if (lista.isEmpty()) {
                System.out.println("No hay propiedades cargadas.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar propiedades: " + e.getMessage());
        }
    }

    private static void modificarPropiedad(Scanner sc, PropiedadService service) {
        try {
            long id = leerLong(sc, "ID de la propiedad a modificar: ");
            Propiedad p = service.getById(id);
            if (p == null) {
                System.out.println("⚠ No se encontró propiedad con ese ID.");
                return;
            }

            System.out.println("Propiedad actual: " + p);

            String padron = leerTextoObligatorio(sc, "Nuevo padrón (" + p.getPadronCatastral() + "): ");
            String direccion = leerTextoObligatorio(sc, "Nueva dirección (" + p.getDireccion() + "): ");
            BigDecimal superficie = leerBigDecimal(sc, "Nueva superficie (" + p.getSuperficieM2() + "): ");
            Propiedad.Destino destino = leerDestino(sc, "Nuevo destino (actual " + p.getDestino() + "): ");
            int antiguedad = leerEntero(sc, "Nueva antigüedad (" + p.getAntiguedad() + "): ");

            p.setPadronCatastral(padron);
            p.setDireccion(direccion);
            p.setSuperficieM2(superficie);
            p.setDestino(destino);
            p.setAntiguedad(antiguedad);

            service.actualizar(p);
            System.out.println("✔ Propiedad actualizada.");
        } catch (Exception e) {
            System.out.println("❌ Error al modificar propiedad: " + e.getMessage());
        }
    }

    private static void borrarPropiedad(Scanner sc, PropiedadService service) {
        try {
            long id = leerLong(sc, "ID de la propiedad a borrar: ");
            service.eliminar(id);
            System.out.println("✔ Propiedad eliminada.");
        } catch (Exception e) {
            System.out.println("❌ Error al borrar propiedad: " + e.getMessage());
        }
    }

    private static void buscarPropiedadPorPadron(Scanner sc, PropiedadService service) {
        try {
            String padron = leerTextoObligatorio(sc, "Padrón a buscar: ");
            Propiedad p = service.getByPadronCatastral(padron);
            if (p == null) {
                System.out.println("⚠ No se encontró propiedad con ese padrón.");
            } else {
                System.out.println("Propiedad encontrada: " + p);
            }
        } catch (Exception e) {
            System.out.println("❌ Error en la búsqueda: " + e.getMessage());
        }
    }

    // -------- ESCRITURAS --------

    private static void agregarEscritura(Scanner sc, EscrituraNotarialService service) {
        try {
            String nroEscritura = leerTextoObligatorio(sc, "Número de escritura: ");
            LocalDate fecha = leerFecha(sc, "Fecha de la escritura");
            String notaria = leerTextoObligatorio(sc, "Notaría: ");
            String tomo = leerTextoObligatorio(sc, "Tomo: ");
            String folio = leerTextoObligatorio(sc, "Folio: ");
            String observaciones = leerTextoOpcional(sc, "Observaciones (puede quedar vacío): ");

            EscrituraNotarial e = new EscrituraNotarial();
            e.setId(null);
            e.setEliminado(false);
            e.setNroEscritura(nroEscritura);
            e.setFecha(fecha);
            e.setNotaria(notaria);
            e.setTomo(tomo);
            e.setFolio(folio);
            e.setObservaciones(observaciones);

            service.insertar(e);
            System.out.println("✔ Escritura agregada con ID: " + e.getId());
        } catch (Exception e) {
            System.out.println("❌ Error al agregar escritura: " + e.getMessage());
        }
    }

    private static void verEscrituras(EscrituraNotarialService service) {
        try {
            List<EscrituraNotarial> lista = service.getAll();
            if (lista.isEmpty()) {
                System.out.println("No hay escrituras cargadas.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar escrituras: " + e.getMessage());
        }
    }

    private static void modificarEscritura(Scanner sc, EscrituraNotarialService service) {
        try {
            long id = leerLong(sc, "ID de la escritura a modificar: ");
            EscrituraNotarial e = service.getById(id);
            if (e == null) {
                System.out.println("⚠ No se encontró escritura con ese ID.");
                return;
            }

            System.out.println("Escritura actual: " + e);

            String nroEscritura = leerTextoObligatorio(sc,
                    "Nuevo número (" + e.getNroEscritura() + "): ");
            LocalDate fecha = leerFecha(sc, "Nueva fecha");
            String notaria = leerTextoObligatorio(sc,
                    "Nueva notaría (" + e.getNotaria() + "): ");
            String tomo = leerTextoObligatorio(sc,
                    "Nuevo tomo (" + e.getTomo() + "): ");
            String folio = leerTextoObligatorio(sc,
                    "Nuevo folio (" + e.getFolio() + "): ");
            String observaciones = leerTextoOpcional(sc,
                    "Nuevas observaciones (actual: " + e.getObservaciones() + "): ");

            e.setNroEscritura(nroEscritura);
            e.setFecha(fecha);
            e.setNotaria(notaria);
            e.setTomo(tomo);
            e.setFolio(folio);
            e.setObservaciones(observaciones);

            service.actualizar(e);
            System.out.println("✔ Escritura actualizada.");
        } catch (Exception ex) {
            System.out.println("❌ Error al modificar escritura: " + ex.getMessage());
        }
    }

    private static void borrarEscritura(Scanner sc, EscrituraNotarialService service) {
        try {
            long id = leerLong(sc, "ID de la escritura a borrar: ");
            service.eliminar(id);
            System.out.println("✔ Escritura eliminada.");
        } catch (Exception e) {
            System.out.println("❌ Error al borrar escritura: " + e.getMessage());
        }
    }
}
