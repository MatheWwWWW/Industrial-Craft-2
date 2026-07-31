/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.rendering.camouflage;

import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.models.ShapeBuilder;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class CamouflageBuilder {
    List<Shape> shapes = CollectionUtils.createList();

    public CamouflageBuilder addBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return this.addBox(DirectionList.ALL, DirectionList.EMPTY, minX, minY, minZ, maxX, maxY, maxZ);
    }

    public CamouflageBuilder addCulledBox(DirectionList culled, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return this.addBox(DirectionList.ALL, culled, minX, minY, minZ, maxX, maxY, maxZ);
    }

    public CamouflageBuilder addBox(DirectionList directions, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        return this.addBox(directions, DirectionList.EMPTY, minX, minY, minZ, maxX, maxY, maxZ);
    }

    public CamouflageBuilder addBox(DirectionList directions, DirectionList culled, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.shapes.add(new Shape(new AABB((double)minX, (double)minY, (double)minZ, (double)maxX, (double)maxY, (double)maxZ), directions, culled));
        return this;
    }

    public CamouflageBuilder addBox(AABB box) {
        return this.addBox(DirectionList.ALL, DirectionList.EMPTY, box);
    }

    public CamouflageBuilder addCulledBox(DirectionList culled, AABB box) {
        return this.addBox(DirectionList.ALL, culled, box);
    }

    public CamouflageBuilder addBox(DirectionList directions, AABB box) {
        return this.addBox(directions, DirectionList.EMPTY, box);
    }

    public CamouflageBuilder addBox(DirectionList directions, DirectionList culled, AABB box) {
        this.shapes.add(new Shape(box, directions, culled));
        return this;
    }

    public List<ShapeBuilder.Quad>[][] build() {
        List[][] quads = new List[this.shapes.size()][];
        int m = this.shapes.size();
        for (int i = 0; i < m; ++i) {
            quads[i] = this.shapes.get(i).build(CollectionUtils.createLists(6));
        }
        return quads;
    }

    private static class Shape {
        AABB box;
        DirectionList directions = DirectionList.EMPTY;
        DirectionList culled = DirectionList.EMPTY;

        public Shape(AABB box, DirectionList directions, DirectionList culled) {
            this.box = box;
            this.directions = directions;
            this.culled = culled;
        }

        public List<ShapeBuilder.Quad>[] build(List<ShapeBuilder.Quad>[] quads) {
            for (Direction dir : this.directions) {
                quads[dir.m_122411_()].add(ShapeBuilder.buildQuad(this.box, dir, new ShapeBuilder.ColoredUVs(-1, 0, ShapeBuilder.uvsByFace(dir, this.box)), this.culled.contains(dir)));
            }
            return quads;
        }
    }
}

