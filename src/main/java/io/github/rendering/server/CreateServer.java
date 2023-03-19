package io.github.rendering.server;

import io.github.rendering.LanguageManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CreateServer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static String appdata = System.getenv("APPDATA");

    private static Integer Ver;
    private static Boolean NotIntVer = false, NotIntMinerVer = false;
    private static String VerCodeStr, VerStr, MinerVerStr;
    private static List<String> MinerVerList;

    public static void ServerCreateMenu() throws InterruptedException, IOException {
        JSONParser parser = new JSONParser();

        while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 콘솔 클리어
            if (NotIntVer) {
                System.out.println("         " + LanguageManager.CorrectVer);
            } else {
                System.out.println("\n");
            }
            System.out.println("\n" + LanguageManager.CreateServer);
            System.out.println(LanguageManager.SelectVersion + "\n");

            System.out.println("   - 1.19   - 1.18   - 1.17   - 1.16");
            System.out.println("   - 1.15   - 1.14   - 1.13   - 1.12");
            System.out.println("   - 1.10   - 1.9    - 1.8    - 1.7");

            System.out.print("\nVersion > ");
            Scanner sc = new Scanner(System.in);
            VerStr = sc.nextLine();

            System.out.println(VerStr);
            if (VerStr.equals("1.19")) {
                VerCodeStr = "19";
                break;
            } else if (VerStr.equals("1.18")) {
                VerCodeStr = "18";
                break;
            } else if (VerStr.equals("1.17")) {
                VerCodeStr = "17";
                break;
            } else if (VerStr.equals("1.16")) {
                VerCodeStr = "16";
                break;
            } else if (VerStr.equals("1.15")) {
                VerCodeStr = "15";
                break;
            } else if (VerStr.equals("1.14")) {
                VerCodeStr = "14";
                break;
            } else if (VerStr.equals("1.13")) {
                VerCodeStr = "13";
                break;
            } else if (VerStr.equals("1.12")) {
                VerCodeStr = "12";
                break;
            } else if (VerStr.equals("1.11")) {
                VerCodeStr = "11";
                break;
            } else if (VerStr.equals("1.10")) {
                VerCodeStr = "10";
                break;
            } else if (VerStr.equals("1.9")) {
                VerCodeStr = "09";
                break;
            } else if (VerStr.equals("1.8")) {
                VerCodeStr = "08";
                break;
            } else if (VerStr.equals("1.7")) {
                VerCodeStr = "07";
                break;
            }
            else {
                NotIntVer = true;
            }
        }

        try (Reader reader = new FileReader(appdata + "/ASFM/verList.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject versions = (JSONObject) jsonObject.get("versions");
            MinerVerList = (List<String>) versions.get(VerCodeStr);

        } catch (IOException e) {
            System.out.println(ANSI_RED + "[ ERROR ] verList.json file does not exist." + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        } catch (ParseException e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing json.\n" + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }
        while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 콘솔 클리어
            System.out.println("\n" + LanguageManager.SelectMinerVer);
            if (NotIntMinerVer) {
                System.out.println("     " + LanguageManager.CorrectVer + "\n");
            } else {
                System.out.println("");
            }
            for (String obj: MinerVerList) {
                System.out.print("   "+ VerStr +"." + obj + ": " + obj);
            }
            System.out.print("\n\nMinerVer > ");
            Scanner sc = new Scanner(System.in);
            MinerVerStr = sc.nextLine();

            if (MinerVerList.contains(MinerVerStr)) {
                break;
            } else {
                NotIntMinerVer = true;
            }
        }

        VerCodeStr = VerCodeStr + MinerVerStr;
        VerStr = VerStr + "." + MinerVerStr;

        while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 콘솔 클리어
            System.out.println("\n" + LanguageManager.InputMem + "\n" + LanguageManager.MemWarning);
        }
    }
}
