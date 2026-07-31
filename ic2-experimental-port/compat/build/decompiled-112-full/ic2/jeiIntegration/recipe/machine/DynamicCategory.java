/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.IGuiHelper
 *  mezz.jei.api.gui.IDrawable
 *  mezz.jei.api.gui.IDrawableAnimated
 *  mezz.jei.api.gui.IDrawableAnimated$StartDirection
 *  mezz.jei.api.gui.IDrawableStatic
 *  net.minecraft.client.Minecraft
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.core.block.ITeBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.util.Tuple;
import ic2.jeiIntegration.SlotPosition;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;

public class DynamicCategory<T>
extends IORecipeCategory<T>
implements IDrawable {
    protected static final int xOffset = 0;
    protected static final int yOffset = -16;
    protected final List<Tuple.T2<IDrawable, SlotPosition>> elements = new ArrayList<Tuple.T2<IDrawable, SlotPosition>>();
    private final List<SlotPosition> inputSlots = new ArrayList<SlotPosition>();
    private final List<SlotPosition> outputSlots = new ArrayList<SlotPosition>();

    public DynamicCategory(ITeBlock block, T recipeManager, IGuiHelper guiHelper) {
        super(block, recipeManager);
        this.initializeWidgets(guiHelper, GuiParser.parse(block));
    }

    private void initializeWidgets(IGuiHelper guiHelper, GuiParser.ParentNode parentNode) {
        block8: for (GuiParser.Node rawNode : parentNode.getNodes()) {
            block0 : switch (rawNode.getType()) {
                case energygauge: {
                    GuiParser.Node node = (GuiParser.EnergyGaugeNode)rawNode;
                    SlotPosition pos = new SlotPosition(((GuiParser.EnergyGaugeNode)node).x + ((GuiParser.EnergyGaugeNode)node).style.properties.bgXOffset + 0, ((GuiParser.EnergyGaugeNode)node).y + ((GuiParser.EnergyGaugeNode)node).style.properties.bgYOffset + -16);
                    IDrawableStatic energyBackground = guiHelper.createDrawable(((GuiParser.EnergyGaugeNode)node).style.properties.texture, (int)((GuiParser.EnergyGaugeNode)node).style.properties.uBgInactive, (int)((GuiParser.EnergyGaugeNode)node).style.properties.vBgInactive, (int)((GuiParser.EnergyGaugeNode)node).style.properties.bgWidth, (int)((GuiParser.EnergyGaugeNode)node).style.properties.bgHeight);
                    this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>(energyBackground, pos));
                    energyBackground = guiHelper.createDrawable(((GuiParser.EnergyGaugeNode)node).style.properties.texture, (int)((GuiParser.EnergyGaugeNode)node).style.properties.uInner, (int)((GuiParser.EnergyGaugeNode)node).style.properties.vInner, (int)((GuiParser.EnergyGaugeNode)node).style.properties.innerWidth, (int)((GuiParser.EnergyGaugeNode)node).style.properties.innerHeight);
                    IDrawableAnimated energyAnimated = guiHelper.createAnimatedDrawable(energyBackground, 300, ((GuiParser.EnergyGaugeNode)node).style.properties.reverse ? (((GuiParser.EnergyGaugeNode)node).style.properties.vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.RIGHT) : (((GuiParser.EnergyGaugeNode)node).style.properties.vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.LEFT), true);
                    this.elements.add(new Tuple.T2<IDrawableAnimated, SlotPosition>(energyAnimated, new SlotPosition(((GuiParser.EnergyGaugeNode)node).x + 0, ((GuiParser.EnergyGaugeNode)node).y + -16)));
                    break;
                }
                case gauge: {
                    GuiParser.Node node = (GuiParser.GaugeNode)rawNode;
                    Gauge.GaugeProperties properties = ((GuiParser.GaugeNode)node).style.getProperties();
                    SlotPosition pos = new SlotPosition(((GuiParser.GaugeNode)node).x + properties.bgXOffset + 0, ((GuiParser.GaugeNode)node).y + properties.bgYOffset + -16);
                    IDrawableStatic guageBackground = guiHelper.createDrawable(properties.texture, (int)properties.uBgActive, (int)properties.vBgActive, (int)properties.bgWidth, (int)properties.bgHeight);
                    this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>(guageBackground, pos));
                    guageBackground = guiHelper.createDrawable(properties.texture, (int)properties.uInner, (int)properties.vInner, (int)properties.innerWidth, (int)properties.innerHeight);
                    Object gaugeForeground = ((GuiParser.GaugeNode)node).style == Gauge.GaugeStyle.HeatCentrifuge ? guageBackground : guiHelper.createAnimatedDrawable(guageBackground, this.getProcessSpeed(((GuiParser.GaugeNode)node).name), properties.reverse ? (properties.vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.RIGHT) : (properties.vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.LEFT), false);
                    this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>((IDrawableStatic)gaugeForeground, new SlotPosition(((GuiParser.GaugeNode)node).x + 0, ((GuiParser.GaugeNode)node).y + -16)));
                    break;
                }
                case image: {
                    GuiParser.Node node = (GuiParser.ImageNode)rawNode;
                    SlotPosition pos = new SlotPosition(((GuiParser.ImageNode)node).x + 0, ((GuiParser.ImageNode)node).y + -16);
                    IDrawableStatic image = guiHelper.createDrawable(((GuiParser.ImageNode)node).src, ((GuiParser.ImageNode)node).u1, ((GuiParser.ImageNode)node).v1, ((GuiParser.ImageNode)node).width, ((GuiParser.ImageNode)node).height, ((GuiParser.ImageNode)node).baseWidth, ((GuiParser.ImageNode)node).baseHeight);
                    this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>(image, pos));
                    break;
                }
                case slot: {
                    String slotName;
                    GuiParser.Node node = (GuiParser.SlotNode)rawNode;
                    SlotPosition pos = new SlotPosition(((GuiParser.SlotNode)node).x + 0, ((GuiParser.SlotNode)node).y + -16, ((GuiParser.SlotNode)node).style);
                    IDrawableStatic drawable = guiHelper.createDrawable(GuiElement.commonTexture, pos.getStyle().u, pos.getStyle().v, pos.getStyle().width, pos.getStyle().height);
                    this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>(drawable, pos));
                    int extraX = 0;
                    int extraY = 0;
                    if (((GuiParser.SlotNode)node).style == SlotGrid.SlotStyle.Large) {
                        extraY = 4;
                        extraX = 4;
                    }
                    if ((slotName = ((GuiParser.SlotNode)node).name.toLowerCase(Locale.ENGLISH)).contains("input") || slotName.equals("cutterInputSlot")) {
                        this.inputSlots.add(new SlotPosition(pos, extraX, extraY));
                        break;
                    }
                    if (!slotName.contains("output")) break;
                    this.outputSlots.add(new SlotPosition(pos, extraX, extraY));
                    break;
                }
                case slotgrid: {
                    GuiParser.Node node = (GuiParser.SlotGridNode)rawNode;
                    TileEntityInventory dummyTe = (TileEntityInventory)this.block.getDummyTe();
                    if (dummyTe == null) {
                        throw new NullPointerException("Received null dummy for " + this.block + " in the JeiPlugin.");
                    }
                    InvSlot slot = dummyTe.getInventorySlot(((GuiParser.SlotGridNode)node).name);
                    if (slot == null) {
                        throw new RuntimeException("invalid invslot name " + ((GuiParser.SlotGridNode)node).name + " for base " + dummyTe);
                    }
                    int size = slot.size();
                    if (size <= ((GuiParser.SlotGridNode)node).offset) break;
                    GuiParser.SlotGridNode.SlotGridDimension dim = ((GuiParser.SlotGridNode)node).getDimension(size);
                    IDrawableStatic drawable = guiHelper.createDrawable(GuiElement.commonTexture, ((GuiParser.SlotGridNode)node).style.u, ((GuiParser.SlotGridNode)node).style.v, ((GuiParser.SlotGridNode)node).style.width, ((GuiParser.SlotGridNode)node).style.height);
                    boolean isInput = ((GuiParser.SlotGridNode)node).name.toLowerCase().contains("input");
                    boolean isOutput = ((GuiParser.SlotGridNode)node).name.toLowerCase().contains("output");
                    for (int i = 0; i < dim.cols; ++i) {
                        for (int j = 0; j < dim.rows; ++j) {
                            if (i * dim.rows + j > size) break block0;
                            SlotPosition pos = new SlotPosition(((GuiParser.SlotGridNode)node).x + 0 + i * ((GuiParser.SlotGridNode)node).style.width, ((GuiParser.SlotGridNode)node).y + -16 + j * ((GuiParser.SlotGridNode)node).style.height, ((GuiParser.SlotGridNode)node).style);
                            this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>(drawable, pos));
                            if (isInput) {
                                this.inputSlots.add(pos);
                                continue;
                            }
                            if (!isOutput) continue;
                            this.outputSlots.add(pos);
                        }
                    }
                    continue block8;
                }
                case environment: {
                    GuiParser.Node node = (GuiParser.EnvironmentNode)rawNode;
                    if (((GuiParser.EnvironmentNode)node).environment != GuiEnvironment.JEI) break;
                    this.initializeWidgets(guiHelper, (GuiParser.ParentNode)node);
                    break;
                }
            }
        }
    }

    public IDrawable getBackground() {
        return this;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        for (Tuple.T2<IDrawable, SlotPosition> element : this.elements) {
            ((IDrawable)element.a).draw(minecraft, ((SlotPosition)element.b).getX(), ((SlotPosition)element.b).getY());
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

    public void draw(Minecraft minecraft) {
    }

    public void draw(Minecraft minecraft, int xOffset, int yOffset) {
    }

    public int getHeight() {
        return 60;
    }

    public int getWidth() {
        return 160;
    }

    protected int getProcessSpeed(String name) {
        TileEntityBlock te;
        if ("progress".equals(name) && (te = this.block.getDummyTe()) != null && te instanceof TileEntityStandardMachine) {
            return ((TileEntityStandardMachine)te).defaultOperationLength / 3;
        }
        return 200;
    }
}

