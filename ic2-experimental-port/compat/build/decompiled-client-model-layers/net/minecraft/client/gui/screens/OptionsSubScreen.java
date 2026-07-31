/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class OptionsSubScreen
extends Screen {
    protected final Screen f_96281_;
    protected final Options f_96282_;

    public OptionsSubScreen(Screen p_96284_, Options p_96285_, Component p_96286_) {
        super(p_96286_);
        this.f_96281_ = p_96284_;
        this.f_96282_ = p_96285_;
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91066_.m_92169_();
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_96281_);
    }

    public static List<FormattedCharSequence> m_96287_(OptionsList p_96288_, int p_96289_, int p_96290_) {
        Optional<AbstractWidget> $$3 = p_96288_.m_94480_(p_96289_, p_96290_);
        if ($$3.isPresent() && $$3.get() instanceof TooltipAccessor) {
            return ((TooltipAccessor)((Object)$$3.get())).m_141932_();
        }
        return ImmutableList.of();
    }
}

