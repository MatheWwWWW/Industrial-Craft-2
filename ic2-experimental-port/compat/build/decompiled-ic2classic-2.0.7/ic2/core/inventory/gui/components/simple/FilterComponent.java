/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.core.Registry
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.ITagItem;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.ComponentContainerScreen;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.OpenerButton;
import ic2.core.inventory.gui.components.simple.IFilterTarget;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.item.misc.FluidDisplay;
import ic2.core.item.misc.TagBlock;
import ic2.core.item.misc.TagItem;
import ic2.core.platform.recipes.helpers.ItemStackCache;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class FilterComponent
extends GuiWidget
implements ICloseableComponent {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/misc/filter_helper.png");
    private static final Component SELECTED = Component.m_237115_((String)"gui.ic2.inventory.selected").m_130940_(ChatFormatting.YELLOW);
    private static final Component CONFIGURE = Component.m_237115_((String)"gui.ic2.inventory.configure").m_130940_(ChatFormatting.YELLOW);
    final Component selecting_text;
    final boolean helper;
    IFilterTarget ghost;
    List<ItemStack> allValid = CollectionUtils.createList();
    List<String> allValidText = CollectionUtils.createList();
    List<ItemStack> displayed = CollectionUtils.createList();
    SliderComponent slider;
    String lastText = "";
    final Vec2i offsets;
    final Vec2i buttonOffset;
    boolean blockTags;
    boolean fluids;
    boolean blockClosing;

    public FilterComponent(Vec2i offsets) {
        super(new Box2i(-118 + offsets.getX(), offsets.getY(), 118, 132));
        this.offsets = offsets;
        this.helper = false;
        this.buttonOffset = null;
        this.selecting_text = this.translate("gui.ic2.filter.ghost");
        this.slider = this.addChild(new SliderComponent(new Box2i(-19 + offsets.getX(), 37 + offsets.getY(), 12, 88), new Box2i(0, 132, 12, 15), 5)).setNonEmptyRows(4).setCustomTexture(TEXTURE);
        this.setVisible(false);
        this.slider.setVisible(false);
    }

    public FilterComponent(Vec2i offsets, Vec2i buttonOffset) {
        super(new Box2i(-118 + offsets.getX(), offsets.getY(), 118, 132));
        this.offsets = offsets;
        this.buttonOffset = buttonOffset;
        this.helper = true;
        this.selecting_text = this.translate("gui.ic2.filter.normal");
        this.slider = this.addChild(new SliderComponent(new Box2i(-19 + offsets.getX(), 37 + offsets.getY(), 12, 88), new Box2i(0, 132, 12, 15), 5)).setNonEmptyRows(4).setCustomTexture(TEXTURE);
        this.setVisible(false);
        this.slider.setVisible(false);
    }

    public FilterComponent setBlockTags() {
        this.blockTags = true;
        return this;
    }

    public FilterComponent blockClosing() {
        this.blockClosing = true;
        return this;
    }

    public FilterComponent setFluid() {
        this.fluids = true;
        return this;
    }

    @Override
    public boolean isMouseOver(int x, int y) {
        return true;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void setGhost(IFilterTarget ghost) {
        if (this.ghost == ghost) {
            return;
        }
        this.displayed.clear();
        this.allValid.clear();
        this.slider.setMax(0);
        if (ghost == null) {
            this.ghost = null;
            return;
        }
        if (!this.helper) {
            this.toggleHelper(true);
        }
        this.ghost = ghost;
        if (ghost.allowEmptyStack()) {
            this.allValid.add(ItemStack.f_41583_);
            this.allValidText.add("empty");
        }
        if (this.fluids && ghost.isFluidSelection()) {
            for (Fluid fluid : ForgeRegistries.FLUIDS) {
                if (!fluid.m_7444_(fluid.m_76145_())) continue;
                ItemStack stack = FluidDisplay.createDisplay(fluid);
                this.allValid.add(stack);
                this.allValidText.add(this.getText(stack));
            }
        } else {
            int i;
            ObjectList tag = CollectionUtils.createList();
            ItemStack[] cache = ItemStackCache.INSTANCE.getRawCache();
            int m = ItemStackCache.INSTANCE.size();
            for (i = 0; i < m; ++i) {
                ItemStack stack = cache[i];
                if (!ghost.isValid(stack)) continue;
                if (stack.m_41720_() instanceof ITagItem) {
                    tag.add((ItemStack)stack);
                    continue;
                }
                if (stack.m_41720_() instanceof TagItem) continue;
                this.allValid.add(stack.m_41777_());
                this.allValidText.add(this.getText(stack));
            }
            Registry.f_122827_.m_203613_().forEach(T -> {
                ItemStack stack = new ItemStack((ItemLike)IC2Items.TAG_ITEM);
                TagItem.setTag(stack, T.f_203868_());
                if (ghost.isValid(stack)) {
                    this.allValid.add(stack);
                    this.allValidText.add(this.getText(stack));
                }
            });
            if (this.blockTags) {
                Registry.f_122824_.m_203613_().forEach(T -> {
                    ItemStack stack = new ItemStack((ItemLike)IC2Items.TAG_BLOCK);
                    TagBlock.setTag(stack, T.f_203868_());
                    if (ghost.isValid(stack)) {
                        this.allValid.add(stack);
                        this.allValidText.add(this.getText(stack));
                    }
                });
            }
            this.allValid.addAll((Collection<ItemStack>)tag);
            m = tag.size();
            for (i = 0; i < m; ++i) {
                this.allValidText.add(this.getText((ItemStack)tag.get(i)));
            }
        }
        String s = this.lastText;
        this.lastText = "THIS WILL NEVER MATCH";
        this.updateValues(s);
    }

    @OnlyIn(value=Dist.CLIENT)
    private String getText(ItemStack stack) {
        List components = stack.m_41651_((Player)this.gui.getPlayer(), (TooltipFlag)(this.gui.getMinecraft().f_91066_.f_92125_ ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL));
        StringBuilder builder = new StringBuilder();
        int m = components.size();
        for (int i = 0; i < m; ++i) {
            builder.append(ChatFormatting.m_126649_((String)((Component)components.get(i)).getString()).toLowerCase(Locale.ROOT));
        }
        return builder.toString();
    }

    public boolean isHelper() {
        return this.helper;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_FOREGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
        requests.add(GuiWidget.ActionRequest.KEY_INPUT);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        if (gui instanceof ComponentContainerScreen) {
            ((ComponentContainerScreen)gui).registerComponent(new ActivatorComponent(this));
        }
        EditBox text = gui.addRenderableWidget(this.helper ? 1001 : 1000, new EditBox(gui.getFont(), gui.getGuiLeft() - 110 + this.offsets.getX(), gui.getGuiTop() + 22 + this.offsets.getY(), 99, 9, (Component)this.string()));
        text.m_94199_(50);
        text.m_94182_(false);
        text.m_94190_(true);
        text.m_94194_(false);
        text.m_94151_(this::updateValues);
        if (this.helper) {
            gui.addRenderableWidget(1002, new OpenerButton(gui.getGuiLeft() + 5 + this.buttonOffset.getX(), gui.getGuiTop() + 16 + this.buttonOffset.getY(), 10, 10, (Component)this.string("S"), T -> this.toggleHelper(!this.isVisible())).setToolTip("gui.ic2.filter.button"));
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.gui.bindTexture(TEXTURE);
        int x = this.gui.getGuiLeft() - 118 + this.offsets.getX();
        int y = this.gui.getGuiTop() + this.offsets.getY();
        this.gui.drawTextureRegion(matrix, x, y, 0.0f, 0.0f, 118.0f, 132.0f);
        this.gui.drawCenterString(matrix, this.selecting_text, x + 57, y + 8, 0x404040);
        Lighting.m_84931_();
        int listOffset = this.slider.getCurrent();
        for (int i = 0; i < 25 && this.displayed.size() > i + listOffset; ++i) {
            int xPos = x + 6 + 18 * (i % 5);
            int yPos = y + 37 + 18 * (i / 5);
            this.gui.getRenderItem().m_115203_(this.displayed.get(i + listOffset), xPos, yPos);
            this.gui.getRenderItem().m_115174_(this.gui.getFont(), this.displayed.get(i + listOffset), xPos, yPos, "");
            if (mouseX + this.gui.getGuiLeft() < xPos || mouseX + this.gui.getGuiLeft() > xPos + 16 || mouseY + this.gui.getGuiTop() < yPos || mouseY + this.gui.getGuiTop() > yPos + 16) continue;
            RenderSystem.m_69465_();
            RenderSystem.m_69444_((boolean)true, (boolean)true, (boolean)true, (boolean)false);
            int slotColor = this.gui.getSlotColor(0);
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            this.gui.drawColoredRegion(matrix, xPos, yPos, 16.0f, 16.0f, slotColor);
            RenderSystem.m_69461_();
            RenderSystem.m_69444_((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            RenderSystem.m_69482_();
        }
        Lighting.m_84931_();
    }

    @Override
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        matrix.m_85837_(0.0, 0.0, 100.0);
        if (this.ghost != null) {
            this.gui.drawColoredRegion(matrix, this.ghost.getX(), this.ghost.getY(), this.ghost.getWidth(), this.ghost.getHeight(), -2130720768);
        }
        for (Slot slot : ((IC2Container)this.gui.m_6262_()).f_38839_) {
            IGhostSlot extra;
            if (!slot.m_6659_() || !(slot instanceof IGhostSlot) || (extra = (IGhostSlot)slot).getType() != (this.helper ? IGhostSlot.GhostType.NORMAL : IGhostSlot.GhostType.FILTER) || this.ghost != null && slot.f_40220_ - 2 == this.ghost.getX() && slot.f_40221_ - 2 == this.ghost.getY()) continue;
            this.gui.drawColoredRegion(matrix, slot.f_40220_ - 2, slot.f_40221_ - 2, 20.0f, 20.0f, -2145473563);
        }
        matrix.m_85837_(0.0, 0.0, -100.0);
        RenderSystem.m_69461_();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        int listOffset = this.slider.getCurrent();
        for (int i = 0; i < 25 && this.displayed.size() > i + listOffset; ++i) {
            int xPos = -113 + 18 * (i % 5) + this.offsets.getX();
            int yPos = 37 + 18 * (i / 5) + this.offsets.getY();
            if (mouseX < xPos || mouseX > xPos + 16 || mouseY < yPos || mouseY > yPos + 16) continue;
            for (Component comp : this.displayed.get(i + listOffset).m_41651_((Player)this.gui.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL)) {
                tooltips.accept(comp);
            }
        }
        for (Slot slot : ((IC2Container)this.gui.m_6262_()).f_38839_) {
            IGhostSlot extra;
            if (!slot.m_6659_() || !(slot instanceof IGhostSlot) || (extra = (IGhostSlot)slot).getType() != (this.helper ? IGhostSlot.GhostType.NORMAL : IGhostSlot.GhostType.FILTER) || slot.f_40220_ > mouseX || slot.f_40220_ + 20 < mouseX || slot.f_40221_ > mouseY || slot.f_40221_ + 20 < mouseY) continue;
            if (this.ghost != null && slot.f_40220_ - 2 == this.ghost.getX() && slot.f_40221_ - 2 == this.ghost.getY()) {
                tooltips.accept(SELECTED);
                continue;
            }
            tooltips.accept(CONFIGURE);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        IGhostSlot type;
        if (this.box.isInBox(mouseX, mouseY)) {
            if (this.ghost != null && !this.helper) {
                int listOffset = this.slider.getCurrent();
                for (int i = 0; i < 25 && this.displayed.size() > i + listOffset; ++i) {
                    int xPos = this.box.getX() + 6 + 18 * (i % 5);
                    int yPos = this.box.getY() + 37 + 18 * (i / 5);
                    if (mouseX < xPos || mouseX > xPos + 18 || mouseY < yPos || mouseY > yPos + 18) continue;
                    this.ghost.accept(this.displayed.get(i + listOffset));
                    this.toggleHelper(false);
                    return true;
                }
            }
            return false;
        }
        if (this.gui.isHoveringOpenerButton(mouseX, mouseY)) {
            return false;
        }
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot instanceof IGhostSlot && ((type = (IGhostSlot)slot).getType() == IGhostSlot.GhostType.FILTER && !slot.m_6657_() && ((IC2Container)this.gui.m_6262_()).m_142621_().m_41619_() || this.helper && type.getType() == IGhostSlot.GhostType.NORMAL)) {
            return false;
        }
        if (this.isVisible() && !this.helper) {
            this.toggleHelper(false);
            return true;
        }
        return this.isVisible();
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        if (this.slider.isMouseOver(mouseX, mouseY)) {
            return false;
        }
        if (this.box.isInBox(mouseX, mouseY)) {
            this.slider.onMouseScroll(mouseX, mouseY, scroll);
        }
        return false;
    }

    public void updateValues(String text) {
        if ((text = text.toLowerCase(Locale.ROOT)).equalsIgnoreCase(this.lastText)) {
            return;
        }
        this.lastText = text;
        this.displayed.clear();
        int m = this.allValid.size();
        for (int i = 0; i < m; ++i) {
            if (!this.allValidText.get(i).contains(text)) continue;
            this.displayed.add(this.allValid.get(i));
        }
        this.slider.setMax(this.displayed.size());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onKeyTyped(int keyCode) {
        if (this.isVisible()) {
            if (keyCode == 69 && this.gui.getButton(this.helper ? 1001 : 1000).m_93696_()) {
                return true;
            }
            if (keyCode == 256) {
                this.setVisible(false);
                this.slider.setVisible(false);
                this.gui.getButton((int)(this.helper ? 1001 : 1000)).f_93624_ = false;
                return true;
            }
        }
        return super.onKeyTyped(keyCode);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void toggleHelper(boolean value) {
        if (!this.blockClosing) {
            this.gui.closeAllComponents();
        } else if (!value) {
            this.setGhost(null);
        }
        this.setVisible(value);
        this.slider.setVisible(value);
        this.gui.getButton((int)(this.helper ? 1001 : 1000)).f_93624_ = value;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void closeComponent(IC2Screen screen) {
        this.setVisible(false);
        this.slider.setVisible(false);
        this.gui.getButton((int)(this.helper ? 1001 : 1000)).f_93624_ = false;
        this.setGhost(null);
    }

    public static class ActivatorComponent
    extends GuiWidget {
        FilterComponent comp;

        public ActivatorComponent(FilterComponent comp) {
            super(Box2i.EMPTY_BOX);
            this.comp = comp;
        }

        @Override
        public boolean isMouseOver(int x, int y) {
            return !this.comp.helper || this.comp.isVisible();
        }

        @Override
        protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
            requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        }

        @Override
        @OnlyIn(value=Dist.CLIENT)
        public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
            IGhostSlot type;
            Slot slot = this.gui.getSlotUnderMouse();
            if (slot instanceof IGhostSlot && ((type = (IGhostSlot)slot).getType() == IGhostSlot.GhostType.FILTER && !slot.m_6657_() && ((IC2Container)this.gui.m_6262_()).m_142621_().m_41619_() || this.comp.helper && type.getType() == IGhostSlot.GhostType.NORMAL)) {
                this.comp.setGhost(IFilterTarget.ghost((IGhostSlot)slot));
                return true;
            }
            return super.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }
}

