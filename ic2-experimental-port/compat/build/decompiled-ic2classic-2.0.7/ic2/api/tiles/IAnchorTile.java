/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.tiles;

import net.minecraft.core.Direction;

public interface IAnchorTile {
    public boolean hasAnchor(Direction var1);

    public boolean addAnchor(Direction var1);

    public boolean removeAnchor(Direction var1);
}

