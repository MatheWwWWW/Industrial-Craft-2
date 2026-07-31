/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LerpingModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class Axolotl
extends Animal
implements LerpingModel,
Bucketable {
    private static final Logger f_212250_ = LogUtils.getLogger();
    public static final int f_149090_ = 200;
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Axolotl>>> f_149091_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26822_, SensorType.f_26814_, SensorType.f_148315_, SensorType.f_148316_);
    protected static final ImmutableList<? extends MemoryModuleType<?>> f_149092_ = ImmutableList.of(MemoryModuleType.f_26375_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26371_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26377_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_, MemoryModuleType.f_26331_, (Object[])new MemoryModuleType[]{MemoryModuleType.f_26382_, MemoryModuleType.f_148195_, MemoryModuleType.f_148194_, MemoryModuleType.f_148196_, MemoryModuleType.f_148197_, MemoryModuleType.f_148198_, MemoryModuleType.f_148201_, MemoryModuleType.f_217768_});
    private static final EntityDataAccessor<Integer> f_149096_ = SynchedEntityData.m_135353_(Axolotl.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_149097_ = SynchedEntityData.m_135353_(Axolotl.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_149098_ = SynchedEntityData.m_135353_(Axolotl.class, EntityDataSerializers.f_135035_);
    public static final double f_149093_ = 20.0;
    public static final int f_149094_ = 1200;
    private static final int f_149099_ = 6000;
    public static final String f_149095_ = "Variant";
    private static final int f_149100_ = 1800;
    private static final int f_181147_ = 2400;
    private final Map<String, Vector3f> f_149101_ = Maps.newHashMap();
    private static final int f_149102_ = 100;

    public Axolotl(EntityType<? extends Axolotl> p_149105_, Level p_149106_) {
        super((EntityType<? extends Animal>)p_149105_, p_149106_);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.f_21342_ = new AxolotlMoveControl(this);
        this.f_21365_ = new AxolotlLookControl(this, 20);
        this.f_19793_ = 1.0f;
    }

    @Override
    public Map<String, Vector3f> m_142115_() {
        return this.f_149101_;
    }

    @Override
    public float m_5610_(BlockPos p_149140_, LevelReader p_149141_) {
        return 0.0f;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_149096_, 0);
        this.f_19804_.m_135372_(f_149097_, false);
        this.f_19804_.m_135372_(f_149098_, false);
    }

    @Override
    public void m_7380_(CompoundTag p_149158_) {
        super.m_7380_(p_149158_);
        p_149158_.m_128405_(f_149095_, this.m_149179_().m_149242_());
        p_149158_.m_128379_("FromBucket", this.m_27487_());
    }

    @Override
    public void m_7378_(CompoundTag p_149145_) {
        super.m_7378_(p_149145_);
        this.m_149117_(Variant.f_149230_[p_149145_.m_128451_(f_149095_)]);
        this.m_27497_(p_149145_.m_128471_("FromBucket"));
    }

    @Override
    public void m_8032_() {
        if (this.m_149175_()) {
            return;
        }
        super.m_8032_();
    }

    @Override
    public SpawnGroupData m_6518_(ServerLevelAccessor p_149132_, DifficultyInstance p_149133_, MobSpawnType p_149134_, @Nullable SpawnGroupData p_149135_, @Nullable CompoundTag p_149136_) {
        boolean $$5 = false;
        if (p_149134_ == MobSpawnType.BUCKET) {
            return p_149135_;
        }
        RandomSource $$6 = p_149132_.m_213780_();
        if (p_149135_ instanceof AxolotlGroupData) {
            if (((AxolotlGroupData)p_149135_).m_146777_() >= 2) {
                $$5 = true;
            }
        } else {
            p_149135_ = new AxolotlGroupData(Variant.m_218448_($$6), Variant.m_218448_($$6));
        }
        this.m_149117_(((AxolotlGroupData)p_149135_).m_218446_($$6));
        if ($$5) {
            this.m_146762_(-24000);
        }
        return super.m_6518_(p_149132_, p_149133_, p_149134_, p_149135_, p_149136_);
    }

    @Override
    public void m_6075_() {
        int $$0 = this.m_20146_();
        super.m_6075_();
        if (!this.m_21525_()) {
            this.m_149193_($$0);
        }
    }

    protected void m_149193_(int p_149194_) {
        if (this.m_6084_() && !this.m_20071_()) {
            this.m_20301_(p_149194_ - 1);
            if (this.m_20146_() == -20) {
                this.m_20301_(0);
                this.m_6469_(DamageSource.f_19324_, 2.0f);
            }
        } else {
            this.m_20301_(this.m_6062_());
        }
    }

    public void m_149177_() {
        int $$0 = this.m_20146_() + 1800;
        this.m_20301_(Math.min($$0, this.m_6062_()));
    }

    @Override
    public int m_6062_() {
        return 6000;
    }

    public Variant m_149179_() {
        return Variant.f_149230_[this.f_19804_.m_135370_(f_149096_)];
    }

    private void m_149117_(Variant p_149118_) {
        this.f_19804_.m_135381_(f_149096_, p_149118_.m_149242_());
    }

    private static boolean m_218435_(RandomSource p_218436_) {
        return p_218436_.m_188503_(1200) == 0;
    }

    @Override
    public boolean m_6914_(LevelReader p_149130_) {
        return p_149130_.m_45784_(this);
    }

    @Override
    public boolean m_6040_() {
        return true;
    }

    @Override
    public boolean m_6063_() {
        return false;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21644_;
    }

    public void m_149198_(boolean p_149199_) {
        this.f_19804_.m_135381_(f_149097_, p_149199_);
    }

    public boolean m_149175_() {
        return this.f_19804_.m_135370_(f_149097_);
    }

    @Override
    public boolean m_27487_() {
        return this.f_19804_.m_135370_(f_149098_);
    }

    @Override
    public void m_27497_(boolean p_149196_) {
        this.f_19804_.m_135381_(f_149098_, p_149196_);
    }

    @Override
    @Nullable
    public AgeableMob m_142606_(ServerLevel p_149112_, AgeableMob p_149113_) {
        Axolotl $$2 = EntityType.f_147039_.m_20615_(p_149112_);
        if ($$2 != null) {
            Variant $$4;
            if (Axolotl.m_218435_(this.f_19796_)) {
                Variant $$3 = Variant.m_218453_(this.f_19796_);
            } else {
                $$4 = this.f_19796_.m_188499_() ? this.m_149179_() : ((Axolotl)p_149113_).m_149179_();
            }
            $$2.m_149117_($$4);
            $$2.m_21530_();
        }
        return $$2;
    }

    @Override
    public double m_142593_(LivingEntity p_149185_) {
        return 1.5 + (double)p_149185_.m_20205_() * 2.0;
    }

    @Override
    public boolean m_6898_(ItemStack p_149189_) {
        return p_149189_.m_204117_(ItemTags.f_144321_);
    }

    @Override
    public boolean m_6573_(Player p_149122_) {
        return true;
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("axolotlBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("axolotlActivityUpdate");
        AxolotlAi.m_149292_(this);
        this.f_19853_.m_46473_().m_7238_();
        if (!this.m_21525_()) {
            Optional<Integer> $$0 = this.m_6274_().m_21952_(MemoryModuleType.f_148195_);
            this.m_149198_($$0.isPresent() && $$0.get() > 0);
        }
    }

    public static AttributeSupplier.Builder m_149176_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 14.0).m_22268_(Attributes.f_22279_, 1.0).m_22268_(Attributes.f_22281_, 2.0);
    }

    @Override
    protected PathNavigation m_6037_(Level p_149128_) {
        return new AmphibiousPathNavigation(this, p_149128_);
    }

    @Override
    public boolean m_7327_(Entity p_149201_) {
        boolean $$1 = p_149201_.m_6469_(DamageSource.m_19370_(this), (int)this.m_21133_(Attributes.f_22281_));
        if ($$1) {
            this.m_19970_(this, p_149201_);
            this.m_5496_(SoundEvents.f_144061_, 1.0f, 1.0f);
        }
        return $$1;
    }

    @Override
    public boolean m_6469_(DamageSource p_149115_, float p_149116_) {
        float $$2 = this.m_21223_();
        if (!(this.f_19853_.f_46443_ || this.m_21525_() || this.f_19853_.f_46441_.m_188503_(3) != 0 || !((float)this.f_19853_.f_46441_.m_188503_(3) < p_149116_) && !($$2 / this.m_21233_() < 0.5f) || !(p_149116_ < $$2) || !this.m_20069_() || p_149115_.m_7639_() == null && p_149115_.m_7640_() == null || this.m_149175_())) {
            this.f_20939_.m_21879_(MemoryModuleType.f_148195_, 200);
        }
        return super.m_6469_(p_149115_, p_149116_);
    }

    @Override
    protected float m_6431_(Pose p_149152_, EntityDimensions p_149153_) {
        return p_149153_.f_20378_ * 0.655f;
    }

    @Override
    public int m_8132_() {
        return 1;
    }

    @Override
    public int m_8085_() {
        return 1;
    }

    @Override
    public InteractionResult m_6071_(Player p_149155_, InteractionHand p_149156_) {
        return Bucketable.m_148828_(p_149155_, p_149156_, this).orElse(super.m_6071_(p_149155_, p_149156_));
    }

    @Override
    public void m_6872_(ItemStack p_149187_) {
        Bucketable.m_148822_(this, p_149187_);
        CompoundTag $$1 = p_149187_.m_41784_();
        $$1.m_128405_(f_149095_, this.m_149179_().m_149242_());
        $$1.m_128405_("Age", this.m_146764_());
        Brain<Axolotl> $$2 = this.m_6274_();
        if ($$2.m_21874_(MemoryModuleType.f_148201_)) {
            $$1.m_128356_("HuntingCooldown", $$2.m_147341_(MemoryModuleType.f_148201_));
        }
    }

    @Override
    public void m_142278_(CompoundTag p_149163_) {
        Bucketable.m_148825_(this, p_149163_);
        int $$1 = p_149163_.m_128451_(f_149095_);
        if ($$1 >= 0 && $$1 < Variant.f_149230_.length) {
            this.m_149117_(Variant.f_149230_[$$1]);
        } else {
            f_212250_.error("Invalid variant: {}", (Object)$$1);
        }
        if (p_149163_.m_128441_("Age")) {
            this.m_146762_(p_149163_.m_128451_("Age"));
        }
        if (p_149163_.m_128441_("HuntingCooldown")) {
            this.m_6274_().m_21882_(MemoryModuleType.f_148201_, true, p_149163_.m_128454_("HuntingCooldown"));
        }
    }

    @Override
    public ItemStack m_28282_() {
        return new ItemStack(Items.f_151057_);
    }

    @Override
    public SoundEvent m_142623_() {
        return SoundEvents.f_144077_;
    }

    @Override
    public boolean m_142066_() {
        return !this.m_149175_() && super.m_142066_();
    }

    public static void m_218443_(Axolotl p_218444_, LivingEntity p_218445_) {
        Entity $$4;
        DamageSource $$3;
        Level $$2 = p_218444_.f_19853_;
        if (p_218445_.m_21224_() && ($$3 = p_218445_.m_21225_()) != null && ($$4 = $$3.m_7639_()) != null && $$4.m_6095_() == EntityType.f_20532_) {
            Player $$5 = (Player)$$4;
            List<Player> $$6 = $$2.m_45976_(Player.class, p_218444_.m_20191_().m_82400_(20.0));
            if ($$6.contains($$5)) {
                p_218444_.m_149173_($$5);
            }
        }
    }

    public void m_149173_(Player p_149174_) {
        int $$2;
        MobEffectInstance $$1 = p_149174_.m_21124_(MobEffects.f_19605_);
        int n = $$2 = $$1 != null ? $$1.m_19557_() : 0;
        if ($$2 < 2400) {
            $$2 = Math.min(2400, 100 + $$2);
            p_149174_.m_147207_(new MobEffectInstance(MobEffects.f_19605_, $$2, 0), this);
        }
        p_149174_.m_21195_(MobEffects.f_19599_);
    }

    @Override
    public boolean m_8023_() {
        return super.m_8023_() || this.m_27487_();
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_149161_) {
        return SoundEvents.f_144063_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_144062_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        return this.m_20069_() ? SoundEvents.f_144065_ : SoundEvents.f_144064_;
    }

    @Override
    protected SoundEvent m_5509_() {
        return SoundEvents.f_144066_;
    }

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_144067_;
    }

    protected Brain.Provider<Axolotl> m_5490_() {
        return Brain.m_21923_(f_149092_, f_149091_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_149138_) {
        return AxolotlAi.m_149290_(this.m_5490_().m_22073_(p_149138_));
    }

    public Brain<Axolotl> m_6274_() {
        return super.m_6274_();
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public void m_7023_(Vec3 p_149181_) {
        if (this.m_6142_() && this.m_20069_()) {
            this.m_19920_(this.m_6113_(), p_149181_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9));
        } else {
            super.m_7023_(p_149181_);
        }
    }

    @Override
    protected void m_142075_(Player p_149124_, InteractionHand p_149125_, ItemStack p_149126_) {
        if (p_149126_.m_150930_(Items.f_42459_)) {
            p_149124_.m_21008_(p_149125_, new ItemStack(Items.f_42447_));
        } else {
            super.m_142075_(p_149124_, p_149125_, p_149126_);
        }
    }

    @Override
    public boolean m_6785_(double p_149183_) {
        return !this.m_27487_() && !this.m_8077_();
    }

    public static boolean m_218437_(EntityType<? extends LivingEntity> p_218438_, ServerLevelAccessor p_218439_, MobSpawnType p_218440_, BlockPos p_218441_, RandomSource p_218442_) {
        return p_218439_.m_8055_(p_218441_.m_7495_()).m_204336_(BlockTags.f_184229_);
    }

    static class AxolotlMoveControl
    extends SmoothSwimmingMoveControl {
        private final Axolotl f_149213_;

        public AxolotlMoveControl(Axolotl p_149215_) {
            super(p_149215_, 85, 10, 0.1f, 0.5f, false);
            this.f_149213_ = p_149215_;
        }

        @Override
        public void m_8126_() {
            if (!this.f_149213_.m_149175_()) {
                super.m_8126_();
            }
        }
    }

    class AxolotlLookControl
    extends SmoothSwimmingLookControl {
        public AxolotlLookControl(Axolotl p_149210_, int p_149211_) {
            super(p_149210_, p_149211_);
        }

        @Override
        public void m_8128_() {
            if (!Axolotl.this.m_149175_()) {
                super.m_8128_();
            }
        }
    }

    public static final class Variant
    extends Enum<Variant> {
        public static final /* enum */ Variant LUCY = new Variant(0, "lucy", true);
        public static final /* enum */ Variant WILD = new Variant(1, "wild", true);
        public static final /* enum */ Variant GOLD = new Variant(2, "gold", true);
        public static final /* enum */ Variant CYAN = new Variant(3, "cyan", true);
        public static final /* enum */ Variant BLUE = new Variant(4, "blue", false);
        public static final Variant[] f_149230_;
        private final int f_149231_;
        private final String f_149232_;
        private final boolean f_149233_;
        private static final /* synthetic */ Variant[] $VALUES;

        public static Variant[] values() {
            return (Variant[])$VALUES.clone();
        }

        public static Variant valueOf(String p_149260_) {
            return Enum.valueOf(Variant.class, p_149260_);
        }

        private Variant(int p_149239_, String p_149240_, boolean p_149241_) {
            this.f_149231_ = p_149239_;
            this.f_149232_ = p_149240_;
            this.f_149233_ = p_149241_;
        }

        public int m_149242_() {
            return this.f_149231_;
        }

        public String m_149253_() {
            return this.f_149232_;
        }

        public static Variant m_218448_(RandomSource p_218449_) {
            return Variant.m_218450_(p_218449_, true);
        }

        public static Variant m_218453_(RandomSource p_218454_) {
            return Variant.m_218450_(p_218454_, false);
        }

        private static Variant m_218450_(RandomSource p_218451_, boolean p_218452_) {
            Variant[] $$2 = (Variant[])Arrays.stream(f_149230_).filter(p_149252_ -> p_149252_.f_149233_ == p_218452_).toArray(Variant[]::new);
            return Util.m_214670_($$2, p_218451_);
        }

        private static /* synthetic */ Variant[] m_149258_() {
            return new Variant[]{LUCY, WILD, GOLD, CYAN, BLUE};
        }

        static {
            $VALUES = Variant.m_149258_();
            f_149230_ = (Variant[])Arrays.stream(Variant.values()).sorted(Comparator.comparingInt(Variant::m_149242_)).toArray(Variant[]::new);
        }
    }

    public static class AxolotlGroupData
    extends AgeableMob.AgeableMobGroupData {
        public final Variant[] f_149202_;

        public AxolotlGroupData(Variant ... p_149204_) {
            super(false);
            this.f_149202_ = p_149204_;
        }

        public Variant m_218446_(RandomSource p_218447_) {
            return this.f_149202_[p_218447_.m_188503_(this.f_149202_.length)];
        }
    }
}

