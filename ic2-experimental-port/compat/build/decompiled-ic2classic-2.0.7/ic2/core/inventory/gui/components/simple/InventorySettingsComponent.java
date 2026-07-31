/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.IconCheckBox;
import ic2.core.inventory.gui.components.base.ItemCheckBox;
import ic2.core.inventory.gui.components.base.OpenerButton;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InventorySettingsComponent
extends GuiWidget
implements ICloseableComponent {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/misc/inventory_helper.png");
    public static final List<Component> RULE_BUTTONS = ObjectArrayList.wrap((Object[])new Component[]{Component.m_237113_((String)"D"), Component.m_237113_((String)"I"), Component.m_237113_((String)"E"), Component.m_237113_((String)"B")});
    private static final Component SELECTED = Component.m_237115_((String)"gui.ic2.inventory.selected").m_130940_(ChatFormatting.YELLOW);
    private static final Component CONFIGURE = Component.m_237115_((String)"gui.ic2.inventory.configure").m_130940_(ChatFormatting.YELLOW);
    InventoryHandler handler;
    int selectedSlot = -1;
    int realSlot = -1;
    Vec2i offset;
    Vec2i buttonOffset;
    IntList ourSlots = new IntArrayList();

    public InventorySettingsComponent(InventoryHandler handler, Vec2i offset, Vec2i buttonOffset) {
        super(new Box2i(-100 + offset.getX(), offset.getY(), 100, 113));
        this.handler = handler;
        this.offset = offset;
        this.buttonOffset = buttonOffset;
        this.setVisible(false);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.GUI_TICK);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
    }

    public InventoryHandler getHandler() {
        return this.handler;
    }

    public Vec2i getOffset() {
        return this.offset;
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return true;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        int xOff = gui.getGuiLeft();
        int yOff = gui.getGuiTop();
        this.ourSlots.clear();
        for (int i = 0; i < 6; ++i) {
            int x = xOff - 95 + 15 * i + this.offset.getX();
            int y = yOff + 30 + this.offset.getY();
            gui.addRenderableWidget(100 + i, new IconCheckBox(x, y, 14, 14, (Component)this.string(), true).setListener(new PressHandler(this, i, true)));
            gui.addRenderableWidget(110 + i, new ToolTipButton(x, y + 15, 14, 14, (Component)this.string("B"), new PressHandler(this, i, false)));
            this.ourSlots.add(100 + i);
            this.ourSlots.add(110 + i);
        }
        this.ourSlots.add(120);
        this.ourSlots.add(121);
        this.ourSlots.add(122);
        gui.addRenderableWidget(120, new ToolTipButton(xOff - 95 + this.offset.getX(), yOff + 60 + this.offset.getY(), 89, 14, (Component)this.translate("gui.ic2.inventory.reset"), new ResetHandler(this)));
        gui.addRenderableWidget(121, new ItemCheckBox(xOff - 95 + this.offset.getX(), yOff + 75 + this.offset.getY(), 14, 14, new FlagHandler(this, 1), IC2Items.ICON_DISPLAY.create(6), this.handler.isSingleItem())).setToolTip("1 Item per Slot");
        gui.addRenderableWidget(122, new ItemCheckBox(xOff - 80 + this.offset.getX(), yOff + 75 + this.offset.getY(), 14, 14, new FlagHandler(this, 2), IC2Items.ICON_DISPLAY.create(7), this.handler.isSingleStack())).setToolTip("1 Stack per Item");
        gui.addRenderableWidget(1100, new OpenerButton(xOff + 5 + this.buttonOffset.getX(), yOff + 5 + this.buttonOffset.getY(), 10, 10, (Component)this.string("I"), new ActivateHandler(this, gui)).setToolTip("gui.ic2.inventory.button"));
        this.tick(gui);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void closeComponent(IC2Screen screen) {
        this.setVisible(false);
        this.selectedSlot = -1;
        this.realSlot = -1;
        this.tick(screen);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen gui) {
        boolean notLocked = !this.handler.isLocked();
        for (int i = 0; i < 6; ++i) {
            AbstractWidget widget = gui.getButton(100 + i);
            widget.f_93623_ = this.isVisible() && notLocked;
            widget.f_93624_ = this.isVisible();
            widget = gui.getButton(110 + i);
            widget.f_93623_ = this.isVisible() && notLocked;
            widget.f_93624_ = this.isVisible();
        }
        AbstractWidget widget = gui.getButton(120);
        widget.f_93623_ = this.isVisible() && notLocked;
        widget.f_93624_ = this.isVisible();
        ItemCheckBox check = gui.getCastedButton(121, ItemCheckBox.class);
        check.f_93623_ = this.isVisible() && notLocked;
        check.f_93624_ = this.isVisible();
        check.setChecked(this.handler.isSingleItem());
        check = gui.getCastedButton(122, ItemCheckBox.class);
        check.f_93623_ = this.isVisible() && notLocked;
        check.f_93624_ = this.isVisible();
        check.setChecked(this.handler.isSingleStack());
        if (!this.isVisible()) {
            return;
        }
        if (this.selectedSlot != -1) {
            this.loadData(this.realSlot);
        } else {
            for (Direction dir : DirectionList.ALL) {
                gui.getCastedButton(110 + dir.m_122411_(), ToolTipButton.class).setToolTip("gui.ic2.inventory.access.rule", DirectionList.getName(dir), this.handler.getBlockAccess(dir).getName()).m_93666_(RULE_BUTTONS.get(this.handler.getBlockAccess(dir).getIndex()));
                gui.getCastedButton(100 + dir.m_122411_(), IconCheckBox.class).setToolTip(this.handler.isBlockSideUnlocked(dir) ? "gui.ic2.inventory.access.enabled" : "gui.ic2.inventory.access.disabled", DirectionList.getName(dir)).setChecked(this.handler.isBlockSideUnlocked(dir));
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        Slot slot;
        if (!this.isVisible()) {
            return;
        }
        this.gui.bindTexture(TEXTURE);
        this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() - 100 + this.offset.getX(), this.gui.getGuiTop() + this.offset.getY(), 0.0f, 0.0f, 100.0f, 113.0f);
        RenderSystem.m_69478_();
        AbstractContainerMenu menu = this.gui.getCastedContainer(AbstractContainerMenu.class);
        if (this.selectedSlot != -1 && (slot = menu.m_38853_(this.selectedSlot)) != null) {
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + slot.f_40220_ - 2, this.gui.getGuiTop() + slot.f_40221_ - 2, 20.0f, 20.0f, -2130720768);
        }
        IntListIterator intListIterator = this.handler.getAllSlots().iterator();
        while (intListIterator.hasNext()) {
            Slot data;
            int slot2 = (Integer)intListIterator.next();
            if (slot2 == this.selectedSlot || (data = menu.m_38853_(slot2)) == null) continue;
            this.gui.drawColoredRegion(matrix, this.gui.getGuiLeft() + data.f_40220_ - 2, this.gui.getGuiTop() + data.f_40221_ - 2, 20.0f, 20.0f, -2145473563);
        }
        RenderSystem.m_69461_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        AbstractContainerMenu menu = this.gui.getCastedContainer(AbstractContainerMenu.class);
        IntListIterator intListIterator = this.handler.getAllSlots().iterator();
        while (intListIterator.hasNext()) {
            int slot = (Integer)intListIterator.next();
            Slot data = menu.m_38853_(slot);
            if (data == null || data.f_40220_ > mouseX || data.f_40220_ + 20 < mouseX || data.f_40221_ > mouseY || data.f_40221_ + 20 < mouseY) continue;
            SlotType type = this.handler.getSlotType(slot);
            if (type != null) {
                tooltips.accept((Component)type.getName().m_6881_().m_130940_(ChatFormatting.YELLOW));
            }
            if (slot == this.selectedSlot) {
                tooltips.accept(SELECTED);
                continue;
            }
            tooltips.accept(CONFIGURE);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        if (!this.isVisible()) {
            return;
        }
        MutableComponent text = this.selectedSlot == -1 ? this.translate("gui.ic2.inventory.machine") : this.handler.getSlotType(this.realSlot).getName();
        this.gui.drawCenterString(matrix, (Component)text, -50 + this.offset.getX(), 7 + this.offset.getY(), 0x404040);
        String s = "DUNSWE";
        for (int i = 0; i < s.length(); ++i) {
            this.gui.drawString(matrix, (Component)this.string(s.substring(i, i + 1)), -90 + i * 15 + this.offset.getX(), 20 + this.offset.getY(), 0x404040);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!this.isVisible()) {
            return false;
        }
        IntListIterator intListIterator = this.ourSlots.iterator();
        while (intListIterator.hasNext()) {
            int id = (Integer)intListIterator.next();
            AbstractWidget widget = this.gui.getButton(id);
            if (widget == null || !widget.m_198029_()) continue;
            return false;
        }
        if (this.gui.isHoveringOpenerButton(mouseX, mouseY)) {
            return false;
        }
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot != null && this.handler.containsSlot(slot)) {
            this.loadData(slot.getSlotIndex());
            this.realSlot = slot.getSlotIndex();
            this.selectedSlot = slot.f_40219_;
            return true;
        }
        if (slot == null) {
            this.realSlot = -1;
            this.selectedSlot = -1;
            return true;
        }
        return !(slot instanceof ContainerComponent.SettingsSlot) && (!(slot.f_40218_ instanceof InventoryMenu) || !SpecialFilters.HANDLER_ITEM.matches(slot.m_7993_()));
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void loadData(int slot) {
        for (Direction dir : DirectionList.ALL) {
            this.gui.getCastedButton(110 + dir.m_122411_(), ToolTipButton.class).setToolTip("gui.ic2.inventory.access.rule", DirectionList.getName(dir), this.handler.getSlotAccess(slot, dir).getName()).m_93666_(RULE_BUTTONS.get(this.handler.getSlotAccess(slot, dir).getIndex()));
            this.gui.getCastedButton(100 + dir.m_122411_(), IconCheckBox.class).setToolTip(this.handler.isBlockSideUnlocked(dir) ? "gui.ic2.inventory.access.enabled" : "gui.ic2.inventory.access.disabled", DirectionList.getName(dir)).setChecked(this.handler.isSlotSideUnlocked(slot, dir));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class PressHandler
    implements Button.OnPress {
        InventorySettingsComponent settings;
        int index;
        boolean side;

        public PressHandler(InventorySettingsComponent settings, int index, boolean side) {
            this.settings = settings;
            this.index = index;
            this.side = side;
        }

        @OnlyIn(value=Dist.CLIENT)
        public void m_93750_(Button p_onPress_1_) {
            IHasInventory inventory = this.settings.handler.getInventory();
            if (!(inventory instanceof BlockEntity)) {
                return;
            }
            IC2.NETWORKING.get(false).sendClientTileEvent((BlockEntity)inventory, Short.MAX_VALUE + this.settings.realSlot + 1, this.index << 1 | (this.side ? 1 : 0));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class ResetHandler
    implements Button.OnPress {
        InventorySettingsComponent settings;

        public ResetHandler(InventorySettingsComponent settings) {
            this.settings = settings;
        }

        @OnlyIn(value=Dist.CLIENT)
        public void m_93750_(Button p_onPress_1_) {
            IHasInventory inv = this.settings.handler.getInventory();
            if (!(inv instanceof BlockEntity)) {
                return;
            }
            IC2.NETWORKING.get(false).sendClientTileEvent((BlockEntity)inv, Short.MAX_VALUE + inv.getSlotCount() + 3, this.settings.realSlot);
        }
    }

    public static class FlagHandler
    implements Button.OnPress {
        InventorySettingsComponent settings;
        int subFlag;

        public FlagHandler(InventorySettingsComponent settings, int subFlag) {
            this.settings = settings;
            this.subFlag = subFlag;
        }

        public void m_93750_(Button p_onPress_1_) {
            IHasInventory inv = this.settings.handler.getInventory();
            if (!(inv instanceof BlockEntity)) {
                return;
            }
            IC2.NETWORKING.get(false).sendClientTileEvent((BlockEntity)inv, Short.MAX_VALUE + inv.getSlotCount() + this.subFlag, this.getInvertedValue());
        }

        private int getInvertedValue() {
            if (this.subFlag == 1) {
                return !this.settings.handler.isSingleItem() ? 1 : 0;
            }
            if (this.subFlag == 2) {
                return !this.settings.handler.isSingleStack() ? 1 : 0;
            }
            return 0;
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class ActivateHandler
    implements Button.OnPress {
        InventorySettingsComponent settings;
        IC2Screen screen;

        public ActivateHandler(InventorySettingsComponent settings, IC2Screen screen) {
            this.settings = settings;
            this.screen = screen;
        }

        @OnlyIn(value=Dist.CLIENT)
        public void m_93750_(Button p_onPress_1_) {
            boolean visible = !this.settings.isVisible();
            this.screen.closeAllComponents();
            this.settings.setVisible(visible);
            this.settings.selectedSlot = -1;
            this.settings.realSlot = -1;
            this.settings.tick(this.screen);
        }
    }
}

