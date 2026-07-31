/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package ic2.core.block.storage;

import ic2.api.util.IC2DamageSource;
import ic2.core.block.base.IStateController;
import ic2.core.block.base.blocks.BaseTexturedBlock;
import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.block.machines.BaseMachineBlock;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.utils.helpers.Tool;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChargePadBlock
extends BaseTexturedBlock
implements IStateController<BaseChargePadTileEntity>,
IBlockModifiers,
IBlockModel {
    public static final BooleanProperty ACTIVE = BaseMachineBlock.ACTIVE;
    public static final VoxelShape SHAPE = Block.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)4.0, (double)16.0);
    public static final AABB MODEL = new AABB(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    public static final BlockBehaviour.Properties CHARGE_PAD = BlockBehaviour.Properties.m_60939_((Material)Material.f_76279_).m_60918_(SoundType.f_56743_).m_60978_(1.5f).m_60999_();

    public ChargePadBlock(String blockName, BlockEntityType<?> type, ITextureProvider provider) {
        super(blockName, CHARGE_PAD, provider, type);
        this.setHarvestTool(Tool.PICKAXE.withLevel(1));
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(false)));
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{ACTIVE});
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public AABB getModelBounds(BlockState state) {
        return MODEL;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public void onStateUpdate(Level world, BlockPos pos, BlockState state, BaseChargePadTileEntity tile) {
        tile.setState((BlockState)state.m_61124_((Property)ACTIVE, (Comparable)Boolean.valueOf(tile.isActive())));
    }

    @Override
    public void m_214162_(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        BlockEntity tile;
        if (((Boolean)stateIn.m_61143_((Property)ACTIVE)).booleanValue() && (tile = worldIn.m_7702_(pos)) instanceof BaseChargePadTileEntity) {
            ((BaseChargePadTileEntity)tile).spawnParticles(rand);
        }
    }

    public void m_142072_(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (worldIn.f_46443_ || !(entityIn instanceof LivingEntity)) {
            return;
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (tile instanceof BaseChargePadTileEntity) {
            BaseChargePadTileEntity pad = (BaseChargePadTileEntity)tile;
            if (pad.data.getMaxImpact() < fallDistance) {
                entityIn.m_6469_((DamageSource)IC2DamageSource.ELECTRICITY, (float)(3 * pad.data.getBaseTier()));
            }
        }
    }

    public void m_141947_(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        if (worldIn.f_46443_ || !(entityIn instanceof LivingEntity)) {
            return;
        }
        BlockEntity tile = worldIn.m_7702_(pos);
        if (!(tile instanceof BaseChargePadTileEntity)) {
            return;
        }
        BaseChargePadTileEntity pad = (BaseChargePadTileEntity)tile;
        if (pad.inactiveTime > 0) {
            return;
        }
        if (pad.isActive()) {
            pad.activeTime = 20;
            return;
        }
        pad.setActive(true);
        pad.activeTime = 20;
        pad.tick();
    }

    @Override
    public boolean hasTextureRotation(BlockState state, Direction side) {
        return false;
    }

    @Override
    public int getTextureRotation(BlockState state, Direction side) {
        return 0;
    }

    @Override
    public boolean hasCustomTextureUVs(BlockState state, Direction side) {
        return side.m_122434_().m_122479_();
    }

    @Override
    public float[] getCustomTextureUVs(BlockState state, Direction side) {
        return new float[]{0.0f, 12.0f, 16.0f, 16.0f};
    }
}

