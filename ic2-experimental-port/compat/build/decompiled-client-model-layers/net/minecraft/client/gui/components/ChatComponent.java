/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ComponentRenderUtils;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.ChatVisiblity;
import org.slf4j.Logger;

public class ChatComponent
extends GuiComponent {
    private static final Logger f_93757_ = LogUtils.getLogger();
    private static final int f_168843_ = 100;
    private static final int f_240336_ = -1;
    private static final int f_240385_ = 4;
    private static final int f_240337_ = 4;
    private final Minecraft f_93758_;
    private final List<String> f_93759_ = Lists.newArrayList();
    private final List<GuiMessage> f_93760_ = Lists.newArrayList();
    private final List<GuiMessage.Line> f_93761_ = Lists.newArrayList();
    private int f_93763_;
    private boolean f_93764_;

    public ChatComponent(Minecraft p_93768_) {
        this.f_93758_ = p_93768_;
    }

    public void m_93780_(PoseStack p_93781_, int p_93782_) {
        if (this.m_93817_()) {
            return;
        }
        int $$2 = this.m_93816_();
        int $$3 = this.f_93761_.size();
        if ($$3 <= 0) {
            return;
        }
        boolean $$4 = this.m_93818_();
        float $$5 = (float)this.m_93815_();
        int $$6 = Mth.m_14167_((float)this.m_93813_() / $$5);
        p_93781_.m_85836_();
        p_93781_.m_85837_(4.0, 8.0, 0.0);
        p_93781_.m_85841_($$5, $$5, 1.0f);
        double $$7 = this.f_93758_.f_91066_.m_232098_().m_231551_() * (double)0.9f + (double)0.1f;
        double $$8 = this.f_93758_.f_91066_.m_232104_().m_231551_();
        double $$9 = this.f_93758_.f_91066_.m_232101_().m_231551_();
        int $$10 = this.m_240691_();
        double $$11 = -8.0 * ($$9 + 1.0) + 4.0 * $$9;
        int $$12 = 0;
        for (int $$13 = 0; $$13 + this.f_93763_ < this.f_93761_.size() && $$13 < $$2; ++$$13) {
            int $$15;
            GuiMessage.Line $$14 = this.f_93761_.get($$13 + this.f_93763_);
            if ($$14 == null || ($$15 = p_93782_ - $$14.f_240350_()) >= 200 && !$$4) continue;
            double $$16 = $$4 ? 1.0 : ChatComponent.m_93775_($$15);
            int $$17 = (int)(255.0 * $$16 * $$7);
            int $$18 = (int)(255.0 * $$16 * $$8);
            ++$$12;
            if ($$17 <= 3) continue;
            boolean $$19 = false;
            int $$20 = -$$13 * $$10;
            int $$21 = (int)((double)$$20 + $$11);
            p_93781_.m_85836_();
            p_93781_.m_85837_(0.0, 0.0, 50.0);
            ChatComponent.m_93172_(p_93781_, -4, $$20 - $$10, 0 + $$6 + 4 + 4, $$20, $$18 << 24);
            GuiMessageTag $$22 = $$14.f_240351_();
            if ($$22 != null) {
                int $$23 = $$22.f_240386_() | $$17 << 24;
                ChatComponent.m_93172_(p_93781_, -4, $$20 - $$10, -2, $$20, $$23);
                if ($$4 && $$14.f_240367_() && $$22.f_240355_() != null) {
                    int $$24 = this.m_240495_($$14);
                    int $$25 = $$21 + this.f_93758_.f_91062_.f_92710_;
                    this.m_240401_(p_93781_, $$24, $$25, $$22.f_240355_());
                }
            }
            RenderSystem.m_69478_();
            p_93781_.m_85837_(0.0, 0.0, 50.0);
            this.f_93758_.f_91062_.m_92744_(p_93781_, $$14.f_240339_(), 0.0f, $$21, 0xFFFFFF + ($$17 << 24));
            RenderSystem.m_69461_();
            p_93781_.m_85849_();
        }
        long $$26 = this.f_93758_.m_240442_().m_242024_();
        if ($$26 > 0L) {
            int $$27 = (int)(128.0 * $$7);
            int $$28 = (int)(255.0 * $$8);
            p_93781_.m_85836_();
            p_93781_.m_85837_(0.0, 0.0, 50.0);
            ChatComponent.m_93172_(p_93781_, -2, 0, $$6 + 4, 9, $$28 << 24);
            RenderSystem.m_69478_();
            p_93781_.m_85837_(0.0, 0.0, 50.0);
            this.f_93758_.f_91062_.m_92763_(p_93781_, Component.m_237110_("chat.queue", $$26), 0.0f, 1.0f, 0xFFFFFF + ($$27 << 24));
            p_93781_.m_85849_();
            RenderSystem.m_69461_();
        }
        if ($$4) {
            int $$29 = this.m_240691_();
            int $$30 = $$3 * $$29;
            int $$31 = $$12 * $$29;
            int $$32 = this.f_93763_ * $$31 / $$3;
            int $$33 = $$31 * $$31 / $$30;
            if ($$30 != $$31) {
                int $$34 = $$32 > 0 ? 170 : 96;
                int $$35 = this.f_93764_ ? 0xCC3333 : 0x3333AA;
                int $$36 = $$6 + 4;
                ChatComponent.m_93172_(p_93781_, $$36, -$$32, $$36 + 2, -$$32 - $$33, $$35 + ($$34 << 24));
                ChatComponent.m_93172_(p_93781_, $$36 + 2, -$$32, $$36 + 1, -$$32 - $$33, 0xCCCCCC + ($$34 << 24));
            }
        }
        p_93781_.m_85849_();
    }

    private void m_240401_(PoseStack p_240586_, int p_240593_, int p_240610_, GuiMessageTag.Icon p_240605_) {
        int $$4 = p_240610_ - p_240605_.f_240372_ - 1;
        p_240605_.m_240420_(p_240586_, p_240593_, $$4);
    }

    private int m_240495_(GuiMessage.Line p_240622_) {
        return this.f_93758_.f_91062_.m_92724_(p_240622_.f_240339_()) + 4;
    }

    private boolean m_93817_() {
        return this.f_93758_.f_91066_.m_232090_().m_231551_() == ChatVisiblity.HIDDEN;
    }

    private static double m_93775_(int p_93776_) {
        double $$1 = (double)p_93776_ / 200.0;
        $$1 = 1.0 - $$1;
        $$1 *= 10.0;
        $$1 = Mth.m_14008_($$1, 0.0, 1.0);
        $$1 *= $$1;
        return $$1;
    }

    public void m_93795_(boolean p_93796_) {
        this.f_93758_.m_240442_().m_241954_();
        this.f_93761_.clear();
        this.f_93760_.clear();
        if (p_93796_) {
            this.f_93759_.clear();
        }
    }

    public void m_93785_(Component p_93786_) {
        this.m_240964_(p_93786_, null, GuiMessageTag.m_240701_());
    }

    public void m_240964_(Component p_241484_, @Nullable MessageSignature p_241323_, @Nullable GuiMessageTag p_241297_) {
        this.m_240465_(p_241484_, p_241323_, this.f_93758_.f_91065_.m_93079_(), p_241297_, false);
    }

    private void m_242648_(Component p_242919_, @Nullable GuiMessageTag p_242840_) {
        String $$2 = p_242919_.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
        String $$3 = Util.m_214614_(p_242840_, GuiMessageTag::f_240342_);
        if ($$3 != null) {
            f_93757_.info("[{}] [CHAT] {}", (Object)$$3, (Object)$$2);
        } else {
            f_93757_.info("[CHAT] {}", (Object)$$2);
        }
    }

    private void m_240465_(Component p_240562_, @Nullable MessageSignature p_241566_, int p_240583_, @Nullable GuiMessageTag p_240624_, boolean p_240558_) {
        this.m_242648_(p_240562_, p_240624_);
        int $$5 = Mth.m_14107_((double)this.m_93813_() / this.m_93815_());
        if (p_240624_ != null && p_240624_.f_240355_() != null) {
            $$5 -= p_240624_.f_240355_().f_240358_ + 4 + 2;
        }
        List<FormattedCharSequence> $$6 = ComponentRenderUtils.m_94005_(p_240562_, $$5, this.f_93758_.f_91062_);
        boolean $$7 = this.m_93818_();
        for (int $$8 = 0; $$8 < $$6.size(); ++$$8) {
            FormattedCharSequence $$9 = $$6.get($$8);
            if ($$7 && this.f_93763_ > 0) {
                this.f_93764_ = true;
                this.m_205360_(1);
            }
            boolean $$10 = $$8 == $$6.size() - 1;
            this.f_93761_.add(0, new GuiMessage.Line(p_240583_, $$9, p_240624_, $$10));
        }
        while (this.f_93761_.size() > 100) {
            this.f_93761_.remove(this.f_93761_.size() - 1);
        }
        if (!p_240558_) {
            this.f_93760_.add(0, new GuiMessage(p_240583_, p_240562_, p_241566_, p_240624_));
            while (this.f_93760_.size() > 100) {
                this.f_93760_.remove(this.f_93760_.size() - 1);
            }
        }
    }

    public void m_240953_(MessageSignature p_241324_) {
        Iterator<GuiMessage> $$1 = this.f_93760_.iterator();
        while ($$1.hasNext()) {
            MessageSignature $$2 = $$1.next().f_240905_();
            if ($$2 == null || !$$2.equals(p_241324_)) continue;
            $$1.remove();
            break;
        }
        this.m_241120_();
    }

    public void m_93769_() {
        this.m_93810_();
        this.m_241120_();
    }

    private void m_241120_() {
        this.f_93761_.clear();
        for (int $$0 = this.f_93760_.size() - 1; $$0 >= 0; --$$0) {
            GuiMessage $$1 = this.f_93760_.get($$0);
            this.m_240465_($$1.f_240363_(), $$1.f_240905_(), $$1.f_90786_(), $$1.f_240352_(), true);
        }
    }

    public List<String> m_93797_() {
        return this.f_93759_;
    }

    public void m_93783_(String p_93784_) {
        if (this.f_93759_.isEmpty() || !this.f_93759_.get(this.f_93759_.size() - 1).equals(p_93784_)) {
            this.f_93759_.add(p_93784_);
        }
    }

    public void m_93810_() {
        this.f_93763_ = 0;
        this.f_93764_ = false;
    }

    public void m_205360_(int p_205361_) {
        this.f_93763_ += p_205361_;
        int $$1 = this.f_93761_.size();
        if (this.f_93763_ > $$1 - this.m_93816_()) {
            this.f_93763_ = $$1 - this.m_93816_();
        }
        if (this.f_93763_ <= 0) {
            this.f_93763_ = 0;
            this.f_93764_ = false;
        }
    }

    public boolean m_93772_(double p_93773_, double p_93774_) {
        if (!this.m_93818_() || this.f_93758_.f_91066_.f_92062_ || this.m_93817_()) {
            return false;
        }
        ChatListener $$2 = this.f_93758_.m_240442_();
        if ($$2.m_242024_() == 0L) {
            return false;
        }
        double $$3 = p_93773_ - 2.0;
        double $$4 = (double)this.f_93758_.m_91268_().m_85446_() - p_93774_ - 40.0;
        if ($$3 <= (double)Mth.m_14107_((double)this.m_93813_() / this.m_93815_()) && $$4 < 0.0 && $$4 > (double)Mth.m_14107_(-9.0 * this.m_93815_())) {
            $$2.m_240711_();
            return true;
        }
        return false;
    }

    @Nullable
    public Style m_93800_(double p_93801_, double p_93802_) {
        double $$2 = this.m_240491_(p_93801_);
        if ($$2 < 0.0 || $$2 > (double)Mth.m_14107_((double)this.m_93813_() / this.m_93815_())) {
            return null;
        }
        double $$3 = this.m_240485_(p_93802_);
        int $$4 = this.m_240427_($$3);
        if ($$4 >= 0 && $$4 < this.f_93761_.size()) {
            GuiMessage.Line $$5 = this.f_93761_.get($$4);
            return this.f_93758_.f_91062_.m_92865_().m_92338_($$5.f_240339_(), Mth.m_14107_($$2));
        }
        return null;
    }

    @Nullable
    public GuiMessageTag m_240463_(double p_240576_, double p_240554_) {
        GuiMessage.Line $$5;
        GuiMessageTag $$6;
        double $$2 = this.m_240491_(p_240576_);
        double $$3 = this.m_240485_(p_240554_);
        int $$4 = this.m_240427_($$3);
        if ($$4 >= 0 && $$4 < this.f_93761_.size() && ($$6 = ($$5 = this.f_93761_.get($$4)).f_240351_()) != null && this.m_240447_($$2, $$5, $$6)) {
            return $$6;
        }
        return null;
    }

    private boolean m_240447_(double p_240619_, GuiMessage.Line p_240547_, GuiMessageTag p_240637_) {
        if (p_240619_ < 0.0) {
            return true;
        }
        GuiMessageTag.Icon $$3 = p_240637_.f_240355_();
        if ($$3 != null) {
            int $$4 = this.m_240495_(p_240547_);
            int $$5 = $$4 + $$3.f_240358_;
            return p_240619_ >= (double)$$4 && p_240619_ <= (double)$$5;
        }
        return false;
    }

    private double m_240491_(double p_240580_) {
        return (p_240580_ - 4.0) / this.m_93815_();
    }

    private double m_240485_(double p_240548_) {
        double $$1 = (double)this.f_93758_.m_91268_().m_85446_() - p_240548_ - 40.0;
        return $$1 / (this.m_93815_() * (this.f_93758_.f_91066_.m_232101_().m_231551_() + 1.0));
    }

    private int m_240427_(double p_240641_) {
        int $$2;
        if (!this.m_93818_() || this.f_93758_.f_91066_.f_92062_ || this.m_93817_()) {
            return -1;
        }
        int $$1 = Math.min(this.m_93816_(), this.f_93761_.size());
        if (p_240641_ >= 0.0 && p_240641_ < (double)(this.f_93758_.f_91062_.f_92710_ * $$1 + $$1) && ($$2 = Mth.m_14107_(p_240641_ / (double)this.f_93758_.f_91062_.f_92710_ + (double)this.f_93763_)) >= 0 && $$2 < this.f_93761_.size()) {
            return $$2;
        }
        return -1;
    }

    @Nullable
    public ChatScreen m_232476_() {
        Screen screen = this.f_93758_.f_91080_;
        if (screen instanceof ChatScreen) {
            ChatScreen $$0 = (ChatScreen)screen;
            return $$0;
        }
        return null;
    }

    private boolean m_93818_() {
        return this.m_232476_() != null;
    }

    public int m_93813_() {
        return ChatComponent.m_93798_(this.f_93758_.f_91066_.m_232113_().m_231551_());
    }

    public int m_93814_() {
        return ChatComponent.m_93811_(this.m_93818_() ? this.f_93758_.f_91066_.m_232117_().m_231551_() : this.f_93758_.f_91066_.m_232116_().m_231551_());
    }

    public double m_93815_() {
        return this.f_93758_.f_91066_.m_232110_().m_231551_();
    }

    public static int m_93798_(double p_93799_) {
        int $$1 = 320;
        int $$2 = 40;
        return Mth.m_14107_(p_93799_ * 280.0 + 40.0);
    }

    public static int m_93811_(double p_93812_) {
        int $$1 = 180;
        int $$2 = 20;
        return Mth.m_14107_(p_93812_ * 160.0 + 20.0);
    }

    public static double m_232477_() {
        int $$0 = 180;
        int $$1 = 20;
        return 70.0 / (double)(ChatComponent.m_93811_(1.0) - 20);
    }

    public int m_93816_() {
        return this.m_93814_() / this.m_240691_();
    }

    private int m_240691_() {
        return (int)((double)this.f_93758_.f_91062_.f_92710_ * (this.f_93758_.f_91066_.m_232101_().m_231551_() + 1.0));
    }
}

