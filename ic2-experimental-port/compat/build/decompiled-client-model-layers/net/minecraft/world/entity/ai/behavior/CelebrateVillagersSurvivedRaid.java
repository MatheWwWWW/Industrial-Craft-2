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
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.MoveToSkySeeingSpot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CelebrateVillagersSurvivedRaid
extends Behavior<Villager> {
    @Nullable
    private Raid f_22682_;

    public CelebrateVillagersSurvivedRaid(int p_22684_, int p_22685_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), p_22684_, p_22685_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22690_, Villager p_22691_) {
        BlockPos $$2 = p_22691_.m_20183_();
        this.f_22682_ = p_22690_.m_8832_($$2);
        return this.f_22682_ != null && this.f_22682_.m_37767_() && MoveToSkySeeingSpot.m_23558_(p_22690_, p_22691_, $$2);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_22693_, Villager p_22694_, long p_22695_) {
        return this.f_22682_ != null && !this.f_22682_.m_37762_();
    }

    @Override
    protected void m_6732_(ServerLevel p_22704_, Villager p_22705_, long p_22706_) {
        this.f_22682_ = null;
        p_22705_.m_6274_().m_21862_(p_22704_.m_46468_(), p_22704_.m_46467_());
    }

    @Override
    protected void m_6725_(ServerLevel p_22712_, Villager p_22713_, long p_22714_) {
        RandomSource $$3 = p_22713_.m_217043_();
        if ($$3.m_188503_(100) == 0) {
            p_22713_.m_35310_();
        }
        if ($$3.m_188503_(200) == 0 && MoveToSkySeeingSpot.m_23558_(p_22712_, p_22713_, p_22713_.m_20183_())) {
            DyeColor $$4 = Util.m_214670_(DyeColor.values(), $$3);
            int $$5 = $$3.m_188503_(3);
            ItemStack $$6 = this.m_22696_($$4, $$5);
            FireworkRocketEntity $$7 = new FireworkRocketEntity(p_22713_.f_19853_, p_22713_, p_22713_.m_20185_(), p_22713_.m_20188_(), p_22713_.m_20189_(), $$6);
            p_22713_.f_19853_.m_7967_($$7);
        }
    }

    private ItemStack m_22696_(DyeColor p_22697_, int p_22698_) {
        ItemStack $$2 = new ItemStack(Items.f_42688_, 1);
        ItemStack $$3 = new ItemStack(Items.f_42689_);
        CompoundTag $$4 = $$3.m_41698_("Explosion");
        ArrayList $$5 = Lists.newArrayList();
        $$5.add(p_22697_.m_41070_());
        $$4.m_128408_("Colors", $$5);
        $$4.m_128344_("Type", (byte)FireworkRocketItem.Shape.BURST.m_41236_());
        CompoundTag $$6 = $$2.m_41698_("Fireworks");
        ListTag $$7 = new ListTag();
        CompoundTag $$8 = $$3.m_41737_("Explosion");
        if ($$8 != null) {
            $$7.add($$8);
        }
        $$6.m_128344_("Flight", (byte)p_22698_);
        if (!$$7.isEmpty()) {
            $$6.m_128365_("Explosions", $$7);
        }
        return $$2;
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
}

