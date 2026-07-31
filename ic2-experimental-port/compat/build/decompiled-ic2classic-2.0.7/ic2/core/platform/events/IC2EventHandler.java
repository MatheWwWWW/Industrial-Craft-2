/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.SoundOptionsScreen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 *  net.minecraft.nbt.TextComponentTagVisitor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.ClientPlayerNetworkEvent$LoggingIn
 *  net.minecraftforge.client.event.InputEvent$Key
 *  net.minecraftforge.client.event.InputEvent$MouseScrollingEvent
 *  net.minecraftforge.client.event.ScreenEvent$Init$Post
 *  net.minecraftforge.client.event.ScreenEvent$Opening
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 *  net.minecraftforge.event.OnDatapackSyncEvent
 *  net.minecraftforge.event.TagsUpdatedEvent
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$LevelTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.event.entity.living.LivingAttackEvent
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 *  net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent
 *  net.minecraftforge.event.entity.living.LivingFallEvent
 *  net.minecraftforge.event.entity.living.ShieldBlockEvent
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.event.entity.player.PlayerContainerEvent$Close
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerChangedDimensionEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedInEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickBlock
 *  net.minecraftforge.event.level.ChunkEvent$Load
 *  net.minecraftforge.event.level.ChunkEvent$Unload
 *  net.minecraftforge.event.level.LevelEvent$Save
 *  net.minecraftforge.event.level.LevelEvent$Unload
 *  net.minecraftforge.event.server.ServerStoppingEvent
 *  net.minecraftforge.eventbus.api.Event$Result
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.LogicalSide
 *  net.minecraftforge.server.ServerLifecycleHooks
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.core.Logger
 */
package ic2.core.platform.events;

