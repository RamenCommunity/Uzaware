package net.minearchive.util;

import net.fabricmc.loader.impl.util.UrlUtil;
import net.minearchive.Uzaware;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {

    /**
     *
     * please do <code>InputStream.close()</code> after use inputStream
     *
     * @param link Http or Https Image url
     * @return InputStream of Image
     */
    public static InputStream fromWeb(String link) throws IOException {
        URL url = SpotifyHttpManager.makeUri(link).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }
}
