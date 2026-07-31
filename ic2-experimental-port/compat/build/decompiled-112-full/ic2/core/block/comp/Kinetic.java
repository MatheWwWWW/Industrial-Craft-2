/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package ic2.core.block.comp;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.TileEntityComponent;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class Kinetic
extends TileEntityComponent {
    private Set<EnumFacing> sinkDirections;
    private Set<EnumFacing> sourceDirections;

    public Kinetic(TileEntityBlock parent, double capacity, Set<EnumFacing> sinkDirections, Set<EnumFacing> sourceDirections, int sinkTier, int sourceTier, boolean fullEnergy) {
        super(parent);
    }
}

