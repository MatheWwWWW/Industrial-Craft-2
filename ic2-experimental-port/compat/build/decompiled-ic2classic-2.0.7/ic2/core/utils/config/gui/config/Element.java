/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.ContainerObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.narration.NarratableEntry
 *  net.minecraft.client.gui.narration.NarratableEntry$NarrationPriority
 *  net.minecraft.client.gui.narration.NarratedElementType
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.config.IListOwner;
import ic2.core.utils.config.gui.widgets.GuiUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class Element
extends ContainerObjectSelectionList.Entry<Element> {
    protected Minecraft mc = Minecraft.m_91087_();
    protected Font font;
    protected Component name;
    protected Component unchanged;
    protected Component changed;
    protected IListOwner owner;

    public Element(Component name) {
        this.font = this.mc.f_91062_;
        this.setName(name);
    }

    public String getName() {
        return this.name.getString();
    }

    public void setName(Component newName) {
        this.name = newName;
        this.unchanged = this.name.m_6881_().m_130940_(ChatFormatting.GRAY);
        this.changed = this.name.m_6881_().m_130940_(ChatFormatting.ITALIC);
    }

    public boolean isChanged() {
        return false;
    }

    public boolean isDefault() {
        return false;
    }

    public void init() {
    }

    public void tick() {
    }

    public void m_6311_(PoseStack poseStack, int x, int top, int left, int width, int height, int mouseX, int mouseY, boolean selected, float partialTicks) {
    }

    protected void renderName(PoseStack stack, float x, float y, boolean changed) {
        this.renderText(stack, changed ? this.changed : this.unchanged, x, y, -1);
    }

    protected void renderName(PoseStack stack, float x, float y, boolean changed, int maxWidth) {
        this.font.m_92877_(stack, Language.m_128107_().m_5536_(GuiUtils.ellipsizeStyled(changed ? this.changed : this.unchanged, maxWidth, this.font)), x, y, -1);
    }

    protected void renderText(PoseStack stack, Component text, float x, float y, int color) {
        this.font.m_92889_(stack, text, x, y, color);
    }

    public List<? extends GuiEventListener> m_6702_() {
        return ObjectLists.emptyList();
    }

    public List<? extends NarratableEntry> m_142437_() {
        return ObjectLists.singleton((Object)new NarratableEntry(){

            public NarratableEntry.NarrationPriority m_142684_() {
                return NarratableEntry.NarrationPriority.HOVERED;
            }

            public void m_142291_(NarrationElementOutput output) {
                output.m_169146_(NarratedElementType.TITLE, Element.this.name);
            }
        });
    }
}

