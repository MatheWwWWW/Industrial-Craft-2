/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt.visitors;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.visitors.CollectToTag;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.nbt.visitors.FieldTree;

public class SkipFields
extends CollectToTag {
    private final Deque<FieldTree> f_202547_ = new ArrayDeque<FieldTree>();

    public SkipFields(FieldSelector ... p_202549_) {
        FieldTree $$1 = FieldTree.m_202532_();
        for (FieldSelector $$2 : p_202549_) {
            $$1.m_202538_($$2);
        }
        this.f_202547_.push($$1);
    }

    @Override
    public StreamTagVisitor.EntryResult m_196425_(TagType<?> p_202551_, String p_202552_) {
        FieldTree $$3;
        FieldTree $$2 = this.f_202547_.element();
        if ($$2.m_202535_(p_202551_, p_202552_)) {
            return StreamTagVisitor.EntryResult.SKIP;
        }
        if (p_202551_ == CompoundTag.f_128326_ && ($$3 = $$2.f_202525_().get(p_202552_)) != null) {
            this.f_202547_.push($$3);
        }
        return super.m_196425_(p_202551_, p_202552_);
    }

    @Override
    public StreamTagVisitor.ValueResult m_196527_() {
        if (this.m_197714_() == this.f_202547_.element().f_202523_()) {
            this.f_202547_.pop();
        }
        return super.m_196527_();
    }
}

