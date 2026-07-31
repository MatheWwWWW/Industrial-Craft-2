/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.food_and_drink;

import ic2.api.items.IDrinkableFluid;
import ic2.core.item.food_and_drink.IC2Drink;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.features.item.IItemModel;
import ic2.core.utils.collection.CollectionUtils;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class MugItem
extends IC2Drink
implements IItemModel {
    public static final Map<IDrinkableFluid, Item> FILLED_CONTAINER = CollectionUtils.createMap();

    public MugItem() {
        super("mug");
    }

    public MugItem(String name, IDrinkableFluid fluid) {
        super(name, fluid);
        if (fluid == null) {
            throw new IllegalStateException("Null Fluid is now Allowed!");
        }
        if (FILLED_CONTAINER.put(fluid, this) != null) {
            throw new IllegalStateException("Duplicated Drinks are not allowed! [Container=" + this.getRegistryName() + ", Drink=" + fluid.getID() + "]");
        }
    }

    @Override
    public ItemStack fillWith(IDrinkableFluid fluid, int amount) {
        return new ItemStack((ItemLike)FILLED_CONTAINER.getOrDefault(fluid, Items.f_41852_), amount);
    }

    @Override
    public ItemStack getEmptyContainer() {
        return new ItemStack((ItemLike)IC2Items.MUG);
    }

    @Override
    public String getBasePath(ItemStack stack) {
        return "item/food_and_drink/mug";
    }

    @Override
    public Map<IDrinkableFluid, Item> getFilledContainers() {
        return FILLED_CONTAINER;
    }
}

