/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.BuiltInExceptionProvider
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

public abstract class MinMaxBounds<T extends Number> {
    public static final SimpleCommandExceptionType f_55297_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.range.empty"));
    public static final SimpleCommandExceptionType f_55298_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.range.swapped"));
    @Nullable
    protected final T f_55299_;
    @Nullable
    protected final T f_55300_;

    protected MinMaxBounds(@Nullable T p_55303_, @Nullable T p_55304_) {
        this.f_55299_ = p_55303_;
        this.f_55300_ = p_55304_;
    }

    @Nullable
    public T m_55305_() {
        return this.f_55299_;
    }

    @Nullable
    public T m_55326_() {
        return this.f_55300_;
    }

    public boolean m_55327_() {
        return this.f_55299_ == null && this.f_55300_ == null;
    }

    public JsonElement m_55328_() {
        if (this.m_55327_()) {
            return JsonNull.INSTANCE;
        }
        if (this.f_55299_ != null && this.f_55299_.equals(this.f_55300_)) {
            return new JsonPrimitive(this.f_55299_);
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_55299_ != null) {
            $$0.addProperty("min", this.f_55299_);
        }
        if (this.f_55300_ != null) {
            $$0.addProperty("max", this.f_55300_);
        }
        return $$0;
    }

    protected static <T extends Number, R extends MinMaxBounds<T>> R m_55306_(@Nullable JsonElement p_55307_, R p_55308_, BiFunction<JsonElement, String, T> p_55309_, BoundsFactory<T, R> p_55310_) {
        if (p_55307_ == null || p_55307_.isJsonNull()) {
            return p_55308_;
        }
        if (GsonHelper.m_13872_(p_55307_)) {
            Number $$4 = (Number)p_55309_.apply(p_55307_, "value");
            return p_55310_.m_55329_($$4, $$4);
        }
        JsonObject $$5 = GsonHelper.m_13918_(p_55307_, "value");
        Number $$6 = $$5.has("min") ? (Number)((Number)p_55309_.apply($$5.get("min"), "min")) : (Number)null;
        Number $$7 = $$5.has("max") ? (Number)((Number)p_55309_.apply($$5.get("max"), "max")) : (Number)null;
        return p_55310_.m_55329_($$6, $$7);
    }

    protected static <T extends Number, R extends MinMaxBounds<T>> R m_55313_(StringReader p_55314_, BoundsFromReaderFactory<T, R> p_55315_, Function<String, T> p_55316_, Supplier<DynamicCommandExceptionType> p_55317_, Function<T, T> p_55318_) throws CommandSyntaxException {
        if (!p_55314_.canRead()) {
            throw f_55297_.createWithContext((ImmutableStringReader)p_55314_);
        }
        int $$5 = p_55314_.getCursor();
        try {
            Number $$8;
            Number $$6 = (Number)MinMaxBounds.m_55323_(MinMaxBounds.m_55319_(p_55314_, p_55316_, p_55317_), p_55318_);
            if (p_55314_.canRead(2) && p_55314_.peek() == '.' && p_55314_.peek(1) == '.') {
                p_55314_.skip();
                p_55314_.skip();
                Number $$7 = (Number)MinMaxBounds.m_55323_(MinMaxBounds.m_55319_(p_55314_, p_55316_, p_55317_), p_55318_);
                if ($$6 == null && $$7 == null) {
                    throw f_55297_.createWithContext((ImmutableStringReader)p_55314_);
                }
            } else {
                $$8 = $$6;
            }
            if ($$6 == null && $$8 == null) {
                throw f_55297_.createWithContext((ImmutableStringReader)p_55314_);
            }
            return p_55315_.m_55332_(p_55314_, $$6, $$8);
        }
        catch (CommandSyntaxException $$9) {
            p_55314_.setCursor($$5);
            throw new CommandSyntaxException($$9.getType(), $$9.getRawMessage(), $$9.getInput(), $$5);
        }
    }

    @Nullable
    private static <T extends Number> T m_55319_(StringReader p_55320_, Function<String, T> p_55321_, Supplier<DynamicCommandExceptionType> p_55322_) throws CommandSyntaxException {
        int $$3 = p_55320_.getCursor();
        while (p_55320_.canRead() && MinMaxBounds.m_55311_(p_55320_)) {
            p_55320_.skip();
        }
        String $$4 = p_55320_.getString().substring($$3, p_55320_.getCursor());
        if ($$4.isEmpty()) {
            return null;
        }
        try {
            return (T)((Number)p_55321_.apply($$4));
        }
        catch (NumberFormatException $$5) {
            throw p_55322_.get().createWithContext((ImmutableStringReader)p_55320_, (Object)$$4);
        }
    }

    private static boolean m_55311_(StringReader p_55312_) {
        char $$1 = p_55312_.peek();
        if ($$1 >= '0' && $$1 <= '9' || $$1 == '-') {
            return true;
        }
        if ($$1 == '.') {
            return !p_55312_.canRead(2) || p_55312_.peek(1) != '.';
        }
        return false;
    }

    @Nullable
    private static <T> T m_55323_(@Nullable T p_55324_, Function<T, T> p_55325_) {
        return p_55324_ == null ? null : (T)p_55325_.apply(p_55324_);
    }

    @FunctionalInterface
    protected static interface BoundsFactory<T extends Number, R extends MinMaxBounds<T>> {
        public R m_55329_(@Nullable T var1, @Nullable T var2);
    }

    @FunctionalInterface
    protected static interface BoundsFromReaderFactory<T extends Number, R extends MinMaxBounds<T>> {
        public R m_55332_(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
    }

