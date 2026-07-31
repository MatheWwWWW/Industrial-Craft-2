/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.IGuiHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.core.block.wiring.CableType;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.jeiIntegration.recipe.machine.DynamicCategory;
import mezz.jei.api.IGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public final class MetalFormerCategory
extends DynamicCategory<IBasicMachineRecipeManager> {
    private final int mode;
    private static final ItemStack[] icon = new ItemStack[]{ItemName.cable.getItemStack(CableType.copper), ItemName.forge_hammer.getItemStack(), ItemName.cutter.getItemStack()};

    public MetalFormerCategory(IBasicMachineRecipeManager recipeManager, int mode, IGuiHelper guiHelper) {
        super(TeBlock.metal_former, recipeManager, guiHelper);
        this.mode = mode;
    }

    @Override
    public String getUid() {
        return super.getUid() + this.mode;
    }

    @Override
    public void draw(Minecraft minecraft) {
        super.draw(minecraft);
        minecraft.func_175599_af().func_180450_b(icon[this.mode], 70, 35);
    }
}

