/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FishingLoot
implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    public static final LootItemCondition.Builder f_124385_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48222_));
    public static final LootItemCondition.Builder f_194714_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_186769_));
    public static final LootItemCondition.Builder f_124388_ = LocationCheck.m_81725_(LocationPredicate.Builder.m_52651_().m_52656_(Biomes.f_48197_));

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124395_) {
        p_124395_.accept(BuiltInLootTables.f_78720_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)LootTableReference.m_79776_(BuiltInLootTables.f_78721_).m_79707_(10)).m_79711_(-2)).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)LootTableReference.m_79776_(BuiltInLootTables.f_78722_).m_79707_(5)).m_79711_(2)).m_79080_(LootItemEntityPropertyCondition.m_81864_(LootContext.EntityTarget.THIS, EntityPredicate.Builder.m_36633_().m_218800_(FishingHookPredicate.m_39766_(true))))).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)LootTableReference.m_79776_(BuiltInLootTables.f_78723_).m_79707_(85)).m_79711_(-1))));
        p_124395_.accept(BuiltInLootTables.f_78723_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42526_).m_79707_(60)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42527_).m_79707_(25)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42528_).m_79707_(2)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42529_).m_79707_(13))));
        p_124395_.accept(BuiltInLootTables.f_78721_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Blocks.f_50196_).m_79707_(17)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42463_).m_79707_(10)).m_79078_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0f, 0.9f))))).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42454_).m_79707_(10)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42500_).m_79707_(10)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42589_).m_79707_(10)).m_79078_(SetPotionFunction.m_193075_(Potions.f_43599_)))).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42401_).m_79707_(5)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42523_).m_79707_(2)).m_79078_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0f, 0.9f))))).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42399_).m_79707_(10)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42398_).m_79707_(5)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42532_).m_79707_(1)).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(10.0f))))).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Blocks.f_50266_).m_79707_(10)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42583_).m_79707_(10)).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Blocks.f_50571_).m_79080_(f_124385_.m_7818_(f_194714_).m_7818_(f_124388_))).m_79707_(10))));
        p_124395_.accept(BuiltInLootTables.f_78722_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_42656_)).m_79076_(LootItem.m_79579_(Items.f_42450_)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42411_).m_79078_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0f, 0.25f)))).m_79078_(EnchantWithLevelsFunction.m_165196_(ConstantValue.m_165692_(30.0f)).m_80499_()))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42523_).m_79078_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.0f, 0.25f)))).m_79078_(EnchantWithLevelsFunction.m_165196_(ConstantValue.m_165692_(30.0f)).m_80499_()))).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(Items.f_42517_).m_79078_(EnchantWithLevelsFunction.m_165196_(ConstantValue.m_165692_(30.0f)).m_80499_()))).m_79076_(LootItem.m_79579_(Items.f_42715_))));
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((BiConsumer)object);
    }
}

