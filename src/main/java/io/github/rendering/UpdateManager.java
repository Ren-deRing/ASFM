package io.github.rendering;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UpdateManager {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static boolean IsAvailableUpdate() throws InterruptedException, IOException {

        String appdata = System.getenv("APPDATA");
        File file = new File(appdata + "/ASFM/newver.txt");

        if( file.exists() ){
            file.delete();
        }
        try (InputStream in = URI.create("https://github.com/Ren-deRing/ASFM/releases/latest/download/ver.txt").toURL().openStream()) {

            Files.copy(in, Paths.get(appdata + "/ASFM/newver.txt"));
        } catch (IOException e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error related to a file occurred while checking for updates.\n" + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }
        String newverS = TxtReader.TxTOneLineRead(appdata + "/ASFM/newver.txt");
        String verS = TxtReader.TxTOneLineRead(appdata + "/ASFM/ver.txt");

        int newver = 0, ver = 0;

        try {
            newver = Integer.parseInt(newverS.replaceAll("[.]", ""));
            ver = Integer.parseInt(verS.replaceAll("[.]", ""));
        } catch (Exception e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing the update version.\n" + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }

        if (newver > ver) {
            return true;
        } else {
            return false;
        }
    }

}
