/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.rendering.block.tubes;

import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.rendering.block.tubes.CableModel;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class SuperCableModel
extends CableModel {
    public SuperCableModel(ITubeBlock tube, BlockState state) {
        super(tube, state);
    }

    @Override
    public BlockElementFace getFace(Direction dir, Direction side, float min, float max) {
        if (side == dir) {
            return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, min, max, max}, 0));
        }
        if (side.m_122434_().m_122478_()) {
            if (side.m_122421_() == Direction.AxisDirection.NEGATIVE) {
                return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, 0.0f, max, min}, dir.m_122416_() * 90 + (dir.m_122434_() == Direction.Axis.X ? 180 : 0)));
            }
            return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{min, max, max, 16.0f}, dir.m_122416_() * 90));
        }
        if (side.m_122434_().m_122479_()) {
            if (side.m_122421_() == Direction.AxisDirection.NEGATIVE) {
                return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{0.0f, min, min, max}, 0));
            }
            return new BlockElementFace(null, -1, "", new BlockFaceUV(new float[]{max, min, 16.0f, max}, 0));
        }
        return null;
    }
}

