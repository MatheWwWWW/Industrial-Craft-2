/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.Ingredient
 *  net.minecraft.util.NonNullList
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.crafting.IShapedRecipe
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.oredict.OreDictionary
 *  org.apache.commons.lang3.StringUtils
 */
package ic2.core.recipe;

import ic2.api.item.ElectricItem;
import ic2.api.recipe.ICraftingRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.init.Rezepte;
import ic2.core.recipe.RecipeInputFluidContainer;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

public class AdvRecipe
implements IShapedRecipe {
    private static final boolean debug = Util.hasAssertions();
    public final ItemStack output;
    public final IRecipeInput[] input;
    public final IRecipeInput[] inputMirrored;
    public final int[] masks;
    public final int[] masksMirrored;
    public final int inputWidth;
    public final int inputHeight;
    public final boolean hidden;
    public final boolean consuming;
    private ResourceLocation name;

    public static void addAndRegister(ItemStack result, Object ... args) {
        block2: {
            try {
                Rezepte.registerRecipe((IRecipe)new AdvRecipe(result, args));
            }
            catch (RuntimeException e) {
                if (MainConfig.ignoreInvalidRecipes) break block2;
                throw e;
            }
        }
    }

    public AdvRecipe(ItemStack result, Object ... args) {
        if (StackUtil.isEmpty(result)) {
            AdvRecipe.displayError("null result", null, result, false);
        }
        HashMap<Character, IRecipeInput> charMapping = new HashMap<Character, IRecipeInput>();
        ArrayList<String> inputArrangement = new ArrayList<String>();
        Character lastChar = null;
        boolean isHidden = false;
        boolean isConsuming = false;
        boolean isFixedSize = false;
        for (Object arg : args) {
            if (arg instanceof String) {
                if (lastChar == null) {
                    String str;
                    if (!charMapping.isEmpty()) {
                        AdvRecipe.displayError("oredict name without preceding char", "Name: " + arg, result, false);
                    }
                    if ((str = (String)arg).isEmpty() || str.length() > 3) {
                        AdvRecipe.displayError("none or too many crafting columns", "Input: " + str + "\nSize: " + str.length(), result, false);
                    }
                    inputArrangement.add(str);
                    continue;
                }
                charMapping.put(lastChar, AdvRecipe.getRecipeObject(arg));
                lastChar = null;
                continue;
            }
            if (arg instanceof Character) {
                if (lastChar != null) {
                    AdvRecipe.displayError("two consecutive char definitions", "Input: " + arg + "\nprev. Input: " + lastChar, result, false);
                }
                lastChar = (Character)arg;
                continue;
            }
            if (arg instanceof Boolean) {
                isHidden = (Boolean)arg;
                continue;
            }
            if (arg instanceof ICraftingRecipeManager.AttributeContainer) {
                isHidden = ((ICraftingRecipeManager.AttributeContainer)arg).hidden;
                isConsuming = ((ICraftingRecipeManager.AttributeContainer)arg).consuming;
                isFixedSize = ((ICraftingRecipeManager.AttributeContainer)arg).fixedSize;
                continue;
            }
            if (lastChar == null) {
                AdvRecipe.displayError("two consecutive char definitions", "Input: " + arg + "\nprev. Input: " + lastChar, result, false);
            }
            try {
                IRecipeInput last = charMapping.put(lastChar, AdvRecipe.getRecipeObject(arg));
                if (last != null) {
                    AdvRecipe.displayError("duplicate char mapping", "Char: " + lastChar + "\nInput: " + arg + "\nType: " + arg.getClass().getName(), result, false);
                }
                lastChar = null;
            }
            catch (Exception e) {
                e.printStackTrace();
                AdvRecipe.displayError("unknown type", "Input: " + arg + "\nType: " + arg.getClass().getName(), result, false);
            }
        }
        this.hidden = isHidden;
        this.consuming = isConsuming;
        this.inputHeight = inputArrangement.size();
        if (lastChar != null) {
            AdvRecipe.displayError("one or more unused mapping chars", "Letter: " + lastChar, result, false);
        }
        if (this.inputHeight == 0 || this.inputHeight > 3) {
            AdvRecipe.displayError("none or too many crafting rows", "Size: " + inputArrangement.size(), result, false);
        }
        if (charMapping.size() == 0) {
            AdvRecipe.displayError("no mapping chars", null, result, false);
        }
        this.inputWidth = ((String)inputArrangement.get(0)).length();
        if (debug && !isFixedSize) {
            if (StringUtils.containsOnly((CharSequence)((CharSequence)inputArrangement.get(0)), (char[])new char[]{' '})) {
                IC2.log.warn(LogCategory.Recipe, "Leading empty row in shaped recipe for %s (%s), from %s.", result, result.func_82833_r(), AdvRecipe.getCaller());
            }
            if (StringUtils.containsOnly((CharSequence)((CharSequence)inputArrangement.get(this.inputHeight - 1)), (char[])new char[]{' '})) {
                IC2.log.warn(LogCategory.Recipe, "Trailing empty row in shaped recipe for %s (%s), from %s.", result, result.func_82833_r(), AdvRecipe.getCaller());
            }
            for (int pass = 0; pass < 2; ++pass) {
                boolean found = true;
                for (int y = 0; y < this.inputHeight; ++y) {
                    String str = (String)inputArrangement.get(y);
                    if ((pass != 0 || str.charAt(0) == ' ') && (pass != 1 || str.charAt(this.inputWidth - 1) == ' ')) continue;
                    found = false;
                    break;
                }
                if (!found) continue;
                if (pass == 0) {
                    IC2.log.warn(LogCategory.Recipe, "Leading empty column in shaped recipe for %s (%s), from %s.", result, result.func_82833_r(), AdvRecipe.getCaller());
                    continue;
                }
                IC2.log.warn(LogCategory.Recipe, "Trailing empty column in shaped recipe for %s (%s), from %s.", result, result.func_82833_r(), AdvRecipe.getCaller());
            }
        }
        int xMasks = -this.inputWidth + 4;
        int yMasks = -this.inputHeight + 4;
        int mask = 0;
        ArrayList inputs = new ArrayList();
        for (int y = 0; y < 3; ++y) {
            Object str = null;
            if (y < this.inputHeight && ((String)(str = (String)inputArrangement.get(y))).length() != this.inputWidth) {
                AdvRecipe.displayError("no fixed width", "Expected: " + this.inputWidth + "\nGot: " + ((String)str).length(), result, false);
            }
            for (int x = 0; x < 3; ++x) {
                char c;
                mask <<= 1;
                if (x >= this.inputWidth || str == null || (c = ((String)str).charAt(x)) == ' ') continue;
                if (!charMapping.containsKey(Character.valueOf(c))) {
                    AdvRecipe.displayError("missing char mapping", "Letter: " + c, result, false);
                }
                inputs.add(charMapping.get(Character.valueOf(c)));
                mask |= 1;
            }
        }
        this.input = inputs.toArray(new IRecipeInput[0]);
        boolean mirror = false;
        if (this.inputWidth != 1) {
            for (String s : inputArrangement) {
                if (s.charAt(0) == s.charAt(this.inputWidth - 1)) continue;
                mirror = true;
                break;
            }
        }
        if (!mirror) {
            this.inputMirrored = null;
        } else {
            IRecipeInput[] tmp = new IRecipeInput[9];
            int j = 0;
            for (int i = 0; i < 9; ++i) {
                if ((mask & 1 << 8 - i) == 0) continue;
                tmp[i] = this.input[j];
                ++j;
            }
            IRecipeInput old = tmp[0];
            tmp[0] = tmp[2];
            tmp[2] = old;
            old = tmp[3];
            tmp[3] = tmp[5];
            tmp[5] = old;
            old = tmp[6];
            tmp[6] = tmp[8];
            tmp[8] = old;
            this.inputMirrored = new IRecipeInput[this.input.length];
            int j2 = 0;
            for (int i = 0; i < 9; ++i) {
                if (tmp[i] == null) continue;
                this.inputMirrored[j2] = tmp[i];
                ++j2;
            }
        }
        this.masks = new int[xMasks * yMasks];
        this.masksMirrored = (int[])(!mirror ? null : new int[this.masks.length]);
        for (int y = 0; y < yMasks; ++y) {
            int yMask = mask >>> y * 3;
            for (int x = 0; x < xMasks; ++x) {
                int xyMask;
                this.masks[x + y * xMasks] = xyMask = yMask >>> x;
                if (!mirror) continue;
                this.masksMirrored[x + y * xMasks] = xyMask << 2 & 0x124 | xyMask & 0x92 | xyMask >>> 2 & 0x49;
            }
        }
        this.output = result;
    }

    public boolean func_77569_a(InventoryCrafting inventorycrafting, World world) {
        return this.func_77572_b(inventorycrafting) != StackUtil.emptyStack;
    }

    public ItemStack func_77572_b(InventoryCrafting inventorycrafting) {
        ItemStack ret;
        int size = inventorycrafting.func_70302_i_();
        int mask = 0;
        for (int i = 0; i < size; ++i) {
            mask <<= 1;
            if (StackUtil.isEmpty(inventorycrafting.func_70301_a(i))) continue;
            mask |= 1;
        }
        if (size == 4) {
            mask = (mask & 0xC) << 5 | (mask & 3) << 4;
        }
        if (AdvRecipe.checkMask(mask, this.masks) && !StackUtil.isEmpty(ret = this.checkItems((IInventory)inventorycrafting, this.input))) {
            return ret;
        }
        if (this.masksMirrored != null && AdvRecipe.checkMask(mask, this.masksMirrored) && !StackUtil.isEmpty(ret = this.checkItems((IInventory)inventorycrafting, this.inputMirrored))) {
            return ret;
        }
        return StackUtil.emptyStack;
    }

    public ItemStack func_77571_b() {
        return this.output;
    }

    public static boolean canShow(Object[] input, ItemStack output, boolean hidden) {
        return !hidden || !ConfigUtil.getBool(MainConfig.get(), "misc/hideSecretRecipes");
    }

    public boolean canShow() {
        return AdvRecipe.canShow(this.input, this.output, this.hidden);
    }

    public static List<ItemStack> expand(Object o) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if (o instanceof IRecipeInput) {
            ret.addAll(((IRecipeInput)o).getInputs());
        } else if (o instanceof String) {
            String s = (String)o;
            if (s.startsWith("liquid$")) {
                String name = s.substring(7);
                Fluid fluid = FluidRegistry.getFluid((String)name);
                ret.addAll(RecipeInputFluidContainer.getFluidContainer(fluid));
            } else {
                for (ItemStack stack : OreDictionary.getOres((String)((String)o))) {
                    if (StackUtil.isEmpty(stack)) continue;
                    ret.add(stack);
                }
            }
        } else if (o instanceof ItemStack) {
            if (!StackUtil.isEmpty((ItemStack)o)) {
                ret.add((ItemStack)o);
            }
        } else if (o.getClass().isArray()) {
            assert (Array.getLength(o) != 0) : "empty array";
            for (int i = 0; i < Array.getLength(o); ++i) {
                ret.addAll(AdvRecipe.expand(Array.get(o, i)));
            }
        } else if (o instanceof Iterable) {
            assert (((Iterable)o).iterator().hasNext()) : "emtpy iterable";
            for (Object o2 : (Iterable)o) {
                ret.addAll(AdvRecipe.expand(o2));
            }
        } else {
            AdvRecipe.displayError("unknown type", "Input: " + o + "\nType: " + o.getClass().getName(), null, false);
            return null;
        }
        return ret;
    }

    public static List<ItemStack>[] expandArray(Object[] array) {
        List[] ret = new List[array.length];
        for (int i = 0; i < array.length; ++i) {
            ret[i] = array[i] == null ? null : AdvRecipe.expand(array[i]);
        }
        return ret;
    }

    public static void displayError(String cause, String tech, ItemStack result, boolean shapeless) {
        String msg = "An invalid crafting recipe was attempted to be added. This could happen due to a bug in IndustrialCraft 2 or an addon.\n\n(Technical information: Adv" + (shapeless ? "Shapeless" : "") + "Recipe, " + cause + ")\n" + (result != null ? "Output: " + result + "\n" : "") + (tech != null ? tech + "\n" : "") + "Source: " + AdvRecipe.getCaller();
        if (MainConfig.ignoreInvalidRecipes) {
            IC2.log.warn(LogCategory.Recipe, msg);
            throw new RuntimeException(msg);
        }
        IC2.platform.displayError(msg, new Object[0]);
    }

    private static String getCaller() {
        String ret = "unknown";
        for (StackTraceElement st : Thread.currentThread().getStackTrace()) {
            String pkg;
            String className = st.getClassName();
            int pkgSeparator = className.lastIndexOf(46);
            String string = pkg = pkgSeparator == -1 ? "" : className.substring(0, pkgSeparator);
            if (className.equals("ic2.core.recipe.AdvRecipe") || className.equals("ic2.core.recipe.AdvShapelessRecipe") || className.equals("ic2.core.recipe.AdvCraftingRecipeManager") || pkg.startsWith("ic2.api") || pkg.startsWith("java.")) continue;
            ret = className + "." + st.getMethodName() + "(" + st.getFileName() + ":" + st.getLineNumber() + ")";
            break;
        }
        return ret;
    }

    private static boolean checkMask(int mask, int[] request) {
        for (int cmpMask : request) {
            if (mask != cmpMask) continue;
            return true;
        }
        return false;
    }

    static IRecipeInput getRecipeObject(Object o) {
        if (o == null) {
            throw new NullPointerException("Null recipe input object.");
        }
        if (o instanceof IRecipeInput) {
            return (IRecipeInput)o;
        }
        if (o instanceof ItemStack) {
            return Recipes.inputFactory.forStack((ItemStack)o);
        }
        if (o instanceof Block) {
            return Recipes.inputFactory.forStack(new ItemStack((Block)o));
        }
        if (o instanceof Item) {
            return Recipes.inputFactory.forStack(new ItemStack((Item)o));
        }
        if (o instanceof String) {
            return Recipes.inputFactory.forOreDict((String)o);
        }
        if (o instanceof Fluid) {
            return Recipes.inputFactory.forFluidContainer((Fluid)o);
        }
        if (o instanceof FluidStack) {
            return Recipes.inputFactory.forFluidContainer(((FluidStack)o).getFluid(), ((FluidStack)o).amount);
        }
        if (o instanceof Iterable) {
            ArrayList<IRecipeInput> list = new ArrayList<IRecipeInput>();
            for (Object o1 : (Iterable)o) {
                list.add(AdvRecipe.getRecipeObject(o1));
            }
            return Recipes.inputFactory.forAny(list);
        }
        if (o.getClass().isArray()) {
            IRecipeInput[] inputs = new IRecipeInput[Array.getLength(o)];
            for (int i = 0; i < inputs.length; ++i) {
                inputs[i] = AdvRecipe.getRecipeObject(Array.get(o, i));
            }
            return Recipes.inputFactory.forAny(inputs);
        }
        throw new IllegalArgumentException("Invalid object found as RecipeInput: " + o);
    }

    private ItemStack checkItems(IInventory inventory, IRecipeInput[] request) {
        int size = inventory.func_70302_i_();
        double outputCharge = 0.0;
        int j = 0;
        for (int i = 0; i < size; ++i) {
            ItemStack offer = inventory.func_70301_a(i);
            if (StackUtil.isEmpty(offer)) continue;
            if (request[j++].matches(offer)) {
                outputCharge += ElectricItem.manager.getCharge(StackUtil.copyWithSize(offer, 1));
                continue;
            }
            return StackUtil.emptyStack;
        }
        ItemStack ret = this.output.func_77946_l();
        ElectricItem.manager.charge(ret, outputCharge, Integer.MAX_VALUE, true, false);
        return ret;
    }

    public NonNullList<ItemStack> func_179532_b(InventoryCrafting inv) {
        return this.consuming ? NonNullList.func_191197_a((int)inv.func_70302_i_(), (Object)StackUtil.emptyStack) : ForgeHooks.defaultRecipeGetRemainingItems((InventoryCrafting)inv);
    }

    public IRecipe setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    public ResourceLocation getRegistryName() {
        return this.name;
    }

    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }

    public boolean func_194133_a(int x, int y) {
        return this.inputWidth <= x && this.inputHeight <= y;
    }

    public int getRecipeWidth() {
        return this.inputWidth;
    }

    public int getRecipeHeight() {
        return this.inputHeight;
    }

    public NonNullList<Ingredient> func_192400_c() {
        NonNullList list = NonNullList.func_191196_a();
        if (!this.hidden) {
            int mask = this.masks[0];
            int actualIngredient = 0;
            for (int x = 0; x < 9; ++x) {
                if ((mask >>> 8 - x & 1) != 0) {
                    list.add((Object)this.input[actualIngredient++].getIngredient());
                    continue;
                }
                list.add((Object)Ingredient.field_193370_a);
            }
        }
        return list;
    }

    public boolean func_192399_d() {
        return this.hidden;
    }
}

