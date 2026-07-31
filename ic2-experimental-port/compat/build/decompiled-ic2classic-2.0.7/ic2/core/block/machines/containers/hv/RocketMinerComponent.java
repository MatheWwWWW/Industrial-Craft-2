/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.containers.hv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RocketMinerComponent
extends GuiWidget {
    private static final int COLOR = ColorUtils.darker(ColorUtils.CYAN);
    private static final Box2i CELL_BOX = new Box2i(152, 15, 6, 36);
    private static final Box2i MINING_BOX = new Box2i(164, 15, 6, 36);
    RocketMinerTileEntity tile;

    public RocketMinerComponent(RocketMinerTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ToolTipButton(x + 145, y + 4, 10, 10, (Component)Component.m_237113_((String)"D"), T -> this.drainCells())).setToolTip("gui.ic2.rocket_miner.cells.drain");
        gui.addRenderableWidget(1, new ToolTipButton(x + 162, y + 4, 10, 10, (Component)Component.m_237113_((String)"D"), T -> this.drainPipes())).setToolTip("gui.ic2.rocket_miner.pipes.drain");
    }

    private void drainPipes() {
        this.tile.sendToServer(1, 0);
    }

    private void drainCells() {
        this.tile.sendToServer(2, 0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getButton((int)0).f_93623_ = this.tile.storedCells > 0;
        gui.getButton((int)1).f_93623_ = this.tile.storedMiningPipes > 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        float progress = (float)this.tile.storedCells / 1024.0f;
        this.gui.drawColoredRegion(matrix, x + 153, (float)(y + 16 + 34) - 34.0f * progress, 2.0f, 34.0f * progress, COLOR);
        progress = (float)this.tile.storedMiningPipes / 512.0f;
        this.gui.drawColoredRegion(matrix, x + 165, (float)(y + 16 + 34) - 34.0f * progress, 2.0f, 34.0f * progress, COLOR);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (CELL_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.rocket_miner.cells", this.tile.storedCells));
        }
        if (MINING_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.rocket_miner.pipes", this.tile.storedMiningPipes));
        }
    }
}

