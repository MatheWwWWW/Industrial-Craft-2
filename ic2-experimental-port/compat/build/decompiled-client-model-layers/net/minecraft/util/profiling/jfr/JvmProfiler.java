/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.net.SocketAddress;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public interface JvmProfiler {
    public static final JvmProfiler f_185340_ = Runtime.class.getModule().getLayer().findModule("jdk.jfr").isPresent() ? JfrProfiler.m_185298_() : new NoOpProfiler();

    public boolean m_183425_(Environment var1);

    public Path m_183243_();

    public boolean m_183608_();

    public boolean m_183609_();

    public void m_183597_(float var1);

    public void m_183510_(int var1, int var2, SocketAddress var3, int var4);

    public void m_183508_(int var1, int var2, SocketAddress var3, int var4);

    @Nullable
    public ProfiledDuration m_183494_();

    @Nullable
    public ProfiledDuration m_183559_(ChunkPos var1, ResourceKey<Level> var2, String var3);

    public static class NoOpProfiler
    implements JvmProfiler {
        private static final Logger f_185355_ = LogUtils.getLogger();
        static final ProfiledDuration f_185356_ = () -> {};

        @Override
        public boolean m_183425_(Environment p_185368_) {
            f_185355_.warn("Attempted to start Flight Recorder, but it's not supported on this JVM");
            return false;
        }

        @Override
        public Path m_183243_() {
            throw new IllegalStateException("Attempted to stop Flight Recorder, but it's not supported on this JVM");
        }

        @Override
        public boolean m_183608_() {
            return false;
        }

        @Override
        public boolean m_183609_() {
            return false;
        }

        @Override
        public void m_183510_(int p_185363_, int p_185364_, SocketAddress p_185365_, int p_185366_) {
        }

        @Override
        public void m_183508_(int p_185375_, int p_185376_, SocketAddress p_185377_, int p_185378_) {
        }

        @Override
        public void m_183597_(float p_185361_) {
        }

        @Override
        public ProfiledDuration m_183494_() {
            return f_185356_;
        }

        @Override
        @Nullable
        public ProfiledDuration m_183559_(ChunkPos p_185370_, ResourceKey<Level> p_185371_, String p_185372_) {
            return null;
        }
    }
}

