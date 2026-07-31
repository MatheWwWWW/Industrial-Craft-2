/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.tags.TagsProvider$TagAppender
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.block.Block
 */
package ic2.data.tag;

import ic2.compat.AbstractBlockTagProvider;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.wiring.AbstractCableBlock;
import ic2.core.crop.TileEntityCrop;
import ic2.core.ref.Ic2BlockTags;
import ic2.core.ref.Ic2Blocks;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class Ic2BlockTagProvider
extends AbstractBlockTagProvider {
    private static final Set<Block> unconnectableBlockList = Set.of(Ic2Blocks.CLASSIC_NUKE, Ic2Blocks.NUKE, Ic2Blocks.ITNT, Ic2Blocks.ITEM_BUFFER, Ic2Blocks.OBSCURED_WALL, Ic2Blocks.IRON_FURNACE);

    public Ic2BlockTagProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    protected TagsProvider.TagAppender<Block> tag(TagKey<Block> tagKey) {
        return this.m_206424_(tagKey);
    }

    protected void m_6577_() {
        Registry.f_122824_.forEach(block -> {
            if (block instanceof AbstractCableBlock) {
                this.tag(Ic2BlockTags.CABLE_CONNECTABLE).m_126582_(block);
                return;
            }
            if (!(block instanceof Ic2TileEntityBlock)) {
                return;
            }
            Ic2TileEntityBlock ic2TileEntityBlock = (Ic2TileEntityBlock)block;
            if (!this.canCableConnect((Block)block)) {
                return;
            }
            if (ic2TileEntityBlock.getTeClass().equals(TileEntityCrop.class)) {
                return;
            }
            this.tag(Ic2BlockTags.CABLE_CONNECTABLE).m_126582_(block);
        });
    }

    public boolean canCableConnect(Block block) {
        String string = Registry.f_122824_.m_7981_((Object)block).m_135815_();
        return !unconnectableBlockList.contains(block) && !string.contains("storage_box") && !string.contains("tank");
    }
}

