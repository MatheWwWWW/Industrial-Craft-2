/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.generators.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.generators.tiles.SolarTurbineTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SolarTurbineComponent
extends GuiWidget {
    SolarTurbineTileEntity tile;

    public SolarTurbineComponent(SolarTurbineTileEntity tile) {
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
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.gen"), 75, 52, 0x404040);
        this.gui.drawString(matrix, (Component)this.string(ItemStack.f_41584_.format((float)this.tile.heat / 240.0f) + "%"), 60, 62, 0x404040);
    }
}

