/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.EffectProgram;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

public class EffectInstance
implements Effect,
AutoCloseable {
    private static final String f_172564_ = "shaders/program/";
    private static final Logger f_108921_ = LogUtils.getLogger();
    private static final AbstractUniform f_108922_ = new AbstractUniform();
    private static final boolean f_172565_ = true;
    private static EffectInstance f_108923_;
    private static int f_108924_;
    private final Map<String, IntSupplier> f_108925_ = Maps.newHashMap();
    private final List<String> f_108926_ = Lists.newArrayList();
    private final List<Integer> f_108927_ = Lists.newArrayList();
    private final List<Uniform> f_108928_ = Lists.newArrayList();
    private final List<Integer> f_108929_ = Lists.newArrayList();
    private final Map<String, Uniform> f_108930_ = Maps.newHashMap();
    private final int f_108931_;
    private final String f_108932_;
    private boolean f_108933_;
    private final BlendMode f_108934_;
    private final List<Integer> f_108935_;
    private final List<String> f_108936_;
    private final EffectProgram f_108937_;
    private final EffectProgram f_108938_;

    public EffectInstance(ResourceManager p_108941_, String p_108942_) throws IOException {
        ResourceLocation $$2 = new ResourceLocation(f_172564_ + p_108942_ + ".json");
        this.f_108932_ = p_108942_;
        Resource $$3 = p_108941_.m_215593_($$2);
        try (BufferedReader $$4 = $$3.m_215508_();){
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
                        this.m_108948_((JsonElement)$$10);
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
                this.f_108935_ = Lists.newArrayListWithCapacity((int)$$13.size());
                this.f_108936_ = Lists.newArrayListWithCapacity((int)$$13.size());
                for (Iterator $$15 : $$13) {
                    try {
                        this.f_108936_.add(GsonHelper.m_13805_((JsonElement)$$15, "attribute"));
                    }
                    catch (Exception $$16) {
                        ChainedJsonException $$17 = ChainedJsonException.m_135906_($$16);
                        $$17.m_135908_("attributes[" + $$14 + "]");
                        throw $$17;
                    }
                    ++$$14;
                }
            } else {
                this.f_108935_ = null;
                this.f_108936_ = null;
            }
            if (($$18 = GsonHelper.m_13832_($$5, "uniforms", null)) != null) {
                int $$19 = 0;
                for (JsonElement $$20 : $$18) {
                    try {
                        this.m_108958_($$20);
                    }
                    catch (Exception $$21) {
                        ChainedJsonException $$22 = ChainedJsonException.m_135906_($$21);
                        $$22.m_135908_("uniforms[" + $$19 + "]");
                        throw $$22;
                    }
                    ++$$19;
                }
            }
            this.f_108934_ = EffectInstance.m_108950_(GsonHelper.m_13841_($$5, "blend", null));
            this.f_108937_ = EffectInstance.m_172566_(p_108941_, Program.Type.VERTEX, $$6);
            this.f_108938_ = EffectInstance.m_172566_(p_108941_, Program.Type.FRAGMENT, $$7);
            this.f_108931_ = ProgramManager.m_85577_();
            ProgramManager.m_166623_(this);
            this.m_108967_();
            if (this.f_108936_ != null) {
                for (String $$23 : this.f_108936_) {
                    int $$24 = Uniform.m_85639_(this.f_108931_, $$23);
                    this.f_108935_.add($$24);
                }
            }
        }
        catch (Exception $$25) {
            ChainedJsonException $$26 = ChainedJsonException.m_135906_($$25);
            $$26.m_135910_($$2.m_135815_() + " (" + $$3.m_215506_() + ")");
            throw $$26;
        }
        this.m_108957_();
    }

    public static EffectProgram m_172566_(ResourceManager p_172567_, Program.Type p_172568_, String p_172569_) throws IOException {
        EffectProgram $$9;
        Program $$3 = p_172568_.m_85570_().get(p_172569_);
        if ($$3 != null && !($$3 instanceof EffectProgram)) {
            throw new InvalidClassException("Program is not of type EffectProgram");
        }
        if ($$3 == null) {
            ResourceLocation $$4 = new ResourceLocation(f_172564_ + p_172569_ + p_172568_.m_85569_());
            Resource $$5 = p_172567_.m_215593_($$4);
            try (InputStream $$6 = $$5.m_215507_();){
                EffectProgram $$7 = EffectProgram.m_166588_(p_172568_, p_172569_, $$6, $$5.m_215506_());
            }
        } else {
            $$9 = (EffectProgram)$$3;
        }
        return $$9;
    }

    public static BlendMode m_108950_(@Nullable JsonObject p_108951_) {
        if (p_108951_ == null) {
            return new BlendMode();
        }
        int $$1 = 32774;
        int $$2 = 1;
        int $$3 = 0;
        int $$4 = 1;
        int $$5 = 0;
        boolean $$6 = true;
        boolean $$7 = false;
        if (GsonHelper.m_13813_(p_108951_, "func") && ($$1 = BlendMode.m_85527_(p_108951_.get("func").getAsString())) != 32774) {
            $$6 = false;
        }
        if (GsonHelper.m_13813_(p_108951_, "srcrgb") && ($$2 = BlendMode.m_85530_(p_108951_.get("srcrgb").getAsString())) != 1) {
            $$6 = false;
        }
        if (GsonHelper.m_13813_(p_108951_, "dstrgb") && ($$3 = BlendMode.m_85530_(p_108951_.get("dstrgb").getAsString())) != 0) {
            $$6 = false;
        }
        if (GsonHelper.m_13813_(p_108951_, "srcalpha")) {
            $$4 = BlendMode.m_85530_(p_108951_.get("srcalpha").getAsString());
            if ($$4 != 1) {
                $$6 = false;
            }
            $$7 = true;
        }
        if (GsonHelper.m_13813_(p_108951_, "dstalpha")) {
            $$5 = BlendMode.m_85530_(p_108951_.get("dstalpha").getAsString());
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
        for (Uniform $$0 : this.f_108928_) {
            $$0.close();
        }
        ProgramManager.m_166621_(this);
    }

    public void m_108965_() {
        RenderSystem.m_187554_();
        ProgramManager.m_85578_(0);
        f_108924_ = -1;
        f_108923_ = null;
        for (int $$0 = 0; $$0 < this.f_108927_.size(); ++$$0) {
            if (this.f_108925_.get(this.f_108926_.get($$0)) == null) continue;
            GlStateManager.m_84538_(33984 + $$0);
            GlStateManager.m_84110_();
            GlStateManager.m_84544_(0);
        }
    }

    public void m_108966_() {
        RenderSystem.m_187552_();
        this.f_108933_ = false;
        f_108923_ = this;
        this.f_108934_.m_85526_();
        if (this.f_108931_ != f_108924_) {
            ProgramManager.m_85578_(this.f_108931_);
            f_108924_ = this.f_108931_;
        }
        for (int $$0 = 0; $$0 < this.f_108927_.size(); ++$$0) {
            String $$1 = this.f_108926_.get($$0);
            IntSupplier $$2 = this.f_108925_.get($$1);
            if ($$2 == null) continue;
            RenderSystem.m_69388_(33984 + $$0);
            RenderSystem.m_69493_();
            int $$3 = $$2.getAsInt();
            if ($$3 == -1) continue;
            RenderSystem.m_69396_($$3);
            Uniform.m_85616_(this.f_108927_.get($$0), $$0);
        }
        for (Uniform $$4 : this.f_108928_) {
            $$4.m_85633_();
        }
    }

    @Override
    public void m_108957_() {
        this.f_108933_ = true;
    }

    @Nullable
    public Uniform m_108952_(String p_108953_) {
        RenderSystem.m_187554_();
        return this.f_108930_.get(p_108953_);
    }

    public AbstractUniform m_108960_(String p_108961_) {
        RenderSystem.m_187552_();
        Uniform $$1 = this.m_108952_(p_108961_);
        return $$1 == null ? f_108922_ : $$1;
    }

    private void m_108967_() {
        RenderSystem.m_187554_();
        IntArrayList $$0 = new IntArrayList();
        for (int $$1 = 0; $$1 < this.f_108926_.size(); ++$$1) {
            String $$2 = this.f_108926_.get($$1);
            int $$3 = Uniform.m_85624_(this.f_108931_, $$2);
            if ($$3 == -1) {
                f_108921_.warn("Shader {} could not find sampler named {} in the specified shader program.", (Object)this.f_108932_, (Object)$$2);
                this.f_108925_.remove($$2);
                $$0.add($$1);
                continue;
            }
            this.f_108927_.add($$3);
        }
        for (int $$4 = $$0.size() - 1; $$4 >= 0; --$$4) {
            this.f_108926_.remove($$0.getInt($$4));
        }
        for (Uniform $$5 : this.f_108928_) {
            String $$6 = $$5.m_85599_();
            int $$7 = Uniform.m_85624_(this.f_108931_, $$6);
            if ($$7 == -1) {
                f_108921_.warn("Shader {} could not find uniform named {} in the specified shader program.", (Object)this.f_108932_, (Object)$$6);
                continue;
            }
            this.f_108929_.add($$7);
            $$5.m_85614_($$7);
            this.f_108930_.put($$6, $$5);
        }
    }

    private void m_108948_(JsonElement p_108949_) {
        JsonObject $$1 = GsonHelper.m_13918_(p_108949_, "sampler");
        String $$2 = GsonHelper.m_13906_($$1, "name");
        if (!GsonHelper.m_13813_($$1, "file")) {
            this.f_108925_.put($$2, null);
            this.f_108926_.add($$2);
            return;
        }
        this.f_108926_.add($$2);
    }

    public void m_108954_(String p_108955_, IntSupplier p_108956_) {
        if (this.f_108925_.containsKey(p_108955_)) {
            this.f_108925_.remove(p_108955_);
        }
        this.f_108925_.put(p_108955_, p_108956_);
        this.m_108957_();
    }

    private void m_108958_(JsonElement p_108959_) throws ChainedJsonException {
        JsonObject $$1 = GsonHelper.m_13918_(p_108959_, "uniform");
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
            $$12.m_5941_($$5);
        }
        this.f_108928_.add($$12);
    }

    @Override
    public Program m_108962_() {
        return this.f_108937_;
    }

    @Override
    public Program m_108964_() {
        return this.f_108938_;
    }

    @Override
    public void m_142662_() {
        this.f_108938_.m_166586_(this);
        this.f_108937_.m_166586_(this);
    }

    public String m_172571_() {
        return this.f_108932_;
    }

    @Override
    public int m_108943_() {
        return this.f_108931_;
    }

    static {
        f_108924_ = -1;
    }
}

