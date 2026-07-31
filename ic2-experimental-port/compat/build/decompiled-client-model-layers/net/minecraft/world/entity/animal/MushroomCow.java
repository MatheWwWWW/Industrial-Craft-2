/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.tuple.Pair
 */
package net.minecraft.world.entity.animal;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.commons.lang3.tuple.Pair;

public class MushroomCow
extends Cow
implements Shearable {
    private static final EntityDataAccessor<String> f_28908_ = SynchedEntityData.m_135353_(MushroomCow.class, EntityDataSerializers.f_135030_);
    private static final int f_148934_ = 1024;
    @Nullable
    private MobEffect f_28909_;
    private int f_28910_;
    @Nullable
    private UUID f_28911_;

    public MushroomCow(EntityType<? extends MushroomCow> p_28914_, Level p_28915_) {
        super((EntityType<? extends Cow>)p_28914_, p_28915_);
    }

    @Override
    public float m_5610_(BlockPos p_28933_, LevelReader p_28934_) {
        if (p_28934_.m_8055_(p_28933_.m_7495_()).m_60713_(Blocks.f_50195_)) {
            return 10.0f;
        }
        return p_28934_.m_220419_(p_28933_);
    }

    public static boolean m_218200_(EntityType<MushroomCow> p_218201_, LevelAccessor p_218202_, MobSpawnType p_218203_, BlockPos p_218204_, RandomSource p_218205_) {
        return p_218202_.m_8055_(p_218204_.m_7495_()).m_204336_(BlockTags.f_184231_) && MushroomCow.m_186209_(p_218202_, p_218204_);
    }

    @Override
    public void m_8038_(ServerLevel p_28921_, LightningBolt p_28922_) {
        UUID $$2 = p_28922_.m_20148_();
        if (!$$2.equals(this.f_28911_)) {
            this.m_28928_(this.m_28955_() == MushroomType.RED ? MushroomType.BROWN : MushroomType.RED);
            this.f_28911_ = $$2;
            this.m_5496_(SoundEvents.f_12071_, 2.0f, 1.0f);
        }
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_28908_, MushroomType.RED.f_28960_);
    }

    @Override
    public InteractionResult m_6071_(Player p_28941_, InteractionHand p_28942_) {
        ItemStack $$2 = p_28941_.m_21120_(p_28942_);
        if ($$2.m_150930_(Items.f_42399_) && !this.m_6162_()) {
            SoundEvent $$8;
            ItemStack $$5;
            boolean $$3 = false;
            if (this.f_28909_ != null) {
                $$3 = true;
                ItemStack $$4 = new ItemStack(Items.f_42718_);
                SuspiciousStewItem.m_43258_($$4, this.f_28909_, this.f_28910_);
                this.f_28909_ = null;
                this.f_28910_ = 0;
            } else {
                $$5 = new ItemStack(Items.f_42400_);
            }
            ItemStack $$6 = ItemUtils.m_41817_($$2, p_28941_, $$5, false);
            p_28941_.m_21008_(p_28942_, $$6);
            if ($$3) {
                SoundEvent $$7 = SoundEvents.f_12074_;
            } else {
                $$8 = SoundEvents.f_12073_;
            }
            this.m_5496_($$8, 1.0f, 1.0f);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if ($$2.m_150930_(Items.f_42574_) && this.m_6220_()) {
            this.m_5851_(SoundSource.PLAYERS);
            this.m_146852_(GameEvent.f_157781_, p_28941_);
            if (!this.f_19853_.f_46443_) {
                $$2.m_41622_(1, p_28941_, p_28927_ -> p_28927_.m_21190_(p_28942_));
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if (this.m_28955_() == MushroomType.BROWN && $$2.m_204117_(ItemTags.f_13145_)) {
            if (this.f_28909_ != null) {
                for (int $$9 = 0; $$9 < 2; ++$$9) {
                    this.f_19853_.m_7106_(ParticleTypes.f_123762_, this.m_20185_() + this.f_19796_.m_188500_() / 2.0, this.m_20227_(0.5), this.m_20189_() + this.f_19796_.m_188500_() / 2.0, 0.0, this.f_19796_.m_188500_() / 5.0, 0.0);
                }
            } else {
                Optional<Pair<MobEffect, Integer>> $$10 = this.m_28956_($$2);
                if (!$$10.isPresent()) {
                    return InteractionResult.PASS;
                }
                Pair<MobEffect, Integer> $$11 = $$10.get();
                if (!p_28941_.m_150110_().f_35937_) {
                    $$2.m_41774_(1);
                }
                for (int $$12 = 0; $$12 < 4; ++$$12) {
                    this.f_19853_.m_7106_(ParticleTypes.f_123806_, this.m_20185_() + this.f_19796_.m_188500_() / 2.0, this.m_20227_(0.5), this.m_20189_() + this.f_19796_.m_188500_() / 2.0, 0.0, this.f_19796_.m_188500_() / 5.0, 0.0);
                }
                this.f_28909_ = (MobEffect)$$11.getLeft();
                this.f_28910_ = (Integer)$$11.getRight();
                this.m_5496_(SoundEvents.f_12072_, 2.0f, 1.0f);
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_28941_, p_28942_);
    }

    @Override
    public void m_5851_(SoundSource p_28924_) {
        this.f_19853_.m_6269_(null, this, SoundEvents.f_12075_, p_28924_, 1.0f, 1.0f);
        if (!this.f_19853_.m_5776_()) {
            ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20227_(0.5), this.m_20189_(), 1, 0.0, 0.0, 0.0, 0.0);
            this.m_146870_();
            Cow $$1 = EntityType.f_20557_.m_20615_(this.f_19853_);
            $$1.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
            $$1.m_21153_(this.m_21223_());
            $$1.f_20883_ = this.f_20883_;
            if (this.m_8077_()) {
                $$1.m_6593_(this.m_7770_());
                $$1.m_20340_(this.m_20151_());
            }
            if (this.m_21532_()) {
                $$1.m_21530_();
            }
            $$1.m_20331_(this.m_20147_());
            this.f_19853_.m_7967_($$1);
            for (int $$2 = 0; $$2 < 5; ++$$2) {
                this.f_19853_.m_7967_(new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20227_(1.0), this.m_20189_(), new ItemStack(this.m_28955_().f_28961_.m_60734_())));
            }
        }
    }

    @Override
    public boolean m_6220_() {
        return this.m_6084_() && !this.m_6162_();
    }

    @Override
    public void m_7380_(CompoundTag p_28944_) {
        super.m_7380_(p_28944_);
        p_28944_.m_128359_("Type", this.m_28955_().f_28960_);
        if (this.f_28909_ != null) {
            p_28944_.m_128405_("EffectId", MobEffect.m_19459_(this.f_28909_));
            p_28944_.m_128405_("EffectDuration", this.f_28910_);
        }
    }

    @Override
    public void m_7378_(CompoundTag p_28936_) {
        super.m_7378_(p_28936_);
        this.m_28928_(MushroomType.m_28976_(p_28936_.m_128461_("Type")));
        if (p_28936_.m_128425_("EffectId", 1)) {
            this.f_28909_ = MobEffect.m_19453_(p_28936_.m_128451_("EffectId"));
        }
        if (p_28936_.m_128425_("EffectDuration", 3)) {
            this.f_28910_ = p_28936_.m_128451_("EffectDuration");
        }
    }

    private Optional<Pair<MobEffect, Integer>> m_28956_(ItemStack p_28957_) {
        Block $$2;
        Item $$1 = p_28957_.m_41720_();
        if ($$1 instanceof BlockItem && ($$2 = ((BlockItem)$$1).m_40614_()) instanceof FlowerBlock) {
            FlowerBlock $$3 = (FlowerBlock)$$2;
            return Optional.of(Pair.of((Object)$$3.m_53521_(), (Object)$$3.m_53522_()));
        }
        return Optional.empty();
    }

    private void m_28928_(MushroomType p_28929_) {
        this.f_19804_.m_135381_(f_28908_, p_28929_.f_28960_);
    }

    public MushroomType m_28955_() {
        return MushroomType.m_28976_(this.f_19804_.m_135370_(f_28908_));
    }

    @Override
    public MushroomCow m_142606_(ServerLevel p_148942_, AgeableMob p_148943_) {
        MushroomCow $$2 = EntityType.f_20504_.m_20615_(p_148942_);
        $$2.m_28928_(this.m_28930_((MushroomCow)p_148943_));
        return $$2;
    }

    private MushroomType m_28930_(MushroomCow p_28931_) {
        MushroomType $$4;
        MushroomType $$2;
        MushroomType $$1 = this.m_28955_();
        if ($$1 == ($$2 = p_28931_.m_28955_()) && this.f_19796_.m_188503_(1024) == 0) {
            MushroomType $$3 = $$1 == MushroomType.BROWN ? MushroomType.RED : MushroomType.BROWN;
        } else {
            $$4 = this.f_19796_.m_188499_() ? $$1 : $$2;
        }
        return $$4;
    }

    @Override
    public /* synthetic */ Cow m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    public static final class MushroomType
    extends Enum<MushroomType> {
        public static final /* enum */ MushroomType RED = new MushroomType("red", Blocks.f_50073_.m_49966_());
        public static final /* enum */ MushroomType BROWN = new MushroomType("brown", Blocks.f_50072_.m_49966_());
        final String f_28960_;
        final BlockState f_28961_;
        private static final /* synthetic */ MushroomType[] $VALUES;

        public static MushroomType[] values() {
            return (MushroomType[])$VALUES.clone();
        }

        public static MushroomType valueOf(String p_28979_) {
            return Enum.valueOf(MushroomType.class, p_28979_);
        }

        private MushroomType(String p_28967_, BlockState p_28968_) {
            this.f_28960_ = p_28967_;
            this.f_28961_ = p_28968_;
        }

        public BlockState m_28969_() {
            return this.f_28961_;
        }

        static MushroomType m_28976_(String p_28977_) {
            for (MushroomType $$1 : MushroomType.values()) {
                if (!$$1.f_28960_.equals(p_28977_)) continue;
                return $$1;
            }
            return RED;
        }

        private static /* synthetic */ MushroomType[] m_148944_() {
            return new MushroomType[]{RED, BROWN};
        }

        static {
            $VALUES = MushroomType.m_148944_();
        }
    }
}

