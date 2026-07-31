/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class ShowTradesToPlayer
extends Behavior<Villager> {
    private static final int f_147915_ = 900;
    private static final int f_147916_ = 40;
    @Nullable
    private ItemStack f_24090_;
    private final List<ItemStack> f_24091_ = Lists.newArrayList();
    private int f_24092_;
    private int f_24093_;
    private int f_24094_;

    public ShowTradesToPlayer(int p_24096_, int p_24097_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26374_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), p_24096_, p_24097_);
    }

    @Override
    public boolean m_6114_(ServerLevel p_24106_, Villager p_24107_) {
        Brain<Villager> $$2 = p_24107_.m_6274_();
        if (!$$2.m_21952_(MemoryModuleType.f_26374_).isPresent()) {
            return false;
        }
        LivingEntity $$3 = $$2.m_21952_(MemoryModuleType.f_26374_).get();
        return $$3.m_6095_() == EntityType.f_20532_ && p_24107_.m_6084_() && $$3.m_6084_() && !p_24107_.m_6162_() && p_24107_.m_20280_($$3) <= 17.0;
    }

    @Override
    public boolean m_6737_(ServerLevel p_24109_, Villager p_24110_, long p_24111_) {
        return this.m_6114_(p_24109_, p_24110_) && this.f_24094_ > 0 && p_24110_.m_6274_().m_21952_(MemoryModuleType.f_26374_).isPresent();
    }

    @Override
    public void m_6735_(ServerLevel p_24124_, Villager p_24125_, long p_24126_) {
        super.m_6735_(p_24124_, p_24125_, p_24126_);
        this.m_24137_(p_24125_);
        this.f_24092_ = 0;
        this.f_24093_ = 0;
        this.f_24094_ = 40;
    }

    @Override
    public void m_6725_(ServerLevel p_24134_, Villager p_24135_, long p_24136_) {
        LivingEntity $$3 = this.m_24137_(p_24135_);
        this.m_24112_($$3, p_24135_);
        if (!this.f_24091_.isEmpty()) {
            this.m_24147_(p_24135_);
        } else {
            ShowTradesToPlayer.m_182373_(p_24135_);
            this.f_24094_ = Math.min(this.f_24094_, 40);
        }
        --this.f_24094_;
    }

    @Override
    public void m_6732_(ServerLevel p_24144_, Villager p_24145_, long p_24146_) {
        super.m_6732_(p_24144_, p_24145_, p_24146_);
        p_24145_.m_6274_().m_21936_(MemoryModuleType.f_26374_);
        ShowTradesToPlayer.m_182373_(p_24145_);
        this.f_24090_ = null;
    }

    private void m_24112_(LivingEntity p_24113_, Villager p_24114_) {
        boolean $$2 = false;
        ItemStack $$3 = p_24113_.m_21205_();
        if (this.f_24090_ == null || !ItemStack.m_41746_(this.f_24090_, $$3)) {
            this.f_24090_ = $$3;
            $$2 = true;
            this.f_24091_.clear();
        }
        if ($$2 && !this.f_24090_.m_41619_()) {
            this.m_24127_(p_24114_);
            if (!this.f_24091_.isEmpty()) {
                this.f_24094_ = 900;
                this.m_24115_(p_24114_);
            }
        }
    }

    private void m_24115_(Villager p_24116_) {
        ShowTradesToPlayer.m_182370_(p_24116_, this.f_24091_.get(0));
    }

    private void m_24127_(Villager p_24128_) {
        for (MerchantOffer $$1 : p_24128_.m_6616_()) {
            if ($$1.m_45380_() || !this.m_24117_($$1)) continue;
            this.f_24091_.add($$1.m_45368_());
        }
    }

    private boolean m_24117_(MerchantOffer p_24118_) {
        return ItemStack.m_41746_(this.f_24090_, p_24118_.m_45358_()) || ItemStack.m_41746_(this.f_24090_, p_24118_.m_45364_());
    }

    private static void m_182373_(Villager p_182374_) {
        p_182374_.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
        p_182374_.m_21409_(EquipmentSlot.MAINHAND, 0.085f);
    }

    private static void m_182370_(Villager p_182371_, ItemStack p_182372_) {
        p_182371_.m_8061_(EquipmentSlot.MAINHAND, p_182372_);
        p_182371_.m_21409_(EquipmentSlot.MAINHAND, 0.0f);
    }

    private LivingEntity m_24137_(Villager p_24138_) {
        Brain<Villager> $$1 = p_24138_.m_6274_();
        LivingEntity $$2 = $$1.m_21952_(MemoryModuleType.f_26374_).get();
        $$1.m_21879_(MemoryModuleType.f_26371_, new EntityTracker($$2, true));
        return $$2;
    }

    private void m_24147_(Villager p_24148_) {
        if (this.f_24091_.size() >= 2 && ++this.f_24092_ >= 40) {
            ++this.f_24093_;
            this.f_24092_ = 0;
            if (this.f_24093_ > this.f_24091_.size() - 1) {
                this.f_24093_ = 0;
            }
            ShowTradesToPlayer.m_182370_(p_24148_, this.f_24091_.get(this.f_24093_));
        }
    }

    @Override
    public /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    public /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    public /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    public /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Villager)livingEntity, l);
    }
}

