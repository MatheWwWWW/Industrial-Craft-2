/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.common.io.Files
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.compress.archivers.tar.TarArchiveEntry
 *  org.apache.commons.compress.archivers.tar.TarArchiveInputStream
 *  org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.io.output.CountingOutputStream
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.http.client.config.RequestConfig
 *  org.apache.http.client.methods.CloseableHttpResponse
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.impl.client.CloseableHttpClient
 *  org.apache.http.impl.client.HttpClientBuilder
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.client;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;

public class FileDownload {
    static final Logger f_86953_ = LogUtils.getLogger();
    volatile boolean f_86954_;
    volatile boolean f_86955_;
    volatile boolean f_86956_;
    volatile boolean f_86957_;
    @Nullable
    private volatile File f_86958_;
    volatile File f_86959_;
    @Nullable
    private volatile HttpGet f_86960_;
    @Nullable
    private Thread f_86961_;
    private final RequestConfig f_86962_ = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
    private static final String[] f_86963_ = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long m_86989_(String p_86990_) {
        CloseableHttpClient $$1 = null;
        HttpGet $$2 = null;
        try {
            $$2 = new HttpGet(p_86990_);
            $$1 = HttpClientBuilder.create().setDefaultRequestConfig(this.f_86962_).build();
            CloseableHttpResponse $$3 = $$1.execute((HttpUriRequest)$$2);
            long l = Long.parseLong($$3.getFirstHeader("Content-Length").getValue());
            return l;
        }
        catch (Throwable $$5) {
            f_86953_.error("Unable to get content length for download");
            long l = 0L;
            return l;
        }
        finally {
            if ($$2 != null) {
                $$2.releaseConnection();
            }
            if ($$1 != null) {
                try {
                    $$1.close();
                }
                catch (IOException $$4) {
                    f_86953_.error("Could not close http client", (Throwable)$$4);
                }
            }
        }
    }

