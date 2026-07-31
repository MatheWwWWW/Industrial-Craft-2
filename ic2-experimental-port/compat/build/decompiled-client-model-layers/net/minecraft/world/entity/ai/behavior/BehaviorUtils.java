/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class BehaviorUtils {
    private BehaviorUtils() {
    }

    public static void m_22602_(LivingEntity p_22603_, LivingEntity p_22604_, float p_22605_) {
        BehaviorUtils.m_22670_(p_22603_, p_22604_);
        BehaviorUtils.m_22660_(p_22603_, p_22604_, p_22605_);
    }

    public static boolean m_22636_(Brain<?> p_22637_, LivingEntity p_22638_) {
        Optional<NearestVisibleLivingEntities> $$2 = p_22637_.m_21952_(MemoryModuleType.f_148205_);
        return $$2.isPresent() && $$2.get().m_186107_(p_22638_);
    }

    public static boolean m_22639_(Brain<?> p_22640_, MemoryModuleType<? extends LivingEntity> p_22641_, EntityType<?> p_22642_) {
        return BehaviorUtils.m_22643_(p_22640_, p_22641_, p_186022_ -> p_186022_.m_6095_() == p_22642_);
    }

    private static boolean m_22643_(Brain<?> p_22644_, MemoryModuleType<? extends LivingEntity> p_22645_, Predicate<LivingEntity> p_22646_) {
        return p_22644_.m_21952_(p_22645_).filter(p_22646_).filter(LivingEntity::m_6084_).filter(p_186037_ -> BehaviorUtils.m_22636_(p_22644_, p_186037_)).isPresent();
    }

    private static void m_22670_(LivingEntity p_22671_, LivingEntity p_22672_) {
        BehaviorUtils.m_22595_(p_22671_, p_22672_);
        BehaviorUtils.m_22595_(p_22672_, p_22671_);
    }

    public static void m_22595_(LivingEntity p_22596_, LivingEntity p_22597_) {
        p_22596_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_22597_, true));
    }

    private static void m_22660_(LivingEntity p_22661_, LivingEntity p_22662_, float p_22663_) {
        int $$3 = 2;
        BehaviorUtils.m_22590_(p_22661_, p_22662_, p_22663_, 2);
        BehaviorUtils.m_22590_(p_22662_, p_22661_, p_22663_, 2);
    }

    public static void m_22590_(LivingEntity p_22591_, Entity p_22592_, float p_22593_, int p_22594_) {
        BehaviorUtils.m_217128_(p_22591_, new EntityTracker(p_22592_, true), p_22593_, p_22594_);
    }

    public static void m_22617_(LivingEntity p_22618_, BlockPos p_22619_, float p_22620_, int p_22621_) {
        BehaviorUtils.m_217128_(p_22618_, new BlockPosTracker(p_22619_), p_22620_, p_22621_);
    }

    public static void m_217128_(LivingEntity p_217129_, PositionTracker p_217130_, float p_217131_, int p_217132_) {
        WalkTarget $$4 = new WalkTarget(p_217130_, p_217131_, p_217132_);
        p_217129_.m_6274_().m_21879_(MemoryModuleType.f_26371_, p_217130_);
        p_217129_.m_6274_().m_21879_(MemoryModuleType.f_26370_, $$4);
    }

    public static void m_22613_(LivingEntity p_22614_, ItemStack p_22615_, Vec3 p_22616_) {
        Vec3 $$3 = new Vec3(0.3f, 0.3f, 0.3f);
        BehaviorUtils.m_217133_(p_22614_, p_22615_, p_22616_, $$3, 0.3f);
    }

    public static void m_217133_(LivingEntity p_217134_, ItemStack p_217135_, Vec3 p_217136_, Vec3 p_217137_, float p_217138_) {
        double $$5 = p_217134_.m_20188_() - (double)p_217138_;
        ItemEntity $$6 = new ItemEntity(p_217134_.f_19853_, p_217134_.m_20185_(), $$5, p_217134_.m_20189_(), p_217135_);
        $$6.m_32052_(p_217134_.m_20148_());
        Vec3 $$7 = p_217136_.m_82546_(p_217134_.m_20182_());
        $$7 = $$7.m_82541_().m_82542_(p_217137_.f_82479_, p_217137_.f_82480_, p_217137_.f_82481_);
        $$6.m_20256_($$7);
        $$6.m_32060_();
        p_217134_.f_19853_.m_7967_($$6);
    }

    public static SectionPos m_22581_(ServerLevel p_22582_, SectionPos p_22583_, int p_22584_) {
        int $$3 = p_22582_.m_8828_(p_22583_);
        return SectionPos.m_123201_(p_22583_, p_22584_).filter(p_186017_ -> p_22582_.m_8828_((SectionPos)p_186017_) < $$3).min(Comparator.comparingInt(p_22582_::m_8828_)).orElse(p_22583_);
    }

    public static boolean m_22632_(Mob p_22633_, LivingEntity p_22634_, int p_22635_) {
        Item $$3 = p_22633_.m_21205_().m_41720_();
        if ($$3 instanceof ProjectileWeaponItem) {
            ProjectileWeaponItem $$4 = (ProjectileWeaponItem)$$3;
            if (p_22633_.m_5886_((ProjectileWeaponItem)$$3)) {
                int $$5 = $$4.m_6615_() - p_22635_;
                return p_22633_.m_19950_(p_22634_, $$5);
            }
        }
        return p_22633_.m_217066_(p_22634_);
    }

    public static boolean m_22598_(LivingEntity p_22599_, LivingEntity p_22600_, double p_22601_) {
        Optional<LivingEntity> $$3 = p_22599_.m_6274_().m_21952_(MemoryModuleType.f_26372_);
        if ($$3.isEmpty()) {
            return false;
        }
        double $$4 = p_22599_.m_20238_($$3.get().m_20182_());
        double $$5 = p_22599_.m_20238_(p_22600_.m_20182_());
        return $$5 > $$4 + p_22601_ * p_22601_;
    }

    public static boolean m_22667_(LivingEntity p_22668_, LivingEntity p_22669_) {
        Brain<NearestVisibleLivingEntities> $$2 = p_22668_.m_6274_();
        if (!$$2.m_21874_(MemoryModuleType.f_148205_)) {
            return false;
        }
        return $$2.m_21952_(MemoryModuleType.f_148205_).get().m_186107_(p_22669_);
    }

    public static LivingEntity m_22625_(LivingEntity p_22626_, Optional<LivingEntity> p_22627_, LivingEntity p_22628_) {
        if (p_22627_.isEmpty()) {
            return p_22628_;
        }
        return BehaviorUtils.m_22606_(p_22626_, p_22627_.get(), p_22628_);
    }

    public static LivingEntity m_22606_(LivingEntity p_22607_, LivingEntity p_22608_, LivingEntity p_22609_) {
        Vec3 $$3 = p_22608_.m_20182_();
        Vec3 $$4 = p_22609_.m_20182_();
        return p_22607_.m_20238_($$3) < p_22607_.m_20238_($$4) ? p_22608_ : p_22609_;
    }

    public static Optional<LivingEntity> m_22610_(LivingEntity p_22611_, MemoryModuleType<UUID> p_22612_) {
        Optional<UUID> $$2 = p_22611_.m_6274_().m_21952_(p_22612_);
        return $$2.map(p_186027_ -> ((ServerLevel)p_186026_.f_19853_).m_8791_((UUID)p_186027_)).map(p_186019_ -> {
            LivingEntity $$1;
            return p_186019_ instanceof LivingEntity ? ($$1 = (LivingEntity)p_186019_) : null;
        });
    }

    public static Stream<Villager> m_22650_(Villager p_22651_, Predicate<Villager> p_22652_) {
        return p_22651_.m_6274_().m_21952_(MemoryModuleType.f_148204_).map(p_186034_ -> p_186034_.stream().filter(p_186030_ -> p_186030_ instanceof Villager && p_186030_ != p_22651_).map(p_186024_ -> (Villager)p_186024_).filter(LivingEntity::m_6084_).filter(p_22652_)).orElseGet(Stream::empty);
    }

    @Nullable
    public static Vec3 m_147444_(PathfinderMob p_147445_, int p_147446_, int p_147447_) {
        Vec3 $$3 = DefaultRandomPos.m_148403_(p_147445_, p_147446_, p_147447_);
        int $$4 = 0;
        while ($$3 != null && !p_147445_.f_19853_.m_8055_(new BlockPos($$3)).m_60647_(p_147445_.f_19853_, new BlockPos($$3), PathComputationType.WATER) && $$4++ < 10) {
            $$3 = DefaultRandomPos.m_148403_(p_147445_, p_147446_, p_147447_);
        }
        return $$3;
    }

    public static boolean m_217126_(LivingEntity p_217127_) {
        return p_217127_.m_6274_().m_21874_(MemoryModuleType.f_26375_);
    }
}

