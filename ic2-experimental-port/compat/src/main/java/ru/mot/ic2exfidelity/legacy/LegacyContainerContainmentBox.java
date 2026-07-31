package ru.mot.ic2exfidelity.legacy;

import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.slot.SlotRadioactive;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

public final class LegacyContainerContainmentBox
        extends ContainerHandHeldInventory<LegacyHandHeldContainmentBox> {
    public LegacyContainerContainmentBox(int syncId, LegacyHandHeldContainmentBox base) {
        super(RestoredLegacyContent.containmentBoxMenu(), syncId, base);
        for (int slot = 0; slot < 12; slot++) {
            int column = slot % 4;
            int row = slot / 4;
            m_38897_(new SlotRadioactive(base, slot, 53 + column * 18, 19 + row * 18));
        }
        addPlayerInventorySlots(base.player.m_150109_(), 166);
    }
}
