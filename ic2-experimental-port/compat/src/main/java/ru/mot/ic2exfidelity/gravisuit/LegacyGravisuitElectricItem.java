package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.item.BaseElectricItem;
import net.minecraft.world.item.Item;

/** Common electric storage used by the staged Gravisuit tool ports. */
public class LegacyGravisuitElectricItem extends BaseElectricItem {
    public LegacyGravisuitElectricItem(
            Item.Properties properties, double capacity, double transferLimit, int tier) {
        super(properties, capacity, transferLimit, tier);
    }
}
