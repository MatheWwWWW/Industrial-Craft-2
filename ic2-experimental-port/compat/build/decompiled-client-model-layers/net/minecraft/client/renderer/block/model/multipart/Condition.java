/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.block.model.multipart;

import java.util.function.Predicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

@FunctionalInterface
public interface Condition {
    public static final Condition f_111922_ = p_111932_ -> p_173506_ -> true;
    public static final Condition f_111923_ = p_111928_ -> p_173504_ -> false;

    public Predicate<BlockState> m_7289_(StateDefinition<Block, BlockState> var1);
}

