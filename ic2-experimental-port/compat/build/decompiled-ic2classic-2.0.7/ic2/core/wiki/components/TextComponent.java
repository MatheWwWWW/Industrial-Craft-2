/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.util.FormattedCharSink
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.utils.tooltips.TextHelper;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.components.BaseWikiComponent;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TextComponent
extends BaseWikiComponent {
    List<FormattedCharSequence> texts;

    public TextComponent(List<FormattedCharSequence> texts) {
        super(texts.size() * 9);
        this.texts = texts;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> listener) {
        TextHelper.StringConsumer summer = new TextHelper.StringConsumer();
        for (FormattedCharSequence processor : this.texts) {
            processor.m_13731_((FormattedCharSink)summer);
        }
        listener.accept((Component)Component.m_237113_((String)summer.getBuilder().toString()));
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < this.texts.size(); ++i) {
            screen.drawString(stack, this.texts.get(i), x, y + i * 9, -16777216);
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        this.renderHovers(screen, stack, IC2Screen.getComponentNew(this.texts, mouseX - x, mouseY - y), mouseX, mouseY, tooltips);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        Style comp = IC2Screen.getComponentNew(this.texts, mouseX - x - 8, mouseY - y - 8);
        return comp != null && provider.handleClickEvent(comp.m_131182_());
    }
}

