/*
 * EJERCICIO 8 - Control de temperatura
 * Archivo: temperaturas.txt
 *
 * Una estación meteorológica almacena las temperaturas registradas durante
 * una semana en un archivo llamado temperaturas.txt. Desarrolla un programa
 * que permita guardar nuevas temperaturas, leer los registros y convertir los
 * datos almacenados a valores numéricos. El programa deberá calcular la
 * temperatura promedio, identificar la temperatura más alta y determinar cuántos
 * registros superan los 30 grados. Además, deberá controlar los errores cuando
 * los datos no tengan un formato numérico válido.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio08_ControlTemperatura {
    static final String ARCHIVO = "temperaturas.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== CONTROL DE TEMPERATURA ===");
            System.out.println("1. Guardar nueva temperatura");
            System.out.println("2. Analizar registros");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Temperatura (°C): ");
                    guardar(sc.nextLine().trim());
                    break;
                case 2:
                    analizar();
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

    static void guardar(String texto) {
        try {
            Double.parseDouble(texto); // valida antes de guardar
        } catch (NumberFormatException e) {
            System.out.println("Valor no numérico, no se guardó.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(texto);
            bw.newLine();
            System.out.println("Temperatura guardada.");
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    static void analizar() {
        if (!new File(ARCHIVO).exists()) {
            System.out.println("El archivo " + ARCHIVO + " aún no existe.");
            return;
        }

        double suma = 0;
        double maxima = Double.NEGATIVE_INFINITY;
        int validos = 0;
        int mayoresA30 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    double t = Double.parseDouble(linea.trim());
                    System.out.println("Registro: " + t);
                    suma += t;
                    validos++;
                    if (t > maxima) {
                        maxima = t;
                    }
                    if (t > 30) {
                        mayoresA30++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Dato con formato inválido: \"" + linea + "\"");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }

        if (validos == 0) {
            System.out.println("No hay temperaturas válidas.");
            return;
        }

        System.out.printf("Temperatura promedio: %.2f °C%n", suma / validos);
        System.out.println("Temperatura más alta: " + maxima + " °C");
        System.out.println("Registros que superan 30 °C: " + mayoresA30);
    }
}
