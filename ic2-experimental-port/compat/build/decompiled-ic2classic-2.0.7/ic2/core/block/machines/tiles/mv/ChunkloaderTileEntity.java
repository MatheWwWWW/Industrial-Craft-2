/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.TicketType
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.world.ForgeChunkManager
 */
package ic2.core.block.machines.tiles.mv;

import com.mojang.math.Vector3f;
import ic2.api.network.buffer.NetworkInfo;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.base.features.personal.IPersonalWrenchable;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.components.mv.ChunkloaderComponent;
import ic2.core.block.machines.containers.mv.ChunkloaderContainer;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.MathUtils;
import java.util.Comparator;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.world.ForgeChunkManager;

public class ChunkloaderTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
BlockHighlighter.IHighlightController,
ITileActivityProvider,
IPersonalTile,
IPersonalWrenchable {
    public static final int[] POWER_COST = new int[]{5, 15, 60, 300};
    public static final TicketType<ChunkPos> CUSTOM_TICKET = TicketType.m_9462_((String)"ic2_chunkloader", Comparator.comparingLong(ChunkPos::m_45588_));
    @NetworkInfo
    public int radius = 1;
    @NetworkInfo
    public boolean doesChunkProcessing = false;
    public float animationProgress = 0.0f;
    @NetworkInfo
    UUID owner;
    @NetworkInfo
    public int mode = 1;

    public ChunkloaderTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0, 128, 60000);
        this.addNetworkFields("owner", "mode");
        this.addGuiFields("radius", "doesChunkProcessing");
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
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
    public BlockEntityType<?> createType() {
        return IC2Tiles.CHUNKLOADER;
    }

    @Override
    public boolean needsUpdateTick() {
        return true;
    }

    public AABB getRenderBoundingBox() {
        return new AABB(this.m_58899_(), this.m_58899_().m_7918_(1, 1, 1));
    }

    @Override
    public boolean keepAlive(BlockHighlighter.Highlighter owner) {
        owner.ticksLeft = 100;
        return !this.m_58901_();
    }

    @Override
    public void setOwner(UUID owner) {
        if (this.owner == null && owner != null) {
            this.owner = owner;
        }
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public boolean canDoAction(UUID user, Action action, boolean ignorePublic) {
        if (this.owner == null) {
            this.setOwner(user);
            return true;
        }
        if (this.owner.equals(user) || !ignorePublic && this.mode == 0 || IC2.PLATFORM.isOp(user)) {
            return true;
        }
        if (this.mode == 2) {
            return false;
        }
        Friend friend = FriendManager.getFriends().getFriend(this.owner, user);
        return friend != null && friend.canApplyAction(action);
    }

    public boolean isPublic() {
        return this.mode == 0;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.canDoAction(player.m_20148_(), Action.CHUNKLOADER_ACCESS, false);
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.canDoAction(player.m_20148_(), Action.CHUNKLOADER_ACCESS, false);
    }

    @Override
    public boolean canSetFacing(Direction dir, Player player) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_() && this.canDoAction(player.m_20148_(), Action.CHUNKLOADER_EDIT, false);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return this.canDoAction(player.m_20148_(), Action.CHUNKLOADER_EDIT, false);
    }

    @Override
    public void onTick() {
        if (this.isRendering()) {
            if (this.isActive()) {
                this.animationProgress += this.animationProgress < 1.0f ? 0.02f : 0.01f;
                if (this.animationProgress > 2.0f) {
                    this.animationProgress -= 1.0f;
                }
                this.f_58857_.m_7106_((ParticleOptions)new DustParticleOptions(new Vector3f(1.0f, 1.0f, 1.0f), 1.0f), (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123342_() + 1.0, (double)this.f_58858_.m_123343_() + 0.5, (double)0.1f, (double)0.1f, (double)0.1f);
            } else if (this.animationProgress > 1.0f) {
                this.animationProgress += 0.01f;
                if (this.animationProgress > 2.0f) {
                    this.animationProgress = 1.0f;
                }
            } else if (this.animationProgress > 0.0f) {
                this.animationProgress = Math.max(0.0f, this.animationProgress - 0.02f);
            }
            return;
        }
        this.handleRedstone();
        if (this.isActive() && this.clock(4)) {
            ((ServerLevel)this.f_58857_).m_8767_((ParticleOptions)ParticleTypes.f_123760_, (double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 1.5, (double)this.f_58858_.m_123343_() + 0.5, 1, 0.0, (double)0.2f, 0.0, (double)0.2f);
        }
        int cost = POWER_COST[this.radius] * (this.doesChunkProcessing ? 2 : 1);
        if (this.isActive() && (!this.hasEnergy(cost) || this.isRedstonePowered())) {
            this.setActive(false);
            this.unloadChunks(false);
        } else if (!this.isActive() && this.hasEnergy(Math.min(cost * 100, this.maxEnergy / 2)) && !this.isRedstonePowered()) {
            this.setActive(true);
            this.loadChunks();
        }
        if (this.isActive()) {
            this.useEnergy(cost);
        }
        this.handleComparators();
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isRendering()) {
            this.animationProgress = this.isActive() ? 1.0f : 0.0f;
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.isActive() && !chunk) {
            this.unloadChunks(true);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("radius", this.radius);
        compound.m_128379_("processing", this.doesChunkProcessing);
        compound.m_128405_("mode", this.mode);
        if (this.owner != null) {
            compound.m_128362_("owner", this.owner);
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.radius = compound.m_128451_("radius");
        this.doesChunkProcessing = compound.m_128471_("processing");
        this.mode = NBTUtils.getInt(compound, "mode", 1);
        if (compound.m_128403_("owner")) {
            this.owner = compound.m_128342_("owner");
        }
    }

    private void loadChunks() {
        ChunkPos pos = new ChunkPos(this.f_58858_);
        for (int x = -this.radius; x <= this.radius; ++x) {
            for (int z = -this.radius; z <= this.radius; ++z) {
                ForgeChunkManager.forceChunk((ServerLevel)((ServerLevel)this.f_58857_), (String)"ic2", (BlockPos)this.f_58858_, (int)(x + pos.f_45578_), (int)(z + pos.f_45579_), (boolean)true, (boolean)this.doesChunkProcessing);
            }
        }
        this.setActive(true);
    }

    private void unloadChunks(boolean unload) {
        ChunkPos pos = new ChunkPos(this.f_58858_);
        for (int x = -this.radius; x <= this.radius; ++x) {
            for (int z = -this.radius; z <= this.radius; ++z) {
                ForgeChunkManager.forceChunk((ServerLevel)((ServerLevel)this.f_58857_), (String)"ic2", (BlockPos)this.f_58858_, (int)(x + pos.f_45578_), (int)(z + pos.f_45579_), (boolean)false, (boolean)this.doesChunkProcessing);
            }
        }
        if (!unload) {
            this.setActive(false);
        }
    }

    public int getRadius() {
        return this.radius;
    }

    private void setRadius(int radius) {
        if (radius >= 0 && radius <= 3 && this.radius != radius) {
            if (this.isActive()) {
                this.unloadChunks(false);
            }
            this.radius = radius;
            this.updateGuiField("radius");
            if (this.isActive()) {
                this.loadChunks();
            }
        }
    }

    private void setProcessing(boolean value) {
        if (value != this.doesChunkProcessing) {
            if (this.isActive()) {
                this.unloadChunks(false);
            }
            this.doesChunkProcessing = value;
            this.updateGuiField("doesChunkProcessing");
            if (this.isActive()) {
                this.loadChunks();
            }
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            if (!this.canDoAction(entity.m_20148_(), Action.CHUNKLOADER_EDIT, false)) {
                entity.m_5661_((Component)this.translate("gui.ic2.chunkloader.permission"), false);
                return;
            }
            this.setRadius(MathUtils.getShortKey(value));
            this.setProcessing(MathUtils.getShortValue(value) != 0);
        } else if (key == 1) {
            if (entity.m_20148_().equals(this.owner)) {
                this.mode = (this.mode + 1) % 3;
                this.updateTileField("mode");
            } else {
                entity.m_5661_((Component)this.translate("gui.ic2.personal.perm"), false);
            }
        }
    }

    @Override
    public void onGuiFieldChanged(Set<String> fields, Player player) {
        super.onGuiFieldChanged(fields, player);
        if (fields.contains("doesChunkProcessing") || fields.contains("radius")) {
            this.resyncUI();
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void resyncUI() {
        ChunkloaderComponent comp;
        AbstractContainerMenu c = Minecraft.m_91087_().f_91074_.f_36096_;
        if (c instanceof ChunkloaderContainer && (comp = ((ChunkloaderContainer)c).getComponent(ChunkloaderComponent.class)) != null) {
            comp.onSync();
        }
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ChunkloaderContainer(this, player, windowID);
    }
}

