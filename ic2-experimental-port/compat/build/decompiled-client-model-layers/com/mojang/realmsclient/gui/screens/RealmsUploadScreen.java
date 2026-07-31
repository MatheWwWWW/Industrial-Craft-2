/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.RateLimiter
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.compress.archivers.ArchiveEntry
 *  org.apache.commons.compress.archivers.tar.TarArchiveEntry
 *  org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
 *  org.apache.commons.compress.utils.IOUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.Unit;
import com.mojang.realmsclient.client.FileUpload;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.client.UploadStatus;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.util.UploadTokenCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;

public class RealmsUploadScreen
extends RealmsScreen {
    private static final Logger f_90067_ = LogUtils.getLogger();
    private static final ReentrantLock f_90068_ = new ReentrantLock();
    private static final String[] f_90069_ = new String[]{"", ".", ". .", ". . ."};
    private static final Component f_90070_ = Component.m_237115_("mco.upload.verifying");
    private final RealmsResetWorldScreen f_90071_;
    private final LevelSummary f_90072_;
    private final long f_90073_;
    private final int f_90074_;
    private final UploadStatus f_90075_;
    private final RateLimiter f_90076_;
    @Nullable
    private volatile Component[] f_90077_;
    private volatile Component f_90078_ = Component.m_237115_("mco.upload.preparing");
    private volatile String f_90079_;
    private volatile boolean f_90080_;
    private volatile boolean f_90057_;
    private volatile boolean f_90058_ = true;
    private volatile boolean f_90059_;
    private Button f_90060_;
    private Button f_90061_;
    private int f_90062_;
    @Nullable
    private Long f_90063_;
    @Nullable
    private Long f_90064_;
    private long f_90065_;
    private final Runnable f_90066_;

    public RealmsUploadScreen(long p_90083_, int p_90084_, RealmsResetWorldScreen p_90085_, LevelSummary p_90086_, Runnable p_90087_) {
        super(GameNarrator.f_93310_);
        this.f_90073_ = p_90083_;
        this.f_90074_ = p_90084_;
        this.f_90071_ = p_90085_;
        this.f_90072_ = p_90086_;
        this.f_90075_ = new UploadStatus();
        this.f_90076_ = RateLimiter.create((double)0.1f);
        this.f_90066_ = p_90087_;
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_90060_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 42, 200, 20, CommonComponents.f_130660_, p_90118_ -> this.m_90127_()));
        this.f_90060_.f_93624_ = false;
        this.f_90061_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 42, 200, 20, CommonComponents.f_130656_, p_90104_ -> this.m_90128_()));
        if (!this.f_90059_) {
            if (this.f_90071_.f_89313_ == -1) {
                this.m_90129_();
            } else {
                this.f_90071_.m_89382_(() -> {
                    if (!this.f_90059_) {
                        this.f_90059_ = true;
                        this.f_96541_.m_91152_(this);
                        this.m_90129_();
                    }
                });
            }
        }
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_90127_() {
        this.f_90066_.run();
    }

    private void m_90128_() {
        this.f_90080_ = true;
        this.f_96541_.m_91152_(this.f_90071_);
    }

    @Override
    public boolean m_7933_(int p_90089_, int p_90090_, int p_90091_) {
        if (p_90089_ == 256) {
            if (this.f_90058_) {
                this.m_90128_();
            } else {
                this.m_90127_();
            }
            return true;
        }
        return super.m_7933_(p_90089_, p_90090_, p_90091_);
    }

    @Override
    public void m_6305_(PoseStack p_90096_, int p_90097_, int p_90098_, float p_90099_) {
        this.m_7333_(p_90096_);
        if (!this.f_90057_ && this.f_90075_.f_87386_ != 0L && this.f_90075_.f_87386_ == this.f_90075_.f_87387_) {
            this.f_90078_ = f_90070_;
            this.f_90061_.f_93623_ = false;
        }
        RealmsUploadScreen.m_93215_(p_90096_, this.f_96547_, this.f_90078_, this.f_96543_ / 2, 50, 0xFFFFFF);
        if (this.f_90058_) {
            this.m_90115_(p_90096_);
        }
        if (this.f_90075_.f_87386_ != 0L && !this.f_90080_) {
            this.m_90121_(p_90096_);
            this.m_90124_(p_90096_);
        }
        if (this.f_90077_ != null) {
            for (int $$4 = 0; $$4 < this.f_90077_.length; ++$$4) {
                RealmsUploadScreen.m_93215_(p_90096_, this.f_96547_, this.f_90077_[$$4], this.f_96543_ / 2, 110 + 12 * $$4, 0xFF0000);
            }
        }
        super.m_6305_(p_90096_, p_90097_, p_90098_, p_90099_);
    }

    private void m_90115_(PoseStack p_90116_) {
        int $$1 = this.f_96547_.m_92852_(this.f_90078_);
        this.f_96547_.m_92883_(p_90116_, f_90069_[this.f_90062_ / 10 % f_90069_.length], this.f_96543_ / 2 + $$1 / 2 + 5, 50.0f, 0xFFFFFF);
    }

    private void m_90121_(PoseStack p_90122_) {
        double $$1 = Math.min((double)this.f_90075_.f_87386_ / (double)this.f_90075_.f_87387_, 1.0);
        this.f_90079_ = String.format(Locale.ROOT, "%.1f", $$1 * 100.0);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69472_();
        double $$2 = this.f_96543_ / 2 - 100;
        double $$3 = 0.5;
        Tesselator $$4 = Tesselator.m_85913_();
        BufferBuilder $$5 = $$4.m_85915_();
        $$5.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        $$5.m_5483_($$2 - 0.5, 95.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$5.m_5483_($$2 + 200.0 * $$1 + 0.5, 95.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$5.m_5483_($$2 + 200.0 * $$1 + 0.5, 79.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$5.m_5483_($$2 - 0.5, 79.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$5.m_5483_($$2, 95.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$5.m_5483_($$2 + 200.0 * $$1, 95.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$5.m_5483_($$2 + 200.0 * $$1, 80.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$5.m_5483_($$2, 80.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$4.m_85914_();
        RenderSystem.m_69493_();
        RealmsUploadScreen.m_93208_(p_90122_, this.f_96547_, this.f_90079_ + " %", this.f_96543_ / 2, 84, 0xFFFFFF);
    }

    private void m_90124_(PoseStack p_90125_) {
        if (this.f_90062_ % 20 == 0) {
            if (this.f_90063_ != null) {
                long $$1 = Util.m_137550_() - this.f_90064_;
                if ($$1 == 0L) {
                    $$1 = 1L;
                }
                this.f_90065_ = 1000L * (this.f_90075_.f_87386_ - this.f_90063_) / $$1;
                this.m_90100_(p_90125_, this.f_90065_);
            }
            this.f_90063_ = this.f_90075_.f_87386_;
            this.f_90064_ = Util.m_137550_();
        } else {
            this.m_90100_(p_90125_, this.f_90065_);
        }
    }

    private void m_90100_(PoseStack p_90101_, long p_90102_) {
        if (p_90102_ > 0L) {
            int $$2 = this.f_96547_.m_92895_(this.f_90079_);
            String $$3 = "(" + Unit.m_86945_(p_90102_) + "/s)";
            this.f_96547_.m_92883_(p_90101_, $$3, this.f_96543_ / 2 + $$2 / 2 + 15, 84.0f, 0xFFFFFF);
        }
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        ++this.f_90062_;
        if (this.f_90078_ != null && this.f_90076_.tryAcquire(1)) {
            Component $$0 = this.m_167558_();
            this.f_96541_.m_240477_().m_168785_($$0);
        }
    }

    private Component m_167558_() {
        ArrayList $$0 = Lists.newArrayList();
        $$0.add(this.f_90078_);
        if (this.f_90079_ != null) {
            $$0.add(Component.m_237113_(this.f_90079_ + "%"));
        }
        if (this.f_90077_ != null) {
            $$0.addAll(Arrays.asList(this.f_90077_));
        }
        return CommonComponents.m_178391_($$0);
    }

    private void m_90129_() {
        this.f_90059_ = true;
        new Thread(() -> {
            File $$0 = null;
            RealmsClient $$1 = RealmsClient.m_87169_();
            long $$2 = this.f_90073_;
            try {
                if (!f_90068_.tryLock(1L, TimeUnit.SECONDS)) {
                    this.f_90078_ = Component.m_237115_("mco.upload.close.failure");
                    return;
                }
                UploadInfo $$3 = null;
                for (int $$4 = 0; $$4 < 20; ++$$4) {
                    block35: {
                        if (!this.f_90080_) break block35;
                        this.m_90130_();
                        return;
                    }
                    try {
                        $$3 = $$1.m_87256_($$2, UploadTokenCache.m_90292_($$2));
                        if ($$3 == null) continue;
                        break;
                    }
                    catch (RetryCallException $$5) {
                        Thread.sleep($$5.f_87787_ * 1000);
                    }
                }
                if ($$3 == null) {
                    this.f_90078_ = Component.m_237115_("mco.upload.close.failure");
                    return;
                }
                UploadTokenCache.m_90294_($$2, $$3.m_87696_());
                if (!$$3.m_87711_()) {
                    this.f_90078_ = Component.m_237115_("mco.upload.close.failure");
                    return;
                }
                if (this.f_90080_) {
                    this.m_90130_();
                    return;
                }
                File $$6 = new File(this.f_96541_.f_91069_.getAbsolutePath(), "saves");
                $$0 = this.m_90119_(new File($$6, this.f_90072_.m_78358_()));
                if (this.f_90080_) {
                    this.m_90130_();
                    return;
                }
                if (!this.m_90105_($$0)) {
                    long $$7 = $$0.length();
                    Unit $$8 = Unit.m_86940_($$7);
                    Unit $$9 = Unit.m_86940_(0x140000000L);
                    if (Unit.m_86947_($$7, $$8).equals(Unit.m_86947_(0x140000000L, $$9)) && $$8 != Unit.B) {
                        Unit $$10 = Unit.values()[$$8.ordinal() - 1];
                        this.m_90112_(Component.m_237110_("mco.upload.size.failure.line1", this.f_90072_.m_78361_()), Component.m_237110_("mco.upload.size.failure.line2", Unit.m_86947_($$7, $$10), Unit.m_86947_(0x140000000L, $$10)));
                        return;
                    }
                    this.m_90112_(Component.m_237110_("mco.upload.size.failure.line1", this.f_90072_.m_78361_()), Component.m_237110_("mco.upload.size.failure.line2", Unit.m_86947_($$7, $$8), Unit.m_86947_(0x140000000L, $$9)));
                    return;
                }
                this.f_90078_ = Component.m_237110_("mco.upload.uploading", this.f_90072_.m_78361_());
                FileUpload $$11 = new FileUpload($$0, this.f_90073_, this.f_90074_, $$3, this.f_96541_.m_91094_(), SharedConstants.m_183709_().getName(), this.f_90075_);
                $$11.m_87084_(p_167557_ -> {
                    if (p_167557_.f_90133_ >= 200 && p_167557_.f_90133_ < 300) {
                        this.f_90057_ = true;
                        this.f_90078_ = Component.m_237115_("mco.upload.done");
                        this.f_90060_.m_93666_(CommonComponents.f_130655_);
                        UploadTokenCache.m_90297_($$2);
                    } else if (p_167557_.f_90133_ == 400 && p_167557_.f_90134_ != null) {
                        this.m_90112_(Component.m_237110_("mco.upload.failed", p_167557_.f_90134_));
                    } else {
                        this.m_90112_(Component.m_237110_("mco.upload.failed", p_167557_.f_90133_));
                    }
                });
                while (!$$11.m_87096_()) {
                    if (this.f_90080_) {
                        $$11.m_87078_();
                        this.m_90130_();
                        return;
                    }
                    try {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException $$12) {
                        f_90067_.error("Failed to check Realms file upload status");
                    }
                }
            }
            catch (IOException $$13) {
                this.m_90112_(Component.m_237110_("mco.upload.failed", $$13.getMessage()));
            }
            catch (RealmsServiceException $$14) {
                this.m_90112_(Component.m_237110_("mco.upload.failed", $$14.toString()));
            }
            catch (InterruptedException $$15) {
                f_90067_.error("Could not acquire upload lock");
            }
            finally {
                this.f_90057_ = true;
                if (!f_90068_.isHeldByCurrentThread()) {
                    return;
                }
                f_90068_.unlock();
                this.f_90058_ = false;
                this.f_90060_.f_93624_ = true;
                this.f_90061_.f_93624_ = false;
                if ($$0 != null) {
                    f_90067_.debug("Deleting file {}", (Object)$$0.getAbsolutePath());
                    $$0.delete();
                }
            }
        }).start();
    }

    private void m_90112_(Component ... p_90113_) {
        this.f_90077_ = p_90113_;
    }

    private void m_90130_() {
        this.f_90078_ = Component.m_237115_("mco.upload.cancelled");
        f_90067_.debug("Upload was cancelled");
    }

    private boolean m_90105_(File p_90106_) {
        return p_90106_.length() < 0x140000000L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private File m_90119_(File p_90120_) throws IOException {
        try (TarArchiveOutputStream $$1 = null;){
            File $$2 = File.createTempFile("realms-upload-file", ".tar.gz");
            $$1 = new TarArchiveOutputStream((OutputStream)new GZIPOutputStream(new FileOutputStream($$2)));
            $$1.setLongFileMode(3);
            this.m_90107_($$1, p_90120_.getAbsolutePath(), "world", true);
            $$1.finish();
            File file = $$2;
            return file;
        }
    }

    private void m_90107_(TarArchiveOutputStream p_90108_, String p_90109_, String p_90110_, boolean p_90111_) throws IOException {
        if (this.f_90080_) {
            return;
        }
        File $$4 = new File(p_90109_);
        String $$5 = p_90111_ ? p_90110_ : p_90110_ + $$4.getName();
        TarArchiveEntry $$6 = new TarArchiveEntry($$4, $$5);
        p_90108_.putArchiveEntry((ArchiveEntry)$$6);
        if ($$4.isFile()) {
            IOUtils.copy((InputStream)new FileInputStream($$4), (OutputStream)p_90108_);
            p_90108_.closeArchiveEntry();
        } else {
            p_90108_.closeArchiveEntry();
            File[] $$7 = $$4.listFiles();
            if ($$7 != null) {
                for (File $$8 : $$7) {
                    this.m_90107_(p_90108_, $$8.getAbsolutePath(), $$5 + "/", false);
                }
            }
        }
    }
}

