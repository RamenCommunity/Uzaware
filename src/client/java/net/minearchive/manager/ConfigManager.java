package net.minearchive.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minearchive.AccessMC;
import net.minearchive.Uzaware;
import net.minearchive.module.HudModule;
import net.minearchive.module.Module;
import net.minearchive.setting.settings.*;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ConfigManager implements AccessMC {
    private final File CLIENT_PATH = new File(client.runDirectory, "uzaware");
    private String currentConfig = "default", currentBind = "default", currentHud = "default";

    public void onInit() {
        load();
    }

    public void onShutdown() {
        save(
                new ConfigFile(new File(new File(CLIENT_PATH, "modules"), String.format("%s.json", currentConfig)), TYPE.MODULE),
                new ConfigFile(new File(new File(CLIENT_PATH, "binds"), String.format("%s.json", currentBind)), TYPE.BIND),
                new ConfigFile(new File(new File(CLIENT_PATH, "hud"), String.format("%s.json", currentHud)), TYPE.HUD)
        );
    }

    public final void save(ConfigFile... configFiles) {
        if (configFiles.length == 0) return;
        Arrays.stream(configFiles).forEach(configFile -> {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            if (configFile.type == TYPE.MODULE) {
                jsonObject.addProperty("CREATED_VERSION", Uzaware.version);
                ModuleManager.INSTANCE.modules.stream().filter(m -> !(m instanceof HudModule)).forEach(m -> jsonArray.add(new ModuleConfig(m).getJsonObject()));
                jsonObject.add("modules", jsonArray);
            }

            if (configFile.type == TYPE.BIND) {
                ModuleManager.INSTANCE.modules.forEach(m -> jsonArray.add(new BindConfig(m).getJsonObject()));
                jsonObject.add("binds", jsonArray);
            }

            if (configFile.type == TYPE.HUD) {
                ModuleManager.INSTANCE.modules.stream().filter(m -> m instanceof HudModule).forEach(m -> jsonArray.add(new HudConfig((HudModule) m).jsonObject));
                jsonObject.add("hud", jsonArray);
            }

            createFile(configFile.fileName, jsonObject);
        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CREATED_VERSION", Uzaware.version);
        jsonObject.addProperty("CHOSE_MODULE_CONFIG", currentConfig == null ? "default" : currentConfig);
        jsonObject.addProperty("CHOSE_BIND_CONFIG", currentBind == null ? "default" : currentBind);
        jsonObject.addProperty("CHOSE_HUD_CONFIG", currentHud == null ? "default" : currentHud);
        createFile(new File(CLIENT_PATH, "client.json"), jsonObject);
    }

    public void load() {
        JsonObject client = loadFile(new File(CLIENT_PATH, "client.json"));
        if (client.isEmpty() || client.isJsonNull()) return;
        currentConfig = client.get("CHOSE_MODULE_CONFIG").getAsString();
        currentBind = client.get("CHOSE_BIND_CONFIG").getAsString();
        currentHud = client.get("CHOSE_HUD_CONFIG").getAsString();

        for (TYPE value : TYPE.values()) {
            if (value == TYPE.FRIEND) continue; //TODO
            JsonObject config = loadFile(new File(new File(CLIENT_PATH, value.getDir()), String.format("%s.json", client.get(value.getClientConfig()).getAsString())));
            if (value == TYPE.MODULE)
                LogManager.getLogger("Uzaware ConfigManager").info("Just loaded module config created in version: {}", config.get("CREATED_VERSION").getAsString());
            config.get(value.getDir()).getAsJsonArray().forEach(je -> je.getAsJsonObject().entrySet().forEach(entry -> {
                Object data;
                if (value != TYPE.BIND) data = entry.getValue().getAsJsonObject();
                else data = entry.getValue().getAsInt();
                Module module = ModuleManager.INSTANCE.modules.stream()
                        .filter(m -> m.name.equals(entry.getKey()))
                        .findFirst().orElse(null);
                if (module == null) return;
                load(module, data, value);
            }));
        }
    }

    private void load(Module module, Object jsonObject, TYPE type) {
        switch (type) {
            case MODULE -> ((JsonObject) jsonObject).entrySet().forEach(config -> {
                if (config.getKey().equals("enabled")) module.enabled = config.getValue().getAsBoolean();
                if (config.getKey().equals("configs")) apply(config, module);
            });
            case BIND -> module.bind.getValue().setKey((int) jsonObject);
            case FRIEND -> {
                //TODO
            }
            case HUD -> ((JsonObject) jsonObject).entrySet().forEach(config -> {
                if (!(module instanceof HudModule)) return;
                if (config.getKey().equals("enabled")) module.enabled = config.getValue().getAsBoolean();
                if (config.getKey().equals("x")) ((HudModule) module).x = config.getValue().getAsFloat();
                if (config.getKey().equals("y")) ((HudModule) module).x = config.getValue().getAsFloat();
                if (config.getKey().equals("configs")) apply(config, module);
            });
        }
    }

    private void apply(Map. Entry<String, JsonElement> entry, Module module) {
        entry.getValue().getAsJsonArray().forEach(jsonElement -> jsonElement.getAsJsonObject().entrySet().forEach(setting -> module.settings.stream().filter(s -> setting.getKey().equals(s.getName())).forEach(s -> {
            if (s instanceof BooleanSetting) ((BooleanSetting) s).setValue(setting.getValue().getAsBoolean());
            // if (setting instanceof ColorSetting) ((ColorSetting) s).setValue(setting.getValue().getAsColor());
            if (s instanceof DoubleSetting) ((DoubleSetting) s).setValue(setting.getValue().getAsDouble());
            if (s instanceof EnumSetting<? extends Enum<?>>) ((EnumSetting<? extends Enum<?>>) s).setValueByName(setting.getValue().getAsString());
            if (s instanceof FloatSetting) ((FloatSetting) s).setValue(setting.getValue().getAsFloat());
            if (s instanceof IntegerSetting) ((IntegerSetting) s).setValue(setting.getValue().getAsInt());
            if (s instanceof KeyBindSetting) ((KeyBindSetting) s).getValue().setKey(setting.getValue().getAsInt());
            if (s instanceof StringSetting) ((StringSetting) s).setValue(setting.getValue().getAsString());
        })));
    }

    private void createFile(File file, JsonObject jsonObject) {
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs()
                file.createNewFile();
            }
            if (!file.exists()) file.createNewFile();

            try (Writer writer = new FileWriter(file)) {
                Gson gson = new Gson();
                gson.toJson(jsonObject, writer);
            }
        } catch (Exception e) {
            LogManager.getLogger("Uzaware ConfigManager").error("Error caused while creating file...", e);
        }
    }

    public JsonObject loadFile(File file) {
        if (!file.exists()) return new JsonObject();
        try (InputStream is = new FileInputStream(file)) {
            try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return new Gson().fromJson(reader, JsonObject.class);
            }
        } catch (Exception e) {
            LogManager.getLogger("Uzaware ConfigManager").error("Error caused while loading file...", e);
        }
        return new JsonObject();
    }

    public record ConfigFile(File fileName, TYPE type) { }

    public enum TYPE {
        MODULE("modules", "CHOSE_MODULE_CONFIG"),
        BIND("binds", "CHOSE_BIND_CONFIG"),
        FRIEND("friends", "CHOSE_FRIEND_CONFIG"),
        HUD("hud", "CHOSE_HUD_CONFIG");

        private final String dir, clientConfig;
        TYPE(String dir, String clientConfig) {
            this.dir = dir;
            this.clientConfig = clientConfig;
        }

        public String getDir() {
            return dir;
        }

        public String getClientConfig() {
            return clientConfig;
        }
    }

    public abstract static class Config {
        protected final JsonObject jsonObject = new JsonObject();

        public JsonObject getJsonObject() {
            return jsonObject;
        }
    }

    public static class ModuleConfig extends Config {
        public ModuleConfig(Module module) {
            if (module == null) return;

            final JsonArray jsonArray = new JsonArray();
            module.settings.forEach(s -> {
                final JsonObject object = new JsonObject();
                if (s instanceof BooleanSetting) object.addProperty(s.getName(), ((BooleanSetting) s).getValue());
//                if (s instanceof ColorSetting) object.addProperty(s.getName(), ((ColorSetting) s).getValue());
                if (s instanceof DoubleSetting) object.addProperty(s.getName(), ((DoubleSetting) s).getValue());
                if (s instanceof EnumSetting<? extends Enum<?>>) object.addProperty(s.getName(), ((EnumSetting<? extends Enum<?>>) s).getValue().name());
                if (s instanceof FloatSetting) object.addProperty(s.getName(), ((FloatSetting) s).getValue());
                if (s instanceof IntegerSetting) object.addProperty(s.getName(), ((IntegerSetting) s).getValue());
                if (s instanceof KeyBindSetting) {
                    if (s == module.bind) return;
                    object.addProperty(s.getName(), ((KeyBindSetting) s).getValue().getKey());
                }
                if (s instanceof StringSetting) object.addProperty(s.getName(), ((StringSetting) s).getValue());
                jsonArray.add(object);
            });
            JsonObject data = new JsonObject();
            data.addProperty("enabled", module.enabled);
            data.add("configs", jsonArray);
            jsonObject.add(module.name, data);
        }
    }

    public static class BindConfig extends Config {
        public BindConfig(Module module) {
            jsonObject.addProperty(module.name, module.bind.getValue().getKey());
        }
    }

    public static class HudConfig extends Config {
        public HudConfig(HudModule hudModule) {
            final JsonObject object = new JsonObject();
            final JsonArray jsonArray = new JsonArray();
            object.addProperty("enabled", hudModule.enabled);
            object.addProperty("x", hudModule.x);
            object.addProperty("y", hudModule.y);
            hudModule.settings.forEach(s -> {
                JsonObject config = new JsonObject();
                if (s instanceof BooleanSetting) config.addProperty(s.getName(), ((BooleanSetting) s).getValue());
//                if (s instanceof ColorSetting) config.addProperty(s.getName(), ((ColorSetting) s).getValue());
                if (s instanceof DoubleSetting) config.addProperty(s.getName(), ((DoubleSetting) s).getValue());
                if (s instanceof EnumSetting<? extends Enum<?>>) config.addProperty(s.getName(), ((EnumSetting<? extends Enum<?>>) s).getValue().name());
                if (s instanceof FloatSetting) config.addProperty(s.getName(), ((FloatSetting) s).getValue());
                if (s instanceof IntegerSetting) config.addProperty(s.getName(), ((IntegerSetting) s).getValue());
                if (s instanceof KeyBindSetting) {
                    if (s == hudModule.bind) return;
                    config.addProperty(s.getName(), ((KeyBindSetting) s).getValue().getKey());
                }
                if (s instanceof StringSetting) config.addProperty(s.getName(), ((StringSetting) s).getValue());
                jsonArray.add(config);
            });
            object.add("configs", jsonArray);
            jsonObject.add(hudModule.name, object);
        }
    }
}
