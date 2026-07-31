/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.storage.tiles.tank;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class BaseValveTileEntity
extends BaseLinkingTileEntity
implements ICamouflagable {
    @NetworkInfo
    public CamouflageStorage storage = new CamouflageStorage(CamouflageShape.FULL_CUBE);
    @NetworkInfo
    public boolean textureCopied = false;

    public BaseValveTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("storage", "textureCopied");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.VALVE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "storage", this.storage.save(new CompoundTag()));
        if (this.textureCopied) {
            NBTUtils.putBoolean(compound, "tex", this.textureCopied, false);
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.textureCopied = compound.m_128471_("tex");
        if (this.textureCopied) {
            this.storage.load(compound.m_128469_("storage"));
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("storage")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.storage.canApply(DirectionList.ALL, container)) {
            this.storage.setTexture(DirectionList.ALL, container);
            this.textureCopied = true;
            this.updateTileFields("storage", "textureCopied");
            return true;
        }
        return false;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return this.textureCopied;
    }

    @Override
    public boolean removeCamouflage() {
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        return this.textureCopied ? ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T)).build() : ModelData.EMPTY;
    }
}

