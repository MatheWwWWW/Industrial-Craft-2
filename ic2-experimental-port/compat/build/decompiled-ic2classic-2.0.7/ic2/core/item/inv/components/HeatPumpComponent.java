/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.item.inv.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.item.inv.inventory.HeatPumpInventory;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class HeatPumpComponent
extends GuiWidget {
    HeatPumpInventory inventory;

    public HeatPumpComponent(HeatPumpInventory inventory) {
        super(Box2i.EMPTY_BOX);
        this.inventory = inventory;
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
        gui.addRenderableWidget((int)0, new ExtendedButton((int)(x + 40), (int)(y + 16), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.pull"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 1) == 0;
        gui.addRenderableWidget((int)1, new ExtendedButton((int)(x + 75), (int)(y + 16), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.push"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 1) != 0;
        gui.addRenderableWidget((int)2, new ExtendedButton((int)(x + 40), (int)(y + 31), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.pull"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 2) == 0;
        gui.addRenderableWidget((int)3, new ExtendedButton((int)(x + 75), (int)(y + 31), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.push"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$3(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 2) != 0;
        gui.addRenderableWidget((int)4, new ExtendedButton((int)(x + 40), (int)(y + 46), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.pull"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$4(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 4) == 0;
        gui.addRenderableWidget((int)5, new ExtendedButton((int)(x + 75), (int)(y + 46), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.push"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$5(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 4) != 0;
        gui.addRenderableWidget((int)6, new ExtendedButton((int)(x + 40), (int)(y + 61), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.pull"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$6(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 8) == 0;
        gui.addRenderableWidget((int)7, new ExtendedButton((int)(x + 75), (int)(y + 61), (int)30, (int)14, (Component)this.string((String)"tooltip.ic2.heat_pump.push"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$7(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((HeatPumpComponent)this))).f_93623_ = (this.inventory.directions & 8) != 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)0).f_93623_ = (this.inventory.directions & 1) == 0;
        gui.getButton((int)1).f_93623_ = (this.inventory.directions & 1) != 0;
        gui.getButton((int)2).f_93623_ = (this.inventory.directions & 2) == 0;
        gui.getButton((int)3).f_93623_ = (this.inventory.directions & 2) != 0;
        gui.getButton((int)4).f_93623_ = (this.inventory.directions & 4) == 0;
        gui.getButton((int)5).f_93623_ = (this.inventory.directions & 4) != 0;
        gui.getButton((int)6).f_93623_ = (this.inventory.directions & 8) == 0;
        gui.getButton((int)7).f_93623_ = (this.inventory.directions & 8) != 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawRightString(matrix, (Component)this.translate("misc.ic2.side.up").m_130946_(": "), 40, 19, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.translate("misc.ic2.side.down").m_130946_(": "), 40, 33, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.translate("misc.ic2.side.left").m_130946_(": "), 40, 50, 0x404040);
        this.gui.drawRightString(matrix, (Component)this.translate("misc.ic2.side.right").m_130946_(": "), 40, 65, 0x404040);
    }

    private void toggleSide(int index, boolean value) {
        this.inventory.onDataReceived(index, value ? 1 : 0);
        IC2.NETWORKING.get(false).sendClientItemEvent(this.inventory.getInventoryStack(), index, value ? 1 : 0);
    }

    private /* synthetic */ void lambda$init$7(Button T) {
        this.toggleSide(3, true);
    }

    private /* synthetic */ void lambda$init$6(Button T) {
        this.toggleSide(3, false);
    }

    private /* synthetic */ void lambda$init$5(Button T) {
        this.toggleSide(2, true);
    }

    private /* synthetic */ void lambda$init$4(Button T) {
        this.toggleSide(2, false);
    }

    private /* synthetic */ void lambda$init$3(Button T) {
        this.toggleSide(1, true);
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.toggleSide(1, false);
    }

    private /* synthetic */ void lambda$init$1(Button T) {
        this.toggleSide(0, true);
    }

    private /* synthetic */ void lambda$init$0(Button T) {
        this.toggleSide(0, false);
    }
}

