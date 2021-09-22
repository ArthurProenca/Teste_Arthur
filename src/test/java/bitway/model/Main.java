package bitway.model;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        int cont = 1, opt;
        Scanner in = new Scanner( System.in );
        while(cont != 0){
            System.out.println("""
                    Welcome to request CEP program.
                    1 - Search by city name
                    2 - Search by CEP
                    3 - Quit""");
            opt = in.nextInt();
            switch (opt) {
                case 1 -> ControllerCEP.SearchCity();
                case 2 -> ControllerCEP.SearchCEP();
                case 3 -> cont = 0;
            }
        }
    }
}
