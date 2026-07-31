/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.storage.components;

import ic2.core.block.base.tiles.impls.BaseElectricLoaderTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LoaderComponent
extends GuiWidget {
    public static final Component[] NAMES = new Component[]{Component.m_237115_((String)"tooltip.block.ic2.loader.nothing"), Component.m_237115_((String)"tooltip.block.ic2.loader.full"), Component.m_237115_((String)"tooltip.block.ic2.loader.transfering"), Component.m_237115_((String)"tooltip.block.ic2.loader.half_more"), Component.m_237115_((String)"tooltip.block.ic2.loader.half_less"), Component.m_237115_((String)"tooltip.block.ic2.loader.output_if_empty")};
    BaseElectricLoaderTileEntity tile;
    int lastMode;

    public LoaderComponent(BaseElectricLoaderTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
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
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        if (this.lastMode != this.tile.redstoneMode) {
            this.lastMode = this.tile.redstoneMode;
            gui.getCastedButton(0, IconButton.class).setToolTip(NAMES[this.lastMode]);
        }
    }

    private void onClick() {
        this.tile.sendToServer(0, 0);
    }
}

