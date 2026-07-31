/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.Style
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.components.IWikiComponent;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BaseWikiComponent
implements IWikiComponent,
ILangHelper {
    int height;

    public BaseWikiComponent(int height) {
        this.height = height;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void tick(IC2Screen screen) {
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> listener) {
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        return false;
    }

    @Override
    public boolean onMouseRelease(IC2Screen screen, int x, int y, int mouseX, int mouseY) {
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(IC2Screen screen, int x, int y, int mouseX, int mouseY, int scroll, IWikiProvider provider) {
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
    }

    @OnlyIn(value=Dist.CLIENT)
    public void renderHovers(IC2Screen screen, PoseStack stack, Style component, int mouseX, int mouseY, Consumer<Component> tooltips) {
        if (component == null) {
            return;
        }
        HoverEvent event = component.m_131186_();
        if (event == null) {
            return;
        }
        if (event.m_130820_() == HoverEvent.Action.f_130831_) {
            tooltips.accept((Component)event.m_130823_(HoverEvent.Action.f_130831_));
        } else {
            screen.m_96570_(stack, component, mouseX, mouseY);
        }
    }
}

