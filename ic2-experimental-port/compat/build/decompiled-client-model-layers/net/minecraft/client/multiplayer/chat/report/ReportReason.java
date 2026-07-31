/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.multiplayer.chat.report;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public final class ReportReason
extends Enum<ReportReason> {
    public static final /* enum */ ReportReason FALSE_REPORTING = new ReportReason(2, "false_reporting", false);
    public static final /* enum */ ReportReason HATE_SPEECH = new ReportReason(5, "hate_speech", true);
    public static final /* enum */ ReportReason TERRORISM_OR_VIOLENT_EXTREMISM = new ReportReason(16, "terrorism_or_violent_extremism", true);
    public static final /* enum */ ReportReason CHILD_SEXUAL_EXPLOITATION_OR_ABUSE = new ReportReason(17, "child_sexual_exploitation_or_abuse", true);
    public static final /* enum */ ReportReason IMMINENT_HARM = new ReportReason(18, "imminent_harm", true);
    public static final /* enum */ ReportReason NON_CONSENSUAL_INTIMATE_IMAGERY = new ReportReason(19, "non_consensual_intimate_imagery", true);
    public static final /* enum */ ReportReason HARASSMENT_OR_BULLYING = new ReportReason(21, "harassment_or_bullying", true);
    public static final /* enum */ ReportReason DEFAMATION_IMPERSONATION_FALSE_INFORMATION = new ReportReason(27, "defamation_impersonation_false_information", true);
    public static final /* enum */ ReportReason SELF_HARM_OR_SUICIDE = new ReportReason(31, "self_harm_or_suicide", true);
    public static final /* enum */ ReportReason ALCOHOL_TOBACCO_DRUGS = new ReportReason(39, "alcohol_tobacco_drugs", true);
    private final int f_238739_;
    private final String f_238735_;
    private final boolean f_242496_;
    private final Component f_238806_;
    private final Component f_238818_;
    private static final /* synthetic */ ReportReason[] $VALUES;

    public static ReportReason[] values() {
        return (ReportReason[])$VALUES.clone();
    }

    public static ReportReason valueOf(String p_239416_) {
        return Enum.valueOf(ReportReason.class, p_239416_);
    }

    private ReportReason(int p_242843_, String p_242899_, boolean p_242895_) {
        this.f_238739_ = p_242843_;
        this.f_238735_ = p_242899_.toUpperCase(Locale.ROOT);
        this.f_242496_ = p_242895_;
        String $$3 = "gui.abuseReport.reason." + p_242899_;
        this.f_238806_ = Component.m_237115_($$3);
        this.f_238818_ = Component.m_237115_($$3 + ".description");
    }

    public String m_239892_() {
        return this.f_238735_;
    }

    public Component m_239342_() {
        return this.f_238806_;
    }

    public Component m_240151_() {
        return this.f_238818_;
    }

    public boolean m_242666_() {
        return this.f_242496_;
    }

    @Nullable
    public static Component m_239749_(int p_239750_) {
        for (ReportReason $$1 : ReportReason.values()) {
            if ($$1.f_238739_ != p_239750_) continue;
            return $$1.f_238806_;
        }
        return null;
    }

    private static /* synthetic */ ReportReason[] m_240189_() {
        return new ReportReason[]{FALSE_REPORTING, HATE_SPEECH, TERRORISM_OR_VIOLENT_EXTREMISM, CHILD_SEXUAL_EXPLOITATION_OR_ABUSE, IMMINENT_HARM, NON_CONSENSUAL_INTIMATE_IMAGERY, HARASSMENT_OR_BULLYING, DEFAMATION_IMPERSONATION_FALSE_INFORMATION, SELF_HARM_OR_SUICIDE, ALCOHOL_TOBACCO_DRUGS};
    }

    static {
        $VALUES = ReportReason.m_240189_();
    }
}

