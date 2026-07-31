/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockElementRotation
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block;

import com.mojang.math.Vector3f;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class DynamiteModel
extends BaseModel {
    Direction direction;
    List<BakedQuad> quads = CollectionUtils.createList();

    public DynamiteModel(TextureAtlasSprite texture, Direction side) {
        this.setParticleTexture(texture);
        this.direction = side;
    }

    @Override
    public void init() {
        if (this.direction == Direction.DOWN) {
            this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(7.0f, 0.0f, 7.0f), new Vector3f(9.0f, 10.0f, 9.0f), new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{7.0f, 13.0f, 9.0f, 15.0f}, 0)), this.m_6160_(), Direction.DOWN, (ModelState)BlockModelRotation.X0_Y0, null, false, LOCATION));
            this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(7.0f, 0.0f, 7.0f), new Vector3f(9.0f, 10.0f, 9.0f), new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{7.0f, 6.0f, 9.0f, 8.0f}, 0)), this.m_6160_(), Direction.UP, (ModelState)BlockModelRotation.X0_Y0, null, false, LOCATION));
            this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(7.0f, 0.0f, 0.0f), new Vector3f(9.0f, 16.0f, 16.0f), new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)), this.m_6160_(), Direction.WEST, (ModelState)BlockModelRotation.X0_Y0, null, false, LOCATION));
            this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(7.0f, 0.0f, 0.0f), new Vector3f(9.0f, 16.0f, 16.0f), new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)), this.m_6160_(), Direction.EAST, (ModelState)BlockModelRotation.X0_Y0, null, false, LOCATION));
            this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(0.0f, 0.0f, 7.0f), new Vector3f(16.0f, 16.0f, 9.0f), new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)), this.m_6160_(), Direction.NORTH, (ModelState)BlockModelRotation.X0_Y0, null, false, LOCATION));
            this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(0.0f, 0.0f, 7.0f), new Vector3f(16.0f, 16.0f, 9.0f), new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)), this.m_6160_(), Direction.SOUTH, (ModelState)BlockModelRotation.X0_Y0, null, false, LOCATION));
        } else {
            Vector3f vector = new Vector3f(0.0f, 3.5f, 8.0f);
            vector.m_122261_(0.0625f);
            BlockModelRotation rot = BlockModelRotation.values()[DirectionList.rotateAround(this.direction, Direction.Axis.Y).m_122416_()];
            for (Direction facing : DirectionList.ALL) {
                float[] fArray;
                Vector3f vector3f = new Vector3f(-1.0f, 3.5f, 7.0f);
                Vector3f vector3f2 = new Vector3f(1.0f, 13.5f, 9.0f);
                if (facing == Direction.UP) {
                    float[] fArray2 = new float[4];
                    fArray2[0] = 7.0f;
                    fArray2[1] = 6.0f;
                    fArray2[2] = 9.0f;
                    fArray = fArray2;
                    fArray2[3] = 8.0f;
                } else {
                    float[] fArray3 = new float[4];
                    fArray3[0] = 7.0f;
                    fArray3[1] = 6.0f;
                    fArray3[2] = 9.0f;
                    fArray = fArray3;
                    fArray3[3] = 14.0f;
                }
                this.quads.add(QuadBaker.BAKER.m_111600_(vector3f, vector3f2, new BlockElementFace(null, -1, "", new BlockFaceUV(fArray, 0)), this.m_6160_(), facing, (ModelState)rot, new BlockElementRotation(vector, Direction.Axis.Z, -22.5f, false), false, LOCATION));
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side != null || state == null ? DynamiteModel.empty() : this.quads;
    }

    @Override
    public boolean m_7541_() {
        return false;
    }

    @Override
    public boolean m_7539_() {
        return false;
    }

    @Override
    public boolean m_7521_() {
        return false;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

