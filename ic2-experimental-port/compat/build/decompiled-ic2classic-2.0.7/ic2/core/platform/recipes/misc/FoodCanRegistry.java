/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.platform.recipes.misc;

import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.misc.ICanEffect;
import ic2.api.recipes.registries.IFoodCanRegistry;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.item.food_and_drink.TinCanItem;
import ic2.core.platform.recipes.misc.ChorusFruitEffect;
import ic2.core.platform.recipes.misc.IC2SimpleCanEffect;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.SimpleRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class FoodCanRegistry
implements IFoodCanRegistry {
    public static final ResourceLocation EMPTY = new ResourceLocation("empty");
    public static final FoodCanRegistry INSTANCE = new FoodCanRegistry();
    SimpleRegistry<TinCanItem> registry = new SimpleRegistry();
    Map<ItemStack, ResourceLocation> ids = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);

    public void preInit() {
        RecipeRegistry.CAN_EFFECTS = INSTANCE;
    }

    public void init() {
        this.registry.register(EMPTY, (TinCanItem)IC2Items.TIN_CAN_FILLED);
        String textureLocation = "food_and_drink/tincan";
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42583_), new ResourceLocation("ic2", "bad"));
        IC2SimpleCanEffect badTinCan = new IC2SimpleCanEffect(new ResourceLocation("ic2", "bad"), new MobEffectInstance(MobEffects.f_19604_, 150), new MobEffectInstance(MobEffects.f_19612_, 150, 0));
        badTinCan.setTexture(textureLocation, "bad_tin_can");
        badTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.bad"));
        badTinCan.setRandom(0.8f);
        this.registerEffect(badTinCan);
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42591_), new ResourceLocation("ic2", "poison"));
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42675_), new ResourceLocation("ic2", "poison"));
        IC2SimpleCanEffect poisonTinCan = new IC2SimpleCanEffect(new ResourceLocation("ic2", "poison"), new MobEffectInstance(MobEffects.f_19614_, 50, 0));
        poisonTinCan.setTexture(textureLocation, "bad_tin_can");
        poisonTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.poison"));
        this.registerEffect(poisonTinCan);
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42581_), new ResourceLocation("ic2", "chicken"));
        IC2SimpleCanEffect chickenTinCan = new IC2SimpleCanEffect(new ResourceLocation("ic2", "chicken"), new MobEffectInstance(MobEffects.f_19612_, 300, 0));
        chickenTinCan.setTexture(textureLocation, "chicken_tin_can");
        chickenTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.chicken"));
        chickenTinCan.setRandom(0.3f);
        this.registerEffect(chickenTinCan);
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42436_), new ResourceLocation("ic2", "golden_apple"));
        IC2SimpleCanEffect goldenAppleTinCan = new IC2SimpleCanEffect(new ResourceLocation("ic2", "golden_apple"), new MobEffectInstance(MobEffects.f_19605_, 25, 1), new MobEffectInstance(MobEffects.f_19617_, 600, 0));
        goldenAppleTinCan.setTexture(textureLocation, "golden_tin_can");
        goldenAppleTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.golden_apple"));
        this.registerEffect(goldenAppleTinCan);
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42437_), new ResourceLocation("ic2", "enchanted_golden_apple"));
        IC2SimpleCanEffect enchantedGoldenAppleTinCan = new IC2SimpleCanEffect(new ResourceLocation("ic2", "enchanted_golden_apple"), new MobEffectInstance(MobEffects.f_19605_, 100, 1), new MobEffectInstance(MobEffects.f_19606_, 1500, 0), new MobEffectInstance(MobEffects.f_19607_, 1500, 0), new MobEffectInstance(MobEffects.f_19617_, 600, 3));
        enchantedGoldenAppleTinCan.setTexture(textureLocation, "golden_tin_can");
        enchantedGoldenAppleTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.enchanted_golden_apple"));
        this.registerEffect(enchantedGoldenAppleTinCan);
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42730_), new ResourceLocation("ic2", "chorus_fruit"));
        ChorusFruitEffect chorusTinCan = new ChorusFruitEffect(new ResourceLocation("ic2", "chorus_fruit"));
        chorusTinCan.setTexture(textureLocation, "chorus_tin_can");
        chorusTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.chorus_fruit"));
        this.registerEffect(chorusTinCan);
        this.registerItemForEffect(new ItemStack((ItemLike)Items.f_42529_), new ResourceLocation("ic2", "pufferfish"));
        IC2SimpleCanEffect pufferfishTinCan = new IC2SimpleCanEffect(new ResourceLocation("ic2", "pufferfish"), new MobEffectInstance(MobEffects.f_19614_, 1200, 3), new MobEffectInstance(MobEffects.f_19612_, 300, 2), new MobEffectInstance(MobEffects.f_19604_, 300, 1));
        pufferfishTinCan.setTexture(textureLocation, "bad_tin_can");
        pufferfishTinCan.setTooltip((Component)Component.m_237115_((String)"tooltip.item.ic2.filled_tin_can.pufferfish"));
        this.registerEffect(pufferfishTinCan);
    }

    @Override
    public Item registerEffect(ICanEffect effect) {
        if (effect.getID().equals((Object)EMPTY)) {
            IC2.LOGGER.info("Failed");
            return null;
        }
        TinCanItem item = new TinCanItem(effect);
        IC2Items.registerItem(item);
        this.registry.register(effect.getID(), item);
        return item;
    }

    @Override
    public ICanEffect getEffect(ResourceLocation location) {
        TinCanItem item = this.registry.getValue(location);
        return item == null ? null : item.getEffect();
    }

    @Override
    public List<ResourceLocation> getAllEffects() {
        return new ObjectArrayList(this.registry.keySet());
    }

    @Override
    public void registerItemForEffect(ItemStack stack, ResourceLocation location) {
        this.ids.put(stack.m_41777_(), location);
    }

    @Override
    public ResourceLocation getEffectForFood(ItemStack stack) {
        return this.ids.get(stack);
    }

    @Override
    public Item getEffectItem(ResourceLocation item) {
        return this.registry.getValue(item);
    }
}

