/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneTorchBlock
extends TorchBlock {
    public static final BooleanProperty f_55674_ = BlockStateProperties.f_61443_;
    private static final Map<BlockGetter, List<Toggle>> f_55675_ = new WeakHashMap<BlockGetter, List<Toggle>>();
    public static final int f_154325_ = 60;
    public static final int f_154326_ = 8;
    public static final int f_154327_ = 160;
    private static final int f_154328_ = 2;

    protected RedstoneTorchBlock(BlockBehaviour.Properties p_55678_) {
        super(p_55678_, DustParticleOptions.f_123656_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55674_, true));
    }

    @Override
    public void m_6807_(BlockState p_55724_, Level p_55725_, BlockPos p_55726_, BlockState p_55727_, boolean p_55728_) {
        for (Direction $$5 : Direction.values()) {
            p_55725_.m_46672_(p_55726_.m_121945_($$5), this);
        }
    }

    @Override
    public void m_6810_(BlockState p_55706_, Level p_55707_, BlockPos p_55708_, BlockState p_55709_, boolean p_55710_) {
        if (p_55710_) {
            return;
        }
        for (Direction $$5 : Direction.values()) {
            p_55707_.m_46672_(p_55708_.m_121945_($$5), this);
        }
    }

    @Override
    public int m_6378_(BlockState p_55694_, BlockGetter p_55695_, BlockPos p_55696_, Direction p_55697_) {
        if (p_55694_.m_61143_(f_55674_).booleanValue() && Direction.UP != p_55697_) {
            return 15;
        }
        return 0;
    }

    protected boolean m_6918_(Level p_55681_, BlockPos p_55682_, BlockState p_55683_) {
        return p_55681_.m_46616_(p_55682_.m_7495_(), Direction.DOWN);
    }

    @Override
    public void m_213897_(BlockState p_221949_, ServerLevel p_221950_, BlockPos p_221951_, RandomSource p_221952_) {
        boolean $$4 = this.m_6918_(p_221950_, p_221951_, p_221949_);
        List<Toggle> $$5 = f_55675_.get(p_221950_);
        while ($$5 != null && !$$5.isEmpty() && p_221950_.m_46467_() - $$5.get((int)0).f_55732_ > 60L) {
            $$5.remove(0);
        }
        if (p_221949_.m_61143_(f_55674_).booleanValue()) {
            if ($$4) {
                p_221950_.m_7731_(p_221951_, (BlockState)p_221949_.m_61124_(f_55674_, false), 3);
                if (RedstoneTorchBlock.m_55684_(p_221950_, p_221951_, true)) {
                    p_221950_.m_46796_(1502, p_221951_, 0);
                    p_221950_.m_186460_(p_221951_, p_221950_.m_8055_(p_221951_).m_60734_(), 160);
                }
            }
        } else if (!$$4 && !RedstoneTorchBlock.m_55684_(p_221950_, p_221951_, false)) {
            p_221950_.m_7731_(p_221951_, (BlockState)p_221949_.m_61124_(f_55674_, true), 3);
        }
    }

    @Override
    public void m_6861_(BlockState p_55699_, Level p_55700_, BlockPos p_55701_, Block p_55702_, BlockPos p_55703_, boolean p_55704_) {
        if (p_55699_.m_61143_(f_55674_).booleanValue() == this.m_6918_(p_55700_, p_55701_, p_55699_) && !p_55700_.m_183326_().m_183588_(p_55701_, this)) {
            p_55700_.m_186460_(p_55701_, this, 2);
        }
    }

    @Override
    public int m_6376_(BlockState p_55719_, BlockGetter p_55720_, BlockPos p_55721_, Direction p_55722_) {
        if (p_55722_ == Direction.DOWN) {
            return p_55719_.m_60746_(p_55720_, p_55721_, p_55722_);
        }
        return 0;
    }

    @Override
    public boolean m_7899_(BlockState p_55730_) {
        return true;
    }

    @Override
    public void m_214162_(BlockState p_221954_, Level p_221955_, BlockPos p_221956_, RandomSource p_221957_) {
        if (!p_221954_.m_61143_(f_55674_).booleanValue()) {
            return;
        }
        double $$4 = (double)p_221956_.m_123341_() + 0.5 + (p_221957_.m_188500_() - 0.5) * 0.2;
        double $$5 = (double)p_221956_.m_123342_() + 0.7 + (p_221957_.m_188500_() - 0.5) * 0.2;
        double $$6 = (double)p_221956_.m_123343_() + 0.5 + (p_221957_.m_188500_() - 0.5) * 0.2;
        p_221955_.m_7106_(this.f_57488_, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55717_) {
        p_55717_.m_61104_(f_55674_);
    }

    private static boolean m_55684_(Level p_55685_, BlockPos p_55686_, boolean p_55687_) {
        List $$3 = f_55675_.computeIfAbsent(p_55685_, p_55680_ -> Lists.newArrayList());
        if (p_55687_) {
            $$3.add(new Toggle(p_55686_.m_7949_(), p_55685_.m_46467_()));
        }
        int $$4 = 0;
        for (int $$5 = 0; $$5 < $$3.size(); ++$$5) {
            Toggle $$6 = (Toggle)$$3.get($$5);
            if (!$$6.f_55731_.equals(p_55686_) || ++$$4 < 8) continue;
            return true;
        }
        return false;
    }

    public static class Toggle {
        final BlockPos f_55731_;
        final long f_55732_;

        public Toggle(BlockPos p_55734_, long p_55735_) {
            this.f_55731_ = p_55734_;
            this.f_55732_ = p_55735_;
        }
    }
}

