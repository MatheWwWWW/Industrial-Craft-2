/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.gametest.framework;

import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.TestReporter;
import org.slf4j.Logger;

public class LogTestReporter
implements TestReporter {
    private static final Logger f_127793_ = LogUtils.getLogger();

    @Override
    public void m_8014_(GameTestInfo p_127797_) {
        if (p_127797_.m_127643_()) {
            f_127793_.error("{} failed! {}", (Object)p_127797_.m_127633_(), (Object)Util.m_137575_(p_127797_.m_127642_()));
        } else {
            f_127793_.warn("(optional) {} failed. {}", (Object)p_127797_.m_127633_(), (Object)Util.m_137575_(p_127797_.m_127642_()));
        }
    }

    @Override
    public void m_142335_(GameTestInfo p_177676_) {
    }
}

