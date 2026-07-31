/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.reactor.container;

import ic2.core.ContainerBase;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerNuclearReactor
extends ContainerBase<TileEntityNuclearReactorElectric> {
    private final int size;

    public ContainerNuclearReactor(int n, Inventory inventory, TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric) {
        super(Ic2ScreenHandlers.NUCLEAR_REACTOR, n, inventory, tileEntityNuclearReactorElectric);
        this.size = tileEntityNuclearReactorElectric.getReactorSize();
        int n2 = 26;
        int n3 = 25;
        int n4 = tileEntityNuclearReactorElectric.reactorSlot.size();
        for (int i = 0; i < n4; ++i) {
            int n5 = i % this.size;
            int n6 = i / this.size;
            this.m_38897_(new SlotInvSlot(tileEntityNuclearReactorElectric.reactorSlot, i, n2 + 18 * n5, n3 + 18 * n6));
        }
        this.addPlayerInventorySlots(inventory, 214, 243);
        this.m_38897_(new SlotInvSlot(tileEntityNuclearReactorElectric.coolantinputSlot, 0, 8, 25));
        this.m_38897_(new SlotInvSlot(tileEntityNuclearReactorElectric.hotcoolinputSlot, 0, 188, 25));
        this.m_38897_(new SlotInvSlot(tileEntityNuclearReactorElectric.coolantoutputSlot, 0, 8, 115));
        this.m_38897_(new SlotInvSlot(tileEntityNuclearReactorElectric.hotcoolantoutputSlot, 0, 188, 115));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("heat");
        list.add("maxHeat");
        list.add("EmitHeat");
        list.add("inputTank");
        list.add("outputTank");
        list.add("fluidCooled");
        return list;
    }
}

