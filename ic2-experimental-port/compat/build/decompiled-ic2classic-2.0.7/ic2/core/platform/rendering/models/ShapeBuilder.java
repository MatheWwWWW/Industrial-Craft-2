/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Matrix4f
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockElementRotation
 *  net.minecraft.client.renderer.block.model.BlockFaceUV
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.core.Direction
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.models;

import com.mojang.math.Matrix4f;
import ic2.api.util.DirectionList;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShapeBuilder {
    public static final float CAP = 0.0625f;
    List<Quad> quads = new ObjectArrayList();

    public ShapeBuilder newAutoQuad(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.newQuad(minX, minY, minZ, maxX, maxY, maxZ).addAuto().finish();
    }

    public QuadBuilder newQuad(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new QuadBuilder(this, new AABB(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public QuadBuilder newQuad(AABB quad) {
        return this.newQuad(quad.f_82288_, quad.f_82289_, quad.f_82290_, quad.f_82291_, quad.f_82292_, quad.f_82293_);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildQuads(TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, List<BakedQuad> result) {
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result.add(QuadBaker.createQuad(quad.box, quad.dir, quad.getFace(), sprite, rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildLockedQuads(TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, List<BakedQuad> result) {
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result.add(QuadBaker.createLockedQuad(quad.box, quad.dir, quad.getFace(), sprite, rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildQuads(TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, int from, int to, List<BakedQuad> result) {
        int m = this.quads.size();
        for (int i = from; i < m && i < to; ++i) {
            Quad quad = this.quads.get(i);
            result.add(QuadBaker.createQuad(quad.box, quad.dir, quad.getFace(), sprite, rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildLockedQuads(TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, int from, int to, List<BakedQuad> result) {
        int m = this.quads.size();
        for (int i = from; i < m && i < to; ++i) {
            Quad quad = this.quads.get(i);
            result.add(QuadBaker.createLockedQuad(quad.box, quad.dir, quad.getFace(), sprite, rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildQuads(TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, List<BakedQuad>[] result) {
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result[quad.cull ? Direction.m_122384_((Matrix4f)rotation.m_6189_().m_121104_(), (Direction)quad.dir).m_122411_() : 6].add(QuadBaker.createQuad(quad.box, quad.dir, quad.getFace(), sprite, rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildLockedQuads(TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, List<BakedQuad>[] result) {
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result[quad.cull ? Direction.m_122384_((Matrix4f)rotation.m_6189_().m_121104_(), (Direction)quad.dir).m_122411_() : 6].add(QuadBaker.createLockedQuad(quad.box, quad.dir, quad.getFace(), sprite, rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildQuads(TextureAtlasSprite[] sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, List<BakedQuad> result) {
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result.add(QuadBaker.createQuad(quad.box, quad.dir, quad.getFace(), sprite[quad.dir.m_122411_()], rotation, partRotation, shade));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void buildLockedQuads(TextureAtlasSprite[] sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade, List<BakedQuad> result) {
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result.add(QuadBaker.createLockedQuad(quad.box, quad.dir, quad.getFace(), sprite[quad.dir.m_122411_()], rotation, partRotation, shade));
        }
    }

    public List<Quad> getQuads() {
        return this.quads;
    }

    public List<Quad>[] getMappedQuads() {
        List<K>[] result = CollectionUtils.createLists(6);
        int m = this.quads.size();
        for (int i = 0; i < m; ++i) {
            Quad quad = this.quads.get(i);
            result[quad.dir.m_122411_()].add(quad);
        }
        return result;
    }

    public static float[] uvsByFace(Direction side, AABB box) {
        switch (side) {
            case DOWN: {
                return new float[]{(float)box.f_82288_, 16.0f - (float)box.f_82293_, (float)box.f_82291_, 16.0f - (float)box.f_82290_};
            }
            case UP: {
                return new float[]{(float)box.f_82288_, (float)box.f_82290_, (float)box.f_82291_, (float)box.f_82293_};
            }
            default: {
                return new float[]{16.0f - (float)box.f_82291_, 16.0f - (float)box.f_82292_, 16.0f - (float)box.f_82288_, 16.0f - (float)box.f_82289_};
            }
            case SOUTH: {
                return new float[]{(float)box.f_82288_, 16.0f - (float)box.f_82292_, (float)box.f_82291_, 16.0f - (float)box.f_82289_};
            }
            case WEST: {
                return new float[]{(float)box.f_82290_, 16.0f - (float)box.f_82292_, (float)box.f_82293_, 16.0f - (float)box.f_82289_};
            }
            case EAST: 
        }
        return new float[]{16.0f - (float)box.f_82293_, 16.0f - (float)box.f_82292_, 16.0f - (float)box.f_82290_, 16.0f - (float)box.f_82289_};
    }

    public static Quad buildQuad(AABB box, Direction dir, ColoredUVs face, boolean cull) {
        return new Quad(box, dir, face, cull);
    }

    public class QuadBuilder {
        AABB box;
        ShapeBuilder builder;

        QuadBuilder(ShapeBuilder builder, AABB box) {
            this.builder = builder;
            this.box = box;
        }

        public QuadBuilder addAuto() {
            return this.addAuto(DirectionList.ALL);
        }

        public QuadBuilder addAuto(DirectionList dirs) {
            return this.addAuto(dirs, -1, 0);
        }

        public QuadBuilder addAuto(DirectionList dirs, int tintIndex) {
            return this.addAuto(dirs, tintIndex, 0);
        }

        public QuadBuilder addAuto(DirectionList dirs, int tintIndex, int rotation) {
            for (Direction dir : dirs) {
                this.addFace(dir, tintIndex, rotation, ShapeBuilder.uvsByFace(dir, this.box));
            }
            return this;
        }

        public QuadBuilder addCube(float ... uvs) {
            return this.addCube(-1, uvs);
        }

        public QuadBuilder addCube(float[] ... uvs) {
            for (Direction dir : DirectionList.ALL) {
                this.addFace(dir, -1, uvs[dir.m_122411_()]);
            }
            return this;
        }

        public QuadBuilder addHorizontalCube(float[] ... uvs) {
            for (Direction dir : DirectionList.HORIZONTAL) {
                this.addFace(dir, -1, uvs[dir.m_122416_()]);
            }
            return this;
        }

        public QuadBuilder addCube(int tintedIndex, float ... uvs) {
            for (Direction dir : DirectionList.ALL) {
                this.addFace(dir, tintedIndex, uvs);
            }
            return this;
        }

        public QuadBuilder addHorizontalCube(int tintedIndex, float[] ... uvs) {
            for (Direction dir : DirectionList.HORIZONTAL) {
                this.addFace(dir, tintedIndex, uvs[dir.m_122416_()]);
            }
            return this;
        }

        public QuadBuilder addCube(int tintedIndex, float[] ... uvs) {
            for (Direction dir : DirectionList.ALL) {
                this.addFace(dir, tintedIndex, uvs[dir.m_122411_()]);
            }
            return this;
        }

        public QuadBuilder addCulledCube(float ... uvs) {
            return this.addCulledCube(-1, uvs);
        }

        public QuadBuilder addCulledCube(float[] ... uvs) {
            for (Direction dir : DirectionList.ALL) {
                this.addCulledFace(dir, -1, uvs[dir.m_122411_()]);
            }
            return this;
        }

        public QuadBuilder addHorizontalCulledCube(float[] ... uvs) {
            for (Direction dir : DirectionList.HORIZONTAL) {
                this.addCulledFace(dir, -1, uvs[dir.m_122416_()]);
            }
            return this;
        }

        public QuadBuilder addCulledCube(int tintedIndex, float ... uvs) {
            for (Direction dir : DirectionList.ALL) {
                this.addCulledFace(dir, tintedIndex, uvs);
            }
            return this;
        }

        public QuadBuilder addHorizontalCulledCube(int tintedIndex, float[] ... uvs) {
            for (Direction dir : DirectionList.HORIZONTAL) {
                this.addCulledFace(dir, tintedIndex, uvs[dir.m_122416_()]);
            }
            return this;
        }

        public QuadBuilder addCulledCube(int tintedIndex, float[] ... uvs) {
            for (Direction dir : DirectionList.ALL) {
                this.addCulledFace(dir, tintedIndex, uvs[dir.m_122411_()]);
            }
            return this;
        }

        public QuadBuilder addFaces(DirectionList dirs, float ... uvs) {
            for (Direction dir : dirs) {
                this.addFace(dir, uvs);
            }
            return this;
        }

        public QuadBuilder addFaces(DirectionList dirs, int tintedIndex, float ... uvs) {
            for (Direction dir : dirs) {
                this.addFace(dir, tintedIndex, uvs);
            }
            return this;
        }

        public QuadBuilder addFaces(DirectionList dirs, int tintedIndex, int textureRotation, float ... uvs) {
            for (Direction dir : dirs) {
                this.addFace(dir, tintedIndex, textureRotation, uvs);
            }
            return this;
        }

        public QuadBuilder addCulledFaces(DirectionList dirs, float ... uvs) {
            for (Direction dir : dirs) {
                this.addCulledFace(dir, uvs);
            }
            return this;
        }

        public QuadBuilder addCulledFaces(DirectionList dirs, int tintedIndex, float ... uvs) {
            for (Direction dir : dirs) {
                this.addCulledFace(dir, tintedIndex, uvs);
            }
            return this;
        }

        public QuadBuilder addCulledFaces(DirectionList dirs, int tintedIndex, int textureRotation, float ... uvs) {
            for (Direction dir : dirs) {
                this.addCulledFace(dir, tintedIndex, textureRotation, uvs);
            }
            return this;
        }

        public QuadBuilder addFace(Direction direction, float ... uvs) {
            return this.addFace(direction, -1, 0, uvs);
        }

        public QuadBuilder addFace(Direction direction, int tintedIndex, float ... uvs) {
            return this.addFace(direction, tintedIndex, 0, uvs);
        }

        public QuadBuilder addFace(Direction direction, int tintedIndex, int textureRotation, float ... uvs) {
            if (uvs.length != 4) {
                throw new IllegalStateException("has to be 4 elements");
            }
            this.builder.quads.add(new Quad(this.box, direction, new ColoredUVs(tintedIndex, textureRotation, uvs), false));
            return this;
        }

        public QuadBuilder addCulledFace(Direction direction, float ... uvs) {
            return this.addCulledFace(direction, -1, 0, uvs);
        }

        public QuadBuilder addCulledFace(Direction direction, int tintedIndex, float ... uvs) {
            return this.addCulledFace(direction, tintedIndex, 0, uvs);
        }

        public QuadBuilder addCulledFace(Direction direction, int tintedIndex, int textureRotation, float ... uvs) {
            if (uvs.length != 4) {
                throw new IllegalStateException("has to be 4 elements");
            }
            this.builder.quads.add(new Quad(this.box, direction, new ColoredUVs(tintedIndex, textureRotation, uvs), true));
            return this;
        }

        public ShapeBuilder finish() {
            return this.builder;
        }
    }

    public static class Quad {
        AABB box;
        ColoredUVs face;
        Direction dir;
        boolean cull;

        Quad(AABB box, Direction dir, ColoredUVs face, boolean cull) {
            this.box = box;
            this.face = face;
            this.dir = dir;
            this.cull = cull;
        }

        public AABB getBox() {
            return this.box;
        }

        public ColoredUVs getUvs() {
            return this.face;
        }

        @OnlyIn(value=Dist.CLIENT)
        public BlockElementFace getFace() {
            return this.face.create();
        }

        public Direction getDir() {
            return this.dir;
        }

        public boolean isCull() {
            return this.cull;
        }
    }

    public static class ColoredUVs {
        int tintedIndex;
        int rotation;
        float[] uvs;

        public ColoredUVs(int tintedIndex, int rotation, float[] uvs) {
            this.tintedIndex = tintedIndex;
            this.rotation = rotation;
            this.uvs = uvs;
        }

        @OnlyIn(value=Dist.CLIENT)
        public BlockElementFace create() {
            return new BlockElementFace(null, this.tintedIndex, "", new BlockFaceUV(this.uvs, this.rotation));
        }
    }
}

