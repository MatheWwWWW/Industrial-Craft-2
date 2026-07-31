/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.components.BaseWikiComponent;
import ic2.core.wiki.components.builders.CategoryObj;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CategoryComponent
extends BaseWikiComponent {
    Component comp;
    CategoryObj.Link headerLink;
    List<CategoryObj.Link> links;
    float[] animationProgress;

    public CategoryComponent(Component comp, CategoryObj.Link headerLink, List<CategoryObj.Link> links) {
        super(16 + Mth.m_14167_((float)((float)links.size() / 4.0f)) * 20);
        this.comp = comp;
        this.headerLink = headerLink;
        this.links = links;
        this.animationProgress = new float[links.size()];
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        int width;
        int min;
        if (this.links.isEmpty()) {
            return false;
        }
        if (this.headerLink != null && mouseX >= x + (min = 59 - (width = screen.getFont().m_92852_((FormattedText)this.comp)) / 2) && mouseX <= x + min + width && mouseY >= y && mouseY <= y + 10) {
            return provider.handleClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "redirect;" + this.headerLink.getLink().toString()));
        }
        int offset = 70 - Math.min(this.links.size() / 2, 2) * 25;
        int m = this.links.size();
        for (int i = 0; i < m; ++i) {
            int xOff = i % 4 * 25 + x + offset;
            int yOff = i / 4 * 20 + y + 24;
            if (mouseX < xOff || mouseX > xOff + 20 || mouseY < yOff || mouseY > yOff + 20) continue;
            return provider.handleClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, "redirect;" + this.links.get(i).getLink().toString()));
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        int width;
        int min;
        if (this.links.isEmpty()) {
            return;
        }
        if (this.headerLink != null && mouseX >= x + (min = 59 - (width = screen.getFont().m_92852_((FormattedText)this.comp)) / 2) && mouseX <= x + min + width && mouseY >= y && mouseY <= y + 10) {
            tooltips.accept(this.headerLink.getTooltip());
        }
        int offset = 59 - Math.min(this.links.size() / 2, 2) * 25;
        int m = this.links.size();
        for (int i = 0; i < m; ++i) {
            int xOff = i % 4 * 25 + x + offset;
            int yOff = i / 4 * 20 + y + 12;
            if (mouseX < xOff || mouseX > xOff + 20 || mouseY < yOff || mouseY > yOff + 20) continue;
            tooltips.accept(this.links.get(i).getTooltip());
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        int i;
        if (this.links.isEmpty()) {
            return;
        }
        screen.drawCenterString(stack, this.comp, x + 59, y, -16777216);
        RenderSystem.m_157453_((int)0, (int)screen.getActiveTexture().m_117963_());
        screen.drawTextureRegion(stack, x + 1, y + 10, 0.0f, 248.0f, 116.0f, 4.0f);
        screen.drawTextureRegion(stack, x + 1, y + 10 + Mth.m_14167_((float)((float)this.links.size() / 4.0f)) * 20, 0.0f, 252.0f, 116.0f, 4.0f);
        int offset = 59 - Math.min(2, this.links.size() / 2) * 25;
        int m = this.links.size();
        for (i = 0; i < m; ++i) {
            screen.drawItemStack(stack, x + offset + i % 4 * 25, y + 12 + i / 4 * 20, this.links.get(i).getItem());
        }
        RenderSystem.m_157453_((int)0, (int)screen.getActiveTexture().m_117963_());
        RenderSystem.m_69478_();
        mouseX += screen.getGuiLeft();
        mouseY += screen.getGuiTop();
        m = this.links.size();
        for (i = 0; i < m; ++i) {
            int xOff = i % 4 * 25 + x + offset;
            int yOff = i / 4 * 20 + y + 12;
            if (mouseX >= xOff && mouseX <= xOff + 20 && mouseY >= yOff && mouseY <= yOff + 20) {
                this.animationProgress[i] = Math.min(1.0f, this.animationProgress[i] + partialTicks * 0.1f);
            } else if (this.animationProgress[i] > 0.0f) {
                this.animationProgress[i] = Math.max(0.0f, this.animationProgress[i] - partialTicks * 0.1f);
            }
            if (this.animationProgress[i] <= 0.0f) {
                screen.drawTextureRegion(stack, xOff, yOff, xOff - screen.getGuiLeft(), yOff - screen.getGuiTop(), 20.0f, 20.0f, -1342177281);
                continue;
            }
            if (!(this.animationProgress[i] < 1.0f)) continue;
            float forward = 20.0f * this.animationProgress[i] * 0.5f;
            screen.drawTextureRegion(stack, xOff, yOff, xOff - screen.getGuiLeft(), yOff - screen.getGuiTop(), 10.0f - forward, 10.0f - forward, -1342177281);
            screen.drawTextureRegion(stack, (float)(xOff + 10) + forward, yOff, xOff - screen.getGuiLeft(), yOff - screen.getGuiTop(), 10.0f - forward, 10.0f - forward, -1342177281);
            screen.drawTextureRegion(stack, xOff, (float)(yOff + 10) + forward, xOff - screen.getGuiLeft(), yOff - screen.getGuiTop(), 10.0f - forward, 10.0f - forward, -1342177281);
            screen.drawTextureRegion(stack, (float)(xOff + 10) + forward, (float)(yOff + 10) + forward, xOff - screen.getGuiLeft(), yOff - screen.getGuiTop(), 10.0f - forward, 10.0f - forward, -1342177281);
        }
        RenderSystem.m_69461_();
    }
}

