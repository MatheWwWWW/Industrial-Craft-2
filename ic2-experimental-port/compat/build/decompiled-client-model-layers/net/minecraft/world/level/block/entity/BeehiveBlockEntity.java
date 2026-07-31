/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class BeehiveBlockEntity
extends BlockEntity {
    public static final String f_155121_ = "FlowerPos";
    public static final String f_155122_ = "MinOccupationTicks";
    public static final String f_155123_ = "EntityData";
    public static final String f_155124_ = "TicksInHive";
    public static final String f_155125_ = "HasNectar";
    public static final String f_155126_ = "Bees";
    private static final List<String> f_155129_ = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID");
    public static final int f_155127_ = 3;
    private static final int f_155130_ = 400;
    private static final int f_155131_ = 2400;
    public static final int f_155128_ = 600;
    private final List<BeeData> f_58732_ = Lists.newArrayList();
    @Nullable
    private BlockPos f_58733_;

    public BeehiveBlockEntity(BlockPos p_155134_, BlockState p_155135_) {
        super(BlockEntityType.f_58912_, p_155134_, p_155135_);
    }

    @Override
    public void m_6596_() {
        if (this.m_58773_()) {
            this.m_58748_(null, this.f_58857_.m_8055_(this.m_58899_()), BeeReleaseStatus.EMERGENCY);
        }
        super.m_6596_();
    }

    public boolean m_58773_() {
        if (this.f_58857_ == null) {
            return false;
        }
        for (BlockPos $$0 : BlockPos.m_121940_(this.f_58858_.m_7918_(-1, -1, -1), this.f_58858_.m_7918_(1, 1, 1))) {
            if (!(this.f_58857_.m_8055_($$0).m_60734_() instanceof FireBlock)) continue;
            return true;
        }
        return false;
    }

    public boolean m_58774_() {
        return this.f_58732_.isEmpty();
    }

    public boolean m_58775_() {
        return this.f_58732_.size() == 3;
    }

    public void m_58748_(@Nullable Player p_58749_, BlockState p_58750_, BeeReleaseStatus p_58751_) {
        List<Entity> $$3 = this.m_58759_(p_58750_, p_58751_);
        if (p_58749_ != null) {
            for (Entity $$4 : $$3) {
                if (!($$4 instanceof Bee)) continue;
                Bee $$5 = (Bee)$$4;
                if (!(p_58749_.m_20182_().m_82557_($$4.m_20182_()) <= 16.0)) continue;
                if (!this.m_58777_()) {
                    $$5.m_6710_(p_58749_);
                    continue;
                }
                $$5.m_27915_(400);
            }
        }
    }

    private List<Entity> m_58759_(BlockState p_58760_, BeeReleaseStatus p_58761_) {
        ArrayList $$2 = Lists.newArrayList();
        this.f_58732_.removeIf(p_58766_ -> BeehiveBlockEntity.m_155136_(this.f_58857_, this.f_58858_, p_58760_, p_58766_, $$2, p_58761_, this.f_58733_));
        if (!$$2.isEmpty()) {
            super.m_6596_();
        }
        return $$2;
    }

    public void m_58741_(Entity p_58742_, boolean p_58743_) {
        this.m_58744_(p_58742_, p_58743_, 0);
    }

    @VisibleForDebug
    public int m_58776_() {
        return this.f_58732_.size();
    }

    public static int m_58752_(BlockState p_58753_) {
        return p_58753_.m_61143_(BeehiveBlock.f_49564_);
    }

    @VisibleForDebug
    public boolean m_58777_() {
        return CampfireBlock.m_51248_(this.f_58857_, this.m_58899_());
    }

    public void m_58744_(Entity p_58745_, boolean p_58746_, int p_58747_) {
        if (this.f_58732_.size() >= 3) {
            return;
        }
        p_58745_.m_8127_();
        p_58745_.m_20153_();
        CompoundTag $$3 = new CompoundTag();
        p_58745_.m_20223_($$3);
        this.m_155157_($$3, p_58747_, p_58746_);
        if (this.f_58857_ != null) {
            Bee $$4;
            if (p_58745_ instanceof Bee && ($$4 = (Bee)p_58745_).m_27852_() && (!this.m_58780_() || this.f_58857_.f_46441_.m_188499_())) {
                this.f_58733_ = $$4.m_27851_();
            }
            BlockPos $$5 = this.m_58899_();
            this.f_58857_.m_6263_(null, $$5.m_123341_(), $$5.m_123342_(), $$5.m_123343_(), SoundEvents.f_11695_, SoundSource.BLOCKS, 1.0f, 1.0f);
            this.f_58857_.m_220407_(GameEvent.f_157792_, $$5, GameEvent.Context.m_223719_(p_58745_, this.m_58900_()));
        }
        p_58745_.m_146870_();
        super.m_6596_();
    }

    public void m_155157_(CompoundTag p_155158_, int p_155159_, boolean p_155160_) {
        this.f_58732_.add(new BeeData(p_155158_, p_155159_, p_155160_ ? 2400 : 600));
    }

    private static boolean m_155136_(Level p_155137_, BlockPos p_155138_, BlockState p_155139_, BeeData p_155140_, @Nullable List<Entity> p_155141_, BeeReleaseStatus p_155142_, @Nullable BlockPos p_155143_) {
        boolean $$10;
        if ((p_155137_.m_46462_() || p_155137_.m_46471_()) && p_155142_ != BeeReleaseStatus.EMERGENCY) {
            return false;
        }
        CompoundTag $$7 = p_155140_.f_58782_.m_6426_();
        BeehiveBlockEntity.m_155161_($$7);
        $$7.m_128365_("HivePos", NbtUtils.m_129224_(p_155138_));
        $$7.m_128379_("NoGravity", true);
        Direction $$8 = p_155139_.m_61143_(BeehiveBlock.f_49563_);
        BlockPos $$9 = p_155138_.m_121945_($$8);
        boolean bl = $$10 = !p_155137_.m_8055_($$9).m_60812_(p_155137_, $$9).m_83281_();
        if ($$10 && p_155142_ != BeeReleaseStatus.EMERGENCY) {
            return false;
        }
        Entity $$11 = EntityType.m_20645_($$7, p_155137_, p_58740_ -> p_58740_);
        if ($$11 != null) {
            if (!$$11.m_6095_().m_204039_(EntityTypeTags.f_13122_)) {
                return false;
            }
            if ($$11 instanceof Bee) {
                Bee $$12 = (Bee)$$11;
                if (p_155143_ != null && !$$12.m_27852_() && p_155137_.f_46441_.m_188501_() < 0.9f) {
                    $$12.m_27876_(p_155143_);
                }
                if (p_155142_ == BeeReleaseStatus.HONEY_DELIVERED) {
                    int $$13;
                    $$12.m_27864_();
                    if (p_155139_.m_204338_(BlockTags.f_13072_, p_202037_ -> p_202037_.m_61138_(BeehiveBlock.f_49564_)) && ($$13 = BeehiveBlockEntity.m_58752_(p_155139_)) < 5) {
                        int $$14;
                        int n = $$14 = p_155137_.f_46441_.m_188503_(100) == 0 ? 2 : 1;
                        if ($$13 + $$14 > 5) {
                            --$$14;
                        }
                        p_155137_.m_46597_(p_155138_, (BlockState)p_155139_.m_61124_(BeehiveBlock.f_49564_, $$13 + $$14));
                    }
                }
                BeehiveBlockEntity.m_58736_(p_155140_.f_58783_, $$12);
                if (p_155141_ != null) {
                    p_155141_.add($$12);
                }
                float $$15 = $$11.m_20205_();
                double $$16 = $$10 ? 0.0 : 0.55 + (double)($$15 / 2.0f);
                double $$17 = (double)p_155138_.m_123341_() + 0.5 + $$16 * (double)$$8.m_122429_();
                double $$18 = (double)p_155138_.m_123342_() + 0.5 - (double)($$11.m_20206_() / 2.0f);
                double $$19 = (double)p_155138_.m_123343_() + 0.5 + $$16 * (double)$$8.m_122431_();
                $$11.m_7678_($$17, $$18, $$19, $$11.m_146908_(), $$11.m_146909_());
            }
            p_155137_.m_5594_(null, p_155138_, SoundEvents.f_11696_, SoundSource.BLOCKS, 1.0f, 1.0f);
            p_155137_.m_220407_(GameEvent.f_157792_, p_155138_, GameEvent.Context.m_223719_($$11, p_155137_.m_8055_(p_155138_)));
            return p_155137_.m_7967_($$11);
        }
        return false;
    }

    static void m_155161_(CompoundTag p_155162_) {
        for (String $$1 : f_155129_) {
            p_155162_.m_128473_($$1);
        }
    }

    private static void m_58736_(int p_58737_, Bee p_58738_) {
        int $$2 = p_58738_.m_146764_();
        if ($$2 < 0) {
            p_58738_.m_146762_(Math.min(0, $$2 + p_58737_));
        } else if ($$2 > 0) {
            p_58738_.m_146762_(Math.max(0, $$2 - p_58737_));
        }
        p_58738_.m_27601_(Math.max(0, p_58738_.m_27591_() - p_58737_));
    }

    private boolean m_58780_() {
        return this.f_58733_ != null;
    }

    private static void m_155149_(Level p_155150_, BlockPos p_155151_, BlockState p_155152_, List<BeeData> p_155153_, @Nullable BlockPos p_155154_) {
        boolean $$5 = false;
        Iterator<BeeData> $$6 = p_155153_.iterator();
        while ($$6.hasNext()) {
            BeeData $$7 = $$6.next();
            if ($$7.f_58783_ > $$7.f_58784_) {
                BeeReleaseStatus $$8;
                BeeReleaseStatus beeReleaseStatus = $$8 = $$7.f_58782_.m_128471_(f_155125_) ? BeeReleaseStatus.HONEY_DELIVERED : BeeReleaseStatus.BEE_RELEASED;
                if (BeehiveBlockEntity.m_155136_(p_155150_, p_155151_, p_155152_, $$7, null, $$8, p_155154_)) {
                    $$5 = true;
                    $$6.remove();
                }
            }
            ++$$7.f_58783_;
        }
        if ($$5) {
            BeehiveBlockEntity.m_155232_(p_155150_, p_155151_, p_155152_);
        }
    }

    public static void m_155144_(Level p_155145_, BlockPos p_155146_, BlockState p_155147_, BeehiveBlockEntity p_155148_) {
        BeehiveBlockEntity.m_155149_(p_155145_, p_155146_, p_155147_, p_155148_.f_58732_, p_155148_.f_58733_);
        if (!p_155148_.f_58732_.isEmpty() && p_155145_.m_213780_().m_188500_() < 0.005) {
            double $$4 = (double)p_155146_.m_123341_() + 0.5;
            double $$5 = p_155146_.m_123342_();
            double $$6 = (double)p_155146_.m_123343_() + 0.5;
            p_155145_.m_6263_(null, $$4, $$5, $$6, SoundEvents.f_11698_, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        DebugPackets.m_179510_(p_155145_, p_155146_, p_155147_, p_155148_);
    }

    @Override
    public void m_142466_(CompoundTag p_155156_) {
        super.m_142466_(p_155156_);
        this.f_58732_.clear();
        ListTag $$1 = p_155156_.m_128437_(f_155126_, 10);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            CompoundTag $$3 = $$1.m_128728_($$2);
            BeeData $$4 = new BeeData($$3.m_128469_(f_155123_), $$3.m_128451_(f_155124_), $$3.m_128451_(f_155122_));
            this.f_58732_.add($$4);
        }
        this.f_58733_ = null;
        if (p_155156_.m_128441_(f_155121_)) {
            this.f_58733_ = NbtUtils.m_129239_(p_155156_.m_128469_(f_155121_));
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_187467_) {
        super.m_183515_(p_187467_);
        p_187467_.m_128365_(f_155126_, this.m_58779_());
        if (this.m_58780_()) {
            p_187467_.m_128365_(f_155121_, NbtUtils.m_129224_(this.f_58733_));
        }
    }

    public ListTag m_58779_() {
        ListTag $$0 = new ListTag();
        for (BeeData $$1 : this.f_58732_) {
            CompoundTag $$2 = $$1.f_58782_.m_6426_();
            $$2.m_128473_("UUID");
            CompoundTag $$3 = new CompoundTag();
            $$3.m_128365_(f_155123_, $$2);
            $$3.m_128405_(f_155124_, $$1.f_58783_);
            $$3.m_128405_(f_155122_, $$1.f_58784_);
            $$0.add($$3);
        }
        return $$0;
    }

    public static final class BeeReleaseStatus
    extends Enum<BeeReleaseStatus> {
        public static final /* enum */ BeeReleaseStatus HONEY_DELIVERED = new BeeReleaseStatus();
        public static final /* enum */ BeeReleaseStatus BEE_RELEASED = new BeeReleaseStatus();
        public static final /* enum */ BeeReleaseStatus EMERGENCY = new BeeReleaseStatus();
        private static final /* synthetic */ BeeReleaseStatus[] $VALUES;

        public static BeeReleaseStatus[] values() {
            return (BeeReleaseStatus[])$VALUES.clone();
        }

        public static BeeReleaseStatus valueOf(String p_58811_) {
            return Enum.valueOf(BeeReleaseStatus.class, p_58811_);
        }

        private static /* synthetic */ BeeReleaseStatus[] m_155163_() {
            return new BeeReleaseStatus[]{HONEY_DELIVERED, BEE_RELEASED, EMERGENCY};
        }

        static {
            $VALUES = BeeReleaseStatus.m_155163_();
        }
    }

    static class BeeData {
        final CompoundTag f_58782_;
        int f_58783_;
        final int f_58784_;

        BeeData(CompoundTag p_58786_, int p_58787_, int p_58788_) {
            BeehiveBlockEntity.m_155161_(p_58786_);
            this.f_58782_ = p_58786_;
            this.f_58783_ = p_58787_;
            this.f_58784_ = p_58788_;
        }
    }
}

