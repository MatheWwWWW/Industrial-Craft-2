/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringRepresentable;

public record ChatTypeDecoration(String f_238741_, List<Parameter> f_238656_, Style f_238694_) {
    public static final Codec<ChatTypeDecoration> f_238580_ = RecordCodecBuilder.create(p_239989_ -> p_239989_.group((App)Codec.STRING.fieldOf("translation_key").forGetter(ChatTypeDecoration::f_238741_), (App)Parameter.f_238794_.listOf().fieldOf("parameters").forGetter(ChatTypeDecoration::f_238656_), (App)Style.f_237254_.optionalFieldOf("style", (Object)Style.f_131099_).forGetter(ChatTypeDecoration::f_238694_)).apply((Applicative)p_239989_, ChatTypeDecoration::new));

    public static ChatTypeDecoration m_239222_(String p_239223_) {
        return new ChatTypeDecoration(p_239223_, List.of(Parameter.SENDER, Parameter.CONTENT), Style.f_131099_);
    }

    public static ChatTypeDecoration m_239424_(String p_239425_) {
        Style $$1 = Style.f_131099_.m_131140_(ChatFormatting.GRAY).m_131155_(true);
        return new ChatTypeDecoration(p_239425_, List.of(Parameter.SENDER, Parameter.CONTENT), $$1);
    }

    public static ChatTypeDecoration m_240709_(String p_240772_) {
        Style $$1 = Style.f_131099_.m_131140_(ChatFormatting.GRAY).m_131155_(true);
        return new ChatTypeDecoration(p_240772_, List.of(Parameter.TARGET, Parameter.CONTENT), $$1);
    }

    public static ChatTypeDecoration m_239094_(String p_239095_) {
        return new ChatTypeDecoration(p_239095_, List.of(Parameter.TARGET, Parameter.SENDER, Parameter.CONTENT), Style.f_131099_);
    }

    public Component m_240955_(Component p_241301_, ChatType.Bound p_241391_) {
        Object[] $$2 = this.m_241038_(p_241301_, p_241391_);
        return Component.m_237110_(this.f_238741_, $$2).m_130948_(this.f_238694_);
    }

    private Component[] m_241038_(Component p_241365_, ChatType.Bound p_241559_) {
        Component[] $$2 = new Component[this.f_238656_.size()];
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            Parameter $$4 = this.f_238656_.get($$3);
            $$2[$$3] = $$4.m_240974_(p_241365_, p_241559_);
        }
        return $$2;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChatTypeDecoration.class, "translationKey;parameters;style", "f_238741_", "f_238656_", "f_238694_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChatTypeDecoration.class, "translationKey;parameters;style", "f_238741_", "f_238656_", "f_238694_"}, this);
    }

    @Override
    public final boolean equals(Object p_239430_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChatTypeDecoration.class, "translationKey;parameters;style", "f_238741_", "f_238656_", "f_238694_"}, this, p_239430_);
    }

    public static final class Parameter
    extends Enum<Parameter>
    implements StringRepresentable {
        public static final /* enum */ Parameter SENDER = new Parameter("sender", (p_241238_, p_241239_) -> p_241239_.f_240886_());
        public static final /* enum */ Parameter TARGET = new Parameter("target", (p_241236_, p_241237_) -> p_241237_.f_240896_());
        public static final /* enum */ Parameter CONTENT = new Parameter("content", (p_239974_, p_241427_) -> p_239974_);
        public static final Codec<Parameter> f_238794_;
        private final String f_238673_;
        private final Selector f_238789_;
        private static final /* synthetic */ Parameter[] $VALUES;

        public static Parameter[] values() {
            return (Parameter[])$VALUES.clone();
        }

        public static Parameter valueOf(String p_239464_) {
            return Enum.valueOf(Parameter.class, p_239464_);
        }

        private Parameter(String p_239588_, Selector p_239589_) {
            this.f_238673_ = p_239588_;
            this.f_238789_ = p_239589_;
        }

        public Component m_240974_(Component p_241369_, ChatType.Bound p_241509_) {
            Component $$2 = this.f_238789_.m_239619_(p_241369_, p_241509_);
            return Objects.requireNonNullElse($$2, CommonComponents.f_237098_);
        }

        @Override
        public String m_7912_() {
            return this.f_238673_;
        }

        private static /* synthetic */ Parameter[] m_238947_() {
            return new Parameter[]{SENDER, TARGET, CONTENT};
        }

        static {
            $VALUES = Parameter.m_238947_();
            f_238794_ = StringRepresentable.m_216439_(Parameter::values);
        }

        public static interface Selector {
            @Nullable
            public Component m_239619_(Component var1, ChatType.Bound var2);
        }
    }
}

