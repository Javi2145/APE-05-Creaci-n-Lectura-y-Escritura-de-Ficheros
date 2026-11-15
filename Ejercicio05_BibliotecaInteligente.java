/*
 * EJERCICIO 5 - Biblioteca inteligente
 * Archivo: libros.txt
 *
 * Una biblioteca necesita administrar sus libros mediante un archivo llamado
 * libros.txt. Desarrolla un programa en Java que permita registrar nuevos libros,
 * agregar información sin eliminar los registros anteriores, leer los títulos
 * almacenados y buscar un libro específico. Además, debe contar los libros
 * registrados y validar que el archivo exista antes de realizar la lectura.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio05_BibliotecaInteligente {
    static final String ARCHIVO = "libros.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== BIBLIOTECA INTELIGENTE ===");
            System.out.println("1. Registrar libro");
            System.out.println("2. Leer títulos");
            System.out.println("3. Buscar libro");
            System.out.println("4. Contar libros");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Título del libro: ");
                    registrar(sc.nextLine().trim());
                    break;
                case 2:
                    leer();
                    break;
                case 3:
                    System.out.print("Título a buscar: ");
                    buscar(sc.nextLine().trim());
                    break;
                case 4:
                    System.out.println("Libros registrados: " + contar());
                    break;
                case 5:
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
        sc.close();
    }

    // true = modo append: no elimina los registros anteriores
    static void registrar(String titulo) {
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }
        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {
            fw.write(titulo + System.lineSeparator());
            System.out.println("Libro registrado.");
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    static boolean existe() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            System.out.println("El archivo " + ARCHIVO + " aún no existe.");
            return false;
        }
        return true;
    }

    static void leer() {
        if (!existe()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int n = 0;
            System.out.println("--- Títulos almacenados ---");
            while ((linea = br.readLine()) != null) {
                n++;
                System.out.println(n + ". " + linea);
            }
            if (n == 0) {
                System.out.println("(archivo vacío)");
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    static void buscar(String titulo) {
        if (!existe()) {
            return;
        }
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.equalsIgnoreCase(titulo)) {
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }
        System.out.println(encontrado ? "El libro SÍ está registrado." : "El libro NO está registrado.");
    }

    static int contar() {
        if (!new File(ARCHIVO).exists()) {
            return 0;
        }
        int n = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    n++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return n;
    }
}
