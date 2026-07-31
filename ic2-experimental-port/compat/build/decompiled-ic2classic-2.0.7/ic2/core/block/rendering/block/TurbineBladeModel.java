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
import ic2.core.block.generators.TurbineBladeBlock;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

public class TurbineBladeModel
extends SimpleBlockModel {
    TextureAtlasSprite turbine;

    public TurbineBladeModel(BlockState state, IBlockModel model, TextureAtlasSprite turbine) {
        super(state, model);
        this.turbine = turbine;
    }

    @Override
    public void init() {
        super.init();
        int index = (Integer)this.state.m_61143_((Property)TurbineBladeBlock.FORMED) - 1;
        DirectionList dir = index < 0 ? DirectionList.ALL : DirectionList.ofFacing((Direction)this.state.m_61143_((Property)TurbineBladeBlock.FACINGS));
        BlockElementFace face = null;
        if (index < 0) {
            face = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, 0.0f, 16.0f, 16.0f}, 0));
        } else {
            float progress = 5.3333335f;
            float x = (float)(index % 3) * progress;
            float y = (float)(index / 3) * progress;
            face = new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{x, y, x + progress, y + progress}, 0));
        }
        AABB box = this.model.getModelBounds(this.state).m_82400_((double)0.05f);
        for (Direction side : dir) {
            this.quads[side.m_122411_()].add(QuadBaker.createQuad(box, side, face, this.turbine, BlockModelRotation.X0_Y0, null, true));
        }
    }
}

