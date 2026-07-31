/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.personal.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.components.mv.planner.NamingTabComponent;
import ic2.core.block.personal.components.BaseTradeOMatComponent;
import ic2.core.block.personal.tile.TradeOMatTileEntity;
import ic2.core.block.personal.trade.ItemTrade;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconTooltip;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TradeOMatOpenComponent
extends BaseTradeOMatComponent {
    TradeOMatTileEntity tile;
    boolean isUpdating = false;

    public TradeOMatOpenComponent(TradeOMatTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        ItemTrade trade = (ItemTrade)this.tile.getTrade();
        gui.addRenderableWidget(0, new IconTooltip(x + 8, y + 37, 14, 14, new ItemStack((ItemLike)IC2Items.COIN)).setToolTip("gui.ic2.trade.money"));
        gui.addRenderableWidget(1, new IconTooltip(x + 8, y + 51, 14, 14, new ItemStack((ItemLike)Items.f_42524_)).setToolTip("gui.ic2.trade.delay"));
        gui.addRenderableWidget(2, new IconTooltip(x + 8, y + 65, 14, 14, new ItemStack((ItemLike)Items.f_42127_)).setToolTip("gui.ic2.trade.cap"));
        gui.addRenderableWidget(3, new ItemCheckBox(x + 81, y + 65, 14, 14, T -> this.sendDelayReset(), new ItemStack((ItemLike)Items.f_42084_), trade.tradeReset).setToolTip("gui.ic2.trade.cap_reset"));
        gui.addRenderableWidget(4, new ItemCheckBox(x + 153, y + 74, 14, 14, T -> this.sendInfiniteStock(), new ItemStack((ItemLike)IC2Items.UUMATTER), trade.infinite).setToolTip("gui.ic2.trade.creative"));
        gui.addRenderableWidget(5, new ItemCheckBox(x + 138, y + 74, 14, 14, T -> this.tile.sendToServer(8, 0), new ItemStack((ItemLike)Items.f_42616_)).setToolTip("Per Player Trades"));
        gui.addRenderableWidget(10, new ItemCheckBox(x + 134, y + 36, 14, 14, T -> this.sendInputFlags(128), new ItemStack((ItemLike)Items.f_42447_), (this.tile.inputFilter & 0x80) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.fluid");
        gui.addRenderableWidget(11, new ItemCheckBox(x + 118, y + 36, 14, 14, T -> this.sendInputFlags(16), new ItemStack((ItemLike)Items.f_42516_), (this.tile.inputFilter & 0x10) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.nbt");
        gui.addRenderableWidget(15, new ItemCheckBox(x + 134, y + 52, 14, 14, T -> this.sendOutputFlags(128), new ItemStack((ItemLike)Items.f_42447_), (this.tile.inputFilter & 0x80) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.fluid");
        gui.addRenderableWidget(16, new ItemCheckBox(x + 118, y + 52, 14, 14, T -> this.sendOutputFlags(16), new ItemStack((ItemLike)Items.f_42516_), (this.tile.inputFilter & 0x10) != 0)).setToolTip("tooltip.item.ic2.transport_update.compare.nbt");
        gui.addRenderableWidget(20, this.create(40, trade.moneyNeeded, T -> this.sendValue((String)T, 2)));
        gui.addRenderableWidget(21, this.create(54, trade.tradeDelay, T -> this.sendValue((String)T, 3)));
        gui.addRenderableWidget(22, this.create(68, trade.tradeCap, T -> this.sendValue((String)T, 4)));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onChanged() {
        ItemTrade trade = (ItemTrade)this.tile.getTrade();
        this.isUpdating = true;
        this.update(20, Integer.toString(trade.moneyNeeded));
        this.update(21, Integer.toString(trade.tradeDelay));
        this.update(22, Integer.toString(trade.tradeCap));
        boolean app = this.gui.getCastedButton(3, ItemCheckBox.class).isChecked();
        if (app && (trade.tradeCap == 0 || trade.tradeDelay == 0)) {
            this.sendDelayReset();
        }
        this.isUpdating = false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void update(int index, String newValue) {
        EditBox text = this.gui.getCastedButton(index, EditBox.class);
        if (text != null && !text.m_94155_().equals(newValue)) {
            text.m_94144_(newValue);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getCastedButton(3, ItemCheckBox.class).setChecked(((ItemTrade)this.tile.getTrade()).tradeReset);
        gui.getCastedButton(4, ItemCheckBox.class).setChecked(((ItemTrade)this.tile.getTrade()).infinite);
        gui.getCastedButton(5, ItemCheckBox.class).setChecked(((ItemTrade)this.tile.getTrade()).perPlayer);
        gui.getCastedButton(10, ItemCheckBox.class).setChecked((this.tile.inputFilter & 0x80) != 0);
        gui.getCastedButton(11, ItemCheckBox.class).setChecked((this.tile.inputFilter & 0x10) != 0);
        gui.getCastedButton(15, ItemCheckBox.class).setChecked((this.tile.outputFilter & 0x80) != 0);
        gui.getCastedButton(16, ItemCheckBox.class).setChecked((this.tile.outputFilter & 0x10) != 0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.trade.in"), 115, 40, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.translate("gui.ic2.trade.out"), 115, 55, 0x404040);
    }

    @OnlyIn(value=Dist.CLIENT)
    public EditBox create(int yLevel, int originalValue, Consumer<String> listener) {
        ImprovedTextWidget text = new ImprovedTextWidget(this.gui.getGuiLeft() + 27, this.gui.getGuiTop() + yLevel, 60, 12);
        text.m_94144_(Integer.toString(originalValue));
        text.m_94199_(8);
        text.m_94153_(NamingTabComponent.NUMBERS_ONLY);
        text.m_94151_(T -> {
            if (!this.isUpdating) {
                listener.accept((String)T);
            }
        });
        text.m_94190_(true);
        text.m_94182_(false);
        return text;
    }

    @OnlyIn(value=Dist.CLIENT)
    private void sendReset(Button button, int index) {
        if (button instanceof ItemCheckBox) {
            ItemCheckBox box;
            box.setChecked(!(box = (ItemCheckBox)button).isChecked());
            if (!box.isChecked()) {
                this.tile.sendToServer(index, 0);
            }
        }
    }

    private void sendInfiniteStock() {
        this.tile.sendToServer(6, 0);
    }

    private void sendDelayReset() {
        this.tile.sendToServer(5, 0);
    }

    private void sendValue(String s, int index) {
        this.tile.sendToServer(index, s.isEmpty() ? 0 : Integer.parseInt(s));
    }

    private void sendInputFlags(int flag) {
        this.tile.sendToServer(0, flag);
    }

    private void sendOutputFlags(int flag) {
        this.tile.sendToServer(1, flag);
    }
}

