/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.client.gui.screens.MenuScreens
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
 *  net.minecraft.client.renderer.entity.EntityRendererProvider
 *  net.minecraft.client.renderer.item.ClampedItemPropertyFunction
 *  net.minecraft.client.renderer.item.ItemProperties
 *  net.minecraft.client.renderer.item.ItemPropertyFunction
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.fml.loading.FMLPaths
 */
package ic2.forge;

import ic2.core.fluid.Ic2FluidStack;
import ic2.core.init.Localization;
import ic2.core.proxy.ClientEnvProxy;
import ic2.core.proxy.SideProxyClient;
import ic2.forge.EnvFluidHandlerForge;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fml.loading.FMLPaths;

public final class ClientEnvProxyForge
implements ClientEnvProxy {
    static List<BlockColorProviderRegistration> blockColorProviderRegistrations = new ArrayList<BlockColorProviderRegistration>();
    static List<ItemColorProviderRegistration> itemColorProviderRegistrations = new ArrayList<ItemColorProviderRegistration>();
    static List<KeyMapping> keyBindingRegistrations = new ArrayList<KeyMapping>();
    static List<BerRegistration<?>> berRegistrations = new ArrayList();
    static List<BlockLayerRegistration> blockLayerRegistrations = new ArrayList<BlockLayerRegistration>();
    static List<EntityRendererRegistration<?>> entityRendererRegistrations = new ArrayList();
    static List<BlockEntityRendererRegistration<?>> blockEntityRendererRegistrations = new ArrayList();

    @Override
    public <H extends AbstractContainerMenu> void registerScreen(MenuType<H> menuType, ClientEnvProxy.ScreenFactory<H> screenFactory) {
        MenuScreens.m_96206_(menuType, screenFactory::create);
    }

    @Override
    public File getMinecraftDir() {
        return FMLPaths.GAMEDIR.get().toFile();
    }

    @Override
    public void registerColorProvider(BlockColor blockColor, Block ... blockArray) {
        blockColorProviderRegistrations.add(new BlockColorProviderRegistration(blockColor, blockArray));
    }

    @Override
    public void registerColorProvider(ItemColor itemColor, ItemLike ... itemLikeArray) {
        itemColorProviderRegistrations.add(new ItemColorProviderRegistration(itemColor, itemLikeArray));
    }

    @Override
    public void registerKeyBinding(KeyMapping keyMapping) {
        keyBindingRegistrations.add(keyMapping);
    }

    @Override
    public <E extends BlockEntity> void registerBer(BlockEntityType<E> blockEntityType, BlockEntityRendererProvider<? super E> blockEntityRendererProvider) {
        berRegistrations.add(new BerRegistration<E>(blockEntityType, blockEntityRendererProvider));
    }

    @Override
    public void registerModelPredicateProvider(ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        ItemProperties.registerGeneric((ResourceLocation)resourceLocation, (ItemPropertyFunction)clampedItemPropertyFunction);
    }

    @Override
    public void registerModelPredicateProvider(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        ItemProperties.register((Item)item, (ResourceLocation)resourceLocation, (ItemPropertyFunction)clampedItemPropertyFunction);
    }

    @Override
    public void registerBlockLayer(RenderType renderType, Block ... blockArray) {
        blockLayerRegistrations.add(new BlockLayerRegistration(renderType, blockArray));
    }

    @Override
    public <E extends Entity> void registerEntityRenderer(EntityType<? extends E> entityType, EntityRendererProvider<E> entityRendererProvider) {
        entityRendererRegistrations.add(new EntityRendererRegistration<E>(entityType, entityRendererProvider));
    }

    @Override
    public <E extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<? extends E> blockEntityType, BlockEntityRendererProvider<E> blockEntityRendererProvider) {
        blockEntityRendererRegistrations.add(new BlockEntityRendererRegistration<E>(blockEntityType, blockEntityRendererProvider));
    }

    IClientFluidTypeExtensions getAttributes(Ic2FluidStack ic2FluidStack) {
        return IClientFluidTypeExtensions.of((Fluid)ic2FluidStack.getFluid());
    }

    @Override
    public TextureAtlasSprite getFluidStillSprite(Ic2FluidStack ic2FluidStack) {
        ResourceLocation resourceLocation = this.getAttributes(ic2FluidStack).getStillTexture(EnvFluidHandlerForge.getForgeFs(ic2FluidStack));
        return (TextureAtlasSprite)SideProxyClient.mc.m_91258_(TextureAtlas.f_118259_).apply(resourceLocation);
    }

    @Override
    public int getFluidColor(Ic2FluidStack ic2FluidStack) {
        return this.getAttributes(ic2FluidStack).getTintColor(EnvFluidHandlerForge.getForgeFs(ic2FluidStack));
    }

    @Override
    public String getFluidName(Ic2FluidStack ic2FluidStack) {
        return Localization.translate(ic2FluidStack.getFluid().getFluidType().getDescriptionId(EnvFluidHandlerForge.getForgeFs(ic2FluidStack)));
    }

    record BlockColorProviderRegistration(BlockColor provider, Block[] blocks) {
    }

    record ItemColorProviderRegistration(ItemColor provider, ItemLike[] items) {
    }

    record BerRegistration<T extends BlockEntity>(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T> blockEntityRendererProvider) {
    }

    record BlockLayerRegistration(RenderType layer, Block[] blocks) {
    }

    record EntityRendererRegistration<E extends Entity>(EntityType<? extends E> type, EntityRendererProvider<E> factory) {
    }

    record BlockEntityRendererRegistration<B extends BlockEntity>(BlockEntityType<? extends B> type, BlockEntityRendererProvider<B> factory) {
    }
}

