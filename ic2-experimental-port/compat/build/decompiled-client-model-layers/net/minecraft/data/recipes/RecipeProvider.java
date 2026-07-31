/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.data.recipes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

public class RecipeProvider
implements DataProvider {
    private static final Logger f_125968_ = LogUtils.getLogger();
    private static final ImmutableList<ItemLike> f_176505_ = ImmutableList.of((Object)Items.f_41835_, (Object)Items.f_150963_);
    private static final ImmutableList<ItemLike> f_176506_ = ImmutableList.of((Object)Items.f_41834_, (Object)Items.f_150964_, (Object)Items.f_151050_);
    private static final ImmutableList<ItemLike> f_176507_ = ImmutableList.of((Object)Items.f_150965_, (Object)Items.f_150966_, (Object)Items.f_151051_);
    private static final ImmutableList<ItemLike> f_176508_ = ImmutableList.of((Object)Items.f_41833_, (Object)Items.f_150967_, (Object)Items.f_41836_, (Object)Items.f_151053_);
    private static final ImmutableList<ItemLike> f_176509_ = ImmutableList.of((Object)Items.f_42010_, (Object)Items.f_150994_);
    private static final ImmutableList<ItemLike> f_176510_ = ImmutableList.of((Object)Items.f_41853_, (Object)Items.f_150993_);
    private static final ImmutableList<ItemLike> f_176511_ = ImmutableList.of((Object)Items.f_41977_, (Object)Items.f_150968_);
    private static final ImmutableList<ItemLike> f_176512_ = ImmutableList.of((Object)Items.f_42107_, (Object)Items.f_150969_);
    private final DataGenerator.PathProvider f_236355_;
    private final DataGenerator.PathProvider f_236356_;
    private static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> f_176513_ = ImmutableMap.builder().put((Object)BlockFamily.Variant.BUTTON, (p_176733_, p_176734_) -> RecipeProvider.m_176658_(p_176733_, Ingredient.m_43929_(p_176734_))).put((Object)BlockFamily.Variant.CHISELED, (p_176730_, p_176731_) -> RecipeProvider.m_176646_(p_176730_, Ingredient.m_43929_(p_176731_))).put((Object)BlockFamily.Variant.CUT, (p_176724_, p_176725_) -> RecipeProvider.m_176634_(p_176724_, Ingredient.m_43929_(p_176725_))).put((Object)BlockFamily.Variant.DOOR, (p_176714_, p_176715_) -> RecipeProvider.m_176670_(p_176714_, Ingredient.m_43929_(p_176715_))).put((Object)BlockFamily.Variant.FENCE, (p_176708_, p_176709_) -> RecipeProvider.m_176678_(p_176708_, Ingredient.m_43929_(p_176709_))).put((Object)BlockFamily.Variant.FENCE_GATE, (p_176698_, p_176699_) -> RecipeProvider.m_176684_(p_176698_, Ingredient.m_43929_(p_176699_))).put((Object)BlockFamily.Variant.SIGN, (p_176688_, p_176689_) -> RecipeProvider.m_176726_(p_176688_, Ingredient.m_43929_(p_176689_))).put((Object)BlockFamily.Variant.SLAB, (p_176682_, p_176683_) -> RecipeProvider.m_176704_(p_176682_, Ingredient.m_43929_(p_176683_))).put((Object)BlockFamily.Variant.STAIRS, (p_176674_, p_176675_) -> RecipeProvider.m_176710_(p_176674_, Ingredient.m_43929_(p_176675_))).put((Object)BlockFamily.Variant.PRESSURE_PLATE, (p_176662_, p_176663_) -> RecipeProvider.m_176694_(p_176662_, Ingredient.m_43929_(p_176663_))).put((Object)BlockFamily.Variant.POLISHED, (p_176650_, p_176651_) -> RecipeProvider.m_176604_(p_176650_, Ingredient.m_43929_(p_176651_))).put((Object)BlockFamily.Variant.TRAPDOOR, (p_176638_, p_176639_) -> RecipeProvider.m_176720_(p_176638_, Ingredient.m_43929_(p_176639_))).put((Object)BlockFamily.Variant.WALL, (p_176608_, p_176609_) -> RecipeProvider.m_176514_(p_176608_, Ingredient.m_43929_(p_176609_))).build();

    public RecipeProvider(DataGenerator p_125973_) {
        this.f_236355_ = p_125973_.m_236036_(DataGenerator.Target.DATA_PACK, "recipes");
        this.f_236356_ = p_125973_.m_236036_(DataGenerator.Target.DATA_PACK, "advancements");
    }

    @Override
    public void m_213708_(CachedOutput p_236358_) {
        HashSet $$1 = Sets.newHashSet();
        RecipeProvider.m_176531_(p_236366_ -> {
            if (!$$1.add(p_236366_.m_6445_())) {
                throw new IllegalStateException("Duplicate recipe " + p_236366_.m_6445_());
            }
            RecipeProvider.m_236359_(p_236358_, p_236366_.m_125966_(), this.f_236355_.m_236048_(p_236366_.m_6445_()));
            JsonObject $$3 = p_236366_.m_5860_();
            if ($$3 != null) {
                RecipeProvider.m_236367_(p_236358_, $$3, this.f_236356_.m_236048_(p_236366_.m_6448_()));
            }
        });
        RecipeProvider.m_236367_(p_236358_, Advancement.Builder.m_138353_().m_138386_("impossible", new ImpossibleTrigger.TriggerInstance()).m_138400_(), this.f_236356_.m_236048_(RecipeBuilder.f_236353_));
    }

    private static void m_236359_(CachedOutput p_236360_, JsonObject p_236361_, Path p_236362_) {
        try {
            DataProvider.m_236072_(p_236360_, (JsonElement)p_236361_, p_236362_);
        }
        catch (IOException $$3) {
            f_125968_.error("Couldn't save recipe {}", (Object)p_236362_, (Object)$$3);
        }
    }

    private static void m_236367_(CachedOutput p_236368_, JsonObject p_236369_, Path p_236370_) {
        try {
            DataProvider.m_236072_(p_236368_, (JsonElement)p_236369_, p_236370_);
        }
        catch (IOException $$3) {
            f_125968_.error("Couldn't save recipe advancement {}", (Object)p_236370_, (Object)$$3);
        }
    }

    private static void m_176531_(Consumer<FinishedRecipe> p_176532_) {
        BlockFamilies.m_175934_().filter(BlockFamily::m_175956_).forEach(p_176624_ -> RecipeProvider.m_176580_(p_176532_, p_176624_));
        RecipeProvider.m_206408_(p_176532_, Blocks.f_50744_, ItemTags.f_13186_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_50742_, ItemTags.f_13185_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_50655_, ItemTags.f_13189_);
        RecipeProvider.m_206408_(p_176532_, Blocks.f_50745_, ItemTags.f_13183_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_50743_, ItemTags.f_13187_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_50705_, ItemTags.f_13184_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_50741_, ItemTags.f_13188_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_50656_, ItemTags.f_13190_);
        RecipeProvider.m_206412_(p_176532_, Blocks.f_220865_, ItemTags.f_215869_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50015_, Blocks.f_50003_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50013_, Blocks.f_50001_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50043_, Blocks.f_50004_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50014_, Blocks.f_50002_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50011_, Blocks.f_49999_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50012_, Blocks.f_50000_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50697_, Blocks.f_50695_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50688_, Blocks.f_50686_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_220836_, Blocks.f_220832_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50048_, Blocks.f_50008_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50046_, Blocks.f_50006_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50049_, Blocks.f_50009_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50047_, Blocks.f_50007_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50044_, Blocks.f_50010_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50045_, Blocks.f_50005_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50698_, Blocks.f_50696_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_50689_, Blocks.f_50687_);
        RecipeProvider.m_126002_(p_176532_, Blocks.f_220837_, Blocks.f_220835_);
        RecipeProvider.m_126021_(p_176532_, Items.f_42745_, Blocks.f_50744_);
        RecipeProvider.m_126021_(p_176532_, Items.f_42743_, Blocks.f_50742_);
        RecipeProvider.m_126021_(p_176532_, Items.f_42746_, Blocks.f_50745_);
        RecipeProvider.m_126021_(p_176532_, Items.f_42744_, Blocks.f_50743_);
        RecipeProvider.m_126021_(p_176532_, Items.f_42453_, Blocks.f_50705_);
        RecipeProvider.m_126021_(p_176532_, Items.f_42742_, Blocks.f_50741_);
        RecipeProvider.m_126021_(p_176532_, Items.f_220204_, Blocks.f_220865_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50109_, Items.f_42498_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50351_, Blocks.f_50109_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50351_, Items.f_42498_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42571_, Blocks.f_50109_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42571_, Items.f_42498_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42728_, Blocks.f_50109_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50105_, Items.f_42494_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50347_, Blocks.f_50105_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50347_, Items.f_42494_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42514_, Blocks.f_50105_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42514_, Items.f_42494_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42671_, Blocks.f_50105_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50106_, Items.f_42495_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50348_, Blocks.f_50106_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50348_, Items.f_42495_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42568_, Blocks.f_50106_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42568_, Items.f_42495_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42672_, Blocks.f_50106_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50103_, Items.f_42492_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50345_, Blocks.f_50103_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50345_, Items.f_42492_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42512_, Blocks.f_50103_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42512_, Items.f_42492_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42669_, Blocks.f_50103_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50101_, Items.f_42490_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50343_, Blocks.f_50101_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50343_, Items.f_42490_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42510_, Blocks.f_50101_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42510_, Items.f_42490_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42667_, Blocks.f_50101_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50107_, Items.f_42496_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50349_, Blocks.f_50107_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50349_, Items.f_42496_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42569_, Blocks.f_50107_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42569_, Items.f_42496_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42673_, Blocks.f_50107_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50097_, Items.f_42538_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50339_, Blocks.f_50097_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50339_, Items.f_42538_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42506_, Blocks.f_50097_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42506_, Items.f_42538_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42663_, Blocks.f_50097_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50102_, Items.f_42491_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50344_, Blocks.f_50102_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50344_, Items.f_42491_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42511_, Blocks.f_50102_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42511_, Items.f_42491_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42668_, Blocks.f_50102_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50099_, Items.f_42540_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50341_, Blocks.f_50099_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50341_, Items.f_42540_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42508_, Blocks.f_50099_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42508_, Items.f_42540_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42665_, Blocks.f_50099_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50096_, Items.f_42537_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50338_, Blocks.f_50096_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50338_, Items.f_42537_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42505_, Blocks.f_50096_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42505_, Items.f_42537_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42662_, Blocks.f_50096_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50042_, Items.f_42536_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50337_, Blocks.f_50042_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50337_, Items.f_42536_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42504_, Blocks.f_50042_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42504_, Items.f_42536_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42661_, Blocks.f_50042_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50100_, Items.f_42489_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50342_, Blocks.f_50100_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50342_, Items.f_42489_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42509_, Blocks.f_50100_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42509_, Items.f_42489_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42666_, Blocks.f_50100_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50104_, Items.f_42493_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50346_, Blocks.f_50104_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50346_, Items.f_42493_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42513_, Blocks.f_50104_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42513_, Items.f_42493_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42670_, Blocks.f_50104_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50108_, Items.f_42497_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50350_, Blocks.f_50108_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50350_, Items.f_42497_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42570_, Blocks.f_50108_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42570_, Items.f_42497_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42727_, Blocks.f_50108_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50336_, Blocks.f_50041_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42503_, Blocks.f_50041_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42660_, Blocks.f_50041_);
        RecipeProvider.m_126061_(p_176532_, Blocks.f_50098_, Items.f_42539_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_50340_, Blocks.f_50098_);
        RecipeProvider.m_126069_(p_176532_, Blocks.f_50340_, Items.f_42539_);
        RecipeProvider.m_126073_(p_176532_, Items.f_42507_, Blocks.f_50098_);
        RecipeProvider.m_126077_(p_176532_, Items.f_42507_, Items.f_42539_);
        RecipeProvider.m_126081_(p_176532_, Items.f_42664_, Blocks.f_50098_);
        RecipeProvider.m_176716_(p_176532_, Blocks.f_152543_, Blocks.f_152544_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50215_, Items.f_42498_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50371_, Blocks.f_50215_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50371_, Items.f_42498_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50211_, Items.f_42494_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50367_, Blocks.f_50211_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50367_, Items.f_42494_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50212_, Items.f_42495_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50368_, Blocks.f_50212_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50368_, Items.f_42495_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50209_, Items.f_42492_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50365_, Blocks.f_50209_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50365_, Items.f_42492_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50207_, Items.f_42490_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50363_, Blocks.f_50207_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50363_, Items.f_42490_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50213_, Items.f_42496_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50369_, Blocks.f_50213_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50369_, Items.f_42496_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50203_, Items.f_42538_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50306_, Blocks.f_50203_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50306_, Items.f_42538_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50208_, Items.f_42491_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50364_, Blocks.f_50208_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50364_, Items.f_42491_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50205_, Items.f_42540_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50361_, Blocks.f_50205_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50361_, Items.f_42540_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50202_, Items.f_42537_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50305_, Blocks.f_50202_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50305_, Items.f_42537_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50148_, Items.f_42536_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50304_, Blocks.f_50148_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50304_, Items.f_42536_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50206_, Items.f_42489_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50362_, Blocks.f_50206_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50362_, Items.f_42489_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50210_, Items.f_42493_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50366_, Blocks.f_50210_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50366_, Items.f_42493_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50214_, Items.f_42497_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50370_, Blocks.f_50214_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50370_, Items.f_42497_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50147_, Items.f_42535_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50303_, Blocks.f_50147_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50303_, Items.f_42535_);
        RecipeProvider.m_126085_(p_176532_, Blocks.f_50204_, Items.f_42539_);
        RecipeProvider.m_126089_(p_176532_, Blocks.f_50307_, Blocks.f_50204_);
        RecipeProvider.m_126093_(p_176532_, Blocks.f_50307_, Items.f_42539_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50302_, Items.f_42498_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50298_, Items.f_42494_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50299_, Items.f_42495_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50296_, Items.f_42492_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50294_, Items.f_42490_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50300_, Items.f_42496_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50290_, Items.f_42538_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50295_, Items.f_42491_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50292_, Items.f_42540_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50289_, Items.f_42537_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50288_, Items.f_42536_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50293_, Items.f_42489_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50297_, Items.f_42493_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50301_, Items.f_42497_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50287_, Items.f_42535_);
        RecipeProvider.m_126097_(p_176532_, Blocks.f_50291_, Items.f_42539_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50574_, Items.f_42498_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50517_, Items.f_42494_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50518_, Items.f_42495_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50515_, Items.f_42492_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50513_, Items.f_42490_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50519_, Items.f_42496_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50509_, Items.f_42538_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50514_, Items.f_42491_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50511_, Items.f_42540_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50508_, Items.f_42537_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50507_, Items.f_42536_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50512_, Items.f_42489_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50516_, Items.f_42493_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50573_, Items.f_42497_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50506_, Items.f_42535_);
        RecipeProvider.m_126101_(p_176532_, Blocks.f_50510_, Items.f_42539_);
        ShapedRecipeBuilder.m_126116_(Items.f_151065_).m_126127_(Character.valueOf('S'), Items.f_42401_).m_126127_(Character.valueOf('H'), Items.f_42784_).m_126130_("S").m_126130_("H").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_126132_("has_honeycomb", RecipeProvider.m_125977_(Items.f_42784_)).m_176498_(p_176532_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152524_, Items.f_42498_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152520_, Items.f_42494_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152521_, Items.f_42495_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152518_, Items.f_42492_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152516_, Items.f_42490_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152522_, Items.f_42496_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152512_, Items.f_42538_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152517_, Items.f_42491_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152514_, Items.f_42540_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152511_, Items.f_42537_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152484_, Items.f_42536_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152515_, Items.f_42489_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152519_, Items.f_42493_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152523_, Items.f_42497_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152483_, Items.f_42535_);
        RecipeProvider.m_176542_(p_176532_, Blocks.f_152513_, Items.f_42539_);
        ShapelessRecipeBuilder.m_126191_(Blocks.f_220843_, 1).m_126209_(Blocks.f_220864_).m_126209_(Items.f_42405_).m_126132_("has_mud", RecipeProvider.m_125977_(Blocks.f_220864_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_220844_, 4).m_126127_(Character.valueOf('#'), Blocks.f_220843_).m_126130_("##").m_126130_("##").m_126132_("has_packed_mud", RecipeProvider.m_125977_(Blocks.f_220843_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Blocks.f_220834_, 1).m_126209_(Blocks.f_220864_).m_126209_(Items.f_220180_).m_126132_("has_mangrove_roots", RecipeProvider.m_125977_(Blocks.f_220833_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50285_, 6).m_126127_(Character.valueOf('#'), Blocks.f_50174_).m_126127_(Character.valueOf('S'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("XSX").m_126130_("X#X").m_126130_("XSX").m_126132_("has_rail", RecipeProvider.m_125977_(Blocks.f_50156_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Blocks.f_50334_, 2).m_126209_(Blocks.f_50228_).m_126209_(Blocks.f_50652_).m_126132_("has_stone", RecipeProvider.m_125977_(Blocks.f_50228_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50322_).m_126127_(Character.valueOf('I'), Blocks.f_50075_).m_126127_(Character.valueOf('i'), Items.f_42416_).m_126130_("III").m_126130_(" i ").m_126130_("iii").m_126132_("has_iron_block", RecipeProvider.m_125977_(Blocks.f_50075_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42650_).m_126127_(Character.valueOf('/'), Items.f_42398_).m_126127_(Character.valueOf('_'), Blocks.f_50405_).m_126130_("///").m_126130_(" / ").m_126130_("/_/").m_126132_("has_stone_slab", RecipeProvider.m_125977_(Blocks.f_50405_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42412_, 4).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42484_).m_126127_(Character.valueOf('Y'), Items.f_42402_).m_126130_("X").m_126130_("#").m_126130_("Y").m_126132_("has_feather", RecipeProvider.m_125977_(Items.f_42402_)).m_126132_("has_flint", RecipeProvider.m_125977_(Items.f_42484_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50618_, 1).m_206416_(Character.valueOf('P'), ItemTags.f_13168_).m_206416_(Character.valueOf('S'), ItemTags.f_13175_).m_126130_("PSP").m_126130_("P P").m_126130_("PSP").m_126132_("has_planks", RecipeProvider.m_206406_(ItemTags.f_13168_)).m_126132_("has_wood_slab", RecipeProvider.m_206406_(ItemTags.f_13175_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50273_).m_126127_(Character.valueOf('S'), Items.f_42686_).m_126127_(Character.valueOf('G'), Blocks.f_50058_).m_126127_(Character.valueOf('O'), Blocks.f_50080_).m_126130_("GGG").m_126130_("GSG").m_126130_("OOO").m_126132_("has_nether_star", RecipeProvider.m_125977_(Items.f_42686_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50718_).m_206416_(Character.valueOf('P'), ItemTags.f_13168_).m_126127_(Character.valueOf('H'), Items.f_42784_).m_126130_("PPP").m_126130_("HHH").m_126130_("PPP").m_126132_("has_honeycomb", RecipeProvider.m_125977_(Items.f_42784_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42734_).m_126209_(Items.f_42399_).m_126211_(Items.f_42732_, 6).m_126132_("has_beetroot", RecipeProvider.m_125977_(Items.f_42732_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42498_).m_126209_(Items.f_42532_).m_126145_("black_dye").m_126132_("has_ink_sac", RecipeProvider.m_125977_(Items.f_42532_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42498_, Blocks.f_50070_, "black_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42593_, 2).m_126209_(Items.f_42585_).m_126132_("has_blaze_rod", RecipeProvider.m_125977_(Items.f_42585_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42494_).m_126209_(Items.f_42534_).m_126145_("blue_dye").m_126132_("has_lapis_lazuli", RecipeProvider.m_125977_(Items.f_42534_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42494_, Blocks.f_50121_, "blue_dye");
        ShapedRecipeBuilder.m_126116_(Blocks.f_50568_).m_126127_(Character.valueOf('#'), Blocks.f_50354_).m_126130_("###").m_126130_("###").m_126130_("###").m_126132_("has_packed_ice", RecipeProvider.m_125977_(Blocks.f_50354_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42499_, 3).m_126209_(Items.f_42500_).m_126145_("bonemeal").m_126132_("has_bone", RecipeProvider.m_125977_(Items.f_42500_)).m_176498_(p_176532_);
        RecipeProvider.m_176616_(p_176532_, Items.f_42499_, Items.f_42262_, "bone_meal_from_bone_block", "bonemeal");
        ShapelessRecipeBuilder.m_126189_(Items.f_42517_).m_126211_(Items.f_42516_, 3).m_126209_(Items.f_42454_).m_126132_("has_paper", RecipeProvider.m_125977_(Items.f_42516_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50078_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('X'), Items.f_42517_).m_126130_("###").m_126130_("XXX").m_126130_("###").m_126132_("has_book", RecipeProvider.m_125977_(Items.f_42517_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42411_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42401_).m_126130_(" #X").m_126130_("# X").m_126130_(" #X").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42399_, 4).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126130_("# #").m_126130_(" # ").m_126132_("has_brown_mushroom", RecipeProvider.m_125977_(Blocks.f_50072_)).m_126132_("has_red_mushroom", RecipeProvider.m_125977_(Blocks.f_50073_)).m_126132_("has_mushroom_stew", RecipeProvider.m_125977_(Items.f_42400_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42406_).m_126127_(Character.valueOf('#'), Items.f_42405_).m_126130_("###").m_126132_("has_wheat", RecipeProvider.m_125977_(Items.f_42405_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50255_).m_126127_(Character.valueOf('B'), Items.f_42585_).m_206416_(Character.valueOf('#'), ItemTags.f_13166_).m_126130_(" B ").m_126130_("###").m_126132_("has_blaze_rod", RecipeProvider.m_125977_(Items.f_42585_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50076_).m_126127_(Character.valueOf('#'), Items.f_42460_).m_126130_("##").m_126130_("##").m_126132_("has_brick", RecipeProvider.m_125977_(Items.f_42460_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42495_).m_126209_(Items.f_42533_).m_126145_("brown_dye").m_126132_("has_cocoa_beans", RecipeProvider.m_125977_(Items.f_42533_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42446_).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126130_("# #").m_126130_(" # ").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50145_).m_126127_(Character.valueOf('A'), Items.f_42455_).m_126127_(Character.valueOf('B'), Items.f_42501_).m_126127_(Character.valueOf('C'), Items.f_42405_).m_126127_(Character.valueOf('E'), Items.f_42521_).m_126130_("AAA").m_126130_("BEB").m_126130_("CCC").m_126132_("has_egg", RecipeProvider.m_125977_(Items.f_42521_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50683_).m_206416_(Character.valueOf('L'), ItemTags.f_13182_).m_126127_(Character.valueOf('S'), Items.f_42398_).m_206416_(Character.valueOf('C'), ItemTags.f_13160_).m_126130_(" S ").m_126130_("SCS").m_126130_("LLL").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_126132_("has_coal", RecipeProvider.m_206406_(ItemTags.f_13160_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42684_).m_126127_(Character.valueOf('#'), Items.f_42523_).m_126127_(Character.valueOf('X'), Items.f_42619_).m_126130_("# ").m_126130_(" X").m_126132_("has_carrot", RecipeProvider.m_125977_(Items.f_42619_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42685_).m_126127_(Character.valueOf('#'), Items.f_42523_).m_126127_(Character.valueOf('X'), Items.f_41955_).m_126130_("# ").m_126130_(" X").m_126132_("has_warped_fungus", RecipeProvider.m_125977_(Items.f_41955_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50256_).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126130_("# #").m_126130_("# #").m_126130_("###").m_126132_("has_water_bucket", RecipeProvider.m_125977_(Items.f_42447_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50715_).m_206416_(Character.valueOf('#'), ItemTags.f_13175_).m_126130_("# #").m_126130_("# #").m_126130_("###").m_126132_("has_wood_slab", RecipeProvider.m_206406_(ItemTags.f_13175_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50087_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126130_("###").m_126130_("# #").m_126130_("###").m_126132_("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.m_55386_(10), MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, new ItemPredicate[0])).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42519_).m_126209_(Blocks.f_50087_).m_126209_(Items.f_42449_).m_126132_("has_minecart", RecipeProvider.m_125977_(Items.f_42449_)).m_176498_(p_176532_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220202_, Items.f_42745_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220200_, Items.f_42743_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220203_, Items.f_42746_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220201_, Items.f_42744_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220207_, Items.f_42453_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220208_, Items.f_42742_);
        RecipeProvider.m_236371_(p_176532_, Items.f_220205_, Items.f_220204_);
        RecipeProvider.m_176646_(Blocks.f_50282_, Ingredient.m_43929_(Blocks.f_50413_)).m_126132_("has_chiseled_quartz_block", RecipeProvider.m_125977_(Blocks.f_50282_)).m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_126132_("has_quartz_pillar", RecipeProvider.m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
        RecipeProvider.m_176646_(Blocks.f_50225_, Ingredient.m_43929_(Blocks.f_50411_)).m_126132_("has_tag", RecipeProvider.m_206406_(ItemTags.f_13169_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50129_).m_126127_(Character.valueOf('#'), Items.f_42461_).m_126130_("##").m_126130_("##").m_126132_("has_clay_ball", RecipeProvider.m_125977_(Items.f_42461_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42524_).m_126127_(Character.valueOf('#'), Items.f_42417_).m_126127_(Character.valueOf('X'), Items.f_42451_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_176498_(p_176532_);
        RecipeProvider.m_176743_(p_176532_, Items.f_42413_, Items.f_42200_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50546_, 4).m_126127_(Character.valueOf('D'), Blocks.f_50493_).m_126127_(Character.valueOf('G'), Blocks.f_49994_).m_126130_("DG").m_126130_("GD").m_126132_("has_gravel", RecipeProvider.m_125977_(Blocks.f_49994_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50328_).m_126127_(Character.valueOf('#'), Blocks.f_50174_).m_126127_(Character.valueOf('X'), Items.f_42692_).m_126127_(Character.valueOf('I'), Blocks.f_50069_).m_126130_(" # ").m_126130_("#X#").m_126130_("III").m_126132_("has_quartz", RecipeProvider.m_125977_(Items.f_42692_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42522_).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126127_(Character.valueOf('X'), Items.f_42451_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42572_, 8).m_126127_(Character.valueOf('#'), Items.f_42405_).m_126127_(Character.valueOf('X'), Items.f_42533_).m_126130_("#X#").m_126132_("has_cocoa", RecipeProvider.m_125977_(Items.f_42533_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50091_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126130_("##").m_126130_("##").m_126132_("has_planks", RecipeProvider.m_206406_(ItemTags.f_13168_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42717_).m_126127_(Character.valueOf('~'), Items.f_42401_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('&'), Items.f_42416_).m_126127_(Character.valueOf('$'), Blocks.f_50266_).m_126130_("#&#").m_126130_("~$~").m_126130_(" # ").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_126132_("has_tripwire_hook", RecipeProvider.m_125977_(Blocks.f_50266_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50617_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('@'), Items.f_42401_).m_126130_("@@").m_126130_("##").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_176498_(p_176532_);
        RecipeProvider.m_176646_(Blocks.f_50395_, Ingredient.m_43929_(Blocks.f_50467_)).m_126132_("has_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50394_)).m_126132_("has_chiseled_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50395_)).m_126132_("has_cut_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50396_)).m_176498_(p_176532_);
        RecipeProvider.m_176664_(p_176532_, Blocks.f_50063_, Blocks.f_50406_);
        RecipeProvider.m_176616_(p_176532_, Items.f_151052_, Items.f_151000_, RecipeProvider.m_176644_(Items.f_151052_), RecipeProvider.m_176632_(Items.f_151052_));
        ShapelessRecipeBuilder.m_126191_(Items.f_151052_, 9).m_126209_(Blocks.f_152571_).m_126145_(RecipeProvider.m_176632_(Items.f_151052_)).m_126132_(RecipeProvider.m_176602_(Blocks.f_152571_), RecipeProvider.m_125977_(Blocks.f_152571_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Items.f_151052_, Blocks.f_152571_));
        RecipeProvider.m_176610_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42492_, 2).m_126209_(Items.f_42494_).m_126209_(Items.f_42496_).m_126132_("has_green_dye", RecipeProvider.m_125977_(Items.f_42496_)).m_126132_("has_blue_dye", RecipeProvider.m_125977_(Items.f_42494_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50379_).m_126127_(Character.valueOf('S'), Items.f_42695_).m_126127_(Character.valueOf('I'), Items.f_42498_).m_126130_("SSS").m_126130_("SIS").m_126130_("SSS").m_126132_("has_prismarine_shard", RecipeProvider.m_125977_(Items.f_42695_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50329_).m_126127_(Character.valueOf('Q'), Items.f_42692_).m_126127_(Character.valueOf('G'), Blocks.f_50058_).m_126124_(Character.valueOf('W'), Ingredient.m_204132_(ItemTags.f_13175_)).m_126130_("GGG").m_126130_("QQQ").m_126130_("WWW").m_126132_("has_quartz", RecipeProvider.m_125977_(Items.f_42692_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_152589_, 4).m_126127_(Character.valueOf('S'), Blocks.f_152555_).m_126130_("SS").m_126130_("SS").m_126132_("has_polished_deepslate", RecipeProvider.m_125977_(Blocks.f_152555_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_152559_, 4).m_126127_(Character.valueOf('S'), Blocks.f_152589_).m_126130_("SS").m_126130_("SS").m_126132_("has_deepslate_bricks", RecipeProvider.m_125977_(Blocks.f_152589_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50031_, 6).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('#'), Blocks.f_50165_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("X X").m_126130_("X#X").m_126130_("XRX").m_126132_("has_rail", RecipeProvider.m_125977_(Blocks.f_50156_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42391_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        RecipeProvider.m_176743_(p_176532_, Items.f_42415_, Items.f_41959_);
        ShapedRecipeBuilder.m_126116_(Items.f_42475_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("X X").m_126130_("X X").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42473_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42472_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("XXX").m_126130_("X X").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42392_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42474_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42390_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42389_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("X").m_126130_("#").m_126130_("#").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42388_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("X").m_126130_("X").m_126130_("#").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50228_, 2).m_126127_(Character.valueOf('Q'), Items.f_42692_).m_126127_(Character.valueOf('C'), Blocks.f_50652_).m_126130_("CQ").m_126130_("QC").m_126132_("has_quartz", RecipeProvider.m_125977_(Items.f_42692_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50061_).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('#'), Blocks.f_50652_).m_126127_(Character.valueOf('X'), Items.f_42411_).m_126130_("###").m_126130_("#X#").m_126130_("#R#").m_126132_("has_bow", RecipeProvider.m_125977_(Items.f_42411_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_152537_).m_126127_(Character.valueOf('#'), Items.f_151087_).m_126130_("##").m_126130_("##").m_126145_("pointed_dripstone").m_126132_("has_pointed_dripstone", RecipeProvider.m_125977_(Items.f_151087_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50286_).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('#'), Blocks.f_50652_).m_126130_("###").m_126130_("# #").m_126130_("#R#").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_176498_(p_176532_);
        RecipeProvider.m_176743_(p_176532_, Items.f_42616_, Items.f_42110_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50201_).m_126127_(Character.valueOf('B'), Items.f_42517_).m_126127_(Character.valueOf('#'), Blocks.f_50080_).m_126127_(Character.valueOf('D'), Items.f_42415_).m_126130_(" B ").m_126130_("D#D").m_126130_("###").m_126132_("has_obsidian", RecipeProvider.m_125977_(Blocks.f_50080_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50265_).m_126127_(Character.valueOf('#'), Blocks.f_50080_).m_126127_(Character.valueOf('E'), Items.f_42545_).m_126130_("###").m_126130_("#E#").m_126130_("###").m_126132_("has_ender_eye", RecipeProvider.m_125977_(Items.f_42545_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42545_).m_126209_(Items.f_42584_).m_126209_(Items.f_42593_).m_126132_("has_blaze_powder", RecipeProvider.m_125977_(Items.f_42593_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50443_, 4).m_126127_(Character.valueOf('#'), Blocks.f_50259_).m_126130_("##").m_126130_("##").m_126132_("has_end_stone", RecipeProvider.m_125977_(Blocks.f_50259_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42729_).m_126127_(Character.valueOf('T'), Items.f_42586_).m_126127_(Character.valueOf('E'), Items.f_42545_).m_126127_(Character.valueOf('G'), Blocks.f_50058_).m_126130_("GGG").m_126130_("GEG").m_126130_("GTG").m_126132_("has_ender_eye", RecipeProvider.m_125977_(Items.f_42545_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50489_, 4).m_126127_(Character.valueOf('#'), Items.f_42731_).m_126127_(Character.valueOf('/'), Items.f_42585_).m_126130_("/").m_126130_("#").m_126132_("has_chorus_fruit_popped", RecipeProvider.m_125977_(Items.f_42731_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42592_).m_126209_(Items.f_42591_).m_126209_(Blocks.f_50072_).m_126209_(Items.f_42501_).m_126132_("has_spider_eye", RecipeProvider.m_125977_(Items.f_42591_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42613_, 3).m_126209_(Items.f_42403_).m_126209_(Items.f_42593_).m_126184_(Ingredient.m_43929_(Items.f_42413_, Items.f_42414_)).m_126132_("has_blaze_powder", RecipeProvider.m_125977_(Items.f_42593_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42688_, 3).m_126209_(Items.f_42403_).m_126209_(Items.f_42516_).m_126132_("has_gunpowder", RecipeProvider.m_125977_(Items.f_42403_)).m_176500_(p_176532_, "firework_rocket_simple");
        ShapedRecipeBuilder.m_126116_(Items.f_42523_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42401_).m_126130_("  #").m_126130_(" #X").m_126130_("# X").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42409_).m_126209_(Items.f_42416_).m_126209_(Items.f_42484_).m_126132_("has_flint", RecipeProvider.m_125977_(Items.f_42484_)).m_126132_("has_obsidian", RecipeProvider.m_125977_(Blocks.f_50080_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50276_).m_126127_(Character.valueOf('#'), Items.f_42460_).m_126130_("# #").m_126130_(" # ").m_126132_("has_brick", RecipeProvider.m_125977_(Items.f_42460_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50094_).m_206416_(Character.valueOf('#'), ItemTags.f_13166_).m_126130_("###").m_126130_("# #").m_126130_("###").m_126132_("has_cobblestone", RecipeProvider.m_206406_(ItemTags.f_13166_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42520_).m_126209_(Blocks.f_50094_).m_126209_(Items.f_42449_).m_126132_("has_minecart", RecipeProvider.m_125977_(Items.f_42449_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42590_, 3).m_126127_(Character.valueOf('#'), Blocks.f_50058_).m_126130_("# #").m_126130_(" # ").m_126132_("has_glass", RecipeProvider.m_125977_(Blocks.f_50058_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50185_, 16).m_126127_(Character.valueOf('#'), Blocks.f_50058_).m_126130_("###").m_126130_("###").m_126132_("has_glass", RecipeProvider.m_125977_(Blocks.f_50058_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50141_).m_126127_(Character.valueOf('#'), Items.f_42525_).m_126130_("##").m_126130_("##").m_126132_("has_glowstone_dust", RecipeProvider.m_125977_(Items.f_42525_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_151063_).m_126209_(Items.f_42617_).m_126209_(Items.f_151056_).m_126132_("has_item_frame", RecipeProvider.m_125977_(Items.f_42617_)).m_126132_("has_glow_ink_sac", RecipeProvider.m_125977_(Items.f_151056_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42436_).m_126127_(Character.valueOf('#'), Items.f_42417_).m_126127_(Character.valueOf('X'), Items.f_42410_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42433_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42479_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("X X").m_126130_("X X").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42677_).m_126127_(Character.valueOf('#'), Items.f_42587_).m_126127_(Character.valueOf('X'), Items.f_42619_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_gold_nugget", RecipeProvider.m_125977_(Items.f_42587_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42477_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42476_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("XXX").m_126130_("X X").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42434_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42478_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42432_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50030_, 6).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("X X").m_126130_("X#X").m_126130_("XRX").m_126132_("has_rail", RecipeProvider.m_125977_(Blocks.f_50156_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42431_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("X").m_126130_("#").m_126130_("#").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42430_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42417_).m_126130_("X").m_126130_("X").m_126130_("#").m_126132_("has_gold_ingot", RecipeProvider.m_125977_(Items.f_42417_)).m_176498_(p_176532_);
        RecipeProvider.m_176616_(p_176532_, Items.f_42417_, Items.f_41912_, "gold_ingot_from_gold_block", "gold_ingot");
        RecipeProvider.m_176562_(p_176532_, Items.f_42587_, Items.f_42417_, "gold_ingot_from_nuggets", "gold_ingot");
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50122_).m_126209_(Blocks.f_50228_).m_126209_(Items.f_42692_).m_126132_("has_quartz", RecipeProvider.m_125977_(Items.f_42692_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42490_, 2).m_126209_(Items.f_42498_).m_126209_(Items.f_42535_).m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_126132_("has_black_dye", RecipeProvider.m_125977_(Items.f_42498_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50335_).m_126127_(Character.valueOf('#'), Items.f_42405_).m_126130_("###").m_126130_("###").m_126130_("###").m_126132_("has_wheat", RecipeProvider.m_125977_(Items.f_42405_)).m_176498_(p_176532_);
        RecipeProvider.m_176690_(p_176532_, Blocks.f_50327_, Items.f_42416_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42787_, 4).m_126209_(Items.f_42788_).m_126211_(Items.f_42590_, 4).m_126132_("has_honey_block", RecipeProvider.m_125977_(Blocks.f_50719_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50719_, 1).m_126127_(Character.valueOf('S'), Items.f_42787_).m_126130_("SS").m_126130_("SS").m_126132_("has_honey_bottle", RecipeProvider.m_125977_(Items.f_42787_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50720_).m_126127_(Character.valueOf('H'), Items.f_42784_).m_126130_("HH").m_126130_("HH").m_126132_("has_honeycomb", RecipeProvider.m_125977_(Items.f_42784_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50332_).m_126127_(Character.valueOf('C'), Blocks.f_50087_).m_126127_(Character.valueOf('I'), Items.f_42416_).m_126130_("I I").m_126130_("ICI").m_126130_(" I ").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42694_).m_126209_(Blocks.f_50332_).m_126209_(Items.f_42449_).m_126132_("has_minecart", RecipeProvider.m_125977_(Items.f_42449_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42386_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50183_, 16).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126130_("###").m_126130_("###").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42471_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("X X").m_126130_("X X").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42469_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        RecipeProvider.m_176670_(Blocks.f_50166_, Ingredient.m_43929_(Items.f_42416_)).m_126132_(RecipeProvider.m_176602_(Items.f_42416_), RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42468_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("XXX").m_126130_("X X").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42387_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        RecipeProvider.m_176616_(p_176532_, Items.f_42416_, Items.f_41913_, "iron_ingot_from_iron_block", "iron_ingot");
        RecipeProvider.m_176562_(p_176532_, Items.f_42749_, Items.f_42416_, "iron_ingot_from_nuggets", "iron_ingot");
        ShapedRecipeBuilder.m_126116_(Items.f_42470_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42385_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42384_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("X").m_126130_("#").m_126130_("#").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42383_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("X").m_126130_("X").m_126130_("#").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50376_).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126130_("##").m_126130_("##").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42617_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42454_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_leather", RecipeProvider.m_125977_(Items.f_42454_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50131_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('X'), Items.f_42415_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_diamond", RecipeProvider.m_125977_(Items.f_42415_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50155_, 3).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126130_("# #").m_126130_("###").m_126130_("# #").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_176498_(p_176532_);
        RecipeProvider.m_176743_(p_176532_, Items.f_42534_, Items.f_41854_);
        ShapedRecipeBuilder.m_126118_(Items.f_42655_, 2).m_126127_(Character.valueOf('~'), Items.f_42401_).m_126127_(Character.valueOf('O'), Items.f_42518_).m_126130_("~~ ").m_126130_("~O ").m_126130_("  ~").m_126132_("has_slime_ball", RecipeProvider.m_125977_(Items.f_42518_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42454_).m_126127_(Character.valueOf('#'), Items.f_42649_).m_126130_("##").m_126130_("##").m_126132_("has_rabbit_hide", RecipeProvider.m_125977_(Items.f_42649_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42463_).m_126127_(Character.valueOf('X'), Items.f_42454_).m_126130_("X X").m_126130_("X X").m_126132_("has_leather", RecipeProvider.m_125977_(Items.f_42454_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42408_).m_126127_(Character.valueOf('X'), Items.f_42454_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_126132_("has_leather", RecipeProvider.m_125977_(Items.f_42454_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42407_).m_126127_(Character.valueOf('X'), Items.f_42454_).m_126130_("XXX").m_126130_("X X").m_126132_("has_leather", RecipeProvider.m_125977_(Items.f_42454_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42462_).m_126127_(Character.valueOf('X'), Items.f_42454_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_126132_("has_leather", RecipeProvider.m_125977_(Items.f_42454_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42654_).m_126127_(Character.valueOf('X'), Items.f_42454_).m_126130_("X X").m_126130_("XXX").m_126130_("X X").m_126132_("has_leather", RecipeProvider.m_125977_(Items.f_42454_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50624_).m_206416_(Character.valueOf('S'), ItemTags.f_13175_).m_126127_(Character.valueOf('B'), Blocks.f_50078_).m_126130_("SSS").m_126130_(" B ").m_126130_(" S ").m_126132_("has_book", RecipeProvider.m_125977_(Items.f_42517_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50164_).m_126127_(Character.valueOf('#'), Blocks.f_50652_).m_126127_(Character.valueOf('X'), Items.f_42398_).m_126130_("X").m_126130_("#").m_126132_("has_cobblestone", RecipeProvider.m_125977_(Blocks.f_50652_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42538_, Blocks.f_50113_, "light_blue_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42538_, 2).m_126209_(Items.f_42494_).m_126209_(Items.f_42535_).m_126145_("light_blue_dye").m_126132_("has_blue_dye", RecipeProvider.m_125977_(Items.f_42494_)).m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_176500_(p_176532_, "light_blue_dye_from_blue_white_dye");
        RecipeProvider.m_176551_(p_176532_, Items.f_42491_, Blocks.f_50115_, "light_gray_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42491_, 2).m_126209_(Items.f_42490_).m_126209_(Items.f_42535_).m_126145_("light_gray_dye").m_126132_("has_gray_dye", RecipeProvider.m_125977_(Items.f_42490_)).m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_176500_(p_176532_, "light_gray_dye_from_gray_white_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42491_, 3).m_126209_(Items.f_42498_).m_126211_(Items.f_42535_, 2).m_126145_("light_gray_dye").m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_126132_("has_black_dye", RecipeProvider.m_125977_(Items.f_42498_)).m_176500_(p_176532_, "light_gray_dye_from_black_white_dye");
        RecipeProvider.m_176551_(p_176532_, Items.f_42491_, Blocks.f_50120_, "light_gray_dye");
        RecipeProvider.m_176551_(p_176532_, Items.f_42491_, Blocks.f_50118_, "light_gray_dye");
        RecipeProvider.m_176690_(p_176532_, Blocks.f_50326_, Items.f_42417_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_152587_).m_126127_(Character.valueOf('#'), Items.f_151052_).m_126130_("#").m_126130_("#").m_126130_("#").m_126132_("has_copper_ingot", RecipeProvider.m_125977_(Items.f_151052_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42540_, 2).m_126209_(Items.f_42496_).m_126209_(Items.f_42535_).m_126132_("has_green_dye", RecipeProvider.m_125977_(Items.f_42496_)).m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50144_).m_126127_(Character.valueOf('A'), Blocks.f_50143_).m_126127_(Character.valueOf('B'), Blocks.f_50081_).m_126130_("A").m_126130_("B").m_126132_("has_carved_pumpkin", RecipeProvider.m_125977_(Blocks.f_50143_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42537_, Blocks.f_50114_, "magenta_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42537_, 4).m_126209_(Items.f_42494_).m_126211_(Items.f_42497_, 2).m_126209_(Items.f_42535_).m_126145_("magenta_dye").m_126132_("has_blue_dye", RecipeProvider.m_125977_(Items.f_42494_)).m_126132_("has_rose_red", RecipeProvider.m_125977_(Items.f_42497_)).m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_176500_(p_176532_, "magenta_dye_from_blue_red_white_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42537_, 3).m_126209_(Items.f_42494_).m_126209_(Items.f_42497_).m_126209_(Items.f_42489_).m_126145_("magenta_dye").m_126132_("has_pink_dye", RecipeProvider.m_125977_(Items.f_42489_)).m_126132_("has_blue_dye", RecipeProvider.m_125977_(Items.f_42494_)).m_126132_("has_red_dye", RecipeProvider.m_125977_(Items.f_42497_)).m_176500_(p_176532_, "magenta_dye_from_blue_red_pink");
        RecipeProvider.m_176556_(p_176532_, Items.f_42537_, Blocks.f_50356_, "magenta_dye", 2);
        ShapelessRecipeBuilder.m_126191_(Items.f_42537_, 2).m_126209_(Items.f_42493_).m_126209_(Items.f_42489_).m_126145_("magenta_dye").m_126132_("has_pink_dye", RecipeProvider.m_125977_(Items.f_42489_)).m_126132_("has_purple_dye", RecipeProvider.m_125977_(Items.f_42493_)).m_176500_(p_176532_, "magenta_dye_from_purple_and_pink");
        ShapedRecipeBuilder.m_126116_(Blocks.f_50450_).m_126127_(Character.valueOf('#'), Items.f_42542_).m_126130_("##").m_126130_("##").m_126132_("has_magma_cream", RecipeProvider.m_125977_(Items.f_42542_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42542_).m_126209_(Items.f_42593_).m_126209_(Items.f_42518_).m_126132_("has_blaze_powder", RecipeProvider.m_125977_(Items.f_42593_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42676_).m_126127_(Character.valueOf('#'), Items.f_42516_).m_126127_(Character.valueOf('X'), Items.f_42522_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_compass", RecipeProvider.m_125977_(Items.f_42522_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50186_).m_126127_(Character.valueOf('M'), Items.f_42575_).m_126130_("MMM").m_126130_("MMM").m_126130_("MMM").m_126132_("has_melon", RecipeProvider.m_125977_(Items.f_42575_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42578_).m_126209_(Items.f_42575_).m_126132_("has_melon", RecipeProvider.m_125977_(Items.f_42575_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42449_).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126130_("# #").m_126130_("###").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50079_).m_126209_(Blocks.f_50652_).m_126209_(Blocks.f_50191_).m_126145_("mossy_cobblestone").m_126132_("has_vine", RecipeProvider.m_125977_(Blocks.f_50191_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Blocks.f_50079_, Blocks.f_50191_));
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50223_).m_126209_(Blocks.f_50222_).m_126209_(Blocks.f_50191_).m_126145_("mossy_stone_bricks").m_126132_("has_vine", RecipeProvider.m_125977_(Blocks.f_50191_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Blocks.f_50223_, Blocks.f_50191_));
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50079_).m_126209_(Blocks.f_50652_).m_126209_(Blocks.f_152544_).m_126145_("mossy_cobblestone").m_126132_("has_moss_block", RecipeProvider.m_125977_(Blocks.f_152544_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Blocks.f_50079_, Blocks.f_152544_));
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50223_).m_126209_(Blocks.f_50222_).m_126209_(Blocks.f_152544_).m_126145_("mossy_stone_bricks").m_126132_("has_moss_block", RecipeProvider.m_125977_(Blocks.f_152544_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Blocks.f_50223_, Blocks.f_152544_));
        ShapelessRecipeBuilder.m_126189_(Items.f_42400_).m_126209_(Blocks.f_50072_).m_126209_(Blocks.f_50073_).m_126209_(Items.f_42399_).m_126132_("has_mushroom_stew", RecipeProvider.m_125977_(Items.f_42400_)).m_126132_("has_bowl", RecipeProvider.m_125977_(Items.f_42399_)).m_126132_("has_brown_mushroom", RecipeProvider.m_125977_(Blocks.f_50072_)).m_126132_("has_red_mushroom", RecipeProvider.m_125977_(Blocks.f_50073_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50197_).m_126127_(Character.valueOf('N'), Items.f_42691_).m_126130_("NN").m_126130_("NN").m_126132_("has_netherbrick", RecipeProvider.m_125977_(Items.f_42691_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50451_).m_126127_(Character.valueOf('#'), Items.f_42588_).m_126130_("###").m_126130_("###").m_126130_("###").m_126132_("has_nether_wart", RecipeProvider.m_125977_(Items.f_42588_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50065_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('X'), Items.f_42451_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50455_).m_126127_(Character.valueOf('Q'), Items.f_42692_).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('#'), Blocks.f_50652_).m_126130_("###").m_126130_("RRQ").m_126130_("###").m_126132_("has_quartz", RecipeProvider.m_125977_(Items.f_42692_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42536_, Blocks.f_50117_, "orange_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42536_, 2).m_126209_(Items.f_42497_).m_126209_(Items.f_42539_).m_126145_("orange_dye").m_126132_("has_red_dye", RecipeProvider.m_125977_(Items.f_42497_)).m_126132_("has_yellow_dye", RecipeProvider.m_125977_(Items.f_42539_)).m_176500_(p_176532_, "orange_dye_from_red_yellow");
        ShapedRecipeBuilder.m_126116_(Items.f_42487_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126124_(Character.valueOf('X'), Ingredient.m_204132_(ItemTags.f_13167_)).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_wool", RecipeProvider.m_206406_(ItemTags.f_13167_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42516_, 3).m_126127_(Character.valueOf('#'), Blocks.f_50130_).m_126130_("###").m_126132_("has_reeds", RecipeProvider.m_125977_(Blocks.f_50130_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50283_, 2).m_126127_(Character.valueOf('#'), Blocks.f_50333_).m_126130_("#").m_126130_("#").m_126132_("has_chiseled_quartz_block", RecipeProvider.m_125977_(Blocks.f_50282_)).m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_126132_("has_quartz_pillar", RecipeProvider.m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50354_).m_126211_(Blocks.f_50126_, 9).m_126132_("has_ice", RecipeProvider.m_125977_(Blocks.f_50126_)).m_176498_(p_176532_);
        RecipeProvider.m_176556_(p_176532_, Items.f_42489_, Blocks.f_50358_, "pink_dye", 2);
        RecipeProvider.m_176551_(p_176532_, Items.f_42489_, Blocks.f_50119_, "pink_dye");
        ShapelessRecipeBuilder.m_126191_(Items.f_42489_, 2).m_126209_(Items.f_42497_).m_126209_(Items.f_42535_).m_126145_("pink_dye").m_126132_("has_white_dye", RecipeProvider.m_125977_(Items.f_42535_)).m_126132_("has_red_dye", RecipeProvider.m_125977_(Items.f_42497_)).m_176500_(p_176532_, "pink_dye_from_red_white_dye");
        ShapedRecipeBuilder.m_126116_(Blocks.f_50039_).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('#'), Blocks.f_50652_).m_206416_(Character.valueOf('T'), ItemTags.f_13168_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("TTT").m_126130_("#X#").m_126130_("#R#").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_176498_(p_176532_);
        RecipeProvider.m_176640_(p_176532_, Blocks.f_50138_, Blocks.f_50137_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50377_).m_126127_(Character.valueOf('S'), Items.f_42695_).m_126130_("SS").m_126130_("SS").m_126132_("has_prismarine_shard", RecipeProvider.m_125977_(Items.f_42695_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50378_).m_126127_(Character.valueOf('S'), Items.f_42695_).m_126130_("SSS").m_126130_("SSS").m_126130_("SSS").m_126132_("has_prismarine_shard", RecipeProvider.m_125977_(Items.f_42695_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42687_).m_126209_(Blocks.f_50133_).m_126209_(Items.f_42501_).m_126209_(Items.f_42521_).m_126132_("has_carved_pumpkin", RecipeProvider.m_125977_(Blocks.f_50143_)).m_126132_("has_pumpkin", RecipeProvider.m_125977_(Blocks.f_50133_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42577_, 4).m_126209_(Blocks.f_50133_).m_126132_("has_pumpkin", RecipeProvider.m_125977_(Blocks.f_50133_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42493_, 2).m_126209_(Items.f_42494_).m_126209_(Items.f_42497_).m_126132_("has_blue_dye", RecipeProvider.m_125977_(Items.f_42494_)).m_126132_("has_red_dye", RecipeProvider.m_125977_(Items.f_42497_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50456_).m_126127_(Character.valueOf('#'), Blocks.f_50087_).m_126127_(Character.valueOf('-'), Items.f_42748_).m_126130_("-").m_126130_("#").m_126130_("-").m_126132_("has_shulker_shell", RecipeProvider.m_125977_(Items.f_42748_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50492_, 4).m_126127_(Character.valueOf('F'), Items.f_42731_).m_126130_("FF").m_126130_("FF").m_126132_("has_chorus_fruit_popped", RecipeProvider.m_125977_(Items.f_42731_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50441_).m_126127_(Character.valueOf('#'), Blocks.f_50469_).m_126130_("#").m_126130_("#").m_126132_("has_purpur_block", RecipeProvider.m_125977_(Blocks.f_50492_)).m_176498_(p_176532_);
        RecipeProvider.m_176704_(Blocks.f_50469_, Ingredient.m_43929_(Blocks.f_50492_, Blocks.f_50441_)).m_126132_("has_purpur_block", RecipeProvider.m_125977_(Blocks.f_50492_)).m_176498_(p_176532_);
        RecipeProvider.m_176710_(Blocks.f_50442_, Ingredient.m_43929_(Blocks.f_50492_, Blocks.f_50441_)).m_126132_("has_purpur_block", RecipeProvider.m_125977_(Blocks.f_50492_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50333_).m_126127_(Character.valueOf('#'), Items.f_42692_).m_126130_("##").m_126130_("##").m_126132_("has_quartz", RecipeProvider.m_125977_(Items.f_42692_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50714_, 4).m_126127_(Character.valueOf('#'), Blocks.f_50333_).m_126130_("##").m_126130_("##").m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_176498_(p_176532_);
        RecipeProvider.m_176704_(Blocks.f_50413_, Ingredient.m_43929_(Blocks.f_50282_, Blocks.f_50333_, Blocks.f_50283_)).m_126132_("has_chiseled_quartz_block", RecipeProvider.m_125977_(Blocks.f_50282_)).m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_126132_("has_quartz_pillar", RecipeProvider.m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
        RecipeProvider.m_176710_(Blocks.f_50284_, Ingredient.m_43929_(Blocks.f_50282_, Blocks.f_50333_, Blocks.f_50283_)).m_126132_("has_chiseled_quartz_block", RecipeProvider.m_125977_(Blocks.f_50282_)).m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_126132_("has_quartz_pillar", RecipeProvider.m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42699_).m_126209_(Items.f_42674_).m_126209_(Items.f_42698_).m_126209_(Items.f_42399_).m_126209_(Items.f_42619_).m_126209_(Blocks.f_50072_).m_126145_("rabbit_stew").m_126132_("has_cooked_rabbit", RecipeProvider.m_125977_(Items.f_42698_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Items.f_42699_, Items.f_41952_));
        ShapelessRecipeBuilder.m_126189_(Items.f_42699_).m_126209_(Items.f_42674_).m_126209_(Items.f_42698_).m_126209_(Items.f_42399_).m_126209_(Items.f_42619_).m_126209_(Blocks.f_50073_).m_126145_("rabbit_stew").m_126132_("has_cooked_rabbit", RecipeProvider.m_125977_(Items.f_42698_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Items.f_42699_, Items.f_41953_));
        ShapedRecipeBuilder.m_126118_(Blocks.f_50156_, 16).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42416_).m_126130_("X X").m_126130_("X#X").m_126130_("X X").m_126132_("has_minecart", RecipeProvider.m_125977_(Items.f_42449_)).m_176498_(p_176532_);
        RecipeProvider.m_176743_(p_176532_, Items.f_42451_, Items.f_42153_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50261_).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126127_(Character.valueOf('G'), Blocks.f_50141_).m_126130_(" R ").m_126130_("RGR").m_126130_(" R ").m_126132_("has_glowstone", RecipeProvider.m_125977_(Blocks.f_50141_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50174_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126127_(Character.valueOf('X'), Items.f_42451_).m_126130_("X").m_126130_("#").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42497_, Items.f_42732_, "red_dye");
        RecipeProvider.m_176551_(p_176532_, Items.f_42497_, Blocks.f_50112_, "red_dye");
        RecipeProvider.m_176556_(p_176532_, Items.f_42497_, Blocks.f_50357_, "red_dye", 2);
        ShapelessRecipeBuilder.m_126189_(Items.f_42497_).m_126209_(Blocks.f_50116_).m_126145_("red_dye").m_126132_("has_red_flower", RecipeProvider.m_125977_(Blocks.f_50116_)).m_176500_(p_176532_, "red_dye_from_tulip");
        ShapedRecipeBuilder.m_126116_(Blocks.f_50452_).m_126127_(Character.valueOf('W'), Items.f_42588_).m_126127_(Character.valueOf('N'), Items.f_42691_).m_126130_("NW").m_126130_("WN").m_126132_("has_nether_wart", RecipeProvider.m_125977_(Items.f_42588_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50394_).m_126127_(Character.valueOf('#'), Blocks.f_49993_).m_126130_("##").m_126130_("##").m_126132_("has_sand", RecipeProvider.m_125977_(Blocks.f_49993_)).m_176498_(p_176532_);
        RecipeProvider.m_176704_(Blocks.f_50467_, Ingredient.m_43929_(Blocks.f_50394_, Blocks.f_50395_)).m_126132_("has_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50394_)).m_126132_("has_chiseled_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50395_)).m_176498_(p_176532_);
        RecipeProvider.m_176710_(Blocks.f_50397_, Ingredient.m_43929_(Blocks.f_50394_, Blocks.f_50395_, Blocks.f_50396_)).m_126132_("has_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50394_)).m_126132_("has_chiseled_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50395_)).m_126132_("has_cut_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50396_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50146_).m_126127_(Character.valueOf('#'), Blocks.f_50174_).m_126127_(Character.valueOf('X'), Items.f_42451_).m_126127_(Character.valueOf('I'), Blocks.f_50069_).m_126130_("#X#").m_126130_("III").m_126132_("has_redstone_torch", RecipeProvider.m_125977_(Blocks.f_50174_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50062_).m_126127_(Character.valueOf('#'), Blocks.f_49992_).m_126130_("##").m_126130_("##").m_126132_("has_sand", RecipeProvider.m_125977_(Blocks.f_49992_)).m_176498_(p_176532_);
        RecipeProvider.m_176704_(Blocks.f_50406_, Ingredient.m_43929_(Blocks.f_50062_, Blocks.f_50063_)).m_126132_("has_sandstone", RecipeProvider.m_125977_(Blocks.f_50062_)).m_126132_("has_chiseled_sandstone", RecipeProvider.m_125977_(Blocks.f_50063_)).m_176498_(p_176532_);
        RecipeProvider.m_176710_(Blocks.f_50263_, Ingredient.m_43929_(Blocks.f_50062_, Blocks.f_50063_, Blocks.f_50064_)).m_126132_("has_sandstone", RecipeProvider.m_125977_(Blocks.f_50062_)).m_126132_("has_chiseled_sandstone", RecipeProvider.m_125977_(Blocks.f_50063_)).m_126132_("has_cut_sandstone", RecipeProvider.m_125977_(Blocks.f_50064_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50386_).m_126127_(Character.valueOf('S'), Items.f_42695_).m_126127_(Character.valueOf('C'), Items.f_42696_).m_126130_("SCS").m_126130_("CCC").m_126130_("SCS").m_126132_("has_prismarine_crystals", RecipeProvider.m_125977_(Items.f_42696_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42574_).m_126127_(Character.valueOf('#'), Items.f_42416_).m_126130_(" #").m_126130_("# ").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42740_).m_206416_(Character.valueOf('W'), ItemTags.f_13168_).m_126127_(Character.valueOf('o'), Items.f_42416_).m_126130_("WoW").m_126130_("WWW").m_126130_(" W ").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        RecipeProvider.m_176743_(p_176532_, Items.f_42518_, Items.f_42204_);
        RecipeProvider.m_176652_(p_176532_, Blocks.f_50396_, Blocks.f_50394_);
        RecipeProvider.m_176652_(p_176532_, Blocks.f_50064_, Blocks.f_50062_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50127_).m_126127_(Character.valueOf('#'), Items.f_42452_).m_126130_("##").m_126130_("##").m_126132_("has_snowball", RecipeProvider.m_125977_(Items.f_42452_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50125_, 6).m_126127_(Character.valueOf('#'), Blocks.f_50127_).m_126130_("###").m_126132_("has_snowball", RecipeProvider.m_125977_(Items.f_42452_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50684_).m_206416_(Character.valueOf('L'), ItemTags.f_13182_).m_126127_(Character.valueOf('S'), Items.f_42398_).m_206416_(Character.valueOf('#'), ItemTags.f_13154_).m_126130_(" S ").m_126130_("S#S").m_126130_("LLL").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_126132_("has_soul_sand", RecipeProvider.m_206406_(ItemTags.f_13154_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42546_).m_126127_(Character.valueOf('#'), Items.f_42587_).m_126127_(Character.valueOf('X'), Items.f_42575_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_melon", RecipeProvider.m_125977_(Items.f_42575_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42737_, 2).m_126127_(Character.valueOf('#'), Items.f_42525_).m_126127_(Character.valueOf('X'), Items.f_42412_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_126132_("has_glowstone_dust", RecipeProvider.m_125977_(Items.f_42525_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_151059_).m_126127_(Character.valueOf('#'), Items.f_151049_).m_126127_(Character.valueOf('X'), Items.f_151052_).m_126130_(" # ").m_126130_(" X ").m_126130_(" X ").m_126132_("has_amethyst_shard", RecipeProvider.m_125977_(Items.f_151049_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42398_, 4).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126130_("#").m_126130_("#").m_126145_("sticks").m_126132_("has_planks", RecipeProvider.m_206406_(ItemTags.f_13168_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Items.f_42398_, 1).m_126127_(Character.valueOf('#'), Blocks.f_50571_).m_126130_("#").m_126130_("#").m_126145_("sticks").m_126132_("has_bamboo", RecipeProvider.m_125977_(Blocks.f_50571_)).m_176500_(p_176532_, "stick_from_bamboo_item");
        ShapedRecipeBuilder.m_126116_(Blocks.f_50032_).m_126127_(Character.valueOf('P'), Blocks.f_50039_).m_126127_(Character.valueOf('S'), Items.f_42518_).m_126130_("S").m_126130_("P").m_126132_("has_slime_ball", RecipeProvider.m_125977_(Items.f_42518_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50222_, 4).m_126127_(Character.valueOf('#'), Blocks.f_50069_).m_126130_("##").m_126130_("##").m_126132_("has_stone", RecipeProvider.m_125977_(Blocks.f_50069_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42428_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13165_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_126132_("has_cobblestone", RecipeProvider.m_206406_(ItemTags.f_13165_)).m_176498_(p_176532_);
        RecipeProvider.m_176704_(Blocks.f_50411_, Ingredient.m_43929_(Blocks.f_50222_)).m_126132_("has_stone_bricks", RecipeProvider.m_206406_(ItemTags.f_13169_)).m_176498_(p_176532_);
        RecipeProvider.m_176710_(Blocks.f_50194_, Ingredient.m_43929_(Blocks.f_50222_)).m_126132_("has_stone_bricks", RecipeProvider.m_206406_(ItemTags.f_13169_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42429_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13165_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_126132_("has_cobblestone", RecipeProvider.m_206406_(ItemTags.f_13165_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42427_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13165_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_126132_("has_cobblestone", RecipeProvider.m_206406_(ItemTags.f_13165_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42426_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13165_).m_126130_("X").m_126130_("#").m_126130_("#").m_126132_("has_cobblestone", RecipeProvider.m_206406_(ItemTags.f_13165_)).m_176498_(p_176532_);
        RecipeProvider.m_176700_(p_176532_, Blocks.f_50405_, Blocks.f_50470_);
        ShapedRecipeBuilder.m_126116_(Items.f_42425_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13165_).m_126130_("X").m_126130_("X").m_126130_("#").m_126132_("has_cobblestone", RecipeProvider.m_206406_(ItemTags.f_13165_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50041_).m_126127_(Character.valueOf('#'), Items.f_42401_).m_126130_("##").m_126130_("##").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Blocks.f_50041_, Items.f_42401_));
        RecipeProvider.m_176551_(p_176532_, Items.f_42501_, Blocks.f_50130_, "sugar");
        ShapelessRecipeBuilder.m_126191_(Items.f_42501_, 3).m_126209_(Items.f_42787_).m_126145_("sugar").m_126132_("has_honey_bottle", RecipeProvider.m_125977_(Items.f_42787_)).m_176500_(p_176532_, RecipeProvider.m_176517_(Items.f_42501_, Items.f_42787_));
        ShapedRecipeBuilder.m_126116_(Blocks.f_50716_).m_126127_(Character.valueOf('H'), Items.f_42129_).m_126127_(Character.valueOf('R'), Items.f_42451_).m_126130_(" R ").m_126130_("RHR").m_126130_(" R ").m_126132_("has_redstone", RecipeProvider.m_125977_(Items.f_42451_)).m_126132_("has_hay_block", RecipeProvider.m_125977_(Blocks.f_50335_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50077_).m_126124_(Character.valueOf('#'), Ingredient.m_43929_(Blocks.f_49992_, Blocks.f_49993_)).m_126127_(Character.valueOf('X'), Items.f_42403_).m_126130_("X#X").m_126130_("#X#").m_126130_("X#X").m_126132_("has_gunpowder", RecipeProvider.m_125977_(Items.f_42403_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42693_).m_126209_(Blocks.f_50077_).m_126209_(Items.f_42449_).m_126132_("has_minecart", RecipeProvider.m_125977_(Items.f_42449_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50081_, 4).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126124_(Character.valueOf('X'), Ingredient.m_43929_(Items.f_42413_, Items.f_42414_)).m_126130_("X").m_126130_("#").m_126132_("has_stone_pickaxe", RecipeProvider.m_125977_(Items.f_42427_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50139_, 4).m_126124_(Character.valueOf('X'), Ingredient.m_43929_(Items.f_42413_, Items.f_42414_)).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('S'), ItemTags.f_13154_).m_126130_("X").m_126130_("#").m_126130_("S").m_126132_("has_soul_sand", RecipeProvider.m_206406_(ItemTags.f_13154_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50681_).m_126127_(Character.valueOf('#'), Items.f_42000_).m_126127_(Character.valueOf('X'), Items.f_42749_).m_126130_("XXX").m_126130_("X#X").m_126130_("XXX").m_126132_("has_iron_nugget", RecipeProvider.m_125977_(Items.f_42749_)).m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50682_).m_126127_(Character.valueOf('#'), Items.f_42053_).m_126127_(Character.valueOf('X'), Items.f_42749_).m_126130_("XXX").m_126130_("X#X").m_126130_("XXX").m_126132_("has_soul_torch", RecipeProvider.m_125977_(Items.f_42053_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Blocks.f_50325_).m_126209_(Blocks.f_50087_).m_126209_(Blocks.f_50266_).m_126132_("has_tripwire_hook", RecipeProvider.m_125977_(Blocks.f_50266_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50266_, 2).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('S'), Items.f_42398_).m_126127_(Character.valueOf('I'), Items.f_42416_).m_126130_("I").m_126130_("S").m_126130_("#").m_126132_("has_string", RecipeProvider.m_125977_(Items.f_42401_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42354_).m_126127_(Character.valueOf('X'), Items.f_42355_).m_126130_("XXX").m_126130_("X X").m_126132_("has_scute", RecipeProvider.m_125977_(Items.f_42355_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126191_(Items.f_42405_, 9).m_126209_(Blocks.f_50335_).m_126132_("has_hay_block", RecipeProvider.m_125977_(Blocks.f_50335_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42535_).m_126209_(Items.f_42499_).m_126145_("white_dye").m_126132_("has_bone_meal", RecipeProvider.m_125977_(Items.f_42499_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42535_, Blocks.f_50071_, "white_dye");
        ShapedRecipeBuilder.m_126116_(Items.f_42423_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13168_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42424_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13168_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42422_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13168_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42421_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13168_).m_126130_("X").m_126130_("#").m_126130_("#").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_42420_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_206416_(Character.valueOf('X'), ItemTags.f_13168_).m_126130_("X").m_126130_("X").m_126130_("#").m_126132_("has_stick", RecipeProvider.m_125977_(Items.f_42398_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42614_).m_126209_(Items.f_42517_).m_126209_(Items.f_42532_).m_126209_(Items.f_42402_).m_126132_("has_book", RecipeProvider.m_125977_(Items.f_42517_)).m_176498_(p_176532_);
        RecipeProvider.m_176551_(p_176532_, Items.f_42539_, Blocks.f_50111_, "yellow_dye");
        RecipeProvider.m_176556_(p_176532_, Items.f_42539_, Blocks.f_50355_, "yellow_dye", 2);
        RecipeProvider.m_176743_(p_176532_, Items.f_42576_, Items.f_42515_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50569_).m_126127_(Character.valueOf('#'), Items.f_42715_).m_126127_(Character.valueOf('X'), Items.f_42716_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126132_("has_nautilus_core", RecipeProvider.m_125977_(Items.f_42716_)).m_126132_("has_nautilus_shell", RecipeProvider.m_125977_(Items.f_42715_)).m_176498_(p_176532_);
        RecipeProvider.m_176612_(p_176532_, Blocks.f_50606_, Blocks.f_50394_);
        RecipeProvider.m_176612_(p_176532_, Blocks.f_50609_, Blocks.f_50222_);
        RecipeProvider.m_176612_(p_176532_, Blocks.f_50613_, Blocks.f_50062_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42721_).m_126209_(Items.f_42516_).m_126209_(Items.f_42682_).m_126132_("has_creeper_head", RecipeProvider.m_125977_(Items.f_42682_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42722_).m_126209_(Items.f_42516_).m_126209_(Items.f_42679_).m_126132_("has_wither_skeleton_skull", RecipeProvider.m_125977_(Items.f_42679_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42720_).m_126209_(Items.f_42516_).m_126209_(Blocks.f_50120_).m_126132_("has_oxeye_daisy", RecipeProvider.m_125977_(Blocks.f_50120_)).m_176498_(p_176532_);
        ShapelessRecipeBuilder.m_126189_(Items.f_42723_).m_126209_(Items.f_42516_).m_126209_(Items.f_42437_).m_126132_("has_enchanted_golden_apple", RecipeProvider.m_125977_(Items.f_42437_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_50616_, 6).m_126127_(Character.valueOf('~'), Items.f_42401_).m_126127_(Character.valueOf('I'), Blocks.f_50571_).m_126130_("I~I").m_126130_("I I").m_126130_("I I").m_126132_("has_bamboo", RecipeProvider.m_125977_(Blocks.f_50571_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50623_).m_126127_(Character.valueOf('I'), Items.f_42398_).m_126127_(Character.valueOf('-'), Blocks.f_50404_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126130_("I-I").m_126130_("# #").m_126132_("has_stone_slab", RecipeProvider.m_125977_(Blocks.f_50404_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50620_).m_126127_(Character.valueOf('#'), Blocks.f_50470_).m_126127_(Character.valueOf('X'), Blocks.f_50094_).m_126127_(Character.valueOf('I'), Items.f_42416_).m_126130_("III").m_126130_("IXI").m_126130_("###").m_126132_("has_smooth_stone", RecipeProvider.m_125977_(Blocks.f_50470_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50619_).m_206416_(Character.valueOf('#'), ItemTags.f_13182_).m_126127_(Character.valueOf('X'), Blocks.f_50094_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_126132_("has_furnace", RecipeProvider.m_125977_(Blocks.f_50094_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50621_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('@'), Items.f_42516_).m_126130_("@@").m_126130_("##").m_126130_("##").m_126132_("has_paper", RecipeProvider.m_125977_(Items.f_42516_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50625_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('@'), Items.f_42416_).m_126130_("@@").m_126130_("##").m_126130_("##").m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50622_).m_206416_(Character.valueOf('#'), ItemTags.f_13168_).m_126127_(Character.valueOf('@'), Items.f_42484_).m_126130_("@@").m_126130_("##").m_126130_("##").m_126132_("has_flint", RecipeProvider.m_125977_(Items.f_42484_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50679_).m_126127_(Character.valueOf('I'), Items.f_42416_).m_126127_(Character.valueOf('#'), Blocks.f_50069_).m_126130_(" I ").m_126130_("###").m_126132_("has_stone", RecipeProvider.m_125977_(Blocks.f_50069_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50729_).m_126127_(Character.valueOf('S'), Items.f_42021_).m_126127_(Character.valueOf('#'), Items.f_42418_).m_126130_("SSS").m_126130_("S#S").m_126130_("SSS").m_126132_("has_netherite_ingot", RecipeProvider.m_125977_(Items.f_42418_)).m_176498_(p_176532_);
        RecipeProvider.m_176616_(p_176532_, Items.f_42418_, Items.f_42791_, "netherite_ingot_from_netherite_block", "netherite_ingot");
        ShapelessRecipeBuilder.m_126189_(Items.f_42418_).m_126211_(Items.f_42419_, 4).m_126211_(Items.f_42417_, 4).m_126145_("netherite_ingot").m_126132_("has_netherite_scrap", RecipeProvider.m_125977_(Items.f_42419_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50724_).m_126127_(Character.valueOf('O'), Blocks.f_50723_).m_126127_(Character.valueOf('G'), Blocks.f_50141_).m_126130_("OOO").m_126130_("GGG").m_126130_("OOO").m_126132_("has_obsidian", RecipeProvider.m_125977_(Blocks.f_50723_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_50184_).m_126127_(Character.valueOf('I'), Items.f_42416_).m_126127_(Character.valueOf('N'), Items.f_42749_).m_126130_("N").m_126130_("I").m_126130_("N").m_126132_("has_iron_nugget", RecipeProvider.m_125977_(Items.f_42749_)).m_126132_("has_iron_ingot", RecipeProvider.m_125977_(Items.f_42416_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126118_(Blocks.f_152498_, 2).m_126127_(Character.valueOf('G'), Blocks.f_50058_).m_126127_(Character.valueOf('S'), Items.f_151049_).m_126130_(" S ").m_126130_("SGS").m_126130_(" S ").m_126132_("has_amethyst_shard", RecipeProvider.m_125977_(Items.f_151049_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Blocks.f_152490_).m_126127_(Character.valueOf('S'), Items.f_151049_).m_126130_("SS").m_126130_("SS").m_126132_("has_amethyst_shard", RecipeProvider.m_125977_(Items.f_151049_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_220211_).m_126127_(Character.valueOf('C'), Items.f_42522_).m_126127_(Character.valueOf('S'), Items.f_220224_).m_126130_("SSS").m_126130_("SCS").m_126130_("SSS").m_126132_("has_echo_shard", RecipeProvider.m_125977_(Items.f_220224_)).m_176498_(p_176532_);
        ShapedRecipeBuilder.m_126116_(Items.f_220217_).m_126127_(Character.valueOf('S'), Items.f_220218_).m_126130_("SSS").m_126130_("SSS").m_126130_("SSS").m_126132_("has_disc_fragment_5", RecipeProvider.m_125977_(Items.f_220218_)).m_176498_(p_176532_);
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44078_).m_126359_(p_176532_, "armor_dye");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44086_).m_126359_(p_176532_, "banner_duplicate");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44079_).m_126359_(p_176532_, "book_cloning");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44082_).m_126359_(p_176532_, "firework_rocket");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44083_).m_126359_(p_176532_, "firework_star");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44084_).m_126359_(p_176532_, "firework_star_fade");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44080_).m_126359_(p_176532_, "map_cloning");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44081_).m_126359_(p_176532_, "map_extending");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44090_).m_126359_(p_176532_, "repair_item");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44087_).m_126359_(p_176532_, "shield_decoration");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44088_).m_126359_(p_176532_, "shulker_box_coloring");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44085_).m_126359_(p_176532_, "tipped_arrow");
        SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44089_).m_126359_(p_176532_, "suspicious_stew");
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42620_), Items.f_42674_, 0.35f, 200).m_126132_("has_potato", RecipeProvider.m_125977_(Items.f_42620_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42461_), Items.f_42460_, 0.3f, 200).m_126132_("has_clay_ball", RecipeProvider.m_125977_(Items.f_42461_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_204132_(ItemTags.f_13181_), Items.f_42414_, 0.15f, 200).m_126132_("has_log", RecipeProvider.m_206406_(ItemTags.f_13181_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42730_), Items.f_42731_, 0.1f, 200).m_126132_("has_chorus_fruit", RecipeProvider.m_125977_(Items.f_42730_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42579_), Items.f_42580_, 0.35f, 200).m_126132_("has_beef", RecipeProvider.m_125977_(Items.f_42579_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42581_), Items.f_42582_, 0.35f, 200).m_126132_("has_chicken", RecipeProvider.m_125977_(Items.f_42581_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42526_), Items.f_42530_, 0.35f, 200).m_126132_("has_cod", RecipeProvider.m_125977_(Items.f_42526_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50575_), Items.f_42576_, 0.1f, 200).m_126132_("has_kelp", RecipeProvider.m_125977_(Blocks.f_50575_)).m_176500_(p_176532_, RecipeProvider.m_176656_(Items.f_42576_));
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42527_), Items.f_42531_, 0.35f, 200).m_126132_("has_salmon", RecipeProvider.m_125977_(Items.f_42527_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42658_), Items.f_42659_, 0.35f, 200).m_126132_("has_mutton", RecipeProvider.m_125977_(Items.f_42658_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42485_), Items.f_42486_, 0.35f, 200).m_126132_("has_porkchop", RecipeProvider.m_125977_(Items.f_42485_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42697_), Items.f_42698_, 0.35f, 200).m_126132_("has_rabbit", RecipeProvider.m_125977_(Items.f_42697_)).m_176498_(p_176532_);
        RecipeProvider.m_176591_(p_176532_, f_176505_, Items.f_42413_, 0.1f, 200, "coal");
        RecipeProvider.m_176591_(p_176532_, f_176506_, Items.f_42416_, 0.7f, 200, "iron_ingot");
        RecipeProvider.m_176591_(p_176532_, f_176507_, Items.f_151052_, 0.7f, 200, "copper_ingot");
        RecipeProvider.m_176591_(p_176532_, f_176508_, Items.f_42417_, 1.0f, 200, "gold_ingot");
        RecipeProvider.m_176591_(p_176532_, f_176509_, Items.f_42415_, 1.0f, 200, "diamond");
        RecipeProvider.m_176591_(p_176532_, f_176510_, Items.f_42534_, 0.2f, 200, "lapis_lazuli");
        RecipeProvider.m_176591_(p_176532_, f_176511_, Items.f_42451_, 0.7f, 200, "redstone");
        RecipeProvider.m_176591_(p_176532_, f_176512_, Items.f_42616_, 1.0f, 200, "emerald");
        RecipeProvider.m_176743_(p_176532_, Items.f_151050_, Items.f_150995_);
        RecipeProvider.m_176743_(p_176532_, Items.f_151051_, Items.f_150996_);
        RecipeProvider.m_176743_(p_176532_, Items.f_151053_, Items.f_150997_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_204132_(ItemTags.f_13137_), Blocks.f_50058_.m_5456_(), 0.1f, 200).m_126132_("has_sand", RecipeProvider.m_206406_(ItemTags.f_13137_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50567_), Items.f_42540_, 0.1f, 200).m_126132_("has_sea_pickle", RecipeProvider.m_125977_(Blocks.f_50567_)).m_176500_(p_176532_, RecipeProvider.m_176656_(Items.f_42540_));
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50128_.m_5456_()), Items.f_42496_, 1.0f, 200).m_126132_("has_cactus", RecipeProvider.m_125977_(Blocks.f_50128_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42432_, Items.f_42431_, Items.f_42433_, Items.f_42434_, Items.f_42430_, Items.f_42476_, Items.f_42477_, Items.f_42478_, Items.f_42479_, Items.f_42652_), Items.f_42587_, 0.1f, 200).m_126132_("has_golden_pickaxe", RecipeProvider.m_125977_(Items.f_42432_)).m_126132_("has_golden_shovel", RecipeProvider.m_125977_(Items.f_42431_)).m_126132_("has_golden_axe", RecipeProvider.m_125977_(Items.f_42433_)).m_126132_("has_golden_hoe", RecipeProvider.m_125977_(Items.f_42434_)).m_126132_("has_golden_sword", RecipeProvider.m_125977_(Items.f_42430_)).m_126132_("has_golden_helmet", RecipeProvider.m_125977_(Items.f_42476_)).m_126132_("has_golden_chestplate", RecipeProvider.m_125977_(Items.f_42477_)).m_126132_("has_golden_leggings", RecipeProvider.m_125977_(Items.f_42478_)).m_126132_("has_golden_boots", RecipeProvider.m_125977_(Items.f_42479_)).m_126132_("has_golden_horse_armor", RecipeProvider.m_125977_(Items.f_42652_)).m_176500_(p_176532_, RecipeProvider.m_176656_(Items.f_42587_));
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42385_, Items.f_42384_, Items.f_42386_, Items.f_42387_, Items.f_42383_, Items.f_42468_, Items.f_42469_, Items.f_42470_, Items.f_42471_, Items.f_42651_, Items.f_42464_, Items.f_42465_, Items.f_42466_, Items.f_42467_), Items.f_42749_, 0.1f, 200).m_126132_("has_iron_pickaxe", RecipeProvider.m_125977_(Items.f_42385_)).m_126132_("has_iron_shovel", RecipeProvider.m_125977_(Items.f_42384_)).m_126132_("has_iron_axe", RecipeProvider.m_125977_(Items.f_42386_)).m_126132_("has_iron_hoe", RecipeProvider.m_125977_(Items.f_42387_)).m_126132_("has_iron_sword", RecipeProvider.m_125977_(Items.f_42383_)).m_126132_("has_iron_helmet", RecipeProvider.m_125977_(Items.f_42468_)).m_126132_("has_iron_chestplate", RecipeProvider.m_125977_(Items.f_42469_)).m_126132_("has_iron_leggings", RecipeProvider.m_125977_(Items.f_42470_)).m_126132_("has_iron_boots", RecipeProvider.m_125977_(Items.f_42471_)).m_126132_("has_iron_horse_armor", RecipeProvider.m_125977_(Items.f_42651_)).m_126132_("has_chainmail_helmet", RecipeProvider.m_125977_(Items.f_42464_)).m_126132_("has_chainmail_chestplate", RecipeProvider.m_125977_(Items.f_42465_)).m_126132_("has_chainmail_leggings", RecipeProvider.m_125977_(Items.f_42466_)).m_126132_("has_chainmail_boots", RecipeProvider.m_125977_(Items.f_42467_)).m_176500_(p_176532_, RecipeProvider.m_176656_(Items.f_42749_));
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50129_), Blocks.f_50352_.m_5456_(), 0.35f, 200).m_126132_("has_clay_block", RecipeProvider.m_125977_(Blocks.f_50129_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50134_), Items.f_42691_, 0.1f, 200).m_126132_("has_netherrack", RecipeProvider.m_125977_(Blocks.f_50134_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50331_), Items.f_42692_, 0.2f, 200).m_126132_("has_nether_quartz_ore", RecipeProvider.m_125977_(Blocks.f_50331_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50057_), Blocks.f_50056_.m_5456_(), 0.15f, 200).m_126132_("has_wet_sponge", RecipeProvider.m_125977_(Blocks.f_50057_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50652_), Blocks.f_50069_.m_5456_(), 0.1f, 200).m_126132_("has_cobblestone", RecipeProvider.m_125977_(Blocks.f_50652_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50069_), Blocks.f_50470_.m_5456_(), 0.1f, 200).m_126132_("has_stone", RecipeProvider.m_125977_(Blocks.f_50069_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50062_), Blocks.f_50471_.m_5456_(), 0.1f, 200).m_126132_("has_sandstone", RecipeProvider.m_125977_(Blocks.f_50062_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50394_), Blocks.f_50473_.m_5456_(), 0.1f, 200).m_126132_("has_red_sandstone", RecipeProvider.m_125977_(Blocks.f_50394_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50333_), Blocks.f_50472_.m_5456_(), 0.1f, 200).m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50222_), Blocks.f_50224_.m_5456_(), 0.1f, 200).m_126132_("has_stone_bricks", RecipeProvider.m_125977_(Blocks.f_50222_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50302_), Blocks.f_50541_.m_5456_(), 0.1f, 200).m_126132_("has_black_terracotta", RecipeProvider.m_125977_(Blocks.f_50302_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50298_), Blocks.f_50537_.m_5456_(), 0.1f, 200).m_126132_("has_blue_terracotta", RecipeProvider.m_125977_(Blocks.f_50298_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50299_), Blocks.f_50538_.m_5456_(), 0.1f, 200).m_126132_("has_brown_terracotta", RecipeProvider.m_125977_(Blocks.f_50299_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50296_), Blocks.f_50535_.m_5456_(), 0.1f, 200).m_126132_("has_cyan_terracotta", RecipeProvider.m_125977_(Blocks.f_50296_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50294_), Blocks.f_50533_.m_5456_(), 0.1f, 200).m_126132_("has_gray_terracotta", RecipeProvider.m_125977_(Blocks.f_50294_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50300_), Blocks.f_50539_.m_5456_(), 0.1f, 200).m_126132_("has_green_terracotta", RecipeProvider.m_125977_(Blocks.f_50300_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50290_), Blocks.f_50529_.m_5456_(), 0.1f, 200).m_126132_("has_light_blue_terracotta", RecipeProvider.m_125977_(Blocks.f_50290_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50295_), Blocks.f_50534_.m_5456_(), 0.1f, 200).m_126132_("has_light_gray_terracotta", RecipeProvider.m_125977_(Blocks.f_50295_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50292_), Blocks.f_50531_.m_5456_(), 0.1f, 200).m_126132_("has_lime_terracotta", RecipeProvider.m_125977_(Blocks.f_50292_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50289_), Blocks.f_50528_.m_5456_(), 0.1f, 200).m_126132_("has_magenta_terracotta", RecipeProvider.m_125977_(Blocks.f_50289_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50288_), Blocks.f_50527_.m_5456_(), 0.1f, 200).m_126132_("has_orange_terracotta", RecipeProvider.m_125977_(Blocks.f_50288_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50293_), Blocks.f_50532_.m_5456_(), 0.1f, 200).m_126132_("has_pink_terracotta", RecipeProvider.m_125977_(Blocks.f_50293_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50297_), Blocks.f_50536_.m_5456_(), 0.1f, 200).m_126132_("has_purple_terracotta", RecipeProvider.m_125977_(Blocks.f_50297_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50301_), Blocks.f_50540_.m_5456_(), 0.1f, 200).m_126132_("has_red_terracotta", RecipeProvider.m_125977_(Blocks.f_50301_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50287_), Blocks.f_50526_.m_5456_(), 0.1f, 200).m_126132_("has_white_terracotta", RecipeProvider.m_125977_(Blocks.f_50287_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50291_), Blocks.f_50530_.m_5456_(), 0.1f, 200).m_126132_("has_yellow_terracotta", RecipeProvider.m_125977_(Blocks.f_50291_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50722_), Items.f_42419_, 2.0f, 200).m_126132_("has_ancient_debris", RecipeProvider.m_125977_(Blocks.f_50722_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50137_), Blocks.f_152597_, 0.1f, 200).m_126132_("has_basalt", RecipeProvider.m_125977_(Blocks.f_50137_)).m_176498_(p_176532_);
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_152551_), Blocks.f_152550_, 0.1f, 200).m_126132_("has_cobbled_deepslate", RecipeProvider.m_125977_(Blocks.f_152551_)).m_176498_(p_176532_);
        RecipeProvider.m_176625_(p_176532_, f_176505_, Items.f_42413_, 0.1f, 100, "coal");
        RecipeProvider.m_176625_(p_176532_, f_176506_, Items.f_42416_, 0.7f, 100, "iron_ingot");
        RecipeProvider.m_176625_(p_176532_, f_176507_, Items.f_151052_, 0.7f, 100, "copper_ingot");
        RecipeProvider.m_176625_(p_176532_, f_176508_, Items.f_42417_, 1.0f, 100, "gold_ingot");
        RecipeProvider.m_176625_(p_176532_, f_176509_, Items.f_42415_, 1.0f, 100, "diamond");
        RecipeProvider.m_176625_(p_176532_, f_176510_, Items.f_42534_, 0.2f, 100, "lapis_lazuli");
        RecipeProvider.m_176625_(p_176532_, f_176511_, Items.f_42451_, 0.7f, 100, "redstone");
        RecipeProvider.m_176625_(p_176532_, f_176512_, Items.f_42616_, 1.0f, 100, "emerald");
        SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Blocks.f_50331_), Items.f_42692_, 0.2f, 100).m_126132_("has_nether_quartz_ore", RecipeProvider.m_125977_(Blocks.f_50331_)).m_176500_(p_176532_, RecipeProvider.m_176668_(Items.f_42692_));
        SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Items.f_42432_, Items.f_42431_, Items.f_42433_, Items.f_42434_, Items.f_42430_, Items.f_42476_, Items.f_42477_, Items.f_42478_, Items.f_42479_, Items.f_42652_), Items.f_42587_, 0.1f, 100).m_126132_("has_golden_pickaxe", RecipeProvider.m_125977_(Items.f_42432_)).m_126132_("has_golden_shovel", RecipeProvider.m_125977_(Items.f_42431_)).m_126132_("has_golden_axe", RecipeProvider.m_125977_(Items.f_42433_)).m_126132_("has_golden_hoe", RecipeProvider.m_125977_(Items.f_42434_)).m_126132_("has_golden_sword", RecipeProvider.m_125977_(Items.f_42430_)).m_126132_("has_golden_helmet", RecipeProvider.m_125977_(Items.f_42476_)).m_126132_("has_golden_chestplate", RecipeProvider.m_125977_(Items.f_42477_)).m_126132_("has_golden_leggings", RecipeProvider.m_125977_(Items.f_42478_)).m_126132_("has_golden_boots", RecipeProvider.m_125977_(Items.f_42479_)).m_126132_("has_golden_horse_armor", RecipeProvider.m_125977_(Items.f_42652_)).m_176500_(p_176532_, RecipeProvider.m_176668_(Items.f_42587_));
        SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Items.f_42385_, Items.f_42384_, Items.f_42386_, Items.f_42387_, Items.f_42383_, Items.f_42468_, Items.f_42469_, Items.f_42470_, Items.f_42471_, Items.f_42651_, Items.f_42464_, Items.f_42465_, Items.f_42466_, Items.f_42467_), Items.f_42749_, 0.1f, 100).m_126132_("has_iron_pickaxe", RecipeProvider.m_125977_(Items.f_42385_)).m_126132_("has_iron_shovel", RecipeProvider.m_125977_(Items.f_42384_)).m_126132_("has_iron_axe", RecipeProvider.m_125977_(Items.f_42386_)).m_126132_("has_iron_hoe", RecipeProvider.m_125977_(Items.f_42387_)).m_126132_("has_iron_sword", RecipeProvider.m_125977_(Items.f_42383_)).m_126132_("has_iron_helmet", RecipeProvider.m_125977_(Items.f_42468_)).m_126132_("has_iron_chestplate", RecipeProvider.m_125977_(Items.f_42469_)).m_126132_("has_iron_leggings", RecipeProvider.m_125977_(Items.f_42470_)).m_126132_("has_iron_boots", RecipeProvider.m_125977_(Items.f_42471_)).m_126132_("has_iron_horse_armor", RecipeProvider.m_125977_(Items.f_42651_)).m_126132_("has_chainmail_helmet", RecipeProvider.m_125977_(Items.f_42464_)).m_126132_("has_chainmail_chestplate", RecipeProvider.m_125977_(Items.f_42465_)).m_126132_("has_chainmail_leggings", RecipeProvider.m_125977_(Items.f_42466_)).m_126132_("has_chainmail_boots", RecipeProvider.m_125977_(Items.f_42467_)).m_176500_(p_176532_, RecipeProvider.m_176668_(Items.f_42749_));
        SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Blocks.f_50722_), Items.f_42419_, 2.0f, 100).m_126132_("has_ancient_debris", RecipeProvider.m_125977_(Blocks.f_50722_)).m_176500_(p_176532_, RecipeProvider.m_176668_(Items.f_42419_));
        RecipeProvider.m_126006_(p_176532_, "smoking", RecipeSerializer.f_44093_, 100);
        RecipeProvider.m_126006_(p_176532_, "campfire_cooking", RecipeSerializer.f_44094_, 600);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50404_, Blocks.f_50069_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50635_, Blocks.f_50069_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50222_, Blocks.f_50069_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50411_, Blocks.f_50069_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50194_, Blocks.f_50069_);
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50069_), Blocks.f_50225_).m_126132_("has_stone", RecipeProvider.m_125977_(Blocks.f_50069_)).m_176500_(p_176532_, "chiseled_stone_bricks_stone_from_stonecutting");
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50069_), Blocks.f_50609_).m_126132_("has_stone", RecipeProvider.m_125977_(Blocks.f_50069_)).m_176500_(p_176532_, "stone_brick_walls_from_stone_stonecutting");
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50064_, Blocks.f_50062_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50406_, Blocks.f_50062_, 2);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50407_, Blocks.f_50062_, 2);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50407_, Blocks.f_50064_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50263_, Blocks.f_50062_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50613_, Blocks.f_50062_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50063_, Blocks.f_50062_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50396_, Blocks.f_50394_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50467_, Blocks.f_50394_, 2);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50468_, Blocks.f_50394_, 2);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50468_, Blocks.f_50396_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50397_, Blocks.f_50394_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50606_, Blocks.f_50394_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50395_, Blocks.f_50394_);
        SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50333_), Blocks.f_50413_, 2).m_126132_("has_quartz_block", RecipeProvider.m_125977_(Blocks.f_50333_)).m_176500_(p_176532_, "quartz_slab_from_stonecutting");
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50284_, Blocks.f_50333_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50283_, Blocks.f_50333_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50282_, Blocks.f_50333_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50714_, Blocks.f_50333_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50157_, Blocks.f_50652_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50409_, Blocks.f_50652_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50274_, Blocks.f_50652_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50411_, Blocks.f_50222_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50194_, Blocks.f_50222_);
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50222_), Blocks.f_50609_).m_126132_("has_stone_bricks", RecipeProvider.m_125977_(Blocks.f_50222_)).m_176500_(p_176532_, "stone_brick_wall_from_stone_bricks_stonecutting");
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50225_, Blocks.f_50222_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50410_, Blocks.f_50076_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50193_, Blocks.f_50076_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50604_, Blocks.f_50076_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_220849_, Blocks.f_220844_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_220845_, Blocks.f_220844_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_220854_, Blocks.f_220844_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50412_, Blocks.f_50197_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50199_, Blocks.f_50197_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50610_, Blocks.f_50197_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50712_, Blocks.f_50197_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50601_, Blocks.f_50452_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50640_, Blocks.f_50452_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50612_, Blocks.f_50452_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50469_, Blocks.f_50492_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50442_, Blocks.f_50492_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50441_, Blocks.f_50492_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50383_, Blocks.f_50377_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50380_, Blocks.f_50377_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50605_, Blocks.f_50377_);
        SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50378_), Blocks.f_50384_, 2).m_126132_("has_prismarine_brick", RecipeProvider.m_125977_(Blocks.f_50378_)).m_176500_(p_176532_, "prismarine_brick_slab_from_prismarine_stonecutting");
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50378_), Blocks.f_50381_).m_126132_("has_prismarine_brick", RecipeProvider.m_125977_(Blocks.f_50378_)).m_176500_(p_176532_, "prismarine_brick_stairs_from_prismarine_stonecutting");
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50385_, Blocks.f_50379_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50382_, Blocks.f_50379_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50600_, Blocks.f_50334_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50639_, Blocks.f_50334_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50611_, Blocks.f_50334_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50387_, Blocks.f_50334_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50602_, Blocks.f_50334_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50641_, Blocks.f_50334_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50602_, Blocks.f_50387_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50641_, Blocks.f_50387_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50138_, Blocks.f_50137_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50651_, Blocks.f_50122_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50638_, Blocks.f_50122_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50608_, Blocks.f_50122_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50175_, Blocks.f_50122_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50643_, Blocks.f_50122_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50629_, Blocks.f_50122_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50643_, Blocks.f_50175_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50629_, Blocks.f_50175_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50603_, Blocks.f_50228_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50642_, Blocks.f_50228_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50615_, Blocks.f_50228_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50281_, Blocks.f_50228_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50646_, Blocks.f_50228_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50632_, Blocks.f_50228_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50646_, Blocks.f_50281_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50632_, Blocks.f_50281_);
        SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50223_), Blocks.f_50645_, 2).m_126132_("has_mossy_stone_bricks", RecipeProvider.m_125977_(Blocks.f_50223_)).m_176500_(p_176532_, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50223_), Blocks.f_50631_).m_126132_("has_mossy_stone_bricks", RecipeProvider.m_125977_(Blocks.f_50223_)).m_176500_(p_176532_, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50223_), Blocks.f_50607_).m_126132_("has_mossy_stone_bricks", RecipeProvider.m_125977_(Blocks.f_50223_)).m_176500_(p_176532_, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50647_, Blocks.f_50079_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50633_, Blocks.f_50079_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50275_, Blocks.f_50079_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50649_, Blocks.f_50471_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50636_, Blocks.f_50471_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50644_, Blocks.f_50473_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50630_, Blocks.f_50473_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50650_, Blocks.f_50472_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50637_, Blocks.f_50472_);
        SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50443_), Blocks.f_50648_, 2).m_126132_("has_end_stone_brick", RecipeProvider.m_125977_(Blocks.f_50443_)).m_176500_(p_176532_, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50443_), Blocks.f_50634_).m_126132_("has_end_stone_brick", RecipeProvider.m_125977_(Blocks.f_50443_)).m_176500_(p_176532_, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
        SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50443_), Blocks.f_50614_).m_126132_("has_end_stone_brick", RecipeProvider.m_125977_(Blocks.f_50443_)).m_176500_(p_176532_, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50443_, Blocks.f_50259_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50648_, Blocks.f_50259_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50634_, Blocks.f_50259_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50614_, Blocks.f_50259_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50405_, Blocks.f_50470_, 2);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50733_, Blocks.f_50730_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50731_, Blocks.f_50730_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50732_, Blocks.f_50730_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50734_, Blocks.f_50730_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50711_, Blocks.f_50730_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50708_, Blocks.f_50730_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50707_, Blocks.f_50730_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50737_, Blocks.f_50730_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50735_, Blocks.f_50730_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50738_, Blocks.f_50730_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50739_, Blocks.f_50730_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50740_, Blocks.f_50730_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50708_, Blocks.f_50734_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50707_, Blocks.f_50734_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50735_, Blocks.f_50734_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50711_, Blocks.f_50734_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50738_, Blocks.f_50734_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50739_, Blocks.f_50734_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50740_, Blocks.f_50734_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50737_, Blocks.f_50734_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_50738_, Blocks.f_50735_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50739_, Blocks.f_50735_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_50740_, Blocks.f_50735_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152570_, Blocks.f_152510_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152566_, Blocks.f_152510_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152569_, Blocks.f_152509_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152565_, Blocks.f_152509_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152568_, Blocks.f_152508_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152564_, Blocks.f_152508_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152567_, Blocks.f_152507_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152563_, Blocks.f_152507_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152586_, Blocks.f_152578_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152582_, Blocks.f_152578_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152585_, Blocks.f_152577_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152581_, Blocks.f_152577_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152584_, Blocks.f_152576_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152580_, Blocks.f_152576_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152583_, Blocks.f_152575_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152579_, Blocks.f_152575_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152510_, Blocks.f_152504_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152566_, Blocks.f_152504_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152570_, Blocks.f_152504_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152509_, Blocks.f_152503_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152565_, Blocks.f_152503_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152569_, Blocks.f_152503_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152508_, Blocks.f_152502_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152564_, Blocks.f_152502_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152568_, Blocks.f_152502_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152507_, Blocks.f_152501_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152563_, Blocks.f_152501_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152567_, Blocks.f_152501_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152578_, Blocks.f_152571_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152582_, Blocks.f_152571_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152586_, Blocks.f_152571_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152577_, Blocks.f_152573_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152581_, Blocks.f_152573_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152585_, Blocks.f_152573_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152576_, Blocks.f_152572_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152580_, Blocks.f_152572_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152584_, Blocks.f_152572_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152575_, Blocks.f_152574_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152579_, Blocks.f_152574_, 4);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152583_, Blocks.f_152574_, 8);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152553_, Blocks.f_152551_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152552_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152554_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152593_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152555_, Blocks.f_152551_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152557_, Blocks.f_152551_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152556_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152558_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152589_, Blocks.f_152551_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152591_, Blocks.f_152551_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152590_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152592_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152559_, Blocks.f_152551_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152551_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152551_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152551_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152557_, Blocks.f_152555_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152556_, Blocks.f_152555_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152558_, Blocks.f_152555_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152589_, Blocks.f_152555_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152591_, Blocks.f_152555_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152590_, Blocks.f_152555_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152592_, Blocks.f_152555_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152559_, Blocks.f_152555_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152555_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152555_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152555_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152591_, Blocks.f_152589_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152590_, Blocks.f_152589_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152592_, Blocks.f_152589_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152559_, Blocks.f_152589_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152589_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152589_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152589_);
        RecipeProvider.m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152559_, 2);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152559_);
        RecipeProvider.m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152559_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42473_, Items.f_42481_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42474_, Items.f_42482_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42472_, Items.f_42480_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42475_, Items.f_42483_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42388_, Items.f_42393_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42391_, Items.f_42396_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42390_, Items.f_42395_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42392_, Items.f_42397_);
        RecipeProvider.m_125994_(p_176532_, Items.f_42389_, Items.f_42394_);
    }

    private static void m_176551_(Consumer<FinishedRecipe> p_176552_, ItemLike p_176553_, ItemLike p_176554_, @Nullable String p_176555_) {
        RecipeProvider.m_176556_(p_176552_, p_176553_, p_176554_, p_176555_, 1);
    }

    private static void m_176556_(Consumer<FinishedRecipe> p_176557_, ItemLike p_176558_, ItemLike p_176559_, @Nullable String p_176560_, int p_176561_) {
        ShapelessRecipeBuilder.m_126191_(p_176558_, p_176561_).m_126209_(p_176559_).m_126145_(p_176560_).m_126132_(RecipeProvider.m_176602_(p_176559_), RecipeProvider.m_125977_(p_176559_)).m_176500_(p_176557_, RecipeProvider.m_176517_(p_176558_, p_176559_));
    }

    private static void m_176591_(Consumer<FinishedRecipe> p_176592_, List<ItemLike> p_176593_, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        RecipeProvider.m_176533_(p_176592_, RecipeSerializer.f_44091_, p_176593_, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    private static void m_176625_(Consumer<FinishedRecipe> p_176626_, List<ItemLike> p_176627_, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        RecipeProvider.m_176533_(p_176626_, RecipeSerializer.f_44092_, p_176627_, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    private static void m_176533_(Consumer<FinishedRecipe> p_176534_, SimpleCookingSerializer<?> p_176535_, List<ItemLike> p_176536_, ItemLike p_176537_, float p_176538_, int p_176539_, String p_176540_, String p_176541_) {
        for (ItemLike $$8 : p_176536_) {
            SimpleCookingRecipeBuilder.m_126248_(Ingredient.m_43929_($$8), p_176537_, p_176538_, p_176539_, p_176535_).m_126145_(p_176540_).m_126132_(RecipeProvider.m_176602_($$8), RecipeProvider.m_125977_($$8)).m_176500_(p_176534_, RecipeProvider.m_176632_(p_176537_) + p_176541_ + "_" + RecipeProvider.m_176632_($$8));
        }
    }

    private static void m_125994_(Consumer<FinishedRecipe> p_125995_, Item p_125996_, Item p_125997_) {
        UpgradeRecipeBuilder.m_126385_(Ingredient.m_43929_(p_125996_), Ingredient.m_43929_(Items.f_42418_), p_125997_).m_126389_("has_netherite_ingot", RecipeProvider.m_125977_(Items.f_42418_)).m_126392_(p_125995_, RecipeProvider.m_176632_(p_125997_) + "_smithing");
    }

    private static void m_206408_(Consumer<FinishedRecipe> p_206409_, ItemLike p_206410_, TagKey<Item> p_206411_) {
        ShapelessRecipeBuilder.m_126191_(p_206410_, 4).m_206419_(p_206411_).m_126145_("planks").m_126132_("has_log", RecipeProvider.m_206406_(p_206411_)).m_176498_(p_206409_);
    }

    private static void m_206412_(Consumer<FinishedRecipe> p_206413_, ItemLike p_206414_, TagKey<Item> p_206415_) {
        ShapelessRecipeBuilder.m_126191_(p_206414_, 4).m_206419_(p_206415_).m_126145_("planks").m_126132_("has_logs", RecipeProvider.m_206406_(p_206415_)).m_176498_(p_206413_);
    }

    private static void m_126002_(Consumer<FinishedRecipe> p_126003_, ItemLike p_126004_, ItemLike p_126005_) {
        ShapedRecipeBuilder.m_126118_(p_126004_, 3).m_126127_(Character.valueOf('#'), p_126005_).m_126130_("##").m_126130_("##").m_126145_("bark").m_126132_("has_log", RecipeProvider.m_125977_(p_126005_)).m_176498_(p_126003_);
    }

    private static void m_126021_(Consumer<FinishedRecipe> p_126022_, ItemLike p_126023_, ItemLike p_126024_) {
        ShapedRecipeBuilder.m_126116_(p_126023_).m_126127_(Character.valueOf('#'), p_126024_).m_126130_("# #").m_126130_("###").m_126145_("boat").m_126132_("in_water", RecipeProvider.m_125979_(Blocks.f_49990_)).m_176498_(p_126022_);
    }

    private static void m_236371_(Consumer<FinishedRecipe> p_236372_, ItemLike p_236373_, ItemLike p_236374_) {
        ShapelessRecipeBuilder.m_126189_(p_236373_).m_126209_(Blocks.f_50087_).m_126209_(p_236374_).m_126145_("chest_boat").m_126132_("has_boat", RecipeProvider.m_206406_(ItemTags.f_13155_)).m_176498_(p_236372_);
    }

    private static RecipeBuilder m_176658_(ItemLike p_176659_, Ingredient p_176660_) {
        return ShapelessRecipeBuilder.m_126189_(p_176659_).m_126184_(p_176660_);
    }

    private static RecipeBuilder m_176670_(ItemLike p_176671_, Ingredient p_176672_) {
        return ShapedRecipeBuilder.m_126118_(p_176671_, 3).m_126124_(Character.valueOf('#'), p_176672_).m_126130_("##").m_126130_("##").m_126130_("##");
    }

    private static RecipeBuilder m_176678_(ItemLike p_176679_, Ingredient p_176680_) {
        int $$2 = p_176679_ == Blocks.f_50198_ ? 6 : 3;
        Item $$3 = p_176679_ == Blocks.f_50198_ ? Items.f_42691_ : Items.f_42398_;
        return ShapedRecipeBuilder.m_126118_(p_176679_, $$2).m_126124_(Character.valueOf('W'), p_176680_).m_126127_(Character.valueOf('#'), $$3).m_126130_("W#W").m_126130_("W#W");
    }

    private static RecipeBuilder m_176684_(ItemLike p_176685_, Ingredient p_176686_) {
        return ShapedRecipeBuilder.m_126116_(p_176685_).m_126127_(Character.valueOf('#'), Items.f_42398_).m_126124_(Character.valueOf('W'), p_176686_).m_126130_("#W#").m_126130_("#W#");
    }

    private static void m_176690_(Consumer<FinishedRecipe> p_176691_, ItemLike p_176692_, ItemLike p_176693_) {
        RecipeProvider.m_176694_(p_176692_, Ingredient.m_43929_(p_176693_)).m_126132_(RecipeProvider.m_176602_(p_176693_), RecipeProvider.m_125977_(p_176693_)).m_176498_(p_176691_);
    }

    private static RecipeBuilder m_176694_(ItemLike p_176695_, Ingredient p_176696_) {
        return ShapedRecipeBuilder.m_126116_(p_176695_).m_126124_(Character.valueOf('#'), p_176696_).m_126130_("##");
    }

    private static void m_176700_(Consumer<FinishedRecipe> p_176701_, ItemLike p_176702_, ItemLike p_176703_) {
        RecipeProvider.m_176704_(p_176702_, Ingredient.m_43929_(p_176703_)).m_126132_(RecipeProvider.m_176602_(p_176703_), RecipeProvider.m_125977_(p_176703_)).m_176498_(p_176701_);
    }

    private static RecipeBuilder m_176704_(ItemLike p_176705_, Ingredient p_176706_) {
        return ShapedRecipeBuilder.m_126118_(p_176705_, 6).m_126124_(Character.valueOf('#'), p_176706_).m_126130_("###");
    }

    private static RecipeBuilder m_176710_(ItemLike p_176711_, Ingredient p_176712_) {
        return ShapedRecipeBuilder.m_126118_(p_176711_, 4).m_126124_(Character.valueOf('#'), p_176712_).m_126130_("#  ").m_126130_("## ").m_126130_("###");
    }

    private static RecipeBuilder m_176720_(ItemLike p_176721_, Ingredient p_176722_) {
        return ShapedRecipeBuilder.m_126118_(p_176721_, 2).m_126124_(Character.valueOf('#'), p_176722_).m_126130_("###").m_126130_("###");
    }

    private static RecipeBuilder m_176726_(ItemLike p_176727_, Ingredient p_176728_) {
        return ShapedRecipeBuilder.m_126118_(p_176727_, 3).m_126145_("sign").m_126124_(Character.valueOf('#'), p_176728_).m_126127_(Character.valueOf('X'), Items.f_42398_).m_126130_("###").m_126130_("###").m_126130_(" X ");
    }

    private static void m_126061_(Consumer<FinishedRecipe> p_126062_, ItemLike p_126063_, ItemLike p_126064_) {
        ShapelessRecipeBuilder.m_126189_(p_126063_).m_126209_(p_126064_).m_126209_(Blocks.f_50041_).m_126145_("wool").m_126132_("has_white_wool", RecipeProvider.m_125977_(Blocks.f_50041_)).m_176498_(p_126062_);
    }

    private static void m_176716_(Consumer<FinishedRecipe> p_176717_, ItemLike p_176718_, ItemLike p_176719_) {
        ShapedRecipeBuilder.m_126118_(p_176718_, 3).m_126127_(Character.valueOf('#'), p_176719_).m_126130_("##").m_126145_("carpet").m_126132_(RecipeProvider.m_176602_(p_176719_), RecipeProvider.m_125977_(p_176719_)).m_176498_(p_176717_);
    }

    private static void m_126069_(Consumer<FinishedRecipe> p_126070_, ItemLike p_126071_, ItemLike p_126072_) {
        ShapedRecipeBuilder.m_126118_(p_126071_, 8).m_126127_(Character.valueOf('#'), Blocks.f_50336_).m_126127_(Character.valueOf('$'), p_126072_).m_126130_("###").m_126130_("#$#").m_126130_("###").m_126145_("carpet").m_126132_("has_white_carpet", RecipeProvider.m_125977_(Blocks.f_50336_)).m_126132_(RecipeProvider.m_176602_(p_126072_), RecipeProvider.m_125977_(p_126072_)).m_176500_(p_126070_, RecipeProvider.m_176517_(p_126071_, Blocks.f_50336_));
    }

    private static void m_126073_(Consumer<FinishedRecipe> p_126074_, ItemLike p_126075_, ItemLike p_126076_) {
        ShapedRecipeBuilder.m_126116_(p_126075_).m_126127_(Character.valueOf('#'), p_126076_).m_206416_(Character.valueOf('X'), ItemTags.f_13168_).m_126130_("###").m_126130_("XXX").m_126145_("bed").m_126132_(RecipeProvider.m_176602_(p_126076_), RecipeProvider.m_125977_(p_126076_)).m_176498_(p_126074_);
    }

    private static void m_126077_(Consumer<FinishedRecipe> p_126078_, ItemLike p_126079_, ItemLike p_126080_) {
        ShapelessRecipeBuilder.m_126189_(p_126079_).m_126209_(Items.f_42503_).m_126209_(p_126080_).m_126145_("dyed_bed").m_126132_("has_bed", RecipeProvider.m_125977_(Items.f_42503_)).m_176500_(p_126078_, RecipeProvider.m_176517_(p_126079_, Items.f_42503_));
    }

    private static void m_126081_(Consumer<FinishedRecipe> p_126082_, ItemLike p_126083_, ItemLike p_126084_) {
        ShapedRecipeBuilder.m_126116_(p_126083_).m_126127_(Character.valueOf('#'), p_126084_).m_126127_(Character.valueOf('|'), Items.f_42398_).m_126130_("###").m_126130_("###").m_126130_(" | ").m_126145_("banner").m_126132_(RecipeProvider.m_176602_(p_126084_), RecipeProvider.m_125977_(p_126084_)).m_176498_(p_126082_);
    }

    private static void m_126085_(Consumer<FinishedRecipe> p_126086_, ItemLike p_126087_, ItemLike p_126088_) {
        ShapedRecipeBuilder.m_126118_(p_126087_, 8).m_126127_(Character.valueOf('#'), Blocks.f_50058_).m_126127_(Character.valueOf('X'), p_126088_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126145_("stained_glass").m_126132_("has_glass", RecipeProvider.m_125977_(Blocks.f_50058_)).m_176498_(p_126086_);
    }

    private static void m_126089_(Consumer<FinishedRecipe> p_126090_, ItemLike p_126091_, ItemLike p_126092_) {
        ShapedRecipeBuilder.m_126118_(p_126091_, 16).m_126127_(Character.valueOf('#'), p_126092_).m_126130_("###").m_126130_("###").m_126145_("stained_glass_pane").m_126132_("has_glass", RecipeProvider.m_125977_(p_126092_)).m_176498_(p_126090_);
    }

    private static void m_126093_(Consumer<FinishedRecipe> p_126094_, ItemLike p_126095_, ItemLike p_126096_) {
        ShapedRecipeBuilder.m_126118_(p_126095_, 8).m_126127_(Character.valueOf('#'), Blocks.f_50185_).m_126127_(Character.valueOf('$'), p_126096_).m_126130_("###").m_126130_("#$#").m_126130_("###").m_126145_("stained_glass_pane").m_126132_("has_glass_pane", RecipeProvider.m_125977_(Blocks.f_50185_)).m_126132_(RecipeProvider.m_176602_(p_126096_), RecipeProvider.m_125977_(p_126096_)).m_176500_(p_126094_, RecipeProvider.m_176517_(p_126095_, Blocks.f_50185_));
    }

    private static void m_126097_(Consumer<FinishedRecipe> p_126098_, ItemLike p_126099_, ItemLike p_126100_) {
        ShapedRecipeBuilder.m_126118_(p_126099_, 8).m_126127_(Character.valueOf('#'), Blocks.f_50352_).m_126127_(Character.valueOf('X'), p_126100_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_126145_("stained_terracotta").m_126132_("has_terracotta", RecipeProvider.m_125977_(Blocks.f_50352_)).m_176498_(p_126098_);
    }

    private static void m_126101_(Consumer<FinishedRecipe> p_126102_, ItemLike p_126103_, ItemLike p_126104_) {
        ShapelessRecipeBuilder.m_126191_(p_126103_, 8).m_126209_(p_126104_).m_126211_(Blocks.f_49992_, 4).m_126211_(Blocks.f_49994_, 4).m_126145_("concrete_powder").m_126132_("has_sand", RecipeProvider.m_125977_(Blocks.f_49992_)).m_126132_("has_gravel", RecipeProvider.m_125977_(Blocks.f_49994_)).m_176498_(p_126102_);
    }

    public static void m_176542_(Consumer<FinishedRecipe> p_176543_, ItemLike p_176544_, ItemLike p_176545_) {
        ShapelessRecipeBuilder.m_126189_(p_176544_).m_126209_(Blocks.f_152482_).m_126209_(p_176545_).m_126145_("dyed_candle").m_126132_(RecipeProvider.m_176602_(p_176545_), RecipeProvider.m_125977_(p_176545_)).m_176498_(p_176543_);
    }

    public static void m_176612_(Consumer<FinishedRecipe> p_176613_, ItemLike p_176614_, ItemLike p_176615_) {
        RecipeProvider.m_176514_(p_176614_, Ingredient.m_43929_(p_176615_)).m_126132_(RecipeProvider.m_176602_(p_176615_), RecipeProvider.m_125977_(p_176615_)).m_176498_(p_176613_);
    }

    public static RecipeBuilder m_176514_(ItemLike p_176515_, Ingredient p_176516_) {
        return ShapedRecipeBuilder.m_126118_(p_176515_, 6).m_126124_(Character.valueOf('#'), p_176516_).m_126130_("###").m_126130_("###");
    }

    public static void m_176640_(Consumer<FinishedRecipe> p_176641_, ItemLike p_176642_, ItemLike p_176643_) {
        RecipeProvider.m_176604_(p_176642_, Ingredient.m_43929_(p_176643_)).m_126132_(RecipeProvider.m_176602_(p_176643_), RecipeProvider.m_125977_(p_176643_)).m_176498_(p_176641_);
    }

    public static RecipeBuilder m_176604_(ItemLike p_176605_, Ingredient p_176606_) {
        return ShapedRecipeBuilder.m_126118_(p_176605_, 4).m_126124_(Character.valueOf('S'), p_176606_).m_126130_("SS").m_126130_("SS");
    }

    public static void m_176652_(Consumer<FinishedRecipe> p_176653_, ItemLike p_176654_, ItemLike p_176655_) {
        RecipeProvider.m_176634_(p_176654_, Ingredient.m_43929_(p_176655_)).m_126132_(RecipeProvider.m_176602_(p_176655_), RecipeProvider.m_125977_(p_176655_)).m_176498_(p_176653_);
    }

    public static ShapedRecipeBuilder m_176634_(ItemLike p_176635_, Ingredient p_176636_) {
        return ShapedRecipeBuilder.m_126118_(p_176635_, 4).m_126124_(Character.valueOf('#'), p_176636_).m_126130_("##").m_126130_("##");
    }

    public static void m_176664_(Consumer<FinishedRecipe> p_176665_, ItemLike p_176666_, ItemLike p_176667_) {
        RecipeProvider.m_176646_(p_176666_, Ingredient.m_43929_(p_176667_)).m_126132_(RecipeProvider.m_176602_(p_176667_), RecipeProvider.m_125977_(p_176667_)).m_176498_(p_176665_);
    }

    public static ShapedRecipeBuilder m_176646_(ItemLike p_176647_, Ingredient p_176648_) {
        return ShapedRecipeBuilder.m_126116_(p_176647_).m_126124_(Character.valueOf('#'), p_176648_).m_126130_("#").m_126130_("#");
    }

    private static void m_176735_(Consumer<FinishedRecipe> p_176736_, ItemLike p_176737_, ItemLike p_176738_) {
        RecipeProvider.m_176546_(p_176736_, p_176737_, p_176738_, 1);
    }

    private static void m_176546_(Consumer<FinishedRecipe> p_176547_, ItemLike p_176548_, ItemLike p_176549_, int p_176550_) {
        SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(p_176549_), p_176548_, p_176550_).m_126132_(RecipeProvider.m_176602_(p_176549_), RecipeProvider.m_125977_(p_176549_)).m_176500_(p_176547_, RecipeProvider.m_176517_(p_176548_, p_176549_) + "_stonecutting");
    }

    private static void m_176739_(Consumer<FinishedRecipe> p_176740_, ItemLike p_176741_, ItemLike p_176742_) {
        SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(p_176742_), p_176741_, 0.1f, 200).m_126132_(RecipeProvider.m_176602_(p_176742_), RecipeProvider.m_125977_(p_176742_)).m_176498_(p_176740_);
    }

    private static void m_176743_(Consumer<FinishedRecipe> p_176744_, ItemLike p_176745_, ItemLike p_176746_) {
        RecipeProvider.m_176568_(p_176744_, p_176745_, p_176746_, RecipeProvider.m_176644_(p_176746_), null, RecipeProvider.m_176644_(p_176745_), null);
    }

    private static void m_176562_(Consumer<FinishedRecipe> p_176563_, ItemLike p_176564_, ItemLike p_176565_, String p_176566_, String p_176567_) {
        RecipeProvider.m_176568_(p_176563_, p_176564_, p_176565_, p_176566_, p_176567_, RecipeProvider.m_176644_(p_176564_), null);
    }

    private static void m_176616_(Consumer<FinishedRecipe> p_176617_, ItemLike p_176618_, ItemLike p_176619_, String p_176620_, String p_176621_) {
        RecipeProvider.m_176568_(p_176617_, p_176618_, p_176619_, RecipeProvider.m_176644_(p_176619_), null, p_176620_, p_176621_);
    }

    private static void m_176568_(Consumer<FinishedRecipe> p_176569_, ItemLike p_176570_, ItemLike p_176571_, String p_176572_, @Nullable String p_176573_, String p_176574_, @Nullable String p_176575_) {
        ShapelessRecipeBuilder.m_126191_(p_176570_, 9).m_126209_(p_176571_).m_126145_(p_176575_).m_126132_(RecipeProvider.m_176602_(p_176571_), RecipeProvider.m_125977_(p_176571_)).m_126140_(p_176569_, new ResourceLocation(p_176574_));
        ShapedRecipeBuilder.m_126116_(p_176571_).m_126127_(Character.valueOf('#'), p_176570_).m_126130_("###").m_126130_("###").m_126130_("###").m_126145_(p_176573_).m_126132_(RecipeProvider.m_176602_(p_176570_), RecipeProvider.m_125977_(p_176570_)).m_126140_(p_176569_, new ResourceLocation(p_176572_));
    }

    private static void m_126006_(Consumer<FinishedRecipe> p_126007_, String p_126008_, SimpleCookingSerializer<?> p_126009_, int p_126010_) {
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42579_, Items.f_42580_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42581_, Items.f_42582_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42526_, Items.f_42530_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_41910_, Items.f_42576_, 0.1f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42527_, Items.f_42531_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42658_, Items.f_42659_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42485_, Items.f_42486_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42620_, Items.f_42674_, 0.35f);
        RecipeProvider.m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42697_, Items.f_42698_, 0.35f);
    }

    private static void m_176583_(Consumer<FinishedRecipe> p_176584_, String p_176585_, SimpleCookingSerializer<?> p_176586_, int p_176587_, ItemLike p_176588_, ItemLike p_176589_, float p_176590_) {
        SimpleCookingRecipeBuilder.m_126248_(Ingredient.m_43929_(p_176588_), p_176589_, p_176590_, p_176587_, p_176586_).m_126132_(RecipeProvider.m_176602_(p_176588_), RecipeProvider.m_125977_(p_176588_)).m_176500_(p_176584_, RecipeProvider.m_176632_(p_176589_) + "_from_" + p_176585_);
    }

    private static void m_176610_(Consumer<FinishedRecipe> p_176611_) {
        HoneycombItem.f_150863_.get().forEach((p_176578_, p_176579_) -> ShapelessRecipeBuilder.m_126189_(p_176579_).m_126209_((ItemLike)p_176578_).m_126209_(Items.f_42784_).m_126145_(RecipeProvider.m_176632_(p_176579_)).m_126132_(RecipeProvider.m_176602_(p_176578_), RecipeProvider.m_125977_(p_176578_)).m_176500_(p_176611_, RecipeProvider.m_176517_(p_176579_, Items.f_42784_)));
    }

    private static void m_176580_(Consumer<FinishedRecipe> p_176581_, BlockFamily p_176582_) {
        p_176582_.m_175954_().forEach((p_176529_, p_176530_) -> {
            BiFunction<ItemLike, ItemLike, RecipeBuilder> $$4 = f_176513_.get(p_176529_);
            Block $$5 = RecipeProvider.m_176523_(p_176582_, p_176529_);
            if ($$4 != null) {
                RecipeBuilder $$6 = $$4.apply((ItemLike)p_176530_, $$5);
                p_176582_.m_175957_().ifPresent(p_176601_ -> $$6.m_126145_(p_176601_ + (String)(p_176529_ == BlockFamily.Variant.CUT ? "" : "_" + p_176529_.m_176020_())));
                $$6.m_126132_(p_176582_.m_175958_().orElseGet(() -> RecipeProvider.m_176602_($$5)), RecipeProvider.m_125977_($$5));
                $$6.m_176498_(p_176581_);
            }
            if (p_176529_ == BlockFamily.Variant.CRACKED) {
                RecipeProvider.m_176739_(p_176581_, p_176530_, $$5);
            }
        });
    }

    private static Block m_176523_(BlockFamily p_176524_, BlockFamily.Variant p_176525_) {
        if (p_176525_ == BlockFamily.Variant.CHISELED) {
            if (!p_176524_.m_175954_().containsKey((Object)BlockFamily.Variant.SLAB)) {
                throw new IllegalStateException("Slab is not defined for the family.");
            }
            return p_176524_.m_175952_(BlockFamily.Variant.SLAB);
        }
        return p_176524_.m_175951_();
    }

    private static EnterBlockTrigger.TriggerInstance m_125979_(Block p_125980_) {
        return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_125980_, StatePropertiesPredicate.f_67658_);
    }

    private static InventoryChangeTrigger.TriggerInstance m_176520_(MinMaxBounds.Ints p_176521_, ItemLike p_176522_) {
        return RecipeProvider.m_126011_(ItemPredicate.Builder.m_45068_().m_151445_(p_176522_).m_151443_(p_176521_).m_45077_());
    }

    private static InventoryChangeTrigger.TriggerInstance m_125977_(ItemLike p_125978_) {
        return RecipeProvider.m_126011_(ItemPredicate.Builder.m_45068_().m_151445_(p_125978_).m_45077_());
    }

    private static InventoryChangeTrigger.TriggerInstance m_206406_(TagKey<Item> p_206407_) {
        return RecipeProvider.m_126011_(ItemPredicate.Builder.m_45068_().m_204145_(p_206407_).m_45077_());
    }

    private static InventoryChangeTrigger.TriggerInstance m_126011_(ItemPredicate ... p_126012_) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, p_126012_);
    }

    private static String m_176602_(ItemLike p_176603_) {
        return "has_" + RecipeProvider.m_176632_(p_176603_);
    }

    private static String m_176632_(ItemLike p_176633_) {
        return Registry.f_122827_.m_7981_(p_176633_.m_5456_()).m_135815_();
    }

    private static String m_176644_(ItemLike p_176645_) {
        return RecipeProvider.m_176632_(p_176645_);
    }

    private static String m_176517_(ItemLike p_176518_, ItemLike p_176519_) {
        return RecipeProvider.m_176632_(p_176518_) + "_from_" + RecipeProvider.m_176632_(p_176519_);
    }

    private static String m_176656_(ItemLike p_176657_) {
        return RecipeProvider.m_176632_(p_176657_) + "_from_smelting";
    }

    private static String m_176668_(ItemLike p_176669_) {
        return RecipeProvider.m_176632_(p_176669_) + "_from_blasting";
    }

    @Override
    public String m_6055_() {
        return "Recipes";
    }
}

