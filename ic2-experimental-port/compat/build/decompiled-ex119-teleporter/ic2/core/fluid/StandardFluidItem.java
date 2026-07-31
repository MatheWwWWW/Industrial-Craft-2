/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 *  org.apache.commons.lang3.mutable.Mutable
 */
package ic2.core.fluid;

import ic2.core.fluid.Ic2FluidItem;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.Mutable;

public interface StandardFluidItem
extends Ic2FluidItem {
    default public boolean canDrain(ItemStack itemStack) {
        return true;
    }

    default public boolean canFill(ItemStack itemStack, Ic2FluidStack ic2FluidStack) {
        return true;
    }

    @Override
    default public Ic2FluidStack getFluidStack(ItemStack itemStack) {
        Ic2FluidStack ic2FluidStack = StandardFluidItem.getFs(itemStack);
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return Ic2FluidStack.EMPTY;
        }
        return ic2FluidStack;
    }

    @Override
    default public Ic2FluidStack drainMb(ItemStack itemStack, int n, boolean bl, Mutable<ItemStack> mutable) {
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        if (n < 0) {
            throw new IllegalArgumentException("negative amount");
        }
        if (n == 0) {
            return Ic2FluidStack.EMPTY;
        }
        if (itemStack.m_41613_() != 1) {
            throw new IllegalArgumentException("invalid stack size");
        }
        if (!this.canDrain(itemStack)) {
            return Ic2FluidStack.EMPTY;
        }
        Ic2FluidStack ic2FluidStack = StandardFluidItem.getFs(itemStack);
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            return Ic2FluidStack.EMPTY;
        }
        if (ic2FluidStack.getAmountMb() <= n) {
            n = ic2FluidStack.getAmountMb();
            if (!bl) {
                StandardFluidItem.setFs(itemStack, null);
                this.updateDamage(itemStack, 0);
            }
        } else {
            if (!bl) {
                ic2FluidStack.decreaseMb(n);
                StandardFluidItem.setFs(itemStack, ic2FluidStack);
                this.updateDamage(itemStack, ic2FluidStack.getAmountMb());
            }
            ic2FluidStack.setAmountMb(n);
        }
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        return ic2FluidStack;
    }

    @Override
    default public int drainMb(ItemStack itemStack, Ic2FluidStack ic2FluidStack, boolean bl, Mutable<ItemStack> mutable) {
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        if (ic2FluidStack == null) {
            throw new IllegalArgumentException("invalid drain medium");
        }
        if (ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (itemStack.m_41613_() != 1) {
            throw new IllegalArgumentException("invalid stack size");
        }
        if (!this.canDrain(itemStack)) {
            return 0;
        }
        Ic2FluidStack ic2FluidStack2 = StandardFluidItem.getFs(itemStack);
        if (ic2FluidStack2 == null || ic2FluidStack2.isEmpty() || !ic2FluidStack2.hasExactFluid(ic2FluidStack)) {
            return 0;
        }
        int n = ic2FluidStack.getAmountMb();
        if (ic2FluidStack2.getAmountMb() <= n) {
            n = ic2FluidStack2.getAmountMb();
            if (!bl) {
                StandardFluidItem.setFs(itemStack, null);
                this.updateDamage(itemStack, 0);
            }
        } else if (!bl) {
            ic2FluidStack2.decreaseMb(n);
            StandardFluidItem.setFs(itemStack, ic2FluidStack2);
            this.updateDamage(itemStack, ic2FluidStack2.getAmountMb());
        }
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        return n;
    }

    @Override
    default public int fillMb(ItemStack itemStack, Ic2FluidStack ic2FluidStack, boolean bl, Mutable<ItemStack> mutable) {
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        if (ic2FluidStack == null) {
            throw new IllegalArgumentException("invalid fill medium");
        }
        if (ic2FluidStack.isEmpty()) {
            return 0;
        }
        if (itemStack.m_41613_() != 1) {
            throw new IllegalArgumentException("invalid stack size");
        }
        if (!this.canFill(itemStack, ic2FluidStack)) {
            return 0;
        }
        int n = this.getCapacityMb(itemStack);
        if (n <= 0) {
            return 0;
        }
        int n2 = ic2FluidStack.getAmountMb();
        Ic2FluidStack ic2FluidStack2 = StandardFluidItem.getFs(itemStack);
        if (ic2FluidStack2 == null || ic2FluidStack2.isEmpty() && !ic2FluidStack2.hasExactFluid(ic2FluidStack)) {
            n2 = Math.min(n2, n);
            if (!bl) {
                ic2FluidStack2 = ic2FluidStack.copyWithAmountMb(n2);
                StandardFluidItem.setFs(itemStack, ic2FluidStack2);
                this.updateDamage(itemStack, n2);
            }
        } else {
            if (!ic2FluidStack2.hasExactFluid(ic2FluidStack)) {
                return 0;
            }
            n2 = Math.min(n2, n - ic2FluidStack2.getAmountMb());
            if (!bl) {
                ic2FluidStack2.increaseMb(n2);
                StandardFluidItem.setFs(itemStack, ic2FluidStack2);
                this.updateDamage(itemStack, ic2FluidStack2.getAmountMb());
            }
        }
        if (mutable != null) {
            mutable.setValue((Object)itemStack);
        }
        return n2;
    }

    public static Ic2FluidStack getFs(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            return null;
        }
        if (!compoundTag.m_128425_("Fluid", 10)) {
            return null;
        }
        return Ic2FluidStack.read(compoundTag.m_128469_("Fluid"));
    }

    public static void setFs(ItemStack itemStack, Ic2FluidStack ic2FluidStack) {
        CompoundTag compoundTag = itemStack.m_41783_();
        if (compoundTag == null) {
            compoundTag = new CompoundTag();
            itemStack.m_41751_(compoundTag);
        }
        if (ic2FluidStack == null || ic2FluidStack.isEmpty()) {
            compoundTag.m_128473_("Fluid");
        } else {
            CompoundTag compoundTag2 = new CompoundTag();
            ic2FluidStack.toNbt(compoundTag2);
            compoundTag.m_128365_("Fluid", (Tag)compoundTag2);
        }
    }

    default public void updateDamage(ItemStack itemStack, int n) {
        if (!itemStack.m_41763_()) {
            return;
        }
        int n2 = itemStack.m_41776_();
        if (n2 > 2) {
            itemStack.m_41721_(n2 - 1 - (int)Util.map(n, this.getCapacityMb(itemStack), n2 - 2));
        }
    }
}

