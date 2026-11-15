/*
 * EJERCICIO 6 - Sistema de mensajes secretos
 * Archivo: secreto.txt
 *
 * Un grupo de investigadores almacena mensajes en un archivo llamado
 * secreto.txt. Desarrolla un programa en Java que permita escribir mensajes
 * utilizando FileWriter y BufferedWriter, agregar nuevos mensajes sin
 * sobrescribir los anteriores y leer su contenido mediante FileReader y
 * BufferedReader. El programa deberá mostrar los mensajes, contar las líneas
 * registradas y controlar posibles errores de entrada y salida.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio06_MensajesSecretos {
    static final String ARCHIVO = "secreto.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== MENSAJES SECRETOS ===");
            System.out.println("1. Escribir mensaje (agregar al final)");
            System.out.println("2. Mostrar mensajes y contar líneas");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Mensaje: ");
                    escribir(sc.nextLine());
                    break;
                case 2:
                    leer();
                    break;
                case 3:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
        sc.close();
    }

    static void escribir(String mensaje) {
        // append = true para no sobrescribir los mensajes anteriores
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(mensaje);
            bw.newLine();
            System.out.println("Mensaje guardado.");
        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        }
    }

    static void leer() {
        if (!new File(ARCHIVO).exists()) {
            System.out.println("El archivo " + ARCHIVO + " aún no existe.");
            return;
        }
        int lineas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            System.out.println("--- Mensajes ---");
            while ((linea = br.readLine()) != null) {
                lineas++;
                System.out.println(lineas + ": " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
            return;
        }
        System.out.println("Total de líneas registradas: " + lineas);
    }
}
