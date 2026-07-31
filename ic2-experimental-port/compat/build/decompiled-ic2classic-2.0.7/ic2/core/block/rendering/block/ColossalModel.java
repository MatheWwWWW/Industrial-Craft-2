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
package ic2.core.block.rendering.block;

import com.mojang.math.Matrix4f;
import ic2.api.util.DirectionList;
import ic2.core.block.rendering.props.TextureProperty;
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
import java.util.Map;
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

public class ColossalModel
extends SimpleBlockModel {
    Map<ITextureProvider, List<BakedQuad>[]> quadMap = CollectionUtils.createLinkedMap();

    public ColossalModel(BlockState state, IBlockModel model) {
        super(state, model);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        ITextureProvider provider = (ITextureProvider)data.get((ModelProperty)TextureProperty.INSTANCE);
        if (provider != null) {
            List<BakedQuad>[] quads = this.quadMap.get(provider);
            if (quads == null) {
                quads = this.generateTextures(provider);
            }
            return quads[side == null ? 6 : side.m_122411_()];
        }
        return super.getQuads(state, side, rand, data, type);
    }

    private List<BakedQuad>[] generateTextures(ITextureProvider provider) {
        IRotatableBlock facing;
        List<K>[] quads = CollectionUtils.createLists(7);
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
        AABB box = this.model.getModelBounds(this.state);
        if (full) {
            for (Direction side : DirectionList.ALL) {
                BlockModelRotation sideRotation = this.getRotation(blockFacing, side, rotation);
                BlockElementFace face = this.createBlockFace(side, colorModel == null ? -1 : colorModel.getTintedIndexFor(this.state, side, 0));
                TextureAtlasSprite sprite = IC2Models.getSafeIcon(provider.getTexture(this.state, side));
                quads[Direction.m_122384_((Matrix4f)sideRotation.m_6189_().m_121104_(), (Direction)side).m_122411_()].add(locked ? QuadBaker.createLockedQuad(box, side, face, sprite, sideRotation, null, true) : QuadBaker.createQuad(box, side, face, sprite, sideRotation, null, true));
            }
        } else {
            for (Direction side : DirectionList.ALL) {
                BlockModelRotation sideRotation = this.getRotation(blockFacing, side, rotation);
                BlockElementFace face = this.createBlockFace(side, colorModel == null ? -1 : colorModel.getTintedIndexFor(this.state, side, 0));
                TextureAtlasSprite sprite = IC2Models.getSafeIcon(provider.getTexture(this.state, side));
                quads[6].add(locked ? QuadBaker.createLockedQuad(box, side, face, sprite, sideRotation, null, true) : QuadBaker.createQuad(box, side, face, sprite, sideRotation, null, true));
            }
        }
        this.quadMap.put(provider, quads);
        return quads;
    }
}

