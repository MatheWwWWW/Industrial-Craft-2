/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.math.Vector3f
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.DustParticleOptions
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.boss.wither.WitherBoss
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.AbstractMinecart
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.machine.tileentity;

import com.mojang.math.Vector3f;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import ic2.core.audio.AudioPosition;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.init.MainConfig;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.util.ConfigUtil;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityTeleporter
extends Ic2TileEntity
implements INetworkTileEntityEventListener {
    private BlockPos target;
    private AudioSource audioSource = null;
    private int targetCheckTicker = IC2.random.m_188503_(1024);
    private int cooldown = 0;
    protected final ComparatorEmitter comparator = this.addComponent(new ComparatorEmitter(this));
    private static final int EventTeleport = 0;

    public TileEntityTeleporter(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.TELEPORTER, blockPos, blockState);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        if (compoundTag.m_128441_("targetX")) {
            this.target = new BlockPos(compoundTag.m_128451_("targetX"), compoundTag.m_128451_("targetY"), compoundTag.m_128451_("targetZ"));
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (this.target != null) {
            compoundTag.m_128405_("targetX", this.target.m_123341_());
            compoundTag.m_128405_("targetY", this.target.m_123342_());
            compoundTag.m_128405_("targetZ", this.target.m_123343_());
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.sideProxy.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateComparatorLevel();
    }

    @Override
    protected void updateEntityServer() {
        Level level;
        boolean bl;
        super.updateEntityServer();
        boolean bl2 = bl = this.cooldown > 0;
        if (bl) {
            --this.cooldown;
            IC2.network.get(true).updateTileEntityField(this, "cooldown");
        }
        if ((level = this.m_58904_()).m_46753_(this.f_58858_) && this.target != null) {
            this.setActive(true);
            List list = bl ? Collections.emptyList() : level.m_6443_(Entity.class, new AABB((double)(this.f_58858_.m_123341_() - 1), (double)this.f_58858_.m_123342_(), (double)(this.f_58858_.m_123343_() - 1), (double)(this.f_58858_.m_123341_() + 2), (double)(this.f_58858_.m_123342_() + 3), (double)(this.f_58858_.m_123343_() + 2)), null);
            if (!list.isEmpty() && this.verifyTarget()) {
                double d = Double.MAX_VALUE;
                Entity entity = null;
                for (Entity entity2 : list) {
                    double d2;
                    if (entity2.m_20202_() != null || !((d2 = this.f_58858_.m_203202_(entity2.m_20185_(), entity2.m_20186_(), entity2.m_20189_())) < d)) continue;
                    d = d2;
                    entity = entity2;
                }
                assert (entity != null);
                this.teleport(entity, Math.sqrt(this.f_58858_.m_123331_((Vec3i)this.target)));
            } else if (++this.targetCheckTicker % 1024 == 0) {
                this.verifyTarget();
            }
        } else {
            this.setActive(false);
        }
    }

    private boolean verifyTarget() {
        if (this.m_58904_().m_7702_(this.target) instanceof TileEntityTeleporter) {
            return true;
        }
        this.target = null;
        this.updateComparatorLevel();
        this.setActive(false);
        return false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        if (this.getActive()) {
            if (this.cooldown > 0) {
                this.spawnGreenParticles(2, this.f_58858_);
            } else {
                this.spawnBlueParticles(2, this.f_58858_);
            }
        }
    }

    private void updateComparatorLevel() {
        int n = this.target != null ? 15 : 0;
        this.comparator.setLevel(n);
    }

    public void teleport(Entity entity, double d) {
        int n = this.getWeightOf(entity);
        if (n == 0) {
            return;
        }
        int n2 = (int)((double)n * Math.pow(d + 10.0, 0.7) * 5.0);
        if (n2 > this.getAvailableEnergy()) {
            return;
        }
        this.consumeEnergy(n2);
        if (entity instanceof ServerPlayer) {
            ((ServerPlayer)entity).m_6021_((double)this.target.m_123341_() + 0.5, (double)this.target.m_123342_() + 1.5 + entity.m_6049_(), (double)this.target.m_123343_() + 0.5);
        } else {
            entity.m_19890_((double)this.target.m_123341_() + 0.5, (double)this.target.m_123342_() + 1.5 + entity.m_6049_(), (double)this.target.m_123343_() + 0.5, entity.m_146908_(), entity.m_146909_());
        }
        BlockEntity blockEntity = this.m_58904_().m_7702_(this.target);
        assert (blockEntity instanceof TileEntityTeleporter);
        ((TileEntityTeleporter)blockEntity).onTeleportTo(this, entity);
        IC2.network.get(true).initiateTileEntityEvent(this, 0, true);
        if (entity instanceof Player && d >= 1000.0) {
            IC2.achievements.issueAchievement((Player)entity, "teleportFarAway");
        }
    }

    public void spawnBlueParticles(int n, BlockPos blockPos) {
        this.spawnParticles(n, blockPos, -1, 0, 1);
    }

    public void spawnGreenParticles(int n, BlockPos blockPos) {
        this.spawnParticles(n, blockPos, -1, 1, 0);
    }

    private void spawnParticles(int n, BlockPos blockPos, int n2, int n3, int n4) {
        Level level = this.m_58904_();
        RandomSource randomSource = level.f_46441_;
        DustParticleOptions dustParticleOptions = new DustParticleOptions(new Vector3f((float)n2 / 255.0f, (float)n3 / 255.0f, (float)n4 / 255.0f), 1.0f);
        for (int i = 0; i < n; ++i) {
            level.m_7106_((ParticleOptions)dustParticleOptions, (double)((float)blockPos.m_123341_() + randomSource.m_188501_()), (double)((float)(blockPos.m_123342_() + 1) + randomSource.m_188501_()), (double)((float)blockPos.m_123343_() + randomSource.m_188501_()), 0.0, 0.0, 0.0);
            level.m_7106_((ParticleOptions)dustParticleOptions, (double)((float)blockPos.m_123341_() + randomSource.m_188501_()), (double)((float)(blockPos.m_123342_() + 2) + randomSource.m_188501_()), (double)((float)blockPos.m_123343_() + randomSource.m_188501_()), 0.0, 0.0, 0.0);
        }
    }

    public void consumeEnergy(int n) {
        Level level = this.m_58904_();
        LinkedList<IEnergyStorage> linkedList = new LinkedList<IEnergyStorage>();
        for (Direction direction : Util.ALL_DIRS) {
            IEnergyStorage iEnergyStorage;
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof IEnergyStorage) || !(iEnergyStorage = (IEnergyStorage)blockEntity).isTeleporterCompatible(direction.m_122424_()) || iEnergyStorage.getStored() <= 0) continue;
            linkedList.add(iEnergyStorage);
        }
        while (n > 0) {
            int n2 = (n + linkedList.size() - 1) / linkedList.size();
            Iterator iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                IEnergyStorage iEnergyStorage = (IEnergyStorage)iterator.next();
                if (n2 > n) {
                    n2 = n;
                }
                if (iEnergyStorage.getStored() <= n2) {
                    n -= iEnergyStorage.getStored();
                    iEnergyStorage.setStored(0);
                    iterator.remove();
                    continue;
                }
                n -= n2;
                iEnergyStorage.addEnergy(-n2);
            }
        }
    }

    public int getAvailableEnergy() {
        Level level = this.m_58904_();
        int n = 0;
        for (Direction direction : Util.ALL_DIRS) {
            IEnergyStorage iEnergyStorage;
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof IEnergyStorage) || !(iEnergyStorage = (IEnergyStorage)blockEntity).isTeleporterCompatible(direction.m_122424_())) continue;
            n += iEnergyStorage.getStored();
        }
        return n;
    }

    public int getWeightOf(Entity entity) {
        Object object;
        boolean bl = ConfigUtil.getBool(MainConfig.get(), "balance/teleporterUseInventoryWeight");
        int n = 0;
        if (entity instanceof ItemEntity) {
            object = ((ItemEntity)entity).m_32055_();
            n += 100 * StackUtil.getSize((ItemStack)object) / object.m_41741_();
        } else if (entity instanceof Animal || entity instanceof AbstractMinecart || entity instanceof Boat) {
            n += 100;
        } else if (entity instanceof Player) {
            n += 1000;
            if (bl) {
                for (Entity entity2 : ((Player)entity).m_150109_().f_35974_) {
                    n += TileEntityTeleporter.getStackCost((ItemStack)entity2);
                }
            }
        } else if (entity instanceof Ghast) {
            n += 2500;
        } else if (entity instanceof WitherBoss) {
            n += 5000;
        } else if (entity instanceof EnderDragon) {
            n += 10000;
        } else if (entity instanceof Mob) {
            n += 500;
        }
        if (bl && entity instanceof LivingEntity) {
            object = (LivingEntity)entity;
            for (ItemStack itemStack : object.m_20158_()) {
                n += TileEntityTeleporter.getStackCost(itemStack);
            }
            if (entity instanceof Player) {
                Entity entity2;
                entity2 = object.m_21205_();
                n -= TileEntityTeleporter.getStackCost((ItemStack)entity2);
            }
        }
        for (Entity entity2 : entity.m_20197_()) {
            n += this.getWeightOf(entity2);
        }
        return n;
    }

    private static int getStackCost(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        return 100 * StackUtil.getSize(itemStack) / itemStack.m_41741_();
    }

    private void onTeleportTo(TileEntityTeleporter tileEntityTeleporter, Entity entity) {
        this.cooldown = 20;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return !(entity instanceof EnderDragon) && !(entity instanceof WitherBoss);
    }

    public boolean hasTarget() {
        return this.target != null;
    }

    public BlockPos getTarget() {
        return this.target;
    }

    public void setTarget(BlockPos blockPos) {
        this.target = blockPos;
        this.updateComparatorLevel();
        IC2.network.get(true).updateTileEntityField(this, "target");
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("target");
        return list;
    }

    @Override
    public void onNetworkUpdate(String string) {
        if (string.equals("active")) {
            if (this.audioSource == null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/Teleporter/TeleChargedLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
            }
            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }
        super.onNetworkUpdate(string);
    }

    @Override
    public void onNetworkEvent(int n) {
        switch (n) {
            case 0: {
                IC2.audioManager.playOnce(this, "Machines/Teleporter/TeleUse.ogg");
                IC2.audioManager.playOnce(new AudioPosition(this.m_58904_(), this.f_58858_), "Machines/Teleporter/TeleUse.ogg");
                this.spawnBlueParticles(20, this.f_58858_);
                this.spawnBlueParticles(20, this.target);
                break;
            }
            default: {
                IC2.sideProxy.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + n + ", tile entity below)\nT: " + this + " (" + this.f_58858_ + ")", new Object[0]);
            }
        }
    }
}

