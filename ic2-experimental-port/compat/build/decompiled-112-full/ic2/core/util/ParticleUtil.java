/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleBlockDust
 *  net.minecraft.client.particle.ParticleDigging
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumFacing$Axis
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.util;

import ic2.core.block.ITeBlock;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.state.Ic2BlockState;
import ic2.core.model.ISpecialParticleModel;
import ic2.core.model.ModelUtil;
import java.lang.reflect.Constructor;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleBlockDust;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ParticleUtil {
    private static final Constructor<ParticleBlockDust> particleBlockDust_ctor = ParticleUtil.getParticleBlockDustCtor();
    private static final Constructor<ParticleDigging> particleDigging_ctor = ParticleUtil.getParticleDiggingCtor();

    public static void showFlames(World world, BlockPos pos, EnumFacing facing) {
        if (world.field_73012_v.nextInt(8) != 0) {
            return;
        }
        double width = 0.625;
        double height = 0.625;
        double depthOffset = 0.02;
        double x = (double)pos.func_177958_n() + ((double)facing.func_82601_c() * 1.04 + 1.0) / 2.0;
        double y = (double)pos.func_177956_o() + (double)world.field_73012_v.nextFloat() * 0.625;
        double z = (double)pos.func_177952_p() + ((double)facing.func_82599_e() * 1.04 + 1.0) / 2.0;
        if (facing.func_176740_k() == EnumFacing.Axis.X) {
            z += (double)world.field_73012_v.nextFloat() * 0.625 - 0.3125;
        } else {
            x += (double)world.field_73012_v.nextFloat() * 0.625 - 0.3125;
        }
        world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0, 0.0, 0.0, new int[0]);
        world.func_175688_a(EnumParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0, new int[0]);
    }

    public static void spawnBlockLandParticles(World world, BlockPos pos, double x, double y, double z, int count, ITeBlock teBlock) {
        Minecraft mc = Minecraft.func_71410_x();
        Random rnd = world.field_73012_v;
        if (mc.field_71441_e != world || mc.field_71439_g == null) {
            return;
        }
        if (mc.field_71474_y.field_74362_aa > 1 || mc.field_71474_y.field_74362_aa == 1 && rnd.nextInt(3) == 0) {
            return;
        }
        if (mc.field_71439_g.func_70092_e(x, y, z) > 1024.0) {
            return;
        }
        double speed = 0.15;
        IBlockState state = TeBlockRegistry.get(teBlock.getIdentifier()).getState(teBlock);
        for (int i = 0; i < count; ++i) {
            double mx = rnd.nextGaussian() * 0.15;
            double my = rnd.nextGaussian() * 0.15;
            double mz = rnd.nextGaussian() * 0.15;
            ParticleBlockDust particle = ParticleUtil.newParticleBlockDust(world, x, y, z, mx, my, mz, state);
            ParticleUtil.ensureTexture(world, pos, (Particle)particle.func_174845_l(), state);
            mc.field_71452_i.func_78873_a((Particle)particle);
        }
    }

    public static void spawnBlockRunParticles(World world, BlockPos pos, double x, double y, double z, double xSpeed, double zSpeed, ITeBlock block) {
        IBlockState state = TeBlockRegistry.get(block.getIdentifier()).getState(block);
        ParticleDigging particle = ParticleUtil.newParticleDigging(world, x, y, z, xSpeed, 1.5, zSpeed, state);
        ParticleUtil.ensureTexture(world, pos, (Particle)particle.func_174845_l(), state);
        Minecraft.func_71410_x().field_71452_i.func_78873_a((Particle)particle);
    }

    public static void spawnBlockHitParticles(TileEntityBlock te, EnumFacing side) {
        ParticleUtil.spawnBlockHitParticles(te, side, false);
    }

    public static void spawnBlockHitParticles(TileEntityBlock te, EnumFacing side, boolean checkTexture) {
        World world = te.func_145831_w();
        BlockPos pos = te.func_174877_v();
        double offset = 0.1;
        AxisAlignedBB aabb = te.getVisualBoundingBox();
        double x = (double)pos.func_177958_n() + world.field_73012_v.nextDouble() * (aabb.field_72336_d - aabb.field_72340_a - offset * 2.0) + offset + aabb.field_72340_a;
        double y = (double)pos.func_177956_o() + world.field_73012_v.nextDouble() * (aabb.field_72337_e - aabb.field_72338_b - offset * 2.0) + offset + aabb.field_72338_b;
        double z = (double)pos.func_177952_p() + world.field_73012_v.nextDouble() * (aabb.field_72334_f - aabb.field_72339_c - offset * 2.0) + offset + aabb.field_72339_c;
        switch (side) {
            case DOWN: {
                y = (double)pos.func_177956_o() + aabb.field_72338_b - offset;
                break;
            }
            case UP: {
                y = (double)pos.func_177956_o() + aabb.field_72337_e + offset;
                break;
            }
            case NORTH: {
                z = (double)pos.func_177952_p() + aabb.field_72339_c - offset;
                break;
            }
            case SOUTH: {
                z = (double)pos.func_177952_p() + aabb.field_72334_f + offset;
                break;
            }
            case WEST: {
                x = (double)pos.func_177958_n() + aabb.field_72340_a - offset;
                break;
            }
            case EAST: {
                x = (double)pos.func_177958_n() + aabb.field_72336_d + offset;
                break;
            }
            default: {
                throw new IllegalStateException("invalid facing: " + side);
            }
        }
        ParticleDigging particle = ParticleUtil.newParticleDigging(world, x, y, z, 0.0, 0.0, 0.0, te.getBlockState());
        particle.func_174846_a(pos);
        particle.func_70543_e(0.2f);
        particle.func_70541_f(0.6f);
        if (checkTexture) {
            ParticleUtil.ensureTexture(world, pos, (Particle)particle, te.getBlockState());
        }
        Minecraft.func_71410_x().field_71452_i.func_78873_a((Particle)particle);
    }

    public static void spawnBlockBreakParticles(TileEntityBlock te) {
        World world = te.func_145831_w();
        BlockPos pos = te.func_174877_v();
        IBlockState state = te.getBlockState();
        Minecraft mc = Minecraft.func_71410_x();
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                for (int z = 0; z < 4; ++z) {
                    double xOffset = ((double)x + 0.5) / 4.0;
                    double yOffset = ((double)y + 0.5) / 4.0;
                    double zOffset = ((double)z + 0.5) / 4.0;
                    ParticleDigging particle = ParticleUtil.newParticleDigging(world, (double)pos.func_177958_n() + xOffset, (double)pos.func_177956_o() + yOffset, (double)pos.func_177952_p() + zOffset, xOffset - 0.5, yOffset - 0.5, zOffset - 0.5, state);
                    particle.func_174846_a(pos);
                    ParticleUtil.ensureTexture(world, pos, (Particle)particle, state);
                    mc.field_71452_i.func_78873_a((Particle)particle);
                }
            }
        }
    }

    public static ParticleBlockDust newParticleBlockDust(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
        try {
            return particleBlockDust_ctor.newInstance(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, state);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ParticleDigging newParticleDigging(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
        try {
            return particleDigging_ctor.newInstance(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, state);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ensureTexture(World world, BlockPos pos, Particle particle, IBlockState state) {
        if (pos == null) {
            return;
        }
        IBakedModel model = ModelUtil.getBlockModel(state);
        if (model instanceof ISpecialParticleModel && ((ISpecialParticleModel)model).needsEnhancing(state)) {
            state = state.func_185899_b((IBlockAccess)world, pos);
            state = state.func_177230_c().getExtendedState(state, (IBlockAccess)world, pos);
            assert (state instanceof Ic2BlockState.Ic2BlockStateInstance);
            ((ISpecialParticleModel)model).enhanceParticle(particle, (Ic2BlockState.Ic2BlockStateInstance)state);
        }
    }

    private static Constructor<ParticleBlockDust> getParticleBlockDustCtor() {
        try {
            Constructor<ParticleBlockDust> ret = ParticleBlockDust.class.getDeclaredConstructor(World.class, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, IBlockState.class);
            ret.setAccessible(true);
            return ret;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<ParticleDigging> getParticleDiggingCtor() {
        try {
            Constructor<ParticleDigging> ret = ParticleDigging.class.getDeclaredConstructor(World.class, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, IBlockState.class);
            ret.setAccessible(true);
            return ret;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

