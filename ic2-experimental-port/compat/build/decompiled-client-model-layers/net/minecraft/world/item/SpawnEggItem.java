/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SpawnEggItem
extends Item {
    private static final Map<EntityType<? extends Mob>, SpawnEggItem> f_43201_ = Maps.newIdentityHashMap();
    private final int f_151200_;
    private final int f_151201_;
    private final EntityType<?> f_43204_;

    public SpawnEggItem(EntityType<? extends Mob> p_43207_, int p_43208_, int p_43209_, Item.Properties p_43210_) {
        super(p_43210_);
        this.f_43204_ = p_43207_;
        this.f_151200_ = p_43208_;
        this.f_151201_ = p_43209_;
        f_43201_.put(p_43207_, this);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_43223_) {
        BlockPos $$10;
        BlockEntity $$6;
        Level $$1 = p_43223_.m_43725_();
        if (!($$1 instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack $$2 = p_43223_.m_43722_();
        BlockPos $$3 = p_43223_.m_8083_();
        Direction $$4 = p_43223_.m_43719_();
        BlockState $$5 = $$1.m_8055_($$3);
        if ($$5.m_60713_(Blocks.f_50085_) && ($$6 = $$1.m_7702_($$3)) instanceof SpawnerBlockEntity) {
            BaseSpawner $$7 = ((SpawnerBlockEntity)$$6).m_59801_();
            EntityType<?> $$8 = this.m_43228_($$2.m_41783_());
            $$7.m_45462_($$8);
            $$6.m_6596_();
            $$1.m_7260_($$3, $$5, $$5, 3);
            $$1.m_142346_(p_43223_.m_43723_(), GameEvent.f_157792_, $$3);
            $$2.m_41774_(1);
            return InteractionResult.CONSUME;
        }
        if ($$5.m_60812_($$1, $$3).m_83281_()) {
            BlockPos $$9 = $$3;
        } else {
            $$10 = $$3.m_121945_($$4);
        }
        EntityType<?> $$11 = this.m_43228_($$2.m_41783_());
        if ($$11.m_20592_((ServerLevel)$$1, $$2, p_43223_.m_43723_(), $$10, MobSpawnType.SPAWN_EGG, true, !Objects.equals($$3, $$10) && $$4 == Direction.UP) != null) {
            $$2.m_41774_(1);
            $$1.m_142346_(p_43223_.m_43723_(), GameEvent.f_157810_, $$3);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_43225_, Player p_43226_, InteractionHand p_43227_) {
        ItemStack $$3 = p_43226_.m_21120_(p_43227_);
        BlockHitResult $$4 = SpawnEggItem.m_41435_(p_43225_, p_43226_, ClipContext.Fluid.SOURCE_ONLY);
        if (((HitResult)$$4).m_6662_() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.m_19098_($$3);
        }
        if (!(p_43225_ instanceof ServerLevel)) {
            return InteractionResultHolder.m_19090_($$3);
        }
        BlockHitResult $$5 = $$4;
        BlockPos $$6 = $$5.m_82425_();
        if (!(p_43225_.m_8055_($$6).m_60734_() instanceof LiquidBlock)) {
            return InteractionResultHolder.m_19098_($$3);
        }
        if (!p_43225_.m_7966_(p_43226_, $$6) || !p_43226_.m_36204_($$6, $$5.m_82434_(), $$3)) {
            return InteractionResultHolder.m_19100_($$3);
        }
        EntityType<?> $$7 = this.m_43228_($$3.m_41783_());
        Entity $$8 = $$7.m_20592_((ServerLevel)p_43225_, $$3, p_43226_, $$6, MobSpawnType.SPAWN_EGG, false, false);
        if ($$8 == null) {
            return InteractionResultHolder.m_19098_($$3);
        }
        if (!p_43226_.m_150110_().f_35937_) {
            $$3.m_41774_(1);
        }
        p_43226_.m_36246_(Stats.f_12982_.m_12902_(this));
        p_43225_.m_220400_(p_43226_, GameEvent.f_157810_, $$8.m_20182_());
        return InteractionResultHolder.m_19096_($$3);
    }

    public boolean m_43230_(@Nullable CompoundTag p_43231_, EntityType<?> p_43232_) {
        return Objects.equals(this.m_43228_(p_43231_), p_43232_);
    }

    public int m_43211_(int p_43212_) {
        return p_43212_ == 0 ? this.f_151200_ : this.f_151201_;
    }

    @Nullable
    public static SpawnEggItem m_43213_(@Nullable EntityType<?> p_43214_) {
        return f_43201_.get(p_43214_);
    }

    public static Iterable<SpawnEggItem> m_43233_() {
        return Iterables.unmodifiableIterable(f_43201_.values());
    }

    public EntityType<?> m_43228_(@Nullable CompoundTag p_43229_) {
        CompoundTag $$1;
        if (p_43229_ != null && p_43229_.m_128425_("EntityTag", 10) && ($$1 = p_43229_.m_128469_("EntityTag")).m_128425_("id", 8)) {
            return EntityType.m_20632_($$1.m_128461_("id")).orElse(this.f_43204_);
        }
        return this.f_43204_;
    }

    public Optional<Mob> m_43215_(Player p_43216_, Mob p_43217_, EntityType<? extends Mob> p_43218_, ServerLevel p_43219_, Vec3 p_43220_, ItemStack p_43221_) {
        Mob $$7;
        if (!this.m_43230_(p_43221_.m_41783_(), p_43218_)) {
            return Optional.empty();
        }
        if (p_43217_ instanceof AgeableMob) {
            AgeableMob $$6 = ((AgeableMob)p_43217_).m_142606_(p_43219_, (AgeableMob)p_43217_);
        } else {
            $$7 = p_43218_.m_20615_(p_43219_);
        }
        if ($$7 == null) {
            return Optional.empty();
        }
        $$7.m_6863_(true);
        if (!$$7.m_6162_()) {
            return Optional.empty();
        }
        $$7.m_7678_(p_43220_.m_7096_(), p_43220_.m_7098_(), p_43220_.m_7094_(), 0.0f, 0.0f);
        p_43219_.m_47205_($$7);
        if (p_43221_.m_41788_()) {
            $$7.m_6593_(p_43221_.m_41786_());
        }
        if (!p_43216_.m_150110_().f_35937_) {
            p_43221_.m_41774_(1);
        }
        return Optional.of($$7);
    }
}

