/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.core.block.base.features;

import ic2.core.block.base.features.IWrenchableTile;
import net.minecraft.core.Direction;

public interface IWrenchRemovable
extends IWrenchableTile {
    @Override
    default public boolean canSetFacing(Direction dir) {
        return false;
    }
}

