/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerLiquidHeatExchanger
extends ContainerFullInv<TileEntityLiquidHeatExchanger> {
    public ContainerLiquidHeatExchanger(int n, Inventory inventory, TileEntityLiquidHeatExchanger tileEntityLiquidHeatExchanger) {
        super(Ic2ScreenHandlers.LIQUID_HEAT_EXCHANGER, n, inventory, tileEntityLiquidHeatExchanger, 204);
        int n2;
        this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.hotfluidinputSlot, 0, 8, 103));
        this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.cooloutputSlot, 0, 152, 103));
        this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.coolfluidinputSlot, 0, 134, 103));
        this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.hotoutputSlot, 0, 26, 103));
        for (n2 = 0; n2 < 3; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.upgradeSlot, n2, 62 + n2 * 18, 103));
        }
        for (n2 = 0; n2 < 5; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.heatexchangerslots, n2, 46 + n2 * 17, 50));
        }
        for (n2 = 5; n2 < 10; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityLiquidHeatExchanger.heatexchangerslots, n2, 46 + (n2 - 5) * 17, 72));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("inputTank");
        list.add("outputTank");
        list.add("transmitHeat");
        list.add("maxHeatEmitpeerTick");
        return list;
    }
}

