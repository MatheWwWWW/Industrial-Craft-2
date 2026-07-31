/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.gui.handlers.IGuiClickableArea
 *  mezz.jei.api.gui.handlers.IGuiContainerHandler
 *  mezz.jei.api.recipe.RecipeType
 *  net.minecraft.client.renderer.Rect2i
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.jeiplugin.core;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IRecipeMachine;
import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.block.generators.tiles.LiquidFuelGenTileEntity;
import ic2.core.block.machines.components.ev.ColossalMachineComponent;
import ic2.core.block.machines.containers.ev.ColossalMachineContainer;
import ic2.core.block.machines.containers.mv.ReactorPlannerContainer;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.simple.FuelComponent;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.renderer.Rect2i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class IC2GuiHandler
implements IGuiContainerHandler<IC2Screen> {
    Map<IMachineRecipeList, RecipeType<?>> locations;
    Map<Class<?>, RecipeType<?>> classBased;

    public IC2GuiHandler(Map<IMachineRecipeList, RecipeType<?>> locations, Map<Class<?>, RecipeType<?>> classBased) {
        this.locations = locations;
        this.classBased = classBased;
    }

    public List<Rect2i> getGuiExtraAreas(IC2Screen containerScreen) {
        ObjectList extraBoxes = CollectionUtils.createList();
        if (containerScreen.m_6262_() instanceof ReactorPlannerContainer) {
            extraBoxes.add((Rect2i)new Rect2i(containerScreen.getGuiLeft() + 80, containerScreen.getGuiTop(), 212, 212));
        }
        if (containerScreen instanceof ComponentContainerScreen) {
            ComponentContainerScreen screen = (ComponentContainerScreen)containerScreen;
            for (GuiWidget comp : screen.getComponents()) {
                Box2i box = comp.getBox();
                if (box == Box2i.EMPTY_BOX || !comp.isVisible()) continue;
                extraBoxes.add((Rect2i)new Rect2i(containerScreen.getGuiLeft() + box.getX(), containerScreen.getGuiTop() + box.getY(), box.getWidth(), box.getHeight()));
            }
        }
        return extraBoxes;
    }

    public Collection<IGuiClickableArea> getGuiClickableAreas(IC2Screen containerScreen, double mouseX, double mouseY) {
        ContainerComponent comp;
        ObjectList areas = CollectionUtils.createList();
        if (containerScreen instanceof ComponentContainerScreen && (comp = containerScreen.getCastedContainer(ContainerComponent.class)) != null) {
            Box2i box;
            ProgressComponent progress;
            IRecipeMachine recipe;
            RecipeType<?> location;
            if (comp.getHolder() instanceof IRecipeMachine && (location = this.locations.get((recipe = (IRecipeMachine)comp.getHolder()).getRecipeList())) != null && (progress = ((ComponentContainerScreen)containerScreen).getComponentFromClass(ProgressComponent.class)) != null) {
                box = progress.getBox();
                areas.add((IGuiClickableArea)IGuiClickableArea.createBasic((int)box.getX(), (int)box.getY(), (int)box.getWidth(), (int)box.getHeight(), (RecipeType[])new RecipeType[]{location}));
            }
            if (comp.getHolder() != null) {
                RecipeType<?> location2 = this.classBased.get(comp.getHolder().getClass());
                if (location2 != null) {
                    FuelComponent fuel;
                    ProgressComponent progress2 = ((ComponentContainerScreen)containerScreen).getComponentFromClass(ProgressComponent.class);
                    if (progress2 != null) {
                        Box2i box2 = progress2.getBox();
                        areas.add((IGuiClickableArea)IGuiClickableArea.createBasic((int)box2.getX(), (int)box2.getY(), (int)box2.getWidth(), (int)box2.getHeight(), (RecipeType[])new RecipeType[]{location2}));
                    } else if (comp.getHolder() instanceof LiquidFuelGenTileEntity && (fuel = ((ComponentContainerScreen)containerScreen).getComponentFromClass(FuelComponent.class)) != null) {
                        box = fuel.getBox();
                        areas.add((IGuiClickableArea)IGuiClickableArea.createBasic((int)box.getX(), (int)box.getY(), (int)box.getWidth(), (int)box.getHeight(), (RecipeType[])new RecipeType[]{location2}));
                    }
                }
                if (comp instanceof ColossalMachineContainer && comp.getComponent(ColossalMachineComponent.class) != null) {
                    for (Vec2i pos : ColossalMachineComponent.PROGRESS[((BaseColossalMachineTileEntity)comp.getHolder()).getSlotsInUse()]) {
                        areas.add((IGuiClickableArea)IGuiClickableArea.createBasic((int)pos.getX(), (int)pos.getY(), (int)24, (int)16, (RecipeType[])new RecipeType[]{location2}));
                    }
                }
            }
        }
        return areas;
    }
}

