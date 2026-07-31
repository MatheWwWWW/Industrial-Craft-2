/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.tool;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.init.Localization;
import ic2.core.item.tool.ContainerToolScanner;
import ic2.core.util.Tuple;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GuiToolScanner
extends Ic2Gui<ContainerToolScanner> {
    public GuiToolScanner(ContainerToolScanner containerToolScanner, Inventory inventory, Component component) {
        super(containerToolScanner, inventory, component, 230);
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 10, 52, Localization.translate("ic2.itemScanner.found"), 2157374);
        if (((ContainerToolScanner)this.f_97732_).scanResults != null) {
            int n3 = 0;
            for (Tuple.T2<ItemStack, Integer> t2 : ((ContainerToolScanner)this.f_97732_).scanResults) {
                String string = ((ItemStack)t2.a).m_41720_().m_7626_((ItemStack)t2.a).getString();
                this.drawString(poseStack, 10, 66 + n3 * 11, t2.b + "x " + string, 5752026);
                if (++n3 != 10) continue;
                break;
            }
        }
    }

    @Override
    protected void m_7286_(PoseStack poseStack, float f, int n, int n2) {
        super.m_7286_(poseStack, f, n, n2);
        if (((ContainerToolScanner)this.f_97732_).scanResults != null) {
            int n3 = 0;
            for (Tuple.T2<ItemStack, Integer> t2 : ((ContainerToolScanner)this.f_97732_).scanResults) {
                int n4 = 135 + (n3 & 1) * 15;
                this.drawItem(n4, 11 * n3 + 28, (ItemStack)t2.a);
                if (++n3 != 10) continue;
                break;
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guitoolscanner.png");
    }
}

