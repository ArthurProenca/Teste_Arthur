package bitway.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public interface ControllerCEP {
    static String Busca(String typed_url) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(typed_url);

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

    static boolean VerifyCEP(String CEP) {
        if (CEP.length() != 9) {
            return false;
        } else {
            if (CEP.charAt(5) != '-') {
                return false;
            }
            if (CEP.matches("[a-z?]")) {
                return false;
            }
        }
        return true;
    }

    static void SearchCity() throws Exception {
        String City, State, Adress;
        Scanner in = new Scanner(System.in);
        System.out.println("Type the city name: ");
        City = in.nextLine();
        System.out.println("Type the UF (State): ");
        State = in.nextLine();

        String url = "https://viacep.com.br/ws/" + State + "/" + City + "/json/";

        if (Busca(url).equals("[]")) {
            System.out.println("Type the street's name: ");
            Adress = in.nextLine();
            url = "https://viacep.com.br/ws/" + State + "/" + City + "/" + Adress + "/json/";
            System.out.println(Busca(url));
        }

    }

    static void SearchCEP() throws Exception {
        String CEP;
        Scanner in = new Scanner(System.in);
        System.out.println("Type the CEP: ");
        CEP = in.nextLine();

        if (VerifyCEP(CEP)) {
            String url = "https://viacep.com.br/ws/" + CEP + "/json/";
            System.out.println(Busca(url));
        } else {
            System.out.println("Invalid CEP, type a valid CEP." +
                    "\nA valid CEP are composed by IIIII-III, where 'I' are a number.");
        }
    }
}
