/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state.predicate;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStatePredicate
implements Predicate<BlockState> {
    public static final Predicate<BlockState> f_61281_ = p_61299_ -> true;
    private final StateDefinition<Block, BlockState> f_61282_;
    private final Map<Property<?>, Predicate<Object>> f_61283_ = Maps.newHashMap();

    private BlockStatePredicate(StateDefinition<Block, BlockState> p_61286_) {
        this.f_61282_ = p_61286_;
    }

    public static BlockStatePredicate m_61287_(Block p_61288_) {
        return new BlockStatePredicate(p_61288_.m_49965_());
    }

    @Override
    public boolean test(@Nullable BlockState p_61290_) {
        if (p_61290_ == null || !p_61290_.m_60734_().equals(this.f_61282_.m_61091_())) {
            return false;
        }
        if (this.f_61283_.isEmpty()) {
            return true;
        }
        for (Map.Entry<Property<?>, Predicate<Object>> $$1 : this.f_61283_.entrySet()) {
            if (this.m_61291_(p_61290_, $$1.getKey(), $$1.getValue())) continue;
            return false;
        }
        return true;
    }

    protected <T extends Comparable<T>> boolean m_61291_(BlockState p_61292_, Property<T> p_61293_, Predicate<Object> p_61294_) {
        T $$3 = p_61292_.m_61143_(p_61293_);
        return p_61294_.test($$3);
    }

    public <V extends Comparable<V>> BlockStatePredicate m_61295_(Property<V> p_61296_, Predicate<Object> p_61297_) {
        if (!this.f_61282_.m_61092_().contains(p_61296_)) {
            throw new IllegalArgumentException(this.f_61282_ + " cannot support property " + p_61296_);
        }
        this.f_61283_.put(p_61296_, p_61297_);
        return this;
    }

    @Override
    public /* synthetic */ boolean test(@Nullable Object object) {
        return this.test((BlockState)object);
    }
}

