/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleEngine
 *  net.minecraft.client.particle.TerrainParticle
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.extensions.common.IClientBlockExtensions
 *  net.minecraftforge.common.extensions.IForgeBlock
 */
package ic2.core.block.base;

import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.misc.color.IBlockColorListener;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.platform.rendering.features.ICamouflageRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface ICamouflageBlock
extends IForgeBlock,
IBlockColorListener,
ICamouflageRenderType {
    private Block self() {
        return (Block)this;
    }

    default public CamouflageStorage getTile(LevelAccessor world, BlockPos pos, Direction dir) {
        BlockEntity tile = world.m_7702_(pos);
        return tile instanceof ICamouflagable && ((ICamouflagable)tile).isSideEnabled(dir) ? ((ICamouflagable)tile).getStorage() : null;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    default public int getItemColor(ItemStack stack, int tintIndex) {
        return -1;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    default public int getBlockColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
        if (world == null || pos == null) {
            return -1;
        }
        BlockEntity tile = world.m_7702_(pos);
        if (tile instanceof ICamouflagable) {
            CamouflageStorage storage = ((ICamouflagable)tile).getStorage();
            int dir = tintIndex / 50;
            int index = tintIndex % 50;
            int[] colors = storage.getCache(Direction.m_122376_((int)dir)).getColors();
            return colors.length <= 0 ? -1 : colors[index % colors.length];
        }
        return -1;
    }

    default public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        BlockEntity tile;
        if (player.m_6144_() && (tile = world.m_7702_(pos)) instanceof ICamouflagable && ((ICamouflagable)tile).removeCamouflage()) {
            this.self().m_5707_(world, pos, state, player);
            return false;
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @OnlyIn(value=Dist.CLIENT)
    default public Direction getHitSide() {
        try {
            HitResult result = Minecraft.m_91087_().f_91077_;
            return result instanceof BlockHitResult ? ((BlockHitResult)result).m_82434_() : Direction.NORTH;
        }
        catch (Exception e) {
            e.printStackTrace();
            return Direction.NORTH;
        }
    }

    default public boolean addRunningEffects(BlockState state, Level world, BlockPos pos, Entity entity) {
        CamouflageStorage tile = this.getTile((LevelAccessor)world, pos, Direction.UP);
        if (tile == null) {
            return false;
        }
        try {
            BlockState newState = tile.getCache(Direction.UP).getDisplayBlock();
            if (newState.m_60799_() != RenderShape.INVISIBLE) {
                Vec3 motion = entity.m_20184_();
                world.m_7106_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, newState).setPos(pos), entity.m_20185_() + ((double)world.f_46441_.m_188501_() - 0.5) * (double)entity.m_6972_((Pose)entity.m_20089_()).f_20377_, entity.m_20186_() + 0.1, entity.m_20189_() + ((double)world.f_46441_.m_188501_() - 0.5) * (double)entity.m_6972_((Pose)entity.m_20089_()).f_20377_, motion.f_82479_ * -4.0, 1.5, motion.f_82481_ * -4.0);
            }
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    default public boolean addLandingEffects(BlockState state1, ServerLevel serverWorld, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        CamouflageStorage tile = this.getTile((LevelAccessor)serverWorld, pos, Direction.UP);
        if (tile == null) {
            return false;
        }
        try {
            serverWorld.m_8767_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, tile.getCache(Direction.UP).getDisplayBlock()), (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 1.0, (double)pos.m_123343_() + 0.5, numberOfParticles, 0.0, 0.0, 0.0, 0.15);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static class CamuflageWrapper
    implements IClientBlockExtensions {
        ICamouflageBlock owner;

        public CamuflageWrapper(ICamouflageBlock owner) {
            this.owner = owner;
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean addDestroyEffects(BlockState state, Level world, BlockPos pos, ParticleEngine manager) {
            Direction dir = this.owner.getHitSide();
            CamouflageStorage tile = this.owner.getTile((LevelAccessor)world, pos, dir);
            if (tile != null) {
                BlockState newState = tile.getCache(dir).getDisplayBlock();
                try {
                    VoxelShape voxelshape = state.m_60808_((BlockGetter)world, pos);
                    voxelshape.m_83286_((p_199284_3_, p_199284_5_, p_199284_7_, p_199284_9_, p_199284_11_, p_199284_13_) -> {
                        double d1 = Math.min(1.0, p_199284_9_ - p_199284_3_);
                        double d2 = Math.min(1.0, p_199284_11_ - p_199284_5_);
                        double d3 = Math.min(1.0, p_199284_13_ - p_199284_7_);
                        int i = Math.max(2, Mth.m_14165_((double)(d1 / 0.25)));
                        int j = Math.max(2, Mth.m_14165_((double)(d2 / 0.25)));
                        int k = Math.max(2, Mth.m_14165_((double)(d3 / 0.25)));
                        for (int l = 0; l < i; ++l) {
                            for (int i1 = 0; i1 < j; ++i1) {
                                for (int j1 = 0; j1 < k; ++j1) {
                                    double d4 = ((double)l + 0.5) / (double)i;
                                    double d5 = ((double)i1 + 0.5) / (double)j;
                                    double d6 = ((double)j1 + 0.5) / (double)k;
                                    double d7 = d4 * d1 + p_199284_3_;
                                    double d8 = d5 * d2 + p_199284_5_;
                                    double d9 = d6 * d3 + p_199284_7_;
                                    manager.m_107344_((Particle)new TerrainParticle((ClientLevel)world, (double)pos.m_123341_() + d7, (double)pos.m_123342_() + d8, (double)pos.m_123343_() + d9, d4 - 0.5, d5 - 0.5, d6 - 0.5, newState, pos));
                                }
                            }
                        }
                    });
                }
                catch (Exception e) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @OnlyIn(value=Dist.CLIENT)
        public boolean addHitEffects(BlockState state, Level worldObj, HitResult target, ParticleEngine manager) {
            if (!(target instanceof BlockHitResult)) {
                return false;
            }
            BlockHitResult result = (BlockHitResult)target;
            CamouflageStorage tile = this.owner.getTile((LevelAccessor)worldObj, result.m_82425_(), result.m_82434_());
            if (tile == null) {
                return false;
            }
            try {
                BlockState blockstate = tile.getCache(result.m_82434_()).getDisplayBlock();
                BlockPos pos = result.m_82425_();
                Direction side = result.m_82434_();
                if (blockstate.m_60799_() != RenderShape.INVISIBLE) {
                    int i = pos.m_123341_();
                    int j = pos.m_123342_();
                    int k = pos.m_123343_();
                    AABB axisalignedbb = state.m_60808_((BlockGetter)worldObj, pos).m_83215_();
                    double d0 = (double)i + worldObj.f_46441_.m_188500_() * (axisalignedbb.f_82291_ - axisalignedbb.f_82288_ - (double)0.2f) + (double)0.1f + axisalignedbb.f_82288_;
                    double d1 = (double)j + worldObj.f_46441_.m_188500_() * (axisalignedbb.f_82292_ - axisalignedbb.f_82289_ - (double)0.2f) + (double)0.1f + axisalignedbb.f_82289_;
                    double d2 = (double)k + worldObj.f_46441_.m_188500_() * (axisalignedbb.f_82293_ - axisalignedbb.f_82290_ - (double)0.2f) + (double)0.1f + axisalignedbb.f_82290_;
                    if (side == Direction.DOWN) {
                        d1 = (double)j + axisalignedbb.f_82289_ - (double)0.1f;
                    }
                    if (side == Direction.UP) {
                        d1 = (double)j + axisalignedbb.f_82292_ + (double)0.1f;
                    }
                    if (side == Direction.NORTH) {
                        d2 = (double)k + axisalignedbb.f_82290_ - (double)0.1f;
                    }
                    if (side == Direction.SOUTH) {
                        d2 = (double)k + axisalignedbb.f_82293_ + (double)0.1f;
                    }
                    if (side == Direction.WEST) {
                        d0 = (double)i + axisalignedbb.f_82288_ - (double)0.1f;
                    }
                    if (side == Direction.EAST) {
                        d0 = (double)i + axisalignedbb.f_82291_ + (double)0.1f;
                    }
                    manager.m_107344_(new TerrainParticle((ClientLevel)worldObj, d0, d1, d2, 0.0, 0.0, 0.0, blockstate, pos).m_107268_(0.2f).m_6569_(0.6f));
                    return true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return false;
        }
    }
}

