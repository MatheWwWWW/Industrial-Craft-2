/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.mojang.logging.LogUtils
 *  com.sun.jna.Memory
 *  com.sun.jna.Native
 *  com.sun.jna.Platform
 *  com.sun.jna.Pointer
 *  com.sun.jna.platform.win32.Kernel32
 *  com.sun.jna.platform.win32.Kernel32Util
 *  com.sun.jna.platform.win32.Tlhelp32$MODULEENTRY32W
 *  com.sun.jna.platform.win32.Version
 *  com.sun.jna.platform.win32.Win32Exception
 *  com.sun.jna.ptr.IntByReference
 *  com.sun.jna.ptr.PointerByReference
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.Version;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.CrashReportCategory;
import org.slf4j.Logger;

public class NativeModuleLister {
    private static final Logger f_184659_ = LogUtils.getLogger();
    private static final int f_184660_ = 65535;
    private static final int f_184661_ = 1033;
    private static final int f_184662_ = -65536;
    private static final int f_184663_ = 0x4B00000;

    public static List<NativeModuleInfo> m_184666_() {
        if (!Platform.isWindows()) {
            return ImmutableList.of();
        }
        int $$0 = Kernel32.INSTANCE.GetCurrentProcessId();
        ImmutableList.Builder $$1 = ImmutableList.builder();
        List $$2 = Kernel32Util.getModules((int)$$0);
        for (Tlhelp32.MODULEENTRY32W $$3 : $$2) {
            String $$4 = $$3.szModule();
            Optional<NativeModuleVersion> $$5 = NativeModuleLister.m_184673_($$3.szExePath());
            $$1.add((Object)new NativeModuleInfo($$4, $$5));
        }
        return $$1.build();
    }

    private static Optional<NativeModuleVersion> m_184673_(String p_184674_) {
        try {
            IntByReference $$1 = new IntByReference();
            int $$2 = Version.INSTANCE.GetFileVersionInfoSize(p_184674_, $$1);
            if ($$2 == 0) {
                int $$3 = Native.getLastError();
                if ($$3 == 1813 || $$3 == 1812) {
                    return Optional.empty();
                }
                throw new Win32Exception($$3);
            }
            Memory $$4 = new Memory((long)$$2);
            if (!Version.INSTANCE.GetFileVersionInfo(p_184674_, 0, $$2, (Pointer)$$4)) {
                throw new Win32Exception(Native.getLastError());
            }
            IntByReference $$5 = new IntByReference();
            Pointer $$6 = NativeModuleLister.m_184669_((Pointer)$$4, "\\VarFileInfo\\Translation", $$5);
            int[] $$7 = $$6.getIntArray(0L, $$5.getValue() / 4);
            OptionalInt $$8 = NativeModuleLister.m_184681_($$7);
            if (!$$8.isPresent()) {
                return Optional.empty();
            }
            int $$9 = $$8.getAsInt();
            int $$10 = $$9 & 0xFFFF;
            int $$11 = ($$9 & 0xFFFF0000) >> 16;
            String $$12 = NativeModuleLister.m_184686_((Pointer)$$4, NativeModuleLister.m_184675_("FileDescription", $$10, $$11), $$5);
            String $$13 = NativeModuleLister.m_184686_((Pointer)$$4, NativeModuleLister.m_184675_("CompanyName", $$10, $$11), $$5);
            String $$14 = NativeModuleLister.m_184686_((Pointer)$$4, NativeModuleLister.m_184675_("FileVersion", $$10, $$11), $$5);
            return Optional.of(new NativeModuleVersion($$12, $$14, $$13));
        }
        catch (Exception $$15) {
            f_184659_.info("Failed to find module info for {}", (Object)p_184674_, (Object)$$15);
            return Optional.empty();
        }
    }

    private static String m_184675_(String p_184676_, int p_184677_, int p_184678_) {
        return String.format(Locale.ROOT, "\\StringFileInfo\\%04x%04x\\%s", p_184677_, p_184678_, p_184676_);
    }

    private static OptionalInt m_184681_(int[] p_184682_) {
        OptionalInt $$1 = OptionalInt.empty();
        for (int $$2 : p_184682_) {
            if (($$2 & 0xFFFF0000) == 0x4B00000 && ($$2 & 0xFFFF) == 1033) {
                return OptionalInt.of($$2);
            }
            $$1 = OptionalInt.of($$2);
        }
        return $$1;
    }

    private static Pointer m_184669_(Pointer p_184670_, String p_184671_, IntByReference p_184672_) {
        PointerByReference $$3 = new PointerByReference();
        if (!Version.INSTANCE.VerQueryValue(p_184670_, p_184671_, $$3, p_184672_)) {
            throw new UnsupportedOperationException("Can't get version value " + p_184671_);
        }
        return $$3.getValue();
    }

    private static String m_184686_(Pointer p_184687_, String p_184688_, IntByReference p_184689_) {
        try {
            Pointer $$3 = NativeModuleLister.m_184669_(p_184687_, p_184688_, p_184689_);
            byte[] $$4 = $$3.getByteArray(0L, (p_184689_.getValue() - 1) * 2);
            return new String($$4, StandardCharsets.UTF_16LE);
        }
        catch (Exception $$5) {
            return "";
        }
    }

    public static void m_184679_(CrashReportCategory p_184680_) {
        p_184680_.m_128165_("Modules", () -> NativeModuleLister.m_184666_().stream().sorted(Comparator.comparing(p_184685_ -> p_184685_.f_184690_)).map(p_184668_ -> "\n\t\t" + p_184668_).collect(Collectors.joining()));
    }

    public static class NativeModuleInfo {
        public final String f_184690_;
        public final Optional<NativeModuleVersion> f_184691_;

        public NativeModuleInfo(String p_184693_, Optional<NativeModuleVersion> p_184694_) {
            this.f_184690_ = p_184693_;
            this.f_184691_ = p_184694_;
        }

        public String toString() {
            return this.f_184691_.map(p_184696_ -> this.f_184690_ + ":" + p_184696_).orElse(this.f_184690_);
        }
    }

    public static class NativeModuleVersion {
        public final String f_184698_;
        public final String f_184699_;
        public final String f_184700_;

        public NativeModuleVersion(String p_184702_, String p_184703_, String p_184704_) {
            this.f_184698_ = p_184702_;
            this.f_184699_ = p_184703_;
            this.f_184700_ = p_184704_;
        }

        public String toString() {
            return this.f_184698_ + ":" + this.f_184699_ + ":" + this.f_184700_;
        }
    }
}

