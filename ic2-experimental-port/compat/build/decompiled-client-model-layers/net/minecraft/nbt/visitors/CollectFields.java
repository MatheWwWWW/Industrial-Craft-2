/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 */
package net.minecraft.nbt.visitors;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.visitors.CollectToTag;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.nbt.visitors.FieldTree;

public class CollectFields
extends CollectToTag {
    private int f_197602_;
    private final Set<TagType<?>> f_197603_;
    private final Deque<FieldTree> f_197604_ = new ArrayDeque<FieldTree>();

    public CollectFields(FieldSelector ... p_202496_) {
        this.f_197602_ = p_202496_.length;
        ImmutableSet.Builder $$1 = ImmutableSet.builder();
        FieldTree $$2 = FieldTree.m_202532_();
        for (FieldSelector $$3 : p_202496_) {
            $$2.m_202538_($$3);
            $$1.add($$3.f_202498_());
        }
        this.f_197604_.push($$2);
        $$1.add(CompoundTag.f_128326_);
        this.f_197603_ = $$1.build();
    }

    @Override
    public StreamTagVisitor.ValueResult m_196213_(TagType<?> p_197614_) {
        if (p_197614_ != CompoundTag.f_128326_) {
            return StreamTagVisitor.ValueResult.HALT;
        }
        return super.m_196213_(p_197614_);
    }

    @Override
    public StreamTagVisitor.EntryResult m_196214_(TagType<?> p_197608_) {
        FieldTree $$1 = this.f_197604_.element();
        if (this.m_197714_() > $$1.f_202523_()) {
            return super.m_196214_(p_197608_);
        }
        if (this.f_197602_ <= 0) {
            return StreamTagVisitor.EntryResult.HALT;
        }
        if (!this.f_197603_.contains(p_197608_)) {
            return StreamTagVisitor.EntryResult.SKIP;
        }
        return super.m_196214_(p_197608_);
    }

    @Override
    public StreamTagVisitor.EntryResult m_196425_(TagType<?> p_197610_, String p_197611_) {
        FieldTree $$3;
        FieldTree $$2 = this.f_197604_.element();
        if (this.m_197714_() > $$2.f_202523_()) {
            return super.m_196425_(p_197610_, p_197611_);
        }
        if ($$2.f_202524_().remove(p_197611_, p_197610_)) {
            --this.f_197602_;
            return super.m_196425_(p_197610_, p_197611_);
        }
        if (p_197610_ == CompoundTag.f_128326_ && ($$3 = $$2.f_202525_().get(p_197611_)) != null) {
            this.f_197604_.push($$3);
            return super.m_196425_(p_197610_, p_197611_);
        }
        return StreamTagVisitor.EntryResult.SKIP;
    }

    @Override
    public StreamTagVisitor.ValueResult m_196527_() {
        if (this.m_197714_() == this.f_197604_.element().f_202523_()) {
            this.f_197604_.pop();
        }
        return super.m_196527_();
    }

    public int m_197615_() {
        return this.f_197602_;
    }
}

