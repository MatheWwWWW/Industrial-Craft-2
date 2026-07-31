/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Boat$Status
 *  net.minecraft.world.entity.vehicle.Boat$Type
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.network.NetworkHooks
 */
package ic2.core.entity.misc;

import ic2.core.platform.corehacks.mixins.server.BoatMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class IC2Boat
extends Boat {
    public IC2Boat(EntityType<? extends Boat> type, Level world) {
        super(type, world);
        this.m_38332_(Boat.Type.OAK);
    }

    public IC2Boat(EntityType<? extends Boat> type, Level worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.m_6034_(x, y, z);
        this.m_20256_(Vec3.f_82478_);
        this.f_19854_ = x;
        this.f_19855_ = y;
        this.f_19856_ = z;
    }

    public abstract Item m_38369_();

    public abstract ResourceLocation getTexture();

    public void spawnBrokenDrops() {
    }

    protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.updateLastY();
        if (!this.m_20159_()) {
            if (onGroundIn) {
                if (this.f_19789_ > 3.0f) {
                    if (this.m_38392_() != Boat.Status.ON_LAND) {
                        this.f_19789_ = 0.0f;
                        return;
                    }
                    this.m_142535_(this.f_19789_, 1.0f, DamageSource.f_19318_);
                    if (!this.f_19853_.f_46443_ && this.m_6084_()) {
                        this.m_142687_(Entity.RemovalReason.DISCARDED);
                        if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
                            this.spawnBrokenDrops();
                        }
                    }
                }
                this.f_19789_ = 0.0f;
            } else if (!this.f_19853_.m_6425_(this.m_20183_().m_7495_()).m_205070_(FluidTags.f_13131_) && y < 0.0) {
                this.f_19789_ = (float)((double)this.f_19789_ - y);
            }
        }
    }

    public Boat.Status m_38392_() {
        return ((BoatMixin)((Object)this)).getActualStatus();
    }

    public void updateLastY() {
        ((BoatMixin)((Object)this)).setLastYDelta(this.m_20184_().f_82480_);
    }

    public Packet<?> m_5654_() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }
}

