/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.npc;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.sensing.GolemSensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class Villager
extends AbstractVillager
implements ReputationEventHandler,
VillagerDataHolder {
    private static final Logger f_201976_ = LogUtils.getLogger();
    private static final EntityDataAccessor<VillagerData> f_35371_ = SynchedEntityData.m_135353_(Villager.class, EntityDataSerializers.f_135043_);
    public static final int f_149997_ = 12;
    public static final Map<Item, Integer> f_35369_ = ImmutableMap.of((Object)Items.f_42406_, (Object)4, (Object)Items.f_42620_, (Object)1, (Object)Items.f_42619_, (Object)1, (Object)Items.f_42732_, (Object)1);
    private static final int f_149999_ = 2;
    private static final Set<Item> f_35372_ = ImmutableSet.of((Object)Items.f_42406_, (Object)Items.f_42620_, (Object)Items.f_42619_, (Object)Items.f_42405_, (Object)Items.f_42404_, (Object)Items.f_42732_, (Object[])new Item[]{Items.f_42733_});
    private static final int f_150000_ = 10;
    private static final int f_150001_ = 1200;
    private static final int f_150002_ = 24000;
    private static final int f_150003_ = 25;
    private static final int f_150004_ = 10;
    private static final int f_150005_ = 5;
    private static final long f_150006_ = 24000L;
    @VisibleForTesting
    public static final float f_149998_ = 0.5f;
    private int f_35373_;
    private boolean f_35374_;
    @Nullable
    private Player f_35375_;
    private boolean f_150007_;
    private int f_35376_;
    private final GossipContainer f_35377_ = new GossipContainer();
    private long f_35378_;
    private long f_35361_;
    private int f_35362_;
    private long f_35363_;
    private int f_35364_;
    private long f_35365_;
    private boolean f_35366_;
    private static final ImmutableList<MemoryModuleType<?>> f_35367_ = ImmutableList.of(MemoryModuleType.f_26359_, MemoryModuleType.f_26360_, MemoryModuleType.f_26361_, MemoryModuleType.f_26362_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26366_, MemoryModuleType.f_26367_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26332_, MemoryModuleType.f_26370_, (Object[])new MemoryModuleType[]{MemoryModuleType.f_26371_, MemoryModuleType.f_26374_, MemoryModuleType.f_26375_, MemoryModuleType.f_26377_, MemoryModuleType.f_26379_, MemoryModuleType.f_26380_, MemoryModuleType.f_26381_, MemoryModuleType.f_26382_, MemoryModuleType.f_26323_, MemoryModuleType.f_26363_, MemoryModuleType.f_26324_, MemoryModuleType.f_26325_, MemoryModuleType.f_26326_, MemoryModuleType.f_26328_, MemoryModuleType.f_26329_, MemoryModuleType.f_26330_, MemoryModuleType.f_26327_});
    private static final ImmutableList<SensorType<? extends Sensor<? super Villager>>> f_35368_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26810_, SensorType.f_26813_, SensorType.f_26814_, SensorType.f_26815_, SensorType.f_26816_, SensorType.f_26817_, SensorType.f_26818_);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, Holder<PoiType>>> f_35370_ = ImmutableMap.of(MemoryModuleType.f_26359_, (p_219625_, p_219626_) -> p_219626_.m_203565_(PoiTypes.f_218060_), MemoryModuleType.f_26360_, (p_219622_, p_219623_) -> p_219622_.m_7141_().m_35571_().f_219628_().test((Holder<PoiType>)p_219623_), MemoryModuleType.f_26361_, (p_219619_, p_219620_) -> VillagerProfession.f_219627_.test((Holder<PoiType>)p_219620_), MemoryModuleType.f_26362_, (p_219616_, p_219617_) -> p_219617_.m_203565_(PoiTypes.f_218061_));

    public Villager(EntityType<? extends Villager> p_35381_, Level p_35382_) {
        this(p_35381_, p_35382_, VillagerType.f_35821_);
    }

    public Villager(EntityType<? extends Villager> p_35384_, Level p_35385_, VillagerType p_35386_) {
        super((EntityType<? extends AbstractVillager>)p_35384_, p_35385_);
        ((GroundPathNavigation)this.m_21573_()).m_26477_(true);
        this.m_21573_().m_7008_(true);
        this.m_21553_(true);
        this.m_34375_(this.m_7141_().m_35567_(p_35386_).m_35565_(VillagerProfession.f_35585_));
    }

    public Brain<Villager> m_6274_() {
        return super.m_6274_();
    }

    protected Brain.Provider<Villager> m_5490_() {
        return Brain.m_21923_(f_35367_, f_35368_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_35445_) {
        Brain<Villager> $$1 = this.m_5490_().m_22073_(p_35445_);
        this.m_35424_($$1);
        return $$1;
    }

    public void m_35483_(ServerLevel p_35484_) {
        Brain<Villager> $$1 = this.m_6274_();
        $$1.m_21933_(p_35484_, this);
        this.f_20939_ = $$1.m_21973_();
        this.m_35424_(this.m_6274_());
    }

    private void m_35424_(Brain<Villager> p_35425_) {
        VillagerProfession $$1 = this.m_7141_().m_35571_();
        if (this.m_6162_()) {
            p_35425_.m_21912_(Schedule.f_38014_);
            p_35425_.m_21900_(Activity.f_37981_, VillagerGoalPackages.m_24583_(0.5f));
        } else {
            p_35425_.m_21912_(Schedule.f_38015_);
            p_35425_.m_21903_(Activity.f_37980_, (ImmutableList<Pair<Integer, Behavior<Villager>>>)VillagerGoalPackages.m_24589_($$1, 0.5f), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_26360_, (Object)((Object)MemoryStatus.VALUE_PRESENT))));
        }
        p_35425_.m_21900_(Activity.f_37978_, VillagerGoalPackages.m_24585_($$1, 0.5f));
        p_35425_.m_21903_(Activity.f_37983_, (ImmutableList<Pair<Integer, Behavior<Villager>>>)VillagerGoalPackages.m_24595_($$1, 0.5f), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of((Object)Pair.of(MemoryModuleType.f_26362_, (Object)((Object)MemoryStatus.VALUE_PRESENT))));
        p_35425_.m_21900_(Activity.f_37982_, VillagerGoalPackages.m_24592_($$1, 0.5f));
        p_35425_.m_21900_(Activity.f_37979_, VillagerGoalPackages.m_24598_($$1, 0.5f));
        p_35425_.m_21900_(Activity.f_37984_, VillagerGoalPackages.m_24601_($$1, 0.5f));
        p_35425_.m_21900_(Activity.f_37986_, VillagerGoalPackages.m_24604_($$1, 0.5f));
        p_35425_.m_21900_(Activity.f_37985_, VillagerGoalPackages.m_24607_($$1, 0.5f));
        p_35425_.m_21900_(Activity.f_37987_, VillagerGoalPackages.m_24610_($$1, 0.5f));
        p_35425_.m_21930_((Set<Activity>)ImmutableSet.of((Object)Activity.f_37978_));
        p_35425_.m_21944_(Activity.f_37979_);
        p_35425_.m_21889_(Activity.f_37979_);
        p_35425_.m_21862_(this.f_19853_.m_46468_(), this.f_19853_.m_46467_());
    }

    @Override
    protected void m_30232_() {
        super.m_30232_();
        if (this.f_19853_ instanceof ServerLevel) {
            this.m_35483_((ServerLevel)this.f_19853_);
        }
    }

    public static AttributeSupplier.Builder m_35503_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22279_, 0.5).m_22268_(Attributes.f_22277_, 48.0);
    }

    public boolean m_35504_() {
        return this.f_35366_;
    }

    @Override
    protected void m_8024_() {
        Raid $$0;
        this.f_19853_.m_46473_().m_6180_("villagerBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        if (this.f_35366_) {
            this.f_35366_ = false;
        }
        if (!this.m_35306_() && this.f_35373_ > 0) {
            --this.f_35373_;
            if (this.f_35373_ <= 0) {
                if (this.f_35374_) {
                    this.m_35528_();
                    this.f_35374_ = false;
                }
                this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 200, 0));
            }
        }
        if (this.f_35375_ != null && this.f_19853_ instanceof ServerLevel) {
            ((ServerLevel)this.f_19853_).m_8670_(ReputationEventType.f_26989_, this.f_35375_, this);
            this.f_19853_.m_7605_(this, (byte)14);
            this.f_35375_ = null;
        }
        if (!this.m_21525_() && this.f_19796_.m_188503_(100) == 0 && ($$0 = ((ServerLevel)this.f_19853_).m_8832_(this.m_20183_())) != null && $$0.m_37782_() && !$$0.m_37706_()) {
            this.f_19853_.m_7605_(this, (byte)42);
        }
        if (this.m_7141_().m_35571_() == VillagerProfession.f_35585_ && this.m_35306_()) {
            this.m_7996_();
        }
        super.m_8024_();
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.m_35303_() > 0) {
            this.m_35319_(this.m_35303_() - 1);
        }
        this.m_35530_();
    }

    @Override
    public InteractionResult m_6071_(Player p_35472_, InteractionHand p_35473_) {
        ItemStack $$2 = p_35472_.m_21120_(p_35473_);
        if (!$$2.m_150930_(Items.f_42601_) && this.m_6084_() && !this.m_35306_() && !this.m_5803_()) {
            if (this.m_6162_()) {
                this.m_35518_();
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            boolean $$3 = this.m_6616_().isEmpty();
            if (p_35473_ == InteractionHand.MAIN_HAND) {
                if ($$3 && !this.f_19853_.f_46443_) {
                    this.m_35518_();
                }
                p_35472_.m_36220_(Stats.f_12940_);
            }
            if ($$3) {
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            if (!this.f_19853_.f_46443_ && !this.f_35261_.isEmpty()) {
                this.m_35536_(p_35472_);
            }
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        return super.m_6071_(p_35472_, p_35473_);
    }

    private void m_35518_() {
        this.m_35319_(40);
        if (!this.f_19853_.m_5776_()) {
            this.m_5496_(SoundEvents.f_12507_, this.m_6121_(), this.m_6100_());
        }
    }

    private void m_35536_(Player p_35537_) {
        this.m_35540_(p_35537_);
        this.m_7189_(p_35537_);
        this.m_45301_(p_35537_, this.m_5446_(), this.m_7141_().m_35576_());
    }

    @Override
    public void m_7189_(@Nullable Player p_35508_) {
        boolean $$1 = this.m_7962_() != null && p_35508_ == null;
        super.m_7189_(p_35508_);
        if ($$1) {
            this.m_7996_();
        }
    }

    @Override
    protected void m_7996_() {
        super.m_7996_();
        this.m_35519_();
    }

    private void m_35519_() {
        for (MerchantOffer $$0 : this.m_6616_()) {
            $$0.m_45376_();
        }
    }

    @Override
    public boolean m_7862_() {
        return true;
    }

    @Override
    public boolean m_183595_() {
        return this.m_9236_().f_46443_;
    }

    public void m_35510_() {
        this.m_35523_();
        for (MerchantOffer $$0 : this.m_6616_()) {
            $$0.m_45372_();
        }
        this.f_35363_ = this.f_19853_.m_46467_();
        ++this.f_35364_;
    }

    private boolean m_35520_() {
        for (MerchantOffer $$0 : this.m_6616_()) {
            if (!$$0.m_45382_()) continue;
            return true;
        }
        return false;
    }

    private boolean m_35521_() {
        return this.f_35364_ == 0 || this.f_35364_ < 2 && this.f_19853_.m_46467_() > this.f_35363_ + 2400L;
    }

    public boolean m_35511_() {
        long $$0 = this.f_35363_ + 12000L;
        long $$1 = this.f_19853_.m_46467_();
        boolean $$2 = $$1 > $$0;
        long $$3 = this.f_19853_.m_46468_();
        if (this.f_35365_ > 0L) {
            long $$5 = $$3 / 24000L;
            long $$4 = this.f_35365_ / 24000L;
            $$2 |= $$5 > $$4;
        }
        this.f_35365_ = $$3;
        if ($$2) {
            this.f_35363_ = $$1;
            this.m_35531_();
        }
        return this.m_35521_() && this.m_35520_();
    }

    private void m_35522_() {
        int $$0 = 2 - this.f_35364_;
        if ($$0 > 0) {
            for (MerchantOffer $$1 : this.m_6616_()) {
                $$1.m_45372_();
            }
        }
        for (int $$2 = 0; $$2 < $$0; ++$$2) {
            this.m_35523_();
        }
    }

    private void m_35523_() {
        for (MerchantOffer $$0 : this.m_6616_()) {
            $$0.m_45369_();
        }
    }

    private void m_35540_(Player p_35541_) {
        int $$1 = this.m_35532_(p_35541_);
        if ($$1 != 0) {
            for (MerchantOffer $$2 : this.m_6616_()) {
                $$2.m_45353_(-Mth.m_14143_((float)$$1 * $$2.m_45378_()));
            }
        }
        if (p_35541_.m_21023_(MobEffects.f_19595_)) {
            MobEffectInstance $$3 = p_35541_.m_21124_(MobEffects.f_19595_);
            int $$4 = $$3.m_19564_();
            for (MerchantOffer $$5 : this.m_6616_()) {
                double $$6 = 0.3 + 0.0625 * (double)$$4;
                int $$7 = (int)Math.floor($$6 * (double)$$5.m_45352_().m_41613_());
                $$5.m_45353_(-Math.max($$7, 1));
            }
        }
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_35371_, new VillagerData(VillagerType.f_35821_, VillagerProfession.f_35585_, 1));
    }

    @Override
    public void m_7380_(CompoundTag p_35481_) {
        super.m_7380_(p_35481_);
        VillagerData.f_35550_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.m_7141_()).resultOrPartial(arg_0 -> ((Logger)f_201976_).error(arg_0)).ifPresent(p_35454_ -> p_35481_.m_128365_("VillagerData", (Tag)p_35454_));
        p_35481_.m_128344_("FoodLevel", (byte)this.f_35376_);
        p_35481_.m_128365_("Gossips", (Tag)this.f_35377_.m_26179_(NbtOps.f_128958_).getValue());
        p_35481_.m_128405_("Xp", this.f_35362_);
        p_35481_.m_128356_("LastRestock", this.f_35363_);
        p_35481_.m_128356_("LastGossipDecay", this.f_35361_);
        p_35481_.m_128405_("RestocksToday", this.f_35364_);
        if (this.f_35366_) {
            p_35481_.m_128379_("AssignProfessionWhenSpawned", true);
        }
    }

    @Override
    public void m_7378_(CompoundTag p_35451_) {
        super.m_7378_(p_35451_);
        if (p_35451_.m_128425_("VillagerData", 10)) {
            DataResult $$1 = VillagerData.f_35550_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_35451_.m_128423_("VillagerData")));
            $$1.resultOrPartial(arg_0 -> ((Logger)f_201976_).error(arg_0)).ifPresent(this::m_34375_);
        }
        if (p_35451_.m_128425_("Offers", 10)) {
            this.f_35261_ = new MerchantOffers(p_35451_.m_128469_("Offers"));
        }
        if (p_35451_.m_128425_("FoodLevel", 1)) {
            this.f_35376_ = p_35451_.m_128445_("FoodLevel");
        }
        ListTag $$2 = p_35451_.m_128437_("Gossips", 10);
        this.f_35377_.m_26177_(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)$$2));
        if (p_35451_.m_128425_("Xp", 3)) {
            this.f_35362_ = p_35451_.m_128451_("Xp");
        }
        this.f_35363_ = p_35451_.m_128454_("LastRestock");
        this.f_35361_ = p_35451_.m_128454_("LastGossipDecay");
        this.m_21553_(true);
        if (this.f_19853_ instanceof ServerLevel) {
            this.m_35483_((ServerLevel)this.f_19853_);
        }
        this.f_35364_ = p_35451_.m_128451_("RestocksToday");
        if (p_35451_.m_128441_("AssignProfessionWhenSpawned")) {
            this.f_35366_ = p_35451_.m_128471_("AssignProfessionWhenSpawned");
        }
    }

    @Override
    public boolean m_6785_(double p_35535_) {
        return false;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        if (this.m_5803_()) {
            return null;
        }
        if (this.m_35306_()) {
            return SoundEvents.f_12508_;
        }
        return SoundEvents.f_12503_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_35498_) {
        return SoundEvents.f_12506_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12505_;
    }

    public void m_35512_() {
        SoundEvent $$0 = this.m_7141_().m_35571_().f_35604_();
        if ($$0 != null) {
            this.m_5496_($$0, this.m_6121_(), this.m_6100_());
        }
    }

    @Override
    public void m_34375_(VillagerData p_35437_) {
        VillagerData $$1 = this.m_7141_();
        if ($$1.m_35571_() != p_35437_.m_35571_()) {
            this.f_35261_ = null;
        }
        this.f_19804_.m_135381_(f_35371_, p_35437_);
    }

    @Override
    public VillagerData m_7141_() {
        return this.f_19804_.m_135370_(f_35371_);
    }

    @Override
    protected void m_8058_(MerchantOffer p_35475_) {
        int $$1 = 3 + this.f_19796_.m_188503_(4);
        this.f_35362_ += p_35475_.m_45379_();
        this.f_35375_ = this.m_7962_();
        if (this.m_35527_()) {
            this.f_35373_ = 40;
            this.f_35374_ = true;
            $$1 += 5;
        }
        if (p_35475_.m_45383_()) {
            this.f_19853_.m_7967_(new ExperienceOrb(this.f_19853_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), $$1));
        }
    }

    public void m_150015_(boolean p_150016_) {
        this.f_150007_ = p_150016_;
    }

    public boolean m_150014_() {
        return this.f_150007_;
    }

    @Override
    public void m_6703_(@Nullable LivingEntity p_35423_) {
        if (p_35423_ != null && this.f_19853_ instanceof ServerLevel) {
            ((ServerLevel)this.f_19853_).m_8670_(ReputationEventType.f_26987_, p_35423_, this);
            if (this.m_6084_() && p_35423_ instanceof Player) {
                this.f_19853_.m_7605_(this, (byte)13);
            }
        }
        super.m_6703_(p_35423_);
    }

    @Override
    public void m_6667_(DamageSource p_35419_) {
        f_201976_.info("Villager {} died, message: '{}'", (Object)this, (Object)p_35419_.m_6157_(this).getString());
        Entity $$1 = p_35419_.m_7639_();
        if ($$1 != null) {
            this.m_35420_($$1);
        }
        this.m_35524_();
        super.m_6667_(p_35419_);
    }

    private void m_35524_() {
        this.m_35428_(MemoryModuleType.f_26359_);
        this.m_35428_(MemoryModuleType.f_26360_);
        this.m_35428_(MemoryModuleType.f_26361_);
        this.m_35428_(MemoryModuleType.f_26362_);
    }

    /*
     * WARNING - void declaration
     */
    private void m_35420_(Entity p_35421_) {
        void $$2;
        Level level = this.f_19853_;
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel $$1 = (ServerLevel)level;
        Optional<NearestVisibleLivingEntities> $$3 = this.f_20939_.m_21952_(MemoryModuleType.f_148205_);
        if ($$3.isEmpty()) {
            return;
        }
        $$3.get().m_186123_(ReputationEventHandler.class::isInstance).forEach(arg_0 -> Villager.m_186294_((ServerLevel)$$2, p_35421_, arg_0));
    }

    public void m_35428_(MemoryModuleType<GlobalPos> p_35429_) {
        if (!(this.f_19853_ instanceof ServerLevel)) {
            return;
        }
        MinecraftServer $$1 = ((ServerLevel)this.f_19853_).m_7654_();
        this.f_20939_.m_21952_(p_35429_).ifPresent(p_186306_ -> {
            ServerLevel $$3 = $$1.m_129880_(p_186306_.m_122640_());
            if ($$3 == null) {
                return;
            }
            PoiManager $$4 = $$3.m_8904_();
            Optional<Holder<PoiType>> $$5 = $$4.m_27177_(p_186306_.m_122646_());
            BiPredicate<Villager, Holder<PoiType>> $$6 = f_35370_.get(p_35429_);
            if ($$5.isPresent() && $$6.test(this, $$5.get())) {
                $$4.m_27154_(p_186306_.m_122646_());
                DebugPackets.m_133719_($$3, p_186306_.m_122646_());
            }
        });
    }

    @Override
    public boolean m_35506_() {
        return this.f_35376_ + this.m_35529_() >= 12 && this.m_146764_() == 0;
    }

    private boolean m_35525_() {
        return this.f_35376_ < 12;
    }

    private void m_35526_() {
        if (!this.m_35525_() || this.m_35529_() == 0) {
            return;
        }
        for (int $$0 = 0; $$0 < this.m_35311_().m_6643_(); ++$$0) {
            int $$3;
            Integer $$2;
            ItemStack $$1 = this.m_35311_().m_8020_($$0);
            if ($$1.m_41619_() || ($$2 = f_35369_.get($$1.m_41720_())) == null) continue;
            for (int $$4 = $$3 = $$1.m_41613_(); $$4 > 0; --$$4) {
                this.f_35376_ += $$2.intValue();
                this.m_35311_().m_7407_($$0, 1);
                if (this.m_35525_()) continue;
                return;
            }
        }
    }

    public int m_35532_(Player p_35533_) {
        return this.f_35377_.m_26195_(p_35533_.m_20148_(), p_186302_ -> true);
    }

    private void m_35548_(int p_35549_) {
        this.f_35376_ -= p_35549_;
    }

    public void m_35513_() {
        this.m_35526_();
        this.m_35548_(12);
    }

    public void m_35476_(MerchantOffers p_35477_) {
        this.f_35261_ = p_35477_;
    }

    private boolean m_35527_() {
        int $$0 = this.m_7141_().m_35576_();
        return VillagerData.m_35582_($$0) && this.f_35362_ >= VillagerData.m_35577_($$0);
    }

    private void m_35528_() {
        this.m_34375_(this.m_7141_().m_35561_(this.m_7141_().m_35576_() + 1));
        this.m_7604_();
    }

    @Override
    protected Component m_5677_() {
        return Component.m_237115_(this.m_6095_().m_20675_() + "." + Registry.f_122869_.m_7981_(this.m_7141_().m_35571_()).m_135815_());
    }

    @Override
    public void m_7822_(byte p_35391_) {
        if (p_35391_ == 12) {
            this.m_35287_(ParticleTypes.f_123750_);
        } else if (p_35391_ == 13) {
            this.m_35287_(ParticleTypes.f_123792_);
        } else if (p_35391_ == 14) {
            this.m_35287_(ParticleTypes.f_123748_);
        } else if (p_35391_ == 42) {
            this.m_35287_(ParticleTypes.f_123769_);
        } else {
            super.m_7822_(p_35391_);
        }
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_35439_, DifficultyInstance p_35440_, MobSpawnType p_35441_, @Nullable SpawnGroupData p_35442_, @Nullable CompoundTag p_35443_) {
        if (p_35441_ == MobSpawnType.BREEDING) {
            this.m_34375_(this.m_7141_().m_35565_(VillagerProfession.f_35585_));
        }
        if (p_35441_ == MobSpawnType.COMMAND || p_35441_ == MobSpawnType.SPAWN_EGG || p_35441_ == MobSpawnType.SPAWNER || p_35441_ == MobSpawnType.DISPENSER) {
            this.m_34375_(this.m_7141_().m_35567_(VillagerType.m_204073_(p_35439_.m_204166_(this.m_20183_()))));
        }
        if (p_35441_ == MobSpawnType.STRUCTURE) {
            this.f_35366_ = true;
        }
        return super.m_6518_(p_35439_, p_35440_, p_35441_, p_35442_, p_35443_);
    }

    @Override
    public Villager m_142606_(ServerLevel p_150012_, AgeableMob p_150013_) {
        VillagerType $$5;
        double $$2 = this.f_19796_.m_188500_();
        if ($$2 < 0.5) {
            VillagerType $$3 = VillagerType.m_204073_(p_150012_.m_204166_(this.m_20183_()));
        } else if ($$2 < 0.75) {
            VillagerType $$4 = this.m_7141_().m_35560_();
        } else {
            $$5 = ((Villager)p_150013_).m_7141_().m_35560_();
        }
        Villager $$6 = new Villager(EntityType.f_20492_, p_150012_, $$5);
        $$6.m_6518_(p_150012_, p_150012_.m_6436_($$6.m_20183_()), MobSpawnType.BREEDING, null, null);
        return $$6;
    }

    @Override
    public void m_8038_(ServerLevel p_35409_, LightningBolt p_35410_) {
        if (p_35409_.m_46791_() != Difficulty.PEACEFUL) {
            f_201976_.info("Villager {} was struck by lightning {}.", (Object)this, (Object)p_35410_);
            Witch $$2 = EntityType.f_20495_.m_20615_(p_35409_);
            $$2.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
            $$2.m_6518_(p_35409_, p_35409_.m_6436_($$2.m_20183_()), MobSpawnType.CONVERSION, null, null);
            $$2.m_21557_(this.m_21525_());
            if (this.m_8077_()) {
                $$2.m_6593_(this.m_7770_());
                $$2.m_20340_(this.m_20151_());
            }
            $$2.m_21530_();
            p_35409_.m_47205_($$2);
            this.m_35524_();
            this.m_146870_();
        } else {
            super.m_8038_(p_35409_, p_35410_);
        }
    }

    @Override
    protected void m_7581_(ItemEntity p_35467_) {
        InventoryCarrier.m_219611_(this, this, p_35467_);
    }

    @Override
    public boolean m_7243_(ItemStack p_35543_) {
        Item $$1 = p_35543_.m_41720_();
        return (f_35372_.contains($$1) || this.m_7141_().m_35571_().f_35602_().contains((Object)$$1)) && this.m_35311_().m_19183_(p_35543_);
    }

    public boolean m_35514_() {
        return this.m_35529_() >= 24;
    }

    public boolean m_35515_() {
        return this.m_35529_() < 12;
    }

    private int m_35529_() {
        SimpleContainer $$0 = this.m_35311_();
        return f_35369_.entrySet().stream().mapToInt(p_186300_ -> $$0.m_18947_((Item)p_186300_.getKey()) * (Integer)p_186300_.getValue()).sum();
    }

    public boolean m_35516_() {
        return this.m_35311_().m_18949_((Set<Item>)ImmutableSet.of((Object)Items.f_42404_, (Object)Items.f_42620_, (Object)Items.f_42619_, (Object)Items.f_42733_));
    }

    @Override
    protected void m_7604_() {
        VillagerData $$0 = this.m_7141_();
        Int2ObjectMap<VillagerTrades.ItemListing[]> $$1 = VillagerTrades.f_35627_.get($$0.m_35571_());
        if ($$1 == null || $$1.isEmpty()) {
            return;
        }
        VillagerTrades.ItemListing[] $$2 = (VillagerTrades.ItemListing[])$$1.get($$0.m_35576_());
        if ($$2 == null) {
            return;
        }
        MerchantOffers $$3 = this.m_6616_();
        this.m_35277_($$3, $$2, 2);
    }

    public void m_35411_(ServerLevel p_35412_, Villager p_35413_, long p_35414_) {
        if (p_35414_ >= this.f_35378_ && p_35414_ < this.f_35378_ + 1200L || p_35414_ >= p_35413_.f_35378_ && p_35414_ < p_35413_.f_35378_ + 1200L) {
            return;
        }
        this.f_35377_.m_217762_(p_35413_.f_35377_, this.f_19796_, 10);
        this.f_35378_ = p_35414_;
        p_35413_.f_35378_ = p_35414_;
        this.m_35397_(p_35412_, p_35414_, 5);
    }

    private void m_35530_() {
        long $$0 = this.f_19853_.m_46467_();
        if (this.f_35361_ == 0L) {
            this.f_35361_ = $$0;
            return;
        }
        if ($$0 < this.f_35361_ + 24000L) {
            return;
        }
        this.f_35377_.m_26198_();
        this.f_35361_ = $$0;
    }

    public void m_35397_(ServerLevel p_35398_, long p_35399_, int p_35400_) {
        if (!this.m_35392_(p_35399_)) {
            return;
        }
        AABB $$3 = this.m_20191_().m_82377_(10.0, 10.0, 10.0);
        List<Villager> $$4 = p_35398_.m_45976_(Villager.class, $$3);
        List $$5 = $$4.stream().filter(p_186293_ -> p_186293_.m_35392_(p_35399_)).limit(5L).collect(Collectors.toList());
        if ($$5.size() < p_35400_) {
            return;
        }
        if (!SpawnUtil.m_216403_(EntityType.f_20460_, MobSpawnType.MOB_SUMMONED, p_35398_, this.m_20183_(), 10, 8, 6, SpawnUtil.Strategy.f_216412_).isPresent()) {
            return;
        }
        $$4.forEach(GolemSensor::m_26649_);
    }

    public boolean m_35392_(long p_35393_) {
        if (!this.m_35461_(this.f_19853_.m_46467_())) {
            return false;
        }
        return !this.f_20939_.m_21874_(MemoryModuleType.f_26327_);
    }

    @Override
    public void m_6814_(ReputationEventType p_35431_, Entity p_35432_) {
        if (p_35431_ == ReputationEventType.f_26985_) {
            this.f_35377_.m_26191_(p_35432_.m_20148_(), GossipType.MAJOR_POSITIVE, 20);
            this.f_35377_.m_26191_(p_35432_.m_20148_(), GossipType.MINOR_POSITIVE, 25);
        } else if (p_35431_ == ReputationEventType.f_26989_) {
            this.f_35377_.m_26191_(p_35432_.m_20148_(), GossipType.TRADING, 2);
        } else if (p_35431_ == ReputationEventType.f_26987_) {
            this.f_35377_.m_26191_(p_35432_.m_20148_(), GossipType.MINOR_NEGATIVE, 25);
        } else if (p_35431_ == ReputationEventType.f_26988_) {
            this.f_35377_.m_26191_(p_35432_.m_20148_(), GossipType.MAJOR_NEGATIVE, 25);
        }
    }

    @Override
    public int m_7809_() {
        return this.f_35362_;
    }

    public void m_35546_(int p_35547_) {
        this.f_35362_ = p_35547_;
    }

    private void m_35531_() {
        this.m_35522_();
        this.f_35364_ = 0;
    }

    public GossipContainer m_35517_() {
        return this.f_35377_;
    }

    public void m_35455_(Tag p_35456_) {
        this.f_35377_.m_26177_(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_35456_));
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public void m_5802_(BlockPos p_35479_) {
        super.m_5802_(p_35479_);
        this.f_20939_.m_21879_(MemoryModuleType.f_26328_, this.f_19853_.m_46467_());
        this.f_20939_.m_21936_(MemoryModuleType.f_26370_);
        this.f_20939_.m_21936_(MemoryModuleType.f_26326_);
    }

    @Override
    public void m_5796_() {
        super.m_5796_();
        this.f_20939_.m_21879_(MemoryModuleType.f_26329_, this.f_19853_.m_46467_());
    }

    private boolean m_35461_(long p_35462_) {
        Optional<Long> $$1 = this.f_20939_.m_21952_(MemoryModuleType.f_26328_);
        if ($$1.isPresent()) {
            return p_35462_ - $$1.get() < 24000L;
        }
        return false;
    }

    @Override
    public /* synthetic */ AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this.m_142606_(serverLevel, ageableMob);
    }

    private static /* synthetic */ void m_186294_(ServerLevel p_186295_, Entity p_186296_, LivingEntity p_186297_) {
        p_186295_.m_8670_(ReputationEventType.f_26988_, p_186296_, (ReputationEventHandler)((Object)p_186297_));
    }
}

