/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 */
package ic2.core.slot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;

public class ArmorSlot {
    private static final EquipmentSlot[] armorSlots = ArmorSlot.getArmorSlots();
    private static final List<EquipmentSlot> armorSlotList = Collections.unmodifiableList(Arrays.asList(armorSlots));

    public static EquipmentSlot get(int n) {
        return armorSlots[n];
    }

    public static int getCount() {
        return armorSlots.length;
    }

    public static Iterable<EquipmentSlot> getAll() {
        return armorSlotList;
    }

    private static EquipmentSlot[] getArmorSlots() {
        int n;
        EquipmentSlot[] equipmentSlotArray = EquipmentSlot.values();
        int n2 = 0;
        for (EquipmentSlot equipmentSlot : equipmentSlotArray) {
            if (equipmentSlot.m_20743_() != EquipmentSlot.Type.ARMOR) continue;
            ++n2;
        }
        EquipmentSlot[] equipmentSlotArray2 = new EquipmentSlot[n2];
        block1: for (n = 0; n < equipmentSlotArray2.length; ++n) {
            for (EquipmentSlot equipmentSlot : equipmentSlotArray) {
                if (equipmentSlot.m_20743_() != EquipmentSlot.Type.ARMOR || equipmentSlot.m_20749_() != n) continue;
                equipmentSlotArray2[n] = equipmentSlot;
                continue block1;
            }
        }
        for (n = 0; n < equipmentSlotArray2.length; ++n) {
            if (equipmentSlotArray2[n] != null) continue;
            throw new RuntimeException("Can't find an armor mapping for idx " + n);
        }
        return equipmentSlotArray2;
    }
}

