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
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.core.block.misc.base.IC2EdgeRailingBlock;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.data.ModelData;

public class EdgeRailingModel
extends BaseModel {
    static final ShapeBuilder OUTER = new ShapeBuilder().newQuad(0.0, 0.0, 14.0, 16.0, 16.0, 16.0).addCube(new float[][]{{1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish().newQuad(14.0, 0.0, 0.0, 16.0, 16.0, 16.0).addCube(new float[][]{{1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish();
    static final ShapeBuilder INNER = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 2.0, 16.0, 2.0).addCube(new float[][]{{1.0f, 1.0f, 3.0f, 3.0f}, {1.0f, 1.0f, 3.0f, 3.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish();
    private BlockState state;
    TextureAtlasSprite main;
    TextureAtlasSprite secondary;
    private List<BakedQuad> quads = CollectionUtils.createList();

    public EdgeRailingModel(BlockState state, TextureAtlasSprite main, TextureAtlasSprite secondary) {
        this.state = state;
        this.setParticleTexture(main);
        this.main = main;
        this.secondary = secondary;
    }

    @Override
    public void init() {
        boolean right = (Boolean)this.state.m_61143_((Property)IC2EdgeRailingBlock.RIGHT);
        Direction facing = (Direction)this.state.m_61143_((Property)IC2EdgeRailingBlock.FACING);
        OUTER.buildQuads(this.main, BlockModelRotation.m_119153_((int)0, (int)(facing.m_122416_() * 90 + (right ? 270 : 180))), null, true, this.quads);
        INNER.buildQuads(this.secondary, BlockModelRotation.m_119153_((int)0, (int)(facing.m_122424_().m_122416_() * 90 + (right ? 90 : 0))), null, true, this.quads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? this.quads : EdgeRailingModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

