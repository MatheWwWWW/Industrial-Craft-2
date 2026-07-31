/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state.pattern;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockInWorld {
    private final LevelReader f_61158_;
    private final BlockPos f_61159_;
    private final boolean f_61160_;
    @Nullable
    private BlockState f_61161_;
    @Nullable
    private BlockEntity f_61162_;
    private boolean f_61163_;

    public BlockInWorld(LevelReader p_61165_, BlockPos p_61166_, boolean p_61167_) {
        this.f_61158_ = p_61165_;
        this.f_61159_ = p_61166_.m_7949_();
        this.f_61160_ = p_61167_;
    }

    public BlockState m_61168_() {
        if (this.f_61161_ == null && (this.f_61160_ || this.f_61158_.m_46805_(this.f_61159_))) {
            this.f_61161_ = this.f_61158_.m_8055_(this.f_61159_);
        }
        return this.f_61161_;
    }

    @Nullable
    public BlockEntity m_61174_() {
        if (this.f_61162_ == null && !this.f_61163_) {
            this.f_61162_ = this.f_61158_.m_7702_(this.f_61159_);
            this.f_61163_ = true;
        }
        return this.f_61162_;
    }

    public LevelReader m_61175_() {
        return this.f_61158_;
    }

    public BlockPos m_61176_() {
        return this.f_61159_;
    }

    public static Predicate<BlockInWorld> m_61169_(Predicate<BlockState> p_61170_) {
        return p_61173_ -> p_61173_ != null && p_61170_.test(p_61173_.m_61168_());
    }
}

