/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ca.weblite.objc.Client
 *  ca.weblite.objc.NSObject
 *  com.sun.jna.Pointer
 *  org.lwjgl.glfw.GLFWNativeCocoa
 */
package com.mojang.blaze3d.platform;

import ca.weblite.objc.Client;
import ca.weblite.objc.NSObject;
import com.sun.jna.Pointer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import org.lwjgl.glfw.GLFWNativeCocoa;

public class MacosUtil {
    private static final int f_182515_ = 16384;

    public static void m_182517_(long p_182518_) {
        MacosUtil.m_182521_(p_182518_).filter(MacosUtil::m_182519_).ifPresent(MacosUtil::m_182523_);
    }

    private static Optional<NSObject> m_182521_(long p_182522_) {
        long $$1 = GLFWNativeCocoa.glfwGetCocoaWindow((long)p_182522_);
        if ($$1 != 0L) {
            return Optional.of(new NSObject(new Pointer($$1)));
        }
        return Optional.empty();
    }

    private static boolean m_182519_(NSObject p_182520_) {
        return ((Long)p_182520_.sendRaw("styleMask", new Object[0]) & 0x4000L) == 16384L;
    }

    private static void m_182523_(NSObject p_182524_) {
        p_182524_.send("toggleFullScreen:", new Object[]{Pointer.NULL});
    }

    public static void m_231133_(InputStream p_231134_) throws IOException {
        String $$1 = Base64.getEncoder().encodeToString(p_231134_.readAllBytes());
        Client $$2 = Client.getInstance();
        Object $$3 = $$2.sendProxy("NSData", "alloc", new Object[0]).send("initWithBase64Encoding:", new Object[]{$$1});
        Object $$4 = $$2.sendProxy("NSImage", "alloc", new Object[0]).send("initWithData:", new Object[]{$$3});
        $$2.sendProxy("NSApplication", "sharedApplication", new Object[0]).send("setApplicationIconImage:", new Object[]{$$4});
    }
}

