/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.base.tiles.impls.BaseEnergyStorageTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyStorageComponent
extends GuiWidget {
    public static final Component[] NAMES = new Component[]{Component.m_237115_((String)"tooltip.block.ic2.energy_storage.nothing"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.full"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.part"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.half_more"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.half_less"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.empty"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.no_output"), Component.m_237115_((String)"tooltip.block.ic2.energy_storage.no_output_unless_full")};
    protected BaseEnergyStorageTileEntity tile;
    int lastMode = -1;

    public EnergyStorageComponent(BaseEnergyStorageTileEntity block) {
        super(Box2i.EMPTY_BOX);
        this.tile = block;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        this.lastMode = this.tile.redstoneMode;
        gui.addRenderableWidget(0, new IconButton(gui.getGuiLeft() + 152, gui.getGuiTop() + 4, 20, 20, new ItemStack((ItemLike)Items.f_42451_), T -> this.onClick())).setToolTip(NAMES[this.lastMode]);
    }

    @Override
    public void tick(IC2Screen gui) {
        if (this.lastMode != this.tile.redstoneMode) {
            this.lastMode = this.tile.redstoneMode;
            gui.getCastedButton(0, IconButton.class).setToolTip(NAMES[this.lastMode]);
        }
    }

    private void onClick() {
        this.tile.sendToServer(0, 0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        float per;
        float lvl;
        int eu = this.tile.getStoredEU();
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        int offset = this.tile.getGuiOffset();
        this.gui.drawTextureRegion(matrix, x + 55 + offset, y + 16, 0.0f, 166.0f, 52.0f, 54.0f);
        if (eu > 0 && (lvl = (per = Math.min(1.0f, (float)eu / (float)this.tile.getMaxEU())) * 24.0f) > 0.0f) {
            this.gui.drawTextureRegion(matrix, x + 79 + offset, y + 34, 176.0f, 14.0f, lvl, 16.0f);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int offset = this.tile.getGuiOffset();
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.storage.power"), 85 + offset, 23, 0x404040);
        int max = this.tile.getMaxEU();
        int eu = Math.min(this.tile.getStoredEU(), max);
        this.gui.drawString(matrix, (Component)this.translate("misc.ic2.eu", Formatters.EU_FORMAT.format(eu)), 109 + offset, 35, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("/").m_7220_((Component)this.translate("misc.ic2.eu", Formatters.EU_FORMAT.format(max))), 109 + offset, 45, 0x404040);
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.storage.output", Formatters.EU_FORMAT.format(this.tile.output)), 85 + offset, 58, 0x404040);
    }
}

