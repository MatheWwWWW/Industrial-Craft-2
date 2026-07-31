/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.entity.monster.warden;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WardenSpawnTracker {
    public static final Codec<WardenSpawnTracker> f_219557_ = RecordCodecBuilder.create(p_219589_ -> p_219589_.group((App)ExtraCodecs.f_144628_.fieldOf("ticks_since_last_warning").orElse((Object)0).forGetter(p_219607_ -> p_219607_.f_219563_), (App)ExtraCodecs.f_144628_.fieldOf("warning_level").orElse((Object)0).forGetter(p_219604_ -> p_219604_.f_219564_), (App)ExtraCodecs.f_144628_.fieldOf("cooldown_ticks").orElse((Object)0).forGetter(p_219601_ -> p_219601_.f_219565_)).apply((Applicative)p_219589_, WardenSpawnTracker::new));
    public static final int f_219558_ = 4;
    private static final double f_219559_ = 16.0;
    private static final int f_219560_ = 48;
    private static final int f_219561_ = 12000;
    private static final int f_219562_ = 200;
    private int f_219563_;
    private int f_219564_;
    private int f_219565_;

    public WardenSpawnTracker(int p_219568_, int p_219569_, int p_219570_) {
        this.f_219563_ = p_219568_;
        this.f_219564_ = p_219569_;
        this.f_219565_ = p_219570_;
    }

    public void m_219571_() {
        if (this.f_219563_ >= 12000) {
            this.m_219608_();
            this.f_219563_ = 0;
        } else {
            ++this.f_219563_;
        }
        if (this.f_219565_ > 0) {
            --this.f_219565_;
        }
    }

    public void m_219593_() {
        this.f_219563_ = 0;
        this.f_219564_ = 0;
        this.f_219565_ = 0;
    }

    public static OptionalInt m_219577_(ServerLevel p_219578_, BlockPos p_219579_, ServerPlayer p_219580_) {
        if (WardenSpawnTracker.m_219574_(p_219578_, p_219579_)) {
            return OptionalInt.empty();
        }
        List<ServerPlayer> $$3 = WardenSpawnTracker.m_219594_(p_219578_, p_219579_);
        if (!$$3.contains(p_219580_)) {
            $$3.add(p_219580_);
        }
        if ($$3.stream().anyMatch(p_219582_ -> p_219582_.m_219758_().m_219602_())) {
            return OptionalInt.empty();
        }
        Optional<WardenSpawnTracker> $$4 = $$3.stream().map(Player::m_219758_).max(Comparator.comparingInt(p_219598_ -> p_219598_.f_219564_));
        WardenSpawnTracker $$5 = $$4.get();
        $$5.m_219605_();
        $$3.forEach(p_219587_ -> p_219587_.m_219758_().m_219583_($$5));
        return OptionalInt.of($$5.f_219564_);
    }

    private boolean m_219602_() {
        return this.f_219565_ > 0;
    }

    private static boolean m_219574_(ServerLevel p_219575_, BlockPos p_219576_) {
        AABB $$2 = AABB.m_165882_(Vec3.m_82512_(p_219576_), 48.0, 48.0, 48.0);
        return !p_219575_.m_45976_(Warden.class, $$2).isEmpty();
    }

    private static List<ServerPlayer> m_219594_(ServerLevel p_219595_, BlockPos p_219596_) {
        Vec3 $$2 = Vec3.m_82512_(p_219596_);
        Predicate<ServerPlayer> $$3 = p_219592_ -> p_219592_.m_20182_().m_82509_($$2, 16.0);
        return p_219595_.m_8795_($$3.and(LivingEntity::m_6084_).and(EntitySelector.f_20408_));
    }

    private void m_219605_() {
        if (!this.m_219602_()) {
            this.f_219563_ = 0;
            this.f_219565_ = 200;
            this.m_219572_(this.m_219599_() + 1);
        }
    }

    private void m_219608_() {
        this.m_219572_(this.m_219599_() - 1);
    }

    public void m_219572_(int p_219573_) {
        this.f_219564_ = Mth.m_14045_(p_219573_, 0, 4);
    }

    public int m_219599_() {
        return this.f_219564_;
    }

    private void m_219583_(WardenSpawnTracker p_219584_) {
        this.f_219564_ = p_219584_.f_219564_;
        this.f_219565_ = p_219584_.f_219565_;
        this.f_219563_ = p_219584_.f_219563_;
    }
}

