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
	public static final String modName 						= "Uzaware";
	public static final String modID 						= modName.toLowerCase(Locale.ROOT);
	public static final String version 						= "1.0";
	public static final String prefix 						= "$";

    public static final Logger LOGGER 						= LoggerFactory.getLogger(modName);
	public static final CommandManager commandManager 		= new CommandManager();
	public static final ModuleManager moduleManager 		= new ModuleManager();
	public static final ConfigManager configManager 		= new ConfigManager();
	public static final EntityManager entityManager 		= new EntityManager();
	public static final NanoVGManager nanoVGManager 		= new NanoVGManager();
	public static final SpotifyAPIManager spotifyAPIManager = new SpotifyAPIManager();
	public static final TextureManager textureManager 		= new TextureManager();
	public static final EventBus EVENT_BUS 					= new EventBus();
	public static final List<Object> registeredObject 		= new CopyOnWriteArrayList<>();
	public static final FPSCalculator FPS 					= new FPSCalculator();
	private final String[] serif 							= new String[] {
			"呼ばれて飛び出て！参りました！ みんなのスーパースター、宇沢レイサ、登場です！",
			"おかえりなさい、先生！ 宇沢レイサ、待機していました！",
			"先生の「登場」ですね！！ それでは、今日は何をしましょうか！",
			"お呼びですか、先生！なんでもお申しつけください！",
			"あっ、もしかして、声……大きかったですか？ 響いちゃったかも……すみません……。",
			"ん……不思議です ここにくると、ちょっとだけ力が抜けるというか……。",
			"お、お使いでも行ってきましょうか？ その……じっと立ってると、なんだか落ち着かなくて……！",
			"この先、何があってもご安心ください！ 先生のそばには私、宇沢レイサがついておりますから！",
			"ついに……！私の出番ですね！",
			"はいっ！いつでも行けますよ！",
			"宇沢レイサに、お任せ～～っくださいっ！",
			"私の力が必要でしたらどこにでも！",
			"皆さんと一緒に、私、宇沢レイサが参りますっ！",
			"おお！私が先頭なんですね！",
			"恐れることなく、前へ！",
			"はぁぁ！",
			"まだまだ行きますよーっ！",
			"次っ！行きますっ！",
			"無駄ですよ！",
			"わっ……、ぁぁぁありがとうございます……。",
			"ぁ……、ありがとうございます……。",
			"最大出力で、行きますよ～！",
			"うっ……。",
			"ぐっ……。",
			"ひっ……。",
			"ま、まだ……、い、け……。",
			"必殺！勝利のポーズ！",
			"とりゃー！",
			"ここに参上！",
			"勝負です！",
			"逃しませんよー！",
			"挑戦状を！受け取ってー！くださいっ！",
			"覚悟してください！",
			"んむ！当然の結果です！",
			"次行きますよ！次！",
			"ふーっ、楽勝でしたねっ！",
			"ふーっ、正義は必ず勝つんです！",
			"私がもっと……ちゃんとしていれば……。",
			"うぅ……、ごめんなさい……。",
			"宇沢レイサの成長に、終わりはありません！",
			"こ、これを、私に、ですか？",
			"成長には責任がともなうもの。もっともっと頑張ります！",
			"ありがとうございます、先生！これからも頑張りますね！",
			"本当は、まだ実感がありません。 私は……ちゃんと先生のお役に立てているのでしょうか…… そうだといいなあって、いつも心から願っています。",
			"居心地の良い場所ですね！",
			"ひ、人が多い……。",
			"どこにいればいいんだろう……。",
			"いや、逆にこういう時こそ普段どおりに……！",
			"や、やっぱ止めとこう……変な人だと思われちゃう。",
			"先生さえよろしければ……その……、先生の護衛をもっとしたいです。",
			"私が今ここにいるのは全部先生のおかげです！",
			"先生には……、その……、情けないところを多くお見せした気がしますが……大丈夫ですよね？",
			"これまで通り、いえ！これからも！よろしく！！！お願いいたします！！！",
			"……なんだか、不思議な気分です。こうして…… 人と一緒に、日の出を見るのは初めてで。",
			"そういえば、誰かと二人きりで過ごした時間も……。 もしかすると、これが最長かも……はは。",
			"んん……また変なことを言ってしまった気がします……。 実はまだ、少し眠くて……自分でも何を言っているのか、よく……。",
			"で、でも先生は……なんでも分かってくださるから…… 今回も……いつもみたいに、分かってくださいね。へへ……。",
			"ん……いいですね……。 先生……ありがとうございます……ひひ……。",
			"アメがなかったら、イタズラをしてもらえる日です！ ……え？解釈が変？",
			"メリークリスマス～！！ え？プレゼント交換？ あ、ああっ……！？忘れてました…！ あんまりこういうのは、慣れてなくて……！",
			"あけましておめでとうございます、先生！ 今年もよろしくお願いしますね！",
			"あっ、私の誕生日ですか？ありがとうございます！ 最近、誕生日を祝ってくださる方が多くて！ ……本当、長生きしてみるもんですね！ 私、まだ15歳ですけど！",
			"お誕生日おめでとうございます、先生！！！ すっごく特別な日ですから、一緒にパトロールなどいかがでしょうか！"
	};

	@Override
	public void onInitializeClient() {
		LOGGER.info(serif[new Random().nextInt(serif.length)]);

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
