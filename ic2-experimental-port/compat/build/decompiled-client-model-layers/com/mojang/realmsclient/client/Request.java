/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.client;

import com.mojang.realmsclient.client.RealmsClientConfig;
import com.mojang.realmsclient.exception.RealmsHttpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;

public abstract class Request<T extends Request<T>> {
    protected HttpURLConnection f_87306_;
    private boolean f_87308_;
    protected String f_87307_;
    private static final int f_167283_ = 60000;
    private static final int f_167284_ = 5000;

    public Request(String p_87310_, int p_87311_, int p_87312_) {
        try {
            this.f_87307_ = p_87310_;
            Proxy $$3 = RealmsClientConfig.m_87292_();
            this.f_87306_ = $$3 != null ? (HttpURLConnection)new URL(p_87310_).openConnection($$3) : (HttpURLConnection)new URL(p_87310_).openConnection();
            this.f_87306_.setConnectTimeout(p_87311_);
            this.f_87306_.setReadTimeout(p_87312_);
        }
        catch (MalformedURLException $$4) {
            throw new RealmsHttpException($$4.getMessage(), $$4);
        }
        catch (IOException $$5) {
            throw new RealmsHttpException($$5.getMessage(), $$5);
        }
    }

    public void m_87322_(String p_87323_, String p_87324_) {
        Request.m_87335_(this.f_87306_, p_87323_, p_87324_);
    }

    public static void m_87335_(HttpURLConnection p_87336_, String p_87337_, String p_87338_) {
        String $$3 = p_87336_.getRequestProperty("Cookie");
        if ($$3 == null) {
            p_87336_.setRequestProperty("Cookie", p_87337_ + "=" + p_87338_);
        } else {
            p_87336_.setRequestProperty("Cookie", $$3 + ";" + p_87337_ + "=" + p_87338_);
        }
    }

    public T m_167285_(String p_167286_, String p_167287_) {
        this.f_87306_.addRequestProperty(p_167286_, p_167287_);
        return (T)this;
    }

    public int m_87313_() {
        return Request.m_87330_(this.f_87306_);
    }

    public static int m_87330_(HttpURLConnection p_87331_) {
        String $$1 = p_87331_.getHeaderField("Retry-After");
        try {
            return Integer.valueOf($$1);
        }
        catch (Exception $$2) {
            return 5;
        }
    }

    public int m_87339_() {
        try {
            this.m_87356_();
            return this.f_87306_.getResponseCode();
        }
        catch (Exception $$0) {
            throw new RealmsHttpException($$0.getMessage(), $$0);
        }
    }

    public String m_87350_() {
        try {
            String $$1;
            this.m_87356_();
            if (this.m_87339_() >= 400) {
                String $$0 = this.m_87314_(this.f_87306_.getErrorStream());
            } else {
                $$1 = this.m_87314_(this.f_87306_.getInputStream());
            }
            this.m_87357_();
            return $$1;
        }
        catch (IOException $$2) {
            throw new RealmsHttpException($$2.getMessage(), $$2);
        }
    }

