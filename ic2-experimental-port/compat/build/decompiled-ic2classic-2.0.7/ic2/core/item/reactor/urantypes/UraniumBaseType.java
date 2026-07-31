/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor.urantypes;

import ic2.core.item.reactor.base.IUraniumRod;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public abstract class UraniumBaseType
implements IUraniumRod {
    private List<int[]> offsetArray = CollectionUtils.createList();

    public UraniumBaseType() {
        this.addArray(-1, 0);
        this.addArray(1, 0);
        this.addArray(0, -1);
        this.addArray(0, 1);
    }

    public void addArray(int ... array) {
        this.offsetArray.add(array);
    }

    @Override
    public List<int[]> getPulseArea() {
        return this.offsetArray;
    }

    @Override
    public List<int[]> getHeatArea() {
        return this.offsetArray;
    }

    @Override
    public ItemStack createNearDepletedRod() {
        return this.createNearDepletedRod(1);
    }
}

