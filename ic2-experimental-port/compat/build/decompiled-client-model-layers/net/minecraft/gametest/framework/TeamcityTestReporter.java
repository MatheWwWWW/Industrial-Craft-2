/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.escape.Escaper
 *  com.google.common.escape.Escapers
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.gametest.framework;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.TestReporter;
import org.slf4j.Logger;

public class TeamcityTestReporter
implements TestReporter {
    private static final Logger f_177778_ = LogUtils.getLogger();
    private static final Escaper f_177779_ = Escapers.builder().addEscape('\'', "|'").addEscape('\n', "|n").addEscape('\r', "|r").addEscape('|', "||").addEscape('[', "|[").addEscape(']', "|]").build();

    @Override
    public void m_8014_(GameTestInfo p_177783_) {
        String $$1 = f_177779_.escape(p_177783_.m_127633_());
        String $$2 = f_177779_.escape(p_177783_.m_127642_().getMessage());
        String $$3 = f_177779_.escape(Util.m_137575_(p_177783_.m_127642_()));
        f_177778_.info("##teamcity[testStarted name='{}']", (Object)$$1);
        if (p_177783_.m_127643_()) {
            f_177778_.info("##teamcity[testFailed name='{}' message='{}' details='{}']", new Object[]{$$1, $$2, $$3});
        } else {
            f_177778_.info("##teamcity[testIgnored name='{}' message='{}' details='{}']", new Object[]{$$1, $$2, $$3});
        }
        f_177778_.info("##teamcity[testFinished name='{}' duration='{}']", (Object)$$1, (Object)p_177783_.m_177485_());
    }

    @Override
    public void m_142335_(GameTestInfo p_177785_) {
        String $$1 = f_177779_.escape(p_177785_.m_127633_());
        f_177778_.info("##teamcity[testStarted name='{}']", (Object)$$1);
        f_177778_.info("##teamcity[testFinished name='{}' duration='{}']", (Object)$$1, (Object)p_177785_.m_177485_());
    }
}

