/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.RecipesArmorDyes
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.common.registry.ForgeRegistries
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.recipe.RecipeInputBase;
import ic2.core.util.Ic2Color;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ArmorDyeingRecipe
extends RecipesArmorDyes {
    private static final Map<IRecipeInput, int[]> stackToRGB = ArmorDyeingRecipe.buildDyeMap();
    protected final IRecipeInput armour;

    private static Map<IRecipeInput, int[]> buildDyeMap() {
        HashMap<IRecipeInput, int[]> ret = new HashMap<IRecipeInput, int[]>();
        for (Ic2Color colour : Ic2Color.values) {
            float[] dyeMap = colour.mcColor.func_193349_f();
            assert (dyeMap != null);
            ret.put(Recipes.inputFactory.forOreDict(colour.oreDictDyeName), new int[]{(int)(dyeMap[0] * 255.0f), (int)(dyeMap[1] * 255.0f), (int)(dyeMap[2] * 255.0f)});
        }
        return ret;
    }

    public ArmorDyeingRecipe(ItemStack armour) {
        this(Recipes.inputFactory.forStack(armour));
        if (StackUtil.isEmpty(armour) || !(armour.func_77973_b() instanceof ItemArmor)) {
            throw new IllegalArgumentException("Invalid input stack: " + StackUtil.toStringSafe(armour));
        }
    }

    public ArmorDyeingRecipe(Class<? extends ItemArmor> type) {
        this(new RecipeInputClass(type));
        if (type == null || !ItemArmor.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Invalid input class: " + type);
        }
    }

    public ArmorDyeingRecipe(IRecipeInput input) {
        this.armour = input;
    }

    public static boolean isDye(ItemStack stack) {
        for (IRecipeInput input : stackToRGB.keySet()) {
            if (!input.matches(stack)) continue;
            return true;
        }
        return false;
    }

    public static int[] getColourForStack(ItemStack stack) {
        for (Map.Entry<IRecipeInput, int[]> entry : stackToRGB.entrySet()) {
            if (!entry.getKey().matches(stack)) continue;
            return entry.getValue();
        }
        return null;
    }

    public boolean func_77569_a(InventoryCrafting craftingInv, World world) {
        ItemStack Qsuit = null;
        for (int slot = 0; slot < craftingInv.func_70302_i_(); ++slot) {
            ItemStack stack = craftingInv.func_70301_a(slot);
            if (StackUtil.isEmpty(stack)) continue;
            if (this.armour.matches(stack)) {
                if (Qsuit != null) {
                    return false;
                }
                Qsuit = stack;
                continue;
            }
            if (ArmorDyeingRecipe.getColourForStack(stack) != null) continue;
            return false;
        }
        return Qsuit != null;
    }

    public ItemStack func_77572_b(InventoryCrafting craftingInv) {
        ItemStack armourStack = null;
        ItemArmor Qsuit = null;
        int[] newRBG = new int[3];
        int totalColour = 0;
        int numberOfDyes = 0;
        for (int slot = 0; slot < craftingInv.func_70302_i_(); ++slot) {
            int b;
            int g;
            int r;
            ItemStack stack = craftingInv.func_70301_a(slot);
            if (StackUtil.isEmpty(stack)) continue;
            if (this.armour.matches(stack)) {
                Qsuit = (ItemArmor)stack.func_77973_b();
                if (!StackUtil.isEmpty(armourStack)) {
                    return StackUtil.emptyStack;
                }
                armourStack = StackUtil.copyWithSize(stack, 1);
                if (!Qsuit.func_82816_b_(stack)) continue;
                int oldColour = Qsuit.func_82814_b(armourStack);
                r = oldColour >> 16 & 0xFF;
                g = oldColour >> 8 & 0xFF;
                b = oldColour & 0xFF;
                totalColour += Math.max(r, Math.max(g, b));
                newRBG[0] = newRBG[0] + r;
                newRBG[1] = newRBG[1] + g;
                newRBG[2] = newRBG[2] + b;
                ++numberOfDyes;
                continue;
            }
            int[] dyeRGB = ArmorDyeingRecipe.getColourForStack(stack);
            if (dyeRGB == null) {
                return StackUtil.emptyStack;
            }
            r = dyeRGB[0];
            g = dyeRGB[1];
            b = dyeRGB[2];
            totalColour += Math.max(r, Math.max(g, b));
            newRBG[0] = newRBG[0] + r;
            newRBG[1] = newRBG[1] + g;
            newRBG[2] = newRBG[2] + b;
            ++numberOfDyes;
        }
        if (Qsuit == null || numberOfDyes == 0) {
            return StackUtil.emptyStack;
        }
        if (Qsuit.func_82816_b_(armourStack) && numberOfDyes == 1) {
            Qsuit.func_82815_c(armourStack);
        } else {
            int averageRed = newRBG[0] / numberOfDyes;
            int averageGreen = newRBG[1] / numberOfDyes;
            int averageBlue = newRBG[2] / numberOfDyes;
            float gain = (float)totalColour / (float)numberOfDyes;
            float averageMax = Math.max(averageRed, Math.max(averageGreen, averageBlue));
            averageRed = (int)((float)averageRed * gain / averageMax);
            averageGreen = (int)((float)averageGreen * gain / averageMax);
            averageBlue = (int)((float)averageBlue * gain / averageMax);
            int finalColour = (averageRed << 8) + averageGreen;
            finalColour = (finalColour << 8) + averageBlue;
            Qsuit.func_82813_b(armourStack, finalColour);
        }
        return armourStack;
    }

    public static class RecipeInputClass
    extends RecipeInputBase
    implements IRecipeInput {
        protected final Class<?> type;
        protected final int amount;

        public RecipeInputClass(Class<?> type) {
            this(type, 1);
        }

        public RecipeInputClass(Class<?> type, int amount) {
            this.type = type;
            this.amount = amount;
        }

        @Override
        public boolean matches(ItemStack subject) {
            return this.matches(subject.func_77973_b());
        }

        protected boolean matches(Item item) {
            return this.type.isInstance(item);
        }

        @Override
        public int getAmount() {
            return this.amount;
        }

        @Override
        public List<ItemStack> getInputs() {
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
            for (Item item : ForgeRegistries.ITEMS) {
                if (!this.matches(item)) continue;
                ret.add(new ItemStack(item, 1, Short.MAX_VALUE));
            }
            return ret;
        }

        public String toString() {
            return "RInputClass<" + this.type + ", " + this.amount + '>';
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;
            RecipeInputClass other = (RecipeInputClass)obj;
            if (other.type != this.type) return false;
            if (other.amount != this.amount) return false;
            return true;
        }
    }
}

