/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.network.PacketDistributor
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  top.theillusivec4.curios.api.CuriosApi
 *  top.theillusivec4.curios.api.type.capability.ICuriosItemHandler
 *  top.theillusivec4.curios.client.gui.RenderButton
 *  top.theillusivec4.curios.common.inventory.CosmeticCurioSlot
 *  top.theillusivec4.curios.common.inventory.CurioSlot
 *  top.theillusivec4.curios.common.network.NetworkHandler
 *  top.theillusivec4.curios.common.network.client.CPacketToggleRender
 */
package ic2.curioplugin.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.container.ContainerHasGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.base.OpenerButton;
import ic2.core.inventory.gui.components.simple.SliderComponent;
import ic2.core.inventory.gui.feature.ICloseableComponent;
import ic2.core.networking.PacketManager;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import ic2.curioplugin.core.packet.CurioScrollPacket;
import ic2.curioplugin.core.packet.ToggleCurioPacket;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableBoolean;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.client.gui.RenderButton;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.network.NetworkHandler;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;

public class CurioComponent
extends GuiWidget
implements ICloseableComponent {
    private static final ResourceLocation CURIO_INVENTORY = new ResourceLocation("curios", "textures/gui/inventory.png");
    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static final Box2i SLIDER = new Box2i(-34, 12, 12, 142);
    private static final Box2i SLIDER_COSMETIC = new Box2i(-53, 12, 12, 142);
    SliderComponent slider = this.addChild(new SliderComponent(new Box2i(-34, 12, 12, 142), new Box2i(232, 0, 12, 15))).setCustomTexture(CREATIVE_INVENTORY_TABS).setNonEmptyRows(8);
    ICuriosItemHandler handler;
    MutableBoolean visible = new MutableBoolean(false);
    Vec2i button;
    Vec2i pos;

    public CurioComponent(Player player, Vec2i pos, Vec2i button) {
        super(Box2i.EMPTY_BOX);
        this.pos = pos;
        this.button = button;
        this.handler = (ICuriosItemHandler)CuriosApi.getCuriosHelper().getCuriosHandler((LivingEntity)player).orElse(null);
        this.slider.setVisible(false);
        this.setVisible(false);
        this.slider.setListener(T -> PacketManager.INSTANCE.sendToServer(new CurioScrollPacket(T)));
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        if (this.handler == null) {
            return;
        }
        requests.add(GuiWidget.ActionRequest.GUI_INIT);
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.DRAW_POST);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    public MutableBoolean getVisible() {
        return this.visible;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void init(IC2Screen gui) {
        this.updateSlots();
        gui.addRenderableWidget(1100, new OpenerButton(gui.getGuiLeft() + 16 + this.button.getX(), gui.getGuiTop() + 16 + this.button.getY(), 10, 10, (Component)this.string("U"), T -> this.toggleVisibility(!this.isVisible())).setToolTip("gui.ic2.curio.button"));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void closeComponent(IC2Screen screen) {
        this.setVisible(false);
        this.slider.setVisible(false);
        this.visible.setFalse();
        PacketManager.INSTANCE.sendToServer(new ToggleCurioPacket(false));
    }

    @OnlyIn(value=Dist.CLIENT)
    private void toggleVisibility(boolean visible) {
        this.gui.closeAllComponents();
        this.setVisible(visible);
        this.slider.setVisible(visible && this.handler != null && this.handler.getVisibleSlots() > 8);
        this.visible.setValue(visible);
        PacketManager.INSTANCE.sendToServer(new ToggleCurioPacket(visible));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.slider.setMax(this.handler.getVisibleSlots());
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        this.gui.bindTexture(CURIO_INVENTORY);
        int slotCount = this.handler.getVisibleSlots();
        int upperHeight = 7 + Math.min(slotCount, 9) * 18;
        int xTexOffset = 0;
        int width = 27;
        int xOffset = -26;
        if (this.gui.getCastedContainer(ContainerHasGui.class).cosmeticCurio) {
            xTexOffset = 92;
            width = 46;
            xOffset -= 19;
            this.slider.box = SLIDER_COSMETIC;
        } else {
            this.slider.box = SLIDER;
        }
        this.gui.drawTextureRegion(matrix, x + xOffset, y + 4, xTexOffset, 0.0f, width, upperHeight);
        if (slotCount <= 8) {
            this.gui.drawTextureRegion(matrix, x + xOffset, y + 4 + upperHeight, xTexOffset, 151.0f, width, 7.0f);
        } else {
            this.gui.drawTextureRegion(matrix, x + xOffset - 16, y + 4, 27.0f, 0.0f, 23.0f, 158.0f);
        }
        this.gui.bindTexture(CURIO_INVENTORY);
        for (Slot slot : ((IC2Container)this.gui.m_6262_()).f_38839_) {
            if (!(slot instanceof CosmeticCurioSlot)) continue;
            this.gui.drawTextureRegion(matrix, x + slot.f_40220_ - 1, y + slot.f_40221_ - 1, 138.0f, 0.0f, 18.0f, 18.0f);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void postDraw(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        for (GuiEventListener button : this.gui.m_6702_()) {
            if (!(button instanceof RenderButton)) continue;
            RenderButton entry = (RenderButton)button;
            entry.renderButtonOverlay(matrix, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        for (GuiEventListener button : this.gui.m_6702_()) {
            RenderButton render;
            if (!(button instanceof RenderButton) || !(render = (RenderButton)button).m_5953_((double)mouseX, (double)mouseY)) continue;
            tooltips.accept((Component)this.translate("gui.curios.toggle"));
            return;
        }
        Slot slot = this.gui.getSlotUnderMouse();
        if (slot instanceof CurioSlot) {
            tooltips.accept((Component)this.string(((CurioSlot)slot).getSlotName()));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public void updateSlots() {
        this.gui.removeButtons(T -> T instanceof RenderButton);
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        for (Slot slot : ((IC2Container)this.gui.m_6262_()).f_38839_) {
            if (!(slot instanceof CurioSlot)) continue;
            CurioSlot curio = (CurioSlot)slot;
            if (slot instanceof CosmeticCurioSlot) continue;
            this.gui.m_142416_(new RenderButton(curio, x + slot.f_40220_ + 11, y + slot.f_40221_ - 3, 8, 8, 75, 0, 8, CURIO_INVENTORY, T -> this.onCurioButton(curio)));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void onCurioButton(CurioSlot slot) {
        NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), (Object)new CPacketToggleRender(slot.getIdentifier(), slot.getSlotIndex()));
    }
}

