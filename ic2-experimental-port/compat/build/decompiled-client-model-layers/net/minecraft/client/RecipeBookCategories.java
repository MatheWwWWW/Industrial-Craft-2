/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public final class RecipeBookCategories
extends Enum<RecipeBookCategories> {
    public static final /* enum */ RecipeBookCategories CRAFTING_SEARCH = new RecipeBookCategories(new ItemStack(Items.f_42522_));
    public static final /* enum */ RecipeBookCategories CRAFTING_BUILDING_BLOCKS = new RecipeBookCategories(new ItemStack(Blocks.f_50076_));
    public static final /* enum */ RecipeBookCategories CRAFTING_REDSTONE = new RecipeBookCategories(new ItemStack(Items.f_42451_));
    public static final /* enum */ RecipeBookCategories CRAFTING_EQUIPMENT = new RecipeBookCategories(new ItemStack(Items.f_42386_), new ItemStack(Items.f_42430_));
    public static final /* enum */ RecipeBookCategories CRAFTING_MISC = new RecipeBookCategories(new ItemStack(Items.f_42448_), new ItemStack(Items.f_42410_));
    public static final /* enum */ RecipeBookCategories FURNACE_SEARCH = new RecipeBookCategories(new ItemStack(Items.f_42522_));
    public static final /* enum */ RecipeBookCategories FURNACE_FOOD = new RecipeBookCategories(new ItemStack(Items.f_42485_));
    public static final /* enum */ RecipeBookCategories FURNACE_BLOCKS = new RecipeBookCategories(new ItemStack(Blocks.f_50069_));
    public static final /* enum */ RecipeBookCategories FURNACE_MISC = new RecipeBookCategories(new ItemStack(Items.f_42448_), new ItemStack(Items.f_42616_));
    public static final /* enum */ RecipeBookCategories BLAST_FURNACE_SEARCH = new RecipeBookCategories(new ItemStack(Items.f_42522_));
    public static final /* enum */ RecipeBookCategories BLAST_FURNACE_BLOCKS = new RecipeBookCategories(new ItemStack(Blocks.f_50173_));
    public static final /* enum */ RecipeBookCategories BLAST_FURNACE_MISC = new RecipeBookCategories(new ItemStack(Items.f_42384_), new ItemStack(Items.f_42478_));
    public static final /* enum */ RecipeBookCategories SMOKER_SEARCH = new RecipeBookCategories(new ItemStack(Items.f_42522_));
    public static final /* enum */ RecipeBookCategories SMOKER_FOOD = new RecipeBookCategories(new ItemStack(Items.f_42485_));
    public static final /* enum */ RecipeBookCategories STONECUTTER = new RecipeBookCategories(new ItemStack(Items.f_42021_));
    public static final /* enum */ RecipeBookCategories SMITHING = new RecipeBookCategories(new ItemStack(Items.f_42481_));
    public static final /* enum */ RecipeBookCategories CAMPFIRE = new RecipeBookCategories(new ItemStack(Items.f_42485_));
    public static final /* enum */ RecipeBookCategories UNKNOWN = new RecipeBookCategories(new ItemStack(Items.f_42127_));
    public static final List<RecipeBookCategories> f_92256_;
    public static final List<RecipeBookCategories> f_92257_;
    public static final List<RecipeBookCategories> f_92258_;
    public static final List<RecipeBookCategories> f_92259_;
    public static final Map<RecipeBookCategories, List<RecipeBookCategories>> f_92260_;
    private final List<ItemStack> f_92261_;
    private static final /* synthetic */ RecipeBookCategories[] $VALUES;

    public static RecipeBookCategories[] values() {
        return (RecipeBookCategories[])$VALUES.clone();
    }

    public static RecipeBookCategories valueOf(String p_92272_) {
        return Enum.valueOf(RecipeBookCategories.class, p_92272_);
    }

    private RecipeBookCategories(ItemStack ... p_92267_) {
        this.f_92261_ = ImmutableList.copyOf((Object[])p_92267_);
    }

    public static List<RecipeBookCategories> m_92269_(RecipeBookType p_92270_) {
        switch (p_92270_) {
            case CRAFTING: {
                return f_92259_;
            }
            case FURNACE: {
                return f_92258_;
            }
            case BLAST_FURNACE: {
                return f_92257_;
            }
            case SMOKER: {
                return f_92256_;
            }
        }
        return ImmutableList.of();
    }

    public List<ItemStack> m_92268_() {
        return this.f_92261_;
    }

    private static /* synthetic */ RecipeBookCategories[] m_168550_() {
        return new RecipeBookCategories[]{CRAFTING_SEARCH, CRAFTING_BUILDING_BLOCKS, CRAFTING_REDSTONE, CRAFTING_EQUIPMENT, CRAFTING_MISC, FURNACE_SEARCH, FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC, BLAST_FURNACE_SEARCH, BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC, SMOKER_SEARCH, SMOKER_FOOD, STONECUTTER, SMITHING, CAMPFIRE, UNKNOWN};
    }

    static {
        $VALUES = RecipeBookCategories.m_168550_();
        f_92256_ = ImmutableList.of((Object)((Object)SMOKER_SEARCH), (Object)((Object)SMOKER_FOOD));
        f_92257_ = ImmutableList.of((Object)((Object)BLAST_FURNACE_SEARCH), (Object)((Object)BLAST_FURNACE_BLOCKS), (Object)((Object)BLAST_FURNACE_MISC));
        f_92258_ = ImmutableList.of((Object)((Object)FURNACE_SEARCH), (Object)((Object)FURNACE_FOOD), (Object)((Object)FURNACE_BLOCKS), (Object)((Object)FURNACE_MISC));
        f_92259_ = ImmutableList.of((Object)((Object)CRAFTING_SEARCH), (Object)((Object)CRAFTING_EQUIPMENT), (Object)((Object)CRAFTING_BUILDING_BLOCKS), (Object)((Object)CRAFTING_MISC), (Object)((Object)CRAFTING_REDSTONE));
        f_92260_ = ImmutableMap.of((Object)((Object)CRAFTING_SEARCH), (Object)ImmutableList.of((Object)((Object)CRAFTING_EQUIPMENT), (Object)((Object)CRAFTING_BUILDING_BLOCKS), (Object)((Object)CRAFTING_MISC), (Object)((Object)CRAFTING_REDSTONE)), (Object)((Object)FURNACE_SEARCH), (Object)ImmutableList.of((Object)((Object)FURNACE_FOOD), (Object)((Object)FURNACE_BLOCKS), (Object)((Object)FURNACE_MISC)), (Object)((Object)BLAST_FURNACE_SEARCH), (Object)ImmutableList.of((Object)((Object)BLAST_FURNACE_BLOCKS), (Object)((Object)BLAST_FURNACE_MISC)), (Object)((Object)SMOKER_SEARCH), (Object)ImmutableList.of((Object)((Object)SMOKER_FOOD)));
    }
}

