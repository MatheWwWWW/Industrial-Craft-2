/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class ComponentContainerScreen
extends IC2Screen {
    List<GuiWidget>[] components = CollectionUtils.createLists(GuiWidget.ActionRequest.values().length);
    List<GuiWidget> allComponents = CollectionUtils.createList();
    List<ICloseableComponent> closeable = CollectionUtils.createList();

    public ComponentContainerScreen(ContainerComponent<?> container) {
        super(container, container.getPlayer().m_150109_(), container.getName());
        this.setTexture(container.getTexture());
        this.registerComponents(container.getComponents());
        container.onGuiLoaded(this);
    }

    public void registerComponents(List<GuiWidget> comps) {
        for (GuiWidget comp : comps) {
            this.registerComponent(comp);
        }
    }

    public void registerComponent(GuiWidget comp) {
        comp.setOwner(this);
        for (GuiWidget.ActionRequest event : comp.createListeners()) {
            this.components[event.getIndex()].add(comp);
        }
        this.allComponents.add(comp);
        if (comp instanceof ICloseableComponent) {
            this.closeable.add((ICloseableComponent)((Object)comp));
        }
        for (GuiWidget child : comp.children) {
            this.registerComponent(child);
        }
    }

    public List<GuiWidget> getComponents(GuiWidget.ActionRequest event) {
        return this.components[event.getIndex()];
    }

    public List<Component> m_96555_(ItemStack stack) {
        List comp = super.m_96555_(stack);
        Slot slot = this.getSlotUnderMouse();
        for (GuiWidget entry : this.getComponents(GuiWidget.ActionRequest.ITEM_TOOLTIP)) {
            entry.addItemTooltip(slot, stack, comp);
        }
        return comp;
    }

    public List<GuiWidget> getComponents() {
        return this.allComponents;
    }

    public <T extends GuiWidget> T getComponentFromClass(Class<T> clz) {
        for (GuiWidget comp : this.allComponents) {
            if (!clz.isInstance(comp)) continue;
            return (T)comp;
        }
        return null;
    }

    @Override
    public void closeAllComponents() {
        int m = this.closeable.size();
        for (int i = 0; i < m; ++i) {
            this.closeable.get(i).closeComponent(this);
        }
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.GUI_INIT)) {
            comp.init(this);
        }
    }

    @Override
    public void m_7861_() {
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.GUI_CLOSE)) {
            comp.onClose();
        }
        super.m_7861_();
    }

    @Override
    protected void m_7286_(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        if (this.hasFlag(64)) {
            this.clearFlag(64);
            this.setTexture(this.getCastedContainer(ContainerComponent.class).getTexture());
        }
        super.m_7286_(stack, partialTicks, mouseX, mouseY);
        int x = mouseX - this.getGuiLeft();
        int y = mouseY - this.getGuiTop();
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.DRAW_BACKGROUND)) {
            if (!comp.isEnabled() || !comp.isVisible()) continue;
            this.bindDefaultTexture();
            comp.drawBackground(stack, x, y, partialTicks);
        }
    }

    @Override
    protected void m_7027_(PoseStack stack, int mouseX, int mouseY) {
        super.m_7027_(stack, mouseX, mouseY);
        int x = mouseX - this.getGuiLeft();
        int y = mouseY - this.getGuiTop();
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.DRAW_FOREGROUND)) {
            if (!comp.isEnabled() || !comp.isVisible()) continue;
            comp.drawForeground(stack, x, y);
        }
    }

    @Override
    public void onPostDraw(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        int x = mouseX - this.getGuiLeft();
        int y = mouseY - this.getGuiTop();
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.DRAW_POST)) {
            if (!comp.isEnabled() || !comp.isVisible()) continue;
            comp.postDraw(stack, x, y, partialTicks);
        }
    }

    @Override
    public void onTooltipCollection(PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        int x = mouseX - this.getGuiLeft();
        int y = mouseY - this.getGuiTop();
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.TOOLTIP)) {
            if (!comp.isEnabled() || !comp.isVisible()) continue;
            comp.addTooltips(stack, x, y, tooltips);
        }
    }

    @Override
    public void m_181908_() {
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.GUI_TICK)) {
            if (!comp.isEnabled()) continue;
            comp.tick(this);
        }
        super.m_181908_();
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
        int x = (int)(mouseX - (double)this.getGuiLeft());
        int y = (int)(mouseY - (double)this.getGuiTop());
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.MOUSE_INPUT)) {
            if (!comp.isEnabled() || !comp.isVisible() || !comp.isMouseOver(x, y) || !comp.onMouseClick(x, y, mouseButton)) continue;
            return true;
        }
        return super.m_6375_(mouseX, mouseY, mouseButton);
    }

    public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double motionX, double motionY) {
        int x = (int)(mouseX - (double)this.getGuiLeft());
        int y = (int)(mouseY - (double)this.getGuiTop());
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.MOUSE_INPUT)) {
            if (!comp.isEnabled() || !comp.isVisible() || !comp.onMouseDragged(x, y, mouseButton)) continue;
            return true;
        }
        return super.m_7979_(mouseX, mouseY, mouseButton, motionX, motionY);
    }

    @Override
    public boolean m_6348_(double mouseX, double mouseY, int mouseButton) {
        int x = (int)(mouseX - (double)this.getGuiLeft());
        int y = (int)(mouseY - (double)this.getGuiTop());
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.MOUSE_INPUT)) {
            if (!comp.isEnabled() || !comp.isVisible() || !comp.onMouseRelease(x, y, mouseButton)) continue;
            return true;
        }
        return super.m_6348_(mouseX, mouseY, mouseButton);
    }

    public boolean m_6050_(double mouseX, double mouseY, double scroll) {
        int x = (int)(mouseX - (double)this.getGuiLeft());
        int y = (int)(mouseY - (double)this.getGuiTop());
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.MOUSE_SCROLL)) {
            if (!comp.isEnabled() || !comp.isVisible() || !comp.isMouseOver(x, y) || !comp.onMouseScroll(x, y, (int)scroll)) continue;
            return true;
        }
        return super.m_6050_(mouseX, mouseY, scroll);
    }

    public boolean m_7933_(int key, int p_keyPressed_2_, int p_keyPressed_3_) {
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.KEY_INPUT)) {
            if (!comp.isEnabled() || !comp.onKeyTyped(key)) continue;
            return true;
        }
        return super.m_7933_(key, p_keyPressed_2_, p_keyPressed_3_);
    }

    public boolean m_5534_(char letter, int letterCode) {
        for (GuiWidget comp : this.getComponents(GuiWidget.ActionRequest.KEY_INPUT)) {
            if (!comp.isEnabled() || !comp.onCharTyped(letter, letterCode)) continue;
            return true;
        }
        return super.m_5534_(letter, letterCode);
    }
}