import ic2.api.blocks.BlockRegistries;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.IFarmland;
import ic2.api.crops.ISubSoil;
import ic2.api.items.IUpgradeItem;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.tiles.FakePlayerMachine;
import ic2.api.tiles.tubes.IItemCache;
import ic2.core.IC2;
import ic2.core.audio.AudioManagerClient;
import ic2.core.block.cables.mointor.MonitorGrid;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.crops.PlanterPotBlock;
import ic2.core.block.machines.tiles.mv.BaseTeleporterTileEntity;
import ic2.core.block.misc.LightBlock;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.item.logic.TubeSyncManager;
import ic2.core.block.transport.item.logistic.TubeNet;
import ic2.core.energy.EnergyNetGlobal;
import ic2.core.inventory.base.IHasHeldSlotInventory;
import ic2.core.inventory.custom.FriendsHandler;
import ic2.core.inventory.custom.container.FriendsContainer;
import ic2.core.inventory.gui.IC2SoundGui;
import ic2.core.inventory.slot.UpgradeSlot;
import ic2.core.item.base.IC2Item;
import ic2.core.item.inv.SlotRegistry;
import ic2.core.item.renders.features.ToolBoxRenderer;
import ic2.core.item.wearable.base.IC2JetpackBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.item.wearable.base.IC2ShieldBase;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.OpenSlotPacket;
import ic2.core.networking.packets.client.PollContainerPacket;
import ic2.core.networking.packets.client.custom.ScrollPacket;
import ic2.core.networking.packets.debug.RequestServerData;
import ic2.core.networking.packets.server.gui.open.CloseCustomGuiPacket;
import ic2.core.platform.corehacks.mixins.client.SlotMixin;
import ic2.core.platform.events.ChunkSaver;
import ic2.core.platform.events.MultiBlockManager;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.player.PlayerHandler;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2People;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.wind.WindManager;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.profiler.ProfilerHelper;
import ic2.core.utils.tooltips.IAdvancedTooltip;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.ToolTipHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.function.IntConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TextComponentTagVisitor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class IC2EventHandler {
    public static final IC2EventHandler INSTANCE = new IC2EventHandler();
    static ILangHelper HELPER = new ILangHelper(){};
    public CompoundTag NETWORK_DATA = null;
    public static IntConsumer XP_ABSORBER;

    @SubscribeEvent
    public void onTagsUpdated(TagsUpdatedEvent event) {
        BlockRegistries.reload();
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onEntitySpawn(EntityJoinLevelEvent event) {
        Entity entity;
        if (XP_ABSORBER != null && (entity = event.getEntity()) instanceof ExperienceOrb) {
            ExperienceOrb xp = (ExperienceOrb)entity;
            XP_ABSORBER.accept(xp.m_20801_());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (player == null || event.getUseBlock() == Event.Result.DENY) {
            return;
        }
        if (player.m_6144_() && !player.m_21205_().m_41619_() && !player.m_21206_().m_41619_()) {
            boolean mainSneak = player.m_21205_().doesSneakBypassUse((LevelReader)event.getLevel(), event.getPos(), player);
            boolean secondSneak = player.m_21206_().doesSneakBypassUse((LevelReader)event.getLevel(), event.getPos(), player);
            if (!(!mainSneak && !secondSneak || mainSneak && secondSneak)) {
                event.setUseBlock(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        TubeSyncManager.onTick();
        IC2.NETWORKING.get().onServerTick();
        IC2.TICK_HANDLER.onServerTick(server);
        ProfilerHelper.INSTANCE.onTickEnded();
        CropRegistry.REGISTRY.tickSeed();
    }

    @SubscribeEvent
    public void onRecipesUpdated(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            IC2.RECIPES.get(true).sendRecipesToPlayer((Player)event.getPlayer());
        } else {
            BlockRegistries.reload();
            IC2.RECIPES.get(true).sendRecipes();
        }
    }

    @SubscribeEvent
    public void onServerStoppedEvent(ServerStoppingEvent event) {
        PlayerHandler.onServerStopped();
        IC2.NETWORKING.get().onServerStopped();
        IC2.TICK_HANDLER.onServerStopped();
        EnergyNetGlobal.onServerStopped();
        MonitorGrid.onServerStopped();
        FluidNet.onServerStopped();
        TubeNet.onServerStopped();
        TubeSyncManager.onServerStop();
        IItemCache.getCache().clearCache();
        BaseTeleporterTileEntity.ACTIVE_TELEPORTERS.clear();
        ProfilerHelper.INSTANCE.onServerStopped();
        MultiBlockManager.INSTANCE.onServerStopped();
    }

    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player)entity;
            ItemStack stack = player.m_6844_(EquipmentSlot.FEET);
            if (stack.m_41619_()) {
                return;
            }
            int fallDamage = (int)event.getDistance() - 3;
            if (fallDamage >= 8 && !this.absorbsFully(stack)) {
                if (this.damageItem(stack, fallDamage, player, event, true)) {
                    event.setDistance(Math.max(0.0f, event.getDistance() - 11.0f));
                }
                return;
            }
            this.damageItem(stack, fallDamage, player, event, false);
        }
    }

    private boolean absorbsFully(ItemStack stack) {
        return stack.m_41720_() instanceof IC2ModularElectricArmor && ((IC2ModularElectricArmor)stack.m_41720_()).isFullyAbsorbingFallDamage(stack);
    }

    public boolean damageItem(ItemStack stack, int fallDamage, Player player, LivingFallEvent evt, boolean simulate) {
        Item item = stack.m_41720_();
        if (item == IC2Items.HAZMAT_BOOTS) {
            int armorDamage = (fallDamage + 1) / 2;
            if (armorDamage <= stack.m_41776_() - stack.m_41773_() && armorDamage >= 0) {
                stack.m_41622_(armorDamage, (LivingEntity)player, IC2Item.get(EquipmentSlot.FEET));
                if (!simulate) {
                    evt.setCanceled(true);
                }
                return true;
            }
        } else if (item instanceof IC2ModularElectricArmor) {
            IC2ModularElectricArmor armor = (IC2ModularElectricArmor)item;
            int armorDamage = armor.getEnergyPerDamage(stack) * fallDamage;
            if (armorDamage - ElectricItem.MANAGER.discharge(stack, armorDamage, Integer.MAX_VALUE, true, false, simulate) == 0) {
                if (!simulate) {
                    evt.setCanceled(true);
                }
                return true;
            }
            if (armorDamage > 0) {
                float reduction = (float)armorDamage / (float)armor.getEnergyPerDamage(stack);
                if (!simulate) {
                    evt.setDistance(evt.getDistance() - reduction);
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) {
            return;
        }
        PlayerHandler handler = PlayerHandler.getHandler(event.player);
        if (handler.trackedTile != null) {
            IC2.NETWORKING.get(true).updateGuiData(handler.trackedTile, event.player);
        } else if (handler.hudModeKeyDown && handler.toggleKeyDown && !(event.player.f_36096_ instanceof FriendsContainer)) {
            IC2.PLATFORM.launchGui(event.player, new FriendsHandler());
        }
    }

    @SubscribeEvent
    public void onPlayerDeathEvent(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player && !event.isCanceled()) {
            CompoundTag nbt;
            Player player = (Player)event.getEntity();
            boolean hasFunkyJetpack = false;
            if (IC2.CURIO_PLUGIN != null && (nbt = IC2.CURIO_PLUGIN.getJetpack(player).m_41783_()) != null) {
                hasFunkyJetpack = nbt.m_128471_("SpecialMode");
            }
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.m_20743_() == EquipmentSlot.Type.HAND) continue;
                ItemStack stack = player.m_6844_(slot);
                CompoundTag nbt2 = stack.m_41783_();
                if (stack.m_41720_() instanceof IC2JetpackBase && nbt2 != null) {
                    hasFunkyJetpack |= nbt2.m_128471_("SpecialMode");
                    continue;
                }
                if (!(stack.m_41720_() instanceof IC2ModularElectricArmor) || nbt2 == null) continue;
                hasFunkyJetpack |= nbt2.m_128469_("jetpack_data").m_128471_("SpecialMode");
            }
            if (hasFunkyJetpack && player.getPersistentData().m_128471_("SpecialMovement")) {
                IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:jetpack_kill"));
                player.getPersistentData().m_128473_("SpecialMovement");
                player.getPersistentData().m_128473_("SpecialMovementTicker");
            }
        }
    }

    @SubscribeEvent
    public void onArmorHit(LivingAttackEvent event) {
        if (event.getSource() != DamageSource.f_19308_) {
            return;
        }
        LivingEntity living = event.getEntity();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack;
            if (slot.m_20743_() != EquipmentSlot.Type.ARMOR || !((stack = living.m_6844_(slot)).m_41720_() instanceof IArmorModule.IArmorModuleHolder) || IC2ModularElectricArmor.getValue(stack, IArmorModule.ArmorMod.NO_LAVA_DAMAGE, 0) <= 0) continue;
            event.setCanceled(true);
            return;
        }
    }

    @SubscribeEvent
    public void onDimensionSwitch(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        for (ItemStack stack : player.m_6168_()) {
            if (!(stack.m_41720_() instanceof IArmorModule.IArmorModuleHolder)) continue;
            ((IArmorModule.IArmorModuleHolder)stack.m_41720_()).onEquipmentStateChanged(stack, true, player);
        }
    }

    @SubscribeEvent
    public void onEquipmentChanged(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            ItemStack stack = event.getFrom();
            if (stack.m_41720_() instanceof IArmorModule.IArmorModuleHolder) {
                ((IArmorModule.IArmorModuleHolder)stack.m_41720_()).onEquipmentStateChanged(stack, false, player);
            }
            if ((stack = event.getTo()).m_41720_() instanceof IArmorModule.IArmorModuleHolder) {
                ((IArmorModule.IArmorModuleHolder)stack.m_41720_()).onEquipmentStateChanged(stack, true, player);
            }
        }
    }

    @SubscribeEvent
    public void onContainerClosed(PlayerContainerEvent.Close close) {
        PlayerHandler handler = PlayerHandler.getHandler(close.getEntity());
        handler.trackedTile = null;
        if (handler.customGui != null) {
            handler.customGui = null;
            PacketManager.INSTANCE.sendToPlayer(new CloseCustomGuiPacket(), close.getEntity());
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onClientGuiClosed(ScreenEvent.Opening gui) {
        PlayerHandler handler = PlayerHandler.getClientHandler();
        if (handler.cachedGUIs.size() > 0 && gui.getScreen() == null) {
            PacketManager.INSTANCE.sendToServer(new PollContainerPacket());
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();
        if (screen instanceof SoundOptionsScreen) {
            SoundOptionsScreen sound = (SoundOptionsScreen)screen;
            int i = 11;
            event.addListener((GuiEventListener)new ExtendedButton(sound.f_96543_ / 2 - 155 + i % 2 * 160, sound.f_96544_ / 6 - 22 + 24 * (i >> 1), 150, 20, (Component)Component.m_237113_((String)"IC2 Classic Audio"), T -> Minecraft.m_91087_().m_91152_((Screen)new IC2SoundGui((Screen)sound))));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent handler) {
        Player player;
        CompoundTag nbt;
        FriendManager.getServerFriends().sendDataToPlayer(handler.getEntity());
        ItemStack stack = new ItemStack((ItemLike)IC2Items.WIKI_ITEM);
        if (!stack.m_204117_(IC2Tags.WIKI_SPAWN) && !(nbt = (player = handler.getEntity()).getPersistentData().m_128469_("PlayerPersisted")).m_128471_("ic2_wiki") && player.m_150109_().m_36054_(stack)) {
            nbt.m_128379_("ic2_wiki", true);
            player.getPersistentData().m_128365_("PlayerPersisted", (Tag)nbt);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent handler) {
        PlayerHandler.onPlayerLoggedOut(handler.getEntity());
    }

    @SubscribeEvent
    public void onChunkLoaded(ChunkEvent.Load event) {
        ChunkAccess protoChunk = event.getChunk();
        if (protoChunk instanceof LevelChunk) {
            LevelChunk chunk = (LevelChunk)protoChunk;
            net.minecraft.world.level.Level world = chunk.m_62953_();
            if (!(world instanceof ServerLevel)) {
                return;
            }
            LightBlock.onChunkLoaded((ServerLevel)world, chunk.m_7697_());
        }
    }

    @SubscribeEvent
    public void onWorldTickEvent(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.CLIENT) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            EnergyNetGlobal.onWorldTickStart(event.level);
        } else {
            IC2.NETWORKING.get().onWorldTick(event.level);
            EnergyNetGlobal.onWorldTickStop(event.level);
            FluidNet.onTick(event.level);
            TubeNet.onTick(event.level);
            IC2.TICK_HANDLER.onWorldTick(event.level);
            WindManager.INSTANCE.onTick(event.level);
        }
    }

    @SubscribeEvent
    public void onWorldSave(LevelEvent.Save event) {
        net.minecraft.world.level.Level world = (net.minecraft.world.level.Level)event.getLevel();
        if (world.f_46443_) {
            return;
        }
        ChunkSaver.getSaver(world).onSave(world);
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        ChunkAccess chunkAccess = event.getChunk();
        if (chunkAccess instanceof LevelChunk) {
            LevelChunk level = (LevelChunk)chunkAccess;
            if (!level.m_62953_().f_46443_ && ChunkSaver.getSaver(level.m_62953_()).shouldSave(level.m_7697_())) {
                level.m_8092_(true);
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnload(LevelEvent.Unload event) {
        net.minecraft.world.level.Level world = (net.minecraft.world.level.Level)event.getLevel();
        if (world.f_46443_) {
            return;
        }
        IC2.NETWORKING.get().onWorldUnload(world);
        IC2.TICK_HANDLER.onWorldUnload(world);
        EnergyNetGlobal.onWorldUnload(world);
        TubeSyncManager.onWorldUnload(world);
        FluidNet.onWorldUnload(world);
        TubeNet.onWorldUnload(world);
        WindManager.INSTANCE.onWorldUnload(world);
        ChunkSaver.onWorldUnload(world);
        MonitorGrid.onWorldUnloaded(world);
        ProfilerHelper.INSTANCE.onWorldUnload(world);
    }

    public Slot findSlot(ItemStack stack, AbstractContainerMenu container) {
        for (Slot slot : container.f_38839_) {
            if (slot.m_7993_() != stack) continue;
            return slot;
        }
        return null;
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onEUToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Component tooltip = ElectricItem.MANAGER.getToolTip(stack);
        int start = 1;
        if (tooltip != null) {
            event.getToolTip().add(1, tooltip);
            ++start;
        }
        if (IC2.CONFIG.showUndamagedDurability.get() && event.getFlags().m_7050_() && stack.m_41763_() && !stack.m_41768_()) {
            event.getToolTip().add(event.getToolTip().size() - (stack.m_41782_() ? 2 : 1), Component.m_237110_((String)"tooltip.item.ic2.armor_durability", (Object[])new Object[]{stack.m_41776_() - stack.m_41773_(), stack.m_41776_()}).m_130940_(ChatFormatting.GRAY));
        }
        if (Screen.m_96637_() && stack.m_41782_() && IC2.CONFIG.itemNBT.get()) {
            Slot slot;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                NbtIo.m_128941_((CompoundTag)stack.m_41783_(), (DataOutput)new DataOutputStream(out));
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (out.size() > 10000) {
                event.getToolTip().add(Component.m_237113_((String)("NBTSize: " + out.size() / 1000 + " KB")).m_130940_(ChatFormatting.GOLD));
            } else {
                event.getToolTip().add(Component.m_237113_((String)("NBTSize: " + out.size() + " Bytes")).m_130940_(ChatFormatting.GOLD));
            }
            Logger logger = (Logger)LogManager.getLogger(CompoundTag.class);
            Level before = logger.getLevel();
            if (Screen.m_96638_()) {
                event.getToolTip().addAll(NBTUtils.convertNBT(stack.m_41783_()));
            } else {
                event.getToolTip().add(new TextComponentTagVisitor("", 0).m_178281_((Tag)stack.m_41783_()));
            }
            if (this.NETWORK_DATA != null) {
                event.getToolTip().add(Component.m_237113_((String)""));
                event.getToolTip().add(Component.m_237113_((String)"Server Data: "));
                if (Screen.m_96638_()) {
                    event.getToolTip().addAll(NBTUtils.convertNBT(this.NETWORK_DATA));
                } else {
                    event.getToolTip().add(new TextComponentTagVisitor("", 0).m_178281_((Tag)this.NETWORK_DATA));
                }
            }
            logger.setLevel(before);
            if (event.getEntity() != null && IC2People.isDevTeam(event.getEntity().m_20148_()) && (slot = this.findSlot(stack, event.getEntity().f_36096_)) != null && slot.f_40219_ >= 0) {
                PacketManager.INSTANCE.sendToServer(new RequestServerData(slot.f_40219_));
            }
        }
        if (Screen.m_96639_()) {
            stack.m_204131_().forEach(T -> event.getToolTip().add(Component.m_237113_((String)T.f_203868_().toString()).m_130940_(ChatFormatting.GRAY)));
        }
        ToolTipHelper helper = new ToolTipHelper(event.getToolTip());
        helper.setEnd(start);
        helper.setAddIndex(start);
        Block block = Block.m_49814_((Item)stack.m_41720_());
        if (stack.m_41720_() instanceof IAdvancedTooltip) {
            ((IAdvancedTooltip)stack.m_41720_()).addToolTip(stack, event.getEntity(), event.getFlags(), helper);
        } else if (block instanceof IAdvancedTooltip) {
            ((IAdvancedTooltip)block).addToolTip(stack, event.getEntity(), event.getFlags(), helper);
        }
        if (block != Blocks.f_50016_ && IC2.CONFIG.showCropInfo.get()) {
            ISubSoil soil;
            IFarmland farmland = ICropRegistry.INSTANCE.getFarmland(block);
            BlockState theDefault = farmland != null && farmland.isSpecial() ? farmland.getSpecialState(block) : block.m_49966_();
            boolean ignore = false;
            if (block instanceof PlanterPotBlock) {
                block = PlanterPotBlock.getId(stack);
                farmland = ICropRegistry.INSTANCE.getFarmland(block);
                theDefault = farmland.isSpecial() ? farmland.getSpecialState(block) : block.m_49966_();
                ignore = true;
            }
            if (farmland != null) {
                helper.addSimpleToolTip("info.crop.ic2.data.info.block.farmland", Component.m_237110_((String)"info.crop.ic2.data.qualities.humidity", (Object[])new Object[]{farmland.getHumidity(theDefault)}).m_130940_(ChatFormatting.AQUA), Component.m_237110_((String)"info.crop.ic2.data.qualities.nutrients", (Object[])new Object[]{farmland.getNutrients(theDefault)}).m_130940_(ChatFormatting.GREEN));
            }
            BlockState blockState = theDefault = (soil = ICropRegistry.INSTANCE.getSubSoil(block)) != null && soil.isSpecial() ? soil.getSpecialState(block) : block.m_49966_();
            if (soil != null && !ignore) {
                helper.addSimpleToolTip("info.crop.ic2.data.info.block.subsoil", Component.m_237110_((String)"info.crop.ic2.data.qualities.humidity", (Object[])new Object[]{soil.getHumidity(theDefault)}).m_130940_(ChatFormatting.AQUA), Component.m_237110_((String)"info.crop.ic2.data.qualities.nutrients", (Object[])new Object[]{soil.getNutrients(theDefault)}).m_130940_(ChatFormatting.GREEN));
            }
        }
        if (PlayerHandler.getClientHandler().hasEUReader()) {
            if (stack.m_41720_() instanceof IElectricItem) {
                item = (IElectricItem)stack.m_41720_();
                event.getToolTip().add(start++, HELPER.translate("misc.ic2.eu_transfer", item.getTransferLimit(stack)).m_130940_(ChatFormatting.GRAY));
                event.getToolTip().add(start++, HELPER.translate("misc.ic2.tier", item.getTier(stack)).m_130940_(ChatFormatting.GRAY));
                helper.setEnd(start);
                helper.setAddIndex(start);
            }
            if (stack.m_41720_() instanceof IUpgradeItem) {
                item = (IUpgradeItem)stack.m_41720_();
                ItemStack copy = stack.m_41777_();
                FakePlayerMachine machine = new FakePlayerMachine(event.getEntity());
                Screen screen = Minecraft.m_91087_().f_91080_;
                try {
                    IUpgradeItem.UpgradeType type = item.getType(copy);
                    if (type != IUpgradeItem.UpgradeType.REDSTONE_MOD && type != IUpgradeItem.UpgradeType.AUDIO_MOD) {
                        int size = Screen.m_96637_() ? copy.m_41613_() : 1;
                        int extraSpeed = 0;
                        double speedMultiplier = 1.0;
                        int extraTime = 0;
                        double timeMultiplier = 1.0;
                        int extraUsage = 0;
                        double usageMultiplier = 1.0;
                        int extraEnergy = 0;
                        double energyMultiplier = 1.0;
                        int extraTier = 0;
                        item.onInstall(copy, machine);
                        extraSpeed += item.getExtraProcessingSpeed(copy, machine) * size;
                        speedMultiplier *= Math.pow(item.getProcessingSpeedMultiplier(copy, machine), size);
                        extraTime += item.getExtraProcessingTime(copy, machine) * size;
                        timeMultiplier *= Math.pow(item.getProcessingTimeMultiplier(copy, machine), size);
                        extraUsage += item.getExtraEnergyDemand(copy, machine) * size;
                        usageMultiplier *= Math.pow(item.getEnergyDemandMultiplier(copy, machine), size);
                        extraEnergy += item.getExtraEnergyStorage(copy, machine) * size;
                        energyMultiplier *= Math.pow(item.getEnergyStorageMultiplier(copy, machine), size);
                        extraTier += item.getExtraTier(copy, machine) * size;
                        if (extraSpeed != 0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.speed.add", extraSpeed).m_130940_(ChatFormatting.GRAY));
                        }
                        if (speedMultiplier != 1.0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.speed.mul", ItemStack.f_41584_.format((speedMultiplier - 1.0) * 100.0)).m_130940_(ChatFormatting.GRAY));
                        }
                        if (extraTime != 0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.time.add", extraTime).m_130940_(ChatFormatting.GRAY));
                        }
                        if (timeMultiplier != 1.0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.time.mul", ItemStack.f_41584_.format((timeMultiplier - 1.0) * 100.0)).m_130940_(ChatFormatting.GRAY));
                        }
                        if (extraUsage != 0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.usage.add", extraUsage).m_130940_(ChatFormatting.GRAY));
                        }
                        if (usageMultiplier != 1.0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.usage.mul", ItemStack.f_41584_.format((usageMultiplier - 1.0) * 100.0)).m_130940_(ChatFormatting.GRAY));
                        }
                        if (extraEnergy != 0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.storage.add", extraEnergy).m_130940_(ChatFormatting.GRAY));
                        }
                        if (energyMultiplier != 1.0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.storage.mul", ItemStack.f_41584_.format((energyMultiplier - 1.0) * 100.0)).m_130940_(ChatFormatting.GRAY));
                        }
                        if (extraTier != 0) {
                            event.getToolTip().add(HELPER.translate("tooltip.item.ic2.upgrade.tier.add", (extraTier > 0 ? "+" : "") + extraTier).m_130940_(ChatFormatting.GRAY));
                        }
                        if (extraTier > 0 && screen instanceof AbstractContainerScreen && ((AbstractContainerScreen)screen).getSlotUnderMouse() instanceof UpgradeSlot) {
                            helper.addKeybindingTooltip(HELPER.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.ic2.release_upgrade", new Object[0]));
                        }
                    }
                }
                catch (Exception exception) {}
            }
        } else if (stack.m_41720_() instanceof IUpgradeItem) {
            item = (IUpgradeItem)stack.m_41720_();
            try {
                Screen screen;
                if (item.getExtraTier(stack, new FakePlayerMachine(event.getEntity())) > 0 && (screen = Minecraft.m_91087_().f_91080_) instanceof AbstractContainerScreen && ((AbstractContainerScreen)screen).getSlotUnderMouse() instanceof UpgradeSlot) {
                    helper.addKeybindingTooltip(HELPER.buildKeyDescription(KeyHelper.ALT_KEY, "tooltip.ic2.release_upgrade", new Object[0]));
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (stack.m_41720_() instanceof IHasHeldSlotInventory && ((IHasHeldSlotInventory)stack.m_41720_()).hasInventory(stack)) {
            helper.addKeybindingTooltip(HELPER.buildKeyDescription(KeyHelper.SIDE_INV_KEY, "misc.ic2.gui_open", new Object[0]));
        }
        helper.applyKeyInfo();
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            ((AudioManagerClient)IC2.AUDIO).onTick();
        } else {
            IC2.TICK_HANDLER.onClientTick();
            ((AudioManagerClient)IC2.AUDIO).onPostTick();
            IC2.KEYBOARD.sendKeyUpdate();
            IItemCache.getCache().updateCache();
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onKeyPress(InputEvent.Key event) {
        Screen screen;
        if (event.getKey() == IC2.KEYBOARD.getKey(KeyHelper.SIDE_INV_KEY) && event.getAction() == 1 && (screen = Minecraft.m_91087_().f_91080_) instanceof AbstractContainerScreen) {
            if (screen instanceof CreativeModeInventoryScreen) {
                AbstractContainerScreen container = (AbstractContainerScreen)screen;
                Slot slot = container.getSlotUnderMouse();
                if (slot != null && slot instanceof SlotMixin) {
                    PacketManager.INSTANCE.sendToServer(new OpenSlotPacket(-(slot.f_40219_ + 1)));
                }
                return;
            }
            AbstractContainerScreen container = (AbstractContainerScreen)screen;
            if (container.getSlotUnderMouse() != null) {
                Slot slot = container.getSlotUnderMouse();
                ItemStack stack = slot.m_7993_();
                if (!(stack.m_41720_() instanceof IHasHeldSlotInventory) || !((IHasHeldSlotInventory)stack.m_41720_()).hasInventory(stack) || !slot.m_5857_(stack) || SlotRegistry.INSTANCE.isInvalidSlot(container.m_6262_(), slot)) {
                    return;
                }
                PacketManager.INSTANCE.sendToServer(new OpenSlotPacket(slot.f_40219_));
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        IItemCache.getCache().clearCache();
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public void onScroll(InputEvent.MouseScrollingEvent event) {
        if (ToolBoxRenderer.INSTANCE.onScrollToolbox()) {
            event.setCanceled(true);
            PacketManager.INSTANCE.sendToServer(new ScrollPacket(event.getScrollDelta() > 0.0));
            return;
        }
    }

    public void onServerScroll(Player owner, boolean result) {
        if (ToolBoxRenderer.INSTANCE.onServerScrollToolbox(owner, result)) {
            return;
        }
    }

    @SubscribeEvent
    public void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack active = entity.m_21211_();
        Item item = active.m_41720_();
        if (item instanceof IC2ShieldBase) {
            IC2ShieldBase shield = (IC2ShieldBase)item;
            shield.damageShield(active, 1 + Mth.m_14143_((float)event.getBlockedDamage()), entity);
            event.setShieldTakesDamage(false);
        }
    }
}

