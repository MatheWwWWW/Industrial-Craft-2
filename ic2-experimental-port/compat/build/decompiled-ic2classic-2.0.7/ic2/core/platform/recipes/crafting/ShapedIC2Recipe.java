/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.chars.CharArrayList
 *  it.unimi.dsi.fastutil.chars.CharList
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.NonNullList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.common.crafting.CraftingHelper
 *  net.minecraftforge.common.crafting.IShapedRecipe
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import ic2.api.items.electric.ElectricItem;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.IC2;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.platform.recipes.crafting.helpers.RecipeMods;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class ShapedIC2Recipe
extends RecipeIC2Base
implements IShapedRecipe<CraftingContainer> {
    private static final String RECIPE_CHARACTERS = "XYCVBNMKJHG";
    public int width;
    public int height;
    public IInput[] inputs;
    public int xOffset = 0;
    public int yOffset = 0;

    protected ShapedIC2Recipe(ResourceLocation id, ItemStack output, boolean hidden, boolean consume, IRecipeModifier mod, int width, int height, IInput[] inputs) {
        super(id, output, hidden, consume, mod);
        this.mod = mod;
        this.width = width;
        this.height = height;
        this.inputs = inputs;
    }

    public ShapedIC2Recipe(FriendlyByteBuf buffer, ResourceLocation id) {
        super(id, buffer.m_130267_(), buffer.readBoolean(), buffer.readBoolean(), IngredientRegistry.INSTANCE.readMod(buffer));
        this.width = buffer.readByte();
        this.height = buffer.readByte();
        this.inputs = new IInput[buffer.readByte()];
        for (int i = 0; i < this.inputs.length; ++i) {
            this.inputs[i] = IngredientRegistry.INSTANCE.readInput(buffer);
        }
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeItemStack(this.output, false);
        buffer.writeBoolean(this.hidden);
        buffer.writeBoolean(this.consume);
        IngredientRegistry.INSTANCE.writeMod(this.mod, buffer);
        buffer.writeByte(this.width);
        buffer.writeByte(this.height);
        buffer.writeByte(this.inputs.length);
        for (int i = 0; i < this.inputs.length; ++i) {
            IngredientRegistry.INSTANCE.writeInput(this.inputs[i], buffer);
        }
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "ic2:crafting_ic2_automated");
        obj.addProperty("sub_type", "ic2:shaped");
        obj.addProperty("enabled", Boolean.valueOf(true));
        obj.add("output", (JsonElement)IInput.writeItemStack(this.output));
        obj.addProperty("hidden", Boolean.valueOf(this.hidden));
        obj.addProperty("consume", Boolean.valueOf(this.consume));
        obj.addProperty("width", (Number)this.width);
        obj.addProperty("height", (Number)this.height);
        if (this.mod != null) {
            obj.add("modifier", (JsonElement)IngredientRegistry.INSTANCE.serializeModifier(this.mod));
        }
        CharList[] lanes = new CharList[this.height];
        for (int i = 0; i < this.height; ++i) {
            lanes[i] = this.getLines();
        }
        int index = 0;
        JsonArray list = new JsonArray();
        for (Map.Entry<IInput, IntList> entry : this.mapInputs().entrySet()) {
            char letter = RECIPE_CHARACTERS.charAt(index);
            JsonObject data = IngredientRegistry.INSTANCE.serializeInput(entry.getKey());
            data.addProperty("letter", Character.valueOf(letter));
            list.add((JsonElement)data);
            IntListIterator intListIterator = entry.getValue().iterator();
            while (intListIterator.hasNext()) {
                int slot = (Integer)intListIterator.next();
                lanes[slot / this.width].set(slot % this.width, letter);
            }
            ++index;
        }
        obj.add("inputs", (JsonElement)list);
        list = new JsonArray();
        for (int i = 0; i < lanes.length; ++i) {
            list.add(new String(lanes[i].toCharArray()));
        }
        obj.add("pattern", (JsonElement)list);
        return obj;
    }

    public static ShapedIC2Recipe create(ResourceLocation id, JsonObject object) {
        try {
            ItemStack stack = CraftingHelper.getItemStack((JsonObject)GsonHelper.m_13930_((JsonObject)object, (String)"output"), (boolean)true);
            if (stack.m_41619_()) {
                throw new IllegalStateException("Output is Empty!");
            }
            ObjectList result = CollectionUtils.createList();
            if (GsonHelper.m_13855_((JsonObject)object, (String)"hidden", (boolean)false)) {
                result.add((RecipeMods)RecipeMods.HIDDEN_RECIPE);
            }
            if (GsonHelper.m_13855_((JsonObject)object, (String)"consume", (boolean)false)) {
                result.add((RecipeMods)RecipeMods.CONSUME_CONTAINERS);
            }
            if (object.has("modifier")) {
                result.add((IRecipeModifier)IngredientRegistry.INSTANCE.readMod(GsonHelper.m_13930_((JsonObject)object, (String)"modifier")));
            }
            for (JsonElement element : GsonHelper.m_13933_((JsonObject)object, (String)"pattern")) {
                result.add((String)GsonHelper.m_13805_((JsonElement)element, (String)"pattern entry"));
            }
            for (JsonElement element : GsonHelper.m_13933_((JsonObject)object, (String)"inputs")) {
                JsonObject obj = GsonHelper.m_13918_((JsonElement)element, (String)"Input Entry");
                IInput input = IngredientRegistry.INSTANCE.readInput(obj);
                if (input == null) {
                    throw new IllegalStateException("Input not found!");
                }
                String letter = GsonHelper.m_13906_((JsonObject)obj, (String)"letter");
                if (letter.length() != 1) {
                    throw new JsonSyntaxException("letter needs to be a single character");
                }
                result.add((Character)Character.valueOf(letter.charAt(0)));
                result.add((IInput)input);
            }
            return ShapedIC2Recipe.create(id, stack, result.toArray(Object[]::new));
        }
        catch (Exception e) {
            throw new RuntimeException("failed to load " + id.toString(), e);
        }
    }

    private CharList getLines() {
        CharArrayList builder = new CharArrayList();
        for (int i = 0; i < this.width; ++i) {
            builder.add(' ');
        }
        return builder;
    }

    private Map<IInput, IntList> mapInputs() {
        Object2ObjectSortedMap<IInput, IntList> map = CollectionUtils.createLinkedMap();
        for (int i = 0; i < this.inputs.length; ++i) {
            IInput input = this.inputs[i];
            if (input == null) continue;
            map.computeIfAbsent(input, T -> new IntArrayList()).add(i);
        }
        return map;
    }

    @Override
    public ResourceLocation getMetaSerializer() {
        return new ResourceLocation("ic2", "shaped");
    }

    public boolean matches(CraftingContainer inv, Level worldIn) {
        for (int x = 0; x <= inv.m_39347_() - this.width; ++x) {
            for (int y = 0; y <= inv.m_39346_() - this.height; ++y) {
                if (!this.checkMatch(inv, x, y)) continue;
                this.xOffset = x;
                this.yOffset = y;
                return true;
            }
        }
        this.xOffset = -1;
        this.yOffset = -1;
        return false;
    }

    public ItemStack assemble(CraftingContainer inv) {
        int totalCharge = 0;
        ItemStack out = this.output.m_41777_();
        for (int i = 0; i < inv.m_6643_(); ++i) {
            ItemStack stack = inv.m_8020_(i);
            if (stack.m_41619_()) continue;
            if (this.mod != null && this.mod.isOutputItem(stack)) {
                out = stack.m_41777_();
                continue;
            }
            totalCharge += ElectricItem.MANAGER.getCharge(stack);
        }
        if (totalCharge > 0) {
            ElectricItem.MANAGER.charge(out, totalCharge, Integer.MAX_VALUE, true, false);
        }
        if (this.mod != null) {
            out = this.mod.applyChanges(out, false);
        }
        return out;
    }

    public boolean checkMatch(CraftingContainer inv, int xStart, int yStart) {
        if (this.mod != null) {
            this.mod.reset();
        }
        for (int x = 0; x < inv.m_39347_(); ++x) {
            for (int y = 0; y < inv.m_39346_(); ++y) {
                ItemStack slot;
                int subX = x - xStart;
                int subY = y - yStart;
                IInput target = null;
                if (subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {
                    target = this.inputs[subX + subY * this.width];
                }
                if ((slot = inv.m_8020_(x + y * inv.m_39347_())).m_41619_() && target != null || !slot.m_41619_() && target == null) {
                    return false;
                }
                if (target == null) continue;
                if (!target.matches(slot) || slot.m_41613_() < target.getInputSize()) {
                    return false;
                }
                if (this.mod == null || this.mod.isSlotValid(slot)) continue;
                return false;
            }
        }
        return true;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        if (this.xOffset < 0 || this.yOffset < 0) {
            return super.m_7457_((Container)inv);
        }
        IInput[] toRemove = new IInput[inv.m_6643_()];
        int max = 0;
        for (int i = 0; i < this.inputs.length; ++i) {
            IInput input = this.inputs[i];
            if (input == null) continue;
            int row = i / this.width + this.yOffset;
            int col = i % this.width + this.xOffset;
            int slot = row * inv.m_39347_() + col;
            if (input.getInputSize() <= 1 && !this.canBeDrained(inv.m_8020_(slot))) continue;
            toRemove[slot] = input;
            ++max;
        }
        if (max <= 0) {
            return super.m_7457_((Container)inv);
        }
        NonNullList result = NonNullList.m_122780_((int)inv.m_6643_(), (Object)ItemStack.f_41583_);
        for (int i = 0; i < toRemove.length; ++i) {
            IInput input = toRemove[i];
            if (input == null || this.canBeDrained(inv.m_8020_(i))) {
                if (input != null) {
                    result.set(i, (Object)StackUtil.copyWithSize(inv.m_8020_(i).getCraftingRemainingItem(), input.getInputSize()));
                    inv.m_8020_(i).m_41774_(input.getInputSize() - 1);
                    continue;
                }
                result.set(i, (Object)inv.m_8020_(i).getCraftingRemainingItem());
                continue;
            }
            ItemStack stack = inv.m_8020_(i).m_41777_();
            inv.m_6836_(i, ItemStack.f_41583_);
            result.set(i, (Object)stack);
            if (stack.m_41619_()) continue;
            stack.m_41774_(input.getInputSize());
        }
        return result;
    }

    public boolean m_8004_(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    public RecipeType<?> m_6671_() {
        return RecipeType.f_44107_;
    }

    public int getRecipeWidth() {
        return this.width;
    }

    public int getRecipeHeight() {
        return this.height;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        NonNullList result = NonNullList.m_122780_((int)this.inputs.length, (Object)Ingredient.f_43901_);
        for (int i = 0; i < this.inputs.length; ++i) {
            if (this.inputs[i] == null) continue;
            try {
                result.set(i, (Object)this.inputs[i].asIngredient());
                continue;
            }
            catch (Exception e) {
                IC2.LOGGER.info("Recipe id [" + this.m_6423_().toString() + "] has had a crash during Ingredient creation", (Throwable)e);
                throw e;
            }
        }
        return result;
    }

    public static ShapedIC2Recipe create(ResourceLocation id, ItemStack out, Object ... args) {
        ItemStack output = out.m_41777_();
        IInput[] inputs = null;
        IRecipeModifier mod = null;
        boolean hidden = false;
        boolean consume = false;
        int width = 0;
        int height = 0;
        ArrayList<String> template = new ArrayList<String>();
        Char2ObjectOpenHashMap charToInput = new Char2ObjectOpenHashMap();
        Character lastChar = null;
        for (int i = 0; i < args.length; ++i) {
            Iterator obj = args[i];
            if (obj == null) continue;
            if (obj instanceof String) {
                template.add((String)((Object)obj));
                continue;
            }
            if (obj instanceof Boolean) {
                hidden = (Boolean)((Object)obj);
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
                if (mod != null) {
                    throw new RuntimeException("Only 1 Mod is allowed");
                }
                mod = (IRecipeModifier)((Object)obj);
                continue;
            }
            if (obj instanceof Character) {
                if (lastChar != null) {
                    throw new RuntimeException("Expecting Input");
                }
                lastChar = (Character)((Object)obj);
                continue;
            }
            IInput input = RecipeRegistry.INGREDIENTS.createInputFrom(obj);
            if (input != null) {
                if (lastChar == null) {
                    throw new RuntimeException("Expecting Char");
                }
                charToInput.put(lastChar.charValue(), (Object)input);
                lastChar = null;
                continue;
            }
            throw new RuntimeException("Input is EMPTY");
        }
        if (template.size() == 0 || template.size() > 3) {
            throw new RuntimeException("Template is not Matching");
        }
        StringBuilder shape = new StringBuilder();
        width = -1;
        for (String key : template) {
            shape.append(key);
            if (width != -1 && width != key.length()) {
                throw new RuntimeException("All Template keys need to have the same length");
            }
            width = key.length();
            ++height;
        }
        if (width * height != shape.length()) {
            throw new RuntimeException("Template is not Matching");
        }
        inputs = new IInput[shape.length()];
        int slot = 0;
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                inputs[slot] = (IInput)charToInput.get(shape.charAt(slot));
                ++slot;
            }
        }
        return new ShapedIC2Recipe(id, output, hidden, consume, mod, width, height, inputs);
    }
}

