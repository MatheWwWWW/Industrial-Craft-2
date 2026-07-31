/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.ScrollSlider;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.item.inv.inventory.ImportExportInventory;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ImportExportComponent
extends GuiWidget {
    static final ItemStack[] DATA = ImportExportComponent.createItemStack();
    static final Component[] NAMES = ImportExportComponent.createNames();
    ImportExportInventory inventory;

    public ImportExportComponent(ImportExportInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, this.create(x + 7, y + 35, "S", Direction.SOUTH));
        gui.addRenderableWidget(1, this.create(x + 21, y + 35, "U", Direction.UP));
        gui.addRenderableWidget(2, this.create(x + 7, y + 49, "W", Direction.WEST));
        gui.addRenderableWidget(3, this.create(x + 21, y + 49, "N", Direction.NORTH));
        gui.addRenderableWidget(4, this.create(x + 35, y + 49, "E", Direction.EAST));
        gui.addRenderableWidget(5, this.create(x + 21, y + 63, "D", Direction.DOWN));
        gui.addRenderableWidget(6, new ItemCheckBox(x + 155, y + 35, 16, 16, T -> this.sendFlags(128), new ItemStack((ItemLike)Items.f_42447_), (this.inventory.flags & 0x80) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.fluid");
        gui.addRenderableWidget(8, new ItemCheckBox(x + 138, y + 35, 16, 16, T -> this.sendFlags(256), new ItemStack((ItemLike)IC2Items.DRILL_DIAMOND), (this.inventory.flags & 0x100) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.durability");
        gui.addRenderableWidget(9, new ItemCheckBox(x + 121, y + 35, 16, 16, T -> this.sendFlags(16), new ItemStack((ItemLike)Items.f_42516_), (this.inventory.flags & 0x10) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.nbt");
        gui.addRenderableWidget(10, new ItemCheckBox(x + 104, y + 35, 16, 16, T -> this.sendWhiteList(), new ItemStack((ItemLike)Items.f_42517_), this.inventory.whiteList)).setToolTip("tooltip.item.ic2.transport_update.compare.whitelist");
        gui.addRenderableWidget((int)11, new IconButton((int)(x + 87), (int)(y + 35), (int)16, (int)16, (ItemStack)ImportExportComponent.DATA[Math.max((int)0, (int)this.inventory.durability)], (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$4(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ImportExportComponent)this))).setDurability((boolean)(this.inventory.durability != 0 ? true : false)).setToolTip((Component)ImportExportComponent.NAMES[Math.max((int)0, (int)this.inventory.durability)]).f_93624_ = (this.inventory.flags & 0x100) != 0;
        gui.addRenderableWidget(12, new ScrollSlider(x + 87, y + 52, 84, 16, (Component)this.string(), (Component)this.translate("tooltip.item.ic2.transport_update.items"), 1.0, 64.0, this.inventory.transfer, T -> this.sendTransferSize(T.getValueInt())).setScrollEffect(1.0));
    }

    @OnlyIn(value=Dist.CLIENT)
    private AbstractWidget create(int x, int y, String s, Direction dir) {
        ToolTipButton result = new ToolTipButton(x, y, 14, 14, (Component)this.string(s), T -> this.sendDirection(dir)).setToolTip((Component)DirectionList.getName(dir));
        ((AbstractWidget)result).f_93623_ = this.inventory.direction != dir.m_122411_();
        return result;
    }

    private void sendDirection(Direction dir) {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 0, dir.m_122411_());
    }

    private void sendDurability() {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 1, (this.inventory.durability + 1) % 11);
    }

    private void sendFlags(int flags) {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 2, flags);
    }

    private void sendWhiteList() {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 3, this.inventory.whiteList ? 0 : 1);
    }

    private void sendTransferSize(int value) {
        this.inventory.transfer = value;
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 4, value);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)0).f_93623_ = this.inventory.direction != Direction.SOUTH.m_122411_();
        gui.getButton((int)1).f_93623_ = this.inventory.direction != Direction.UP.m_122411_();
        gui.getButton((int)2).f_93623_ = this.inventory.direction != Direction.WEST.m_122411_();
        gui.getButton((int)3).f_93623_ = this.inventory.direction != Direction.NORTH.m_122411_();
        gui.getButton((int)4).f_93623_ = this.inventory.direction != Direction.EAST.m_122411_();
        gui.getButton((int)5).f_93623_ = this.inventory.direction != Direction.DOWN.m_122411_();
        gui.getCastedButton(6, ItemCheckBox.class).setChecked((this.inventory.flags & 0x80) != 0);
        gui.getCastedButton(8, ItemCheckBox.class).setChecked((this.inventory.flags & 0x100) != 0);
        gui.getCastedButton(9, ItemCheckBox.class).setChecked((this.inventory.flags & 0x10) != 0);
        gui.getCastedButton(10, ItemCheckBox.class).setChecked(this.inventory.whiteList);
        gui.getCastedButton((int)11, IconButton.class).setDisplay((ItemStack)ImportExportComponent.DATA[Math.max((int)0, (int)this.inventory.durability)]).setDurability((boolean)(this.inventory.durability != 0 ? true : false)).setToolTip((Component)ImportExportComponent.NAMES[Math.max((int)0, (int)this.inventory.durability)]).f_93624_ = (this.inventory.flags & 0x100) != 0;
        gui.getCastedButton(12, ScrollSlider.class).m_93611_(this.inventory.transfer);
    }

    static Component[] createNames() {
        Component[] data = new Component[]{Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.exact"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.1a"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.1b"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.25a"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.25b"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.50a"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.50b"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.70a"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.70b"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.90a"), Component.m_237115_((String)"tooltip.item.ic2.transport_update.durability.90b")};
        return data;
    }

    static ItemStack[] createItemStack() {
        double[] max = new double[]{1.0, 0.9, 0.9, 0.75, 0.75, 0.5, 0.5, 0.25, 0.25, 0.1, 0.0};
        ItemStack[] data = new ItemStack[max.length];
        for (int i = 0; i < max.length; ++i) {
            ItemStack stack = new ItemStack((ItemLike)IC2Items.DRILL_DIAMOND);
            int maxEU = ((IElectricItem)stack.m_41720_()).getCapacity(stack);
            ElectricItem.MANAGER.charge(stack, (int)((double)maxEU * max[i]), Integer.MAX_VALUE, true, false);
            data[i] = stack;
        }
        return data;
    }

    private /* synthetic */ void lambda$init$4(Button T) {
        this.sendDurability();
    }
}

