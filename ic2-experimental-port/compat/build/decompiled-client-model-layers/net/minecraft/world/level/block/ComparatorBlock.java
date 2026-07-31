/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;

public class ComparatorBlock
extends DiodeBlock
implements EntityBlock {
    public static final EnumProperty<ComparatorMode> f_51854_ = BlockStateProperties.f_61393_;

    public ComparatorBlock(BlockBehaviour.Properties p_51857_) {
        super(p_51857_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_52496_, false)).m_61124_(f_51854_, ComparatorMode.COMPARE));
    }

    @Override
    protected int m_6112_(BlockState p_51912_) {
        return 2;
    }

    @Override
    protected int m_5968_(BlockGetter p_51892_, BlockPos p_51893_, BlockState p_51894_) {
        BlockEntity $$3 = p_51892_.m_7702_(p_51893_);
        if ($$3 instanceof ComparatorBlockEntity) {
            return ((ComparatorBlockEntity)$$3).m_59182_();
        }
        return 0;
    }

    private int m_51903_(Level p_51904_, BlockPos p_51905_, BlockState p_51906_) {
        int $$3 = this.m_7312_(p_51904_, p_51905_, p_51906_);
        if ($$3 == 0) {
            return 0;
        }
        int $$4 = this.m_52547_(p_51904_, p_51905_, p_51906_);
        if ($$4 > $$3) {
            return 0;
        }
        if (p_51906_.m_61143_(f_51854_) == ComparatorMode.SUBTRACT) {
            return $$3 - $$4;
        }
        return $$3;
    }

    @Override
    protected boolean m_7320_(Level p_51861_, BlockPos p_51862_, BlockState p_51863_) {
        int $$3 = this.m_7312_(p_51861_, p_51862_, p_51863_);
        if ($$3 == 0) {
            return false;
        }
        int $$4 = this.m_52547_(p_51861_, p_51862_, p_51863_);
        if ($$3 > $$4) {
            return true;
        }
        return $$3 == $$4 && p_51863_.m_61143_(f_51854_) == ComparatorMode.COMPARE;
    }

    @Override
    protected int m_7312_(Level p_51896_, BlockPos p_51897_, BlockState p_51898_) {
        int $$3 = super.m_7312_(p_51896_, p_51897_, p_51898_);
        Direction $$4 = p_51898_.m_61143_(f_54117_);
        BlockPos $$5 = p_51897_.m_121945_($$4);
        BlockState $$6 = p_51896_.m_8055_($$5);
        if ($$6.m_60807_()) {
            $$3 = $$6.m_60674_(p_51896_, $$5);
        } else if ($$3 < 15 && $$6.m_60796_(p_51896_, $$5)) {
            $$5 = $$5.m_121945_($$4);
            $$6 = p_51896_.m_8055_($$5);
            ItemFrame $$7 = this.m_51864_(p_51896_, $$4, $$5);
            int $$8 = Math.max($$7 == null ? Integer.MIN_VALUE : $$7.m_31824_(), $$6.m_60807_() ? $$6.m_60674_(p_51896_, $$5) : Integer.MIN_VALUE);
            if ($$8 != Integer.MIN_VALUE) {
                $$3 = $$8;
            }
        }
        return $$3;
    }

    @Nullable
    private ItemFrame m_51864_(Level p_51865_, Direction p_51866_, BlockPos p_51867_) {
        List<ItemFrame> $$3 = p_51865_.m_6443_(ItemFrame.class, new AABB(p_51867_.m_123341_(), p_51867_.m_123342_(), p_51867_.m_123343_(), p_51867_.m_123341_() + 1, p_51867_.m_123342_() + 1, p_51867_.m_123343_() + 1), p_51890_ -> p_51890_ != null && p_51890_.m_6350_() == p_51866_);
        if ($$3.size() == 1) {
            return $$3.get(0);
        }
        return null;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51880_, Level p_51881_, BlockPos p_51882_, Player p_51883_, InteractionHand p_51884_, BlockHitResult p_51885_) {
        if (!p_51883_.m_150110_().f_35938_) {
            return InteractionResult.PASS;
        }
        float $$6 = (p_51880_ = (BlockState)p_51880_.m_61122_(f_51854_)).m_61143_(f_51854_) == ComparatorMode.SUBTRACT ? 0.55f : 0.5f;
        p_51881_.m_5594_(p_51883_, p_51882_, SoundEvents.f_11762_, SoundSource.BLOCKS, 0.3f, $$6);
        p_51881_.m_7731_(p_51882_, p_51880_, 2);
        this.m_51907_(p_51881_, p_51882_, p_51880_);
        return InteractionResult.m_19078_(p_51881_.f_46443_);
    }

    @Override
    protected void m_7321_(Level p_51900_, BlockPos p_51901_, BlockState p_51902_) {
        int $$5;
        if (p_51900_.m_183326_().m_183588_(p_51901_, this)) {
            return;
        }
        int $$3 = this.m_51903_(p_51900_, p_51901_, p_51902_);
        BlockEntity $$4 = p_51900_.m_7702_(p_51901_);
        int n = $$5 = $$4 instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)$$4).m_59182_() : 0;
        if ($$3 != $$5 || p_51902_.m_61143_(f_52496_).booleanValue() != this.m_7320_(p_51900_, p_51901_, p_51902_)) {
            TickPriority $$6 = this.m_52573_(p_51900_, p_51901_, p_51902_) ? TickPriority.HIGH : TickPriority.NORMAL;
            p_51900_.m_186464_(p_51901_, this, 2, $$6);
        }
    }

    private void m_51907_(Level p_51908_, BlockPos p_51909_, BlockState p_51910_) {
        int $$3 = this.m_51903_(p_51908_, p_51909_, p_51910_);
        BlockEntity $$4 = p_51908_.m_7702_(p_51909_);
        int $$5 = 0;
        if ($$4 instanceof ComparatorBlockEntity) {
            ComparatorBlockEntity $$6 = (ComparatorBlockEntity)$$4;
            $$5 = $$6.m_59182_();
            $$6.m_59175_($$3);
        }
        if ($$5 != $$3 || p_51910_.m_61143_(f_51854_) == ComparatorMode.COMPARE) {
            boolean $$7 = this.m_7320_(p_51908_, p_51909_, p_51910_);
            boolean $$8 = p_51910_.m_61143_(f_52496_);
            if ($$8 && !$$7) {
                p_51908_.m_7731_(p_51909_, (BlockState)p_51910_.m_61124_(f_52496_, false), 2);
            } else if (!$$8 && $$7) {
                p_51908_.m_7731_(p_51909_, (BlockState)p_51910_.m_61124_(f_52496_, true), 2);
            }
            this.m_52580_(p_51908_, p_51909_, p_51910_);
        }
    }

    @Override
    public void m_213897_(BlockState p_221010_, ServerLevel p_221011_, BlockPos p_221012_, RandomSource p_221013_) {
        this.m_51907_(p_221011_, p_221012_, p_221010_);
    }

    @Override
    public boolean m_8133_(BlockState p_51874_, Level p_51875_, BlockPos p_51876_, int p_51877_, int p_51878_) {
        super.m_8133_(p_51874_, p_51875_, p_51876_, p_51877_, p_51878_);
        BlockEntity $$5 = p_51875_.m_7702_(p_51876_);
        return $$5 != null && $$5.m_7531_(p_51877_, p_51878_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153086_, BlockState p_153087_) {
        return new ComparatorBlockEntity(p_153086_, p_153087_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51887_) {
        p_51887_.m_61104_(f_54117_, f_51854_, f_52496_);
    }
}

