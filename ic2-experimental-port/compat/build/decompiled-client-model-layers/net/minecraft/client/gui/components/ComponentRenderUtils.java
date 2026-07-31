/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.ComponentCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class ComponentRenderUtils {
    private static final FormattedCharSequence f_93993_ = FormattedCharSequence.m_13693_(32, Style.f_131099_);

    private static String m_93999_(String p_94000_) {
        return Minecraft.m_91087_().f_91066_.m_231814_().m_231551_() != false ? p_94000_ : ChatFormatting.m_126649_(p_94000_);
    }

    public static List<FormattedCharSequence> m_94005_(FormattedText p_94006_, int p_94007_, Font p_94008_) {
        ComponentCollector $$3 = new ComponentCollector();
        p_94006_.m_7451_((p_93997_, p_93998_) -> {
            $$3.m_90675_(FormattedText.m_130762_(ComponentRenderUtils.m_93999_(p_93998_), p_93997_));
            return Optional.empty();
        }, Style.f_131099_);
        ArrayList $$4 = Lists.newArrayList();
        p_94008_.m_92865_().m_92393_($$3.m_90677_(), p_94007_, Style.f_131099_, (p_94003_, p_94004_) -> {
            FormattedCharSequence $$3 = Language.m_128107_().m_5536_((FormattedText)p_94003_);
            $$4.add(p_94004_ != false ? FormattedCharSequence.m_13696_(f_93993_, $$3) : $$3);
        });
        if ($$4.isEmpty()) {
            return Lists.newArrayList((Object[])new FormattedCharSequence[]{FormattedCharSequence.f_13691_});
        }
        return $$4;
    }
}

