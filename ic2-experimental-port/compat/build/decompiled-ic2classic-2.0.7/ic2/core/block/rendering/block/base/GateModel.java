/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class GateModel
extends BaseModel {
    private static final ResourceLocation GATE = new ResourceLocation("ic2:fence_gate");
    private static final String JSON_DATA = "{\"display\":{\"gui\":{\"rotation\":[ 30, 45, 0 ],\"translation\":[ 0, -1, 0],\"scale\":[ 0.8, 0.8, 0.8 ]},\"head\": {\"rotation\": [ 0, 0, 0 ],\"translation\": [ 0, -3, -6],\"scale\":[ 1, 1, 1]}}}";
    private static final ShapeBuilder OPEN_BUILDER = new ShapeBuilder().newQuad(14.0, 5.0, 7.0, 16.0, 16.0, 9.0).addFaces(DirectionList.VERTICAL, 0.0f, 7.0f, 2.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 0.0f, 2.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(0.0, 5.0, 7.0, 2.0, 16.0, 9.0).addFaces(DirectionList.VERTICAL, 14.0f, 7.0f, 16.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 14.0f, 0.0f, 16.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(14.0, 6.0, 13.0, 16.0, 15.0, 15.0).addFaces(DirectionList.VERTICAL, 14.0f, 13.0f, 16.0f, 15.0f).addFaces(DirectionList.Z_AXIS, 14.0f, 1.0f, 16.0f, 10.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 10.0f).finish().newQuad(0.0, 6.0, 13.0, 2.0, 15.0, 15.0).addFaces(DirectionList.VERTICAL, 0.0f, 13.0f, 2.0f, 15.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 1.0f, 2.0f, 10.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 10.0f).finish().newQuad(14.0, 2.0, 7.0, 16.0, 13.0, 9.0).addFaces(DirectionList.VERTICAL, 0.0f, 7.0f, 2.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 0.0f, 2.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(0.0, 2.0, 7.0, 2.0, 13.0, 9.0).addFaces(DirectionList.VERTICAL, 14.0f, 7.0f, 16.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 14.0f, 0.0f, 16.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(14.0, 3.0, 13.0, 16.0, 12.0, 15.0).addFaces(DirectionList.VERTICAL, 14.0f, 13.0f, 16.0f, 15.0f).addFaces(DirectionList.Z_AXIS, 14.0f, 1.0f, 16.0f, 10.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 10.0f).finish().newQuad(0.0, 3.0, 13.0, 2.0, 12.0, 15.0).addFaces(DirectionList.VERTICAL, 0.0f, 13.0f, 2.0f, 15.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 1.0f, 2.0f, 10.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 10.0f).finish();
    private static final ShapeBuilder CLOSE_BUILDER = new ShapeBuilder().newQuad(14.0, 5.0, 7.0, 16.0, 16.0, 9.0).addFaces(DirectionList.VERTICAL, 14.0f, 7.0f, 16.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 14.0f, 0.0f, 16.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(0.0, 5.0, 7.0, 2.0, 16.0, 9.0).addFaces(DirectionList.VERTICAL, 0.0f, 7.0f, 2.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 0.0f, 2.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(8.0, 6.0, 7.0, 10.0, 15.0, 9.0).addFaces(DirectionList.VERTICAL, 8.0f, 7.0f, 10.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 8.0f, 1.0f, 10.0f, 10.0f).addFaces(DirectionList.X_AXIS, 7.0f, 1.0f, 9.0f, 10.0f).finish().newQuad(6.0, 6.0, 7.0, 8.0, 15.0, 9.0).addFaces(DirectionList.VERTICAL, 6.0f, 7.0f, 8.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 6.0f, 1.0f, 8.0f, 10.0f).addFaces(DirectionList.X_AXIS, 7.0f, 1.0f, 9.0f, 10.0f).finish().newQuad(14.0, 2.0, 7.0, 16.0, 13.0, 9.0).addFaces(DirectionList.VERTICAL, 14.0f, 7.0f, 16.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 14.0f, 0.0f, 16.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(0.0, 2.0, 7.0, 2.0, 13.0, 9.0).addFaces(DirectionList.VERTICAL, 0.0f, 7.0f, 2.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 0.0f, 0.0f, 2.0f, 11.0f).addFaces(DirectionList.X_AXIS, 7.0f, 0.0f, 9.0f, 11.0f).finish().newQuad(8.0, 3.0, 7.0, 10.0, 12.0, 9.0).addFaces(DirectionList.VERTICAL, 8.0f, 7.0f, 10.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 8.0f, 1.0f, 10.0f, 10.0f).addFaces(DirectionList.X_AXIS, 7.0f, 1.0f, 9.0f, 10.0f).finish().newQuad(6.0, 3.0, 7.0, 8.0, 12.0, 9.0).addFaces(DirectionList.VERTICAL, 6.0f, 7.0f, 8.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 6.0f, 1.0f, 8.0f, 10.0f).addFaces(DirectionList.X_AXIS, 7.0f, 1.0f, 9.0f, 10.0f).finish();
    private static final ShapeBuilder OPEN_BAR_BUILDER = new ShapeBuilder().newQuad(14.0, 12.0, 9.0, 16.0, 15.0, 13.0).addFaces(DirectionList.VERTICAL, 14.0f, 9.0f, 16.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 4.0f).finish().newQuad(14.0, 6.0, 9.0, 16.0, 9.0, 13.0).addFaces(DirectionList.VERTICAL, 14.0f, 9.0f, 16.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 7.0f, 15.0f, 10.0f).finish().newQuad(0.0, 12.0, 9.0, 2.0, 15.0, 13.0).addFaces(DirectionList.VERTICAL, 0.0f, 9.0f, 2.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 4.0f).finish().newQuad(0.0, 6.0, 9.0, 2.0, 9.0, 13.0).addFaces(DirectionList.VERTICAL, 0.0f, 9.0f, 2.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 7.0f, 15.0f, 10.0f).finish().newQuad(14.0, 9.0, 9.0, 16.0, 12.0, 13.0).addFaces(DirectionList.VERTICAL, 14.0f, 9.0f, 16.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 4.0f).finish().newQuad(14.0, 3.0, 9.0, 16.0, 6.0, 13.0).addFaces(DirectionList.VERTICAL, 14.0f, 9.0f, 16.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 7.0f, 15.0f, 10.0f).finish().newQuad(0.0, 9.0, 9.0, 2.0, 12.0, 13.0).addFaces(DirectionList.VERTICAL, 0.0f, 9.0f, 2.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 1.0f, 15.0f, 4.0f).finish().newQuad(0.0, 3.0, 9.0, 2.0, 6.0, 13.0).addFaces(DirectionList.VERTICAL, 0.0f, 9.0f, 2.0f, 13.0f).addFaces(DirectionList.X_AXIS, 13.0f, 7.0f, 15.0f, 10.0f).finish();
    public static final ShapeBuilder CLOSE_BAR_BUILDER = new ShapeBuilder().newQuad(10.0, 12.0, 7.0, 14.0, 15.0, 9.0).addFaces(DirectionList.VERTICAL, 10.0f, 7.0f, 14.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 10.0f, 1.0f, 14.0f, 4.0f).finish().newQuad(10.0, 6.0, 7.0, 14.0, 9.0, 9.0).addFaces(DirectionList.VERTICAL, 10.0f, 7.0f, 14.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 10.0f, 7.0f, 14.0f, 10.0f).finish().newQuad(2.0, 12.0, 7.0, 6.0, 15.0, 9.0).addFaces(DirectionList.VERTICAL, 2.0f, 7.0f, 6.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 2.0f, 1.0f, 6.0f, 4.0f).finish().newQuad(2.0, 6.0, 7.0, 6.0, 9.0, 9.0).addFaces(DirectionList.VERTICAL, 2.0f, 7.0f, 6.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 2.0f, 7.0f, 6.0f, 10.0f).finish().newQuad(10.0, 9.0, 7.0, 14.0, 12.0, 9.0).addFaces(DirectionList.VERTICAL, 10.0f, 7.0f, 14.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 10.0f, 1.0f, 14.0f, 4.0f).finish().newQuad(10.0, 3.0, 7.0, 14.0, 6.0, 9.0).addFaces(DirectionList.VERTICAL, 10.0f, 7.0f, 14.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 10.0f, 7.0f, 14.0f, 10.0f).finish().newQuad(2.0, 9.0, 7.0, 6.0, 12.0, 9.0).addFaces(DirectionList.VERTICAL, 2.0f, 7.0f, 6.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 2.0f, 1.0f, 6.0f, 4.0f).finish().newQuad(2.0, 3.0, 7.0, 6.0, 6.0, 9.0).addFaces(DirectionList.VERTICAL, 2.0f, 7.0f, 6.0f, 9.0f).addFaces(DirectionList.Z_AXIS, 2.0f, 7.0f, 6.0f, 10.0f).finish();
    private BlockState state;
    private List<BakedQuad> quads = CollectionUtils.createList();
    private String textureFolder;
    private String textureName;

    public GateModel(BlockState state, String textureFolder, String textureName) {
        this.state = state;
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    @Override
    public void init() {
        int sub;
        this.setTransforms(IC2Models.getTransformMap(IC2Models.BLOCK_LOCATION, GATE, JSON_DATA));
        this.setParticleTexture(IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName));
        boolean open = (Boolean)this.state.m_61143_((Property)FenceGateBlock.f_53341_);
        boolean inWall = (Boolean)this.state.m_61143_((Property)FenceGateBlock.f_53343_);
        BlockModelRotation rotation = BlockModelRotation.m_119153_((int)0, (int)(((Direction)this.state.m_61143_((Property)FenceGateBlock.f_54117_)).m_122416_() * 90));
        int base = inWall ? 24 : 0;
        int n = sub = inWall ? 16 : 0;
        if (open) {
            OPEN_BUILDER.buildQuads(this.m_6160_(), rotation, null, true, base, base + 24, this.quads);
            OPEN_BAR_BUILDER.buildQuads(this.m_6160_(), rotation, null, true, sub, sub + 16, this.quads);
        } else {
            CLOSE_BUILDER.buildQuads(this.m_6160_(), rotation, null, true, base, base + 24, this.quads);
            CLOSE_BAR_BUILDER.buildQuads(this.m_6160_(), rotation, null, true, sub, sub + 16, this.quads);
        }
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData data, RenderType type) {
        return side != null ? GateModel.empty() : this.quads;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

