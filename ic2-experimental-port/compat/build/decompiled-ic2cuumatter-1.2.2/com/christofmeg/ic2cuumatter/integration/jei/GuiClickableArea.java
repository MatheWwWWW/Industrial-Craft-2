/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.block.machines.components.hv.MassFabricatorComponent
 *  ic2.core.block.machines.containers.ev.PlasmafierContainer
 *  ic2.core.block.machines.containers.hv.MassFabricatorContainer
 *  ic2.core.inventory.container.ContainerComponent
 *  ic2.core.inventory.gui.ComponentContainerScreen
 *  ic2.core.inventory.gui.IC2Screen
 *  ic2.core.utils.collection.CollectionUtils
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.gui.handlers.IGuiClickableArea
 *  mezz.jei.api.gui.handlers.IGuiContainerHandler
 *  mezz.jei.api.recipe.RecipeType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.jetbrains.annotations.NotNull
 */
package com.christofmeg.ic2cuumatter.integration.jei;

import com.christofmeg.ic2cuumatter.integration.jei.MassFabricatorCategory;
import com.christofmeg.ic2cuumatter.integration.jei.PlasmafierCategory;
import ic2.core.block.machines.components.hv.MassFabricatorComponent;
import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(value=Dist.CLIENT)
public class GuiClickableArea
implements IGuiContainerHandler<IC2Screen> {
    @NotNull
    public Collection<IGuiClickableArea> getGuiClickableAreas(@NotNull IC2Screen containerScreen, double mouseX, double mouseY) {
        ContainerComponent comp;
        ObjectList areas = CollectionUtils.createList();
        if (containerScreen instanceof ComponentContainerScreen && (comp = (ContainerComponent)containerScreen.getCastedContainer(ContainerComponent.class)) != null && comp.getHolder() != null) {
            if (comp instanceof MassFabricatorContainer && comp.getComponent(MassFabricatorComponent.class) != null) {
                int width = 16;
                int height = 15;
                int posX = 80;
                int posY = 40;
                areas.add(IGuiClickableArea.createBasic((int)posX, (int)posY, (int)width, (int)height, (RecipeType[])new RecipeType[]{MassFabricatorCategory.TYPE}));
            } else if (comp instanceof PlasmafierContainer) {
                int width = 20;
                int height = 54;
                int posX = 78;
                int posY = 14;
                areas.add(IGuiClickableArea.createBasic((int)posX, (int)posY, (int)width, (int)height, (RecipeType[])new RecipeType[]{PlasmafierCategory.TYPE}));
            }
        }
        return areas;
    }
}

