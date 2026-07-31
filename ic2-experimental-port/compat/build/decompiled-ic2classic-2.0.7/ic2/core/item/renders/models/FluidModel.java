/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.block.model.BakedQuad
 *  net.minecraft.client.renderer.block.model.ItemOverrides
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.renders.models;

import ic2.core.item.misc.FluidDisplay;
import ic2.core.platform.rendering.QuadBaker;
import ic2.core.platform.rendering.models.items.BaseItemModel;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidModel
extends BaseItemModel<FluidModel> {
    Map<Fluid, List<BakedQuad>> fluidMap = CollectionUtils.createMap();
    Fluid last = Fluids.f_76193_;
    CustomOverride custom = new CustomOverride();

    @Override
    public void init() {
        this.setParticleTexture((TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(IClientFluidTypeExtensions.of((Fluid)Fluids.f_76193_).getStillTexture()));
        for (Fluid fluid : ForgeRegistries.FLUIDS) {
            if (fluid == Fluids.f_76191_ || !fluid.m_76145_().m_76170_()) continue;
            this.fluidMap.put(fluid, QuadBaker.createQuadsFromTexture(0, (TextureAtlasSprite)Minecraft.m_91087_().m_91258_(InventoryMenu.f_39692_).apply(IClientFluidTypeExtensions.of((Fluid)fluid).getStillTexture()), this.getTransformMap().f_111792_));
        }
        this.quads.addAll(this.fluidMap.getOrDefault(Fluids.f_76193_, Collections.emptyList()));
        this.initOther(new FluidModel());
    }

    @Override
    public ItemOverrides m_7343_() {
        return this.custom;
    }

    public void setFluid(Fluid fluid) {
        if (this.last == fluid) {
            return;
        }
        this.last = fluid;
        this.quads.clear();
        List<Object> quads = this.fluidMap.get(fluid);
        if (quads == null) {
            quads = this.fluidMap.getOrDefault(Fluids.f_76193_, Collections.emptyList());
        }
        this.quads.addAll(quads);
        this.syncQuads();
    }

    class CustomOverride
    extends ItemOverrides {
        CustomOverride() {
        }

        public BakedModel m_173464_(BakedModel model, ItemStack stack, ClientLevel worldIn, LivingEntity entityIn, int randomValue) {
            FluidModel.this.setFluid(FluidDisplay.getFluid(stack));
            return model;
        }
    }
}

