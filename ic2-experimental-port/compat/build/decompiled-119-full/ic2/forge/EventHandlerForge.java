/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 *  net.minecraftforge.event.TickEvent$LevelTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.TickEvent$ServerTickEvent
 *  net.minecraftforge.event.entity.living.LivingAttackEvent
 *  net.minecraftforge.event.entity.living.LivingFallEvent
 *  net.minecraftforge.event.entity.living.LivingSpawnEvent$SpecialSpawn
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$EntityInteract
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickEmpty
 *  net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent
 *  net.minecraftforge.event.level.BlockEvent$BreakEvent
 *  net.minecraftforge.event.level.ChunkDataEvent$Load
 *  net.minecraftforge.event.level.ChunkDataEvent$Save
 *  net.minecraftforge.event.level.ChunkEvent$Load
 *  net.minecraftforge.event.level.ChunkEvent$Unload
 *  net.minecraftforge.event.level.LevelEvent$Load
 *  net.minecraftforge.event.level.LevelEvent$Unload
 *  net.minecraftforge.event.server.ServerStartingEvent
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.IItemHandlerModifiable
 *  net.minecraftforge.items.wrapper.InvWrapper
 *  net.minecraftforge.items.wrapper.SidedInvWrapper
 */
package ic2.forge;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.event.RetextureEvent;
import ic2.api.tile.RetexturableBlock;
import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.event.EventHandler;
import ic2.core.event.TickHandler;
import ic2.core.fluid.FluidBeBridge;
import ic2.core.fluid.Ic2FluidBlock;
import ic2.core.fluid.Ic2FluidItem;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import ic2.forge.BlockFluidCapImpl;
import ic2.forge.EnvProxyForge;
import ic2.forge.ItemFluidCapImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public final class EventHandlerForge {
    private static final ResourceLocation fluidCapId = IC2.getIdentifier("fluid");
    private static final ResourceLocation itemCapId = IC2.getIdentifier("item");

    @SubscribeEvent
    public void serverStart(ServerStartingEvent serverStartingEvent) {
        EventHandler.onServerStart(serverStartingEvent.getServer());
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent playerLoggedOutEvent) {
        EventHandler.onPlayerLogout(playerLoggedOutEvent.getEntity());
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load load) {
        EventHandler.onWorldLoad((Level)load.getLevel());
    }

    @SubscribeEvent
    public void onWorldUnload(LevelEvent.Unload unload) {
        EventHandler.onWorldUnload((Level)unload.getLevel());
    }

    @SubscribeEvent
    public void onChunkDataLoad(ChunkDataEvent.Load load) {
        ChunkAccess chunkAccess = load.getChunk();
        if (chunkAccess instanceof LevelChunk) {
            EventHandler.onChunkDataLoad((LevelChunk)chunkAccess, load.getData());
        }
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save save) {
        ChunkAccess chunkAccess = save.getChunk();
        if (chunkAccess instanceof LevelChunk) {
            EventHandler.onChunkSave((LevelChunk)chunkAccess, save.getData());
        }
    }

    public void onChunkLoad(ChunkEvent.Load load) {
        ChunkAccess chunkAccess = load.getChunk();
        if (chunkAccess instanceof LevelChunk) {
            EventHandler.onChunkLoad((LevelChunk)chunkAccess);
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onChunkUnload(ChunkEvent.Unload unload) {
        ChunkAccess chunkAccess = unload.getChunk();
        if (chunkAccess instanceof LevelChunk) {
            EventHandler.onChunkUnload((LevelChunk)chunkAccess);
        }
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.LevelTickEvent levelTickEvent) {
        Level level = levelTickEvent.level;
        if (levelTickEvent.phase == TickEvent.Phase.START) {
            TickHandler.onWorldTickStart(level);
        } else {
            TickHandler.onWorldTickEnd(level);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent serverTickEvent) {
        if (serverTickEvent.phase == TickEvent.Phase.START) {
            TickHandler.onServerTick();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent) {
        if (playerTickEvent.phase == TickEvent.Phase.START) {
            EventHandler.onPlayerTick(playerTickEvent.player);
        }
    }

    @SubscribeEvent
    public void onLivingSpecialSpawn(LivingSpawnEvent.SpecialSpawn specialSpawn) {
        EventHandler.onLivingSpecialSpawn((LivingEntity)specialSpawn.getEntity());
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void onLivingFall(LivingFallEvent livingFallEvent) {
        if (EventHandler.onLivingFall(livingFallEvent.getEntity(), livingFallEvent.getDistance())) {
            livingFallEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty leftClickEmpty) {
        if (EventHandler.onEntitySwingHand((LivingEntity)leftClickEmpty.getEntity(), leftClickEmpty.getHand())) {
            leftClickEmpty.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock leftClickBlock) {
        if (EventHandler.onEntitySwingHand((LivingEntity)leftClickBlock.getEntity(), leftClickBlock.getHand())) {
            leftClickBlock.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract entityInteract) {
        if (EventHandler.onEntityInteract(entityInteract.getEntity(), entityInteract.getHand(), entityInteract.getTarget())) {
            entityInteract.setCanceled(true);
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
    public void onEntityAttacked(LivingAttackEvent livingAttackEvent) {
        LivingEntity livingEntity = livingAttackEvent.getEntity();
        if (!EventHandler.onEntityAttacked(livingEntity, livingAttackEvent.getSource(), livingAttackEvent.getAmount())) {
            livingAttackEvent.setCanceled(true);
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
    public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
        if (!EventHandler.onAttackEntity(attackEntityEvent.getEntity(), attackEntityEvent.getTarget())) {
            attackEntityEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onBlockStartBreak(PlayerInteractEvent.LeftClickBlock leftClickBlock) {
        if (EventHandler.onBlockStartBreak(leftClickBlock.getEntity(), leftClickBlock.getLevel(), leftClickBlock.getHand(), leftClickBlock.getPos(), leftClickBlock.getFace()) == InteractionResult.FAIL) {
            leftClickBlock.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void beforeBlockBreak(BlockEvent.BreakEvent breakEvent) {
        Level level = (Level)breakEvent.getLevel();
        BlockPos blockPos = breakEvent.getPos();
        BlockEntity blockEntity = level.m_7702_(blockPos);
        if (!EventHandler.beforeBlockBreak(level, breakEvent.getPlayer(), blockPos, breakEvent.getState(), blockEntity)) {
            breakEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onGetBurnTime(FurnaceFuelBurnTimeEvent furnaceFuelBurnTimeEvent) {
        Item item = furnaceFuelBurnTimeEvent.getItemStack().m_41720_();
        if (EnvProxyForge.burnTimeRecord.containsKey(item)) {
            furnaceFuelBurnTimeEvent.setBurnTime(EnvProxyForge.burnTimeRecord.get(item).intValue());
        }
    }

    @SubscribeEvent
    public void onRetexture(RetextureEvent retextureEvent) {
        Block block = retextureEvent.state.m_60734_();
        if (block instanceof RetexturableBlock && ((RetexturableBlock)block).retexture(retextureEvent.state, (Level)retextureEvent.getLevel(), retextureEvent.pos, retextureEvent.side, retextureEvent.player, retextureEvent.refState, retextureEvent.refVariant, retextureEvent.refSide, retextureEvent.refColorMultipliers)) {
            retextureEvent.applied = true;
            retextureEvent.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEnergyTileLoad(EnergyTileLoadEvent energyTileLoadEvent) {
        if (energyTileLoadEvent.getLevel().m_5776_()) {
            IC2.log.warn(LogCategory.EnergyNet, "EnergyTileLoadEvent: posted for %s client-side, aborting", Util.toString(energyTileLoadEvent.tile, (BlockGetter)energyTileLoadEvent.getLevel(), EnergyNet.instance.getPos(energyTileLoadEvent.tile)));
            return;
        }
        EnergyNet.instance.addTileUnchecked(energyTileLoadEvent.tile);
    }

    @SubscribeEvent
    public void onEnergyTileUnload(EnergyTileUnloadEvent energyTileUnloadEvent) {
        if (energyTileUnloadEvent.getLevel().m_5776_()) {
            IC2.log.warn(LogCategory.EnergyNet, "EnergyTileUnloadEvent: posted for %s client-side, aborting", Util.toString(energyTileUnloadEvent.tile, (BlockGetter)energyTileUnloadEvent.getLevel(), EnergyNet.instance.getPos(energyTileUnloadEvent.tile)));
            return;
        }
        EnergyNet.instance.removeTile(energyTileUnloadEvent.tile);
    }

    @SubscribeEvent
    public void onAttachBlockEntityCapabilities(AttachCapabilitiesEvent<BlockEntity> attachCapabilitiesEvent) {
        Ic2FluidBlock ic2FluidBlock;
        final BlockEntity blockEntity = (BlockEntity)attachCapabilitiesEvent.getObject();
        if (!(blockEntity instanceof Ic2TileEntity)) {
            return;
        }
        if (blockEntity instanceof FluidBeBridge && (ic2FluidBlock = ((FluidBeBridge)blockEntity).getFluidBlock()) != null && ic2FluidBlock.isFluidBlock(null, null, null, blockEntity)) {
            attachCapabilitiesEvent.addCapability(fluidCapId, (ICapabilityProvider)new BlockFluidCapImpl(ic2FluidBlock, blockEntity));
        }
        if (blockEntity instanceof WorldlyContainer) {
            attachCapabilitiesEvent.addCapability(itemCapId, new ICapabilityProvider(){
                private final LazyOptional<IItemHandlerModifiable>[] caps;
                {
                    this.caps = SidedInvWrapper.create((WorldlyContainer)((WorldlyContainer)blockEntity), (Direction[])Util.ALL_DIRS);
                }

                public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
                    return direction != null && capability == ForgeCapabilities.ITEM_HANDLER ? this.caps[direction.ordinal()] : LazyOptional.empty();
                }
            });
        } else if (blockEntity instanceof Container) {
            attachCapabilitiesEvent.addCapability(itemCapId, new ICapabilityProvider(){
                private final LazyOptional<IItemHandler> cap = LazyOptional.of(() -> new InvWrapper((Container)blockEntity));

                public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
                    return capability == ForgeCapabilities.ITEM_HANDLER ? this.cap : LazyOptional.empty();
                }
            });
        }
    }

    @SubscribeEvent
    public void onAttachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> attachCapabilitiesEvent) {
        ItemStack itemStack = (ItemStack)attachCapabilitiesEvent.getObject();
        Item item = itemStack.m_41720_();
        if (item instanceof Ic2FluidItem) {
            attachCapabilitiesEvent.addCapability(fluidCapId, (ICapabilityProvider)new ItemFluidCapImpl(itemStack));
        }
    }
}

