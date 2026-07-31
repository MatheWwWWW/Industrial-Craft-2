/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.DataSlot
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.hv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.tiles.hv.ElectricEnchanterTileEntity;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ElectricEnchanterComponent
extends GuiWidget {
    static final int COLOR = ColorUtils.darker(ColorUtils.GREEN);
    static final ItemStack[] MODE_ITEMS = new ItemStack[]{new ItemStack((ItemLike)Items.f_42100_), new ItemStack((ItemLike)Items.f_42146_), new ItemStack((ItemLike)Items.f_42773_)};
    static final Box2i HOVER_BOX = new Box2i(112, 76, 45, 8);
    ElectricEnchanterTileEntity tile;
    DataSlot seed;
    int lastMode = 0;

    public ElectricEnchanterComponent(ElectricEnchanterTileEntity tile, DataSlot seed) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.seed = seed;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ExtendedButton(x + 100, y + 75, 10, 10, (Component)this.string("-"), T -> this.exp(false)));
        gui.addRenderableWidget(1, new ExtendedButton(x + 159, y + 75, 10, 10, (Component)this.string("+"), T -> this.exp(true)));
        gui.addRenderableWidget(2, new ExtendedButton(x + 100, y + 61, 69, 12, this.getMode(), T -> this.start()));
        gui.addRenderableWidget(3, new IconButton(x + 150, y + 5, 20, 20, MODE_ITEMS[this.tile.mode], T -> this.modeSwitch()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        AbstractWidget widget = gui.getButton(2);
        widget.f_93623_ = this.tile.valid && !this.tile.running && this.tile.storedExperience >= this.tile.neededExperience;
        widget.m_93666_(this.getMode());
        if (this.tile.mode != this.lastMode) {
            this.lastMode = this.tile.mode;
            gui.getCastedButton(3, IconButton.class).setDisplay(MODE_ITEMS[this.lastMode]);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        ItemStack stack;
        Slot slot;
        float stored = (float)this.tile.storedExperience / 1000.0f * 45.0f;
        float needed = (float)this.tile.neededExperience / 1000.0f * 45.0f;
        if (stored > 0.0f && needed > 0.0f) {
            if (needed > stored) {
                this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 112, this.gui.getGuiTop() + 76, needed, 8.0f, ColorUtils.RED);
                this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 112, this.gui.getGuiTop() + 76, stored, 8.0f, COLOR);
            } else {
                this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 112, this.gui.getGuiTop() + 76, stored, 8.0f, COLOR);
                this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 112, this.gui.getGuiTop() + 76, needed, 8.0f, ColorUtils.RED);
            }
        } else if (stored > 0.0f) {
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 112, this.gui.getGuiTop() + 76, stored, 8.0f, COLOR);
        } else if (needed > 0.0f) {
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + 112, this.gui.getGuiTop() + 76, needed, 8.0f, ColorUtils.RED);
        }
        AbstractWidget widget = this.gui.getButton(2);
        if (widget != null && widget.f_93623_ && !(slot = ((IC2Container)this.gui.m_6262_()).m_38853_(6)).m_6657_() && !(stack = this.tile.getPotentialOutput(this.seed.m_6501_())).m_41619_()) {
            this.gui.drawItemStack(matrix, this.gui.getGuiLeft() + 124, this.gui.getGuiTop() + 37, stack);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        ItemStack stack;
        Slot slot;
        AbstractWidget widget;
        if (HOVER_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.electric_enchanter.xp.stored", Formatters.EU_FORMAT.format(this.tile.storedExperience)));
            if (this.tile.neededExperience > 0) {
                tooltips.accept((Component)this.translate("gui.ic2.electric_enchanter.xp.needed", Formatters.EU_FORMAT.format(this.tile.neededExperience)));
            }
        }
        if ((widget = this.gui.getButton(2)) != null && widget.f_93623_ && !(slot = ((IC2Container)this.gui.m_6262_()).m_38853_(6)).m_6657_() && this.gui.isSlotHovered(slot, mouseX, mouseY) && !(stack = this.tile.getPotentialOutput(this.seed.m_6501_())).m_41619_()) {
            stack.m_41651_((Player)this.gui.getPlayer(), (TooltipFlag)(this.gui.getMinecraft().f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)).forEach(tooltips);
        }
    }

    private void modeSwitch() {
        this.tile.sendToServer(0, 0);
    }

    private void exp(boolean consume) {
        this.tile.sendToServer(1, (consume ? 1 : -1) * this.getType());
    }

    private void start() {
        this.tile.sendToServer(2, this.seed.m_6501_());
    }

    @OnlyIn(value=Dist.CLIENT)
    public int getType() {
        if (Screen.m_96637_()) {
            return 1000;
        }
        if (Screen.m_96638_()) {
            return 100;
        }
        if (Screen.m_96639_()) {
            return 10;
        }
        return 1;
    }

    private Component getMode() {
        switch (this.tile.mode) {
            case 0: {
                ItemStack stack = (ItemStack)this.tile.inventory.get(5);
                return this.translate("gui.ic2.electric_enchanter." + (stack.m_41720_() == Items.f_42690_ || stack.m_41793_() ? "reroll" : "enchant"));
            }
            case 1: {
                return this.translate("gui.ic2.electric_enchanter.combine");
            }
            case 2: {
                return this.translate("gui.ic2.electric_enchanter.disenchant");
            }
        }
        return this.translate("tooltip.item.ic2.error");
    }
}

