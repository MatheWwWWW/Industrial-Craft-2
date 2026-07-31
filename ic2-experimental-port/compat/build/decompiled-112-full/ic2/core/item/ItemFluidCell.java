/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.World
 *  net.minecraftforge.fluids.DispenseFluidContainer
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 */
package ic2.core.item;

import ic2.core.IC2;
import ic2.core.item.ItemIC2FluidContainer;
import ic2.core.ref.ItemName;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class ItemFluidCell
extends ItemIC2FluidContainer {
    public ItemFluidCell() {
        super(ItemName.fluid_cell, 1000);
        BlockDispenser.field_149943_a.func_82595_a((Object)this, (Object)DispenseFluidContainer.getInstance());
    }

    public boolean isRepairable() {
        return false;
    }

    public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xOffset, float yOffset, float zOffset) {
        if (world.field_72995_K) {
            return EnumActionResult.SUCCESS;
        }
        if (this.interactWithTank(player, hand, world, pos, side)) {
            player.field_71069_bz.func_75142_b();
            return EnumActionResult.SUCCESS;
        }
        RayTraceResult position = this.func_77621_a(world, player, true);
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
            if (LiquidUtil.drainBlockToContainer(world, pos, player, hand) || LiquidUtil.fillBlockFromContainer(world, pos, player, hand) || LiquidUtil.fillBlockFromContainer(world, pos.func_177972_a(side), player, hand)) {
                player.field_71069_bz.func_75142_b();
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean canfill(Fluid fluid) {
        return true;
    }

    public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.func_194125_a(tab) || IC2.version.isClassic()) {
            return;
        }
        ItemStack emptyStack = new ItemStack((Item)this);
        subItems.add((Object)emptyStack);
        for (Fluid fluid : LiquidUtil.getAllFluids()) {
            ItemStack stack;
            if (fluid == null || (stack = this.getItemStack(fluid)) == null) continue;
            subItems.add((Object)stack);
        }
    }

    private boolean interactWithTank(EntityPlayer player, EnumHand hand, World world, BlockPos pos, EnumFacing side) {
        boolean single;
        assert (!world.field_72995_K);
        IFluidHandler tileHandler = FluidUtil.getFluidHandler((World)world, (BlockPos)pos, (EnumFacing)side);
        if (tileHandler == null) {
            return false;
        }
        ItemStack stack = StackUtil.get(player, hand);
        boolean bl = single = StackUtil.getSize(stack) == 1;
        if (!single) {
            stack = StackUtil.copyWithSize(stack, 1);
        }
        boolean changeMade = false;
        do {
            IFluidHandlerItem itemHandler = FluidUtil.getFluidHandler((ItemStack)StackUtil.copy(stack));
            assert (itemHandler != null);
            if (FluidUtil.tryFluidTransfer((IFluidHandler)tileHandler, (IFluidHandler)itemHandler, (int)Integer.MAX_VALUE, (boolean)true) == null) break;
            if (single) {
                StackUtil.set(player, hand, itemHandler.getContainer());
                return true;
            }
            StackUtil.consumeOrError(player, hand, 1);
            StackUtil.storeInventoryItem(itemHandler.getContainer(), player, false);
            changeMade = true;
        } while (!StackUtil.isEmpty(player, hand));
        return changeMade;
    }
}

