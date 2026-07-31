/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.gui.dynamic;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlot;
import ic2.core.gui.Button;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.FluidSlot;
import ic2.core.gui.GuiDefaultBackground;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.Image;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.MouseButton;
import ic2.core.gui.RecipeButton;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.TankGauge;
import ic2.core.gui.Text;
import ic2.core.gui.VanillaButton;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicHandHeldContainer;
import ic2.core.gui.dynamic.GuiEnvironment;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.item.tool.HandHeldInventory;
import ic2.core.util.LogCategory;
import java.util.Collections;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;

public class DynamicGui<T extends ContainerBase<? extends IInventory>>
extends GuiDefaultBackground<T> {
    public static <T extends IInventory> DynamicGui<ContainerBase<T>> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
        DynamicContainer<T> container = DynamicContainer.create(base, player, guiNode);
        return new DynamicGui<ContainerBase<T>>(player, container, guiNode);
    }

    public static <T extends HandHeldInventory> DynamicGui<ContainerBase<T>> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
        DynamicHandHeldContainer<T> container = DynamicHandHeldContainer.create(base, player, guiNode);
        return new DynamicGui<ContainerBase<T>>(player, container, guiNode);
    }

    protected DynamicGui(EntityPlayer player, T container, GuiParser.GuiNode guiNode) {
        super(container, guiNode.width, guiNode.height);
        this.initializeWidgets(player, guiNode);
    }

    private void initializeWidgets(EntityPlayer player, GuiParser.ParentNode parentNode) {
        block30: for (GuiParser.Node rawNode : parentNode.getNodes()) {
            switch (rawNode.getType()) {
                case environment: {
                    if (((GuiParser.EnvironmentNode)rawNode).environment == GuiEnvironment.GAME) break;
                    continue block30;
                }
                case gui: 
                case key: 
                case only: 
                case tooltip: {
                    break;
                }
                case button: {
                    GuiParser.Node node = (GuiParser.ButtonNode)rawNode;
                    if (((GuiParser.ButtonNode)node).type != GuiParser.ButtonNode.ButtonType.RECIPE && !(this.container.base instanceof INetworkClientTileEntityEventListener) && !this.isHandHeldGUI()) {
                        throw new RuntimeException("Invalid base " + this.container.base + " for button elements");
                    }
                    Button button = null;
                    switch (((GuiParser.ButtonNode)node).type) {
                        case VANILLA: {
                            button = new VanillaButton(this, ((GuiParser.ButtonNode)node).x, ((GuiParser.ButtonNode)node).y, ((GuiParser.ButtonNode)node).width, ((GuiParser.ButtonNode)node).height, this.createEventSender(((GuiParser.ButtonNode)node).eventID, ((GuiParser.ButtonNode)node).eventName));
                            break;
                        }
                        case CUSTOM: {
                            break;
                        }
                        case TRANSPARENT: {
                            button = new CustomButton(this, ((GuiParser.ButtonNode)node).x, ((GuiParser.ButtonNode)node).y, ((GuiParser.ButtonNode)node).width, ((GuiParser.ButtonNode)node).height, this.createEventSender(((GuiParser.ButtonNode)node).eventID, ((GuiParser.ButtonNode)node).eventName));
                            break;
                        }
                        case RECIPE: {
                            if (!RecipeButton.canUse() || ((GuiParser.ButtonNode)node).eventName == null) break;
                            button = new RecipeButton(this, ((GuiParser.ButtonNode)node).x, ((GuiParser.ButtonNode)node).y, ((GuiParser.ButtonNode)node).width, ((GuiParser.ButtonNode)node).height, ((GuiParser.ButtonNode)node).eventName.split(",[ ]*"));
                            ((GuiParser.ButtonNode)node).text = TextProvider.of("");
                        }
                    }
                    if (button == null) break;
                    Object text = ((GuiParser.ButtonNode)node).text.get(this.container.base, Collections.singletonMap("name", TextProvider.ofTranslated(this.container.base.func_70005_c_())));
                    if (((GuiParser.ButtonNode)node).icon == null) {
                        button = button.withText((String)text);
                    } else {
                        button.withIcon((Supplier<ItemStack>)Suppliers.ofInstance((Object)((GuiParser.ButtonNode)node).icon));
                        button.withTooltip((String)text);
                    }
                    parentNode.addElement(this, button);
                    break;
                }
                case energygauge: {
                    if (!(this.container.base instanceof TileEntityBlock) || !((TileEntityBlock)this.container.base).hasComponent(Energy.class)) {
                        throw new RuntimeException("invalid base " + this.container.base + " for energygauge elements");
                    }
                    GuiParser.Node node = (GuiParser.EnergyGaugeNode)rawNode;
                    parentNode.addElement(this, new EnergyGauge(this, ((GuiParser.EnergyGaugeNode)node).x, ((GuiParser.EnergyGaugeNode)node).y, (TileEntityBlock)this.container.base, ((GuiParser.EnergyGaugeNode)node).style));
                    break;
                }
                case gauge: {
                    if (!(this.container.base instanceof IGuiValueProvider)) {
                        throw new RuntimeException("invalid base " + this.container.base + " for gauge elements");
                    }
                    GuiParser.Node node = (GuiParser.GaugeNode)rawNode;
                    final boolean isActiveLinked = ((GuiParser.GaugeNode)node).activeLinked;
                    if (isActiveLinked && !(this.container.base instanceof IGuiValueProvider.IActiveGuiValueProvider)) {
                        throw new RuntimeException("Invalid base " + this.container.base + " for active linked gauge elements");
                    }
                    parentNode.addElement(this, new LinkedGauge(this, ((GuiParser.GaugeNode)node).x, ((GuiParser.GaugeNode)node).y, (IGuiValueProvider)this.container.base, ((GuiParser.GaugeNode)node).name, ((GuiParser.GaugeNode)node).style){

                        @Override
                        protected boolean isActive(double ratio) {
                            return isActiveLinked ? ((IGuiValueProvider.IActiveGuiValueProvider)((DynamicGui)DynamicGui.this).container.base).isGuiValueActive(this.name) : super.isActive(ratio);
                        }
                    });
                    break;
                }
                case image: {
                    GuiParser.Node node = (GuiParser.ImageNode)rawNode;
                    parentNode.addElement(this, Image.create(this, ((GuiParser.ImageNode)node).x, ((GuiParser.ImageNode)node).y, ((GuiParser.ImageNode)node).width, ((GuiParser.ImageNode)node).height, ((GuiParser.ImageNode)node).src, ((GuiParser.ImageNode)node).baseWidth, ((GuiParser.ImageNode)node).baseHeight, ((GuiParser.ImageNode)node).u1, ((GuiParser.ImageNode)node).v1, ((GuiParser.ImageNode)node).u2, ((GuiParser.ImageNode)node).v2));
                    break;
                }
                case playerinventory: {
                    GuiParser.Node node = (GuiParser.PlayerInventoryNode)rawNode;
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.PlayerInventoryNode)node).x, ((GuiParser.PlayerInventoryNode)node).y, 9, 3, ((GuiParser.PlayerInventoryNode)node).style, 0, ((GuiParser.PlayerInventoryNode)node).spacing));
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.PlayerInventoryNode)node).x, ((GuiParser.PlayerInventoryNode)node).y + ((GuiParser.PlayerInventoryNode)node).hotbarOffset, 9, 1, ((GuiParser.PlayerInventoryNode)node).style, 0, ((GuiParser.PlayerInventoryNode)node).spacing));
                    if (!((GuiParser.PlayerInventoryNode)node).showTitle) break;
                    parentNode.addElement(this, Text.create(this, ((GuiParser.PlayerInventoryNode)node).x + 1, ((GuiParser.PlayerInventoryNode)node).y - 10, TextProvider.ofTranslated(player.field_71071_by.func_70005_c_()), 0x404040, false));
                    break;
                }
                case slot: 
                case slothologram: {
                    GuiParser.Node node = (GuiParser.SlotNode)rawNode;
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.SlotNode)node).x, ((GuiParser.SlotNode)node).y, 1, 1, ((GuiParser.SlotNode)node).style));
                    break;
                }
                case slotgrid: {
                    if (!(this.container.base instanceof IInventorySlotHolder)) {
                        throw new RuntimeException("Invalid base " + this.container.base + " for slot elements");
                    }
                    GuiParser.Node node = (GuiParser.SlotGridNode)rawNode;
                    InvSlot slot = ((IInventorySlotHolder)this.container.base).getInventorySlot(((GuiParser.SlotGridNode)node).name);
                    if (slot == null) {
                        throw new RuntimeException("Invalid InvSlot name " + ((GuiParser.SlotGridNode)node).name + " for base " + this.container.base);
                    }
                    int size = slot.size();
                    if (size <= ((GuiParser.SlotGridNode)node).offset) break;
                    GuiParser.SlotGridNode.SlotGridDimension dim = ((GuiParser.SlotGridNode)node).getDimension(size);
                    parentNode.addElement(this, new SlotGrid(this, ((GuiParser.SlotGridNode)node).x, ((GuiParser.SlotGridNode)node).y, dim.cols, dim.rows, ((GuiParser.SlotGridNode)node).style, 0, ((GuiParser.SlotGridNode)node).spacing));
                    break;
                }
                case text: {
                    int x;
                    GuiParser.Node node = (GuiParser.TextNode)rawNode;
                    switch (((GuiParser.TextNode)node).align) {
                        case Start: {
                            x = ((GuiParser.TextNode)node).x;
                            break;
                        }
                        case Center: {
                            x = ((GuiParser.TextNode)node).x + this.field_146999_f / 2;
                            break;
                        }
                        case End: {
                            x = ((GuiParser.TextNode)node).x + this.field_146999_f;
                            break;
                        }
                        default: {
                            throw new IllegalArgumentException("invalid alignment: " + (Object)((Object)((GuiParser.TextNode)node).align));
                        }
                    }
                    Object text = ((GuiParser.TextNode)node).rightAligned ? Text.createRightAligned(this, x, ((GuiParser.TextNode)node).y, ((GuiParser.TextNode)node).width, ((GuiParser.TextNode)node).height, ((GuiParser.TextNode)node).text, ((GuiParser.TextNode)node).color, ((GuiParser.TextNode)node).shadow, ((GuiParser.TextNode)node).xOffset, ((GuiParser.TextNode)node).yOffset, ((GuiParser.TextNode)node).centerX, ((GuiParser.TextNode)node).centerY) : Text.create(this, x, ((GuiParser.TextNode)node).y, ((GuiParser.TextNode)node).width, ((GuiParser.TextNode)node).height, ((GuiParser.TextNode)node).text, ((GuiParser.TextNode)node).color, ((GuiParser.TextNode)node).shadow, ((GuiParser.TextNode)node).xOffset, ((GuiParser.TextNode)node).yOffset, ((GuiParser.TextNode)node).centerX, ((GuiParser.TextNode)node).centerY);
                    parentNode.addElement(this, (GuiElement<?>)text);
                    break;
                }
                case fluidtank: {
                    TankGauge tankGauge;
                    if (!(this.container.base instanceof TileEntityBlock) || !((TileEntityBlock)this.container.base).hasComponent(Fluids.class)) {
                        throw new RuntimeException("invalid base " + this.container.base + " for tank elements");
                    }
                    GuiParser.Node node = (GuiParser.FluidTankNode)rawNode;
                    Fluids fluids = ((TileEntityBlock)this.container.base).getComponent(Fluids.class);
                    switch (((GuiParser.FluidTankNode)node).type) {
                        case NORMAL: {
                            tankGauge = TankGauge.createNormal(this, ((GuiParser.FluidTankNode)node).x, ((GuiParser.FluidTankNode)node).y, (IFluidTank)fluids.getFluidTank(((GuiParser.FluidTankNode)node).name));
                            break;
                        }
                        case PLAIN: {
                            tankGauge = TankGauge.createPlain(this, ((GuiParser.FluidTankNode)node).x, ((GuiParser.FluidTankNode)node).y, ((GuiParser.FluidTankNode)node).width, ((GuiParser.FluidTankNode)node).height, (IFluidTank)fluids.getFluidTank(((GuiParser.FluidTankNode)node).name));
                            break;
                        }
                        case BORDERLESS: {
                            tankGauge = TankGauge.createBorderless(this, ((GuiParser.FluidTankNode)node).x, ((GuiParser.FluidTankNode)node).y, (IFluidTank)fluids.getFluidTank(((GuiParser.FluidTankNode)node).name), ((GuiParser.FluidTankNode)node).mirrored);
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Unexpected type " + (Object)((Object)((GuiParser.FluidTankNode)node).type));
                        }
                    }
                    parentNode.addElement(this, tankGauge);
                    break;
                }
                case fluidslot: {
                    if (!(this.container.base instanceof TileEntityBlock) || !((TileEntityBlock)this.container.base).hasComponent(Fluids.class)) {
                        throw new RuntimeException("invalid base " + this.container.base + " for tank elements");
                    }
                    GuiParser.Node node = (GuiParser.FluidSlotNode)rawNode;
                    parentNode.addElement(this, FluidSlot.createFluidSlot(this, ((GuiParser.FluidSlotNode)node).x, ((GuiParser.FluidSlotNode)node).y, (IFluidTank)((TileEntityBlock)this.container.base).getComponent(Fluids.class).getFluidTank(((GuiParser.FluidSlotNode)node).name)));
                    break;
                }
            }
            if (!(rawNode instanceof GuiParser.ParentNode)) continue;
            this.initializeWidgets(player, (GuiParser.ParentNode)rawNode);
        }
    }

    protected IClickHandler createEventSender(int event, String eventString) {
        if (this.isHandHeldGUI()) {
            String eventName;
            if (eventString == null) {
                IC2.log.warn(LogCategory.General, "HandHand inventory given numeric event rather than string");
                eventName = Integer.toString(event);
            } else {
                eventName = eventString;
            }
            return new IClickHandler(){

                @Override
                public void onClick(MouseButton button) {
                    IC2.network.get(false).sendContainerEvent(DynamicGui.this.container, eventName);
                    ((HandHeldInventory)((DynamicGui)DynamicGui.this).container.base).onEvent(eventName);
                }
            };
        }
        assert (eventString == null);
        return this.createEventSender(event);
    }

    protected boolean isHandHeldGUI() {
        return this.container.base instanceof HandHeldInventory;
    }

    @Override
    public void addElement(GuiElement<?> element) {
        super.addElement(element);
    }
}

