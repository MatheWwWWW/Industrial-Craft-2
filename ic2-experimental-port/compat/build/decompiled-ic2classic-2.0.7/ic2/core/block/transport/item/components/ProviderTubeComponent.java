/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
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
import ic2.core.block.machines.components.mv.planner.NamingTabComponent;
import ic2.core.block.transport.item.tubes.ProviderTubeTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ProviderTubeComponent
extends GuiWidget {
    ProviderTubeTileEntity tile;
    IHasInventory inv;
    int lastKeep;
    int lastGlobal;

    public ProviderTubeComponent(ProviderTubeTileEntity tile, IHasInventory inv) {
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
    public void tick(IC2Screen gui) {
        gui.getCastedButton(0, ItemCheckBox.class).setChecked(this.tile.compareNBT);
        gui.getCastedButton(1, ItemCheckBox.class).setChecked(this.tile.keepMode);
        gui.getCastedButton(2, ItemCheckBox.class).setChecked(this.tile.whiteList);
        ImprovedTextWidget widget = gui.getCastedButton(3, ImprovedTextWidget.class);
        widget.m_94120_();
        if (this.tile.globalKeepItems != this.lastGlobal) {
            this.lastGlobal = this.tile.globalKeepItems;
            widget.setExternalValue(Integer.toString(this.lastGlobal));
        }
        widget = gui.getCastedButton(4, ImprovedTextWidget.class);
        widget.m_94120_();
        if (this.tile.keepItems != this.lastKeep) {
            this.lastKeep = this.tile.keepItems;
            widget.setExternalValue(Integer.toString(this.lastKeep));
        }
        int size = this.tile.filters.size();
        for (int i = 0; i < 18; ++i) {
            this.inv.setStackInSlot(i, size > i ? ((ProviderTubeTileEntity.ProvideEntry)this.tile.filters.get(i)).getStack() : ItemStack.f_41583_);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.m_142416_(new ExtendedButton(x + 26, y + 79, 34, 14, (Component)this.translate("gui.ic2.filter_extraction_tube.save"), T -> this.tile.sendToServer(0, 0)));
        gui.addRenderableWidget(0, new ItemCheckBox(x + 134, y + 78, 16, 16, T -> this.tile.sendToServer(1, 0), new ItemStack((ItemLike)Items.f_42516_), this.tile.compareNBT));
        gui.addRenderableWidget(1, new ItemCheckBox(x + 116, y + 78, 16, 16, T -> this.tile.sendToServer(8, 0), new ItemStack((ItemLike)Items.f_42155_), this.tile.keepMode).setToolTip("gui.ic2.tube.provider.keep_mode"));
        gui.addRenderableWidget(2, new ItemCheckBox(x + 152, y + 78, 16, 16, T -> this.tile.sendToServer(3, 0), new ItemStack((ItemLike)Items.f_42517_), this.tile.whiteList));
        ImprovedTextWidget widget = gui.addRenderableWidget(3, new ImprovedTextWidget(x + 117, y + 64, 32, 14));
        widget.m_94199_(4);
        widget.m_94153_(NamingTabComponent.NUMBERS_ONLY);
        widget.m_94144_(Integer.toString(this.tile.globalKeepItems));
        widget.m_94182_(false);
        widget.m_94190_(true);
        widget.m_94151_(this::sendGlobal);
        widget = gui.addRenderableWidget(4, new ImprovedTextWidget(x + 27, y + 64, 32, 14));
        widget.m_94199_(4);
        widget.m_94153_(NamingTabComponent.NUMBERS_ONLY);
        widget.m_94144_(Integer.toString(this.tile.keepItems));
        widget.m_94182_(false);
        widget.m_94190_(true);
        widget.m_94151_(this::sendPerItem);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void sendIndex(int index) {
        if (index != -1 && Screen.m_96638_()) {
            this.tile.sendToServer(5, index);
            return;
        }
        this.tile.sendToServer(4, index);
    }

    private void sendGlobal(String value) {
        try {
            this.tile.sendToServer(6, Integer.parseInt(value));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void sendPerItem(String value) {
        try {
            this.tile.sendToServer(7, Integer.parseInt(value));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.tube.provider.per_item"), 23, 54, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.tube.provider.global"), 118, 54, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.tube.provider.keep"), 75, 64, 0x404040);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (mouseX < 7 || mouseY < 17) {
            return;
        }
        int xIndex = (mouseX - 7) / 18;
        int yIndex = (mouseY - 17) / 18;
        if (xIndex <= 8 && yIndex < 2) {
            int index = yIndex * 9 + xIndex;
            if (this.tile.filters.size() > index) {
                ProviderTubeTileEntity.ProvideEntry entry = (ProviderTubeTileEntity.ProvideEntry)this.tile.filters.get(index);
                tooltips.accept(entry.getStack().m_41786_());
                tooltips.accept((Component)this.buildKeyDescription(KeyHelper.SNEAK_KEY, "gui.ic2.color_tube.to_delete", new Object[0]).m_130940_(ChatFormatting.GRAY));
                if (entry.getAmount() > 0) {
                    tooltips.accept((Component)this.translate("gui.ic2.filter_extraction_tube.items_to_keep", entry.getAmount()));
                }
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseX < 7 || mouseY < 17) {
            return false;
        }
        int xIndex = (mouseX - 7) / 18;
        int yIndex = (mouseY - 17) / 18;
        if (xIndex <= 8 && yIndex < 2) {
            this.sendIndex(yIndex * 9 + xIndex);
            return true;
        }
        return false;
    }
}

