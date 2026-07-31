/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.drawable.IDrawableAnimated
 *  mezz.jei.api.gui.drawable.IDrawableAnimated$StartDirection
 *  mezz.jei.api.gui.drawable.IDrawableStatic
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeType
 *  net.minecraft.core.Registry
 */
package ic2.integration.jei.recipe.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.util.Tuple;
import ic2.integration.jei.recipe.machine.IORecipeCategory;
import ic2.integration.jei.recipe.machine.IORecipeWrapper;
import ic2.integration.jeirei.SlotPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.Registry;

public class DynamicCategory
extends IORecipeCategory {
    protected final int xOffset;
    protected final int yOffset;
    protected final List<Tuple.T2<IDrawable, SlotPosition>> elements = new ArrayList<Tuple.T2<IDrawable, SlotPosition>>();
    private final List<SlotPosition> inputSlots = new ArrayList<SlotPosition>();
    private final List<SlotPosition> outputSlots = new ArrayList<SlotPosition>();
    private final IDrawable background;
    private final RecipeType<IORecipeWrapper> recipeType;

    public DynamicCategory(Ic2TileEntityBlock ic2TileEntityBlock, RecipeType<IORecipeWrapper> recipeType, IGuiHelper iGuiHelper) {
        super(ic2TileEntityBlock);
        this.recipeType = recipeType;
        this.initializeWidgets(iGuiHelper, GuiParser.parse(Registry.f_122824_.m_7981_((Object)ic2TileEntityBlock), ic2TileEntityBlock.getDummyTe().getClass()));
        int n = 1000;
        int n2 = 1000;
        int n3 = -1000;
        int n4 = -1000;
        for (Tuple.T2<IDrawable, SlotPosition> t2 : this.elements) {
            n = Math.min(n, ((SlotPosition)t2.b).getX());
            n2 = Math.min(n2, ((SlotPosition)t2.b).getY());
            n3 = Math.max(n3, ((SlotPosition)t2.b).getX() + ((IDrawable)t2.a).getWidth());
            n4 = Math.max(n4, ((SlotPosition)t2.b).getY() + ((IDrawable)t2.a).getHeight());
        }
        int n5 = n3 - n;
        int n6 = n4 - n2;
        this.xOffset = -n;
        this.yOffset = -n2;
        this.background = iGuiHelper.createBlankDrawable(n5, n6);
    }

    private void initializeWidgets(IGuiHelper iGuiHelper, GuiParser.ParentNode parentNode) {
        block8: for (GuiParser.Node node : parentNode.getNodes()) {
            block0 : switch (node.getType()) {
                case energygauge: {
                    GuiParser.Node node2 = (GuiParser.EnergyGaugeNode)node;
                    Object object = new SlotPosition(((GuiParser.EnergyGaugeNode)node2).x + ((GuiParser.EnergyGaugeNode)node2).style.properties.bgXOffset, ((GuiParser.EnergyGaugeNode)node2).y + ((GuiParser.EnergyGaugeNode)node2).style.properties.bgYOffset);
                    Object object2 = iGuiHelper.createDrawable(((GuiParser.EnergyGaugeNode)node2).style.properties.texture, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.uBgInactive, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.vBgInactive, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.bgWidth, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.bgHeight);
                    this.elements.add(new Tuple.T2<Object, Object>(object2, object));
                    object2 = iGuiHelper.createDrawable(((GuiParser.EnergyGaugeNode)node2).style.properties.texture, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.uInner, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.vInner, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.innerWidth, (int)((GuiParser.EnergyGaugeNode)node2).style.properties.innerHeight);
                    IDrawableAnimated iDrawableAnimated = iGuiHelper.createAnimatedDrawable((IDrawableStatic)object2, 300, ((GuiParser.EnergyGaugeNode)node2).style.properties.reverse ? (((GuiParser.EnergyGaugeNode)node2).style.properties.vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.RIGHT) : (((GuiParser.EnergyGaugeNode)node2).style.properties.vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.LEFT), true);
                    this.elements.add(new Tuple.T2<IDrawableAnimated, SlotPosition>(iDrawableAnimated, new SlotPosition(((GuiParser.EnergyGaugeNode)node2).x, ((GuiParser.EnergyGaugeNode)node2).y)));
                    break;
                }
                case gauge: {
                    GuiParser.Node node2 = (GuiParser.GaugeNode)node;
                    Object object = ((GuiParser.GaugeNode)node2).style.getProperties();
                    Object object2 = new SlotPosition(((GuiParser.GaugeNode)node2).x + ((Gauge.GaugeProperties)object).bgXOffset, ((GuiParser.GaugeNode)node2).y + ((Gauge.GaugeProperties)object).bgYOffset);
                    IDrawableStatic iDrawableStatic = iGuiHelper.createDrawable(((Gauge.GaugeProperties)object).texture, (int)((Gauge.GaugeProperties)object).uBgActive, (int)((Gauge.GaugeProperties)object).vBgActive, (int)((Gauge.GaugeProperties)object).bgWidth, (int)((Gauge.GaugeProperties)object).bgHeight);
                    this.elements.add(new Tuple.T2<IDrawableStatic, Object>(iDrawableStatic, object2));
                    iDrawableStatic = iGuiHelper.createDrawable(((Gauge.GaugeProperties)object).texture, (int)((Gauge.GaugeProperties)object).uInner, (int)((Gauge.GaugeProperties)object).vInner, (int)((Gauge.GaugeProperties)object).innerWidth, (int)((Gauge.GaugeProperties)object).innerHeight);
                    Object object3 = ((GuiParser.GaugeNode)node2).style == Gauge.GaugeStyle.HeatCentrifuge ? iDrawableStatic : iGuiHelper.createAnimatedDrawable(iDrawableStatic, this.getProcessSpeed(((GuiParser.GaugeNode)node2).name), ((Gauge.GaugeProperties)object).reverse ? (((Gauge.GaugeProperties)object).vertical ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.RIGHT) : (((Gauge.GaugeProperties)object).vertical ? IDrawableAnimated.StartDirection.TOP : IDrawableAnimated.StartDirection.LEFT), false);
                    this.elements.add(new Tuple.T2<IDrawableStatic, SlotPosition>((IDrawableStatic)object3, new SlotPosition(((GuiParser.GaugeNode)node2).x, ((GuiParser.GaugeNode)node2).y)));
                    break;
                }
                case image: {
                    GuiParser.Node node2 = (GuiParser.ImageNode)node;
                    Object object = new SlotPosition(((GuiParser.ImageNode)node2).x, ((GuiParser.ImageNode)node2).y);
                    Object object2 = iGuiHelper.drawableBuilder(((GuiParser.ImageNode)node2).src, ((GuiParser.ImageNode)node2).u1, ((GuiParser.ImageNode)node2).v1, ((GuiParser.ImageNode)node2).width, ((GuiParser.ImageNode)node2).height).setTextureSize(((GuiParser.ImageNode)node2).baseWidth, ((GuiParser.ImageNode)node2).baseHeight).build();
                    this.elements.add(new Tuple.T2<Object, Object>(object2, object));
                    break;
                }
                case slot: {
                    GuiParser.Node node2 = (GuiParser.SlotNode)node;
                    Object object = new SlotPosition(((GuiParser.SlotNode)node2).x, ((GuiParser.SlotNode)node2).y, ((GuiParser.SlotNode)node2).style);
                    Object object2 = iGuiHelper.createDrawable(GuiElement.commonTexture, ((SlotPosition)object).getStyle().u, ((SlotPosition)object).getStyle().v, ((SlotPosition)object).getStyle().width, ((SlotPosition)object).getStyle().height);
                    this.elements.add(new Tuple.T2<Object, Object>(object2, object));
                    int n = (((GuiParser.SlotNode)node2).style.width - 16) / 2;
                    int n2 = (((GuiParser.SlotNode)node2).style.height - 16) / 2;
                    Object object4 = ((GuiParser.SlotNode)node2).name.toLowerCase(Locale.ENGLISH);
                    if (((String)object4).contains("input") || ((String)object4).equals("cutterInputSlot")) {
                        this.inputSlots.add(new SlotPosition((SlotPosition)object, n, n2));
                        break;
                    }
                    if (!((String)object4).contains("output")) break;
                    this.outputSlots.add(new SlotPosition((SlotPosition)object, n, n2));
                    break;
                }
                case slotgrid: {
                    GuiParser.Node node2 = (GuiParser.SlotGridNode)node;
                    Object object = (TileEntityInventory)this.block.getDummyTe();
                    if (object == null) {
                        throw new NullPointerException("Received null dummy for " + this.block + " in the JeiPlugin.");
                    }
                    Object object2 = ((TileEntityInventory)object).getInventorySlot(((GuiParser.SlotGridNode)node2).name);
                    if (object2 == null) {
                        throw new RuntimeException("invalid invslot name " + ((GuiParser.SlotGridNode)node2).name + " for base " + (TileEntityInventory)object);
                    }
                    int n = ((InvSlot)object2).size();
                    if (n <= ((GuiParser.SlotGridNode)node2).offset) break;
                    GuiParser.SlotGridNode.SlotGridDimension slotGridDimension = ((GuiParser.SlotGridNode)node2).getDimension(n);
                    Object object4 = iGuiHelper.createDrawable(GuiElement.commonTexture, ((GuiParser.SlotGridNode)node2).style.u, ((GuiParser.SlotGridNode)node2).style.v, ((GuiParser.SlotGridNode)node2).style.width, ((GuiParser.SlotGridNode)node2).style.height);
                    boolean bl = ((GuiParser.SlotGridNode)node2).name.toLowerCase().contains("input");
                    boolean bl2 = ((GuiParser.SlotGridNode)node2).name.toLowerCase().contains("output");
                    int n3 = (((GuiParser.SlotGridNode)node2).style.width - 16) / 2;
                    int n4 = (((GuiParser.SlotGridNode)node2).style.height - 16) / 2;
                    for (int i = 0; i < slotGridDimension.cols; ++i) {
                        for (int j = 0; j < slotGridDimension.rows; ++j) {
                            if (i * slotGridDimension.rows + j > n) break block0;
                            SlotPosition slotPosition = new SlotPosition(((GuiParser.SlotGridNode)node2).x + i * ((GuiParser.SlotGridNode)node2).style.width, ((GuiParser.SlotGridNode)node2).y + j * ((GuiParser.SlotGridNode)node2).style.height, ((GuiParser.SlotGridNode)node2).style);
                            this.elements.add(new Tuple.T2<Object, SlotPosition>(object4, slotPosition));
                            if (bl) {
                                this.inputSlots.add(new SlotPosition(slotPosition, n3, n4));
                                continue;
                            }
                            if (!bl2) continue;
                            this.outputSlots.add(new SlotPosition(slotPosition, n3, n4));
                        }
                    }
                    continue block8;
                }
                case environment: {
                    GuiParser.Node node2 = (GuiParser.EnvironmentNode)node;
                    if (((GuiParser.EnvironmentNode)node2).environment != GuiEnvironment.JEI) break;
                    this.initializeWidgets(iGuiHelper, (GuiParser.ParentNode)node2);
                    break;
                }
            }
        }
    }

    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, IORecipeWrapper iORecipeWrapper, IFocusGroup iFocusGroup) {
        this.addRecipeSlots(iRecipeLayoutBuilder, iORecipeWrapper, iFocusGroup, this.xOffset, this.yOffset);
    }

    public void draw(IORecipeWrapper iORecipeWrapper, IRecipeSlotsView iRecipeSlotsView, PoseStack poseStack, double d, double d2) {
        for (Tuple.T2<IDrawable, SlotPosition> t2 : this.elements) {
            ((IDrawable)t2.a).draw(poseStack, ((SlotPosition)t2.b).getX() + this.xOffset, ((SlotPosition)t2.b).getY() + this.yOffset);
        }
    }

    public RecipeType<IORecipeWrapper> getRecipeType() {
        return this.recipeType;
    }

    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    protected List<SlotPosition> getInputSlotPos() {
        return this.inputSlots;
    }

    @Override
    protected List<SlotPosition> getOutputSlotPos() {
        return this.outputSlots;
    }

    protected int getProcessSpeed(String string) {
        Ic2TileEntity ic2TileEntity;
        if ("progress".equals(string) && (ic2TileEntity = this.block.getDummyTe()) != null && ic2TileEntity instanceof TileEntityStandardMachine) {
            return ((TileEntityStandardMachine)ic2TileEntity).defaultOperationLength / 3;
        }
        return 200;
    }
}

