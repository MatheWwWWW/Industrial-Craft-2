/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.common.crafting.CraftingHelper
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.api.items.electric.ElectricItem;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.IC2;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.platform.recipes.crafting.helpers.RecipeMods;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

public class ShapelessIC2Recipe
extends RecipeIC2Base {
    List<IInput> inputs = new ArrayList<IInput>();

    protected ShapelessIC2Recipe(ResourceLocation id, ItemStack output, boolean hidden, boolean consume, IRecipeModifier mod, List<IInput> inputs) {
        super(id, output, hidden, consume, mod);
        this.inputs.addAll(inputs);
    }

    public ShapelessIC2Recipe(FriendlyByteBuf buffer, ResourceLocation id) {
        super(id, buffer.m_130267_(), buffer.readBoolean(), buffer.readBoolean(), IngredientRegistry.INSTANCE.readMod(buffer));
        int size = buffer.readByte();
        for (int i = 0; i < size; ++i) {
            IInput input = IngredientRegistry.INSTANCE.readInput(buffer);
            if (input == null) continue;
            this.inputs.add(input);
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.output, false);
        buffer.writeBoolean(this.hidden);
        buffer.writeBoolean(this.consume);
        IngredientRegistry.INSTANCE.writeMod(this.mod, buffer);
        buffer.writeByte(this.inputs.size());
        for (int i = 0; i < this.inputs.size(); ++i) {
            IngredientRegistry.INSTANCE.writeInput(this.inputs.get(i), buffer);
        }
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "ic2:crafting_ic2_automated");
        obj.addProperty("sub_type", "ic2:shapeless");
        obj.addProperty("enabled", Boolean.valueOf(true));
        obj.add("output", (JsonElement)IInput.writeItemStack(this.output));
        obj.addProperty("hidden", Boolean.valueOf(this.hidden));
        obj.addProperty("consume", Boolean.valueOf(this.consume));
        if (this.mod != null) {
            obj.add("modifier", (JsonElement)IngredientRegistry.INSTANCE.serializeModifier(this.mod));
        }
        JsonArray list = new JsonArray();
        for (IInput input : this.inputs) {
            list.add((JsonElement)IngredientRegistry.INSTANCE.serializeInput(input));
        }
        obj.add("inputs", (JsonElement)list);
        return obj;
    }

    public boolean matches(CraftingContainer inv, Level worldIn) {
        return !this.assemble(inv).m_41619_();
    }

    public ItemStack assemble(CraftingContainer inv) {
        if (this.mod != null) {
            this.mod.reset();
        }
        ItemStack out = this.output.m_41777_();
        int totalCharge = 0;
        LinkedList<IInput> toFind = new LinkedList<IInput>(this.inputs);
        for (int i = 0; i < inv.m_6643_(); ++i) {
            ItemStack stack = inv.m_8020_(i);
            if (stack.m_41619_()) continue;
            Iterator iter = toFind.iterator();
            boolean inRecipe = false;
            while (iter.hasNext()) {
                IInput in = (IInput)iter.next();
                if (!in.matches(stack) || in.getInputSize() > stack.m_41613_() || this.mod != null && !this.mod.isSlotValid(stack)) continue;
                iter.remove();
                inRecipe = true;
                break;
            }
            if (!inRecipe) {
                return ItemStack.f_41583_;
            }
            if (this.mod != null && this.mod.isOutputItem(stack)) {
                out = stack.m_41777_();
                continue;
            }
            totalCharge += ElectricItem.MANAGER.getCharge(stack);
        }
        if (toFind.size() > 0) {
            return ItemStack.f_41583_;
        }
        if (totalCharge > 0) {
            ElectricItem.MANAGER.charge(out, totalCharge, Integer.MAX_VALUE, true, false);
        }
        if (this.mod != null) {
            out = this.mod.applyChanges(out, false);
        }
        return out;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        LinkedList<IInput> list = new LinkedList<IInput>(this.inputs);
        int[] slot = new int[inv.m_6643_()];
        int maxSlot = 0;
        block0: for (int i = 0; i < inv.m_6643_(); ++i) {
            ItemStack stack = inv.m_8020_(i);
            if (stack.m_41619_()) continue;
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                IInput in = (IInput)iter.next();
                if (!in.matches(stack) || in.getInputSize() > stack.m_41613_()) continue;
                if (in.getInputSize() > 1) {
                    slot[i] = in.getInputSize();
                    ++maxSlot;
                }
                iter.remove();
                continue block0;
            }
        }
        if (maxSlot <= 0) {
            return super.m_7457_((Container)inv);
        }
        NonNullList result = NonNullList.m_122780_((int)slot.length, (Object)ItemStack.f_41583_);
        for (int i = 0; i < slot.length; ++i) {
            if (slot[i] < 1) {
                result.set(i, (Object)inv.m_8020_(i).getCraftingRemainingItem());
                continue;
            }
            ItemStack stack = inv.m_8020_(i).m_41777_();
            inv.m_6836_(i, ItemStack.f_41583_);
            result.set(i, (Object)stack);
            if (stack.m_41619_()) continue;
            stack.m_41774_(slot[i]);
        }
        return result;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        NonNullList results = NonNullList.m_122780_((int)this.inputs.size(), (Object)Ingredient.f_43901_);
        for (int i = 0; i < this.inputs.size(); ++i) {
            try {
                results.set(i, (Object)this.inputs.get(i).asIngredient());
                continue;
            }
            catch (Exception e) {
                IC2.LOGGER.info("Recipe id [" + this.m_6423_().toString() + "] has had a crash during Ingredient creation", (Throwable)e);
                throw e;
            }
        }
        return results;
    }

    public boolean m_8004_(int width, int height) {
        return width * height >= this.inputs.size();
    }

    @Override
    public ResourceLocation getMetaSerializer() {
        return new ResourceLocation("ic2", "shapeless");
    }

    public static ShapelessIC2Recipe create(ResourceLocation id, JsonObject object) {
        ItemStack stack = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)object, (String)"output"), (boolean)true);
        if (stack.m_41619_()) {
            return null;
        }
        ObjectList inputs = CollectionUtils.createList();
        boolean hidden = GsonHelper.m_13855_((JsonObject)object, (String)"hidden", (boolean)false);
        boolean consume = GsonHelper.m_13855_((JsonObject)object, (String)"consume", (boolean)false);
        IRecipeModifier mod = object.has("modifier") ? IngredientRegistry.INSTANCE.readMod(GsonHelper.m_13930_((JsonObject)object, (String)"modifier")) : null;
        for (JsonElement el : GsonHelper.m_13933_((JsonObject)object, (String)"inputs")) {
            inputs.add((IInput)IngredientRegistry.INSTANCE.readInput(GsonHelper.m_13918_((JsonElement)el, (String)"input Entry")));
        }
        return new ShapelessIC2Recipe(id, stack, hidden, consume, mod, (List<IInput>)inputs);
    }

    public static ShapelessIC2Recipe create(ResourceLocation id, ItemStack out, Object ... args) {
        ItemStack output = out.m_41777_();
        ArrayList<IInput> inputs = new ArrayList<IInput>();
        IRecipeModifier mod = null;
        boolean hidden = false;
        boolean consume = false;
        for (int i = 0; i < args.length; ++i) {
            Object obj = args[i];
            if (obj == null) continue;
            if (obj instanceof Boolean) {
                hidden = (Boolean)obj;
                continue;
            }
            if (obj instanceof RecipeMods) {
                switch ((RecipeMods)((Object)obj)) {
                    case CONSUME_CONTAINERS: {
                        consume = true;
                        break;
                    }
                    case HIDDEN_RECIPE: {
                        hidden = true;
                    }
                }
                continue;
            }
            if (obj instanceof IRecipeModifier) {
                mod = (IRecipeModifier)obj;
                continue;
            }
            IInput input = RecipeRegistry.INGREDIENTS.createInputFrom(obj);
            if (input == null) continue;
            inputs.add(input);
        }
        return new ShapelessIC2Recipe(id, output, hidden, consume, mod, inputs);
    }
}

