/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Streams
 */
package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.Streams;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class AndCondition
implements Condition {
    public static final String f_173499_ = "AND";
    private final Iterable<? extends Condition> f_111908_;

    public AndCondition(Iterable<? extends Condition> p_111910_) {
        this.f_111908_ = p_111910_;
    }

    @Override
    public Predicate<BlockState> m_7289_(StateDefinition<Block, BlockState> p_111921_) {
        List $$1 = Streams.stream(this.f_111908_).map(p_111916_ -> p_111916_.m_7289_(p_111921_)).collect(Collectors.toList());
        return p_111919_ -> $$1.stream().allMatch(p_173502_ -> p_173502_.test(p_111919_));
    }
}

