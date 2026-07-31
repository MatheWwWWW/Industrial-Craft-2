/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Half
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.StairsShape
 */
package ic2.core.block.rendering.camouflage.shape;

import ic2.api.events.RetextureEvent;
import ic2.api.util.DirectionList;
import ic2.core.block.rendering.camouflage.CamouflageBuilder;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.platform.rendering.models.ShapeBuilder;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.StairsShape;

public class StairShape
extends CamouflageShape {
    private static final List<ShapeBuilder.Quad>[][] QUADS = new CamouflageBuilder().addCulledBox(DirectionList.UP.invert(), 0.0f, 0.0f, 0.0f, 16.0f, 8.0f, 16.0f).addCulledBox(DirectionList.DOWN.invert(), 0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f).addBox(DirectionList.DOWN.invert(), DirectionList.NORTH.invert(), 0.0f, 8.0f, 8.0f, 16.0f, 16.0f, 16.0f).addBox(DirectionList.DOWN.invert(), DirectionList.EAST.invert(), 0.0f, 8.0f, 0.0f, 8.0f, 16.0f, 16.0f).addBox(DirectionList.DOWN.invert(), DirectionList.SOUTH.invert(), 0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 8.0f).addBox(DirectionList.DOWN.invert(), DirectionList.WEST.invert(), 8.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f).addBox(DirectionList.UP.invert(), DirectionList.NORTH.invert(), 0.0f, 0.0f, 8.0f, 16.0f, 8.0f, 16.0f).addBox(DirectionList.UP.invert(), DirectionList.EAST.invert(), 0.0f, 0.0f, 0.0f, 8.0f, 8.0f, 16.0f).addBox(DirectionList.UP.invert(), DirectionList.SOUTH.invert(), 0.0f, 0.0f, 0.0f, 16.0f, 8.0f, 8.0f).addBox(DirectionList.UP.invert(), DirectionList.WEST.invert(), 8.0f, 0.0f, 0.0f, 16.0f, 8.0f, 16.0f).addBox(DirectionList.DOWN.invert(), DirectionList.NORTH.invert(), 8.0f, 8.0f, 8.0f, 16.0f, 16.0f, 16.0f).addBox(DirectionList.DOWN.invert(), DirectionList.EAST.invert(), 0.0f, 8.0f, 8.0f, 8.0f, 16.0f, 16.0f).addBox(DirectionList.DOWN.invert(), DirectionList.SOUTH.invert(), 0.0f, 8.0f, 0.0f, 8.0f, 16.0f, 8.0f).addBox(DirectionList.DOWN.invert(), DirectionList.WEST.invert(), 8.0f, 8.0f, 0.0f, 16.0f, 16.0f, 8.0f).addBox(DirectionList.UP.invert(), DirectionList.NORTH.invert(), 8.0f, 0.0f, 8.0f, 16.0f, 8.0f, 16.0f).addBox(DirectionList.UP.invert(), DirectionList.EAST.invert(), 0.0f, 0.0f, 8.0f, 8.0f, 8.0f, 16.0f).addBox(DirectionList.UP.invert(), DirectionList.SOUTH.invert(), 0.0f, 0.0f, 0.0f, 8.0f, 8.0f, 8.0f).addBox(DirectionList.UP.invert(), DirectionList.WEST.invert(), 8.0f, 0.0f, 0.0f, 16.0f, 8.0f, 8.0f).build();

    @Override
    protected boolean isSideFull(BlockState state, Direction side) {
        return false;
    }

    private Direction getDirection(StairsShape shape, Direction facing) {
        if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
            return shape == StairsShape.INNER_LEFT ? facing.m_122428_() : facing.m_122424_();
        }
        if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
            return shape == StairsShape.OUTER_RIGHT ? facing.m_122427_() : facing;
        }
        return facing;
    }

    @Override
    protected void generateBakedQuads(BlockState original, Direction dir, RetextureEvent.Rotation[] rotations, List<BakedQuad> blockQuads, List<BakedQuad>[] result) {
        StairsShape shape = (StairsShape)original.m_61143_((Property)StairBlock.f_56843_);
        boolean top = original.m_61143_((Property)StairBlock.f_56842_) == Half.TOP;
        Direction facing = (Direction)original.m_61143_((Property)StairBlock.f_56841_);
        List<BakedQuad> out = result[dir.m_122411_()];
        List<BakedQuad> allOut = result[6];
        List<ShapeBuilder.Quad> main = QUADS[top ? 1 : 0][dir.m_122411_()];
        List<ShapeBuilder.Quad> straight = QUADS[2 + (top ? 4 : 0) + facing.m_122416_()][dir.m_122411_()];
        List<ShapeBuilder.Quad> corner = QUADS[10 + (top ? 4 : 0) + this.getDirection(shape, facing).m_122416_()][dir.m_122411_()];
        int m = blockQuads.size();
        for (int i = 0; i < m; ++i) {
            BakedQuad baked = blockQuads.get(i);
            for (ShapeBuilder.Quad quad : main) {
                (quad.isCull() ? out : allOut).add(StairShape.createQuad(baked, quad, i, dir, rotations[i]));
            }
            if (shape == StairsShape.STRAIGHT || shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
                for (ShapeBuilder.Quad quad : straight) {
                    (quad.isCull() ? out : allOut).add(StairShape.createQuad(baked, quad, i, dir, rotations[i]));
                }
            }
            if (shape == StairsShape.STRAIGHT) continue;
            for (ShapeBuilder.Quad quad : corner) {
                (quad.isCull() ? out : allOut).add(StairShape.createQuad(baked, quad, i, dir, rotations[i]));
            }
        }
    }
}

