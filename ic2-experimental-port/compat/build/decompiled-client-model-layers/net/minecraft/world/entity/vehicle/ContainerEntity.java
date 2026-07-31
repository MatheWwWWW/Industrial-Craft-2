/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.vehicle;

import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public interface ContainerEntity
extends Container,
MenuProvider {
    public Vec3 m_20182_();

    @Nullable
    public ResourceLocation m_214142_();

    public void m_214199_(@Nullable ResourceLocation var1);

    public long m_213803_();

    public void m_214065_(long var1);

    public NonNullList<ItemStack> m_213659_();

    public void m_213775_();

    public Level m_9236_();

    public boolean m_213877_();

    @Override
    default public boolean m_7983_() {
        return this.m_219956_();
    }

    default public void m_219943_(CompoundTag p_219944_) {
        if (this.m_214142_() != null) {
            p_219944_.m_128359_("LootTable", this.m_214142_().toString());
            if (this.m_213803_() != 0L) {
                p_219944_.m_128356_("LootTableSeed", this.m_213803_());
            }
        } else {
            ContainerHelper.m_18973_(p_219944_, this.m_213659_());
        }
    }

    default public void m_219934_(CompoundTag p_219935_) {
        this.m_213775_();
        if (p_219935_.m_128425_("LootTable", 8)) {
            this.m_214199_(new ResourceLocation(p_219935_.m_128461_("LootTable")));
            this.m_214065_(p_219935_.m_128454_("LootTableSeed"));
        } else {
            ContainerHelper.m_18980_(p_219935_, this.m_213659_());
        }
    }

    default public void m_219927_(DamageSource p_219928_, Level p_219929_, Entity p_219930_) {
        Entity $$3;
        if (!p_219929_.m_46469_().m_46207_(GameRules.f_46137_)) {
            return;
        }
        Containers.m_18998_(p_219929_, p_219930_, this);
        if (!p_219929_.f_46443_ && ($$3 = p_219928_.m_7640_()) != null && $$3.m_6095_() == EntityType.f_20532_) {
            PiglinAi.m_34873_((Player)$$3, true);
        }
    }

    default public InteractionResult m_219931_(BiConsumer<GameEvent, Entity> p_219932_, Player p_219933_) {
        p_219933_.m_5893_(this);
        if (!p_219933_.f_19853_.f_46443_) {
            p_219932_.accept(GameEvent.f_157803_, p_219933_);
            PiglinAi.m_34873_(p_219933_, true);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    default public void m_219949_(@Nullable Player p_219950_) {
        MinecraftServer $$1 = this.m_9236_().m_7654_();
        if (this.m_214142_() != null && $$1 != null) {
            LootTable $$2 = $$1.m_129898_().m_79217_(this.m_214142_());
            if (p_219950_ != null) {
                CriteriaTriggers.f_10563_.m_54597_((ServerPlayer)p_219950_, this.m_214142_());
            }
            this.m_214199_(null);
            LootContext.Builder $$3 = new LootContext.Builder((ServerLevel)this.m_9236_()).m_78972_(LootContextParams.f_81460_, this.m_20182_()).m_78965_(this.m_213803_());
            if (p_219950_ != null) {
                $$3.m_78963_(p_219950_.m_36336_()).m_78972_(LootContextParams.f_81455_, p_219950_);
            }
            $$2.m_79123_(this, $$3.m_78975_(LootContextParamSets.f_81411_));
        }
    }

    default public void m_219953_() {
        this.m_219949_(null);
        this.m_213659_().clear();
    }

    default public boolean m_219956_() {
        for (ItemStack $$0 : this.m_213659_()) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }

    default public ItemStack m_219945_(int p_219946_) {
        this.m_219949_(null);
        ItemStack $$1 = this.m_213659_().get(p_219946_);
        if ($$1.m_41619_()) {
            return ItemStack.f_41583_;
        }
        this.m_213659_().set(p_219946_, ItemStack.f_41583_);
        return $$1;
    }

    default public ItemStack m_219947_(int p_219948_) {
        this.m_219949_(null);
        return this.m_213659_().get(p_219948_);
    }

    default public ItemStack m_219936_(int p_219937_, int p_219938_) {
        this.m_219949_(null);
        return ContainerHelper.m_18969_(this.m_213659_(), p_219937_, p_219938_);
    }

    default public void m_219940_(int p_219941_, ItemStack p_219942_) {
        this.m_219949_(null);
        this.m_213659_().set(p_219941_, p_219942_);
        if (!p_219942_.m_41619_() && p_219942_.m_41613_() > this.m_6893_()) {
            p_219942_.m_41764_(this.m_6893_());
        }
    }

    default public SlotAccess m_219951_(final int p_219952_) {
        if (p_219952_ >= 0 && p_219952_ < this.m_6643_()) {
            return new SlotAccess(){

                @Override
                public ItemStack m_142196_() {
                    return ContainerEntity.this.m_219947_(p_219952_);
                }

                @Override
                public boolean m_142104_(ItemStack p_219964_) {
                    ContainerEntity.this.m_219940_(p_219952_, p_219964_);
                    return true;
                }
            };
        }
        return SlotAccess.f_147290_;
    }

    default public boolean m_219954_(Player p_219955_) {
        return !this.m_213877_() && this.m_20182_().m_82509_(p_219955_.m_20182_(), 8.0);
    }
}

