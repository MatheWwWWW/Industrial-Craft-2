/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.ConfigScreenHandler$ConfigScreenFactory
 *  net.minecraftforge.client.event.ClientPlayerNetworkEvent$LoggingIn
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.ModContainer
 *  net.minecraftforge.fml.ModLoadingContext
 *  net.minecraftforge.fml.loading.FMLEnvironment
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.utils.config.impl.internal;

import ic2.core.IC2;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.config.BulkSyncPacket;
import ic2.core.networking.packets.config.SyncPacket;
import ic2.core.utils.config.api.IConfigChangeListener;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.gui.api.DataType;
import ic2.core.utils.config.gui.api.IModConfigs;
import ic2.core.utils.config.gui.api.ISuggestionRenderer;
import ic2.core.utils.config.gui.config.ColorElement;
import ic2.core.utils.config.gui.config.RegistryElement;
import ic2.core.utils.config.gui.screen.ConfigSelectorScreen;
import ic2.core.utils.config.gui.widgets.SuggestionRenderers;
import ic2.core.utils.config.impl.entries.ColorEntry;
import ic2.core.utils.config.impl.internal.ModConfigs;
import ic2.core.utils.config.utils.SyncType;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;

public class EventHandler
implements IConfigChangeListener {
    public static final EventHandler INSTANCE = new EventHandler();
    Map<ModContainer, ModConfigs> configs = new Object2ObjectLinkedOpenHashMap();

    @Override
    public void onConfigCreated(ConfigHandler config) {
        this.initMinecraftDataTypes(config);
        if (FMLEnvironment.dist.isDedicatedServer()) {
            return;
        }
        ModLoadingContext context = ModLoadingContext.get();
        if ("minecraft".equals(context.getActiveNamespace())) {
            if (FMLEnvironment.production) {
                return;
            }
            throw new IllegalStateException("Mod Configs Must be created (not loaded) during a Mod Loading Phase");
        }
        this.configs.computeIfAbsent(context.getActiveContainer(), ModConfigs::new).addConfig(config);
    }

    public void initMinecraftDataTypes(ConfigHandler config) {
        config.addParser('C', ColorEntry::parse);
    }

    @Override
    public void onConfigAdded(ConfigHandler config) {
    }

    @Override
    public void onConfigChanged(ConfigHandler config) {
        SyncPacket packet;
        if (FMLEnvironment.dist.isDedicatedServer()) {
            SyncPacket packet2 = SyncPacket.create(config, SyncType.SERVER_TO_CLIENT, false);
            if (packet2 != null) {
                PacketManager.INSTANCE.sendToAllPlayers(packet2);
            }
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null && (packet = SyncPacket.create(config, SyncType.SERVER_TO_CLIENT, false)) != null) {
            PacketManager.INSTANCE.sendToAllPlayers(packet);
        }
        if (IC2.PLATFORM.isInWorld() && (packet = SyncPacket.create(config, SyncType.CLIENT_TO_SERVER, false)) != null) {
            PacketManager.INSTANCE.sendToServer(packet);
        }
    }

    @Override
    public void onConfigErrored(ConfigHandler configHandler) {
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.DEDICATED_SERVER)
    public void onServerTickEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            this.processEvents();
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            this.processEvents();
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onConfigsLoaded() {
        this.loadDefaultTypes();
        this.configs.forEach((M, C) -> {
            if (M.getCustomExtension(ConfigScreenHandler.ConfigScreenFactory.class).isPresent()) {
                return;
            }
            M.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((U, S) -> this.create((Screen)S, (IModConfigs)C)));
        });
    }

    @OnlyIn(value=Dist.CLIENT)
    private void loadDefaultTypes() {
        ISuggestionRenderer.Registry.register(Item.class, new SuggestionRenderers.ItemEntry());
        ISuggestionRenderer.Registry.register(Block.class, new SuggestionRenderers.ItemEntry());
        ISuggestionRenderer.Registry.register(Fluid.class, new SuggestionRenderers.FluidEntry());
        ISuggestionRenderer.Registry.register(Enchantment.class, new SuggestionRenderers.EnchantmentEntry());
        ISuggestionRenderer.Registry.register(ColorEntry.ColorWrapper.class, new SuggestionRenderers.ColorEntry());
        ISuggestionRenderer.Registry.register(MobEffect.class, new SuggestionRenderers.PotionEntry());
        DataType.registerType(Item.class, RegistryElement.createForType(Item.class, "minecraft:air"));
        DataType.registerType(Block.class, RegistryElement.createForType(Block.class, "minecraft:air"));
        DataType.registerType(Fluid.class, RegistryElement.createForType(Fluid.class, "minecraft:empty"));
        DataType.registerType(Enchantment.class, RegistryElement.createForType(Enchantment.class, "minecraft:fortune"));
        DataType.registerType(MobEffect.class, RegistryElement.createForType(MobEffect.class, "minecraft:luck"));
        DataType.registerType(ColorEntry.ColorWrapper.class, new DataType(false, "0xFFFFFFFF", ColorElement::new, ColorElement::new));
    }

    @OnlyIn(value=Dist.CLIENT)
    private Screen create(Screen screen, IModConfigs configs) {
        return new ConfigSelectorScreen(configs, screen);
    }

    @SubscribeEvent
    public void onPlayerServerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        BulkSyncPacket packet = BulkSyncPacket.create(IC2.FILE_WATCHER.getConfigsToSync(), SyncType.SERVER_TO_CLIENT, true);
        if (packet == null) {
            return;
        }
        PacketManager.INSTANCE.sendToPlayer(packet, event.getEntity());
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onPlayerServerJoinEvent(ClientPlayerNetworkEvent.LoggingIn event) {
        BulkSyncPacket packet = BulkSyncPacket.create(IC2.FILE_WATCHER.getConfigsToSync(), SyncType.CLIENT_TO_SERVER, true);
        if (packet == null) {
            return;
        }
        PacketManager.INSTANCE.sendToServer(packet);
    }

    private void processEvents() {
        IC2.FILE_WATCHER.processFileSystemEvents();
    }
}

