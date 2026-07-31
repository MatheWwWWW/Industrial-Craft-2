/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.SpawnerBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.EmptyFluidHandler
 */
package ic2.core.block.machines.tiles.hv;

import com.mojang.math.Vector3f;
import ic2.api.tiles.IEnergyStorage;
import ic2.api.tiles.teleporter.ITeleporterTarget;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.InterfaceCache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.filter.SetItemFilter;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.TeleportUtil;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;

public class TeleporterTileEntity
extends BaseTileEntity
implements ITickListener,
ITeleporterTarget,
IWrenchableTile {
    public TeleporterTarget target = null;
    private int delay = 0;
    private int activeDelay = 0;
    boolean blockChange = false;
    EnumSet<ITeleporterTarget.TeleportType> types = EnumSet.noneOf(ITeleporterTarget.TeleportType.class);
    InterfaceCache<IEnergyStorage> powerSources = new InterfaceCache<IEnergyStorage>(this, DirectionList.ALL, IEnergyStorage.class);

    public TeleporterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.powerSources);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TELEPORTER;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return this.getFacing() != dir;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.7;
    }

    @Override
    public void onTick() {
        if (this.blockChange) {
            this.blockChange = false;
            this.updateTargetTypes();
        }
        if (this.target == null) {
            this.setActive(false);
            return;
        }
        if (this.activeDelay > 0) {
            --this.activeDelay;
            if (this.activeDelay == 0) {
                this.setActive(false);
            }
        }
        this.handleRedstone();
        if (!this.isRedstonePowered()) {
            if (this.delay > 0) {
                --this.delay;
            }
            return;
        }
        if (this.delay > 0) {
            return;
        }
        ServerLevel world = this.target.getWorld();
        if (world == null) {
            this.target = null;
            this.setActive(false);
            return;
        }
        this.delay = 20;
        this.activeDelay = 10;
        boolean result = this.teleport(world);
        if (result) {
            IC2.AUDIO.playSound(this, new ResourceLocation("ic2", "sounds/machines/teleport.ogg"));
        }
        this.setActive(result);
    }

    protected boolean teleport(ServerLevel world) {
        switch (this.getSendType()) {
            case ENERGY: {
                return this.handleEnergy(world);
            }
            case ENTITY: {
                return this.handleEntity(world);
            }
            case FLUID: {
                return this.handleFluids(world);
            }
            case ITEM: {
                return this.handleItems(world);
            }
            case SPAWNER: {
                return this.handleSpawner(world);
            }
        }
        return false;
    }

    protected boolean handleSpawner(ServerLevel targetWorld) {
        SpawnerBlockEntity source = DirectionList.getNeighborInterface(this, this.getFacing(), SpawnerBlockEntity.class);
        if (source == null) {
            return false;
        }
        BlockEntity targetTile = targetWorld.m_7702_(this.target.getTargetPosition());
        if (!(targetTile instanceof ITeleporterTarget)) {
            this.target = null;
            return false;
        }
        ITeleporterTarget target = (ITeleporterTarget)targetTile;
        if (!target.canReceive(ITeleporterTarget.TeleportType.SPAWNER)) {
            return false;
        }
        BlockPos targetPos = target.getPosition().m_121945_(target.getFacing());
        if (!targetWorld.m_46859_(targetPos)) {
            return false;
        }
        double distanceCost = this.getBaseCost(targetWorld) * 25000.0;
        if ((double)this.getAvailableEnergy() < distanceCost) {
            return false;
        }
        this.consumeEnergy((int)distanceCost);
        targetWorld.m_46597_(targetPos, Blocks.f_50085_.m_49966_());
        SpawnerBlockEntity spawner = DirectionList.getNeighborInterface(targetTile, target.getFacing(), SpawnerBlockEntity.class);
        if (spawner == null) {
            return false;
        }
        spawner.m_59801_().m_151328_((Level)targetWorld, targetPos, source.m_59801_().m_186381_(new CompoundTag()));
        this.f_58857_.m_46597_(this.f_58858_.m_121945_(this.getFacing()), Blocks.f_50016_.m_49966_());
        return true;
    }

    protected boolean handleEnergy(ServerLevel targetWorld) {
        IEnergyStorage source = DirectionList.getNeighborInterface(this, this.getFacing(), IEnergyStorage.class);
        if (source == null) {
            return false;
        }
        BlockEntity targetTile = targetWorld.m_7702_(this.target.getTargetPosition());
        if (!(targetTile instanceof ITeleporterTarget)) {
            this.target = null;
            return false;
        }
        ITeleporterTarget target = (ITeleporterTarget)targetTile;
        if (!target.canReceive(ITeleporterTarget.TeleportType.ENERGY)) {
            return false;
        }
        IEnergyStorage receiver = DirectionList.getNeighborInterface(targetTile, target.getFacing(), IEnergyStorage.class);
        if (receiver == null) {
            return false;
        }
        double distanceCost = this.getBaseCost(targetWorld);
        if ((double)this.getAvailableEnergy() < distanceCost) {
            return false;
        }
        this.consumeEnergy((int)distanceCost);
        int provided = source.getStoredEU();
        if (provided <= 0) {
            return false;
        }
        source.drawEnergy(receiver.addEnergy(provided));
        return true;
    }

    protected boolean handleFluids(ServerLevel targetWorld) {
        LazyOptional lazySource = DirectionList.getNeighborCapability(this, this.getFacing(), ForgeCapabilities.FLUID_HANDLER);
        if (!lazySource.isPresent()) {
            return false;
        }
        BlockEntity targetTile = targetWorld.m_7702_(this.target.getTargetPosition());
        if (!(targetTile instanceof ITeleporterTarget)) {
            this.target = null;
            return false;
        }
        ITeleporterTarget target = (ITeleporterTarget)targetTile;
        if (!target.canReceive(ITeleporterTarget.TeleportType.FLUID)) {
            return false;
        }
        LazyOptional lazyReceiver = DirectionList.getNeighborCapability(targetTile, target.getFacing(), ForgeCapabilities.FLUID_HANDLER);
        if (!lazyReceiver.isPresent()) {
            return false;
        }
        double distanceCost = this.getBaseCost(targetWorld);
        int availableWeight = (int)((double)this.getAvailableEnergy() / distanceCost);
        if (availableWeight <= 0) {
            return false;
        }
        int usedWeight = 0;
        int tankIndex = 0;
        IFluidHandler source = (IFluidHandler)lazySource.orElse((Object)EmptyFluidHandler.INSTANCE);
        IFluidHandler sink = (IFluidHandler)lazyReceiver.orElse((Object)EmptyFluidHandler.INSTANCE);
        while (availableWeight > usedWeight) {
            FluidStack stack = source.drain(1000, IFluidHandler.FluidAction.SIMULATE);
            if (stack.isEmpty()) {
                if (++tankIndex < source.getTanks()) continue;
                break;
            }
            int weight = TeleportUtil.getWeightOfFluid(stack);
            if (weight + usedWeight > availableWeight) break;
            stack.setAmount(sink.fill(stack, IFluidHandler.FluidAction.EXECUTE));
            if (stack.getAmount() <= 0) continue;
            usedWeight += TeleportUtil.getWeightOfFluid(stack);
            source.drain(stack, IFluidHandler.FluidAction.EXECUTE);
        }
        this.consumeEnergy((int)((double)usedWeight * distanceCost));
        return usedWeight > 0;
    }

    protected boolean handleItems(ServerLevel targetWorld) {
        int weight;
        ItemStack stack;
        IItemTransporter source = TransporterManager.getTransporter(DirectionList.getNeighborTile(this, this.getFacing()));
        if (source == null) {
            return false;
        }
        BlockEntity tile = targetWorld.m_7702_(this.target.getTargetPosition());
        if (!(tile instanceof ITeleporterTarget)) {
            this.target = null;
            return false;
        }
        ITeleporterTarget target = (ITeleporterTarget)tile;
        if (!target.canReceive(ITeleporterTarget.TeleportType.ITEM)) {
            return false;
        }
        IItemTransporter receiver = TransporterManager.getTransporter(DirectionList.getNeighborTile(tile, target.getFacing()));
        if (receiver == null) {
            return false;
        }
        double distanceCost = this.getBaseCost(targetWorld);
        int availableWeight = (int)((double)this.getAvailableEnergy() / distanceCost);
        if (availableWeight <= 0) {
            return false;
        }
        int usedWeight = 0;
        ObjectSet itemFilter = CollectionUtils.createSet();
        SetItemFilter filter = new SetItemFilter((Set<Item>)itemFilter, false);
        while (availableWeight > usedWeight && !(stack = source.removeItem(filter, this.getFacing().m_122424_(), 64, true)).m_41619_() && (weight = TeleportUtil.getWeightOfItem(stack)) + usedWeight <= availableWeight) {
            int added = receiver.addItem(stack, target.getFacing().m_122424_(), false);
            if (added <= 0) {
                itemFilter.add((Item)stack.m_41720_());
                continue;
            }
            stack.m_41764_(added);
            source.removeItem(StackFilter.defaultCompare(stack), this.getFacing().m_122424_(), 64, false);
            usedWeight += TeleportUtil.getWeightOfItem(stack);
        }
        this.consumeEnergy((int)((double)usedWeight * distanceCost));
        return usedWeight > 0;
    }

    protected boolean handleEntity(ServerLevel targetWorld) {
        Object entity = AABBUtil.getClosestEntity(this.f_58857_.m_45976_(Entity.class, new AABB(this.m_58899_()).m_82377_(2.0, 3.0, 2.0)), Vec3.m_82528_((Vec3i)this.m_58899_().m_121945_(this.getFacing()).m_7637_(0.5, 0.5, 0.5)));
        if (entity == null) {
            return false;
        }
        BlockEntity tile = targetWorld.m_7702_(this.target.getTargetPosition());
        if (!(tile instanceof ITeleporterTarget)) {
            this.target = null;
            return false;
        }
        ITeleporterTarget type = (ITeleporterTarget)tile;
        if (!type.canReceive(ITeleporterTarget.TeleportType.ENTITY)) {
            return false;
        }
        boolean keepItems = IC2.CONFIG.teleporterKeepItems.get();
        int weight = TeleportUtil.getWeightOfEntity(entity, keepItems);
        if (weight == 0) {
            return false;
        }
        int energyCost = (int)((double)weight * this.getBaseCost(targetWorld) * 5.0);
        if ((long)energyCost > this.getAvailableEnergy()) {
            return false;
        }
        this.consumeEnergy(energyCost);
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (!keepItems) {
                player.m_150109_().m_36071_();
            }
            double distance = Math.sqrt(this.m_58899_().m_123331_((Vec3i)this.target.getTargetPosition()));
            player.m_36222_(IC2Stats.DISTANCE_TELEPORTED, (int)distance * 1000);
            if (distance >= 1000.0) {
                IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:teleport_far"));
            }
        }
        if (tile instanceof TeleporterTileEntity) {
            ((TeleporterTileEntity)tile).delay = 20;
        }
        AABB axis = entity.m_20191_();
        ((ServerLevel)this.f_58857_).m_8767_((ParticleOptions)new DustParticleOptions(new Vector3f(ColorUtils.rgbFloat((int)(this.getPosition().m_121878_() ^ this.f_58857_.m_46467_()))), 1.0f), axis.m_82340_(Direction.Axis.X) + axis.m_82362_() * 0.5, axis.m_82340_(Direction.Axis.Y) + axis.m_82376_() * 0.5, axis.m_82340_(Direction.Axis.Z) + axis.m_82385_() * 0.5, 50, axis.m_82362_() * 0.5, axis.m_82376_() * 0.5, axis.m_82385_() * 0.5, 0.0);
        TeleportUtil.teleportEntity(entity, targetWorld, this.target.getTargetPosition(), ((ITeleporterTarget)tile).getFacing());
        axis = entity.m_20191_();
        targetWorld.m_8767_((ParticleOptions)new DustParticleOptions(new Vector3f(ColorUtils.rgbFloat((int)(this.getPosition().m_121878_() ^ this.f_58857_.m_46467_()))), 1.0f), axis.m_82340_(Direction.Axis.X) + axis.m_82362_() * 0.5, axis.m_82340_(Direction.Axis.Y) + axis.m_82376_() * 0.5, axis.m_82340_(Direction.Axis.Z) + axis.m_82385_() * 0.5, 50, axis.m_82362_() * 0.5, axis.m_82376_() * 0.25, axis.m_82385_() * 0.5, 0.0);
        return true;
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        this.blockChange = true;
    }

    protected void updateTargetTypes() {
        this.types.clear();
        BlockEntity tile = DirectionList.getNeighborTile(this, this.getFacing());
        if (tile == null) {
            this.types.add(ITeleporterTarget.TeleportType.ENTITY);
            this.types.add(ITeleporterTarget.TeleportType.SPAWNER);
            return;
        }
        if (tile instanceof Container || tile.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
            this.types.add(ITeleporterTarget.TeleportType.ITEM);
        }
        if (tile.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
            this.types.add(ITeleporterTarget.TeleportType.FLUID);
        }
        if (tile instanceof IEnergyStorage) {
            this.types.add(ITeleporterTarget.TeleportType.ENERGY);
        }
        if (tile instanceof SpawnerBlockEntity) {
            this.types.add(ITeleporterTarget.TeleportType.NOTHING);
        }
        if (this.types.isEmpty()) {
            this.types.add(ITeleporterTarget.TeleportType.ENTITY);
        }
    }

    public boolean hasMatchingType() {
        if (this.target == null || this.target.getWorld() == null) {
            return false;
        }
        BlockEntity tile = this.target.getTile();
        if (!(tile instanceof ITeleporterTarget)) {
            return false;
        }
        return ((ITeleporterTarget)tile).getSendType().matches(this.getSendType());
    }

    public double getBaseCost(ServerLevel target) {
        return TeleportUtil.getDistanceCost(this.f_58857_, this.m_58899_(), (Level)target, this.target.getTargetPosition());
    }

    public int getBaseCost() {
        if (this.target == null) {
            return 0;
        }
        ServerLevel server = this.target.getWorld();
        return server == null ? 0 : (int)TeleportUtil.getDistanceCost(this.f_58857_, this.m_58899_(), (Level)server, this.target.getTargetPosition());
    }

    public long getAvailableEnergy() {
        long stored = 0L;
        for (Direction dir : this.powerSources.getPresentSides().remove(this.getFacing())) {
            IEnergyStorage storage = this.powerSources.getHandler(dir);
            if (storage == null) continue;
            stored += (long)storage.getStoredEU();
        }
        return stored;
    }

    public long getMaxEnergy() {
        long stored = 0L;
        for (Direction dir : this.powerSources.getPresentSides().remove(this.getFacing())) {
            IEnergyStorage storage = this.powerSources.getHandler(dir);
            if (storage == null) continue;
            stored += (long)storage.getMaxEU();
        }
        return stored;
    }

    public void consumeEnergy(int energy) {
        PriorityQueue queue = CollectionUtils.createInsertionQueue();
        for (Direction dir : this.powerSources.getPresentSides().remove(this.getFacing())) {
            IEnergyStorage storage = this.powerSources.getHandler(dir);
            if (storage == null || storage.getStoredEU() <= 0) continue;
            queue.enqueue((Object)storage);
        }
        while (energy > 0) {
            int drain = (energy + queue.size() - 1) / queue.size();
            int m = queue.size();
            for (int i = 0; i < m && energy > 0; ++i) {
                IEnergyStorage storage = (IEnergyStorage)queue.dequeue();
                drain = Math.min(drain, energy);
                energy -= storage.drawEnergy(drain);
                if (storage.getStoredEU() <= 0) continue;
                queue.enqueue((Object)storage);
            }
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.target = TeleporterTarget.read(compound.m_128469_("target"));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.target != null) {
            compound.m_128365_("target", (Tag)this.target.write(new CompoundTag()));
        }
    }

    @Override
    public ITeleporterTarget.TeleportType getSendType() {
        if (this.target == null) {
            return ITeleporterTarget.TeleportType.NOTHING;
        }
        BlockEntity tile = DirectionList.getNeighborTile(this, this.getFacing());
        if (tile != null) {
            if (tile instanceof IEnergyStorage) {
                return ITeleporterTarget.TeleportType.ENERGY;
            }
            if (tile.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
                return ITeleporterTarget.TeleportType.FLUID;
            }
            if (tile.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                return ITeleporterTarget.TeleportType.ITEM;
            }
            if (tile instanceof SpawnerBlockEntity) {
                return ITeleporterTarget.TeleportType.SPAWNER;
            }
        }
        return ITeleporterTarget.TeleportType.ENTITY;
    }

    public ITeleporterTarget.TeleportType getProbeSendType() {
        BlockEntity tile = DirectionList.getNeighborTile(this, this.getFacing());
        if (tile != null) {
            if (tile instanceof IEnergyStorage) {
                return ITeleporterTarget.TeleportType.ENERGY;
            }
            if (tile.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
                return ITeleporterTarget.TeleportType.FLUID;
            }
            if (tile.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                return ITeleporterTarget.TeleportType.ITEM;
            }
            if (tile instanceof SpawnerBlockEntity) {
                return ITeleporterTarget.TeleportType.SPAWNER;
            }
        }
        return ITeleporterTarget.TeleportType.ENTITY;
    }

    @Override
    public boolean canReceive(ITeleporterTarget.TeleportType type) {
        if (this.types.isEmpty()) {
            this.updateTargetTypes();
        }
        return this.types.contains((Object)type);
    }

    @Override
    public boolean setTarget(TeleporterTarget target) {
        if (this.target == null || !this.target.equals(target)) {
            this.target = new TeleporterTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasTarget(TeleporterTarget target) {
        return this.target != null && this.target.equals(target);
    }
}

