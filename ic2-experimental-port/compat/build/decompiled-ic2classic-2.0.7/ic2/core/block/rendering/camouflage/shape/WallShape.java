/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.WallSide
 */
package ic2.core.block.rendering.camouflage.shape;

import ic2.api.events.RetextureEvent;
import ic2.core.block.rendering.camouflage.CamouflageBuilder;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.platform.rendering.models.ShapeBuilder;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;

public class WallShape
extends CamouflageShape {
    private static final List<ShapeBuilder.Quad>[][] QUADS = new CamouflageBuilder().addBox(4.0f, 0.0f, 4.0f, 12.0f, 16.0f, 12.0f).addBox(5.0f, 0.16f, 8.0f, 11.0f, 14.0f, 16.0f).addBox(0.0f, 0.16f, 5.0f, 8.0f, 14.0f, 11.0f).addBox(5.0f, 0.16f, 0.0f, 11.0f, 14.0f, 8.0f).addBox(8.0f, 0.16f, 5.0f, 16.0f, 14.0f, 11.0f).build();

    @Override
    protected boolean isSideFull(BlockState state, Direction side) {
        return false;
    }

    @Override
    protected void generateBakedQuads(BlockState original, Direction dir, RetextureEvent.Rotation[] rotations, List<BakedQuad> blockQuads, List<BakedQuad>[] result) {
        List<BakedQuad> out = result[dir.m_122411_()];
        List<BakedQuad> allOut = result[6];
        boolean up = (Boolean)original.m_61143_((Property)WallBlock.f_57949_);
        boolean north = original.m_61143_((Property)WallBlock.f_57951_) != WallSide.NONE;
        boolean east = original.m_61143_((Property)WallBlock.f_57950_) != WallSide.NONE;
        boolean west = original.m_61143_((Property)WallBlock.f_57953_) != WallSide.NONE;
        boolean south = original.m_61143_((Property)WallBlock.f_57952_) != WallSide.NONE;
        int m = blockQuads.size();
        for (int i = 0; i < m; ++i) {
            BakedQuad baked = blockQuads.get(i);
            if (up) {
                for (ShapeBuilder.Quad quad : QUADS[0][dir.m_122411_()]) {
                    (dir.m_122434_().m_122478_() ? out : allOut).add(WallShape.createQuad(baked, quad, i, dir, rotations[i]));
                }
            }
            if (!north && !east && !west && !south) continue;
            if (south) {
                for (ShapeBuilder.Quad quad : QUADS[1][dir.m_122411_()]) {
                    (dir == Direction.DOWN || dir == Direction.NORTH ? out : allOut).add(WallShape.createQuad(baked, quad, i, dir, rotations[i]));
                }
            }
            if (west) {
                for (ShapeBuilder.Quad quad : QUADS[2][dir.m_122411_()]) {
                    (dir == Direction.DOWN || dir == Direction.EAST ? out : allOut).add(WallShape.createQuad(baked, quad, i, dir, rotations[i]));
                }
            }
            if (north) {
                for (ShapeBuilder.Quad quad : QUADS[3][dir.m_122411_()]) {
                    (dir == Direction.DOWN || dir == Direction.SOUTH ? out : allOut).add(WallShape.createQuad(baked, quad, i, dir, rotations[i]));
                }
            }
            if (!east) continue;
            for (ShapeBuilder.Quad quad : QUADS[4][dir.m_122411_()]) {
                (dir == Direction.DOWN || dir == Direction.WEST ? out : allOut).add(WallShape.createQuad(baked, quad, i, dir, rotations[i]));
            }
        }
    }
}

