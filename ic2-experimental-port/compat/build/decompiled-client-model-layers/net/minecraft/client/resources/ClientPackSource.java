/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.hash.Hashing
 *  com.google.common.io.Files
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.comparator.LastModifiedFileComparator
 *  org.apache.commons.io.filefilter.IOFileFilter
 *  org.apache.commons.io.filefilter.TrueFileFilter
 *  org.slf4j.Logger
 */
package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.client.resources.DefaultClientPackResources;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;

public class ClientPackSource
implements RepositorySource {
    private static final PackMetadataSection f_174791_ = new PackMetadataSection(Component.m_237115_("resourcePack.vanilla.description"), PackType.CLIENT_RESOURCES.m_143756_(SharedConstants.m_183709_()));
    private static final Logger f_118543_ = LogUtils.getLogger();
    private static final Pattern f_118544_ = Pattern.compile("^[a-fA-F0-9]{40}$");
    private static final int f_200482_ = 0xFA00000;
    private static final int f_174793_ = 10;
    private static final String f_174794_ = "vanilla";
    private static final String f_174795_ = "server";
    private static final String f_174796_ = "programer_art";
    private static final String f_174797_ = "Programmer Art";
    private static final Component f_174798_ = Component.m_237115_("multiplayer.applyingPack");
    private final VanillaPackResources f_118545_;
    private final File f_118546_;
    private final ReentrantLock f_118547_ = new ReentrantLock();
    private final AssetIndex f_118548_;
    @Nullable
    private CompletableFuture<?> f_118549_;
    @Nullable
    private Pack f_118550_;

    public ClientPackSource(File p_118553_, AssetIndex p_118554_) {
        this.f_118546_ = p_118553_;
        this.f_118548_ = p_118554_;
        this.f_118545_ = new DefaultClientPackResources(f_174791_, p_118554_);
    }

    @Override
    public void m_7686_(Consumer<Pack> p_118584_, Pack.PackConstructor p_118585_) {
        Pack $$3;
        Pack $$2 = Pack.m_10430_(f_174794_, true, () -> this.f_118545_, p_118585_, Pack.Position.BOTTOM, PackSource.f_10528_);
        if ($$2 != null) {
            p_118584_.accept($$2);
        }
        if (this.f_118550_ != null) {
            p_118584_.accept(this.f_118550_);
        }
        if (($$3 = this.m_118556_(p_118585_)) != null) {
            p_118584_.accept($$3);
        }
    }

    public VanillaPackResources m_118555_() {
        return this.f_118545_;
    }

    private static Map<String, String> m_118589_() {
        HashMap $$0 = Maps.newHashMap();
        $$0.put("X-Minecraft-Username", Minecraft.m_91087_().m_91094_().m_92546_());
        $$0.put("X-Minecraft-UUID", Minecraft.m_91087_().m_91094_().m_92545_());
        $$0.put("X-Minecraft-Version", SharedConstants.m_183709_().getName());
        $$0.put("X-Minecraft-Version-ID", SharedConstants.m_183709_().getId());
        $$0.put("X-Minecraft-Pack-Format", String.valueOf(PackType.CLIENT_RESOURCES.m_143756_(SharedConstants.m_183709_())));
        $$0.put("User-Agent", "Minecraft Java/" + SharedConstants.m_183709_().getName());
        return $$0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CompletableFuture<?> m_235005_(URL p_235006_, String p_235007_, boolean p_235008_) {
        String $$3 = Hashing.sha1().hashString((CharSequence)p_235006_.toString(), StandardCharsets.UTF_8).toString();
        String $$4 = f_118544_.matcher(p_235007_).matches() ? p_235007_ : "";
        this.f_118547_.lock();
        try {
            CompletableFuture<?> $$10;
            Minecraft $$5 = Minecraft.m_91087_();
            File $$6 = new File(this.f_118546_, $$3);
            if ($$6.exists()) {
                CompletableFuture<String> $$7 = CompletableFuture.completedFuture("");
            } else {
                ProgressScreen $$8 = new ProgressScreen(p_235008_);
                Map<String, String> $$9 = ClientPackSource.m_118589_();
                $$5.m_18709_(() -> $$5.m_91152_($$8));
                $$10 = HttpUtil.m_216225_($$6, p_235006_, $$9, 0xFA00000, $$8, $$5.m_91096_());
            }
            CompletableFuture<?> completableFuture = this.f_118549_ = ((CompletableFuture)((CompletableFuture)$$10.thenCompose(p_174812_ -> {
                if (!this.m_118573_($$4, $$6)) {
                    return Util.m_137498_(new RuntimeException("Hash check failure for file " + $$6 + ", see log"));
                }
                $$5.execute(() -> {
                    if (!p_235008_) {
                        $$5.m_91152_(new GenericDirtMessageScreen(f_174798_));
                    }
                });
                return this.m_118566_($$6, PackSource.f_10530_);
            })).exceptionallyCompose(p_238902_ -> ((CompletableFuture)this.m_235009_().thenAcceptAsync(p_238895_ -> {
                f_118543_.warn("Pack application failed: {}, deleting file {}", (Object)p_238902_.getMessage(), (Object)$$6);
                ClientPackSource.m_118564_($$6);
            }, (Executor)Util.m_183992_())).thenAcceptAsync(p_238899_ -> $$5.m_91152_(new ConfirmScreen(p_238897_ -> {
                if (p_238897_) {
                    $$5.m_91152_(null);
                } else {
                    ClientPacketListener $$2 = $$5.m_91403_();
                    if ($$2 != null) {
                        $$2.m_6198_().m_129507_(Component.m_237115_("connect.aborted"));
                    }
                }
            }, Component.m_237115_("multiplayer.texturePrompt.failure.line1"), Component.m_237115_("multiplayer.texturePrompt.failure.line2"), CommonComponents.f_130659_, Component.m_237115_("menu.disconnect"))), (Executor)$$5))).thenAcceptAsync(p_238903_ -> this.m_118592_(), (Executor)Util.m_183992_());
            return completableFuture;
        }
        finally {
            this.f_118547_.unlock();
        }
    }

    private static void m_118564_(File p_118565_) {
        try {
            Files.delete(p_118565_.toPath());
        }
        catch (IOException $$1) {
            f_118543_.warn("Failed to delete file {}: {}", (Object)p_118565_, (Object)$$1.getMessage());
        }
    }

    public CompletableFuture<Void> m_235009_() {
        this.f_118547_.lock();
        try {
            if (this.f_118549_ != null) {
                this.f_118549_.cancel(true);
            }
            this.f_118549_ = null;
            if (this.f_118550_ != null) {
                this.f_118550_ = null;
                CompletableFuture<Void> completableFuture = Minecraft.m_91087_().m_91088_();
                return completableFuture;
            }
        }
        finally {
            this.f_118547_.unlock();
        }
        return CompletableFuture.completedFuture(null);
    }

    private boolean m_118573_(String p_118574_, File p_118575_) {
        try {
            String $$2 = com.google.common.io.Files.asByteSource((File)p_118575_).hash(Hashing.sha1()).toString();
            if (p_118574_.isEmpty()) {
                f_118543_.info("Found file {} without verification hash", (Object)p_118575_);
                return true;
            }
            if ($$2.toLowerCase(Locale.ROOT).equals(p_118574_.toLowerCase(Locale.ROOT))) {
                f_118543_.info("Found file {} matching requested hash {}", (Object)p_118575_, (Object)p_118574_);
                return true;
            }
            f_118543_.warn("File {} had wrong hash (expected {}, found {}).", new Object[]{p_118575_, p_118574_, $$2});
        }
        catch (IOException $$3) {
            f_118543_.warn("File {} couldn't be hashed.", (Object)p_118575_, (Object)$$3);
        }
        return false;
    }

    private void m_118592_() {
        if (!this.f_118546_.isDirectory()) {
            return;
        }
        try {
            ArrayList $$0 = Lists.newArrayList((Iterable)FileUtils.listFiles((File)this.f_118546_, (IOFileFilter)TrueFileFilter.TRUE, null));
            $$0.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int $$1 = 0;
            for (File $$2 : $$0) {
                if ($$1++ < 10) continue;
                f_118543_.info("Deleting old server resource pack {}", (Object)$$2.getName());
                FileUtils.deleteQuietly((File)$$2);
            }
        }
        catch (Exception $$3) {
            f_118543_.error("Error while deleting old server resource pack : {}", (Object)$$3.getMessage());
        }
    }

    public CompletableFuture<Void> m_235003_(LevelStorageSource.LevelStorageAccess p_235004_) {
        Path $$1 = p_235004_.m_78283_(LevelResource.f_78181_);
        if (Files.exists($$1, new LinkOption[0]) && !Files.isDirectory($$1, new LinkOption[0])) {
            return this.m_118566_($$1.toFile(), PackSource.f_10529_);
        }
        return CompletableFuture.completedFuture(null);
    }

    /*
     * WARNING - void declaration
     */
    public CompletableFuture<Void> m_118566_(File p_118567_, PackSource p_118568_) {
        void $$6;
        try (FilePackResources $$2 = new FilePackResources(p_118567_);){
            PackMetadataSection $$3 = $$2.m_5550_(PackMetadataSection.f_10366_);
        }
        catch (IOException $$5) {
            return Util.m_137498_(new IOException(String.format(Locale.ROOT, "Invalid resourcepack at %s", p_118567_), $$5));
        }
        f_118543_.info("Applying server pack {}", (Object)p_118567_);
        this.f_118550_ = new Pack(f_174795_, true, () -> new FilePackResources(p_118567_), Component.m_237115_("resourcePack.server.name"), $$6.m_10373_(), PackCompatibility.m_143885_((PackMetadataSection)$$6, PackType.CLIENT_RESOURCES), Pack.Position.TOP, true, p_118568_);
        return Minecraft.m_91087_().m_91088_();
    }

    @Nullable
    private Pack m_118556_(Pack.PackConstructor p_118557_) {
        File $$3;
        Pack $$1 = null;
        File $$2 = this.f_118548_.m_7879_(new ResourceLocation("resourcepacks/programmer_art.zip"));
        if ($$2 != null && $$2.isFile()) {
            $$1 = ClientPackSource.m_118558_(p_118557_, () -> ClientPackSource.m_118590_($$2));
        }
        if ($$1 == null && SharedConstants.f_136183_ && ($$3 = this.f_118548_.m_7974_("../resourcepacks/programmer_art")) != null && $$3.isDirectory()) {
            $$1 = ClientPackSource.m_118558_(p_118557_, () -> ClientPackSource.m_118587_($$3));
        }
        return $$1;
    }

    @Nullable
    private static Pack m_118558_(Pack.PackConstructor p_118559_, Supplier<PackResources> p_118560_) {
        return Pack.m_10430_(f_174796_, false, p_118560_, p_118559_, Pack.Position.TOP, PackSource.f_10528_);
    }

    private static FolderPackResources m_118587_(File p_118588_) {
        return new FolderPackResources(p_118588_){

            @Override
            public String m_8017_() {
                return ClientPackSource.f_174797_;
            }
        };
    }

    private static PackResources m_118590_(File p_118591_) {
        return new FilePackResources(p_118591_){

            @Override
            public String m_8017_() {
                return ClientPackSource.f_174797_;
            }
        };
    }
}

