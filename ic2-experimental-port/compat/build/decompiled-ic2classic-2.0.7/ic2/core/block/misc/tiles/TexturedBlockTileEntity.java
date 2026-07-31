/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.misc.tiles;

import ic2.api.blocks.DyeableMap;
import ic2.api.blocks.PainterHelper;
import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public abstract class TexturedBlockTileEntity
extends BaseTileEntity
implements ICamouflagable {
    @NetworkInfo
    CamouflageStorage storage;
    DyeableMap blocks;

    public TexturedBlockTileEntity(BlockPos pos, BlockState state, CamouflageShape shape, DyeableMap blocks) {
        super(pos, state);
        this.storage = new CamouflageStorage(shape);
        this.clearNetworkFields();
        this.addNetworkFields("storage");
        this.blocks = blocks;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128473_("isActive");
        compound.m_128473_("facing");
        compound.m_128473_("customName");
        NBTUtils.put(compound, "storage", this.storage.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.storage.load(compound.m_128469_("storage"));
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("storage")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        return ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T)).build();
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return true;
    }

    @Override
    public boolean removeCamouflage() {
        return false;
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.storage.canApply(dir, container)) {
            this.storage.setTexture(DirectionList.ofFacing(dir), container);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    public boolean setColor(Direction dir, DyeColor color, boolean ignoreColor) {
        DirectionList list;
        DirectionList directionList = list = dir == null ? DirectionList.ALL : DirectionList.ofFacing(dir);
        if (this.storage.canApply(list, color)) {
            this.storage.setColor(list, color);
            if (ignoreColor) {
                this.updateTileField("storage");
            } else {
                DyeColor otherColor = this.storage.isSingleColor();
                if (otherColor == null) {
                    this.updateTileField("storage");
                } else {
                    Block block = this.blocks.getBlock(otherColor);
                    if (block == null) {
                        this.updateTileField("storage");
                    } else {
                        this.setState(PainterHelper.copyProperties(this.m_58900_(), block.m_49966_()));
                    }
                }
            }
            return true;
        }
        return false;
    }
}

