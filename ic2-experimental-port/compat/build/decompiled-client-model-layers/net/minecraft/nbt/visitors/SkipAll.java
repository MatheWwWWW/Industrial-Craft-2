/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt.visitors;

import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;

public interface SkipAll
extends StreamTagVisitor {
    public static final SkipAll f_197715_ = new SkipAll(){};

    @Override
    default public StreamTagVisitor.ValueResult m_196525_() {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196458_(String p_197729_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196209_(byte p_197719_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196553_(short p_197739_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196353_(int p_197725_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196295_(long p_197727_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196532_(float p_197723_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196455_(double p_197721_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196152_(byte[] p_197741_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196376_(int[] p_197743_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196280_(long[] p_197745_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196339_(TagType<?> p_197733_, int p_197734_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.EntryResult m_196338_(TagType<?> p_197750_, int p_197751_) {
        return StreamTagVisitor.EntryResult.SKIP;
    }

    @Override
    default public StreamTagVisitor.EntryResult m_196214_(TagType<?> p_197731_) {
        return StreamTagVisitor.EntryResult.SKIP;
    }

    @Override
    default public StreamTagVisitor.EntryResult m_196425_(TagType<?> p_197736_, String p_197737_) {
        return StreamTagVisitor.EntryResult.SKIP;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196527_() {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }

    @Override
    default public StreamTagVisitor.ValueResult m_196213_(TagType<?> p_197748_) {
        return StreamTagVisitor.ValueResult.CONTINUE;
    }
}

