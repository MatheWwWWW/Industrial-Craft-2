/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.entity.LevelEntityGetter
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.FakePlayerFactory
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.wrapper.EmptyHandler
 */
package ic2.core.block.machines.tiles.hv;

import com.mojang.authlib.GameProfile;
import ic2.api.core.IC2Classic;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.components.hv.villager.VillagerSelectorComponent;
import ic2.core.block.machines.containers.hv.VillagerOMatContainer;
import ic2.core.block.machines.logic.villager.VillagerList;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.inventory.transporter.transporters.special.DirectionalTransporter;
import ic2.core.networking.buffers.data.VillagerBuffer;
import ic2.core.platform.corehacks.mixins.server.LevelMixin;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

public class VillagerOMatTileEntity
extends BaseElectricTileEntity
implements ITileGui,
ITickListener,
INetworkDataEventListener,
IAreaOfEffect {
    public UUID owner = null;
    @NetworkInfo
    public VillagerList trades = new VillagerList();
    @NetworkInfo
    public Direction importDir = Direction.NORTH;
    @NetworkInfo
    public Direction exportDir = Direction.SOUTH;
    @NetworkInfo
    public int storedXP;
    public int visualId = -1;
    ICache<IItemHandler> invCache = new CapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER);
    ICache<ITube> tubeCache = new CapabilityCache<ITube>(this, DirectionList.ALL, IC2Classic.TUBE_CAPABILITY);

    public VillagerOMatTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0, 512, 15000000);
        this.addCaches(this.invCache, this.tubeCache);
        this.addCapability(ForgeCapabilities.ITEM_HANDLER, EmptyHandler.INSTANCE);
        this.addGuiFields("trades", "importDir", "exportDir", "storedXP");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.VILLAGER_O_MAT;
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public boolean allowsUI() {
        return false;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.trades.load(compound);
        this.importDir = Direction.m_122376_((int)compound.m_128451_("importdir"));
        this.exportDir = Direction.m_122376_((int)compound.m_128451_("exportdir"));
        this.storedXP = compound.m_128451_("xp");
        this.owner = compound.m_128403_("owner") ? compound.m_128342_("owner") : null;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        this.trades.save(compound);
        compound.m_128344_("importdir", (byte)this.importDir.m_122411_());
        compound.m_128344_("exportdir", (byte)this.exportDir.m_122411_());
        compound.m_128405_("xp", this.storedXP);
        if (this.owner != null) {
            compound.m_128362_("owner", this.owner);
        }
    }

    @Override
    public void onGuiFieldChanged(Set<String> fields, Player player) {
        super.onGuiFieldChanged(fields, player);
        if (fields.contains("trades")) {
            this.getContainer(player, VillagerOMatContainer.class, T -> T.getComponent(VillagerSelectorComponent.class).reloadVillagerData());
        }
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        if (data instanceof VillagerBuffer) {
            VillagerBuffer buffer = (VillagerBuffer)data;
            if (target.isDedicatedServer() && this.trades.loadData(buffer.getId(), buffer.getIndex(), buffer.getState())) {
                this.updateGuiField("trades");
            }
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        Entity type;
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.importDir = Direction.m_122376_((int)value);
            this.updateGuiField("importDir");
        } else if (key == 1) {
            this.exportDir = Direction.m_122376_((int)value);
            this.updateGuiField("exportDir");
        } else if (key == 2) {
            entity.m_6756_(this.storedXP);
            this.storedXP = 0;
            this.updateGuiField("storedXP");
        } else if (key == 3 && (type = (Entity)((LevelMixin)this.f_58857_).getEntityAccessor().m_142597_(value)) instanceof Villager) {
            Villager villager = (Villager)type;
            villager.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 200));
            entity.m_6915_();
        }
    }

    private void acceptXP(int amount) {
        this.storedXP += amount;
        if (this.storedXP > 100000) {
            this.storedXP = 100000;
        }
        this.updateGuiField("storedXP");
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new VillagerOMatContainer(this, player, windowID);
    }

    @Override
    public AABB getAreaOfEffect() {
        return new AABB(this.f_58858_).m_82400_(15.0);
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2131340288;
    }

    @Override
    public void setVisualizationId(int id) {
        this.visualId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.visualId;
    }

    @Override
    public void onTick() {
        if (this.clock(80)) {
            LevelEntityGetter<Entity> getter = ((LevelMixin)this.f_58857_).getEntityAccessor();
            Player trader = this.getOwner();
            if (this.trades.update(this.f_58857_.m_45976_(Villager.class, new AABB(this.f_58858_).m_82400_(15.0)), getter, trader)) {
                this.updateGuiField("trades");
            }
            if (this.clock(1200) && this.hasEnergy(this.trades.getActiveTrades() * 6000)) {
                IItemTransporter output = this.get(this.exportDir);
                IItemTransporter input = TransporterManager.getTransporter(this.invCache.getHandler(this.importDir));
                if (output == null || input == null) {
                    return;
                }
                if (this.trades.trade(getter, trader, new DirectionalTransporter(input, this.importDir.m_122424_()), new DirectionalTransporter(output, this.exportDir.m_122424_()), this::acceptXP)) {
                    IC2.AUDIO.playSound(this, IC2Sounds.TRADED_SOUND);
                    this.useEnergy(this.trades.getActiveTrades() * 6000);
                }
            }
        }
    }

    private IItemTransporter get(Direction dir) {
        return this.tubeCache.contains(dir) ? TransporterManager.getTransporter(this.tubeCache.getHandler(dir)) : TransporterManager.getTransporter(this.invCache.getHandler(dir));
    }

    public Player getOwner() {
        Level level = this.f_58857_;
        if (!(level instanceof ServerLevel)) {
            return null;
        }
        ServerLevel level2 = (ServerLevel)level;
        if (this.owner == null) {
            return FakePlayerFactory.getMinecraft((ServerLevel)level2);
        }
        ServerPlayer player = level2.m_7654_().m_6846_().m_11259_(this.owner);
        if (player != null) {
            return player;
        }
        return FakePlayerFactory.get((ServerLevel)level2, (GameProfile)this.getProfile());
    }

    public GameProfile getProfile() {
        return new GameProfile(this.owner, "Trader [" + this.owner.toString() + "]");
    }
}

