/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;

public interface TagVisitor {
    public void m_142614_(StringTag var1);

    public void m_141946_(ByteTag var1);

    public void m_142183_(ShortTag var1);

    public void m_142045_(IntTag var1);

    public void m_142046_(LongTag var1);

    public void m_142181_(FloatTag var1);

    public void m_142121_(DoubleTag var1);

    public void m_142154_(ByteArrayTag var1);

    public void m_142251_(IntArrayTag var1);

    public void m_142309_(LongArrayTag var1);

    public void m_142447_(ListTag var1);

    public void m_142303_(CompoundTag var1);

    public void m_142384_(EndTag var1);
}

