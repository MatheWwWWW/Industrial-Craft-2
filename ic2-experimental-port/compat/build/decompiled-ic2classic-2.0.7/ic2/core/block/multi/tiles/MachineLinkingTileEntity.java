/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.multi.tiles;

import ic2.api.core.IC2Classic;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.multiblock.ITexturedMultiBlock;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.rendering.props.TextureProperty;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class MachineLinkingTileEntity
extends BaseLinkingTileEntity
implements ILocation {
    public ICache<IItemHandler> invCache = new FilteredCapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER, x$0 -> this.isTile((BlockEntity)x$0));
    public ICache<ITube> tubeCache = new FilteredCapabilityCache<ITube>(this, DirectionList.ALL, IC2Classic.TUBE_CAPABILITY, x$0 -> this.isTile((BlockEntity)x$0));

    public MachineLinkingTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.invCache, this.tubeCache);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MACHINE_STRUCTURE;
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        BlockEntity tile = this.getMaster();
        if (tile instanceof ITexturedMultiBlock) {
            return ModelData.builder().with((ModelProperty)TextureProperty.INSTANCE, (Object)((ITexturedMultiBlock)tile).getMasterTexture()).build();
        }
        return super.getModelData();
    }

    @Override
    protected void onCachesUpdated() {
        BlockEntity master = this.getMaster();
        if (master instanceof ICacheListener) {
            ((ICacheListener)master).onChildCacheUpdated();
        }
    }

    public static interface ICacheListener {
        public void onChildCacheUpdated();
    }
}

