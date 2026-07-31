/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LightBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final int f_153656_ = 15;
    public static final IntegerProperty f_153657_ = BlockStateProperties.f_61422_;
    public static final BooleanProperty f_153658_ = BlockStateProperties.f_61362_;
    public static final ToIntFunction<BlockState> f_153659_ = p_153701_ -> p_153701_.m_61143_(f_153657_);

    public LightBlock(BlockBehaviour.Properties p_153662_) {
        super(p_153662_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_153657_, 15)).m_61124_(f_153658_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153687_) {
        p_153687_.m_61104_(f_153657_, f_153658_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_153673_, Level p_153674_, BlockPos p_153675_, Player p_153676_, InteractionHand p_153677_, BlockHitResult p_153678_) {
        if (!p_153674_.f_46443_) {
            p_153674_.m_7731_(p_153675_, (BlockState)p_153673_.m_61122_(f_153657_), 2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_153668_, BlockGetter p_153669_, BlockPos p_153670_, CollisionContext p_153671_) {
        return p_153671_.m_7142_(Items.f_151033_) ? Shapes.m_83144_() : Shapes.m_83040_();
    }

    @Override
    public boolean m_7420_(BlockState p_153695_, BlockGetter p_153696_, BlockPos p_153697_) {
        return true;
    }

    @Override
    public RenderShape m_7514_(BlockState p_153693_) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public float m_7749_(BlockState p_153689_, BlockGetter p_153690_, BlockPos p_153691_) {
        return 1.0f;
    }

    @Override
    public BlockState m_7417_(BlockState p_153680_, Direction p_153681_, BlockState p_153682_, LevelAccessor p_153683_, BlockPos p_153684_, BlockPos p_153685_) {
        if (p_153680_.m_61143_(f_153658_).booleanValue()) {
            p_153683_.m_186469_(p_153684_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153683_));
        }
        return super.m_7417_(p_153680_, p_153681_, p_153682_, p_153683_, p_153684_, p_153685_);
    }

    @Override
    public FluidState m_5888_(BlockState p_153699_) {
        if (p_153699_.m_61143_(f_153658_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_153699_);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_153664_, BlockPos p_153665_, BlockState p_153666_) {
        ItemStack $$3 = super.m_7397_(p_153664_, p_153665_, p_153666_);
        if (p_153666_.m_61143_(f_153657_) != 15) {
            CompoundTag $$4 = new CompoundTag();
            $$4.m_128359_(f_153657_.m_61708_(), String.valueOf(p_153666_.m_61143_(f_153657_)));
            $$3.m_41700_("BlockStateTag", $$4);
        }
        return $$3;
    }
}

