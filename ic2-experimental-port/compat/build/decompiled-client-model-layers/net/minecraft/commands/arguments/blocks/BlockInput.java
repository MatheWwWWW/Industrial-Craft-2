/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.blocks;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockInput
implements Predicate<BlockInWorld> {
    private final BlockState f_114662_;
    private final Set<Property<?>> f_114663_;
    @Nullable
    private final CompoundTag f_114664_;

    public BlockInput(BlockState p_114666_, Set<Property<?>> p_114667_, @Nullable CompoundTag p_114668_) {
        this.f_114662_ = p_114666_;
        this.f_114663_ = p_114667_;
        this.f_114664_ = p_114668_;
    }

    public BlockState m_114669_() {
        return this.f_114662_;
    }

    public Set<Property<?>> m_173526_() {
        return this.f_114663_;
    }

    @Override
    public boolean test(BlockInWorld p_114675_) {
        BlockState $$1 = p_114675_.m_61168_();
        if (!$$1.m_60713_(this.f_114662_.m_60734_())) {
            return false;
        }
        for (Property<?> $$2 : this.f_114663_) {
            if ($$1.m_61143_($$2) == this.f_114662_.m_61143_($$2)) continue;
            return false;
        }
        if (this.f_114664_ != null) {
            BlockEntity $$3 = p_114675_.m_61174_();
            return $$3 != null && NbtUtils.m_129235_(this.f_114664_, $$3.m_187480_(), true);
        }
        return true;
    }

    public boolean m_173523_(ServerLevel p_173524_, BlockPos p_173525_) {
        return this.test(new BlockInWorld(p_173524_, p_173525_, false));
    }

    public boolean m_114670_(ServerLevel p_114671_, BlockPos p_114672_, int p_114673_) {
        BlockEntity $$4;
        BlockState $$3 = Block.m_49931_(this.f_114662_, p_114671_, p_114672_);
        if ($$3.m_60795_()) {
            $$3 = this.f_114662_;
        }
        if (!p_114671_.m_7731_(p_114672_, $$3, p_114673_)) {
            return false;
        }
        if (this.f_114664_ != null && ($$4 = p_114671_.m_7702_(p_114672_)) != null) {
            $$4.m_142466_(this.f_114664_);
        }
        return true;
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((BlockInWorld)object);
    }
}

