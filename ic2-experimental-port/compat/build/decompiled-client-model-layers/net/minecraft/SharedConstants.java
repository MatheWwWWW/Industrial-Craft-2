/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  io.netty.util.ResourceLeakDetector
 *  io.netty.util.ResourceLeakDetector$Level
 *  javax.annotation.Nullable
 */
package net.minecraft;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.util.ResourceLeakDetector;
import java.time.Duration;
import javax.annotation.Nullable;
import net.minecraft.DetectedVersion;
import net.minecraft.WorldVersion;
import net.minecraft.commands.BrigadierExceptions;
import net.minecraft.util.datafix.DataFixerOptimizationOption;
import net.minecraft.world.level.ChunkPos;

public class SharedConstants {
    @Deprecated
    public static final boolean f_142912_ = false;
    @Deprecated
    public static final int f_142951_ = 3120;
    @Deprecated
    public static final String f_183702_ = "main";
    @Deprecated
    public static final String f_142952_ = "1.19.2";
    @Deprecated
    public static final String f_142953_ = "1.19.2";
    @Deprecated
    public static final int f_142954_ = 760;
    @Deprecated
    public static final int f_142955_ = 103;
    public static final int f_142956_ = 3075;
    private static final int f_142925_ = 30;
    public static final boolean f_201847_ = false;
    @Deprecated
    public static final int f_142957_ = 9;
    @Deprecated
    public static final int f_142958_ = 10;
    public static final String f_142959_ = "DataVersion";
    public static final boolean f_142961_ = false;
    public static final boolean f_142965_ = false;
    public static final boolean f_142966_ = false;
    public static final boolean f_142967_ = false;
    public static final boolean f_142968_ = false;
    public static final boolean f_142970_ = false;
    public static final boolean f_183703_ = false;
    public static final boolean f_183704_ = false;
    public static final boolean f_142972_ = false;
    public static final boolean f_142973_ = false;
    public static final boolean f_142974_ = false;
    public static final boolean f_142975_ = false;
    public static final boolean f_142886_ = false;
    public static final boolean f_142887_ = false;
    public static final boolean f_142888_ = false;
    public static final boolean f_142889_ = false;
    public static final boolean f_142890_ = false;
    public static final boolean f_142891_ = false;
    public static final boolean f_142892_ = false;
    public static final boolean f_142893_ = false;
    public static final boolean f_142894_ = false;
    public static final boolean f_142895_ = false;
    public static final boolean f_142896_ = false;
    public static final boolean f_142897_ = false;
    public static final boolean f_142898_ = false;
    public static final boolean f_142899_ = false;
    public static final boolean f_142900_ = false;
    public static final boolean f_142901_ = false;
    public static final boolean f_142902_ = false;
    public static final boolean f_142903_ = false;
    public static final boolean f_142904_ = false;
    public static final boolean f_142905_ = false;
    public static final boolean f_142906_ = false;
    public static final boolean f_142907_ = false;
    public static final boolean f_142908_ = false;
    public static final boolean f_142909_ = false;
    public static final boolean f_142910_ = false;
    public static final boolean f_142911_ = false;
    public static final boolean f_142926_ = false;
    public static final boolean f_142927_ = false;
    public static final boolean f_142928_ = false;
    public static final boolean f_142929_ = false;
    public static final boolean f_142930_ = false;
    public static final boolean f_142931_ = false;
    public static final boolean f_214356_ = false;
    public static final boolean f_214357_ = false;
    public static final boolean f_238781_ = false;
    public static final boolean f_183695_ = false;
    public static final boolean f_142932_ = false;
    public static final boolean f_142933_ = false;
    public static final boolean f_183696_ = false;
    public static final boolean f_183697_ = false;
    public static boolean f_183698_ = false;
    public static boolean f_183699_ = false;
    public static final boolean f_142934_ = false;
    public static final boolean f_142935_ = false;
    public static final boolean f_142936_ = false;
    public static final boolean f_142938_ = false;
    public static final boolean f_142939_ = false;
    public static final boolean f_142940_ = false;
    public static final boolean f_142941_ = false;
    public static final boolean f_142942_ = false;
    public static final boolean f_183700_ = false;
    public static final boolean f_183701_ = false;
    public static final int f_142944_ = 25565;
    public static final boolean f_142945_ = false;
    public static final boolean f_142946_ = false;
    public static final int f_142947_ = 0;
    public static final int f_142948_ = 0;
    public static final ResourceLeakDetector.Level f_136180_ = ResourceLeakDetector.Level.DISABLED;
    public static final boolean f_142949_ = false;
    public static final boolean f_142950_ = false;
    public static final boolean f_142913_ = false;
    public static final boolean f_142914_ = false;
    public static final boolean f_183694_ = false;
    public static final long f_136181_ = Duration.ofMillis(300L).toNanos();
    public static boolean f_136182_ = true;
    public static boolean f_136183_;
    public static DataFixerOptimizationOption f_214354_;
    public static final int f_142916_ = 16;
    public static final int f_142917_ = 256;
    public static final int f_142918_ = 32500;
    public static final int f_214355_ = 1000000;
    public static final int f_242499_ = 32;
    public static final char[] f_136184_;
    public static final int f_142919_ = 20;
    public static final int f_142920_ = 1200;
    public static final int f_142921_ = 24000;
    public static final float f_142922_ = 1365.3334f;
    public static final float f_142923_ = 0.87890625f;
    public static final float f_142924_ = 17.578125f;
    @Nullable
    private static WorldVersion f_136185_;

