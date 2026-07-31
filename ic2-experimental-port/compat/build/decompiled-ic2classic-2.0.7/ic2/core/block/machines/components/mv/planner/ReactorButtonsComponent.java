/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.machines.components.mv.planner;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ReactorButtonsComponent
extends GuiWidget {
    public static final Box2i REACTOR_BOX = new Box2i(-28, 17, 31, 28);
    public static final Box2i STEAM_BOX = new Box2i(-28, 47, 31, 28);
    ItemStack reactor;
    ItemStack steam_reactor;
    ReactorPlannerTileEntity tile;

    public ReactorButtonsComponent(ReactorPlannerTileEntity tile) {
        super(Box2i.EMPTY_BOX);
        this.tile = tile;
        this.reactor = new ItemStack((ItemLike)IC2Blocks.NUCLEAR_REACTOR);
        this.steam_reactor = new ItemStack((ItemLike)IC2Blocks.STEAM_REACTOR);
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.TOOLTIP);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.drawSideTab(matrix, REACTOR_BOX.getX(), REACTOR_BOX.getY(), this.reactor, !this.tile.isSteamReactor());
        this.drawSideTab(matrix, STEAM_BOX.getX(), STEAM_BOX.getY(), this.steam_reactor, this.tile.isSteamReactor());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return false;
        }
        if (REACTOR_BOX.isInBox(mouseX, mouseY) && this.tile.isSteamReactor()) {
            this.tile.sendToServer(5, 0);
            IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_12490_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 1.0f);
            return true;
        }
        if (STEAM_BOX.isInBox(mouseX, mouseY) && !this.tile.isSteamReactor()) {
            this.tile.sendToServer(5, 1);
            IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_12490_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (REACTOR_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.reactor_planner.tabs.reactor"));
        }
        if (STEAM_BOX.isInBox(mouseX, mouseY)) {
            tooltips.accept((Component)this.translate("gui.ic2.reactor_planner.tabs.steam_reactor"));
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void drawSideTab(PoseStack matrix, int xOffset, int yOffset, ItemStack item, boolean enabled) {
        int x = this.gui.getGuiLeft();
        int y = this.gui.getGuiTop();
        ItemRenderer render = this.gui.getRenderItem();
        this.gui.drawTextureRegion(matrix, x + xOffset, y + yOffset, enabled ? 122.0f : 156.0f, 212.0f, enabled ? 31.0f : 28.0f, 28.0f);
        Lighting.m_84931_();
        this.gui.setZLevel(200);
        render.f_115093_ = 200.0f;
        RenderUtils.renderGuiItem(render, matrix, item, x + xOffset + 8, y + yOffset + 6);
        this.gui.setZLevel(0);
        render.f_115093_ = 0.0f;
        this.gui.bindDefaultTexture();
    }
}

