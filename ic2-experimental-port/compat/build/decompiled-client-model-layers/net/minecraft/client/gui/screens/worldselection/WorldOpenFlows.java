/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Lifecycle
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.slf4j.Logger;

public class WorldOpenFlows {
    private static final Logger f_233088_ = LogUtils.getLogger();
    private final Minecraft f_233089_;
    private final LevelStorageSource f_233090_;

    public WorldOpenFlows(Minecraft p_233093_, LevelStorageSource p_233094_) {
        this.f_233089_ = p_233093_;
        this.f_233090_ = p_233094_;
    }

    public void m_233133_(Screen p_233134_, String p_233135_) {
        this.m_233145_(p_233134_, p_233135_, false, true);
    }

    public void m_233157_(String p_233158_, LevelSettings p_233159_, RegistryAccess p_233160_, WorldGenSettings p_233161_) {
        LevelStorageSource.LevelStorageAccess $$4 = this.m_233155_(p_233158_);
        if ($$4 == null) {
            return;
        }
        PackRepository $$5 = WorldOpenFlows.m_233105_($$4);
        DataPackConfig $$6 = p_233159_.m_46934_();
        try {
            WorldLoader.PackConfig $$7 = new WorldLoader.PackConfig($$5, $$6, false);
            WorldStem $$8 = this.m_233096_($$7, (p_233103_, p_233104_) -> Pair.of((Object)new PrimaryLevelData(p_233159_, p_233161_, Lifecycle.stable()), (Object)p_233160_.m_203557_()));
            this.f_233089_.m_231380_(p_233158_, $$4, $$5, $$8);
        }
        catch (Exception $$9) {
            f_233088_.warn("Failed to load datapacks, can't proceed with server load", (Throwable)$$9);
            WorldOpenFlows.m_233116_($$4, p_233158_);
        }
    }

    @Nullable
    private LevelStorageSource.LevelStorageAccess m_233155_(String p_233156_) {
        try {
            return this.f_233090_.m_78260_(p_233156_);
        }
        catch (IOException $$1) {
            f_233088_.warn("Failed to read level {} data", (Object)p_233156_, (Object)$$1);
            SystemToast.m_94852_(this.f_233089_, p_233156_);
            this.f_233089_.m_91152_(null);
            return null;
        }
    }

    public void m_233107_(LevelStorageSource.LevelStorageAccess p_233108_, ReloadableServerResources p_233109_, RegistryAccess.Frozen p_233110_, WorldData p_233111_) {
        PackRepository $$4 = WorldOpenFlows.m_233105_(p_233108_);
        CloseableResourceManager $$5 = (CloseableResourceManager)new WorldLoader.PackConfig($$4, p_233111_.m_7513_(), false).m_214399_().getSecond();
        this.f_233089_.m_231380_(p_233108_.m_78277_(), p_233108_, $$4, new WorldStem($$5, p_233109_, p_233110_, p_233111_));
    }

