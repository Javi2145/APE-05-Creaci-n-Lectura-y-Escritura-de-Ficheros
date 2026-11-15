/*
 * EJERCICIO 7 - Competencia de videojuegos
 * Archivo: jugadores.txt
 *
 * Una competencia registra los nombres y puntuaciones de sus jugadores en un
 * archivo llamado jugadores.txt. Cada línea contiene el nombre del jugador y su
 * puntuación, separados por una coma. Desarrolla un programa que lea los
 * registros, convierta las puntuaciones de texto a números, calcule el promedio,
 * identifique al jugador con mayor puntaje y filtre a los participantes que
 * obtuvieron una puntuación superior a 70. También deberá validar el formato de
 * los registros.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio07_CompetenciaVideojuegos {
    public static void main(String[] args) {
        String archivo = "jugadores.txt";

        if (!new File(archivo).exists()) {
            System.out.println("El archivo " + archivo + " no existe.");
            return;
        }

        List<String> nombres = new ArrayList<>();
        List<Double> puntajes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length != 2 || partes[0].trim().isEmpty()) {
                    System.out.println("Línea " + numLinea + " con formato inválido: " + linea);
                    continue;
                }
                try {
                    double puntaje = Double.parseDouble(partes[1].trim());
                    nombres.add(partes[0].trim());
                    puntajes.add(puntaje);
                } catch (NumberFormatException e) {
                    System.out.println("Línea " + numLinea + " con puntuación no numérica: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        if (nombres.isEmpty()) {
            System.out.println("No hay registros válidos.");
            return;
        }

        double suma = 0;
        int idxMax = 0;
        for (int i = 0; i < puntajes.size(); i++) {
            suma += puntajes.get(i);
            if (puntajes.get(i) > puntajes.get(idxMax)) {
                idxMax = i;
            }
        }

        System.out.println("=== RESULTADOS ===");
        System.out.printf("Promedio de puntuaciones: %.2f%n", suma / puntajes.size());
        System.out.println("Mayor puntaje: " + nombres.get(idxMax) + " con " + puntajes.get(idxMax));

        System.out.println("\nJugadores con puntuación superior a 70:");
        boolean alguno = false;
        for (int i = 0; i < nombres.size(); i++) {
            if (puntajes.get(i) > 70) {
                System.out.println("- " + nombres.get(i) + ": " + puntajes.get(i));
                alguno = true;
            }
        }
        if (!alguno) {
            System.out.println("(ninguno)");
        }
    }
}
