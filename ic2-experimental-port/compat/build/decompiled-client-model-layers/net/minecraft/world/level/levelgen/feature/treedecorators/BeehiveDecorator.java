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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class BeehiveDecorator
extends TreeDecorator {
    public static final Codec<BeehiveDecorator> f_69954_ = Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("probability").xmap(BeehiveDecorator::new, p_69971_ -> Float.valueOf(p_69971_.f_69955_)).codec();
    private static final Direction f_202294_ = Direction.SOUTH;
    private static final Direction[] f_202295_ = (Direction[])Direction.Plane.HORIZONTAL.m_122557_().filter(p_202307_ -> p_202307_ != f_202294_.m_122424_()).toArray(Direction[]::new);
    private final float f_69955_;

    public BeehiveDecorator(float p_69958_) {
        this.f_69955_ = p_69958_;
    }

    @Override
    protected TreeDecoratorType<?> m_6663_() {
        return TreeDecoratorType.f_70045_;
    }

    @Override
    public void m_214187_(TreeDecorator.Context p_226019_) {
        RandomSource $$1 = p_226019_.m_226067_();
        if ($$1.m_188501_() >= this.f_69955_) {
            return;
        }
        ObjectArrayList<BlockPos> $$2 = p_226019_.m_226069_();
        ObjectArrayList<BlockPos> $$3 = p_226019_.m_226068_();
        int $$4 = !$$2.isEmpty() ? Math.max(((BlockPos)$$2.get(0)).m_123342_() - 1, ((BlockPos)$$3.get(0)).m_123342_() + 1) : Math.min(((BlockPos)$$3.get(0)).m_123342_() + 1 + $$1.m_188503_(3), ((BlockPos)$$3.get($$3.size() - 1)).m_123342_());
        List $$5 = $$3.stream().filter(p_202300_ -> p_202300_.m_123342_() == $$4).flatMap(p_202305_ -> Stream.of(f_202295_).map(p_202305_::m_121945_)).collect(Collectors.toList());
        if ($$5.isEmpty()) {
            return;
        }
        Collections.shuffle($$5);
        Optional<BlockPos> $$6 = $$5.stream().filter(p_226022_ -> p_226019_.m_226059_((BlockPos)p_226022_) && p_226019_.m_226059_(p_226022_.m_121945_(f_202294_))).findFirst();
        if ($$6.isEmpty()) {
            return;
        }
        p_226019_.m_226061_($$6.get(), (BlockState)Blocks.f_50717_.m_49966_().m_61124_(BeehiveBlock.f_49563_, f_202294_));
        p_226019_.m_226058_().m_141902_($$6.get(), BlockEntityType.f_58912_).ifPresent(p_226017_ -> {
            int $$2 = 2 + $$1.m_188503_(2);
            for (int $$3 = 0; $$3 < $$2; ++$$3) {
                CompoundTag $$4 = new CompoundTag();
                $$4.m_128359_("id", Registry.f_122826_.m_7981_(EntityType.f_20550_).toString());
                p_226017_.m_155157_($$4, $$1.m_188503_(599), false);
            }
        });
    }
}

