/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.beam;

import ic2.core.block.beam.EntityParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class RenderBeam
extends Render<EntityParticle> {
    private final ResourceLocation texture = new ResourceLocation("ic2", "textures/models/beam.png");

    public RenderBeam(RenderManager manager) {
        super(manager);
    }

    public void doRender(EntityParticle entity, double x, double y, double z, float yaw, float partialTickTime) {
        EntityParticle particle = entity;
        EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        double playerX = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * (double)partialTickTime;
        double playerY = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * (double)partialTickTime;
        double playerZ = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * (double)partialTickTime;
        double particleX = particle.field_70169_q + (particle.field_70165_t - particle.field_70169_q) * (double)partialTickTime - playerX;
        double particleY = particle.field_70167_r + (particle.field_70163_u - particle.field_70167_r) * (double)partialTickTime - playerY;
        double particleZ = particle.field_70166_s + (particle.field_70161_v - particle.field_70166_s) * (double)partialTickTime - playerZ;
        double u1 = 0.0;
        double u2 = 1.0;
        double v1 = 0.0;
        double v2 = 1.0;
        double scale = 0.1;
        this.func_110776_a(this.getEntityTexture(entity));
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179147_l();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(particleX - (double)(ActiveRenderInfo.func_178808_b() + ActiveRenderInfo.func_178805_e()) * scale, particleY - (double)ActiveRenderInfo.func_178809_c() * scale, particleZ - (double)(ActiveRenderInfo.func_178803_d() + ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u2, v2).func_181675_d();
        worldrenderer.func_181662_b(particleX - (double)(ActiveRenderInfo.func_178808_b() - ActiveRenderInfo.func_178805_e()) * scale, particleY + (double)ActiveRenderInfo.func_178809_c() * scale, particleZ - (double)(ActiveRenderInfo.func_178803_d() - ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u2, v1).func_181675_d();
        worldrenderer.func_181662_b(particleX + (double)(ActiveRenderInfo.func_178808_b() + ActiveRenderInfo.func_178805_e()) * scale, particleY + (double)ActiveRenderInfo.func_178809_c() * scale, particleZ + (double)(ActiveRenderInfo.func_178803_d() + ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u1, v1).func_181675_d();
        worldrenderer.func_181662_b(particleX + (double)(ActiveRenderInfo.func_178808_b() - ActiveRenderInfo.func_178805_e()) * scale, particleY - (double)ActiveRenderInfo.func_178809_c() * scale, particleZ + (double)(ActiveRenderInfo.func_178803_d() - ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u1, v2).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179084_k();
        GlStateManager.func_179132_a((boolean)true);
    }

    protected ResourceLocation getEntityTexture(EntityParticle entity) {
        return this.texture;
    }
}

