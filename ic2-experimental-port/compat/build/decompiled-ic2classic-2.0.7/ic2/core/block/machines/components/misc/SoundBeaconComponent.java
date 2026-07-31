/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.tiles.nv.SoundBeaconTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoundBeaconComponent
extends GuiWidget {
    SoundBeaconTileEntity tile;

    public SoundBeaconComponent(SoundBeaconTileEntity tile) {
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
        int itemEffect = (int)(this.tile.itemMod * 100.0f);
        int armorEffect = (int)(this.tile.armorMod * 100.0f);
        int blockEffect = (int)(this.tile.blockMod * 100.0f);
        matrix.m_85836_();
        matrix.m_85837_(30.0, 38.0, 0.0);
        matrix.m_85841_(0.5f, 0.5f, 1.0f);
        this.gui.drawString(matrix, (Component)this.string("Blocks"), 0, -30, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Range: " + this.tile.blockRange), 0, -20, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Effect: " + blockEffect + "%"), 0, -10, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Item"), 100, -30, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Range: " + this.tile.itemRange), 100, -20, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Effect: " + itemEffect + "%"), 100, -10, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Armor"), 200, -30, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Range: " + this.tile.armorRange), 200, -20, 0x404040);
        this.gui.drawString(matrix, (Component)this.string("Effect: " + armorEffect + "%"), 200, -10, 0x404040);
        matrix.m_85849_();
    }
}

