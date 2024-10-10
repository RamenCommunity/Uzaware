package net.minearchive;

import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minearchive.event.events.TickEndEvent;
import net.minearchive.event.events.TickStartEvent;
import net.minearchive.manager.*;
import net.minearchive.util.FPSCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Uzaware implements ClientModInitializer {
	public static final String modName 					= "Uzaware";
	public static final String modID 					= modName.toLowerCase(Locale.ROOT);
	public static final String version 					= "1.0";

    public static final Logger LOGGER 					= LoggerFactory.getLogger(modName);
	public static final ModuleManager moduleManager 	= new ModuleManager();
	public static final NanoVGManager nanoVGManager 	= new NanoVGManager();
	public static final TextureManager textureManager 	= new TextureManager();
	public static final ConfigManager configManager 	= new ConfigManager();
	public static final EntityManager entityManager 	= new EntityManager();
	public static final EventBus EVENT_BUS 				= new EventBus();
	public static final List<Object> registeredObject 	= new CopyOnWriteArrayList<>();
	public static final FPSCalculator FPS 				= new FPSCalculator();
	private final String[] serif 						= new String[] {
			"呼ばれて飛び出て！参りました！ みんなのスーパースター、宇沢レイサ、登場です！",
			"おかえりなさい、先生！ 宇沢レイサ、待機していました！",
			"先生の「登場」ですね！！ それでは、今日は何をしましょうか！",
			"お呼びですか、先生！なんでもお申しつけください！",
			"あっ、もしかして、声……大きかったですか？ 響いちゃったかも……すみません……。",
			"ん……不思議です ここにくると、ちょっとだけ力が抜けるというか……。",
			"お、お使いでも行ってきましょうか？ その……じっと立ってると、なんだか落ち着かなくて……！"
	};

	@Override
	public void onInitializeClient() {
		LOGGER.info(serif[new Random().nextInt(4)]);

		ClientTickEvents.START_CLIENT_TICK.register(tick -> EVENT_BUS.post(new TickStartEvent()));
		ClientTickEvents.END_CLIENT_TICK.register(tick -> EVENT_BUS.post(new TickEndEvent()));
		registerEventBus(new AlwaysListener());
		configManager.onInit();

		fuckRam();
		LOGGER.info("Uzaware Initialized!");

		Runtime.getRuntime().addShutdownHook(new Thread(configManager::saveAll, "Shutdown_hook"));
	}

	public static void registerEventBus(Object o) {
		if (!registeredObject.contains(o)) {
			registeredObject.add(o);
			EVENT_BUS.register(o);
		}
	}

	public static void unRegisterEventBus(Object o) {
		if (registeredObject.contains(o)) {
			registeredObject.remove(o);
			EVENT_BUS.unregister(o);
		}
	}

	public static void fuckRam() {
		System.gc();
	}
}
