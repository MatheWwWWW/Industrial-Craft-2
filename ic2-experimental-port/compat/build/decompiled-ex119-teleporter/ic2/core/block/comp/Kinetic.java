/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.comp;

import ic2.core.block.comp.TileEntityComponent;
import ic2.core.block.tileentity.Ic2TileEntity;
import java.util.Set;
import net.minecraft.core.Direction;

public class Kinetic
extends TileEntityComponent {
    private Set<Direction> sinkDirections;
    private Set<Direction> sourceDirections;

    public Kinetic(Ic2TileEntity ic2TileEntity, double d, Set<Direction> set, Set<Direction> set2, int n, int n2, boolean bl) {
        super(ic2TileEntity);
    }
}

