/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.item.renders.features;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.items.armor.IArmorHud;
import ic2.api.items.electric.ICustomElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.core.IC2;
import ic2.core.platform.rendering.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyHudRenderer {
    public static final EnergyHudRenderer INSTANCE = new EnergyHudRenderer();

    @OnlyIn(value=Dist.CLIENT)
    public void renderOverlay(PoseStack stack, int width, int height) {
        IArmorHud hud;
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ == null) {
            return;
        }
        ItemStack head = mc.f_91074_.m_6844_(EquipmentSlot.HEAD);
        if (head.m_41720_() instanceof IArmorHud && (hud = (IArmorHud)head.m_41720_()).isHudEnabled(head)) {
            stack.m_85837_(0.0, 0.0, 1.0);
            int x = this.calcXBase(width);
            int y = this.calcYBase(height);
            Lighting.m_84930_();
            ItemStack chest = mc.f_91074_.m_6844_(EquipmentSlot.CHEST);
            ItemStack legs = mc.f_91074_.m_6844_(EquipmentSlot.LEGS);
            ItemStack feet = mc.f_91074_.m_6844_(EquipmentSlot.FEET);
            this.renderItemStack(mc, stack, head, 0, x, y);
            this.renderItemStack(mc, stack, chest, 1, x, y);
            this.renderItemStack(mc, stack, legs, 2, x, y);
            this.renderItemStack(mc, stack, feet, 3, x, y);
            stack.m_85837_(0.0, 0.0, -1.0);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    private void renderItemStack(Minecraft mc, PoseStack matrix, ItemStack stack, int slotNum, int xCenter, int yCenter) {
        if (stack.m_41720_() instanceof IElectricItem || stack.m_41720_() instanceof ICustomElectricItem) {
            boolean vertical = IC2.CONFIG.hudOrientation.get() == RenderOrientation.VERTICAL;
            int x = xCenter + (vertical ? 0 : 16 * slotNum);
            int y = yCenter + (vertical ? 16 * slotNum : 0);
            RenderUtils.renderGuiItem(mc.m_91291_(), matrix, stack, x, y);
            RenderUtils.renderGuiItemDecorations(matrix, mc.f_91062_, stack, x, y);
        }
    }

    private int calcXBase(int screenWidth) {
        int x = screenWidth;
        int xOffset = IC2.CONFIG.hudXOffset.get();
        boolean vertical = IC2.CONFIG.hudOrientation.get() == RenderOrientation.VERTICAL;
        switch (IC2.CONFIG.dockHorizontal.get()) {
            case LEFT: {
                x = xOffset;
                break;
            }
            case CENTER: {
                x = x / 2 + xOffset + (vertical ? -8 : -32);
                break;
            }
            case RIGHT: {
                x = x + xOffset + (vertical ? -16 : -64);
            }
        }
        return x;
    }

    private int calcYBase(int screenHeight) {
        int y = screenHeight;
        int yOffset = IC2.CONFIG.hudYOffset.get();
        boolean vertical = IC2.CONFIG.hudOrientation.get() == RenderOrientation.VERTICAL;
        switch (IC2.CONFIG.dockVertical.get()) {
            case TOP: {
                y = yOffset;
                break;
            }
            case CENTER: {
                y = y / 2 + yOffset + (vertical ? -32 : -8);
                break;
            }
            case BOTTOM: {
                y = y + yOffset + (vertical ? -64 : -16);
            }
        }
        return y;
    }

    public static enum RenderOrientation {
        HORIZONTAL,
        VERTICAL;

    }

    public static enum HorizontalDocks {
        LEFT,
        CENTER,
        RIGHT;

    }

    public static enum VerticalDocks {
        TOP,
        CENTER,
        BOTTOM;

    }
}

