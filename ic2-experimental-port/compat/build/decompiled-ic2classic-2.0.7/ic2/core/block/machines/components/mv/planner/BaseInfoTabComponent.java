/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BaseInfoTabComponent
extends GuiWidget {
    protected ReactorPlannerTileEntity tile;
    final Component component;
    final ItemStack stack;
    final int myIndex;

    public BaseInfoTabComponent(int y, ReactorPlannerTileEntity tile, Component component, ItemStack stack, int myIndex) {
        super(new Box2i(-28, y, 28, 28));
        this.tile = tile;
        this.component = component;
        this.stack = stack;
        this.myIndex = myIndex;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
    }

    public boolean isSelected() {
        return this.tile.selectedView == this.myIndex;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.drawSideTab(matrix, this.getBox().getX(), this.getBox().getY(), this.stack, this.isSelected());
    }

    @Override
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!this.isSelected()) {
            this.tile.sendToServer(6, this.myIndex);
            IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_12490_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    @Override
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (this.isMouseOver(mouseX, mouseY)) {
            tooltips.accept(this.component);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void drawSideTab(PoseStack matrix, int xOffset, int yOffset, ItemStack item, boolean enabled) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        ItemRenderer render = this.gui.getRenderItem();
        this.gui.drawTextureRegion(matrix, x + xOffset, y + yOffset, enabled ? 122.0f : 156.0f, 212.0f, enabled ? 31.0f : 28.0f, 28.0f);
        this.gui.setZLevel(200);
        render.f_115093_ = 200.0f;
        RenderUtils.renderGuiItem(render, matrix, item, x + xOffset + 8, y + yOffset + 6);
        this.gui.setZLevel(0);
        render.f_115093_ = 0.0f;
        this.gui.bindDefaultTexture();
    }
}

