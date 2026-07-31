package ru.mot.ic2exfidelity.legacy;

import ic2.api.crops.ICropSeed;
import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.slot.SlotDischarge;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

public final class LegacyContainerCropAnalyzer
        extends ContainerHandHeldInventory<LegacyHandHeldCropAnalyzer> {
    public LegacyContainerCropAnalyzer(int syncId, LegacyHandHeldCropAnalyzer base) {
        super(RestoredLegacyContent.cropAnalyzerMenu(), syncId, base);
        m_38897_(new Slot(base, 0, 8, 7) {
            @Override
            public boolean m_5857_(ItemStack stack) {
                return !stack.m_41619_() && stack.m_41720_() instanceof ICropSeed;
            }
        });
        m_38897_(new Slot(base, 1, 41, 7) {
            @Override
            public boolean m_5857_(ItemStack stack) {
                return false;
            }
        });
        m_38897_(new SlotDischarge(base, 2, 152, 7));
        addPlayerInventorySlots(base.player.m_150109_(), 223);
    }

    @Override
    public void m_38946_() {
        if (!base.player.m_20193_().f_46443_) {
            base.tickServer();
        }
        super.m_38946_();
    }
}
