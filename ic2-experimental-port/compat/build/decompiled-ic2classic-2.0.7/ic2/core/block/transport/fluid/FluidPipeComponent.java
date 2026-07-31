/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.fluid;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.transport.fluid.helper.InventoryData;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FluidPipeComponent
extends GuiWidget {
    BaseTileEntity tile;
    InventoryData data;
    Predicate<Direction> pumping;
    int lastDir = 0;

    public FluidPipeComponent(BaseTileEntity tile, InventoryData data, Predicate<Direction> pumping) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.data = data;
        this.pumping = pumping;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(0, this.create(x + 7, y + 42, "D", Direction.DOWN));
        gui.addRenderableWidget(1, this.create(x + 18, y + 42, "U", Direction.UP));
        gui.addRenderableWidget(2, this.create(x + 29, y + 42, "N", Direction.NORTH));
        gui.addRenderableWidget(3, this.create(x + 40, y + 42, "S", Direction.SOUTH));
        gui.addRenderableWidget(4, this.create(x + 51, y + 42, "W", Direction.WEST));
        gui.addRenderableWidget(5, this.create(x + 62, y + 42, "E", Direction.EAST));
        gui.addRenderableWidget(6, new ItemCheckBox(x + 73, y + 42, 14, 14, T -> this.sendWhiteList(), new ItemStack((ItemLike)Items.f_42517_), this.data.isWhiteList())).setToolTip("tooltip.item.ic2.transport_update.compare.whitelist");
        MutableComponent all = this.string().m_7220_((Component)this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.ctrl.name"), "10x")).m_130946_(Character.toString('\n')).m_7220_((Component)this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.shift.name"), "100x")).m_130946_(Character.toString('\n')).m_7220_((Component)this.translate("tooltip.ic2.press_key_description", this.translate("tooltip.ic2.alt.name"), "1000x"));
        gui.m_142416_(new ToolTipButton(x + 97, y + 50, 10, 10, (Component)this.string("-"), T -> this.sendPriority(-1)).setToolTip((Component)all));
        gui.m_142416_(new ToolTipButton(x + 159, y + 50, 10, 10, (Component)this.string("+"), T -> this.sendPriority(1)).setToolTip((Component)all));
        gui.addRenderableWidget(7, new ItemCheckBox(x + 152, y + 5, 16, 16, T -> this.sendEmit(), new ItemStack((ItemLike)IC2Items.FLUID_IMPORT_UPGRADE_SIMPLE), this.isPumping())).setToolTip("gui.ic2.extract.fluid");
    }

    private boolean isPumping() {
        return this.pumping.test(Direction.m_122376_((int)this.data.getDir()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        for (int i = 0; i < 6; ++i) {
            gui.getButton((int)i).f_93623_ = (this.data.getDirs() & 1 << i) != 0 && i != this.data.getDir();
        }
        gui.getCastedButton(6, ItemCheckBox.class).setChecked(this.data.isWhiteList());
        gui.getCastedButton(7, ItemCheckBox.class).setChecked(this.isPumping());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        this.gui.drawCenterString(matrix, (Component)this.translate("gui.ic2.pipe.priority"), 133, 42, 0x404040);
        this.gui.drawCenterString(matrix, (Component)this.string("" + this.data.getPriority()), 133, 52, 0x404040);
        RenderSystem.m_157456_((int)0, (ResourceLocation)InventoryMenu.f_39692_);
        Lighting.m_84931_();
        RenderUtils.renderGuiItem(this.gui.getRenderItem(), matrix, new ItemStack((ItemLike)DirectionList.getNeighborState(this.tile, Direction.m_122376_((int)this.data.getDir())).m_60734_()), 7.0f, 4.0f);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    private AbstractWidget create(int x, int y, String s, Direction dir) {
        ToolTipButton result = new ToolTipButton(x, y, 10, 10, (Component)this.string(s), T -> this.sendDirection(dir)).setToolTip((Component)DirectionList.getName(dir));
        ((AbstractWidget)result).f_93623_ = this.data.getDir() == dir.m_122411_() && (this.data.getDirs() & 1 << dir.m_122411_()) != 0;
        return result;
    }

    private void sendEmit() {
        this.tile.sendToServer(3, 0);
    }

    private void sendDirection(Direction dir) {
        this.tile.sendToServer(0, dir.m_122411_());
    }

    private void sendWhiteList() {
        this.tile.sendToServer(2, this.data.isWhiteList() ? 0 : 1);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void sendPriority(int value) {
        value *= Screen.m_96637_() ? 10 : 1;
        value *= Screen.m_96638_() ? 100 : 1;
        this.tile.sendToServer(1, value *= Screen.m_96639_() ? 1000 : 1);
    }
}

