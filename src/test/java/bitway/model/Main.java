package bitway.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        int i = 0, cont = 1, opt;
        Scanner in = new Scanner( System.in );

        while(cont != 0){
            System.out.println("""
                    Welcome to request CEP program.
                    1 - Search by city name
                    2 - Search by CEP
                    3 - Quit""");
            opt = in.nextInt();
            switch (opt) {
                case 1 -> System.out.println(getHTML("https://viacep.com.br/ws/01001000/json/"));
                case 2 -> System.out.println(getHTML("https://viacep.com.br/ws/MG/Ita%C3%BA%20de%20Minas/json/"));
                case 3 -> cont = 0;
            }
        }
    }
}