    public static class Doubles
    extends MinMaxBounds<Double> {
        public static final Doubles f_154779_ = new Doubles(null, null);
        @Nullable
        private final Double f_154780_;
        @Nullable
        private final Double f_154781_;

        private static Doubles m_154795_(StringReader p_154796_, @Nullable Double p_154797_, @Nullable Double p_154798_) throws CommandSyntaxException {
            if (p_154797_ != null && p_154798_ != null && p_154797_ > p_154798_) {
                throw f_55298_.createWithContext((ImmutableStringReader)p_154796_);
            }
            return new Doubles(p_154797_, p_154798_);
        }

        @Nullable
        private static Double m_154802_(@Nullable Double p_154803_) {
            return p_154803_ == null ? null : Double.valueOf(p_154803_ * p_154803_);
        }

        private Doubles(@Nullable Double p_154784_, @Nullable Double p_154785_) {
            super(p_154784_, p_154785_);
            this.f_154780_ = Doubles.m_154802_(p_154784_);
            this.f_154781_ = Doubles.m_154802_(p_154785_);
        }

        public static Doubles m_154786_(double p_154787_) {
            return new Doubles(p_154787_, p_154787_);
        }

        public static Doubles m_154788_(double p_154789_, double p_154790_) {
            return new Doubles(p_154789_, p_154790_);
        }

        public static Doubles m_154804_(double p_154805_) {
            return new Doubles(p_154805_, null);
        }

        public static Doubles m_154808_(double p_154809_) {
            return new Doubles(null, p_154809_);
        }

        public boolean m_154810_(double p_154811_) {
            if (this.f_55299_ != null && (Double)this.f_55299_ > p_154811_) {
                return false;
            }
            return this.f_55300_ == null || !((Double)this.f_55300_ < p_154811_);
        }

        public boolean m_154812_(double p_154813_) {
            if (this.f_154780_ != null && this.f_154780_ > p_154813_) {
                return false;
            }
            return this.f_154781_ == null || !(this.f_154781_ < p_154813_);
        }

        public static Doubles m_154791_(@Nullable JsonElement p_154792_) {
            return Doubles.m_55306_(p_154792_, f_154779_, GsonHelper::m_144769_, Doubles::new);
        }

        public static Doubles m_154793_(StringReader p_154794_) throws CommandSyntaxException {
            return Doubles.m_154799_(p_154794_, p_154807_ -> p_154807_);
        }

        public static Doubles m_154799_(StringReader p_154800_, Function<Double, Double> p_154801_) throws CommandSyntaxException {
            return Doubles.m_55313_(p_154800_, Doubles::m_154795_, Double::parseDouble, () -> ((BuiltInExceptionProvider)CommandSyntaxException.BUILT_IN_EXCEPTIONS).readerInvalidDouble(), p_154801_);
        }
    }

    public static class Ints
    extends MinMaxBounds<Integer> {
        public static final Ints f_55364_ = new Ints(null, null);
        @Nullable
        private final Long f_55365_;
        @Nullable
        private final Long f_55366_;

        private static Ints m_55377_(StringReader p_55378_, @Nullable Integer p_55379_, @Nullable Integer p_55380_) throws CommandSyntaxException {
            if (p_55379_ != null && p_55380_ != null && p_55379_ > p_55380_) {
                throw f_55298_.createWithContext((ImmutableStringReader)p_55378_);
            }
            return new Ints(p_55379_, p_55380_);
        }

        @Nullable
        private static Long m_55384_(@Nullable Integer p_55385_) {
            return p_55385_ == null ? null : Long.valueOf(p_55385_.longValue() * p_55385_.longValue());
        }

        private Ints(@Nullable Integer p_55369_, @Nullable Integer p_55370_) {
            super(p_55369_, p_55370_);
            this.f_55365_ = Ints.m_55384_(p_55369_);
            this.f_55366_ = Ints.m_55384_(p_55370_);
        }

        public static Ints m_55371_(int p_55372_) {
            return new Ints(p_55372_, p_55372_);
        }

        public static Ints m_154814_(int p_154815_, int p_154816_) {
            return new Ints(p_154815_, p_154816_);
        }

        public static Ints m_55386_(int p_55387_) {
            return new Ints(p_55387_, null);
        }

        public static Ints m_154819_(int p_154820_) {
            return new Ints(null, p_154820_);
        }

        public boolean m_55390_(int p_55391_) {
            if (this.f_55299_ != null && (Integer)this.f_55299_ > p_55391_) {
                return false;
            }
            return this.f_55300_ == null || (Integer)this.f_55300_ >= p_55391_;
        }

        public boolean m_154817_(long p_154818_) {
            if (this.f_55365_ != null && this.f_55365_ > p_154818_) {
                return false;
            }
            return this.f_55366_ == null || this.f_55366_ >= p_154818_;
        }

        public static Ints m_55373_(@Nullable JsonElement p_55374_) {
            return Ints.m_55306_(p_55374_, f_55364_, GsonHelper::m_13897_, Ints::new);
        }

        public static Ints m_55375_(StringReader p_55376_) throws CommandSyntaxException {
            return Ints.m_55381_(p_55376_, p_55389_ -> p_55389_);
        }

        public static Ints m_55381_(StringReader p_55382_, Function<Integer, Integer> p_55383_) throws CommandSyntaxException {
            return Ints.m_55313_(p_55382_, Ints::m_55377_, Integer::parseInt, () -> ((BuiltInExceptionProvider)CommandSyntaxException.BUILT_IN_EXCEPTIONS).readerInvalidInt(), p_55383_);
        }
    }
}

