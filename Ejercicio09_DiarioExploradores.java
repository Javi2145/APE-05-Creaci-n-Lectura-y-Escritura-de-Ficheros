/*
 * EJERCICIO 9 - Diario de exploradores
 * Archivo: descubrimientos.txt
 *
 * Un grupo de exploradores registra sus descubrimientos en un archivo llamado
 * descubrimientos.txt. Desarrolla un programa que permita escribir nuevos
 * descubrimientos, agregar información al final del archivo y leer los registros
 * línea por línea. El programa deberá contar los descubrimientos, buscar uno
 * específico por su nombre y validar que el archivo exista y contenga información
 * antes de procesarlo. También deberá manejar las posibles excepciones
 * durante la lectura y escritura.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio09_DiarioExploradores {
    static final String ARCHIVO = "descubrimientos.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== DIARIO DE EXPLORADORES ===");
            System.out.println("1. Escribir nuevo descubrimiento");
            System.out.println("2. Leer descubrimientos y contarlos");
            System.out.println("3. Buscar descubrimiento por nombre");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Descubrimiento: ");
                    escribir(sc.nextLine().trim());
                    break;
                case 2:
                    leer();
                    break;
                case 3:
                    System.out.print("Nombre a buscar: ");
                    buscar(sc.nextLine().trim());
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

    static void escribir(String texto) {
        if (texto.isEmpty()) {
            System.out.println("El descubrimiento no puede estar vacío.");
            return;
        }
        // true = agrega al final del archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(texto);
            bw.newLine();
            System.out.println("Descubrimiento registrado.");
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    // Valida que el archivo exista y tenga información
    static boolean archivoValido() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            System.out.println("El archivo " + ARCHIVO + " no existe.");
            return false;
        }
        if (f.length() == 0) {
            System.out.println("El archivo está vacío.");
            return false;
        }
        return true;
    }

    static void leer() {
        if (!archivoValido()) {
            return;
        }
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            System.out.println("--- Descubrimientos ---");
            while ((linea = br.readLine()) != null) {
                total++;
                System.out.println(total + ". " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }
        System.out.println("Total de descubrimientos: " + total);
    }

    static void buscar(String nombre) {
        if (!archivoValido()) {
            return;
        }
        boolean encontrado = false;
        int numLinea = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                if (linea.equalsIgnoreCase(nombre)) {
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }
        System.out.println(encontrado
                ? "Encontrado en la línea " + numLinea + "."
                : "No se encontró ese descubrimiento.");
    }
}
