/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.rendering.camouflage.shape;

import ic2.api.events.RetextureEvent;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.platform.rendering.models.ShapeBuilder;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FullCubeShape
extends CamouflageShape {
    static final List<ShapeBuilder.Quad>[] QUADS = new ShapeBuilder().newQuad(new AABB(0.0, 0.0, 0.0, 16.0, 16.0, 16.0).m_82400_((double)0.001f)).addCube(0.0f, 0.0f, 16.0f, 16.0f).finish().getMappedQuads();

    @Override
    protected boolean isSideFull(BlockState state, Direction side) {
        return true;
    }

    @Override
    protected void generateBakedQuads(BlockState original, Direction dir, RetextureEvent.Rotation[] rotations, List<BakedQuad> blockQuads, List<BakedQuad>[] result) {
        List<ShapeBuilder.Quad> quads = QUADS[dir.m_122411_()];
        if (quads.isEmpty()) {
            return;
        }
        List<BakedQuad> out = result[dir.m_122411_()];
        int m = blockQuads.size();
        for (int i = 0; i < m; ++i) {
            BakedQuad baked = blockQuads.get(i);
            for (ShapeBuilder.Quad quad : quads) {
                out.add(FullCubeShape.createQuad(baked, quad, i, dir, rotations[i]));
            }
        }
    }
}

