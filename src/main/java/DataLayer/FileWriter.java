package DataLayer;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class FileWriter {

    /**
     * Aceasta metoda scrie linie cu linie intr-un fisier
     * @param filename - unde va scrie
     * @param line - linia
     */
    public static void writeLineToFile(String filename, String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filename,true));
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileContent(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filename));
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
