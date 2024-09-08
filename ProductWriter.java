import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main(String[] args) {
        ArrayList<String> product = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean doneInput = false;
        String ID;
        String Name;
        String Description;
        double Cost;

        do {
            ID = SafeInput.getNonZeroLenString(in, "Product ID");
            Name = SafeInput.getNonZeroLenString(in, "Product Name");
            Description = SafeInput.getNonZeroLenString(in, "Product Description");
            Cost = (double) SafeInput.getRangedDouble(in, "Product Cost", 1, 9999);

            String productRec = String.format("%s, %s, %s, %.2f", ID, Name, Description, Cost);
            product.add(productRec);

            doneInput = !SafeInput.getYNConfirm(in, "Do you have more products to enter?");
        } while (!doneInput);

        //File Name
        String fileName = SafeInput.getNonZeroLenString(in, "Enter the name you would like to save your record as");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + File.separator + fileName + ".txt");

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            //Header - Terminal
            System.out.println("\nGenerated Product Records:");
            System.out.println(String.format("%-8s %-15s %-25s %-8s%n", "ID#", "Name", "Description", "Cost"));
            System.out.println("==========================================================");

            for (String rec : product){
                writer.write(rec, 0, rec.length());
                writer.newLine();

                //Print Record Terminal
                String[] fields = rec.split(",\\s*");
                System.out.printf("%-8s %-15s %-25s %-8s%n", fields[0], fields[1], fields[2], fields[3]);
            }
            writer.close();
            System.out.println("\nData file written to: " + file.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
