/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.ISidedInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.items.CapabilityItemHandler
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.wrapper.InvWrapper
 *  net.minecraftforge.items.wrapper.SidedInvWrapper
 */
package ic2.core.block;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.ITeBlock;
import ic2.core.block.TeBlockRegistry;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityInventory
extends TileEntityBlock
implements ISidedInventory,
IInventorySlotHolder<TileEntityInventory> {
    private final List<InvSlot> invSlots = new ArrayList<InvSlot>();
    private final IItemHandler[] itemHandler = new IItemHandler[EnumFacing.field_82609_l.length + 1];
    protected final ComparatorEmitter comparator = this.addComponent(new ComparatorEmitter(this));

    public TileEntityInventory() {
        this.comparator.setUpdate(this::calcRedstoneFromInvSlots);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbtTagCompound) {
        super.func_145839_a(nbtTagCompound);
        NBTTagCompound invSlotsTag = nbtTagCompound.func_74775_l("InvSlots");
        for (InvSlot invSlot : this.invSlots) {
            invSlot.readFromNbt(invSlotsTag.func_74775_l(invSlot.name));
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        NBTTagCompound invSlotsTag = new NBTTagCompound();
        for (InvSlot invSlot : this.invSlots) {
            NBTTagCompound invSlotTag = new NBTTagCompound();
            invSlot.writeToNbt(invSlotTag);
            invSlotsTag.func_74782_a(invSlot.name, (NBTBase)invSlotTag);
        }
        nbt.func_74782_a("InvSlots", (NBTBase)invSlotsTag);
        return nbt;
    }

    public int func_70302_i_() {
        int ret = 0;
        for (InvSlot invSlot : this.invSlots) {
            ret += invSlot.size();
        }
        return ret;
    }

    public boolean func_191420_l() {
        for (InvSlot invSlot : this.invSlots) {
            if (invSlot.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public ItemStack func_70301_a(int index) {
        int loc = this.locateInvSlot(index);
        if (loc == -1) {
            return StackUtil.emptyStack;
        }
        return this.getStackAt(loc);
    }

    public ItemStack func_70298_a(int index, int amount) {
        int loc = this.locateInvSlot(index);
        if (loc == -1) {
            return StackUtil.emptyStack;
        }
        ItemStack stack = this.getStackAt(loc);
        if (StackUtil.isEmpty(stack)) {
            return StackUtil.emptyStack;
        }
        if (amount >= StackUtil.getSize(stack)) {
            this.putStackAt(loc, StackUtil.emptyStack);
            return stack;
        }
        if (amount != 0) {
            if (amount < 0) {
                int space = Math.min(this.getAt(loc).getStackSizeLimit(), stack.func_77976_d()) - StackUtil.getSize(stack);
                amount = Math.max(amount, -space);
            }
            this.putStackAt(loc, StackUtil.decSize(stack, amount));
        }
        ItemStack ret = stack.func_77946_l();
        ret = StackUtil.setSize(ret, amount);
        return ret;
    }

    public ItemStack func_70304_b(int index) {
        int loc = this.locateInvSlot(index);
        if (loc == -1) {
            return StackUtil.emptyStack;
        }
        ItemStack ret = this.getStackAt(loc);
        if (!StackUtil.isEmpty(ret)) {
            this.putStackAt(loc, StackUtil.emptyStack);
        }
        return ret;
    }

    public void func_70299_a(int index, ItemStack stack) {
        int loc = this.locateInvSlot(index);
        if (loc == -1) {
            assert (false);
            return;
        }
        if (StackUtil.isEmpty(stack)) {
            stack = StackUtil.emptyStack;
        }
        this.putStackAt(loc, stack);
    }

    public void func_70296_d() {
        super.func_70296_d();
        for (InvSlot invSlot : this.invSlots) {
            invSlot.onChanged();
        }
    }

    public String func_70005_c_() {
        ITeBlock teBlock = TeBlockRegistry.get(this.getClass());
        String name = teBlock == null ? "invalid" : teBlock.getName();
        return this.getBlockType().func_149739_a() + "." + name;
    }

    public boolean func_145818_k_() {
        return false;
    }

    public ITextComponent func_145748_c_() {
        return new TextComponentString(this.func_70005_c_());
    }

    public int func_70297_j_() {
        int max = 0;
        for (InvSlot slot : this.invSlots) {
            max = Math.max(max, slot.getStackSizeLimit());
        }
        return max;
    }

    public boolean func_70300_a(EntityPlayer player) {
        return !this.func_145837_r() && player.func_174818_b(this.field_174879_c) <= 64.0;
    }

    public void func_174889_b(EntityPlayer player) {
    }

    public void func_174886_c(EntityPlayer player) {
    }

    public boolean func_94041_b(int index, ItemStack stack) {
        if (stack.func_190926_b()) {
            return false;
        }
        InvSlot invSlot = this.getInventorySlot(index);
        return invSlot != null && invSlot.canInput() && invSlot.accepts(stack);
    }

    public int[] func_180463_a(EnumFacing side) {
        int[] ret = new int[this.func_70302_i_()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = i;
        }
        return ret;
    }

    public boolean func_180462_a(int index, ItemStack stack, EnumFacing side) {
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        InvSlot targetSlot = this.getInventorySlot(index);
        if (targetSlot == null) {
            return false;
        }
        if (!targetSlot.canInput() || !targetSlot.accepts(stack)) {
            return false;
        }
        if (targetSlot.preferredSide != InvSlot.InvSide.ANY && targetSlot.preferredSide.matches(side)) {
            return true;
        }
        for (InvSlot invSlot : this.invSlots) {
            if (invSlot == targetSlot || invSlot.preferredSide == InvSlot.InvSide.ANY || !invSlot.preferredSide.matches(side) || !invSlot.canInput() || !invSlot.accepts(stack)) continue;
            return false;
        }
        return true;
    }

    public boolean func_180461_b(int index, ItemStack stack, EnumFacing side) {
        InvSlot targetSlot = this.getInventorySlot(index);
        if (targetSlot == null || !targetSlot.canOutput()) {
            return false;
        }
        boolean correctSide = targetSlot.preferredSide.matches(side);
        if (targetSlot.preferredSide != InvSlot.InvSide.ANY && correctSide) {
            return true;
        }
        for (InvSlot invSlot : this.invSlots) {
            if (invSlot == targetSlot || invSlot.preferredSide == InvSlot.InvSide.ANY && correctSide || !invSlot.preferredSide.matches(side) || !invSlot.canOutput() || invSlot.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public int func_174887_a_(int id) {
        return 0;
    }

    public void func_174885_b(int id, int value) {
    }

    public int func_174890_g() {
        return 0;
    }

    public void func_174888_l() {
        for (InvSlot invSlot : this.invSlots) {
            invSlot.clear();
        }
    }

    @Override
    public int getBaseIndex(InvSlot invSlot) {
        int ret = 0;
        for (InvSlot slot : this.invSlots) {
            if (slot == invSlot) {
                return ret;
            }
            ret += slot.size();
        }
        return -1;
    }

    @Override
    public TileEntityInventory getParent() {
        return this;
    }

    @Override
    public InvSlot getInventorySlot(String name) {
        for (InvSlot invSlot : this.invSlots) {
            if (!invSlot.name.equals(name)) continue;
            return invSlot;
        }
        return null;
    }

    @Override
    public void addInventorySlot(InvSlot inventorySlot) {
        assert (this.invSlots.stream().noneMatch(slot -> slot.name.equals(inventorySlot.name)));
        this.invSlots.add(inventorySlot);
    }

    private int locateInvSlot(int extIndex) {
        if (extIndex < 0) {
            return -1;
        }
        for (int i = 0; i < this.invSlots.size(); ++i) {
            int size = this.invSlots.get(i).size();
            if (extIndex < size) {
                return i << 16 | extIndex;
            }
            extIndex -= size;
        }
        return -1;
    }

    private static int getIndex(int loc) {
        return loc >>> 16;
    }

    private static int getOffset(int loc) {
        return loc & 0xFFFF;
    }

    private InvSlot getAt(int loc) {
        assert (loc != -1);
        return this.invSlots.get(TileEntityInventory.getIndex(loc));
    }

    private ItemStack getStackAt(int loc) {
        return this.getAt(loc).get(TileEntityInventory.getOffset(loc));
    }

    private void putStackAt(int loc, ItemStack stack) {
        this.getAt(loc).put(TileEntityInventory.getOffset(loc), stack);
        super.func_70296_d();
    }

    private InvSlot getInventorySlot(int extIndex) {
        int loc = this.locateInvSlot(extIndex);
        if (loc == -1) {
            return null;
        }
        return this.getAt(loc);
    }

    @Override
    protected List<ItemStack> getAuxDrops(int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>(super.getAuxDrops(fortune));
        for (InvSlot slot : this.invSlots) {
            for (ItemStack stack : slot) {
                if (StackUtil.isEmpty(stack)) continue;
                ret.add(stack);
            }
        }
        return ret;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                if (this.itemHandler[this.itemHandler.length - 1] == null) {
                    this.itemHandler[this.itemHandler.length - 1] = new InvWrapper((IInventory)this);
                }
                return (T)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast((Object)this.itemHandler[this.itemHandler.length - 1]);
            }
            if (this.itemHandler[facing.ordinal()] == null) {
                this.itemHandler[facing.ordinal()] = new SidedInvWrapper((ISidedInventory)this, facing);
            }
            return (T)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast((Object)this.itemHandler[facing.ordinal()]);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    protected static int calcRedstoneFromInvSlots(InvSlot ... slots) {
        return TileEntityInventory.calcRedstoneFromInvSlots(Arrays.asList(slots));
    }

    protected int calcRedstoneFromInvSlots() {
        return TileEntityInventory.calcRedstoneFromInvSlots(this.invSlots);
    }

    protected static int calcRedstoneFromInvSlots(Iterable<InvSlot> invSlots) {
        int space = 0;
        int used = 0;
        for (InvSlot slot : invSlots) {
            if (slot instanceof InvSlotUpgrade) continue;
            int size = slot.size();
            int limit = slot.getStackSizeLimit();
            space += size * limit;
            for (int i = 0; i < size; ++i) {
                ItemStack stack = slot.get(i);
                if (StackUtil.isEmpty(stack)) continue;
                used += Math.min(limit, stack.func_190916_E() * limit / stack.func_77976_d());
            }
        }
        if (used == 0 || space == 0) {
            return 0;
        }
        return 1 + used * 14 / space;
    }
}

