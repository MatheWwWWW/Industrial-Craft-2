/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SubtitleOverlay
extends GuiComponent
implements SoundEventListener {
    private static final long f_169070_ = 3000L;
    private final Minecraft f_94637_;
    private final List<Subtitle> f_94638_ = Lists.newArrayList();
    private boolean f_94639_;

    public SubtitleOverlay(Minecraft p_94641_) {
        this.f_94637_ = p_94641_;
    }

    public void m_94642_(PoseStack p_94643_) {
        if (!this.f_94639_ && this.f_94637_.f_91066_.m_231825_().m_231551_().booleanValue()) {
            this.f_94637_.m_91106_().m_120374_(this);
            this.f_94639_ = true;
        } else if (this.f_94639_ && !this.f_94637_.f_91066_.m_231825_().m_231551_().booleanValue()) {
            this.f_94637_.m_91106_().m_120401_(this);
            this.f_94639_ = false;
        }
        if (!this.f_94639_ || this.f_94638_.isEmpty()) {
            return;
        }
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        Vec3 $$1 = new Vec3(this.f_94637_.f_91074_.m_20185_(), this.f_94637_.f_91074_.m_20188_(), this.f_94637_.f_91074_.m_20189_());
        Vec3 $$2 = new Vec3(0.0, 0.0, -1.0).m_82496_(-this.f_94637_.f_91074_.m_146909_() * ((float)Math.PI / 180)).m_82524_(-this.f_94637_.f_91074_.m_146908_() * ((float)Math.PI / 180));
        Vec3 $$3 = new Vec3(0.0, 1.0, 0.0).m_82496_(-this.f_94637_.f_91074_.m_146909_() * ((float)Math.PI / 180)).m_82524_(-this.f_94637_.f_91074_.m_146908_() * ((float)Math.PI / 180));
        Vec3 $$4 = $$2.m_82537_($$3);
        int $$5 = 0;
        int $$6 = 0;
        Iterator<Subtitle> $$7 = this.f_94638_.iterator();
        while ($$7.hasNext()) {
            Subtitle $$8 = $$7.next();
            if ($$8.m_94658_() + 3000L <= Util.m_137550_()) {
                $$7.remove();
                continue;
            }
            $$6 = Math.max($$6, this.f_94637_.f_91062_.m_92852_($$8.m_94655_()));
        }
        $$6 += this.f_94637_.f_91062_.m_92895_("<") + this.f_94637_.f_91062_.m_92895_(" ") + this.f_94637_.f_91062_.m_92895_(">") + this.f_94637_.f_91062_.m_92895_(" ");
        for (Subtitle $$9 : this.f_94638_) {
            int $$10 = 255;
            Component $$11 = $$9.m_94655_();
            Vec3 $$12 = $$9.m_94659_().m_82546_($$1).m_82541_();
            double $$13 = -$$4.m_82526_($$12);
            double $$14 = -$$2.m_82526_($$12);
            boolean $$15 = $$14 > 0.5;
            int $$16 = $$6 / 2;
            int $$17 = this.f_94637_.f_91062_.f_92710_;
            int $$18 = $$17 / 2;
            float $$19 = 1.0f;
            int $$20 = this.f_94637_.f_91062_.m_92852_($$11);
            int $$21 = Mth.m_14143_(Mth.m_144920_(255.0f, 75.0f, (float)(Util.m_137550_() - $$9.m_94658_()) / 3000.0f));
            int $$22 = $$21 << 16 | $$21 << 8 | $$21;
            p_94643_.m_85836_();
            p_94643_.m_85837_((float)this.f_94637_.m_91268_().m_85445_() - (float)$$16 * 1.0f - 2.0f, (float)(this.f_94637_.m_91268_().m_85446_() - 35) - (float)($$5 * ($$17 + 1)) * 1.0f, 0.0);
            p_94643_.m_85841_(1.0f, 1.0f, 1.0f);
            SubtitleOverlay.m_93172_(p_94643_, -$$16 - 1, -$$18 - 1, $$16 + 1, $$18 + 1, this.f_94637_.f_91066_.m_92170_(0.8f));
            RenderSystem.m_69478_();
            if (!$$15) {
                if ($$13 > 0.0) {
                    this.f_94637_.f_91062_.m_92883_(p_94643_, ">", $$16 - this.f_94637_.f_91062_.m_92895_(">"), -$$18, $$22 + -16777216);
                } else if ($$13 < 0.0) {
                    this.f_94637_.f_91062_.m_92883_(p_94643_, "<", -$$16, -$$18, $$22 + -16777216);
                }
            }
            this.f_94637_.f_91062_.m_92889_(p_94643_, $$11, -$$20 / 2, -$$18, $$22 + -16777216);
            p_94643_.m_85849_();
            ++$$5;
        }
        RenderSystem.m_69461_();
    }

    @Override
    public void m_6985_(SoundInstance p_94645_, WeighedSoundEvents p_94646_) {
        if (p_94646_.m_120453_() == null) {
            return;
        }
        Component $$2 = p_94646_.m_120453_();
        if (!this.f_94638_.isEmpty()) {
            for (Subtitle $$3 : this.f_94638_) {
                if (!$$3.m_94655_().equals($$2)) continue;
                $$3.m_94656_(new Vec3(p_94645_.m_7772_(), p_94645_.m_7780_(), p_94645_.m_7778_()));
                return;
            }
        }
        this.f_94638_.add(new Subtitle($$2, new Vec3(p_94645_.m_7772_(), p_94645_.m_7780_(), p_94645_.m_7778_())));
    }

    public static class Subtitle {
        private final Component f_94648_;
        private long f_94649_;
        private Vec3 f_94650_;

        public Subtitle(Component p_169072_, Vec3 p_169073_) {
            this.f_94648_ = p_169072_;
            this.f_94650_ = p_169073_;
            this.f_94649_ = Util.m_137550_();
        }

        public Component m_94655_() {
            return this.f_94648_;
        }

        public long m_94658_() {
            return this.f_94649_;
        }

        public Vec3 m_94659_() {
            return this.f_94650_;
        }

        public void m_94656_(Vec3 p_94657_) {
            this.f_94650_ = p_94657_;
            this.f_94649_ = Util.m_137550_();
        }
    }
}

