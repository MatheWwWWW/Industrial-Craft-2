/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Lifecycle
 *  joptsimple.AbstractOptionSpec
 *  joptsimple.ArgumentAcceptingOptionSpec
 *  joptsimple.NonOptionArgumentSpec
 *  joptsimple.OptionParser
 *  joptsimple.OptionSet
 *  joptsimple.OptionSpec
 *  joptsimple.OptionSpecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.server;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.OutputStream;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import net.minecraft.CrashReport;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.Eula;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;

public class Main {
    private static final Logger f_129670_ = LogUtils.getLogger();

    /*
     * WARNING - void declaration
     */
    @DontObfuscate
    public static void main(String[] p_129699_) {
        SharedConstants.m_142977_();
        OptionParser $$1 = new OptionParser();
        OptionSpecBuilder $$2 = $$1.accepts("nogui");
        OptionSpecBuilder $$3 = $$1.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
        OptionSpecBuilder $$4 = $$1.accepts("demo");
        OptionSpecBuilder $$5 = $$1.accepts("bonusChest");
        OptionSpecBuilder $$6 = $$1.accepts("forceUpgrade");
        OptionSpecBuilder $$7 = $$1.accepts("eraseCache");
        OptionSpecBuilder $$8 = $$1.accepts("safeMode", "Loads level with vanilla datapack only");
        AbstractOptionSpec $$9 = $$1.accepts("help").forHelp();
        ArgumentAcceptingOptionSpec $$10 = $$1.accepts("singleplayer").withRequiredArg();
        ArgumentAcceptingOptionSpec $$11 = $$1.accepts("universe").withRequiredArg().defaultsTo((Object)".", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec $$12 = $$1.accepts("world").withRequiredArg();
        ArgumentAcceptingOptionSpec $$13 = $$1.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo((Object)-1, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec $$14 = $$1.accepts("serverId").withRequiredArg();
        OptionSpecBuilder $$15 = $$1.accepts("jfrProfile");
        NonOptionArgumentSpec $$16 = $$1.nonOptions();
        try {
            void $$35;
            boolean $$28;
            OptionSet $$17 = $$1.parse(p_129699_);
            if ($$17.has((OptionSpec)$$9)) {
                $$1.printHelpOn((OutputStream)System.err);
                return;
            }
            CrashReport.m_127529_();
            if ($$17.has((OptionSpec)$$15)) {
                JvmProfiler.f_185340_.m_183425_(Environment.SERVER);
            }
            Bootstrap.m_135870_();
            Bootstrap.m_135889_();
            Util.m_137584_();
            Path $$18 = Paths.get("server.properties", new String[0]);
            DedicatedServerSettings $$19 = new DedicatedServerSettings($$18);
            $$19.m_139780_();
            Path $$20 = Paths.get("eula.txt", new String[0]);
            Eula $$21 = new Eula($$20);
            if ($$17.has((OptionSpec)$$3)) {
                f_129670_.info("Initialized '{}' and '{}'", (Object)$$18.toAbsolutePath(), (Object)$$20.toAbsolutePath());
                return;
            }
            if (!$$21.m_135944_()) {
                f_129670_.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
                return;
            }
            File $$22 = new File((String)$$17.valueOf((OptionSpec)$$11));
            Services $$23 = Services.m_214344_(new YggdrasilAuthenticationService(Proxy.NO_PROXY), $$22);
            String $$24 = Optional.ofNullable((String)$$17.valueOf((OptionSpec)$$12)).orElse($$19.m_139777_().f_139741_);
            LevelStorageSource $$25 = LevelStorageSource.m_78242_($$22.toPath());
            LevelStorageSource.LevelStorageAccess $$26 = $$25.m_78260_($$24);
            LevelSummary $$27 = $$26.m_78308_();
            if ($$27 != null) {
                if ($$27.m_193020_()) {
                    f_129670_.info("This world must be opened in an older version (like 1.6.4) to be safely converted");
                    return;
                }
                if (!$$27.m_193021_()) {
                    f_129670_.info("This world was created by an incompatible version.");
                    return;
                }
            }
            if ($$28 = $$17.has((OptionSpec)$$8)) {
                f_129670_.warn("Safe mode active, only vanilla datapack will be loaded");
            }
            PackRepository $$29 = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource($$26.m_78283_(LevelResource.f_78180_).toFile(), PackSource.f_10529_));
            try {
                DataPackConfig $$30 = Objects.requireNonNullElse($$26.m_78309_(), DataPackConfig.f_45842_);
                WorldLoader.PackConfig $$31 = new WorldLoader.PackConfig($$29, $$30, $$28);
                WorldLoader.InitConfig $$32 = new WorldLoader.InitConfig($$31, Commands.CommandSelection.DEDICATED, $$19.m_139777_().f_139711_);
                WorldStem $$33 = (WorldStem)Util.m_214679_(arg_0 -> Main.m_236689_($$32, $$26, $$17, (OptionSpec)$$4, $$19, (OptionSpec)$$5, arg_0)).get();
            }
            catch (Exception $$34) {
                f_129670_.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", (Throwable)$$34);
                return;
            }
            RegistryAccess.Frozen $$36 = $$35.f_206894_();
            $$19.m_139777_().m_180927_($$36);
            WorldData $$37 = $$35.f_206895_();
            if ($$17.has((OptionSpec)$$6)) {
                Main.m_195488_($$26, DataFixers.m_14512_(), $$17.has((OptionSpec)$$7), () -> true, $$37.m_5961_());
            }
            $$26.m_78287_($$36, $$37);
            final DedicatedServer $$38 = MinecraftServer.m_129872_(arg_0 -> Main.m_236697_($$26, $$29, (WorldStem)$$35, $$19, $$23, $$17, (OptionSpec)$$10, (OptionSpec)$$13, (OptionSpec)$$4, (OptionSpec)$$14, (OptionSpec)$$2, (OptionSpec)$$16, arg_0));
            Thread $$39 = new Thread("Server Shutdown Thread"){

                @Override
                public void run() {
                    $$38.m_7570_(true);
                }
            };
            $$39.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_129670_));
            Runtime.getRuntime().addShutdownHook($$39);
        }
        catch (Exception $$40) {
            f_129670_.error(LogUtils.FATAL_MARKER, "Failed to start the minecraft server", (Throwable)$$40);
        }
    }

    private static void m_195488_(LevelStorageSource.LevelStorageAccess p_195489_, DataFixer p_195490_, boolean p_195491_, BooleanSupplier p_195492_, WorldGenSettings p_195493_) {
        f_129670_.info("Forcing world upgrade!");
        WorldUpgrader $$5 = new WorldUpgrader(p_195489_, p_195490_, p_195493_, p_195491_);
        Component $$6 = null;
        while (!$$5.m_18829_()) {
            int $$8;
            Component $$7 = $$5.m_18837_();
            if ($$6 != $$7) {
                $$6 = $$7;
                f_129670_.info($$5.m_18837_().getString());
            }
            if (($$8 = $$5.m_18834_()) > 0) {
                int $$9 = $$5.m_18835_() + $$5.m_18836_();
                f_129670_.info("{}% completed ({} / {} chunks)...", new Object[]{Mth.m_14143_((float)$$9 / (float)$$8 * 100.0f), $$9, $$8});
            }
            if (!p_195492_.getAsBoolean()) {
                $$5.m_18820_();
                continue;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private static /* synthetic */ DedicatedServer m_236697_(LevelStorageSource.LevelStorageAccess p_236698_, PackRepository p_236699_, WorldStem p_236700_, DedicatedServerSettings p_236701_, Services p_236702_, OptionSet p_236703_, OptionSpec p_236704_, OptionSpec p_236705_, OptionSpec p_236706_, OptionSpec p_236707_, OptionSpec p_236708_, OptionSpec p_236709_, Thread p_236710_) {
        boolean $$14;
        DedicatedServer $$13 = new DedicatedServer(p_236710_, p_236698_, p_236699_, p_236700_, p_236701_, DataFixers.m_14512_(), p_236702_, LoggerChunkProgressListener::new);
        $$13.m_236740_(p_236703_.has(p_236704_) ? new GameProfile(null, (String)p_236703_.valueOf(p_236704_)) : null);
        $$13.m_129801_((Integer)p_236703_.valueOf(p_236705_));
        $$13.m_129975_(p_236703_.has(p_236706_));
        $$13.m_129948_((String)p_236703_.valueOf(p_236707_));
        boolean bl = $$14 = !p_236703_.has(p_236708_) && !p_236703_.valuesOf(p_236709_).contains("nogui");
        if ($$14 && !GraphicsEnvironment.isHeadless()) {
            $$13.m_139667_();
        }
        return $$13;
    }

    private static /* synthetic */ CompletableFuture m_236689_(WorldLoader.InitConfig p_236690_, LevelStorageSource.LevelStorageAccess p_236691_, OptionSet p_236692_, OptionSpec p_236693_, DedicatedServerSettings p_236694_, OptionSpec p_236695_, Executor p_236696_) {
        return WorldStem.m_214415_(p_236690_, (p_236717_, p_236718_) -> {
            WorldGenSettings $$14;
            LevelSettings $$13;
            RegistryAccess.Writable $$7 = RegistryAccess.m_206197_();
            RegistryOps<Tag> $$8 = RegistryOps.m_206813_(NbtOps.f_128958_, $$7, p_236717_);
            WorldData $$9 = p_236691_.m_211747_($$8, p_236718_, $$7.m_211816_());
            if ($$9 != null) {
                return Pair.of((Object)$$9, (Object)$$7.m_203557_());
            }
            if (p_236692_.has(p_236693_)) {
                LevelSettings $$10 = MinecraftServer.f_129743_;
                WorldGenSettings $$11 = WorldPresets.m_226461_($$7);
            } else {
                DedicatedServerProperties $$12 = p_236694_.m_139777_();
                $$13 = new LevelSettings($$12.f_139741_, $$12.f_139740_, $$12.f_139752_, $$12.f_139739_, false, new GameRules(), p_236718_);
                $$14 = p_236692_.has(p_236695_) ? $$12.m_180927_($$7).m_64671_() : $$12.m_180927_($$7);
            }
            PrimaryLevelData $$15 = new PrimaryLevelData($$13, $$14, Lifecycle.stable());
            return Pair.of((Object)$$15, (Object)$$7.m_203557_());
        }, Util.m_183991_(), p_236696_);
    }
}

