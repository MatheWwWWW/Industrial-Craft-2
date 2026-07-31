/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.ev;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColossalMachineComponent
extends GuiWidget {
    BaseColossalMachineTileEntity tile;
    public static final Vec2i[][] PROGRESS = new Vec2i[][]{new Vec2i[0], new Vec2i[0], {new Vec2i(74, 23), new Vec2i(74, 55)}, new Vec2i[0], {new Vec2i(45, 30), new Vec2i(108, 30), new Vec2i(45, 49), new Vec2i(108, 49)}, new Vec2i[0], new Vec2i[0], new Vec2i[0], {new Vec2i(45, 19), new Vec2i(107, 19), new Vec2i(45, 38), new Vec2i(107, 38), new Vec2i(45, 57), new Vec2i(107, 57), new Vec2i(45, 76), new Vec2i(107, 76)}};

    public ColossalMachineComponent(BaseColossalMachineTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int size = this.tile.getSlotsInUse();
        for (int i = 0; i < size; ++i) {
            float progress;
            float f = progress = this.tile.recipeOperation[i] <= 0 ? 0.0f : this.tile.progress[i] / (float)this.tile.recipeOperation[i] * 24.0f;
            if (!(progress > 0.0f)) continue;
            Vec2i pos = PROGRESS[size][i];
            this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() + pos.getX(), this.gui.getGuiTop() + pos.getY(), 176.0f, 14.0f, progress, 16.0f);
        }
    }

    @Override
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        int size = this.tile.getSlotsInUse();
        for (int i = 0; i < size; ++i) {
            Vec2i pos = PROGRESS[size][i];
            int minX = pos.getX();
            int minY = pos.getY();
            if (mouseX < minX || mouseX > minX + 24 || mouseY < minY || mouseY > minY + 16) continue;
            tooltips.accept((Component)this.translate("gui.ic2.progress", Formatters.EU_FORMAT.format(this.tile.progress[i]), Formatters.EU_FORMAT.format(this.tile.recipeOperation[i])));
        }
    }
}

