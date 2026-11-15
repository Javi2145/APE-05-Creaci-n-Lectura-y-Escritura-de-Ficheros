/*
 * EJERCICIO 10 - Sistema de ventas de una cafetería
 * Archivo: ventas.txt
 *
 * Una cafetería almacena sus ventas en un archivo llamado ventas.txt. Cada
 * línea contiene el nombre de un producto y su precio, separados por una coma.
 * Desarrolla un programa en Java que permita registrar nuevas ventas utilizando
 * BufferedWriter, leer los registros almacenados, convertir los precios de texto a
 * números decimales y calcular el total de ventas. Además, deberá identificar el
 * producto de mayor precio, filtrar los productos que superen un valor
 * determinado y validar que los precios sean correctos.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio10_VentasCafeteria {
    static final String ARCHIVO = "ventas.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== VENTAS DE LA CAFETERÍA ===");
            System.out.println("1. Registrar venta");
            System.out.println("2. Ver ventas, total y producto de mayor precio");
            System.out.println("3. Filtrar productos que superen un valor");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Producto: ");
                    String producto = sc.nextLine().trim();
                    System.out.print("Precio: ");
                    registrar(producto, sc.nextLine().trim());
                    break;
                case 2:
                    resumen();
                    break;
                case 3:
                    System.out.print("Valor mínimo: ");
                    try {
                        filtrar(Double.parseDouble(sc.nextLine().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;
                case 4:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
        sc.close();
    }

    static void registrar(String producto, String precioTexto) {
        if (producto.isEmpty() || producto.contains(",")) {
            System.out.println("Nombre de producto inválido.");
            return;
        }
        double precio;
        try {
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException e) {
            System.out.println("El precio no es numérico.");
            return;
        }
        if (precio <= 0) {
            System.out.println("El precio debe ser mayor que 0.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(producto + "," + precio);
            bw.newLine();
            System.out.println("Venta registrada.");
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    // Lee el archivo y carga productos y precios válidos en las listas
    static boolean cargar(List<String> productos, List<Double> precios) {
        if (!new File(ARCHIVO).exists()) {
            System.out.println("El archivo " + ARCHIVO + " aún no existe.");
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.out.println("Registro con formato inválido: " + linea);
                    continue;
                }
                try {
                    double precio = Double.parseDouble(partes[1].trim());
                    if (precio <= 0) {
                        System.out.println("Precio no válido (<= 0): " + linea);
                        continue;
                    }
                    productos.add(partes[0].trim());
                    precios.add(precio);
                } catch (NumberFormatException e) {
                    System.out.println("Precio no numérico: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return false;
        }
        return true;
    }

    static void resumen() {
        List<String> productos = new ArrayList<>();
        List<Double> precios = new ArrayList<>();
        if (!cargar(productos, precios)) {
            return;
        }
        if (productos.isEmpty()) {
            System.out.println("No hay ventas válidas.");
            return;
        }

        double total = 0;
        int idxMax = 0;
        System.out.println("--- Ventas registradas ---");
        for (int i = 0; i < productos.size(); i++) {
            System.out.printf("%s - $%.2f%n", productos.get(i), precios.get(i));
            total += precios.get(i);
            if (precios.get(i) > precios.get(idxMax)) {
                idxMax = i;
            }
        }
        System.out.printf("Total de ventas: $%.2f%n", total);
        System.out.printf("Producto de mayor precio: %s ($%.2f)%n", productos.get(idxMax), precios.get(idxMax));
    }

    static void filtrar(double minimo) {
        List<String> productos = new ArrayList<>();
        List<Double> precios = new ArrayList<>();
        if (!cargar(productos, precios)) {
            return;
        }
        System.out.println("--- Productos con precio mayor a " + minimo + " ---");
        boolean alguno = false;
        for (int i = 0; i < productos.size(); i++) {
            if (precios.get(i) > minimo) {
                System.out.printf("%s - $%.2f%n", productos.get(i), precios.get(i));
                alguno = true;
            }
        }
        if (!alguno) {
            System.out.println("(ninguno)");
        }
    }
}
