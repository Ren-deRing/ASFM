package io.github.rendering;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    public static Boolean IsAutoFIleUpdate = null;
    public static String Language = null;
    public static String appdata = System.getenv("APPDATA");
    public static String ver;

    static {
        try {
            ver = TxtReader.TxTOneLineRead(appdata + "/ASFM/ver.txt");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) throws InterruptedException, IOException {
        JSONParser parser = new JSONParser();

        Boolean IsUseGui = null;
        Boolean IsCheckUpdate = null;
        Boolean IsAvailUpdate;

        String ver = TxtReader.TxTOneLineRead(appdata + "/ASFM/ver.txt");

        System.out.println("ASFM v" + ver);
        System.out.println("[ OK ] Java ASFM runs successfully.");
        System.out.println("[ ING ] Load setting.json File...");

        try (Reader reader = new FileReader(appdata + "/ASFM/setting.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            IsCheckUpdate = (Boolean) jsonObject.get("AutoUpdateCheck");
            IsAutoFIleUpdate = (Boolean) jsonObject.get("AutoFileUpdate");
            JSONObject Beta = (JSONObject) jsonObject.get("Beta");
            Language = (String) jsonObject.get("Language");

            IsUseGui = (Boolean) Beta.get("UseGui");

        } catch (IOException e) {
            System.out.println(ANSI_RED + "[ ERROR ] setting.json file does not exist." + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        } catch (ParseException e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing json.\n" + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }

        System.out.println("[ OK ] Json was loaded successfully.");
        if (IsCheckUpdate) {
            System.out.println("[ ING ] Checking Update...");
            IsAvailUpdate = UpdateManager.IsAvailableUpdate();
            if (IsAvailUpdate) {
                String nver = TxtReader.TxTOneLineRead(appdata + "/ASFM/newver.txt");
                System.out.println("[ ! ] There are updates available. v" + ver + " -> v" + nver);
                if (IsAutoFIleUpdate) {
                    System.out.println("[ ING ] Updating ASFM...");
                }
            } else {
                System.out.println("[ OK ] This is the latest version.");
            }
        }

        System.out.println("[ ING ] Starting ASFM...");

        if (IsUseGui) {
            System.out.println("[ ING ] Starting GUI...");
            GUI.StartGUI();
        } else {
            System.out.println("[ ING ] Starting Console Menu...");
            ConsoleMenu.MenuStart(ver, appdata);
        }
    }
}
