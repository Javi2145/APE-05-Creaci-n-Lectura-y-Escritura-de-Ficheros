/*
 * EJERCICIO 4 - Parque de aventuras: resultados de participantes
 * Archivo: resultados.txt
 *
 * Un parque de aventuras almacena los nombres y puntajes de sus participantes en un
 * archivo llamado resultados.txt. Desarrolla un programa que lea la información
 * utilizando BufferedReader, muestre los participantes, calcule el puntaje total e
 * identifique el puntaje más alto.
 *
 * Formato de cada línea: nombre,puntaje   (ej.: Ana,85)
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio04_ParqueAventuras {
    public static void main(String[] args) {
        String archivo = "resultados.txt";

        if (!new File(archivo).exists()) {
            System.out.println("El archivo " + archivo + " no existe.");
            return;
        }

        int total = 0;
        int maximo = Integer.MIN_VALUE;
        String ganador = "";
        int participantes = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("=== PARTICIPANTES ===");
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.out.println("Registro inválido: " + linea);
                    continue;
                }
                try {
                    String nombre = partes[0].trim();
                    int puntaje = Integer.parseInt(partes[1].trim());
                    System.out.println(nombre + " - " + puntaje + " puntos");
                    total += puntaje;
                    participantes++;
                    if (puntaje > maximo) {
                        maximo = puntaje;
                        ganador = nombre;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Puntaje no numérico en: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        if (participantes == 0) {
            System.out.println("No hay participantes válidos.");
            return;
        }

        System.out.println("\nPuntaje total: " + total);
        System.out.println("Puntaje más alto: " + maximo + " (" + ganador + ")");
    }
}
