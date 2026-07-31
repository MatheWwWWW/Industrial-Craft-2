/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.common.crafting.IShapedRecipe
 */
package ic2.core.block.machines.logic.crafter;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.IngredientNBTFilter;
import ic2.core.inventory.inv.IC2CraftingInventory;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.Iterator;
import java.util.LinkedList;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class CraftRecipe
implements INetworkDataBuffer {
    static final CraftingContainer CRAFTING = new IC2CraftingInventory(3, 3);
    CraftingRecipe recipe;
    NonNullList<ItemStack> displayItems = NonNullList.m_122780_((int)9, (Object)ItemStack.f_41583_);
    ItemStack output = ItemStack.f_41583_;
    boolean isValid = false;
    public int flags = 4;
    int[] toDrain = new int[9];
    Object2ObjectMap<IFilter, int[]> filters = null;
    Object2ObjectMap<IFilter, ItemStack> displayFilters = null;

    public void consumeItems(int slot, int amount) {
        int n = slot;
        this.toDrain[n] = this.toDrain[n] + amount;
    }

    public int[] getToConsume() {
        int[] oldArray = this.toDrain;
        this.toDrain = new int[9];
        return oldArray;
    }

    public boolean validate(Level world) {
        this.recipe = this.getRecipe(world);
        this.isValid = this.recipe != null;
        if (this.isValid) {
            this.output = this.recipe.m_8043_().m_41777_();
            this.getFilters();
            return true;
        }
        return false;
    }

    public boolean isValid() {
        return this.recipe != null && this.isValid;
    }

    public Recipe<?> getRecipe() {
        return this.recipe;
    }

    public ItemStack getFilter(IFilter filter) {
        return this.displayFilters == null ? ItemStack.f_41583_ : (ItemStack)this.displayFilters.getOrDefault((Object)filter, (Object)ItemStack.f_41583_);
    }

    public Object2ObjectMap<IFilter, int[]> getFilters() {
        if (this.filters == null) {
            this.filters = CollectionUtils.createLinkedMap();
            this.displayFilters = CollectionUtils.createMap();
            if (this.recipe != null) {
                boolean nbt;
                boolean bl = nbt = (this.flags & 0x10) != 0;
                if (this.recipe instanceof IShapedRecipe) {
                    IShapedRecipe shaped = (IShapedRecipe)this.recipe;
                    int width = shaped.getRecipeWidth();
                    NonNullList items = shaped.m_7527_();
                    boolean done = false;
                    boolean success = false;
                    for (int xOff = 0; xOff < 3 && !done; ++xOff) {
                        for (int yOff = 0; yOff < 3 && !done; ++yOff) {
                            if (!((Ingredient)items.get(0)).test((ItemStack)this.displayItems.get(yOff * 3 + xOff))) continue;
                            boolean retry = false;
                            for (int i = 0; i < items.size(); ++i) {
                                int xSlot = i % width + xOff;
                                int ySlot = i / width + yOff;
                                if (xSlot < 0 || xSlot >= 3 || ySlot < 0 || ySlot >= 3) {
                                    retry = true;
                                    break;
                                }
                                ItemStack item = ((ItemStack)this.displayItems.get(xSlot + ySlot * 3)).m_41777_();
                                if (!((Ingredient)items.get(i)).test(item)) {
                                    retry = true;
                                    break;
                                }
                                if (item.m_41619_()) continue;
                                IngredientNBTFilter filter = new IngredientNBTFilter((Ingredient)items.get(i), item, false);
                                this.filters.put((Object)filter, (Object)new int[]{xSlot + ySlot * 3, item.m_41613_()});
                                this.displayFilters.put((Object)filter, (Object)item);
                            }
                            if (retry) continue;
                            done = true;
                            success = true;
                        }
                    }
                    if (!success) {
                        this.filters.clear();
                        this.displayFilters.clear();
                    }
                } else {
                    LinkedList items = new LinkedList(this.recipe.m_7527_());
                    block3: for (int i = 0; i < 9; ++i) {
                        if (((ItemStack)this.displayItems.get(i)).m_41619_()) continue;
                        Iterator iter = items.iterator();
                        while (iter.hasNext()) {
                            Ingredient ingredient = (Ingredient)iter.next();
                            if (!ingredient.test((ItemStack)this.displayItems.get(i))) continue;
                            ItemStack stack = ((ItemStack)this.displayItems.get(i)).m_41777_();
                            IngredientNBTFilter filter = new IngredientNBTFilter(ingredient, stack, nbt);
                            this.filters.put((Object)filter, (Object)new int[]{i, stack.m_41613_()});
                            this.displayFilters.put((Object)filter, (Object)stack);
                            iter.remove();
                            continue block3;
                        }
                    }
                    if (items.size() > 0) {
                        this.filters.clear();
                        this.displayFilters.clear();
                    }
                }
            }
        }
        return this.filters;
    }

    protected boolean tryMatchShapedRecipe(int xOff, int yOff, int width, int height, boolean nbt, NonNullList<Ingredient> items) {
        for (int xPos = 0; xPos < width; ++xPos) {
            for (int yPos = 0; yPos < width; ++yPos) {
                int index = yPos * width + xPos;
                if (items.size() <= index) continue;
                int subIndex = (yOff + yPos) * width + (xOff + xPos);
                if (!((Ingredient)items.get(index)).test((ItemStack)this.displayItems.get(subIndex))) {
                    return false;
                }
                if (((ItemStack)this.displayItems.get(subIndex)).m_41619_()) continue;
                ItemStack stack = ((ItemStack)this.displayItems.get(subIndex)).m_41777_();
                IngredientNBTFilter filter = new IngredientNBTFilter((Ingredient)items.get(index), stack, nbt);
                this.filters.put((Object)filter, (Object)new int[]{subIndex, stack.m_41613_()});
                this.displayFilters.put((Object)filter, (Object)stack);
            }
        }
        return true;
    }

    public ItemStack getDisplayItem() {
        return this.output;
    }

    public ItemStack getOutput(CraftingContainer inventory, Level world) {
        if (this.recipe != null && this.recipe.m_5818_((Container)inventory, world)) {
            return this.recipe.m_5874_((Container)inventory).m_41777_();
        }
        return ItemStack.f_41583_;
    }

    public ItemStack getFakeOutput(Level world) {
        for (int i = 0; i < 9; ++i) {
            CRAFTING.m_6836_(i, ((ItemStack)this.displayItems.get(i)).m_41777_());
        }
        ItemStack output = this.recipe.m_5874_((Container)CRAFTING).m_41777_();
        CRAFTING.m_6211_();
        return output;
    }

    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventory, Level world) {
        if (this.recipe != null && this.recipe.m_5818_((Container)inventory, world)) {
            return this.recipe.m_7457_((Container)inventory);
        }
        return NonNullList.m_122780_((int)inventory.m_6643_(), (Object)ItemStack.f_41583_);
    }

    protected CraftingRecipe getRecipe(Level world) {
        if (!this.isValid) {
            return null;
        }
        for (int i = 0; i < 9; ++i) {
            CRAFTING.m_6836_(i, ((ItemStack)this.displayItems.get(i)).m_41777_());
        }
        if (this.recipe != null && this.recipe.m_5818_((Container)CRAFTING, world)) {
            return this.recipe;
        }
        return IC2.PLATFORM.getRecipes().m_44015_(RecipeType.f_44107_, (Container)CRAFTING, world).orElse(null);
    }

    public void applyTo(IHasInventory inventory, Level world) {
        for (int i = 0; i < 9; ++i) {
            inventory.setStackInSlot(i, ((ItemStack)this.displayItems.get(i)).m_41777_());
        }
        inventory.setStackInSlot(9, this.getFakeOutput(world).m_41777_());
    }

    public void setRecipe(CraftingRecipe recipe, IHasInventory inv) {
        for (int i = 0; i < 9; ++i) {
            this.displayItems.set(i, (Object)inv.getStackInSlot(i).m_41777_());
        }
        this.recipe = recipe;
        this.isValid = true;
        this.filters = null;
        this.displayFilters = null;
        this.output = recipe.m_8043_().m_41777_();
    }

    public void clear() {
        for (int i = 0; i < 9; ++i) {
            this.displayItems.set(i, (Object)ItemStack.f_41583_);
        }
        this.displayFilters = null;
        this.filters = null;
        this.recipe = null;
        this.output = ItemStack.f_41583_;
        this.isValid = false;
    }

    public void clearFilters() {
        this.filters = null;
        this.displayFilters = null;
    }

    public CraftRecipe copy() {
        CraftRecipe newRecipe = new CraftRecipe();
        newRecipe.readRecipe(this.writeRecipe(new CompoundTag()).m_6426_());
        return newRecipe;
    }

    public CompoundTag writeRecipe(CompoundTag nbt) {
        nbt.m_128379_("valid", this.isValid);
        nbt.m_128359_("recipe", this.recipe == null ? "empty" : this.recipe.m_6423_().toString());
        nbt.m_128344_("flags", (byte)this.flags);
        if (!this.isValid) {
            return nbt;
        }
        NBTUtils.saveItems(nbt, "items", this.displayItems);
        return nbt;
    }

    public void readRecipe(CompoundTag nbt) {
        this.isValid = nbt.m_128471_("valid");
        String recipeID = nbt.m_128461_("recipe");
        this.flags = nbt.m_128451_("flags");
        this.recipe = null;
        if (!recipeID.equalsIgnoreCase("empty")) {
            this.recipe = IC2.PLATFORM.getRecipes().m_44043_(new ResourceLocation(recipeID)).orElse(null);
        }
        this.displayItems.clear();
        if (!this.isValid) {
            return;
        }
        NBTUtils.loadItems(nbt, "items", this.displayItems);
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte((byte)this.flags);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.flags = buffer.readByte();
    }
}

