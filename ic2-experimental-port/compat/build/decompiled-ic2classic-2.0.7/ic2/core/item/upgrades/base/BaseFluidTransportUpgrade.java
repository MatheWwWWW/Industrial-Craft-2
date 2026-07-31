/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.upgrades.base;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.item.INetworkItemEvent;
import ic2.api.tiles.IMachine;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.base.IPortableInventory;
import ic2.core.item.inv.container.FluidImportExportContainer;
import ic2.core.item.inv.inventory.FluidImportExportInventory;
import ic2.core.item.upgrades.base.BaseDirectionalUpgrade;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class BaseFluidTransportUpgrade
extends BaseDirectionalUpgrade
implements INetworkItemEvent,
IHasHeldSlotInventory {
    public static final String FILTERS = "filters";
    public static final String TRANSFER = "transfer";
    public static final String INVERTED = "inverted";

    public BaseFluidTransportUpgrade(String itemName) {
        super(itemName);
        this.functions.remove((Object)IUpgradeItem.Functions.RECIPE);
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.TRANSPORT_MOD;
    }

    @Override
    public void onEventReceived(ItemStack stack, Player player, int key, int value, Dist target) {
        if (target.isDedicatedServer() && player.f_36096_ instanceof FluidImportExportContainer) {
            ((FluidImportExportInventory)((FluidImportExportContainer)player.f_36096_).getHolder()).onDataReceived(key, value);
        }
    }

    @Override
    public IPortableInventory getInventory(Player player, InteractionHand hand, ItemStack stack) {
        return new FluidImportExportInventory(player, this, stack, null).load(stack);
    }

    @Override
    public IPortableInventory getInventory(Player player, ItemStack stack, Slot slot) {
        return new FluidImportExportInventory(player, this, stack, slot).load(stack);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (IC2.KEYBOARD.isSideInventoryKeyDown(playerIn)) {
            ItemStack stack = playerIn.m_21120_(handIn);
            if (IC2.PLATFORM.isSimulating()) {
                IC2.PLATFORM.launchGui(playerIn, handIn, null, this.getInventory(playerIn, handIn, stack));
            }
            return InteractionResultHolder.m_19090_((Object)stack);
        }
        return super.m_7203_(worldIn, playerIn, handIn);
    }

    public Set<Fluid> getFluids(ItemStack stack) {
        ObjectSortedSet fluids = CollectionUtils.createLinkedSet();
        for (CompoundTag nbt : NBTListWrapper.wrap(StackUtil.getNbtData(stack).m_128437_(FILTERS, 10), CompoundTag.class)) {
            Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(nbt.m_128461_("id")));
            if (fluid == Fluids.f_76191_) continue;
            fluids.add((Fluid)fluid);
        }
        return fluids;
    }

    public int transferRate(ItemStack stack) {
        return NBTUtils.getInt(StackUtil.getNbtData(stack), TRANSFER, 1000);
    }

    public boolean isInverted(ItemStack stack) {
        return !NBTUtils.getBoolean(StackUtil.getNbtData(stack), INVERTED, true);
    }

    public void transferFluid(IMachine machine, IFluidHandler source, IFluidHandler target, int transferRate, Set<Fluid> fluids, boolean inverted) {
        if (transferRate <= 0) {
            return;
        }
        int used = 0;
        int m = source.getTanks();
        for (int i = 0; i < m; ++i) {
            int drained;
            FluidStack fluid = source.getFluidInTank(i);
            if (!fluids.isEmpty() && fluids.contains(fluid.getFluid()) == inverted || (drained = target.fill(source.drain(new FluidStack(fluid, transferRate), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE)) <= 0) continue;
            source.drain(new FluidStack(fluid, drained), IFluidHandler.FluidAction.EXECUTE);
            used += drained;
            if ((transferRate -= drained) <= 0) break;
        }
        machine.useEnergy(Mth.m_14167_((float)((float)used / 100.0f)), true);
    }
}

