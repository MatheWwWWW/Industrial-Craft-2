/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.registries;

import ic2.api.items.IRepairable;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ICannerRecipeRegistry
extends IListenableRegistry<ICannerRecipeRegistry> {
    public void registerRepairable(IRepairable var1, IInput var2, int var3);

    public void removeRepairable(IRepairable var1);

    public Tuple<IInput, Integer> getRepairableItem(ItemStack var1, ItemStack var2, boolean var3);

    public Map<Item, List<Tuple<IInput, Integer>>> getRepairItems();

    public void registerFuelValue(ItemStack var1, int var2);

    public void registerFuelMultiplier(ItemStack var1, float var2);

    public void removeValue(ItemStack var1);

    public FuelValue getValueForStack(ItemStack var1);

    public List<FuelValue> getFuels();

    public void registerFillable(ItemStack var1, IInput var2, ItemStack var3);

    public boolean hasContainer(ItemStack var1);

    public void removeFillable(ItemStack var1);

    public void removeFillable(ItemStack var1, ItemStack var2);

    public Tuple<IInput, ItemStack> getFillOutput(ItemStack var1, ItemStack var2, boolean var3);

    public Map<ItemStack, List<Tuple<IInput, ItemStack>>> getFillables();

    public static class FuelValue {
        ItemStack owner;
        int fuelValue;
        float fuelMultiplier;
        boolean multiplier;

        public FuelValue(FriendlyByteBuf buffer) {
            this.owner = buffer.m_130267_();
            this.multiplier = buffer.readBoolean();
            this.fuelValue = buffer.readInt();
            this.fuelMultiplier = buffer.readFloat();
        }

        public FuelValue(ItemStack owner, int fuelValue) {
            this(owner, fuelValue, 0.0f, false);
        }

        public FuelValue(ItemStack owner, float fuelMultiplier) {
            this(owner, 0, fuelMultiplier, true);
        }

        public FuelValue(ItemStack owner, int fuelValue, float fuelMultiplier, boolean multiplier) {
            this.owner = owner;
            this.fuelValue = fuelValue;
            this.fuelMultiplier = fuelMultiplier;
            this.multiplier = multiplier;
        }

        public int test(int base) {
            return this.multiplier ? (int)((float)base * this.fuelMultiplier) : this.fuelValue;
        }

        public int apply(int base) {
            return this.multiplier ? base + (int)((float)base * this.fuelMultiplier) : base + this.fuelValue;
        }

        public boolean isMultiplier() {
            return this.multiplier;
        }

        public float getFuelMultiplier() {
            return this.fuelMultiplier;
        }

        public int getFuelValue() {
            return this.fuelValue;
        }

        public ItemStack getStack() {
            return this.owner.m_41777_();
        }

        public void write(FriendlyByteBuf buffer) {
            buffer.m_130055_(this.owner);
            buffer.writeBoolean(this.multiplier);
            buffer.writeInt(this.fuelValue);
            buffer.writeFloat(this.fuelMultiplier);
        }
    }
}

