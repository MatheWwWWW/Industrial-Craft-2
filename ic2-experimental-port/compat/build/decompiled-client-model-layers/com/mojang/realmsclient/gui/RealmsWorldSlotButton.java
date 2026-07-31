/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class RealmsWorldSlotButton
extends Button {
    public static final ResourceLocation f_87917_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
    public static final ResourceLocation f_87918_ = new ResourceLocation("realms", "textures/gui/realms/empty_frame.png");
    public static final ResourceLocation f_231297_ = new ResourceLocation("realms", "textures/gui/realms/checkmark.png");
    public static final ResourceLocation f_87919_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_0.png");
    public static final ResourceLocation f_87920_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_2.png");
    public static final ResourceLocation f_87921_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_3.png");
    private static final Component f_87922_ = Component.m_237115_("mco.configure.world.slot.tooltip.active");
    private static final Component f_87923_ = Component.m_237115_("mco.configure.world.slot.tooltip.minigame");
    private static final Component f_87924_ = Component.m_237115_("mco.configure.world.slot.tooltip");
    private final Supplier<RealmsServer> f_87925_;
    private final Consumer<Component> f_87926_;
    private final int f_87914_;
    @Nullable
    private State f_87916_;

    public RealmsWorldSlotButton(int p_87929_, int p_87930_, int p_87931_, int p_87932_, Supplier<RealmsServer> p_87933_, Consumer<Component> p_87934_, int p_87935_, Button.OnPress p_87936_) {
        super(p_87929_, p_87930_, p_87931_, p_87932_, CommonComponents.f_237098_, p_87936_);
        this.f_87925_ = p_87933_;
        this.f_87914_ = p_87935_;
        this.f_87926_ = p_87934_;
    }

    @Nullable
    public State m_87937_() {
        return this.f_87916_;
    }

    public void m_87968_() {
        boolean $$12;
        String $$11;
        long $$10;
        String $$9;
        boolean $$8;
        boolean $$2;
        RealmsServer $$0 = this.f_87925_.get();
        if ($$0 == null) {
            return;
        }
        RealmsWorldOptions $$1 = $$0.f_87481_.get(this.f_87914_);
        boolean bl = $$2 = this.f_87914_ == 4;
        if ($$2) {
            boolean $$3 = $$0.f_87485_ == RealmsServer.WorldType.MINIGAME;
            String $$4 = "Minigame";
            long $$5 = $$0.f_87488_;
            String $$6 = $$0.f_87489_;
            boolean $$7 = $$0.f_87488_ == -1;
        } else {
            $$8 = $$0.f_87486_ == this.f_87914_ && $$0.f_87485_ != RealmsServer.WorldType.MINIGAME;
            $$9 = $$1.m_87626_(this.f_87914_);
            $$10 = $$1.f_87608_;
            $$11 = $$1.f_87609_;
            $$12 = $$1.f_87611_;
        }
        Action $$13 = RealmsWorldSlotButton.m_87959_($$0, $$8, $$2);
        Pair<Component, Component> $$14 = this.m_87953_($$0, $$9, $$12, $$2, $$13);
        this.f_87916_ = new State($$8, $$9, $$10, $$11, $$12, $$2, $$13, (Component)$$14.getFirst());
        this.m_93666_((Component)$$14.getSecond());
    }

    private static Action m_87959_(RealmsServer p_87960_, boolean p_87961_, boolean p_87962_) {
        if (p_87961_) {
            if (!p_87960_.f_87482_ && p_87960_.f_87477_ != RealmsServer.State.UNINITIALIZED) {
                return Action.JOIN;
            }
        } else if (p_87962_) {
            if (!p_87960_.f_87482_) {
                return Action.SWITCH_SLOT;
            }
        } else {
            return Action.SWITCH_SLOT;
        }
        return Action.NOTHING;
    }

    private Pair<Component, Component> m_87953_(RealmsServer p_87954_, String p_87955_, boolean p_87956_, boolean p_87957_, Action p_87958_) {
        Component $$9;
        MutableComponent $$7;
        if (p_87958_ == Action.NOTHING) {
            return Pair.of(null, (Object)Component.m_237113_(p_87955_));
        }
        if (p_87957_) {
            if (p_87956_) {
                Component $$5 = CommonComponents.f_237098_;
            } else {
                MutableComponent $$6 = Component.m_237113_(" ").m_130946_(p_87955_).m_130946_(" ").m_130946_(p_87954_.f_87487_);
            }
        } else {
            $$7 = Component.m_237113_(" ").m_130946_(p_87955_);
        }
        if (p_87958_ == Action.JOIN) {
            Component $$8 = f_87922_;
        } else {
            $$9 = p_87957_ ? f_87923_ : f_87924_;
        }
        MutableComponent $$10 = $$9.m_6881_().m_7220_($$7);
        return Pair.of((Object)$$9, (Object)$$10);
    }

    @Override
    public void m_6303_(PoseStack p_87964_, int p_87965_, int p_87966_, float p_87967_) {
        if (this.f_87916_ == null) {
            return;
        }
        this.m_87938_(p_87964_, this.f_93620_, this.f_93621_, p_87965_, p_87966_, this.f_87916_.f_87983_, this.f_87916_.f_87984_, this.f_87914_, this.f_87916_.f_87985_, this.f_87916_.f_87986_, this.f_87916_.f_87980_, this.f_87916_.f_87981_, this.f_87916_.f_87982_, this.f_87916_.f_87987_);
    }

    private void m_87938_(PoseStack p_87939_, int p_87940_, int p_87941_, int p_87942_, int p_87943_, boolean p_87944_, String p_87945_, int p_87946_, long p_87947_, @Nullable String p_87948_, boolean p_87949_, boolean p_87950_, Action p_87951_, @Nullable Component p_87952_) {
        boolean $$16;
        boolean $$14 = this.m_198029_();
        if (this.m_5953_(p_87942_, p_87943_) && p_87952_ != null) {
            this.f_87926_.accept(p_87952_);
        }
        Minecraft $$15 = Minecraft.m_91087_();
        if (p_87950_) {
            RealmsTextureManager.m_90190_(String.valueOf(p_87947_), p_87948_);
        } else if (p_87949_) {
            RenderSystem.m_157456_(0, f_87918_);
        } else if (p_87948_ != null && p_87947_ != -1L) {
            RealmsTextureManager.m_90190_(String.valueOf(p_87947_), p_87948_);
        } else if (p_87946_ == 1) {
            RenderSystem.m_157456_(0, f_87919_);
        } else if (p_87946_ == 2) {
            RenderSystem.m_157456_(0, f_87920_);
        } else if (p_87946_ == 3) {
            RenderSystem.m_157456_(0, f_87921_);
        }
        if (p_87944_) {
            RenderSystem.m_157429_(0.56f, 0.56f, 0.56f, 1.0f);
        } else {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        }
        RealmsWorldSlotButton.m_93133_(p_87939_, p_87940_ + 3, p_87941_ + 3, 0.0f, 0.0f, 74, 74, 74, 74);
        RenderSystem.m_157456_(0, f_87917_);
        boolean bl = $$16 = $$14 && p_87951_ != Action.NOTHING;
        if ($$16) {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        } else if (p_87944_) {
            RenderSystem.m_157429_(0.8f, 0.8f, 0.8f, 1.0f);
        } else {
            RenderSystem.m_157429_(0.56f, 0.56f, 0.56f, 1.0f);
        }
        RealmsWorldSlotButton.m_93133_(p_87939_, p_87940_, p_87941_, 0.0f, 0.0f, 80, 80, 80, 80);
        if (p_87944_) {
            this.m_231298_(p_87939_, p_87940_, p_87941_);
        }
        RealmsWorldSlotButton.m_93208_(p_87939_, $$15.f_91062_, p_87945_, p_87940_ + 40, p_87941_ + 66, 0xFFFFFF);
    }

    private void m_231298_(PoseStack p_231299_, int p_231300_, int p_231301_) {
        RenderSystem.m_157456_(0, f_231297_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RealmsWorldSlotButton.m_93133_(p_231299_, p_231300_ + 67, p_231301_ + 4, 0.0f, 0.0f, 9, 8, 9, 8);
        RenderSystem.m_69461_();
    }

    public static class State {
        final boolean f_87983_;
        final String f_87984_;
        final long f_87985_;
        @Nullable
        final String f_87986_;
        public final boolean f_87980_;
        public final boolean f_87981_;
        public final Action f_87982_;
        @Nullable
        final Component f_87987_;

        State(boolean p_87989_, String p_87990_, long p_87991_, @Nullable String p_87992_, boolean p_87993_, boolean p_87994_, Action p_87995_, @Nullable Component p_87996_) {
            this.f_87983_ = p_87989_;
            this.f_87984_ = p_87990_;
            this.f_87985_ = p_87991_;
            this.f_87986_ = p_87992_;
            this.f_87980_ = p_87993_;
            this.f_87981_ = p_87994_;
            this.f_87982_ = p_87995_;
            this.f_87987_ = p_87996_;
        }
    }

    public static final class Action
    extends Enum<Action> {
        public static final /* enum */ Action NOTHING = new Action();
        public static final /* enum */ Action SWITCH_SLOT = new Action();
        public static final /* enum */ Action JOIN = new Action();
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_87978_) {
            return Enum.valueOf(Action.class, p_87978_);
        }

        private static /* synthetic */ Action[] m_167351_() {
            return new Action[]{NOTHING, SWITCH_SLOT, JOIN};
        }

        static {
            $VALUES = Action.m_167351_();
        }
    }
}

