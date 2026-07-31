/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.hash.Hashing
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.LoadingDotsText;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.WorldStem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class WorldSelectionList
extends ObjectSelectionList<Entry> {
    static final Logger f_101645_ = LogUtils.getLogger();
    static final DateFormat f_101646_ = new SimpleDateFormat();
    static final ResourceLocation f_101647_ = new ResourceLocation("textures/misc/unknown_server.png");
    static final ResourceLocation f_101648_ = new ResourceLocation("textures/gui/world_selection.png");
    static final Component f_101649_ = Component.m_237115_("selectWorld.tooltip.fromNewerVersion1").m_130940_(ChatFormatting.RED);
    static final Component f_101650_ = Component.m_237115_("selectWorld.tooltip.fromNewerVersion2").m_130940_(ChatFormatting.RED);
    static final Component f_101651_ = Component.m_237115_("selectWorld.tooltip.snapshot1").m_130940_(ChatFormatting.GOLD);
    static final Component f_101652_ = Component.m_237115_("selectWorld.tooltip.snapshot2").m_130940_(ChatFormatting.GOLD);
    static final Component f_101653_ = Component.m_237115_("selectWorld.locked").m_130940_(ChatFormatting.RED);
    static final Component f_194113_ = Component.m_237115_("selectWorld.conversion.tooltip").m_130940_(ChatFormatting.RED);
    private final SelectWorldScreen f_101654_;
    private CompletableFuture<List<LevelSummary>> f_238666_;
    @Nullable
    private List<LevelSummary> f_238575_;
    private String f_238624_;
    private final LoadingHeader f_233184_;

    public WorldSelectionList(SelectWorldScreen p_239540_, Minecraft p_239541_, int p_239542_, int p_239543_, int p_239544_, int p_239545_, int p_239546_, String p_239547_, @Nullable WorldSelectionList p_239548_) {
        super(p_239541_, p_239542_, p_239543_, p_239544_, p_239545_, p_239546_);
        this.f_101654_ = p_239540_;
        this.f_233184_ = new LoadingHeader(p_239541_);
        this.f_238624_ = p_239547_;
        this.f_238666_ = p_239548_ != null ? p_239548_.f_238666_ : this.m_233213_();
        this.m_239664_(this.m_239987_());
    }

    @Nullable
    private List<LevelSummary> m_239987_() {
        try {
            return this.f_238666_.getNow(null);
        }
        catch (CancellationException | CompletionException $$0) {
            return null;
        }
    }

    void m_233206_() {
        this.f_238666_ = this.m_233213_();
    }

    @Override
    public void m_6305_(PoseStack p_239123_, int p_239124_, int p_239125_, float p_239126_) {
        List<LevelSummary> $$4 = this.m_239987_();
        if ($$4 != this.f_238575_) {
            this.m_239664_($$4);
        }
        super.m_6305_(p_239123_, p_239124_, p_239125_, p_239126_);
    }

    private void m_239664_(@Nullable List<LevelSummary> p_239665_) {
        if (p_239665_ == null) {
            this.m_233214_();
        } else {
            this.m_233198_(this.f_238624_, p_239665_);
        }
        this.f_238575_ = p_239665_;
    }

    public void m_239900_(String p_239901_) {
        if (this.f_238575_ != null && !p_239901_.equals(this.f_238624_)) {
            this.m_233198_(p_239901_, this.f_238575_);
        }
        this.f_238624_ = p_239901_;
    }

    /*
     * WARNING - void declaration
     */
    private CompletableFuture<List<LevelSummary>> m_233213_() {
        void $$2;
        try {
            LevelStorageSource.LevelCandidates $$0 = this.f_93386_.m_91392_().m_230833_();
        }
        catch (LevelStorageException $$1) {
            f_101645_.error("Couldn't load level list", (Throwable)$$1);
            this.m_233211_($$1.m_230806_());
            return CompletableFuture.completedFuture(List.of());
        }
        if ($$2.m_230843_()) {
            CreateWorldScreen.m_232896_(this.f_93386_, null);
            return CompletableFuture.completedFuture(List.of());
        }
        return this.f_93386_.m_91392_().m_230813_((LevelStorageSource.LevelCandidates)$$2).exceptionally(p_233202_ -> {
            this.f_93386_.m_231412_(CrashReport.m_127521_(p_233202_, "Couldn't load level list"));
            return List.of();
        });
    }

    private void m_233198_(String p_233199_, List<LevelSummary> p_233200_) {
        this.m_93516_();
        p_233199_ = p_233199_.toLowerCase(Locale.ROOT);
        for (LevelSummary $$2 : p_233200_) {
            if (!this.m_233195_(p_233199_, $$2)) continue;
            this.m_7085_(new WorldListEntry(this, $$2));
        }
        this.m_233215_();
    }

    private boolean m_233195_(String p_233196_, LevelSummary p_233197_) {
        return p_233197_.m_78361_().toLowerCase(Locale.ROOT).contains(p_233196_) || p_233197_.m_78358_().toLowerCase(Locale.ROOT).contains(p_233196_);
    }

    private void m_233214_() {
        this.m_93516_();
        this.m_7085_(this.f_233184_);
        this.m_233215_();
    }

    private void m_233215_() {
        this.f_101654_.m_169407_(true);
    }

    private void m_233211_(Component p_233212_) {
        this.f_93386_.m_91152_(new ErrorScreen(Component.m_237115_("selectWorld.unable_to_load"), p_233212_));
    }

    @Override
    protected int m_5756_() {
        return super.m_5756_() + 20;
    }

    @Override
    public int m_5759_() {
        return super.m_5759_() + 50;
    }

    @Override
    protected boolean m_5694_() {
        return this.f_101654_.m_7222_() == this;
    }

    @Override
    public void m_6987_(@Nullable Entry p_233190_) {
        super.m_6987_(p_233190_);
        this.f_101654_.m_101369_(p_233190_ != null && p_233190_.m_214209_());
    }

    @Override
    protected void m_6778_(AbstractSelectionList.SelectionDirection p_101673_) {
        this.m_93464_(p_101673_, Entry::m_214209_);
    }

    public Optional<WorldListEntry> m_101684_() {
        Entry $$0 = (Entry)this.m_93511_();
        if ($$0 instanceof WorldListEntry) {
            WorldListEntry $$1 = (WorldListEntry)$$0;
            return Optional.of($$1);
        }
        return Optional.empty();
    }

    public SelectWorldScreen m_101685_() {
        return this.f_101654_;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_233188_) {
        if (this.m_6702_().contains(this.f_233184_)) {
            this.f_233184_.m_142291_(p_233188_);
            return;
        }
        super.m_142291_(p_233188_);
    }

    public static class LoadingHeader
    extends Entry {
        private static final Component f_233218_ = Component.m_237115_("selectWorld.loading_list");
        private final Minecraft f_233219_;

        public LoadingHeader(Minecraft p_233222_) {
            this.f_233219_ = p_233222_;
        }

        @Override
        public void m_6311_(PoseStack p_233225_, int p_233226_, int p_233227_, int p_233228_, int p_233229_, int p_233230_, int p_233231_, int p_233232_, boolean p_233233_, float p_233234_) {
            int $$10 = (this.f_233219_.f_91080_.f_96543_ - this.f_233219_.f_91062_.m_92852_(f_233218_)) / 2;
            int $$11 = p_233227_ + (p_233230_ - this.f_233219_.f_91062_.f_92710_) / 2;
            this.f_233219_.f_91062_.m_92889_(p_233225_, f_233218_, $$10, $$11, 0xFFFFFF);
            String $$12 = LoadingDotsText.m_232744_(Util.m_137550_());
            int $$13 = (this.f_233219_.f_91080_.f_96543_ - this.f_233219_.f_91062_.m_92895_($$12)) / 2;
            int $$14 = $$11 + this.f_233219_.f_91062_.f_92710_;
            this.f_233219_.f_91062_.m_92883_(p_233225_, $$12, $$13, $$14, 0x808080);
        }

        @Override
        public Component m_142172_() {
            return f_233218_;
        }

        @Override
        public boolean m_214209_() {
            return false;
        }
    }

    public final class WorldListEntry
    extends Entry
    implements AutoCloseable {
        private static final int f_170312_ = 32;
        private static final int f_170313_ = 32;
        private static final int f_170314_ = 0;
        private static final int f_170315_ = 32;
        private static final int f_170316_ = 64;
        private static final int f_170317_ = 96;
        private static final int f_170318_ = 0;
        private static final int f_170319_ = 32;
        private final Minecraft f_101693_;
        private final SelectWorldScreen f_101694_;
        private final LevelSummary f_101695_;
        private final ResourceLocation f_101696_;
        @Nullable
        private Path f_101697_;
        @Nullable
        private final DynamicTexture f_101698_;
        private long f_101699_;

        public WorldListEntry(WorldSelectionList p_101702_, LevelSummary p_101703_) {
            this.f_101693_ = p_101702_.f_93386_;
            this.f_101694_ = p_101702_.m_101685_();
            this.f_101695_ = p_101703_;
            String $$3 = p_101703_.m_78358_();
            this.f_101696_ = new ResourceLocation("minecraft", "worlds/" + Util.m_137483_($$3, ResourceLocation::m_135828_) + "/" + Hashing.sha1().hashUnencodedChars((CharSequence)$$3) + "/icon");
            this.f_101697_ = p_101703_.m_230875_();
            if (!Files.isRegularFile(this.f_101697_, new LinkOption[0])) {
                this.f_101697_ = null;
            }
            this.f_101698_ = this.m_101746_();
        }

        @Override
        public Component m_142172_() {
            MutableComponent $$2;
            MutableComponent $$0 = Component.m_237110_("narrator.select.world", this.f_101695_.m_78361_(), new Date(this.f_101695_.m_78366_()), this.f_101695_.m_78368_() ? Component.m_237115_("gameMode.hardcore") : Component.m_237115_("gameMode." + this.f_101695_.m_78367_().m_46405_()), this.f_101695_.m_78369_() ? Component.m_237115_("selectWorld.cheats") : CommonComponents.f_237098_, this.f_101695_.m_78370_());
            if (this.f_101695_.m_78375_()) {
                MutableComponent $$1 = CommonComponents.m_178398_($$0, f_101653_);
            } else {
                $$2 = $$0;
            }
            return Component.m_237110_("narrator.select", $$2);
        }

        @Override
        public void m_6311_(PoseStack p_101721_, int p_101722_, int p_101723_, int p_101724_, int p_101725_, int p_101726_, int p_101727_, int p_101728_, boolean p_101729_, float p_101730_) {
            Object $$10 = this.f_101695_.m_78361_();
            String $$11 = this.f_101695_.m_78358_() + " (" + f_101646_.format(new Date(this.f_101695_.m_78366_())) + ")";
            if (StringUtils.isEmpty((CharSequence)$$10)) {
                $$10 = I18n.m_118938_("selectWorld.world", new Object[0]) + " " + (p_101722_ + 1);
            }
            Component $$12 = this.f_101695_.m_78376_();
            this.f_101693_.f_91062_.m_92883_(p_101721_, (String)$$10, p_101724_ + 32 + 3, p_101723_ + 1, 0xFFFFFF);
            this.f_101693_.f_91062_.m_92883_(p_101721_, $$11, p_101724_ + 32 + 3, p_101723_ + this.f_101693_.f_91062_.f_92710_ + 3, 0x808080);
            this.f_101693_.f_91062_.m_92889_(p_101721_, $$12, p_101724_ + 32 + 3, p_101723_ + this.f_101693_.f_91062_.f_92710_ + this.f_101693_.f_91062_.f_92710_ + 3, 0x808080);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_157456_(0, this.f_101698_ != null ? this.f_101696_ : f_101647_);
            RenderSystem.m_69478_();
            GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.m_69461_();
            if (this.f_101693_.f_91066_.m_231828_().m_231551_().booleanValue() || p_101729_) {
                int $$15;
                RenderSystem.m_157456_(0, f_101648_);
                GuiComponent.m_93172_(p_101721_, p_101724_, p_101723_, p_101724_ + 32, p_101723_ + 32, -1601138544);
                RenderSystem.m_157427_(GameRenderer::m_172817_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                int $$13 = p_101727_ - p_101724_;
                boolean $$14 = $$13 < 32;
                int n = $$15 = $$14 ? 32 : 0;
                if (this.f_101695_.m_78375_()) {
                    GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 96.0f, $$15, 32, 32, 256, 256);
                    if ($$14) {
                        this.f_101694_.m_101363_(this.f_101693_.f_91062_.m_92923_(f_101653_, 175));
                    }
                } else if (this.f_101695_.m_193020_()) {
                    GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 96.0f, $$15, 32, 32, 256, 256);
                    if ($$14) {
                        this.f_101694_.m_101363_(this.f_101693_.f_91062_.m_92923_(f_194113_, 175));
                    }
                } else if (this.f_101695_.m_78372_()) {
                    GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 32.0f, $$15, 32, 32, 256, 256);
                    if (this.f_101695_.m_78373_()) {
                        GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 96.0f, $$15, 32, 32, 256, 256);
                        if ($$14) {
                            this.f_101694_.m_101363_((List<FormattedCharSequence>)ImmutableList.of((Object)f_101649_.m_7532_(), (Object)f_101650_.m_7532_()));
                        }
                    } else if (!SharedConstants.m_183709_().isStable()) {
                        GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 64.0f, $$15, 32, 32, 256, 256);
                        if ($$14) {
                            this.f_101694_.m_101363_((List<FormattedCharSequence>)ImmutableList.of((Object)f_101651_.m_7532_(), (Object)f_101652_.m_7532_()));
                        }
                    }
                } else {
                    GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 0.0f, $$15, 32, 32, 256, 256);
                }
            }
        }

        @Override
        public boolean m_6375_(double p_101706_, double p_101707_, int p_101708_) {
            if (this.f_101695_.m_164916_()) {
                return true;
            }
            WorldSelectionList.this.m_6987_(this);
            this.f_101694_.m_101369_(WorldSelectionList.this.m_101684_().isPresent());
            if (p_101706_ - (double)WorldSelectionList.this.m_5747_() <= 32.0) {
                this.m_101704_();
                return true;
            }
            if (Util.m_137550_() - this.f_101699_ < 250L) {
                this.m_101704_();
                return true;
            }
            this.f_101699_ = Util.m_137550_();
            return false;
        }

        public void m_101704_() {
            if (this.f_101695_.m_164916_()) {
                return;
            }
            LevelSummary.BackupStatus $$0 = this.f_101695_.m_164914_();
            if ($$0.m_164931_()) {
                String $$1 = "selectWorld.backupQuestion." + $$0.m_164933_();
                String $$2 = "selectWorld.backupWarning." + $$0.m_164933_();
                MutableComponent $$3 = Component.m_237115_($$1);
                if ($$0.m_164932_()) {
                    $$3.m_130944_(ChatFormatting.BOLD, ChatFormatting.RED);
                }
                MutableComponent $$4 = Component.m_237110_($$2, this.f_101695_.m_78370_(), SharedConstants.m_183709_().getName());
                this.f_101693_.m_91152_(new BackupConfirmScreen(this.f_101694_, (p_101736_, p_101737_) -> {
                    if (p_101736_) {
                        String $$2 = this.f_101695_.m_78358_();
                        try (LevelStorageSource.LevelStorageAccess $$3 = this.f_101693_.m_91392_().m_78260_($$2);){
                            EditWorldScreen.m_101258_($$3);
                        }
                        catch (IOException $$4) {
                            SystemToast.m_94852_(this.f_101693_, $$2);
                            f_101645_.error("Failed to backup level {}", (Object)$$2, (Object)$$4);
                        }
                    }
                    this.m_101744_();
                }, $$3, $$4, false));
            } else if (this.f_101695_.m_78373_()) {
                this.f_101693_.m_91152_(new ConfirmScreen(p_101741_ -> {
                    if (p_101741_) {
                        try {
                            this.m_101744_();
                        }
                        catch (Exception $$1) {
                            f_101645_.error("Failure to open 'future world'", (Throwable)$$1);
                            this.f_101693_.m_91152_(new AlertScreen(() -> this.f_101693_.m_91152_(this.f_101694_), Component.m_237115_("selectWorld.futureworld.error.title"), Component.m_237115_("selectWorld.futureworld.error.text")));
                        }
                    } else {
                        this.f_101693_.m_91152_(this.f_101694_);
                    }
                }, Component.m_237115_("selectWorld.versionQuestion"), Component.m_237110_("selectWorld.versionWarning", this.f_101695_.m_78370_()), Component.m_237115_("selectWorld.versionJoinButton"), CommonComponents.f_130656_));
            } else {
                this.m_101744_();
            }
        }

        public void m_101738_() {
            this.f_101693_.m_91152_(new ConfirmScreen(p_170322_ -> {
                if (p_170322_) {
                    this.f_101693_.m_91152_(new ProgressScreen(true));
                    this.m_170323_();
                }
                this.f_101693_.m_91152_(this.f_101694_);
            }, Component.m_237115_("selectWorld.deleteQuestion"), Component.m_237110_("selectWorld.deleteWarning", this.f_101695_.m_78361_()), Component.m_237115_("selectWorld.deleteButton"), CommonComponents.f_130656_));
        }

        public void m_170323_() {
            LevelStorageSource $$0 = this.f_101693_.m_91392_();
            String $$1 = this.f_101695_.m_78358_();
            try (LevelStorageSource.LevelStorageAccess $$2 = $$0.m_78260_($$1);){
                $$2.m_78311_();
            }
            catch (IOException $$3) {
                SystemToast.m_94866_(this.f_101693_, $$1);
                f_101645_.error("Failed to delete world {}", (Object)$$1, (Object)$$3);
            }
            WorldSelectionList.this.m_233206_();
        }

        public void m_101739_() {
            this.m_101745_();
            String $$0 = this.f_101695_.m_78358_();
            try {
                LevelStorageSource.LevelStorageAccess $$1 = this.f_101693_.m_91392_().m_78260_($$0);
                this.f_101693_.m_91152_(new EditWorldScreen(p_233244_ -> {
                    try {
                        $$1.close();
                    }
                    catch (IOException $$3) {
                        f_101645_.error("Failed to unlock level {}", (Object)$$0, (Object)$$3);
                    }
                    if (p_233244_) {
                        WorldSelectionList.this.m_233206_();
                    }
                    this.f_101693_.m_91152_(this.f_101694_);
                }, $$1));
            }
            catch (IOException $$2) {
                SystemToast.m_94852_(this.f_101693_, $$0);
                f_101645_.error("Failed to access level {}", (Object)$$0, (Object)$$2);
                WorldSelectionList.this.m_233206_();
            }
        }

        public void m_101743_() {
            this.m_101745_();
            try (LevelStorageSource.LevelStorageAccess $$0 = this.f_101693_.m_91392_().m_78260_(this.f_101695_.m_78358_());
                 WorldStem $$1 = this.f_101693_.m_231466_().m_233119_($$0, false);){
                WorldGenSettings $$2 = $$1.f_206895_().m_5961_();
                Path $$3 = CreateWorldScreen.m_100906_($$0.m_78283_(LevelResource.f_78180_), this.f_101693_);
                if ($$2.m_64670_()) {
                    this.f_101693_.m_91152_(new ConfirmScreen(p_233240_ -> this.f_101693_.m_91152_(p_233240_ ? CreateWorldScreen.m_205426_(this.f_101694_, $$1, $$3) : this.f_101694_), Component.m_237115_("selectWorld.recreate.customized.title"), Component.m_237115_("selectWorld.recreate.customized.text"), CommonComponents.f_130659_, CommonComponents.f_130656_));
                } else {
                    this.f_101693_.m_91152_(CreateWorldScreen.m_205426_(this.f_101694_, $$1, $$3));
                }
            }
            catch (Exception $$4) {
                f_101645_.error("Unable to recreate world", (Throwable)$$4);
                this.f_101693_.m_91152_(new AlertScreen(() -> this.f_101693_.m_91152_(this.f_101694_), Component.m_237115_("selectWorld.recreate.error.title"), Component.m_237115_("selectWorld.recreate.error.text")));
            }
        }

        private void m_101744_() {
            this.f_101693_.m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0f));
            if (this.f_101693_.m_91392_().m_78255_(this.f_101695_.m_78358_())) {
                this.m_101745_();
                this.f_101693_.m_231466_().m_233133_(this.f_101694_, this.f_101695_.m_78358_());
            }
        }

        private void m_101745_() {
            this.f_101693_.m_91346_(new GenericDirtMessageScreen(Component.m_237115_("selectWorld.data_read")));
        }

        @Nullable
        private DynamicTexture m_101746_() {
            boolean $$0;
            boolean bl = $$0 = this.f_101697_ != null && Files.isRegularFile(this.f_101697_, new LinkOption[0]);
            if ($$0) {
                DynamicTexture dynamicTexture;
                block9: {
                    InputStream $$1 = Files.newInputStream(this.f_101697_, new OpenOption[0]);
                    try {
                        NativeImage $$2 = NativeImage.m_85058_($$1);
                        Validate.validState(($$2.m_84982_() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                        Validate.validState(($$2.m_85084_() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels high", (Object[])new Object[0]);
                        DynamicTexture $$3 = new DynamicTexture($$2);
                        this.f_101693_.m_91097_().m_118495_(this.f_101696_, $$3);
                        dynamicTexture = $$3;
                        if ($$1 == null) break block9;
                    }
                    catch (Throwable throwable) {
                        try {
                            if ($$1 != null) {
                                try {
                                    $$1.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        catch (Throwable $$4) {
                            f_101645_.error("Invalid icon for world {}", (Object)this.f_101695_.m_78358_(), (Object)$$4);
                            this.f_101697_ = null;
                            return null;
                        }
                    }
                    $$1.close();
                }
                return dynamicTexture;
            }
            this.f_101693_.m_91097_().m_118513_(this.f_101696_);
            return null;
        }

        @Override
        public void close() {
            if (this.f_101698_ != null) {
                this.f_101698_.close();
            }
        }

        public String m_170324_() {
            return this.f_101695_.m_78361_();
        }

        @Override
        public boolean m_214209_() {
            return !this.f_101695_.m_164916_();
        }
    }

    public static abstract class Entry
    extends ObjectSelectionList.Entry<Entry>
    implements AutoCloseable {
        public abstract boolean m_214209_();

        @Override
        public void close() {
        }
    }
}

