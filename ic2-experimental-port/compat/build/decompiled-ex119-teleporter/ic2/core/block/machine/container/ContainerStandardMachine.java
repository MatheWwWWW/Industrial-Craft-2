/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.MenuType
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class ContainerStandardMachine<T extends TileEntityStandardMachine<?, ?, ?>>
extends ContainerElectricMachine<T> {
    public ContainerStandardMachine(MenuType<? extends ContainerStandardMachine<T>> menuType, int n, Inventory inventory, T t) {
        this(menuType, n, inventory, t, 166, 56, 53, 56, 17, 116, 35, 152, 8);
    }

    public ContainerStandardMachine(MenuType<? extends ContainerStandardMachine<T>> menuType, int n, Inventory inventory, T t, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        super(menuType, n, inventory, t, n2, n3, n4);
        if (((TileEntityStandardMachine)t).inputSlot != null) {
            this.m_38897_(new SlotInvSlot(((TileEntityStandardMachine)t).inputSlot, 0, n5, n6));
        }
        if (((TileEntityStandardMachine)t).outputSlot != null) {
            this.m_38897_(new SlotInvSlot(((TileEntityStandardMachine)t).outputSlot, 0, n7, n8));
        }
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new SlotInvSlot(((TileEntityStandardMachine)t).upgradeSlot, i, n9, n10 + i * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("guiProgress");
        return list;
    }
}

