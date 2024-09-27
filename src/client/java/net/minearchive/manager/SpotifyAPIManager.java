package net.minearchive.manager;

import com.neovisionaries.i18n.CountryCode;
import com.sun.net.httpserver.HttpServer;
import net.minearchive.mixin.mixins.AccessorMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;
import org.apache.hc.core5.http.ParseException;
import org.apache.logging.log4j.LogManager;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.minearchive.util.ParallelRunner.runParallel;

public class SpotifyAPIManager {
    public static SpotifyAPIManager INSTANCE = new SpotifyAPIManager();

    private final String CODE_CHALLENGE = "w6iZIj99vHGtEx_NVl9u3sthTN646vvkiP8OMCGfPmo";
    private final String CODE_VERIFIER = "NlJx4kD4opk4HY7zBM6WfUHxX7HoF8A2TUhOIPGA74w";

    private SpotifyApi spotifyApi;
    private AuthorizationCodeUriRequest authCodeRequest;
    private int refreshInterval = 2;
    private boolean authed = false;
    private final SpotifyStateManager spotifyStateManager = new SpotifyStateManager();

    private final String input = "<!DOCTYPE html><html lang=\"ja\"><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width," +
            " initial-scale=1.0\"><title>Uzaware Spotify Setting</title><style>body{display:flex;justify-content:center;align-items:center;heigh" +
            "t:100vh;margin:0}.form-container{display:flex;flex-direction:column;align-items:center}input[type=\"text\"]{width:300px;padding:10p" +
            "x;margin:10px 0;font-size:16px}button{padding:10px 20px;font-size:16px}</style><script></script><div class=\"form-container\"><form" +
            " id=\"myForm\"><label><input type=\"text\" id=\"textbox1\" placeholder=\"Your API Key\"></label> <button type=\"submit\">Open</butt" +
            "on></form></div><script>document.getElementById(\"myForm\").addEventListener(\"submit\",function(n){n.preventDefault();const t=docu" +
            "ment.getElementById(\"textbox1\").value,i=`http://localhost:4001/receive?textbox1=${encodeURIComponent(t)}`;fetch(i).then(n=>{if(!n.ok)throw" +
            " new Error(\"ネットワークエラー\");return n.text()})})</script>";
    public final String close = "<!DOCTYPE html><html lang=\"en\"><meta charset=\"UTF-8\"><meta name=\"viewport\"content=\"width=device-width, i" +
            "nitial-scale=1.0\"><script src=\"https://cdn.tailwindcss.com\"></script><title>Modal with Close Button</title><script>tailwind.confi" +
            "g={theme:{extend:{colors:{spotify:\"#1ed760\",spotifyHv:\"#1db954\",back:\"#1e2022\",panel:\"#181a1b\"}}}}</script><body class=\"fle" +
            "x items-center justify-center min-h-screen bg-gray-100\"><div class=\"bg-white p-8 rounded-lg shadow-md w-96 bg-panel\"><div class=\"" +
            "mb-4 text-xl\"><p>You just logged in!!!</div><button class=\"bg-spotify hover:bg-spotifyHv text-white font-bold py-2 px-4 rounded\" on" +
            "click=\"closeModal()\">Close</button></div><script>function closeModal(){console.log(\"a\");window.close()}</script>";

