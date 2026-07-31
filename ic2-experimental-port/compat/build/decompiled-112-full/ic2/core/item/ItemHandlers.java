/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockPistonBase
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ActionResult
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.FluidRegistry
 */
package ic2.core.item;

import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.block.BlockSheet;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.item.ItemMulti;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.item.type.CellType;
import ic2.core.item.type.IRadioactiveItemType;
import ic2.core.item.upgrade.ItemUpgradeModule;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class ItemHandlers {
    public static ItemMulti.IItemRightClickHandler cfPowderApply = new ItemMulti.IItemRightClickHandler(){

        @Override
        public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer player, EnumHand hand) {
            RayTraceResult position = Util.traceBlocks(player, true);
            if (position == null) {
                return new ActionResult(EnumActionResult.PASS, (Object)stack);
            }
            if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
                World world = player.func_130014_f_();
                if (!world.canMineBlockBody(player, position.func_178782_a())) {
                    return new ActionResult(EnumActionResult.FAIL, (Object)stack);
                }
                if (world.func_180495_p(position.func_178782_a()).func_177230_c() == Blocks.field_150355_j) {
                    stack = StackUtil.decSize(stack);
                    world.func_175656_a(position.func_178782_a(), FluidName.construction_foam.getInstance().getBlock().func_176223_P());
                    new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
                }
            }
            return new ActionResult(EnumActionResult.FAIL, (Object)stack);
        }
    };
    public static ItemMulti.IItemRightClickHandler scrapBoxUnpack = new ItemMulti.IItemRightClickHandler(){

        @Override
        public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer player, EnumHand hand) {
            ItemStack drop;
            if (!player.func_130014_f_().field_72995_K && (drop = Recipes.scrapboxDrops.getDrop(stack, false)) != null && player.func_71019_a(drop, false) != null && !player.field_71075_bZ.field_75098_d) {
                stack = StackUtil.decSize(stack);
                return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
            }
            return new ActionResult(EnumActionResult.PASS, (Object)stack);
        }
    };
    public static ItemMulti.IItemUseHandler resinUse = new ItemMulti.IItemUseHandler(){

        @Override
        public EnumActionResult onUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumHand hand, EnumFacing side) {
            World world = player.func_130014_f_();
            IBlockState state = world.func_180495_p(pos);
            if (state.func_177230_c() == Blocks.field_150331_J && state.func_177229_b((IProperty)BlockPistonBase.field_176387_N) == side) {
                IBlockState newState = Blocks.field_150320_F.func_176223_P().func_177226_a((IProperty)BlockPistonBase.field_176387_N, (Comparable)side).func_177226_a((IProperty)BlockPistonBase.field_176320_b, state.func_177229_b((IProperty)BlockPistonBase.field_176320_b));
                world.func_180501_a(pos, newState, 3);
                if (!player.field_71075_bZ.field_75098_d) {
                    StackUtil.consumeOrError(player, hand, 1);
                }
                return EnumActionResult.SUCCESS;
            }
            if (side != EnumFacing.UP) {
                return EnumActionResult.PASS;
            }
            pos = pos.func_177984_a();
            if (!state.func_177230_c().isAir(state, (IBlockAccess)world, pos) || !BlockName.sheet.getInstance().func_176198_a(world, pos, side)) {
                return EnumActionResult.PASS;
            }
            world.func_175656_a(pos, BlockName.sheet.getBlockState(BlockSheet.SheetType.resin));
            if (!player.field_71075_bZ.field_75098_d) {
                StackUtil.consumeOrError(player, hand, 1);
            }
            return EnumActionResult.PASS;
        }
    };
    public static ItemMulti.IItemUpdateHandler radioactiveUpdate = new ItemMulti.IItemUpdateHandler(){

        @Override
        public void onUpdate(ItemStack stack, World world, Entity rawEntity, int slotIndex, boolean isCurrentItem) {
            Item item = stack.func_77973_b();
            if (item == null || !(item instanceof ItemMulti)) {
                return;
            }
            Object rawType = ((ItemMulti)item).getType(stack);
            if (!(rawType instanceof IRadioactiveItemType)) {
                return;
            }
            IRadioactiveItemType type = (IRadioactiveItemType)rawType;
            if (!(rawEntity instanceof EntityLivingBase)) {
                return;
            }
            EntityLivingBase entity = (EntityLivingBase)rawEntity;
            if (ItemArmorHazmat.hasCompleteHazmat(entity)) {
                return;
            }
            IC2Potion.radiation.applyTo(entity, type.getRadiationDuration() * 20, type.getRadiationAmplifier());
        }
    };
    public static TeBlock.ITePlaceHandler reactorChamberPlace = new TeBlock.ITePlaceHandler(){

        @Override
        public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
            int count = 0;
            for (EnumFacing dir : EnumFacing.field_82609_l) {
                TileEntity te = world.func_175625_s(pos.func_177972_a(dir));
                if (!(te instanceof TileEntityNuclearReactorElectric)) continue;
                ++count;
            }
            return count == 1;
        }
    };
    public static ItemMulti.IItemRightClickHandler openAdvancedUpgradeGUI = new ItemMulti.IItemRightClickHandler(){

        @Override
        public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer player, EnumHand hand) {
            assert (stack.func_77973_b() == ItemName.upgrade.getInstance());
            if (IC2.platform.isSimulating()) {
                IC2.platform.launchGui(player, ((ItemUpgradeModule)stack.func_77973_b()).getInventory(player, stack));
            }
            return new ActionResult(EnumActionResult.SUCCESS, (Object)stack);
        }
    };
    public static ItemMulti.IItemUseHandler emptyCellFill = new ItemMulti.IItemUseHandler(){

        @Override
        public EnumActionResult onUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumHand hand, EnumFacing side) {
            assert (stack.func_77973_b() == ItemName.cell.getInstance());
            World world = player.func_130014_f_();
            RayTraceResult position = Util.traceBlocks(player, true);
            if (position == null) {
                return EnumActionResult.FAIL;
            }
            if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
                pos = position.func_178782_a();
                if (!world.canMineBlockBody(player, pos)) {
                    return EnumActionResult.FAIL;
                }
                if (!player.func_175151_a(pos, position.field_178784_b, player.func_184586_b(hand))) {
                    return EnumActionResult.FAIL;
                }
                LiquidUtil.LiquidData data = LiquidUtil.getLiquid(world, pos);
                if (data != null && data.isSource) {
                    if (data.liquid == FluidRegistry.WATER && StackUtil.storeInventoryItem(ItemName.cell.getItemStack(CellType.water), player, true)) {
                        world.func_175698_g(pos);
                        StackUtil.consumeOrError(player, hand, 1);
                        StackUtil.storeInventoryItem(ItemName.cell.getItemStack(CellType.water), player, false);
                        return EnumActionResult.SUCCESS;
                    }
                    if (data.liquid == FluidRegistry.LAVA && StackUtil.storeInventoryItem(ItemName.cell.getItemStack(CellType.lava), player, true)) {
                        world.func_175698_g(pos);
                        StackUtil.consumeOrError(player, hand, 1);
                        StackUtil.storeInventoryItem(ItemName.cell.getItemStack(CellType.lava), player, false);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
            return EnumActionResult.PASS;
        }
    };

    public static ItemMulti.IItemUseHandler getFluidPlacer(final Block type) {
        return new ItemMulti.IItemUseHandler(){

            @Override
            public EnumActionResult onUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumHand hand, EnumFacing side) {
                assert (stack.func_77973_b() == ItemName.misc_resource.getInstance());
                World world = player.func_130014_f_();
                if (!world.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)world, pos)) {
                    pos = pos.func_177972_a(side);
                }
                if (player.func_175151_a(pos, side, stack) && world.func_190527_a(type, pos, false, side, null)) {
                    IBlockState placedState = type.getStateForPlacement(world, pos, side, 0.0f, 0.0f, 0.0f, 0, (EntityLivingBase)player, hand);
                    world.func_175656_a(pos, placedState);
                    SoundType sound = placedState.func_177230_c().getSoundType(placedState, world, pos, (Entity)player);
                    world.func_184133_a(player, pos, sound.func_185841_e(), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0f) / 2.0f, sound.func_185847_b() * 0.8f);
                    StackUtil.consumeOrError(player, hand, 1);
                    return EnumActionResult.SUCCESS;
                }
                return EnumActionResult.FAIL;
            }
        };
    }
}

