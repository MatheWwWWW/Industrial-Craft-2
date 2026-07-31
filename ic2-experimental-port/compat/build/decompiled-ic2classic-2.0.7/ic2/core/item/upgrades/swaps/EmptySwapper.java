/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.item.upgrades.swaps;

import ic2.core.item.upgrades.swaps.ISwapper;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EmptySwapper
implements ISwapper {
    public static final ISwapper INSTANCE = new EmptySwapper();

    @Override
    public void transfer(BlockEntity from, BlockEntity to) {
    }
}

