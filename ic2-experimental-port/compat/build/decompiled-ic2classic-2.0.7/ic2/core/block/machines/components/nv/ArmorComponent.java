/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.nv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.armor.IArmorModule;
import ic2.core.block.machines.containers.nv.ArmorConfiguratorContainer;
import ic2.core.block.machines.tiles.nv.ArmorConfiguratorTileEntity;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArmorComponent
extends GuiWidget {
    SliderComponent slider = this.addChild(new SliderComponent(new Box2i(172, 21, 12, 86), new Box2i(0, 241, 12, 15))).setListener(this::sendScroll).setNonEmptyRows(3);
    ArmorConfiguratorTileEntity tile;

    public ArmorComponent(ArmorConfiguratorTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        for (int j = 0; j < 3; ++j) {
            int m = Math.min(this.tile.armory.getSize(j), 9);
            for (int i = 0; i < m; ++i) {
                this.gui.drawTextureRegion(matrix, 7 + x + 18 * i, 28 + y + j * 30, 17.0f, 238.0f, 18.0f, 18.0f);
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        for (int j = 0; j < 3; ++j) {
            IArmorModule.ModuleType type = this.tile.armory.getType(j);
            if (type == null) continue;
            this.gui.drawString(matrix, type.getName(), 7, 19 + j * 30, 0x404040);
        }
    }

    private void sendScroll(int scroll) {
        this.tile.sendToServer(1, scroll);
        this.tile.offset = Mth.m_14045_((int)scroll, (int)0, (int)this.tile.armory.getTypes());
        ((IC2Container)this.gui.getPlayer().f_36096_).clearSlots(ArmorConfiguratorContainer.ModuleSlot.class);
    }

    public void syncScroll() {
        this.slider.setMax(this.tile.armory.getTypes()).setCurrent(this.tile.offset);
    }
}

