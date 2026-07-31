/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.transport.item.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.block.transport.item.tubes.RoundRobinTubeTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class RoundRobinComponent
extends GuiWidget {
    public static final int[] DIRECTION_COLORS = new int[]{65535, 0xFF0000, 0xFFFF00, 255, 0xFF00FF, 65280};
    RoundRobinTubeTileEntity tile;

    public RoundRobinComponent(RoundRobinTubeTileEntity tile) {
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
        for (int i = 0; i < 6; ++i) {
            int index = i;
            gui.addRenderableWidget((int)(i * 2), new ExtendedButton((int)(x + 20), (int)(y + 20 + i * 13), (int)12, (int)12, (Component)this.string((String)"-"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$0(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((RoundRobinComponent)this, (int)index))).f_93623_ = this.tile.cap[i] > 0;
            gui.addRenderableWidget((int)(i * 2 + 1), new ExtendedButton((int)(x + 108), (int)(y + 20 + i * 13), (int)12, (int)12, (Component)this.string((String)"+"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(int net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((RoundRobinComponent)this, (int)index))).f_93623_ = this.tile.cap[i] < 1000;
        }
    }

    private void add(int index) {
        this.tile.sendToServer(Screen.m_96638_() ? 2 : 0, index);
    }

    private void remove(int index) {
        this.tile.sendToServer(Screen.m_96638_() ? 3 : 1, index);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        for (int i = 0; i < 6; ++i) {
            gui.getButton((int)(i * 2)).f_93623_ = this.tile.cap[i] > 0;
            gui.getButton((int)(i * 2 + 1)).f_93623_ = this.tile.cap[i] < 1000;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        for (int i = 0; i < 6; ++i) {
            this.gui.drawCenterString(matrix, (Component)DirectionList.getName(Direction.m_122376_((int)i)).m_130946_(": " + this.tile.cap[i]), 72, 22 + i * 13, DIRECTION_COLORS[i]);
        }
    }

    private /* synthetic */ void lambda$init$1(int index, Button T) {
        this.add(index);
    }

    private /* synthetic */ void lambda$init$0(int index, Button T) {
        this.remove(index);
    }
}

