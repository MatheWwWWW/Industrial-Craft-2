/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.hv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.tiles.hv.MassFabricatorTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MassFabricatorComponent
extends GuiWidget {
    MassFabricatorTileEntity tile;

    public MassFabricatorComponent(MassFabricatorTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawString(matrix, (Component)this.translate("gui.ic2.mass_fabricator.progress"), 25, 20, 0x404040);
        this.gui.drawString(matrix, (Component)this.string(Formatters.EU_READER_FORMAT.format(this.tile.getProgress() / this.tile.getMaxProgress() * 100.0f) + "%"), 25, 29, 0x404040);
        if (this.tile.getScrap() > 0) {
            this.gui.drawString(matrix, (Component)this.translate("gui.ic2.mass_fabricator.amplifier"), 105, 20, 0x404040);
            this.gui.drawString(matrix, (Component)this.string("" + this.tile.getScrap()), 105, 29, 0x404040);
        }
    }
}

