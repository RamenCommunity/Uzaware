package net.minearchive.manager;

import net.minearchive.module.HudModule;
import net.minearchive.module.Module;
import net.minearchive.module.modules.client.*;
import net.minearchive.module.modules.combat.*;
import net.minearchive.module.modules.hud.*;
import net.minearchive.module.modules.misc.*;
import net.minearchive.module.modules.movement.*;
import net.minearchive.module.modules.player.InstantMineModule;
import net.minearchive.module.modules.render.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ModuleManager {
    public static ModuleManager INSTANCE;
    public final List<Module> modules = new ArrayList<>();
    public HudModule dragging = null;

    public ModuleManager() {
        INSTANCE = this;
        //Combat
        register(AutoPhaseModule.class);

        //Misc
        register(ChatSuffixModule.class);
        register(SuperIQMathModule.class);
        register(FakePlayerModule.class);
        register(DeathEffectModule.class);

        //Movement
        register(FlightModule.class);
        register(MovementTweaksModule.class);

        //Render
        register(FullBrightModule.class);
        register(NoRenderModule.class);

        //Player
        register(InstantMineModule.class);

        //Client
        register(ClickGuiModule.class);
        register(ElementSample.class);
        register(ClientDebuggerModule.class);

        //Hud
        register(WaterMarkModule.class);
        register(NotificationModule.class);
        register(SpotifyHudModule.class);
        register(TargetHudModule.class);

        modules.sort(Comparator.comparing(m -> m.name));
    }

    public Optional<Module> getInstance(Class<? extends Module> clazz) {
        return modules.stream().filter(m -> m.getClass() == clazz).findFirst();
    }

    public void register(Class<? extends Module> clazz) {
        try {
            Module module = clazz.getDeclaredConstructor().newInstance();
            modules.add(module);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
