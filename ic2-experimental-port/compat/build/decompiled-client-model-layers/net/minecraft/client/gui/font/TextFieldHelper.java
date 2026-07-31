/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.font;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

public class TextFieldHelper {
    private final Supplier<String> f_95129_;
    private final Consumer<String> f_95130_;
    private final Supplier<String> f_95131_;
    private final Consumer<String> f_95132_;
    private final Predicate<String> f_95133_;
    private int f_95134_;
    private int f_95135_;

    public TextFieldHelper(Supplier<String> p_95137_, Consumer<String> p_95138_, Supplier<String> p_95139_, Consumer<String> p_95140_, Predicate<String> p_95141_) {
        this.f_95129_ = p_95137_;
        this.f_95130_ = p_95138_;
        this.f_95131_ = p_95139_;
        this.f_95132_ = p_95140_;
        this.f_95133_ = p_95141_;
        this.m_95193_();
    }

    public static Supplier<String> m_95153_(Minecraft p_95154_) {
        return () -> TextFieldHelper.m_95169_(p_95154_);
    }

    public static String m_95169_(Minecraft p_95170_) {
        return ChatFormatting.m_126649_(p_95170_.f_91068_.m_90876_().replaceAll("\\r", ""));
    }

    public static Consumer<String> m_95182_(Minecraft p_95183_) {
        return p_95173_ -> TextFieldHelper.m_95155_(p_95183_, p_95173_);
    }

    public static void m_95155_(Minecraft p_95156_, String p_95157_) {
        p_95156_.f_91068_.m_90911_(p_95157_);
    }

    public boolean m_95143_(char p_95144_) {
        if (SharedConstants.m_136188_(p_95144_)) {
            this.m_95160_(this.f_95129_.get(), Character.toString(p_95144_));
        }
        return true;
    }

    public boolean m_95145_(int p_95146_) {
        CursorStep $$1;
        if (Screen.m_96634_(p_95146_)) {
            this.m_95188_();
            return true;
        }
        if (Screen.m_96632_(p_95146_)) {
            this.m_95178_();
            return true;
        }
        if (Screen.m_96630_(p_95146_)) {
            this.m_95165_();
            return true;
        }
        if (Screen.m_96628_(p_95146_)) {
            this.m_95142_();
            return true;
        }
        CursorStep cursorStep = $$1 = Screen.m_96637_() ? CursorStep.WORD : CursorStep.CHARACTER;
        if (p_95146_ == 259) {
            this.m_232572_(-1, $$1);
            return true;
        }
        if (p_95146_ == 261) {
            this.m_232572_(1, $$1);
        } else {
            if (p_95146_ == 263) {
                this.m_232575_(-1, Screen.m_96638_(), $$1);
                return true;
            }
            if (p_95146_ == 262) {
                this.m_232575_(1, Screen.m_96638_(), $$1);
                return true;
            }
            if (p_95146_ == 268) {
                this.m_95176_(Screen.m_96638_());
                return true;
            }
            if (p_95146_ == 269) {
                this.m_95186_(Screen.m_96638_());
                return true;
            }
        }
        return false;
    }

    private int m_95195_(int p_95196_) {
        return Mth.m_14045_(p_95196_, 0, this.f_95129_.get().length());
    }

    private void m_95160_(String p_95161_, String p_95162_) {
        if (this.f_95135_ != this.f_95134_) {
            p_95161_ = this.m_95184_(p_95161_);
        }
        this.f_95134_ = Mth.m_14045_(this.f_95134_, 0, p_95161_.length());
        String $$2 = new StringBuilder(p_95161_).insert(this.f_95134_, p_95162_).toString();
        if (this.f_95133_.test($$2)) {
            this.f_95130_.accept($$2);
            this.f_95135_ = this.f_95134_ = Math.min($$2.length(), this.f_95134_ + p_95162_.length());
        }
    }

    public void m_95158_(String p_95159_) {
        this.m_95160_(this.f_95129_.get(), p_95159_);
    }

    private void m_95163_(boolean p_95164_) {
        if (!p_95164_) {
            this.f_95135_ = this.f_95134_;
        }
    }

    public void m_232575_(int p_232576_, boolean p_232577_, CursorStep p_232578_) {
        switch (p_232578_) {
            case CHARACTER: {
                this.m_95150_(p_232576_, p_232577_);
                break;
            }
            case WORD: {
                this.m_95166_(p_232576_, p_232577_);
            }
        }
    }

    public void m_169093_(int p_169094_) {
        this.m_95150_(p_169094_, false);
    }

    public void m_95150_(int p_95151_, boolean p_95152_) {
        this.f_95134_ = Util.m_137479_(this.f_95129_.get(), this.f_95134_, p_95151_);
        this.m_95163_(p_95152_);
    }

    public void m_169095_(int p_169096_) {
        this.m_95166_(p_169096_, false);
    }

