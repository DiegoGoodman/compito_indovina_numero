package com.example;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        ServerSocket ss = new ServerSocket(3000);
        Socket s = new Socket();
        ss.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        int range_number1 = 1;
        int range_number2 = 100;
        int segreto = ThreadLocalRandom.current().nextInt(1, 101);
        int tentativi = 0;
        out.println("WELCOME INDOVINA v1 RANGE " + range_number1 + " " + range_number2);
        String comando;
        do {
            comando = in.readLine();
            String[] parts = comando.split(" "); 
            if (parts[0].equals("RANGE")){
                if (tentativi == 0){
                    try {
                        range_number1 = Integer.parseInt(parts[1]);
                        range_number2 = Integer.parseInt(parts[2]);
                        if (range_number1 >= range_number2) out.println("ERR SYNTAX"); 
                        out.println("OK RANGE " + range_number1 + " " + range_number2);
                    } catch (NumberFormatException ex) {
                        out.println("ERR SYNTAX");
                    }
                }
                else out.println("ERR NOTALLOWED");
            }
            else if (parts[0].equals("GUESS")){
                try {
                    int numeroTentativo = Integer.parseInt(parts[1]);
                    if (numeroTentativo < range_number1 || numeroTentativo > range_number2){
                        out.println("ERR OOUTOFRANGE " + range_number1 + " " + range_number2);
                    }
                    else if (numeroTentativo < segreto) {out.println("HINT LOWER"); tentativi++;}
                    else if (numeroTentativo == segreto) out.println("OK CORRECT in T=" + tentativi);
                    else {out.println("HINT HIGHER"); tentativi++;}
                } catch (NumberFormatException ex) {
                    out.println("ERR SYNTAX");
                }
            }
            else if (parts[0].equals("STATS")){
                out.println("INFO RANGE " + range_number1 + " " + range_number2 + "; " + "TRIES " + tentativi);
            }
            else if (parts[0].equals("NEW")){
                tentativi = 0;
                segreto = ThreadLocalRandom.current().nextInt(1, 101);
                out.println("OK NEW");
                out.println("WELCOME INDOVINA v1 RANGE " + range_number1 + " " + range_number2);
            }
            else if (parts[0].equals("QUIT")){
                out.println("BYE");
            }
            else {
                out.println("ERR UKNOWNCMD");
            }
        }while(!comando.equals("QUIT"));
        ss.close();
    }
}