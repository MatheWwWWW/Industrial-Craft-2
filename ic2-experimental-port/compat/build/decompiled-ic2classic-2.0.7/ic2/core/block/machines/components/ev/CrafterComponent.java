/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.world.inventory.Slot
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.ev;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.containers.ev.CrafterContainer;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.slot.MemorySlot;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrafterComponent
extends GuiWidget {
    public CrafterComponent() {
        super(new Box2i(176, 0, 61, 64));
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        Box2i box = this.getBox();
        this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() + box.getX(), this.gui.getGuiTop() + box.getY(), box.getX(), box.getY(), box.getWidth(), box.getHeight());
        for (Slot slot : this.gui.getCastedContainer(CrafterContainer.class).f_38839_) {
            if (!(slot instanceof MemorySlot) || slot.m_6659_()) continue;
            this.gui.makeSlotFunction(matrix, slot);
        }
    }
}

