/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.PreloadedTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public class TextureManager
implements PreparableReloadListener,
Tickable,
AutoCloseable {
    private static final Logger f_118467_ = LogUtils.getLogger();
    public static final ResourceLocation f_118466_ = new ResourceLocation("");
    private final Map<ResourceLocation, AbstractTexture> f_118468_ = Maps.newHashMap();
    private final Set<Tickable> f_118469_ = Sets.newHashSet();
    private final Map<String, Integer> f_118470_ = Maps.newHashMap();
    private final ResourceManager f_118471_;

    public TextureManager(ResourceManager p_118474_) {
        this.f_118471_ = p_118474_;
    }

    public void m_174784_(ResourceLocation p_174785_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> this.m_118519_(p_174785_));
        } else {
            this.m_118519_(p_174785_);
        }
    }

    private void m_118519_(ResourceLocation p_118520_) {
        AbstractTexture $$1 = this.f_118468_.get(p_118520_);
        if ($$1 == null) {
            $$1 = new SimpleTexture(p_118520_);
            this.m_118495_(p_118520_, $$1);
        }
        $$1.m_117966_();
    }

    public void m_118495_(ResourceLocation p_118496_, AbstractTexture p_118497_) {
        AbstractTexture $$2 = this.f_118468_.put(p_118496_, p_118497_ = this.m_118515_(p_118496_, p_118497_));
        if ($$2 != p_118497_) {
            if ($$2 != null && $$2 != MissingTextureAtlasSprite.m_118080_()) {
                this.f_118469_.remove($$2);
                this.m_118508_(p_118496_, $$2);
            }
            if (p_118497_ instanceof Tickable) {
                this.f_118469_.add((Tickable)((Object)p_118497_));
            }
        }
    }

    private void m_118508_(ResourceLocation p_118509_, AbstractTexture p_118510_) {
        if (p_118510_ != MissingTextureAtlasSprite.m_118080_()) {
            try {
                p_118510_.close();
            }
            catch (Exception $$2) {
                f_118467_.warn("Failed to close texture {}", (Object)p_118509_, (Object)$$2);
            }
        }
        p_118510_.m_117964_();
    }

    private AbstractTexture m_118515_(ResourceLocation p_118516_, AbstractTexture p_118517_) {
        try {
            p_118517_.m_6704_(this.f_118471_);
            return p_118517_;
        }
        catch (IOException $$2) {
            if (p_118516_ != f_118466_) {
                f_118467_.warn("Failed to load texture: {}", (Object)p_118516_, (Object)$$2);
            }
            return MissingTextureAtlasSprite.m_118080_();
        }
        catch (Throwable $$3) {
            CrashReport $$4 = CrashReport.m_127521_($$3, "Registering texture");
            CrashReportCategory $$5 = $$4.m_127514_("Resource location being registered");
            $$5.m_128159_("Resource location", p_118516_);
            $$5.m_128165_("Texture object class", () -> p_118517_.getClass().getName());
            throw new ReportedException($$4);
        }
    }

    public AbstractTexture m_118506_(ResourceLocation p_118507_) {
        AbstractTexture $$1 = this.f_118468_.get(p_118507_);
        if ($$1 == null) {
            $$1 = new SimpleTexture(p_118507_);
            this.m_118495_(p_118507_, $$1);
        }
        return $$1;
    }

    public AbstractTexture m_174786_(ResourceLocation p_174787_, AbstractTexture p_174788_) {
        return this.f_118468_.getOrDefault(p_174787_, p_174788_);
    }

    public ResourceLocation m_118490_(String p_118491_, DynamicTexture p_118492_) {
        Integer $$2 = this.f_118470_.get(p_118491_);
        if ($$2 == null) {
            $$2 = 1;
        } else {
            Integer n = $$2;
            $$2 = $$2 + 1;
        }
        this.f_118470_.put(p_118491_, $$2);
        ResourceLocation $$3 = new ResourceLocation(String.format(Locale.ROOT, "dynamic/%s_%d", p_118491_, $$2));
        this.m_118495_($$3, p_118492_);
        return $$3;
    }

    public CompletableFuture<Void> m_118501_(ResourceLocation p_118502_, Executor p_118503_) {
        if (!this.f_118468_.containsKey(p_118502_)) {
            PreloadedTexture $$2 = new PreloadedTexture(this.f_118471_, p_118502_, p_118503_);
            this.f_118468_.put(p_118502_, $$2);
            return $$2.m_118105_().thenRunAsync(() -> this.m_118495_(p_118502_, $$2), TextureManager::m_118488_);
        }
        return CompletableFuture.completedFuture(null);
    }

    private static void m_118488_(Runnable p_118489_) {
        Minecraft.m_91087_().execute(() -> RenderSystem.m_69879_(p_118489_::run));
    }

    @Override
    public void m_7673_() {
        for (Tickable $$0 : this.f_118469_) {
            $$0.m_7673_();
        }
    }

    public void m_118513_(ResourceLocation p_118514_) {
        AbstractTexture $$1 = this.m_174786_(p_118514_, MissingTextureAtlasSprite.m_118080_());
        if ($$1 != MissingTextureAtlasSprite.m_118080_()) {
            TextureUtil.m_85281_($$1.m_117963_());
        }
    }

    @Override
    public void close() {
        this.f_118468_.forEach(this::m_118508_);
        this.f_118468_.clear();
        this.f_118469_.clear();
        this.f_118470_.clear();
    }

    @Override
    public CompletableFuture<Void> m_5540_(PreparableReloadListener.PreparationBarrier p_118476_, ResourceManager p_118477_, ProfilerFiller p_118478_, ProfilerFiller p_118479_, Executor p_118480_, Executor p_118481_) {
        return ((CompletableFuture)CompletableFuture.allOf(TitleScreen.m_96754_(this, p_118480_), this.m_118501_(AbstractWidget.f_93617_, p_118480_)).thenCompose(p_118476_::m_6769_)).thenAcceptAsync(p_118485_ -> {
            MissingTextureAtlasSprite.m_118080_();
            RealmsMainScreen.m_86406_(this.f_118471_);
            Iterator<Map.Entry<ResourceLocation, AbstractTexture>> $$3 = this.f_118468_.entrySet().iterator();
            while ($$3.hasNext()) {
                Map.Entry<ResourceLocation, AbstractTexture> $$4 = $$3.next();
                ResourceLocation $$5 = $$4.getKey();
                AbstractTexture $$6 = $$4.getValue();
                if ($$6 == MissingTextureAtlasSprite.m_118080_() && !$$5.equals(MissingTextureAtlasSprite.m_118071_())) {
                    $$3.remove();
                    continue;
                }
                $$6.m_6479_(this, p_118477_, $$5, p_118481_);
            }
        }, p_118505_ -> RenderSystem.m_69879_(p_118505_::run));
    }
}

