/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 */
package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;

public class IntegerArgumentInfo
implements ArgumentTypeInfo<IntegerArgumentType, Template> {
    @Override
    public void m_214155_(Template p_235551_, FriendlyByteBuf p_235552_) {
        boolean $$2 = p_235551_.f_235558_ != Integer.MIN_VALUE;
        boolean $$3 = p_235551_.f_235559_ != Integer.MAX_VALUE;
        p_235552_.writeByte(ArgumentUtils.m_235427_($$2, $$3));
        if ($$2) {
            p_235552_.writeInt(p_235551_.f_235558_);
        }
        if ($$3) {
            p_235552_.writeInt(p_235551_.f_235559_);
        }
    }

    @Override
    public Template m_213618_(FriendlyByteBuf p_235554_) {
        byte $$1 = p_235554_.readByte();
        int $$2 = ArgumentUtils.m_235402_($$1) ? p_235554_.readInt() : Integer.MIN_VALUE;
        int $$3 = ArgumentUtils.m_235430_($$1) ? p_235554_.readInt() : Integer.MAX_VALUE;
        return new Template($$2, $$3);
    }

    @Override
    public void m_213719_(Template p_235548_, JsonObject p_235549_) {
        if (p_235548_.f_235558_ != Integer.MIN_VALUE) {
            p_235549_.addProperty("min", (Number)p_235548_.f_235558_);
        }
        if (p_235548_.f_235559_ != Integer.MAX_VALUE) {
            p_235549_.addProperty("max", (Number)p_235548_.f_235559_);
        }
    }

    @Override
    public Template m_214163_(IntegerArgumentType p_235540_) {
        return new Template(p_235540_.getMinimum(), p_235540_.getMaximum());
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
        return this.m_213618_(friendlyByteBuf);
    }

    public final class Template
    implements ArgumentTypeInfo.Template<IntegerArgumentType> {
        final int f_235558_;
        final int f_235559_;

        Template(int p_235562_, int p_235563_) {
            this.f_235558_ = p_235562_;
            this.f_235559_ = p_235563_;
        }

        @Override
        public IntegerArgumentType m_213879_(CommandBuildContext p_235566_) {
            return IntegerArgumentType.integer((int)this.f_235558_, (int)this.f_235559_);
        }

        @Override
        public ArgumentTypeInfo<IntegerArgumentType, ?> m_213709_() {
            return IntegerArgumentInfo.this;
        }

        @Override
        public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
            return this.m_213879_(commandBuildContext);
        }
    }
}

