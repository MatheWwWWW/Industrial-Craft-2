/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.server.ServerLifecycleHooks
 *  net.minecraftforge.server.command.EnumArgument
 */
package ic2.core.platform.events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.ICannerRecipeRegistry;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.recipes.registries.IRareEarthRegistry;
import ic2.api.recipes.registries.IRefiningRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.command.EnumArgument;

public class RecipeCheckCommand {
    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder source = Commands.m_82127_((String)"missingrecipes");
        source.then(Commands.m_82129_((String)"type", (ArgumentType)EnumArgument.enumArgument(TestType.class)).executes(RecipeCheckCommand::testIC2Recipes));
        dispatcher.register(source);
    }

    private static int testIC2Recipes(CommandContext<CommandSourceStack> entry) {
        TestType type = (TestType)((Object)entry.getArgument("type", TestType.class));
        RecipeCheckCommand.testRecipes("ic2", type.isInput(), type.isOutput(), type == TestType.XOR);
        return 0;
    }

    public static void testRecipes(String mod, boolean inputs, boolean outputs, boolean xOr) {
        ObjectSet input = CollectionUtils.createSet();
        ObjectSet output = CollectionUtils.createSet();
        RecipeCheckCommand.fillRecipes(input, output);
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Item item : RecipeCheckCommand.getItemsToTest("ic2")) {
            boolean missingOutput;
            boolean missingInput = !input.contains(item);
            boolean bl = missingOutput = !output.contains(item);
            if (!inputs) {
                missingInput = false;
            }
            if (!outputs) {
                missingOutput = false;
            }
            if (!(!xOr || missingInput && missingOutput)) {
                missingInput = false;
                missingOutput = false;
            }
            if (!missingInput && !missingOutput) continue;
            builder.append("\n");
            builder.append(ForgeRegistries.ITEMS.getKey((Object)item).toString()).append(" ");
            builder.append("Is Missing: ").append(missingInput ? (missingOutput ? "Input/Output" : "Input") : "Output");
            ++count;
        }
        if (count > 0) {
            IC2.LOGGER.info(builder.toString());
        }
    }

    public static void fillRecipes(Set<Item> inputs, Set<Item> outputs) {
        for (Recipe recipe : ServerLifecycleHooks.getCurrentServer().m_129894_().m_44051_()) {
            outputs.add(recipe.m_8043_().m_41720_());
            for (Ingredient input : recipe.m_7527_()) {
                RecipeCheckCommand.addTo(input.m_43908_(), inputs);
            }
        }
        GlobalRecipes recipes = IC2.RECIPES.get(true);
        RecipeCheckCommand.fillMachineRecipes(recipes.blast_furnace, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.smoker, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.sawmill, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.compressor, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.extractor, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.furnace, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.macerator, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.massFabricator, inputs, outputs);
        RecipeCheckCommand.fillMachineRecipes(recipes.mixingFurnace, inputs, outputs);
        for (IRareEarthRegistry.RareEntry rareEntry : recipes.rare_earth.getAllRecipes()) {
            outputs.add(rareEntry.getOutput().m_41720_());
            inputs.add(rareEntry.getInput().m_41720_());
        }
        for (List list : recipes.canner.getRepairItems().values()) {
            for (Tuple subEntry : list) {
                RecipeCheckCommand.addTo(((IInput)subEntry.m_14418_()).getComponents(), inputs);
            }
        }
        for (ICannerRecipeRegistry.FuelValue fuelValue : recipes.canner.getFuels()) {
            inputs.add(fuelValue.getStack().m_41720_());
        }
        for (Map.Entry entry : recipes.canner.getFillables().entrySet()) {
            inputs.add(((ItemStack)entry.getKey()).m_41720_());
            for (Tuple subEntry : (List)entry.getValue()) {
                RecipeCheckCommand.addTo(((IInput)subEntry.m_14418_()).getComponents(), inputs);
                outputs.add(((ItemStack)subEntry.m_14419_()).m_41720_());
            }
        }
        for (IElectrolyzerRecipeList.ElectrolyzerRecipe electrolyzerRecipe : recipes.electrolyzer.getRecipes()) {
            inputs.add(electrolyzerRecipe.getInput().m_41720_());
            outputs.add(electrolyzerRecipe.getOutput().m_41720_());
        }
        for (EnrichRecipe enrichRecipe : recipes.enricher.getRecipes()) {
            inputs.addAll(enrichRecipe.getInputs());
            outputs.add(enrichRecipe.getOutput().m_41720_());
        }
        for (IRefiningRecipeList.FluidRecipe fluidRecipe : recipes.refining.getAllRecipes()) {
            RecipeCheckCommand.addTo(fluidRecipe.getItemInput().getComponents(), inputs);
            RecipeCheckCommand.addTo(fluidRecipe.getOutput().getAllOutputs(), outputs);
        }
    }

    public static void fillMachineRecipes(IMachineRecipeList list, Set<Item> inputs, Set<Item> outputs) {
        for (IMachineRecipeList.RecipeEntry entry : list.getAllEntries()) {
            for (IInput input : entry.getInputs()) {
                RecipeCheckCommand.addTo(input.getComponents(), inputs);
            }
            RecipeCheckCommand.addTo(entry.getOutput().getAllOutputs(), outputs);
        }
    }

    public static void addTo(Collection<ItemStack> items, Set<Item> result) {
        for (ItemStack item : items) {
            result.add(item.m_41720_());
        }
    }

    public static void addTo(ItemStack[] items, Set<Item> result) {
        int m = items.length;
        for (int i = 0; i < m; ++i) {
            result.add(items[i].m_41720_());
        }
    }

    public static List<Item> getItemsToTest(String mod) {
        ObjectList items = CollectionUtils.createList();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (!ForgeRegistries.ITEMS.getKey((Object)item).m_135827_().equalsIgnoreCase(mod)) continue;
            items.add((Item)item);
        }
        return items;
    }

    public static enum TestType {
        INPUTS,
        OUTPUTS,
        BOTH,
        XOR;


        public boolean isInput() {
            return this == INPUTS || this == BOTH || this == XOR;
        }

        public boolean isOutput() {
            return this == OUTPUTS || this == BOTH || this == XOR;
        }
    }
}

