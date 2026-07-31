/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.logic.planner.newLogic.ReactorLogic;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class LogicButtonComponent
extends GuiWidget {
    public static final Predicate<String> NUMBERS_ONLY = T -> {
        try {
            if (T.isEmpty()) {
                return true;
            }
            int number = Integer.parseInt(T);
            return number >= 1;
        }
        catch (Exception exception) {
            return false;
        }
    };
    ReactorPlannerTileEntity tile;
    @OnlyIn(value=Dist.CLIENT)
    EditBox speed;
    boolean lastActive = false;
    boolean updating = false;

    public LogicButtonComponent(ReactorPlannerTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(100, new ExtendedButton(x + 170, y + 20, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.start"), T -> this.tile.sendToServer(7, 0)));
        gui.addRenderableWidget(99, new ExtendedButton(x + 170, y + 31, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.pulse"), T -> this.tile.sendToServer(18, 0)));
        gui.addRenderableWidget(101, new ExtendedButton(x + 170, y + 42, 47, 10, this.getPauseName(), T -> this.tile.sendToServer(8, 0)));
        gui.addRenderableWidget(102, new ExtendedButton(x + 170, y + 53, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.stop"), T -> this.tile.sendToServer(7, 1)));
        gui.addRenderableWidget(103, new ExtendedButton(x + 170, y + 67, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.compile"), T -> this.tile.sendToServer(9, 0)));
        gui.addRenderableWidget(104, new ExtendedButton(x + 170, y + 78, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.reset"), T -> this.tile.sendToServer(9, 1)));
        gui.addRenderableWidget(105, new ExtendedButton(x + 170, y + 92, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.backup"), T -> this.tile.sendToServer(10, 0)));
        gui.addRenderableWidget(106, new ExtendedButton(x + 170, y + 103, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.restore"), T -> this.tile.sendToServer(10, 1)));
        gui.addRenderableWidget(107, new ExtendedButton(x + 170, y + 118, 47, 10, (Component)this.translate("gui.ic2.reactor_planner.button.clear"), T -> this.tile.sendToServer(14, Screen.m_96638_() ? 1 : 0)));
        gui.addRenderableWidget(110, new ExtendedButton(x + 220, y + 130, 10, 10, (Component)this.string("+"), T -> this.tile.sendToServer(13, Screen.m_96638_() ? 10 : 1)));
        gui.addRenderableWidget(110, new ExtendedButton(x + 264, y + 130, 10, 10, (Component)this.string("-"), T -> {
            if (!this.updating) {
                this.tile.sendToServer(13, Screen.m_96638_() ? -10 : -1);
            }
        }));
        this.updating = true;
        this.speed = new EditBox(gui.getFont(), gui.getGuiLeft() + 220, gui.getGuiTop() + 153, 54, 10, (Component)this.string());
        this.speed.m_94144_(Integer.toString(this.tile.settings.ticksPerTick));
        this.speed.m_94151_(T -> {
            if (!T.isEmpty() && !this.updating) {
                this.tile.sendToServer(15, Math.min(100000, Integer.parseInt(T)));
            }
        });
        this.speed.m_94190_(true);
        this.speed.m_94153_(NUMBERS_ONLY);
        gui.addRenderableWidget(111, this.speed);
        this.updating = false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        ReactorLogic logic = this.tile.simulation;
        gui.getButton((int)100).f_93623_ = logic.isValid && !this.tile.isActive();
        gui.getButton((int)99).f_93623_ = logic.isValid && !this.tile.isActive();
        AbstractWidget button = gui.getButton(101);
        button.f_93623_ = logic.isValid;
        if (this.lastActive != logic.isProducing) {
            this.lastActive = logic.isProducing;
            button.m_93666_(this.getPauseName());
        }
        gui.getButton((int)102).f_93623_ = this.tile.isActive();
        gui.getButton((int)103).f_93623_ = !logic.isValid;
        gui.getButton((int)104).f_93623_ = logic.isValid || logic.tick > 0;
        gui.getButton((int)105).f_93623_ = logic.isValid;
        gui.getButton((int)106).f_93623_ = this.tile.backup.hasBackup && !this.tile.isActive();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        if (this.speed.m_5953_((double)(mouseX + this.gui.getGuiLeft()), (double)(mouseY + this.gui.getGuiTop()))) {
            this.tile.sendToServer(16, scroll * (Screen.m_96638_() ? 10 : 1));
            return true;
        }
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void onChanged() {
        this.updating = true;
        this.speed.m_94144_(Integer.toString(this.tile.settings.ticksPerTick));
        this.updating = false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.string(Integer.toString(this.tile.stackSize)), 247, 131, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.reactor_planner.sim_speed"), 220, 143, 0x404040);
    }

    public Component getPauseName() {
        return this.translate("gui.ic2.reactor_planner.button." + (this.tile.simulation.isProducing ? "pause" : "continue"));
    }
}

