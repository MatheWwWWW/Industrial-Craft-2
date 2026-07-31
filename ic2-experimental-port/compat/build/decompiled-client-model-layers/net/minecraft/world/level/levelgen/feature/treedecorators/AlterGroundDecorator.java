/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class AlterGroundDecorator
extends TreeDecorator {
    public static final Codec<AlterGroundDecorator> f_69302_ = BlockStateProvider.f_68747_.fieldOf("provider").xmap(AlterGroundDecorator::new, p_69327_ -> p_69327_.f_69303_).codec();
    private final BlockStateProvider f_69303_;

    public AlterGroundDecorator(BlockStateProvider p_69306_) {
        this.f_69303_ = p_69306_;
    }

    @Override
    protected TreeDecoratorType<?> m_6663_() {
        return TreeDecoratorType.f_70046_;
    }

    @Override
    public void m_214187_(TreeDecorator.Context p_225969_) {
        ArrayList $$1 = Lists.newArrayList();
        ObjectArrayList<BlockPos> $$2 = p_225969_.m_226070_();
        ObjectArrayList<BlockPos> $$3 = p_225969_.m_226068_();
        if ($$2.isEmpty()) {
            $$1.addAll($$3);
        } else if (!$$3.isEmpty() && ((BlockPos)$$2.get(0)).m_123342_() == ((BlockPos)$$3.get(0)).m_123342_()) {
            $$1.addAll($$3);
            $$1.addAll($$2);
        } else {
            $$1.addAll($$2);
        }
        if ($$1.isEmpty()) {
            return;
        }
        int $$4 = ((BlockPos)$$1.get(0)).m_123342_();
        $$1.stream().filter(p_69310_ -> p_69310_.m_123342_() == $$4).forEach(p_225978_ -> {
            this.m_225970_(p_225969_, p_225978_.m_122024_().m_122012_());
            this.m_225970_(p_225969_, p_225978_.m_122030_(2).m_122012_());
            this.m_225970_(p_225969_, p_225978_.m_122024_().m_122020_(2));
            this.m_225970_(p_225969_, p_225978_.m_122030_(2).m_122020_(2));
            for (int $$2 = 0; $$2 < 5; ++$$2) {
                int $$3 = p_225969_.m_226067_().m_188503_(64);
                int $$4 = $$3 % 8;
                int $$5 = $$3 / 8;
                if ($$4 != 0 && $$4 != 7 && $$5 != 0 && $$5 != 7) continue;
                this.m_225970_(p_225969_, p_225978_.m_7918_(-3 + $$4, 0, -3 + $$5));
            }
        });
    }

    private void m_225970_(TreeDecorator.Context p_225971_, BlockPos p_225972_) {
        for (int $$2 = -2; $$2 <= 2; ++$$2) {
            for (int $$3 = -2; $$3 <= 2; ++$$3) {
                if (Math.abs($$2) == 2 && Math.abs($$3) == 2) continue;
                this.m_225973_(p_225971_, p_225972_.m_7918_($$2, 0, $$3));
            }
        }
    }

    private void m_225973_(TreeDecorator.Context p_225974_, BlockPos p_225975_) {
        for (int $$2 = 2; $$2 >= -3; --$$2) {
            BlockPos $$3 = p_225975_.m_6630_($$2);
            if (Feature.m_65788_(p_225974_.m_226058_(), $$3)) {
                p_225974_.m_226061_($$3, this.f_69303_.m_213972_(p_225974_.m_226067_(), p_225975_));
                break;
            }
            if (!p_225974_.m_226059_($$3) && $$2 < 0) break;
        }
    }
}