    private static PackRepository m_233105_(LevelStorageSource.LevelStorageAccess p_233106_) {
        return new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(p_233106_.m_78283_(LevelResource.f_78180_).toFile(), PackSource.f_10529_));
    }

    private WorldStem m_233122_(LevelStorageSource.LevelStorageAccess p_233123_, boolean p_233124_, PackRepository p_233125_) throws Exception {
        DataPackConfig $$3 = p_233123_.m_78309_();
        if ($$3 == null) {
            throw new IllegalStateException("Failed to load data pack config");
        }
        WorldLoader.PackConfig $$4 = new WorldLoader.PackConfig(p_233125_, $$3, p_233124_);
        return this.m_233096_($$4, (p_233114_, p_233115_) -> {
            RegistryAccess.Writable $$3 = RegistryAccess.m_206197_();
            RegistryOps<Tag> $$4 = RegistryOps.m_206813_(NbtOps.f_128958_, $$3, p_233114_);
            WorldData $$5 = p_233123_.m_211747_($$4, p_233115_, $$3.m_211816_());
            if ($$5 == null) {
                throw new IllegalStateException("Failed to load world");
            }
            return Pair.of((Object)$$5, (Object)$$3.m_203557_());
        });
    }

    public WorldStem m_233119_(LevelStorageSource.LevelStorageAccess p_233120_, boolean p_233121_) throws Exception {
        PackRepository $$2 = WorldOpenFlows.m_233105_(p_233120_);
        return this.m_233122_(p_233120_, p_233121_, $$2);
    }

    private WorldStem m_233096_(WorldLoader.PackConfig p_233097_, WorldLoader.WorldDataSupplier<WorldData> p_233098_) throws Exception {
        WorldLoader.InitConfig $$2 = new WorldLoader.InitConfig(p_233097_, Commands.CommandSelection.INTEGRATED, 2);
        CompletableFuture<WorldStem> $$3 = WorldStem.m_214415_($$2, p_233098_, Util.m_183991_(), this.f_233089_);
        this.f_233089_.m_18701_($$3::isDone);
        return $$3.get();
    }

    /*
     * WARNING - void declaration
     */
    private void m_233145_(Screen p_233146_, String p_233147_, boolean p_233148_, boolean p_233149_) {
        boolean $$11;
        void $$8;
        LevelStorageSource.LevelStorageAccess $$4 = this.m_233155_(p_233147_);
        if ($$4 == null) {
            return;
        }
        PackRepository $$5 = WorldOpenFlows.m_233105_($$4);
        try {
            WorldStem $$6 = this.m_233122_($$4, p_233148_, $$5);
        }
        catch (Exception $$7) {
            f_233088_.warn("Failed to load datapacks, can't proceed with server load", (Throwable)$$7);
            this.f_233089_.m_91152_(new DatapackLoadFailureScreen(() -> this.m_233145_(p_233146_, p_233147_, true, p_233149_)));
            WorldOpenFlows.m_233116_($$4, p_233147_);
            return;
        }
        WorldData $$9 = $$8.f_206895_();
        boolean $$10 = $$9.m_5961_().m_64670_();
        boolean bl = $$11 = $$9.m_5754_() != Lifecycle.stable();
        if (p_233149_ && ($$10 || $$11)) {
            this.m_233140_(p_233146_, p_233147_, $$10, () -> this.m_233145_(p_233146_, p_233147_, p_233148_, false));
            $$8.close();
            WorldOpenFlows.m_233116_($$4, p_233147_);
            return;
        }
        ((CompletableFuture)((CompletableFuture)((CompletableFuture)this.f_233089_.m_91100_().m_235003_($$4).thenApply(p_233177_ -> true)).exceptionallyComposeAsync(p_233183_ -> {
            f_233088_.warn("Failed to load pack: ", p_233183_);
            return this.m_233095_();
        }, (Executor)this.f_233089_)).thenAcceptAsync(arg_0 -> this.m_233162_(p_233147_, $$4, $$5, (WorldStem)$$8, p_233146_, arg_0), (Executor)this.f_233089_)).exceptionally(p_233175_ -> {
            this.f_233089_.m_231412_(CrashReport.m_127521_(p_233175_, "Load world"));
            return null;
        });
    }

    private CompletableFuture<Boolean> m_233095_() {
        CompletableFuture<Boolean> $$0 = new CompletableFuture<Boolean>();
        this.f_233089_.m_91152_(new ConfirmScreen($$0::complete, Component.m_237115_("multiplayer.texturePrompt.failure.line1"), Component.m_237115_("multiplayer.texturePrompt.failure.line2"), CommonComponents.f_130659_, CommonComponents.f_130656_));
        return $$0;
    }

    private static void m_233116_(LevelStorageSource.LevelStorageAccess p_233117_, String p_233118_) {
        try {
            p_233117_.close();
        }
        catch (IOException $$2) {
            f_233088_.warn("Failed to unlock access to level {}", (Object)p_233118_, (Object)$$2);
        }
    }

    private void m_233140_(Screen p_233141_, String p_233142_, boolean p_233143_, Runnable p_233144_) {
        MutableComponent $$7;
        MutableComponent $$6;
        if (p_233143_) {
            MutableComponent $$4 = Component.m_237115_("selectWorld.backupQuestion.customized");
            MutableComponent $$5 = Component.m_237115_("selectWorld.backupWarning.customized");
        } else {
            $$6 = Component.m_237115_("selectWorld.backupQuestion.experimental");
            $$7 = Component.m_237115_("selectWorld.backupWarning.experimental");
        }
        this.f_233089_.m_91152_(new BackupConfirmScreen(p_233141_, (p_233172_, p_233173_) -> {
            if (p_233172_) {
                EditWorldScreen.m_101260_(this.f_233090_, p_233142_);
            }
            p_233144_.run();
        }, $$6, $$7, false));
    }

    public static void m_233126_(Minecraft p_233127_, CreateWorldScreen p_233128_, Lifecycle p_233129_, Runnable p_233130_) {
        BooleanConsumer $$4 = p_233154_ -> {
            if (p_233154_) {
                p_233130_.run();
            } else {
                p_233127_.m_91152_(p_233128_);
            }
        };
        if (p_233129_ == Lifecycle.stable()) {
            p_233130_.run();
        } else if (p_233129_ == Lifecycle.experimental()) {
            p_233127_.m_91152_(new ConfirmScreen($$4, Component.m_237115_("selectWorld.import_worldgen_settings.experimental.title"), Component.m_237115_("selectWorld.import_worldgen_settings.experimental.question")));
        } else {
            p_233127_.m_91152_(new ConfirmScreen($$4, Component.m_237115_("selectWorld.import_worldgen_settings.deprecated.title"), Component.m_237115_("selectWorld.import_worldgen_settings.deprecated.question")));
        }
    }

    private /* synthetic */ void m_233162_(String p_233163_, LevelStorageSource.LevelStorageAccess p_233164_, PackRepository p_233165_, WorldStem p_233166_, Screen p_233167_, Boolean p_233168_) {
        if (p_233168_.booleanValue()) {
            this.f_233089_.m_231380_(p_233163_, p_233164_, p_233165_, p_233166_);
        } else {
            p_233166_.close();
            WorldOpenFlows.m_233116_(p_233164_, p_233163_);
            this.f_233089_.m_91100_().m_235009_().thenRunAsync(() -> this.f_233089_.m_91152_(p_233167_), this.f_233089_);
        }
    }
}

