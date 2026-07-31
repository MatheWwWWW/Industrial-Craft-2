/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.tooltips;

import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class ToolTipHelper {
    List<Component> components;
    List<Component>[] key_components = CollectionUtils.createLists(3);
    int startIndex = 1;
    int endIndex = 1;
    int index = 1;
    int flags = 0;
    int usedFlags = 0;

    public ToolTipHelper(List<Component> components) {
        this.components = components;
        this.flags |= Screen.m_96637_() ? 1 : 0;
        this.flags |= Screen.m_96638_() ? 2 : 0;
        this.flags |= Screen.m_96639_() ? 4 : 0;
    }

    public boolean hasAltDown() {
        return (this.flags & 4) != 0;
    }

    public boolean hasShiftDown() {
        return (this.flags & 2) != 0;
    }

    public boolean hasCtrlDown() {
        return (this.flags & 1) != 0;
    }

    public void applyKeyInfo() {
        if (this.key_components[0].size() > 0) {
            if (this.hasCtrlDown()) {
                this.components.addAll(this.endIndex, this.key_components[0]);
                this.endIndex += this.key_components[0].size();
            } else {
                this.components.add(this.endIndex++, (Component)Component.m_237115_((String)"tooltip.ic2.press_ctrl.name").m_130940_(ChatFormatting.GOLD));
            }
        }
        if (this.key_components[1].size() > 0) {
            if (this.hasShiftDown()) {
                this.components.addAll(this.endIndex, this.key_components[1]);
                this.endIndex += this.key_components[1].size();
            } else {
                this.components.add(this.endIndex++, (Component)Component.m_237115_((String)"tooltip.ic2.press_shift.name").m_130940_(ChatFormatting.GOLD));
            }
        }
        if (this.key_components[2].size() > 0) {
            if (this.hasAltDown()) {
                this.components.addAll(this.endIndex, this.key_components[2]);
                this.endIndex += this.key_components[2].size();
            } else {
                this.components.add(this.endIndex++, (Component)Component.m_237115_((String)"tooltip.ic2.press_alt.name").m_130940_(ChatFormatting.GOLD));
            }
        }
    }

    public void setAddIndex(int index) {
        this.index = Mth.m_14045_((int)index, (int)0, (int)this.components.size());
    }

    public void setEnd(int endIndex) {
        this.endIndex = Mth.m_14045_((int)endIndex, (int)0, (int)this.components.size());
    }

    public List<Component> getTooltips() {
        return this.components;
    }

    public ToolTipHelper addSimpleToolTip(MutableComponent component) {
        this.components.add(this.index++, (Component)component);
        ++this.endIndex;
        Style style = component.m_7383_();
        if (style.m_131135_() == null) {
            component.m_6270_(style.m_131157_(ChatFormatting.GRAY));
        }
        return this;
    }

    public ToolTipHelper addSimpleToolTip(String translationKey, Object ... args) {
        return this.addSimpleToolTip(Component.m_237110_((String)translationKey, (Object[])args));
    }

    public ToolTipHelper addKeybindingTooltip(MutableComponent component) {
        this.key_components[0].add((Component)component);
        Style style = component.m_7383_();
        if (style.m_131135_() == null) {
            component.m_6270_(style.m_131157_(ChatFormatting.GRAY));
        }
        return this;
    }

    public ToolTipHelper addKeybindingTooltip(String translationKey, Object ... args) {
        return this.addKeybindingTooltip(Component.m_237110_((String)translationKey, (Object[])args));
    }

    public ToolTipHelper addDataTooltip(MutableComponent component) {
        this.key_components[1].add((Component)component);
        Style style = component.m_7383_();
        if (style.m_131135_() == null) {
            component.m_6270_(style.m_131157_(ChatFormatting.GRAY));
        }
        return this;
    }

    public ToolTipHelper addDataTooltip(String translationKey, Object ... args) {
        return this.addDataTooltip(Component.m_237110_((String)translationKey, (Object[])args));
    }

    public ToolTipHelper addStatTooltip(MutableComponent component) {
        this.key_components[2].add((Component)component);
        Style style = component.m_7383_();
        if (style.m_131135_() == null) {
            component.m_6270_(style.m_131157_(ChatFormatting.GRAY));
        }
        return this;
    }

    public ToolTipHelper addStatTooltip(String translationKey, Object ... args) {
        return this.addStatTooltip(Component.m_237110_((String)translationKey, (Object[])args));
    }
}

