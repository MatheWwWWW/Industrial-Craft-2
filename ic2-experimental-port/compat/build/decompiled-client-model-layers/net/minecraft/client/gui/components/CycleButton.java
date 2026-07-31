/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class CycleButton<T>
extends AbstractButton
implements TooltipAccessor {
    static final BooleanSupplier f_168856_ = Screen::m_96639_;
    private static final List<Boolean> f_168857_ = ImmutableList.of((Object)Boolean.TRUE, (Object)Boolean.FALSE);
    private final Component f_168858_;
    private int f_168859_;
    private T f_168860_;
    private final ValueListSupplier<T> f_168861_;
    private final Function<T, Component> f_168862_;
    private final Function<CycleButton<T>, MutableComponent> f_168863_;
    private final OnValueChange<T> f_168864_;
    private final OptionInstance.TooltipSupplier<T> f_168865_;
    private final boolean f_168866_;

    CycleButton(int p_232484_, int p_232485_, int p_232486_, int p_232487_, Component p_232488_, Component p_232489_, int p_232490_, T p_232491_, ValueListSupplier<T> p_232492_, Function<T, Component> p_232493_, Function<CycleButton<T>, MutableComponent> p_232494_, OnValueChange<T> p_232495_, OptionInstance.TooltipSupplier<T> p_232496_, boolean p_232497_) {
        super(p_232484_, p_232485_, p_232486_, p_232487_, p_232488_);
        this.f_168858_ = p_232489_;
        this.f_168859_ = p_232490_;
        this.f_168860_ = p_232491_;
        this.f_168861_ = p_232492_;
        this.f_168862_ = p_232493_;
        this.f_168863_ = p_232494_;
        this.f_168864_ = p_232495_;
        this.f_168865_ = p_232496_;
        this.f_168866_ = p_232497_;
    }

    @Override
    public void m_5691_() {
        if (Screen.m_96638_()) {
            this.m_168908_(-1);
        } else {
            this.m_168908_(1);
        }
    }

    private void m_168908_(int p_168909_) {
        List<T> $$1 = this.f_168861_.m_142477_();
        this.f_168859_ = Mth.m_14100_(this.f_168859_ + p_168909_, $$1.size());
        T $$2 = $$1.get(this.f_168859_);
        this.m_168905_($$2);
        this.f_168864_.m_168965_(this, $$2);
    }

    private T m_168914_(int p_168915_) {
        List<T> $$1 = this.f_168861_.m_142477_();
        return $$1.get(Mth.m_14100_(this.f_168859_ + p_168915_, $$1.size()));
    }

    @Override
    public boolean m_6050_(double p_168885_, double p_168886_, double p_168887_) {
        if (p_168887_ > 0.0) {
            this.m_168908_(-1);
        } else if (p_168887_ < 0.0) {
            this.m_168908_(1);
        }
        return true;
    }

    public void m_168892_(T p_168893_) {
        List<T> $$1 = this.f_168861_.m_142477_();
        int $$2 = $$1.indexOf(p_168893_);
        if ($$2 != -1) {
            this.f_168859_ = $$2;
        }
        this.m_168905_(p_168893_);
    }

    private void m_168905_(T p_168906_) {
        Component $$1 = this.m_168910_(p_168906_);
        this.m_93666_($$1);
        this.f_168860_ = p_168906_;
    }

    private Component m_168910_(T p_168911_) {
        return this.f_168866_ ? this.f_168862_.apply(p_168911_) : this.m_168912_(p_168911_);
    }

    private MutableComponent m_168912_(T p_168913_) {
        return CommonComponents.m_178393_(this.f_168858_, this.f_168862_.apply(p_168913_));
    }

    public T m_168883_() {
        return this.f_168860_;
    }

    @Override
    protected MutableComponent m_5646_() {
        return this.f_168863_.apply(this);
    }

    @Override
    public void m_142291_(NarrationElementOutput p_168889_) {
        p_168889_.m_169146_(NarratedElementType.TITLE, this.m_5646_());
        if (this.f_93623_) {
            T $$1 = this.m_168914_(1);
            Component $$2 = this.m_168910_($$1);
            if (this.m_93696_()) {
                p_168889_.m_169146_(NarratedElementType.USAGE, Component.m_237110_("narration.cycle_button.usage.focused", $$2));
            } else {
                p_168889_.m_169146_(NarratedElementType.USAGE, Component.m_237110_("narration.cycle_button.usage.hovered", $$2));
            }
        }
    }

    public MutableComponent m_168904_() {
        return CycleButton.m_168799_(this.f_168866_ ? this.m_168912_(this.f_168860_) : this.m_6035_());
    }

    @Override
    public List<FormattedCharSequence> m_141932_() {
        return (List)this.f_168865_.apply(this.f_168860_);
    }

    public static <T> Builder<T> m_168894_(Function<T, Component> p_168895_) {
        return new Builder<T>(p_168895_);
    }

    public static Builder<Boolean> m_168896_(Component p_168897_, Component p_168898_) {
        return new Builder<Boolean>(p_168902_ -> p_168902_ != false ? p_168897_ : p_168898_).m_232502_(f_168857_);
    }

    public static Builder<Boolean> m_168919_() {
        return new Builder<Boolean>(p_168891_ -> p_168891_ != false ? CommonComponents.f_130653_ : CommonComponents.f_130654_).m_232502_(f_168857_);
    }

    public static Builder<Boolean> m_168916_(boolean p_168917_) {
        return CycleButton.m_168919_().m_168948_(p_168917_);
    }

    public static interface ValueListSupplier<T> {
        public List<T> m_142477_();

        public List<T> m_142478_();

        public static <T> ValueListSupplier<T> m_232504_(Collection<T> p_232505_) {
            ImmutableList $$1 = ImmutableList.copyOf(p_232505_);
            return new ValueListSupplier<T>((List)$$1){
                final /* synthetic */ List f_168974_;
                {
                    this.f_168974_ = list;
                }

                @Override
                public List<T> m_142477_() {
                    return this.f_168974_;
                }

                @Override
                public List<T> m_142478_() {
                    return this.f_168974_;
                }
            };
        }

        public static <T> ValueListSupplier<T> m_168970_(final BooleanSupplier p_168971_, List<T> p_168972_, List<T> p_168973_) {
            ImmutableList $$3 = ImmutableList.copyOf(p_168972_);
            ImmutableList $$4 = ImmutableList.copyOf(p_168973_);
            return new ValueListSupplier<T>((List)$$4, (List)$$3){
                final /* synthetic */ List f_168980_;
                final /* synthetic */ List f_168981_;
                {
                    this.f_168980_ = list;
                    this.f_168981_ = list2;
                }

                @Override
                public List<T> m_142477_() {
                    return p_168971_.getAsBoolean() ? this.f_168980_ : this.f_168981_;
                }

                @Override
                public List<T> m_142478_() {
                    return this.f_168981_;
                }
            };
        }
    }

    public static interface OnValueChange<T> {
        public void m_168965_(CycleButton<T> var1, T var2);
    }

    public static class Builder<T> {
        private int f_168920_;
        @Nullable
        private T f_168921_;
        private final Function<T, Component> f_168922_;
        private OptionInstance.TooltipSupplier<T> f_168923_ = p_168964_ -> ImmutableList.of();
        private Function<CycleButton<T>, MutableComponent> f_168924_ = CycleButton::m_168904_;
        private ValueListSupplier<T> f_168925_ = ValueListSupplier.m_232504_(ImmutableList.of());
        private boolean f_168926_;

        public Builder(Function<T, Component> p_168928_) {
            this.f_168922_ = p_168928_;
        }

        public Builder<T> m_232502_(Collection<T> p_232503_) {
            return this.m_232500_(ValueListSupplier.m_232504_(p_232503_));
        }

        @SafeVarargs
        public final Builder<T> m_168961_(T ... p_168962_) {
            return this.m_232502_((Collection<T>)ImmutableList.copyOf((Object[])p_168962_));
        }

        public Builder<T> m_168952_(List<T> p_168953_, List<T> p_168954_) {
            return this.m_232500_(ValueListSupplier.m_168970_(f_168856_, p_168953_, p_168954_));
        }

        public Builder<T> m_168955_(BooleanSupplier p_168956_, List<T> p_168957_, List<T> p_168958_) {
            return this.m_232500_(ValueListSupplier.m_168970_(p_168956_, p_168957_, p_168958_));
        }

        public Builder<T> m_232500_(ValueListSupplier<T> p_232501_) {
            this.f_168925_ = p_232501_;
            return this;
        }

        public Builder<T> m_232498_(OptionInstance.TooltipSupplier<T> p_232499_) {
            this.f_168923_ = p_232499_;
            return this;
        }

        public Builder<T> m_168948_(T p_168949_) {
            this.f_168921_ = p_168949_;
            int $$1 = this.f_168925_.m_142478_().indexOf(p_168949_);
            if ($$1 != -1) {
                this.f_168920_ = $$1;
            }
            return this;
        }

        public Builder<T> m_168959_(Function<CycleButton<T>, MutableComponent> p_168960_) {
            this.f_168924_ = p_168960_;
            return this;
        }

        public Builder<T> m_168929_() {
            this.f_168926_ = true;
            return this;
        }

        public CycleButton<T> m_168930_(int p_168931_, int p_168932_, int p_168933_, int p_168934_, Component p_168935_) {
            return this.m_168936_(p_168931_, p_168932_, p_168933_, p_168934_, p_168935_, (p_168946_, p_168947_) -> {});
        }

        public CycleButton<T> m_168936_(int p_168937_, int p_168938_, int p_168939_, int p_168940_, Component p_168941_, OnValueChange<T> p_168942_) {
            List<T> $$6 = this.f_168925_.m_142478_();
            if ($$6.isEmpty()) {
                throw new IllegalStateException("No values for cycle button");
            }
            T $$7 = this.f_168921_ != null ? this.f_168921_ : $$6.get(this.f_168920_);
            Component $$8 = this.f_168922_.apply($$7);
            Component $$9 = this.f_168926_ ? $$8 : CommonComponents.m_178393_(p_168941_, $$8);
            return new CycleButton<T>(p_168937_, p_168938_, p_168939_, p_168940_, $$9, p_168941_, this.f_168920_, $$7, this.f_168925_, this.f_168922_, this.f_168924_, p_168942_, this.f_168923_, this.f_168926_);
        }
    }
}

