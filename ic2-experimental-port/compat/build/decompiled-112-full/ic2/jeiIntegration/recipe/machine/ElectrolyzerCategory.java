/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.IGuiHelper
 *  mezz.jei.api.gui.IDrawable
 *  mezz.jei.api.gui.IDrawableAnimated
 *  mezz.jei.api.gui.IDrawableAnimated$StartDirection
 *  mezz.jei.api.gui.IDrawableStatic
 *  mezz.jei.api.gui.IGuiFluidStackGroup
 *  mezz.jei.api.gui.IRecipeLayout
 *  mezz.jei.api.ingredients.IIngredients
 *  mezz.jei.api.recipe.IRecipeWrapper
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.api.recipe.IElectrolyzerRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.machine.gui.GuiElectrolyzer;
import ic2.core.gui.ElectrolyzerTankController;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.ref.TeBlock;
import ic2.core.util.Tuple;
import ic2.jeiIntegration.SlotPosition;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;

public final class ElectrolyzerCategory
extends IORecipeCategory<IElectrolyzerRecipeManager>
implements IDrawable {
    private static final int xOffset = 0;
    private static final int yOffset = 0;
    private final List<SlotPosition> inputSlots;
    private final List<SlotPosition> outputSlots;
    private final List<Tuple.T2<IDrawable, SlotPosition>> elements = new ArrayList<Tuple.T2<IDrawable, SlotPosition>>();
    private final List<Tuple.T2<IDrawable, SlotPosition>> progress = new ArrayList<Tuple.T2<IDrawable, SlotPosition>>();

    public ElectrolyzerCategory(IGuiHelper guiHelper) {
        super(TeBlock.electrolyzer, Recipes.electrolyzer);
        SlotPosition pos = new SlotPosition(78, 0);
        this.elements.add(new Tuple.T2<IDrawable, SlotPosition>(this.getFluidSlot(guiHelper), pos));
        this.inputSlots = Collections.singletonList(pos);
        ArrayList<SlotPosition> tempOutput = new ArrayList<SlotPosition>(5);
        for (int i = 0; i < 5; ++i) {
            pos = new SlotPosition(36 + 21 * i + 0, 45);
            this.elements.add(new Tuple.T2<IDrawable, SlotPosition>(this.getFluidSlot(guiHelper), pos));
            tempOutput.add(pos);
        }
        this.outputSlots = Collections.unmodifiableList(tempOutput);
        Gauge.GaugeProperties energyStyle = EnergyGauge.EnergyGaugeStyle.get((String)EnergyGauge.EnergyGaugeStyle.Bolt.name).properties;
        pos = new SlotPosition(12 + energyStyle.bgXOffset + 0, 44 + energyStyle.bgYOffset + 0 - 16);
        this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>(guiHelper.createDrawable(energyStyle.texture, (int)energyStyle.uBgInactive, (int)energyStyle.vBgInactive, (int)energyStyle.bgWidth, (int)energyStyle.bgHeight), pos));
        this.progress.add(new Tuple.T2<IDrawableAnimated, SlotPosition>(guiHelper.createAnimatedDrawable(this.drawableProperties(guiHelper, energyStyle), 300, this.getDirection(energyStyle.reverse, energyStyle.vertical), true), new SlotPosition(12, 28)));
        this.progress.add(new Tuple.T2<IDrawableAnimated, SlotPosition>(guiHelper.createAnimatedDrawable(this.drawableProperties(guiHelper, GuiElectrolyzer.ElectrolyzerGauges.THREE_TANK.properties), 100, IDrawableAnimated.StartDirection.TOP, false), new SlotPosition(60, 20)));
    }

    private IDrawableStatic drawableProperties(IGuiHelper guiHelper, Gauge.GaugeProperties properties) {
        return guiHelper.createDrawable(properties.texture, (int)properties.uInner, (int)properties.vInner, (int)properties.innerWidth, (int)properties.innerHeight);
    }

    private IDrawableAnimated.StartDirection getDirection(boolean reverse, boolean vertical) {
        return reverse ? (vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.RIGHT) : (vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.LEFT);
    }

    private IDrawable getFluidSlot(IGuiHelper guiHelper) {
        return guiHelper.createDrawable(GuiElement.commonTexture, 8, 160, 18, 18);
    }

    public IDrawable getBackground() {
        return this;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        for (Tuple.T2<IDrawable, SlotPosition> bar : this.progress) {
            ((IDrawable)bar.a).draw(minecraft, ((SlotPosition)bar.b).getX(), ((SlotPosition)bar.b).getY());
        }
    }

    @Override
    protected List<SlotPosition> getInputSlotPos() {
        return this.inputSlots;
    }

    @Override
    protected List<SlotPosition> getOutputSlotPos() {
        return this.outputSlots;
    }

    private static List<FluidStack> unpack(List<List<FluidStack>> in) {
        ArrayList<FluidStack> out = new ArrayList<FluidStack>();
        for (List<FluidStack> stack : in) {
            if (stack.isEmpty()) continue;
            out.add(stack.get(0));
        }
        return out;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        List<SlotPosition> inputSlots = this.getInputSlotPos();
        List inputStacks = ingredients.getInputs(FluidStack.class);
        int ID = 0;
        SlotPosition pos = inputSlots.get(0);
        FluidStack fluid = (FluidStack)((List)inputStacks.get(0)).get(0);
        fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
        fluidStacks.set(ID++, fluid);
        List<SlotPosition> outputSlots = this.getOutputSlotPos();
        List<FluidStack> outputStacks = ElectrolyzerCategory.unpack(ingredients.getOutputs(FluidStack.class));
        int length = outputStacks.size();
        if (ElectrolyzerTankController.ONE_THREE_FIVE.contains(length)) {
            pos = outputSlots.get(3);
            fluid = outputStacks.get(length / 2);
            fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
            fluidStacks.set(ID++, fluid);
        }
        if (ElectrolyzerTankController.TWO_TO_FIVE.contains(length)) {
            pos = outputSlots.get(1);
            fluid = outputStacks.get(length < 4 ? 0 : 1);
            fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
            fluidStacks.set(ID++, fluid);
            pos = outputSlots.get(3);
            fluid = outputStacks.get(length - (length < 4 ? 1 : 2));
            fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
            fluidStacks.set(ID++, fluid);
        }
        if (ElectrolyzerTankController.FOUR_FIVE.contains(length)) {
            pos = outputSlots.get(0);
            fluid = outputStacks.get(0);
            fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
            fluidStacks.set(ID++, fluid);
            pos = outputSlots.get(4);
            fluid = outputStacks.get(length - 1);
            fluidStacks.init(ID, false, pos.getX() + 1, pos.getY() + 1, 16, 16, fluid.amount, false, null);
            fluidStacks.set(ID++, fluid);
        }
    }

    public void draw(Minecraft minecraft) {
        for (Tuple.T2<IDrawable, SlotPosition> element : this.elements) {
            ((IDrawable)element.a).draw(minecraft, ((SlotPosition)element.b).getX(), ((SlotPosition)element.b).getY());
        }
    }

    public void draw(Minecraft minecraft, int xOffset, int yOffset) {
    }

    public int getWidth() {
        return 160;
    }

    public int getHeight() {
        return 60;
    }
}