    private final ExecutorService scheduledExecutorService = Executors.newSingleThreadExecutor();
    private HttpServer callbackServer;
    private final SpotifyCallback callback = code -> {
        AuthorizationCodePKCERequest codePKCERequest = spotifyApi.authorizationCodePKCE(code, CODE_VERIFIER).build();
        try {
            final AuthorizationCodeCredentials authCredentials = codePKCERequest.execute();
            spotifyApi.setAccessToken(authCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authCredentials.getRefreshToken());
            refreshInterval = authCredentials.getExpiresIn();
            runParallel(() -> {
                try {
                    TimeUnit.SECONDS.sleep(refreshInterval - 2);
                    final AuthorizationCodeCredentials refreshRequest = spotifyApi.authorizationCodePKCERefresh().build().execute();
                    spotifyApi.setAccessToken(refreshRequest.getAccessToken());
                    spotifyApi.setRefreshToken(refreshRequest.getRefreshToken());
                    refreshInterval = refreshRequest.getExpiresIn();
                } catch (Exception e) {
                    LogManager.getLogger("Uzaware Spotify Client").error("Error caused while creating access token", e);
                }
            });

            runParallel(() -> {
                try {
                    while (!((AccessorMinecraftClient) MinecraftClient.getInstance()).getThread().isInterrupted()) {
                        TimeUnit.SECONDS.sleep(1);
                        if (hasActiveDevice()) {
                            final CurrentlyPlayingContext context = spotifyApi.getInformationAboutUsersCurrentPlayback()
                                    .market(CountryCode.JP).build().execute();
                            if (context == null) return;
                            final Track track = spotifyApi.getTrack(context.getItem().getId())
                                    .market(CountryCode.JP).build().execute();
                            if (track == null) return;
                            spotifyStateManager.setProgressMS(context.getProgress_ms());
                            spotifyStateManager.setPlayingContext(context);
                            spotifyStateManager.setCurrentTrack(track);
                        }
                    }
                } catch (Exception e) {
                    LogManager.getLogger("Uzaware Spotify Client").error("Error caused while fetching data", e);
                }
            });

        } catch (Exception e) {
            LogManager.getLogger("Uzaware Spotify Client").error("Error caused while creating access token", e);
        }
    };

    public void start() {
        if (authed) return;
        scheduledExecutorService.submit(() -> {
            System.out.println("start 4040 server");
            try {
                if (callbackServer != null) callbackServer.stop(0);
                callbackServer = HttpServer.create(new InetSocketAddress(4040), 0);
                callbackServer.createContext("/", context -> {
                    callback.codeCallback(context.getRequestURI().getQuery().split("=")[1]);
                    final String messageSuccess = context.getRequestURI().getQuery().contains("code")
                            ? close
                            : "Fuck Your Self";
                    context.sendResponseHeaders(200, messageSuccess.length());
                    OutputStream out = context.getResponseBody();
                    out.write(messageSuccess.getBytes());
                    out.close();
                    callbackServer.stop(0);
                    authed = true;
                });
                callbackServer.start();
            } catch (Exception e) {
                LogManager.getLogger("Uzaware Spotify Client").error("Error caused while initializing spotify api client", e);
            }
        });
    }

    public void build(String clientID) {
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setRedirectUri(SpotifyHttpManager.makeUri("http://localhost:4040"))
                .build();

        this.authCodeRequest = spotifyApi
                .authorizationCodePKCEUri(CODE_CHALLENGE)
                .code_challenge_method("S256")
                .scope("user-read-playback-state user-read-playback-position user-modify-playback-state user-read-currently-playing")
                .build();
    }

    private boolean hasActiveDevice() throws IOException, ParseException, SpotifyWebApiException {
        return spotifyApi.getUsersAvailableDevices().build().execute().length > 0;
    }

    private void openBrowser(URI url) {
        Util.getOperatingSystem().open(url);
    }

    interface SpotifyCallback {
        void codeCallback(final String code);
    }

    public static class SpotifyStateManager {
        private long progressMS;
        private Track currentTrack;
        private CurrentlyPlayingContext playingContext;

        public void setProgressMS(long progressMS) {
            this.progressMS = progressMS;
        }

        public void setCurrentTrack(Track currentTrack) {
            this.currentTrack = currentTrack;
        }

        public void setPlayingContext(CurrentlyPlayingContext playingContext) {
            this.playingContext = playingContext;
        }

        public long getProgressMS() {
            return progressMS;
        }

        public Track getCurrentTrack() {
            return currentTrack;
        }

        public CurrentlyPlayingContext getPlayingContext() {
            return playingContext;
        }
    }

    public static long getProgressMS() {
        return INSTANCE.spotifyStateManager.getProgressMS();
    }

    public static Track getCurrentTrack() {
        return INSTANCE.spotifyStateManager.getCurrentTrack();
    }

    public static CurrentlyPlayingContext getPlayingContext() {
        return INSTANCE.spotifyStateManager.getPlayingContext();
    }
}