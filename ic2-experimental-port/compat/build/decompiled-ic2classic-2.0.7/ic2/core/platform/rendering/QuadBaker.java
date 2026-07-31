/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Transformation
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.FaceInfo$Constants
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.BlockElement
 *  net.minecraft.client.renderer.block.model.BlockElementFace
 *  net.minecraft.client.renderer.block.model.BlockElementRotation
 *  net.minecraft.client.renderer.block.model.FaceBakery
 *  net.minecraft.client.renderer.block.model.ItemModelGenerator
 *  net.minecraft.client.renderer.block.model.ItemTransform
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BlockModelRotation
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.core.Direction
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.QuadTransformers
 *  net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer
 */
package ic2.core.platform.rendering;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import ic2.core.platform.rendering.misc.ModelTransform;
import ic2.core.platform.rendering.models.BaseModel;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.QuadTransformers;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;

@OnlyIn(value=Dist.CLIENT)
public class QuadBaker {
    public static final FaceBakery BAKER = new FaceBakery();
    public static final ItemModelGenerator GENERATOR = new ItemModelGenerator();
    static final Vec3Helper[][] FACES = new Vec3Helper[][]{{new Vec3Helper(false, false, false), new Vec3Helper(true, false, true)}, {new Vec3Helper(false, true, false), new Vec3Helper(true, true, true)}, {new Vec3Helper(false, false, false), new Vec3Helper(true, true, false)}, {new Vec3Helper(false, false, true), new Vec3Helper(true, true, true)}, {new Vec3Helper(false, false, false), new Vec3Helper(true, true, true)}, {new Vec3Helper(false, false, false), new Vec3Helper(true, true, true)}};

    public static BakedQuad copyWithTint(BakedQuad quad, int tint) {
        return new BakedQuad(quad.m_111303_(), tint, quad.m_111306_(), quad.m_173410_(), quad.m_111307_());
    }

    public static List<BakedQuad> createQuadsFromTexture(int tint, TextureAtlasSprite sprite, ItemTransform transform) {
        List parts = GENERATOR.m_111638_(tint, "test", sprite);
        if (parts == null || parts.isEmpty()) {
            return Collections.emptyList();
        }
        ObjectList list = CollectionUtils.createList();
        for (BlockElement part : parts) {
            for (Direction dir : part.f_111310_.keySet()) {
                BlockElementFace face = (BlockElementFace)part.f_111310_.get(dir);
                list.add((BakedQuad)BAKER.m_111600_(part.f_111308_, part.f_111309_, face, sprite, dir, (ModelState)BlockModelRotation.X0_Y0, part.f_111311_, false, BaseModel.LOCATION));
            }
        }
        return list;
    }

    public static BakedQuad createQuad(AABB box, Direction side, BlockElementFace face, TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade) {
        return BAKER.m_111600_(QuadBaker.getMin(side, box), QuadBaker.getMax(side, box), face, sprite, side, (ModelState)rotation, partRotation, shade, BaseModel.LOCATION);
    }

    public static BakedQuad createLockedQuad(AABB box, Direction side, BlockElementFace face, TextureAtlasSprite sprite, BlockModelRotation rotation, BlockElementRotation partRotation, boolean shade) {
        return BAKER.m_111600_(QuadBaker.getMin(side, box), QuadBaker.getMax(side, box), face, sprite, side, (ModelState)new ModelTransform((ModelState)rotation, true), partRotation, shade, BaseModel.LOCATION);
    }

    public static float[] getPosArray(AABB box, Direction dir) {
        return QuadBaker.getPosArray(QuadBaker.getMin(dir, box), QuadBaker.getMax(dir, box));
    }

    public static float[] getPosArray(Vector3f min, Vector3f max) {
        float[] data = new float[6];
        data[FaceInfo.Constants.f_108996_] = min.m_122239_();
        data[FaceInfo.Constants.f_108995_] = min.m_122260_();
        data[FaceInfo.Constants.f_108994_] = min.m_122269_();
        data[FaceInfo.Constants.f_108993_] = max.m_122239_();
        data[FaceInfo.Constants.f_108992_] = max.m_122260_();
        data[FaceInfo.Constants.f_108991_] = max.m_122269_();
        return data;
    }

    public static Vector3f getMin(Direction side, Vector3f min, Vector3f max) {
        return FACES[side.m_122411_()][0].applyBoundingBox(min, max);
    }

    public static Vector3f getMax(Direction side, Vector3f min, Vector3f max) {
        return FACES[side.m_122411_()][1].applyBoundingBox(min, max);
    }

