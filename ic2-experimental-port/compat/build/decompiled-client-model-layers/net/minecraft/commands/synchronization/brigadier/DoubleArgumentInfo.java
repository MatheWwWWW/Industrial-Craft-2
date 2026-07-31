/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 */
package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;

public class DoubleArgumentInfo
implements ArgumentTypeInfo<DoubleArgumentType, Template> {
    @Override
    public void m_214155_(Template p_235485_, FriendlyByteBuf p_235486_) {
        boolean $$2 = p_235485_.f_235492_ != -1.7976931348623157E308;
        boolean $$3 = p_235485_.f_235493_ != Double.MAX_VALUE;
        p_235486_.writeByte(ArgumentUtils.m_235427_($$2, $$3));
        if ($$2) {
            p_235486_.writeDouble(p_235485_.f_235492_);
        }
        if ($$3) {
            p_235486_.writeDouble(p_235485_.f_235493_);
        }
    }

    @Override
    public Template m_213618_(FriendlyByteBuf p_235488_) {
        byte $$1 = p_235488_.readByte();
        double $$2 = ArgumentUtils.m_235402_($$1) ? p_235488_.readDouble() : -1.7976931348623157E308;
        double $$3 = ArgumentUtils.m_235430_($$1) ? p_235488_.readDouble() : Double.MAX_VALUE;
        return new Template($$2, $$3);
    }

    @Override
    public void m_213719_(Template p_235482_, JsonObject p_235483_) {
        if (p_235482_.f_235492_ != -1.7976931348623157E308) {
            p_235483_.addProperty("min", (Number)p_235482_.f_235492_);
        }
        if (p_235482_.f_235493_ != Double.MAX_VALUE) {
            p_235483_.addProperty("max", (Number)p_235482_.f_235493_);
        }
    }

    @Override
    public Template m_214163_(DoubleArgumentType p_235474_) {
        return new Template(p_235474_.getMinimum(), p_235474_.getMaximum());
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
        return this.m_213618_(friendlyByteBuf);
    }

    public final class Template
    implements ArgumentTypeInfo.Template<DoubleArgumentType> {
        final double f_235492_;
        final double f_235493_;

        Template(double p_235496_, double p_235497_) {
            this.f_235492_ = p_235496_;
            this.f_235493_ = p_235497_;
        }

        @Override
        public DoubleArgumentType m_213879_(CommandBuildContext p_235500_) {
            return DoubleArgumentType.doubleArg((double)this.f_235492_, (double)this.f_235493_);
        }

        @Override
        public ArgumentTypeInfo<DoubleArgumentType, ?> m_213709_() {
            return DoubleArgumentInfo.this;
        }

        @Override
        public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
            return this.m_213879_(commandBuildContext);
        }
    }
}

