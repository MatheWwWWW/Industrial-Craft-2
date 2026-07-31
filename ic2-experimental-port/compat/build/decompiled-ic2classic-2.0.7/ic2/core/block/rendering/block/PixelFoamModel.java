/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.rendering.block;

import ic2.api.util.DirectionList;
import ic2.core.block.misc.PixelFoamBlock;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

public class PixelFoamModel
extends SimpleBlockModel {
    public PixelFoamModel(BlockState state, IBlockModel model) {
        super(state, model);
    }

    @Override
    public void init() {
        super.init();
        int type = (Integer)this.state.m_61143_((Property)PixelFoamBlock.SustainablePixelFoamBlock.TYPE);
        if (type == 3) {
            return;
        }
        Map<String, TextureAtlasSprite> map = IC2Textures.getMappedEntriesBlockIC2("cfoam/pixelfoam/among_us");
        TextureAtlasSprite base = map.get(type == 1 ? "dead" : "alive");
        TextureAtlasSprite overlay = map.get(type == 1 ? "dead_overlay" : "alive_overlay");
        AABB box = this.model.getModelBounds(this.state).m_82400_((double)0.05f);
        for (Direction side : DirectionList.ALL) {
            this.quads[side.m_122411_()].add(QuadBaker.createQuad(box, side, new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)), base, BlockModelRotation.X0_Y0, null, true));
            this.quads[side.m_122411_()].add(QuadBaker.createQuad(box, side, new BlockElementFace(null, 0, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0)), overlay, BlockModelRotation.X0_Y0, null, true));
        }
    }
}

