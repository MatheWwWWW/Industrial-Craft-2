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
import net.minecraft.nbt.TagType;

public class TagTypes {
    private static final TagType<?>[] f_129395_ = new TagType[]{EndTag.f_128533_, ByteTag.f_128255_, ShortTag.f_129244_, IntTag.f_128670_, LongTag.f_128873_, FloatTag.f_128560_, DoubleTag.f_128494_, ByteArrayTag.f_128185_, StringTag.f_129288_, ListTag.f_128714_, CompoundTag.f_128326_, IntArrayTag.f_128599_, LongArrayTag.f_128800_};

    public static TagType<?> m_129397_(int p_129398_) {
        if (p_129398_ < 0 || p_129398_ >= f_129395_.length) {
            return TagType.m_129377_(p_129398_);
        }
        return f_129395_[p_129398_];
    }
}

