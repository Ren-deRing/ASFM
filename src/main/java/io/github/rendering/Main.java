package io.github.rendering;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    public static Boolean IsAutoFIleUpdate = null;
    public static Boolean IsInitial = null;
    public static Boolean IsAvailUpdate = null;
    public static String Language = null;
    public static String appdata = System.getenv("APPDATA");
    public static String ver;

    static Boolean IsUseGui = null;
    static Boolean IsCheckUpdate = null;

    static boolean DebugMode;

    static {
        try {
            ver = TextManager.TxTOneLineRead(appdata + "/ASFM/ver.txt");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void ReloadSetting() throws InterruptedException {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(appdata + "/ASFM/setting.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            IsCheckUpdate = (Boolean) jsonObject.get("AutoUpdateCheck");
            IsAutoFIleUpdate = (Boolean) jsonObject.get("AutoFileUpdate");
            JSONObject Beta = (JSONObject) jsonObject.get("Beta");
            Language = (String) jsonObject.get("Language");
            IsInitial = (Boolean) jsonObject.get("IsInitial");
            DebugMode = (Boolean) jsonObject.get("DebugMode");

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
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = (os.indexOf("win") >= 0);

        if (!isWindows) {
            System.out.println(ANSI_RED + "[ !!! ] ERROR!: ASFM only supports Windows, but your OS is not Windows.");
            Thread.sleep(5000);
            System.exit(1);
        }

        JSONParser parser = new JSONParser();
        String ver = TextManager.TxTOneLineRead(appdata + "/ASFM/ver.txt");

        System.out.println("ASFM v" + ver);
        System.out.println("[ OK ] Java ASFM runs successfully.");
        System.out.println("[ ING ] Load setting.json File...");

        ReloadSetting();

        System.out.println("[ OK ] Json was loaded successfully.");
        if (IsCheckUpdate) {
            System.out.println("[ ING ] Checking Update...");
            IsAvailUpdate = UpdateManager.IsAvailableUpdate();
            if (IsAvailUpdate) {
                String nver = TextManager.TxTOneLineRead(appdata + "/ASFM/newver.txt");
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
        } else if (DebugMode) {
            System.out.println("[ ! ] Starting Debug Mode!");
            System.out.println("[ OK ] Debug Mode Started");
            System.out.println("[ ING ] Start Debug: DownloadManager");
            DownloadManager.Download();
        } else {
            System.out.println("[ ING ] Starting Console Menu...");
            ConsoleMenu.MenuStart(ver, appdata);
        }
    }
}
