package ru.mot.ic2exfidelity.legacy;

import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Exact 176x227 menu layout used by the 2.8.222 Trading Terminal. */
public final class LegacyContainerTradingTerminal
        extends ContainerFullInv<LegacyTradingTerminalBlockEntity> {
    public final Slot rangeSlot;

    public LegacyContainerTradingTerminal(
            int syncId, Inventory inventory, LegacyTradingTerminalBlockEntity base) {
        super(RestoredLegacyContent.tradingTerminalMenu(), syncId, inventory, base, 227);
        rangeSlot = m_38897_(new SlotInvSlot(base.rangeUpgrade, 0, -100, -100));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> fields = super.getNetworkedFields();
        fields.add("range");
        return fields;
    }
}
