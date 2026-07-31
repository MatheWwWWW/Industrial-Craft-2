/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MoverType
 *  net.minecraft.world.entity.animal.WaterAnimal
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.entity.vehicle.Boat$Status
 *  net.minecraft.world.entity.vehicle.Boat$Type
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.api.entity.boat;

import ic2.api.entity.boat.BoatType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBoatEntity
extends Boat {
    protected boolean isExtraItemDropped = false;

    public AbstractBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public AbstractBoatEntity(EntityType<? extends AbstractBoatEntity> entityType, Level level, double d, double d2, double d3) {
        this(entityType, level);
        this.m_6034_(d, d2, d3);
        this.f_19854_ = d;
        this.f_19855_ = d2;
        this.f_19856_ = d3;
    }

    public void m_38332_(Boat.Type type) {
        super.m_38332_(Boat.Type.OAK);
    }

    public ItemStack getExtraDropItemStack() {
        return ItemStack.f_41583_;
    }

    public abstract BoatType getOverrideBoatType();

    public boolean brokenByFalling() {
        return true;
    }

    public boolean canFloatOn(FluidState fluidState) {
        return fluidState.m_205070_(FluidTags.f_13131_);
    }

    protected SoundEvent m_38370_() {
        switch (this.checkLocation()) {
            case IN_WATER: 
            case UNDER_WATER: 
            case UNDER_FLOWING_WATER: {
                return SoundEvents.f_11707_;
            }
            case ON_LAND: {
                return SoundEvents.f_11706_;
            }
        }
        return null;
    }

    private Boat.Status checkLocation() {
        Class<Boat> clazz = Boat.class;
        try {
            Field field = clazz.getDeclaredField("waterLevel");
            Field field2 = clazz.getDeclaredField("nearbySlipperiness");
            field.setAccessible(true);
            field2.setAccessible(true);
            Boat.Status status = this.getUnderWaterLocation();
            if (status != null) {
                field.set((Object)this, this.m_20191_().f_82292_);
                return status;
            }
            if (this.checkBoatInWater()) {
                return Boat.Status.IN_WATER;
            }
            float f = this.m_38377_();
            if (f > 0.0f) {
                field2.set((Object)this, Float.valueOf(f));
                return Boat.Status.ON_LAND;
            }
            return Boat.Status.IN_AIR;
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private boolean checkBoatInWater() {
        Class<Boat> clazz = Boat.class;
        try {
            Field field = clazz.getDeclaredField("waterLevel");
            field.setAccessible(true);
            AABB aABB = this.m_20191_();
            int n = Mth.m_14107_((double)aABB.f_82288_);
            int n2 = Mth.m_14165_((double)aABB.f_82291_);
            int n3 = Mth.m_14107_((double)aABB.f_82289_);
            int n4 = Mth.m_14165_((double)(aABB.f_82289_ + 0.001));
            int n5 = Mth.m_14107_((double)aABB.f_82290_);
            int n6 = Mth.m_14165_((double)aABB.f_82293_);
            boolean bl = false;
            field.set((Object)this, -1.7976931348623157E308);
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            for (int i = n; i < n2; ++i) {
                for (int j = n3; j < n4; ++j) {
                    for (int k = n5; k < n6; ++k) {
                        mutableBlockPos.m_122178_(i, j, k);
                        FluidState fluidState = this.f_19853_.m_6425_((BlockPos)mutableBlockPos);
                        if (!this.canFloatOn(fluidState)) continue;
                        float f = (float)j + fluidState.m_76155_((BlockGetter)this.f_19853_, (BlockPos)mutableBlockPos);
                        field.set((Object)this, Math.max((double)f, (Double)field.get((Object)this)));
                        bl |= aABB.f_82289_ < (double)f;
                    }
                }
            }
            return bl;
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private Boat.Status getUnderWaterLocation() {
        AABB aABB = this.m_20191_();
        double d = aABB.f_82292_ + 0.001;
        int n = Mth.m_14107_((double)aABB.f_82288_);
        int n2 = Mth.m_14165_((double)aABB.f_82291_);
        int n3 = Mth.m_14107_((double)aABB.f_82292_);
        int n4 = Mth.m_14165_((double)d);
        int n5 = Mth.m_14107_((double)aABB.f_82290_);
        int n6 = Mth.m_14165_((double)aABB.f_82293_);
        boolean bl = false;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    mutableBlockPos.m_122178_(i, j, k);
                    FluidState fluidState = this.f_19853_.m_6425_((BlockPos)mutableBlockPos);
                    if (!this.canFloatOn(fluidState) || !(d < (double)((float)mutableBlockPos.m_123342_() + fluidState.m_76155_((BlockGetter)this.f_19853_, (BlockPos)mutableBlockPos)))) continue;
                    if (fluidState.m_76170_()) {
                        bl = true;
                        continue;
                    }
                    return Boat.Status.UNDER_FLOWING_WATER;
                }
            }
        }
        return bl ? Boat.Status.UNDER_WATER : null;
    }

    public float m_38371_() {
        Class<Boat> clazz = Boat.class;
        try {
            Field field = clazz.getDeclaredField("fallVelocity");
            field.setAccessible(true);
            AABB aABB = this.m_20191_();
            int n = Mth.m_14107_((double)aABB.f_82288_);
            int n2 = Mth.m_14165_((double)aABB.f_82291_);
            int n3 = Mth.m_14107_((double)aABB.f_82292_);
            int n4 = Mth.m_14165_((double)(aABB.f_82292_ - (Double)field.get((Object)this)));
            int n5 = Mth.m_14107_((double)aABB.f_82290_);
            int n6 = Mth.m_14165_((double)aABB.f_82293_);
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            block2: for (int i = n3; i < n4; ++i) {
                float f = 0.0f;
                for (int j = n; j < n2; ++j) {
                    for (int k = n5; k < n6; ++k) {
                        mutableBlockPos.m_122178_(j, i, k);
                        FluidState fluidState = this.f_19853_.m_6425_((BlockPos)mutableBlockPos);
                        if (this.canFloatOn(fluidState)) {
                            f = Math.max(f, fluidState.m_76155_((BlockGetter)this.f_19853_, (BlockPos)mutableBlockPos));
                        }
                        if (f >= 1.0f) continue block2;
                    }
                }
                if (!(f < 1.0f)) continue;
                return (float)mutableBlockPos.m_123342_() + f;
            }
            return n4 + 1;
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private void invokePrivateMethod(Class<Boat> clazz, String string, Object ... objectArray) {
        try {
            Class[] classArray = new Class[objectArray.length];
            for (int i = 0; i < objectArray.length; ++i) {
                classArray[i] = objectArray[i].getClass();
            }
            Method method = clazz.getDeclaredMethod(string, classArray);
            method.setAccessible(true);
            method.invoke((Object)this, objectArray);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public void m_8119_() {
        Class<Boat> clazz = Boat.class;
        try {
            Field field = clazz.getDeclaredField("lastLocation");
            Field field2 = clazz.getDeclaredField("location");
            Field field3 = clazz.getDeclaredField("ticksUnderwater");
            Field field4 = clazz.getDeclaredField("paddlePhases");
            field.setAccessible(true);
            field2.setAccessible(true);
            field3.setAccessible(true);
            field4.setAccessible(true);
            field.set((Object)this, field2.get((Object)this));
            field2.set((Object)this, this.checkLocation());
            Boat.Status status = (Boat.Status)field2.get((Object)this);
            float f = ((Float)field3.get((Object)this)).floatValue() + 1.0f;
            float[] fArray = (float[])field4.get((Object)this);
            field3.set((Object)this, Float.valueOf(status == Boat.Status.UNDER_WATER || status == Boat.Status.UNDER_FLOWING_WATER ? f : 0.0f));
            float f2 = ((Float)field3.get((Object)this)).floatValue();
            if (!this.f_19853_.f_46443_ && f2 >= 60.0f) {
                this.m_20153_();
            }
            if (this.m_38385_() > 0) {
                this.m_38354_(this.m_38385_() - 1);
            }
            if (this.m_38384_() > 0.0f) {
                this.m_38311_(this.m_38384_() - 1.0f);
            }
            this.m_6075_();
            this.invokePrivateMethod(clazz, "updatePositionAndRotation", new Object[0]);
            if (this.m_6109_()) {
                if (!(this.m_146895_() instanceof Player)) {
                    this.m_38339_(false, false);
                }
                this.invokePrivateMethod(clazz, "updateVelocity", new Object[0]);
                if (this.f_19853_.f_46443_) {
                    this.invokePrivateMethod(clazz, "updatePaddles", new Object[0]);
                    this.f_19853_.m_5503_((Packet)new ServerboundPaddleBoatPacket(this.m_38313_(0), this.m_38313_(1)));
                }
                this.m_6478_(MoverType.SELF, this.m_20184_());
            } else {
                this.m_20256_(Vec3.f_82478_);
            }
            this.invokePrivateMethod(clazz, "handleBubbleColumn", new Object[0]);
            for (int i = 0; i <= 1; ++i) {
                if (this.m_38313_(i)) {
                    SoundEvent soundEvent;
                    if (!this.m_20067_() && (double)(fArray[i] % ((float)Math.PI * 2)) <= 0.7853981852531433 && (double)((fArray[i] + 0.3926991f) % ((float)Math.PI * 2)) >= 0.7853981852531433 && (soundEvent = this.m_38370_()) != null) {
                        Vec3 vec3 = this.m_20252_(1.0f);
                        double d = i == 1 ? -vec3.f_82481_ : vec3.f_82481_;
                        double d2 = i == 1 ? vec3.f_82479_ : -vec3.f_82479_;
                        this.f_19853_.m_6263_(null, this.m_20185_() + d, this.m_20186_(), this.m_20189_() + d2, soundEvent, this.m_5720_(), 1.0f, 0.8f + 0.4f * this.f_19796_.m_188501_());
                    }
                    fArray[i] = fArray[i] + 0.3926991f;
                    continue;
                }
                fArray[i] = 0.0f;
            }
            field4.set((Object)this, fArray);
            this.m_20101_();
            List list = this.f_19853_.m_6249_((Entity)this, this.m_20191_().m_82377_((double)0.2f, (double)-0.01f, (double)0.2f), EntitySelector.m_20421_((Entity)this));
            if (!list.isEmpty()) {
                boolean bl = !this.f_19853_.f_46443_ && !(this.m_6688_() instanceof Player);
                for (Entity entity : list) {
                    if (entity.m_20363_((Entity)this)) continue;
                    if (bl && this.m_20197_().size() < this.m_213801_() && !entity.m_20159_() && entity.m_20205_() < this.m_20205_() && entity instanceof LivingEntity && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
                        entity.m_20329_((Entity)this);
                        continue;
                    }
                    this.m_7334_(entity);
                }
            }
        }
        catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    protected void m_7840_(double d, boolean bl, BlockState blockState, BlockPos blockPos) {
        if (bl && this.brokenByFalling()) {
            super.m_7840_(d, true, blockState, blockPos);
            return;
        }
        if (!bl) {
            super.m_7840_(d, false, blockState, blockPos);
        }
    }

    public ItemEntity m_19998_(ItemLike itemLike) {
        if (itemLike == this.m_38387_().m_38434_()) {
            return this.m_19983_(new ItemStack((ItemLike)this.getOverrideBoatType().getBaseItem()));
        }
        if (itemLike == Items.f_42398_ && !this.isExtraItemDropped) {
            this.isExtraItemDropped = true;
            return this.m_19983_(this.getExtraDropItemStack());
        }
        return super.m_19998_(itemLike);
    }

    public void m_183634_() {
        super.m_183634_();
        this.isExtraItemDropped = false;
    }
}