    public void m_95166_(int p_95167_, boolean p_95168_) {
        this.f_95134_ = StringSplitter.m_92355_(this.f_95129_.get(), p_95167_, this.f_95134_, true);
        this.m_95163_(p_95168_);
    }

    public void m_232572_(int p_232573_, CursorStep p_232574_) {
        switch (p_232574_) {
            case CHARACTER: {
                this.m_95189_(p_232573_);
                break;
            }
            case WORD: {
                this.m_232579_(p_232573_);
            }
        }
    }

    public void m_232579_(int p_232580_) {
        int $$1 = StringSplitter.m_92355_(this.f_95129_.get(), p_232580_, this.f_95134_, true);
        this.m_95189_($$1 - this.f_95134_);
    }

    public void m_95189_(int p_95190_) {
        String $$1 = this.f_95129_.get();
        if (!$$1.isEmpty()) {
            String $$6;
            if (this.f_95135_ != this.f_95134_) {
                String $$2 = this.m_95184_($$1);
            } else {
                int $$3 = Util.m_137479_($$1, this.f_95134_, p_95190_);
                int $$4 = Math.min($$3, this.f_95134_);
                int $$5 = Math.max($$3, this.f_95134_);
                $$6 = new StringBuilder($$1).delete($$4, $$5).toString();
                if (p_95190_ < 0) {
                    this.f_95135_ = this.f_95134_ = $$4;
                }
            }
            this.f_95130_.accept($$6);
        }
    }

    public void m_95142_() {
        String $$0 = this.f_95129_.get();
        this.f_95132_.accept(this.m_95174_($$0));
        this.f_95130_.accept(this.m_95184_($$0));
    }

    public void m_95165_() {
        this.m_95160_(this.f_95129_.get(), this.f_95131_.get());
        this.f_95135_ = this.f_95134_;
    }

    public void m_95178_() {
        this.f_95132_.accept(this.m_95174_(this.f_95129_.get()));
    }

    public void m_95188_() {
        this.f_95135_ = 0;
        this.f_95134_ = this.f_95129_.get().length();
    }

    private String m_95174_(String p_95175_) {
        int $$1 = Math.min(this.f_95134_, this.f_95135_);
        int $$2 = Math.max(this.f_95134_, this.f_95135_);
        return p_95175_.substring($$1, $$2);
    }

    private String m_95184_(String p_95185_) {
        if (this.f_95135_ == this.f_95134_) {
            return p_95185_;
        }
        int $$1 = Math.min(this.f_95134_, this.f_95135_);
        int $$2 = Math.max(this.f_95134_, this.f_95135_);
        String $$3 = p_95185_.substring(0, $$1) + p_95185_.substring($$2);
        this.f_95135_ = this.f_95134_ = $$1;
        return $$3;
    }

    public void m_169097_() {
        this.m_95176_(false);
    }

    public void m_95176_(boolean p_95177_) {
        this.f_95134_ = 0;
        this.m_95163_(p_95177_);
    }

    public void m_95193_() {
        this.m_95186_(false);
    }

    public void m_95186_(boolean p_95187_) {
        this.f_95134_ = this.f_95129_.get().length();
        this.m_95163_(p_95187_);
    }

    public int m_95194_() {
        return this.f_95134_;
    }

    public void m_169098_(int p_169099_) {
        this.m_95179_(p_169099_, true);
    }

    public void m_95179_(int p_95180_, boolean p_95181_) {
        this.f_95134_ = this.m_95195_(p_95180_);
        this.m_95163_(p_95181_);
    }

    public int m_95197_() {
        return this.f_95135_;
    }

    public void m_169100_(int p_169101_) {
        this.f_95135_ = this.m_95195_(p_169101_);
    }

    public void m_95147_(int p_95148_, int p_95149_) {
        int $$2 = this.f_95129_.get().length();
        this.f_95134_ = Mth.m_14045_(p_95148_, 0, $$2);
        this.f_95135_ = Mth.m_14045_(p_95149_, 0, $$2);
    }

    public boolean m_95198_() {
        return this.f_95134_ != this.f_95135_;
    }

    public static final class CursorStep
    extends Enum<CursorStep> {
        public static final /* enum */ CursorStep CHARACTER = new CursorStep();
        public static final /* enum */ CursorStep WORD = new CursorStep();
        private static final /* synthetic */ CursorStep[] $VALUES;

        public static CursorStep[] values() {
            return (CursorStep[])$VALUES.clone();
        }

        public static CursorStep valueOf(String p_232592_) {
            return Enum.valueOf(CursorStep.class, p_232592_);
        }

        private static /* synthetic */ CursorStep[] m_232590_() {
            return new CursorStep[]{CHARACTER, WORD};
        }

        static {
            $VALUES = CursorStep.m_232590_();
        }
    }
}

