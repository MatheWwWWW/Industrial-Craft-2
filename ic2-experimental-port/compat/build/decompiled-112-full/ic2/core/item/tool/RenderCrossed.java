/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.item.tool;

import ic2.core.item.tool.EntityMiningLaser;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderCrossed
extends Render<EntityMiningLaser> {
    private final ResourceLocation texture;

    public RenderCrossed(RenderManager manager, ResourceLocation texture) {
        super(manager);
        this.texture = texture;
    }

    public void doRender(EntityMiningLaser entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.field_70126_B == 0.0f && entity.field_70127_C == 0.0f) {
            return;
        }
        this.func_110776_a(this.getEntityTexture(entity));
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)((float)x), (float)((float)y), (float)((float)z));
        GlStateManager.func_179114_b((float)(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks - 90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks), (float)0.0f, (float)0.0f, (float)1.0f);
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder worldrenderer = tessellator.func_178180_c();
        float uSideS = 0.0f;
        float uSideE = 0.5f;
        float vSideS = 0.0f;
        float vSideE = 0.15625f;
        float uBackS = 0.0f;
        float uBackE = 0.15625f;
        float vBackS = 0.15625f;
        float vBackE = 0.3125f;
        float scale = 0.05625f;
        GlStateManager.func_179091_B();
        GlStateManager.func_179114_b((float)45.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179152_a((float)scale, (float)scale, (float)scale);
        GlStateManager.func_179109_b((float)-4.0f, (float)0.0f, (float)0.0f);
        GL11.glNormal3f((float)scale, (float)0.0f, (float)0.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(-7.0, -2.0, -2.0).func_187315_a((double)uBackS, (double)vBackS).func_181675_d();
        worldrenderer.func_181662_b(-7.0, -2.0, 2.0).func_187315_a((double)uBackE, (double)vBackS).func_181675_d();
        worldrenderer.func_181662_b(-7.0, 2.0, 2.0).func_187315_a((double)uBackE, (double)vBackE).func_181675_d();
        worldrenderer.func_181662_b(-7.0, 2.0, -2.0).func_187315_a((double)uBackS, (double)vBackE).func_181675_d();
        tessellator.func_78381_a();
        GL11.glNormal3f((float)(-scale), (float)0.0f, (float)0.0f);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(-7.0, 2.0, -2.0).func_187315_a((double)uBackS, (double)vBackS).func_181675_d();
        worldrenderer.func_181662_b(-7.0, 2.0, 2.0).func_187315_a((double)uBackE, (double)vBackS).func_181675_d();
        worldrenderer.func_181662_b(-7.0, -2.0, 2.0).func_187315_a((double)uBackE, (double)vBackE).func_181675_d();
        worldrenderer.func_181662_b(-7.0, -2.0, -2.0).func_187315_a((double)uBackS, (double)vBackE).func_181675_d();
        tessellator.func_78381_a();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.func_179114_b((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glNormal3f((float)0.0f, (float)0.0f, (float)scale);
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
            worldrenderer.func_181662_b(-8.0, -2.0, 0.0).func_187315_a((double)uSideS, (double)vSideS).func_181675_d();
            worldrenderer.func_181662_b(8.0, -2.0, 0.0).func_187315_a((double)uSideE, (double)vSideS).func_181675_d();
            worldrenderer.func_181662_b(8.0, 2.0, 0.0).func_187315_a((double)uSideE, (double)vSideE).func_181675_d();
            worldrenderer.func_181662_b(-8.0, 2.0, 0.0).func_187315_a((double)uSideS, (double)vSideE).func_181675_d();
            tessellator.func_78381_a();
        }
        GlStateManager.func_179101_C();
        GlStateManager.func_179121_F();
    }

    protected ResourceLocation getEntityTexture(EntityMiningLaser entity) {
        return this.texture;
    }
}

