/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.gametest.framework;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import java.net.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.gametest.framework.GameTestBatch;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestRunner;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.gametest.framework.GlobalTestReporter;
import net.minecraft.gametest.framework.MultipleTestTracker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.slf4j.Logger;

public class GameTestServer
extends MinecraftServer {
    private static final Logger f_177585_ = LogUtils.getLogger();
    private static final int f_177586_ = 20;
    private static final Services f_236789_ = new Services(null, SignatureValidator.f_216348_, null, null);
    private final List<GameTestBatch> f_177587_;
    private final BlockPos f_177588_;
    private static final GameRules f_177589_ = Util.m_137469_(new GameRules(), p_177615_ -> {
        p_177615_.m_46170_(GameRules.f_46134_).m_46246_(false, null);
        p_177615_.m_46170_(GameRules.f_46150_).m_46246_(false, null);
    });
    private static final LevelSettings f_177590_ = new LevelSettings("Test Level", GameType.CREATIVE, false, Difficulty.NORMAL, true, f_177589_, DataPackConfig.f_45842_);
    @Nullable
    private MultipleTestTracker f_177591_;

    public static GameTestServer m_206606_(Thread p_206607_, LevelStorageSource.LevelStorageAccess p_206608_, PackRepository p_206609_, Collection<GameTestBatch> p_206610_, BlockPos p_206611_) {
        if (p_206610_.isEmpty()) {
            throw new IllegalArgumentException("No test batches were given!");
        }
        WorldLoader.PackConfig $$5 = new WorldLoader.PackConfig(p_206609_, DataPackConfig.f_45842_, false);
        WorldLoader.InitConfig $$6 = new WorldLoader.InitConfig($$5, Commands.CommandSelection.DEDICATED, 4);
        try {
            f_177585_.debug("Starting resource loading");
            Stopwatch $$7 = Stopwatch.createStarted();
            WorldStem $$8 = (WorldStem)Util.m_214679_(p_236792_ -> WorldStem.m_214415_($$6, (p_236794_, p_236795_) -> {
                RegistryAccess.Frozen $$2 = RegistryAccess.f_123049_.get();
                WorldGenSettings $$3 = $$2.m_175515_(Registry.f_235726_).m_206081_(WorldPresets.f_226438_).m_203334_().m_226421_(0L, false, false);
                PrimaryLevelData $$4 = new PrimaryLevelData(f_177590_, $$3, Lifecycle.stable());
                return Pair.of((Object)$$4, (Object)$$2);
            }, Util.m_183991_(), p_236792_)).get();
            $$7.stop();
            f_177585_.debug("Finished resource loading after {} ms", (Object)$$7.elapsed(TimeUnit.MILLISECONDS));
            return new GameTestServer(p_206607_, p_206608_, p_206609_, $$8, p_206610_, p_206611_);
        }
        catch (Exception $$9) {
            f_177585_.warn("Failed to load vanilla datapack, bit oops", (Throwable)$$9);
            System.exit(-1);
            throw new IllegalStateException();
        }
    }

    private GameTestServer(Thread p_206597_, LevelStorageSource.LevelStorageAccess p_206598_, PackRepository p_206599_, WorldStem p_206600_, Collection<GameTestBatch> p_206601_, BlockPos p_206602_) {
        super(p_206597_, p_206598_, p_206599_, p_206600_, Proxy.NO_PROXY, DataFixers.m_14512_(), f_236789_, LoggerChunkProgressListener::new);
        this.f_177587_ = Lists.newArrayList(p_206601_);
        this.f_177588_ = p_206602_;
    }

    @Override
    public boolean m_7038_() {
        this.m_129823_(new PlayerList(this, this.m_206579_(), this.f_129745_, 1){});
        this.m_130006_();
        ServerLevel $$0 = this.m_129783_();
        $$0.m_8733_(this.f_177588_, 0.0f);
        int $$1 = 20000000;
        $$0.m_8606_(20000000, 20000000, false, false);
        f_177585_.info("Started game test server");
        return true;
    }

    @Override
    public void m_5705_(BooleanSupplier p_177619_) {
        super.m_5705_(p_177619_);
        ServerLevel $$1 = this.m_129783_();
        if (!this.m_177628_()) {
            this.m_177624_($$1);
        }
        if ($$1.m_46467_() % 20L == 0L) {
            f_177585_.info(this.f_177591_.m_127822_());
        }
        if (this.f_177591_.m_127821_()) {
            this.m_7570_(false);
            f_177585_.info(this.f_177591_.m_127822_());
            GlobalTestReporter.m_177652_();
            f_177585_.info("========= {} GAME TESTS COMPLETE ======================", (Object)this.f_177591_.m_127820_());
            if (this.f_177591_.m_127818_()) {
                f_177585_.info("{} required tests failed :(", (Object)this.f_177591_.m_127803_());
                this.f_177591_.m_177682_().forEach(p_206615_ -> f_177585_.info("   - {}", (Object)p_206615_.m_127633_()));
            } else {
                f_177585_.info("All {} required tests passed :)", (Object)this.f_177591_.m_127820_());
            }
            if (this.f_177591_.m_127819_()) {
                f_177585_.info("{} optional tests failed", (Object)this.f_177591_.m_127816_());
                this.f_177591_.m_177683_().forEach(p_206613_ -> f_177585_.info("   - {}", (Object)p_206613_.m_127633_()));
            }
            f_177585_.info("====================================================");
        }
    }

    @Override
    public SystemReport m_142424_(SystemReport p_177613_) {
        p_177613_.m_143519_("Type", "Game test server");
        return p_177613_;
    }

    @Override
    public void m_6988_() {
        super.m_6988_();
        f_177585_.info("Game test server shutting down");
        System.exit(this.f_177591_.m_127803_());
    }

    @Override
    public void m_7268_(CrashReport p_177623_) {
        super.m_7268_(p_177623_);
        f_177585_.error("Game test server crashed\n{}", (Object)p_177623_.m_127526_());
        System.exit(1);
    }

    private void m_177624_(ServerLevel p_177625_) {
        Collection<GameTestInfo> $$1 = GameTestRunner.m_127726_(this.f_177587_, new BlockPos(0, -60, 0), Rotation.NONE, p_177625_, GameTestTicker.f_177648_, 8);
        this.f_177591_ = new MultipleTestTracker($$1);
        f_177585_.info("{} tests are now running!", (Object)this.f_177591_.m_127820_());
    }

    private boolean m_177628_() {
        return this.f_177591_ != null;
    }

    @Override
    public boolean m_7035_() {
        return false;
    }

    @Override
    public int m_7022_() {
        return 0;
    }

    @Override
    public int m_7034_() {
        return 4;
    }

    @Override
    public boolean m_6983_() {
        return false;
    }

    @Override
    public boolean m_6982_() {
        return false;
    }

    @Override
    public int m_7032_() {
        return 0;
    }

    @Override
    public boolean m_6994_() {
        return false;
    }

    @Override
    public boolean m_6993_() {
        return true;
    }

    @Override
    public boolean m_6992_() {
        return false;
    }

    @Override
    public boolean m_6102_() {
        return false;
    }

    @Override
    public boolean m_7779_(GameProfile p_177617_) {
        return false;
    }
}

