package io.github.rendering.server;

import io.github.rendering.DownloadManager;
import org.json.simple.JSONObject;

import java.util.List;

public class ServerManager {

    // Sobj JSONObject Example
    //JSONObject Sobj = {
    //  "ver": 193,
    //  "Saveat": "Server Loc"
    //};
    public boolean MakeServer(JSONObject Sobj) {
        Integer ver = (Integer) Sobj.get("ver");
        String Saveat = (String) Sobj.get("Saveat");



        return false;
    }
}
