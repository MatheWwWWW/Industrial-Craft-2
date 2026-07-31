/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.FluidRegistry
 */
package ic2.core.util;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.ref.IMultiBlock;
import ic2.core.ref.IMultiItem;
import ic2.core.util.Config;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class ConfigUtil {
    public static List<String> asList(String str) {
        if ((str = str.trim()).isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(str.split("\\s*,\\s*"));
    }

    public static List<IRecipeInput> asRecipeInputList(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            try {
                return ConfigUtil.asRecipeInputList(value.getString());
            }
            catch (ParseException e) {
                throw new Config.ParseException("Invalid value", value, e);
            }
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return null;
        }
    }

    public static List<ItemStack> asStackList(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            try {
                return ConfigUtil.asStackList(value.getString());
            }
            catch (ParseException e) {
                throw new Config.ParseException("Invalid value", value, e);
            }
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return null;
        }
    }

    public static ItemStack asStack(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            try {
                return ConfigUtil.asStack(value.getString());
            }
            catch (ParseException e) {
                throw new Config.ParseException("Invalid value", value, e);
            }
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return null;
        }
    }

    public static String getString(Config config, String key) {
        return config.get(key).getString();
    }

    public static boolean getBool(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            return value.getBool();
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return false;
        }
    }

    public static int getInt(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            return value.getInt();
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return 0;
        }
    }

    public static float getFloat(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            return value.getFloat();
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return 0.0f;
        }
    }

    public static double getDouble(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            return value.getDouble();
        }
        catch (Config.ParseException e) {
            ConfigUtil.displayError(e, key);
            return 0.0;
        }
    }

    public static int[] asIntArray(Config config, String key) {
        Config.Value value = config.get(key);
        try {
            return ConfigUtil.asList(value.getString()).stream().mapToInt(Integer::parseInt).toArray();
        }
        catch (NumberFormatException e) {
            ConfigUtil.displayError(new Config.ParseException("Invalid value", value, e), key);
            return new int[0];
        }
    }

    public static List<ItemStack> asStackList(String str) throws ParseException {
        List<String> parts = ConfigUtil.asList(str);
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>(parts.size());
        for (String part : parts) {
            ret.add(ConfigUtil.asStack(part));
        }
        return ret;
    }

    public static List<IRecipeInput> asRecipeInputList(String str) throws ParseException {
        return ConfigUtil.asRecipeInputList(str, false);
    }

    public static List<IRecipeInput> asRecipeInputList(String str, boolean allowNull) throws ParseException {
        List<String> parts = ConfigUtil.asList(str);
        ArrayList<IRecipeInput> ret = new ArrayList<IRecipeInput>(parts.size());
        for (String part : parts) {
            IRecipeInput input = ConfigUtil.asRecipeInput(part);
            if (input == null && !allowNull) {
                throw new ParseException("There is no item matching " + part + ".", -1);
            }
            ret.add(input);
        }
        return ret;
    }

    private static ItemStack asStack(String str, boolean checkAmount) throws ParseException {
        String[] parts = str.split("(?=(@|#|\\*))");
        String itemName = parts[0];
        Item item = Util.getItem(itemName);
        if (item == null) {
            return null;
        }
        ItemStack stack = new ItemStack(item);
        int amount = 1;
        for (int i = 1; i < parts.length; ++i) {
            String tmp = parts[i];
            if (tmp.startsWith("@")) {
                if (i + 1 < parts.length && parts[i + 1].equals("*")) {
                    stack = new ItemStack(item, 1, Short.MAX_VALUE);
                    ++i;
                    continue;
                }
                stack = new ItemStack(item, 1, Integer.parseInt(tmp.substring(1)));
                continue;
            }
            if (tmp.startsWith("#")) {
                if (item instanceof IMultiItem) {
                    stack = ((IMultiItem)item).getItemStack(tmp.substring(1));
                    continue;
                }
                if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d() instanceof IMultiBlock) {
                    stack = ((IMultiBlock)((ItemBlock)item).func_179223_d()).getItemStack(tmp.substring(1));
                    continue;
                }
                throw new ParseException("# is not supported on non-IC2-Items: " + str, 0);
            }
            if (!tmp.startsWith("*")) continue;
            if (!checkAmount) {
                throw new ParseException("We do not support amount here.", -1);
            }
            amount = Integer.parseInt(tmp.substring(1));
        }
        if (checkAmount) {
            stack = StackUtil.setSize(stack, amount);
        }
        return stack;
    }

    public static ItemStack asStack(String str) throws ParseException {
        return ConfigUtil.asStack(str, false);
    }

    public static ItemStack asStackWithAmount(String str) throws ParseException {
        return ConfigUtil.asStack(str, true);
    }

    public static String fromStack(ItemStack stack) {
        return ConfigUtil.fromStack(stack, false);
    }

    private static String fromStack(ItemStack stack, boolean amount) {
        String ret = Util.getName(stack.func_77973_b()).toString();
        if (amount) {
            ret = ret + "*" + StackUtil.getSize(stack);
        }
        if (stack.func_77973_b() instanceof IMultiItem) {
            String variant = ((IMultiItem)stack.func_77973_b()).getVariant(stack);
            if (variant != null) {
                ret = ret + "#" + variant;
            }
        } else if (stack.func_77973_b() instanceof ItemBlock && ((ItemBlock)stack.func_77973_b()).func_179223_d() instanceof IMultiBlock) {
            String variant = ((IMultiBlock)((ItemBlock)stack.func_77973_b()).func_179223_d()).getVariant(stack);
            if (variant != null) {
                ret = ret + "#" + variant;
            }
        } else if (stack.func_77952_i() == Short.MAX_VALUE) {
            ret = ret + "@*";
        } else if (stack.func_77952_i() != 0) {
            ret = ret + "@" + stack.func_77952_i();
        }
        return ret;
    }

    public static String fromStackWithAmount(ItemStack stack) {
        return ConfigUtil.fromStack(stack, true);
    }

    public static IRecipeInput asRecipeInput(Config.Value value) {
        try {
            return ConfigUtil.asRecipeInput(value.getString());
        }
        catch (ParseException e) {
            throw new Config.ParseException("Invalid value", value, e);
        }
    }

    private static IRecipeInput asRecipeInput(String str, boolean checkAmount) throws ParseException {
        String[] parts = str.split("(?=(@|#|\\*))");
        String itemName = parts[0];
        if (!itemName.startsWith("OreDict:") && !itemName.startsWith("Fluid:")) {
            ItemStack stack = ConfigUtil.asStack(str, checkAmount);
            if (stack == null) {
                return null;
            }
            return Recipes.inputFactory.forStack(stack);
        }
        Integer amount = null;
        Integer meta = null;
        for (int i = 1; i < parts.length; ++i) {
            String tmp = parts[i];
            if (tmp.startsWith("@")) {
                if (i + 1 < parts.length && parts[i + 1].equals("*")) {
                    meta = Short.MAX_VALUE;
                    ++i;
                    continue;
                }
                meta = Integer.parseInt(tmp.substring(1));
                continue;
            }
            if (!tmp.startsWith("*")) continue;
            if (!checkAmount) {
                throw new ParseException("We do not support amount here.", -1);
            }
            amount = Integer.parseInt(tmp.substring(1));
        }
        if (itemName.startsWith("OreDict:")) {
            if (amount == null) {
                amount = 1;
            }
            if (meta == null) {
                return Recipes.inputFactory.forOreDict(itemName.substring("OreDict:".length()), amount);
            }
            return Recipes.inputFactory.forOreDict(itemName.substring("OreDict:".length()), amount, meta);
        }
        if (itemName.startsWith("Fluid:")) {
            if (amount == null) {
                amount = 1000;
            }
            return Recipes.inputFactory.forFluidContainer(FluidRegistry.getFluid((String)itemName.substring("Fluid:".length())), amount);
        }
        return null;
    }

    public static IRecipeInput asRecipeInput(String str) throws ParseException {
        return ConfigUtil.asRecipeInput(str, false);
    }

    public static IRecipeInput asRecipeInputWithAmount(String str) throws ParseException {
        return ConfigUtil.asRecipeInput(str, true);
    }

    private static void displayError(Config.ParseException e, String key) {
        IC2.platform.displayError("The IC2 config file contains an invalid entry for %s.\n\n%s%s", key, e.getMessage(), e.getCause() != null ? "\n\n" + e.getCause().getMessage() : "");
    }
}

