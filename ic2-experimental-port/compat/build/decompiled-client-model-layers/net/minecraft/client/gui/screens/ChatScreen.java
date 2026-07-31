/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.tree.CommandNode
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.chat.ChatPreviewAnimator;
import net.minecraft.client.gui.chat.ClientChatPreview;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.chat.ChatPreviewStatus;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.PreviewableCommand;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.StringUtils;

public class ChatScreen
extends Screen {
    private static final int f_241611_ = 15118153;
    private static final int f_241682_ = 7844841;
    private static final int f_241696_ = 10533887;
    public static final double f_169234_ = 7.0;
    private static final Component f_169235_ = Component.m_237115_("chat_screen.usage");
    private static final int f_232692_ = 2;
    private static final int f_232693_ = 2;
    private static final int f_232694_ = 15;
    private static final Component f_232695_ = Component.m_237115_("chatPreview.warning.toast.title");
    private static final Component f_232696_ = Component.m_237115_("chatPreview.warning.toast");
    private static final Component f_241687_ = Component.m_237110_("chat.previewInput", Component.m_237115_("key.keyboard.enter")).m_130940_(ChatFormatting.DARK_GRAY);
    private static final int f_240354_ = 260;
    private String f_95574_ = "";
    private int f_95575_ = -1;
    protected EditBox f_95573_;
    private String f_95576_;
    CommandSuggestions f_95577_;
    private ClientChatPreview f_232698_;
    private ChatPreviewStatus f_241615_;
    private boolean f_241631_;
    private final ChatPreviewAnimator f_241643_ = new ChatPreviewAnimator();

    public ChatScreen(String p_95579_) {
        super(Component.m_237115_("chat_screen.title"));
        this.f_95576_ = p_95579_;
    }

    @Override
    protected void m_7856_() {
        ServerData.ChatPreview $$1;
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_95575_ = this.f_96541_.f_91065_.m_93076_().m_93797_().size();
        this.f_95573_ = new EditBox(this.f_96541_.f_243022_, 4, this.f_96544_ - 12, this.f_96543_ - 4, 12, (Component)Component.m_237115_("chat.editBox")){

            @Override
            protected MutableComponent m_5646_() {
                return super.m_5646_().m_130946_(ChatScreen.this.f_95577_.m_93924_());
            }
        };
        this.f_95573_.m_94199_(256);
        this.f_95573_.m_94182_(false);
        this.f_95573_.m_94144_(this.f_95576_);
        this.f_95573_.m_94151_(this::m_95610_);
        this.m_7787_(this.f_95573_);
        this.f_95577_ = new CommandSuggestions(this.f_96541_, this, this.f_95573_, this.f_96547_, false, false, 1, 10, true, -805306368);
        this.f_95577_.m_93881_();
        this.m_94718_(this.f_95573_);
        this.f_241643_.m_241926_(Util.m_137550_());
        this.f_232698_ = new ClientChatPreview(this.f_96541_);
        this.m_232718_(this.f_95573_.m_94155_());
        ServerData $$0 = this.f_96541_.m_91089_();
        ChatPreviewStatus chatPreviewStatus = this.f_241615_ = $$0 != null && !$$0.m_233818_() ? ChatPreviewStatus.OFF : this.f_96541_.f_91066_.m_231835_().m_231551_();
        if ($$0 != null && this.f_241615_ != ChatPreviewStatus.OFF && ($$1 = $$0.m_233817_()) != null && $$0.m_233818_() && $$1.m_233831_()) {
            ServerList.m_105446_($$0);
            SystemToast $$2 = SystemToast.m_94847_(this.f_96541_, SystemToast.SystemToastIds.CHAT_PREVIEW_WARNING, f_232695_, f_232696_);
            this.f_96541_.m_91300_().m_94922_($$2);
        }
        if (this.f_241615_ == ChatPreviewStatus.CONFIRM) {
            this.f_241631_ = this.f_95576_.startsWith("/") && !this.f_96541_.f_91074_.m_241927_(this.f_95576_.substring(1));
        }
    }

    @Override
    public void m_6574_(Minecraft p_95600_, int p_95601_, int p_95602_) {
        String $$3 = this.f_95573_.m_94155_();
        this.m_6575_(p_95600_, p_95601_, p_95602_);
        this.m_95612_($$3);
        this.f_95577_.m_93881_();
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
        this.f_96541_.f_91065_.m_93076_().m_93810_();
    }

    @Override
    public void m_86600_() {
        this.f_95573_.m_94120_();
        this.f_232698_.m_232412_();
    }

    private void m_95610_(String p_95611_) {
        String $$1 = this.f_95573_.m_94155_();
        this.f_95577_.m_93922_(!$$1.equals(this.f_95576_));
        this.f_95577_.m_93881_();
        if (this.f_241615_ == ChatPreviewStatus.LIVE) {
            this.m_232718_($$1);
        } else if (this.f_241615_ == ChatPreviewStatus.CONFIRM && !this.f_232698_.m_241933_($$1)) {
            this.f_241631_ = $$1.startsWith("/") && !this.f_96541_.f_91074_.m_241927_($$1.substring(1));
            this.f_232698_.m_232416_("");
        }
    }

    private void m_232718_(String p_232719_) {
        String $$1 = this.m_232706_(p_232719_);
        if (this.m_232727_()) {
            this.m_232720_($$1);
        } else {
            this.f_232698_.m_232418_();
        }
    }

    private void m_232720_(String p_232721_) {
        if (p_232721_.startsWith("/")) {
            this.m_232724_(p_232721_);
        } else {
            this.m_232722_(p_232721_);
        }
    }

    private void m_232722_(String p_232723_) {
        this.f_232698_.m_232416_(p_232723_);
    }

    private void m_232724_(String p_232725_) {
        ParseResults<SharedSuggestionProvider> $$1 = this.f_95577_.m_242637_();
        CommandNode<SharedSuggestionProvider> $$2 = this.f_95577_.m_232478_(this.f_95573_.m_94207_());
        if ($$1 != null && $$2 != null && PreviewableCommand.m_242644_($$1).m_242639_($$2)) {
            this.f_232698_.m_232416_(p_232725_);
        } else {
            this.f_232698_.m_232418_();
        }
    }

    private boolean m_232727_() {
        if (this.f_96541_.f_91074_ == null) {
            return false;
        }
        if (this.f_96541_.m_91090_()) {
            return true;
        }
        if (this.f_241615_ != ChatPreviewStatus.OFF) {
            ServerData $$0 = this.f_96541_.m_91089_();
            return $$0 != null && $$0.m_233818_();
        }
        return false;
    }

    @Override
    public boolean m_7933_(int p_95591_, int p_95592_, int p_95593_) {
        if (this.f_95577_.m_93888_(p_95591_, p_95592_, p_95593_)) {
            return true;
        }
        if (super.m_7933_(p_95591_, p_95592_, p_95593_)) {
            return true;
        }
        if (p_95591_ == 256) {
            this.f_96541_.m_91152_(null);
            return true;
        }
        if (p_95591_ == 257 || p_95591_ == 335) {
            if (this.m_241797_(this.f_95573_.m_94155_(), true)) {
                this.f_96541_.m_91152_(null);
            }
            return true;
        }
        if (p_95591_ == 265) {
            this.m_95588_(-1);
            return true;
        }
        if (p_95591_ == 264) {
            this.m_95588_(1);
            return true;
        }
        if (p_95591_ == 266) {
            this.f_96541_.f_91065_.m_93076_().m_205360_(this.f_96541_.f_91065_.m_93076_().m_93816_() - 1);
            return true;
        }
        if (p_95591_ == 267) {
            this.f_96541_.f_91065_.m_93076_().m_205360_(-this.f_96541_.f_91065_.m_93076_().m_93816_() + 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6050_(double p_95581_, double p_95582_, double p_95583_) {
        if (this.f_95577_.m_93882_(p_95583_ = Mth.m_14008_(p_95583_, -1.0, 1.0))) {
            return true;
        }
        if (!ChatScreen.m_96638_()) {
            p_95583_ *= 7.0;
        }
        this.f_96541_.f_91065_.m_93076_().m_205360_((int)p_95583_);
        return true;
    }

    @Override
    public boolean m_6375_(double p_95585_, double p_95586_, int p_95587_) {
        if (this.f_95577_.m_93884_((int)p_95585_, (int)p_95586_, p_95587_)) {
            return true;
        }
        if (p_95587_ == 0) {
            ChatComponent $$3 = this.f_96541_.f_91065_.m_93076_();
            if ($$3.m_93772_(p_95585_, p_95586_)) {
                return true;
            }
            Style $$4 = this.m_232701_(p_95585_, p_95586_);
            if ($$4 != null && this.m_5561_($$4)) {
                this.f_95576_ = this.f_95573_.m_94155_();
                return true;
            }
        }
        if (this.f_95573_.m_6375_(p_95585_, p_95586_, p_95587_)) {
            return true;
        }
        return super.m_6375_(p_95585_, p_95586_, p_95587_);
    }

    @Override
    protected void m_6697_(String p_95606_, boolean p_95607_) {
        if (p_95607_) {
            this.f_95573_.m_94144_(p_95606_);
        } else {
            this.f_95573_.m_94164_(p_95606_);
        }
    }

    public void m_95588_(int p_95589_) {
        int $$1 = this.f_95575_ + p_95589_;
        int $$2 = this.f_96541_.f_91065_.m_93076_().m_93797_().size();
        if (($$1 = Mth.m_14045_($$1, 0, $$2)) == this.f_95575_) {
            return;
        }
        if ($$1 == $$2) {
            this.f_95575_ = $$2;
            this.f_95573_.m_94144_(this.f_95574_);
            return;
        }
        if (this.f_95575_ == $$2) {
            this.f_95574_ = this.f_95573_.m_94155_();
        }
        this.f_95573_.m_94144_(this.f_96541_.f_91065_.m_93076_().m_93797_().get($$1));
        this.f_95577_.m_93922_(false);
        this.f_95575_ = $$1;
    }

    @Override
    public void m_6305_(PoseStack p_95595_, int p_95596_, int p_95597_, float p_95598_) {
        this.m_7522_(this.f_95573_);
        this.f_95573_.m_94178_(true);
        ChatScreen.m_93172_(p_95595_, 2, this.f_96544_ - 14, this.f_96543_ - 2, this.f_96544_ - 2, this.f_96541_.f_91066_.m_92143_(Integer.MIN_VALUE));
        this.f_95573_.m_6305_(p_95595_, p_95596_, p_95597_, p_95598_);
        super.m_6305_(p_95595_, p_95596_, p_95597_, p_95598_);
        boolean $$4 = this.f_96541_.m_231465_().m_233775_() != null;
        ChatPreviewAnimator.State $$5 = this.f_241643_.m_241860_(Util.m_137550_(), this.m_242596_());
        if ($$5.f_241637_() != null) {
            this.m_241793_(p_95595_, $$5.f_241637_(), $$5.f_241616_(), $$4);
            this.f_95577_.m_241972_(p_95595_, p_95596_, p_95597_);
        } else {
            this.f_95577_.m_93900_(p_95595_, p_95596_, p_95597_);
            if ($$4) {
                p_95595_.m_85836_();
                ChatScreen.m_93172_(p_95595_, 0, this.f_96544_ - 14, 2, this.f_96544_ - 2, -8932375);
                p_95595_.m_85849_();
            }
        }
        Style $$6 = this.m_232701_(p_95596_, p_95597_);
        if ($$6 != null && $$6.m_131186_() != null) {
            this.m_96570_(p_95595_, $$6, p_95596_, p_95597_);
        } else {
            GuiMessageTag $$7 = this.f_96541_.f_91065_.m_93076_().m_240463_(p_95596_, p_95597_);
            if ($$7 != null && $$7.f_240381_() != null) {
                this.m_96617_(p_95595_, this.f_96547_.m_92923_($$7.f_240381_(), 260), p_95596_, p_95597_);
            }
        }
    }

    @Nullable
    protected Component m_242596_() {
        String $$0 = this.f_95573_.m_94155_();
        if ($$0.isBlank()) {
            return null;
        }
        Component $$1 = this.m_241838_();
        if (this.f_241615_ == ChatPreviewStatus.CONFIRM && !this.f_241631_) {
            return Objects.requireNonNullElse($$1, this.f_232698_.m_241933_($$0) && !$$0.startsWith("/") ? Component.m_237113_($$0) : f_241687_);
        }
        return $$1;
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    private void m_95612_(String p_95613_) {
        this.f_95573_.m_94144_(p_95613_);
    }

    @Override
    protected void m_142228_(NarrationElementOutput p_169238_) {
        p_169238_.m_169146_(NarratedElementType.TITLE, this.m_96636_());
        p_169238_.m_169146_(NarratedElementType.USAGE, f_169235_);
        String $$1 = this.f_95573_.m_94155_();
        if (!$$1.isEmpty()) {
            p_169238_.m_142047_().m_169146_(NarratedElementType.TITLE, Component.m_237110_("chat_screen.message", $$1));
        }
    }

    public void m_241793_(PoseStack p_242432_, Component p_242318_, float p_242443_, boolean p_242189_) {
        int $$4 = (int)(255.0 * (this.f_96541_.f_91066_.m_232098_().m_231551_() * (double)0.9f + (double)0.1f) * (double)p_242443_);
        int $$5 = (int)((double)(this.f_232698_.m_241947_() ? 127 : 255) * this.f_96541_.f_91066_.m_232104_().m_231551_() * (double)p_242443_);
        int $$6 = this.m_232729_();
        List<FormattedCharSequence> $$7 = this.m_242004_(p_242318_);
        int $$8 = this.m_232713_($$7);
        int $$9 = this.m_232708_($$8);
        RenderSystem.m_69478_();
        p_242432_.m_85836_();
        p_242432_.m_85837_(this.m_232699_(), $$9, 0.0);
        ChatScreen.m_93172_(p_242432_, 0, 0, $$6, $$8, $$5 << 24);
        if ($$4 > 0) {
            p_242432_.m_85837_(2.0, 2.0, 0.0);
            for (int $$10 = 0; $$10 < $$7.size(); ++$$10) {
                FormattedCharSequence $$11 = $$7.get($$10);
                int $$12 = $$10 * this.f_96547_.f_92710_;
                this.m_242003_(p_242432_, $$11, $$12, $$4);
                this.f_96547_.m_92744_(p_242432_, $$11, 0.0f, $$12, $$4 << 24 | 0xFFFFFF);
            }
        }
        p_242432_.m_85849_();
        RenderSystem.m_69461_();
        if (p_242189_ && this.f_232698_.m_241808_() != null) {
            int $$13 = this.f_232698_.m_241947_() ? 15118153 : 7844841;
            int $$14 = (int)(255.0f * p_242443_);
            p_242432_.m_85836_();
            ChatScreen.m_93172_(p_242432_, 0, $$9, 2, this.m_232730_(), $$14 << 24 | $$13);
            p_242432_.m_85849_();
        }
    }

    private void m_242003_(PoseStack p_242454_, FormattedCharSequence p_242367_, int p_242163_, int p_242358_) {
        int $$4 = p_242163_ + this.f_96547_.f_92710_;
        int $$5 = p_242358_ << 24 | 0xA0BBFF;
        Predicate<Style> $$6 = p_242204_ -> p_242204_.m_131186_() != null || p_242204_.m_131182_() != null;
        for (StringSplitter.Span $$7 : this.f_96547_.m_92865_().m_241773_(p_242367_, $$6)) {
            int $$8 = Mth.m_14143_($$7.f_241699_());
            int $$9 = Mth.m_14167_($$7.f_241679_());
            ChatScreen.m_93172_(p_242454_, $$8, p_242163_, $$9, $$4, $$5);
        }
    }

    @Nullable
    private Style m_232701_(double p_232702_, double p_232703_) {
        Style $$2 = this.f_96541_.f_91065_.m_93076_().m_93800_(p_232702_, p_232703_);
        if ($$2 == null) {
            $$2 = this.m_232715_(p_232702_, p_232703_);
        }
        return $$2;
    }

    @Nullable
    private Style m_232715_(double p_232716_, double p_232717_) {
        if (this.f_96541_.f_91066_.f_92062_) {
            return null;
        }
        Component $$2 = this.m_241838_();
        if ($$2 == null) {
            return null;
        }
        List<FormattedCharSequence> $$3 = this.m_242004_($$2);
        int $$4 = this.m_232713_($$3);
        if (p_232716_ < (double)this.m_232699_() || p_232716_ > (double)this.m_232700_() || p_232717_ < (double)this.m_232708_($$4) || p_232717_ > (double)this.m_232730_()) {
            return null;
        }
        int $$5 = this.m_232699_() + 2;
        int $$6 = this.m_232708_($$4) + 2;
        int $$7 = (Mth.m_14107_(p_232717_) - $$6) / this.f_96547_.f_92710_;
        if ($$7 >= 0 && $$7 < $$3.size()) {
            FormattedCharSequence $$8 = $$3.get($$7);
            return this.f_96541_.f_91062_.m_92865_().m_92338_($$8, (int)(p_232716_ - (double)$$5));
        }
        return null;
    }

    @Nullable
    private Component m_241838_() {
        return Util.m_214614_(this.f_232698_.m_241808_(), ClientChatPreview.Preview::f_232430_);
    }

    private List<FormattedCharSequence> m_242004_(Component p_242266_) {
        return this.f_96547_.m_92923_(p_242266_, this.m_232729_());
    }

    private int m_232729_() {
        return this.f_96541_.f_91080_.f_96543_ - 4;
    }

    private int m_232713_(List<FormattedCharSequence> p_232714_) {
        return Math.max(p_232714_.size(), 1) * this.f_96547_.f_92710_ + 4;
    }

    private int m_232730_() {
        return this.f_96541_.f_91080_.f_96544_ - 15;
    }

    private int m_232708_(int p_232709_) {
        return this.m_232730_() - p_232709_;
    }

    private int m_232699_() {
        return 2;
    }

    private int m_232700_() {
        return this.f_96541_.f_91080_.f_96543_ - 2;
    }

    public boolean m_241797_(String p_242400_, boolean p_242161_) {
        if ((p_242400_ = this.m_232706_(p_242400_)).isEmpty()) {
            return true;
        }
        if (this.f_241615_ == ChatPreviewStatus.CONFIRM && !this.f_241631_) {
            this.f_95577_.m_241889_();
            if (!this.f_232698_.m_241933_(p_242400_)) {
                this.m_232718_(p_242400_);
                return false;
            }
        }
        if (p_242161_) {
            this.f_96541_.f_91065_.m_93076_().m_93783_(p_242400_);
        }
        Component $$2 = Util.m_214614_(this.f_232698_.m_241899_(p_242400_), ClientChatPreview.Preview::f_232430_);
        if (p_242400_.startsWith("/")) {
            this.f_96541_.f_91074_.m_234148_(p_242400_.substring(1), $$2);
        } else {
            this.f_96541_.f_91074_.m_240287_(p_242400_, $$2);
        }
        return true;
    }

    public String m_232706_(String p_232707_) {
        return StringUtils.normalizeSpace((String)p_232707_.trim());
    }

    public ClientChatPreview m_232726_() {
        return this.f_232698_;
    }
}

