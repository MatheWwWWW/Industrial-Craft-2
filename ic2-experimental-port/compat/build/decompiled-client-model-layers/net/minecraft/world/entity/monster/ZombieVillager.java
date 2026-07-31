/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.monster;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

public class ZombieVillager
extends Zombie
implements VillagerDataHolder {
    private static final Logger f_201975_ = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> f_34359_ = SynchedEntityData.m_135353_(ZombieVillager.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<VillagerData> f_34364_ = SynchedEntityData.m_135353_(ZombieVillager.class, EntityDataSerializers.f_135043_);
    private static final int f_149888_ = 3600;
    private static final int f_149885_ = 6000;
    private static final int f_149886_ = 14;
    private static final int f_149887_ = 4;
    private int f_34365_;
    @Nullable
    private UUID f_34360_;
    @Nullable
    private Tag f_34361_;
    @Nullable
    private CompoundTag f_34362_;
    private int f_34363_;

    public ZombieVillager(EntityType<? extends ZombieVillager> p_34368_, Level p_34369_) {
        super((EntityType<? extends Zombie>)p_34368_, p_34369_);
        Registry.f_122869_.m_213642_(this.f_19796_).ifPresent(p_204069_ -> this.m_34375_(this.m_7141_().m_35565_((VillagerProfession)p_204069_.m_203334_())));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_34359_, false);
        this.f_19804_.m_135372_(f_34364_, new VillagerData(VillagerType.f_35821_, VillagerProfession.f_35585_, 1));
    }

    @Override
    public void m_7380_(CompoundTag p_34397_) {
        super.m_7380_(p_34397_);
        VillagerData.f_35550_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.m_7141_()).resultOrPartial(arg_0 -> ((Logger)f_201975_).error(arg_0)).ifPresent(p_204072_ -> p_34397_.m_128365_("VillagerData", (Tag)p_204072_));
        if (this.f_34362_ != null) {
            p_34397_.m_128365_("Offers", this.f_34362_);
        }
        if (this.f_34361_ != null) {
            p_34397_.m_128365_("Gossips", this.f_34361_);
        }
        p_34397_.m_128405_("ConversionTime", this.m_34408_() ? this.f_34365_ : -1);
        if (this.f_34360_ != null) {
            p_34397_.m_128362_("ConversionPlayer", this.f_34360_);
        }
        p_34397_.m_128405_("Xp", this.f_34363_);
    }

    @Override
    public void m_7378_(CompoundTag p_34387_) {
        super.m_7378_(p_34387_);
        if (p_34387_.m_128425_("VillagerData", 10)) {
            DataResult $$1 = VillagerData.f_35550_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_34387_.m_128423_("VillagerData")));
            $$1.resultOrPartial(arg_0 -> ((Logger)f_201975_).error(arg_0)).ifPresent(this::m_34375_);
        }
        if (p_34387_.m_128425_("Offers", 10)) {
            this.f_34362_ = p_34387_.m_128469_("Offers");
        }
        if (p_34387_.m_128425_("Gossips", 10)) {
            this.f_34361_ = p_34387_.m_128437_("Gossips", 10);
        }
        if (p_34387_.m_128425_("ConversionTime", 99) && p_34387_.m_128451_("ConversionTime") > -1) {
            this.m_34383_(p_34387_.m_128403_("ConversionPlayer") ? p_34387_.m_128342_("ConversionPlayer") : null, p_34387_.m_128451_("ConversionTime"));
        }
        if (p_34387_.m_128425_("Xp", 3)) {
            this.f_34363_ = p_34387_.m_128451_("Xp");
        }
    }

    @Override
    public void m_8119_() {
        if (!this.f_19853_.f_46443_ && this.m_6084_() && this.m_34408_()) {
            int $$0 = this.m_34410_();
            this.f_34365_ -= $$0;
            if (this.f_34365_ <= 0) {
                this.m_34398_((ServerLevel)this.f_19853_);
            }
        }
        super.m_8119_();
    }

    @Override
    public InteractionResult m_6071_(Player p_34394_, InteractionHand p_34395_) {
        ItemStack $$2 = p_34394_.m_21120_(p_34395_);
        if ($$2.m_150930_(Items.f_42436_)) {
            if (this.m_21023_(MobEffects.f_19613_)) {
                if (!p_34394_.m_150110_().f_35937_) {
                    $$2.m_41774_(1);
                }
                if (!this.f_19853_.f_46443_) {
                    this.m_34383_(p_34394_.m_20148_(), this.f_19796_.m_188503_(2401) + 3600);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return super.m_6071_(p_34394_, p_34395_);
    }

    @Override
    protected boolean m_7593_() {
        return false;
    }

    @Override
    public boolean m_6785_(double p_34414_) {
        return !this.m_34408_() && this.f_34363_ == 0;
    }

    public boolean m_34408_() {
        return this.m_20088_().m_135370_(f_34359_);
    }

    private void m_34383_(@Nullable UUID p_34384_, int p_34385_) {
        this.f_34360_ = p_34384_;
        this.f_34365_ = p_34385_;
        this.m_20088_().m_135381_(f_34359_, true);
        this.m_21195_(MobEffects.f_19613_);
        this.m_7292_(new MobEffectInstance(MobEffects.f_19600_, p_34385_, Math.min(this.f_19853_.m_46791_().m_19028_() - 1, 0)));
        this.f_19853_.m_7605_(this, (byte)16);
    }

    @Override
    public void m_7822_(byte p_34372_) {
        if (p_34372_ == 16) {
            if (!this.m_20067_()) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20188_(), this.m_20189_(), SoundEvents.f_12644_, this.m_5720_(), 1.0f + this.f_19796_.m_188501_(), this.f_19796_.m_188501_() * 0.7f + 0.3f, false);
            }
            return;
        }
        super.m_7822_(p_34372_);
    }

    private void m_34398_(ServerLevel p_34399_) {
        Player $$5;
        Villager $$1 = this.m_21406_(EntityType.f_20492_, false);
        for (EquipmentSlot $$2 : EquipmentSlot.values()) {
            ItemStack $$3 = this.m_6844_($$2);
            if ($$3.m_41619_()) continue;
            if (EnchantmentHelper.m_44920_($$3)) {
                $$1.m_141942_($$2.m_20749_() + 300).m_142104_($$3);
                continue;
            }
            double $$4 = this.m_21519_($$2);
            if (!($$4 > 1.0)) continue;
            this.m_19983_($$3);
        }
        $$1.m_34375_(this.m_7141_());
        if (this.f_34361_ != null) {
            $$1.m_35455_(this.f_34361_);
        }
        if (this.f_34362_ != null) {
            $$1.m_35476_(new MerchantOffers(this.f_34362_));
        }
        $$1.m_35546_(this.f_34363_);
        $$1.m_6518_(p_34399_, p_34399_.m_6436_($$1.m_20183_()), MobSpawnType.CONVERSION, null, null);
        $$1.m_35483_(p_34399_);
        if (this.f_34360_ != null && ($$5 = p_34399_.m_46003_(this.f_34360_)) instanceof ServerPlayer) {
            CriteriaTriggers.f_10584_.m_24274_((ServerPlayer)$$5, this, $$1);
            p_34399_.m_8670_(ReputationEventType.f_26985_, $$5, $$1);
        }
        $$1.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
        if (!this.m_20067_()) {
            p_34399_.m_5898_(null, 1027, this.m_20183_(), 0);
        }
    }

    private int m_34410_() {
        int $$0 = 1;
        if (this.f_19796_.m_188501_() < 0.01f) {
            int $$1 = 0;
            BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
            for (int $$3 = (int)this.m_20185_() - 4; $$3 < (int)this.m_20185_() + 4 && $$1 < 14; ++$$3) {
                for (int $$4 = (int)this.m_20186_() - 4; $$4 < (int)this.m_20186_() + 4 && $$1 < 14; ++$$4) {
                    for (int $$5 = (int)this.m_20189_() - 4; $$5 < (int)this.m_20189_() + 4 && $$1 < 14; ++$$5) {
                        BlockState $$6 = this.f_19853_.m_8055_($$2.m_122178_($$3, $$4, $$5));
                        if (!$$6.m_60713_(Blocks.f_50183_) && !($$6.m_60734_() instanceof BedBlock)) continue;
                        if (this.f_19796_.m_188501_() < 0.3f) {
                            ++$$0;
                        }
                        ++$$1;
                    }
                }
            }
        }
        return $$0;
    }

    @Override
    public float m_6100_() {
        if (this.m_6162_()) {
            return (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 2.0f;
        }
        return (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f;
    }

    @Override
    public SoundEvent m_7515_() {
        return SoundEvents.f_12615_;
    }

    @Override
    public SoundEvent m_7975_(DamageSource p_34404_) {
        return SoundEvents.f_12646_;
    }

    @Override
    public SoundEvent m_5592_() {
        return SoundEvents.f_12645_;
    }

    @Override
    public SoundEvent m_7660_() {
        return SoundEvents.f_12647_;
    }

    @Override
    protected ItemStack m_5728_() {
        return ItemStack.f_41583_;
    }

    public void m_34411_(CompoundTag p_34412_) {
        this.f_34362_ = p_34412_;
    }

    public void m_34391_(Tag p_34392_) {
        this.f_34361_ = p_34392_;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_34378_, DifficultyInstance p_34379_, MobSpawnType p_34380_, @Nullable SpawnGroupData p_34381_, @Nullable CompoundTag p_34382_) {
        this.m_34375_(this.m_7141_().m_35567_(VillagerType.m_204073_(p_34378_.m_204166_(this.m_20183_()))));
        return super.m_6518_(p_34378_, p_34379_, p_34380_, p_34381_, p_34382_);
    }

    @Override
    public void m_34375_(VillagerData p_34376_) {
        VillagerData $$1 = this.m_7141_();
        if ($$1.m_35571_() != p_34376_.m_35571_()) {
            this.f_34362_ = null;
        }
        this.f_19804_.m_135381_(f_34364_, p_34376_);
    }

    @Override
    public VillagerData m_7141_() {
        return this.f_19804_.m_135370_(f_34364_);
    }

    public int m_149889_() {
        return this.f_34363_;
    }

    public void m_34373_(int p_34374_) {
        this.f_34363_ = p_34374_;
    }
}

