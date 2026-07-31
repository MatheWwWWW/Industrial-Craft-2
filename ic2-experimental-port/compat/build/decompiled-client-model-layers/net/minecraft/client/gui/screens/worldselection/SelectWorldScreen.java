/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.worldselection;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import org.slf4j.Logger;

public class SelectWorldScreen
extends Screen {
    private static final Logger f_170237_ = LogUtils.getLogger();
    protected final Screen f_101329_;
    @Nullable
    private List<FormattedCharSequence> f_101331_;
    private Button f_101332_;
    private Button f_101333_;
    private Button f_101334_;
    private Button f_101335_;
    protected EditBox f_101330_;
    private WorldSelectionList f_101336_;

    public SelectWorldScreen(Screen p_101338_) {
        super(Component.m_237115_("selectWorld.title"));
        this.f_101329_ = p_101338_;
    }

    @Override
    public boolean m_6050_(double p_101343_, double p_101344_, double p_101345_) {
        return super.m_6050_(p_101343_, p_101344_, p_101345_);
    }

    @Override
    public void m_86600_() {
        this.f_101330_.m_94120_();
    }

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_101330_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 22, 200, 20, this.f_101330_, Component.m_237115_("selectWorld.search"));
        this.f_101330_.m_94151_(p_232980_ -> this.f_101336_.m_239900_((String)p_232980_));
        this.f_101336_ = new WorldSelectionList(this, this.f_96541_, this.f_96543_, this.f_96544_, 48, this.f_96544_ - 64, 36, this.f_101330_.m_94155_(), this.f_101336_);
        this.m_7787_(this.f_101330_);
        this.m_7787_(this.f_101336_);
        this.f_101333_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 52, 150, 20, Component.m_237115_("selectWorld.select"), p_232984_ -> this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101704_)));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 52, 150, 20, Component.m_237115_("selectWorld.create"), p_232982_ -> CreateWorldScreen.m_232896_(this.f_96541_, this)));
        this.f_101334_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 28, 72, 20, Component.m_237115_("selectWorld.edit"), p_101378_ -> this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101739_)));
        this.f_101332_ = this.m_142416_(new Button(this.f_96543_ / 2 - 76, this.f_96544_ - 28, 72, 20, Component.m_237115_("selectWorld.delete"), p_101376_ -> this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101738_)));
        this.f_101335_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 28, 72, 20, Component.m_237115_("selectWorld.recreate"), p_101373_ -> this.f_101336_.m_101684_().ifPresent(WorldSelectionList.WorldListEntry::m_101743_)));
        this.m_142416_(new Button(this.f_96543_ / 2 + 82, this.f_96544_ - 28, 72, 20, CommonComponents.f_130656_, p_101366_ -> this.f_96541_.m_91152_(this.f_101329_)));
        this.m_101369_(false);
        this.m_94718_(this.f_101330_);
    }

    @Override
    public boolean m_7933_(int p_101347_, int p_101348_, int p_101349_) {
        if (super.m_7933_(p_101347_, p_101348_, p_101349_)) {
            return true;
        }
        return this.f_101330_.m_7933_(p_101347_, p_101348_, p_101349_);
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_101329_);
    }

    @Override
    public boolean m_5534_(char p_101340_, int p_101341_) {
        return this.f_101330_.m_5534_(p_101340_, p_101341_);
    }

    @Override
    public void m_6305_(PoseStack p_101351_, int p_101352_, int p_101353_, float p_101354_) {
        this.f_101331_ = null;
        this.f_101336_.m_6305_(p_101351_, p_101352_, p_101353_, p_101354_);
        this.f_101330_.m_6305_(p_101351_, p_101352_, p_101353_, p_101354_);
        SelectWorldScreen.m_93215_(p_101351_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 0xFFFFFF);
        super.m_6305_(p_101351_, p_101352_, p_101353_, p_101354_);
        if (this.f_101331_ != null) {
            this.m_96617_(p_101351_, this.f_101331_, p_101352_, p_101353_);
        }
    }

    public void m_101363_(List<FormattedCharSequence> p_101364_) {
        this.f_101331_ = p_101364_;
    }

    public void m_101369_(boolean p_101370_) {
        this.f_101333_.f_93623_ = p_101370_;
        this.f_101332_.f_93623_ = p_101370_;
        this.f_101334_.f_93623_ = p_101370_;
        this.f_101335_.f_93623_ = p_101370_;
    }

    @Override
    public void m_7861_() {
        if (this.f_101336_ != null) {
            this.f_101336_.m_6702_().forEach(WorldSelectionList.Entry::close);
        }
    }

    private /* synthetic */ void m_101359_(Button p_101360_) {
        try {
            WorldSelectionList.WorldListEntry $$3;
            WorldSelectionList.Entry $$2;
            String $$1 = "DEBUG world";
            if (!this.f_101336_.m_6702_().isEmpty() && ($$2 = (WorldSelectionList.Entry)this.f_101336_.m_6702_().get(0)) instanceof WorldSelectionList.WorldListEntry && ($$3 = (WorldSelectionList.WorldListEntry)$$2).m_170324_().equals("DEBUG world")) {
                $$3.m_170323_();
            }
            RegistryAccess.Frozen $$4 = RegistryAccess.m_206197_().m_203557_();
            WorldGenSettings $$5 = WorldPresets.m_226451_($$4, "test1".hashCode());
            LevelSettings $$6 = new LevelSettings("DEBUG world", GameType.SPECTATOR, false, Difficulty.NORMAL, true, new GameRules(), DataPackConfig.f_45842_);
            String $$7 = FileUtil.m_133730_(this.f_96541_.m_91392_().m_78257_(), "DEBUG world", "");
            this.f_96541_.m_231466_().m_233157_($$7, $$6, $$4, $$5);
        }
        catch (IOException $$8) {
            f_170237_.error("Failed to recreate the debug world", (Throwable)$$8);
        }
    }
}

