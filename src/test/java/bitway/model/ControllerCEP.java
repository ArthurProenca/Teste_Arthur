package bitway.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(aux);

            String cep = node.get("cep").asText();
            String logradouro = node.get("logradouro").asText();
            String complemento = node.get("complemento").asText();
            String bairro = node.get("bairro").asText();
            String localidade = node.get("localidade").asText();
            String uf = node.get("uf").asText();
            String IBGE = node.get("ibge").asText();
            if(IBGE == ""){
                IBGE = "0";
            }
            String GIA = node.get("gia").asText();
            String DDD = node.get("ddd").asText();
            if(DDD == ""){
                DDD = "0";
            }
            String siafi = node.get("siafi").asText();
            if(siafi == ""){
                siafi = "0";
            }

            CEP c = new CEP(1, localidade, logradouro, cep, complemento, bairro, uf, GIA, Integer.parseInt(DDD), Integer.parseInt(siafi), Integer.parseInt(IBGE));

            InputStream serviceAccount = new FileInputStream("src/Database/cep-bitway-firebase-adminsdk-ba1xe-3a73714aaa.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            DocumentReference docRef = db.collection("CEP").document();

            Map<String, Object> data = new HashMap<>();

            data.put("CEP", c.getCEP());
            data.put("DDD", c.getDDD());
            data.put("GIA", c.getGIA());
            data.put("IBGE", c.getIBGE());
            data.put("SIAFI", c.getSIAFI());
            data.put("UF", c.getUF());
            data.put("bairro", c.getBairro());
            data.put("complemento", c.getComplemento());
            data.put("id", 0);
            data.put("logradouro", c.getLogradouro());
            data.put("localidade", c.getCidade());

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.set(data);
            // ...
            // result.get() blocks on response
            System.out.println("Update time : " + result.get().getUpdateTime());

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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

            CreateInstance((Search(url)));
        } else {
            System.out.println("Invalid CEP, type a valid CEP." +
                    "\nA valid CEP are composed by IIIII-III, where 'I' are a number.");
        }
    }
}
