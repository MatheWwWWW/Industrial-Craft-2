/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.gametest.framework;

import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.LogTestReporter;
import net.minecraft.gametest.framework.TestReporter;

public class GlobalTestReporter {
    private static TestReporter f_177649_ = new LogTestReporter();

    public static void m_177655_(TestReporter p_177656_) {
        f_177649_ = p_177656_;
    }

    public static void m_177653_(GameTestInfo p_177654_) {
        f_177649_.m_8014_(p_177654_);
    }

    public static void m_177657_(GameTestInfo p_177658_) {
        f_177649_.m_142335_(p_177658_);
    }

    public static void m_177652_() {
        f_177649_.m_142411_();
    }
}

