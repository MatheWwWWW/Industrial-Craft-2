/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 */
package net.minecraft.data.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class BlockLoot
implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    private static final LootItemCondition.Builder f_124062_ = MatchTool.m_81997_(ItemPredicate.Builder.m_45068_().m_45071_(new EnchantmentPredicate(Enchantments.f_44985_, MinMaxBounds.Ints.m_55386_(1))));
    private static final LootItemCondition.Builder f_124063_ = f_124062_.m_81807_();
    private static final LootItemCondition.Builder f_124064_ = MatchTool.m_81997_(ItemPredicate.Builder.m_45068_().m_151445_(Items.f_42574_));
    private static final LootItemCondition.Builder f_124065_ = f_124064_.m_7818_(f_124062_);
    private static final LootItemCondition.Builder f_124066_ = f_124065_.m_81807_();
    private static final Set<Item> f_124067_ = (Set)Stream.of(Blocks.f_50260_, Blocks.f_50273_, Blocks.f_50569_, Blocks.f_50310_, Blocks.f_50312_, Blocks.f_50316_, Blocks.f_50314_, Blocks.f_50318_, Blocks.f_50320_, Blocks.f_50456_, Blocks.f_50525_, Blocks.f_50521_, Blocks.f_50522_, Blocks.f_50466_, Blocks.f_50464_, Blocks.f_50523_, Blocks.f_50460_, Blocks.f_50465_, Blocks.f_50462_, Blocks.f_50459_, Blocks.f_50458_, Blocks.f_50463_, Blocks.f_50520_, Blocks.f_50524_, Blocks.f_50457_, Blocks.f_50461_).map(ItemLike::m_5456_).collect(ImmutableSet.toImmutableSet());
    private static final float[] f_124068_ = new float[]{0.05f, 0.0625f, 0.083333336f, 0.1f};
    private static final float[] f_124069_ = new float[]{0.025f, 0.027777778f, 0.03125f, 0.041666668f, 0.1f};
    private static final float[] f_236220_ = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f};
    private final Map<ResourceLocation, LootTable.Builder> f_124070_ = Maps.newHashMap();

    private static <T extends FunctionUserBuilder<T>> T m_236221_(ItemLike p_236222_, FunctionUserBuilder<T> p_236223_) {
        if (!f_124067_.contains(p_236222_.m_5456_())) {
            return p_236223_.m_79078_(ApplyExplosionDecay.m_80037_());
        }
        return p_236223_.m_79073_();
    }

    private static <T extends ConditionUserBuilder<T>> T m_236224_(ItemLike p_236225_, ConditionUserBuilder<T> p_236226_) {
        if (!f_124067_.contains(p_236225_.m_5456_())) {
            return p_236226_.m_79080_(ExplosionCondition.m_81661_());
        }
        return p_236226_.m_79073_();
    }

    private static LootTable.Builder m_124126_(ItemLike p_124127_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(p_124127_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(LootItem.m_79579_(p_124127_))));
    }

    private static LootTable.Builder m_124171_(Block p_124172_, LootItemCondition.Builder p_124173_, LootPoolEntryContainer.Builder<?> p_124174_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124172_).m_79080_(p_124173_)).m_7170_(p_124174_)));
    }

    private static LootTable.Builder m_124168_(Block p_124169_, LootPoolEntryContainer.Builder<?> p_124170_) {
        return BlockLoot.m_124171_(p_124169_, f_124062_, p_124170_);
    }

    private static LootTable.Builder m_124267_(Block p_124268_, LootPoolEntryContainer.Builder<?> p_124269_) {
        return BlockLoot.m_124171_(p_124268_, f_124064_, p_124269_);
    }

    private static LootTable.Builder m_124283_(Block p_124284_, LootPoolEntryContainer.Builder<?> p_124285_) {
        return BlockLoot.m_124171_(p_124284_, f_124065_, p_124285_);
    }

    private static LootTable.Builder m_124257_(Block p_124258_, ItemLike p_124259_) {
        return BlockLoot.m_124168_(p_124258_, (LootPoolEntryContainer.Builder)BlockLoot.m_236224_(p_124258_, LootItem.m_79579_(p_124259_)));
    }

    private static LootTable.Builder m_176039_(ItemLike p_176040_, NumberProvider p_176041_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176040_, LootItem.m_79579_(p_176040_).m_79078_(SetItemCountFunction.m_165412_(p_176041_)))));
    }

    private static LootTable.Builder m_176042_(Block p_176043_, ItemLike p_176044_, NumberProvider p_176045_) {
        return BlockLoot.m_124168_(p_176043_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176043_, LootItem.m_79579_(p_176044_).m_79078_(SetItemCountFunction.m_165412_(p_176045_))));
    }

    private static LootTable.Builder m_124250_(ItemLike p_124251_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79080_(f_124062_).m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(LootItem.m_79579_(p_124251_)));
    }

    private static LootTable.Builder m_124270_(ItemLike p_124271_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(Blocks.f_50276_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(LootItem.m_79579_(Blocks.f_50276_)))).m_79161_(BlockLoot.m_236224_(p_124271_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(LootItem.m_79579_(p_124271_))));
    }

    private static LootTable.Builder m_124290_(Block p_124291_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124291_, LootItem.m_79579_(p_124291_).m_79078_((LootItemFunction.Builder)((Object)SetItemCountFunction.m_165412_(ConstantValue.m_165692_(2.0f)).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124291_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67697_(SlabBlock.f_56353_, SlabType.DOUBLE))))))));
    }

    private static <T extends Comparable<T> & StringRepresentable> LootTable.Builder m_124161_(Block p_124162_, Property<T> p_124163_, T p_124164_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(p_124162_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(p_124162_).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124162_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67697_(p_124163_, p_124164_))))));
    }

    private static LootTable.Builder m_124292_(Block p_124293_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(p_124293_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(p_124293_).m_79078_(CopyNameFunction.m_80187_(CopyNameFunction.NameSource.BLOCK_ENTITY))))));
    }

    private static LootTable.Builder m_124294_(Block p_124295_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(p_124295_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124295_).m_79078_(CopyNameFunction.m_80187_(CopyNameFunction.NameSource.BLOCK_ENTITY))).m_79078_(CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_).m_80279_("Lock", "BlockEntityTag.Lock").m_80279_("LootTable", "BlockEntityTag.LootTable").m_80279_("LootTableSeed", "BlockEntityTag.LootTableSeed"))).m_79078_(SetContainerContents.m_193036_(BlockEntityType.f_58939_).m_80930_(DynamicLoot.m_79483_(ShulkerBoxBlock.f_56184_)))))));
    }

    private static LootTable.Builder m_176046_(Block p_176047_) {
        return BlockLoot.m_124168_(p_176047_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176047_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_151051_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 5.0f)))).m_79078_(ApplyBonusCount.m_79915_(Enchantments.f_44987_))));
    }

    private static LootTable.Builder m_176048_(Block p_176049_) {
        return BlockLoot.m_124168_(p_176049_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176049_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42534_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0f, 9.0f)))).m_79078_(ApplyBonusCount.m_79915_(Enchantments.f_44987_))));
    }

    private static LootTable.Builder m_176050_(Block p_176051_) {
        return BlockLoot.m_124168_(p_176051_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176051_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42451_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0f, 5.0f)))).m_79078_(ApplyBonusCount.m_79939_(Enchantments.f_44987_))));
    }

    private static LootTable.Builder m_124296_(Block p_124297_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(p_124297_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124297_).m_79078_(CopyNameFunction.m_80187_(CopyNameFunction.NameSource.BLOCK_ENTITY))).m_79078_(CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_).m_80279_("Patterns", "BlockEntityTag.Patterns"))))));
    }

    private static LootTable.Builder m_124298_(Block p_124299_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79080_(f_124062_).m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124299_).m_79078_(CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_).m_80279_("Bees", "BlockEntityTag.Bees"))).m_79078_(CopyBlockState.m_80062_(p_124299_).m_80084_(BeehiveBlock.f_49564_)))));
    }

    private static LootTable.Builder m_124300_(Block p_124301_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(((LootPoolEntryContainer.Builder)((Object)((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124301_).m_79080_(f_124062_)).m_79078_(CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_).m_80279_("Bees", "BlockEntityTag.Bees"))).m_79078_(CopyBlockState.m_80062_(p_124301_).m_80084_(BeehiveBlock.f_49564_)))).m_7170_(LootItem.m_79579_(p_124301_))));
    }

    private static LootTable.Builder m_176052_(Block p_176053_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_151079_)).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_176053_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67703_(CaveVines.f_152949_, true))));
    }

    private static LootTable.Builder m_124139_(Block p_124140_, Item p_124141_) {
        return BlockLoot.m_124168_(p_124140_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124140_, LootItem.m_79579_(p_124141_).m_79078_(ApplyBonusCount.m_79915_(Enchantments.f_44987_))));
    }

    private static LootTable.Builder m_124277_(Block p_124278_, ItemLike p_124279_) {
        return BlockLoot.m_124168_(p_124278_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124278_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124279_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(-6.0f, 2.0f)))).m_79078_(LimitCount.m_165215_(IntRange.m_165026_(0)))));
    }

    private static LootTable.Builder m_124302_(Block p_124303_) {
        return BlockLoot.m_124267_(p_124303_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124303_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42404_).m_79080_(LootItemRandomChanceCondition.m_81927_(0.125f))).m_79078_(ApplyBonusCount.m_79921_(Enchantments.f_44987_, 2))));
    }

    private static LootTable.Builder m_124254_(Block p_124255_, Item p_124256_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236221_(p_124255_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_(p_124256_).m_230984_(StemBlock.f_57013_.m_6908_(), p_236247_ -> SetItemCountFunction.m_165412_(BinomialDistributionGenerator.m_165659_(3, (float)(p_236247_ + 1) / 15.0f)).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124255_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(StemBlock.f_57013_, (int)p_236247_)))))));
    }

    private static LootTable.Builder m_124274_(Block p_124275_, Item p_124276_) {
        return LootTable.m_79147_().m_79161_(BlockLoot.m_236221_(p_124275_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(p_124276_).m_79078_(SetItemCountFunction.m_165412_(BinomialDistributionGenerator.m_165659_(3, 0.53333336f)))))));
    }

    private static LootTable.Builder m_124286_(ItemLike p_124287_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79080_(f_124064_).m_79076_(LootItem.m_79579_(p_124287_)));
    }

    private static LootTable.Builder m_236227_(Block p_236228_, LootItemCondition.Builder p_236229_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_236228_, ((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_236228_).m_79080_(p_236229_)).m_230987_(Direction.values(), p_236232_ -> SetItemCountFunction.m_165414_(ConstantValue.m_165692_(1.0f), true).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_236228_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67703_(MultifaceBlock.m_153933_(p_236232_), true))))).m_79078_(SetItemCountFunction.m_165414_(ConstantValue.m_165692_(-1.0f), true)))));
    }

    private static LootTable.Builder m_124157_(Block p_124158_, Block p_124159_, float ... p_124160_) {
        return BlockLoot.m_124283_(p_124158_, ((LootPoolSingletonContainer.Builder)BlockLoot.m_236224_(p_124158_, LootItem.m_79579_(p_124159_))).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, p_124160_))).m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79080_(f_124066_).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)BlockLoot.m_236221_(p_124158_, LootItem.m_79579_(Items.f_42398_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0f, 2.0f))))).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, f_236220_))));
    }

    private static LootTable.Builder m_124263_(Block p_124264_, Block p_124265_, float ... p_124266_) {
        return BlockLoot.m_124157_(p_124264_, p_124265_, p_124266_).m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79080_(f_124066_).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)BlockLoot.m_236224_(p_124264_, LootItem.m_79579_(Items.f_42410_))).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, 0.005f, 0.0055555557f, 0.00625f, 0.008333334f, 0.025f))));
    }

    private static LootTable.Builder m_236248_(Block p_236249_) {
        return BlockLoot.m_124283_(p_236249_, ((LootPoolSingletonContainer.Builder)BlockLoot.m_236221_(Blocks.f_220838_, LootItem.m_79579_(Items.f_42398_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0f, 2.0f))))).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, f_236220_)));
    }

    private static LootTable.Builder m_124142_(Block p_124143_, Item p_124144_, Item p_124145_, LootItemCondition.Builder p_124146_) {
        return BlockLoot.m_236221_(p_124143_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(((LootPoolSingletonContainer.Builder)LootItem.m_79579_(p_124144_).m_79080_(p_124146_)).m_7170_(LootItem.m_79579_(p_124145_)))).m_79161_(LootPool.m_79043_().m_79080_(p_124146_).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(p_124145_).m_79078_(ApplyBonusCount.m_79917_(Enchantments.f_44987_, 0.5714286f, 3))))));
    }

    private static LootTable.Builder m_124304_(Block p_124305_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79080_(f_124064_).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(p_124305_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(2.0f))))));
    }

    private static LootTable.Builder m_124260_(Block p_124261_, Block p_124262_) {
        AlternativesEntry.Builder $$2 = ((LootPoolSingletonContainer.Builder)((LootPoolEntryContainer.Builder)((Object)LootItem.m_79579_(p_124262_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(2.0f))))).m_79080_(f_124064_)).m_7170_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)BlockLoot.m_236224_(p_124261_, LootItem.m_79579_(Items.f_42404_))).m_79080_(LootItemRandomChanceCondition.m_81927_(0.125f)));
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_($$2).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124261_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67697_(DoublePlantBlock.f_52858_, DoubleBlockHalf.LOWER))).m_79080_(LocationCheck.m_81727_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(p_124261_).m_17929_(StatePropertiesPredicate.Builder.m_67693_().m_67697_(DoublePlantBlock.f_52858_, DoubleBlockHalf.UPPER).m_67706_()).m_17931_()), new BlockPos(0, 1, 0)))).m_79161_(LootPool.m_79043_().m_79076_($$2).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124261_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67697_(DoublePlantBlock.f_52858_, DoubleBlockHalf.UPPER))).m_79080_(LocationCheck.m_81727_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(p_124261_).m_17929_(StatePropertiesPredicate.Builder.m_67693_().m_67697_(DoublePlantBlock.f_52858_, DoubleBlockHalf.LOWER).m_67706_()).m_17931_()), new BlockPos(0, -1, 0))));
    }

    private static LootTable.Builder m_176056_(Block p_176057_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176057_, LootItem.m_79579_(p_176057_).m_230984_(List.of(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)), p_236244_ -> SetItemCountFunction.m_165412_(ConstantValue.m_165692_(p_236244_.intValue())).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_176057_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(CandleBlock.f_152790_, (int)p_236244_)))))));
    }

    private static LootTable.Builder m_176058_(Block p_176059_) {
        return LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_(LootItem.m_79579_(p_176059_)));
    }

    public static LootTable.Builder m_124125_() {
        return LootTable.m_79147_();
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124179_) {
        this.m_124288_(Blocks.f_50122_);
        this.m_124288_(Blocks.f_50175_);
        this.m_124288_(Blocks.f_50228_);
        this.m_124288_(Blocks.f_50281_);
        this.m_124288_(Blocks.f_50334_);
        this.m_124288_(Blocks.f_50387_);
        this.m_124288_(Blocks.f_50493_);
        this.m_124288_(Blocks.f_50546_);
        this.m_124288_(Blocks.f_50652_);
        this.m_124288_(Blocks.f_50705_);
        this.m_124288_(Blocks.f_50741_);
        this.m_124288_(Blocks.f_50742_);
        this.m_124288_(Blocks.f_50743_);
        this.m_124288_(Blocks.f_50744_);
        this.m_124288_(Blocks.f_50745_);
        this.m_124288_(Blocks.f_220865_);
        this.m_124288_(Blocks.f_50746_);
        this.m_124288_(Blocks.f_50747_);
        this.m_124288_(Blocks.f_50748_);
        this.m_124288_(Blocks.f_50749_);
        this.m_124288_(Blocks.f_50750_);
        this.m_124288_(Blocks.f_50751_);
        this.m_124288_(Blocks.f_49992_);
        this.m_124288_(Blocks.f_49993_);
        this.m_124288_(Blocks.f_49999_);
        this.m_124288_(Blocks.f_50000_);
        this.m_124288_(Blocks.f_50001_);
        this.m_124288_(Blocks.f_50002_);
        this.m_124288_(Blocks.f_50003_);
        this.m_124288_(Blocks.f_50004_);
        this.m_124288_(Blocks.f_50005_);
        this.m_124288_(Blocks.f_50006_);
        this.m_124288_(Blocks.f_50007_);
        this.m_124288_(Blocks.f_50008_);
        this.m_124288_(Blocks.f_50009_);
        this.m_124288_(Blocks.f_50010_);
        this.m_124288_(Blocks.f_50687_);
        this.m_124288_(Blocks.f_50696_);
        this.m_124288_(Blocks.f_220835_);
        this.m_124288_(Blocks.f_50011_);
        this.m_124288_(Blocks.f_50012_);
        this.m_124288_(Blocks.f_50013_);
        this.m_124288_(Blocks.f_50014_);
        this.m_124288_(Blocks.f_50015_);
        this.m_124288_(Blocks.f_50043_);
        this.m_124288_(Blocks.f_220836_);
        this.m_124288_(Blocks.f_50044_);
        this.m_124288_(Blocks.f_50045_);
        this.m_124288_(Blocks.f_50046_);
        this.m_124288_(Blocks.f_50047_);
        this.m_124288_(Blocks.f_50048_);
        this.m_124288_(Blocks.f_50049_);
        this.m_124288_(Blocks.f_50698_);
        this.m_124288_(Blocks.f_50689_);
        this.m_124288_(Blocks.f_220837_);
        this.m_124288_(Blocks.f_50056_);
        this.m_124288_(Blocks.f_50057_);
        this.m_124288_(Blocks.f_50060_);
        this.m_124288_(Blocks.f_50062_);
        this.m_124288_(Blocks.f_50063_);
        this.m_124288_(Blocks.f_50064_);
        this.m_124288_(Blocks.f_50065_);
        this.m_124288_(Blocks.f_50030_);
        this.m_124288_(Blocks.f_50031_);
        this.m_124288_(Blocks.f_50032_);
        this.m_124288_(Blocks.f_50039_);
        this.m_124288_(Blocks.f_50041_);
        this.m_124288_(Blocks.f_50042_);
        this.m_124288_(Blocks.f_50096_);
        this.m_124288_(Blocks.f_50097_);
        this.m_124288_(Blocks.f_50098_);
        this.m_124288_(Blocks.f_50099_);
        this.m_124288_(Blocks.f_50100_);
        this.m_124288_(Blocks.f_50101_);
        this.m_124288_(Blocks.f_50102_);
        this.m_124288_(Blocks.f_50103_);
        this.m_124288_(Blocks.f_50104_);
        this.m_124288_(Blocks.f_50105_);
        this.m_124288_(Blocks.f_50106_);
        this.m_124288_(Blocks.f_50107_);
        this.m_124288_(Blocks.f_50108_);
        this.m_124288_(Blocks.f_50109_);
        this.m_124288_(Blocks.f_50111_);
        this.m_124288_(Blocks.f_50112_);
        this.m_124288_(Blocks.f_50113_);
        this.m_124288_(Blocks.f_50114_);
        this.m_124288_(Blocks.f_50115_);
        this.m_124288_(Blocks.f_50116_);
        this.m_124288_(Blocks.f_50117_);
        this.m_124288_(Blocks.f_50118_);
        this.m_124288_(Blocks.f_50119_);
        this.m_124288_(Blocks.f_50120_);
        this.m_124288_(Blocks.f_50121_);
        this.m_124288_(Blocks.f_50070_);
        this.m_124288_(Blocks.f_50071_);
        this.m_124288_(Blocks.f_50072_);
        this.m_124288_(Blocks.f_50073_);
        this.m_124288_(Blocks.f_50074_);
        this.m_124288_(Blocks.f_50075_);
        this.m_124288_(Blocks.f_50076_);
        this.m_124288_(Blocks.f_50079_);
        this.m_124288_(Blocks.f_50080_);
        this.m_124288_(Blocks.f_50723_);
        this.m_124288_(Blocks.f_50081_);
        this.m_124288_(Blocks.f_50086_);
        this.m_124288_(Blocks.f_220848_);
        this.m_124288_(Blocks.f_50088_);
        this.m_124288_(Blocks.f_50090_);
        this.m_124288_(Blocks.f_50091_);
        this.m_124288_(Blocks.f_50095_);
        this.m_124288_(Blocks.f_50149_);
        this.m_124288_(Blocks.f_50150_);
        this.m_124288_(Blocks.f_50151_);
        this.m_124288_(Blocks.f_50152_);
        this.m_124288_(Blocks.f_50153_);
        this.m_124288_(Blocks.f_220841_);
        this.m_124288_(Blocks.f_50155_);
        this.m_124288_(Blocks.f_50156_);
        this.m_124288_(Blocks.f_50157_);
        this.m_124288_(Blocks.f_50164_);
        this.m_124288_(Blocks.f_50165_);
        this.m_124288_(Blocks.f_50167_);
        this.m_124288_(Blocks.f_50168_);
        this.m_124288_(Blocks.f_50169_);
        this.m_124288_(Blocks.f_50170_);
        this.m_124288_(Blocks.f_50171_);
        this.m_124288_(Blocks.f_50172_);
        this.m_124288_(Blocks.f_220840_);
        this.m_124288_(Blocks.f_50174_);
        this.m_124288_(Blocks.f_50124_);
        this.m_124288_(Blocks.f_50128_);
        this.m_124288_(Blocks.f_50130_);
        this.m_124288_(Blocks.f_50131_);
        this.m_124288_(Blocks.f_50132_);
        this.m_124288_(Blocks.f_220852_);
        this.m_124288_(Blocks.f_50133_);
        this.m_124288_(Blocks.f_50134_);
        this.m_124288_(Blocks.f_50135_);
        this.m_124288_(Blocks.f_50136_);
        this.m_124288_(Blocks.f_50137_);
        this.m_124288_(Blocks.f_50138_);
        this.m_124288_(Blocks.f_152597_);
        this.m_124288_(Blocks.f_50139_);
        this.m_124288_(Blocks.f_50143_);
        this.m_124288_(Blocks.f_50144_);
        this.m_124288_(Blocks.f_50146_);
        this.m_124288_(Blocks.f_50216_);
        this.m_124288_(Blocks.f_50217_);
        this.m_124288_(Blocks.f_50218_);
        this.m_124288_(Blocks.f_50219_);
        this.m_124288_(Blocks.f_50220_);
        this.m_124288_(Blocks.f_50221_);
        this.m_124288_(Blocks.f_220842_);
        this.m_124288_(Blocks.f_50222_);
        this.m_124288_(Blocks.f_50223_);
        this.m_124288_(Blocks.f_50224_);
        this.m_124288_(Blocks.f_50225_);
        this.m_124288_(Blocks.f_50183_);
        this.m_124288_(Blocks.f_50192_);
        this.m_124288_(Blocks.f_220850_);
        this.m_124288_(Blocks.f_50193_);
        this.m_124288_(Blocks.f_50194_);
        this.m_124288_(Blocks.f_50196_);
        this.m_124288_(Blocks.f_50197_);
        this.m_124288_(Blocks.f_50198_);
        this.m_124288_(Blocks.f_50199_);
        this.m_124288_(Blocks.f_50256_);
        this.m_124288_(Blocks.f_50259_);
        this.m_124288_(Blocks.f_50261_);
        this.m_124288_(Blocks.f_50263_);
        this.m_124288_(Blocks.f_50266_);
        this.m_124288_(Blocks.f_50268_);
        this.m_124288_(Blocks.f_50269_);
        this.m_124288_(Blocks.f_50270_);
        this.m_124288_(Blocks.f_50271_);
        this.m_124288_(Blocks.f_50274_);
        this.m_124288_(Blocks.f_50275_);
        this.m_124288_(Blocks.f_50276_);
        this.m_124288_(Blocks.f_50251_);
        this.m_124288_(Blocks.f_50252_);
        this.m_124288_(Blocks.f_50253_);
        this.m_124288_(Blocks.f_50254_);
        this.m_124288_(Blocks.f_50308_);
        this.m_124288_(Blocks.f_50309_);
        this.m_124288_(Blocks.f_220846_);
        this.m_124288_(Blocks.f_50310_);
        this.m_124288_(Blocks.f_50312_);
        this.m_124288_(Blocks.f_50314_);
        this.m_124288_(Blocks.f_50318_);
        this.m_124288_(Blocks.f_50320_);
        this.m_124288_(Blocks.f_50322_);
        this.m_124288_(Blocks.f_50323_);
        this.m_124288_(Blocks.f_50324_);
        this.m_124288_(Blocks.f_50326_);
        this.m_124288_(Blocks.f_50327_);
        this.m_124288_(Blocks.f_50328_);
        this.m_124288_(Blocks.f_50329_);
        this.m_124288_(Blocks.f_50330_);
        this.m_124288_(Blocks.f_50333_);
        this.m_124288_(Blocks.f_50282_);
        this.m_124288_(Blocks.f_50283_);
        this.m_124288_(Blocks.f_50284_);
        this.m_124288_(Blocks.f_50285_);
        this.m_124288_(Blocks.f_50287_);
        this.m_124288_(Blocks.f_50288_);
        this.m_124288_(Blocks.f_50289_);
        this.m_124288_(Blocks.f_50290_);
        this.m_124288_(Blocks.f_50291_);
        this.m_124288_(Blocks.f_50292_);
        this.m_124288_(Blocks.f_50293_);
        this.m_124288_(Blocks.f_50294_);
        this.m_124288_(Blocks.f_50295_);
        this.m_124288_(Blocks.f_50296_);
        this.m_124288_(Blocks.f_50297_);
        this.m_124288_(Blocks.f_50298_);
        this.m_124288_(Blocks.f_50299_);
        this.m_124288_(Blocks.f_50300_);
        this.m_124288_(Blocks.f_50301_);
        this.m_124288_(Blocks.f_50302_);
        this.m_124288_(Blocks.f_50372_);
        this.m_124288_(Blocks.f_50373_);
        this.m_124288_(Blocks.f_50374_);
        this.m_124288_(Blocks.f_50376_);
        this.m_124288_(Blocks.f_50377_);
        this.m_124288_(Blocks.f_50378_);
        this.m_124288_(Blocks.f_50379_);
        this.m_124288_(Blocks.f_50380_);
        this.m_124288_(Blocks.f_50381_);
        this.m_124288_(Blocks.f_50382_);
        this.m_124288_(Blocks.f_50335_);
        this.m_124288_(Blocks.f_50336_);
        this.m_124288_(Blocks.f_50337_);
        this.m_124288_(Blocks.f_50338_);
        this.m_124288_(Blocks.f_50339_);
        this.m_124288_(Blocks.f_50340_);
        this.m_124288_(Blocks.f_50341_);
        this.m_124288_(Blocks.f_50342_);
        this.m_124288_(Blocks.f_50343_);
        this.m_124288_(Blocks.f_50344_);
        this.m_124288_(Blocks.f_50345_);
        this.m_124288_(Blocks.f_50346_);
        this.m_124288_(Blocks.f_50347_);
        this.m_124288_(Blocks.f_50348_);
        this.m_124288_(Blocks.f_50349_);
        this.m_124288_(Blocks.f_50350_);
        this.m_124288_(Blocks.f_50351_);
        this.m_124288_(Blocks.f_50352_);
        this.m_124288_(Blocks.f_50353_);
        this.m_124288_(Blocks.f_50394_);
        this.m_124288_(Blocks.f_50395_);
        this.m_124288_(Blocks.f_50396_);
        this.m_124288_(Blocks.f_50397_);
        this.m_124288_(Blocks.f_50470_);
        this.m_124288_(Blocks.f_50471_);
        this.m_124288_(Blocks.f_50472_);
        this.m_124288_(Blocks.f_50473_);
        this.m_124288_(Blocks.f_50474_);
        this.m_124288_(Blocks.f_50475_);
        this.m_124288_(Blocks.f_50476_);
        this.m_124288_(Blocks.f_50477_);
        this.m_124288_(Blocks.f_50478_);
        this.m_124288_(Blocks.f_50479_);
        this.m_124288_(Blocks.f_50480_);
        this.m_124288_(Blocks.f_50481_);
        this.m_124288_(Blocks.f_50482_);
        this.m_124288_(Blocks.f_50483_);
        this.m_124288_(Blocks.f_50489_);
        this.m_124288_(Blocks.f_50492_);
        this.m_124288_(Blocks.f_50441_);
        this.m_124288_(Blocks.f_50442_);
        this.m_124288_(Blocks.f_50443_);
        this.m_124288_(Blocks.f_50450_);
        this.m_124288_(Blocks.f_50451_);
        this.m_124288_(Blocks.f_50452_);
        this.m_124288_(Blocks.f_50453_);
        this.m_124288_(Blocks.f_50455_);
        this.m_124288_(Blocks.f_50716_);
        this.m_124288_(Blocks.f_50526_);
        this.m_124288_(Blocks.f_50527_);
        this.m_124288_(Blocks.f_50528_);
        this.m_124288_(Blocks.f_50529_);
        this.m_124288_(Blocks.f_50530_);
        this.m_124288_(Blocks.f_50531_);
        this.m_124288_(Blocks.f_50532_);
        this.m_124288_(Blocks.f_50533_);
        this.m_124288_(Blocks.f_50534_);
        this.m_124288_(Blocks.f_50535_);
        this.m_124288_(Blocks.f_50536_);
        this.m_124288_(Blocks.f_50537_);
        this.m_124288_(Blocks.f_50538_);
        this.m_124288_(Blocks.f_50539_);
        this.m_124288_(Blocks.f_50540_);
        this.m_124288_(Blocks.f_50541_);
        this.m_124288_(Blocks.f_50542_);
        this.m_124288_(Blocks.f_50543_);
        this.m_124288_(Blocks.f_50544_);
        this.m_124288_(Blocks.f_50545_);
        this.m_124288_(Blocks.f_50494_);
        this.m_124288_(Blocks.f_50495_);
        this.m_124288_(Blocks.f_50496_);
        this.m_124288_(Blocks.f_50497_);
        this.m_124288_(Blocks.f_50498_);
        this.m_124288_(Blocks.f_50499_);
        this.m_124288_(Blocks.f_50500_);
        this.m_124288_(Blocks.f_50501_);
        this.m_124288_(Blocks.f_50502_);
        this.m_124288_(Blocks.f_50503_);
        this.m_124288_(Blocks.f_50504_);
        this.m_124288_(Blocks.f_50505_);
        this.m_124288_(Blocks.f_50506_);
        this.m_124288_(Blocks.f_50507_);
        this.m_124288_(Blocks.f_50508_);
        this.m_124288_(Blocks.f_50509_);
        this.m_124288_(Blocks.f_50510_);
        this.m_124288_(Blocks.f_50511_);
        this.m_124288_(Blocks.f_50512_);
        this.m_124288_(Blocks.f_50513_);
        this.m_124288_(Blocks.f_50514_);
        this.m_124288_(Blocks.f_50515_);
        this.m_124288_(Blocks.f_50516_);
        this.m_124288_(Blocks.f_50517_);
        this.m_124288_(Blocks.f_50518_);
        this.m_124288_(Blocks.f_50519_);
        this.m_124288_(Blocks.f_50573_);
        this.m_124288_(Blocks.f_50574_);
        this.m_124288_(Blocks.f_50575_);
        this.m_124288_(Blocks.f_50577_);
        this.m_124288_(Blocks.f_50579_);
        this.m_124288_(Blocks.f_50580_);
        this.m_124288_(Blocks.f_50581_);
        this.m_124288_(Blocks.f_50582_);
        this.m_124288_(Blocks.f_50583_);
        this.m_124288_(Blocks.f_50569_);
        this.m_124288_(Blocks.f_50260_);
        this.m_124288_(Blocks.f_50571_);
        this.m_124288_(Blocks.f_50629_);
        this.m_124288_(Blocks.f_50630_);
        this.m_124288_(Blocks.f_50631_);
        this.m_124288_(Blocks.f_50632_);
        this.m_124288_(Blocks.f_50633_);
        this.m_124288_(Blocks.f_50634_);
        this.m_124288_(Blocks.f_50635_);
        this.m_124288_(Blocks.f_50636_);
        this.m_124288_(Blocks.f_50637_);
        this.m_124288_(Blocks.f_50638_);
        this.m_124288_(Blocks.f_50639_);
        this.m_124288_(Blocks.f_50640_);
        this.m_124288_(Blocks.f_50641_);
        this.m_124288_(Blocks.f_50642_);
        this.m_124288_(Blocks.f_50604_);
        this.m_124288_(Blocks.f_50605_);
        this.m_124288_(Blocks.f_50606_);
        this.m_124288_(Blocks.f_50607_);
        this.m_124288_(Blocks.f_50608_);
        this.m_124288_(Blocks.f_50609_);
        this.m_124288_(Blocks.f_50610_);
        this.m_124288_(Blocks.f_50611_);
        this.m_124288_(Blocks.f_50612_);
        this.m_124288_(Blocks.f_50613_);
        this.m_124288_(Blocks.f_50614_);
        this.m_124288_(Blocks.f_50615_);
        this.m_124288_(Blocks.f_220854_);
        this.m_124288_(Blocks.f_50617_);
        this.m_124288_(Blocks.f_50616_);
        this.m_124288_(Blocks.f_50719_);
        this.m_124288_(Blocks.f_50720_);
        this.m_124288_(Blocks.f_50724_);
        this.m_124288_(Blocks.f_50729_);
        this.m_124288_(Blocks.f_50686_);
        this.m_124288_(Blocks.f_50688_);
        this.m_124288_(Blocks.f_50691_);
        this.m_124288_(Blocks.f_50692_);
        this.m_124288_(Blocks.f_50695_);
        this.m_124288_(Blocks.f_50697_);
        this.m_124288_(Blocks.f_50700_);
        this.m_124288_(Blocks.f_50701_);
        this.m_124288_(Blocks.f_50655_);
        this.m_124288_(Blocks.f_50656_);
        this.m_124288_(Blocks.f_50660_);
        this.m_124288_(Blocks.f_50662_);
        this.m_124288_(Blocks.f_50664_);
        this.m_124288_(Blocks.f_50666_);
        this.m_124288_(Blocks.f_50668_);
        this.m_124288_(Blocks.f_50670_);
        this.m_124288_(Blocks.f_50674_);
        this.m_124288_(Blocks.f_50659_);
        this.m_124288_(Blocks.f_50661_);
        this.m_124288_(Blocks.f_50663_);
        this.m_124288_(Blocks.f_50665_);
        this.m_124288_(Blocks.f_50667_);
        this.m_124288_(Blocks.f_50669_);
        this.m_124288_(Blocks.f_50673_);
        this.m_124288_(Blocks.f_50721_);
        this.m_124288_(Blocks.f_50722_);
        this.m_124288_(Blocks.f_50730_);
        this.m_124288_(Blocks.f_50735_);
        this.m_124288_(Blocks.f_50739_);
        this.m_124288_(Blocks.f_50731_);
        this.m_124288_(Blocks.f_50732_);
        this.m_124288_(Blocks.f_50740_);
        this.m_124288_(Blocks.f_50737_);
        this.m_124288_(Blocks.f_50736_);
        this.m_124288_(Blocks.f_50734_);
        this.m_124288_(Blocks.f_50707_);
        this.m_124288_(Blocks.f_50709_);
        this.m_124288_(Blocks.f_50710_);
        this.m_124288_(Blocks.f_50711_);
        this.m_124288_(Blocks.f_50712_);
        this.m_124288_(Blocks.f_50713_);
        this.m_124288_(Blocks.f_50714_);
        this.m_124288_(Blocks.f_50184_);
        this.m_124288_(Blocks.f_50693_);
        this.m_124288_(Blocks.f_50654_);
        this.m_124288_(Blocks.f_220844_);
        this.m_124288_(Blocks.f_220834_);
        this.m_124288_(Blocks.f_220845_);
        this.m_124288_(Blocks.f_152490_);
        this.m_124288_(Blocks.f_152497_);
        this.m_124288_(Blocks.f_152496_);
        this.m_124288_(Blocks.f_152498_);
        this.m_124272_(Blocks.f_152500_);
        this.m_124272_(Blocks.f_220855_);
        this.m_124272_(Blocks.f_220857_);
        this.m_124175_(Blocks.f_220856_, p_124189_ -> BlockLoot.m_236227_(p_124189_, f_124062_));
        this.m_124272_(Blocks.f_220858_);
        this.m_124288_(Blocks.f_152504_);
        this.m_124288_(Blocks.f_152503_);
        this.m_124288_(Blocks.f_152502_);
        this.m_124288_(Blocks.f_152501_);
        this.m_124288_(Blocks.f_152510_);
        this.m_124288_(Blocks.f_152509_);
        this.m_124288_(Blocks.f_152508_);
        this.m_124288_(Blocks.f_152507_);
        this.m_124288_(Blocks.f_152571_);
        this.m_124288_(Blocks.f_152572_);
        this.m_124288_(Blocks.f_152573_);
        this.m_124288_(Blocks.f_152574_);
        this.m_124288_(Blocks.f_152578_);
        this.m_124288_(Blocks.f_152576_);
        this.m_124288_(Blocks.f_152577_);
        this.m_124288_(Blocks.f_152575_);
        this.m_124288_(Blocks.f_152582_);
        this.m_124288_(Blocks.f_152581_);
        this.m_124288_(Blocks.f_152580_);
        this.m_124288_(Blocks.f_152579_);
        this.m_124288_(Blocks.f_152566_);
        this.m_124288_(Blocks.f_152565_);
        this.m_124288_(Blocks.f_152564_);
        this.m_124288_(Blocks.f_152563_);
        this.m_124288_(Blocks.f_152587_);
        this.m_124288_(Blocks.f_152588_);
        this.m_124288_(Blocks.f_152537_);
        this.m_124288_(Blocks.f_152540_);
        this.m_124288_(Blocks.f_152542_);
        this.m_124288_(Blocks.f_152541_);
        this.m_124288_(Blocks.f_152543_);
        this.m_124288_(Blocks.f_152545_);
        this.m_124288_(Blocks.f_152544_);
        this.m_124288_(Blocks.f_152549_);
        this.m_124288_(Blocks.f_152551_);
        this.m_124288_(Blocks.f_152552_);
        this.m_124288_(Blocks.f_152554_);
        this.m_124288_(Blocks.f_152555_);
        this.m_124288_(Blocks.f_152556_);
        this.m_124288_(Blocks.f_152558_);
        this.m_124288_(Blocks.f_152559_);
        this.m_124288_(Blocks.f_152560_);
        this.m_124288_(Blocks.f_152562_);
        this.m_124288_(Blocks.f_152589_);
        this.m_124288_(Blocks.f_152590_);
        this.m_124288_(Blocks.f_152592_);
        this.m_124288_(Blocks.f_152593_);
        this.m_124288_(Blocks.f_152594_);
        this.m_124288_(Blocks.f_152595_);
        this.m_124288_(Blocks.f_152598_);
        this.m_124288_(Blocks.f_152599_);
        this.m_124288_(Blocks.f_152600_);
        this.m_124288_(Blocks.f_220859_);
        this.m_124288_(Blocks.f_220860_);
        this.m_124288_(Blocks.f_220861_);
        this.m_124288_(Blocks.f_220833_);
        this.m_124288_(Blocks.f_220832_);
        this.m_124288_(Blocks.f_220864_);
        this.m_124288_(Blocks.f_220843_);
        this.m_124147_(Blocks.f_50093_, Blocks.f_50493_);
        this.m_124147_(Blocks.f_50267_, Items.f_42401_);
        this.m_124147_(Blocks.f_152481_, Blocks.f_50493_);
        this.m_124147_(Blocks.f_50576_, Blocks.f_50575_);
        this.m_124147_(Blocks.f_50570_, Blocks.f_50571_);
        this.m_124147_(Blocks.f_152476_, Blocks.f_50256_);
        this.m_124147_(Blocks.f_152477_, Blocks.f_50256_);
        this.m_124147_(Blocks.f_152478_, Blocks.f_50256_);
        this.m_124147_(Blocks.f_152546_, Blocks.f_152545_);
        this.m_124175_(Blocks.f_50069_, p_124187_ -> BlockLoot.m_124257_(p_124187_, Blocks.f_50652_));
        this.m_124175_(Blocks.f_152550_, p_124185_ -> BlockLoot.m_124257_(p_124185_, Blocks.f_152551_));
        this.m_124175_(Blocks.f_50440_, p_124183_ -> BlockLoot.m_124257_(p_124183_, Blocks.f_50493_));
        this.m_124175_(Blocks.f_50599_, p_124181_ -> BlockLoot.m_124257_(p_124181_, Blocks.f_50493_));
        this.m_124175_(Blocks.f_50195_, p_124249_ -> BlockLoot.m_124257_(p_124249_, Blocks.f_50493_));
        this.m_124175_(Blocks.f_50584_, p_124247_ -> BlockLoot.m_124257_(p_124247_, Blocks.f_50579_));
        this.m_124175_(Blocks.f_50585_, p_124245_ -> BlockLoot.m_124257_(p_124245_, Blocks.f_50580_));
        this.m_124175_(Blocks.f_50586_, p_124243_ -> BlockLoot.m_124257_(p_124243_, Blocks.f_50581_));
        this.m_124175_(Blocks.f_50587_, p_124241_ -> BlockLoot.m_124257_(p_124241_, Blocks.f_50582_));
        this.m_124175_(Blocks.f_50588_, p_124239_ -> BlockLoot.m_124257_(p_124239_, Blocks.f_50583_));
        this.m_124175_(Blocks.f_50699_, p_124237_ -> BlockLoot.m_124257_(p_124237_, Blocks.f_50134_));
        this.m_124175_(Blocks.f_50690_, p_124235_ -> BlockLoot.m_124257_(p_124235_, Blocks.f_50134_));
        this.m_124175_(Blocks.f_50078_, p_124233_ -> BlockLoot.m_176042_(p_124233_, Items.f_42517_, ConstantValue.m_165692_(3.0f)));
        this.m_124175_(Blocks.f_50129_, p_124231_ -> BlockLoot.m_176042_(p_124231_, Items.f_42461_, ConstantValue.m_165692_(4.0f)));
        this.m_124175_(Blocks.f_50265_, p_124229_ -> BlockLoot.m_176042_(p_124229_, Blocks.f_50080_, ConstantValue.m_165692_(8.0f)));
        this.m_124175_(Blocks.f_50127_, p_124227_ -> BlockLoot.m_176042_(p_124227_, Items.f_42452_, ConstantValue.m_165692_(4.0f)));
        this.m_124165_(Blocks.f_50490_, BlockLoot.m_176039_(Items.f_42730_, UniformGenerator.m_165780_(0.0f, 1.0f)));
        this.m_124252_(Blocks.f_50277_);
        this.m_124252_(Blocks.f_50278_);
        this.m_124252_(Blocks.f_50279_);
        this.m_124252_(Blocks.f_50280_);
        this.m_124252_(Blocks.f_50229_);
        this.m_124252_(Blocks.f_50230_);
        this.m_124252_(Blocks.f_220847_);
        this.m_124252_(Blocks.f_50231_);
        this.m_124252_(Blocks.f_50232_);
        this.m_124252_(Blocks.f_50233_);
        this.m_124252_(Blocks.f_50234_);
        this.m_124252_(Blocks.f_50235_);
        this.m_124252_(Blocks.f_50236_);
        this.m_124252_(Blocks.f_50237_);
        this.m_124252_(Blocks.f_50238_);
        this.m_124252_(Blocks.f_50239_);
        this.m_124252_(Blocks.f_50240_);
        this.m_124252_(Blocks.f_50241_);
        this.m_124252_(Blocks.f_50242_);
        this.m_124252_(Blocks.f_50243_);
        this.m_124252_(Blocks.f_50244_);
        this.m_124252_(Blocks.f_50245_);
        this.m_124252_(Blocks.f_50246_);
        this.m_124252_(Blocks.f_50247_);
        this.m_124252_(Blocks.f_50248_);
        this.m_124252_(Blocks.f_50572_);
        this.m_124252_(Blocks.f_50725_);
        this.m_124252_(Blocks.f_50726_);
        this.m_124252_(Blocks.f_50727_);
        this.m_124252_(Blocks.f_50728_);
        this.m_124252_(Blocks.f_152601_);
        this.m_124252_(Blocks.f_152602_);
        this.m_124175_(Blocks.f_220851_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50402_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50400_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50410_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50409_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50403_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50385_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50401_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50412_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50398_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50408_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50384_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50383_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50469_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50413_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50467_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50406_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50468_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50407_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50399_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50411_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50404_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50405_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50643_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50644_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50645_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50646_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50647_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50648_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50649_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50650_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50651_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50600_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50601_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50602_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50603_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50657_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50658_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50733_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50738_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50708_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152567_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152568_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152569_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152570_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152583_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152584_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152585_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152586_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152553_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152557_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152561_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_152591_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_220849_, BlockLoot::m_124290_);
        this.m_124175_(Blocks.f_50487_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50485_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50488_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50166_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50486_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50154_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50484_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50672_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50671_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_220853_, BlockLoot::m_124137_);
        this.m_124175_(Blocks.f_50029_, p_124225_ -> BlockLoot.m_124161_(p_124225_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50025_, p_124223_ -> BlockLoot.m_124161_(p_124223_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50026_, p_124221_ -> BlockLoot.m_124161_(p_124221_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50023_, p_124219_ -> BlockLoot.m_124161_(p_124219_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50021_, p_124217_ -> BlockLoot.m_124161_(p_124217_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50027_, p_124215_ -> BlockLoot.m_124161_(p_124215_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50017_, p_124213_ -> BlockLoot.m_124161_(p_124213_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50022_, p_124211_ -> BlockLoot.m_124161_(p_124211_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50019_, p_124209_ -> BlockLoot.m_124161_(p_124209_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50068_, p_124207_ -> BlockLoot.m_124161_(p_124207_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50024_, p_124205_ -> BlockLoot.m_124161_(p_124205_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50067_, p_124201_ -> BlockLoot.m_124161_(p_124201_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50020_, p_124199_ -> BlockLoot.m_124161_(p_124199_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50028_, p_124197_ -> BlockLoot.m_124161_(p_124197_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50066_, p_124124_ -> BlockLoot.m_124161_(p_124124_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50018_, p_124122_ -> BlockLoot.m_124161_(p_124122_, BedBlock.f_49440_, BedPart.HEAD));
        this.m_124175_(Blocks.f_50356_, p_124120_ -> BlockLoot.m_124161_(p_124120_, DoublePlantBlock.f_52858_, DoubleBlockHalf.LOWER));
        this.m_124175_(Blocks.f_50355_, p_124118_ -> BlockLoot.m_124161_(p_124118_, DoublePlantBlock.f_52858_, DoubleBlockHalf.LOWER));
        this.m_124175_(Blocks.f_50358_, p_124116_ -> BlockLoot.m_124161_(p_124116_, DoublePlantBlock.f_52858_, DoubleBlockHalf.LOWER));
        this.m_124175_(Blocks.f_50357_, p_124114_ -> BlockLoot.m_124161_(p_124114_, DoublePlantBlock.f_52858_, DoubleBlockHalf.LOWER));
        this.m_124165_(Blocks.f_50077_, LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(Blocks.f_50077_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Blocks.f_50077_).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50077_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67703_(TntBlock.f_57419_, false)))))));
        this.m_124175_(Blocks.f_50262_, p_124112_ -> LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124112_, LootItem.m_79579_(Items.f_42533_).m_79078_((LootItemFunction.Builder)((Object)SetItemCountFunction.m_165412_(ConstantValue.m_165692_(3.0f)).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124112_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(CocoaBlock.f_51736_, 2)))))))));
        this.m_124175_(Blocks.f_50567_, p_124108_ -> LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(Blocks.f_50567_, LootItem.m_79579_(p_124108_).m_230984_(List.of(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)), p_236241_ -> SetItemCountFunction.m_165412_(ConstantValue.m_165692_(p_236241_.intValue())).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124108_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(SeaPickleBlock.f_56074_, (int)p_236241_))))))));
        this.m_124175_(Blocks.f_50715_, p_124106_ -> LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124106_, LootItem.m_79579_(Items.f_42726_)))).m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_42499_)).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_124106_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(ComposterBlock.f_51913_, 8)))));
        this.m_124175_(Blocks.f_152538_, BlockLoot::m_176052_);
        this.m_124175_(Blocks.f_152539_, BlockLoot::m_176052_);
        this.m_124175_(Blocks.f_152482_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152483_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152484_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152511_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152512_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152513_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152514_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152515_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152516_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152517_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152518_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152519_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152520_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152521_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152522_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152523_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_152524_, BlockLoot::m_176056_);
        this.m_124175_(Blocks.f_50273_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50255_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50087_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50061_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50286_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50201_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50094_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50332_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50325_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50619_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50620_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50618_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50621_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50622_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50623_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50624_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50625_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50679_, BlockLoot::m_124292_);
        this.m_124175_(Blocks.f_50680_, BlockLoot::m_124126_);
        this.m_124175_(Blocks.f_50681_, BlockLoot::m_124126_);
        this.m_124175_(Blocks.f_50682_, BlockLoot::m_124126_);
        this.m_124175_(Blocks.f_50456_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50525_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50521_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50522_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50466_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50464_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50523_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50460_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50465_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50462_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50459_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50458_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50463_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50520_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50524_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50457_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50461_, BlockLoot::m_124294_);
        this.m_124175_(Blocks.f_50429_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50425_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50426_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50423_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50421_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50427_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50417_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50422_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50419_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50416_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50415_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50420_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50424_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50428_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50414_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50418_, BlockLoot::m_124296_);
        this.m_124175_(Blocks.f_50316_, p_124104_ -> LootTable.m_79147_().m_79161_(BlockLoot.m_236224_(p_124104_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(p_124104_).m_79078_(CopyNbtFunction.m_165180_(ContextNbtProvider.f_165562_).m_80279_("SkullOwner", "SkullOwner")))))));
        this.m_124175_(Blocks.f_50717_, BlockLoot::m_124298_);
        this.m_124175_(Blocks.f_50718_, BlockLoot::m_124300_);
        this.m_124175_(Blocks.f_50052_, p_124102_ -> BlockLoot.m_124157_(p_124102_, Blocks.f_50748_, f_124068_));
        this.m_124175_(Blocks.f_50054_, p_124100_ -> BlockLoot.m_124157_(p_124100_, Blocks.f_50750_, f_124068_));
        this.m_124175_(Blocks.f_50053_, p_124098_ -> BlockLoot.m_124157_(p_124098_, Blocks.f_50749_, f_124069_));
        this.m_124175_(Blocks.f_50051_, p_124096_ -> BlockLoot.m_124157_(p_124096_, Blocks.f_50747_, f_124068_));
        this.m_124175_(Blocks.f_50050_, p_124094_ -> BlockLoot.m_124263_(p_124094_, Blocks.f_50746_, f_124068_));
        this.m_124175_(Blocks.f_50055_, p_124092_ -> BlockLoot.m_124263_(p_124092_, Blocks.f_50751_, f_124068_));
        this.m_124175_(Blocks.f_152470_, p_124090_ -> BlockLoot.m_124157_(p_124090_, Blocks.f_152541_, f_124068_));
        this.m_124175_(Blocks.f_152471_, p_124088_ -> BlockLoot.m_124157_(p_124088_, Blocks.f_152542_, f_124068_));
        LootItemBlockStatePropertyCondition.Builder $$1 = LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50444_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(BeetrootBlock.f_49657_, 3));
        this.m_124165_(Blocks.f_50444_, BlockLoot.m_124142_(Blocks.f_50444_, Items.f_42732_, Items.f_42733_, $$1));
        LootItemBlockStatePropertyCondition.Builder $$2 = LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50092_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(CropBlock.f_52244_, 7));
        this.m_124165_(Blocks.f_50092_, BlockLoot.m_124142_(Blocks.f_50092_, Items.f_42405_, Items.f_42404_, $$2));
        LootItemBlockStatePropertyCondition.Builder $$3 = LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50249_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(CarrotBlock.f_52244_, 7));
        LootItemBlockStatePropertyCondition.Builder $$4 = LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_220831_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(MangrovePropaguleBlock.f_221441_, 4));
        this.m_124165_(Blocks.f_220831_, BlockLoot.m_236221_(Blocks.f_220831_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79080_($$4).m_79076_(LootItem.m_79579_(Items.f_220175_)))));
        this.m_124165_(Blocks.f_50249_, BlockLoot.m_236221_(Blocks.f_50249_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_42619_))).m_79161_(LootPool.m_79043_().m_79080_($$3).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(Items.f_42619_).m_79078_(ApplyBonusCount.m_79917_(Enchantments.f_44987_, 0.5714286f, 3)))))));
        LootItemBlockStatePropertyCondition.Builder $$5 = LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50250_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(PotatoBlock.f_52244_, 7));
        this.m_124165_(Blocks.f_50250_, BlockLoot.m_236221_(Blocks.f_50250_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79076_(LootItem.m_79579_(Items.f_42620_))).m_79161_(LootPool.m_79043_().m_79080_($$5).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)LootItem.m_79579_(Items.f_42620_).m_79078_(ApplyBonusCount.m_79917_(Enchantments.f_44987_, 0.5714286f, 3))))).m_79161_(LootPool.m_79043_().m_79080_($$5).m_79076_((LootPoolEntryContainer.Builder<?>)LootItem.m_79579_(Items.f_42675_).m_79080_(LootItemRandomChanceCondition.m_81927_(0.02f))))));
        this.m_124175_(Blocks.f_50685_, p_124086_ -> BlockLoot.m_236221_(p_124086_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79080_(LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50685_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(SweetBerryBushBlock.f_57244_, 3))).m_79076_(LootItem.m_79579_(Items.f_42780_)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 3.0f))).m_79078_(ApplyBonusCount.m_79939_(Enchantments.f_44987_))).m_79161_(LootPool.m_79043_().m_79080_(LootItemBlockStatePropertyCondition.m_81769_(Blocks.f_50685_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(SweetBerryBushBlock.f_57244_, 2))).m_79076_(LootItem.m_79579_(Items.f_42780_)).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0f, 2.0f))).m_79078_(ApplyBonusCount.m_79939_(Enchantments.f_44987_)))));
        this.m_124175_(Blocks.f_50180_, p_124084_ -> BlockLoot.m_124277_(p_124084_, Blocks.f_50072_));
        this.m_124175_(Blocks.f_50181_, p_124082_ -> BlockLoot.m_124277_(p_124082_, Blocks.f_50073_));
        this.m_124175_(Blocks.f_49997_, p_124080_ -> BlockLoot.m_124139_(p_124080_, Items.f_42413_));
        this.m_124175_(Blocks.f_152469_, p_124078_ -> BlockLoot.m_124139_(p_124078_, Items.f_42413_));
        this.m_124175_(Blocks.f_50264_, p_124076_ -> BlockLoot.m_124139_(p_124076_, Items.f_42616_));
        this.m_124175_(Blocks.f_152479_, p_124074_ -> BlockLoot.m_124139_(p_124074_, Items.f_42616_));
        this.m_124175_(Blocks.f_50331_, p_124333_ -> BlockLoot.m_124139_(p_124333_, Items.f_42692_));
        this.m_124175_(Blocks.f_50089_, p_124331_ -> BlockLoot.m_124139_(p_124331_, Items.f_42415_));
        this.m_124175_(Blocks.f_152474_, p_124329_ -> BlockLoot.m_124139_(p_124329_, Items.f_42415_));
        this.m_124175_(Blocks.f_152505_, BlockLoot::m_176046_);
        this.m_124175_(Blocks.f_152506_, BlockLoot::m_176046_);
        this.m_124175_(Blocks.f_49996_, p_124327_ -> BlockLoot.m_124139_(p_124327_, Items.f_151050_));
        this.m_124175_(Blocks.f_152468_, p_124325_ -> BlockLoot.m_124139_(p_124325_, Items.f_151050_));
        this.m_124175_(Blocks.f_49995_, p_124323_ -> BlockLoot.m_124139_(p_124323_, Items.f_151053_));
        this.m_124175_(Blocks.f_152467_, p_124321_ -> BlockLoot.m_124139_(p_124321_, Items.f_151053_));
        this.m_124175_(Blocks.f_49998_, p_124319_ -> BlockLoot.m_124168_(p_124319_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124319_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42587_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 6.0f)))).m_79078_(ApplyBonusCount.m_79915_(Enchantments.f_44987_)))));
        this.m_124175_(Blocks.f_50059_, BlockLoot::m_176048_);
        this.m_124175_(Blocks.f_152472_, BlockLoot::m_176048_);
        this.m_124175_(Blocks.f_50033_, p_124317_ -> BlockLoot.m_124283_(p_124317_, (LootPoolEntryContainer.Builder)BlockLoot.m_236224_(p_124317_, LootItem.m_79579_(Items.f_42401_))));
        this.m_124175_(Blocks.f_50036_, p_124315_ -> BlockLoot.m_124267_(p_124315_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_124315_, LootItem.m_79579_(Items.f_42398_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(0.0f, 2.0f))))));
        this.m_124175_(Blocks.f_50694_, BlockLoot::m_124286_);
        this.m_124175_(Blocks.f_50037_, BlockLoot::m_124286_);
        this.m_124175_(Blocks.f_50191_, BlockLoot::m_124286_);
        this.m_124175_(Blocks.f_152475_, p_124313_ -> BlockLoot.m_236227_(p_124313_, f_124064_));
        this.m_124175_(Blocks.f_152548_, BlockLoot::m_124286_);
        this.m_124175_(Blocks.f_152547_, BlockLoot::m_124286_);
        this.m_124175_(Blocks.f_220838_, BlockLoot::m_236248_);
        this.m_124165_(Blocks.f_50038_, BlockLoot.m_124304_(Blocks.f_50037_));
        this.m_124175_(Blocks.f_50360_, p_124311_ -> BlockLoot.m_124260_(p_124311_, Blocks.f_50035_));
        this.m_124175_(Blocks.f_50359_, p_124309_ -> BlockLoot.m_124260_(p_124309_, Blocks.f_50034_));
        this.m_124175_(Blocks.f_50190_, p_176038_ -> BlockLoot.m_124254_(p_176038_, Items.f_42578_));
        this.m_124175_(Blocks.f_50188_, p_176036_ -> BlockLoot.m_124274_(p_176036_, Items.f_42578_));
        this.m_124175_(Blocks.f_50189_, p_176034_ -> BlockLoot.m_124254_(p_176034_, Items.f_42577_));
        this.m_124175_(Blocks.f_50187_, p_176073_ -> BlockLoot.m_124274_(p_176073_, Items.f_42577_));
        this.m_124175_(Blocks.f_50491_, p_176071_ -> LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)BlockLoot.m_236224_(p_176071_, LootItem.m_79579_(p_176071_))).m_79080_(LootItemEntityPropertyCondition.m_81862_(LootContext.EntityTarget.THIS)))));
        this.m_124175_(Blocks.f_50035_, BlockLoot::m_124302_);
        this.m_124175_(Blocks.f_50034_, BlockLoot::m_124302_);
        this.m_124175_(Blocks.f_50141_, p_176069_ -> BlockLoot.m_124168_(p_176069_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176069_, ((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42525_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 4.0f)))).m_79078_(ApplyBonusCount.m_79939_(Enchantments.f_44987_))).m_79078_(LimitCount.m_165215_(IntRange.m_165011_(1, 4))))));
        this.m_124175_(Blocks.f_50186_, p_176067_ -> BlockLoot.m_124168_(p_176067_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176067_, ((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42575_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0f, 7.0f)))).m_79078_(ApplyBonusCount.m_79939_(Enchantments.f_44987_))).m_79078_(LimitCount.m_165215_(IntRange.m_165040_(9))))));
        this.m_124175_(Blocks.f_50173_, BlockLoot::m_176050_);
        this.m_124175_(Blocks.f_152473_, BlockLoot::m_176050_);
        this.m_124175_(Blocks.f_50386_, p_176065_ -> BlockLoot.m_124168_(p_176065_, (LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_176065_, ((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42696_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 3.0f)))).m_79078_(ApplyBonusCount.m_79939_(Enchantments.f_44987_))).m_79078_(LimitCount.m_165215_(IntRange.m_165011_(1, 5))))));
        this.m_124175_(Blocks.f_50200_, p_176063_ -> LootTable.m_79147_().m_79161_(BlockLoot.m_236221_(p_176063_, LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0f)).m_79076_((LootPoolEntryContainer.Builder<?>)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42588_).m_79078_((LootItemFunction.Builder)((Object)SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 4.0f)).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_176063_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(NetherWartBlock.f_54967_, 3)))))).m_79078_((LootItemFunction.Builder)((Object)ApplyBonusCount.m_79939_(Enchantments.f_44987_).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_176063_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(NetherWartBlock.f_54967_, 3))))))))));
        this.m_124175_(Blocks.f_50125_, p_236263_ -> LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_79080_(LootItemEntityPropertyCondition.m_81862_(LootContext.EntityTarget.THIS)).m_79076_(AlternativesEntry.m_79395_(new LootPoolEntryContainer.Builder[]{AlternativesEntry.m_230933_(SnowLayerBlock.f_56581_.m_6908_(), p_236238_ -> ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42452_).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_236263_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(SnowLayerBlock.f_56581_, (int)p_236238_)))).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(p_236238_.intValue())))).m_79080_(f_124063_), AlternativesEntry.m_230933_(SnowLayerBlock.f_56581_.m_6908_(), p_236235_ -> p_236235_ == 8 ? LootItem.m_79579_(Blocks.f_50127_) : ((LootPoolEntryContainer.Builder)((Object)LootItem.m_79579_(Blocks.f_50125_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(p_236235_.intValue()))))).m_79080_(LootItemBlockStatePropertyCondition.m_81769_(p_236263_).m_81784_(StatePropertiesPredicate.Builder.m_67693_().m_67694_(SnowLayerBlock.f_56581_, (int)p_236235_))))}))));
        this.m_124175_(Blocks.f_49994_, p_236261_ -> BlockLoot.m_124168_(p_236261_, BlockLoot.m_236224_(p_236261_, ((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_42484_).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, 0.1f, 0.14285715f, 0.25f, 1.0f))).m_7170_(LootItem.m_79579_(p_236261_)))));
        this.m_124175_(Blocks.f_50683_, p_236259_ -> BlockLoot.m_124168_(p_236259_, (LootPoolEntryContainer.Builder)BlockLoot.m_236224_(p_236259_, LootItem.m_79579_(Items.f_42414_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(2.0f))))));
        this.m_124175_(Blocks.f_50706_, p_236257_ -> BlockLoot.m_124168_(p_236257_, BlockLoot.m_236224_(p_236257_, ((LootPoolSingletonContainer.Builder)((LootPoolEntryContainer.Builder)((Object)LootItem.m_79579_(Items.f_42587_).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0f, 5.0f))))).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, 0.1f, 0.14285715f, 0.25f, 1.0f))).m_7170_(LootItem.m_79579_(p_236257_)))));
        this.m_124175_(Blocks.f_50684_, p_236255_ -> BlockLoot.m_124168_(p_236255_, (LootPoolEntryContainer.Builder)BlockLoot.m_236224_(p_236255_, LootItem.m_79579_(Items.f_42050_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(1.0f))))));
        this.m_124175_(Blocks.f_152492_, p_236253_ -> BlockLoot.m_124168_(p_236253_, ((LootPoolSingletonContainer.Builder)((LootPoolEntryContainer.Builder)((Object)((LootPoolSingletonContainer.Builder)LootItem.m_79579_(Items.f_151049_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(4.0f)))).m_79078_(ApplyBonusCount.m_79915_(Enchantments.f_44987_)))).m_79080_(MatchTool.m_81997_(ItemPredicate.Builder.m_45068_().m_204145_(ItemTags.f_144323_)))).m_7170_((LootPoolEntryContainer.Builder)BlockLoot.m_236221_(p_236253_, LootItem.m_79579_(Items.f_151049_).m_79078_(SetItemCountFunction.m_165412_(ConstantValue.m_165692_(2.0f)))))));
        this.m_124272_(Blocks.f_152495_);
        this.m_124272_(Blocks.f_152494_);
        this.m_124272_(Blocks.f_152493_);
        this.m_124272_(Blocks.f_50058_);
        this.m_124272_(Blocks.f_50147_);
        this.m_124272_(Blocks.f_50148_);
        this.m_124272_(Blocks.f_50202_);
        this.m_124272_(Blocks.f_50203_);
        this.m_124272_(Blocks.f_50204_);
        this.m_124272_(Blocks.f_50205_);
        this.m_124272_(Blocks.f_50206_);
        this.m_124272_(Blocks.f_50207_);
        this.m_124272_(Blocks.f_50208_);
        this.m_124272_(Blocks.f_50209_);
        this.m_124272_(Blocks.f_50210_);
        this.m_124272_(Blocks.f_50211_);
        this.m_124272_(Blocks.f_50212_);
        this.m_124272_(Blocks.f_50213_);
        this.m_124272_(Blocks.f_50214_);
        this.m_124272_(Blocks.f_50215_);
        this.m_124272_(Blocks.f_50185_);
        this.m_124272_(Blocks.f_50303_);
        this.m_124272_(Blocks.f_50304_);
        this.m_124272_(Blocks.f_50305_);
        this.m_124272_(Blocks.f_50306_);
        this.m_124272_(Blocks.f_50307_);
        this.m_124272_(Blocks.f_50361_);
        this.m_124272_(Blocks.f_50362_);
        this.m_124272_(Blocks.f_50363_);
        this.m_124272_(Blocks.f_50364_);
        this.m_124272_(Blocks.f_50365_);
        this.m_124272_(Blocks.f_50366_);
        this.m_124272_(Blocks.f_50367_);
        this.m_124272_(Blocks.f_50368_);
        this.m_124272_(Blocks.f_50369_);
        this.m_124272_(Blocks.f_50370_);
        this.m_124272_(Blocks.f_50371_);
        this.m_124272_(Blocks.f_50126_);
        this.m_124272_(Blocks.f_50354_);
        this.m_124272_(Blocks.f_50568_);
        this.m_124272_(Blocks.f_50578_);
        this.m_124272_(Blocks.f_50182_);
        this.m_124272_(Blocks.f_50589_);
        this.m_124272_(Blocks.f_50590_);
        this.m_124272_(Blocks.f_50591_);
        this.m_124272_(Blocks.f_50592_);
        this.m_124272_(Blocks.f_50593_);
        this.m_124272_(Blocks.f_50594_);
        this.m_124272_(Blocks.f_50595_);
        this.m_124272_(Blocks.f_50596_);
        this.m_124272_(Blocks.f_50597_);
        this.m_124272_(Blocks.f_50598_);
        this.m_124272_(Blocks.f_50547_);
        this.m_124272_(Blocks.f_50548_);
        this.m_124272_(Blocks.f_50549_);
        this.m_124272_(Blocks.f_50550_);
        this.m_124272_(Blocks.f_50551_);
        this.m_124272_(Blocks.f_50552_);
        this.m_124272_(Blocks.f_50553_);
        this.m_124272_(Blocks.f_50554_);
        this.m_124272_(Blocks.f_50555_);
        this.m_124272_(Blocks.f_50556_);
        this.m_124154_(Blocks.f_50226_, Blocks.f_50069_);
        this.m_124154_(Blocks.f_50227_, Blocks.f_50652_);
        this.m_124154_(Blocks.f_50176_, Blocks.f_50222_);
        this.m_124154_(Blocks.f_50177_, Blocks.f_50223_);
        this.m_124154_(Blocks.f_50178_, Blocks.f_50224_);
        this.m_124154_(Blocks.f_50179_, Blocks.f_50225_);
        this.m_124154_(Blocks.f_152596_, Blocks.f_152550_);
        this.m_124280_(Blocks.f_50702_, Blocks.f_50703_);
        this.m_124280_(Blocks.f_50704_, Blocks.f_50653_);
        this.m_124165_(Blocks.f_50145_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_152525_, BlockLoot.m_176058_(Blocks.f_152482_));
        this.m_124165_(Blocks.f_152526_, BlockLoot.m_176058_(Blocks.f_152483_));
        this.m_124165_(Blocks.f_152527_, BlockLoot.m_176058_(Blocks.f_152484_));
        this.m_124165_(Blocks.f_152528_, BlockLoot.m_176058_(Blocks.f_152511_));
        this.m_124165_(Blocks.f_152529_, BlockLoot.m_176058_(Blocks.f_152512_));
        this.m_124165_(Blocks.f_152530_, BlockLoot.m_176058_(Blocks.f_152513_));
        this.m_124165_(Blocks.f_152531_, BlockLoot.m_176058_(Blocks.f_152514_));
        this.m_124165_(Blocks.f_152532_, BlockLoot.m_176058_(Blocks.f_152515_));
        this.m_124165_(Blocks.f_152533_, BlockLoot.m_176058_(Blocks.f_152516_));
        this.m_124165_(Blocks.f_152534_, BlockLoot.m_176058_(Blocks.f_152517_));
        this.m_124165_(Blocks.f_152535_, BlockLoot.m_176058_(Blocks.f_152518_));
        this.m_124165_(Blocks.f_152536_, BlockLoot.m_176058_(Blocks.f_152519_));
        this.m_124165_(Blocks.f_152485_, BlockLoot.m_176058_(Blocks.f_152520_));
        this.m_124165_(Blocks.f_152486_, BlockLoot.m_176058_(Blocks.f_152521_));
        this.m_124165_(Blocks.f_152487_, BlockLoot.m_176058_(Blocks.f_152522_));
        this.m_124165_(Blocks.f_152488_, BlockLoot.m_176058_(Blocks.f_152523_));
        this.m_124165_(Blocks.f_152489_, BlockLoot.m_176058_(Blocks.f_152524_));
        this.m_124165_(Blocks.f_50449_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_50085_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_50083_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_50084_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_50142_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_152491_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_152499_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_220862_, BlockLoot.m_124125_());
        this.m_124165_(Blocks.f_220863_, BlockLoot.m_124125_());
        HashSet $$6 = Sets.newHashSet();
        for (Block $$7 : Registry.f_122824_) {
            ResourceLocation $$8 = $$7.m_60589_();
            if ($$8 == BuiltInLootTables.f_78712_ || !$$6.add($$8)) continue;
            LootTable.Builder $$9 = this.f_124070_.remove($$8);
            if ($$9 == null) {
                throw new IllegalStateException(String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", $$8, Registry.f_122824_.m_7981_($$7)));
            }
            p_124179_.accept($$8, $$9);
        }
        if (!this.f_124070_.isEmpty()) {
            throw new IllegalStateException("Created block loot tables for non-blocks: " + this.f_124070_.keySet());
        }
    }

    private void m_124280_(Block p_124281_, Block p_124282_) {
        LootTable.Builder $$2 = BlockLoot.m_124283_(p_124281_, LootItem.m_79579_(p_124281_).m_79080_(BonusLevelTableCondition.m_81517_(Enchantments.f_44987_, 0.33f, 0.55f, 0.77f, 1.0f)));
        this.m_124165_(p_124281_, $$2);
        this.m_124165_(p_124282_, $$2);
    }

    public static LootTable.Builder m_124137_(Block p_124138_) {
        return BlockLoot.m_124161_(p_124138_, DoorBlock.f_52730_, DoubleBlockHalf.LOWER);
    }

    public void m_124252_(Block p_124253_) {
        this.m_124175_(p_124253_, p_236251_ -> BlockLoot.m_124270_(((FlowerPotBlock)p_236251_).m_53560_()));
    }

    public void m_124154_(Block p_124155_, Block p_124156_) {
        this.m_124165_(p_124155_, BlockLoot.m_124250_(p_124156_));
    }

    public void m_124147_(Block p_124148_, ItemLike p_124149_) {
        this.m_124165_(p_124148_, BlockLoot.m_124126_(p_124149_));
    }

    public void m_124272_(Block p_124273_) {
        this.m_124154_(p_124273_, p_124273_);
    }

    public void m_124288_(Block p_124289_) {
        this.m_124147_(p_124289_, p_124289_);
    }

    private void m_124175_(Block p_124176_, Function<Block, LootTable.Builder> p_124177_) {
        this.m_124165_(p_124176_, p_124177_.apply(p_124176_));
    }

    private void m_124165_(Block p_124166_, LootTable.Builder p_124167_) {
        this.f_124070_.put(p_124166_.m_60589_(), p_124167_);
    }

    @Override
    public /* synthetic */ void accept(Object object) {
        this.accept((BiConsumer)object);
    }
}

