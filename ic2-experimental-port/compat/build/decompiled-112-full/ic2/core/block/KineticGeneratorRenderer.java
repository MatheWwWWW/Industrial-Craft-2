/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package ic2.core.block;

import ic2.api.tile.IRotorProvider;
import ic2.core.block.KineticGeneratorRotor;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class KineticGeneratorRenderer<T extends TileEntity>
extends TileEntitySpecialRenderer<T> {
    private static final Map<Integer, ModelBase> rotorModels = new HashMap<Integer, ModelBase>();

    protected void renderBlockRotor(IRotorProvider windGen, World world, BlockPos pos) {
        int diameter = windGen.getRotorDiameter();
        if (diameter == 0) {
            return;
        }
        float angle = windGen.getAngle();
        ResourceLocation rotorRL = windGen.getRotorRenderTexture();
        ModelBase model = rotorModels.get(diameter);
        if (model == null) {
            model = new KineticGeneratorRotor(diameter);
            rotorModels.put(diameter, model);
        }
        EnumFacing facing = windGen.getFacing();
        pos = pos.func_177972_a(facing);
        int light = world.func_175626_b(pos, 0);
        int blockLight = light % 65536;
        int skyLight = light / 65536;
        OpenGlHelper.func_77475_a((int)OpenGlHelper.field_77476_b, (float)blockLight, (float)skyLight);
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)0.5f, (float)0.5f, (float)0.5f);
        switch (facing) {
            case NORTH: {
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                break;
            }
            case EAST: {
                GL11.glRotatef((float)-180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                break;
            }
            case SOUTH: {
                GL11.glRotatef((float)-270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                break;
            }
            case UP: {
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                break;
            }
        }
        GlStateManager.func_179114_b((float)angle, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179109_b((float)-0.2f, (float)0.0f, (float)0.0f);
        this.func_147499_a(rotorRL);
        model.func_78088_a(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.func_179121_F();
    }

    public void func_192841_a(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)x), (float)((float)y), (float)((float)z));
        if (te instanceof IRotorProvider) {
            this.renderBlockRotor((IRotorProvider)te, te.func_145831_w(), te.func_174877_v());
        }
        GL11.glPopMatrix();
    }
}

