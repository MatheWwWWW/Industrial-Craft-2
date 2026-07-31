/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.platform.rendering.models.blocks;

import com.mojang.math.Matrix4f;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.IBlockModifiers;
import ic2.core.platform.rendering.features.block.IColoredBlockModel;
import ic2.core.platform.rendering.features.block.ILockedBlockUV;
import ic2.core.platform.rendering.features.block.IRotatableBlock;
import ic2.core.platform.rendering.models.BaseModel;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class SimpleBlockModel
extends BaseModel {
    public static final BlockFaceUV DEFAULT_UVS = new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0);
    protected BlockState state;
    protected IBlockModel model;
    protected List<BakedQuad>[] quads = SimpleBlockModel.multiList(7);
    protected IBlockModifiers mod;

    public SimpleBlockModel(BlockState state, IBlockModel model) {
        this.state = state;
        this.model = model;
    }

    @Override
    public void init() {
        IRotatableBlock facing;
        this.setParticleTexture(this.model.getSpriteForParticle(this.state));
        this.mod = this.state.m_60734_() instanceof IBlockModifiers ? (IBlockModifiers)this.state.m_60734_() : null;
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
                TextureAtlasSprite sprite = IC2Models.getSafeIcon(this.model.getSpriteForState(this.state, side));
                this.quads[Direction.m_122384_((Matrix4f)sideRotation.m_6189_().m_121104_(), (Direction)side).m_122411_()].add(locked ? QuadBaker.createLockedQuad(box, side, face, sprite, sideRotation, null, true) : QuadBaker.createQuad(box, side, face, sprite, sideRotation, null, true));
            }
        } else {
            for (Direction side : DirectionList.ALL) {
                BlockModelRotation sideRotation = this.getRotation(blockFacing, side, rotation);
                BlockElementFace face = this.createBlockFace(side, colorModel == null ? -1 : colorModel.getTintedIndexFor(this.state, side, 0));
                TextureAtlasSprite sprite = IC2Models.getSafeIcon(this.model.getSpriteForState(this.state, side));
                this.quads[6].add(locked ? QuadBaker.createLockedQuad(box, side, face, sprite, sideRotation, null, true) : QuadBaker.createQuad(box, side, face, sprite, sideRotation, null, true));
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        return this.quads[side == null ? 6 : side.m_122411_()];
    }

    @Override
    public boolean m_7547_() {
        return true;
    }

    protected BlockModelRotation getRotation(Direction facing, Direction side, BlockModelRotation defaultRotation) {
        return defaultRotation;
    }

    protected BlockElementFace createBlockFace(Direction side, int color) {
        BlockElementFace blockElementFace;
        if (this.mod != null) {
            float[] fArray;
            if (this.mod.hasCustomTextureUVs(this.state, side)) {
                fArray = this.mod.getCustomTextureUVs(this.state, side);
            } else {
                float[] fArray2 = new float[4];
                fArray2[0] = 0.0f;
                fArray2[1] = 0.0f;
                fArray2[2] = 16.0f;
                fArray = fArray2;
                fArray2[3] = 16.0f;
            }
            blockElementFace = new BlockElementFace(null, color, "", new BlockFaceUV(fArray, this.mod.hasTextureRotation(this.state, side) ? this.mod.getTextureRotation(this.state, side) : 0));
        } else {
            blockElementFace = new BlockElementFace(null, color, "", DEFAULT_UVS);
        }
        return blockElementFace;
    }
}

