/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.tiles;

import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ArrayTube
implements ITube {
    ITube[] array;
    int index;
    boolean loop;

    public ArrayTube(List<ITube> tubes) {
        this(tubes.toArray(new ITube[tubes.size()]));
    }

    public ArrayTube(ITube[] array) {
        this(array, true);
    }

    public ArrayTube(ITube[] array, boolean loop) {
        this.array = array;
        this.loop = loop;
    }

    @Override
    public ITube.TubeType getTubeType() {
        return ITube.TubeType.SIMPLE;
    }

    @Override
    public void addItem(ItemStack item, Direction side, DyeColor color) {
        this.index = this.loop ? ++this.index % this.array.length : 0;
        this.array[this.index].addItem(item, side, color);
    }

    @Override
    public void addItem(TransportedItem item, Direction side) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canAddItem(TransportedItem item, Direction side) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        throw new UnsupportedOperationException();
    }
}

