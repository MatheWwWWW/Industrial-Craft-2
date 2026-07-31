/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TradeWithVillager
extends Behavior<Villager> {
    private static final int f_147996_ = 5;
    private static final float f_147997_ = 0.5f;
    private Set<Item> f_24406_ = ImmutableSet.of();

    public TradeWithVillager() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26374_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24416_, Villager p_24417_) {
        return BehaviorUtils.m_22639_(p_24417_.m_6274_(), MemoryModuleType.f_26374_, EntityType.f_20492_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24419_, Villager p_24420_, long p_24421_) {
        return this.m_6114_(p_24419_, p_24420_);
    }

    @Override
    protected void m_6735_(ServerLevel p_24437_, Villager p_24438_, long p_24439_) {
        Villager $$3 = (Villager)p_24438_.m_6274_().m_21952_(MemoryModuleType.f_26374_).get();
        BehaviorUtils.m_22602_(p_24438_, $$3, 0.5f);
        this.f_24406_ = TradeWithVillager.m_24422_(p_24438_, $$3);
    }

    @Override
    protected void m_6725_(ServerLevel p_24445_, Villager p_24446_, long p_24447_) {
        Villager $$3 = (Villager)p_24446_.m_6274_().m_21952_(MemoryModuleType.f_26374_).get();
        if (p_24446_.m_20280_($$3) > 5.0) {
            return;
        }
        BehaviorUtils.m_22602_(p_24446_, $$3, 0.5f);
        p_24446_.m_35411_(p_24445_, $$3, p_24447_);
        if (p_24446_.m_35514_() && (p_24446_.m_7141_().m_35571_() == VillagerProfession.f_35590_ || $$3.m_35515_())) {
            TradeWithVillager.m_24425_(p_24446_, Villager.f_35369_.keySet(), $$3);
        }
        if ($$3.m_7141_().m_35571_() == VillagerProfession.f_35590_ && p_24446_.m_35311_().m_18947_(Items.f_42405_) > Items.f_42405_.m_41459_() / 2) {
            TradeWithVillager.m_24425_(p_24446_, (Set<Item>)ImmutableSet.of((Object)Items.f_42405_), $$3);
        }
        if (!this.f_24406_.isEmpty() && p_24446_.m_35311_().m_18949_(this.f_24406_)) {
            TradeWithVillager.m_24425_(p_24446_, this.f_24406_, $$3);
        }
    }

    @Override
    protected void m_6732_(ServerLevel p_24453_, Villager p_24454_, long p_24455_) {
        p_24454_.m_6274_().m_21936_(MemoryModuleType.f_26374_);
    }

    private static Set<Item> m_24422_(Villager p_24423_, Villager p_24424_) {
        ImmutableSet<Item> $$2 = p_24424_.m_7141_().m_35571_().f_35602_();
        ImmutableSet<Item> $$3 = p_24423_.m_7141_().m_35571_().f_35602_();
        return $$2.stream().filter(p_24431_ -> !$$3.contains(p_24431_)).collect(Collectors.toSet());
    }

    private static void m_24425_(Villager p_24426_, Set<Item> p_24427_, LivingEntity p_24428_) {
        SimpleContainer $$3 = p_24426_.m_35311_();
        ItemStack $$4 = ItemStack.f_41583_;
        for (int $$5 = 0; $$5 < $$3.m_6643_(); ++$$5) {
            int $$9;
            Item $$7;
            ItemStack $$6 = $$3.m_8020_($$5);
            if ($$6.m_41619_() || !p_24427_.contains($$7 = $$6.m_41720_())) continue;
            if ($$6.m_41613_() > $$6.m_41741_() / 2) {
                int $$8 = $$6.m_41613_() / 2;
            } else {
                if ($$6.m_41613_() <= 24) continue;
                $$9 = $$6.m_41613_() - 24;
            }
            $$6.m_41774_($$9);
            $$4 = new ItemStack($$7, $$9);
            break;
        }
        if (!$$4.m_41619_()) {
            BehaviorUtils.m_22613_(p_24426_, $$4, p_24428_.m_20182_());
        }
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Villager)livingEntity, l);
    }
}

