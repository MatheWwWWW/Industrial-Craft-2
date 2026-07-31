/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.renders.models;

import ic2.core.item.misc.CropSeedItem;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.items.BaseItemModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class CropItemModel
extends BaseItemModel<CropItemModel> {
    @Override
    public void init() {
        this.setParticleTexture(IC2Textures.getMappedEntriesItemIC2("crops").get("seed_bag"));
        this.quads.addAll(QuadBaker.createQuadsFromTexture(-1, this.m_6160_(), this.getTransformMap().f_111792_));
        this.initOther(new CropItemModel());
    }

    @Override
    public ItemOverrides m_7343_() {
        return CropOverride.INSTANCE;
    }

    public static class CropOverride
    extends ItemOverrides {
        public static final CropOverride INSTANCE = new CropOverride();

        public BakedModel m_173464_(BakedModel model, ItemStack stack, ClientLevel worldIn, LivingEntity entityIn, int randomValue) {
            ItemStack modelStack = CropSeedItem.getDisplayItem(stack);
            if (!modelStack.m_41619_()) {
                return Minecraft.m_91087_().m_91291_().m_174264_(modelStack, (Level)worldIn, entityIn, randomValue);
            }
            return model;
        }
    }
}

