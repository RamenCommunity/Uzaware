package net.minearchive;

import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Uzaware implements ClientModInitializer {
	public static String modID 				= "uzaware";
	public static String modName 			= "Uzaware";
	public static String version 			= "1.0";

    public static final Logger LOGGER 		= LoggerFactory.getLogger(modName);
	public static final EventBus EVENT_BUS 	= new EventBus();

	private final String[] serif = new String[] {
			"呼ばれて飛び出て！参りました！ みんなのスーパースター、宇沢レイサ、登場です！",
			"おかえりなさい、先生！ 宇沢レイサ、待機していました！",
			"先生の「登場」ですね！！ それでは、今日は何をしましょうか！",
			"お呼びですか、先生！なんでもお申しつけください！"
	};

	@Override
	public void onInitializeClient() {
		LOGGER.info("Uzaware Initialized!");
		LOGGER.info(serif[new Random().nextInt(4)]);
	}
}