/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerSolarDestiller
extends ContainerFullInv<TileEntitySolarDestiller> {
    public ContainerSolarDestiller(int n, Inventory inventory, TileEntitySolarDestiller tileEntitySolarDestiller) {
        super(Ic2ScreenHandlers.SOLAR_DISTILLER, n, inventory, tileEntitySolarDestiller, 184);
        this.m_38897_(new SlotInvSlot(tileEntitySolarDestiller.waterinputSlot, 0, 17, 27));
        this.m_38897_(new SlotInvSlot(tileEntitySolarDestiller.destiwaterinputSlot, 0, 136, 64));
        this.m_38897_(new SlotInvSlot(tileEntitySolarDestiller.wateroutputSlot, 0, 17, 45));
        this.m_38897_(new SlotInvSlot(tileEntitySolarDestiller.destiwateroutputSlott, 0, 136, 82));
        for (int i = 0; i < 2; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntitySolarDestiller.upgradeSlot, i, 152, 8 + i * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("skyLight");
        list.add("inputTank");
        list.add("outputTank");
        return list;
    }
}

