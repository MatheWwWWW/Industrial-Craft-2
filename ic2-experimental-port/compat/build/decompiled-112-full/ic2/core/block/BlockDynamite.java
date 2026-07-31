/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockTorch
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.properties.PropertyBool
 *  net.minecraft.block.state.BlockStateContainer
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import com.google.common.base.Function;
import ic2.core.block.BlockBase;
import ic2.core.block.EntityStickyDynamite;
import ic2.core.block.MaterialIC2TNT;
import ic2.core.ref.BlockName;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDynamite
extends BlockBase {
    public static final IProperty<Boolean> linked = PropertyBool.func_177716_a((String)"linked");

    public BlockDynamite() {
        super(BlockName.dynamite, MaterialIC2TNT.instance, (java.util.function.Function<Block, Item>)((Function)null));
        this.func_149675_a(true);
        this.func_149711_c(0.0f);
        this.func_149672_a(SoundType.field_185850_c);
        this.func_149647_a(null);
        this.func_180632_j(this.func_176223_P().func_177226_a(linked, (Comparable)Boolean.valueOf(false)).func_177226_a((IProperty)BlockTorch.field_176596_a, (Comparable)EnumFacing.UP));
    }

    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[]{BlockTorch.field_176596_a, linked});
    }

    public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
        return Blocks.field_150478_aa.func_176223_P().func_177226_a((IProperty)BlockTorch.field_176596_a, state.func_177229_b((IProperty)BlockTorch.field_176596_a)).func_185900_c(source, pos);
    }

    public boolean func_149662_c(IBlockState state) {
        return false;
    }

    public boolean func_149686_d(IBlockState state) {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean func_176196_c(World world, BlockPos pos) {
        for (EnumFacing dir : BlockTorch.field_176596_a.func_177700_c()) {
            if (!world.func_175677_d(pos.func_177972_a(dir.func_176734_d()), false)) continue;
            return true;
        }
        return false;
    }

    public IBlockState func_180642_a(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing == EnumFacing.DOWN || !world.func_175677_d(pos.func_177972_a(facing.func_176734_d()), false)) {
            for (EnumFacing facing2 : BlockTorch.field_176596_a.func_177700_c()) {
                if (!world.func_175677_d(pos.func_177972_a(facing2.func_176734_d()), false)) continue;
                facing = facing2;
                break;
            }
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockTorch.field_176596_a, (Comparable)facing);
    }

    public int func_176201_c(IBlockState state) {
        return ((EnumFacing)state.func_177229_b((IProperty)BlockTorch.field_176596_a)).ordinal() << 1 | ((Boolean)state.func_177229_b(linked) != false ? 1 : 0);
    }

    public IBlockState func_176203_a(int meta) {
        return this.func_176223_P().func_177226_a(linked, (Comparable)Boolean.valueOf((meta & 1) != 0)).func_177226_a((IProperty)BlockTorch.field_176596_a, (Comparable)EnumFacing.field_82609_l[meta >> 1]);
    }

    public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        this.checkPlacement(world, pos, state);
    }

    public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
        this.checkPlacement(world, pos, state);
    }

    public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        this.checkPlacement(world, pos, state);
    }

    public int func_149745_a(Random random) {
        return 0;
    }

    public int func_180651_a(IBlockState state) {
        return 0;
    }

    public void func_180652_a(World world, BlockPos pos, Explosion explosion) {
        this.explode(world, pos, explosion != null ? explosion.func_94613_c() : null, true);
    }

    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (!world.field_72995_K) {
            this.explode(world, pos, (EntityLivingBase)player, false);
        }
        return false;
    }

    private void checkPlacement(World world, BlockPos pos, IBlockState state) {
        if (world.field_72995_K) {
            return;
        }
        if (world.func_175640_z(pos)) {
            this.explode(world, pos, null, false);
        } else if (!world.func_175677_d(pos.func_177972_a(((EnumFacing)state.func_177229_b((IProperty)BlockTorch.field_176596_a)).func_176734_d()), false)) {
            world.func_175698_g(pos);
            this.func_176226_b(world, pos, state, 0);
        }
    }

    private void explode(World world, BlockPos pos, EntityLivingBase player, boolean byExplosion) {
        world.func_175698_g(pos);
        EntityStickyDynamite entity = new EntityStickyDynamite(world, (double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (float)pos.func_177952_p() + 0.5f);
        entity.owner = player;
        entity.fuse = byExplosion ? 5 : 40;
        world.func_72838_d((Entity)entity);
        world.func_184133_a(null, pos, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public RayTraceResult func_180636_a(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        return Blocks.field_150478_aa.func_180636_a(state, world, pos, start, end);
    }
}

