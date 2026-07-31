/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.selector;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntitySelector {
    public static final int f_175099_ = Integer.MAX_VALUE;
    private static final EntityTypeTest<Entity, ?> f_175100_ = new EntityTypeTest<Entity, Entity>(){

        @Override
        public Entity m_141992_(Entity p_175109_) {
            return p_175109_;
        }

        @Override
        public Class<? extends Entity> m_142225_() {
            return Entity.class;
        }
    };
    private final int f_121111_;
    private final boolean f_121112_;
    private final boolean f_121113_;
    private final Predicate<Entity> f_121114_;
    private final MinMaxBounds.Doubles f_121115_;
    private final Function<Vec3, Vec3> f_121116_;
    @Nullable
    private final AABB f_121117_;
    private final BiConsumer<Vec3, List<? extends Entity>> f_121118_;
    private final boolean f_121119_;
    @Nullable
    private final String f_121120_;
    @Nullable
    private final UUID f_121121_;
    private EntityTypeTest<Entity, ?> f_121122_;
    private final boolean f_121123_;

    public EntitySelector(int p_121125_, boolean p_121126_, boolean p_121127_, Predicate<Entity> p_121128_, MinMaxBounds.Doubles p_121129_, Function<Vec3, Vec3> p_121130_, @Nullable AABB p_121131_, BiConsumer<Vec3, List<? extends Entity>> p_121132_, boolean p_121133_, @Nullable String p_121134_, @Nullable UUID p_121135_, @Nullable EntityType<?> p_121136_, boolean p_121137_) {
        this.f_121111_ = p_121125_;
        this.f_121112_ = p_121126_;
        this.f_121113_ = p_121127_;
        this.f_121114_ = p_121128_;
        this.f_121115_ = p_121129_;
        this.f_121116_ = p_121130_;
        this.f_121117_ = p_121131_;
        this.f_121118_ = p_121132_;
        this.f_121119_ = p_121133_;
        this.f_121120_ = p_121134_;
        this.f_121121_ = p_121135_;
        this.f_121122_ = p_121136_ == null ? f_175100_ : p_121136_;
        this.f_121123_ = p_121137_;
    }

    public int m_121138_() {
        return this.f_121111_;
    }

    public boolean m_121159_() {
        return this.f_121112_;
    }

    public boolean m_121162_() {
        return this.f_121119_;
    }

    public boolean m_121165_() {
        return this.f_121113_;
    }

    public boolean m_175105_() {
        return this.f_121123_;
    }

    private void m_121168_(CommandSourceStack p_121169_) throws CommandSyntaxException {
        if (this.f_121123_ && !p_121169_.m_6761_(2)) {
            throw EntityArgument.f_91441_.create();
        }
    }

    public Entity m_121139_(CommandSourceStack p_121140_) throws CommandSyntaxException {
        this.m_121168_(p_121140_);
        List<? extends Entity> $$1 = this.m_121160_(p_121140_);
        if ($$1.isEmpty()) {
            throw EntityArgument.f_91439_.create();
        }
        if ($$1.size() > 1) {
            throw EntityArgument.f_91436_.create();
        }
        return $$1.get(0);
    }

    public List<? extends Entity> m_121160_(CommandSourceStack p_121161_) throws CommandSyntaxException {
        this.m_121168_(p_121161_);
        if (!this.f_121112_) {
            return this.m_121166_(p_121161_);
        }
        if (this.f_121120_ != null) {
            ServerPlayer $$1 = p_121161_.m_81377_().m_6846_().m_11255_(this.f_121120_);
            if ($$1 == null) {
                return Collections.emptyList();
            }
            return Lists.newArrayList((Object[])new ServerPlayer[]{$$1});
        }
        if (this.f_121121_ != null) {
            for (ServerLevel $$2 : p_121161_.m_81377_().m_129785_()) {
                Entity $$3 = $$2.m_8791_(this.f_121121_);
                if ($$3 == null) continue;
                return Lists.newArrayList((Object[])new Entity[]{$$3});
            }
            return Collections.emptyList();
        }
        Vec3 $$4 = this.f_121116_.apply(p_121161_.m_81371_());
        Predicate<Entity> $$5 = this.m_121144_($$4);
        if (this.f_121119_) {
            if (p_121161_.m_81373_() != null && $$5.test(p_121161_.m_81373_())) {
                return Lists.newArrayList((Object[])new Entity[]{p_121161_.m_81373_()});
            }
            return Collections.emptyList();
        }
        ArrayList $$6 = Lists.newArrayList();
        if (this.m_121165_()) {
            this.m_121154_($$6, p_121161_.m_81372_(), $$4, $$5);
        } else {
            for (ServerLevel $$7 : p_121161_.m_81377_().m_129785_()) {
                this.m_121154_($$6, $$7, $$4, $$5);
            }
        }
        return this.m_121149_($$4, $$6);
    }

    private void m_121154_(List<Entity> p_121155_, ServerLevel p_121156_, Vec3 p_121157_, Predicate<Entity> p_121158_) {
        if (this.f_121117_ != null) {
            p_121155_.addAll(p_121156_.m_142425_(this.f_121122_, this.f_121117_.m_82383_(p_121157_), p_121158_));
        } else {
            p_121155_.addAll(p_121156_.m_143280_(this.f_121122_, p_121158_));
        }
    }

    public ServerPlayer m_121163_(CommandSourceStack p_121164_) throws CommandSyntaxException {
        this.m_121168_(p_121164_);
        List<ServerPlayer> $$1 = this.m_121166_(p_121164_);
        if ($$1.size() != 1) {
            throw EntityArgument.f_91440_.create();
        }
        return $$1.get(0);
    }

    public List<ServerPlayer> m_121166_(CommandSourceStack p_121167_) throws CommandSyntaxException {
        ArrayList $$7;
        this.m_121168_(p_121167_);
        if (this.f_121120_ != null) {
            ServerPlayer $$1 = p_121167_.m_81377_().m_6846_().m_11255_(this.f_121120_);
            if ($$1 == null) {
                return Collections.emptyList();
            }
            return Lists.newArrayList((Object[])new ServerPlayer[]{$$1});
        }
        if (this.f_121121_ != null) {
            ServerPlayer $$2 = p_121167_.m_81377_().m_6846_().m_11259_(this.f_121121_);
            if ($$2 == null) {
                return Collections.emptyList();
            }
            return Lists.newArrayList((Object[])new ServerPlayer[]{$$2});
        }
        Vec3 $$3 = this.f_121116_.apply(p_121167_.m_81371_());
        Predicate<Entity> $$4 = this.m_121144_($$3);
        if (this.f_121119_) {
            ServerPlayer $$5;
            if (p_121167_.m_81373_() instanceof ServerPlayer && $$4.test($$5 = (ServerPlayer)p_121167_.m_81373_())) {
                return Lists.newArrayList((Object[])new ServerPlayer[]{$$5});
            }
            return Collections.emptyList();
        }
        if (this.m_121165_()) {
            List<ServerPlayer> $$6 = p_121167_.m_81372_().m_8795_($$4);
        } else {
            $$7 = Lists.newArrayList();
            for (ServerPlayer $$8 : p_121167_.m_81377_().m_6846_().m_11314_()) {
                if (!$$4.test($$8)) continue;
                $$7.add($$8);
            }
        }
        return this.m_121149_($$3, $$7);
    }

    private Predicate<Entity> m_121144_(Vec3 p_121145_) {
        Predicate<Entity> $$1 = this.f_121114_;
        if (this.f_121117_ != null) {
            AABB $$2 = this.f_121117_.m_82383_(p_121145_);
            $$1 = $$1.and(p_121143_ -> $$2.m_82381_(p_121143_.m_20191_()));
        }
        if (!this.f_121115_.m_55327_()) {
            $$1 = $$1.and(p_121148_ -> this.f_121115_.m_154812_(p_121148_.m_20238_(p_121145_)));
        }
        return $$1;
    }

    private <T extends Entity> List<T> m_121149_(Vec3 p_121150_, List<T> p_121151_) {
        if (p_121151_.size() > 1) {
            this.f_121118_.accept(p_121150_, p_121151_);
        }
        return p_121151_.subList(0, Math.min(this.f_121111_, p_121151_.size()));
    }

    public static Component m_175103_(List<? extends Entity> p_175104_) {
        return ComponentUtils.m_178440_(p_175104_, Entity::m_5446_);
    }
}

