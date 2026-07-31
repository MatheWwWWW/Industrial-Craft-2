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
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 */
package ic2.core.block.rendering.block;

import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

@OnlyIn(value=Dist.CLIENT)
public class CamouflageModel
extends BaseModel {
    static final CamouflageModel INSTANCE = new CamouflageModel();
    static final ShapeBuilder BUILDER = new ShapeBuilder().newAutoQuad(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    List<BakedQuad> quads = CollectionUtils.createList();

    private CamouflageModel() {
    }

    public static CamouflageModel getInstance() {
        return INSTANCE;
    }

    @Override
    public void init() {
        this.quads.clear();
        this.setParticleTexture(IC2Textures.getMappedEntriesBlockIC2("cfoam").get("wet_containing"));
        BUILDER.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y0, null, true, this.quads);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side != null) {
            return CamouflageModel.empty();
        }
        Function quads = (Function)extraData.get((ModelProperty)CamouflageProperty.INSTANCE);
        if (quads != null) {
            List<BakedQuad> releaseQuads = ((CamouflageShape.QuadResults)quads.apply(type)).getQuads(type);
            if (releaseQuads.size() > 0) {
                return releaseQuads;
            }
            return CamouflageModel.empty();
        }
        return this.quads;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

