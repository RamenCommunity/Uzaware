package net.minearchive;

import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minearchive.event.events.TickEndEvent;
import net.minearchive.event.events.TickStartEvent;
import net.minearchive.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Uzaware implements ClientModInitializer {
	public static final String modID 			= "uzaware";
	public static final String modName 			= "Uzaware";
	public static final String version 			= "1.0";

    public static final Logger LOGGER 			= LoggerFactory.getLogger(modName);
	public static ModuleManager moduleManager 	= new ModuleManager();
	public static NanoVGManager nanoVGManager 	= new NanoVGManager();
	public static TextureManager textureManager = new TextureManager();
	public static ConfigManager configManager 	= new ConfigManager();
	public static EntityManager entityManager 	= new EntityManager();
	public static final EventBus EVENT_BUS 		= new EventBus();
	public static List<Object> registeredObject = new CopyOnWriteArrayList<>();
	private final String[] serif 				= new String[] {
			"呼ばれて飛び出て！参りました！ みんなのスーパースター、宇沢レイサ、登場です！",
			"おかえりなさい、先生！ 宇沢レイサ、待機していました！",
			"先生の「登場」ですね！！ それでは、今日は何をしましょうか！",
			"お呼びですか、先生！なんでもお申しつけください！"
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

		Runtime.getRuntime().addShutdownHook(new Thread(() -> configManager.saveAll(), "Shutdown_hook"));
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
