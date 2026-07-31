/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.client.renderer.entity.layers.LayerBipedArmor
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.armor.jetpack;

import ic2.core.item.armor.jetpack.JetpackHandler;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class LayerJetpackOverride
extends LayerBipedArmor {
    private final RenderLivingBase<?> renderer;

    public LayerJetpackOverride(RenderLivingBase<?> renderer) {
        super(null);
        this.renderer = renderer;
    }

    public void func_177141_a(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ModelBiped model = this.getArmorModelHook(entity, JetpackHandler.jetpack, EntityEquipmentSlot.CHEST, (ModelBiped)this.func_188360_a(EntityEquipmentSlot.CHEST));
        model.func_178686_a(this.renderer.func_177087_b());
        model.func_78086_a(entity, limbSwing, limbSwingAmount, partialTicks);
        this.func_188359_a(model, EntityEquipmentSlot.CHEST);
        this.renderer.func_110776_a(this.getArmorResource((Entity)entity, JetpackHandler.jetpack, EntityEquipmentSlot.CHEST, null));
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        model.func_78088_a((Entity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}

