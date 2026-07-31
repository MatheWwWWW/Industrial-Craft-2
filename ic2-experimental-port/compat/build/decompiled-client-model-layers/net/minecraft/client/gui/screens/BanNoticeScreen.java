/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.minecraft.BanDetails
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.client.gui.screens;

import com.mojang.authlib.minecraft.BanDetails;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.time.Duration;
import java.time.Instant;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.multiplayer.chat.report.ReportReason;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import org.apache.commons.lang3.StringUtils;

public class BanNoticeScreen {
    public static final String f_238553_ = "https://aka.ms/mcjavamoderation";
    private static final Component f_238586_ = Component.m_237115_("gui.banned.title.temporary").m_130940_(ChatFormatting.BOLD);
    private static final Component f_238702_ = Component.m_237115_("gui.banned.title.permanent").m_130940_(ChatFormatting.BOLD);

    public static ConfirmLinkScreen m_239967_(BooleanConsumer p_239968_, BanDetails p_239969_) {
        return new ConfirmLinkScreen(p_239968_, BanNoticeScreen.m_239952_(p_239969_), BanNoticeScreen.m_239137_(p_239969_), f_238553_, CommonComponents.f_238584_, true);
    }

    private static Component m_239952_(BanDetails p_239953_) {
        return BanNoticeScreen.m_239500_(p_239953_) ? f_238586_ : f_238702_;
    }

    private static Component m_239137_(BanDetails p_239138_) {
        return Component.m_237110_("gui.banned.description", BanNoticeScreen.m_239533_(p_239138_), BanNoticeScreen.m_239318_(p_239138_), Component.m_237113_(f_238553_));
    }

    private static Component m_239533_(BanDetails p_239534_) {
        String $$1 = p_239534_.reason();
        String $$2 = p_239534_.reasonMessage();
        if (StringUtils.isNumeric((CharSequence)$$1)) {
            int $$3 = Integer.parseInt($$1);
            Component $$4 = ReportReason.m_239749_($$3);
            $$4 = $$4 != null ? ComponentUtils.m_130750_($$4.m_6881_(), Style.f_131099_.m_131136_(true)) : ($$2 != null ? Component.m_237110_("gui.banned.description.reason_id_message", $$3, $$2).m_130940_(ChatFormatting.BOLD) : Component.m_237110_("gui.banned.description.reason_id", $$3).m_130940_(ChatFormatting.BOLD));
            return Component.m_237110_("gui.banned.description.reason", $$4);
        }
        return Component.m_237115_("gui.banned.description.unknownreason");
    }

    private static Component m_239318_(BanDetails p_239319_) {
        if (BanNoticeScreen.m_239500_(p_239319_)) {
            Component $$1 = BanNoticeScreen.m_239879_(p_239319_);
            return Component.m_237110_("gui.banned.description.temporary", Component.m_237110_("gui.banned.description.temporary.duration", $$1).m_130940_(ChatFormatting.BOLD));
        }
        return Component.m_237115_("gui.banned.description.permanent").m_130940_(ChatFormatting.BOLD);
    }

    private static Component m_239879_(BanDetails p_239880_) {
        Duration $$1 = Duration.between(Instant.now(), p_239880_.expires());
        long $$2 = $$1.toHours();
        if ($$2 > 72L) {
            return CommonComponents.m_239422_($$1.toDays());
        }
        if ($$2 < 1L) {
            return CommonComponents.m_239877_($$1.toMinutes());
        }
        return CommonComponents.m_240041_($$1.toHours());
    }

    private static boolean m_239500_(BanDetails p_239501_) {
        return p_239501_.expires() != null;
    }
}

