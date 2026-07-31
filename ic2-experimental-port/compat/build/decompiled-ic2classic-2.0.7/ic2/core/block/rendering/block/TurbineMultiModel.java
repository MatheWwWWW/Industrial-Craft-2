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
import ic2.core.block.multi.TurbineMultiBlock;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.models.blocks.SimpleBlockModel;
import ic2.core.utils.math.ConnectionState;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

public class TurbineMultiModel
extends SimpleBlockModel {
    TextureAtlasSprite turbine;

    public TurbineMultiModel(BlockState state, IBlockModel model, TextureAtlasSprite turbine) {
        super(state, model);
        this.turbine = turbine;
    }

    @Override
    public void init() {
        super.init();
        int index = (Integer)this.state.m_61143_((Property)TurbineMultiBlock.FORMED);
        if (index < 0 || index >= 4) {
            return;
        }
        Direction dir = Direction.m_122407_((int)index);
        ConnectionState connect = ConnectionState.fromList(DirectionList.ofNumber((Integer)this.state.m_61143_((Property)TurbineMultiBlock.CONNECTION)), dir);
        BlockElementFace face = new BlockElementFace(null, -1, "", new BlockFaceUV(connect.get3x3UVs(), 0));
        AABB box = this.model.getModelBounds(this.state).m_82400_((double)0.05f);
        this.quads[dir.m_122411_()].add(QuadBaker.createQuad(box, dir, face, this.turbine, BlockModelRotation.X0_Y0, null, true));
    }
}

