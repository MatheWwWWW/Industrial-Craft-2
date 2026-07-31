/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsLongConfirmationScreen
extends RealmsScreen {
    private final Type f_88726_;
    private final Component f_88727_;
    private final Component f_88728_;
    protected final BooleanConsumer f_88725_;
    private final boolean f_88729_;

    public RealmsLongConfirmationScreen(BooleanConsumer p_88731_, Type p_88732_, Component p_88733_, Component p_88734_, boolean p_88735_) {
        super(GameNarrator.f_93310_);
        this.f_88725_ = p_88731_;
        this.f_88726_ = p_88732_;
        this.f_88727_ = p_88733_;
        this.f_88728_ = p_88734_;
        this.f_88729_ = p_88735_;
    }

    @Override
    public void m_7856_() {
        if (this.f_88729_) {
            this.m_142416_(new Button(this.f_96543_ / 2 - 105, RealmsLongConfirmationScreen.m_120774_(8), 100, 20, CommonComponents.f_130657_, p_88751_ -> this.f_88725_.accept(true)));
            this.m_142416_(new Button(this.f_96543_ / 2 + 5, RealmsLongConfirmationScreen.m_120774_(8), 100, 20, CommonComponents.f_130658_, p_88749_ -> this.f_88725_.accept(false)));
        } else {
            this.m_142416_(new Button(this.f_96543_ / 2 - 50, RealmsLongConfirmationScreen.m_120774_(8), 100, 20, Component.m_237115_("mco.gui.ok"), p_88746_ -> this.f_88725_.accept(true)));
        }
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178396_(this.f_88726_.f_88755_, this.f_88727_, this.f_88728_);
    }

    @Override
    public boolean m_7933_(int p_88737_, int p_88738_, int p_88739_) {
        if (p_88737_ == 256) {
            this.f_88725_.accept(false);
            return true;
        }
        return super.m_7933_(p_88737_, p_88738_, p_88739_);
    }

    @Override
    public void m_6305_(PoseStack p_88741_, int p_88742_, int p_88743_, float p_88744_) {
        this.m_7333_(p_88741_);
        RealmsLongConfirmationScreen.m_93215_(p_88741_, this.f_96547_, this.f_88726_.f_88755_, this.f_96543_ / 2, RealmsLongConfirmationScreen.m_120774_(2), this.f_88726_.f_88754_);
        RealmsLongConfirmationScreen.m_93215_(p_88741_, this.f_96547_, this.f_88727_, this.f_96543_ / 2, RealmsLongConfirmationScreen.m_120774_(4), 0xFFFFFF);
        RealmsLongConfirmationScreen.m_93215_(p_88741_, this.f_96547_, this.f_88728_, this.f_96543_ / 2, RealmsLongConfirmationScreen.m_120774_(6), 0xFFFFFF);
        super.m_6305_(p_88741_, p_88742_, p_88743_, p_88744_);
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type Warning = new Type("Warning!", 0xFF0000);
        public static final /* enum */ Type Info = new Type("Info!", 8226750);
        public final int f_88754_;
        public final Component f_88755_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_88764_) {
            return Enum.valueOf(Type.class, p_88764_);
        }

        private Type(String p_88761_, int p_88762_) {
            this.f_88755_ = Component.m_237113_(p_88761_);
            this.f_88754_ = p_88762_;
        }

        private static /* synthetic */ Type[] m_167412_() {
            return new Type[]{Warning, Info};
        }

        static {
            $VALUES = Type.m_167412_();
        }
    }
}

