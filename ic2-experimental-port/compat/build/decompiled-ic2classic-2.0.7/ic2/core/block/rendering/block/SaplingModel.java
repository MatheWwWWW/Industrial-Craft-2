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
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 */
package ic2.core.block.rendering.block;

import com.mojang.math.Vector3f;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(value=Dist.CLIENT)
public class SaplingModel
extends BaseModel {
    public static final BlockElementFace NORTH = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{16.0f, 0.0f, 0.0f, 16.0f}, 0));
    public static final BlockElementFace SOUTH = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0));
    public static final BlockElementFace WEST = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0));
    public static final BlockElementFace EAST = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{16.0f, 0.0f, 0.0f, 16.0f}, 0));
    List<BakedQuad> quads = CollectionUtils.createList();

    public SaplingModel(TextureAtlasSprite sprite) {
        this.setParticleTexture(sprite);
    }

    @Override
    public void init() {
        this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(0.8f, 0.0f, 8.0f), new Vector3f(15.2f, 16.0f, 8.0f), NORTH, this.m_6160_(), Direction.NORTH, (ModelState)BlockModelRotation.X0_Y0, new BlockElementRotation(new Vector3f(0.5f, 0.5f, 0.5f), Direction.Axis.Y, 45.0f, true), true, LOCATION));
        this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(0.8f, 0.0f, 8.0f), new Vector3f(15.2f, 16.0f, 8.0f), SOUTH, this.m_6160_(), Direction.SOUTH, (ModelState)BlockModelRotation.X0_Y0, new BlockElementRotation(new Vector3f(0.5f, 0.5f, 0.5f), Direction.Axis.Y, 45.0f, true), true, LOCATION));
        this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(8.0f, 0.0f, 0.8f), new Vector3f(8.0f, 16.0f, 15.2f), WEST, this.m_6160_(), Direction.WEST, (ModelState)BlockModelRotation.X0_Y0, new BlockElementRotation(new Vector3f(0.5f, 0.5f, 0.5f), Direction.Axis.Y, 45.0f, true), true, LOCATION));
        this.quads.add(QuadBaker.BAKER.m_111600_(new Vector3f(8.0f, 0.0f, 0.8f), new Vector3f(8.0f, 16.0f, 15.2f), EAST, this.m_6160_(), Direction.EAST, (ModelState)BlockModelRotation.X0_Y0, new BlockElementRotation(new Vector3f(0.5f, 0.5f, 0.5f), Direction.Axis.Y, 45.0f, true), true, LOCATION));
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType type) {
        return side == null ? this.quads : SaplingModel.empty();
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

