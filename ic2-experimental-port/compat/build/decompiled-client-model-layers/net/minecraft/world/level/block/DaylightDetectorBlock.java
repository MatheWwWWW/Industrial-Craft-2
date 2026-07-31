/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DaylightDetectorBlock
extends BaseEntityBlock {
    public static final IntegerProperty f_52377_ = BlockStateProperties.f_61426_;
    public static final BooleanProperty f_52378_ = BlockStateProperties.f_61441_;
    protected static final VoxelShape f_52379_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

    public DaylightDetectorBlock(BlockBehaviour.Properties p_52382_) {
        super(p_52382_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52377_, 0)).m_61124_(f_52378_, false));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52402_, BlockGetter p_52403_, BlockPos p_52404_, CollisionContext p_52405_) {
        return f_52379_;
    }

    @Override
    public boolean m_7923_(BlockState p_52409_) {
        return true;
    }

    @Override
    public int m_6378_(BlockState p_52386_, BlockGetter p_52387_, BlockPos p_52388_, Direction p_52389_) {
        return p_52386_.m_61143_(f_52377_);
    }

    private static void m_52410_(BlockState p_52411_, Level p_52412_, BlockPos p_52413_) {
        int $$3 = p_52412_.m_45517_(LightLayer.SKY, p_52413_) - p_52412_.m_7445_();
        float $$4 = p_52412_.m_46490_(1.0f);
        boolean $$5 = p_52411_.m_61143_(f_52378_);
        if ($$5) {
            $$3 = 15 - $$3;
        } else if ($$3 > 0) {
            float $$6 = $$4 < (float)Math.PI ? 0.0f : (float)Math.PI * 2;
            $$4 += ($$6 - $$4) * 0.2f;
            $$3 = Math.round((float)$$3 * Mth.m_14089_($$4));
        }
        $$3 = Mth.m_14045_($$3, 0, 15);
        if (p_52411_.m_61143_(f_52377_) != $$3) {
            p_52412_.m_7731_(p_52413_, (BlockState)p_52411_.m_61124_(f_52377_, $$3), 3);
        }
    }

    @Override
    public InteractionResult m_6227_(BlockState p_52391_, Level p_52392_, BlockPos p_52393_, Player p_52394_, InteractionHand p_52395_, BlockHitResult p_52396_) {
        if (p_52394_.m_36326_()) {
            if (p_52392_.f_46443_) {
                return InteractionResult.SUCCESS;
            }
            BlockState $$6 = (BlockState)p_52391_.m_61122_(f_52378_);
            p_52392_.m_7731_(p_52393_, $$6, 4);
            p_52392_.m_220407_(GameEvent.f_157792_, p_52393_, GameEvent.Context.m_223719_(p_52394_, $$6));
            DaylightDetectorBlock.m_52410_($$6, p_52392_, p_52393_);
            return InteractionResult.CONSUME;
        }
        return super.m_6227_(p_52391_, p_52392_, p_52393_, p_52394_, p_52395_, p_52396_);
    }

    @Override
    public RenderShape m_7514_(BlockState p_52400_) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean m_7899_(BlockState p_52407_) {
        return true;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153118_, BlockState p_153119_) {
        return new DaylightDetectorBlockEntity(p_153118_, p_153119_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153109_, BlockState p_153110_, BlockEntityType<T> p_153111_) {
        if (!p_153109_.f_46443_ && p_153109_.m_6042_().f_223549_()) {
            return DaylightDetectorBlock.m_152132_(p_153111_, BlockEntityType.f_58932_, DaylightDetectorBlock::m_153112_);
        }
        return null;
    }

    private static void m_153112_(Level p_153113_, BlockPos p_153114_, BlockState p_153115_, DaylightDetectorBlockEntity p_153116_) {
        if (p_153113_.m_46467_() % 20L == 0L) {
            DaylightDetectorBlock.m_52410_(p_153115_, p_153113_, p_153114_);
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52398_) {
        p_52398_.m_61104_(f_52377_, f_52378_);
    }
}

