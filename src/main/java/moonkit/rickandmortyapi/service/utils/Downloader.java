package moonkit.rickandmortyapi.service.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Downloader {
    private static final String GET_TYPE = "GET";

    @Value("${custom.full-images-folder}")
    private String resourceFolder;

    public void downloadFile(String strUrl, String dir, String fileName, int buffSize) {
        try {
            URL connection = new URL(strUrl);
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) connection.openConnection();
            urlConnection.setRequestMethod(GET_TYPE);
            urlConnection.connect();
            InputStream in = null;
            in = urlConnection.getInputStream();
            File file = new File(resourceFolder + File.separator + dir
                    + File.separator + fileName + "."
                    + strUrl.split("\\.")[strUrl.split("\\.").length - 1]);
            OutputStream writer = new FileOutputStream(file);
            byte[] buffer = new byte[buffSize];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Can't download image from url " + strUrl, e);
        }
    }
}