    public static boolean m_136188_(char p_136189_) {
        return p_136189_ != '\u00a7' && p_136189_ >= ' ' && p_136189_ != '\u007f';
    }

    public static String m_136190_(String p_136191_) {
        return SharedConstants.m_239657_(p_136191_, false);
    }

    public static String m_239657_(String p_239658_, boolean p_239659_) {
        StringBuilder $$2 = new StringBuilder();
        for (char $$3 : p_239658_.toCharArray()) {
            if (SharedConstants.m_136188_($$3)) {
                $$2.append($$3);
                continue;
            }
            if (!p_239659_ || $$3 != '\n') continue;
            $$2.append($$3);
        }
        return $$2.toString();
    }

    public static void m_183705_(WorldVersion p_183706_) {
        if (f_136185_ == null) {
            f_136185_ = p_183706_;
        } else if (p_183706_ != f_136185_) {
            throw new IllegalStateException("Cannot override the current game version!");
        }
    }

    public static void m_142977_() {
        if (f_136185_ == null) {
            f_136185_ = DetectedVersion.m_195834_();
        }
    }

    public static WorldVersion m_183709_() {
        if (f_136185_ == null) {
            throw new IllegalStateException("Game version not set");
        }
        return f_136185_;
    }

    public static int m_136192_() {
        return 760;
    }

    public static boolean m_183707_(ChunkPos p_183708_) {
        int $$1 = p_183708_.m_45604_();
        int $$2 = p_183708_.m_45605_();
        if (f_183698_) {
            return $$1 > 8192 || $$1 < 0 || $$2 > 1024 || $$2 < 0;
        }
        return false;
    }

    public static void m_214358_() {
        f_214354_ = switch (f_214354_) {
            case DataFixerOptimizationOption.INITIALIZED_UNOPTIMIZED -> throw new IllegalStateException("Tried to enable datafixer optimization after unoptimized initialization");
            case DataFixerOptimizationOption.INITIALIZED_OPTIMIZED -> DataFixerOptimizationOption.INITIALIZED_OPTIMIZED;
            default -> DataFixerOptimizationOption.UNINITIALIZED_OPTIMIZED;
        };
    }

    static {
        f_214354_ = DataFixerOptimizationOption.UNINITIALIZED_UNOPTIMIZED;
        f_136184_ = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
        ResourceLeakDetector.setLevel((ResourceLeakDetector.Level)f_136180_);
        CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
        CommandSyntaxException.BUILT_IN_EXCEPTIONS = new BrigadierExceptions();
    }
}

