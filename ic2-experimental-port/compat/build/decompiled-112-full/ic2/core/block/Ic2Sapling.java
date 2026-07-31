/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBush
 *  net.minecraft.block.IGrowable
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.EnumPlantType
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.IC2;
import ic2.core.block.BlockBase;
import ic2.core.block.WorldGenRubTree;
import ic2.core.init.BlocksItems;
import ic2.core.item.block.ItemBlockIC2;
import ic2.core.ref.BlockName;
import ic2.core.ref.IBlockModelProvider;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Ic2Sapling
extends BlockBush
implements IBlockModelProvider,
IGrowable {
    public Ic2Sapling() {
        this.func_149711_c(0.0f);
        this.func_149672_a(SoundType.field_185850_c);
        this.func_149663_c(BlockName.sapling.name());
        this.func_149647_a(IC2.tabIC2);
        ResourceLocation name = IC2.getIdentifier(BlockName.sapling.name());
        BlocksItems.registerBlock(this, name);
        BlocksItems.registerItem(new ItemBlockIC2((Block)this), name);
        BlockName.sapling.setInstance(this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(BlockName name) {
        BlockBase.registerDefaultItemModel((Block)this);
    }

    public String func_149739_a() {
        return "ic2." + super.func_149739_a().substring(5) + ".rubber";
    }

    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public void func_180650_b(World world, BlockPos pos, IBlockState state, Random random) {
        if (world.field_72995_K) {
            return;
        }
        if (!this.func_180671_f(world, pos, state)) {
            this.func_176226_b(world, pos, state, 0);
            world.func_175698_g(pos);
            return;
        }
        if (world.func_175671_l(pos.func_177984_a()) >= 9 && random.nextInt(30) == 0) {
            this.func_176474_b(world, random, pos, state);
        }
    }

    public void func_176474_b(World world, Random rand, BlockPos pos, IBlockState state) {
        new WorldGenRubTree(true).grow(world, pos, rand);
    }

    public int func_180651_a(IBlockState state) {
        return 0;
    }

    public boolean func_176473_a(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    public boolean func_180670_a(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }
}

