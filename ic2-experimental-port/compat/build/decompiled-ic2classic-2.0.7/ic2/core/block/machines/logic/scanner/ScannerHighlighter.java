/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.machines.logic.scanner;

import ic2.core.block.rendering.world.impl.BlockHighlighter;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ScannerHighlighter
implements BlockHighlighter.IHighlightController {
    LongSet present = new LongOpenHashSet();
    BlockEntity tile;

    public ScannerHighlighter(BlockEntity tile) {
        this.tile = tile;
    }

    public void clearOrSet(LongList list, int color) {
        if (this.present.isEmpty()) {
            this.present.addAll((LongCollection)list);
            int m = list.size();
            for (int i = 0; i < m; ++i) {
                BlockHighlighter.INSTANCE.addHighlight(BlockPos.m_122022_((long)list.getLong(i)), 5, color, false, this);
            }
            return;
        }
        this.present.clear();
    }

    @Override
    public boolean keepAlive(BlockHighlighter.Highlighter owner) {
        if (this.present.contains(owner.pos.m_121878_()) && !this.tile.m_58901_()) {
            owner.ticksLeft = 2;
            return true;
        }
        return false;
    }
}

