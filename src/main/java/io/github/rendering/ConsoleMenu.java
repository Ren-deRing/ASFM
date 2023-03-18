package io.github.rendering;

import io.github.rendering.server.CreateServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleMenu {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private static Integer Menu;
    private static Boolean NotIntMenu = false;

    public static void MenuStart(String version, String appdata) throws IOException, InterruptedException {

        ResetVar(); // 변수 초기화

        JSONParser parser = new JSONParser();
        for (int i=0; i<10; i++) {
            System.out.println("                                                                                                                                ");
        }
        System.out.println("                                                    ASFM v" + version);
        System.out.println("                                                Starting ConsoleMenu");
        System.out.println("                                                    ");
        System.out.println("                                                    Checking Jars\n\n");
        String DATA_DIRECTORY = appdata + "/ASFM/Jars";
        File dir = new File(DATA_DIRECTORY);

        List<String> jarfiles = new ArrayList<String>();
        String[] filenames = dir.list();
        for (String filename : filenames) {
            boolean regex = Pattern.matches("^[a-z0-9]+\\.jar$", filename);
            if (regex) {
                jarfiles.add(filename);
            }
        }
        for (String jar : jarfiles) {
            System.out.println("     -  " + jar);
        }
        System.out.println("                                                  Loading Lang.json");
        LanguageManager.LoadTexts(Main.Language);
        System.out.println("                                              Detected Language: " + Main.Language + "\n");

        if (true) {
            System.out.println("                                           " + LanguageManager.UpdateAvail);
        }
        if (Main.IsInitial) {
            System.out.println("\n" + LanguageManager.Welcome);
            List<String> Langs = LanguageManager.LoadLangs();
            System.out.println(LanguageManager.Initial1 + "\n");
            try (Reader reader = new FileReader(appdata + "/ASFM/lang.json")) {
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                JSONObject langlist = (JSONObject) jsonObject.get("langs");
                for (String obj : Langs) {
                    String objLang = (String) langlist.get(obj);
                    System.out.println(objLang + ": " + obj);
                }
            }
            catch (Exception e) {
                System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing json.\n - " + e + ANSI_RESET + "\nExiting...");
                Thread.sleep(3000);
                System.exit(1);
            }
            while (true) {
                System.out.print("Language > ");
                Scanner sc = new Scanner(System.in);
                String Lang = sc.nextLine();
                if (Langs.contains(Lang)) {
                    TextManager.writeToJson(appdata + "/ASFM/setting.json", "Language", Lang);
                    break;
                }
                System.out.println(LanguageManager.LangNotExist);
            }
            System.out.println("\n" + LanguageManager.LangReloading);
            Main.ReloadSetting(); // Main 클래스에서 설정 리로드
            LanguageManager.LoadTexts(Main.Language);
            System.out.println("\n" + LanguageManager.Initial2);
            System.out.println(" - Deleting Cache...");
            System.out.println(" - Saving Files...");
            TextManager.writeToJson(appdata + "/ASFM/setting.json", "IsInitial", false);
            System.out.println(LanguageManager.InitialEnd);
        }
        System.out.println("\n\n- ASFM " + version);
        System.out.println("  Cli Menu");
        while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 콘솔 클리어
            System.out.println("\n");
            if (NotIntMenu) {
                System.out.println("\n");
                System.out.println("         - 1~6까지의 숫자를 입력하세요.");
            } else {
                System.out.println("\n");
            }
            System.out.println("\n");
            System.out.println("         " + LanguageManager.MakeServer + "     " + LanguageManager.SevSetting);
            System.out.println("         " + LanguageManager.PlSetting + "      " + LanguageManager.ASFMSetting);
            System.out.println("         " + LanguageManager.WorlSetting + "       " + LanguageManager.Exit);
            System.out.println("\n\n");
            System.out.print("Menu > ");
            Scanner sc = new Scanner(System.in);
            String MenuStr = sc.nextLine();

            try {
                Menu = Integer.parseInt(MenuStr);
                if (Menu == 1) {
                    CreateServer.ServerCreateMenu();
                    break;
                } else if (Menu == 2) {
                    break;
                } else if (Menu == 3) {
                    break;
                } else if (Menu == 4) {
                    break;
                } else if (Menu == 5) {
                    break;
                } else if (Menu == 6) {
                    break;
                } else {
                    NotIntMenu = true;
                }
            } catch (NumberFormatException ex) {
                NotIntMenu = true;
            }
        }

    }

    private static void ResetVar() {
        Menu = 0;
        NotIntMenu = false;
    }

}
