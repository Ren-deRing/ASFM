package io.github.rendering;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class LanguageManager {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static String appdata = System.getenv("APPDATA");



    public static String UpdateAvail, Welcome, Initial1, Initial2 ,Initial3, InitialEnd, LangNotExist, LangReloading,
            MakeServer, SevSetting, PlSetting, ASFMSetting, WorldSetting, Exit, CorrectMenuInt,
            CorrectVer, CreateServer, SelectVersion, SelectMinerVer, InputMem, MemWarning;
    public static Boolean IsTextReset;

    public static void LoadTexts(String lang) throws InterruptedException {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(appdata + "/ASFM/lang.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject uselang = (JSONObject) jsonObject.get(Main.Language);

            UpdateAvail = (String) uselang.get("UpdateAvail");
            Welcome = (String) uselang.get("Welcome");
            Initial1 = (String) uselang.get("Initial1");
            Initial2 = (String) uselang.get("Initial2");
            Initial3 = (String) uselang.get("Initial3");
            InitialEnd = (String) uselang.get("InitialEnd");
            LangNotExist = (String) uselang.get("LangNotExist");
            LangReloading = (String) uselang.get("LangReloading");

            JSONObject Menu = (JSONObject) uselang.get("Menu");

            MakeServer = (String) Menu.get("MakeServer");
            SevSetting = (String) Menu.get("PlSetting");
            PlSetting = (String) Menu.get("ASFMSetting");
            ASFMSetting = (String) Menu.get("ASFMSetting");
            WorldSetting = (String) Menu.get("WorlSetting");
            Exit = (String) Menu.get("Exit");
            CorrectMenuInt = (String) Menu.get("CorrectMenuInt");

            JSONObject CreateServerObj = (JSONObject) uselang.get("CreateServer");

            CorrectVer = (String) CreateServerObj.get("CorrectVer");
            CreateServer = (String) CreateServerObj.get("CreateServer");
            SelectVersion = (String) CreateServerObj.get("SelectVersion");
            SelectMinerVer = (String) CreateServerObj.get("SelectMinerVer");
            InputMem = (String) CreateServerObj.get("InputMem");
            MemWarning = (String) CreateServerObj.get("MemWarning");

            IsTextReset = true;

        } catch (IOException e) {
            System.out.println(ANSI_RED + "[ ERROR ] lang.json file does not exist." + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing json.\n - " + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }
    }
    public static List<String> LoadLangs() throws InterruptedException {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(appdata + "/ASFM/lang.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject langlist = (JSONObject) jsonObject.get("langs");

            List<String> Langs = (List<String>) langlist.get("list");

            return Langs;

        } catch (IOException e) {
            System.out.println(ANSI_RED + "[ ERROR ] lang.json file does not exist." + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "[ ERROR ] An unknown error occurred while parsing json.\n - " + e + ANSI_RESET + "\nExiting...");
            Thread.sleep(3000);
            System.exit(1);
        }
        return  null;
    }
}
