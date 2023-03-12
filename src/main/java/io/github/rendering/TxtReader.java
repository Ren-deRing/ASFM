package io.github.rendering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static String TxTOneLineRead(String file) throws InterruptedException {

        String temp = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            temp = reader.readLine();
        } catch (Exception e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing text.\n" + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }
        return temp;
    }
}
