/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class PiglinBarterLoot
implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124468_) {
        p_124468_.accept(BuiltInLootTables.f_78738_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42517_).m_79707_(5)).m_79078_(new EnchantRandomlyFunction.Builder().m_80444_(Enchantments.f_44976_)))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42471_).m_79707_(8)).m_79078_(new EnchantRandomlyFunction.Builder().m_80444_(Enchantments.f_44976_)))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42589_).m_79707_(8)).m_79078_(SetPotionFunction.m_193075_(Potions.f_43610_)))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42736_).m_79707_(8)).m_79078_(SetPotionFunction.m_193075_(Potions.f_43610_)))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42589_).m_79707_(10)).m_79078_(SetPotionFunction.m_193075_(Potions.f_43599_)))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42749_).m_79707_(10)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(10.0f, 36.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42584_).m_79707_(10)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 4.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42401_).m_79707_(20)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0f, 9.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42692_).m_79707_(20)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0f, 12.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_41999_).m_79707_(40)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42754_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0f, 3.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42613_).m_79707_(40)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42454_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 4.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42049_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 8.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42691_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 8.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42737_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(6.0f, 12.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_41832_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0f, 16.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42755_).m_79707_(40)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0f, 16.0f)))))));
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((BiConsumer)object);
    }
}

