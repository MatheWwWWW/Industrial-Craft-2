/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import net.minecraft.world.item.Item;

public interface IFusionRecipeList
extends IListenableRegistry<IFusionRecipeList> {
    public void addFuel(Item var1, int var2, float var3, boolean var4);

    public FusionFuel getFuel(Item var1);

    public void removeFuel(Item var1);

    public List<FusionFuel> getFuels();

    public record FusionFuel(Item fuel, int fuelValue, float productionRate, boolean consumed) {
    }
}

