/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonIOException
 *  com.google.gson.stream.JsonWriter
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DataResult$PartialResult
 *  com.mojang.serialization.JsonOps
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.OptimizeWorldScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.WorldStem;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class EditWorldScreen
extends Screen {
    private static final Logger f_101243_ = LogUtils.getLogger();
    private static final Gson f_101244_ = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    private static final Component f_101245_ = Component.m_237115_("selectWorld.enterName");
    private Button f_101246_;
    private final BooleanConsumer f_101247_;
    private EditBox f_101248_;
    private final LevelStorageSource.LevelStorageAccess f_101249_;

    public EditWorldScreen(BooleanConsumer p_101252_, LevelStorageSource.LevelStorageAccess p_101253_) {
        super(Component.m_237115_("selectWorld.edit.title"));
        this.f_101247_ = p_101252_;
        this.f_101249_ = p_101253_;
    }

    @Override
    public void m_86600_() {
        this.f_101248_.m_94120_();
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        Button $$0 = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 0 + 5, 200, 20, Component.m_237115_("selectWorld.edit.resetIcon"), p_101297_ -> {
            this.f_101249_.m_182514_().ifPresent(p_182594_ -> FileUtils.deleteQuietly((File)p_182594_.toFile()));
            p_101297_.f_93623_ = false;
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 24 + 5, 200, 20, Component.m_237115_("selectWorld.edit.openFolder"), p_101294_ -> Util.m_137581_().m_137644_(this.f_101249_.m_78283_(LevelResource.f_78182_).toFile())));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 48 + 5, 200, 20, Component.m_237115_("selectWorld.edit.backup"), p_101292_ -> {
            boolean $$1 = EditWorldScreen.m_101258_(this.f_101249_);
            this.f_101247_.accept(!$$1);
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 72 + 5, 200, 20, Component.m_237115_("selectWorld.edit.backupFolder"), p_101290_ -> {
            LevelStorageSource $$1 = this.f_96541_.m_91392_();
            Path $$2 = $$1.m_78262_();
            try {
                Files.createDirectories(Files.exists($$2, new LinkOption[0]) ? $$2.toRealPath(new LinkOption[0]) : $$2, new FileAttribute[0]);
            }
            catch (IOException $$3) {
                throw new RuntimeException($$3);
            }
            Util.m_137581_().m_137644_($$2.toFile());
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 96 + 5, 200, 20, Component.m_237115_("selectWorld.edit.optimize"), p_101287_ -> this.f_96541_.m_91152_(new BackupConfirmScreen(this, (p_170235_, p_170236_) -> {
            if (p_170235_) {
                EditWorldScreen.m_101258_(this.f_101249_);
            }
            this.f_96541_.m_91152_(OptimizeWorldScreen.m_101315_(this.f_96541_, this.f_101247_, this.f_96541_.m_91295_(), this.f_101249_, p_170236_));
        }, Component.m_237115_("optimizeWorld.confirm.title"), Component.m_237115_("optimizeWorld.confirm.description"), true))));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 5, 200, 20, Component.m_237115_("selectWorld.edit.export_worldgen_settings"), p_101284_ -> {
            DataResult $$7;
            try (WorldStem $$1 = this.f_96541_.m_231466_().m_233119_(this.f_101249_, false);){
                RegistryOps $$2 = RegistryOps.m_206821_(JsonOps.INSTANCE, $$1.f_206894_());
                DataResult $$3 = WorldGenSettings.f_64600_.encodeStart($$2, (Object)$$1.f_206895_().m_5961_());
                DataResult $$4 = $$3.flatMap(p_170231_ -> {
                    Path $$1 = this.f_101249_.m_78283_(LevelResource.f_78182_).resolve("worldgen_settings_export.json");
                    try (JsonWriter $$2 = f_101244_.newJsonWriter((Writer)Files.newBufferedWriter($$1, StandardCharsets.UTF_8, new OpenOption[0]));){
                        f_101244_.toJson(p_170231_, $$2);
                    }
                    catch (JsonIOException | IOException $$3) {
                        return DataResult.error((String)("Error writing file: " + $$3.getMessage()));
                    }
                    return DataResult.success((Object)$$1.toString());
                });
            }
            catch (Exception $$6) {
                f_101243_.warn("Could not parse level data", (Throwable)$$6);
                $$7 = DataResult.error((String)("Could not parse level data: " + $$6.getMessage()));
            }
            MutableComponent $$8 = Component.m_237113_((String)$$7.get().map(Function.identity(), DataResult.PartialResult::message));
            MutableComponent $$9 = Component.m_237115_($$7.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure");
            $$7.error().ifPresent(p_170233_ -> f_101243_.error("Error exporting world settings: {}", p_170233_));
            this.f_96541_.m_91300_().m_94922_(SystemToast.m_94847_(this.f_96541_, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, $$9, $$8));
        }));
        this.f_101246_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 144 + 5, 98, 20, Component.m_237115_("selectWorld.edit.save"), p_101280_ -> this.m_101295_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 2, this.f_96544_ / 4 + 144 + 5, 98, 20, CommonComponents.f_130656_, p_101273_ -> this.f_101247_.accept(false)));
        $$0.f_93623_ = this.f_101249_.m_182514_().filter(p_182587_ -> Files.isRegularFile(p_182587_, new LinkOption[0])).isPresent();
        LevelSummary $$1 = this.f_101249_.m_78308_();
        String $$2 = $$1 == null ? "" : $$1.m_78361_();
        this.f_101248_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 38, 200, 20, Component.m_237115_("selectWorld.enterName"));
        this.f_101248_.m_94144_($$2);
        this.f_101248_.m_94151_(p_101282_ -> {
            this.f_101246_.f_93623_ = !p_101282_.trim().isEmpty();
        });
        this.m_7787_(this.f_101248_);
        this.m_94718_(this.f_101248_);
    }

    @Override
    public void m_6574_(Minecraft p_101269_, int p_101270_, int p_101271_) {
        String $$3 = this.f_101248_.m_94155_();
        this.m_6575_(p_101269_, p_101270_, p_101271_);
        this.f_101248_.m_94144_($$3);
    }

    @Override
    public void m_7379_() {
        this.f_101247_.accept(false);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_101295_() {
        try {
            this.f_101249_.m_78297_(this.f_101248_.m_94155_().trim());
            this.f_101247_.accept(true);
        }
        catch (IOException $$0) {
            f_101243_.error("Failed to access world '{}'", (Object)this.f_101249_.m_78277_(), (Object)$$0);
            SystemToast.m_94852_(this.f_96541_, this.f_101249_.m_78277_());
            this.f_101247_.accept(true);
        }
    }

    public static void m_101260_(LevelStorageSource p_101261_, String p_101262_) {
        boolean $$2 = false;
        try (LevelStorageSource.LevelStorageAccess $$3 = p_101261_.m_78260_(p_101262_);){
            $$2 = true;
            EditWorldScreen.m_101258_($$3);
        }
        catch (IOException $$4) {
            if (!$$2) {
                SystemToast.m_94852_(Minecraft.m_91087_(), p_101262_);
            }
            f_101243_.warn("Failed to create backup of level {}", (Object)p_101262_, (Object)$$4);
        }
    }

    public static boolean m_101258_(LevelStorageSource.LevelStorageAccess p_101259_) {
        long $$1 = 0L;
        IOException $$2 = null;
        try {
            $$1 = p_101259_.m_78312_();
        }
        catch (IOException $$3) {
            $$2 = $$3;
        }
        if ($$2 != null) {
            MutableComponent $$4 = Component.m_237115_("selectWorld.edit.backupFailed");
            MutableComponent $$5 = Component.m_237113_($$2.getMessage());
            Minecraft.m_91087_().m_91300_().m_94922_(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, $$4, $$5));
            return false;
        }
        MutableComponent $$6 = Component.m_237110_("selectWorld.edit.backupCreated", p_101259_.m_78277_());
        MutableComponent $$7 = Component.m_237110_("selectWorld.edit.backupSize", Mth.m_14165_((double)$$1 / 1048576.0));
        Minecraft.m_91087_().m_91300_().m_94922_(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, $$6, $$7));
        return true;
    }

    @Override
    public void m_6305_(PoseStack p_101264_, int p_101265_, int p_101266_, float p_101267_) {
        this.m_7333_(p_101264_);
        EditWorldScreen.m_93215_(p_101264_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 0xFFFFFF);
        EditWorldScreen.m_93243_(p_101264_, this.f_96547_, f_101245_, this.f_96543_ / 2 - 100, 24, 0xA0A0A0);
        this.f_101248_.m_6305_(p_101264_, p_101265_, p_101266_, p_101267_);
        super.m_6305_(p_101264_, p_101265_, p_101266_, p_101267_);
    }
}

