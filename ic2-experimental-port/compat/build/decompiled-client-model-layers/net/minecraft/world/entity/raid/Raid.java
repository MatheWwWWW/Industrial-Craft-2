/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Raid {
    private static final int f_150204_ = 2;
    private static final int f_150205_ = 0;
    private static final int f_150206_ = 1;
    private static final int f_150207_ = 2;
    private static final int f_150208_ = 32;
    private static final int f_150209_ = 48000;
    private static final int f_150210_ = 3;
    private static final String f_150211_ = "block.minecraft.ominous_banner";
    private static final String f_150212_ = "event.minecraft.raid.raiders_remaining";
    public static final int f_150197_ = 16;
    private static final int f_150213_ = 40;
    private static final int f_150214_ = 300;
    public static final int f_150198_ = 2400;
    public static final int f_150199_ = 600;
    private static final int f_150215_ = 30;
    public static final int f_150200_ = 24000;
    public static final int f_150201_ = 5;
    private static final int f_150216_ = 2;
    private static final Component f_37665_ = Component.m_237115_("event.minecraft.raid");
    private static final Component f_37666_ = Component.m_237115_("event.minecraft.raid.victory");
    private static final Component f_37667_ = Component.m_237115_("event.minecraft.raid.defeat");
    private static final Component f_37668_ = f_37665_.m_6881_().m_130946_(" - ").m_7220_(f_37666_);
    private static final Component f_37669_ = f_37665_.m_6881_().m_130946_(" - ").m_7220_(f_37667_);
    private static final int f_150217_ = 48000;
    public static final int f_150202_ = 9216;
    public static final int f_150203_ = 12544;
    private final Map<Integer, Raider> f_37670_ = Maps.newHashMap();
    private final Map<Integer, Set<Raider>> f_37671_ = Maps.newHashMap();
    private final Set<UUID> f_37672_ = Sets.newHashSet();
    private long f_37673_;
    private BlockPos f_37674_;
    private final ServerLevel f_37675_;
    private boolean f_37676_;
    private final int f_37677_;
    private float f_37678_;
    private int f_37679_;
    private boolean f_37680_;
    private int f_37681_;
    private final ServerBossEvent f_37682_ = new ServerBossEvent(f_37665_, BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);
    private int f_37683_;
    private int f_37684_;
    private final RandomSource f_37685_ = RandomSource.m_216327_();
    private final int f_37686_;
    private RaidStatus f_37687_;
    private int f_37688_;
    private Optional<BlockPos> f_37689_ = Optional.empty();

    public Raid(int p_37692_, ServerLevel p_37693_, BlockPos p_37694_) {
        this.f_37677_ = p_37692_;
        this.f_37675_ = p_37693_;
        this.f_37680_ = true;
        this.f_37684_ = 300;
        this.f_37682_.m_142711_(0.0f);
        this.f_37674_ = p_37694_;
        this.f_37686_ = this.m_37724_(p_37693_.m_46791_());
        this.f_37687_ = RaidStatus.ONGOING;
    }

    public Raid(ServerLevel p_37696_, CompoundTag p_37697_) {
        this.f_37675_ = p_37696_;
        this.f_37677_ = p_37697_.m_128451_("Id");
        this.f_37676_ = p_37697_.m_128471_("Started");
        this.f_37680_ = p_37697_.m_128471_("Active");
        this.f_37673_ = p_37697_.m_128454_("TicksActive");
        this.f_37679_ = p_37697_.m_128451_("BadOmenLevel");
        this.f_37681_ = p_37697_.m_128451_("GroupsSpawned");
        this.f_37684_ = p_37697_.m_128451_("PreRaidTicks");
        this.f_37683_ = p_37697_.m_128451_("PostRaidTicks");
        this.f_37678_ = p_37697_.m_128457_("TotalHealth");
        this.f_37674_ = new BlockPos(p_37697_.m_128451_("CX"), p_37697_.m_128451_("CY"), p_37697_.m_128451_("CZ"));
        this.f_37686_ = p_37697_.m_128451_("NumGroups");
        this.f_37687_ = RaidStatus.m_37803_(p_37697_.m_128461_("Status"));
        this.f_37672_.clear();
        if (p_37697_.m_128425_("HeroesOfTheVillage", 9)) {
            ListTag $$2 = p_37697_.m_128437_("HeroesOfTheVillage", 11);
            for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
                this.f_37672_.add(NbtUtils.m_129233_($$2.get($$3)));
            }
        }
    }

    public boolean m_37706_() {
        return this.m_37767_() || this.m_37768_();
    }

    public boolean m_37749_() {
        return this.m_37757_() && this.m_37778_() == 0 && this.f_37684_ > 0;
    }

    public boolean m_37757_() {
        return this.f_37681_ > 0;
    }

    public boolean m_37762_() {
        return this.f_37687_ == RaidStatus.STOPPED;
    }

    public boolean m_37767_() {
        return this.f_37687_ == RaidStatus.VICTORY;
    }

    public boolean m_37768_() {
        return this.f_37687_ == RaidStatus.LOSS;
    }

    public float m_150220_() {
        return this.f_37678_;
    }

    public Set<Raider> m_150221_() {
        HashSet $$0 = Sets.newHashSet();
        for (Set<Raider> $$1 : this.f_37671_.values()) {
            $$0.addAll($$1);
        }
        return $$0;
    }

    public Level m_37769_() {
        return this.f_37675_;
    }

    public boolean m_37770_() {
        return this.f_37676_;
    }

    public int m_37771_() {
        return this.f_37681_;
    }

    private Predicate<ServerPlayer> m_37784_() {
        return p_37723_ -> {
            BlockPos $$1 = p_37723_.m_20183_();
            return p_37723_.m_6084_() && this.f_37675_.m_8832_($$1) == this;
        };
    }

    private void m_37785_() {
        HashSet $$0 = Sets.newHashSet(this.f_37682_.m_8324_());
        List<ServerPlayer> $$1 = this.f_37675_.m_8795_(this.m_37784_());
        for (ServerPlayer $$2 : $$1) {
            if ($$0.contains($$2)) continue;
            this.f_37682_.m_6543_($$2);
        }
        for (ServerPlayer $$3 : $$0) {
            if ($$1.contains($$3)) continue;
            this.f_37682_.m_6539_($$3);
        }
    }

    public int m_37772_() {
        return 5;
    }

    public int m_37773_() {
        return this.f_37679_;
    }

    public void m_150218_(int p_150219_) {
        this.f_37679_ = p_150219_;
    }

    public void m_37728_(Player p_37729_) {
        if (p_37729_.m_21023_(MobEffects.f_19594_)) {
            this.f_37679_ += p_37729_.m_21124_(MobEffects.f_19594_).m_19564_() + 1;
            this.f_37679_ = Mth.m_14045_(this.f_37679_, 0, this.m_37772_());
        }
        p_37729_.m_21195_(MobEffects.f_19594_);
    }

    public void m_37774_() {
        this.f_37680_ = false;
        this.f_37682_.m_7706_();
        this.f_37687_ = RaidStatus.STOPPED;
    }

    public void m_37775_() {
        if (this.m_37762_()) {
            return;
        }
        if (this.f_37687_ == RaidStatus.ONGOING) {
            boolean $$0 = this.f_37680_;
            this.f_37680_ = this.f_37675_.m_46805_(this.f_37674_);
            if (this.f_37675_.m_46791_() == Difficulty.PEACEFUL) {
                this.m_37774_();
                return;
            }
            if ($$0 != this.f_37680_) {
                this.f_37682_.m_8321_(this.f_37680_);
            }
            if (!this.f_37680_) {
                return;
            }
            if (!this.f_37675_.m_8802_(this.f_37674_)) {
                this.m_37786_();
            }
            if (!this.f_37675_.m_8802_(this.f_37674_)) {
                if (this.f_37681_ > 0) {
                    this.f_37687_ = RaidStatus.LOSS;
                } else {
                    this.m_37774_();
                }
            }
            ++this.f_37673_;
            if (this.f_37673_ >= 48000L) {
                this.m_37774_();
                return;
            }
            int $$1 = this.m_37778_();
            if ($$1 == 0 && this.m_37698_()) {
                if (this.f_37684_ > 0) {
                    boolean $$3;
                    boolean $$2 = this.f_37689_.isPresent();
                    boolean bl = $$3 = !$$2 && this.f_37684_ % 5 == 0;
                    if ($$2 && !this.f_37675_.m_143340_(this.f_37689_.get())) {
                        $$3 = true;
                    }
                    if ($$3) {
                        int $$4 = 0;
                        if (this.f_37684_ < 100) {
                            $$4 = 1;
                        } else if (this.f_37684_ < 40) {
                            $$4 = 2;
                        }
                        this.f_37689_ = this.m_37763_($$4);
                    }
                    if (this.f_37684_ == 300 || this.f_37684_ % 20 == 0) {
                        this.m_37785_();
                    }
                    --this.f_37684_;
                    this.f_37682_.m_142711_(Mth.m_14036_((float)(300 - this.f_37684_) / 300.0f, 0.0f, 1.0f));
                } else if (this.f_37684_ == 0 && this.f_37681_ > 0) {
                    this.f_37684_ = 300;
                    this.f_37682_.m_6456_(f_37665_);
                    return;
                }
            }
            if (this.f_37673_ % 20L == 0L) {
                this.m_37785_();
                this.m_37703_();
                if ($$1 > 0) {
                    if ($$1 <= 2) {
                        this.f_37682_.m_6456_(f_37665_.m_6881_().m_130946_(" - ").m_7220_(Component.m_237110_(f_150212_, $$1)));
                    } else {
                        this.f_37682_.m_6456_(f_37665_);
                    }
                } else {
                    this.f_37682_.m_6456_(f_37665_);
                }
            }
            boolean $$5 = false;
            int $$6 = 0;
            while (this.m_37704_()) {
                BlockPos $$7;
                BlockPos blockPos = $$7 = this.f_37689_.isPresent() ? this.f_37689_.get() : this.m_37707_($$6, 20);
                if ($$7 != null) {
                    this.f_37676_ = true;
                    this.m_37755_($$7);
                    if (!$$5) {
                        this.m_37743_($$7);
                        $$5 = true;
                    }
                } else {
                    ++$$6;
                }
                if ($$6 <= 3) continue;
                this.m_37774_();
                break;
            }
            if (this.m_37770_() && !this.m_37698_() && $$1 == 0) {
                if (this.f_37683_ < 40) {
                    ++this.f_37683_;
                } else {
                    this.f_37687_ = RaidStatus.VICTORY;
                    for (UUID $$8 : this.f_37672_) {
                        Entity $$9 = this.f_37675_.m_8791_($$8);
                        if (!($$9 instanceof LivingEntity) || $$9.m_5833_()) continue;
                        LivingEntity $$10 = (LivingEntity)$$9;
                        $$10.m_7292_(new MobEffectInstance(MobEffects.f_19595_, 48000, this.f_37679_ - 1, false, false, true));
                        if (!($$10 instanceof ServerPlayer)) continue;
                        ServerPlayer $$11 = (ServerPlayer)$$10;
                        $$11.m_36220_(Stats.f_12950_);
                        CriteriaTriggers.f_10557_.m_222618_($$11);
                    }
                }
            }
            this.m_37705_();
        } else if (this.m_37706_()) {
            ++this.f_37688_;
            if (this.f_37688_ >= 600) {
                this.m_37774_();
                return;
            }
            if (this.f_37688_ % 20 == 0) {
                this.m_37785_();
                this.f_37682_.m_8321_(true);
                if (this.m_37767_()) {
                    this.f_37682_.m_142711_(0.0f);
                    this.f_37682_.m_6456_(f_37668_);
                } else {
                    this.f_37682_.m_6456_(f_37669_);
                }
            }
        }
    }

    private void m_37786_() {
        Stream<SectionPos> $$0 = SectionPos.m_123201_(SectionPos.m_123199_(this.f_37674_), 2);
        $$0.filter(this.f_37675_::m_8762_).map(SectionPos::m_123250_).min(Comparator.comparingDouble(p_37766_ -> p_37766_.m_123331_(this.f_37674_))).ifPresent(this::m_37760_);
    }

    private Optional<BlockPos> m_37763_(int p_37764_) {
        for (int $$1 = 0; $$1 < 3; ++$$1) {
            BlockPos $$2 = this.m_37707_(p_37764_, 1);
            if ($$2 == null) continue;
            return Optional.of($$2);
        }
        return Optional.empty();
    }

    private boolean m_37698_() {
        if (this.m_37700_()) {
            return !this.m_37701_();
        }
        return !this.m_37699_();
    }

    private boolean m_37699_() {
        return this.m_37771_() == this.f_37686_;
    }

    private boolean m_37700_() {
        return this.f_37679_ > 1;
    }

    private boolean m_37701_() {
        return this.m_37771_() > this.f_37686_;
    }

    private boolean m_37702_() {
        return this.m_37699_() && this.m_37778_() == 0 && this.m_37700_();
    }

    private void m_37703_() {
        Iterator<Set<Raider>> $$0 = this.f_37671_.values().iterator();
        HashSet $$1 = Sets.newHashSet();
        while ($$0.hasNext()) {
            Set<Raider> $$2 = $$0.next();
            for (Raider $$3 : $$2) {
                BlockPos $$4 = $$3.m_20183_();
                if ($$3.m_213877_() || $$3.f_19853_.m_46472_() != this.f_37675_.m_46472_() || this.f_37674_.m_123331_($$4) >= 12544.0) {
                    $$1.add($$3);
                    continue;
                }
                if ($$3.f_19797_ <= 600) continue;
                if (this.f_37675_.m_8791_($$3.m_20148_()) == null) {
                    $$1.add($$3);
                }
                if (!this.f_37675_.m_8802_($$4) && $$3.m_21216_() > 2400) {
                    $$3.m_37863_($$3.m_37889_() + 1);
                }
                if ($$3.m_37889_() < 30) continue;
                $$1.add($$3);
            }
        }
        for (Raider $$5 : $$1) {
            this.m_37740_($$5, true);
        }
    }

    private void m_37743_(BlockPos p_37744_) {
        float $$1 = 13.0f;
        int $$2 = 64;
        Collection<ServerPlayer> $$3 = this.f_37682_.m_8324_();
        long $$4 = this.f_37685_.m_188505_();
        for (ServerPlayer $$5 : this.f_37675_.m_6907_()) {
            Vec3 $$6 = $$5.m_20182_();
            Vec3 $$7 = Vec3.m_82512_(p_37744_);
            double $$8 = Math.sqrt(($$7.f_82479_ - $$6.f_82479_) * ($$7.f_82479_ - $$6.f_82479_) + ($$7.f_82481_ - $$6.f_82481_) * ($$7.f_82481_ - $$6.f_82481_));
            double $$9 = $$6.f_82479_ + 13.0 / $$8 * ($$7.f_82479_ - $$6.f_82479_);
            double $$10 = $$6.f_82481_ + 13.0 / $$8 * ($$7.f_82481_ - $$6.f_82481_);
            if (!($$8 <= 64.0) && !$$3.contains($$5)) continue;
            $$5.f_8906_.m_9829_(new ClientboundSoundPacket(SoundEvents.f_12355_, SoundSource.NEUTRAL, $$9, $$5.m_20186_(), $$10, 64.0f, 1.0f, $$4));
        }
    }

    private void m_37755_(BlockPos p_37756_) {
        boolean $$1 = false;
        int $$2 = this.f_37681_ + 1;
        this.f_37678_ = 0.0f;
        DifficultyInstance $$3 = this.f_37675_.m_6436_(p_37756_);
        boolean $$4 = this.m_37702_();
        for (RaiderType $$5 : RaiderType.f_37813_) {
            int $$6 = this.m_37730_($$5, $$2, $$4) + this.m_219828_($$5, this.f_37685_, $$2, $$3, $$4);
            int $$7 = 0;
            for (int $$8 = 0; $$8 < $$6; ++$$8) {
                Raider $$9 = $$5.f_37814_.m_20615_(this.f_37675_);
                if (!$$1 && $$9.m_7490_()) {
                    $$9.m_33075_(true);
                    this.m_37710_($$2, $$9);
                    $$1 = true;
                }
                this.m_37713_($$2, $$9, p_37756_, false);
                if ($$5.f_37814_ != EntityType.f_20518_) continue;
                Raider $$10 = null;
                if ($$2 == this.m_37724_(Difficulty.NORMAL)) {
                    $$10 = EntityType.f_20513_.m_20615_(this.f_37675_);
                } else if ($$2 >= this.m_37724_(Difficulty.HARD)) {
                    $$10 = $$7 == 0 ? (Raider)EntityType.f_20568_.m_20615_(this.f_37675_) : (Raider)EntityType.f_20493_.m_20615_(this.f_37675_);
                }
                ++$$7;
                if ($$10 == null) continue;
                this.m_37713_($$2, $$10, p_37756_, false);
                $$10.m_20035_(p_37756_, 0.0f, 0.0f);
                $$10.m_20329_($$9);
            }
        }
        this.f_37689_ = Optional.empty();
        ++this.f_37681_;
        this.m_37776_();
        this.m_37705_();
    }

    public void m_37713_(int p_37714_, Raider p_37715_, @Nullable BlockPos p_37716_, boolean p_37717_) {
        boolean $$4 = this.m_37752_(p_37714_, p_37715_);
        if ($$4) {
            p_37715_.m_37851_(this);
            p_37715_.m_37842_(p_37714_);
            p_37715_.m_37897_(true);
            p_37715_.m_37863_(0);
            if (!p_37717_ && p_37716_ != null) {
                p_37715_.m_6034_((double)p_37716_.m_123341_() + 0.5, (double)p_37716_.m_123342_() + 1.0, (double)p_37716_.m_123343_() + 0.5);
                p_37715_.m_6518_(this.f_37675_, this.f_37675_.m_6436_(p_37716_), MobSpawnType.EVENT, null, null);
                p_37715_.m_7895_(p_37714_, false);
                p_37715_.m_6853_(true);
                this.f_37675_.m_47205_(p_37715_);
            }
        }
    }

    public void m_37776_() {
        this.f_37682_.m_142711_(Mth.m_14036_(this.m_37777_() / this.f_37678_, 0.0f, 1.0f));
    }

    public float m_37777_() {
        float $$0 = 0.0f;
        for (Set<Raider> $$1 : this.f_37671_.values()) {
            for (Raider $$2 : $$1) {
                $$0 += $$2.m_21223_();
            }
        }
        return $$0;
    }

    private boolean m_37704_() {
        return this.f_37684_ == 0 && (this.f_37681_ < this.f_37686_ || this.m_37702_()) && this.m_37778_() == 0;
    }

    public int m_37778_() {
        return this.f_37671_.values().stream().mapToInt(Set::size).sum();
    }

    public void m_37740_(Raider p_37741_, boolean p_37742_) {
        boolean $$3;
        Set<Raider> $$2 = this.f_37671_.get(p_37741_.m_37887_());
        if ($$2 != null && ($$3 = $$2.remove(p_37741_))) {
            if (p_37742_) {
                this.f_37678_ -= p_37741_.m_21223_();
            }
            p_37741_.m_37851_(null);
            this.m_37776_();
            this.m_37705_();
        }
    }

    private void m_37705_() {
        this.f_37675_.m_8905_().m_77762_();
    }

    public static ItemStack m_37779_() {
        ItemStack $$0 = new ItemStack(Items.f_42660_);
        CompoundTag $$1 = new CompoundTag();
        ListTag $$2 = new BannerPattern.Builder().m_222705_(BannerPatterns.f_222751_, DyeColor.CYAN).m_222705_(BannerPatterns.f_222731_, DyeColor.LIGHT_GRAY).m_222705_(BannerPatterns.f_222735_, DyeColor.GRAY).m_222705_(BannerPatterns.f_222715_, DyeColor.LIGHT_GRAY).m_222705_(BannerPatterns.f_222736_, DyeColor.BLACK).m_222705_(BannerPatterns.f_222712_, DyeColor.LIGHT_GRAY).m_222705_(BannerPatterns.f_222750_, DyeColor.LIGHT_GRAY).m_222705_(BannerPatterns.f_222715_, DyeColor.BLACK).m_58587_();
        $$1.m_128365_("Patterns", $$2);
        BlockItem.m_186338_($$0, BlockEntityType.f_58935_, $$1);
        $$0.m_41654_(ItemStack.TooltipPart.ADDITIONAL);
        $$0.m_41714_(Component.m_237115_(f_150211_).m_130940_(ChatFormatting.GOLD));
        return $$0;
    }

    @Nullable
    public Raider m_37750_(int p_37751_) {
        return this.f_37670_.get(p_37751_);
    }

    @Nullable
    private BlockPos m_37707_(int p_37708_, int p_37709_) {
        int $$2 = p_37708_ == 0 ? 2 : 2 - p_37708_;
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        for (int $$4 = 0; $$4 < p_37709_; ++$$4) {
            float $$5 = this.f_37675_.f_46441_.m_188501_() * ((float)Math.PI * 2);
            int $$6 = this.f_37674_.m_123341_() + Mth.m_14143_(Mth.m_14089_($$5) * 32.0f * (float)$$2) + this.f_37675_.f_46441_.m_188503_(5);
            int $$7 = this.f_37674_.m_123343_() + Mth.m_14143_(Mth.m_14031_($$5) * 32.0f * (float)$$2) + this.f_37675_.f_46441_.m_188503_(5);
            int $$8 = this.f_37675_.m_6924_(Heightmap.Types.WORLD_SURFACE, $$6, $$7);
            $$3.m_122178_($$6, $$8, $$7);
            if (this.f_37675_.m_8802_($$3) && p_37708_ < 2) continue;
            int $$9 = 10;
            if (!this.f_37675_.m_151572_($$3.m_123341_() - 10, $$3.m_123343_() - 10, $$3.m_123341_() + 10, $$3.m_123343_() + 10) || !this.f_37675_.m_143340_($$3) || !NaturalSpawner.m_47051_(SpawnPlacements.Type.ON_GROUND, this.f_37675_, $$3, EntityType.f_20518_) && (!this.f_37675_.m_8055_((BlockPos)$$3.m_7495_()).m_60713_(Blocks.f_50125_) || !this.f_37675_.m_8055_($$3).m_60795_())) continue;
            return $$3;
        }
        return null;
    }

    private boolean m_37752_(int p_37753_, Raider p_37754_) {
        return this.m_37718_(p_37753_, p_37754_, true);
    }

    public boolean m_37718_(int p_37719_, Raider p_37720_, boolean p_37721_) {
        this.f_37671_.computeIfAbsent(p_37719_, p_37746_ -> Sets.newHashSet());
        Set<Raider> $$3 = this.f_37671_.get(p_37719_);
        Raider $$4 = null;
        for (Raider $$5 : $$3) {
            if (!$$5.m_20148_().equals(p_37720_.m_20148_())) continue;
            $$4 = $$5;
            break;
        }
        if ($$4 != null) {
            $$3.remove($$4);
            $$3.add(p_37720_);
        }
        $$3.add(p_37720_);
        if (p_37721_) {
            this.f_37678_ += p_37720_.m_21223_();
        }
        this.m_37776_();
        this.m_37705_();
        return true;
    }

    public void m_37710_(int p_37711_, Raider p_37712_) {
        this.f_37670_.put(p_37711_, p_37712_);
        p_37712_.m_8061_(EquipmentSlot.HEAD, Raid.m_37779_());
        p_37712_.m_21409_(EquipmentSlot.HEAD, 2.0f);
    }

    public void m_37758_(int p_37759_) {
        this.f_37670_.remove(p_37759_);
    }

    public BlockPos m_37780_() {
        return this.f_37674_;
    }

    private void m_37760_(BlockPos p_37761_) {
        this.f_37674_ = p_37761_;
    }

    public int m_37781_() {
        return this.f_37677_;
    }

    private int m_37730_(RaiderType p_37731_, int p_37732_, boolean p_37733_) {
        return p_37733_ ? p_37731_.f_37815_[this.f_37686_] : p_37731_.f_37815_[p_37732_];
    }

    /*
     * WARNING - void declaration
     */
    private int m_219828_(RaiderType p_219829_, RandomSource p_219830_, int p_219831_, DifficultyInstance p_219832_, boolean p_219833_) {
        void $$13;
        Difficulty $$5 = p_219832_.m_19048_();
        boolean $$6 = $$5 == Difficulty.EASY;
        boolean $$7 = $$5 == Difficulty.NORMAL;
        switch (p_219829_) {
            case WITCH: {
                if (!$$6 && p_219831_ > 2 && p_219831_ != 4) {
                    boolean $$8 = true;
                    break;
                }
                return 0;
            }
            case PILLAGER: 
            case VINDICATOR: {
                if ($$6) {
                    int $$9 = p_219830_.m_188503_(2);
                    break;
                }
                if ($$7) {
                    boolean $$10 = true;
                    break;
                }
                int $$11 = 2;
                break;
            }
            case RAVAGER: {
                boolean $$12 = !$$6 && p_219833_;
                break;
            }
            default: {
                return 0;
            }
        }
        return $$13 > 0 ? p_219830_.m_188503_((int)($$13 + true)) : 0;
    }

    public boolean m_37782_() {
        return this.f_37680_;
    }

    public CompoundTag m_37747_(CompoundTag p_37748_) {
        p_37748_.m_128405_("Id", this.f_37677_);
        p_37748_.m_128379_("Started", this.f_37676_);
        p_37748_.m_128379_("Active", this.f_37680_);
        p_37748_.m_128356_("TicksActive", this.f_37673_);
        p_37748_.m_128405_("BadOmenLevel", this.f_37679_);
        p_37748_.m_128405_("GroupsSpawned", this.f_37681_);
        p_37748_.m_128405_("PreRaidTicks", this.f_37684_);
        p_37748_.m_128405_("PostRaidTicks", this.f_37683_);
        p_37748_.m_128350_("TotalHealth", this.f_37678_);
        p_37748_.m_128405_("NumGroups", this.f_37686_);
        p_37748_.m_128359_("Status", this.f_37687_.m_37800_());
        p_37748_.m_128405_("CX", this.f_37674_.m_123341_());
        p_37748_.m_128405_("CY", this.f_37674_.m_123342_());
        p_37748_.m_128405_("CZ", this.f_37674_.m_123343_());
        ListTag $$1 = new ListTag();
        for (UUID $$2 : this.f_37672_) {
            $$1.add(NbtUtils.m_129226_($$2));
        }
        p_37748_.m_128365_("HeroesOfTheVillage", $$1);
        return p_37748_;
    }

    public int m_37724_(Difficulty p_37725_) {
        switch (p_37725_) {
            case EASY: {
                return 3;
            }
            case NORMAL: {
                return 5;
            }
            case HARD: {
                return 7;
            }
        }
        return 0;
    }

    public float m_37783_() {
        int $$0 = this.m_37773_();
        if ($$0 == 2) {
            return 0.1f;
        }
        if ($$0 == 3) {
            return 0.25f;
        }
        if ($$0 == 4) {
            return 0.5f;
        }
        if ($$0 == 5) {
            return 0.75f;
        }
        return 0.0f;
    }

    public void m_37726_(Entity p_37727_) {
        this.f_37672_.add(p_37727_.m_20148_());
    }

    static final class RaidStatus
    extends Enum<RaidStatus> {
        public static final /* enum */ RaidStatus ONGOING = new RaidStatus();
        public static final /* enum */ RaidStatus VICTORY = new RaidStatus();
        public static final /* enum */ RaidStatus LOSS = new RaidStatus();
        public static final /* enum */ RaidStatus STOPPED = new RaidStatus();
        private static final RaidStatus[] f_37794_;
        private static final /* synthetic */ RaidStatus[] $VALUES;

        public static RaidStatus[] values() {
            return (RaidStatus[])$VALUES.clone();
        }

        public static RaidStatus valueOf(String p_37806_) {
            return Enum.valueOf(RaidStatus.class, p_37806_);
        }

        static RaidStatus m_37803_(String p_37804_) {
            for (RaidStatus $$1 : f_37794_) {
                if (!p_37804_.equalsIgnoreCase($$1.name())) continue;
                return $$1;
            }
            return ONGOING;
        }

        public String m_37800_() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        private static /* synthetic */ RaidStatus[] m_150222_() {
            return new RaidStatus[]{ONGOING, VICTORY, LOSS, STOPPED};
        }

        static {
            $VALUES = RaidStatus.m_150222_();
            f_37794_ = RaidStatus.values();
        }
    }

    static final class RaiderType
    extends Enum<RaiderType> {
        public static final /* enum */ RaiderType VINDICATOR = new RaiderType(EntityType.f_20493_, new int[]{0, 0, 2, 0, 1, 4, 2, 5});
        public static final /* enum */ RaiderType EVOKER = new RaiderType(EntityType.f_20568_, new int[]{0, 0, 0, 0, 0, 1, 1, 2});
        public static final /* enum */ RaiderType PILLAGER = new RaiderType(EntityType.f_20513_, new int[]{0, 4, 3, 3, 4, 4, 4, 2});
        public static final /* enum */ RaiderType WITCH = new RaiderType(EntityType.f_20495_, new int[]{0, 0, 0, 0, 3, 0, 0, 1});
        public static final /* enum */ RaiderType RAVAGER = new RaiderType(EntityType.f_20518_, new int[]{0, 0, 0, 1, 0, 1, 0, 2});
        static final RaiderType[] f_37813_;
        final EntityType<? extends Raider> f_37814_;
        final int[] f_37815_;
        private static final /* synthetic */ RaiderType[] $VALUES;

        public static RaiderType[] values() {
            return (RaiderType[])$VALUES.clone();
        }

        public static RaiderType valueOf(String p_37829_) {
            return Enum.valueOf(RaiderType.class, p_37829_);
        }

        private RaiderType(EntityType<? extends Raider> p_37821_, int[] p_37822_) {
            this.f_37814_ = p_37821_;
            this.f_37815_ = p_37822_;
        }

        private static /* synthetic */ RaiderType[] m_150223_() {
            return new RaiderType[]{VINDICATOR, EVOKER, PILLAGER, WITCH, RAVAGER};
        }

        static {
            $VALUES = RaiderType.m_150223_();
            f_37813_ = RaiderType.values();
        }
    }
}

