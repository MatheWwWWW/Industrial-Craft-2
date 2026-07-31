/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public abstract class BaseSpawner {
    private static final Logger f_45441_ = LogUtils.getLogger();
    private static final int f_151303_ = 1;
    private int f_45442_ = 20;
    private SimpleWeightedRandomList<SpawnData> f_45443_ = SimpleWeightedRandomList.m_185864_();
    private SpawnData f_45444_ = new SpawnData();
    private double f_45445_;
    private double f_45446_;
    private int f_45447_ = 200;
    private int f_45448_ = 800;
    private int f_45449_ = 4;
    @Nullable
    private Entity f_45450_;
    private int f_45451_ = 6;
    private int f_45452_ = 16;
    private int f_45453_ = 4;

    public void m_45462_(EntityType<?> p_45463_) {
        this.f_45444_.m_186567_().m_128359_("id", Registry.f_122826_.m_7981_(p_45463_).toString());
    }

    private boolean m_151343_(Level p_151344_, BlockPos p_151345_) {
        return p_151344_.m_45914_((double)p_151345_.m_123341_() + 0.5, (double)p_151345_.m_123342_() + 0.5, (double)p_151345_.m_123343_() + 0.5, this.f_45452_);
    }

    public void m_151319_(Level p_151320_, BlockPos p_151321_) {
        if (!this.m_151343_(p_151320_, p_151321_)) {
            this.f_45446_ = this.f_45445_;
        } else {
            RandomSource $$2 = p_151320_.m_213780_();
            double $$3 = (double)p_151321_.m_123341_() + $$2.m_188500_();
            double $$4 = (double)p_151321_.m_123342_() + $$2.m_188500_();
            double $$5 = (double)p_151321_.m_123343_() + $$2.m_188500_();
            p_151320_.m_7106_(ParticleTypes.f_123762_, $$3, $$4, $$5, 0.0, 0.0, 0.0);
            p_151320_.m_7106_(ParticleTypes.f_123744_, $$3, $$4, $$5, 0.0, 0.0, 0.0);
            if (this.f_45442_ > 0) {
                --this.f_45442_;
            }
            this.f_45446_ = this.f_45445_;
            this.f_45445_ = (this.f_45445_ + (double)(1000.0f / ((float)this.f_45442_ + 200.0f))) % 360.0;
        }
    }

    public void m_151311_(ServerLevel p_151312_, BlockPos p_151313_) {
        if (!this.m_151343_(p_151312_, p_151313_)) {
            return;
        }
        if (this.f_45442_ == -1) {
            this.m_151350_(p_151312_, p_151313_);
        }
        if (this.f_45442_ > 0) {
            --this.f_45442_;
            return;
        }
        boolean $$2 = false;
        for (int $$3 = 0; $$3 < this.f_45449_; ++$$3) {
            SpawnData.CustomSpawnRules $$13;
            double $$11;
            CompoundTag $$4 = this.f_45444_.m_186567_();
            Optional<EntityType<?>> $$5 = EntityType.m_20637_($$4);
            if ($$5.isEmpty()) {
                this.m_151350_(p_151312_, p_151313_);
                return;
            }
            ListTag $$6 = $$4.m_128437_("Pos", 6);
            int $$7 = $$6.size();
            RandomSource $$8 = p_151312_.m_213780_();
            double $$9 = $$7 >= 1 ? $$6.m_128772_(0) : (double)p_151313_.m_123341_() + ($$8.m_188500_() - $$8.m_188500_()) * (double)this.f_45453_ + 0.5;
            double $$10 = $$7 >= 2 ? $$6.m_128772_(1) : (double)(p_151313_.m_123342_() + $$8.m_188503_(3) - 1);
            double d = $$11 = $$7 >= 3 ? $$6.m_128772_(2) : (double)p_151313_.m_123343_() + ($$8.m_188500_() - $$8.m_188500_()) * (double)this.f_45453_ + 0.5;
            if (!p_151312_.m_45772_($$5.get().m_20585_($$9, $$10, $$11))) continue;
            BlockPos $$12 = new BlockPos($$9, $$10, $$11);
            if (!this.f_45444_.m_186574_().isPresent() ? !SpawnPlacements.m_217074_($$5.get(), p_151312_, MobSpawnType.SPAWNER, $$12, p_151312_.m_213780_()) : !$$5.get().m_20674_().m_21609_() && p_151312_.m_46791_() == Difficulty.PEACEFUL || !($$13 = this.f_45444_.m_186574_().get()).f_186584_().m_184578_(p_151312_.m_45517_(LightLayer.BLOCK, $$12)) || !$$13.f_186585_().m_184578_(p_151312_.m_45517_(LightLayer.SKY, $$12))) continue;
            Entity $$14 = EntityType.m_20645_($$4, p_151312_, p_151310_ -> {
                p_151310_.m_7678_($$9, $$10, $$11, p_151310_.m_146908_(), p_151310_.m_146909_());
                return p_151310_;
            });
            if ($$14 == null) {
                this.m_151350_(p_151312_, p_151313_);
                return;
            }
            int $$15 = p_151312_.m_45976_($$14.getClass(), new AABB(p_151313_.m_123341_(), p_151313_.m_123342_(), p_151313_.m_123343_(), p_151313_.m_123341_() + 1, p_151313_.m_123342_() + 1, p_151313_.m_123343_() + 1).m_82400_(this.f_45453_)).size();
            if ($$15 >= this.f_45451_) {
                this.m_151350_(p_151312_, p_151313_);
                return;
            }
            $$14.m_7678_($$14.m_20185_(), $$14.m_20186_(), $$14.m_20189_(), $$8.m_188501_() * 360.0f, 0.0f);
            if ($$14 instanceof Mob) {
                Mob $$16 = (Mob)$$14;
                if (this.f_45444_.m_186574_().isEmpty() && !$$16.m_5545_(p_151312_, MobSpawnType.SPAWNER) || !$$16.m_6914_(p_151312_)) continue;
                if (this.f_45444_.m_186567_().m_128440_() == 1 && this.f_45444_.m_186567_().m_128425_("id", 8)) {
                    ((Mob)$$14).m_6518_(p_151312_, p_151312_.m_6436_($$14.m_20183_()), MobSpawnType.SPAWNER, null, null);
                }
            }
            if (!p_151312_.m_8860_($$14)) {
                this.m_151350_(p_151312_, p_151313_);
                return;
            }
            p_151312_.m_46796_(2004, p_151313_, 0);
            p_151312_.m_142346_($$14, GameEvent.f_157810_, $$12);
            if ($$14 instanceof Mob) {
                ((Mob)$$14).m_21373_();
            }
            $$2 = true;
        }
        if ($$2) {
            this.m_151350_(p_151312_, p_151313_);
        }
    }

    private void m_151350_(Level p_151351_, BlockPos p_151352_) {
        RandomSource $$2 = p_151351_.f_46441_;
        this.f_45442_ = this.f_45448_ <= this.f_45447_ ? this.f_45447_ : this.f_45447_ + $$2.m_188503_(this.f_45448_ - this.f_45447_);
        this.f_45443_.m_216829_($$2).ifPresent(p_186386_ -> this.m_142667_(p_151351_, p_151352_, (SpawnData)p_186386_.m_146310_()));
        this.m_142523_(p_151351_, p_151352_, 1);
    }

    public void m_151328_(@Nullable Level p_151329_, BlockPos p_151330_, CompoundTag p_151331_) {
        this.f_45442_ = p_151331_.m_128448_("Delay");
        boolean $$3 = p_151331_.m_128425_("SpawnPotentials", 9);
        boolean $$4 = p_151331_.m_128425_("SpawnData", 10);
        if (!$$3) {
            SpawnData $$6;
            if ($$4) {
                SpawnData $$5 = SpawnData.f_186559_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_151331_.m_128469_("SpawnData")).resultOrPartial(p_186391_ -> f_45441_.warn("Invalid SpawnData: {}", p_186391_)).orElseGet(SpawnData::new);
            } else {
                $$6 = new SpawnData();
            }
            this.f_45443_ = SimpleWeightedRandomList.m_185862_($$6);
            this.m_142667_(p_151329_, p_151330_, $$6);
        } else {
            ListTag $$7 = p_151331_.m_128437_("SpawnPotentials", 10);
            this.f_45443_ = SpawnData.f_186560_.parse((DynamicOps)NbtOps.f_128958_, (Object)$$7).resultOrPartial(p_186388_ -> f_45441_.warn("Invalid SpawnPotentials list: {}", p_186388_)).orElseGet(SimpleWeightedRandomList::m_185864_);
            if ($$4) {
                SpawnData $$8 = SpawnData.f_186559_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_151331_.m_128469_("SpawnData")).resultOrPartial(p_186380_ -> f_45441_.warn("Invalid SpawnData: {}", p_186380_)).orElseGet(SpawnData::new);
                this.m_142667_(p_151329_, p_151330_, $$8);
            } else {
                this.f_45443_.m_216829_(p_151329_.m_213780_()).ifPresent(p_186378_ -> this.m_142667_(p_151329_, p_151330_, (SpawnData)p_186378_.m_146310_()));
            }
        }
        if (p_151331_.m_128425_("MinSpawnDelay", 99)) {
            this.f_45447_ = p_151331_.m_128448_("MinSpawnDelay");
            this.f_45448_ = p_151331_.m_128448_("MaxSpawnDelay");
            this.f_45449_ = p_151331_.m_128448_("SpawnCount");
        }
        if (p_151331_.m_128425_("MaxNearbyEntities", 99)) {
            this.f_45451_ = p_151331_.m_128448_("MaxNearbyEntities");
            this.f_45452_ = p_151331_.m_128448_("RequiredPlayerRange");
        }
        if (p_151331_.m_128425_("SpawnRange", 99)) {
            this.f_45453_ = p_151331_.m_128448_("SpawnRange");
        }
        this.f_45450_ = null;
    }

    public CompoundTag m_186381_(CompoundTag p_186382_) {
        p_186382_.m_128376_("Delay", (short)this.f_45442_);
        p_186382_.m_128376_("MinSpawnDelay", (short)this.f_45447_);
        p_186382_.m_128376_("MaxSpawnDelay", (short)this.f_45448_);
        p_186382_.m_128376_("SpawnCount", (short)this.f_45449_);
        p_186382_.m_128376_("MaxNearbyEntities", (short)this.f_45451_);
        p_186382_.m_128376_("RequiredPlayerRange", (short)this.f_45452_);
        p_186382_.m_128376_("SpawnRange", (short)this.f_45453_);
        p_186382_.m_128365_("SpawnData", (Tag)SpawnData.f_186559_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_45444_).result().orElseThrow(() -> new IllegalStateException("Invalid SpawnData")));
        p_186382_.m_128365_("SpawnPotentials", (Tag)SpawnData.f_186560_.encodeStart((DynamicOps)NbtOps.f_128958_, this.f_45443_).result().orElseThrow());
        return p_186382_;
    }

    @Nullable
    public Entity m_151314_(Level p_151315_) {
        if (this.f_45450_ == null) {
            this.f_45450_ = EntityType.m_20645_(this.f_45444_.m_186567_(), p_151315_, Function.identity());
            if (this.f_45444_.m_186567_().m_128440_() != 1 || !this.f_45444_.m_186567_().m_128425_("id", 8) || this.f_45450_ instanceof Mob) {
                // empty if block
            }
        }
        return this.f_45450_;
    }

    public boolean m_151316_(Level p_151317_, int p_151318_) {
        if (p_151318_ == 1) {
            if (p_151317_.f_46443_) {
                this.f_45442_ = this.f_45447_;
            }
            return true;
        }
        return false;
    }

    public void m_142667_(@Nullable Level p_151325_, BlockPos p_151326_, SpawnData p_151327_) {
        this.f_45444_ = p_151327_;
    }

    public abstract void m_142523_(Level var1, BlockPos var2, int var3);

    public double m_45473_() {
        return this.f_45445_;
    }

    public double m_45474_() {
        return this.f_45446_;
    }
}

