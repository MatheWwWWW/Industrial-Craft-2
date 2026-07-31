/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.NameValuePair
 *  org.apache.http.client.config.RequestConfig
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpPost
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.entity.InputStreamEntity
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.apache.http.util.Args
 *  org.apache.http.util.EntityUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.UploadStatus;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.gui.screens.UploadResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

public class FileUpload {
    private static final Logger f_87057_ = LogUtils.getLogger();
    private static final int f_167233_ = 5;
    private static final String f_167234_ = "/upload";
    private final File f_87058_;
    private final long f_87059_;
    private final int f_87060_;
    private final UploadInfo f_87061_;
    private final String f_87062_;
    private final String f_87063_;
    private final String f_87064_;
    private final UploadStatus f_87065_;
    private final AtomicBoolean f_87066_ = new AtomicBoolean(false);
    @Nullable
    private CompletableFuture<UploadResult> f_87067_;
    private final RequestConfig f_87068_ = RequestConfig.custom().setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L)).setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L)).build();

    public FileUpload(File p_87071_, long p_87072_, int p_87073_, UploadInfo p_87074_, User p_87075_, String p_87076_, UploadStatus p_87077_) {
        this.f_87058_ = p_87071_;
        this.f_87059_ = p_87072_;
        this.f_87060_ = p_87073_;
        this.f_87061_ = p_87074_;
        this.f_87062_ = p_87075_.m_92544_();
        this.f_87063_ = p_87075_.m_92546_();
        this.f_87064_ = p_87076_;
        this.f_87065_ = p_87077_;
    }

    public void m_87084_(Consumer<UploadResult> p_87085_) {
        if (this.f_87067_ != null) {
            return;
        }
        this.f_87067_ = CompletableFuture.supplyAsync(() -> this.m_87079_(0));
        this.f_87067_.thenAccept((Consumer)p_87085_);
    }

    public void m_87078_() {
        this.f_87066_.set(true);
        if (this.f_87067_ != null) {
            this.f_87067_.cancel(false);
            this.f_87067_ = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private UploadResult m_87079_(int p_87080_) {
        UploadResult.Builder $$1 = new UploadResult.Builder();
        if (this.f_87066_.get()) {
            return $$1.m_90145_();
        }
        this.f_87065_.f_87387_ = this.f_87058_.length();
        HttpPost $$2 = new HttpPost(this.f_87061_.m_87708_().resolve("/upload/" + this.f_87059_ + "/" + this.f_87060_));
        CloseableHttpClient $$3 = HttpClientBuilder.create().setDefaultRequestConfig(this.f_87068_).build();
        try {
            this.m_87091_($$2);
            CloseableHttpResponse $$4 = $$3.execute((HttpUriRequest)$$2);
            long $$5 = this.m_87086_((HttpResponse)$$4);
            if (this.m_87081_($$5, p_87080_)) {
                UploadResult uploadResult = this.m_87097_($$5, p_87080_);
                return uploadResult;
            }
            this.m_87088_((HttpResponse)$$4, $$1);
        }
        catch (Exception $$6) {
            if (!this.f_87066_.get()) {
                f_87057_.error("Caught exception while uploading: ", (Throwable)$$6);
            }
        }
        finally {
            this.m_87093_($$2, $$3);
        }
        return $$1.m_90145_();
    }

    private void m_87093_(HttpPost p_87094_, @Nullable CloseableHttpClient p_87095_) {
        p_87094_.releaseConnection();
        if (p_87095_ != null) {
            try {
                p_87095_.close();
            }
            catch (IOException $$2) {
                f_87057_.error("Failed to close Realms upload client");
            }
        }
    }

    private void m_87091_(HttpPost p_87092_) throws FileNotFoundException {
        p_87092_.setHeader("Cookie", "sid=" + this.f_87062_ + ";token=" + this.f_87061_.m_87696_() + ";user=" + this.f_87063_ + ";version=" + this.f_87064_);
        CustomInputStreamEntity $$1 = new CustomInputStreamEntity(new FileInputStream(this.f_87058_), this.f_87058_.length(), this.f_87065_);
        $$1.setContentType("application/octet-stream");
        p_87092_.setEntity((HttpEntity)$$1);
    }

    private void m_87088_(HttpResponse p_87089_, UploadResult.Builder p_87090_) throws IOException {
        String $$3;
        int $$2 = p_87089_.getStatusLine().getStatusCode();
        if ($$2 == 401) {
            f_87057_.debug("Realms server returned 401: {}", (Object)p_87089_.getFirstHeader("WWW-Authenticate"));
        }
        p_87090_.m_90146_($$2);
        if (p_87089_.getEntity() != null && ($$3 = EntityUtils.toString((HttpEntity)p_87089_.getEntity(), (String)"UTF-8")) != null) {
            try {
                JsonParser $$4 = new JsonParser();
                JsonElement $$5 = $$4.parse($$3).getAsJsonObject().get("errorMsg");
                Optional<String> $$6 = Optional.ofNullable($$5).map(JsonElement::getAsString);
                p_87090_.m_90148_($$6.orElse(null));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private boolean m_87081_(long p_87082_, int p_87083_) {
        return p_87082_ > 0L && p_87083_ + 1 < 5;
    }

    private UploadResult m_87097_(long p_87098_, int p_87099_) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(p_87098_).toMillis());
        return this.m_87079_(p_87099_ + 1);
    }

    private long m_87086_(HttpResponse p_87087_) {
        return Optional.ofNullable(p_87087_.getFirstHeader("Retry-After")).map(NameValuePair::getValue).map(Long::valueOf).orElse(0L);
    }

    public boolean m_87096_() {
        return this.f_87067_.isDone() || this.f_87067_.isCancelled();
    }

    static class CustomInputStreamEntity
    extends InputStreamEntity {
        private final long f_87101_;
        private final InputStream f_87102_;
        private final UploadStatus f_87103_;

        public CustomInputStreamEntity(InputStream p_87105_, long p_87106_, UploadStatus p_87107_) {
            super(p_87105_);
            this.f_87102_ = p_87105_;
            this.f_87101_ = p_87106_;
            this.f_87103_ = p_87107_;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void writeTo(OutputStream p_87109_) throws IOException {
            block7: {
                Args.notNull((Object)p_87109_, (String)"Output stream");
                try (InputStream $$1 = this.f_87102_;){
                    int $$5;
                    byte[] $$2 = new byte[4096];
                    if (this.f_87101_ < 0L) {
                        int $$3;
                        while (($$3 = $$1.read($$2)) != -1) {
                            p_87109_.write($$2, 0, $$3);
                            this.f_87103_.f_87386_ += (long)$$3;
                        }
                        break block7;
                    }
                    for (long $$4 = this.f_87101_; $$4 > 0L; $$4 -= (long)$$5) {
                        $$5 = $$1.read($$2, 0, (int)Math.min(4096L, $$4));
                        if ($$5 == -1) {
                            break;
                        }
                        p_87109_.write($$2, 0, $$5);
                        this.f_87103_.f_87386_ += (long)$$5;
                        p_87109_.flush();
                    }
                }
            }
        }
    }
}

