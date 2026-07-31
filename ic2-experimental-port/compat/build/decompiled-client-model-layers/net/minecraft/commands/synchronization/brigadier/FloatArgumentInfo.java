/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 */
package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;

public class FloatArgumentInfo
implements ArgumentTypeInfo<FloatArgumentType, Template> {
    @Override
    public void m_214155_(Template p_235518_, FriendlyByteBuf p_235519_) {
        boolean $$2 = p_235518_.f_235525_ != -3.4028235E38f;
        boolean $$3 = p_235518_.f_235526_ != Float.MAX_VALUE;
        p_235519_.writeByte(ArgumentUtils.m_235427_($$2, $$3));
        if ($$2) {
            p_235519_.writeFloat(p_235518_.f_235525_);
        }
        if ($$3) {
            p_235519_.writeFloat(p_235518_.f_235526_);
        }
    }

    @Override
    public Template m_213618_(FriendlyByteBuf p_235521_) {
        byte $$1 = p_235521_.readByte();
        float $$2 = ArgumentUtils.m_235402_($$1) ? p_235521_.readFloat() : -3.4028235E38f;
        float $$3 = ArgumentUtils.m_235430_($$1) ? p_235521_.readFloat() : Float.MAX_VALUE;
        return new Template($$2, $$3);
    }

    @Override
    public void m_213719_(Template p_235515_, JsonObject p_235516_) {
        if (p_235515_.f_235525_ != -3.4028235E38f) {
            p_235516_.addProperty("min", (Number)Float.valueOf(p_235515_.f_235525_));
        }
        if (p_235515_.f_235526_ != Float.MAX_VALUE) {
            p_235516_.addProperty("max", (Number)Float.valueOf(p_235515_.f_235526_));
        }
    }

    @Override
    public Template m_214163_(FloatArgumentType p_235507_) {
        return new Template(p_235507_.getMinimum(), p_235507_.getMaximum());
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
        return this.m_213618_(friendlyByteBuf);
    }

    public final class Template
    implements ArgumentTypeInfo.Template<FloatArgumentType> {
        final float f_235525_;
        final float f_235526_;

        Template(float p_235529_, float p_235530_) {
            this.f_235525_ = p_235529_;
            this.f_235526_ = p_235530_;
        }

        @Override
        public FloatArgumentType m_213879_(CommandBuildContext p_235533_) {
            return FloatArgumentType.floatArg((float)this.f_235525_, (float)this.f_235526_);
        }

        @Override
        public ArgumentTypeInfo<FloatArgumentType, ?> m_213709_() {
            return FloatArgumentInfo.this;
        }

        @Override
        public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
            return this.m_213879_(commandBuildContext);
        }
    }
}

