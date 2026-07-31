/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLeaves
 *  net.minecraft.block.BlockPlanks$EnumType
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.properties.PropertyEnum
 *  net.minecraft.block.state.BlockStateContainer
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.renderer.block.statemap.IStateMapper
 *  net.minecraft.client.renderer.block.statemap.StateMap
 *  net.minecraft.client.renderer.block.statemap.StateMap$Builder
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.BlockRenderLayer
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.IStringSerializable
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.client.model.ModelLoader
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.IC2;
import ic2.core.block.BlockBase;
import ic2.core.init.BlocksItems;
import ic2.core.item.block.ItemIc2Leaves;
import ic2.core.ref.BlockName;
import ic2.core.ref.IBlockModelProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Ic2Leaves
extends BlockLeaves
implements IBlockModelProvider {
    public static final PropertyEnum<LeavesType> typeProperty = PropertyEnum.func_177709_a((String)"type", LeavesType.class);
    private static final int checkDecayFlag = 8;
    private static final int decayableFlag = 4;

    public Ic2Leaves() {
        this.func_149663_c(BlockName.leaves.name());
        this.func_149647_a(IC2.tabIC2);
        ResourceLocation name = IC2.getIdentifier(BlockName.leaves.name());
        BlocksItems.registerBlock(this, name);
        BlocksItems.registerItem(new ItemIc2Leaves((Block)this), name);
        BlockName.leaves.setInstance(this);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)field_176236_b, (Comparable)Boolean.valueOf(true)).func_177226_a((IProperty)field_176237_a, (Comparable)Boolean.valueOf(true)).func_177226_a(typeProperty, (Comparable)((Object)LeavesType.rubber)));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(BlockName name) {
        StateMap mapper = new StateMap.Builder().func_178442_a(new IProperty[]{field_176236_b, field_176237_a}).func_178441_a();
        ModelLoader.setCustomStateMapper((Block)this, (IStateMapper)mapper);
        ArrayList<IBlockState> states = new ArrayList<IBlockState>(typeProperty.func_177700_c().size());
        for (LeavesType type : LeavesType.values) {
            states.add(Ic2Leaves.getDropState(this.func_176223_P().func_177226_a(typeProperty, (Comparable)((Object)type))));
        }
        BlockBase.registerItemModels((Block)this, states, (IStateMapper)mapper);
    }

    private static IBlockState getDropState(IBlockState state) {
        return state.func_177226_a((IProperty)field_176236_b, (Comparable)Boolean.valueOf(false)).func_177226_a((IProperty)field_176237_a, (Comparable)Boolean.valueOf(false));
    }

    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[]{field_176236_b, field_176237_a, typeProperty});
    }

    public IBlockState func_176203_a(int meta) {
        boolean checkDecay = (meta & 8) != 0;
        boolean decayable = (meta & 4) != 0;
        IBlockState ret = this.func_176223_P().func_177226_a((IProperty)field_176236_b, (Comparable)Boolean.valueOf(checkDecay)).func_177226_a((IProperty)field_176237_a, (Comparable)Boolean.valueOf(decayable));
        if ((meta &= 3) < LeavesType.values.length) {
            ret = ret.func_177226_a(typeProperty, (Comparable)((Object)LeavesType.values[meta]));
        }
        return ret;
    }

    public int func_176201_c(IBlockState state) {
        int ret = 0;
        if (((Boolean)state.func_177229_b((IProperty)field_176236_b)).booleanValue()) {
            ret |= 8;
        }
        if (((Boolean)state.func_177229_b((IProperty)field_176237_a)).booleanValue()) {
            ret |= 4;
        }
        return ret |= ((LeavesType)((Object)state.func_177229_b(typeProperty))).ordinal();
    }

    public boolean func_149662_c(IBlockState state) {
        return Blocks.field_150362_t.func_149662_c(state);
    }

    @SideOnly(value=Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return Blocks.field_150362_t.func_180664_k();
    }

    public boolean func_176225_a(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        BlockPos nPos = pos.func_177972_a(side);
        return (!this.func_149662_c(state) || world.func_180495_p(nPos) != state) && !world.func_180495_p(nPos).doesSideBlockRendering(world, nPos, side.func_176734_d());
    }

    public Item func_180660_a(IBlockState state, Random rand, int fortune) {
        return ((LeavesType)((Object)state.func_177229_b(typeProperty))).getSapling().func_77973_b();
    }

    public int func_180651_a(IBlockState state) {
        return ((LeavesType)((Object)state.func_177229_b(typeProperty))).getSapling().func_77960_j();
    }

    protected int func_176232_d(IBlockState state) {
        return ((LeavesType)((Object)state.func_177229_b(Ic2Leaves.typeProperty))).saplingDropChance;
    }

    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        IBlockState state = Ic2Leaves.getDropState(world.func_180495_p(pos));
        return Arrays.asList(new ItemStack((Block)this, 1, this.func_176201_c(state)));
    }

    public void func_149666_a(CreativeTabs tab, NonNullList<ItemStack> list) {
        IBlockState state = Ic2Leaves.getDropState(this.func_176223_P());
        for (LeavesType type : LeavesType.values) {
            list.add((Object)new ItemStack((Block)this, 1, this.func_176201_c(state.func_177226_a(typeProperty, (Comparable)((Object)type)))));
        }
    }

    public BlockPlanks.EnumType func_176233_b(int meta) {
        return null;
    }

    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 30;
    }

    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 20;
    }

    public static enum LeavesType implements IStringSerializable
    {
        rubber(35);

        public final int saplingDropChance;
        private static final LeavesType[] values;

        private LeavesType(int saplingDropChance) {
            this.saplingDropChance = saplingDropChance;
        }

        public String func_176610_l() {
            return this.name();
        }

        public ItemStack getSapling() {
            return new ItemStack(BlockName.sapling.getInstance());
        }

        static {
            values = LeavesType.values();
        }
    }
}

