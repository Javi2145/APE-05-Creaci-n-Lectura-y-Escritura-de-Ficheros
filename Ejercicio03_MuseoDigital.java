/*
 * EJERCICIO 3 - Museo digital: registro de obras
 * Archivo: obras.txt
 *
 * Un museo almacena información de sus obras de arte en un archivo llamado obras.txt.
 * Cada línea contiene el nombre de una obra y su categoría. Desarrolla un programa
 * que lea los registros, muestre las obras y clasifique cuántas pertenecen a cada
 * categoría.
 *
 * Formato de cada línea: nombre,categoria   (ej.: La Gioconda,Pintura)
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Ejercicio03_MuseoDigital {
    public static void main(String[] args) {
        String archivo = "obras.txt";

        if (!new File(archivo).exists()) {
            System.out.println("El archivo " + archivo + " no existe.");
            return;
        }

        Map<String, Integer> categorias = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("=== OBRAS DEL MUSEO ===");
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.out.println("Registro con formato inválido: " + linea);
                    continue;
                }
                String nombre = partes[0].trim();
                String categoria = partes[1].trim();
                System.out.println("Obra: " + nombre + " | Categoría: " + categoria);
                categorias.put(categoria, categorias.getOrDefault(categoria, 0) + 1);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        System.out.println("\n=== CLASIFICACIÓN POR CATEGORÍA ===");
        for (Map.Entry<String, Integer> entrada : categorias.entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue() + " obra(s)");
        }
    }
}