    public static Vector3f getMin(Direction side, AABB box) {
        return FACES[side.m_122411_()][0].applyBoundingBox(box);
    }

    public static Vector3f getMax(Direction side, AABB box) {
        return FACES[side.m_122411_()][1].applyBoundingBox(box);
    }

    public static Tuple<Integer, Integer> getModelRotation(Direction facing) {
        if (facing.m_122434_().m_122479_()) {
            return new Tuple((Object)0, (Object)(facing.m_122416_() * 90));
        }
        return new Tuple((Object)(facing == Direction.DOWN ? 270 : 90), (Object)0);
    }

    public static BakedQuad genQuad(Transformation transform, float x1, float y1, float x2, float y2, float z, TextureAtlasSprite sprite, Direction facing, int color, int tint) {
        float u1 = sprite.m_118367_(0.0);
        float v1 = sprite.m_118393_(0.0);
        float u2 = sprite.m_118367_(16.0);
        float v2 = sprite.m_118393_(16.0);
        float tmp = y1 /= 16.0f;
        y1 = 1.0f - (y2 /= 16.0f);
        y2 = 1.0f - tmp;
        return QuadBaker.putQuad(transform, facing, sprite, color, tint, x1 /= 16.0f, y1, x2 /= 16.0f, y2, z, u1, v1, u2, v2, 0);
    }

    private static BakedQuad putQuad(Transformation transform, Direction side, TextureAtlasSprite sprite, int color, int tint, float x1, float y1, float x2, float y2, float z, float u1, float v1, float u2, float v2, int luminosity) {
        BakedQuad[] result = new BakedQuad[1];
        QuadBakingVertexConsumer builder = new QuadBakingVertexConsumer(T -> {
            result[0] = T;
        });
        builder.setTintIndex(tint);
        builder.setDirection(side);
        builder.setShade(luminosity == 0);
        builder.setSprite(sprite);
        if (side == Direction.SOUTH) {
            QuadBaker.putVertex((VertexConsumer)builder, side, x1, y1, z, u1, v2, color, luminosity);
            QuadBaker.putVertex((VertexConsumer)builder, side, x2, y1, z, u2, v2, color, luminosity);
            QuadBaker.putVertex((VertexConsumer)builder, side, x2, y2, z, u2, v1, color, luminosity);
            QuadBaker.putVertex((VertexConsumer)builder, side, x1, y2, z, u1, v1, color, luminosity);
        } else {
            QuadBaker.putVertex((VertexConsumer)builder, side, x1, y1, z, u1, v2, color, luminosity);
            QuadBaker.putVertex((VertexConsumer)builder, side, x1, y2, z, u1, v1, color, luminosity);
            QuadBaker.putVertex((VertexConsumer)builder, side, x2, y2, z, u2, v1, color, luminosity);
            QuadBaker.putVertex((VertexConsumer)builder, side, x2, y1, z, u2, v2, color, luminosity);
        }
        if (!transform.isIdentity()) {
            QuadTransformers.applying((Transformation)transform).processInPlace(result[0]);
        }
        return result[0];
    }

    private static void putVertex(VertexConsumer consumer, Direction side, float x, float y, float z, float u, float v, int color, int luminosity) {
        consumer.m_5483_((double)x, (double)y, (double)z).m_193479_(color).m_7421_(u, v).m_7120_((int)((float)(luminosity << 4) / 32768.0f), (int)((float)(luminosity << 4) / 32768.0f)).m_5601_((float)side.m_122429_(), (float)side.m_122430_(), (float)side.m_122431_()).m_5752_();
    }

    public static class Vec3Helper {
        boolean x;
        boolean y;
        boolean z;

        public Vec3Helper(boolean par1, boolean par2, boolean par3) {
            this.x = par1;
            this.y = par2;
            this.z = par3;
        }

        public Vector3f applyBoundingBox(Vector3f min, Vector3f max) {
            float xScale = this.x ? max.m_122239_() : min.m_122239_();
            float yScale = this.y ? max.m_122260_() : min.m_122260_();
            float zScale = this.z ? max.m_122269_() : min.m_122269_();
            return new Vector3f(xScale, yScale, zScale);
        }

        public Vector3f applyBoundingBox(AABB box) {
            float xScale = (float)(this.x ? box.f_82291_ : box.f_82288_);
            float yScale = (float)(this.y ? box.f_82292_ : box.f_82289_);
            float zScale = (float)(this.z ? box.f_82293_ : box.f_82290_);
            return new Vector3f(xScale, yScale, zScale);
        }
    }
}

