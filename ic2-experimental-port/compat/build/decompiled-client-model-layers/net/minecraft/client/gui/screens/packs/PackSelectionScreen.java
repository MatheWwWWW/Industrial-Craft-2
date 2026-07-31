/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.hash.Hashing
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens.packs;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;

public class PackSelectionScreen
extends Screen {
    static final Logger f_99969_ = LogUtils.getLogger();
    private static final int f_169993_ = 200;
    private static final Component f_99970_ = Component.m_237115_("pack.dropInfo").m_130940_(ChatFormatting.GRAY);
    static final Component f_99971_ = Component.m_237115_("pack.folderInfo");
    private static final int f_169994_ = 20;
    private static final ResourceLocation f_99972_ = new ResourceLocation("textures/misc/unknown_pack.png");
    private final PackSelectionModel f_99973_;
    private final Screen f_99974_;
    @Nullable
    private Watcher f_99975_;
    private long f_99976_;
    private TransferableSelectionList f_99977_;
    private TransferableSelectionList f_99978_;
    private final File f_99979_;
    private Button f_99980_;
    private final Map<String, ResourceLocation> f_99981_ = Maps.newHashMap();

    public PackSelectionScreen(Screen p_99984_, PackRepository p_99985_, Consumer<PackRepository> p_99986_, File p_99987_, Component p_99988_) {
        super(p_99988_);
        this.f_99974_ = p_99984_;
        this.f_99973_ = new PackSelectionModel(this::m_100040_, this::m_99989_, p_99985_, p_99986_);
        this.f_99979_ = p_99987_;
        this.f_99975_ = Watcher.m_100047_(p_99987_);
    }

    @Override
    public void m_7379_() {
        this.f_99973_.m_99923_();
        this.f_96541_.m_91152_(this.f_99974_);
        this.m_100039_();
    }

    private void m_100039_() {
        if (this.f_99975_ != null) {
            try {
                this.f_99975_.close();
                this.f_99975_ = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    protected void m_7856_() {
        this.f_99980_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 48, 150, 20, CommonComponents.f_130655_, p_100036_ -> this.m_7379_()));
        this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 48, 150, 20, Component.m_237115_("pack.openFolder"), p_100004_ -> Util.m_137581_().m_137644_(this.f_99979_), new Button.OnTooltip(){

            @Override
            public void m_93752_(Button p_170019_, PoseStack p_170020_, int p_170021_, int p_170022_) {
                PackSelectionScreen.this.m_96602_(p_170020_, f_99971_, p_170021_, p_170022_);
            }

            @Override
            public void m_142753_(Consumer<Component> p_170017_) {
                p_170017_.accept(f_99971_);
            }
        }));
        this.f_99977_ = new TransferableSelectionList(this.f_96541_, 200, this.f_96544_, Component.m_237115_("pack.available.title"));
        this.f_99977_.m_93507_(this.f_96543_ / 2 - 4 - 200);
        this.m_7787_(this.f_99977_);
        this.f_99978_ = new TransferableSelectionList(this.f_96541_, 200, this.f_96544_, Component.m_237115_("pack.selected.title"));
        this.f_99978_.m_93507_(this.f_96543_ / 2 + 4);
        this.m_7787_(this.f_99978_);
        this.m_100041_();
    }

    @Override
    public void m_86600_() {
        if (this.f_99975_ != null) {
            try {
                if (this.f_99975_.m_100046_()) {
                    this.f_99976_ = 20L;
                }
            }
            catch (IOException $$0) {
                f_99969_.warn("Failed to poll for directory {} changes, stopping", (Object)this.f_99979_);
                this.m_100039_();
            }
        }
        if (this.f_99976_ > 0L && --this.f_99976_ == 0L) {
            this.m_100041_();
        }
    }

    private void m_100040_() {
        this.m_100013_(this.f_99978_, this.f_99973_.m_99918_());
        this.m_100013_(this.f_99977_, this.f_99973_.m_99913_());
        this.f_99980_.f_93623_ = !this.f_99978_.m_6702_().isEmpty();
    }

    private void m_100013_(TransferableSelectionList p_100014_, Stream<PackSelectionModel.Entry> p_100015_) {
        p_100014_.m_6702_().clear();
        p_100015_.forEach(p_170000_ -> p_100014_.m_6702_().add(new TransferableSelectionList.PackEntry(this.f_96541_, p_100014_, this, (PackSelectionModel.Entry)p_170000_)));
    }

    private void m_100041_() {
        this.f_99973_.m_99926_();
        this.m_100040_();
        this.f_99976_ = 0L;
        this.f_99981_.clear();
    }

    @Override
    public void m_6305_(PoseStack p_99995_, int p_99996_, int p_99997_, float p_99998_) {
        this.m_96626_(0);
        this.f_99977_.m_6305_(p_99995_, p_99996_, p_99997_, p_99998_);
        this.f_99978_.m_6305_(p_99995_, p_99996_, p_99997_, p_99998_);
        PackSelectionScreen.m_93215_(p_99995_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 0xFFFFFF);
        PackSelectionScreen.m_93215_(p_99995_, this.f_96547_, f_99970_, this.f_96543_ / 2, 20, 0xFFFFFF);
        super.m_6305_(p_99995_, p_99996_, p_99997_, p_99998_);
    }

    protected static void m_99999_(Minecraft p_100000_, List<Path> p_100001_, Path p_100002_) {
        MutableBoolean $$3 = new MutableBoolean();
        p_100001_.forEach(p_170009_ -> {
            try (Stream<Path> $$3 = Files.walk(p_170009_, new FileVisitOption[0]);){
                $$3.forEach(p_170005_ -> {
                    try {
                        Util.m_137563_(p_170009_.getParent(), p_100002_, p_170005_);
                    }
                    catch (IOException $$4) {
                        f_99969_.warn("Failed to copy datapack file  from {} to {}", new Object[]{p_170005_, p_100002_, $$4});
                        $$3.setTrue();
                    }
                });
            }
            catch (IOException $$4) {
                f_99969_.warn("Failed to copy datapack file from {} to {}", p_170009_, (Object)p_100002_);
                $$3.setTrue();
            }
        });
        if ($$3.isTrue()) {
            SystemToast.m_94875_(p_100000_, p_100002_.toString());
        }
    }

    @Override
    public void m_7400_(List<Path> p_100029_) {
        String $$1 = p_100029_.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
        this.f_96541_.m_91152_(new ConfirmScreen(p_170012_ -> {
            if (p_170012_) {
                PackSelectionScreen.m_99999_(this.f_96541_, p_100029_, this.f_99979_.toPath());
                this.m_100041_();
            }
            this.f_96541_.m_91152_(this);
        }, Component.m_237115_("pack.dropConfirm"), Component.m_237113_($$1)));
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private ResourceLocation m_100016_(TextureManager p_100017_, Pack p_100018_) {
        try (PackResources $$22 = p_100018_.m_10445_();){
            ResourceLocation resourceLocation;
            block19: {
                InputStream $$3;
                block17: {
                    ResourceLocation resourceLocation2;
                    block18: {
                        $$3 = $$22.m_5542_("pack.png");
                        try {
                            if ($$3 != null) break block17;
                            resourceLocation2 = f_99972_;
                            if ($$3 == null) break block18;
                        }
                        catch (Throwable throwable) {
                            if ($$3 != null) {
                                try {
                                    $$3.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                        $$3.close();
                    }
                    return resourceLocation2;
                }
                String $$4 = p_100018_.m_10446_();
                ResourceLocation $$5 = new ResourceLocation("minecraft", "pack/" + Util.m_137483_($$4, ResourceLocation::m_135828_) + "/" + Hashing.sha1().hashUnencodedChars((CharSequence)$$4) + "/icon");
                NativeImage $$6 = NativeImage.m_85058_($$3);
                p_100017_.m_118495_($$5, new DynamicTexture($$6));
                resourceLocation = $$5;
                if ($$3 == null) break block19;
                $$3.close();
            }
            return resourceLocation;
        }
        catch (FileNotFoundException $$22) {
        }
        catch (Exception $$7) {
            f_99969_.warn("Failed to load icon from pack {}", (Object)p_100018_.m_10446_(), (Object)$$7);
        }
        return f_99972_;
    }

    private ResourceLocation m_99989_(Pack p_99990_) {
        return this.f_99981_.computeIfAbsent(p_99990_.m_10446_(), p_169997_ -> this.m_100016_(this.f_96541_.m_91097_(), p_99990_));
    }

    static class Watcher
    implements AutoCloseable {
        private final WatchService f_100042_;
        private final Path f_100043_;

        public Watcher(File p_100045_) throws IOException {
            this.f_100043_ = p_100045_.toPath();
            this.f_100042_ = this.f_100043_.getFileSystem().newWatchService();
            try {
                this.m_100049_(this.f_100043_);
                try (DirectoryStream<Path> $$1 = Files.newDirectoryStream(this.f_100043_);){
                    for (Path $$2 : $$1) {
                        if (!Files.isDirectory($$2, LinkOption.NOFOLLOW_LINKS)) continue;
                        this.m_100049_($$2);
                    }
                }
            }
            catch (Exception $$3) {
                this.f_100042_.close();
                throw $$3;
            }
        }

        @Nullable
        public static Watcher m_100047_(File p_100048_) {
            try {
                return new Watcher(p_100048_);
            }
            catch (IOException $$1) {
                f_99969_.warn("Failed to initialize pack directory {} monitoring", (Object)p_100048_, (Object)$$1);
                return null;
            }
        }

        private void m_100049_(Path p_100050_) throws IOException {
            p_100050_.register(this.f_100042_, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        }

        public boolean m_100046_() throws IOException {
            WatchKey $$1;
            boolean $$0 = false;
            while (($$1 = this.f_100042_.poll()) != null) {
                List<WatchEvent<?>> $$2 = $$1.pollEvents();
                for (WatchEvent<?> $$3 : $$2) {
                    Path $$4;
                    $$0 = true;
                    if ($$1.watchable() != this.f_100043_ || $$3.kind() != StandardWatchEventKinds.ENTRY_CREATE || !Files.isDirectory($$4 = this.f_100043_.resolve((Path)$$3.context()), LinkOption.NOFOLLOW_LINKS)) continue;
                    this.m_100049_($$4);
                }
                $$1.reset();
            }
            return $$0;
        }

        @Override
        public void close() throws IOException {
            this.f_100042_.close();
        }
    }
}

