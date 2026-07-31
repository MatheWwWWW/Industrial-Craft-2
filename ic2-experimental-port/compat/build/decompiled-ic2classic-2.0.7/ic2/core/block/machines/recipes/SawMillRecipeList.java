/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.block.machines.recipes;

import ic2.api.recipes.ingridients.inputs.ItemStackInput;
import ic2.api.recipes.ingridients.recipes.SawMillOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.machines.recipes.MachineRecipeList;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SawMillRecipeList
extends MachineRecipeList {
    public static final CraftingContainer CRAFTING = new CraftingContainer(new AbstractContainerMenu(null, 0){

        public boolean m_6875_(Player playerIn) {
            return true;
        }

        public ItemStack m_7648_(Player p_38941_, int p_38942_) {
            return ItemStack.f_41583_;
        }
    }, 3, 3);

    public SawMillRecipeList(Consumer<IMachineRecipeList> reloader) {
        super("sawmill", reloader);
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(ItemStack stack, boolean hasStackSize) {
        IMachineRecipeList.RecipeEntry entry = super.getRecipe(stack, hasStackSize);
        if (stack.m_204117_(ItemTags.f_13182_) && (entry == null || entry.getLocation().m_135815_().equalsIgnoreCase("display_log"))) {
            ItemStack output = SawMillRecipeList.findOutput(stack);
            if (!output.m_41619_()) {
                return new IMachineRecipeList.RecipeEntry(new ResourceLocation("ic2", "log_recipe"), new SawMillOutput((List<ItemStack>)ObjectLists.singleton((Object)StackUtil.copyWithSize(output, 6))), new ItemStackInput(stack, 1));
            }
            return null;
        }
        return entry;
    }

    public static ItemStack findOutput(ItemStack recipe) {
        CRAFTING.m_6836_(4, StackUtil.copyWithSize(recipe, 1));
        try {
            Optional result = ServerLifecycleHooks.getCurrentServer().m_129894_().m_44015_(RecipeType.f_44107_, (Container)CRAFTING, SawMillRecipeList.getLevel());
            return result.isPresent() ? ((CraftingRecipe)result.get()).m_5874_((Container)CRAFTING) : ItemStack.f_41583_;
        }
        catch (Exception ignored) {
            ignored.printStackTrace();
            return ItemStack.f_41583_;
        }
    }

    private static Level getLevel() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server == null ? null : server.m_129783_();
    }
}

