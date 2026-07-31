package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.item.tool.ItemToolWrenchElectric;
import net.minecraft.world.item.Item;

/** IC2 Classic precision wrench EU parameters used by GraviSuite's exact recipe. */
public final class LegacyPrecisionWrench extends ItemToolWrenchElectric {
    public LegacyPrecisionWrench(Item.Properties properties) {
        super(properties);
        maxCharge = 40_000;
        transferLimit = 350;
        tier = 2;
        operationEnergyCost = 100;
    }
}
