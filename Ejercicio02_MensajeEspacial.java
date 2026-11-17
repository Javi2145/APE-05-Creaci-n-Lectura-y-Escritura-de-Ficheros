/*
 * EJERCICIO 2 - Estación espacial: descifrado de mensajes
 * Archivo: mensaje.txt
 *
 * Una estación espacial recibe un mensaje de emergencia almacenado en mensaje.txt.
 * Desarrolla un programa que lea el archivo carácter por carácter utilizando FileReader y
 * el método read(), muestre su contenido y contabilice el número total de caracteres.
 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio02_MensajeEspacial {
    public static void main(String[] args) {
        String archivo = "mensaje.txt";

        if (!new File(archivo).exists()) {
            System.out.println("El archivo " + archivo + " no existe.");
            return;
        }

        int total = 0;
        try (FileReader fr = new FileReader(archivo)) {
            int caracter;
            System.out.println("=== MENSAJE DE EMERGENCIA ===");
            while ((caracter = fr.read()) != -1) {
                System.out.print((char) caracter);
                total++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        System.out.println();
        System.out.println("Total de caracteres: " + total);
    }
}
