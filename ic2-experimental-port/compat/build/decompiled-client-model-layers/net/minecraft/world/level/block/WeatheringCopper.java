/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.ImmutableBiMap
 */
package net.minecraft.world.level.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface WeatheringCopper
extends ChangeOverTimeBlock<WeatherState> {
    public static final Supplier<BiMap<Block, Block>> f_154886_ = Suppliers.memoize(() -> ImmutableBiMap.builder().put((Object)Blocks.f_152504_, (Object)Blocks.f_152503_).put((Object)Blocks.f_152503_, (Object)Blocks.f_152502_).put((Object)Blocks.f_152502_, (Object)Blocks.f_152501_).put((Object)Blocks.f_152510_, (Object)Blocks.f_152509_).put((Object)Blocks.f_152509_, (Object)Blocks.f_152508_).put((Object)Blocks.f_152508_, (Object)Blocks.f_152507_).put((Object)Blocks.f_152570_, (Object)Blocks.f_152569_).put((Object)Blocks.f_152569_, (Object)Blocks.f_152568_).put((Object)Blocks.f_152568_, (Object)Blocks.f_152567_).put((Object)Blocks.f_152566_, (Object)Blocks.f_152565_).put((Object)Blocks.f_152565_, (Object)Blocks.f_152564_).put((Object)Blocks.f_152564_, (Object)Blocks.f_152563_).build());
    public static final Supplier<BiMap<Block, Block>> f_154887_ = Suppliers.memoize(() -> f_154886_.get().inverse());

    public static Optional<Block> m_154890_(Block p_154891_) {
        return Optional.ofNullable((Block)f_154887_.get().get((Object)p_154891_));
    }

    public static Block m_154897_(Block p_154898_) {
        Block $$1 = p_154898_;
        Block $$2 = (Block)f_154887_.get().get((Object)$$1);
        while ($$2 != null) {
            $$1 = $$2;
            $$2 = (Block)f_154887_.get().get((Object)$$1);
        }
        return $$1;
    }

    public static Optional<BlockState> m_154899_(BlockState p_154900_) {
        return WeatheringCopper.m_154890_(p_154900_.m_60734_()).map(p_154903_ -> p_154903_.m_152465_(p_154900_));
    }

    public static Optional<Block> m_154904_(Block p_154905_) {
        return Optional.ofNullable((Block)f_154886_.get().get((Object)p_154905_));
    }

    public static BlockState m_154906_(BlockState p_154907_) {
        return WeatheringCopper.m_154897_(p_154907_.m_60734_()).m_152465_(p_154907_);
    }

    @Override
    default public Optional<BlockState> m_142123_(BlockState p_154893_) {
        return WeatheringCopper.m_154904_(p_154893_.m_60734_()).map(p_154896_ -> p_154896_.m_152465_(p_154893_));
    }

    @Override
    default public float m_142377_() {
        if (this.m_142297_() == WeatherState.UNAFFECTED) {
            return 0.75f;
        }
        return 1.0f;
    }

    public static final class WeatherState
    extends Enum<WeatherState> {
        public static final /* enum */ WeatherState UNAFFECTED = new WeatherState();
        public static final /* enum */ WeatherState EXPOSED = new WeatherState();
        public static final /* enum */ WeatherState WEATHERED = new WeatherState();
        public static final /* enum */ WeatherState OXIDIZED = new WeatherState();
        private static final /* synthetic */ WeatherState[] $VALUES;

        public static WeatherState[] values() {
            return (WeatherState[])$VALUES.clone();
        }

        public static WeatherState valueOf(String p_154921_) {
            return Enum.valueOf(WeatherState.class, p_154921_);
        }

        private static /* synthetic */ WeatherState[] m_154919_() {
            return new WeatherState[]{UNAFFECTED, EXPOSED, WEATHERED, OXIDIZED};
        }

        static {
            $VALUES = WeatherState.m_154919_();
        }
    }
}

