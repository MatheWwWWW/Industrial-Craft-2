/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 */
package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.FriendlyByteBuf;

public interface ArgumentTypeInfo<A extends ArgumentType<?>, T extends Template<A>> {
    public void m_214155_(T var1, FriendlyByteBuf var2);

    public T m_213618_(FriendlyByteBuf var1);

    public void m_213719_(T var1, JsonObject var2);

    public T m_214163_(A var1);

    public static interface Template<A extends ArgumentType<?>> {
        public A m_213879_(CommandBuildContext var1);

        public ArgumentTypeInfo<A, ?> m_213709_();
    }
}

