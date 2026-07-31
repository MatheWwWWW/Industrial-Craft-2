/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.nbt.visitors;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import javax.annotation.Nullable;
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
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;

public class CollectToTag
implements StreamTagVisitor {
    private String f_197662_ = "";
    @Nullable
    private Tag f_197663_;
    private final Deque<Consumer<Tag>> f_197664_ = new ArrayDeque<Consumer<Tag>>();

    @Nullable
    public Tag m_197713_() {
        return this.f_197663_;
    }

    protected int m_197714_() {
        return this.f_197664_.size();
    }

    private void m_197682_(Tag p_197683_) {
        this.f_197664_.getLast().accept(p_197683_);
    }

    @Override
    public StreamTagVisitor.ValueResult m_196525_() {
        this.m_197682_(EndTag.f_128534_);
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196458_(String p_197678_) {
        this.m_197682_(StringTag.m_129297_(p_197678_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196209_(byte p_197668_) {
        this.m_197682_(ByteTag.m_128266_(p_197668_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196553_(short p_197693_) {
        this.m_197682_(ShortTag.m_129258_(p_197693_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196353_(int p_197674_) {
        this.m_197682_(IntTag.m_128679_(p_197674_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196295_(long p_197676_) {
        this.m_197682_(LongTag.m_128882_(p_197676_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196532_(float p_197672_) {
        this.m_197682_(FloatTag.m_128566_(p_197672_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196455_(double p_197670_) {
        this.m_197682_(DoubleTag.m_128500_(p_197670_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196152_(byte[] p_197695_) {
        this.m_197682_(new ByteArrayTag(p_197695_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196376_(int[] p_197697_) {
        this.m_197682_(new IntArrayTag(p_197697_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196280_(long[] p_197699_) {
        this.m_197682_(new LongArrayTag(p_197699_));
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196339_(TagType<?> p_197687_, int p_197688_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.EntryResult m_196338_(TagType<?> p_197709_, int p_197710_) {
        this.m_197711_(p_197709_);
        return StreamTagVisitor.EntryResult.ENTER;
    }

    @Override
    public StreamTagVisitor.EntryResult m_196214_(TagType<?> p_197685_) {
        return StreamTagVisitor.EntryResult.ENTER;
    }

    @Override
    public StreamTagVisitor.EntryResult m_196425_(TagType<?> p_197690_, String p_197691_) {
        this.f_197662_ = p_197691_;
        this.m_197711_(p_197690_);
        return StreamTagVisitor.EntryResult.ENTER;
    }

    private void m_197711_(TagType<?> p_197712_) {
        if (p_197712_ == ListTag.f_128714_) {
            ListTag $$1 = new ListTag();
            this.m_197682_($$1);
            this.f_197664_.addLast($$1::add);
        } else if (p_197712_ == CompoundTag.f_128326_) {
            CompoundTag $$2 = new CompoundTag();
            this.m_197682_($$2);
            this.f_197664_.addLast(p_197703_ -> $$2.m_128365_(this.f_197662_, (Tag)p_197703_));
        }
    }

    @Override
    public StreamTagVisitor.ValueResult m_196527_() {
        this.f_197664_.removeLast();
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196213_(TagType<?> p_197707_) {
        if (p_197707_ == ListTag.f_128714_) {
            ListTag $$1 = new ListTag();
            this.f_197663_ = $$1;
            this.f_197664_.addLast($$1::add);
        } else if (p_197707_ == CompoundTag.f_128326_) {
            CompoundTag $$2 = new CompoundTag();
            this.f_197663_ = $$2;
            this.f_197664_.addLast(p_197681_ -> $$2.m_128365_(this.f_197662_, (Tag)p_197681_));
        } else {
            this.f_197664_.addLast(p_197705_ -> {
                this.f_197663_ = p_197705_;
            });
        }
        return StreamTagVisitor.ValueResult.CONTINUE;
    }
}

