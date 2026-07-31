/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.StairsShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.core.block.misc.base.IC2PanelBlock;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class RailingModel
extends BaseModel {
    private static final String JSON_DATA = "{\"display\":{\"gui\":{\"rotation\":[30,45,0],\"translation\":[0,0,0],\"scale\":[0.625,0.625,0.625]},\"head\":{\"rotation\":[0,0,0],\"translation\":[0,0,0],\"scale\":[1,1,1]},\"firstperson_righthand\":{\"rotation\":[0,-45,0],\"translation\":[0,0,0],\"scale\":[0.40,0.40,0.40]},\"thirdperson_lefthand\":{\"rotation\":[75,-45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]}, \"thirdperson_righthand\":{\"rotation\":[75,-45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]}}}";
    private static final ResourceLocation RAILING = new ResourceLocation("ic2:railing");
    static final ShapeBuilder STRAIGHT = new ShapeBuilder().newQuad(0.0, 0.0, 14.0, 16.0, 16.0, 16.0).addCube(new float[][]{{1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish();
    static final ShapeBuilder INNER = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 2.0, 16.0, 2.0).addCube(new float[][]{{1.0f, 1.0f, 3.0f, 3.0f}, {1.0f, 1.0f, 3.0f, 3.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish();
    static final ShapeBuilder OUTER = new ShapeBuilder().newQuad(0.0, 0.0, 14.0, 16.0, 16.0, 16.0).addCube(new float[][]{{1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish().newQuad(14.0, 0.0, 0.0, 16.0, 16.0, 16.0).addCube(new float[][]{{1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 3.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 15.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}, {1.0f, 1.0f, 3.0f, 15.0f}}).finish();
    private BlockState state;
    private String key;
    private String value;
    private List<BakedQuad> quads = CollectionUtils.createList();

    public RailingModel(BlockState state, String key, String value) {
        this.state = state;
        this.key = key;
        this.value = value;
    }

    @Override
    public void init() {
        this.setTransforms(IC2Models.getTransformMap(IC2Models.BLOCK_LOCATION, RAILING, JSON_DATA));
        this.setParticleTexture(IC2Textures.getMappedEntriesBlockIC2(this.key).get(this.value));
        StairsShape shape = (StairsShape)this.state.m_61143_(IC2PanelBlock.SHAPE);
        Direction facing = (Direction)this.state.m_61143_((Property)IC2PanelBlock.FACING);
        if (shape == StairsShape.STRAIGHT) {
            STRAIGHT.buildQuads(this.m_6160_(), BlockModelRotation.m_119153_((int)0, (int)(facing.m_122416_() * 90 + 180)), null, true, this.quads);
        } else if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
            INNER.buildQuads(this.m_6160_(), BlockModelRotation.m_119153_((int)0, (int)(((Direction)this.state.m_61143_((Property)StairBlock.f_56841_)).m_122416_() * 90 + (shape == StairsShape.INNER_LEFT ? 0 : 90))), null, true, this.quads);
        } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
            OUTER.buildQuads(this.m_6160_(), BlockModelRotation.m_119153_((int)0, (int)(((Direction)this.state.m_61143_((Property)StairBlock.f_56841_)).m_122416_() * 90 + (shape == StairsShape.OUTER_LEFT ? 180 : 270))), null, true, this.quads);
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? this.quads : RailingModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

