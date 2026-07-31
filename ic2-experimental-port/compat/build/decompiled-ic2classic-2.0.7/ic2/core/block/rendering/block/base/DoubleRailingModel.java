/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class DoubleRailingModel
extends BaseModel {
    static final ShapeBuilder X_NEG = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 2.0, 16.0, 16.0).addFaces(DirectionList.VERTICAL.add(DirectionList.Z_AXIS), 1.0f, 1.0f, 3.0f, 14.0f).addFaces(DirectionList.X_AXIS, 1.0f, 1.0f, 14.0f, 14.0f).finish();
    static final ShapeBuilder X_POS = new ShapeBuilder().newQuad(14.0, 0.0, 0.0, 16.0, 16.0, 16.0).addFaces(DirectionList.VERTICAL.add(DirectionList.Z_AXIS), 1.0f, 1.0f, 3.0f, 14.0f).addFaces(DirectionList.X_AXIS, 1.0f, 1.0f, 14.0f, 14.0f).finish();
    static final ShapeBuilder Z_NEG = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 16.0, 16.0, 2.0).addFaces(DirectionList.VERTICAL, 1.0f, 1.0f, 14.0f, 3.0f).addFaces(DirectionList.X_AXIS, 1.0f, 1.0f, 3.0f, 14.0f).addFaces(DirectionList.Z_AXIS, 1.0f, 1.0f, 14.0f, 14.0f).finish();
    static final ShapeBuilder Z_POS = new ShapeBuilder().newQuad(0.0, 0.0, 14.0, 16.0, 16.0, 16.0).addFaces(DirectionList.VERTICAL, 1.0f, 1.0f, 14.0f, 3.0f).addFaces(DirectionList.X_AXIS, 1.0f, 1.0f, 3.0f, 14.0f).addFaces(DirectionList.Z_AXIS, 1.0f, 1.0f, 14.0f, 14.0f).finish();
    boolean zAxis;
    TextureAtlasSprite top;
    TextureAtlasSprite bottom;
    List<BakedQuad> results = CollectionUtils.createList();

    public DoubleRailingModel(boolean zAxis, TextureAtlasSprite top, TextureAtlasSprite bottom) {
        this.zAxis = zAxis;
        this.setParticleTexture(top);
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void init() {
        if (this.zAxis) {
            Z_NEG.buildQuads(this.top, BlockModelRotation.X0_Y0, null, true, this.results);
            Z_POS.buildQuads(this.bottom, BlockModelRotation.X0_Y0, null, true, this.results);
        } else {
            X_NEG.buildQuads(this.top, BlockModelRotation.X0_Y0, null, true, this.results);
            X_POS.buildQuads(this.bottom, BlockModelRotation.X0_Y0, null, true, this.results);
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? this.results : DoubleRailingModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

