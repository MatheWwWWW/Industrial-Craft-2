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
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
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
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

public class WrappedMinMaxBounds {
    public static final WrappedMinMaxBounds f_75350_ = new WrappedMinMaxBounds(null, null);
    public static final SimpleCommandExceptionType f_75351_ = new SimpleCommandExceptionType((Message)Component.m_237115_("argument.range.ints"));
    @Nullable
    private final Float f_75352_;
    @Nullable
    private final Float f_75353_;

    public WrappedMinMaxBounds(@Nullable Float p_75356_, @Nullable Float p_75357_) {
        this.f_75352_ = p_75356_;
        this.f_75353_ = p_75357_;
    }

    public static WrappedMinMaxBounds m_164402_(float p_164403_) {
        return new WrappedMinMaxBounds(Float.valueOf(p_164403_), Float.valueOf(p_164403_));
    }

    public static WrappedMinMaxBounds m_164404_(float p_164405_, float p_164406_) {
        return new WrappedMinMaxBounds(Float.valueOf(p_164405_), Float.valueOf(p_164406_));
    }

    public static WrappedMinMaxBounds m_164414_(float p_164415_) {
        return new WrappedMinMaxBounds(Float.valueOf(p_164415_), null);
    }

    public static WrappedMinMaxBounds m_164417_(float p_164418_) {
        return new WrappedMinMaxBounds(null, Float.valueOf(p_164418_));
    }

    public boolean m_164419_(float p_164420_) {
        if (this.f_75352_ != null && this.f_75353_ != null && this.f_75352_.floatValue() > this.f_75353_.floatValue() && this.f_75352_.floatValue() > p_164420_ && this.f_75353_.floatValue() < p_164420_) {
            return false;
        }
        if (this.f_75352_ != null && this.f_75352_.floatValue() > p_164420_) {
            return false;
        }
        return this.f_75353_ == null || !(this.f_75353_.floatValue() < p_164420_);
    }

    public boolean m_164400_(double p_164401_) {
        if (this.f_75352_ != null && this.f_75353_ != null && this.f_75352_.floatValue() > this.f_75353_.floatValue() && (double)(this.f_75352_.floatValue() * this.f_75352_.floatValue()) > p_164401_ && (double)(this.f_75353_.floatValue() * this.f_75353_.floatValue()) < p_164401_) {
            return false;
        }
        if (this.f_75352_ != null && (double)(this.f_75352_.floatValue() * this.f_75352_.floatValue()) > p_164401_) {
            return false;
        }
        return this.f_75353_ == null || !((double)(this.f_75353_.floatValue() * this.f_75353_.floatValue()) < p_164401_);
    }

    @Nullable
    public Float m_75358_() {
        return this.f_75352_;
    }

    @Nullable
    public Float m_75366_() {
        return this.f_75353_;
    }

    public JsonElement m_164416_() {
        if (this == f_75350_) {
            return JsonNull.INSTANCE;
        }
        if (this.f_75352_ != null && this.f_75353_ != null && this.f_75352_.equals(this.f_75353_)) {
            return new JsonPrimitive((Number)this.f_75352_);
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_75352_ != null) {
            $$0.addProperty("min", (Number)this.f_75352_);
        }
        if (this.f_75353_ != null) {
            $$0.addProperty("max", (Number)this.f_75352_);
        }
        return $$0;
    }

    public static WrappedMinMaxBounds m_164407_(@Nullable JsonElement p_164408_) {
        if (p_164408_ == null || p_164408_.isJsonNull()) {
            return f_75350_;
        }
        if (GsonHelper.m_13872_(p_164408_)) {
            float $$1 = GsonHelper.m_13888_(p_164408_, "value");
            return new WrappedMinMaxBounds(Float.valueOf($$1), Float.valueOf($$1));
        }
        JsonObject $$2 = GsonHelper.m_13918_(p_164408_, "value");
        Float $$3 = $$2.has("min") ? Float.valueOf(GsonHelper.m_13915_($$2, "min")) : null;
        Float $$4 = $$2.has("max") ? Float.valueOf(GsonHelper.m_13915_($$2, "max")) : null;
        return new WrappedMinMaxBounds($$3, $$4);
    }

    public static WrappedMinMaxBounds m_164409_(StringReader p_164410_, boolean p_164411_) throws CommandSyntaxException {
        return WrappedMinMaxBounds.m_75359_(p_164410_, p_164411_, p_164413_ -> p_164413_);
    }

    public static WrappedMinMaxBounds m_75359_(StringReader p_75360_, boolean p_75361_, Function<Float, Float> p_75362_) throws CommandSyntaxException {
        Float $$6;
        if (!p_75360_.canRead()) {
            throw MinMaxBounds.f_55297_.createWithContext((ImmutableStringReader)p_75360_);
        }
        int $$3 = p_75360_.getCursor();
        Float $$4 = WrappedMinMaxBounds.m_75363_(WrappedMinMaxBounds.m_75367_(p_75360_, p_75361_), p_75362_);
        if (p_75360_.canRead(2) && p_75360_.peek() == '.' && p_75360_.peek(1) == '.') {
            p_75360_.skip();
            p_75360_.skip();
            Float $$5 = WrappedMinMaxBounds.m_75363_(WrappedMinMaxBounds.m_75367_(p_75360_, p_75361_), p_75362_);
            if ($$4 == null && $$5 == null) {
                p_75360_.setCursor($$3);
                throw MinMaxBounds.f_55297_.createWithContext((ImmutableStringReader)p_75360_);
            }
        } else {
            if (!p_75361_ && p_75360_.canRead() && p_75360_.peek() == '.') {
                p_75360_.setCursor($$3);
                throw f_75351_.createWithContext((ImmutableStringReader)p_75360_);
            }
            $$6 = $$4;
        }
        if ($$4 == null && $$6 == null) {
            p_75360_.setCursor($$3);
            throw MinMaxBounds.f_55297_.createWithContext((ImmutableStringReader)p_75360_);
        }
        return new WrappedMinMaxBounds($$4, $$6);
    }

    @Nullable
    private static Float m_75367_(StringReader p_75368_, boolean p_75369_) throws CommandSyntaxException {
        int $$2 = p_75368_.getCursor();
        while (p_75368_.canRead() && WrappedMinMaxBounds.m_75370_(p_75368_, p_75369_)) {
            p_75368_.skip();
        }
        String $$3 = p_75368_.getString().substring($$2, p_75368_.getCursor());
        if ($$3.isEmpty()) {
            return null;
        }
        try {
            return Float.valueOf(Float.parseFloat($$3));
        }
        catch (NumberFormatException $$4) {
            if (p_75369_) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext((ImmutableStringReader)p_75368_, (Object)$$3);
            }
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext((ImmutableStringReader)p_75368_, (Object)$$3);
        }
    }

    private static boolean m_75370_(StringReader p_75371_, boolean p_75372_) {
        char $$2 = p_75371_.peek();
        if ($$2 >= '0' && $$2 <= '9' || $$2 == '-') {
            return true;
        }
        if (p_75372_ && $$2 == '.') {
            return !p_75371_.canRead(2) || p_75371_.peek(1) != '.';
        }
        return false;
    }

    @Nullable
    private static Float m_75363_(@Nullable Float p_75364_, Function<Float, Float> p_75365_) {
        return p_75364_ == null ? null : p_75365_.apply(p_75364_);
    }
}

