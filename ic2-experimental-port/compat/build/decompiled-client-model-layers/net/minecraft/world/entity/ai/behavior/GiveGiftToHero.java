/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class GiveGiftToHero
extends Behavior<Villager> {
    private static final int f_147546_ = 5;
    private static final int f_147547_ = 600;
    private static final int f_147548_ = 6600;
    private static final int f_147549_ = 20;
    private static final Map<VillagerProfession, ResourceLocation> f_147550_ = Util.m_137469_(Maps.newHashMap(), p_23020_ -> {
        p_23020_.put(VillagerProfession.f_35586_, BuiltInLootTables.f_78725_);
        p_23020_.put(VillagerProfession.f_35587_, BuiltInLootTables.f_78726_);
        p_23020_.put(VillagerProfession.f_35588_, BuiltInLootTables.f_78727_);
        p_23020_.put(VillagerProfession.f_35589_, BuiltInLootTables.f_78728_);
        p_23020_.put(VillagerProfession.f_35590_, BuiltInLootTables.f_78729_);
        p_23020_.put(VillagerProfession.f_35591_, BuiltInLootTables.f_78730_);
        p_23020_.put(VillagerProfession.f_35592_, BuiltInLootTables.f_78731_);
        p_23020_.put(VillagerProfession.f_35593_, BuiltInLootTables.f_78732_);
        p_23020_.put(VillagerProfession.f_35594_, BuiltInLootTables.f_78733_);
        p_23020_.put(VillagerProfession.f_35595_, BuiltInLootTables.f_78734_);
        p_23020_.put(VillagerProfession.f_35597_, BuiltInLootTables.f_78735_);
        p_23020_.put(VillagerProfession.f_35598_, BuiltInLootTables.f_78736_);
        p_23020_.put(VillagerProfession.f_35599_, BuiltInLootTables.f_78737_);
    });
    private static final float f_147551_ = 0.5f;
    private int f_22987_ = 600;
    private boolean f_22988_;
    private long f_22989_;

    public GiveGiftToHero(int p_22992_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26374_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26368_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), p_22992_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23003_, Villager p_23004_) {
        if (!this.m_23029_(p_23004_)) {
            return false;
        }
        if (this.f_22987_ > 0) {
            --this.f_22987_;
            return false;
        }
        return true;
    }

    @Override
    protected void m_6735_(ServerLevel p_23006_, Villager p_23007_, long p_23008_) {
        this.f_22988_ = false;
        this.f_22989_ = p_23008_;
        Player $$3 = this.m_23039_(p_23007_).get();
        p_23007_.m_6274_().m_21879_(MemoryModuleType.f_26374_, $$3);
        BehaviorUtils.m_22595_(p_23007_, $$3);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23026_, Villager p_23027_, long p_23028_) {
        return this.m_23029_(p_23027_) && !this.f_22988_;
    }

    @Override
    protected void m_6725_(ServerLevel p_23036_, Villager p_23037_, long p_23038_) {
        Player $$3 = this.m_23039_(p_23037_).get();
        BehaviorUtils.m_22595_(p_23037_, $$3);
        if (this.m_23014_(p_23037_, $$3)) {
            if (p_23038_ - this.f_22989_ > 20L) {
                this.m_23011_(p_23037_, $$3);
                this.f_22988_ = true;
            }
        } else {
            BehaviorUtils.m_22590_(p_23037_, $$3, 0.5f, 5);
        }
    }

    @Override
    protected void m_6732_(ServerLevel p_23046_, Villager p_23047_, long p_23048_) {
        this.f_22987_ = GiveGiftToHero.m_22993_(p_23046_);
        p_23047_.m_6274_().m_21936_(MemoryModuleType.f_26374_);
        p_23047_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        p_23047_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
    }

    private void m_23011_(Villager p_23012_, LivingEntity p_23013_) {
        List<ItemStack> $$2 = this.m_23009_(p_23012_);
        for (ItemStack $$3 : $$2) {
            BehaviorUtils.m_22613_(p_23012_, $$3, p_23013_.m_20182_());
        }
    }

    private List<ItemStack> m_23009_(Villager p_23010_) {
        if (p_23010_.m_6162_()) {
            return ImmutableList.of((Object)new ItemStack(Items.f_41940_));
        }
        VillagerProfession $$1 = p_23010_.m_7141_().m_35571_();
        if (f_147550_.containsKey($$1)) {
            LootTable $$2 = p_23010_.f_19853_.m_7654_().m_129898_().m_79217_(f_147550_.get($$1));
            LootContext.Builder $$3 = new LootContext.Builder((ServerLevel)p_23010_.f_19853_).m_78972_(LootContextParams.f_81460_, p_23010_.m_20182_()).m_78972_(LootContextParams.f_81455_, p_23010_).m_230911_(p_23010_.m_217043_());
            return $$2.m_230922_($$3.m_78975_(LootContextParamSets.f_81416_));
        }
        return ImmutableList.of((Object)new ItemStack(Items.f_42404_));
    }

    private boolean m_23029_(Villager p_23030_) {
        return this.m_23039_(p_23030_).isPresent();
    }

    private Optional<Player> m_23039_(Villager p_23040_) {
        return p_23040_.m_6274_().m_21952_(MemoryModuleType.f_26368_).filter(this::m_23017_);
    }

    private boolean m_23017_(Player p_23018_) {
        return p_23018_.m_21023_(MobEffects.f_19595_);
    }

    private boolean m_23014_(Villager p_23015_, Player p_23016_) {
        BlockPos $$2 = p_23016_.m_20183_();
        BlockPos $$3 = p_23015_.m_20183_();
        return $$3.m_123314_($$2, 5.0);
    }

    private static int m_22993_(ServerLevel p_22994_) {
        return 600 + p_22994_.f_46441_.m_188503_(6001);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Villager)livingEntity, l);
    }
}

