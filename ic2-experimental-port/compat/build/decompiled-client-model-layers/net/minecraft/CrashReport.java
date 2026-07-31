/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 */
package net.minecraft;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.util.MemoryReserve;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class CrashReport {
    private static final Logger f_127499_ = LogUtils.getLogger();
    private static final DateTimeFormatter f_241641_ = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
    private final String f_127500_;
    private final Throwable f_127501_;
    private final List<CrashReportCategory> f_127503_ = Lists.newArrayList();
    private File f_127504_;
    private boolean f_127505_ = true;
    private StackTraceElement[] f_127506_ = new StackTraceElement[0];
    private final SystemReport f_178624_ = new SystemReport();

    public CrashReport(String p_127509_, Throwable p_127510_) {
        this.f_127500_ = p_127509_;
        this.f_127501_ = p_127510_;
    }

    public String m_127511_() {
        return this.f_127500_;
    }

    public Throwable m_127524_() {
        return this.f_127501_;
    }

    public String m_178625_() {
        StringBuilder $$0 = new StringBuilder();
        this.m_127519_($$0);
        return $$0.toString();
    }

    public void m_127519_(StringBuilder p_127520_) {
        if (!(this.f_127506_ != null && this.f_127506_.length > 0 || this.f_127503_.isEmpty())) {
            this.f_127506_ = (StackTraceElement[])ArrayUtils.subarray((Object[])this.f_127503_.get(0).m_128143_(), (int)0, (int)1);
        }
        if (this.f_127506_ != null && this.f_127506_.length > 0) {
            p_127520_.append("-- Head --\n");
            p_127520_.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
            p_127520_.append("Stacktrace:\n");
            for (StackTraceElement $$1 : this.f_127506_) {
                p_127520_.append("\t").append("at ").append($$1);
                p_127520_.append("\n");
            }
            p_127520_.append("\n");
        }
        for (CrashReportCategory $$2 : this.f_127503_) {
            $$2.m_128168_(p_127520_);
            p_127520_.append("\n\n");
        }
        this.f_178624_.m_143525_(p_127520_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String m_127525_() {
        String string;
        StringWriter $$0 = null;
        PrintWriter $$1 = null;
        Throwable $$2 = this.f_127501_;
        if ($$2.getMessage() == null) {
            if ($$2 instanceof NullPointerException) {
                $$2 = new NullPointerException(this.f_127500_);
            } else if ($$2 instanceof StackOverflowError) {
                $$2 = new StackOverflowError(this.f_127500_);
            } else if ($$2 instanceof OutOfMemoryError) {
                $$2 = new OutOfMemoryError(this.f_127500_);
            }
            $$2.setStackTrace(this.f_127501_.getStackTrace());
        }
        try {
            $$0 = new StringWriter();
            $$1 = new PrintWriter($$0);
            $$2.printStackTrace($$1);
            string = $$0.toString();
        }
        catch (Throwable throwable) {
            IOUtils.closeQuietly((Writer)$$0);
            IOUtils.closeQuietly($$1);
            throw throwable;
        }
        IOUtils.closeQuietly((Writer)$$0);
        IOUtils.closeQuietly((Writer)$$1);
        return string;
    }

    public String m_127526_() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append("---- Minecraft Crash Report ----\n");
        $$0.append("// ");
        $$0.append(CrashReport.m_127531_());
        $$0.append("\n\n");
        $$0.append("Time: ");
        $$0.append(f_241641_.format(ZonedDateTime.now()));
        $$0.append("\n");
        $$0.append("Description: ");
        $$0.append(this.f_127500_);
        $$0.append("\n\n");
        $$0.append(this.m_127525_());
        $$0.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int $$1 = 0; $$1 < 87; ++$$1) {
            $$0.append("-");
        }
        $$0.append("\n\n");
        this.m_127519_($$0);
        return $$0.toString();
    }

    public File m_127527_() {
        return this.f_127504_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean m_127512_(File p_127513_) {
        boolean bl;
        if (this.f_127504_ != null) {
            return false;
        }
        if (p_127513_.getParentFile() != null) {
            p_127513_.getParentFile().mkdirs();
        }
        OutputStreamWriter $$1 = null;
        try {
            $$1 = new OutputStreamWriter((OutputStream)new FileOutputStream(p_127513_), StandardCharsets.UTF_8);
            $$1.write(this.m_127526_());
            this.f_127504_ = p_127513_;
            bl = true;
        }
        catch (Throwable $$2) {
            boolean bl2;
            try {
                f_127499_.error("Could not save crash report to {}", (Object)p_127513_, (Object)$$2);
                bl2 = false;
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly($$1);
                throw throwable;
            }
            IOUtils.closeQuietly((Writer)$$1);
            return bl2;
        }
        IOUtils.closeQuietly((Writer)$$1);
        return bl;
    }

    public SystemReport m_178626_() {
        return this.f_178624_;
    }

    public CrashReportCategory m_127514_(String p_127515_) {
        return this.m_127516_(p_127515_, 1);
    }

    public CrashReportCategory m_127516_(String p_127517_, int p_127518_) {
        CrashReportCategory $$2 = new CrashReportCategory(p_127517_);
        if (this.f_127505_) {
            int $$3 = $$2.m_128148_(p_127518_);
            StackTraceElement[] $$4 = this.f_127501_.getStackTrace();
            StackTraceElement $$5 = null;
            StackTraceElement $$6 = null;
            int $$7 = $$4.length - $$3;
            if ($$7 < 0) {
                System.out.println("Negative index in crash report handler (" + $$4.length + "/" + $$3 + ")");
            }
            if ($$4 != null && 0 <= $$7 && $$7 < $$4.length) {
                $$5 = $$4[$$7];
                if ($$4.length + 1 - $$3 < $$4.length) {
                    $$6 = $$4[$$4.length + 1 - $$3];
                }
            }
            this.f_127505_ = $$2.m_128156_($$5, $$6);
            if ($$4 != null && $$4.length >= $$3 && 0 <= $$7 && $$7 < $$4.length) {
                this.f_127506_ = new StackTraceElement[$$7];
                System.arraycopy($$4, 0, this.f_127506_, 0, this.f_127506_.length);
            } else {
                this.f_127505_ = false;
            }
        }
        this.f_127503_.add($$2);
        return $$2;
    }

    private static String m_127531_() {
        String[] $$0 = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};
        try {
            return $$0[(int)(Util.m_137569_() % (long)$$0.length)];
        }
        catch (Throwable $$1) {
            return "Witty comment unavailable :(";
        }
    }

    public static CrashReport m_127521_(Throwable p_127522_, String p_127523_) {
        CrashReport $$3;
        while (p_127522_ instanceof CompletionException && p_127522_.getCause() != null) {
            p_127522_ = p_127522_.getCause();
        }
        if (p_127522_ instanceof ReportedException) {
            CrashReport $$2 = ((ReportedException)p_127522_).m_134761_();
        } else {
            $$3 = new CrashReport(p_127523_, p_127522_);
        }
        return $$3;
    }

    public static void m_127529_() {
        MemoryReserve.m_182327_();
        new CrashReport("Don't panic!", new Throwable()).m_127526_();
    }
}

