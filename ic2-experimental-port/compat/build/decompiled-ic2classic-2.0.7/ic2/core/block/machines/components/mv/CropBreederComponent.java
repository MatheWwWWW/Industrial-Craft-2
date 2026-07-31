/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.inventory.Slot
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.gui.widget.ExtendedButton
 */
package ic2.core.block.machines.components.mv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.item.inv.inventory.CropBreederInventory;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class CropBreederComponent
extends GuiWidget {
    CropBreederInventory breeder;

    public CropBreederComponent(CropBreederInventory breeder) {
        super(Box2i.EMPTY_BOX);
        this.breeder = breeder;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ExtendedButton(x + 7, y + 108, 10, 10, (Component)this.string("<"), T -> this.moveBar(false, true)));
        gui.addRenderableWidget(1, new ExtendedButton(x + 158, y + 108, 10, 10, (Component)this.string(">"), T -> this.moveBar(false, false)));
        gui.addRenderableWidget(2, new ExtendedButton(x + 30, y + 108, 40, 10, (Component)this.translate("gui.ic2.crop_analyzer.cancel"), T -> this.otherButtons(true)));
        gui.addRenderableWidget(3, new ExtendedButton(x + 100, y + 108, 50, 10, (Component)this.translate("gui.ic2.crop_analyzer.breed"), T -> this.otherButtons(false)));
        gui.addRenderableWidget(4, new ExtendedButton(x + 7, y + 27, 10, 10, (Component)this.string("<"), T -> this.moveBar(true, true)));
        gui.addRenderableWidget(5, new ExtendedButton(x + 158, y + 27, 10, 10, (Component)this.string(">"), T -> this.moveBar(true, false)));
        gui.m_142416_(new ExtendedButton(x + 105, y + 27, 30, 10, (Component)this.translate(this.breeder.waterlogged ? "gui.ic2.crop_analyzer.yes" : "gui.ic2.crop_analyzer.no"), this::toggleResult));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.crop_analyzer.waterlogged"), 38, 28, 0x404040);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void toggleResult(Button button) {
        this.breeder.waterlogged = !this.breeder.waterlogged;
        button.m_93666_((Component)this.translate(this.breeder.waterlogged ? "gui.ic2.crop_analyzer.yes" : "gui.ic2.crop_analyzer.no"));
        IC2.NETWORKING.get(false).sendClientItemEvent(this.breeder.getInventoryStack(), 4, 0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)3).f_93623_ = this.canBreed();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot instanceof LockedSlot) {
            this.moveBar(slot.f_40219_ < 9, scroll > 0);
            return true;
        }
        return false;
    }

    public boolean canBreed() {
        int count = 0;
        for (int i = 0; i < 4; ++i) {
            count += this.breeder.getStackInSlot(i).m_41619_() ? 0 : 1;
        }
        return count > 1;
    }

    public void otherButtons(boolean breed) {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.breeder.getInventoryStack(), breed ? 2 : 3, 0);
    }

    public void moveBar(boolean top, boolean left) {
        IC2.NETWORKING.get(false).sendClientItemEvent(this.breeder.getInventoryStack(), top ? 0 : 1, left ? -1 : 1);
    }
}

