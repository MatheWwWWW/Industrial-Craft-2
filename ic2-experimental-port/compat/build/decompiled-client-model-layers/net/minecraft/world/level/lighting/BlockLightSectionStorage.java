/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 */
package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.DataLayerStorageMap;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;

public class BlockLightSectionStorage
extends LayerLightSectionStorage<BlockDataLayerStorageMap> {
    protected BlockLightSectionStorage(LightChunkGetter p_75511_) {
        super(LightLayer.BLOCK, p_75511_, new BlockDataLayerStorageMap((Long2ObjectOpenHashMap<DataLayer>)new Long2ObjectOpenHashMap()));
    }

    @Override
    protected int m_6181_(long p_75513_) {
        long $$1 = SectionPos.m_123235_(p_75513_);
        DataLayer $$2 = this.m_75758_($$1, false);
        if ($$2 == null) {
            return 0;
        }
        return $$2.m_62560_(SectionPos.m_123207_(BlockPos.m_121983_(p_75513_)), SectionPos.m_123207_(BlockPos.m_122008_(p_75513_)), SectionPos.m_123207_(BlockPos.m_122015_(p_75513_)));
    }

    protected static final class BlockDataLayerStorageMap
    extends DataLayerStorageMap<BlockDataLayerStorageMap> {
        public BlockDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> p_75515_) {
            super(p_75515_);
        }

        @Override
        public BlockDataLayerStorageMap m_5972_() {
            return new BlockDataLayerStorageMap((Long2ObjectOpenHashMap<DataLayer>)this.f_75518_.clone());
        }

        @Override
        public /* synthetic */ DataLayerStorageMap m_5972_() {
            return this.m_5972_();
        }
    }
}

