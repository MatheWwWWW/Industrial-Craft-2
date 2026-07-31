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
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.item.inv.components;

import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.item.inv.inventory.CraftingUpgradeInventory;
import ic2.core.platform.registries.IC2Items;
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
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class CraftingUpgradeComponent
extends GuiWidget {
    CraftingUpgradeInventory inv;

    public CraftingUpgradeComponent(CraftingUpgradeInventory inv) {
        super(Box2i.EMPTY_BOX);
        this.inv = inv;
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
        gui.m_142416_(new ExtendedButton(x + 100, y + 15, 60, 12, (Component)this.translate("gui.ic2.crafting_upgrade.validate"), T -> this.validateRecipe()));
        String s = "DUNSWE";
        int m = s.length();
        for (int i = 0; i < m; ++i) {
            gui.addRenderableWidget(i, this.create(x + 3 + i % 2 * 13, y + 16 + i / 2 * 13, Character.toString(s.charAt(i)), Direction.m_122376_((int)i)));
        }
        gui.addRenderableWidget(6, new ItemCheckBox(x + 152, y + 60, 16, 16, T -> this.sendFlags(128), new ItemStack((ItemLike)Items.f_42447_), (this.inv.flags & 0x80) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.fluid");
        gui.addRenderableWidget(7, new ItemCheckBox(x + 135, y + 60, 16, 16, T -> this.sendFlags(32), new ItemStack((ItemLike)Items.f_42614_), (this.inv.flags & 0x20) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.tag");
        gui.addRenderableWidget(8, new ItemCheckBox(x + 118, y + 60, 16, 16, T -> this.sendFlags(256), new ItemStack((ItemLike)IC2Items.DRILL_DIAMOND), (this.inv.flags & 0x100) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.durability");
        gui.addRenderableWidget(9, new ItemCheckBox(x + 101, y + 60, 16, 16, T -> this.sendFlags(16), new ItemStack((ItemLike)Items.f_42516_), (this.inv.flags & 0x10) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.nbt");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        for (int i = 0; i < 6; ++i) {
            gui.getButton((int)i).f_93623_ = i != this.inv.direction;
        }
        gui.getCastedButton(6, ItemCheckBox.class).setChecked((this.inv.flags & 0x80) != 0);
        gui.getCastedButton(7, ItemCheckBox.class).setChecked((this.inv.flags & 0x20) != 0);
        gui.getCastedButton(8, ItemCheckBox.class).setChecked((this.inv.flags & 0x100) != 0);
        gui.getCastedButton(9, ItemCheckBox.class).setChecked((this.inv.flags & 0x10) != 0);
    }

    @OnlyIn(value=Dist.CLIENT)
    private AbstractWidget create(int x, int y, String s, Direction dir) {
        ToolTipButton result = new ToolTipButton(x, y, 12, 12, (Component)this.string(s), T -> this.sendDirection(dir)).setToolTip((Component)DirectionList.getName(dir));
        ((AbstractWidget)result).f_93623_ = this.inv.direction != dir.m_122411_();
        return result;
    }

    private void validateRecipe() {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inv.getInventoryStack(), 0, 0);
    }

    private void sendFlags(int flag) {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inv.getInventoryStack(), 1, flag);
    }

    private void sendDirection(Direction dir) {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inv.getInventoryStack(), 2, dir.m_122411_());
    }
}

