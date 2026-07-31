/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.tiles.luv;

import com.google.common.base.Strings;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.api.tiles.teleporter.ITeleporterTarget;
import ic2.api.tiles.teleporter.TargetRegistry;
import ic2.api.tiles.teleporter.TeleporterTarget;
import ic2.core.IC2;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.luv.TeleporterHubContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.registries.IC2Advancements;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.TeleportUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TeleporterHubTileEntity
extends BaseElectricTileEntity
implements ITeleporterTarget,
INetworkDataEventListener,
ITileGui,
ITileActivityProvider {
    public static final AtomicInteger INDEX = new AtomicInteger();
    @NetworkInfo
    public TargetStorage targets = new TargetStorage();

    public TeleporterHubTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0, 32768, 100000000);
        this.addGuiFields("targets");
    }

    @Override
    public boolean allowsUI() {
        return false;
    }

    @Override
    public boolean isAllowingUI() {
        return false;
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TELEPORTER_HUB;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new TeleporterHubContainer(this, player, windowID);
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        if (target.isDedicatedServer() && data instanceof NBTBuffer && player != null) {
            NBTBuffer buffer = (NBTBuffer)data;
            if (id.equalsIgnoreCase("internal_rename")) {
                this.setCustomName((Component)Component.m_237113_((String)buffer.getNBT().m_128461_("newName")));
                return;
            }
            TeleporterTarget destination = TeleporterTarget.read(buffer.getNBT());
            if (destination == null || !this.targets.contains(destination)) {
                return;
            }
            if (id.equalsIgnoreCase("delete") && this.targets.removeTarget(destination)) {
                this.updateGuiField("targets");
            } else if (id.equalsIgnoreCase("teleport")) {
                player.m_6915_();
                this.teleport(player, destination);
            } else if (id.equalsIgnoreCase("rename")) {
                this.targets.setCustomName(destination, buffer.getNBT().m_128461_("newName"));
                this.updateGuiField("targets");
            }
        }
    }

    protected void teleport(Player player, TeleporterTarget target) {
        BlockEntity tile = target.getTile();
        if (!(tile instanceof ITeleporterTarget)) {
            this.targets.removeTarget(target);
            this.updateGuiField("targets");
            return;
        }
        this.targets.updateName(target);
        this.updateGuiField("targets");
        int cost = this.getCost(target, player);
        if (cost <= 0 || !this.hasEnergy(cost)) {
            return;
        }
        this.useEnergy(cost);
        int distance = (int)Math.sqrt(this.m_58899_().m_123331_((Vec3i)target.getTargetPosition()));
        player.m_36222_(IC2Stats.DISTANCE_TELEPORTED, distance * 1000);
        if (distance >= 1000) {
            IC2Advancements.ID_TRIGGER.onTrigger(player, new ResourceLocation("ic2:teleport_far"));
        }
        TeleportUtil.teleportEntity((Entity)player, target.getWorld(), target.getTargetPosition(), ((ITeleporterTarget)tile).getFacing());
        IC2.AUDIO.playSound(player, new ResourceLocation("ic2", "sounds/machines/teleport.ogg"));
    }

    public int getCost(TeleporterTarget target, Player player) {
        int weight = TeleportUtil.getWeightOfEntity((Entity)player, true);
        if (weight == 0) {
            return 0;
        }
        double cost = this.isRendering() ? TeleportUtil.getDistanceCost((ResourceKey<Level>)this.f_58857_.m_46472_(), this.f_58858_, target.getDimension(), target.getTargetPosition()) : TeleportUtil.getDistanceCost(this.f_58857_, this.f_58858_, (Level)target.getWorld(), target.getTargetPosition());
        return (int)((double)weight * cost * 5.0);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.isSimulating() && this.m_8077_()) {
            String name = this.m_7770_().getString();
            if (Strings.isNullOrEmpty((String)name)) {
                TargetRegistry.INSTANCE.removeTarget(new TeleporterTarget(this));
            } else {
                TargetRegistry.INSTANCE.addTarget(new TeleporterTarget(this), name);
            }
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating()) {
            TargetRegistry.INSTANCE.removeTarget(new TeleporterTarget(this));
        }
        super.onUnloaded(chunk);
    }

    @Override
    public Component setCustomName(Component comp) {
        Component result = super.setCustomName(comp);
        if (this.isSimulating() && this.m_8077_()) {
            String name = this.m_7770_().getString();
            if (Strings.isNullOrEmpty((String)name)) {
                TargetRegistry.INSTANCE.removeTarget(new TeleporterTarget(this));
            } else {
                TargetRegistry.INSTANCE.addTarget(new TeleporterTarget(this), name);
            }
        }
        return result;
    }

    @Override
    public ITeleporterTarget.TeleportType getSendType() {
        return ITeleporterTarget.TeleportType.ENTITY;
    }

    @Override
    public boolean canReceive(ITeleporterTarget.TeleportType type) {
        return type == ITeleporterTarget.TeleportType.ENTITY;
    }

    @Override
    public boolean setTarget(TeleporterTarget target) {
        this.targets.put(target, "Target " + INDEX.getAndIncrement());
        this.updateGuiField("targets");
        this.setActive(this.targets.size() > 0);
        return true;
    }

    @Override
    public boolean hasTarget(TeleporterTarget target) {
        return this.targets.contains(target);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("targets", (Tag)this.targets.write(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.targets.read(compound.m_128469_("targets"));
    }

    public static class TargetStorage
    implements INetworkDataBuffer {
        Object2ObjectMap<TeleporterTarget, String> targets = CollectionUtils.createLinkedMap();
        Set<TeleporterTarget> custom = CollectionUtils.createSet();
        List<Tuple<TeleporterTarget, String>> clientTargets = CollectionUtils.createList();

        public int size() {
            return this.targets.size();
        }

        public boolean contains(TeleporterTarget target) {
            return this.targets.containsKey((Object)target);
        }

        public void put(TeleporterTarget target, String defaultName) {
            String name = TargetRegistry.INSTANCE.getTargetName(target);
            this.targets.put((Object)target, (Object)(name != null ? name : defaultName));
        }

        public void updateName(TeleporterTarget target) {
            String name;
            if (this.targets.containsKey((Object)target) && !this.custom.contains(target) && (name = TargetRegistry.INSTANCE.getTargetName(target)) != null) {
                this.targets.put((Object)target, (Object)name);
            }
        }

        public void setCustomName(TeleporterTarget target, String name) {
            if (this.targets.containsKey((Object)target)) {
                this.custom.add(target);
                this.targets.put((Object)target, (Object)name);
            }
        }

        public boolean removeTarget(TeleporterTarget target) {
            if (this.targets.remove((Object)target) != null) {
                this.custom.remove(target);
                return true;
            }
            return false;
        }

        @OnlyIn(value=Dist.CLIENT)
        public List<Tuple<TeleporterTarget, String>> getClientTargets() {
            return this.clientTargets;
        }

        @Override
        public void write(IOutputBuffer buffer) {
            buffer.writeShort((short)this.targets.size());
            for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.targets)) {
                buffer.writeString((String)entry.getValue());
                ((TeleporterTarget)entry.getKey()).write(buffer);
            }
        }

        @Override
        public void read(IInputBuffer buffer) {
            ObjectList newClientTargets = CollectionUtils.createList();
            Object2ObjectSortedMap newTargets = CollectionUtils.createLinkedMap();
            int size = buffer.readShort();
            for (int i = 0; i < size; ++i) {
                String name = buffer.readString();
                TeleporterTarget target = TeleporterTarget.readFromBuffer(buffer);
                if (target == null) continue;
                newClientTargets.add((Tuple)new Tuple((Object)target, (Object)name));
                newTargets.put((Object)target, (Object)name);
            }
            this.clientTargets = newClientTargets;
            this.targets = newTargets;
        }

        public CompoundTag write(CompoundTag nbt) {
            ListTag list = new ListTag();
            for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.targets)) {
                CompoundTag data = new CompoundTag();
                data.m_128365_("target", (Tag)((TeleporterTarget)entry.getKey()).write(new CompoundTag()));
                data.m_128359_("name", (String)entry.getValue());
                data.m_128379_("custom", this.custom.contains(entry.getKey()));
                list.add((Object)data);
            }
            nbt.m_128365_("data", (Tag)list);
            return nbt;
        }

        public void read(CompoundTag nbt) {
            for (CompoundTag entry : NBTListWrapper.wrap(nbt.m_128437_("data", 10), CompoundTag.class)) {
                TeleporterTarget target = TeleporterTarget.read(entry.m_128469_("target"));
                if (target == null) continue;
                this.targets.put((Object)target, (Object)entry.m_128461_("name"));
                if (!nbt.m_128471_("custom")) continue;
                this.custom.add(target);
            }
        }
    }
}

