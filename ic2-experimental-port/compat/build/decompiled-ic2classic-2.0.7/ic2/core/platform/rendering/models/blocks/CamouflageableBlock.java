/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.platform.rendering.models.blocks;

import com.mojang.math.Matrix4f;
import ic2.api.util.DirectionList;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.ITextureProvider;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IColoredBlockModel;
import ic2.core.platform.rendering.features.block.ILockedBlockUV;
import ic2.core.platform.rendering.features.block.IRotatableBlock;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

public class CamouflageableBlock
extends SimpleBlockModel {
    List<BakedQuad>[] overlayQuad = CamouflageableBlock.multiList(7);
    boolean transparency;
    ITextureProvider overlay;

    public CamouflageableBlock(BlockState state, IBlockModel model, ITextureProvider overlay, boolean transparency) {
        super(state, model);
        this.overlay = overlay;
        this.transparency = transparency;
    }

    @Override
    public void init() {
        IRotatableBlock facing;
        super.init();
        boolean full = this.model.isFullCube(this.state);
        boolean locked = this.state.m_60734_() instanceof ILockedBlockUV;
        Direction blockFacing = Direction.NORTH;
        BlockModelRotation rotation = BlockModelRotation.X0_Y0;
        if (this.model instanceof IRotatableBlock && (facing = (IRotatableBlock)((Object)this.model)).hasRotation(this.state)) {
            blockFacing = facing.getRotation(this.state);
            Tuple<Integer, Integer> result = QuadBaker.getModelRotation(blockFacing);
            rotation = BlockModelRotation.m_119153_((int)((Integer)result.m_14418_()), (int)((Integer)result.m_14419_()));
        }
        IColoredBlockModel colorModel = this.model instanceof IColoredBlockModel ? (IColoredBlockModel)((Object)this.model) : null;
        AABB box = this.model.getModelBounds(this.state).m_82400_(0.005);
        if (full) {
            for (Direction side : DirectionList.ALL) {
                BlockModelRotation sideRotation = this.getRotation(blockFacing, side, rotation);
                BlockElementFace face = this.createBlockFace(side, colorModel == null ? -1 : colorModel.getTintedIndexFor(this.state, side, 0));
                TextureAtlasSprite sprite = IC2Models.getSafeIcon(this.overlay.getTexture(this.state, side));
                this.overlayQuad[Direction.m_122384_((Matrix4f)sideRotation.m_6189_().m_121104_(), (Direction)side).m_122411_()].add(locked ? QuadBaker.createLockedQuad(box, side, face, sprite, sideRotation, null, true) : QuadBaker.createQuad(box, side, face, sprite, sideRotation, null, true));
            }
        } else {
            for (Direction side : DirectionList.ALL) {
                BlockModelRotation sideRotation = this.getRotation(blockFacing, side, rotation);
                BlockElementFace face = this.createBlockFace(side, colorModel == null ? -1 : colorModel.getTintedIndexFor(this.state, side, 0));
                TextureAtlasSprite sprite = IC2Models.getSafeIcon(this.overlay.getTexture(this.state, side));
                this.overlayQuad[6].add(locked ? QuadBaker.createLockedQuad(box, side, face, sprite, sideRotation, null, true) : QuadBaker.createQuad(box, side, face, sprite, sideRotation, null, true));
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        Function results = (Function)data.get((ModelProperty)CamouflageProperty.INSTANCE);
        if (results == null && !this.transparency && this.isTranslucent(type)) {
            return CamouflageableBlock.empty();
        }
        return this.getResult(side == null ? 6 : side.m_122411_(), results == null ? null : (CamouflageShape.QuadResults)results.apply(type), type);
    }

    private List<BakedQuad> getResult(int index, CamouflageShape.QuadResults results, RenderType type) {
        if (results != null) {
            if (index != 6) {
                return this.overlayQuad[index];
            }
            if (this.overlayQuad[6].isEmpty()) {
                return results.getQuads(type);
            }
            return CollectionUtils.merge(results.getQuads(type), this.overlayQuad[index]);
        }
        if (this.quads[index].isEmpty() && this.overlayQuad[index].isEmpty()) {
            return CamouflageableBlock.empty();
        }
        if (!this.quads[index].isEmpty() && this.overlayQuad[index].isEmpty()) {
            return this.quads[index];
        }
        if (this.quads[index].isEmpty() && !this.overlayQuad[index].isEmpty()) {
            return this.overlayQuad[index];
        }
        return CollectionUtils.merge(this.quads[index], this.overlayQuad[index]);
    }
}

