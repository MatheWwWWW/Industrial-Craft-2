/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.npc;

import com.google.common.collect.ImmutableSet;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public record VillagerProfession(String f_35600_, Predicate<Holder<PoiType>> f_219628_, Predicate<Holder<PoiType>> f_219629_, ImmutableSet<Item> f_35602_, ImmutableSet<Block> f_35603_, @Nullable SoundEvent f_35604_) {
    public static final Predicate<Holder<PoiType>> f_219627_ = p_238239_ -> p_238239_.m_203656_(PoiTypeTags.f_215875_);
    public static final VillagerProfession f_35585_ = VillagerProfession.m_219653_("none", PoiType.f_218034_, f_219627_, null);
    public static final VillagerProfession f_35586_ = VillagerProfession.m_219643_("armorer", PoiTypes.f_218047_, SoundEvents.f_12510_);
    public static final VillagerProfession f_35587_ = VillagerProfession.m_219643_("butcher", PoiTypes.f_218048_, SoundEvents.f_12564_);
    public static final VillagerProfession f_35588_ = VillagerProfession.m_219643_("cartographer", PoiTypes.f_218049_, SoundEvents.f_12565_);
    public static final VillagerProfession f_35589_ = VillagerProfession.m_219643_("cleric", PoiTypes.f_218050_, SoundEvents.f_12566_);
    public static final VillagerProfession f_35590_ = VillagerProfession.m_219647_("farmer", PoiTypes.f_218051_, (ImmutableSet<Item>)ImmutableSet.of((Object)Items.f_42405_, (Object)Items.f_42404_, (Object)Items.f_42733_, (Object)Items.f_42499_), (ImmutableSet<Block>)ImmutableSet.of((Object)Blocks.f_50093_), SoundEvents.f_12567_);
    public static final VillagerProfession f_35591_ = VillagerProfession.m_219643_("fisherman", PoiTypes.f_218052_, SoundEvents.f_12568_);
    public static final VillagerProfession f_35592_ = VillagerProfession.m_219643_("fletcher", PoiTypes.f_218053_, SoundEvents.f_12569_);
    public static final VillagerProfession f_35593_ = VillagerProfession.m_219643_("leatherworker", PoiTypes.f_218054_, SoundEvents.f_12570_);
    public static final VillagerProfession f_35594_ = VillagerProfession.m_219643_("librarian", PoiTypes.f_218055_, SoundEvents.f_12571_);
    public static final VillagerProfession f_35595_ = VillagerProfession.m_219643_("mason", PoiTypes.f_218056_, SoundEvents.f_12572_);
    public static final VillagerProfession f_35596_ = VillagerProfession.m_219653_("nitwit", PoiType.f_218034_, PoiType.f_218034_, null);
    public static final VillagerProfession f_35597_ = VillagerProfession.m_219643_("shepherd", PoiTypes.f_218057_, SoundEvents.f_12573_);
    public static final VillagerProfession f_35598_ = VillagerProfession.m_219643_("toolsmith", PoiTypes.f_218058_, SoundEvents.f_12574_);
    public static final VillagerProfession f_35599_ = VillagerProfession.m_219643_("weaponsmith", PoiTypes.f_218059_, SoundEvents.f_12575_);

    @Override
    public String toString() {
        return this.f_35600_;
    }

    private static VillagerProfession m_219643_(String p_219644_, ResourceKey<PoiType> p_219645_, @Nullable SoundEvent p_219646_) {
        return VillagerProfession.m_219653_(p_219644_, p_219668_ -> p_219668_.m_203565_(p_219645_), p_219640_ -> p_219640_.m_203565_(p_219645_), p_219646_);
    }

    private static VillagerProfession m_219653_(String p_219654_, Predicate<Holder<PoiType>> p_219655_, Predicate<Holder<PoiType>> p_219656_, @Nullable SoundEvent p_219657_) {
        return VillagerProfession.m_219658_(p_219654_, p_219655_, p_219656_, (ImmutableSet<Item>)ImmutableSet.of(), (ImmutableSet<Block>)ImmutableSet.of(), p_219657_);
    }

    private static VillagerProfession m_219647_(String p_219648_, ResourceKey<PoiType> p_219649_, ImmutableSet<Item> p_219650_, ImmutableSet<Block> p_219651_, @Nullable SoundEvent p_219652_) {
        return VillagerProfession.m_219658_(p_219648_, p_238234_ -> p_238234_.m_203565_(p_219649_), p_238237_ -> p_238237_.m_203565_(p_219649_), p_219650_, p_219651_, p_219652_);
    }

    private static VillagerProfession m_219658_(String p_219659_, Predicate<Holder<PoiType>> p_219660_, Predicate<Holder<PoiType>> p_219661_, ImmutableSet<Item> p_219662_, ImmutableSet<Block> p_219663_, @Nullable SoundEvent p_219664_) {
        return Registry.m_122965_(Registry.f_122869_, new ResourceLocation(p_219659_), new VillagerProfession(p_219659_, p_219660_, p_219661_, p_219662_, p_219663_, p_219664_));
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{VillagerProfession.class, "name;heldJobSite;acquirableJobSite;requestedItems;secondaryPoi;workSound", "f_35600_", "f_219628_", "f_219629_", "f_35602_", "f_35603_", "f_35604_"}, this);
    }

    @Override
    public final boolean equals(Object p_219673_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{VillagerProfession.class, "name;heldJobSite;acquirableJobSite;requestedItems;secondaryPoi;workSound", "f_35600_", "f_219628_", "f_219629_", "f_35602_", "f_35603_", "f_35604_"}, this, p_219673_);
    }
}

