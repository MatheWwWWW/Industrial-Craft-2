/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;

public class AdvancementsScreen
extends Screen
implements ClientAdvancements.Listener {
    private static final ResourceLocation f_97329_ = new ResourceLocation("textures/gui/advancements/window.png");
    private static final ResourceLocation f_97330_ = new ResourceLocation("textures/gui/advancements/tabs.png");
    public static final int f_169556_ = 252;
    public static final int f_169557_ = 140;
    private static final int f_169564_ = 9;
    private static final int f_169565_ = 18;
    public static final int f_169558_ = 234;
    public static final int f_169559_ = 113;
    private static final int f_169566_ = 8;
    private static final int f_169567_ = 6;
    public static final int f_169560_ = 16;
    public static final int f_169561_ = 16;
    public static final int f_169562_ = 14;
    public static final int f_169563_ = 7;
    private static final Component f_97331_ = Component.m_237115_("advancements.sad_label");
    private static final Component f_97332_ = Component.m_237115_("advancements.empty");
    private static final Component f_97333_ = Component.m_237115_("gui.advancements");
    private final ClientAdvancements f_97334_;
    private final Map<Advancement, AdvancementTab> f_97335_ = Maps.newLinkedHashMap();
    @Nullable
    private AdvancementTab f_97336_;
    private boolean f_97337_;

    public AdvancementsScreen(ClientAdvancements p_97340_) {
        super(GameNarrator.f_93310_);
        this.f_97334_ = p_97340_;
    }

    @Override
    protected void m_7856_() {
        this.f_97335_.clear();
        this.f_97336_ = null;
        this.f_97334_.m_104397_(this);
        if (this.f_97336_ == null && !this.f_97335_.isEmpty()) {
            this.f_97334_.m_104401_(this.f_97335_.values().iterator().next().m_97182_(), true);
        } else {
            this.f_97334_.m_104401_(this.f_97336_ == null ? null : this.f_97336_.m_97182_(), true);
        }
    }

    @Override
    public void m_7861_() {
        this.f_97334_.m_104397_(null);
        ClientPacketListener $$0 = this.f_96541_.m_91403_();
        if ($$0 != null) {
            $$0.m_104955_(ServerboundSeenAdvancementsPacket.m_134444_());
        }
    }

    @Override
    public boolean m_6375_(double p_97343_, double p_97344_, int p_97345_) {
        if (p_97345_ == 0) {
            int $$3 = (this.f_96543_ - 252) / 2;
            int $$4 = (this.f_96544_ - 140) / 2;
            for (AdvancementTab $$5 : this.f_97335_.values()) {
                if (!$$5.m_97154_($$3, $$4, p_97343_, p_97344_)) continue;
                this.f_97334_.m_104401_($$5.m_97182_(), true);
                break;
            }
        }
        return super.m_6375_(p_97343_, p_97344_, p_97345_);
    }

    @Override
    public boolean m_7933_(int p_97353_, int p_97354_, int p_97355_) {
        if (this.f_96541_.f_91066_.f_92055_.m_90832_(p_97353_, p_97354_)) {
            this.f_96541_.m_91152_(null);
            this.f_96541_.f_91067_.m_91601_();
            return true;
        }
        return super.m_7933_(p_97353_, p_97354_, p_97355_);
    }

    @Override
    public void m_6305_(PoseStack p_97361_, int p_97362_, int p_97363_, float p_97364_) {
        int $$4 = (this.f_96543_ - 252) / 2;
        int $$5 = (this.f_96544_ - 140) / 2;
        this.m_7333_(p_97361_);
        this.m_97373_(p_97361_, p_97362_, p_97363_, $$4, $$5);
        this.m_97356_(p_97361_, $$4, $$5);
        this.m_97381_(p_97361_, p_97362_, p_97363_, $$4, $$5);
    }

    @Override
    public boolean m_7979_(double p_97347_, double p_97348_, int p_97349_, double p_97350_, double p_97351_) {
        if (p_97349_ != 0) {
            this.f_97337_ = false;
            return false;
        }
        if (!this.f_97337_) {
            this.f_97337_ = true;
        } else if (this.f_97336_ != null) {
            this.f_97336_.m_97151_(p_97350_, p_97351_);
        }
        return true;
    }

    private void m_97373_(PoseStack p_97374_, int p_97375_, int p_97376_, int p_97377_, int p_97378_) {
        AdvancementTab $$5 = this.f_97336_;
        if ($$5 == null) {
            AdvancementsScreen.m_93172_(p_97374_, p_97377_ + 9, p_97378_ + 18, p_97377_ + 9 + 234, p_97378_ + 18 + 113, -16777216);
            int $$6 = p_97377_ + 9 + 117;
            AdvancementsScreen.m_93215_(p_97374_, this.f_96547_, f_97332_, $$6, p_97378_ + 18 + 56 - this.f_96547_.f_92710_ / 2, -1);
            AdvancementsScreen.m_93215_(p_97374_, this.f_96547_, f_97331_, $$6, p_97378_ + 18 + 113 - this.f_96547_.f_92710_, -1);
            return;
        }
        PoseStack $$7 = RenderSystem.m_157191_();
        $$7.m_85836_();
        $$7.m_85837_(p_97377_ + 9, p_97378_ + 18, 0.0);
        RenderSystem.m_157182_();
        $$5.m_97163_(p_97374_);
        $$7.m_85849_();
        RenderSystem.m_157182_();
        RenderSystem.m_69456_(515);
        RenderSystem.m_69465_();
    }

    public void m_97356_(PoseStack p_97357_, int p_97358_, int p_97359_) {
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69478_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_97329_);
        this.m_93228_(p_97357_, p_97358_, p_97359_, 0, 0, 252, 140);
        if (this.f_97335_.size() > 1) {
            RenderSystem.m_157456_(0, f_97330_);
            for (AdvancementTab $$3 : this.f_97335_.values()) {
                $$3.m_97165_(p_97357_, p_97358_, p_97359_, $$3 == this.f_97336_);
            }
            RenderSystem.m_69453_();
            for (AdvancementTab $$4 : this.f_97335_.values()) {
                $$4.m_97159_(p_97358_, p_97359_, this.f_96542_);
            }
            RenderSystem.m_69461_();
        }
        this.f_96547_.m_92889_(p_97357_, f_97333_, p_97358_ + 8, p_97359_ + 6, 0x404040);
    }

    private void m_97381_(PoseStack p_97382_, int p_97383_, int p_97384_, int p_97385_, int p_97386_) {
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.f_97336_ != null) {
            PoseStack $$5 = RenderSystem.m_157191_();
            $$5.m_85836_();
            $$5.m_85837_(p_97385_ + 9, p_97386_ + 18, 400.0);
            RenderSystem.m_157182_();
            RenderSystem.m_69482_();
            this.f_97336_.m_97183_(p_97382_, p_97383_ - p_97385_ - 9, p_97384_ - p_97386_ - 18, p_97385_, p_97386_);
            RenderSystem.m_69465_();
            $$5.m_85849_();
            RenderSystem.m_157182_();
        }
        if (this.f_97335_.size() > 1) {
            for (AdvancementTab $$6 : this.f_97335_.values()) {
                if (!$$6.m_97154_(p_97385_, p_97386_, p_97383_, p_97384_)) continue;
                this.m_96602_(p_97382_, $$6.m_97189_(), p_97383_, p_97384_);
            }
        }
    }

    @Override
    public void m_5513_(Advancement p_97366_) {
        AdvancementTab $$1 = AdvancementTab.m_97170_(this.f_96541_, this, this.f_97335_.size(), p_97366_);
        if ($$1 == null) {
            return;
        }
        this.f_97335_.put(p_97366_, $$1);
    }

    @Override
    public void m_5504_(Advancement p_97372_) {
    }

    @Override
    public void m_5505_(Advancement p_97380_) {
        AdvancementTab $$1 = this.m_97394_(p_97380_);
        if ($$1 != null) {
            $$1.m_97178_(p_97380_);
        }
    }

    @Override
    public void m_5516_(Advancement p_97388_) {
    }

    @Override
    public void m_7922_(Advancement p_97368_, AdvancementProgress p_97369_) {
        AdvancementWidget $$2 = this.m_97392_(p_97368_);
        if ($$2 != null) {
            $$2.m_97264_(p_97369_);
        }
    }

    @Override
    public void m_6896_(@Nullable Advancement p_97391_) {
        this.f_97336_ = this.f_97335_.get(p_97391_);
    }

    @Override
    public void m_7204_() {
        this.f_97335_.clear();
        this.f_97336_ = null;
    }

    @Nullable
    public AdvancementWidget m_97392_(Advancement p_97393_) {
        AdvancementTab $$1 = this.m_97394_(p_97393_);
        return $$1 == null ? null : $$1.m_97180_(p_97393_);
    }

    @Nullable
    private AdvancementTab m_97394_(Advancement p_97395_) {
        while (p_97395_.m_138319_() != null) {
            p_97395_ = p_97395_.m_138319_();
        }
        return this.f_97335_.get(p_97395_);
    }
}

