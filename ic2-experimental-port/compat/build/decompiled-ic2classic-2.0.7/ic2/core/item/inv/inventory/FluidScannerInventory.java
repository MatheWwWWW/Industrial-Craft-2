/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.inv.inventory;

import ic2.core.inventory.base.IFluidInventory;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.FluidScannerContainer;
import ic2.core.item.misc.FluidDisplay;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidScannerInventory
extends PortableInventory
implements IFluidInventory {
    NonNullList<Fluid> fluids;

    public FluidScannerInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FluidScannerContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 9;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.fluids = NonNullList.m_122780_((int)9, (Object)Fluids.f_76191_);
        for (CompoundTag data : NBTListWrapper.wrap(StackUtil.getNbtData(this.stack).m_128437_("fluids", 10), CompoundTag.class)) {
            Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(data.m_128461_("fluid")));
            int slot = data.m_128451_("slot");
            if (slot < 0 || slot >= 9 || !this.canInsert(slot, fluid)) continue;
            this.setFluidInSlot(slot, fluid);
        }
    }

    @Override
    public void save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (int i = 0; i < 9; ++i) {
            Fluid fluid = (Fluid)this.fluids.get(i);
            if (fluid == Fluids.f_76191_) continue;
            CompoundTag data = new CompoundTag();
            data.m_128359_("fluid", ForgeRegistries.FLUIDS.getKey((Object)fluid).toString());
            data.m_128344_("slot", (byte)i);
            list.add((Object)data);
        }
        nbt.m_128365_("fluids", (Tag)list);
    }

    @Override
    public int getFluidSlots() {
        return this.getSlotCount();
    }

    @Override
    public void setFluidInSlot(int slot, Fluid fluid) {
        this.fluids.set(slot, (Object)fluid);
        this.inventory.set(slot, (Object)(fluid == Fluids.f_76191_ ? ItemStack.f_41583_ : FluidDisplay.createDisplay(fluid)));
        this.markDirty();
    }

    @Override
    public boolean canInsert(int slot, Fluid fluid) {
        return fluid != Fluids.f_76191_ && fluid.m_76145_().m_76170_();
    }

    @Override
    public Fluid getFluidInSlot(int slot) {
        return (Fluid)this.fluids.get(slot);
    }
}

