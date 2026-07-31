/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.commands;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.PlayerChatMessage;

public interface CommandSigningContext {
    public static final CommandSigningContext f_242494_ = new CommandSigningContext(){

        @Override
        @Nullable
        public PlayerChatMessage m_213987_(String p_242898_) {
            return null;
        }
    };

    @Nullable
    public PlayerChatMessage m_213987_(String var1);

    public record SignedArguments(Map<String, PlayerChatMessage> f_242498_) implements CommandSigningContext
    {
        @Override
        @Nullable
        public PlayerChatMessage m_213987_(String p_242852_) {
            return this.f_242498_.get(p_242852_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SignedArguments.class, "arguments", "f_242498_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SignedArguments.class, "arguments", "f_242498_"}, this);
        }

        @Override
        public final boolean equals(Object p_230600_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SignedArguments.class, "arguments", "f_242498_"}, this, p_230600_);
        }
    }
}

