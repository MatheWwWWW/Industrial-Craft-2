/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType$StringType
 */
package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class StringArgumentSerializer
implements ArgumentTypeInfo<StringArgumentType, Template> {
    @Override
    public void m_214155_(Template p_235616_, FriendlyByteBuf p_235617_) {
        p_235617_.m_130068_((Enum<?>)p_235616_.f_235623_);
    }

    @Override
    public Template m_213618_(FriendlyByteBuf p_235619_) {
        StringArgumentType.StringType $$1 = p_235619_.m_130066_(StringArgumentType.StringType.class);
        return new Template($$1);
    }

    @Override
    public void m_213719_(Template p_235613_, JsonObject p_235614_) {
        p_235614_.addProperty("type", switch (p_235613_.f_235623_) {
            default -> throw new IncompatibleClassChangeError();
            case StringArgumentType.StringType.SINGLE_WORD -> "word";
            case StringArgumentType.StringType.QUOTABLE_PHRASE -> "phrase";
            case StringArgumentType.StringType.GREEDY_PHRASE -> "greedy";
        });
    }

    @Override
    public Template m_214163_(StringArgumentType p_235605_) {
        return new Template(p_235605_.getType());
    }

    @Override
    public /* synthetic */ ArgumentTypeInfo.Template m_213618_(FriendlyByteBuf friendlyByteBuf) {
        return this.m_213618_(friendlyByteBuf);
    }

    public final class Template
    implements ArgumentTypeInfo.Template<StringArgumentType> {
        final StringArgumentType.StringType f_235623_;

        public Template(StringArgumentType.StringType p_235626_) {
            this.f_235623_ = p_235626_;
        }

        @Override
        public StringArgumentType m_213879_(CommandBuildContext p_235629_) {
            return switch (this.f_235623_) {
                default -> throw new IncompatibleClassChangeError();
                case StringArgumentType.StringType.SINGLE_WORD -> StringArgumentType.word();
                case StringArgumentType.StringType.QUOTABLE_PHRASE -> StringArgumentType.string();
                case StringArgumentType.StringType.GREEDY_PHRASE -> StringArgumentType.greedyString();
            };
        }

        @Override
        public ArgumentTypeInfo<StringArgumentType, ?> m_213709_() {
            return StringArgumentSerializer.this;
        }

        @Override
        public /* synthetic */ ArgumentType m_213879_(CommandBuildContext commandBuildContext) {
            return this.m_213879_(commandBuildContext);
        }
    }
}

