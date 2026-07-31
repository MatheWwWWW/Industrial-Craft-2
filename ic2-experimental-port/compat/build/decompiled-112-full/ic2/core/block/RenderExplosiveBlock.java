/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BlockRendererDispatcher
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block;

import ic2.core.block.EntityIC2Explosive;
import ic2.core.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class RenderExplosiveBlock
extends Render<EntityIC2Explosive> {
    public RenderExplosiveBlock(RenderManager manager) {
        super(manager);
        this.field_76989_e = 0.5f;
    }

    public void doRender(EntityIC2Explosive entity, double x, double y, double z, float entityYaw, float partialTicks) {
        BlockRendererDispatcher blockRenderer = Minecraft.func_71410_x().func_175602_ab();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)((float)x), (float)((float)y + 0.5f), (float)((float)z));
        if ((float)entity.fuse - partialTicks + 1.0f < 10.0f) {
            float scale = 1.0f - ((float)entity.fuse - partialTicks + 1.0f) / 10.0f;
            scale = Util.limit(scale, 0.0f, 1.0f);
            scale = Util.square(Util.square(scale));
            scale = 1.0f + scale * 0.3f;
            GlStateManager.func_179152_a((float)scale, (float)scale, (float)scale);
        }
        float alpha = (1.0f - ((float)entity.fuse - partialTicks + 1.0f) / 100.0f) * 0.8f;
        this.func_180548_c(entity);
        GlStateManager.func_179114_b((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179109_b((float)-0.5f, (float)-0.5f, (float)0.5f);
        blockRenderer.func_175016_a(entity.renderBlockState, entity.func_70013_c());
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)1.0f);
        if (entity.fuse / 5 % 2 == 0) {
            GlStateManager.func_179090_x();
            GlStateManager.func_179140_f();
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b((int)770, (int)772);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
            GlStateManager.func_179136_a((float)-3.0f, (float)-3.0f);
            GlStateManager.func_179088_q();
            blockRenderer.func_175016_a(entity.renderBlockState, 1.0f);
            GlStateManager.func_179136_a((float)0.0f, (float)0.0f);
            GlStateManager.func_179113_r();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179084_k();
            GlStateManager.func_179145_e();
            GlStateManager.func_179098_w();
        }
        GlStateManager.func_179121_F();
        super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityIC2Explosive entity) {
        return TextureMap.field_110575_b;
    }
}

