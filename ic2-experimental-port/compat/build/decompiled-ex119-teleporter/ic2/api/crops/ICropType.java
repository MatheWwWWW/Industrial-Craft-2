/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.crops;

import net.minecraft.world.level.block.Block;

public interface ICropType {
    public String getName();

    public String getOwner();

    public Block getCropBlock();

    public int getMaxAge();
}

