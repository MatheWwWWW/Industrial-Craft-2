/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BaseCommandBlock;

public abstract class AbstractCommandBlockEditScreen
extends Screen {
    private static final Component f_97652_ = Component.m_237115_("advMode.setCommand");
    private static final Component f_97653_ = Component.m_237115_("advMode.command");
    private static final Component f_97654_ = Component.m_237115_("advMode.previousOutput");
    protected EditBox f_97646_;
    protected EditBox f_97647_;
    protected Button f_97648_;
    protected Button f_97649_;
    protected CycleButton<Boolean> f_97650_;
    CommandSuggestions f_97655_;

    public AbstractCommandBlockEditScreen() {
        super(GameNarrator.f_93310_);
    }

    @Override
    public void m_86600_() {
        this.f_97646_.m_94120_();
    }

    abstract BaseCommandBlock m_6556_();

    abstract int m_7821_();

    @Override
    protected void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_97648_ = this.m_142416_(new Button(this.f_96543_ / 2 - 4 - 150, this.f_96544_ / 4 + 120 + 12, 150, 20, CommonComponents.f_130655_, p_97691_ -> this.m_97695_()));
        this.f_97649_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 120 + 12, 150, 20, CommonComponents.f_130656_, p_97687_ -> this.m_7379_()));
        boolean $$0 = this.m_6556_().m_45440_();
        this.f_97650_ = this.m_142416_(CycleButton.m_168896_(Component.m_237113_("O"), Component.m_237113_("X")).m_168948_($$0).m_168929_().m_168936_(this.f_96543_ / 2 + 150 - 20, this.m_7821_(), 20, 20, Component.m_237115_("advMode.trackOutput"), (p_169596_, p_169597_) -> {
            BaseCommandBlock $$2 = this.m_6556_();
            $$2.m_45428_((boolean)p_169597_);
            this.m_169598_((boolean)p_169597_);
        }));
        this.f_97646_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 150, 50, 300, 20, (Component)Component.m_237115_("advMode.command")){

            @Override
            protected MutableComponent m_5646_() {
                return super.m_5646_().m_130946_(AbstractCommandBlockEditScreen.this.f_97655_.m_93924_());
            }
        };
        this.f_97646_.m_94199_(32500);
        this.f_97646_.m_94151_(this::m_97688_);
        this.m_7787_(this.f_97646_);
        this.f_97647_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 150, this.m_7821_(), 276, 20, Component.m_237115_("advMode.previousOutput"));
        this.f_97647_.m_94199_(32500);
        this.f_97647_.m_94186_(false);
        this.f_97647_.m_94144_("-");
        this.m_7787_(this.f_97647_);
        this.m_94718_(this.f_97646_);
        this.f_97646_.m_94178_(true);
        this.f_97655_ = new CommandSuggestions(this.f_96541_, this, this.f_97646_, this.f_96547_, true, true, 0, 7, false, Integer.MIN_VALUE);
        this.f_97655_.m_93922_(true);
        this.f_97655_.m_93881_();
        this.m_169598_($$0);
    }

    @Override
    public void m_6574_(Minecraft p_97677_, int p_97678_, int p_97679_) {
        String $$3 = this.f_97646_.m_94155_();
        this.m_6575_(p_97677_, p_97678_, p_97679_);
        this.f_97646_.m_94144_($$3);
        this.f_97655_.m_93881_();
    }

    protected void m_169598_(boolean p_169599_) {
        this.f_97647_.m_94144_(p_169599_ ? this.m_6556_().m_45437_().getString() : "-");
    }

    protected void m_97695_() {
        BaseCommandBlock $$0 = this.m_6556_();
        this.m_6372_($$0);
        if (!$$0.m_45440_()) {
            $$0.m_45433_(null);
        }
        this.f_96541_.m_91152_(null);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    protected abstract void m_6372_(BaseCommandBlock var1);

    private void m_97688_(String p_97689_) {
        this.f_97655_.m_93881_();
    }

    @Override
    public boolean m_7933_(int p_97667_, int p_97668_, int p_97669_) {
        if (this.f_97655_.m_93888_(p_97667_, p_97668_, p_97669_)) {
            return true;
        }
        if (super.m_7933_(p_97667_, p_97668_, p_97669_)) {
            return true;
        }
        if (p_97667_ == 257 || p_97667_ == 335) {
            this.m_97695_();
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6050_(double p_97659_, double p_97660_, double p_97661_) {
        if (this.f_97655_.m_93882_(p_97661_)) {
            return true;
        }
        return super.m_6050_(p_97659_, p_97660_, p_97661_);
    }

    @Override
    public boolean m_6375_(double p_97663_, double p_97664_, int p_97665_) {
        if (this.f_97655_.m_93884_(p_97663_, p_97664_, p_97665_)) {
            return true;
        }
        return super.m_6375_(p_97663_, p_97664_, p_97665_);
    }

    @Override
    public void m_6305_(PoseStack p_97672_, int p_97673_, int p_97674_, float p_97675_) {
        this.m_7333_(p_97672_);
        AbstractCommandBlockEditScreen.m_93215_(p_97672_, this.f_96547_, f_97652_, this.f_96543_ / 2, 20, 0xFFFFFF);
        AbstractCommandBlockEditScreen.m_93243_(p_97672_, this.f_96547_, f_97653_, this.f_96543_ / 2 - 150, 40, 0xA0A0A0);
        this.f_97646_.m_6305_(p_97672_, p_97673_, p_97674_, p_97675_);
        int $$4 = 75;
        if (!this.f_97647_.m_94155_().isEmpty()) {
            AbstractCommandBlockEditScreen.m_93243_(p_97672_, this.f_96547_, f_97654_, this.f_96543_ / 2 - 150, ($$4 += 5 * this.f_96547_.f_92710_ + 1 + this.m_7821_() - 135) + 4, 0xA0A0A0);
            this.f_97647_.m_6305_(p_97672_, p_97673_, p_97674_, p_97675_);
        }
        super.m_6305_(p_97672_, p_97673_, p_97674_, p_97675_);
        this.f_97655_.m_93900_(p_97672_, p_97673_, p_97674_);
    }
}

