/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public interface MultiLineLabel {
    public static final MultiLineLabel f_94331_ = new MultiLineLabel(){

        @Override
        public int m_6276_(PoseStack p_94378_, int p_94379_, int p_94380_) {
            return p_94380_;
        }

        @Override
        public int m_6514_(PoseStack p_94382_, int p_94383_, int p_94384_, int p_94385_, int p_94386_) {
            return p_94384_;
        }

        @Override
        public int m_6516_(PoseStack p_94388_, int p_94389_, int p_94390_, int p_94391_, int p_94392_) {
            return p_94390_;
        }

        @Override
        public int m_6508_(PoseStack p_94394_, int p_94395_, int p_94396_, int p_94397_, int p_94398_) {
            return p_94396_;
        }

        @Override
        public void m_207298_(PoseStack p_210824_, int p_210825_, int p_210826_, int p_210827_, int p_210828_, int p_210829_) {
        }

        @Override
        public int m_5770_() {
            return 0;
        }

        @Override
        public int m_214161_() {
            return 0;
        }
    };

    public static MultiLineLabel m_94341_(Font p_94342_, FormattedText p_94343_, int p_94344_) {
        return MultiLineLabel.m_94361_(p_94342_, (List)p_94342_.m_92923_(p_94343_, p_94344_).stream().map(p_94374_ -> new TextWithWidth((FormattedCharSequence)p_94374_, p_94342_.m_92724_((FormattedCharSequence)p_94374_))).collect(ImmutableList.toImmutableList()));
    }

    public static MultiLineLabel m_94345_(Font p_94346_, FormattedText p_94347_, int p_94348_, int p_94349_) {
        return MultiLineLabel.m_94361_(p_94346_, (List)p_94346_.m_92923_(p_94347_, p_94348_).stream().limit(p_94349_).map(p_94371_ -> new TextWithWidth((FormattedCharSequence)p_94371_, p_94346_.m_92724_((FormattedCharSequence)p_94371_))).collect(ImmutableList.toImmutableList()));
    }

    public static MultiLineLabel m_94350_(Font p_94351_, Component ... p_94352_) {
        return MultiLineLabel.m_94361_(p_94351_, (List)Arrays.stream(p_94352_).map(Component::m_7532_).map(p_94360_ -> new TextWithWidth((FormattedCharSequence)p_94360_, p_94351_.m_92724_((FormattedCharSequence)p_94360_))).collect(ImmutableList.toImmutableList()));
    }

    public static MultiLineLabel m_169036_(Font p_169037_, List<Component> p_169038_) {
        return MultiLineLabel.m_94361_(p_169037_, (List)p_169038_.stream().map(Component::m_7532_).map(p_169035_ -> new TextWithWidth((FormattedCharSequence)p_169035_, p_169037_.m_92724_((FormattedCharSequence)p_169035_))).collect(ImmutableList.toImmutableList()));
    }

    public static MultiLineLabel m_94361_(final Font p_94362_, final List<TextWithWidth> p_94363_) {
        if (p_94363_.isEmpty()) {
            return f_94331_;
        }
        return new MultiLineLabel(){
            private final int f_232519_;
            {
                this.f_232519_ = p_94363_.stream().mapToInt(p_232527_ -> p_232527_.f_94428_).max().orElse(0);
            }

            @Override
            public int m_6276_(PoseStack p_94406_, int p_94407_, int p_94408_) {
                return this.m_6514_(p_94406_, p_94407_, p_94408_, p_94362_.f_92710_, 0xFFFFFF);
            }

            @Override
            public int m_6514_(PoseStack p_94410_, int p_94411_, int p_94412_, int p_94413_, int p_94414_) {
                int $$5 = p_94412_;
                for (TextWithWidth $$6 : p_94363_) {
                    p_94362_.m_92744_(p_94410_, $$6.f_94427_, p_94411_ - $$6.f_94428_ / 2, $$5, p_94414_);
                    $$5 += p_94413_;
                }
                return $$5;
            }

            @Override
            public int m_6516_(PoseStack p_94416_, int p_94417_, int p_94418_, int p_94419_, int p_94420_) {
                int $$5 = p_94418_;
                for (TextWithWidth $$6 : p_94363_) {
                    p_94362_.m_92744_(p_94416_, $$6.f_94427_, p_94417_, $$5, p_94420_);
                    $$5 += p_94419_;
                }
                return $$5;
            }

            @Override
            public int m_6508_(PoseStack p_94422_, int p_94423_, int p_94424_, int p_94425_, int p_94426_) {
                int $$5 = p_94424_;
                for (TextWithWidth $$6 : p_94363_) {
                    p_94362_.m_92877_(p_94422_, $$6.f_94427_, p_94423_, $$5, p_94426_);
                    $$5 += p_94425_;
                }
                return $$5;
            }

            @Override
            public void m_207298_(PoseStack p_210831_, int p_210832_, int p_210833_, int p_210834_, int p_210835_, int p_210836_) {
                int $$6 = p_94363_.stream().mapToInt(p_232524_ -> p_232524_.f_94428_).max().orElse(0);
                if ($$6 > 0) {
                    GuiComponent.m_93172_(p_210831_, p_210832_ - $$6 / 2 - p_210835_, p_210833_ - p_210835_, p_210832_ + $$6 / 2 + p_210835_, p_210833_ + p_94363_.size() * p_210834_ + p_210835_, p_210836_);
                }
            }

            @Override
            public int m_5770_() {
                return p_94363_.size();
            }

            @Override
            public int m_214161_() {
                return this.f_232519_;
            }
        };
    }

    public int m_6276_(PoseStack var1, int var2, int var3);

    public int m_6514_(PoseStack var1, int var2, int var3, int var4, int var5);

    public int m_6516_(PoseStack var1, int var2, int var3, int var4, int var5);

    public int m_6508_(PoseStack var1, int var2, int var3, int var4, int var5);

    public void m_207298_(PoseStack var1, int var2, int var3, int var4, int var5, int var6);

    public int m_5770_();

    public int m_214161_();

    public static class TextWithWidth {
        final FormattedCharSequence f_94427_;
        final int f_94428_;

        TextWithWidth(FormattedCharSequence p_94430_, int p_94431_) {
            this.f_94427_ = p_94430_;
            this.f_94428_ = p_94431_;
        }
    }
}

