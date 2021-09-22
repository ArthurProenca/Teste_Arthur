package bitway.model;

import java.io.*;
import java.util.ArrayList;

public class EasyCSV {
    public static File CreateCSV(String NameOfFile) {

        if (!NameOfFile.endsWith(".csv")) {
            System.out.println("File must be a csv.");
            return null;
        }

        System.out.println("Accessing create CSV method." +
                "\nWe're going to create a file with the name " +
                NameOfFile + ".");

        try {
            File path = new File("Database/CSV/");

            if (!path.exists()) {
                path.mkdirs();
            }

            File file = new File(path, NameOfFile);

            if (file.exists()) {
                return file;
            } else {
                file.createNewFile();
            }

            return file;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void WriteCSV(ArrayList AL, File file) {
        try {
            String s = AL.toString().replaceAll("\\]", "").replaceAll("\\[", "");

            FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter buffer_writer = new BufferedWriter(writer);

            buffer_writer.write(s);
            buffer_writer.newLine();
            buffer_writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList ReadCSV(File file) {
        try {
            /*Estou escrevendo cada linha como 1 arraylist. Quero n arrayslists*/

            FileReader ler = new FileReader(file.getAbsolutePath());
            System.out.println(file.getAbsolutePath());

            BufferedReader reader = new BufferedReader(ler);

            ArrayList<String> Data = new ArrayList<>();

            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                Data.add(line);
                i++;
                System.out.println(i);
            }

            return Data;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

