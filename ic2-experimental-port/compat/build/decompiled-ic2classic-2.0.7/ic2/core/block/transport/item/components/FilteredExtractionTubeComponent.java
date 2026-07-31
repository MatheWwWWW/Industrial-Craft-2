/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.transport.item.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.core.block.machines.components.mv.planner.NamingTabComponent;
import ic2.core.block.transport.item.tubes.FilteredExtractionTubeTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class FilteredExtractionTubeComponent
extends GuiWidget {
    static final ItemStack[] DATA = FilteredExtractionTubeComponent.createItemStack();
    static final Component[] NAMES = FilteredExtractionTubeComponent.createNames();
    FilteredExtractionTubeTileEntity tile;
    int last;
    IHasInventory inv;

    public FilteredExtractionTubeComponent(FilteredExtractionTubeTileEntity tile, IHasInventory inv) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.inv = inv;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        for (int i = 0; i < 16; ++i) {
            this.inv.setStackInSlot(i, i < this.tile.filters.size() ? ((FilteredExtractionTubeTileEntity.FilterEntry)this.tile.filters.get(i)).getStack() : ItemStack.f_41583_);
        }
        gui.addRenderableWidget(20, new ItemCheckBox(x + 106, y + 35, 16, 16, T -> this.sendFlags(128), new ItemStack((ItemLike)Items.f_42447_), (this.tile.flags & 0x80) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.fluid");
        gui.addRenderableWidget(22, new ItemCheckBox(x + 88, y + 35, 16, 16, T -> this.sendFlags(16), new ItemStack((ItemLike)Items.f_42516_), (this.tile.flags & 0x10) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.nbt");
        gui.addRenderableWidget(23, new ItemCheckBox(x + 124, y + 35, 16, 16, T -> this.sendFlags(256), new ItemStack((ItemLike)IC2Items.DRILL_DIAMOND), (this.tile.flags & 0x100) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.durability");
        gui.addRenderableWidget((int)24, new IconButton((int)(x + 142), (int)(y + 35), (int)16, (int)16, (ItemStack)FilteredExtractionTubeComponent.DATA[Math.max((int)0, (int)this.tile.durability)], (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((FilteredExtractionTubeComponent)this))).setDurability((boolean)(this.tile.durability != 0 ? true : false)).setToolTip((Component)FilteredExtractionTubeComponent.NAMES[Math.max((int)0, (int)this.tile.durability)]).f_93624_ = (this.tile.flags & 0x100) != 0;
        gui.addRenderableWidget((int)26, new ExtendedButton((int)(x + 88), (int)(y + 18), (int)52, (int)14, (Component)this.translate((String)"gui.ic2.filter_extraction_tube.save"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$4(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((FilteredExtractionTubeComponent)this))).f_93623_ = this.tile.selectedIndex != -1;
        gui.addRenderableWidget(27, new ItemCheckBox(x + 169, y + 62, 16, 16, T -> this.toggleWhitelist(), new ItemStack((ItemLike)Items.f_42517_), this.tile.whitelist)).setToolTip("tooltip.item.ic2.transport_update.compare.whitelist");
        EditBox text = gui.addRenderableWidget(28, new ImprovedTextWidget(gui.getFont(), x + 89, y + 66, 32, 14, (Component)this.string()));
        text.m_94182_(false);
        text.m_94199_(4);
        text.m_94153_(NamingTabComponent.NUMBERS_ONLY);
        text.m_94190_(true);
        text.m_94164_(Integer.toString(this.tile.keepItems));
        text.m_94151_(this::setKeepItems);
        this.last = this.tile.keepItems;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getCastedButton(20, ItemCheckBox.class).setChecked((this.tile.flags & 0x80) != 0);
        gui.getCastedButton(22, ItemCheckBox.class).setChecked((this.tile.flags & 0x10) != 0);
        gui.getCastedButton(23, ItemCheckBox.class).setChecked((this.tile.flags & 0x100) != 0);
        gui.getCastedButton((int)24, IconButton.class).setDisplay((ItemStack)FilteredExtractionTubeComponent.DATA[Math.max((int)0, (int)this.tile.durability)]).setDurability((boolean)(this.tile.durability != 0 ? true : false)).setToolTip((Component)FilteredExtractionTubeComponent.NAMES[Math.max((int)0, (int)this.tile.durability)]).f_93624_ = (this.tile.flags & 0x100) != 0;
        for (int i = 0; i < 16; ++i) {
            this.inv.setStackInSlot(i, i < this.tile.filters.size() ? ((FilteredExtractionTubeTileEntity.FilterEntry)this.tile.filters.get(i)).getStack() : ItemStack.f_41583_);
        }
        gui.getCastedButton(27, ItemCheckBox.class).setChecked(this.tile.whitelist);
        gui.getButton((int)26).f_93623_ = !this.tile.filterInventory.getStackInSlot(0).m_41619_() && (this.tile.selectedIndex != -1 || this.tile.filters.size() < 16);
        ImprovedTextWidget widget = gui.getCastedButton(28, ImprovedTextWidget.class);
        widget.m_94120_();
        if (this.last != this.tile.keepItems) {
            this.last = this.tile.keepItems;
            widget.setExternalValue(Integer.toString(this.tile.keepItems));
        }
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseX < 7 || mouseY < 17) {
            return false;
        }
        int xIndex = (mouseX - 7) / 18;
        int yIndex = (mouseY - 17) / 18;
        if (xIndex <= 3 && yIndex <= 3) {
            this.selectIndex(yIndex * 4 + xIndex);
            return true;
        }
        return false;
    }

    private void sendDurability() {
        this.tile.sendToServer(2, (this.tile.durability + 1) % 11);
    }

    private void sendFlags(int flags) {
        this.tile.sendToServer(1, flags);
    }

    private void setKeepItems(String value) {
        try {
            this.tile.sendToServer(3, Integer.parseInt(value));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void toggleWhitelist() {
        this.tile.sendToServer(5, this.tile.whitelist ? 1 : 0);
    }

    private void selectIndex(int index) {
        if (index != -1 && Screen.m_96638_()) {
            this.tile.sendToServer(7, index);
            return;
        }
        this.tile.sendToServer(4, index);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.filter_extraction_tube.keep"), 88, 55, 0x404040);
        this.gui.drawRightString(matrix, this.gui.getPlayer().m_150109_().m_7755_(), 177, 82, 0x404040);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (mouseX < 7 || mouseY < 17) {
            return;
        }
        int xIndex = (mouseX - 7) / 18;
        int yIndex = (mouseY - 17) / 18;
        if (xIndex <= 3 && yIndex <= 3) {
            int index = yIndex * 4 + xIndex;
            if (this.tile.filters.size() > index) {
                FilteredExtractionTubeTileEntity.FilterEntry entry = (FilteredExtractionTubeTileEntity.FilterEntry)this.tile.filters.get(index);
                tooltips.accept(entry.getStack().m_41786_());
                tooltips.accept((Component)this.buildKeyDescription(KeyHelper.SNEAK_KEY, "gui.ic2.color_tube.to_delete", new Object[0]).m_130940_(ChatFormatting.GRAY));
                if (entry.getKeepItems() > 0) {
                    tooltips.accept((Component)this.translate("gui.ic2.filter_extraction_tube.items_to_keep", entry.getKeepItems()));
                }
            }
        }
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
        this.tile.sendToServer(6, 0);
    }

    private /* synthetic */ void lambda$init$3(Button T) {
        this.sendDurability();
    }
}

