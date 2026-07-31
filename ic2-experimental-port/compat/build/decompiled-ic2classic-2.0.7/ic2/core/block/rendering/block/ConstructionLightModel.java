/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block;

import ic2.core.block.cables.luminator.ConstructionLightBlock;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.data.ModelData;

public class ConstructionLightModel
extends BaseModel {
    public static final ShapeBuilder BUILDER = new ShapeBuilder().newQuad(7.0, 0.0, 9.0, 9.0, 1.0, 16.0).addCube(new float[][]{{4.75f, 0.0f, 4.25f, 1.75f}, {4.25f, 1.75f, 3.75f, 0.0f}, {3.75f, 1.75f, 4.25f, 2.0f}, {6.0f, 1.75f, 6.5f, 2.0f}, {4.25f, 1.75f, 6.0f, 2.0f}, {2.0f, 1.75f, 3.75f, 2.0f}}).finish().newQuad(7.0, 0.0, 0.0, 9.0, 1.0, 7.0).addCube(new float[][]{{4.75f, 2.0f, 4.25f, 3.75f}, {4.25f, 3.75f, 3.75f, 2.0f}, {3.75f, 3.75f, 4.25f, 4.0f}, {6.0f, 3.75f, 6.5f, 4.0f}, {4.25f, 3.75f, 6.0f, 4.0f}, {2.0f, 3.75f, 3.75f, 4.0f}}).finish().newQuad(9.0, 0.0, 7.0, 16.0, 1.0, 9.0).addCube(new float[][]{{8.75f, 0.0f, 7.0f, 0.5f}, {7.0f, 0.5f, 5.25f, 0.0f}, {5.25f, 0.5f, 7.0f, 0.75f}, {7.5f, 0.5f, 9.25f, 0.75f}, {7.0f, 0.5f, 7.5f, 0.75f}, {4.75f, 0.5f, 5.25f, 0.75f}}).finish().newQuad(0.0, 0.0, 7.0, 7.0, 1.0, 9.0).addCube(new float[][]{{8.75f, 0.75f, 7.0f, 1.25f}, {7.0f, 1.25f, 5.25f, 0.75f}, {5.25f, 1.25f, 7.0f, 1.5f}, {7.5f, 1.25f, 9.25f, 1.5f}, {7.0f, 1.25f, 7.5f, 1.5f}, {4.75f, 1.25f, 5.25f, 1.5f}}).finish().newQuad(7.0, 0.0, 7.0, 9.0, 27.0, 9.0).addCube(new float[][]{{1.5f, 0.0f, 1.0f, 0.5f}, {1.0f, 0.5f, 0.5f, 0.0f}, {0.5f, 0.5f, 1.0f, 7.25f}, {1.5f, 0.5f, 2.0f, 7.25f}, {1.0f, 0.5f, 1.5f, 7.25f}, {0.0f, 0.5f, 0.5f, 7.25f}}).finish().newQuad(7.0, 25.0, 5.0, 9.0, 27.0, 7.0).addCube(new float[][]{{3.5f, 5.5f, 3.0f, 6.0f}, {3.0f, 6.0f, 2.5f, 5.5f}, {2.5f, 6.0f, 3.0f, 6.5f}, {3.5f, 6.0f, 4.0f, 6.5f}, {3.0f, 6.0f, 3.5f, 6.5f}, {2.0f, 6.0f, 2.5f, 6.5f}}).finish().newQuad(6.0, 24.0, 4.0, 10.0, 28.0, 5.0).addCube(new float[][]{{7.0f, 2.0f, 6.0f, 2.25f}, {6.0f, 2.25f, 5.0f, 2.0f}, {5.0f, 2.25f, 6.0f, 3.25f}, {6.25f, 2.25f, 7.25f, 3.25f}, {6.0f, 2.25f, 6.25f, 3.25f}, {4.75f, 2.25f, 5.0f, 3.25f}}).finish().newQuad(5.0, 24.0, 2.0, 11.0, 28.0, 4.0).addCube(new float[][]{{5.5f, 4.0f, 4.0f, 4.5f}, {4.0f, 4.5f, 2.5f, 4.0f}, {2.5f, 4.5f, 4.0f, 5.5f}, {4.5f, 4.5f, 6.0f, 5.5f}, {4.0f, 4.5f, 4.5f, 5.5f}, {2.0f, 4.5f, 2.5f, 5.5f}}).finish().newQuad(10.0, 25.0, 4.0, 11.0, 27.0, 5.0).addCube(new float[][]{{2.75f, 0.0f, 2.5f, 0.25f}, {2.5f, 0.25f, 2.25f, 0.0f}, {2.25f, 0.25f, 2.5f, 0.75f}, {2.75f, 0.25f, 3.0f, 0.75f}, {2.5f, 0.25f, 2.75f, 0.75f}, {2.0f, 0.25f, 2.25f, 0.75f}}).finish().newQuad(5.0, 25.0, 4.0, 6.0, 27.0, 5.0).addCube(new float[][]{{2.75f, 2.0f, 2.5f, 2.25f}, {2.5f, 2.25f, 2.25f, 2.0f}, {2.25f, 2.25f, 2.5f, 2.75f}, {2.75f, 2.25f, 3.0f, 2.75f}, {2.5f, 2.25f, 2.75f, 2.75f}, {2.0f, 2.25f, 2.25f, 2.75f}}).finish();
    BlockState state;
    List<BakedQuad> quads = CollectionUtils.createList();

    public ConstructionLightModel(BlockState state) {
        this.state = state;
    }

    @Override
    public void init() {
        this.setParticleTexture(IC2Textures.getMappedEntriesBlockIC2("electric/luminator").get("construction_light"));
        if (((Boolean)this.state.m_61143_((Property)ConstructionLightBlock.TOP)).booleanValue()) {
            return;
        }
        BUILDER.buildQuads(this.m_6160_(), BlockModelRotation.m_119153_((int)0, (int)((int)((Direction)this.state.m_61143_((Property)ConstructionLightBlock.FACING)).m_122435_())), null, false, this.quads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? this.quads : ConstructionLightModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

