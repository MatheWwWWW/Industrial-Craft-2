/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.SlabType
 */
package ic2.core.block.rendering.camouflage.shape;

import ic2.api.events.RetextureEvent;
import ic2.api.util.DirectionList;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.platform.rendering.models.ShapeBuilder;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;

public class SlabShape
extends CamouflageShape {
    static final List<ShapeBuilder.Quad>[] BOTTOM = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 16.0, 8.0, 16.0).addCulledFaces(DirectionList.HORIZONTAL, 0.0f, 0.0f, 16.0f, 8.0f).addCulledFace(Direction.DOWN, 0.0f, 0.0f, 16.0f, 16.0f).addFace(Direction.UP, 0.0f, 0.0f, 16.0f, 16.0f).finish().getMappedQuads();
    static final List<ShapeBuilder.Quad>[] TOP = new ShapeBuilder().newQuad(0.0, 8.0, 0.0, 16.0, 16.0, 16.0).addCulledFaces(DirectionList.HORIZONTAL, 0.0f, 8.0f, 16.0f, 16.0f).addCulledFace(Direction.UP, 0.0f, 0.0f, 16.0f, 16.0f).addFace(Direction.DOWN, 0.0f, 0.0f, 16.0f, 16.0f).finish().getMappedQuads();
    static final List<ShapeBuilder.Quad>[] DOUBLE = new ShapeBuilder().newQuad(0.0, 0.0, 0.0, 16.0, 16.0, 16.0).addCulledCube(0.0f, 0.0f, 16.0f, 16.0f).finish().getMappedQuads();

    @Override
    protected boolean isSideFull(BlockState state, Direction side) {
        SlabType type = (SlabType)state.m_61143_((Property)SlabBlock.f_56353_);
        return type == SlabType.DOUBLE || side == Direction.DOWN && type == SlabType.BOTTOM || side == Direction.UP && type == SlabType.TOP;
    }

    private List<ShapeBuilder.Quad>[] getQuads(SlabType type) {
        return type == SlabType.DOUBLE ? DOUBLE : (type == SlabType.BOTTOM ? BOTTOM : TOP);
    }

    @Override
    protected void generateBakedQuads(BlockState original, Direction dir, RetextureEvent.Rotation[] rotations, List<BakedQuad> blockQuads, List<BakedQuad>[] result) {
        List<ShapeBuilder.Quad> quads = this.getQuads((SlabType)original.m_61143_((Property)SlabBlock.f_56353_))[dir.m_122411_()];
        if (quads.isEmpty()) {
            return;
        }
        List<BakedQuad> out = result[dir.m_122411_()];
        List<BakedQuad> allOut = result[6];
        int m = blockQuads.size();
        for (int i = 0; i < m; ++i) {
            BakedQuad baked = blockQuads.get(i);
            for (ShapeBuilder.Quad quad : quads) {
                (quad.isCull() ? out : allOut).add(SlabShape.createQuad(baked, quad, i, dir, rotations[i]));
            }
        }
    }
}

