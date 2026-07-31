/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.invslot;

import ic2.api.item.IKineticRotor;
import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableClass;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class InvSlotConsumableKineticRotor
extends InvSlotConsumableClass {
    private final String updateName;
    private final IKineticRotor.GearboxType type;

    public InvSlotConsumableKineticRotor(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, IKineticRotor.GearboxType gearboxType) {
        this(iInventorySlotHolder, string, access, n, invSide, gearboxType, null);
    }

    public InvSlotConsumableKineticRotor(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, IKineticRotor.GearboxType gearboxType, String string2) {
        super(iInventorySlotHolder, string, access, n, invSide, IKineticRotor.class);
        this.type = gearboxType;
        this.updateName = string2;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        if (super.accepts(itemStack)) {
            return ((IKineticRotor)itemStack.m_41720_()).isAcceptedType(itemStack, this.type);
        }
        return false;
    }

    @Override
    public void onChanged() {
        if (this.updateName != null && this.base.getParent().m_58898_() && !this.base.getParent().m_58904_().f_46443_) {
            IC2.network.get(true).updateTileEntityField((BlockEntity)this.base.getParent(), this.updateName);
        }
    }
}

