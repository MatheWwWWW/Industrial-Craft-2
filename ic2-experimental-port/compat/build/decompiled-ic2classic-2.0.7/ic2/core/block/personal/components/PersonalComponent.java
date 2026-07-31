/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.personal.components;

import ic2.core.block.base.tiles.impls.BasePersonalTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconButton;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PersonalComponent
extends GuiWidget {
    static final Component[] COMPONENTS = new Component[]{Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.public")}), Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.protected")}), Component.m_237110_((String)"gui.ic2.personal.mode", (Object[])new Object[]{Component.m_237115_((String)"gui.ic2.personal.mode.private")})};
    static final ItemStack[] COMPONENT_ITEMS = new ItemStack[]{new ItemStack((ItemLike)Items.f_42430_), new ItemStack((ItemLike)Items.f_42411_), new ItemStack((ItemLike)Items.f_42740_)};
    BasePersonalTileEntity tile;
    int lastMode;

    public PersonalComponent(BasePersonalTileEntity tile) {
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
        this.lastMode = this.tile.mode;
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, new IconButton(x + 176, y, 16, 16, COMPONENT_ITEMS[this.lastMode], T -> this.changeMode()).setToolTip(COMPONENTS[this.lastMode]));
        gui.addRenderableWidget(1, new IconButton(x + 176, y + 17, 16, 16, new ItemStack((ItemLike)Items.f_42584_), T -> this.voidContents()).setToolTip("gui.ic2.personal.void"));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        if (this.lastMode != this.tile.mode) {
            this.lastMode = this.tile.mode;
            gui.getCastedButton(0, IconButton.class).setDisplay(COMPONENT_ITEMS[this.lastMode]).setToolTip(COMPONENTS[this.lastMode]);
        }
        gui.getButton((int)1).f_93623_ = Screen.m_96637_() && Screen.m_96638_();
    }

    private void changeMode() {
        this.tile.sendToServer(0, 0);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void voidContents() {
        if (Screen.m_96637_() && Screen.m_96638_()) {
            this.tile.sendToServer(1, 0);
        }
    }
}

