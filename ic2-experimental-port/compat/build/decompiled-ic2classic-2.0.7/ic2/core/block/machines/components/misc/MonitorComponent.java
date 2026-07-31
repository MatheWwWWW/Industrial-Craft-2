/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ColorButton;
import ic2.core.inventory.gui.components.base.IconCheckBox;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class MonitorComponent
extends GuiWidget {
    MonitorTileEntity tile;

    public MonitorComponent(MonitorTileEntity tile) {
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
        gui.addRenderableWidget(0, new IconCheckBox(x + 120, y + 58, 49, 14, (Component)this.translate("gui.ic2.monitor.lock"), this.tile.isLocked()).setListener(T -> this.toggleLock()).setItemLike(true).disableScale(true).setToolTip("gui.ic2.monitor.lock.desc"));
        gui.addRenderableWidget((int)1, new ExtendedButton((int)(x + 7), (int)(y + 58), (int)35, (int)14, (Component)this.translate((String)"gui.ic2.monitor.left"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((MonitorComponent)this))).f_93623_ = this.tile.align != IDisplayInfo.Alignment.LEFT;
        gui.addRenderableWidget((int)2, new ExtendedButton((int)(x + 43), (int)(y + 58), (int)40, (int)14, (Component)this.translate((String)"gui.ic2.monitor.center"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((MonitorComponent)this))).f_93623_ = this.tile.align != IDisplayInfo.Alignment.CENTER;
        gui.addRenderableWidget((int)3, new ExtendedButton((int)(x + 84), (int)(y + 58), (int)35, (int)14, (Component)this.translate((String)"gui.ic2.monitor.right"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((MonitorComponent)this))).f_93623_ = this.tile.align != IDisplayInfo.Alignment.RIGHT;
        gui.addRenderableWidget(4, new ToolTipButton(x + 107, y + 36, 12, 12, (Component)this.string("-"), T -> this.changeScale(-1.0f))).setToolTip("gui.ic2.monitor.modifiers");
        gui.addRenderableWidget(5, new ToolTipButton(x + 152, y + 36, 12, 12, (Component)this.string("+"), T -> this.changeScale(1.0f))).setToolTip("gui.ic2.monitor.modifiers");
        gui.addRenderableWidget(6, new ColorButton(x + 7, y + 37, 16, 16, this.tile.getScreenColor(), this::setColor));
        gui.addRenderableWidget(7, new ItemCheckBox(x + 153, y + 5, 16, 16, T -> this.tile.sendToServer(4, 0), new ItemStack((ItemLike)Items.f_41978_), this.tile.requireRedstone)).setToolTip("gui.ic2.monitor.require");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getCastedButton(0, IconCheckBox.class).setChecked(this.tile.isLocked());
        gui.getButton((int)1).f_93623_ = this.tile.align != IDisplayInfo.Alignment.LEFT;
        gui.getButton((int)2).f_93623_ = this.tile.align != IDisplayInfo.Alignment.CENTER;
        gui.getButton((int)3).f_93623_ = this.tile.align != IDisplayInfo.Alignment.RIGHT;
        gui.getButton((int)4).f_93623_ = this.tile.scale > 1.0f;
        gui.getButton((int)5).f_93623_ = this.tile.scale < 10.0f;
        gui.getCastedButton(6, ColorButton.class).setColor(this.tile.getScreenColor());
        gui.getCastedButton(7, ItemCheckBox.class).setChecked(this.tile.requireRedstone);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.monitor.scale"), 136, 24, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.string(Formatters.F_POINT_2.format(this.tile.scale) + "x"), 136, 38, 0x404040);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void setColor(ColorButton button) {
        this.handleColorClick(button.getColor(), (AbstractWidget)button, false, T -> {
            this.tile.setScreenColor((DyeColor)T);
            this.tile.sendToServer(3, T.m_41060_());
        });
    }

    private void toggleLock() {
        this.tile.sendToServer(0, 0);
    }

    private void toggleAlignment(IDisplayInfo.Alignment align) {
        this.tile.align = align;
        this.tile.sendToServer(1, align.ordinal());
    }

    @OnlyIn(value=Dist.CLIENT)
    private void changeScale(float scale) {
        if (Screen.m_96638_()) {
            scale *= 0.1f;
        } else if (Screen.m_96637_()) {
            scale *= 0.01f;
        }
        this.tile.sendToServer(2, Float.floatToIntBits(scale));
        this.tile.scale = Mth.m_14036_((float)(this.tile.scale + scale), (float)1.0f, (float)10.0f);
    }

    private /* synthetic */ void lambda$init$3(Button T) {
        this.toggleAlignment(IDisplayInfo.Alignment.RIGHT);
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.toggleAlignment(IDisplayInfo.Alignment.CENTER);
    }

    private /* synthetic */ void lambda$init$1(Button T) {
        this.toggleAlignment(IDisplayInfo.Alignment.LEFT);
    }
}

