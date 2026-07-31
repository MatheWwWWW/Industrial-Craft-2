/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.storage.tank;

import ic2.api.util.FluidContainerOutputMode;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.proxy.SideProxyClient;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class TileEntityTank
extends TileEntityInventory
implements IHasGui {
    protected final Fluids fluidsComponent = this.addComponent(new Fluids(this));
    @GuiSynced
    protected final Fluids.InternalFluidTank contents;

    public TileEntityTank(BlockEntityType<? extends TileEntityTank> blockEntityType, BlockPos blockPos, BlockState blockState, int n) {
        super((BlockEntityType<? extends TileEntityInventory>)blockEntityType, blockPos, blockState);
        this.contents = this.fluidsComponent.addTank("contents", 1000 * n);
    }

    @Override
    protected List<ItemStack> getAuxDrops(int n) {
        return Collections.emptyList();
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        if (!this.m_58904_().f_46443_) {
            CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
            this.contents.fromNbt(compoundTag);
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        if (this.contents.getFluidAmount() > 0) {
            this.contents.toNbt(compoundTag);
        }
        return itemStack;
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (LiquidUtil.isFluidContainer(itemStack)) {
            Ic2FluidStack ic2FluidStack = this.contents.getFluidStack();
            int n = 0;
            if (ic2FluidStack != null && !ic2FluidStack.isEmpty() && (n = LiquidUtil.fillContainer(player, interactionHand, ic2FluidStack, FluidContainerOutputMode.InPlacePreferred, false)) != 0) {
                ic2FluidStack.decreaseMb(n);
                return InteractionResult.SUCCESS;
            }
            n = ic2FluidStack != null ? ic2FluidStack.getAmountMb() : 0;
            if ((ic2FluidStack = LiquidUtil.drainContainer(player, interactionHand, ic2FluidStack == null || ic2FluidStack.isEmpty() ? null : ic2FluidStack.getFluid(), this.contents.getCapacity() - n, FluidContainerOutputMode.InPlacePreferred, false)) != null && !ic2FluidStack.isEmpty()) {
                this.contents.fillMb(ic2FluidStack, false);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return super.onActivated(player, interactionHand, direction, vec3);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void addInformation(ItemStack itemStack, List<String> list, TooltipFlag tooltipFlag) {
        list.add("Capacity: " + this.contents.getCapacity() + " mB");
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag != null && !compoundTag.m_128441_("Empty")) {
            Ic2FluidStack ic2FluidStack = Ic2FluidStack.read(compoundTag);
            if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
                list.add("Empty");
            } else {
                list.add(SideProxyClient.envProxy.getFluidName(ic2FluidStack));
                list.add("Amount: " + ic2FluidStack.getAmountMb() + " mB");
                list.add("Type: " + (FluidHandler.isGaseous(ic2FluidStack.getFluid()) ? "Gas" : "Liquid"));
            }
        } else {
            list.add("Empty");
        }
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return DynamicContainer.create(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return DynamicContainer.create(n, inventory, this);
    }
}

