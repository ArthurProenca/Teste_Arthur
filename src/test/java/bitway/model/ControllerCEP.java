package bitway.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public interface ControllerCEP {
    static String Search(String typed_url) throws Exception {
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

    static void CreateInstance(String temp) {
        String aux = temp.replace("[", "");
        aux = aux.replace("]", "");
        aux = aux.replace(" ", "");
        System.out.println(aux);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(aux);
             /*
        "[
            {
                "cep": "37902-050",
                "logradouro": "Rua Coelho Neto",
                "complemento": "",
                "bairro": "São Francisco",
                "localidade": "Passos",
                "uf": "MG",
                "ibge": "3147907",
                "gia": "",
                "ddd": "35",
                "siafi": "4957"
             }
         ]"
         */

            String cep = node.get("cep").asText();
            String logradouro = node.get("logradouro").asText();
            String bairro = node.get("bairro").asText();
            String localidade = node.get("localidade").asText();
            String uf = node.get("uf").asText();
            String IBGE = node.get("ibge").asText();
            String GIA = node.get("gia").asText();
            String DDD = node.get("ddd").asText();
            String siafi = node.get("siafi").asText();

            System.out.println(DDD + " " + siafi + " " + localidade);


        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    static boolean VerifyCEP(String CEP) {
        if (CEP.length() != 9) {
            return false;
        } else {
            if (CEP.charAt(5) != '-') {
                return false;
            }
            return !CEP.matches("[a-z?]");
        }
    }

    static void SearchCity() throws Exception {
        String City, State, Adress;
        Gson g = new Gson();
        Scanner in = new Scanner(System.in);
        System.out.println("Type the city name: ");
        City = in.nextLine();
        System.out.println("Type the UF (State): ");
        State = in.nextLine();

        String url = "https://viacep.com.br/ws/" + State + "/" + City + "/json/";

        if (Search(url).equals("[]")) {
            System.out.println("Type the street's name: ");
            Adress = in.nextLine();
            url = "https://viacep.com.br/ws/" + State + "/" + City + "/" + Adress + "/json/";
            CreateInstance((Search(url)));
            //CEP c = CreateInstance(g.toJson(Search(url)));

        } else {
            //CEP c = CreateInstance(g.toJson(Search(url)));
        }
    }

    static void SearchCEP() throws Exception {
        String CEP;
        Scanner in = new Scanner(System.in);
        System.out.println("Type the CEP: ");
        CEP = in.nextLine();

        if (VerifyCEP(CEP)) {
            String url = "https://viacep.com.br/ws/" + CEP + "/json/";
            Gson g = new Gson();

            //CEP c = CreateInstance(g.toJson(Search(url)));
        } else {
            System.out.println("Invalid CEP, type a valid CEP." +
                    "\nA valid CEP are composed by IIIII-III, where 'I' are a number.");
        }
    }
}
