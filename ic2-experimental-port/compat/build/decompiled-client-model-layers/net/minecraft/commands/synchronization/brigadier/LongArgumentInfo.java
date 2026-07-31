/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.LongArgumentType
 */
package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;

public class LongArgumentInfo
implements ArgumentTypeInfo<LongArgumentType, Template> {
    @Override
    public void m_214155_(Template p_235584_, FriendlyByteBuf p_235585_) {
        boolean $$2 = p_235584_.f_235591_ != Long.MIN_VALUE;
        boolean $$3 = p_235584_.f_235592_ != Long.MAX_VALUE;
        p_235585_.writeByte(ArgumentUtils.m_235427_($$2, $$3));
        if ($$2) {
            p_235585_.writeLong(p_235584_.f_235591_);
        }
        if ($$3) {
            p_235585_.writeLong(p_235584_.f_235592_);
        }
    }

    @Override
    public Template m_213618_(FriendlyByteBuf p_235587_) {
        byte $$1 = p_235587_.readByte();
        long $$2 = ArgumentUtils.m_235402_($$1) ? p_235587_.readLong() : Long.MIN_VALUE;
        long $$3 = ArgumentUtils.m_235430_($$1) ? p_235587_.readLong() : Long.MAX_VALUE;
        return new Template($$2, $$3);
    }

    @Override
    public void m_213719_(Template p_235581_, JsonObject p_235582_) {
        if (p_235581_.f_235591_ != Long.MIN_VALUE) {
            p_235582_.addProperty("min", (Number)p_235581_.f_235591_);
        }
        if (p_235581_.f_235592_ != Long.MAX_VALUE) {
            p_235582_.addProperty("max", (Number)p_235581_.f_235592_);
        }
    }

    @Override
    public Template m_214163_(LongArgumentType p_235573_) {
        return new Template(p_235573_.getMinimum(), p_235573_.getMaximum());
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
        return this.m_213618_(friendlyByteBuf);
    }

    public final class Template
    implements ArgumentTypeInfo.Template<LongArgumentType> {
        final long f_235591_;
        final long f_235592_;

        Template(long p_235595_, long p_235596_) {
            this.f_235591_ = p_235595_;
            this.f_235592_ = p_235596_;
        }

        @Override
        public LongArgumentType m_213879_(CommandBuildContext p_235599_) {
            return LongArgumentType.longArg((long)this.f_235591_, (long)this.f_235592_);
        }

        @Override
        public ArgumentTypeInfo<LongArgumentType, ?> m_213709_() {
            return LongArgumentInfo.this;
        }

        @Override
        public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
            return this.m_213879_(commandBuildContext);
        }
    }
}

