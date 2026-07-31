/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr;

import net.minecraft.server.MinecraftServer;

public final class Environment
extends Enum<Environment> {
    public static final /* enum */ Environment CLIENT = new Environment("client");
    public static final /* enum */ Environment SERVER = new Environment("server");
    private final String f_185270_;
    private static final /* synthetic */ Environment[] $VALUES;

    public static Environment[] values() {
        return (Environment[])$VALUES.clone();
    }

    public static Environment valueOf(String p_185282_) {
        return Enum.valueOf(Environment.class, p_185282_);
    }

    private Environment(String p_185276_) {
        this.f_185270_ = p_185276_;
    }

    public static Environment m_185278_(MinecraftServer p_185279_) {
        return p_185279_.m_6982_() ? SERVER : CLIENT;
    }

    public String m_185277_() {
        return this.f_185270_;
    }

    private static /* synthetic */ Environment[] m_185280_() {
        return new Environment[]{CLIENT, SERVER};
    }

    static {
        $VALUES = Environment.m_185280_();
    }
}

