/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.functions;

import java.util.function.BiFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.FillPlayerHead;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetBannerPatternFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetContainerLootTable;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;

public class LootItemFunctions {
    public static final BiFunction<ItemStack, LootContext, ItemStack> f_80735_ = (p_80760_, p_80761_) -> p_80760_;
    public static final LootItemFunctionType f_80736_ = LootItemFunctions.m_80762_("set_count", new SetItemCountFunction.Serializer());
    public static final LootItemFunctionType f_80737_ = LootItemFunctions.m_80762_("enchant_with_levels", new EnchantWithLevelsFunction.Serializer());
    public static final LootItemFunctionType f_80738_ = LootItemFunctions.m_80762_("enchant_randomly", new EnchantRandomlyFunction.Serializer());
    public static final LootItemFunctionType f_165221_ = LootItemFunctions.m_80762_("set_enchantments", new SetEnchantmentsFunction.Serializer());
    public static final LootItemFunctionType f_80739_ = LootItemFunctions.m_80762_("set_nbt", new SetNbtFunction.Serializer());
    public static final LootItemFunctionType f_80740_ = LootItemFunctions.m_80762_("furnace_smelt", new SmeltItemFunction.Serializer());
    public static final LootItemFunctionType f_80741_ = LootItemFunctions.m_80762_("looting_enchant", new LootingEnchantFunction.Serializer());
    public static final LootItemFunctionType f_80742_ = LootItemFunctions.m_80762_("set_damage", new SetItemDamageFunction.Serializer());
    public static final LootItemFunctionType f_80743_ = LootItemFunctions.m_80762_("set_attributes", new SetAttributesFunction.Serializer());
    public static final LootItemFunctionType f_80744_ = LootItemFunctions.m_80762_("set_name", new SetNameFunction.Serializer());
    public static final LootItemFunctionType f_80745_ = LootItemFunctions.m_80762_("exploration_map", new ExplorationMapFunction.Serializer());
    public static final LootItemFunctionType f_80746_ = LootItemFunctions.m_80762_("set_stew_effect", new SetStewEffectFunction.Serializer());
    public static final LootItemFunctionType f_80747_ = LootItemFunctions.m_80762_("copy_name", new CopyNameFunction.Serializer());
    public static final LootItemFunctionType f_80748_ = LootItemFunctions.m_80762_("set_contents", new SetContainerContents.Serializer());
    public static final LootItemFunctionType f_80749_ = LootItemFunctions.m_80762_("limit_count", new LimitCount.Serializer());
    public static final LootItemFunctionType f_80750_ = LootItemFunctions.m_80762_("apply_bonus", new ApplyBonusCount.Serializer());
    public static final LootItemFunctionType f_80751_ = LootItemFunctions.m_80762_("set_loot_table", new SetContainerLootTable.Serializer());
    public static final LootItemFunctionType f_80752_ = LootItemFunctions.m_80762_("explosion_decay", new ApplyExplosionDecay.Serializer());
    public static final LootItemFunctionType f_80753_ = LootItemFunctions.m_80762_("set_lore", new SetLoreFunction.Serializer());
    public static final LootItemFunctionType f_80754_ = LootItemFunctions.m_80762_("fill_player_head", new FillPlayerHead.Serializer());
    public static final LootItemFunctionType f_80755_ = LootItemFunctions.m_80762_("copy_nbt", new CopyNbtFunction.Serializer());
    public static final LootItemFunctionType f_80756_ = LootItemFunctions.m_80762_("copy_state", new CopyBlockState.Serializer());
    public static final LootItemFunctionType f_165222_ = LootItemFunctions.m_80762_("set_banner_pattern", new SetBannerPatternFunction.Serializer());
    public static final LootItemFunctionType f_193030_ = LootItemFunctions.m_80762_("set_potion", new SetPotionFunction.Serializer());
    public static final LootItemFunctionType f_230994_ = LootItemFunctions.m_80762_("set_instrument", new SetInstrumentFunction.Serializer());

    private static LootItemFunctionType m_80762_(String p_80763_, Serializer<? extends LootItemFunction> p_80764_) {
        return Registry.m_122965_(Registry.f_122876_, new ResourceLocation(p_80763_), new LootItemFunctionType(p_80764_));
    }

    public static Object m_80758_() {
        return GsonAdapterFactory.m_78801_(Registry.f_122876_, "function", "function", LootItemFunction::m_7162_).m_78822_();
    }

    public static BiFunction<ItemStack, LootContext, ItemStack> m_80770_(BiFunction<ItemStack, LootContext, ItemStack>[] p_80771_) {
        switch (p_80771_.length) {
            case 0: {
                return f_80735_;
            }
            case 1: {
                return p_80771_[0];
            }
            case 2: {
                BiFunction<ItemStack, LootContext, ItemStack> $$1 = p_80771_[0];
                BiFunction<ItemStack, LootContext, ItemStack> $$2 = p_80771_[1];
                return (p_80768_, p_80769_) -> (ItemStack)$$2.apply((ItemStack)$$1.apply((ItemStack)p_80768_, (LootContext)p_80769_), (LootContext)p_80769_);
            }
        }
        return (p_80774_, p_80775_) -> {
            for (BiFunction $$3 : p_80771_) {
                p_80774_ = (ItemStack)$$3.apply(p_80774_, p_80775_);
            }
            return p_80774_;
        };
    }
}

