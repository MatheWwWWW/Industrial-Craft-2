/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.tiles.tubes;

import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.ILocation;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public interface ITube
extends ILocation {
    default public void addItem(ItemStack item, Direction side) {
        this.addItem(item, side, null);
    }

    public void addItem(ItemStack var1, Direction var2, DyeColor var3);

    public void addItem(TransportedItem var1, Direction var2);

    public boolean canAddItem(TransportedItem var1, Direction var2);

    public boolean canConnect(ITube var1, Direction var2);

    public TubeType getTubeType();

    public static enum TubeType {
        SIMPLE,
        EXTRACTION;

    }
}

