/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.transport.item.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.transport.item.tubes.StackingTubeTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class StackingTubeComponent
extends GuiWidget {
    public static final Box2i TEXTURE = new Box2i(176, 0, 12, 15);
    public static final Box2i POSITION = new Box2i(156, 29, 12, 80);
    StackingTubeTileEntity tile;
    SliderComponent slider = this.addChild(new SliderComponent(POSITION, TEXTURE, 9).setNonEmptyRows(4));

    public StackingTubeComponent(StackingTubeTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ExtendedButton(x + 60, y + 121, 14, 14, (Component)this.string("+"), T -> this.trigger(1)));
        gui.addRenderableWidget(1, new ExtendedButton(x + 110, y + 121, 14, 14, (Component)this.string("-"), T -> this.trigger(-1)));
        gui.addRenderableWidget(2, new ItemCheckBox(x + 155, y + 121, 14, 14, T -> this.tile.sendToServer(3, 0), new ItemStack((ItemLike)IC2Items.PAINTER))).setToolTip("gui.ic2.stacking_tube.ignore_color");
        for (int i = 0; i < 45; ++i) {
            int index = i;
            int xOff = i % 9;
            int yOff = i / 9;
            boolean showItem = i < this.tile.cached.size();
            gui.addRenderableWidget((int)(10 + i), new IconButton((int)(x + 6 + xOff * 16), (int)(y + 29 + yOff * 16), (int)16, (int)16, (ItemStack)(showItem ? ((StackingTubeTileEntity.StackingStack)this.tile.cached.get((int)i)).getClientStack() : ItemStack.f_41583_), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((StackingTubeComponent)this, (int)index)).setDurability((boolean)true)).f_93624_ = showItem;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)0).f_93623_ = this.tile.stacking < 64;
        gui.getButton((int)1).f_93623_ = this.tile.stacking > 1;
        gui.getCastedButton(2, ItemCheckBox.class).setChecked(this.tile.ignoreColors);
        for (int i = 0; i < 45; ++i) {
            int index = i + this.slider.getCurrent();
            IconButton button = gui.getCastedButton(10 + i, IconButton.class);
            button.f_93624_ = index < this.tile.cached.size();
            button.setDisplay(button.f_93624_ ? ((StackingTubeTileEntity.StackingStack)this.tile.cached.get(index)).getClientStack() : ItemStack.f_41583_);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void trigger(int size) {
        if (Screen.m_96638_()) {
            size *= 10;
        }
        this.tile.stacking = Mth.m_14045_((int)(this.tile.stacking + size), (int)1, (int)64);
        this.tile.sendToServer(1, size);
    }

    protected void sendItem(int index) {
        this.tile.sendToServer(2, index + this.slider.getCurrent());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.slider.setMax(this.tile.cached.size());
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.stacking_tube.items"), 5, 20, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.stacking_tube.threshold"), 67, 112, 0x404040);
        String s = "" + this.tile.stacking;
        this.gui.drawCenterString(matrix, (Component)this.string(s), 92, 125, 0x404040);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        for (int i = 0; i < 45; ++i) {
            AbstractWidget widget = this.gui.getButton(10 + i);
            if (!widget.m_198029_() || !widget.f_93624_ || this.slider.getCurrent() + i >= this.tile.cached.size()) continue;
            for (Component comp : ((StackingTubeTileEntity.StackingStack)this.tile.cached.get(i + this.slider.getCurrent())).getClientStack().m_41651_((Player)this.gui.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL)) {
                tooltips.accept(comp);
            }
            tooltips.accept((Component)this.translate("gui.ic2.stacking_tube.stored", ((StackingTubeTileEntity.StackingStack)this.tile.cached.get(i)).getAmount(), ChatFormatting.GRAY));
            tooltips.accept((Component)this.translate("gui.ic2.stacking_tube.click", ChatFormatting.GRAY));
        }
    }

    private /* synthetic */ void lambda$init$3(int index, Button T) {
        this.sendItem(index);
    }
}

