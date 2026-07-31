/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 */
package ic2.core.init;

import com.google.common.base.Joiner;
import ic2.core.IC2;
import ic2.core.util.Config;
import ic2.core.util.ConfigUtil;
import java.io.File;
import java.util.Iterator;

public class MainConfig {
    public static boolean ignoreInvalidRecipes = false;
    private static Config config;
    private static Config defaultConfig;

    public static void load() {
        config = new Config("ic2 general config");
        defaultConfig = new Config("ic2 default config");
        try {
            config.load(IC2.class.getResourceAsStream("/assets/ic2/config/general.ini"));
            defaultConfig.load(IC2.class.getResourceAsStream("/assets/ic2/config/general.ini"));
        }
        catch (Exception exception) {
            throw new RuntimeException("Error loading base config", exception);
        }
        File file = MainConfig.getFile();
        try {
            if (file.exists()) {
                config.load(file);
            }
        }
        catch (Exception exception) {
            throw new RuntimeException("Error loading user config", exception);
        }
        MainConfig.upgradeContents();
        MainConfig.save();
        ignoreInvalidRecipes = ConfigUtil.getBool(MainConfig.get(), "recipes/ignoreInvalidRecipes");
    }

    public static void save() {
        try {
            config.save(MainConfig.getFile());
        }
        catch (Exception exception) {
            throw new RuntimeException("Error saving user config", exception);
        }
    }

    public static Config get() {
        return config;
    }

    public static Config.Value getDefault(String string) {
        return defaultConfig.get(string);
    }

    public static Iterator<Config.Value> getDefaults(String string) {
        return defaultConfig.getSub(string).valueIterator();
    }

    private static File getFile() {
        File file = new File(IC2.sideProxy.getMinecraftDir(), "config");
        file.mkdirs();
        return new File(file, "IC2.ini");
    }

    private static void upgradeContents() {
        String[] stringArray;
        if (config.get("worldgen/copperOre") != null) {
            for (String string : stringArray = new String[]{"copper", "tin", "uranium", "lead"}) {
                Config.Value value = config.remove("worldgen/" + string + "Ore");
                if (value == null || value.getBool()) continue;
                Config.Value value2 = config.get("worldgen/" + string + "/enabled");
                value2.set(false);
            }
        }
        if ((stringArray = ConfigUtil.asList(ConfigUtil.getString(config, "balance/recyclerBlacklist"))).contains("IC2:blockScaffold")) {
            stringArray.set(stringArray.indexOf("IC2:blockScaffold"), (String)"IC2:scaffold");
            config.set("balance/recyclerBlacklist", Joiner.on((String)", ").join((Iterable)stringArray));
        }
        if (config.get("misc/enableIc2Audio") != null) {
            config.get("audio/enabled").set(config.remove("misc/enableIc2Audio").getBool());
        }
        if (config.get("misc/maxAudioSourceCount") != null) {
            config.get("audio/maxSourceCount").set(config.remove("misc/maxAudioSourceCount").getInt());
        }
    }
}

