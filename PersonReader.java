import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class PersonReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // Display header
                System.out.println(String.format("%-8s %-15s %-15s %-8s %s", "ID#", "Firstname", "Lastname", "Title", "YOB"));
                System.out.println("=================================================================");

                // Read and display each line
                while (reader.ready()) {
                    rec = reader.readLine();
                    String[] fields = rec.split(", ");
                    if (fields.length == 5) {
                        System.out.println(String.format("%-8s %-15s %-15s %-8s %s",
                                fields[0], fields[1], fields[2], fields[3], fields[4]));
                    }
                }
                reader.close();
                System.out.println("\nData file read!");
            } else {
                System.out.println("No file selected. Exiting program.");
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found! Sorry!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}