    public void m_86982_(WorldDownload p_86983_, String p_86984_, RealmsDownloadLatestWorldScreen.DownloadStatus p_86985_, LevelStorageSource p_86986_) {
        if (this.f_86961_ != null) {
            return;
        }
        this.f_86961_ = new Thread(() -> {
            CloseableHttpClient $$4 = null;
            try {
                this.f_86958_ = File.createTempFile("backup", ".tar.gz");
                this.f_86960_ = new HttpGet(p_86978_.f_87718_);
                $$4 = HttpClientBuilder.create().setDefaultRequestConfig(this.f_86962_).build();
                CloseableHttpResponse $$5 = $$4.execute((HttpUriRequest)this.f_86960_);
                p_86979_.f_88661_ = Long.parseLong($$5.getFirstHeader("Content-Length").getValue());
                if ($$5.getStatusLine().getStatusCode() != 200) {
                    this.f_86956_ = true;
                    this.f_86960_.abort();
                    return;
                }
                FileOutputStream $$12 = new FileOutputStream(this.f_86958_);
                ProgressListener $$13 = new ProgressListener(p_86984_.trim(), this.f_86958_, p_86986_, p_86985_);
                DownloadCountingOutputStream $$14 = new DownloadCountingOutputStream($$12);
                $$14.m_87016_($$13);
                IOUtils.copy((InputStream)$$5.getEntity().getContent(), (OutputStream)((Object)$$14));
                return;
            }
            catch (Exception $$21) {
                f_86953_.error("Caught exception while downloading: {}", (Object)$$21.getMessage());
                this.f_86956_ = true;
                return;
            }
            finally {
                block40: {
                    block41: {
                        CloseableHttpResponse $$22;
                        this.f_86960_.releaseConnection();
                        if (this.f_86958_ != null) {
                            this.f_86958_.delete();
                        }
                        if (this.f_86956_) break block40;
                        if (p_86978_.f_87719_.isEmpty() || p_86978_.f_87720_.isEmpty()) break block41;
                        try {
                            this.f_86958_ = File.createTempFile("resources", ".tar.gz");
                            this.f_86960_ = new HttpGet(p_86978_.f_87719_);
                            $$22 = $$4.execute((HttpUriRequest)this.f_86960_);
                            p_86979_.f_88661_ = Long.parseLong($$22.getFirstHeader("Content-Length").getValue());
                            if ($$22.getStatusLine().getStatusCode() != 200) {
                                this.f_86956_ = true;
                                this.f_86960_.abort();
                                return;
                            }
                        }
                        catch (Exception $$26) {
                            f_86953_.error("Caught exception while downloading: {}", (Object)$$26.getMessage());
                            this.f_86956_ = true;
                        }
                        FileOutputStream $$23 = new FileOutputStream(this.f_86958_);
                        ResourcePackProgressListener $$24 = new ResourcePackProgressListener(this.f_86958_, p_86985_, p_86983_);
                        DownloadCountingOutputStream $$25 = new DownloadCountingOutputStream($$23);
                        $$25.m_87016_($$24);
                        IOUtils.copy((InputStream)$$22.getEntity().getContent(), (OutputStream)((Object)$$25));
                        break block40;
                        finally {
                            this.f_86960_.releaseConnection();
                            if (this.f_86958_ != null) {
                                this.f_86958_.delete();
                            }
                        }
                    }
                    this.f_86955_ = true;
                }
                if ($$4 != null) {
                    try {
                        $$4.close();
                    }
                    catch (IOException $$27) {
                        f_86953_.error("Failed to close Realms download client");
                    }
                }
            }
        });
        this.f_86961_.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(f_86953_));
        this.f_86961_.start();
    }

    public void m_86966_() {
        if (this.f_86960_ != null) {
            this.f_86960_.abort();
        }
        if (this.f_86958_ != null) {
            this.f_86958_.delete();
        }
        this.f_86954_ = true;
    }

    public boolean m_86995_() {
        return this.f_86955_;
    }

    public boolean m_87003_() {
        return this.f_86956_;
    }

    public boolean m_87009_() {
        return this.f_86957_;
    }

    public static String m_87001_(String p_87002_) {
        p_87002_ = ((String)p_87002_).replaceAll("[\\./\"]", "_");
        for (String $$1 : f_86963_) {
            if (!((String)p_87002_).equalsIgnoreCase($$1)) continue;
            p_87002_ = "_" + (String)p_87002_ + "_";
        }
        return p_87002_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void m_86991_(String p_86992_, @Nullable File p_86993_, LevelStorageSource p_86994_) throws IOException {
        String $$13;
        Pattern $$3 = Pattern.compile(".*-([0-9]+)$");
        int $$4 = 1;
        for (char $$5 : SharedConstants.f_136184_) {
            p_86992_ = p_86992_.replace($$5, '_');
        }
        if (StringUtils.isEmpty((CharSequence)p_86992_)) {
            p_86992_ = "Realm";
        }
        p_86992_ = FileDownload.m_87001_(p_86992_);
        try {
            Object object = p_86994_.m_230833_().iterator();
            while (object.hasNext()) {
                LevelStorageSource.LevelDirectory $$6 = (LevelStorageSource.LevelDirectory)object.next();
                String $$7 = $$6.m_230853_();
                if (!$$7.toLowerCase(Locale.ROOT).startsWith(p_86992_.toLowerCase(Locale.ROOT))) continue;
                Matcher $$8 = $$3.matcher($$7);
                if ($$8.matches()) {
                    int $$9 = Integer.parseInt($$8.group(1));
                    if ($$9 <= $$4) continue;
                    $$4 = $$9;
                    continue;
                }
                ++$$4;
            }
        }
        catch (Exception $$10) {
            f_86953_.error("Error getting level list", (Throwable)$$10);
            this.f_86956_ = true;
            return;
        }
        if (!p_86994_.m_78240_(p_86992_) || $$4 > 1) {
            String $$11 = p_86992_ + (String)($$4 == 1 ? "" : "-" + $$4);
            if (!p_86994_.m_78240_($$11)) {
                boolean $$12 = false;
                while (!$$12) {
                    if (!p_86994_.m_78240_($$11 = p_86992_ + (String)(++$$4 == 1 ? "" : "-" + $$4))) continue;
                    $$12 = true;
                }
            }
        } else {
            $$13 = p_86992_;
        }
        TarArchiveInputStream $$14 = null;
        File $$15 = new File(Minecraft.m_91087_().f_91069_.getAbsolutePath(), "saves");
        try {
            $$15.mkdir();
            $$14 = new TarArchiveInputStream((InputStream)new GzipCompressorInputStream((InputStream)new BufferedInputStream(new FileInputStream(p_86993_))));
            TarArchiveEntry $$16 = $$14.getNextTarEntry();
            while ($$16 != null) {
                File $$17 = new File($$15, $$16.getName().replace("world", $$13));
                if ($$16.isDirectory()) {
                    $$17.mkdirs();
                } else {
                    $$17.createNewFile();
                    try (FileOutputStream $$18 = new FileOutputStream($$17);){
                        IOUtils.copy((InputStream)$$14, (OutputStream)$$18);
                    }
                }
                $$16 = $$14.getNextTarEntry();
            }
        }
        catch (Exception $$22) {
            f_86953_.error("Error extracting world", (Throwable)$$22);
            this.f_86956_ = true;
        }
        finally {
            if ($$14 != null) {
                $$14.close();
            }
            if (p_86993_ != null) {
                p_86993_.delete();
            }
            try (LevelStorageSource.LevelStorageAccess $$23 = p_86994_.m_78260_($$13);){
                $$23.m_78297_($$13.trim());
                Path $$24 = $$23.m_78283_(LevelResource.f_78178_);
                FileDownload.m_86987_($$24.toFile());
            }
            catch (IOException $$25) {
                f_86953_.error("Failed to rename unpacked realms level {}", (Object)$$13, (Object)$$25);
            }
            this.f_86959_ = new File($$15, $$13 + File.separator + "resources.zip");
        }
    }

    private static void m_86987_(File p_86988_) {
        if (p_86988_.exists()) {
            try {
                CompoundTag $$1 = NbtIo.m_128937_(p_86988_);
                CompoundTag $$2 = $$1.m_128469_("Data");
                $$2.m_128473_("Player");
                NbtIo.m_128944_($$1, p_86988_);
            }
            catch (Exception $$3) {
                $$3.printStackTrace();
            }
        }
    }

    class ResourcePackProgressListener
    implements ActionListener {
        private final File f_87041_;
        private final RealmsDownloadLatestWorldScreen.DownloadStatus f_87042_;
        private final WorldDownload f_87043_;

        ResourcePackProgressListener(File p_87046_, RealmsDownloadLatestWorldScreen.DownloadStatus p_87047_, WorldDownload p_87048_) {
            this.f_87041_ = p_87046_;
            this.f_87042_ = p_87047_;
            this.f_87043_ = p_87048_;
        }

        @Override
        public void actionPerformed(ActionEvent p_87056_) {
            this.f_87042_.f_88660_ = ((DownloadCountingOutputStream)((Object)p_87056_.getSource())).getByteCount();
            if (this.f_87042_.f_88660_ >= this.f_87042_.f_88661_ && !FileDownload.this.f_86954_) {
                try {
                    String $$1 = Hashing.sha1().hashBytes(Files.toByteArray((File)this.f_87041_)).toString();
                    if ($$1.equals(this.f_87043_.f_87720_)) {
                        FileUtils.copyFile((File)this.f_87041_, (File)FileDownload.this.f_86959_);
                        FileDownload.this.f_86955_ = true;
                    } else {
                        f_86953_.error("Resourcepack had wrong hash (expected {}, found {}). Deleting it.", (Object)this.f_87043_.f_87720_, (Object)$$1);
                        FileUtils.deleteQuietly((File)this.f_87041_);
                        FileDownload.this.f_86956_ = true;
                    }
                }
                catch (IOException $$2) {
                    f_86953_.error("Error copying resourcepack file: {}", (Object)$$2.getMessage());
                    FileDownload.this.f_86956_ = true;
                }
            }
        }
    }

    static class DownloadCountingOutputStream
    extends CountingOutputStream {
        @Nullable
        private ActionListener f_87012_;

        public DownloadCountingOutputStream(OutputStream p_193509_) {
            super(p_193509_);
        }

        public void m_87016_(ActionListener p_87017_) {
            this.f_87012_ = p_87017_;
        }

        protected void afterWrite(int p_87019_) throws IOException {
            super.afterWrite(p_87019_);
            if (this.f_87012_ != null) {
                this.f_87012_.actionPerformed(new ActionEvent((Object)this, 0, null));
            }
        }
    }

    class ProgressListener
    implements ActionListener {
        private final String f_87021_;
        private final File f_87022_;
        private final LevelStorageSource f_87023_;
        private final RealmsDownloadLatestWorldScreen.DownloadStatus f_87024_;

        ProgressListener(String p_87027_, File p_87028_, LevelStorageSource p_87029_, RealmsDownloadLatestWorldScreen.DownloadStatus p_87030_) {
            this.f_87021_ = p_87027_;
            this.f_87022_ = p_87028_;
            this.f_87023_ = p_87029_;
            this.f_87024_ = p_87030_;
        }

        @Override
        public void actionPerformed(ActionEvent p_87039_) {
            this.f_87024_.f_88660_ = ((DownloadCountingOutputStream)((Object)p_87039_.getSource())).getByteCount();
            if (this.f_87024_.f_88660_ >= this.f_87024_.f_88661_ && !FileDownload.this.f_86954_ && !FileDownload.this.f_86956_) {
                try {
                    FileDownload.this.f_86957_ = true;
                    FileDownload.this.m_86991_(this.f_87021_, this.f_87022_, this.f_87023_);
                }
                catch (IOException $$1) {
                    f_86953_.error("Error extracting archive", (Throwable)$$1);
                    FileDownload.this.f_86956_ = true;
                }
            }
        }
    }
}

