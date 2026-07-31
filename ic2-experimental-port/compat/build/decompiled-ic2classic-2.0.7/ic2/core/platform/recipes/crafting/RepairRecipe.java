/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.items.IRepairable;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.IC2;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.platform.recipes.crafting.helpers.RecipeMods;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class RepairRecipe
extends RecipeIC2Base {
    IRepairable repair;
    IInput repairMaterial;
    int amount;

    public RepairRecipe(FriendlyByteBuf buffer, ResourceLocation id) {
        super(id, buffer.m_130267_(), buffer.readBoolean(), buffer.readBoolean(), null);
        this.repair = (IRepairable)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
        this.repairMaterial = IngredientRegistry.INSTANCE.readInput(buffer);
        this.amount = buffer.readInt();
    }

    protected RepairRecipe(ResourceLocation id, boolean hidden, boolean consume, IRepairable input, IInput repairMaterial, int amount) {
        super(id, new ItemStack((ItemLike)input), hidden, consume, null);
        this.repair = input;
        this.repairMaterial = repairMaterial;
        this.amount = amount;
    }

    public boolean matches(CraftingContainer inv, Level worldIn) {
        return !this.getResult(inv).m_41619_();
    }

    public ItemStack assemble(CraftingContainer inv) {
        return this.getResult(inv);
    }

    public ItemStack getResult(CraftingContainer inv) {
        ItemStack found = ItemStack.f_41583_;
        int amount = 0;
        int m = inv.m_6643_();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inv.m_8020_(i);
            if (stack.m_41720_() == this.repair) {
                if (found.m_41619_()) {
                    found = stack;
                    continue;
                }
                return ItemStack.f_41583_;
            }
            if (!this.repairMaterial.matches(stack) || stack.m_41613_() < this.repairMaterial.getInputSize()) continue;
            ++amount;
        }
        if (found.m_41619_() || amount <= 0) {
            return ItemStack.f_41583_;
        }
        return this.repair.repairDamage(found = found.m_41777_(), amount * this.amount) ? found : ItemStack.f_41583_;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        IntOpenHashSet slotsToClear = new IntOpenHashSet(inv.m_6643_());
        int chargeIndex = -1;
        int m = inv.m_6643_();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inv.m_8020_(i);
            if (stack.m_41720_() == this.repair) {
                if (chargeIndex == -1) {
                    chargeIndex = i;
                    continue;
                }
                return NonNullList.m_122779_();
            }
            if (!this.repairMaterial.matches(stack) || stack.m_41613_() < this.repairMaterial.getInputSize()) continue;
            slotsToClear.add(i);
        }
        if (chargeIndex == -1 || slotsToClear.size() <= 0) {
            return super.m_7457_((Container)inv);
        }
        NonNullList array = NonNullList.m_122780_((int)inv.m_6643_(), (Object)ItemStack.f_41583_);
        int m2 = inv.m_6643_();
        for (int i = 0; i < m2; ++i) {
            if (i == chargeIndex) continue;
            if (slotsToClear.contains(i)) {
                inv.m_7407_(i, this.repairMaterial.getInputSize());
                array.set(i, (Object)inv.m_8020_(i).m_41777_());
                inv.m_6836_(i, ItemStack.f_41583_);
                continue;
            }
            array.set(i, (Object)inv.m_8020_(i).getCraftingRemainingItem());
        }
        return array;
    }

    public boolean m_8004_(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ResourceLocation getMetaSerializer() {
        return new ResourceLocation("ic2", "repair");
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.output, false);
        buffer.writeBoolean(this.hidden);
        buffer.writeBoolean(this.consume);
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)this.repair.m_5456_());
        IngredientRegistry.INSTANCE.writeInput(this.repairMaterial, buffer);
        buffer.writeInt(this.amount);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "ic2:crafting_ic2_automated");
        obj.addProperty("sub_type", "ic2:repair");
        obj.addProperty("enabled", Boolean.valueOf(true));
        obj.addProperty("repair", ForgeRegistries.ITEMS.getKey((Object)this.repair.m_5456_()).toString());
        obj.add("material", (JsonElement)IngredientRegistry.INSTANCE.serializeInput(this.repairMaterial));
        obj.addProperty("size", (Number)this.amount);
        obj.addProperty("hidden", Boolean.valueOf(this.hidden));
        obj.addProperty("consume", Boolean.valueOf(this.consume));
        return obj;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        NonNullList result = NonNullList.m_122779_();
        result.add((Object)Ingredient.m_43929_((ItemLike[])new ItemLike[]{this.repair}));
        try {
            result.add((Object)this.repairMaterial.asIngredient());
        }
        catch (Exception e) {
            IC2.LOGGER.info("Recipe id [" + this.m_6423_().toString() + "] has had a crash during Ingredient creation", (Throwable)e);
            throw e;
        }
        return result;
    }

    public static RepairRecipe create(ResourceLocation id, JsonObject object) {
        Item item = (Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)GsonHelper.m_13906_((JsonObject)object, (String)"repair")));
        if (!(item instanceof IRepairable)) {
            IC2.LOGGER.error(GsonHelper.m_13906_((JsonObject)object, (String)"repair") + ", is not a IRepaireable Item");
            return null;
        }
        IRepairable repair = (IRepairable)item;
        IInput input = IngredientRegistry.INSTANCE.readInput(GsonHelper.m_13930_((JsonObject)object, (String)"material"));
        boolean hidden = GsonHelper.m_13855_((JsonObject)object, (String)"hidden", (boolean)false);
        boolean consume = GsonHelper.m_13855_((JsonObject)object, (String)"consume", (boolean)false);
        int amount = GsonHelper.m_13927_((JsonObject)object, (String)"size");
        return new RepairRecipe(id, hidden, consume, repair, input, amount);
    }

    public static RepairRecipe create(ResourceLocation id, IRepairable input, IInput repairMaterial, int effect, Object ... mods) {
        boolean hidden = false;
        boolean consume = false;
        block4: for (Object obj : mods) {
            if (obj == null) continue;
            if (obj instanceof Boolean) {
                hidden = (Boolean)obj;
                continue;
            }
            if (!(obj instanceof RecipeMods)) continue;
            switch ((RecipeMods)((Object)obj)) {
                case CONSUME_CONTAINERS: {
                    consume = true;
                    continue block4;
                }
                case HIDDEN_RECIPE: {
                    hidden = true;
                }
            }
        }
        return new RepairRecipe(id, hidden, consume, input, repairMaterial, effect);
    }
}

