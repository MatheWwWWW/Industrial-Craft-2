/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.FenceBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block.base;

import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class FenceModel
extends BaseModel {
    private static final ResourceLocation FENCE = new ResourceLocation("ic2:fence");
    private static final String JSON_DATA = "{\"display\":{\"gui\":{\"rotation\":[30,225,0],\"translation\":[0,0,0],\"scale\":[0.625,0.625,0.625]},\"fixed\":{\"rotation\":[0,90,0],\"translation\":[0,0,0],\"scale\":[1,1,1]}}}";
    private static final float[][][] FACES = new float[][][]{new float[][]{{7.0f, 0.0f, 9.0f, 9.0f}, {7.0f, 0.0f, 9.0f, 9.0f}, {7.0f, 1.0f, 9.0f, 4.0f}, new float[0], {0.0f, 1.0f, 9.0f, 4.0f}, {0.0f, 1.0f, 9.0f, 4.0f}}, new float[][]{{7.0f, 0.0f, 9.0f, 9.0f}, {7.0f, 0.0f, 9.0f, 9.0f}, {7.0f, 7.0f, 9.0f, 10.0f}, new float[0], {0.0f, 7.0f, 9.0f, 10.0f}, {0.0f, 7.0f, 9.0f, 10.0f}}};
    private static final BooleanProperty[] CONNECTION = new BooleanProperty[]{FenceBlock.f_52309_, FenceBlock.f_52310_, FenceBlock.f_52311_, FenceBlock.f_52312_};
    private static final BlockElementFace VERTICAL = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{6.0f, 6.0f, 10.0f, 10.0f}, 0));
    private static final BlockElementFace HORIZONTAL = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{6.0f, 0.0f, 10.0f, 16.0f}, 0));
    private BlockState base;
    private List<BakedQuad> quads = CollectionUtils.createList();
    private List<BakedQuad> itemQuads = CollectionUtils.createList();
    private String textureFolder;
    private String textureName;

    public FenceModel(BlockState state, String textureFolder, String textureName) {
        this.base = state;
        this.textureFolder = textureFolder;
        this.textureName = textureName;
    }

    @Override
    public void init() {
        this.setTransforms(IC2Models.getTransformMap(IC2Models.BLOCK_LOCATION, FENCE, JSON_DATA));
        this.setParticleTexture(IC2Textures.getMappedEntriesBlockIC2(this.textureFolder).get(this.textureName));
        AABB center = new AABB(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
        AABB lower = new AABB(7.0, 6.0, 0.0, 9.0, 9.0, 6.0);
        AABB upper = new AABB(7.0, 12.0, 0.0, 9.0, 15.0, 6.0);
        for (Direction direction : DirectionList.ALL) {
            this.quads.add(QuadBaker.createLockedQuad(center, direction, direction.m_122434_().m_122478_() ? VERTICAL : HORIZONTAL, this.m_6160_(), BlockModelRotation.X0_Y0, null, true));
            if (direction == Direction.SOUTH) continue;
            for (Direction side : DirectionList.HORIZONTAL) {
                if (!((Boolean)this.base.m_61143_((Property)CONNECTION[side.m_122416_()])).booleanValue()) continue;
                this.quads.add(QuadBaker.createLockedQuad(upper, direction, new BlockElementFace(null, 0, "", new BlockFaceUV(FACES[0][direction.m_122411_()], 0)), this.m_6160_(), BlockModelRotation.values()[side.m_122416_()], null, true));
                this.quads.add(QuadBaker.createLockedQuad(lower, direction, new BlockElementFace(null, 0, "", new BlockFaceUV(FACES[1][direction.m_122411_()], 0)), this.m_6160_(), BlockModelRotation.values()[side.m_122416_()], null, true));
            }
        }
        AABB left = new AABB(6.0, 0.0, 0.0, 10.0, 16.0, 4.0);
        AABB right = new AABB(6.0, 0.0, 12.0, 10.0, 16.0, 16.0);
        AABB middleTop = new AABB(7.0, 12.0, -2.0, 9.0, 15.0, 18.0);
        AABB middleBottom = new AABB(7.0, 6.0, -2.0, 9.0, 9.0, 18.0);
        for (Direction direction : DirectionList.ALL) {
            this.itemQuads.add(QuadBaker.createLockedQuad(left, direction, direction.m_122434_().m_122478_() ? VERTICAL : HORIZONTAL, this.m_6160_(), BlockModelRotation.X0_Y90, null, true));
            this.itemQuads.add(QuadBaker.createLockedQuad(right, direction, direction.m_122434_().m_122478_() ? VERTICAL : HORIZONTAL, this.m_6160_(), BlockModelRotation.X0_Y90, null, true));
            if (direction == Direction.SOUTH) continue;
            this.itemQuads.add(QuadBaker.createLockedQuad(middleTop, direction, new BlockElementFace(null, 0, "", new BlockFaceUV(FACES[0][direction.m_122411_()], 0)), this.m_6160_(), BlockModelRotation.X0_Y90, null, true));
            this.itemQuads.add(QuadBaker.createLockedQuad(middleBottom, direction, new BlockElementFace(null, 0, "", new BlockFaceUV(FACES[1][direction.m_122411_()], 0)), this.m_6160_(), BlockModelRotation.X0_Y90, null, true));
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? (state == null ? this.itemQuads : this.quads) : FenceModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

