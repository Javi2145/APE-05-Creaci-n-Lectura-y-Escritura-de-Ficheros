/*
 * EJERCICIO 1 - Agencia de detectives: análisis de pistas
 * Archivo: pistas.txt
 *
 * Una agencia de detectives almacena las pistas de una investigación en un archivo
 * llamado pistas.txt. Desarrolla un programa en Java que permita leer todas las pistas
 * línea por línea, mostrarlas en pantalla y contar cuántas pistas contiene el archivo.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio01_Pistas {
    public static void main(String[] args) {
        String archivo = "pistas.txt";

        if (!new File(archivo).exists()) {
            System.out.println("El archivo " + archivo + " no existe.");
            return;
        }

        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("=== PISTAS DE LA INVESTIGACIÓN ===");
            while ((linea = br.readLine()) != null) {
                contador++;
                System.out.println(contador + ". " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        System.out.println("Total de pistas: " + contador);
    }
}
