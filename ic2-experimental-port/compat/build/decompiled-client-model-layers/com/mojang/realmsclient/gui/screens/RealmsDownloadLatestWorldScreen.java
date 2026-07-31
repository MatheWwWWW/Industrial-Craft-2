/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.RateLimiter
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  javax.annotation.Nullable
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
import com.mojang.realmsclient.client.FileDownload;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsScreen;
import org.slf4j.Logger;

public class RealmsDownloadLatestWorldScreen
extends RealmsScreen {
    private static final Logger f_88609_ = LogUtils.getLogger();
    private static final ReentrantLock f_88610_ = new ReentrantLock();
    private final Screen f_88611_;
    private final WorldDownload f_88612_;
    private final Component f_88613_;
    private final RateLimiter f_88614_;
    private Button f_88615_;
    private final String f_88616_;
    private final DownloadStatus f_88617_;
    @Nullable
    private volatile Component f_88618_;
    private volatile Component f_88619_ = Component.m_237115_("mco.download.preparing");
    @Nullable
    private volatile String f_88620_;
    private volatile boolean f_88621_;
    private volatile boolean f_88622_ = true;
    private volatile boolean f_88599_;
    private volatile boolean f_88600_;
    @Nullable
    private Long f_88601_;
    @Nullable
    private Long f_88602_;
    private long f_88603_;
    private int f_88604_;
    private static final String[] f_88605_ = new String[]{"", ".", ". .", ". . ."};
    private int f_88606_;
    private boolean f_88607_;
    private final BooleanConsumer f_88608_;

    public RealmsDownloadLatestWorldScreen(Screen p_88625_, WorldDownload p_88626_, String p_88627_, BooleanConsumer p_88628_) {
        super(GameNarrator.f_93310_);
        this.f_88608_ = p_88628_;
        this.f_88611_ = p_88625_;
        this.f_88616_ = p_88627_;
        this.f_88612_ = p_88626_;
        this.f_88617_ = new DownloadStatus();
        this.f_88613_ = Component.m_237115_("mco.download.title");
        this.f_88614_ = RateLimiter.create((double)0.1f);
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_88615_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 42, 200, 20, CommonComponents.f_130656_, p_88642_ -> {
            this.f_88621_ = true;
            this.m_88656_();
        }));
        this.m_88655_();
    }

    private void m_88655_() {
        if (this.f_88599_) {
            return;
        }
        if (!this.f_88607_ && this.m_88646_(this.f_88612_.f_87718_) >= 0x140000000L) {
            MutableComponent $$0 = Component.m_237110_("mco.download.confirmation.line1", Unit.m_86945_(0x140000000L));
            MutableComponent $$1 = Component.m_237115_("mco.download.confirmation.line2");
            this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(p_88651_ -> {
                this.f_88607_ = true;
                this.f_96541_.m_91152_(this);
                this.m_88657_();
            }, RealmsLongConfirmationScreen.Type.Warning, $$0, $$1, false));
        } else {
            this.m_88657_();
        }
    }

    private long m_88646_(String p_88647_) {
        FileDownload $$1 = new FileDownload();
        return $$1.m_86989_(p_88647_);
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        ++this.f_88604_;
        if (this.f_88619_ != null && this.f_88614_.tryAcquire(1)) {
            Component $$0 = this.m_167409_();
            this.f_96541_.m_240477_().m_168785_($$0);
        }
    }

    private Component m_167409_() {
        ArrayList $$0 = Lists.newArrayList();
        $$0.add(this.f_88613_);
        $$0.add(this.f_88619_);
        if (this.f_88620_ != null) {
            $$0.add(Component.m_237113_(this.f_88620_ + "%"));
            $$0.add(Component.m_237113_(Unit.m_86945_(this.f_88603_) + "/s"));
        }
        if (this.f_88618_ != null) {
            $$0.add(this.f_88618_);
        }
        return CommonComponents.m_178391_($$0);
    }

    @Override
    public boolean m_7933_(int p_88630_, int p_88631_, int p_88632_) {
        if (p_88630_ == 256) {
            this.f_88621_ = true;
            this.m_88656_();
            return true;
        }
        return super.m_7933_(p_88630_, p_88631_, p_88632_);
    }

    private void m_88656_() {
        if (this.f_88599_ && this.f_88608_ != null && this.f_88618_ == null) {
            this.f_88608_.accept(true);
        }
        this.f_96541_.m_91152_(this.f_88611_);
    }

    @Override
    public void m_6305_(PoseStack p_88634_, int p_88635_, int p_88636_, float p_88637_) {
        this.m_7333_(p_88634_);
        RealmsDownloadLatestWorldScreen.m_93215_(p_88634_, this.f_96547_, this.f_88613_, this.f_96543_ / 2, 20, 0xFFFFFF);
        RealmsDownloadLatestWorldScreen.m_93215_(p_88634_, this.f_96547_, this.f_88619_, this.f_96543_ / 2, 50, 0xFFFFFF);
        if (this.f_88622_) {
            this.m_88644_(p_88634_);
        }
        if (this.f_88617_.f_88660_ != 0L && !this.f_88621_) {
            this.m_88648_(p_88634_);
            this.m_88653_(p_88634_);
        }
        if (this.f_88618_ != null) {
            RealmsDownloadLatestWorldScreen.m_93215_(p_88634_, this.f_96547_, this.f_88618_, this.f_96543_ / 2, 110, 0xFF0000);
        }
        super.m_6305_(p_88634_, p_88635_, p_88636_, p_88637_);
    }

    private void m_88644_(PoseStack p_88645_) {
        int $$1 = this.f_96547_.m_92852_(this.f_88619_);
        if (this.f_88604_ % 10 == 0) {
            ++this.f_88606_;
        }
        this.f_96547_.m_92883_(p_88645_, f_88605_[this.f_88606_ % f_88605_.length], this.f_96543_ / 2 + $$1 / 2 + 5, 50.0f, 0xFFFFFF);
    }

    private void m_88648_(PoseStack p_88649_) {
        double $$1 = Math.min((double)this.f_88617_.f_88660_ / (double)this.f_88617_.f_88661_, 1.0);
        this.f_88620_ = String.format(Locale.ROOT, "%.1f", $$1 * 100.0);
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69472_();
        Tesselator $$2 = Tesselator.m_85913_();
        BufferBuilder $$3 = $$2.m_85915_();
        $$3.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        double $$4 = this.f_96543_ / 2 - 100;
        double $$5 = 0.5;
        $$3.m_5483_($$4 - 0.5, 95.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$3.m_5483_($$4 + 200.0 * $$1 + 0.5, 95.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$3.m_5483_($$4 + 200.0 * $$1 + 0.5, 79.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$3.m_5483_($$4 - 0.5, 79.5, 0.0).m_6122_(217, 210, 210, 255).m_5752_();
        $$3.m_5483_($$4, 95.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$3.m_5483_($$4 + 200.0 * $$1, 95.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$3.m_5483_($$4 + 200.0 * $$1, 80.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$3.m_5483_($$4, 80.0, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$2.m_85914_();
        RenderSystem.m_69493_();
        RealmsDownloadLatestWorldScreen.m_93208_(p_88649_, this.f_96547_, this.f_88620_ + " %", this.f_96543_ / 2, 84, 0xFFFFFF);
    }

    private void m_88653_(PoseStack p_88654_) {
        if (this.f_88604_ % 20 == 0) {
            if (this.f_88601_ != null) {
                long $$1 = Util.m_137550_() - this.f_88602_;
                if ($$1 == 0L) {
                    $$1 = 1L;
                }
                this.f_88603_ = 1000L * (this.f_88617_.f_88660_ - this.f_88601_) / $$1;
                this.m_88638_(p_88654_, this.f_88603_);
            }
            this.f_88601_ = this.f_88617_.f_88660_;
            this.f_88602_ = Util.m_137550_();
        } else {
            this.m_88638_(p_88654_, this.f_88603_);
        }
    }

    private void m_88638_(PoseStack p_88639_, long p_88640_) {
        if (p_88640_ > 0L) {
            int $$2 = this.f_96547_.m_92895_(this.f_88620_);
            String $$3 = "(" + Unit.m_86945_(p_88640_) + "/s)";
            this.f_96547_.m_92883_(p_88639_, $$3, this.f_96543_ / 2 + $$2 / 2 + 15, 84.0f, 0xFFFFFF);
        }
    }

    private void m_88657_() {
        new Thread(() -> {
            try {
                if (!f_88610_.tryLock(1L, TimeUnit.SECONDS)) {
                    this.f_88619_ = Component.m_237115_("mco.download.failed");
                    return;
                }
                if (this.f_88621_) {
                    this.m_88658_();
                    return;
                }
                this.f_88619_ = Component.m_237110_("mco.download.downloading", this.f_88616_);
                FileDownload $$0 = new FileDownload();
                $$0.m_86989_(this.f_88612_.f_87718_);
                $$0.m_86982_(this.f_88612_, this.f_88616_, this.f_88617_, this.f_96541_.m_91392_());
                while (!$$0.m_86995_()) {
                    if ($$0.m_87003_()) {
                        $$0.m_86966_();
                        this.f_88618_ = Component.m_237115_("mco.download.failed");
                        this.f_88615_.m_93666_(CommonComponents.f_130655_);
                        return;
                    }
                    if ($$0.m_87009_()) {
                        if (!this.f_88600_) {
                            this.f_88619_ = Component.m_237115_("mco.download.extracting");
                        }
                        this.f_88600_ = true;
                    }
                    if (this.f_88621_) {
                        $$0.m_86966_();
                        this.m_88658_();
                        return;
                    }
                    try {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException $$1) {
                        f_88609_.error("Failed to check Realms backup download status");
                    }
                }
                this.f_88599_ = true;
                this.f_88619_ = Component.m_237115_("mco.download.done");
                this.f_88615_.m_93666_(CommonComponents.f_130655_);
            }
            catch (InterruptedException $$2) {
                f_88609_.error("Could not acquire upload lock");
            }
            catch (Exception $$3) {
                this.f_88618_ = Component.m_237115_("mco.download.failed");
                $$3.printStackTrace();
            }
            finally {
                if (!f_88610_.isHeldByCurrentThread()) {
                    return;
                }
                f_88610_.unlock();
                this.f_88622_ = false;
                this.f_88599_ = true;
            }
        }).start();
    }

    private void m_88658_() {
        this.f_88619_ = Component.m_237115_("mco.download.cancelled");
    }

    public static class DownloadStatus {
        public volatile long f_88660_;
        public volatile long f_88661_;
    }
}

