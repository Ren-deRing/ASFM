package io.github.rendering;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static io.github.rendering.Main.appdata;


public class TextManager {
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
    public static void writeToJson(String file, String name, String text) throws InterruptedException {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(file)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            jsonObject.put(name, text);
            FileWriter savefile = new FileWriter(file);
            savefile.write(jsonObject.toJSONString());
            savefile.flush();
            savefile.close();

        } catch (IOException e) {
            System.out.println(ANSI_RED + "[ ERROR ] lang.json file does not exist." + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while saving json.\n - " + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }
    }

}
