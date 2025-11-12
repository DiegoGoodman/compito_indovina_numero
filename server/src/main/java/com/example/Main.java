package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(3000);
        Socket s = ss.accept();
        

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
                if (parts.length == 3){
                    if (tentativi == 0){
                        try {
                            range_number1 = Integer.parseInt(parts[1]);
                            range_number2 = Integer.parseInt(parts[2]);
                            if (range_number1 >= range_number2) out.println("ERR SYNTAX"); 
                            else out.println("OK RANGE " + range_number1 + " " + range_number2);
                            segreto = ThreadLocalRandom.current().nextInt(range_number1, range_number2+1);
                        } catch (NumberFormatException ex) {
                            out.println("ERR SYNTAX");
                        }
                    }
                    else out.println("ERR NOTALLOWED");
                } else out.println("ERR SYNTAX");
            }
            else if (parts[0].equals("GUESS")){
                if (parts.length == 2){
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
                    } catch (IndexOutOfBoundsException ex) {
                        out.println("ERR SYNTAX");
                    }
                } else out.println("ERR SYNTAX");
            }
            else if (parts[0].equals("STATS")){
                if (parts.length == 1)
                    out.println("INFO RANGE " + range_number1 + " " + range_number2 + "; " + "TRIES " + tentativi);
                else out.println("ERR SYNTAX");
            }
            else if (parts[0].equals("NEW")){
                if (parts.length == 1){
                    tentativi = 0;
                    segreto = ThreadLocalRandom.current().nextInt(range_number1, range_number2+1);
                    out.println("OK NEW");
                    out.println("WELCOME INDOVINA v1 RANGE " + range_number1 + " " + range_number2);
                }
                else out.println("ERR SYNTAX");
            }
            else if (parts[0].equals("QUIT")){
                if (parts.length == 1)
                    out.println("BYE");
                else out.println("ERR SYNTAX");
            }
            else {
                out.println("ERR UKNOWNCMD");
            }
        }while(!comando.equals("QUIT"));
        ss.close();
    }
}