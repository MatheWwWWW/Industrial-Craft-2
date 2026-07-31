package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.item.ContainerHandHeldInventory;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

public final class LegacyContainerRelocator
        extends ContainerHandHeldInventory<LegacyRelocatorInventory> {
    public LegacyContainerRelocator(int syncId, LegacyRelocatorInventory base) {
        super(RestoredLegacyContent.relocatorMenu(), syncId, base);
    }
}
