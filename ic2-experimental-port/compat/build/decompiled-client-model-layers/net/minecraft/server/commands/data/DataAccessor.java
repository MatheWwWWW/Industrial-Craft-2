/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.server.commands.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public interface DataAccessor {
    public void m_7603_(CompoundTag var1) throws CommandSyntaxException;

    public CompoundTag m_6184_() throws CommandSyntaxException;

    public Component m_6934_();

    public Component m_7624_(Tag var1);

    public Component m_6066_(NbtPathArgument.NbtPath var1, double var2, int var4);
}

