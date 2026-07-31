/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.dedicated;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.Settings;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.slf4j.Logger;

public class DedicatedServerProperties
extends Settings<DedicatedServerProperties> {
    static final Logger f_214805_ = LogUtils.getLogger();
    private static final Pattern f_214806_ = Pattern.compile("^[a-fA-F0-9]{40}$");
    public final boolean f_139728_ = this.m_139836_("online-mode", true);
    public final boolean f_139729_ = this.m_139836_("prevent-proxy-connections", false);
    public final String f_139730_ = this.m_139811_("server-ip", "");
    public final boolean f_139731_ = this.m_139836_("spawn-animals", true);
    public final boolean f_139732_ = this.m_139836_("spawn-npcs", true);
    public final boolean f_139733_ = this.m_139836_("pvp", true);
    public final boolean f_139734_ = this.m_139836_("allow-flight", false);
    public final String f_139736_ = this.m_139811_("motd", "A Minecraft Server");
    public final boolean f_139737_ = this.m_139836_("force-gamemode", false);
    public final boolean f_139738_ = this.m_139836_("enforce-whitelist", false);
    public final Difficulty f_139739_ = this.m_139821_("difficulty", DedicatedServerProperties.m_139850_(Difficulty::m_19029_, Difficulty::m_19031_), Difficulty::m_19036_, Difficulty.EASY);
    public final GameType f_139740_ = this.m_139821_("gamemode", DedicatedServerProperties.m_139850_(GameType::m_46393_, GameType::m_46400_), GameType::m_46405_, GameType.SURVIVAL);
    public final String f_139741_ = this.m_139811_("level-name", "world");
    public final int f_139742_ = this.m_139805_("server-port", 25565);
    @Nullable
    public final Boolean f_139744_ = this.m_139859_("announce-player-achievements");
    public final boolean f_139745_ = this.m_139836_("enable-query", false);
    public final int f_139746_ = this.m_139805_("query.port", 25565);
    public final boolean f_139747_ = this.m_139836_("enable-rcon", false);
    public final int f_139748_ = this.m_139805_("rcon.port", 25575);
    public final String f_139749_ = this.m_139811_("rcon.password", "");
    public final boolean f_139752_ = this.m_139836_("hardcore", false);
    public final boolean f_139753_ = this.m_139836_("allow-nether", true);
    public final boolean f_139705_ = this.m_139836_("spawn-monsters", true);
    public final boolean f_139707_ = this.m_139836_("use-native-transport", true);
    public final boolean f_139708_ = this.m_139836_("enable-command-block", false);
    public final int f_139709_ = this.m_139805_("spawn-protection", 16);
    public final int f_139710_ = this.m_139805_("op-permission-level", 4);
    public final int f_139711_ = this.m_139805_("function-permission-level", 2);
    public final long f_139712_ = this.m_139808_("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
    public final int f_214801_ = this.m_139805_("max-chained-neighbor-updates", 1000000);
    public final int f_139713_ = this.m_139805_("rate-limit", 0);
    public final int f_139714_ = this.m_139805_("view-distance", 10);
    public final int f_183715_ = this.m_139805_("simulation-distance", 10);
    public final int f_139715_ = this.m_139805_("max-players", 20);
    public final int f_139716_ = this.m_139805_("network-compression-threshold", 256);
    public final boolean f_139717_ = this.m_139836_("broadcast-rcon-to-ops", true);
    public final boolean f_139718_ = this.m_139836_("broadcast-console-to-ops", true);
    public final int f_139719_ = this.m_139832_("max-world-size", p_139771_ -> Mth.m_14045_(p_139771_, 1, 29999984), 29999984);
    public final boolean f_139720_ = this.m_139836_("sync-chunk-writes", true);
    public final boolean f_139721_ = this.m_139836_("enable-jmx-monitoring", false);
    public final boolean f_139722_ = this.m_139836_("enable-status", true);
    public final boolean f_183716_ = this.m_139836_("hide-online-players", false);
    public final int f_139723_ = this.m_139832_("entity-broadcast-range-percentage", p_139769_ -> Mth.m_14045_(p_139769_, 10, 1000), 100);
    public final String f_139724_ = this.m_139811_("text-filtering-config", "");
    public Optional<MinecraftServer.ServerResourcePackInfo> f_214802_;
    public final boolean f_214803_ = this.m_139836_("previews-chat", false);
    public final Settings.MutableValue<Integer> f_139725_ = this.m_139861_("player-idle-timeout", 0);
    public final Settings.MutableValue<Boolean> f_139726_ = this.m_139873_("white-list", false);
    public final boolean f_214804_ = this.m_139836_("enforce-secure-profile", true);
    private final WorldGenProperties f_207539_ = new WorldGenProperties(this.m_139811_("level-seed", ""), this.m_139817_("generator-settings", p_211543_ -> GsonHelper.m_13864_(!p_211543_.isEmpty() ? p_211543_ : "{}"), new JsonObject()), this.m_139836_("generate-structures", true), this.m_139817_("level-type", p_211541_ -> p_211541_.toLowerCase(Locale.ROOT), WorldPresets.f_226437_.m_135782_().toString()));
    @Nullable
    private WorldGenSettings f_139727_;

    public DedicatedServerProperties(Properties p_180926_) {
        super(p_180926_);
        this.f_214802_ = DedicatedServerProperties.m_214808_(this.m_139811_("resource-pack", ""), this.m_139811_("resource-pack-sha1", ""), this.m_139803_("resource-pack-hash"), this.m_139836_("require-resource-pack", false), this.m_139811_("resource-pack-prompt", ""));
    }

    public static DedicatedServerProperties m_180929_(Path p_180930_) {
        return new DedicatedServerProperties(DedicatedServerProperties.m_139839_(p_180930_));
    }

    @Override
    protected DedicatedServerProperties m_6764_(RegistryAccess p_139761_, Properties p_139762_) {
        DedicatedServerProperties $$2 = new DedicatedServerProperties(p_139762_);
        $$2.m_180927_(p_139761_);
        return $$2;
    }

    @Nullable
    private static Component m_214814_(String p_214815_) {
        if (!Strings.isNullOrEmpty((String)p_214815_)) {
            try {
                return Component.Serializer.m_130701_(p_214815_);
            }
            catch (Exception $$1) {
                f_214805_.warn("Failed to parse resource pack prompt '{}'", (Object)p_214815_, (Object)$$1);
            }
        }
        return null;
    }

    private static Optional<MinecraftServer.ServerResourcePackInfo> m_214808_(String p_214809_, String p_214810_, @Nullable String p_214811_, boolean p_214812_, String p_214813_) {
        String $$7;
        if (p_214809_.isEmpty()) {
            return Optional.empty();
        }
        if (!p_214810_.isEmpty()) {
            String $$5 = p_214810_;
            if (!Strings.isNullOrEmpty((String)p_214811_)) {
                f_214805_.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
            }
        } else if (!Strings.isNullOrEmpty((String)p_214811_)) {
            f_214805_.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
            String $$6 = p_214811_;
        } else {
            $$7 = "";
        }
        if ($$7.isEmpty()) {
            f_214805_.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
        } else if (!f_214806_.matcher($$7).matches()) {
            f_214805_.warn("Invalid sha1 for resource-pack-sha1");
        }
        Component $$8 = DedicatedServerProperties.m_214814_(p_214813_);
        return Optional.of(new MinecraftServer.ServerResourcePackInfo(p_214809_, $$7, p_214812_, $$8));
    }

    public WorldGenSettings m_180927_(RegistryAccess p_180928_) {
        if (this.f_139727_ == null) {
            this.f_139727_ = this.f_207539_.m_214826_(p_180928_);
        }
        return this.f_139727_;
    }

    @Override
    protected /* synthetic */ Settings m_6764_(RegistryAccess registryAccess, Properties properties) {
        return this.m_6764_(registryAccess, properties);
    }

    public record WorldGenProperties(String f_207542_, JsonObject f_207543_, boolean f_207544_, String f_207545_) {
        private static final Map<String, ResourceKey<WorldPreset>> f_214816_ = Map.of("default", WorldPresets.f_226437_, "largebiomes", WorldPresets.f_226439_);

        public WorldGenSettings m_214826_(RegistryAccess p_214827_) {
            long $$1 = WorldGenSettings.m_202192_(this.f_207542_()).orElse(RandomSource.m_216327_().m_188505_());
            Registry<WorldPreset> $$2 = p_214827_.m_175515_(Registry.f_235726_);
            Holder<WorldPreset> $$3 = $$2.m_203636_(WorldPresets.f_226437_).or(() -> $$2.m_203611_().findAny()).orElseThrow(() -> new IllegalStateException("Invalid datapack contents: can't find default preset"));
            Holder $$4 = Optional.ofNullable(ResourceLocation.m_135820_(this.f_207545_)).map(p_214821_ -> ResourceKey.m_135785_(Registry.f_235726_, p_214821_)).or(() -> Optional.ofNullable(f_214816_.get(this.f_207545_))).flatMap($$2::m_203636_).orElseGet(() -> {
                f_214805_.warn("Failed to parse level-type {}, defaulting to {}", (Object)this.f_207545_, (Object)$$3.m_203543_().map(p_214819_ -> p_214819_.m_135782_().toString()).orElse("[unnamed]"));
                return $$3;
            });
            WorldGenSettings $$5 = ((WorldPreset)$$4.m_203334_()).m_226421_($$1, this.f_207544_, false);
            if ($$4.m_203565_(WorldPresets.f_226438_)) {
                RegistryOps $$6 = RegistryOps.m_206821_(JsonOps.INSTANCE, p_214827_);
                Optional $$7 = FlatLevelGeneratorSettings.f_70347_.parse(new Dynamic($$6, (Object)this.f_207543_())).resultOrPartial(arg_0 -> ((Logger)f_214805_).error(arg_0));
                if ($$7.isPresent()) {
                    Registry<StructureSet> $$8 = p_214827_.m_175515_(Registry.f_211073_);
                    return WorldGenSettings.m_224673_(p_214827_, $$5, new FlatLevelSource($$8, (FlatLevelGeneratorSettings)$$7.get()));
                }
            }
            return $$5;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{WorldGenProperties.class, "levelSeed;generatorSettings;generateStructures;levelType", "f_207542_", "f_207543_", "f_207544_", "f_207545_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{WorldGenProperties.class, "levelSeed;generatorSettings;generateStructures;levelType", "f_207542_", "f_207543_", "f_207544_", "f_207545_"}, this);
        }

        @Override
        public final boolean equals(Object p_207556_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{WorldGenProperties.class, "levelSeed;generatorSettings;generateStructures;levelType", "f_207542_", "f_207543_", "f_207544_", "f_207545_"}, this, p_207556_);
        }
    }
}

