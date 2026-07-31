/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.transport.item.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.block.transport.item.tubes.ColorFilterTubeTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.IconCheckBox;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.platform.player.KeyHelper;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ColorFilterTubeComponent
extends GuiWidget {
    public static final Box2i PROGRESS_BOX = new Box2i(155, 17, 11, 112);
    ColorFilterTubeTileEntity tile;
    IHasInventory inv;
    SliderComponent slider = this.addChild(new SliderComponent(PROGRESS_BOX, new Box2i(176, 0, 12, 15), 7));

    public ColorFilterTubeComponent(ColorFilterTubeTileEntity tile, IHasInventory inv) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.inv = inv;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    private DirectionList getSides(int index) {
        return index < 0 || index >= this.tile.usedColors.size() ? DirectionList.EMPTY : DirectionList.ofNumber(this.tile.colorDirections[((DyeColor)this.tile.usedColors.get(index)).m_41060_()]);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        for (int i = 0; i < 14; ++i) {
            int xOff = i % 7;
            int yOff = i / 7;
            int index = i;
            boolean visible = i < this.tile.usedColors.size();
            gui.addRenderableWidget((int)(i * 7), new IconButton((int)(x + 8 + xOff * 20), (int)(y + 17 + yOff * 56), (int)20, (int)20, (ItemStack)(visible ? new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)((DyeColor)this.tile.usedColors.get((int)i)))) : ItemStack.f_41583_), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((ColorFilterTubeComponent)this, (int)index))).f_93624_ = visible;
            gui.addRenderableWidget(i * 7 + 1, this.create(x + 8 + xOff * 20, y + 37 + yOff * 56, index, "D", Direction.DOWN, 65535));
            gui.addRenderableWidget(i * 7 + 2, this.create(x + 18 + xOff * 20, y + 37 + yOff * 56, index, "U", Direction.UP, 0xFF0000));
            gui.addRenderableWidget(i * 7 + 3, this.create(x + 8 + xOff * 20, y + 49 + yOff * 56, index, "N", Direction.NORTH, 0xFFFF00));
            gui.addRenderableWidget(i * 7 + 4, this.create(x + 18 + xOff * 20, y + 49 + yOff * 56, index, "S", Direction.SOUTH, 255));
            gui.addRenderableWidget(i * 7 + 5, this.create(x + 8 + xOff * 20, y + 61 + yOff * 56, index, "W", Direction.WEST, 0xFF00FF));
            gui.addRenderableWidget(i * 7 + 6, this.create(x + 18 + xOff * 20, y + 61 + yOff * 56, index, "E", Direction.EAST, 65280));
        }
        gui.addRenderableWidget(110, new ExtendedButton(x + 97, y + 134, 36, 14, (Component)this.translate("gui.ic2.color_tube.add"), T -> this.add()));
        gui.addRenderableWidget(111, new ItemCheckBox(x + 75, y + 132, 18, 18, T -> this.inv(), new ItemStack((ItemLike)Items.f_42768_)).setChecked(this.tile.invPriority)).setToolTip((Component)this.translate("gui.ic2.tube_priority_inventory").m_130946_("\n").m_7220_((Component)this.translate("gui.ic2.tube_priority_inventory.desc", ChatFormatting.GRAY)));
        gui.addRenderableWidget(120, this.create(x + 8, y + 131, -1, "D", Direction.DOWN, 65535));
        gui.addRenderableWidget(121, this.create(x + 18, y + 131, -1, "U", Direction.UP, 0xFF0000));
        gui.addRenderableWidget(122, this.create(x + 28, y + 131, -1, "N", Direction.NORTH, 0xFFFF00));
        gui.addRenderableWidget(123, this.create(x + 38, y + 131, -1, "S", Direction.SOUTH, 255));
        gui.addRenderableWidget(124, this.create(x + 48, y + 131, -1, "W", Direction.WEST, 0xFF00FF));
        gui.addRenderableWidget(125, this.create(x + 58, y + 131, -1, "E", Direction.EAST, 65280));
    }

    @OnlyIn(value=Dist.CLIENT)
    private IconCheckBox create(int x, int y, int index, String text, Direction dir, int color) {
        IconCheckBox box = new IconCheckBox(x, y, 10, 12, (Component)this.string(text), DirectionList.ofNumber(index >= 0 ? this.tile.colorDirections[index] : this.tile.emptyDirections).notContains(dir)).setListener(T -> this.toggle(index, dir)).setDisableLike(true).setToolTip((Component)DirectionList.getName(dir));
        box.setFGColor(color);
        box.f_93624_ = index == -1;
        return box;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        this.slider.setMax(this.tile.usedColors.size());
        for (int i = 0; i < 14; ++i) {
            boolean visible = i + this.slider.getCurrent() < this.tile.usedColors.size();
            gui.getCastedButton((int)(i * 7), IconButton.class).setDisplay((ItemStack)(visible ? new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)((DyeColor)this.tile.usedColors.get((int)(this.slider.getCurrent() + i))))) : ItemStack.f_41583_)).f_93624_ = visible;
            DirectionList sides = this.getSides(i + this.slider.getCurrent());
            for (Direction dir : DirectionList.ALL) {
                gui.getCastedButton((int)(i * 7 + dir.m_122411_() + 1), IconCheckBox.class).setChecked((boolean)(visible && sides.notContains((Direction)dir) ? true : false)).f_93624_ = visible;
            }
        }
        gui.getButton((int)110).f_93623_ = !this.inv.getStackInSlot(0).m_41619_();
        gui.getCastedButton(111, ItemCheckBox.class).setChecked(this.tile.invPriority);
        DirectionList sides = DirectionList.ofNumber(this.tile.emptyDirections);
        for (Direction dir : DirectionList.ALL) {
            gui.getCastedButton(120 + dir.m_122411_(), IconCheckBox.class).setChecked(sides.notContains(dir));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        for (int i = 0; i < 14; ++i) {
            IconButton button = this.gui.getCastedButton(i * 7, IconButton.class);
            if (button.m_198029_() && button.f_93624_) {
                tooltips.accept(button.getDisplay().m_41786_());
                tooltips.accept((Component)this.buildKeyDescription(KeyHelper.SNEAK_KEY, "gui.ic2.color_tube.to_delete", new Object[0]).m_130940_(ChatFormatting.GRAY));
                break;
            }
            for (int x = 1; x < 7; ++x) {
                IconCheckBox widget = this.gui.getCastedButton(i * 7 + x, IconCheckBox.class);
                if (!widget.m_198029_() || !widget.f_93624_) continue;
                tooltips.accept((Component)this.translate(widget.isChecked() ? "gui.ic2.color_tube.enable" : "gui.ic2.color_tube.disable", widget.isChecked() ? ChatFormatting.GREEN : ChatFormatting.RED));
                return;
            }
        }
        for (int x = 0; x < 6; ++x) {
            IconCheckBox widget = this.gui.getCastedButton(120 + x, IconCheckBox.class);
            if (!widget.m_198029_() || !widget.f_93624_) continue;
            tooltips.accept((Component)this.translate(widget.isChecked() ? "gui.ic2.color_tube.enable" : "gui.ic2.color_tube.disable", widget.isChecked() ? ChatFormatting.GREEN : ChatFormatting.RED));
            tooltips.accept((Component)this.translate("gui.ic2.color_tube.undefined_color", ChatFormatting.DARK_GRAY));
            return;
        }
    }

    private void removeIndex(int index) {
        if (Screen.m_96638_()) {
            this.tile.sendToServer(1, index + this.slider.getCurrent());
        }
    }

    private void toggle(int index, Direction dir) {
        this.tile.sendToServer(10 + dir.m_122411_(), index + this.slider.getCurrent());
    }

    private void inv() {
        this.tile.sendToServer(2, 0);
    }

    private void add() {
        if (this.inv.getStackInSlot(0).m_41619_()) {
            return;
        }
        DyeColor color = DyeColor.getColor((ItemStack)this.inv.getStackInSlot(0));
        if (color == null) {
            return;
        }
        this.tile.sendToServer(3, color.m_41060_());
    }

    private /* synthetic */ void lambda$init$0(int index, Button T) {
        this.removeIndex(index);
    }
}

