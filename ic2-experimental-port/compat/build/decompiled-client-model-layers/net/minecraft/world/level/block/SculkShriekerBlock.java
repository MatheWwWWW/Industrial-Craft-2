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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkShriekerBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock {
    public static final BooleanProperty f_222152_ = BlockStateProperties.f_222996_;
    public static final BooleanProperty f_222153_ = BlockStateProperties.f_61362_;
    public static final BooleanProperty f_222154_ = BlockStateProperties.f_222997_;
    protected static final VoxelShape f_222155_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public static final double f_222156_ = f_222155_.m_83297_(Direction.Axis.Y);

    public SculkShriekerBlock(BlockBehaviour.Properties p_222159_) {
        super(p_222159_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_222152_, false)).m_61124_(f_222153_, false)).m_61124_(f_222154_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_222211_) {
        p_222211_.m_61104_(f_222152_);
        p_222211_.m_61104_(f_222153_);
        p_222211_.m_61104_(f_222154_);
    }

    @Override
    public void m_141947_(Level p_222177_, BlockPos p_222178_, BlockState p_222179_, Entity p_222180_) {
        if (p_222177_ instanceof ServerLevel) {
            ServerLevel $$4 = (ServerLevel)p_222177_;
            ServerPlayer $$5 = SculkShriekerBlockEntity.m_222861_(p_222180_);
            if ($$5 != null) {
                $$4.m_141902_(p_222178_, BlockEntityType.f_222759_).ifPresent(p_222163_ -> p_222163_.m_222841_($$4, $$5));
            }
        }
        super.m_141947_(p_222177_, p_222178_, p_222179_, p_222180_);
    }

    @Override
    public void m_6810_(BlockState p_222198_, Level p_222199_, BlockPos p_222200_, BlockState p_222201_, boolean p_222202_) {
        if (p_222199_ instanceof ServerLevel) {
            ServerLevel $$5 = (ServerLevel)p_222199_;
            if (p_222198_.m_61143_(f_222152_).booleanValue() && !p_222198_.m_60713_(p_222201_.m_60734_())) {
                $$5.m_141902_(p_222200_, BlockEntityType.f_222759_).ifPresent(p_222217_ -> p_222217_.m_222839_($$5));
            }
        }
        super.m_6810_(p_222198_, p_222199_, p_222200_, p_222201_, p_222202_);
    }

    @Override
    public void m_213897_(BlockState p_222187_, ServerLevel p_222188_, BlockPos p_222189_, RandomSource p_222190_) {
        if (p_222187_.m_61143_(f_222152_).booleanValue()) {
            p_222188_.m_7731_(p_222189_, (BlockState)p_222187_.m_61124_(f_222152_, false), 3);
            p_222188_.m_141902_(p_222189_, BlockEntityType.f_222759_).ifPresent(p_222169_ -> p_222169_.m_222839_(p_222188_));
        }
    }

    @Override
    public RenderShape m_7514_(BlockState p_222219_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape m_5939_(BlockState p_222225_, BlockGetter p_222226_, BlockPos p_222227_, CollisionContext p_222228_) {
        return f_222155_;
    }

    @Override
    public VoxelShape m_7952_(BlockState p_222221_, BlockGetter p_222222_, BlockPos p_222223_) {
        return f_222155_;
    }

    @Override
    public boolean m_7923_(BlockState p_222232_) {
        return true;
    }

    @Override
    @Nullable
    public BlockEntity m_142194_(BlockPos p_222213_, BlockState p_222214_) {
        return new SculkShriekerBlockEntity(p_222213_, p_222214_);
    }

    @Override
    public BlockState m_7417_(BlockState p_222204_, Direction p_222205_, BlockState p_222206_, LevelAccessor p_222207_, BlockPos p_222208_, BlockPos p_222209_) {
        if (p_222204_.m_61143_(f_222153_).booleanValue()) {
            p_222207_.m_186469_(p_222208_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_222207_));
        }
        return super.m_7417_(p_222204_, p_222205_, p_222206_, p_222207_, p_222208_, p_222209_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_222171_) {
        return (BlockState)this.m_49966_().m_61124_(f_222153_, p_222171_.m_43725_().m_6425_(p_222171_.m_8083_()).m_76152_() == Fluids.f_76193_);
    }

    @Override
    public FluidState m_5888_(BlockState p_222230_) {
        if (p_222230_.m_61143_(f_222153_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_222230_);
    }

    @Override
    public void m_213646_(BlockState p_222192_, ServerLevel p_222193_, BlockPos p_222194_, ItemStack p_222195_, boolean p_222196_) {
        super.m_213646_(p_222192_, p_222193_, p_222194_, p_222195_, p_222196_);
        if (p_222196_) {
            this.m_220822_(p_222193_, p_222194_, p_222195_, ConstantInt.m_146483_(5));
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> GameEventListener m_214009_(ServerLevel p_222165_, T p_222166_) {
        if (p_222166_ instanceof SculkShriekerBlockEntity) {
            SculkShriekerBlockEntity $$2 = (SculkShriekerBlockEntity)p_222166_;
            return $$2.m_222879_();
        }
        return null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_222173_, BlockState p_222174_, BlockEntityType<T> p_222175_) {
        if (!p_222173_.f_46443_) {
            return BaseEntityBlock.m_152132_(p_222175_, BlockEntityType.f_222759_, (p_222182_, p_222183_, p_222184_, p_222185_) -> p_222185_.m_222879_().m_157898_(p_222182_));
        }
        return null;
    }
}

