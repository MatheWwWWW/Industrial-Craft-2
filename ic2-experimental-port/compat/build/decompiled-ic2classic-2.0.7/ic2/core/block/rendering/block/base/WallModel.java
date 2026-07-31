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
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.WallSide
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraftforge.client.model.data.ModelData;

public class WallModel
extends BaseModel {
    private static final String JSON_DATA = "{\"display\":{\"gui\":{\"rotation\":[30,45,0],\"translation\":[0,0,0],\"scale\":[0.625,0.625,0.625]},\"head\":{\"rotation\":[0,0,0],\"translation\":[0,0,0],\"scale\":[1,1,1]},\"firstperson_righthand\":{\"rotation\":[0,-45,0],\"translation\":[0,0,0],\"scale\":[0.40,0.40,0.40]},\"thirdperson_lefthand\":{\"rotation\":[75,-45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]}, \"thirdperson_righthand\":{\"rotation\":[75,-45,0],\"translation\":[0,2.5,0],\"scale\":[0.375,0.375,0.375]}}}";
    private static final ResourceLocation WALL = new ResourceLocation("ic2:wall");
    private static final ShapeBuilder POLE = new ShapeBuilder().newQuad(4.0, 0.0, 4.0, 12.0, 16.0, 12.0).addFaces(DirectionList.VERTICAL, 4.0f, 4.0f, 12.0f, 12.0f).addFaces(DirectionList.Z_AXIS, 4.0f, 0.0f, 12.0f, 16.0f).addFaces(DirectionList.X_AXIS, 4.0f, 0.0f, 12.0f, 16.0f).finish();
    private static final ShapeBuilder SIDE_WALL = new ShapeBuilder().newQuad(5.0, 0.16f, 8.0, 11.0, 14.0, 16.0).addCube(new float[][]{{11.0f, 8.0f, 5.0f, 16.0f}, {5.0f, 8.0f, 11.0f, 16.0f}, {5.0f, 0.0f, 11.0f, 13.0f}, {5.0f, 0.0f, 11.0f, 13.0f}, {8.0f, 0.0f, 16.0f, 14.0f}, {0.0f, 0.0f, 8.0f, 14.0f}}).finish();
    private static final ShapeBuilder ITEM_SIDE_WALL = new ShapeBuilder().newQuad(5.0, 0.16f, 8.0, 11.0, 14.0, 16.0).addFaces(DirectionList.VERTICAL, 5.0f, 0.0f, 11.0f, 16.0f).addFaces(DirectionList.Z_AXIS, 5.0f, 3.0f, 11.0f, 16.0f).addFaces(DirectionList.X_AXIS, 0.0f, 3.0f, 16.0f, 16.0f).finish();
    private BlockState state;
    private String textureFolder;
    private String textureName;
    private List<BakedQuad> blockQuads = CollectionUtils.createList();
    private List<BakedQuad> itemQuads = CollectionUtils.createList();

    public WallModel(BlockState state, String textureFolder, String textureName) {
        this.state = state;
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    @Override
    public void init() {
        this.setTransforms(IC2Models.getTransformMap(IC2Models.BLOCK_LOCATION, WALL, JSON_DATA));
        this.setParticleTexture(IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName));
        POLE.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y0, null, true, this.itemQuads);
        ITEM_SIDE_WALL.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y90, null, true, this.itemQuads);
        ITEM_SIDE_WALL.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y270, null, true, this.itemQuads);
        if (((Boolean)this.state.m_61143_((Property)WallBlock.f_57949_)).booleanValue()) {
            POLE.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y0, null, true, this.blockQuads);
        }
        if (this.state.m_61143_((Property)WallBlock.f_57951_) != WallSide.NONE) {
            SIDE_WALL.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y180, null, true, this.blockQuads);
        }
        if (this.state.m_61143_((Property)WallBlock.f_57950_) != WallSide.NONE) {
            SIDE_WALL.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y270, null, true, this.blockQuads);
        }
        if (this.state.m_61143_((Property)WallBlock.f_57952_) != WallSide.NONE) {
            SIDE_WALL.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y0, null, true, this.blockQuads);
        }
        if (this.state.m_61143_((Property)WallBlock.f_57953_) != WallSide.NONE) {
            SIDE_WALL.buildQuads(this.m_6160_(), BlockModelRotation.X0_Y90, null, true, this.blockQuads);
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        if (side != null) {
            return WallModel.empty();
        }
        return state == null ? this.itemQuads : this.blockQuads;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

