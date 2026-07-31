/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state.predicate;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPredicate
implements Predicate<BlockState> {
    private final Block f_61272_;

    public BlockPredicate(Block p_61274_) {
        this.f_61272_ = p_61274_;
    }

    public static BlockPredicate m_61275_(Block p_61276_) {
        return new BlockPredicate(p_61276_);
    }

    @Override
    public boolean test(@Nullable BlockState p_61278_) {
        return p_61278_ != null && p_61278_.m_60713_(this.f_61272_);
    }

    @Override
    public /* synthetic */ boolean test(@Nullable Object object) {
        return this.test((BlockState)object);
    }
}

