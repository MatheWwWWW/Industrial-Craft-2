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

public class OrCondition
implements Condition {
    public static final String f_173510_ = "OR";
    private final Iterable<? extends Condition> f_112001_;

    public OrCondition(Iterable<? extends Condition> p_112003_) {
        this.f_112001_ = p_112003_;
    }

    @Override
    public Predicate<BlockState> m_7289_(StateDefinition<Block, BlockState> p_112014_) {
        List $$1 = Streams.stream(this.f_112001_).map(p_112009_ -> p_112009_.m_7289_(p_112014_)).collect(Collectors.toList());
        return p_112012_ -> $$1.stream().anyMatch(p_173513_ -> p_173513_.test(p_112012_));
    }
}

