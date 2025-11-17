package main;

import entities.Propiedad;
import entities.EscrituraNotarial;
import service.PropiedadService;
import service.EscrituraNotarialService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Menú de consola principal para la gestión de Propiedades y Escrituras Notariales.
 */
public class AppMenu {
    private static Scanner scanner = new Scanner(System.in);
    private static PropiedadService propiedadService = new PropiedadService();
    private static EscrituraNotarialService escrituraService = new EscrituraNotarialService();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE PROPIEDADES");
        System.out.println("  Propiedad → EscrituraNotarial (1→1)");
        System.out.println("========================================\n");
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine().trim().toUpperCase();
            
            try {
                switch (opcion) {
                    case "1":
                        menuPropiedades();
                        break;
                    case "2":
                        menuEscrituras();
                        break;
                    case "3":
                        menuBusquedas();
                        break;
                    case "0":
                        continuar = false;
                        System.out.println("\n¡Hasta luego!");
                        break;
                    default:
                        System.out.println("\n❌ Opción inválida. Por favor, seleccione una opción válida.\n");
                }
            } catch (Exception e) {
                System.out.println("\n❌ ERROR: " + e.getMessage());
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestión de Propiedades");
        System.out.println("2. Gestión de Escrituras Notariales");
        System.out.println("3. Búsquedas");
        System.out.println("0. Salir");
        System.out.print("\nSeleccione una opción: ");
    }
    
    private static void menuPropiedades() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- GESTIÓN DE PROPIEDADES ---");
            System.out.println("1. Crear Propiedad");
            System.out.println("2. Listar Propiedades");
            System.out.println("3. Buscar Propiedad por ID");
            System.out.println("4. Actualizar Propiedad");
            System.out.println("5. Eliminar Propiedad (lógico)");
            System.out.println("0. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");
            
            String opcion = scanner.nextLine().trim().toUpperCase();
            
            try {
                switch (opcion) {
                    case "1":
                        crearPropiedad();
                        break;
                    case "2":
                        listarPropiedades();
                        break;
                    case "3":
                        buscarPropiedadPorId();
                        break;
                    case "4":
                        actualizarPropiedad();
                        break;
                    case "5":
                        eliminarPropiedad();
                        break;
                    case "0":
                        continuar = false;
                        break;
                    default:
                        System.out.println("\n❌ Opción inválida.\n");
                }
            } catch (Exception e) {
                System.out.println("\n❌ ERROR: " + e.getMessage());
                e.printStackTrace(); // Para debugging - mostrar stack trace completo
            }
            
