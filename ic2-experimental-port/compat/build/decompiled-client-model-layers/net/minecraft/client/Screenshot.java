/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.slf4j.Logger;

public class Screenshot {
    private static final Logger f_92276_ = LogUtils.getLogger();
    private int f_168594_;
    private final DataOutputStream f_168595_;
    private final byte[] f_168596_;
    private final int f_168597_;
    private final int f_168598_;
    private File f_168599_;

    public static void m_92289_(File p_92290_, RenderTarget p_92293_, Consumer<Component> p_92294_) {
        Screenshot.m_92295_(p_92290_, null, p_92293_, p_92294_);
    }

    public static void m_92295_(File p_92296_, @Nullable String p_92297_, RenderTarget p_92300_, Consumer<Component> p_92301_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> Screenshot.m_92305_(p_92296_, p_92297_, p_92300_, p_92301_));
        } else {
            Screenshot.m_92305_(p_92296_, p_92297_, p_92300_, p_92301_);
        }
    }

    private static void m_92305_(File p_92306_, @Nullable String p_92307_, RenderTarget p_92310_, Consumer<Component> p_92311_) {
        File $$7;
        NativeImage $$4 = Screenshot.m_92279_(p_92310_);
        File $$5 = new File(p_92306_, "screenshots");
        $$5.mkdir();
        if (p_92307_ == null) {
            File $$6 = Screenshot.m_92287_($$5);
        } else {
            $$7 = new File($$5, p_92307_);
        }
        Util.m_183992_().execute(() -> {
            try {
                $$4.m_85056_($$7);
                MutableComponent $$3 = Component.m_237113_($$7.getName()).m_130940_(ChatFormatting.UNDERLINE).m_130938_(p_168608_ -> p_168608_.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_FILE, $$7.getAbsolutePath())));
                p_92311_.accept(Component.m_237110_("screenshot.success", $$3));
            }
            catch (Exception $$4) {
                f_92276_.warn("Couldn't save screenshot", (Throwable)$$4);
                p_92311_.accept(Component.m_237110_("screenshot.failure", $$4.getMessage()));
            }
            finally {
                $$4.close();
            }
        });
    }

    public static NativeImage m_92279_(RenderTarget p_92282_) {
        int $$1 = p_92282_.f_83915_;
        int $$2 = p_92282_.f_83916_;
        NativeImage $$3 = new NativeImage($$1, $$2, false);
        RenderSystem.m_69396_(p_92282_.m_83975_());
        $$3.m_85045_(0, true);
        $$3.m_85122_();
        return $$3;
    }

    private static File m_92287_(File p_92288_) {
        String $$1 = Util.m_241986_();
        int $$2 = 1;
        File $$3;
        while (($$3 = new File(p_92288_, $$1 + (String)($$2 == 1 ? "" : "_" + $$2) + ".png")).exists()) {
            ++$$2;
        }
        return $$3;
    }

    public Screenshot(File p_168601_, int p_168602_, int p_168603_, int p_168604_) throws IOException {
        this.f_168597_ = p_168602_;
        this.f_168598_ = p_168603_;
        this.f_168594_ = p_168604_;
        File $$4 = new File(p_168601_, "screenshots");
        $$4.mkdir();
        String $$5 = "huge_" + Util.m_241986_();
        int $$6 = 1;
        while ((this.f_168599_ = new File($$4, $$5 + (String)($$6 == 1 ? "" : "_" + $$6) + ".tga")).exists()) {
            ++$$6;
        }
        byte[] $$7 = new byte[18];
        $$7[2] = 2;
        $$7[12] = (byte)(p_168602_ % 256);
        $$7[13] = (byte)(p_168602_ / 256);
        $$7[14] = (byte)(p_168603_ % 256);
        $$7[15] = (byte)(p_168603_ / 256);
        $$7[16] = 24;
        this.f_168596_ = new byte[p_168602_ * p_168604_ * 3];
        this.f_168595_ = new DataOutputStream(new FileOutputStream(this.f_168599_));
        this.f_168595_.write($$7);
    }

    public void m_168609_(ByteBuffer p_168610_, int p_168611_, int p_168612_, int p_168613_, int p_168614_) {
        int $$5 = p_168613_;
        int $$6 = p_168614_;
        if ($$5 > this.f_168597_ - p_168611_) {
            $$5 = this.f_168597_ - p_168611_;
        }
        if ($$6 > this.f_168598_ - p_168612_) {
            $$6 = this.f_168598_ - p_168612_;
        }
        this.f_168594_ = $$6;
        for (int $$7 = 0; $$7 < $$6; ++$$7) {
            p_168610_.position((p_168614_ - $$6) * p_168613_ * 3 + $$7 * p_168613_ * 3);
            int $$8 = (p_168611_ + $$7 * this.f_168597_) * 3;
            p_168610_.get(this.f_168596_, $$8, $$5 * 3);
        }
    }

    public void m_168605_() throws IOException {
        this.f_168595_.write(this.f_168596_, 0, this.f_168597_ * 3 * this.f_168594_);
    }

    public File m_168615_() throws IOException {
        this.f_168595_.close();
        return this.f_168599_;
    }
}

