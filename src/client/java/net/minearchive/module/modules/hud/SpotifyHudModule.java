package net.minearchive.module.modules.hud;

import net.minearchive.Uzaware;
import net.minearchive.event.events.Render2DStartEvent;
import net.minearchive.manager.SpotifyAPIManager;
import net.minearchive.module.Category;
import net.minearchive.module.HudInfo;
import net.minearchive.module.HudModule;
import net.minearchive.module.ModuleInfo;
import net.minearchive.setting.settings.StringSetting;
import net.minearchive.util.ImageUtils;
import net.minearchive.util.render.NanoVGUtils;

import java.io.InputStream;

@ModuleInfo(name = "SpotifyHud", category = Category.HUD)
@HudInfo
public class SpotifyHudModule extends HudModule {

    public final StringSetting clientID = add(new StringSetting("clientID", ""));

    private String cachedUrl = "";

    @Override
    public void onEnable() {
        if (nullCheck()) {
            disable();
            return;
        }
        if (clientID.getValue().length() != 32) return;
        SpotifyAPIManager.INSTANCE.start();
    }

    @Override
    public void onRender(Render2DStartEvent event) {
        if (nullCheck()) return;
        if (SpotifyAPIManager.getCurrentTrack() == null) return;

        NanoVGUtils.ntr.draw(
                SpotifyAPIManager.getCurrentTrack().getName(),
                0,
                0,
                30,
                0xffffffff
        );

        if (!cachedUrl.equals(SpotifyAPIManager.getCurrentTrack().getId())) {
            try {
                cachedUrl = SpotifyAPIManager.getCurrentTrack().getId();
                InputStream stream = ImageUtils.fromWeb(SpotifyAPIManager.getCurrentTrack().getAlbum().getImages()[0].getUrl());
                Uzaware.textureManager.createTexture(stream, SpotifyAPIManager.getCurrentTrack().getUri().toLowerCase());
                stream.close();
            } catch (Exception ignore) { }
        }


    }
}