    private String m_87314_(@Nullable InputStream p_87315_) throws IOException {
        if (p_87315_ == null) {
            return "";
        }
        InputStreamReader $$1 = new InputStreamReader(p_87315_, StandardCharsets.UTF_8);
        StringBuilder $$2 = new StringBuilder();
        int $$3 = $$1.read();
        while ($$3 != -1) {
            $$2.append((char)$$3);
            $$3 = $$1.read();
        }
        return $$2.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void m_87357_() {
        byte[] $$0 = new byte[1024];
        try {
            InputStream $$1 = this.f_87306_.getInputStream();
            while ($$1.read($$0) > 0) {
            }
            $$1.close();
        }
        catch (Exception $$2) {
            InputStream $$3;
            block13: {
                $$3 = this.f_87306_.getErrorStream();
                if ($$3 != null) break block13;
                return;
            }
            try {
                while ($$3.read($$0) > 0) {
                }
                $$3.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        finally {
            if (this.f_87306_ != null) {
                this.f_87306_.disconnect();
            }
        }
    }

    protected T m_87356_() {
        if (this.f_87308_) {
            return (T)this;
        }
        T $$0 = this.m_7218_();
        this.f_87308_ = true;
        return $$0;
    }

    protected abstract T m_7218_();

    public static Request<?> m_87316_(String p_87317_) {
        return new Get(p_87317_, 5000, 60000);
    }

    public static Request<?> m_87318_(String p_87319_, int p_87320_, int p_87321_) {
        return new Get(p_87319_, p_87320_, p_87321_);
    }

    public static Request<?> m_87342_(String p_87343_, String p_87344_) {
        return new Post(p_87343_, p_87344_, 5000, 60000);
    }

    public static Request<?> m_87325_(String p_87326_, String p_87327_, int p_87328_, int p_87329_) {
        return new Post(p_87326_, p_87327_, p_87328_, p_87329_);
    }

    public static Request<?> m_87340_(String p_87341_) {
        return new Delete(p_87341_, 5000, 60000);
    }

    public static Request<?> m_87353_(String p_87354_, String p_87355_) {
        return new Put(p_87354_, p_87355_, 5000, 60000);
    }

    public static Request<?> m_87345_(String p_87346_, String p_87347_, int p_87348_, int p_87349_) {
        return new Put(p_87346_, p_87347_, p_87348_, p_87349_);
    }

    public String m_87351_(String p_87352_) {
        return Request.m_87332_(this.f_87306_, p_87352_);
    }

    public static String m_87332_(HttpURLConnection p_87333_, String p_87334_) {
        try {
            return p_87333_.getHeaderField(p_87334_);
        }
        catch (Exception $$2) {
            return "";
        }
    }

    public static class Get
    extends Request<Get> {
        public Get(String p_87365_, int p_87366_, int p_87367_) {
            super(p_87365_, p_87366_, p_87367_);
        }

        @Override
        public Get m_7218_() {
            try {
                this.f_87306_.setDoInput(true);
                this.f_87306_.setDoOutput(true);
                this.f_87306_.setUseCaches(false);
                this.f_87306_.setRequestMethod("GET");
                return this;
            }
            catch (Exception $$0) {
                throw new RealmsHttpException($$0.getMessage(), $$0);
            }
        }

        @Override
        public /* synthetic */ Request m_7218_() {
            return this.m_7218_();
        }
    }

    public static class Post
    extends Request<Post> {
        private final String f_87370_;

        public Post(String p_87372_, String p_87373_, int p_87374_, int p_87375_) {
            super(p_87372_, p_87374_, p_87375_);
            this.f_87370_ = p_87373_;
        }

        @Override
        public Post m_7218_() {
            try {
                if (this.f_87370_ != null) {
                    this.f_87306_.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                this.f_87306_.setDoInput(true);
                this.f_87306_.setDoOutput(true);
                this.f_87306_.setUseCaches(false);
                this.f_87306_.setRequestMethod("POST");
                OutputStream $$0 = this.f_87306_.getOutputStream();
                OutputStreamWriter $$1 = new OutputStreamWriter($$0, "UTF-8");
                $$1.write(this.f_87370_);
                $$1.close();
                $$0.flush();
                return this;
            }
            catch (Exception $$2) {
                throw new RealmsHttpException($$2.getMessage(), $$2);
            }
        }

        @Override
        public /* synthetic */ Request m_7218_() {
            return this.m_7218_();
        }
    }

    public static class Delete
    extends Request<Delete> {
        public Delete(String p_87359_, int p_87360_, int p_87361_) {
            super(p_87359_, p_87360_, p_87361_);
        }

        @Override
        public Delete m_7218_() {
            try {
                this.f_87306_.setDoOutput(true);
                this.f_87306_.setRequestMethod("DELETE");
                this.f_87306_.connect();
                return this;
            }
            catch (Exception $$0) {
                throw new RealmsHttpException($$0.getMessage(), $$0);
            }
        }

        @Override
        public /* synthetic */ Request m_7218_() {
            return this.m_7218_();
        }
    }

    public static class Put
    extends Request<Put> {
        private final String f_87378_;

        public Put(String p_87380_, String p_87381_, int p_87382_, int p_87383_) {
            super(p_87380_, p_87382_, p_87383_);
            this.f_87378_ = p_87381_;
        }

        @Override
        public Put m_7218_() {
            try {
                if (this.f_87378_ != null) {
                    this.f_87306_.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                this.f_87306_.setDoOutput(true);
                this.f_87306_.setDoInput(true);
                this.f_87306_.setRequestMethod("PUT");
                OutputStream $$0 = this.f_87306_.getOutputStream();
                OutputStreamWriter $$1 = new OutputStreamWriter($$0, "UTF-8");
                $$1.write(this.f_87378_);
                $$1.close();
                $$0.flush();
                return this;
            }
            catch (Exception $$2) {
                throw new RealmsHttpException($$2.getMessage(), $$2);
            }
        }

        @Override
        public /* synthetic */ Request m_7218_() {
            return this.m_7218_();
        }
    }
}

