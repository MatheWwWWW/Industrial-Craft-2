/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.misc.ICanEffect;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IFoodCanRegistry {
    public Item registerEffect(ICanEffect var1);

    public ICanEffect getEffect(ResourceLocation var1);

    public List<ResourceLocation> getAllEffects();

    public Item getEffectItem(ResourceLocation var1);

    public void registerItemForEffect(ItemStack var1, ResourceLocation var2);

    public ResourceLocation getEffectForFood(ItemStack var1);

    default public Item getItemForFood(ItemStack food) {
        ResourceLocation location = this.getEffectForFood(food);
        return location == null ? null : this.getEffectItem(location);
    }
}

