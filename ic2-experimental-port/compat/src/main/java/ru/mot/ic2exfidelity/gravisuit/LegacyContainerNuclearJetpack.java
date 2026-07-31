package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.reactor.IBaseReactorComponent;
import ic2.core.item.ContainerHandHeldInventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Original five-by-five reactor grid and player-inventory geometry. */
public final class LegacyContainerNuclearJetpack
        extends ContainerHandHeldInventory<LegacyNuclearJetpackInventory> {
    public LegacyContainerNuclearJetpack(
            int syncId, LegacyNuclearJetpackInventory base) {
        super(RestoredLegacyContent.nuclearJetpackMenu(), syncId, base);
        for (int y = 0; y < LegacyNuclearJetpackReactor.WIDTH; y++) {
            for (int x = 0; x < LegacyNuclearJetpackReactor.WIDTH; x++) {
                int slot = x + y * LegacyNuclearJetpackReactor.WIDTH;
                m_38897_(new Slot(base, slot, 44 + x * 18, 18 + y * 18) {
                    @Override
                    public boolean m_5857_(ItemStack stack) {
                        return !stack.m_41619_()
                                && stack.m_41720_()
                                        instanceof IBaseReactorComponent
                                && base.m_7013_(slot, stack);
                    }
                });
            }
        }
        addPlayerInventorySlots(base.player.m_150109_(), 222);
    }
}
