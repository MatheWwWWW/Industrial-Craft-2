/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.misc.TreeTapAndBucketBlock;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.data.ModelData;

public class TreeTapAndBucketModel
extends BaseModel {
    private static final ShapeBuilder MODEL = new ShapeBuilder().newQuad(4.0, 0.0, 12.0, 12.0, 6.0, 12.0).addFace(Direction.SOUTH, 0.0f, 0.0f, 4.0f, 3.0f).finish().newQuad(12.0, 0.0, 4.0, 12.0, 6.0, 12.0).addFace(Direction.EAST, 4.0f, 0.0f, 8.0f, 3.0f).finish().newQuad(4.0, 0.0, 4.0, 12.0, 6.0, 4.0).addFace(Direction.NORTH, 8.0f, 0.0f, 12.0f, 3.0f).finish().newQuad(4.0, 0.0, 4.0, 4.0, 6.0, 12.0).addFace(Direction.WEST, 12.0f, 0.0f, 16.0f, 3.0f).finish().newQuad(5.0, 1.0, 11.0, 11.0, 6.0, 11.0).addFace(Direction.NORTH, 6.0f, 3.0f, 9.0f, 5.5f).finish().newQuad(5.0, 1.0, 5.0, 11.0, 6.0, 5.0).addFace(Direction.SOUTH, 0.0f, 3.0f, 3.0f, 5.5f).finish().newQuad(5.0, 1.0, 5.0, 5.0, 6.0, 11.0).addFace(Direction.EAST, 3.0f, 3.0f, 6.0f, 5.5f).finish().newQuad(11.0, 1.0, 5.0, 11.0, 6.0, 11.0).addFace(Direction.WEST, 9.0f, 3.0f, 12.0f, 5.5f).finish().newQuad(4.0, 0.0, 4.0, 12.0, 0.0, 12.0).addCulledFace(Direction.DOWN, 0.0f, 5.5f, 4.0f, 9.5f).finish().newQuad(4.0, 6.0, 4.0, 5.0, 6.0, 12.0).addFace(Direction.UP, 4.0f, 5.5f, 4.5f, 9.5f).finish().newQuad(11.0, 6.0, 4.0, 12.0, 6.0, 12.0).addFace(Direction.UP, 7.5f, 5.5f, 8.0f, 9.5f).finish().newQuad(5.0, 6.0, 4.0, 11.0, 6.0, 5.0).addFace(Direction.UP, 4.5f, 5.5f, 7.5f, 6.0f).finish().newQuad(5.0, 6.0, 11.0, 11.0, 6.0, 12.0).addFace(Direction.UP, 4.5f, 9.0f, 7.5f, 9.5f).finish().newQuad(5.0, 1.0, 5.0, 11.0, 1.0, 11.0).addFace(Direction.UP, 4.5f, 6.0f, 7.5f, 9.0f).finish().newQuad(7.0, 2.0, 0.0, 9.0, 4.0, 4.0).addCube(new float[][]{{0.0f, 9.5f, 1.0f, 11.5f}, {3.0f, 9.5f, 4.0f, 11.5f}, {0.0f, 0.0f, 1.0f, 1.0f}, {0.0f, 0.0f, 1.0f, 1.0f}, {1.0f, 10.5f, 3.0f, 11.5f}, {1.0f, 9.5f, 3.0f, 10.5f}}).finish().newQuad(7.2f, 7.0, 7.0, 8.8f, 11.0, 9.0).addCube(new float[][]{{13.0f, 15.0f, 14.0f, 16.0f}, {13.0f, 12.0f, 14.0f, 13.0f}, {14.0f, 13.0f, 15.0f, 15.0f}, {12.0f, 13.0f, 13.0f, 15.0f}, {15.0f, 13.0f, 16.0f, 15.0f}, {13.0f, 13.0f, 14.0f, 15.0f}}).finish().newQuad(6.0, 12.0, 6.0, 10.0, 13.0, 7.0).addCube(new float[][]{{9.5f, 15.5f, 11.5f, 16.0f}, {9.5f, 14.5f, 11.5f, 15.0f}, {9.5f, 14.0f, 11.5f, 14.5f}, {9.5f, 15.0f, 11.5f, 15.5f}, {9.0f, 15.0f, 9.5f, 15.5f}, {11.5f, 15.0f, 12.0f, 15.5f}}).finish().newQuad(7.0, 10.0, 0.0, 9.0, 12.0, 8.0).addCube(new float[][]{{10.0f, 7.0f, 11.0f, 11.0f}, {11.0f, 7.0f, 12.0f, 11.0f}, {0.0f, 0.0f, 1.0f, 1.0f}, {11.0f, 11.0f, 12.0f, 12.0f}, {12.0f, 11.0f, 16.0f, 12.0f}, {12.0f, 10.0f, 16.0f, 11.0f}}).finish();
    private static final ShapeBuilder RESIN_NEW = new ShapeBuilder().newQuad(5.0, 2.0, 5.0, 11.0, 2.0, 11.0).addFace(Direction.UP, 0.0f, 13.0f, 3.0f, 16.0f).finish().newQuad(5.0, 3.0, 5.0, 11.0, 3.0, 11.0).addFace(Direction.UP, 0.0f, 13.0f, 3.0f, 16.0f).finish().newQuad(5.0, 4.0, 5.0, 11.0, 4.0, 11.0).addFace(Direction.UP, 0.0f, 13.0f, 3.0f, 16.0f).finish().newQuad(5.0, 5.0, 5.0, 11.0, 5.0, 11.0).addFace(Direction.UP, 0.0f, 13.0f, 3.0f, 16.0f).finish().newQuad(5.0, 6.0, 5.0, 11.0, 6.0, 11.0).addFace(Direction.UP, 0.0f, 13.0f, 3.0f, 16.0f).finish();
    BlockState state;
    List<BakedQuad> quads = CollectionUtils.createList();

    public TreeTapAndBucketModel(BlockState state, TextureAtlasSprite texture) {
        this.state = state;
        this.setParticleTexture(texture);
    }

    @Override
    public void init() {
        BlockModelRotation rotation = BlockModelRotation.m_119153_((int)0, (int)((int)((Direction)this.state.m_61143_((Property)TreeTapAndBucketBlock.FACING)).m_122424_().m_122435_()));
        MODEL.buildQuads(this.m_6160_(), rotation, null, true, this.quads);
        int level = (Integer)this.state.m_61143_((Property)TreeTapAndBucketBlock.FILL_STAGE);
        if (level > 0) {
            RESIN_NEW.buildQuads(this.m_6160_(), rotation, null, true, level - 1, level, this.quads);
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? this.quads : TreeTapAndBucketModel.empty();
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        BakedModel model = this.handlePerspective(this, transformType, poseStack, applyLeftHandTransform);
        poseStack.m_85837_(0.0, 0.25, 0.0);
        poseStack.m_85841_(1.5f, 1.5f, 1.5f);
        return model;
    }

    @Override
    public boolean m_7547_() {
        return false;
    }

    @Override
    public boolean m_7541_() {
        return false;
    }
}

