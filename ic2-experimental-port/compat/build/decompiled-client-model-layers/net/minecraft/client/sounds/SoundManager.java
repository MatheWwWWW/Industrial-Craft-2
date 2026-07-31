/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.client.resources.sounds.SoundEventRegistrationSerializer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.MultipliedFloats;
import org.slf4j.Logger;

public class SoundManager
extends SimplePreparableReloadListener<Preparations> {
    public static final Sound f_120344_ = new Sound("meta:missing_sound", ConstantFloat.m_146458_(1.0f), ConstantFloat.m_146458_(1.0f), 1, Sound.Type.FILE, false, false, 16);
    static final Logger f_120345_ = LogUtils.getLogger();
    private static final String f_174997_ = "sounds.json";
    private static final Gson f_120346_ = new GsonBuilder().registerTypeHierarchyAdapter(Component.class, (Object)new Component.Serializer()).registerTypeAdapter(SoundEventRegistration.class, (Object)new SoundEventRegistrationSerializer()).create();
    private static final TypeToken<Map<String, SoundEventRegistration>> f_120347_ = new TypeToken<Map<String, SoundEventRegistration>>(){};
    private final Map<ResourceLocation, WeighedSoundEvents> f_120348_ = Maps.newHashMap();
    private final SoundEngine f_120349_;

    public SoundManager(ResourceManager p_120352_, Options p_120353_) {
        this.f_120349_ = new SoundEngine(this, p_120353_, p_120352_);
    }

    @Override
    protected Preparations m_5944_(ResourceManager p_120356_, ProfilerFiller p_120357_) {
        Preparations $$2 = new Preparations();
        p_120357_.m_7242_();
        for (String $$3 : p_120356_.m_7187_()) {
            p_120357_.m_6180_($$3);
            try {
                List<Resource> $$4 = p_120356_.m_213829_(new ResourceLocation($$3, f_174997_));
                for (Resource $$5 : $$4) {
                    p_120357_.m_6180_($$5.m_215506_());
                    try (BufferedReader $$6 = $$5.m_215508_();){
                        p_120357_.m_6180_("parse");
                        Map<String, SoundEventRegistration> $$7 = GsonHelper.m_13767_(f_120346_, $$6, f_120347_);
                        p_120357_.m_6182_("register");
                        for (Map.Entry<String, SoundEventRegistration> $$8 : $$7.entrySet()) {
                            $$2.m_120425_(new ResourceLocation($$3, $$8.getKey()), $$8.getValue(), p_120356_);
                        }
                        p_120357_.m_7238_();
                    }
                    catch (RuntimeException $$9) {
                        f_120345_.warn("Invalid {} in resourcepack: '{}'", new Object[]{f_174997_, $$5.m_215506_(), $$9});
                    }
                    p_120357_.m_7238_();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            p_120357_.m_7238_();
        }
        p_120357_.m_7241_();
        return $$2;
    }

    @Override
    protected void m_5787_(Preparations p_120377_, ResourceManager p_120378_, ProfilerFiller p_120379_) {
        p_120377_.m_120422_(this.f_120348_, this.f_120349_);
        if (SharedConstants.f_136183_) {
            for (ResourceLocation $$3 : this.f_120348_.keySet()) {
                WeighedSoundEvents $$4 = this.f_120348_.get($$3);
                if (ComponentUtils.m_237134_($$4.m_120453_()) || !Registry.f_122821_.m_7804_($$3)) continue;
                f_120345_.error("Missing subtitle {} for sound event: {}", (Object)$$4.m_120453_(), (Object)$$3);
            }
        }
        if (f_120345_.isDebugEnabled()) {
            for (ResourceLocation $$5 : this.f_120348_.keySet()) {
                if (Registry.f_122821_.m_7804_($$5)) continue;
                f_120345_.debug("Not having sound event for: {}", (Object)$$5);
            }
        }
        this.f_120349_.m_120239_();
    }

    public List<String> m_194525_() {
        return this.f_120349_.m_194511_();
    }

    static boolean m_120395_(Sound p_120396_, ResourceLocation p_120397_, ResourceManager p_120398_) {
        ResourceLocation $$3 = p_120396_.m_119790_();
        if (p_120398_.m_213713_($$3).isEmpty()) {
            f_120345_.warn("File {} does not exist, cannot add it to event {}", (Object)$$3, (Object)p_120397_);
            return false;
        }
        return true;
    }

    @Nullable
    public WeighedSoundEvents m_120384_(ResourceLocation p_120385_) {
        return this.f_120348_.get(p_120385_);
    }

    public Collection<ResourceLocation> m_120354_() {
        return this.f_120348_.keySet();
    }

    public void m_120372_(TickableSoundInstance p_120373_) {
        this.f_120349_.m_120282_(p_120373_);
    }

    public void m_120367_(SoundInstance p_120368_) {
        this.f_120349_.m_120312_(p_120368_);
    }

    public void m_120369_(SoundInstance p_120370_, int p_120371_) {
        this.f_120349_.m_120276_(p_120370_, p_120371_);
    }

    public void m_120361_(Camera p_120362_) {
        this.f_120349_.m_120270_(p_120362_);
    }

    public void m_120391_() {
        this.f_120349_.m_120314_();
    }

    public void m_120405_() {
        this.f_120349_.m_120311_();
    }

    public void m_120406_() {
        this.f_120349_.m_120304_();
    }

    public void m_120389_(boolean p_120390_) {
        this.f_120349_.m_120302_(p_120390_);
    }

    public void m_120407_() {
        this.f_120349_.m_120317_();
    }

    public void m_120358_(SoundSource p_120359_, float p_120360_) {
        if (p_120359_ == SoundSource.MASTER && p_120360_ <= 0.0f) {
            this.m_120405_();
        }
        this.f_120349_.m_120260_(p_120359_, p_120360_);
    }

    public void m_120399_(SoundInstance p_120400_) {
        this.f_120349_.m_120274_(p_120400_);
    }

    public boolean m_120403_(SoundInstance p_120404_) {
        return this.f_120349_.m_120305_(p_120404_);
    }

    public void m_120374_(SoundEventListener p_120375_) {
        this.f_120349_.m_120295_(p_120375_);
    }

    public void m_120401_(SoundEventListener p_120402_) {
        this.f_120349_.m_120307_(p_120402_);
    }

    public void m_120386_(@Nullable ResourceLocation p_120387_, @Nullable SoundSource p_120388_) {
        this.f_120349_.m_120299_(p_120387_, p_120388_);
    }

    public String m_120408_() {
        return this.f_120349_.m_120320_();
    }

    public void m_194526_() {
        this.f_120349_.m_120239_();
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }

    protected static class Preparations {
        final Map<ResourceLocation, WeighedSoundEvents> f_120413_ = Maps.newHashMap();

        protected Preparations() {
        }

        /*
         * WARNING - void declaration
         */
        void m_120425_(ResourceLocation p_120426_, SoundEventRegistration p_120427_, ResourceManager p_120428_) {
            boolean $$4;
            WeighedSoundEvents $$3 = this.f_120413_.get(p_120426_);
            boolean bl = $$4 = $$3 == null;
            if ($$4 || p_120427_.m_119823_()) {
                if (!$$4) {
                    f_120345_.debug("Replaced sound event location {}", (Object)p_120426_);
                }
                $$3 = new WeighedSoundEvents(p_120426_, p_120427_.m_119824_());
                this.f_120413_.put(p_120426_, $$3);
            }
            block4: for (final Sound $$5 : p_120427_.m_119822_()) {
                void $$9;
                final ResourceLocation $$6 = $$5.m_119787_();
                switch ($$5.m_119795_()) {
                    case FILE: {
                        if (!SoundManager.m_120395_($$5, p_120426_, p_120428_)) continue block4;
                        Sound $$7 = $$5;
                        break;
                    }
                    case SOUND_EVENT: {
                        Weighted<Sound> $$8 = new Weighted<Sound>(){

                            @Override
                            public int m_7789_() {
                                WeighedSoundEvents $$0 = f_120413_.get($$6);
                                return $$0 == null ? 0 : $$0.m_7789_();
                            }

                            @Override
                            public Sound m_213718_(RandomSource p_235261_) {
                                WeighedSoundEvents $$1 = f_120413_.get($$6);
                                if ($$1 == null) {
                                    return f_120344_;
                                }
                                Sound $$2 = $$1.m_213718_(p_235261_);
                                return new Sound($$2.m_119787_().toString(), new MultipliedFloats($$2.m_235146_(), $$5.m_235146_()), new MultipliedFloats($$2.m_235147_(), $$5.m_235147_()), $$5.m_7789_(), Sound.Type.FILE, $$2.m_119796_() || $$5.m_119796_(), $$2.m_119797_(), $$2.m_119798_());
                            }

                            @Override
                            public void m_8054_(SoundEngine p_120438_) {
                                WeighedSoundEvents $$1 = f_120413_.get($$6);
                                if ($$1 == null) {
                                    return;
                                }
                                $$1.m_8054_(p_120438_);
                            }

                            @Override
                            public /* synthetic */ Object m_213718_(RandomSource randomSource) {
                                return this.m_213718_(randomSource);
                            }
                        };
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unknown SoundEventRegistration type: " + $$5.m_119795_());
                    }
                }
                $$3.m_120451_((Weighted<Sound>)$$9);
            }
        }

        public void m_120422_(Map<ResourceLocation, WeighedSoundEvents> p_120423_, SoundEngine p_120424_) {
            p_120423_.clear();
            for (Map.Entry<ResourceLocation, WeighedSoundEvents> $$2 : this.f_120413_.entrySet()) {
                p_120423_.put($$2.getKey(), $$2.getValue());
                $$2.getValue().m_8054_(p_120424_);
            }
        }
    }
}

