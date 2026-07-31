/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkHooks
 */
package ic2.core.entity.rockets;

import ic2.api.items.readers.IWrenchTool;
import ic2.core.IC2;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.math.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class MiningRocketEntity
extends Entity {
    protected static final EntityDataAccessor<BlockPos> TARGET = SynchedEntityData.m_135353_(MiningRocketEntity.class, (EntityDataSerializer)EntityDataSerializers.f_135038_);
    public double speed = 0.0;
    public boolean descending = false;
    public CompoundTag nbt = new CompoundTag();
    public double landingY;
    public boolean triggerChunk;

    public MiningRocketEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public MiningRocketEntity(EntityType<?> entityTypeIn, Level worldIn, BlockPos pos, boolean triggerChunk) {
        super(entityTypeIn, worldIn);
        this.m_6034_((float)pos.m_123341_() + 0.5f, pos.m_123342_(), (float)pos.m_123343_() + 0.5f);
        this.triggerChunk = triggerChunk;
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(TARGET, (Object)BlockPos.f_121853_);
    }

    public void setTarget(BlockPos pos) {
        this.f_19804_.m_135381_(TARGET, (Object)pos);
    }

    public BlockPos getTarget() {
        return (BlockPos)this.f_19804_.m_135370_(TARGET);
    }

    public void m_8119_() {
        super.m_8119_();
        if (!this.descending) {
            if (this.speed < 5.0) {
                this.speed += MathUtils.easeInBack((double)this.f_19797_ / 100.0) * 0.01;
                this.speed = Mth.m_14008_((double)this.speed, (double)-0.01, (double)5.0);
            }
            double movementLeft = this.speed;
            while (movementLeft != 0.0) {
                this.m_6478_(MoverType.SELF, new Vec3(0.0, Mth.m_14008_((double)movementLeft, (double)-1.0, (double)1.0), 0.0));
                movementLeft = this.speed < 0.0 ? Math.min(0.0, movementLeft + 1.0) : Math.max(0.0, movementLeft - 1.0);
                if (!(this.m_20186_() >= (double)this.f_19853_.m_151558_()) && !this.isSolidBlock(this.m_20183_().m_7494_()) && !this.isSolidBlock(this.m_20183_().m_6630_(2))) continue;
                this.moveToTarget();
            }
            this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, -0.5, 0.0);
        } else {
            double diff = this.m_20186_() - this.landingY;
            if (diff > 30.0) {
                if (this.speed > -5.0) {
                    this.speed -= 0.2;
                }
            } else if (diff > 10.0 && this.speed < -1.0) {
                if (this.speed < -1.0) {
                    this.speed = Math.min(this.speed + 0.75, -0.9);
                    this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_() - 0.5, this.m_20189_(), 0.0, -1.0, 0.0);
                }
            } else if (this.speed < -0.2 && diff > 2.0) {
                this.speed = Math.min(this.speed + 0.05, -0.1);
                this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_() - 0.5, this.m_20189_(), 0.0, -0.5, 0.0);
            } else if (this.speed < -0.02) {
                this.speed += 0.002;
                this.f_19853_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_() - 0.5, this.m_20189_(), 0.0, -0.5, 0.0);
            }
            for (double movementLeft = this.speed; movementLeft < 0.0; movementLeft += 1.0) {
                this.m_6478_(MoverType.SELF, new Vec3(0.0, Mth.m_14008_((double)movementLeft, (double)-1.0, (double)0.0), 0.0));
                BlockPos pos = this.getTarget();
                BlockPos result = new BlockPos(pos.m_123341_(), Math.max(this.f_19853_.m_141937_(), Mth.m_14165_((double)this.m_20186_())), pos.m_123343_());
                if (result.m_123342_() > this.f_19853_.m_141937_() && !this.isSolidBlock(result.m_7495_()) && !this.isSolidBlock(result)) continue;
                if (IC2.PLATFORM.isSimulating()) {
                    if (!this.triggerChunk && result.m_123342_() < this.getTarget().m_123342_()) {
                        result = this.getTarget();
                        while (!this.f_19853_.m_46859_(result) && result.m_123342_() < 255) {
                            result = result.m_7494_();
                        }
                    }
                    this.f_19853_.m_46597_(result, IC2Blocks.ROCKET_MINER.m_49966_());
                    BlockEntity tile = this.f_19853_.m_7702_(result);
                    if (tile instanceof RocketMinerTileEntity) {
                        RocketMinerTileEntity rocket = (RocketMinerTileEntity)tile;
                        rocket.m_142466_(this.nbt);
                        rocket.energy = 0;
                        rocket.finished = 0;
                        rocket.onLanded(this.triggerChunk);
                    }
                }
                this.m_142687_(Entity.RemovalReason.DISCARDED);
                return;
            }
        }
    }

    public boolean m_6087_() {
        return true;
    }

    public InteractionResult m_7111_(Player player, Vec3 vec, InteractionHand hand) {
        if (player.m_21120_(hand).m_41720_() instanceof IWrenchTool && !this.f_19853_.m_5776_()) {
            this.f_19853_.m_46597_(this.m_20183_(), IC2Blocks.ROCKET_MINER.m_49966_());
            BlockEntity tile = this.f_19853_.m_7702_(this.m_20183_());
            if (tile instanceof RocketMinerTileEntity) {
                RocketMinerTileEntity rocket = (RocketMinerTileEntity)tile;
                rocket.m_142466_(this.nbt);
                rocket.energy = 0;
                rocket.finished = 0;
                rocket.onLanded(this.triggerChunk);
                rocket.finished = 4;
            }
            this.m_142687_(Entity.RemovalReason.DISCARDED);
        }
        return InteractionResult.SUCCESS;
    }

    private boolean isSolidBlock(BlockPos pos) {
        if (!this.f_19853_.m_46859_(pos)) {
            return this.f_19853_.m_8055_(pos).m_60767_().m_76334_();
        }
        return false;
    }

    public void moveToTarget() {
        this.descending = true;
        BlockPos pos = this.getTarget();
        this.m_6021_((float)pos.m_123341_() + 0.5f, this.m_20186_(), (float)pos.m_123343_() + 0.5f);
        this.speed = -0.5;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos((double)pos.m_123341_(), this.m_20186_(), (double)pos.m_123343_());
        while (mutable.m_123342_() >= this.f_19853_.m_141937_() && !this.isSolidBlock((BlockPos)mutable)) {
            mutable.m_122173_(Direction.DOWN);
        }
        this.landingY = mutable.m_122173_(Direction.UP).m_123342_() + 1;
    }

    protected void m_7378_(CompoundTag compound) {
        this.speed = compound.m_128459_("speed");
        this.descending = compound.m_128471_("descending");
        this.triggerChunk = compound.m_128471_("trigger_chunk");
        this.setTarget(BlockPos.m_122022_((long)compound.m_128454_("targetPos")));
        this.nbt = compound.m_128469_("data");
    }

    protected void m_7380_(CompoundTag compound) {
        compound.m_128347_("speed", this.speed);
        compound.m_128379_("descending", this.descending);
        compound.m_128379_("trigger_chunk", this.triggerChunk);
        compound.m_128356_("targetPos", this.getTarget().m_121878_());
        compound.m_128365_("data", (Tag)this.nbt);
    }

    public Packet<?> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

