/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;

public class BossHealthOverlay
extends GuiComponent {
    private static final ResourceLocation f_93697_ = new ResourceLocation("textures/gui/bars.png");
    private static final int f_168805_ = 182;
    private static final int f_168806_ = 5;
    private static final int f_168807_ = 80;
    private final Minecraft f_93698_;
    final Map<UUID, LerpingBossEvent> f_93699_ = Maps.newLinkedHashMap();

    public BossHealthOverlay(Minecraft p_93702_) {
        this.f_93698_ = p_93702_;
    }

    public void m_93704_(PoseStack p_93705_) {
        if (this.f_93699_.isEmpty()) {
            return;
        }
        int $$1 = this.f_93698_.m_91268_().m_85445_();
        int $$2 = 12;
        for (LerpingBossEvent $$3 : this.f_93699_.values()) {
            int $$4 = $$1 / 2 - 91;
            int $$5 = $$2;
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.m_157456_(0, f_93697_);
            this.m_93706_(p_93705_, $$4, $$5, $$3);
            Component $$6 = $$3.m_18861_();
            int $$7 = this.f_93698_.f_91062_.m_92852_($$6);
            int $$8 = $$1 / 2 - $$7 / 2;
            int $$9 = $$5 - 9;
            this.f_93698_.f_91062_.m_92763_(p_93705_, $$6, $$8, $$9, 0xFFFFFF);
            if (($$2 += 10 + this.f_93698_.f_91062_.f_92710_) < this.f_93698_.m_91268_().m_85446_() / 3) continue;
            break;
        }
    }

    private void m_93706_(PoseStack p_93707_, int p_93708_, int p_93709_, BossEvent p_93710_) {
        this.m_232469_(p_93707_, p_93708_, p_93709_, p_93710_, 182, 0);
        int $$4 = (int)(p_93710_.m_142717_() * 183.0f);
        if ($$4 > 0) {
            this.m_232469_(p_93707_, p_93708_, p_93709_, p_93710_, $$4, 5);
        }
    }

    private void m_232469_(PoseStack p_232470_, int p_232471_, int p_232472_, BossEvent p_232473_, int p_232474_, int p_232475_) {
        this.m_93228_(p_232470_, p_232471_, p_232472_, 0, p_232473_.m_18862_().ordinal() * 5 * 2 + p_232475_, p_232474_, 5);
        if (p_232473_.m_18863_() != BossEvent.BossBarOverlay.PROGRESS) {
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            this.m_93228_(p_232470_, p_232471_, p_232472_, 0, 80 + (p_232473_.m_18863_().ordinal() - 1) * 5 * 2 + p_232475_, p_232474_, 5);
            RenderSystem.m_69461_();
        }
    }

    public void m_93711_(ClientboundBossEventPacket p_93712_) {
        p_93712_.m_178643_(new ClientboundBossEventPacket.Handler(){

            @Override
            public void m_142107_(UUID p_168824_, Component p_168825_, float p_168826_, BossEvent.BossBarColor p_168827_, BossEvent.BossBarOverlay p_168828_, boolean p_168829_, boolean p_168830_, boolean p_168831_) {
                BossHealthOverlay.this.f_93699_.put(p_168824_, new LerpingBossEvent(p_168824_, p_168825_, p_168826_, p_168827_, p_168828_, p_168829_, p_168830_, p_168831_));
            }

            @Override
            public void m_142751_(UUID p_168812_) {
                BossHealthOverlay.this.f_93699_.remove(p_168812_);
            }

            @Override
            public void m_142653_(UUID p_168814_, float p_168815_) {
                BossHealthOverlay.this.f_93699_.get(p_168814_).m_142711_(p_168815_);
            }

            @Override
            public void m_142366_(UUID p_168821_, Component p_168822_) {
                BossHealthOverlay.this.f_93699_.get(p_168821_).m_6456_(p_168822_);
            }

            @Override
            public void m_142358_(UUID p_168817_, BossEvent.BossBarColor p_168818_, BossEvent.BossBarOverlay p_168819_) {
                LerpingBossEvent $$3 = BossHealthOverlay.this.f_93699_.get(p_168817_);
                $$3.m_6451_(p_168818_);
                $$3.m_5648_(p_168819_);
            }

            @Override
            public void m_142513_(UUID p_168833_, boolean p_168834_, boolean p_168835_, boolean p_168836_) {
                LerpingBossEvent $$4 = BossHealthOverlay.this.f_93699_.get(p_168833_);
                $$4.m_7003_(p_168834_);
                $$4.m_7005_(p_168835_);
                $$4.m_7006_(p_168836_);
            }
        });
    }

    public void m_93703_() {
        this.f_93699_.clear();
    }

    public boolean m_93713_() {
        if (!this.f_93699_.isEmpty()) {
            for (BossEvent bossEvent : this.f_93699_.values()) {
                if (!bossEvent.m_18865_()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean m_93714_() {
        if (!this.f_93699_.isEmpty()) {
            for (BossEvent bossEvent : this.f_93699_.values()) {
                if (!bossEvent.m_18864_()) continue;
                return true;
            }
        }
        return false;
    }

    public boolean m_93715_() {
        if (!this.f_93699_.isEmpty()) {
            for (BossEvent bossEvent : this.f_93699_.values()) {
                if (!bossEvent.m_18866_()) continue;
                return true;
            }
        }
        return false;
    }
}

