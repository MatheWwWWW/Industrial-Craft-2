/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Quaternion
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.ParticleStatus
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.platform.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import java.util.function.BiConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.gui.Font;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;

@OnlyIn(value=Dist.CLIENT)
public class RenderUtils {
    public static PoseStack createPlayer(PoseStack matrix) {
        matrix.m_85836_();
        Vec3 playerPos = Minecraft.m_91087_().f_91063_.m_109153_().m_90583_();
        matrix.m_85837_(-playerPos.m_7096_(), -playerPos.m_7098_(), -playerPos.m_7094_());
        return matrix;
    }

    public static Frustum createClipper(RenderLevelStageEvent event) {
        Vec3 playerPos = Minecraft.m_91087_().f_91063_.m_109153_().m_90583_();
        Frustum helper = new Frustum(event.getPoseStack().m_85850_().m_85861_(), event.getProjectionMatrix());
        helper.m_113002_(playerPos.m_7096_(), playerPos.m_7098_(), playerPos.m_7094_());
        return helper;
    }

    public static Tesselator draw(PoseStack stack, BiConsumer<BufferBuilder, PoseStack> builder) {
        Tesselator tes = Tesselator.m_85913_();
        builder.accept(tes.m_85915_(), RenderUtils.createPlayer(stack));
        stack.m_85849_();
        return tes;
    }

    public static void renderGuiItem(ItemRenderer render, PoseStack matrix, ItemStack stack, float x, float y) {
        boolean flag;
        RenderSystem.m_69478_();
        RenderSystem.m_69408_((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        BakedModel model = render.m_174264_(stack, null, null, 0);
        boolean bl = flag = !model.m_7547_();
        if (flag) {
            Lighting.m_84930_();
        }
        MultiBufferSource.BufferSource buffer = Minecraft.m_91087_().m_91269_().m_110104_();
        matrix.m_85836_();
        matrix.m_85837_((double)(8.0f + x), (double)(8.0f + y), 100.0);
        matrix.m_85841_(16.0f, -16.0f, 16.0f);
        matrix.m_85850_().m_85864_().m_8180_();
        render.m_115143_(stack, ItemTransforms.TransformType.GUI, false, matrix, (MultiBufferSource)buffer, 0xF000F0, OverlayTexture.f_118083_, model);
        matrix.m_85849_();
        buffer.m_109911_();
        RenderSystem.m_69482_();
        if (flag) {
            Lighting.m_84931_();
        }
    }

    public static void renderGuiItemDecorations(PoseStack matrixstack, Font font, ItemStack stack, int x, int y) {
        RenderUtils.renderGuiItemDecorations(matrixstack, font, stack, x, y, null);
    }

    public static void renderGuiItemDecorations(PoseStack matrixstack, Font font, ItemStack stack, float x, float y, String text) {
        if (!stack.m_41619_()) {
            if (stack.m_41613_() != 1 || text != null) {
                String s = text == null ? String.valueOf(stack.m_41613_()) : text;
                matrixstack.m_85837_(0.0, 0.0, 200.0);
                MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_((BufferBuilder)Tesselator.m_85913_().m_85915_());
                font.m_92811_(s, x + 19.0f - 2.0f - (float)font.m_92895_(s), y + 9.0f, 0xFFFFFF, true, matrixstack.m_85850_().m_85861_(), (MultiBufferSource)buffer, false, 0, 0xF000F0);
                matrixstack.m_85837_(0.0, 0.0, -200.0);
                buffer.m_109911_();
            }
            if (stack.m_150947_()) {
                RenderSystem.m_69465_();
                RenderSystem.m_69472_();
                RenderSystem.m_69461_();
                int i = stack.m_150948_();
                int j = stack.m_150949_();
                Tesselator tessellator = Tesselator.m_85913_();
                BufferBuilder buffer = tessellator.m_85915_();
                buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
                RenderUtils.fillRect(buffer, matrixstack.m_85850_().m_85861_(), x + 2.0f, y + 13.0f, 13.0f, 2.0f, 0, 0, 0, 255);
                RenderUtils.fillRect(buffer, matrixstack.m_85850_().m_85861_(), x + 2.0f, y + 13.0f, i, 1.0f, j >> 16 & 0xFF, j >> 8 & 0xFF, j & 0xFF, 255);
                RenderSystem.m_157427_(GameRenderer::m_172811_);
                tessellator.m_85914_();
                RenderSystem.m_69478_();
                RenderSystem.m_69493_();
                RenderSystem.m_69482_();
            }
        }
    }

    private static void fillRect(BufferBuilder buffer, Matrix4f matrix, float x, float y, float width, float height, int r, int g, int b, int a) {
        buffer.m_85982_(matrix, x, y, 0.0f).m_6122_(r, g, b, a).m_5752_();
        buffer.m_85982_(matrix, x, y + height, 0.0f).m_6122_(r, g, b, a).m_5752_();
        buffer.m_85982_(matrix, x + width, y + height, 0.0f).m_6122_(r, g, b, a).m_5752_();
        buffer.m_85982_(matrix, x + width, y, 0.0f).m_6122_(r, g, b, a).m_5752_();
    }

    @OnlyIn(value=Dist.CLIENT)
    public static void prepareText(PoseStack stack, Direction direction, float textScale, double startX, double startY, double zLevel, double x, double y, double z) {
        stack.m_85837_(x + 0.5, y + 0.5, z + 0.5);
        if (direction.m_122434_().m_122478_()) {
            stack.m_85845_(new Quaternion(direction == Direction.DOWN ? 90.0f : 270.0f, 0.0f, 0.0f, true));
        } else {
            stack.m_85845_(new Quaternion(0.0f, direction.m_122435_() + 180.0f, 0.0f, true));
        }
        stack.m_85845_(new Quaternion(0.0f, 0.0f, 180.0f, true));
        stack.m_85837_(-0.5, -0.5, -0.5);
        stack.m_85837_(0.0, 0.0, zLevel);
        stack.m_85841_(0.0039063f, 0.0039063f, -1.0E-4f);
        stack.m_85837_(startX, startY, 0.0);
        stack.m_85841_(textScale, textScale, 1.0f);
    }

    public static Particle addOptionalParticle(ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        Minecraft mc = Minecraft.m_91087_();
        Camera activerenderinfo = mc.f_91063_.m_109153_();
        if (activerenderinfo.m_90593_() && mc.f_91061_ != null) {
            if (activerenderinfo.m_90583_().m_82531_(x, y, z) > 1024.0) {
                return null;
            }
            return RenderUtils.calculateParticleStatus(true, mc) == ParticleStatus.MINIMAL ? null : mc.f_91061_.m_107370_(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
        }
        return null;
    }

    static ParticleStatus calculateParticleStatus(boolean allowDecrease, Minecraft mc) {
        ParticleStatus particlestatus = (ParticleStatus)mc.f_91066_.m_231929_().m_231551_();
        if (allowDecrease && particlestatus == ParticleStatus.MINIMAL && mc.f_91073_.f_46441_.m_188503_(10) == 0) {
            particlestatus = ParticleStatus.DECREASED;
        }
        if (particlestatus == ParticleStatus.DECREASED && mc.f_91073_.f_46441_.m_188503_(3) == 0) {
            particlestatus = ParticleStatus.MINIMAL;
        }
        return particlestatus;
    }
}

