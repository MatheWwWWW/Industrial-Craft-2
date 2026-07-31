/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.ScrollSlider;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.item.inv.inventory.FluidImportExportInventory;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FluidImportExportComponent
extends GuiWidget {
    FluidImportExportInventory inventory;

    public FluidImportExportComponent(FluidImportExportInventory inventory) {
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
        gui.addRenderableWidget(10, new ItemCheckBox(x + 87, y + 35, 16, 16, T -> this.sendWhiteList(), new ItemStack((ItemLike)Items.f_42517_), this.inventory.whitelist)).setToolTip("tooltip.item.ic2.transport_update.compare.whitelist");
        gui.addRenderableWidget(12, new ScrollSlider(x + 87, y + 52, 84, 16, (Component)this.string(), (Component)this.translate("mB/t"), 1.0, 1000.0, this.inventory.transferRate, T -> this.sendTransferSize(T.getValueInt())).setScrollEffect(1.0));
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

    private void sendWhiteList() {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 2, this.inventory.whitelist ? 0 : 1);
    }

    private void sendTransferSize(int value) {
        this.inventory.transferRate = value;
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 1, value);
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
        gui.getCastedButton(10, ItemCheckBox.class).setChecked(this.inventory.whitelist);
        gui.getCastedButton(12, ScrollSlider.class).m_93611_(this.inventory.transferRate);
    }
}

