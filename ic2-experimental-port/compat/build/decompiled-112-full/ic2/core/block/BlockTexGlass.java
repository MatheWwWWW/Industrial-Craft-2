/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.EntityLiving$SpawnPlacementType
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.block.BlockMultiID;
import ic2.core.block.state.IIdProvider;
import ic2.core.ref.BlockName;
import java.util.Random;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTexGlass
extends BlockMultiID<GlassType> {
    public static BlockTexGlass create() {
        return BlockMultiID.create(BlockTexGlass.class, GlassType.class, new Object[0]);
    }

    private BlockTexGlass() {
        super(BlockName.glass, Material.field_151592_s);
        this.func_149711_c(5.0f);
        this.func_149752_b(180.0f);
        this.func_149672_a(SoundType.field_185853_f);
    }

    public int func_149745_a(Random random) {
        return 0;
    }

    public boolean func_149662_c(IBlockState state) {
        return false;
    }

    public boolean func_149730_j(IBlockState state) {
        return true;
    }

    public boolean func_149686_d(IBlockState state) {
        return false;
    }

    public boolean func_185481_k(IBlockState state) {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public boolean func_176225_a(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (world.func_180495_p(pos.func_177972_a(side)).func_177230_c() == this) {
            return false;
        }
        return super.func_176225_a(state, world, pos, side);
    }

    public static enum GlassType implements IIdProvider
    {
        reinforced;


        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public int getId() {
            return this.ordinal();
        }
    }
}

