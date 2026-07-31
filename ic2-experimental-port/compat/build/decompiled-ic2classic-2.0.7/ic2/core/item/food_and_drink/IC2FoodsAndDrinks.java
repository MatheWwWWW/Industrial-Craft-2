/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.food.FoodProperties$Builder
 */
package ic2.core.item.food_and_drink;

import ic2.api.items.IDrinkableFluid;
import ic2.core.item.food_and_drink.IC2Drink;
import ic2.core.item.food_and_drink.drinks.Beer;
import ic2.core.item.food_and_drink.drinks.BlackTea;
import ic2.core.item.food_and_drink.drinks.Cocoa;
import ic2.core.item.food_and_drink.drinks.Coffee;
import ic2.core.item.food_and_drink.drinks.ColdBlackTea;
import ic2.core.item.food_and_drink.drinks.ColdDarkCoffee;
import ic2.core.item.food_and_drink.drinks.ColdFruitTea;
import ic2.core.item.food_and_drink.drinks.ColdGreenTea;
import ic2.core.item.food_and_drink.drinks.ColdTea;
import ic2.core.item.food_and_drink.drinks.DarkCoffee;
import ic2.core.item.food_and_drink.drinks.FruitTea;
import ic2.core.item.food_and_drink.drinks.GreenTea;
import ic2.core.item.food_and_drink.drinks.HotCocoa;
import ic2.core.item.food_and_drink.drinks.IcedTea;
import ic2.core.item.food_and_drink.drinks.Milk;
import ic2.core.item.food_and_drink.drinks.Rum;
import ic2.core.item.food_and_drink.drinks.Tea;
import ic2.core.item.food_and_drink.drinks.Whisky;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class IC2FoodsAndDrinks {
    public static final List<IC2Drink> CONTAINERS = CollectionUtils.createList();
    public static final IDrinkableFluid TEA = new Tea();
    public static final IDrinkableFluid COLD_TEA = new ColdTea();
    public static final IDrinkableFluid COLD_BLACK_TEA = new ColdBlackTea();
    public static final IDrinkableFluid BLACK_TEA = new BlackTea();
    public static final IDrinkableFluid ICED_TEA = new IcedTea();
    public static final IDrinkableFluid FRUIT_TEA = new FruitTea();
    public static final IDrinkableFluid COLD_FRUIT_TEA = new ColdFruitTea();
    public static final IDrinkableFluid GREEN_TEA = new GreenTea();
    public static final IDrinkableFluid COLD_GREEN_TEA = new ColdGreenTea();
    public static final IDrinkableFluid COFFEE = new Coffee(new ResourceLocation("ic2", "coffee"));
    public static final IDrinkableFluid COLD_DARK_COFFEE = new ColdDarkCoffee(new ResourceLocation("ic2", "cold_dark_coffee"));
    public static final IDrinkableFluid DARK_COFFEE = new DarkCoffee(new ResourceLocation("ic2", "dark_coffee"));
    public static final IDrinkableFluid MILK = new Milk();
    public static final IDrinkableFluid COCOA = new Cocoa();
    public static final IDrinkableFluid HOT_COCOA = new HotCocoa();
    public static final IDrinkableFluid RUM = new Rum(new ResourceLocation("ic2", "rum"));
    public static final IDrinkableFluid BEER = new Beer(new ResourceLocation("ic2", "beer"));
    public static final IDrinkableFluid WHISKY = new Whisky();
    public static final FoodProperties CHOCOLATE_CAKE = new FoodProperties.Builder().m_38766_().m_38765_().m_38760_(2).m_38758_(0.2f).m_38767_();
    public static final FoodProperties COFFEE_CAKE = new FoodProperties.Builder().m_38766_().m_38765_().m_38760_(1).m_38758_(0.5f).effect(() -> new MobEffectInstance(MobEffects.f_19596_, 400, 1), 1.0f).effect(() -> new MobEffectInstance(MobEffects.f_19598_, 400, 1), 1.0f).m_38767_();
    public static final FoodProperties HONEY_CAKE = new FoodProperties.Builder().m_38766_().m_38765_().m_38760_(3).m_38758_(0.1f).m_38767_();
    public static final FoodProperties LEMON_CAKE = new FoodProperties.Builder().m_38766_().m_38765_().m_38760_(1).m_38758_(2.0f).m_38767_();
    public static final FoodProperties CHOCOLATE = new FoodProperties.Builder().m_38760_(1).m_38758_(5.0f).m_38767_();
    public static final FoodProperties FRIES = new FoodProperties.Builder().m_38760_(1).m_38758_(0.5f).m_38765_().m_38767_();
    public static final FoodProperties CHIPS = new FoodProperties.Builder().m_38760_(8).m_38758_(0.0f).m_38766_().m_38767_();

    public static void fillContainerList() {
        CONTAINERS.add((IC2Drink)IC2Items.MUG);
        CONTAINERS.add((IC2Drink)IC2Items.GLASS);
    }
}

