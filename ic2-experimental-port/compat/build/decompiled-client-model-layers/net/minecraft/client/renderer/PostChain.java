/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;

public class PostChain
implements AutoCloseable {
    private static final String f_173045_ = "minecraft:main";
    private final RenderTarget f_110006_;
    private final ResourceManager f_110007_;
    private final String f_110008_;
    private final List<PostPass> f_110009_ = Lists.newArrayList();
    private final Map<String, RenderTarget> f_110010_ = Maps.newHashMap();
    private final List<RenderTarget> f_110011_ = Lists.newArrayList();
    private Matrix4f f_110012_;
    private int f_110013_;
    private int f_110014_;
    private float f_110015_;
    private float f_110016_;

    public PostChain(TextureManager p_110018_, ResourceManager p_110019_, RenderTarget p_110020_, ResourceLocation p_110021_) throws IOException, JsonSyntaxException {
        this.f_110007_ = p_110019_;
        this.f_110006_ = p_110020_;
        this.f_110015_ = 0.0f;
        this.f_110016_ = 0.0f;
        this.f_110013_ = p_110020_.f_83917_;
        this.f_110014_ = p_110020_.f_83918_;
        this.f_110008_ = p_110021_.toString();
        this.m_110046_();
        this.m_110033_(p_110018_, p_110021_);
    }

    private void m_110033_(TextureManager p_110034_, ResourceLocation p_110035_) throws IOException, JsonSyntaxException {
        block15: {
            Resource $$2 = this.f_110007_.m_215593_(p_110035_);
            try (BufferedReader $$3 = $$2.m_215508_();){
                JsonObject $$4 = GsonHelper.m_13859_($$3);
                if (GsonHelper.m_13885_($$4, "targets")) {
                    JsonArray $$5 = $$4.getAsJsonArray("targets");
                    int $$6 = 0;
                    for (JsonElement $$7 : $$5) {
                        try {
                            this.m_110028_($$7);
                        }
                        catch (Exception $$8) {
                            ChainedJsonException $$9 = ChainedJsonException.m_135906_($$8);
                            $$9.m_135908_("targets[" + $$6 + "]");
                            throw $$9;
                        }
                        ++$$6;
                    }
                }
                if (!GsonHelper.m_13885_($$4, "passes")) break block15;
                JsonArray $$10 = $$4.getAsJsonArray("passes");
                int $$11 = 0;
                for (JsonElement $$12 : $$10) {
                    try {
                        this.m_110030_(p_110034_, $$12);
                    }
                    catch (Exception $$13) {
                        ChainedJsonException $$14 = ChainedJsonException.m_135906_($$13);
                        $$14.m_135908_("passes[" + $$11 + "]");
                        throw $$14;
                    }
                    ++$$11;
                }
            }
            catch (Exception $$15) {
                ChainedJsonException $$16 = ChainedJsonException.m_135906_($$15);
                $$16.m_135910_(p_110035_.m_135815_() + " (" + $$2.m_215506_() + ")");
                throw $$16;
            }
        }
    }

    private void m_110028_(JsonElement p_110029_) throws ChainedJsonException {
        if (GsonHelper.m_13803_(p_110029_)) {
            this.m_110038_(p_110029_.getAsString(), this.f_110013_, this.f_110014_);
        } else {
            JsonObject $$1 = GsonHelper.m_13918_(p_110029_, "target");
            String $$2 = GsonHelper.m_13906_($$1, "name");
            int $$3 = GsonHelper.m_13824_($$1, "width", this.f_110013_);
            int $$4 = GsonHelper.m_13824_($$1, "height", this.f_110014_);
            if (this.f_110010_.containsKey($$2)) {
                throw new ChainedJsonException($$2 + " is already defined");
            }
            this.m_110038_($$2, $$3, $$4);
        }
    }

    private void m_110030_(TextureManager p_110031_, JsonElement p_110032_) throws IOException {
        JsonArray $$27;
        JsonObject $$2 = GsonHelper.m_13918_(p_110032_, "pass");
        String $$3 = GsonHelper.m_13906_($$2, "name");
        String $$4 = GsonHelper.m_13906_($$2, "intarget");
        String $$5 = GsonHelper.m_13906_($$2, "outtarget");
        RenderTarget $$6 = this.m_110049_($$4);
        RenderTarget $$7 = this.m_110049_($$5);
        if ($$6 == null) {
            throw new ChainedJsonException("Input target '" + $$4 + "' does not exist");
        }
        if ($$7 == null) {
            throw new ChainedJsonException("Output target '" + $$5 + "' does not exist");
        }
        PostPass $$8 = this.m_110042_($$3, $$6, $$7);
        JsonArray $$9 = GsonHelper.m_13832_($$2, "auxtargets", null);
        if ($$9 != null) {
            int $$10 = 0;
            for (JsonElement $$11 : $$9) {
                try {
                    String $$18;
                    boolean $$17;
                    JsonObject $$12 = GsonHelper.m_13918_($$11, "auxtarget");
                    String $$13 = GsonHelper.m_13906_($$12, "name");
                    String $$14 = GsonHelper.m_13906_($$12, "id");
                    if ($$14.endsWith(":depth")) {
                        boolean $$15 = true;
                        String $$16 = $$14.substring(0, $$14.lastIndexOf(58));
                    } else {
                        $$17 = false;
                        $$18 = $$14;
                    }
                    RenderTarget $$19 = this.m_110049_($$18);
                    if ($$19 == null) {
                        if ($$17) {
                            throw new ChainedJsonException("Render target '" + $$18 + "' can't be used as depth buffer");
                        }
                        ResourceLocation $$20 = new ResourceLocation("textures/effect/" + $$18 + ".png");
                        this.f_110007_.m_213713_($$20).orElseThrow(() -> new ChainedJsonException("Render target or texture '" + $$18 + "' does not exist"));
                        RenderSystem.m_157456_(0, $$20);
                        p_110031_.m_174784_($$20);
                        AbstractTexture $$21 = p_110031_.m_118506_($$20);
                        int $$22 = GsonHelper.m_13927_($$12, "width");
                        int $$23 = GsonHelper.m_13927_($$12, "height");
                        boolean $$24 = GsonHelper.m_13912_($$12, "bilinear");
                        if ($$24) {
                            RenderSystem.m_69937_(3553, 10241, 9729);
                            RenderSystem.m_69937_(3553, 10240, 9729);
                        } else {
                            RenderSystem.m_69937_(3553, 10241, 9728);
                            RenderSystem.m_69937_(3553, 10240, 9728);
                        }
                        $$8.m_110069_($$13, $$21::m_117963_, $$22, $$23);
                    } else if ($$17) {
                        $$8.m_110069_($$13, $$19::m_83980_, $$19.f_83915_, $$19.f_83916_);
                    } else {
                        $$8.m_110069_($$13, $$19::m_83975_, $$19.f_83915_, $$19.f_83916_);
                    }
                }
                catch (Exception $$25) {
                    ChainedJsonException $$26 = ChainedJsonException.m_135906_($$25);
                    $$26.m_135908_("auxtargets[" + $$10 + "]");
                    throw $$26;
                }
                ++$$10;
            }
        }
        if (($$27 = GsonHelper.m_13832_($$2, "uniforms", null)) != null) {
            int $$28 = 0;
            for (JsonElement $$29 : $$27) {
                try {
                    this.m_110047_($$29);
                }
                catch (Exception $$30) {
                    ChainedJsonException $$31 = ChainedJsonException.m_135906_($$30);
                    $$31.m_135908_("uniforms[" + $$28 + "]");
                    throw $$31;
                }
                ++$$28;
            }
        }
    }

    private void m_110047_(JsonElement p_110048_) throws ChainedJsonException {
        JsonObject $$1 = GsonHelper.m_13918_(p_110048_, "uniform");
        String $$2 = GsonHelper.m_13906_($$1, "name");
        Uniform $$3 = this.f_110009_.get(this.f_110009_.size() - 1).m_110074_().m_108952_($$2);
        if ($$3 == null) {
            throw new ChainedJsonException("Uniform '" + $$2 + "' does not exist");
        }
        float[] $$4 = new float[4];
        int $$5 = 0;
        JsonArray $$6 = GsonHelper.m_13933_($$1, "values");
        for (JsonElement $$7 : $$6) {
            try {
                $$4[$$5] = GsonHelper.m_13888_($$7, "value");
            }
            catch (Exception $$8) {
                ChainedJsonException $$9 = ChainedJsonException.m_135906_($$8);
                $$9.m_135908_("values[" + $$5 + "]");
                throw $$9;
            }
            ++$$5;
        }
        switch ($$5) {
            case 0: {
                break;
            }
            case 1: {
                $$3.m_5985_($$4[0]);
                break;
            }
            case 2: {
                $$3.m_7971_($$4[0], $$4[1]);
                break;
            }
            case 3: {
                $$3.m_5889_($$4[0], $$4[1], $$4[2]);
                break;
            }
            case 4: {
                $$3.m_5805_($$4[0], $$4[1], $$4[2], $$4[3]);
            }
        }
    }

    public RenderTarget m_110036_(String p_110037_) {
        return this.f_110010_.get(p_110037_);
    }

    public void m_110038_(String p_110039_, int p_110040_, int p_110041_) {
        TextureTarget $$3 = new TextureTarget(p_110040_, p_110041_, true, Minecraft.f_91002_);
        $$3.m_83931_(0.0f, 0.0f, 0.0f, 0.0f);
        this.f_110010_.put(p_110039_, $$3);
        if (p_110040_ == this.f_110013_ && p_110041_ == this.f_110014_) {
            this.f_110011_.add($$3);
        }
    }

    @Override
    public void close() {
        for (RenderTarget $$0 : this.f_110010_.values()) {
            $$0.m_83930_();
        }
        for (PostPass $$1 : this.f_110009_) {
            $$1.close();
        }
        this.f_110009_.clear();
    }

    public PostPass m_110042_(String p_110043_, RenderTarget p_110044_, RenderTarget p_110045_) throws IOException {
        PostPass $$3 = new PostPass(this.f_110007_, p_110043_, p_110044_, p_110045_);
        this.f_110009_.add(this.f_110009_.size(), $$3);
        return $$3;
    }

    private void m_110046_() {
        this.f_110012_ = Matrix4f.m_162203_(0.0f, this.f_110006_.f_83915_, this.f_110006_.f_83916_, 0.0f, 0.1f, 1000.0f);
    }

    public void m_110025_(int p_110026_, int p_110027_) {
        this.f_110013_ = this.f_110006_.f_83915_;
        this.f_110014_ = this.f_110006_.f_83916_;
        this.m_110046_();
        for (PostPass $$2 : this.f_110009_) {
            $$2.m_110067_(this.f_110012_);
        }
        for (RenderTarget $$3 : this.f_110011_) {
            $$3.m_83941_(p_110026_, p_110027_, Minecraft.f_91002_);
        }
    }

    public void m_110023_(float p_110024_) {
        if (p_110024_ < this.f_110016_) {
            this.f_110015_ += 1.0f - this.f_110016_;
            this.f_110015_ += p_110024_;
        } else {
            this.f_110015_ += p_110024_ - this.f_110016_;
        }
        this.f_110016_ = p_110024_;
        while (this.f_110015_ > 20.0f) {
            this.f_110015_ -= 20.0f;
        }
        for (PostPass $$1 : this.f_110009_) {
            $$1.m_110065_(this.f_110015_ / 20.0f);
        }
    }

    public final String m_110022_() {
        return this.f_110008_;
    }

    @Nullable
    private RenderTarget m_110049_(@Nullable String p_110050_) {
        if (p_110050_ == null) {
            return null;
        }
        if (p_110050_.equals(f_173045_)) {
            return this.f_110006_;
        }
        return this.f_110010_.get(p_110050_);
    }
}

