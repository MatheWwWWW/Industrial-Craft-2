/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public abstract class TreeDecorator {
    public static final Codec<TreeDecorator> f_70021_ = Registry.f_122860_.m_194605_().dispatch(TreeDecorator::m_6663_, TreeDecoratorType::m_70051_);

    protected abstract TreeDecoratorType<?> m_6663_();

    public abstract void m_214187_(Context var1);

    public static final class Context {
        private final LevelSimulatedReader f_226045_;
        private final BiConsumer<BlockPos, BlockState> f_226046_;
        private final RandomSource f_226047_;
        private final ObjectArrayList<BlockPos> f_226048_;
        private final ObjectArrayList<BlockPos> f_226049_;
        private final ObjectArrayList<BlockPos> f_226050_;

        public Context(LevelSimulatedReader p_226052_, BiConsumer<BlockPos, BlockState> p_226053_, RandomSource p_226054_, Set<BlockPos> p_226055_, Set<BlockPos> p_226056_, Set<BlockPos> p_226057_) {
            this.f_226045_ = p_226052_;
            this.f_226046_ = p_226053_;
            this.f_226047_ = p_226054_;
            this.f_226050_ = new ObjectArrayList(p_226057_);
            this.f_226048_ = new ObjectArrayList(p_226055_);
            this.f_226049_ = new ObjectArrayList(p_226056_);
            this.f_226048_.sort(Comparator.comparingInt(Vec3i::m_123342_));
            this.f_226049_.sort(Comparator.comparingInt(Vec3i::m_123342_));
            this.f_226050_.sort(Comparator.comparingInt(Vec3i::m_123342_));
        }

        public void m_226064_(BlockPos p_226065_, BooleanProperty p_226066_) {
            this.m_226061_(p_226065_, (BlockState)Blocks.f_50191_.m_49966_().m_61124_(p_226066_, true));
        }

        public void m_226061_(BlockPos p_226062_, BlockState p_226063_) {
            this.f_226046_.accept(p_226062_, p_226063_);
        }

        public boolean m_226059_(BlockPos p_226060_) {
            return this.f_226045_.m_7433_(p_226060_, BlockBehaviour.BlockStateBase::m_60795_);
        }

        public LevelSimulatedReader m_226058_() {
            return this.f_226045_;
        }

        public RandomSource m_226067_() {
            return this.f_226047_;
        }

        public ObjectArrayList<BlockPos> m_226068_() {
            return this.f_226048_;
        }

        public ObjectArrayList<BlockPos> m_226069_() {
            return this.f_226049_;
        }

        public ObjectArrayList<BlockPos> m_226070_() {
            return this.f_226050_;
        }
    }
}

