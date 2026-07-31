/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.client.event.ModelEvent$RegisterGeometryLoaders
 *  net.minecraftforge.client.event.RegisterColorHandlersEvent$Block
 *  net.minecraftforge.client.event.RegisterColorHandlersEvent$Item
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.model.geometry.IGeometryLoader
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 */
package ic2.forge;

import ic2.forge.ClientEnvProxyForge;
import ic2.forge.model.BeModelLoader;
import ic2.forge.model.CableModelLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientModEventHandlerForge {
    @SubscribeEvent
    public void onRegisterBlockColorProviders(RegisterColorHandlersEvent.Block block) {
        for (ClientEnvProxyForge.BlockColorProviderRegistration blockColorProviderRegistration : ClientEnvProxyForge.blockColorProviderRegistrations) {
            block.getBlockColors().m_92589_(blockColorProviderRegistration.provider(), blockColorProviderRegistration.blocks());
        }
    }

    @SubscribeEvent
    public void onRegisterItemColorProviders(RegisterColorHandlersEvent.Item item) {
        for (ClientEnvProxyForge.ItemColorProviderRegistration itemColorProviderRegistration : ClientEnvProxyForge.itemColorProviderRegistrations) {
            item.getItemColors().m_92689_(itemColorProviderRegistration.provider(), itemColorProviderRegistration.items());
        }
    }

    @SubscribeEvent
    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers registerRenderers) {
        for (ClientEnvProxyForge.BerRegistration<?> record : ClientEnvProxyForge.berRegistrations) {
            ClientModEventHandlerForge.registerBer(record, registerRenderers);
        }
        for (ClientEnvProxyForge.EntityRendererRegistration entityRendererRegistration : ClientEnvProxyForge.entityRendererRegistrations) {
            ClientModEventHandlerForge.registerEntityRenderer(entityRendererRegistration, registerRenderers);
        }
        for (ClientEnvProxyForge.BlockEntityRendererRegistration blockEntityRendererRegistration : ClientEnvProxyForge.blockEntityRendererRegistrations) {
            ClientModEventHandlerForge.registerBlockEntityRenderer(blockEntityRendererRegistration, registerRenderers);
        }
    }

    private static <T extends BlockEntity> void registerBer(ClientEnvProxyForge.BerRegistration<T> berRegistration, EntityRenderersEvent.RegisterRenderers registerRenderers) {
        registerRenderers.registerBlockEntityRenderer(berRegistration.blockEntityType(), berRegistration.blockEntityRendererProvider());
    }

    private static <T extends Entity> void registerEntityRenderer(ClientEnvProxyForge.EntityRendererRegistration<T> entityRendererRegistration, EntityRenderersEvent.RegisterRenderers registerRenderers) {
        registerRenderers.registerEntityRenderer(entityRendererRegistration.type(), entityRendererRegistration.factory());
    }

    private static <T extends BlockEntity> void registerBlockEntityRenderer(ClientEnvProxyForge.BlockEntityRendererRegistration<T> blockEntityRendererRegistration, EntityRenderersEvent.RegisterRenderers registerRenderers) {
        registerRenderers.registerBlockEntityRenderer(blockEntityRendererRegistration.type(), blockEntityRendererRegistration.factory());
    }

    @SubscribeEvent
    public void onModelRegistry(ModelEvent.RegisterGeometryLoaders registerGeometryLoaders) {
        registerGeometryLoaders.register("be", (IGeometryLoader)new BeModelLoader());
        registerGeometryLoaders.register("cable", (IGeometryLoader)new CableModelLoader());
    }

    @SubscribeEvent
    public void onRegisterKeybindings(RegisterKeyMappingsEvent registerKeyMappingsEvent) {
        for (KeyMapping keyMapping : ClientEnvProxyForge.keyBindingRegistrations) {
            registerKeyMappingsEvent.register(keyMapping);
        }
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent fMLClientSetupEvent) {
        for (ClientEnvProxyForge.BlockLayerRegistration blockLayerRegistration : ClientEnvProxyForge.blockLayerRegistrations) {
            for (Block block : blockLayerRegistration.blocks()) {
                ItemBlockRenderTypes.setRenderLayer((Block)block, (RenderType)blockLayerRegistration.layer());
            }
        }
    }
}

