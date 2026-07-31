/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.util;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.util.Config;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

public class ConfigUtil {
    public static List<String> asList(String string) {
        if ((string = string.trim()).isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(string.split("\\s*,\\s*"));
    }

    public static List<IRecipeInput> asRecipeInputList(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            try {
                return ConfigUtil.asRecipeInputList(value.getString());
            }
            catch (ParseException parseException) {
                throw new Config.ParseException("Invalid value", value, parseException);
            }
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return null;
        }
    }

    public static List<ItemStack> asStackList(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            try {
                return ConfigUtil.asStackList(value.getString());
            }
            catch (ParseException parseException) {
                throw new Config.ParseException("Invalid value", value, parseException);
            }
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return null;
        }
    }

    public static ItemStack asStack(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            try {
                return ConfigUtil.asStack(value.getString());
            }
            catch (ParseException parseException) {
                throw new Config.ParseException("Invalid value", value, parseException);
            }
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return null;
        }
    }

    public static String getString(Config config, String string) {
        return config.get(string).getString();
    }

    public static boolean getBool(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            return value.getBool();
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return false;
        }
    }

    public static int getInt(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            return value.getInt();
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return 0;
        }
    }

    public static float getFloat(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            return value.getFloat();
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return 0.0f;
        }
    }

    public static double getDouble(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            return value.getDouble();
        }
        catch (Config.ParseException parseException) {
            ConfigUtil.displayError(parseException, string);
            return 0.0;
        }
    }

    public static int[] asIntArray(Config config, String string) {
        Config.Value value = config.get(string);
        try {
            return ConfigUtil.asList(value.getString()).stream().mapToInt(Integer::parseInt).toArray();
        }
        catch (NumberFormatException numberFormatException) {
            ConfigUtil.displayError(new Config.ParseException("Invalid value", value, numberFormatException), string);
            return new int[0];
        }
    }

    public static List<ItemStack> asStackList(String string) throws ParseException {
        List<String> list = ConfigUtil.asList(string);
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(list.size());
        for (String string2 : list) {
            arrayList.add(ConfigUtil.asStack(string2));
        }
        return arrayList;
    }

    public static List<IRecipeInput> asRecipeInputList(String string) throws ParseException {
        return ConfigUtil.asRecipeInputList(string, false);
    }

    public static List<IRecipeInput> asRecipeInputList(String string, boolean bl) throws ParseException {
        List<String> list = ConfigUtil.asList(string);
        ArrayList<IRecipeInput> arrayList = new ArrayList<IRecipeInput>(list.size());
        for (String string2 : list) {
            IRecipeInput iRecipeInput = ConfigUtil.asRecipeInput(string2);
            if (iRecipeInput == null && !bl) {
                throw new ParseException("There is no item matching " + string2 + ".", -1);
            }
            arrayList.add(iRecipeInput);
        }
        return arrayList;
    }

    private static ItemStack asStack(String string, boolean bl) throws ParseException {
        String[] stringArray = string.split("(?=(\\*))");
        String string2 = stringArray[0];
        Item item = Util.getItem(string2);
        if (item == null) {
            return null;
        }
        ItemStack itemStack = new ItemStack((ItemLike)item);
        int n = 1;
        for (int i = 1; i < stringArray.length; ++i) {
            String string3 = stringArray[i];
            if (!string3.startsWith("*")) continue;
            if (!bl) {
                throw new ParseException("We do not support amount here.", -1);
            }
            n = Integer.parseInt(string3.substring(1));
        }
        if (bl) {
            itemStack = StackUtil.setSize(itemStack, n);
        }
        return itemStack;
    }

    public static ItemStack asStack(String string) throws ParseException {
        return ConfigUtil.asStack(string, false);
    }

    public static ItemStack asStackWithAmount(String string) throws ParseException {
        return ConfigUtil.asStack(string, true);
    }

    public static String fromStack(ItemStack itemStack) {
        return ConfigUtil.fromStack(itemStack, false);
    }

    private static String fromStack(ItemStack itemStack, boolean bl) {
        Object object = Util.getName(itemStack.m_41720_()).toString();
        if (bl) {
            object = (String)object + "*" + StackUtil.getSize(itemStack);
        }
        return object;
    }

    public static String fromStackWithAmount(ItemStack itemStack) {
        return ConfigUtil.fromStack(itemStack, true);
    }

    public static IRecipeInput asRecipeInput(Config.Value value) {
        try {
            return ConfigUtil.asRecipeInput(value.getString());
        }
        catch (ParseException parseException) {
            throw new Config.ParseException("Invalid value", value, parseException);
        }
    }

    private static IRecipeInput asRecipeInput(String string, boolean bl) throws ParseException {
        String string2;
        String[] stringArray = string.split("(?=(\\*))");
        String string3 = stringArray[0];
        if (!string3.startsWith("Tag:") && !string3.startsWith("Fluid:")) {
            ItemStack itemStack = ConfigUtil.asStack(string, bl);
            if (itemStack == null) {
                return null;
            }
            return Recipes.inputFactory.forStack(itemStack);
        }
        Integer n = null;
        for (int i = 1; i < stringArray.length; ++i) {
            string2 = stringArray[i];
            if (!string2.startsWith("*")) continue;
            if (!bl) {
                throw new ParseException("We do not support amount here.", -1);
            }
            n = Integer.parseInt(string2.substring(1));
        }
        if (string3.startsWith("Tag:")) {
            if (n == null) {
                n = 1;
            }
            return Recipes.inputFactory.forTag(string3.substring("Tag:".length()), n);
        }
        if (string3.startsWith("Fluid:")) {
            ResourceLocation resourceLocation;
            if (n == null) {
                n = 1000;
            }
            if ((string2 = Util.getFluid(resourceLocation = new ResourceLocation(string3.substring("Fluid:".length())))) == null) {
                return null;
            }
            return Recipes.inputFactory.forFluidContainer((Fluid)string2, n);
        }
        return null;
    }

    public static IRecipeInput asRecipeInput(String string) throws ParseException {
        return ConfigUtil.asRecipeInput(string, false);
    }

    public static IRecipeInput asRecipeInputWithAmount(String string) throws ParseException {
        return ConfigUtil.asRecipeInput(string, true);
    }

    private static void displayError(Config.ParseException parseException, String string) {
        IC2.sideProxy.displayError("The IC2 config file contains an invalid entry for %s.\n\n%s%s", string, parseException.getMessage(), parseException.getCause() != null ? "\n\n" + parseException.getCause().getMessage() : "");
    }
}

