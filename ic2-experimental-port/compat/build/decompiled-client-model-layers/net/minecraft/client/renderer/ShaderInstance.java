/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class ShaderInstance
implements Shader,
AutoCloseable {
    private static final String f_173321_ = "shaders/core/";
    private static final String f_173322_ = "shaders/include/";
    static final Logger f_173323_ = LogUtils.getLogger();
    private static final AbstractUniform f_173324_ = new AbstractUniform();
    private static final boolean f_173325_ = true;
    private static ShaderInstance f_173326_;
    private static int f_173327_;
    private final Map<String, Object> f_173328_ = Maps.newHashMap();
    private final List<String> f_173329_ = Lists.newArrayList();
    private final List<Integer> f_173330_ = Lists.newArrayList();
    private final List<Uniform> f_173331_ = Lists.newArrayList();
    private final List<Integer> f_173332_ = Lists.newArrayList();
    private final Map<String, Uniform> f_173333_ = Maps.newHashMap();
    private final int f_173299_;
    private final String f_173300_;
    private boolean f_173301_;
    private final BlendMode f_173302_;
    private final List<Integer> f_173303_;
    private final List<String> f_173304_;
    private final Program f_173305_;
    private final Program f_173306_;
    private final VertexFormat f_173307_;
    @Nullable
    public final Uniform f_173308_;
    @Nullable
    public final Uniform f_173309_;
    @Nullable
    public final Uniform f_200956_;
    @Nullable
    public final Uniform f_173310_;
    @Nullable
    public final Uniform f_173311_;
    @Nullable
    public final Uniform f_173312_;
    @Nullable
    public final Uniform f_173313_;
    @Nullable
    public final Uniform f_173314_;
    @Nullable
    public final Uniform f_173315_;
    @Nullable
    public final Uniform f_173316_;
    @Nullable
    public final Uniform f_173317_;
    @Nullable
    public final Uniform f_202432_;
    @Nullable
    public final Uniform f_173318_;
    @Nullable
    public final Uniform f_173319_;
    @Nullable
    public final Uniform f_173320_;

    public ShaderInstance(ResourceProvider p_173336_, String p_173337_, VertexFormat p_173338_) throws IOException {
        this.f_173300_ = p_173337_;
        this.f_173307_ = p_173338_;
        ResourceLocation $$3 = new ResourceLocation(f_173321_ + p_173337_ + ".json");
        try (BufferedReader $$4 = p_173336_.m_215597_($$3);){
            JsonArray $$18;
            JsonArray $$13;
            JsonObject $$5 = GsonHelper.m_13859_($$4);
            String $$6 = GsonHelper.m_13906_($$5, "vertex");
            String $$7 = GsonHelper.m_13906_($$5, "fragment");
            JsonArray $$8 = GsonHelper.m_13832_($$5, "samplers", null);
            if ($$8 != null) {
                int $$9 = 0;
                for (Object $$10 : $$8) {
                    try {
                        this.m_173344_((JsonElement)$$10);
                    }
                    catch (Exception $$11) {
                        ChainedJsonException $$12 = ChainedJsonException.m_135906_($$11);
                        $$12.m_135908_("samplers[" + $$9 + "]");
                        throw $$12;
                    }
                    ++$$9;
                }
            }
            if (($$13 = GsonHelper.m_13832_($$5, "attributes", null)) != null) {
                int $$14 = 0;
                this.f_173303_ = Lists.newArrayListWithCapacity((int)$$13.size());
                this.f_173304_ = Lists.newArrayListWithCapacity((int)$$13.size());
                for (JsonElement $$15 : $$13) {
                    try {
                        this.f_173304_.add(GsonHelper.m_13805_($$15, "attribute"));
                    }
                    catch (Exception $$16) {
                        ChainedJsonException $$17 = ChainedJsonException.m_135906_($$16);
                        $$17.m_135908_("attributes[" + $$14 + "]");
                        throw $$17;
                    }
                    ++$$14;
                }
            } else {
                this.f_173303_ = null;
                this.f_173304_ = null;
            }
            if (($$18 = GsonHelper.m_13832_($$5, "uniforms", null)) != null) {
                int $$19 = 0;
                for (JsonElement $$20 : $$18) {
                    try {
                        this.m_173354_($$20);
                    }
                    catch (Exception $$21) {
                        ChainedJsonException $$22 = ChainedJsonException.m_135906_($$21);
                        $$22.m_135908_("uniforms[" + $$19 + "]");
                        throw $$22;
                    }
                    ++$$19;
                }
            }
            this.f_173302_ = ShaderInstance.m_173346_(GsonHelper.m_13841_($$5, "blend", null));
            this.f_173305_ = ShaderInstance.m_173340_(p_173336_, Program.Type.VERTEX, $$6);
            this.f_173306_ = ShaderInstance.m_173340_(p_173336_, Program.Type.FRAGMENT, $$7);
            this.f_173299_ = ProgramManager.m_85577_();
            if (this.f_173304_ != null) {
                int $$23 = 0;
                for (String $$24 : p_173338_.m_166911_()) {
                    Uniform.m_166710_(this.f_173299_, $$23, $$24);
                    this.f_173303_.add($$23);
                    ++$$23;
                }
            }
            ProgramManager.m_166623_(this);
            this.m_173366_();
        }
        catch (Exception $$26) {
            ChainedJsonException $$27 = ChainedJsonException.m_135906_($$26);
            $$27.m_135910_($$3.m_135815_());
            throw $$27;
        }
        this.m_108957_();
        this.f_173308_ = this.m_173348_("ModelViewMat");
        this.f_173309_ = this.m_173348_("ProjMat");
        this.f_200956_ = this.m_173348_("IViewRotMat");
        this.f_173310_ = this.m_173348_("TextureMat");
        this.f_173311_ = this.m_173348_("ScreenSize");
        this.f_173312_ = this.m_173348_("ColorModulator");
        this.f_173313_ = this.m_173348_("Light0_Direction");
        this.f_173314_ = this.m_173348_("Light1_Direction");
        this.f_173315_ = this.m_173348_("FogStart");
        this.f_173316_ = this.m_173348_("FogEnd");
        this.f_173317_ = this.m_173348_("FogColor");
        this.f_202432_ = this.m_173348_("FogShape");
        this.f_173318_ = this.m_173348_("LineWidth");
        this.f_173319_ = this.m_173348_("GameTime");
        this.f_173320_ = this.m_173348_("ChunkOffset");
    }

    private static Program m_173340_(final ResourceProvider p_173341_, Program.Type p_173342_, String p_173343_) throws IOException {
        Program $$10;
        Program $$3 = p_173342_.m_85570_().get(p_173343_);
        if ($$3 == null) {
            String $$4 = f_173321_ + p_173343_ + p_173342_.m_85569_();
            Resource $$5 = p_173341_.m_215593_(new ResourceLocation($$4));
            try (InputStream $$6 = $$5.m_215507_();){
                final String $$7 = FileUtil.m_179922_($$4);
                Program $$8 = Program.m_166604_(p_173342_, p_173343_, $$6, $$5.m_215506_(), new GlslPreprocessor(){
                    private final Set<String> f_173369_ = Sets.newHashSet();

                    @Override
                    public String m_142138_(boolean p_173374_, String p_173375_) {
                        String string;
                        block9: {
                            p_173375_ = FileUtil.m_179924_((p_173374_ ? $$7 : ShaderInstance.f_173322_) + p_173375_);
                            if (!this.f_173369_.add(p_173375_)) {
                                return null;
                            }
                            ResourceLocation $$2 = new ResourceLocation(p_173375_);
                            BufferedReader $$3 = p_173341_.m_215597_($$2);
                            try {
                                string = IOUtils.toString((Reader)$$3);
                                if ($$3 == null) break block9;
                            }
                            catch (Throwable throwable) {
                                try {
                                    if ($$3 != null) {
                                        try {
                                            ((Reader)$$3).close();
                                        }
                                        catch (Throwable throwable2) {
                                            throwable.addSuppressed(throwable2);
                                        }
                                    }
                                    throw throwable;
                                }
                                catch (IOException $$4) {
                                    f_173323_.error("Could not open GLSL import {}: {}", (Object)p_173375_, (Object)$$4.getMessage());
                                    return "#error " + $$4.getMessage();
                                }
                            }
                            ((Reader)$$3).close();
                        }
                        return string;
                    }
                });
            }
        } else {
            $$10 = $$3;
        }
        return $$10;
    }

    public static BlendMode m_173346_(JsonObject p_173347_) {
        if (p_173347_ == null) {
            return new BlendMode();
        }
        int $$1 = 32774;
        int $$2 = 1;
        int $$3 = 0;
        int $$4 = 1;
        int $$5 = 0;
        boolean $$6 = true;
        boolean $$7 = false;
        if (GsonHelper.m_13813_(p_173347_, "func") && ($$1 = BlendMode.m_85527_(p_173347_.get("func").getAsString())) != 32774) {
            $$6 = false;
        }
        if (GsonHelper.m_13813_(p_173347_, "srcrgb") && ($$2 = BlendMode.m_85530_(p_173347_.get("srcrgb").getAsString())) != 1) {
            $$6 = false;
        }
        if (GsonHelper.m_13813_(p_173347_, "dstrgb") && ($$3 = BlendMode.m_85530_(p_173347_.get("dstrgb").getAsString())) != 0) {
            $$6 = false;
        }
        if (GsonHelper.m_13813_(p_173347_, "srcalpha")) {
            $$4 = BlendMode.m_85530_(p_173347_.get("srcalpha").getAsString());
            if ($$4 != 1) {
                $$6 = false;
            }
            $$7 = true;
        }
        if (GsonHelper.m_13813_(p_173347_, "dstalpha")) {
            $$5 = BlendMode.m_85530_(p_173347_.get("dstalpha").getAsString());
            if ($$5 != 0) {
                $$6 = false;
            }
            $$7 = true;
        }
        if ($$6) {
            return new BlendMode();
        }
        if ($$7) {
            return new BlendMode($$2, $$3, $$4, $$5, $$1);
        }
        return new BlendMode($$2, $$3, $$1);
    }

    @Override
    public void close() {
        for (Uniform $$0 : this.f_173331_) {
            $$0.close();
        }
        ProgramManager.m_166621_(this);
    }

    public void m_173362_() {
        RenderSystem.m_187554_();
        ProgramManager.m_85578_(0);
        f_173327_ = -1;
        f_173326_ = null;
        int $$0 = GlStateManager.m_157058_();
        for (int $$1 = 0; $$1 < this.f_173330_.size(); ++$$1) {
            if (this.f_173328_.get(this.f_173329_.get($$1)) == null) continue;
            GlStateManager.m_84538_(33984 + $$1);
            GlStateManager.m_84544_(0);
        }
        GlStateManager.m_84538_($$0);
    }

    public void m_173363_() {
        RenderSystem.m_187554_();
        this.f_173301_ = false;
        f_173326_ = this;
        this.f_173302_.m_85526_();
        if (this.f_173299_ != f_173327_) {
            ProgramManager.m_85578_(this.f_173299_);
            f_173327_ = this.f_173299_;
        }
        int $$0 = GlStateManager.m_157058_();
        for (int $$1 = 0; $$1 < this.f_173330_.size(); ++$$1) {
            String $$2 = this.f_173329_.get($$1);
            if (this.f_173328_.get($$2) == null) continue;
            int $$3 = Uniform.m_85624_(this.f_173299_, $$2);
            Uniform.m_85616_($$3, $$1);
            RenderSystem.m_69388_(33984 + $$1);
            RenderSystem.m_69493_();
            Object $$4 = this.f_173328_.get($$2);
            int $$5 = -1;
            if ($$4 instanceof RenderTarget) {
                $$5 = ((RenderTarget)$$4).m_83975_();
            } else if ($$4 instanceof AbstractTexture) {
                $$5 = ((AbstractTexture)$$4).m_117963_();
            } else if ($$4 instanceof Integer) {
                $$5 = (Integer)$$4;
            }
            if ($$5 == -1) continue;
            RenderSystem.m_69396_($$5);
        }
        GlStateManager.m_84538_($$0);
        for (Uniform $$6 : this.f_173331_) {
            $$6.m_85633_();
        }
    }

    @Override
    public void m_108957_() {
        this.f_173301_ = true;
    }

    @Nullable
    public Uniform m_173348_(String p_173349_) {
        RenderSystem.m_187554_();
        return this.f_173333_.get(p_173349_);
    }

    public AbstractUniform m_173356_(String p_173357_) {
        RenderSystem.m_187552_();
        Uniform $$1 = this.m_173348_(p_173357_);
        return $$1 == null ? f_173324_ : $$1;
    }

    private void m_173366_() {
        RenderSystem.m_187554_();
        IntArrayList $$0 = new IntArrayList();
        for (int $$1 = 0; $$1 < this.f_173329_.size(); ++$$1) {
            String $$2 = this.f_173329_.get($$1);
            int $$3 = Uniform.m_85624_(this.f_173299_, $$2);
            if ($$3 == -1) {
                f_173323_.warn("Shader {} could not find sampler named {} in the specified shader program.", (Object)this.f_173300_, (Object)$$2);
                this.f_173328_.remove($$2);
                $$0.add($$1);
                continue;
            }
            this.f_173330_.add($$3);
        }
        for (int $$4 = $$0.size() - 1; $$4 >= 0; --$$4) {
            int $$5 = $$0.getInt($$4);
            this.f_173329_.remove($$5);
        }
        for (Uniform $$6 : this.f_173331_) {
            String $$7 = $$6.m_85599_();
            int $$8 = Uniform.m_85624_(this.f_173299_, $$7);
            if ($$8 == -1) {
                f_173323_.warn("Shader {} could not find uniform named {} in the specified shader program.", (Object)this.f_173300_, (Object)$$7);
                continue;
            }
            this.f_173332_.add($$8);
            $$6.m_85614_($$8);
            this.f_173333_.put($$7, $$6);
        }
    }

    private void m_173344_(JsonElement p_173345_) {
        JsonObject $$1 = GsonHelper.m_13918_(p_173345_, "sampler");
        String $$2 = GsonHelper.m_13906_($$1, "name");
        if (!GsonHelper.m_13813_($$1, "file")) {
            this.f_173328_.put($$2, null);
            this.f_173329_.add($$2);
            return;
        }
        this.f_173329_.add($$2);
    }

    public void m_173350_(String p_173351_, Object p_173352_) {
        this.f_173328_.put(p_173351_, p_173352_);
        this.m_108957_();
    }

    private void m_173354_(JsonElement p_173355_) throws ChainedJsonException {
        JsonObject $$1 = GsonHelper.m_13918_(p_173355_, "uniform");
        String $$2 = GsonHelper.m_13906_($$1, "name");
        int $$3 = Uniform.m_85629_(GsonHelper.m_13906_($$1, "type"));
        int $$4 = GsonHelper.m_13927_($$1, "count");
        float[] $$5 = new float[Math.max($$4, 16)];
        JsonArray $$6 = GsonHelper.m_13933_($$1, "values");
        if ($$6.size() != $$4 && $$6.size() > 1) {
            throw new ChainedJsonException("Invalid amount of values specified (expected " + $$4 + ", found " + $$6.size() + ")");
        }
        int $$7 = 0;
        for (JsonElement $$8 : $$6) {
            try {
                $$5[$$7] = GsonHelper.m_13888_($$8, "value");
            }
            catch (Exception $$9) {
                ChainedJsonException $$10 = ChainedJsonException.m_135906_($$9);
                $$10.m_135908_("values[" + $$7 + "]");
                throw $$10;
            }
            ++$$7;
        }
        if ($$4 > 1 && $$6.size() == 1) {
            while ($$7 < $$4) {
                $$5[$$7] = $$5[0];
                ++$$7;
            }
        }
        int $$11 = $$4 > 1 && $$4 <= 4 && $$3 < 8 ? $$4 - 1 : 0;
        Uniform $$12 = new Uniform($$2, $$3 + $$11, $$4, this);
        if ($$3 <= 3) {
            $$12.m_7401_((int)$$5[0], (int)$$5[1], (int)$$5[2], (int)$$5[3]);
        } else if ($$3 <= 7) {
            $$12.m_5808_($$5[0], $$5[1], $$5[2], $$5[3]);
        } else {
            $$12.m_5941_(Arrays.copyOfRange($$5, 0, $$4));
        }
        this.f_173331_.add($$12);
    }

    @Override
    public Program m_108962_() {
        return this.f_173305_;
    }

    @Override
    public Program m_108964_() {
        return this.f_173306_;
    }

    @Override
    public void m_142662_() {
        this.f_173306_.m_166610_(this);
        this.f_173305_.m_166610_(this);
    }

    public VertexFormat m_173364_() {
        return this.f_173307_;
    }

    public String m_173365_() {
        return this.f_173300_;
    }

    @Override
    public int m_108943_() {
        return this.f_173299_;
    }

    static {
        f_173327_ = -1;
    }
}

