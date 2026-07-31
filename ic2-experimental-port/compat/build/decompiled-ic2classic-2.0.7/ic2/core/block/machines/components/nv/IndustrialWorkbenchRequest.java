/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package ic2.core.block.machines.components.nv;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.components.mv.planner.NamingTabComponent;
import ic2.core.block.machines.containers.nv.IndustrialWorkbenchContainer;
import ic2.core.block.machines.tiles.nv.IndustrialWorkbenchTileEntity;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.ColorButton;
import ic2.core.inventory.gui.components.base.ImprovedTextWidget;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.platform.player.KeyHelper;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import java.lang.invoke.LambdaMetafactory;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class IndustrialWorkbenchRequest
extends GuiWidget
implements ICloseableComponent {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_work_table_request_expansion.png");
    IndustrialWorkbenchTileEntity tile;
    MutableBoolean viewOpen;
    DyeColor lastColor;

    public IndustrialWorkbenchRequest(IndustrialWorkbenchTileEntity tile, MutableBoolean viewOpen) {
        super(new Box2i(-118, 0, 118, 83));
        this.tile = tile;
        this.viewOpen = viewOpen;
    }

    @Override
    public void closeComponent(IC2Screen screen) {
        this.viewOpen.setFalse();
        this.gui.getButton((int)8).f_93624_ = this.viewOpen.booleanValue();
        this.gui.getButton((int)9).f_93624_ = this.viewOpen.booleanValue();
        this.gui.getButton((int)10).f_93624_ = this.viewOpen.booleanValue();
        this.gui.getButton((int)11).f_93624_ = this.viewOpen.booleanValue();
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.ITEM_TOOLTIP);
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot instanceof IndustrialWorkbenchContainer.RequestSlot && slot.m_6657_() && Screen.m_96638_()) {
            this.tile.sendToServer(5, slot.getSlotIndex());
            return true;
        }
        return false;
    }

    @Override
    public boolean isVisible() {
        return this.viewOpen.isTrue();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int x = gui.getGuiLeft();
        int y = gui.getGuiTop();
        gui.addRenderableWidget(7, new ItemCheckBox(x + 8, y + 17, 16, 16, T -> this.toggleVisible(), new ItemStack((ItemLike)IC2Blocks.REQUEST_TUBE), false));
        ImprovedTextWidget widget = gui.addRenderableWidget(8, new ImprovedTextWidget(x - 79, y + 15, 32, 14));
        widget.f_93624_ = this.viewOpen.isTrue();
        widget.m_94199_(3);
        widget.m_94153_(NamingTabComponent.NUMBERS_ONLY);
        widget.m_94144_("0");
        widget.m_94182_(false);
        widget.m_94190_(true);
        gui.addRenderableWidget((int)9, new ToolTipButton((int)(x - 43), (int)(y + 13), (int)16, (int)12, (Component)this.string((String)"+"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$1(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((IndustrialWorkbenchRequest)this))).f_93624_ = this.viewOpen.isTrue();
        (new Object[1])[0] = this.tile.requestColor == null ? this.translate("gui.ic2.none") : this.tile.requestColor.m_41065_();
        gui.addRenderableWidget((int)10, new ColorButton((int)(x - 25), (int)(y + 23), (int)16, (int)16, (DyeColor)this.tile.requestColor, (Consumer<ColorButton>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, toggleColor(ic2.core.inventory.gui.components.base.ColorButton ), (Lic2/core/inventory/gui/components/base/ColorButton;)V)((IndustrialWorkbenchRequest)this))).setToolTip((String)"gui.ic2.industrial_workbench.request_color", (Object[])new Object[1]).f_93624_ = this.viewOpen.isTrue();
        gui.addRenderableWidget((int)11, new ToolTipButton((int)(x - 81), (int)(y + 27), (int)54, (int)12, (Component)this.translate((String)"gui.ic2.industrial_workbench.request"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, lambda$init$2(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((IndustrialWorkbenchRequest)this))).f_93624_ = this.viewOpen.isTrue();
    }

    @Override
    public void addItemTooltip(Slot slot, ItemStack stack, List<Component> tooltips) {
        if (slot instanceof IndustrialWorkbenchContainer.RequestSlot) {
            int state = StackUtil.getNbtData(stack).m_128451_("state");
            switch (state) {
                case 0: {
                    tooltips.add((Component)this.translate("gui.ic2.industrial_workbench.requesting", ChatFormatting.GRAY));
                    break;
                }
                case 1: {
                    tooltips.add((Component)this.translate("gui.ic2.industrial_workbench.requested", ChatFormatting.GRAY));
                    break;
                }
                case 2: {
                    tooltips.add((Component)this.translate("gui.ic2.industrial_workbench.awaiting", ChatFormatting.GRAY, new Object[]{StackUtil.getNbtData(stack).m_128451_("awaiting")}));
                }
            }
            tooltips.add((Component)this.buildKeyDescription(KeyHelper.SNEAK_KEY, this.translate("gui.ic2.industrial_workbench.request_remove").m_130940_(ChatFormatting.UNDERLINE)));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void toggleVisible() {
        boolean last = !this.viewOpen.booleanValue();
        this.gui.closeAllComponents();
        this.viewOpen.setValue(last);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void sendRequest() {
        int value = this.getValue();
        if (value <= 0 || this.tile.requestedItem.getStackInSlot(0).m_41619_()) {
            return;
        }
        this.tile.sendToServer(4, value);
    }

    @OnlyIn(value=Dist.CLIENT)
    private void addAmount() {
        int value = (Screen.m_96637_() ? 2 : 1) * (Screen.m_96638_() ? 16 : 1) * (Screen.m_96639_() ? 64 : 1);
        this.gui.getCastedButton(8, ImprovedTextWidget.class).m_94144_(Integer.toString(Mth.m_14045_((int)(value + this.getValue()), (int)0, (int)512)));
    }

    @OnlyIn(value=Dist.CLIENT)
    private void toggleColor(ColorButton button) {
        this.handleColorClick(button.getColor(), (AbstractWidget)button, true, T -> {
            this.tile.sendToServer(3, T == null ? 0 : T.m_41060_() + 1);
            this.tile.requestColor = T;
        });
    }

    private int getValue() {
        try {
            return Integer.parseInt(this.gui.getCastedButton(8, ImprovedTextWidget.class).m_94155_());
        }
        catch (Exception exception) {
            return 0;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        gui.getCastedButton(7, ItemCheckBox.class).setChecked(this.viewOpen.booleanValue());
        int value = this.getValue();
        boolean bl = gui.getButton((int)11).f_93623_ = value > 0 && value <= 512 && !this.tile.requestedItem.getStackInSlot(0).m_41619_();
        if (this.tile.requestColor != this.lastColor) {
            this.lastColor = this.tile.requestColor;
            gui.getCastedButton(10, ColorButton.class).setColor(this.tile.requestColor).setToolTip("gui.ic2.industrial_workbench.request_color", this.tile.requestColor == null ? this.translate("gui.ic2.none") : this.tile.requestColor.m_41065_());
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.gui.getButton((int)8).f_93624_ = this.viewOpen.booleanValue();
        this.gui.getButton((int)9).f_93624_ = this.viewOpen.booleanValue();
        this.gui.getButton((int)10).f_93624_ = this.viewOpen.booleanValue();
        this.gui.getButton((int)11).f_93624_ = this.viewOpen.booleanValue();
        if (this.viewOpen.isFalse()) {
            return;
        }
        this.gui.bindTexture(TEXTURE);
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        this.gui.drawTextureRegion(matrix, x - 118, y, 0.0f, 0.0f, 118.0f, 83.0f);
    }

    private /* synthetic */ void lambda$init$2(Button T) {
        this.sendRequest();
    }

    private /* synthetic */ void lambda$init$1(Button T) {
        this.addAmount();
    }
}

