import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator {
    public static void main(String[] args) {
        ArrayList<String> person = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean doneInput = false;
        String ID;
        String fName;
        String lName;
        String Title;
        int YOB;

        do {
            ID = SafeInput.getNonZeroLenString(in, "Enter your ID (000001)");
            fName = SafeInput.getNonZeroLenString(in, "Enter your First Name ");
            lName = SafeInput.getNonZeroLenString(in, "Enter your Last Name ");
            Title = SafeInput.getNonZeroLenString(in, "Enter your Title ");
            YOB = SafeInput.getRangedInt(in, "Enter your Birth Year ", 1000, 9999);

            String personRec = String.format("%s, %s, %s, %s, %d", ID, fName, lName, Title, YOB);
            person.add(personRec);

            doneInput = !SafeInput.getYNConfirm(in, "Do you have another record? ");

        } while (!doneInput);

        //File name
        String fileName = SafeInput.getNonZeroLenString(in, "Enter the name you would like to save your record as");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + File.separator + fileName + "PersonTestData.txt");

        try {
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Print header in terminal
            System.out.println("\nGenerated Person Records:");
            System.out.println(String.format("%-8s %-15s %-15s %-8s %s", "ID#", "Firstname", "Lastname", "Title", "YOB"));
            System.out.println("=======================================================");

            for (String rec : person) {
                writer.write(rec, 0, rec.length());
                writer.newLine();

                // Print record in terminal
                String[] fields = rec.split(", ");
                System.out.println(String.format("%-8s %-15s %-15s %-8s %s",
                        fields[0], fields[1], fields[2], fields[3], fields[4]));
            }
            writer.close();
            System.out.println("\nData file written to: " + file.toString());
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}


