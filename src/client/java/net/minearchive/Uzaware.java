package net.minearchive;

import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minearchive.event.events.TickEvent.TickEndEvent;
import net.minearchive.event.events.TickEvent.TickStartEvent;
import net.minearchive.manager.ModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Uzaware implements ClientModInitializer {

	public static String modID 					= "uzaware";
	public static String modName 				= "Uzaware";
	public static String version 				= "1.0";

    public static final Logger LOGGER 			= LoggerFactory.getLogger(modName);
	public static ModuleManager moduleManager 	= new ModuleManager();
	public static final EventBus EVENT_BUS 		= new EventBus();
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

		LOGGER.info("Uzaware Initialized!");
	}
}