/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.network.protocol;

import com.mojang.logging.LogUtils;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.thread.BlockableEventLoop;
import org.slf4j.Logger;

public class PacketUtils {
    private static final Logger f_131354_ = LogUtils.getLogger();

    public static <T extends PacketListener> void m_131359_(Packet<T> p_131360_, T p_131361_, ServerLevel p_131362_) throws RunningOnDifferentThreadException {
        PacketUtils.m_131363_(p_131360_, p_131361_, p_131362_.m_7654_());
    }

    public static <T extends PacketListener> void m_131363_(Packet<T> p_131364_, T p_131365_, BlockableEventLoop<?> p_131366_) throws RunningOnDifferentThreadException {
        if (!p_131366_.m_18695_()) {
            p_131366_.m_201446_(() -> {
                if (p_131365_.m_6198_().m_129536_()) {
                    try {
                        p_131364_.m_5797_(p_131365_);
                    }
                    catch (Exception $$2) {
                        if (p_131365_.m_201767_()) {
                            throw $$2;
                        }
                        f_131354_.error("Failed to handle packet {}, suppressing error", (Object)p_131364_, (Object)$$2);
                    }
                } else {
                    f_131354_.debug("Ignoring packet due to disconnection: {}", (Object)p_131364_);
                }
            });
            throw RunningOnDifferentThreadException.f_136017_;
        }
    }
}

