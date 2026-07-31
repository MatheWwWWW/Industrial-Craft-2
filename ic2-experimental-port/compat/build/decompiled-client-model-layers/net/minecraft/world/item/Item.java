/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class Item
implements ItemLike {
    private static final Logger f_150883_ = LogUtils.getLogger();
    public static final Map<Block, Item> f_41373_ = Maps.newHashMap();
    protected static final UUID f_41374_ = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID f_41375_ = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final int f_150884_ = 64;
    public static final int f_150885_ = 32;
    public static final int f_150886_ = 13;
    private final Holder.Reference<Item> f_204113_ = Registry.f_122827_.m_203693_(this);
    @Nullable
    protected final CreativeModeTab f_41377_;
    private final Rarity f_41369_;
    private final int f_41370_;
    private final int f_41371_;
    private final boolean f_41372_;
    @Nullable
    private final Item f_41378_;
    @Nullable
    private String f_41379_;
    @Nullable
    private final FoodProperties f_41380_;

    public static int m_41393_(Item p_41394_) {
        return p_41394_ == null ? 0 : Registry.f_122827_.m_7447_(p_41394_);
    }

    public static Item m_41445_(int p_41446_) {
        return Registry.f_122827_.m_7942_(p_41446_);
    }

    @Deprecated
    public static Item m_41439_(Block p_41440_) {
        return f_41373_.getOrDefault(p_41440_, Items.f_41852_);
    }

    public Item(Properties p_41383_) {
        String $$1;
        this.f_41377_ = p_41383_.f_41481_;
        this.f_41369_ = p_41383_.f_41482_;
        this.f_41378_ = p_41383_.f_41480_;
        this.f_41371_ = p_41383_.f_41479_;
        this.f_41370_ = p_41383_.f_41478_;
        this.f_41380_ = p_41383_.f_41483_;
        this.f_41372_ = p_41383_.f_41484_;
        if (SharedConstants.f_136183_ && !($$1 = this.getClass().getSimpleName()).endsWith("Item")) {
            f_150883_.error("Item classes should end with Item and {} doesn't.", (Object)$$1);
        }
    }

    @Deprecated
    public Holder.Reference<Item> m_204114_() {
        return this.f_204113_;
    }

    public void m_5929_(Level p_41428_, LivingEntity p_41429_, ItemStack p_41430_, int p_41431_) {
    }

    public void m_142023_(ItemEntity p_150887_) {
    }

    public void m_142312_(CompoundTag p_150898_) {
    }

    public boolean m_6777_(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
        return true;
    }

    @Override
    public Item m_5456_() {
        return this;
    }

    public InteractionResult m_6225_(UseOnContext p_41427_) {
        return InteractionResult.PASS;
    }

    public float m_8102_(ItemStack p_41425_, BlockState p_41426_) {
        return 1.0f;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        if (this.m_41472_()) {
            ItemStack $$3 = p_41433_.m_21120_(p_41434_);
            if (p_41433_.m_36391_(this.m_41473_().m_38747_())) {
                p_41433_.m_6672_(p_41434_);
                return InteractionResultHolder.m_19096_($$3);
            }
            return InteractionResultHolder.m_19100_($$3);
        }
        return InteractionResultHolder.m_19098_(p_41433_.m_21120_(p_41434_));
    }

    public ItemStack m_5922_(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if (this.m_41472_()) {
            return p_41411_.m_5584_(p_41410_, p_41409_);
        }
        return p_41409_;
    }

    public final int m_41459_() {
        return this.f_41370_;
    }

    public final int m_41462_() {
        return this.f_41371_;
    }

    public boolean m_41465_() {
        return this.f_41371_ > 0;
    }

    public boolean m_142522_(ItemStack p_150899_) {
        return p_150899_.m_41768_();
    }

    public int m_142158_(ItemStack p_150900_) {
        return Math.round(13.0f - (float)p_150900_.m_41773_() * 13.0f / (float)this.f_41371_);
    }

    public int m_142159_(ItemStack p_150901_) {
        float $$1 = Math.max(0.0f, ((float)this.f_41371_ - (float)p_150901_.m_41773_()) / (float)this.f_41371_);
        return Mth.m_14169_($$1 / 3.0f, 1.0f, 1.0f);
    }

    public boolean m_142207_(ItemStack p_150888_, Slot p_150889_, ClickAction p_150890_, Player p_150891_) {
        return false;
    }

    public boolean m_142305_(ItemStack p_150892_, ItemStack p_150893_, Slot p_150894_, ClickAction p_150895_, Player p_150896_, SlotAccess p_150897_) {
        return false;
    }

    public boolean m_7579_(ItemStack p_41395_, LivingEntity p_41396_, LivingEntity p_41397_) {
        return false;
    }

    public boolean m_6813_(ItemStack p_41416_, Level p_41417_, BlockState p_41418_, BlockPos p_41419_, LivingEntity p_41420_) {
        return false;
    }

    public boolean m_8096_(BlockState p_41450_) {
        return false;
    }

    public InteractionResult m_6880_(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        return InteractionResult.PASS;
    }

    public Component m_41466_() {
        return Component.m_237115_(this.m_5524_());
    }

    public String toString() {
        return Registry.f_122827_.m_7981_(this).m_135815_();
    }

    protected String m_41467_() {
        if (this.f_41379_ == null) {
            this.f_41379_ = Util.m_137492_("item", Registry.f_122827_.m_7981_(this));
        }
        return this.f_41379_;
    }

    public String m_5524_() {
        return this.m_41467_();
    }

    public String m_5671_(ItemStack p_41455_) {
        return this.m_5524_();
    }

    public boolean m_41468_() {
        return true;
    }

    @Nullable
    public final Item m_41469_() {
        return this.f_41378_;
    }

    public boolean m_41470_() {
        return this.f_41378_ != null;
    }

    public void m_6883_(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
    }

    public void m_7836_(ItemStack p_41447_, Level p_41448_, Player p_41449_) {
    }

    public boolean m_7807_() {
        return false;
    }

    public UseAnim m_6164_(ItemStack p_41452_) {
        return p_41452_.m_41720_().m_41472_() ? UseAnim.EAT : UseAnim.NONE;
    }

    public int m_8105_(ItemStack p_41454_) {
        if (p_41454_.m_41720_().m_41472_()) {
            return this.m_41473_().m_38748_() ? 16 : 32;
        }
        return 0;
    }

    public void m_5551_(ItemStack p_41412_, Level p_41413_, LivingEntity p_41414_, int p_41415_) {
    }

    public void m_7373_(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
    }

    public Optional<TooltipComponent> m_142422_(ItemStack p_150902_) {
        return Optional.empty();
    }

    public Component m_7626_(ItemStack p_41458_) {
        return Component.m_237115_(this.m_5671_(p_41458_));
    }

    public boolean m_5812_(ItemStack p_41453_) {
        return p_41453_.m_41793_();
    }

    public Rarity m_41460_(ItemStack p_41461_) {
        if (!p_41461_.m_41793_()) {
            return this.f_41369_;
        }
        switch (this.f_41369_) {
            case COMMON: 
            case UNCOMMON: {
                return Rarity.RARE;
            }
            case RARE: {
                return Rarity.EPIC;
            }
        }
        return this.f_41369_;
    }

    public boolean m_8120_(ItemStack p_41456_) {
        return this.m_41459_() == 1 && this.m_41465_();
    }

    protected static BlockHitResult m_41435_(Level p_41436_, Player p_41437_, ClipContext.Fluid p_41438_) {
        float $$3 = p_41437_.m_146909_();
        float $$4 = p_41437_.m_146908_();
        Vec3 $$5 = p_41437_.m_146892_();
        float $$6 = Mth.m_14089_(-$$4 * ((float)Math.PI / 180) - (float)Math.PI);
        float $$7 = Mth.m_14031_(-$$4 * ((float)Math.PI / 180) - (float)Math.PI);
        float $$8 = -Mth.m_14089_(-$$3 * ((float)Math.PI / 180));
        float $$9 = Mth.m_14031_(-$$3 * ((float)Math.PI / 180));
        float $$10 = $$7 * $$8;
        float $$11 = $$9;
        float $$12 = $$6 * $$8;
        double $$13 = 5.0;
        Vec3 $$14 = $$5.m_82520_((double)$$10 * 5.0, (double)$$11 * 5.0, (double)$$12 * 5.0);
        return p_41436_.m_45547_(new ClipContext($$5, $$14, ClipContext.Block.OUTLINE, p_41438_, p_41437_));
    }

    public int m_6473_() {
        return 0;
    }

    public void m_6787_(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
        if (this.m_220152_(p_41391_)) {
            p_41392_.add(new ItemStack(this));
        }
    }

    protected boolean m_220152_(CreativeModeTab p_220153_) {
        CreativeModeTab $$1 = this.m_41471_();
        return $$1 != null && (p_220153_ == CreativeModeTab.f_40754_ || p_220153_ == $$1);
    }

    @Nullable
    public final CreativeModeTab m_41471_() {
        return this.f_41377_;
    }

    public boolean m_6832_(ItemStack p_41402_, ItemStack p_41403_) {
        return false;
    }

    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_41388_) {
        return ImmutableMultimap.of();
    }

    public boolean m_41463_(ItemStack p_41464_) {
        return false;
    }

    public ItemStack m_7968_() {
        return new ItemStack(this);
    }

    public boolean m_41472_() {
        return this.f_41380_ != null;
    }

    @Nullable
    public FoodProperties m_41473_() {
        return this.f_41380_;
    }

    public SoundEvent m_6023_() {
        return SoundEvents.f_11911_;
    }

    public SoundEvent m_6061_() {
        return SoundEvents.f_11912_;
    }

    public boolean m_41475_() {
        return this.f_41372_;
    }

    public boolean m_41386_(DamageSource p_41387_) {
        return !this.f_41372_ || !p_41387_.m_19384_();
    }

    @Nullable
    public SoundEvent m_142602_() {
        return null;
    }

    public boolean m_142095_() {
        return true;
    }

    public static class Properties {
        int f_41478_ = 64;
        int f_41479_;
        @Nullable
        Item f_41480_;
        @Nullable
        CreativeModeTab f_41481_;
        Rarity f_41482_ = Rarity.COMMON;
        @Nullable
        FoodProperties f_41483_;
        boolean f_41484_;

        public Properties m_41489_(FoodProperties p_41490_) {
            this.f_41483_ = p_41490_;
            return this;
        }

        public Properties m_41487_(int p_41488_) {
            if (this.f_41479_ > 0) {
                throw new RuntimeException("Unable to have damage AND stack.");
            }
            this.f_41478_ = p_41488_;
            return this;
        }

        public Properties m_41499_(int p_41500_) {
            return this.f_41479_ == 0 ? this.m_41503_(p_41500_) : this;
        }

        public Properties m_41503_(int p_41504_) {
            this.f_41479_ = p_41504_;
            this.f_41478_ = 1;
            return this;
        }

        public Properties m_41495_(Item p_41496_) {
            this.f_41480_ = p_41496_;
            return this;
        }

        public Properties m_41491_(CreativeModeTab p_41492_) {
            this.f_41481_ = p_41492_;
            return this;
        }

        public Properties m_41497_(Rarity p_41498_) {
            this.f_41482_ = p_41498_;
            return this;
        }

        public Properties m_41486_() {
            this.f_41484_ = true;
            return this;
        }
    }
}

