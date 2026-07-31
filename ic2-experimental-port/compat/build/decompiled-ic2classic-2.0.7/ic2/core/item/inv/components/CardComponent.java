/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.inv.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ColorButton;
import ic2.core.inventory.gui.components.base.IconCheckBox;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.item.inv.inventory.CardInventory;
import ic2.core.item.tool.infos.BaseTileInfoProvider;
import ic2.core.networking.buffers.data.StringDataBuffer;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CardComponent
extends GuiWidget {
    CardInventory inventory;

    public CardComponent(CardInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        ImprovedTextWidget name = new ImprovedTextWidget(x + 9, y + 27, 82, 20);
        name.m_94190_(true);
        name.m_94199_(128);
        name.m_94182_(false);
        name.m_94144_(this.inventory.name);
        name.m_94151_(this::updateName);
        gui.addRenderableWidget(-1, name);
        for (BaseTileInfoProvider.SettingEntry entry : this.inventory.settings) {
            switch (entry.type) {
                case COLOR: {
                    gui.m_142416_(new ColorButton(x + entry.x, y + entry.y, 16, 16, this.inventory.getColor(entry.index), T -> this.togglePaint(entry.index, (ColorButton)T))).setToolTip(entry.tooltip);
                    break;
                }
                case SIMPLE: {
                    gui.addRenderableWidget(entry.index, new ItemCheckBox(x + entry.x, y + entry.y, 16, 16, T -> this.toggleFlag(entry.index), (ItemStack)entry.display)).setToolTip(entry.tooltip).setChecked((this.inventory.flags & 1 << entry.index) != 0);
                    break;
                }
                case TEXT: {
                    gui.addRenderableWidget(entry.index, new IconCheckBox(x + entry.x, y + entry.y, 16, 16, (Component)entry.display, (this.inventory.flags & 1 << entry.index) == 0)).setItemLike(true).setListener(T -> this.toggleFlag(entry.index)).setToolTip(entry.tooltip);
                }
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        for (BaseTileInfoProvider.SettingEntry entry : this.inventory.settings) {
            switch (entry.type) {
                case COLOR: {
                    break;
                }
                case SIMPLE: {
                    gui.getCastedButton(entry.index, ItemCheckBox.class).setChecked((this.inventory.flags & 1 << entry.index) != 0);
                    break;
                }
                case TEXT: {
                    gui.getCastedButton(entry.index, IconCheckBox.class).setChecked((this.inventory.flags & 1 << entry.index) != 0);
                }
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        return this.gui.getCastedButton(-1, ImprovedTextWidget.class).m_93696_() && keyCode == 69;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.cards.name"), 8, 17, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.cards.show_text"), 8, 42, 0x404040);
    }

    protected void updateName(String name) {
        this.inventory.name = name;
        IC2.NETWORKING.get(false).sendClientItemBuffer(this.inventory.getInventoryStack(), "customName", new StringDataBuffer(0, name));
    }

    protected void toggleFlag(int flag) {
        this.inventory.flags ^= 1 << flag;
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 1, flag);
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void togglePaint(int flag, ColorButton button) {
        this.handleColorClick(button.getColor(), (AbstractWidget)button, false, T -> {
            button.setColor((DyeColor)T);
            this.inventory.selectedColors[flag] = T.m_41060_();
            IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), 3 + flag, this.inventory.selectedColors[flag]);
        });
    }
}

