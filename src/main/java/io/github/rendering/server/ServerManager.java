package io.github.rendering.server;

import io.github.rendering.ConsoleMenu;
import io.github.rendering.DownloadManager;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ServerManager {

    public static String appdata = System.getenv("APPDATA");

    // Sobj JSONObject Example
    //JSONObject Sobj = {
    //  "VerStr": "193",
    //  "url": "https://~~"
    //  "Saveat": "Server Loc",
    //  "Mem": 10;
    //};
    public static boolean MakeServer(JSONObject Sobj) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 콘솔 클리어

        System.out.println("\n\n\n\n서버 생성 중...");

        String VerStr = (String) Sobj.get("VerStr");
        String url = (String) Sobj.get("url");
        String Saveat = (String) Sobj.get("Saveat");
        Integer Mem = (Integer) Sobj.get("Mem");

        // Jar파일 존재 확인 후 다운로드
        if (!ConsoleMenu.jarfiles.contains("P" + VerStr + ".jar")) {
            try {
                DownloadManager.Download(url, appdata + "/ASFM/Jars/P" + VerStr + ".jar", "Paper" + VerStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Jar 파일 옮기기
        Path relativePath = Paths.get("");  // 현재 작업중인 상대경로 선언
        String path = relativePath.toAbsolutePath().toString(); // 현재 작업중인 절대경로 선언

        File file = new File(appdata + "/ASFM/Jars/P" + VerStr + ".jar");
        File newFile = new File(path + "/P" + VerStr + ".jar");

        FileInputStream input;
        FileOutputStream output;
        input = new FileInputStream(file);
        output = new FileOutputStream(newFile);

        byte[] buf = new byte[1024];
        int readData;
        while ((readData = input.read(buf)) > 0) {
            output.write(buf, 0, readData);
        }
        input.close();
        output.close();

        // 실행 파일 생성
        File BatFile = new File(path + "/Start-Server.bat");
        File EULA = new File(path + "/eula.txt");
        FileWriter fw = new FileWriter(BatFile, true);
        FileWriter fwe = new FileWriter(EULA, true);
        fwe.write("eula=true");
        fw.write("@echo off\ntitle Minecraft Server\njava -Xms" + Mem + "G -Xmx" + Mem + "G -jar P" + VerStr + ".jar\necho Server Stop\npause");
        fwe.flush();
        fw.flush();
        fwe.close();
        fw.close();

        return false;
    }
}
