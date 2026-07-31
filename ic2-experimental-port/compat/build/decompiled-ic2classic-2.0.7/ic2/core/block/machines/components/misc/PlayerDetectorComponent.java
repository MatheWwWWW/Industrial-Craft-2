/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.misc.tiles.PlayerDetectorTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.inventory.gui.components.base.ScrollSlider;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerDetectorComponent
extends GuiWidget {
    static final Component[] COMPONENTS = new Component[]{Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.public")}), Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.protected")}), Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.private")})};
    static final ItemStack[] COMPONENT_ITEMS = new ItemStack[]{new ItemStack((ItemLike)Items.f_42430_), new ItemStack((ItemLike)Items.f_42411_), new ItemStack((ItemLike)Items.f_42740_)};
    PlayerDetectorTileEntity tile;
    int lastMode;
    int[] last = new int[3];

    public PlayerDetectorComponent(PlayerDetectorTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        this.lastMode = this.tile.mode;
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new ScrollSlider(x + 25, y + 35, 40, 10, (Component)this.translate("gui.ic2.player_detector.x"), (Component)this.string(), 0.0, 64.0, this.tile.xRadius, T -> this.setRadius(0, T.getValueInt())).setScrollEffect(1.0));
        gui.addRenderableWidget(1, new ScrollSlider(x + 70, y + 35, 40, 10, (Component)this.translate("gui.ic2.player_detector.y"), (Component)this.string(), 0.0, 64.0, this.tile.yRadius, T -> this.setRadius(1, T.getValueInt())).setScrollEffect(1.0));
        gui.addRenderableWidget(2, new ScrollSlider(x + 115, y + 35, 40, 10, (Component)this.translate("gui.ic2.player_detector.z"), (Component)this.string(), 0.0, 64.0, this.tile.zRadius, T -> this.setRadius(2, T.getValueInt())).setScrollEffect(1.0));
        gui.addRenderableWidget(3, new IconButton(x + 176, y, 16, 16, COMPONENT_ITEMS[this.lastMode], T -> this.changeMode()).setToolTip(COMPONENTS[this.lastMode]));
    }

    private void changeMode() {
        this.tile.sendToServer(3, 0);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.player_detector.scan_radius"), 90, 23, 0x404040);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        ScrollSlider slider = gui.getCastedButton(0, ScrollSlider.class);
        if (this.last[0] != this.tile.xRadius) {
            this.last[0] = this.tile.xRadius;
            slider.m_93611_(this.tile.xRadius);
            slider.m_5697_();
        }
        slider = gui.getCastedButton(1, ScrollSlider.class);
        if (this.last[1] != this.tile.yRadius) {
            this.last[1] = this.tile.yRadius;
            slider.m_93611_(this.tile.yRadius);
            slider.m_5697_();
        }
        slider = gui.getCastedButton(2, ScrollSlider.class);
        if (this.last[2] != this.tile.zRadius) {
            this.last[2] = this.tile.zRadius;
            slider.m_93611_(this.tile.zRadius);
            slider.m_5697_();
        }
        if (this.lastMode != this.tile.mode) {
            this.lastMode = this.tile.mode;
            gui.getCastedButton(3, IconButton.class).setDisplay(COMPONENT_ITEMS[this.lastMode]).setToolTip(COMPONENTS[this.lastMode]);
        }
    }

    public void setRadius(int index, int value) {
        this.tile.sendToServer(index, value);
        switch (index) {
            case 0: {
                this.tile.xRadius = Mth.m_14045_((int)value, (int)0, (int)64);
                break;
            }
            case 1: {
                this.tile.yRadius = Mth.m_14045_((int)value, (int)0, (int)64);
                break;
            }
            case 2: {
                this.tile.zRadius = Mth.m_14045_((int)value, (int)0, (int)64);
            }
        }
    }
}