            if (continuar && !opcion.equals("0")) {
                System.out.println("\nPresione Enter para continuar...");
                try {
                    scanner.nextLine(); // Esperar Enter del usuario
                } catch (Exception e) {
                    System.out.println("Error al leer entrada. Continuando...");
                    // Continuar de todas formas para que la app no se cierre
                }
            }
        }
    }
    
    private static void menuEscrituras() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- GESTIÓN DE ESCRITURAS NOTARIALES ---");
            System.out.println("1. Crear Escritura");
            System.out.println("2. Listar Escrituras");
            System.out.println("3. Buscar Escritura por ID");
            System.out.println("4. Actualizar Escritura");
            System.out.println("5. Eliminar Escritura (lógico)");
            System.out.println("0. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");
            
            String opcion = scanner.nextLine().trim().toUpperCase();
            
            try {
                switch (opcion) {
                    case "1":
                        crearEscritura();
                        break;
                    case "2":
                        listarEscrituras();
                        break;
                    case "3":
                        buscarEscrituraPorId();
                        break;
                    case "4":
                        actualizarEscritura();
                        break;
                    case "5":
                        eliminarEscritura();
                        break;
                    case "0":
                        continuar = false;
                        break;
                    default:
                        System.out.println("\n❌ Opción inválida.\n");
                }
            } catch (Exception e) {
                System.out.println("\n❌ ERROR: " + e.getMessage());
            }
            
            if (continuar && !opcion.equals("0")) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private static void menuBusquedas() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- BÚSQUEDAS ---");
            System.out.println("1. Buscar Propiedad por Padrón Catastral");
            System.out.println("2. Buscar Escritura por Número");
            System.out.println("0. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");
            
            String opcion = scanner.nextLine().trim().toUpperCase();
            
            try {
                switch (opcion) {
                    case "1":
                        buscarPropiedadPorPadron();
                        break;
                    case "2":
                        buscarEscrituraPorNumero();
                        break;
                    case "0":
                        continuar = false;
                        break;
                    default:
                        System.out.println("\n❌ Opción inválida.\n");
                }
            } catch (Exception e) {
                System.out.println("\n❌ ERROR: " + e.getMessage());
            }
            
            if (continuar && !opcion.equals("0")) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private static void crearPropiedad() throws Exception {
        System.out.println("\n--- CREAR PROPIEDAD ---");
        
        Propiedad propiedad = new Propiedad();
        propiedad.setEliminado(false);
        
        System.out.print("Padrón Catastral (obligatorio): ");
        String padron = scanner.nextLine().trim();
        if (padron.isEmpty()) {
            throw new IllegalArgumentException("El padrón catastral es obligatorio");
        }
        propiedad.setPadronCatastral(padron.toUpperCase());
        
        System.out.print("Dirección (obligatorio): ");
        String direccion = scanner.nextLine().trim();
        if (direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        propiedad.setDireccion(direccion);
        
        System.out.print("Superficie en m² (obligatorio): ");
        String superficieStr = scanner.nextLine().trim();
        if (superficieStr.isEmpty()) {
            throw new IllegalArgumentException("La superficie es obligatoria");
        }
        try {
            BigDecimal superficie = new BigDecimal(superficieStr);
            propiedad.setSuperficieM2(superficie);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La superficie debe ser un número válido");
        }
        
        System.out.print("Destino (RES/COM) [opcional]: ");
        String destinoStr = scanner.nextLine().trim().toUpperCase();
        if (!destinoStr.isEmpty()) {
            try {
                propiedad.setDestino(Propiedad.Destino.valueOf(destinoStr));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El destino debe ser RES o COM");
            }
        }
        
        System.out.print("Antigüedad (años) [opcional]: ");
        String antiguedadStr = scanner.nextLine().trim();
        if (!antiguedadStr.isEmpty()) {
            try {
                propiedad.setAntiguedad(Integer.parseInt(antiguedadStr));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("La antigüedad debe ser un número entero");
            }
        }
        
        // Preguntar si quiere crear escritura asociada
        System.out.print("\n¿Desea crear una Escritura Notarial asociada? (S/N): ");
        String crearEscritura = scanner.nextLine().trim().toUpperCase();
        if (crearEscritura.equals("S") || crearEscritura.equals("SI")) {
            EscrituraNotarial escritura = leerDatosEscritura();
            propiedad.setEscrituraNotarial(escritura);
        }
        
        propiedadService.insertar(propiedad);
        System.out.println("\n✅ Propiedad creada exitosamente con ID: " + propiedad.getId());
        if (propiedad.getEscrituraNotarial() != null) {
            System.out.println("✅ Escritura Notarial asociada creada con ID: " + propiedad.getEscrituraNotarial().getId());
        }
    }
    
    private static void listarPropiedades() throws Exception {
        System.out.println("\n--- LISTADO DE PROPIEDADES ---");
        List<Propiedad> propiedades = propiedadService.getAll();
        
        if (propiedades.isEmpty()) {
            System.out.println("No hay propiedades registradas.");
        } else {
            System.out.println(String.format("%-5s %-15s %-30s %-12s %-8s %-8s", 
                "ID", "PADRÓN", "DIRECCIÓN", "SUPERFICIE", "DESTINO", "ANTIG."));
            System.out.println("--------------------------------------------------------------------------------");
            for (Propiedad p : propiedades) {
                System.out.println(String.format("%-5d %-15s %-30s %-12s %-8s %-8s",
                    p.getId(),
                    p.getPadronCatastral(),
                    p.getDireccion().length() > 30 ? p.getDireccion().substring(0, 27) + "..." : p.getDireccion(),
                    p.getSuperficieM2(),
                    p.getDestino() != null ? p.getDestino() : "-",
                    p.getAntiguedad() != null ? p.getAntiguedad() : "-"));
            }
            System.out.println("\nTotal: " + propiedades.size() + " propiedad(es)");
        }
    }
    
    private static void buscarPropiedadPorId() throws Exception {
        System.out.println("\n--- BUSCAR PROPIEDAD POR ID ---");
        System.out.print("Ingrese el ID de la propiedad: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            long id = Long.parseLong(idStr);
            Propiedad propiedad = propiedadService.getById(id);
            
            if (propiedad == null) {
                System.out.println("\n❌ No se encontró una propiedad con ID: " + id);
            } else {
                mostrarPropiedad(propiedad);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido");
        }
    }
    
    private static void actualizarPropiedad() throws Exception {
        System.out.println("\n--- ACTUALIZAR PROPIEDAD ---");
        System.out.print("Ingrese el ID de la propiedad a actualizar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            long id = Long.parseLong(idStr);
            Propiedad propiedad = propiedadService.getById(id);
            
            if (propiedad == null) {
                System.out.println("\n❌ No se encontró una propiedad con ID: " + id);
                return;
            }
            
            System.out.println("\nPropiedad actual:");
            mostrarPropiedad(propiedad);
            System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            System.out.print("Padrón Catastral [" + propiedad.getPadronCatastral() + "]: ");
            String padron = scanner.nextLine().trim();
            if (!padron.isEmpty()) {
                propiedad.setPadronCatastral(padron.toUpperCase());
            }
            
            System.out.print("Dirección [" + propiedad.getDireccion() + "]: ");
            String direccion = scanner.nextLine().trim();
            if (!direccion.isEmpty()) {
                propiedad.setDireccion(direccion);
            }
            
            System.out.print("Superficie en m² [" + propiedad.getSuperficieM2() + "]: ");
            String superficieStr = scanner.nextLine().trim();
            if (!superficieStr.isEmpty()) {
                try {
                    propiedad.setSuperficieM2(new BigDecimal(superficieStr));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("La superficie debe ser un número válido");
                }
            }
            
            System.out.print("Destino (RES/COM) [" + (propiedad.getDestino() != null ? propiedad.getDestino() : "") + "]: ");
            String destinoStr = scanner.nextLine().trim().toUpperCase();
            if (!destinoStr.isEmpty()) {
                try {
                    propiedad.setDestino(Propiedad.Destino.valueOf(destinoStr));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("El destino debe ser RES o COM");
                }
            }
            
            System.out.print("Antigüedad [" + (propiedad.getAntiguedad() != null ? propiedad.getAntiguedad() : "") + "]: ");
            String antiguedadStr = scanner.nextLine().trim();
            if (!antiguedadStr.isEmpty()) {
                try {
                    propiedad.setAntiguedad(Integer.parseInt(antiguedadStr));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("La antigüedad debe ser un número entero");
                }
            }
            
            propiedadService.actualizar(propiedad);
            System.out.println("\n✅ Propiedad actualizada exitosamente");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido");
        }
    }
    
    private static void eliminarPropiedad() throws Exception {
        System.out.println("\n--- ELIMINAR PROPIEDAD (LÓGICO) ---");
        System.out.print("Ingrese el ID de la propiedad a eliminar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            long id = Long.parseLong(idStr);
            Propiedad propiedad = propiedadService.getById(id);
            
            if (propiedad == null) {
                System.out.println("\n❌ No se encontró una propiedad con ID: " + id);
                return;
            }
            
            System.out.print("\n¿Está seguro de eliminar esta propiedad? (S/N): ");
            String confirmar = scanner.nextLine().trim().toUpperCase();
            
            if (confirmar.equals("S") || confirmar.equals("SI")) {
                propiedadService.eliminar(id);
                System.out.println("\n✅ Propiedad eliminada exitosamente (baja lógica)");
            } else {
                System.out.println("\nOperación cancelada");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido");
        }
    }
    
    private static void crearEscritura() throws Exception {
        System.out.println("\n--- CREAR ESCRITURA NOTARIAL ---");
        
        System.out.print("ID de la Propiedad asociada (obligatorio): ");
        String propiedadIdStr = scanner.nextLine().trim();
        if (propiedadIdStr.isEmpty()) {
            throw new IllegalArgumentException("El ID de la propiedad es obligatorio");
        }
        
        long propiedadId;
        try {
            propiedadId = Long.parseLong(propiedadIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID de la propiedad debe ser un número válido");
        }
        
        EscrituraNotarial escritura = leerDatosEscritura();
        escritura.setPropiedadId(propiedadId);
        
        escrituraService.insertar(escritura);
        System.out.println("\n✅ Escritura Notarial creada exitosamente con ID: " + escritura.getId());
    }
    
    private static EscrituraNotarial leerDatosEscritura() {
        EscrituraNotarial escritura = new EscrituraNotarial();
        escritura.setEliminado(false);
        
        System.out.print("Número de Escritura [opcional]: ");
        String nroEscritura = scanner.nextLine().trim();
        if (!nroEscritura.isEmpty()) {
            escritura.setNroEscritura(nroEscritura.toUpperCase());
        }
        
        System.out.print("Fecha (YYYY-MM-DD) (obligatorio): ");
        String fechaStr = scanner.nextLine().trim();
        if (fechaStr.isEmpty()) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        try {
            escritura.setFecha(LocalDate.parse(fechaStr, dateFormatter));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha debe tener el formato YYYY-MM-DD (ej: 2024-01-15)");
        }
        
        System.out.print("Notaría [opcional]: ");
        String notaria = scanner.nextLine().trim();
        if (!notaria.isEmpty()) {
            escritura.setNotaria(notaria);
        }
        
        System.out.print("Tomo [opcional]: ");
        String tomo = scanner.nextLine().trim();
        if (!tomo.isEmpty()) {
            escritura.setTomo(tomo.toUpperCase());
        }
        
        System.out.print("Folio [opcional]: ");
        String folio = scanner.nextLine().trim();
        if (!folio.isEmpty()) {
            escritura.setFolio(folio.toUpperCase());
        }
        
        System.out.print("Observaciones [opcional]: ");
        String observaciones = scanner.nextLine().trim();
        if (!observaciones.isEmpty()) {
            escritura.setObservaciones(observaciones);
        }
        
        return escritura;
    }
    
    private static void listarEscrituras() throws Exception {
        System.out.println("\n--- LISTADO DE ESCRITURAS NOTARIALES ---");
        List<EscrituraNotarial> escrituras = escrituraService.getAll();
        
        if (escrituras.isEmpty()) {
            System.out.println("No hay escrituras registradas.");
        } else {
            System.out.println(String.format("%-5s %-15s %-12s %-30s %-8s %-8s", 
                "ID", "NRO. ESCRITURA", "FECHA", "NOTARÍA", "TOMO", "FOLIO"));
            System.out.println("--------------------------------------------------------------------------------");
            for (EscrituraNotarial e : escrituras) {
                System.out.println(String.format("%-5d %-15s %-12s %-30s %-8s %-8s",
                    e.getId(),
                    e.getNroEscritura() != null ? e.getNroEscritura() : "-",
                    e.getFecha() != null ? e.getFecha().toString() : "-",
                    e.getNotaria() != null ? (e.getNotaria().length() > 30 ? e.getNotaria().substring(0, 27) + "..." : e.getNotaria()) : "-",
                    e.getTomo() != null ? e.getTomo() : "-",
                    e.getFolio() != null ? e.getFolio() : "-"));
            }
            System.out.println("\nTotal: " + escrituras.size() + " escritura(s)");
        }
    }
    
    private static void buscarEscrituraPorId() throws Exception {
        System.out.println("\n--- BUSCAR ESCRITURA POR ID ---");
        System.out.print("Ingrese el ID de la escritura: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            long id = Long.parseLong(idStr);
            EscrituraNotarial escritura = escrituraService.getById(id);
            
            if (escritura == null) {
                System.out.println("\n❌ No se encontró una escritura con ID: " + id);
            } else {
                mostrarEscritura(escritura);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido");
        }
    }
    
    private static void actualizarEscritura() throws Exception {
        System.out.println("\n--- ACTUALIZAR ESCRITURA ---");
        System.out.print("Ingrese el ID de la escritura a actualizar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            long id = Long.parseLong(idStr);
            EscrituraNotarial escritura = escrituraService.getById(id);
            
            if (escritura == null) {
                System.out.println("\n❌ No se encontró una escritura con ID: " + id);
                return;
            }
            
            System.out.println("\nEscritura actual:");
            mostrarEscritura(escritura);
            System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el valor actual):");
            
            System.out.print("Número de Escritura [" + (escritura.getNroEscritura() != null ? escritura.getNroEscritura() : "") + "]: ");
            String nroEscritura = scanner.nextLine().trim();
            if (!nroEscritura.isEmpty()) {
                escritura.setNroEscritura(nroEscritura.toUpperCase());
            }
            
            System.out.print("Fecha (YYYY-MM-DD) [" + (escritura.getFecha() != null ? escritura.getFecha().toString() : "") + "]: ");
            String fechaStr = scanner.nextLine().trim();
            if (!fechaStr.isEmpty()) {
                try {
                    escritura.setFecha(LocalDate.parse(fechaStr, dateFormatter));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("La fecha debe tener el formato YYYY-MM-DD");
                }
            }
            
            System.out.print("Notaría [" + (escritura.getNotaria() != null ? escritura.getNotaria() : "") + "]: ");
            String notaria = scanner.nextLine().trim();
            if (!notaria.isEmpty()) {
                escritura.setNotaria(notaria);
            }
            
            System.out.print("Tomo [" + (escritura.getTomo() != null ? escritura.getTomo() : "") + "]: ");
            String tomo = scanner.nextLine().trim();
            if (!tomo.isEmpty()) {
                escritura.setTomo(tomo.toUpperCase());
            }
            
            System.out.print("Folio [" + (escritura.getFolio() != null ? escritura.getFolio() : "") + "]: ");
            String folio = scanner.nextLine().trim();
            if (!folio.isEmpty()) {
                escritura.setFolio(folio.toUpperCase());
            }
            
            System.out.print("Observaciones [" + (escritura.getObservaciones() != null ? escritura.getObservaciones() : "") + "]: ");
            String observaciones = scanner.nextLine().trim();
            if (!observaciones.isEmpty()) {
                escritura.setObservaciones(observaciones);
            }
            
            escrituraService.actualizar(escritura);
            System.out.println("\n✅ Escritura actualizada exitosamente");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido");
        }
    }
    
    private static void eliminarEscritura() throws Exception {
        System.out.println("\n--- ELIMINAR ESCRITURA (LÓGICO) ---");
        System.out.print("Ingrese el ID de la escritura a eliminar: ");
        String idStr = scanner.nextLine().trim();
        
        try {
            long id = Long.parseLong(idStr);
            EscrituraNotarial escritura = escrituraService.getById(id);
            
            if (escritura == null) {
                System.out.println("\n❌ No se encontró una escritura con ID: " + id);
                return;
            }
            
            System.out.print("\n¿Está seguro de eliminar esta escritura? (S/N): ");
            String confirmar = scanner.nextLine().trim().toUpperCase();
            
            if (confirmar.equals("S") || confirmar.equals("SI")) {
                escrituraService.eliminar(id);
                System.out.println("\n✅ Escritura eliminada exitosamente (baja lógica)");
            } else {
                System.out.println("\nOperación cancelada");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID debe ser un número válido");
        }
    }
    
    private static void buscarPropiedadPorPadron() throws Exception {
        System.out.println("\n--- BUSCAR PROPIEDAD POR PADRÓN CATASTRAL ---");
        System.out.print("Ingrese el padrón catastral: ");
        String padron = scanner.nextLine().trim().toUpperCase();
        
        if (padron.isEmpty()) {
            throw new IllegalArgumentException("El padrón catastral no puede estar vacío");
        }
        
        Propiedad propiedad = propiedadService.buscarPorPadron(padron);
        
        if (propiedad == null) {
            System.out.println("\n❌ No se encontró una propiedad con padrón catastral: " + padron);
        } else {
            mostrarPropiedad(propiedad);
        }
    }
    
    private static void buscarEscrituraPorNumero() throws Exception {
        System.out.println("\n--- BUSCAR ESCRITURA POR NÚMERO ---");
        System.out.print("Ingrese el número de escritura: ");
        String nroEscritura = scanner.nextLine().trim().toUpperCase();
        
        if (nroEscritura.isEmpty()) {
            throw new IllegalArgumentException("El número de escritura no puede estar vacío");
        }
        
        EscrituraNotarial escritura = escrituraService.buscarPorNumero(nroEscritura);
        
        if (escritura == null) {
            System.out.println("\n❌ No se encontró una escritura con número: " + nroEscritura);
        } else {
            mostrarEscritura(escritura);
        }
    }
    
    private static void mostrarPropiedad(Propiedad propiedad) {
        System.out.println("\n--- DETALLE DE PROPIEDAD ---");
        System.out.println("ID: " + propiedad.getId());
        System.out.println("Padrón Catastral: " + propiedad.getPadronCatastral());
        System.out.println("Dirección: " + propiedad.getDireccion());
        System.out.println("Superficie (m²): " + propiedad.getSuperficieM2());
        System.out.println("Destino: " + (propiedad.getDestino() != null ? propiedad.getDestino() : "No especificado"));
        System.out.println("Antigüedad: " + (propiedad.getAntiguedad() != null ? propiedad.getAntiguedad() + " años" : "No especificada"));
        System.out.println("Eliminado: " + (propiedad.getEliminado() ? "Sí" : "No"));
        
        if (propiedad.getEscrituraNotarial() != null) {
            System.out.println("\n--- ESCRITURA NOTARIAL ASOCIADA ---");
            mostrarEscritura(propiedad.getEscrituraNotarial());
        } else {
            System.out.println("\nNo tiene escritura notarial asociada");
        }
    }
    
    private static void mostrarEscritura(EscrituraNotarial escritura) {
        System.out.println("\n--- DETALLE DE ESCRITURA NOTARIAL ---");
        System.out.println("ID: " + escritura.getId());
        System.out.println("Número de Escritura: " + (escritura.getNroEscritura() != null ? escritura.getNroEscritura() : "No especificado"));
        System.out.println("Fecha: " + (escritura.getFecha() != null ? escritura.getFecha().toString() : "No especificada"));
        System.out.println("Notaría: " + (escritura.getNotaria() != null ? escritura.getNotaria() : "No especificada"));
        System.out.println("Tomo: " + (escritura.getTomo() != null ? escritura.getTomo() : "No especificado"));
        System.out.println("Folio: " + (escritura.getFolio() != null ? escritura.getFolio() : "No especificado"));
        System.out.println("Observaciones: " + (escritura.getObservaciones() != null ? escritura.getObservaciones() : "No especificadas"));
        System.out.println("ID Propiedad: " + (escritura.getPropiedadId() != null ? escritura.getPropiedadId() : "No especificado"));
        System.out.println("Eliminado: " + (escritura.getEliminado() ? "Sí" : "No"));
    }
}
