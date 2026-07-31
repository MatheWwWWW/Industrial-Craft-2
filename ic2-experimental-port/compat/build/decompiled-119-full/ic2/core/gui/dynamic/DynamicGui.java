/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.gui.dynamic;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.Button;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiDefaultBackground;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.Image;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.MouseButton;
import ic2.core.gui.RecipeButton;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.TankFluidSlot;
import ic2.core.gui.TankGauge;
import ic2.core.gui.TextLabel;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.util.LogCategory;
import java.util.Collections;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class DynamicGui<T extends Container>
extends GuiDefaultBackground<DynamicContainer<T>> {
    public static <T extends Container> DynamicGui<T> create(DynamicContainer<T> dynamicContainer, Inventory inventory, Component component) {
        return new DynamicGui<T>(dynamicContainer, inventory, component);
    }

    protected DynamicGui(DynamicContainer<T> dynamicContainer, Inventory inventory, Component component) {
        super(dynamicContainer, inventory, component, dynamicContainer.guiNode.width, dynamicContainer.guiNode.height);
        this.initializeWidgets(inventory, dynamicContainer.guiNode);
    }

    private void initializeWidgets(Inventory inventory, GuiParser.ParentNode parentNode) {
        block30: for (GuiParser.Node node : parentNode.getNodes()) {
            switch (node.getType()) {
                case environment: {
                    if (((GuiParser.EnvironmentNode)node).environment == GuiEnvironment.GAME) break;
                    continue block30;
                }
                case gui: 
                case key: 
                case only: 
                case tooltip: {
                    break;
                }
                case button: {
                    GuiParser.Node node2 = (GuiParser.ButtonNode)node;
                    if (((GuiParser.ButtonNode)node2).type != GuiParser.ButtonNode.ButtonType.RECIPE && !(((DynamicContainer)this.f_97732_).base instanceof INetworkClientTileEntityEventListener) && !this.isHandHeldGUI()) {
                        throw new RuntimeException("Invalid base " + ((DynamicContainer)this.f_97732_).base + " for button elements");
                    }
                    Button button = null;
                    switch (((GuiParser.ButtonNode)node2).type) {
                        case VANILLA: {
                            button = new VanillaButton(this, ((GuiParser.ButtonNode)node2).x, ((GuiParser.ButtonNode)node2).y, ((GuiParser.ButtonNode)node2).width, ((GuiParser.ButtonNode)node2).height, this.createEventSender(((GuiParser.ButtonNode)node2).eventID, ((GuiParser.ButtonNode)node2).eventName));
                            break;
                        }
                        case CUSTOM: {
                            break;
                        }
                        case TRANSPARENT: {
                            button = new CustomButton(this, ((GuiParser.ButtonNode)node2).x, ((GuiParser.ButtonNode)node2).y, ((GuiParser.ButtonNode)node2).width, ((GuiParser.ButtonNode)node2).height, this.createEventSender(((GuiParser.ButtonNode)node2).eventID, ((GuiParser.ButtonNode)node2).eventName));
                            break;
                        }
                        case RECIPE: {
                            if (!RecipeButton.canUse() || ((GuiParser.ButtonNode)node2).eventName == null) break;
                            button = new RecipeButton(this, ((GuiParser.ButtonNode)node2).x, ((GuiParser.ButtonNode)node2).y, ((GuiParser.ButtonNode)node2).width, ((GuiParser.ButtonNode)node2).height, ((GuiParser.ButtonNode)node2).eventName.split(",[ ]*"));
                            ((GuiParser.ButtonNode)node2).text = TextProvider.of("");
                        }
                    }
                    if (button == null) break;
                    Object object = ((GuiParser.ButtonNode)node2).text.get(((DynamicContainer)this.f_97732_).base, Collections.singletonMap("name", TextProvider.of(this.f_96539_)));
                    if (((GuiParser.ButtonNode)node2).icon == null) {
                        button = button.withText((String)object);
                    } else {
                        button.withIcon(() -> DynamicGui.lambda$initializeWidgets$0((GuiParser.ButtonNode)node2));
                        button.withTooltip((String)object);
                    }
                    parentNode.addElement(this, button);
                    break;
                }
                case energygauge: {
                    if (!(((DynamicContainer)this.f_97732_).base instanceof Ic2TileEntity) || !((Ic2TileEntity)((DynamicContainer)this.f_97732_).base).hasComponent(Energy.class)) {
                        throw new RuntimeException("invalid base " + ((DynamicContainer)this.f_97732_).base + " for energygauge elements");
                    }
                    GuiParser.Node node2 = (GuiParser.EnergyGaugeNode)node;
                    parentNode.addElement(this, new EnergyGauge(this, ((GuiParser.EnergyGaugeNode)node2).x, ((GuiParser.EnergyGaugeNode)node2).y, (Ic2TileEntity)((DynamicContainer)this.f_97732_).base, ((GuiParser.EnergyGaugeNode)node2).style));
                    break;
                }
                case gauge: {
                    if (!(((DynamicContainer)this.f_97732_).base instanceof IGuiValueProvider)) {
                        throw new RuntimeException("invalid base " + ((DynamicContainer)this.f_97732_).base + " for gauge elements");
                    }
                    GuiParser.Node node2 = (GuiParser.GaugeNode)node;
                    final boolean bl = ((GuiParser.GaugeNode)node2).activeLinked;
                    if (bl && !(((DynamicContainer)this.f_97732_).base instanceof IGuiValueProvider.IActiveGuiValueProvider)) {
                        throw new RuntimeException("Invalid base " + ((DynamicContainer)this.f_97732_).base + " for active linked gauge elements");
                    }
                    parentNode.addElement(this, new LinkedGauge(this, ((GuiParser.GaugeNode)node2).x, ((GuiParser.GaugeNode)node2).y, (IGuiValueProvider)((DynamicContainer)this.f_97732_).base, ((GuiParser.GaugeNode)node2).name, ((GuiParser.GaugeNode)node2).style){

                        @Override
                        protected boolean isActive(double d) {
                            return bl ? ((IGuiValueProvider.IActiveGuiValueProvider)((DynamicContainer)((DynamicGui)DynamicGui.this).f_97732_).base).isGuiValueActive(this.name) : super.isActive(d);
                        }
                    });
                    break;
                }
                case image: {
                    GuiParser.Node node2 = (GuiParser.ImageNode)node;
                    parentNode.addElement(this, Image.create(this, ((GuiParser.ImageNode)node2).x, ((GuiParser.ImageNode)node2).y, ((GuiParser.ImageNode)node2).width, ((GuiParser.ImageNode)node2).height, ((GuiParser.ImageNode)node2).src, ((GuiParser.ImageNode)node2).baseWidth, ((GuiParser.ImageNode)node2).baseHeight, ((GuiParser.ImageNode)node2).u1, ((GuiParser.ImageNode)node2).v1, ((GuiParser.ImageNode)node2).u2, ((GuiParser.ImageNode)node2).v2));
                    break;
                }
                case playerinventory: {
                    GuiParser.Node node2 = (GuiParser.PlayerInventoryNode)node;
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.PlayerInventoryNode)node2).x, ((GuiParser.PlayerInventoryNode)node2).y, 9, 3, ((GuiParser.PlayerInventoryNode)node2).style, 0, ((GuiParser.PlayerInventoryNode)node2).spacing));
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.PlayerInventoryNode)node2).x, ((GuiParser.PlayerInventoryNode)node2).y + ((GuiParser.PlayerInventoryNode)node2).hotbarOffset, 9, 1, ((GuiParser.PlayerInventoryNode)node2).style, 0, ((GuiParser.PlayerInventoryNode)node2).spacing));
                    if (!((GuiParser.PlayerInventoryNode)node2).showTitle) break;
                    parentNode.addElement(this, TextLabel.create(this, ((GuiParser.PlayerInventoryNode)node2).x + 1, ((GuiParser.PlayerInventoryNode)node2).y - 10, TextProvider.of(inventory.m_7755_()), 0x404040, false));
                    break;
                }
                case slot: 
                case slothologram: {
                    GuiParser.Node node2 = (GuiParser.SlotNode)node;
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.SlotNode)node2).x, ((GuiParser.SlotNode)node2).y, 1, 1, ((GuiParser.SlotNode)node2).style));
                    break;
                }
                case slotgrid: {
                    if (!(((DynamicContainer)this.f_97732_).base instanceof IInventorySlotHolder)) {
                        throw new RuntimeException("Invalid base " + ((DynamicContainer)this.f_97732_).base + " for slot elements");
                    }
                    GuiParser.Node node2 = (GuiParser.SlotGridNode)node;
                    InvSlot invSlot = ((IInventorySlotHolder)((DynamicContainer)this.f_97732_).base).getInventorySlot(((GuiParser.SlotGridNode)node2).name);
                    if (invSlot == null) {
                        throw new RuntimeException("Invalid InvSlot name " + ((GuiParser.SlotGridNode)node2).name + " for base " + ((DynamicContainer)this.f_97732_).base);
                    }
                    int n = invSlot.size();
                    if (n <= ((GuiParser.SlotGridNode)node2).offset) break;
                    GuiParser.SlotGridNode.SlotGridDimension slotGridDimension = ((GuiParser.SlotGridNode)node2).getDimension(n);
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.SlotGridNode)node2).x, ((GuiParser.SlotGridNode)node2).y, slotGridDimension.cols, slotGridDimension.rows, ((GuiParser.SlotGridNode)node2).style, 0, ((GuiParser.SlotGridNode)node2).spacing));
                    break;
                }
                case text: {
                    GuiParser.Node node2 = (GuiParser.TextNode)node;
                    int n = switch (((GuiParser.TextNode)node2).align) {
                        case TextLabel.TextAlignment.Start -> ((GuiParser.TextNode)node2).x;
                        case TextLabel.TextAlignment.Center -> ((GuiParser.TextNode)node2).x + this.f_97726_ / 2;
                        case TextLabel.TextAlignment.End -> ((GuiParser.TextNode)node2).x + this.f_97726_;
                        default -> throw new IllegalArgumentException("invalid alignment: " + ((GuiParser.TextNode)node2).align);
                    };
                    Object object = ((GuiParser.TextNode)node2).rightAligned ? TextLabel.createRightAligned(this, n, ((GuiParser.TextNode)node2).y, ((GuiParser.TextNode)node2).width, ((GuiParser.TextNode)node2).height, ((GuiParser.TextNode)node2).text, ((GuiParser.TextNode)node2).color, ((GuiParser.TextNode)node2).shadow, ((GuiParser.TextNode)node2).xOffset, ((GuiParser.TextNode)node2).yOffset, ((GuiParser.TextNode)node2).centerX, ((GuiParser.TextNode)node2).centerY) : TextLabel.create(this, n, ((GuiParser.TextNode)node2).y, ((GuiParser.TextNode)node2).width, ((GuiParser.TextNode)node2).height, ((GuiParser.TextNode)node2).text, ((GuiParser.TextNode)node2).color, ((GuiParser.TextNode)node2).shadow, ((GuiParser.TextNode)node2).xOffset, ((GuiParser.TextNode)node2).yOffset, ((GuiParser.TextNode)node2).centerX, ((GuiParser.TextNode)node2).centerY);
                    parentNode.addElement(this, (GuiElement<?>)object);
                    break;
                }
                case fluidtank: {
                    if (!(((DynamicContainer)this.f_97732_).base instanceof Ic2TileEntity) || !((Ic2TileEntity)((DynamicContainer)this.f_97732_).base).hasComponent(Fluids.class)) {
                        throw new RuntimeException("invalid base " + ((DynamicContainer)this.f_97732_).base + " for tank elements");
                    }
                    GuiParser.Node node2 = (GuiParser.FluidTankNode)node;
                    Fluids fluids = ((Ic2TileEntity)((DynamicContainer)this.f_97732_).base).getComponent(Fluids.class);
                    Object object = switch (((GuiParser.FluidTankNode)node2).type) {
                        case GuiParser.FluidTankNode.TankType.NORMAL -> TankGauge.createNormal(this, ((GuiParser.FluidTankNode)node2).x, ((GuiParser.FluidTankNode)node2).y, fluids.getFluidTank(((GuiParser.FluidTankNode)node2).name));
                        case GuiParser.FluidTankNode.TankType.PLAIN -> TankGauge.createPlain(this, ((GuiParser.FluidTankNode)node2).x, ((GuiParser.FluidTankNode)node2).y, ((GuiParser.FluidTankNode)node2).width, ((GuiParser.FluidTankNode)node2).height, fluids.getFluidTank(((GuiParser.FluidTankNode)node2).name));
                        case GuiParser.FluidTankNode.TankType.BORDERLESS -> TankGauge.createBorderless(this, ((GuiParser.FluidTankNode)node2).x, ((GuiParser.FluidTankNode)node2).y, fluids.getFluidTank(((GuiParser.FluidTankNode)node2).name), ((GuiParser.FluidTankNode)node2).mirrored);
                        default -> throw new IllegalStateException("Unexpected type " + ((GuiParser.FluidTankNode)node2).type);
                    };
                    parentNode.addElement(this, (GuiElement<?>)object);
                    break;
                }
                case fluidslot: {
                    if (!(((DynamicContainer)this.f_97732_).base instanceof Ic2TileEntity) || !((Ic2TileEntity)((DynamicContainer)this.f_97732_).base).hasComponent(Fluids.class)) {
                        throw new RuntimeException("invalid base " + ((DynamicContainer)this.f_97732_).base + " for tank elements");
                    }
                    GuiParser.Node node2 = (GuiParser.FluidSlotNode)node;
                    parentNode.addElement(this, TankFluidSlot.createFluidSlot(this, ((GuiParser.FluidSlotNode)node2).x, ((GuiParser.FluidSlotNode)node2).y, ((Ic2TileEntity)((DynamicContainer)this.f_97732_).base).getComponent(Fluids.class).getFluidTank(((GuiParser.FluidSlotNode)node2).name)));
                    break;
                }
            }
            if (!(node instanceof GuiParser.ParentNode)) continue;
            this.initializeWidgets(inventory, (GuiParser.ParentNode)node);
        }
    }

    protected IClickHandler createEventSender(int n, String string) {
        if (this.isHandHeldGUI()) {
            String string2;
            if (string == null) {
                IC2.log.warn(LogCategory.General, "HandHand inventory given numeric event rather than string");
                string2 = Integer.toString(n);
            } else {
                string2 = string;
            }
            return new IClickHandler(){

                @Override
                public void onClick(MouseButton mouseButton) {
                    IC2.network.get(false).sendContainerEvent((ContainerBase)DynamicGui.this.f_97732_, string2);
                    ((HandHeldInventory)((DynamicContainer)((DynamicGui)DynamicGui.this).f_97732_).base).onEvent(string2);
                }
            };
        }
        assert (string == null);
        return this.createEventSender(n);
    }

    protected boolean isHandHeldGUI() {
        return ((DynamicContainer)this.f_97732_).base instanceof HandHeldInventory;
    }

    @Override
    public void addElement(GuiElement<?> guiElement) {
        super.addElement(guiElement);
    }

    private static /* synthetic */ ItemStack lambda$initializeWidgets$0(GuiParser.ButtonNode buttonNode) {
        return buttonNode.icon;
    }
}

