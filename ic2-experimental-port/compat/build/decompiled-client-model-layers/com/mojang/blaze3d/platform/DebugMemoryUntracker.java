/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.system.Pointer
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.GLX;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import org.lwjgl.system.Pointer;

public class DebugMemoryUntracker {
    @Nullable
    private static final MethodHandle f_83998_ = GLX.m_69373_(() -> {
        try {
            MethodHandles.Lookup $$0 = MethodHandles.lookup();
            Class<?> $$1 = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
            Method $$2 = $$1.getDeclaredMethod("untrack", Long.TYPE);
            $$2.setAccessible(true);
            Field $$3 = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
            $$3.setAccessible(true);
            Object $$4 = $$3.get(null);
            if ($$1.isInstance($$4)) {
                return $$0.unreflect($$2);
            }
            return null;
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException $$5) {
            throw new RuntimeException($$5);
        }
    });

    public static void m_84001_(long p_84002_) {
        if (f_83998_ == null) {
            return;
        }
        try {
            f_83998_.invoke(p_84002_);
        }
        catch (Throwable $$1) {
            throw new RuntimeException($$1);
        }
    }

    public static void m_84003_(Pointer p_84004_) {
        DebugMemoryUntracker.m_84001_(p_84004_.address());
    }
}

