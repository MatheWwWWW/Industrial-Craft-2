/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.ArmorDyeRecipe;
import net.minecraft.world.item.crafting.BannerDuplicateRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.BookCloningRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.FireworkRocketRecipe;
import net.minecraft.world.item.crafting.FireworkStarFadeRecipe;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.MapCloningRecipe;
import net.minecraft.world.item.crafting.MapExtendingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.item.crafting.ShulkerBoxColoring;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.SuspiciousStewRecipe;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;

public interface RecipeSerializer<T extends Recipe<?>> {
    public static final RecipeSerializer<ShapedRecipe> f_44076_ = RecipeSerializer.m_44098_("crafting_shaped", new ShapedRecipe.Serializer());
    public static final RecipeSerializer<ShapelessRecipe> f_44077_ = RecipeSerializer.m_44098_("crafting_shapeless", new ShapelessRecipe.Serializer());
    public static final SimpleRecipeSerializer<ArmorDyeRecipe> f_44078_ = RecipeSerializer.m_44098_("crafting_special_armordye", new SimpleRecipeSerializer<ArmorDyeRecipe>(ArmorDyeRecipe::new));
    public static final SimpleRecipeSerializer<BookCloningRecipe> f_44079_ = RecipeSerializer.m_44098_("crafting_special_bookcloning", new SimpleRecipeSerializer<BookCloningRecipe>(BookCloningRecipe::new));
    public static final SimpleRecipeSerializer<MapCloningRecipe> f_44080_ = RecipeSerializer.m_44098_("crafting_special_mapcloning", new SimpleRecipeSerializer<MapCloningRecipe>(MapCloningRecipe::new));
    public static final SimpleRecipeSerializer<MapExtendingRecipe> f_44081_ = RecipeSerializer.m_44098_("crafting_special_mapextending", new SimpleRecipeSerializer<MapExtendingRecipe>(MapExtendingRecipe::new));
    public static final SimpleRecipeSerializer<FireworkRocketRecipe> f_44082_ = RecipeSerializer.m_44098_("crafting_special_firework_rocket", new SimpleRecipeSerializer<FireworkRocketRecipe>(FireworkRocketRecipe::new));
    public static final SimpleRecipeSerializer<FireworkStarRecipe> f_44083_ = RecipeSerializer.m_44098_("crafting_special_firework_star", new SimpleRecipeSerializer<FireworkStarRecipe>(FireworkStarRecipe::new));
    public static final SimpleRecipeSerializer<FireworkStarFadeRecipe> f_44084_ = RecipeSerializer.m_44098_("crafting_special_firework_star_fade", new SimpleRecipeSerializer<FireworkStarFadeRecipe>(FireworkStarFadeRecipe::new));
    public static final SimpleRecipeSerializer<TippedArrowRecipe> f_44085_ = RecipeSerializer.m_44098_("crafting_special_tippedarrow", new SimpleRecipeSerializer<TippedArrowRecipe>(TippedArrowRecipe::new));
    public static final SimpleRecipeSerializer<BannerDuplicateRecipe> f_44086_ = RecipeSerializer.m_44098_("crafting_special_bannerduplicate", new SimpleRecipeSerializer<BannerDuplicateRecipe>(BannerDuplicateRecipe::new));
    public static final SimpleRecipeSerializer<ShieldDecorationRecipe> f_44087_ = RecipeSerializer.m_44098_("crafting_special_shielddecoration", new SimpleRecipeSerializer<ShieldDecorationRecipe>(ShieldDecorationRecipe::new));
    public static final SimpleRecipeSerializer<ShulkerBoxColoring> f_44088_ = RecipeSerializer.m_44098_("crafting_special_shulkerboxcoloring", new SimpleRecipeSerializer<ShulkerBoxColoring>(ShulkerBoxColoring::new));
    public static final SimpleRecipeSerializer<SuspiciousStewRecipe> f_44089_ = RecipeSerializer.m_44098_("crafting_special_suspiciousstew", new SimpleRecipeSerializer<SuspiciousStewRecipe>(SuspiciousStewRecipe::new));
    public static final SimpleRecipeSerializer<RepairItemRecipe> f_44090_ = RecipeSerializer.m_44098_("crafting_special_repairitem", new SimpleRecipeSerializer<RepairItemRecipe>(RepairItemRecipe::new));
    public static final SimpleCookingSerializer<SmeltingRecipe> f_44091_ = RecipeSerializer.m_44098_("smelting", new SimpleCookingSerializer<SmeltingRecipe>(SmeltingRecipe::new, 200));
    public static final SimpleCookingSerializer<BlastingRecipe> f_44092_ = RecipeSerializer.m_44098_("blasting", new SimpleCookingSerializer<BlastingRecipe>(BlastingRecipe::new, 100));
    public static final SimpleCookingSerializer<SmokingRecipe> f_44093_ = RecipeSerializer.m_44098_("smoking", new SimpleCookingSerializer<SmokingRecipe>(SmokingRecipe::new, 100));
    public static final SimpleCookingSerializer<CampfireCookingRecipe> f_44094_ = RecipeSerializer.m_44098_("campfire_cooking", new SimpleCookingSerializer<CampfireCookingRecipe>(CampfireCookingRecipe::new, 100));
    public static final RecipeSerializer<StonecutterRecipe> f_44095_ = RecipeSerializer.m_44098_("stonecutting", new SingleItemRecipe.Serializer<StonecutterRecipe>(StonecutterRecipe::new));
    public static final RecipeSerializer<UpgradeRecipe> f_44096_ = RecipeSerializer.m_44098_("smithing", new UpgradeRecipe.Serializer());

    public T m_6729_(ResourceLocation var1, JsonObject var2);

    public T m_8005_(ResourceLocation var1, FriendlyByteBuf var2);

    public void m_6178_(FriendlyByteBuf var1, T var2);

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S m_44098_(String p_44099_, S p_44100_) {
        return (S)Registry.m_122961_(Registry.f_122865_, p_44099_, p_44100_);
    }
}

