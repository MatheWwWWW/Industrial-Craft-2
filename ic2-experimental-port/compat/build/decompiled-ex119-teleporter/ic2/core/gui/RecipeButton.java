/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 */
package ic2.core.gui;

import com.google.common.base.Function;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.Button;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import java.util.List;
import net.minecraft.network.chat.Component;

public class RecipeButton
extends Button<RecipeButton> {
    public static Function<String[], IClickHandler> jeiRecipeListOpener;

    public static boolean canUse() {
        return jeiRecipeListOpener != null;
    }

    public RecipeButton(GuiElement<?> guiElement, String[] stringArray) {
        this(guiElement.gui, guiElement.x, guiElement.y, guiElement.width, guiElement.height, stringArray);
    }

    public RecipeButton(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, String[] stringArray) {
        super(ic2Gui, n, n2, n3, n4, (IClickHandler)jeiRecipeListOpener.apply((Object)stringArray));
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        list.add((Component)Component.m_237115_((String)"ic2.jei.recipes"));
        return list;
    }

    @Override
    @GuiElement.SkippedMethod
    public void drawBackground(PoseStack poseStack, int n, int n2) {
    }
}

