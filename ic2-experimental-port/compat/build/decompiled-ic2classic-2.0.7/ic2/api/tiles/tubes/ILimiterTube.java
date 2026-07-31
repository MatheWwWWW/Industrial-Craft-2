/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 */
package ic2.api.tiles.tubes;

import ic2.api.tiles.tubes.ITube;
import java.util.EnumSet;
import net.minecraft.world.item.DyeColor;

public interface ILimiterTube
extends ITube {
    public EnumSet<DyeColor> getValidColors();
}

