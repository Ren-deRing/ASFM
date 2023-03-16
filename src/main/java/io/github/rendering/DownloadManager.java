package io.github.rendering;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.junit.rules.TestRule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;

import static io.github.rendering.Main.appdata;

public class DownloadManager {
    public static void Download() throws IOException {
        String fileUrl = "https://github.com/xemulat/MyFilesForDDL/releases/download/youtube/youtube.apk";
        String saveAt = appdata + "/ASFM/Jars/Youtube.apk";
        String filePresentName = "P193";

        URL url = new URL(fileUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
        long completeFileSize = httpConnection.getContentLength();

        try(InputStream inputStream = url.openStream();
            CountingInputStream cis = new CountingInputStream(inputStream);
            FileOutputStream fileOS = new FileOutputStream(saveAt);
            ProgressBar pb = new ProgressBar(filePresentName, Math.floorDiv(completeFileSize, 1000)
            )) {

            pb.setExtraMessage("Downloading...");

            new Thread(() -> {
                try {
                    IOUtils.copyLarge(cis, fileOS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (cis.getByteCount() < completeFileSize) {
                pb.stepTo(Math.floorDiv(cis.getByteCount(), 1000));
            }

            pb.stepTo(Math.floorDiv(cis.getByteCount(), 1000));
        }
    }
}