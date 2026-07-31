/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.renders.models;

import ic2.core.platform.rendering.IC2Models;
import ic2.core.platform.rendering.models.BaseModel;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class ShieldModel
extends BaseModel {
    private static final ResourceLocation modelNormal = new ResourceLocation("minecraft:models/item/shield");
    private static final ResourceLocation modelBlocking = new ResourceLocation("minecraft:models/item/shield_blocking");
    ModelResourceLocation location;
    boolean blocking;

    public ShieldModel(boolean isBlocking) {
        this.setTransforms(IC2Models.getTransformMap(isBlocking ? modelBlocking : modelNormal));
        this.blocking = isBlocking;
    }

    public ShieldModel setOther(ModelResourceLocation texture) {
        this.location = texture;
        return this;
    }

    @Override
    public ItemOverrides m_7343_() {
        return new ShieldOverrideList(this.location);
    }

    @Override
    public boolean m_7521_() {
        return true;
    }

    @Override
    public void init() {
    }

    public static class ShieldOverrideList
    extends ItemOverrides {
        ModelResourceLocation model;

        public ShieldOverrideList(ModelResourceLocation location) {
            this.model = location;
        }

        @Nullable
        public BakedModel m_173464_(BakedModel model, ItemStack stack, ClientLevel worldIn, LivingEntity entityIn, int randomValue) {
            if (model != null && entityIn != null && entityIn.m_6117_() && entityIn.m_21211_() == stack) {
                return Minecraft.m_91087_().m_91304_().m_119422_(this.model);
            }
            return super.m_173464_(model, stack, worldIn, entityIn, randomValue);
        }
    }
}

