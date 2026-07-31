/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.Marker
 *  org.slf4j.MarkerFactory
 */
package net.minecraft.client.sounds;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import com.mojang.blaze3d.audio.Listener;
import com.mojang.blaze3d.audio.SoundBuffer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundEngineExecutor;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class SoundEngine {
    private static final Marker f_120214_ = MarkerFactory.getMarker((String)"SOUNDS");
    private static final Logger f_120215_ = LogUtils.getLogger();
    private static final float f_174983_ = 0.5f;
    private static final float f_174984_ = 2.0f;
    private static final float f_174985_ = 0.0f;
    private static final float f_174986_ = 1.0f;
    private static final int f_174987_ = 20;
    private static final Set<ResourceLocation> f_120216_ = Sets.newHashSet();
    private static final long f_194471_ = 1000L;
    public static final String f_174982_ = "FOR THE DEBUG!";
    public static final String f_194469_ = "OpenAL Soft on ";
    public static final int f_194470_ = "OpenAL Soft on ".length();
    private final SoundManager f_120217_;
    private final Options f_120218_;
    private boolean f_120219_;
    private final Library f_120220_ = new Library();
    private final Listener f_120221_ = this.f_120220_.m_83700_();
    private final SoundBufferLibrary f_120222_;
    private final SoundEngineExecutor f_120223_ = new SoundEngineExecutor();
    private final ChannelAccess f_120224_ = new ChannelAccess(this.f_120220_, this.f_120223_);
    private int f_120225_;
    private long f_194472_;
    private final AtomicReference<DeviceCheckState> f_194473_ = new AtomicReference<DeviceCheckState>(DeviceCheckState.NO_CHANGE);
    private final Map<SoundInstance, ChannelAccess.ChannelHandle> f_120226_ = Maps.newHashMap();
    private final Multimap<SoundSource, SoundInstance> f_120227_ = HashMultimap.create();
    private final List<TickableSoundInstance> f_120228_ = Lists.newArrayList();
    private final Map<SoundInstance, Integer> f_120229_ = Maps.newHashMap();
    private final Map<SoundInstance, Integer> f_120230_ = Maps.newHashMap();
    private final List<SoundEventListener> f_120231_ = Lists.newArrayList();
    private final List<TickableSoundInstance> f_120232_ = Lists.newArrayList();
    private final List<Sound> f_120233_ = Lists.newArrayList();

    public SoundEngine(SoundManager p_120236_, Options p_120237_, ResourceManager p_120238_) {
        this.f_120217_ = p_120236_;
        this.f_120218_ = p_120237_;
        this.f_120222_ = new SoundBufferLibrary(p_120238_);
    }

    public void m_120239_() {
        f_120216_.clear();
        for (SoundEvent $$0 : Registry.f_122821_) {
            ResourceLocation $$1 = $$0.m_11660_();
            if (this.f_120217_.m_120384_($$1) != null) continue;
            f_120215_.warn("Missing sound for event: {}", (Object)Registry.f_122821_.m_7981_($$0));
            f_120216_.add($$1);
        }
        this.m_120304_();
        this.m_120323_();
    }

    private synchronized void m_120323_() {
        if (this.f_120219_) {
            return;
        }
        try {
            String $$0 = this.f_120218_.m_231931_().m_231551_();
            this.f_120220_.m_231084_("".equals($$0) ? null : $$0, this.f_120218_.m_231826_().m_231551_());
            this.f_120221_.m_83745_();
            this.f_120221_.m_83737_(this.f_120218_.m_92147_(SoundSource.MASTER));
            this.f_120222_.m_120198_(this.f_120233_).thenRun(this.f_120233_::clear);
            this.f_120219_ = true;
            f_120215_.info(f_120214_, "Sound engine started");
        }
        catch (RuntimeException $$1) {
            f_120215_.error(f_120214_, "Error starting SoundSystem. Turning off sounds & music", (Throwable)$$1);
        }
    }

    private float m_120258_(@Nullable SoundSource p_120259_) {
        if (p_120259_ == null || p_120259_ == SoundSource.MASTER) {
            return 1.0f;
        }
        return this.f_120218_.m_92147_(p_120259_);
    }

    public void m_120260_(SoundSource p_120261_, float p_120262_) {
        if (!this.f_120219_) {
            return;
        }
        if (p_120261_ == SoundSource.MASTER) {
            this.f_120221_.m_83737_(p_120262_);
            return;
        }
        this.f_120226_.forEach((p_120280_, p_120281_) -> {
            float $$2 = this.m_120327_((SoundInstance)p_120280_);
            p_120281_.m_120154_(p_174990_ -> {
                if ($$2 <= 0.0f) {
                    p_174990_.m_83679_();
                } else {
                    p_174990_.m_83666_($$2);
                }
            });
        });
    }

    public void m_120304_() {
        if (this.f_120219_) {
            this.m_120311_();
            this.f_120222_.m_120193_();
            this.f_120220_.m_83699_();
            this.f_120219_ = false;
        }
    }

    public void m_120274_(SoundInstance p_120275_) {
        ChannelAccess.ChannelHandle $$1;
        if (this.f_120219_ && ($$1 = this.f_120226_.get(p_120275_)) != null) {
            $$1.m_120154_(Channel::m_83679_);
        }
    }

    public void m_120311_() {
        if (this.f_120219_) {
            this.f_120223_.m_120332_();
            this.f_120226_.values().forEach(p_120288_ -> p_120288_.m_120154_(Channel::m_83679_));
            this.f_120226_.clear();
            this.f_120224_.m_120139_();
            this.f_120229_.clear();
            this.f_120228_.clear();
            this.f_120227_.clear();
            this.f_120230_.clear();
            this.f_120232_.clear();
        }
    }

    public void m_120295_(SoundEventListener p_120296_) {
        this.f_120231_.add(p_120296_);
    }

    public void m_120307_(SoundEventListener p_120308_) {
        this.f_120231_.remove(p_120308_);
    }

    private boolean m_194512_() {
        boolean $$1;
        if (this.f_120220_.m_193478_()) {
            f_120215_.info("Audio device was lost!");
            return true;
        }
        long $$0 = Util.m_137550_();
        boolean bl = $$1 = $$0 - this.f_194472_ >= 1000L;
        if ($$1) {
            this.f_194472_ = $$0;
            if (this.f_194473_.compareAndSet(DeviceCheckState.NO_CHANGE, DeviceCheckState.ONGOING)) {
                String $$2 = this.f_120218_.m_231931_().m_231551_();
                Util.m_183992_().execute(() -> {
                    if ("".equals($$2)) {
                        if (this.f_120220_.m_193474_()) {
                            f_120215_.info("System default audio device has changed!");
                            this.f_194473_.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.CHANGE_DETECTED);
                        }
                    } else if (!this.f_120220_.m_193471_().equals($$2) && this.f_120220_.m_193477_().contains($$2)) {
                        f_120215_.info("Preferred audio device has become available!");
                        this.f_194473_.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.CHANGE_DETECTED);
                    }
                    this.f_194473_.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.NO_CHANGE);
                });
            }
        }
        return this.f_194473_.compareAndSet(DeviceCheckState.CHANGE_DETECTED, DeviceCheckState.NO_CHANGE);
    }

    public void m_120302_(boolean p_120303_) {
        if (this.m_194512_()) {
            this.m_120239_();
        }
        if (!p_120303_) {
            this.m_120326_();
        }
        this.f_120224_.m_120127_();
    }

    private void m_120326_() {
        ++this.f_120225_;
        this.f_120232_.stream().filter(SoundInstance::m_7767_).forEach(this::m_120312_);
        this.f_120232_.clear();
        for (TickableSoundInstance $$0 : this.f_120228_) {
            if (!$$0.m_7767_()) {
                this.m_120274_($$0);
            }
            $$0.m_7788_();
            if ($$0.m_7801_()) {
                this.m_120274_($$0);
                continue;
            }
            float $$1 = this.m_120327_($$0);
            float $$2 = this.m_120324_($$0);
            Vec3 $$3 = new Vec3($$0.m_7772_(), $$0.m_7780_(), $$0.m_7778_());
            ChannelAccess.ChannelHandle $$4 = this.f_120226_.get($$0);
            if ($$4 == null) continue;
            $$4.m_120154_(p_194478_ -> {
                p_194478_.m_83666_($$1);
                p_194478_.m_83650_($$2);
                p_194478_.m_83654_($$3);
            });
        }
        Iterator<Map.Entry<SoundInstance, ChannelAccess.ChannelHandle>> $$5 = this.f_120226_.entrySet().iterator();
        while ($$5.hasNext()) {
            int $$10;
            Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> $$6 = $$5.next();
            ChannelAccess.ChannelHandle $$7 = $$6.getValue();
            SoundInstance $$8 = $$6.getKey();
            float $$9 = this.f_120218_.m_92147_($$8.m_8070_());
            if ($$9 <= 0.0f) {
                $$7.m_120154_(Channel::m_83679_);
                $$5.remove();
                continue;
            }
            if (!$$7.m_120151_() || ($$10 = this.f_120230_.get($$8).intValue()) > this.f_120225_) continue;
            if (SoundEngine.m_120318_($$8)) {
                this.f_120229_.put($$8, this.f_120225_ + $$8.m_7766_());
            }
            $$5.remove();
            f_120215_.debug(f_120214_, "Removed channel {} because it's not playing anymore", (Object)$$7);
            this.f_120230_.remove($$8);
            try {
                this.f_120227_.remove((Object)$$8.m_8070_(), (Object)$$8);
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            if (!($$8 instanceof TickableSoundInstance)) continue;
            this.f_120228_.remove($$8);
        }
        Iterator<Map.Entry<SoundInstance, Integer>> $$11 = this.f_120229_.entrySet().iterator();
        while ($$11.hasNext()) {
            Map.Entry<SoundInstance, Integer> $$12 = $$11.next();
            if (this.f_120225_ < $$12.getValue()) continue;
            SoundInstance $$13 = $$12.getKey();
            if ($$13 instanceof TickableSoundInstance) {
                ((TickableSoundInstance)$$13).m_7788_();
            }
            this.m_120312_($$13);
            $$11.remove();
        }
    }

    private static boolean m_120315_(SoundInstance p_120316_) {
        return p_120316_.m_7766_() > 0;
    }

    private static boolean m_120318_(SoundInstance p_120319_) {
        return p_120319_.m_7775_() && SoundEngine.m_120315_(p_120319_);
    }

    private static boolean m_120321_(SoundInstance p_120322_) {
        return p_120322_.m_7775_() && !SoundEngine.m_120315_(p_120322_);
    }

    public boolean m_120305_(SoundInstance p_120306_) {
        if (!this.f_120219_) {
            return false;
        }
        if (this.f_120230_.containsKey(p_120306_) && this.f_120230_.get(p_120306_) <= this.f_120225_) {
            return true;
        }
        return this.f_120226_.containsKey(p_120306_);
    }

    public void m_120312_(SoundInstance p_120313_) {
        if (!this.f_120219_) {
            return;
        }
        if (!p_120313_.m_7767_()) {
            return;
        }
        WeighedSoundEvents $$1 = p_120313_.m_6775_(this.f_120217_);
        ResourceLocation $$2 = p_120313_.m_7904_();
        if ($$1 == null) {
            if (f_120216_.add($$2)) {
                f_120215_.warn(f_120214_, "Unable to play unknown soundEvent: {}", (Object)$$2);
            }
            return;
        }
        Sound $$3 = p_120313_.m_5891_();
        if ($$3 == SoundManager.f_120344_) {
            if (f_120216_.add($$2)) {
                f_120215_.warn(f_120214_, "Unable to play empty soundEvent: {}", (Object)$$2);
            }
            return;
        }
        float $$4 = p_120313_.m_7769_();
        float $$5 = Math.max($$4, 1.0f) * (float)$$3.m_119798_();
        SoundSource $$6 = p_120313_.m_8070_();
        float $$7 = this.m_235257_($$4, $$6);
        float $$8 = this.m_120324_(p_120313_);
        SoundInstance.Attenuation $$9 = p_120313_.m_7438_();
        boolean $$10 = p_120313_.m_7796_();
        if ($$7 == 0.0f && !p_120313_.m_7784_()) {
            f_120215_.debug(f_120214_, "Skipped playing sound {}, volume was zero.", (Object)$$3.m_119787_());
            return;
        }
        Vec3 $$11 = new Vec3(p_120313_.m_7772_(), p_120313_.m_7780_(), p_120313_.m_7778_());
        if (!this.f_120231_.isEmpty()) {
            boolean $$12;
            boolean bl = $$12 = $$10 || $$9 == SoundInstance.Attenuation.NONE || this.f_120221_.m_83736_().m_82557_($$11) < (double)($$5 * $$5);
            if ($$12) {
                for (SoundEventListener $$13 : this.f_120231_) {
                    $$13.m_6985_(p_120313_, $$1);
                }
            } else {
                f_120215_.debug(f_120214_, "Did not notify listeners of soundEvent: {}, it is too far away to hear", (Object)$$2);
            }
        }
        if (this.f_120221_.m_83744_() <= 0.0f) {
            f_120215_.debug(f_120214_, "Skipped playing soundEvent: {}, master volume was zero", (Object)$$2);
            return;
        }
        boolean $$14 = SoundEngine.m_120321_(p_120313_);
        boolean $$15 = $$3.m_119796_();
        CompletableFuture<ChannelAccess.ChannelHandle> $$16 = this.f_120224_.m_120128_($$3.m_119796_() ? Library.Pool.STREAMING : Library.Pool.STATIC);
        ChannelAccess.ChannelHandle $$17 = $$16.join();
        if ($$17 == null) {
            if (SharedConstants.f_136183_) {
                f_120215_.warn("Failed to create new sound handle");
            }
            return;
        }
        f_120215_.debug(f_120214_, "Playing sound {} for event {}", (Object)$$3.m_119787_(), (Object)$$2);
        this.f_120230_.put(p_120313_, this.f_120225_ + 20);
        this.f_120226_.put(p_120313_, $$17);
        this.f_120227_.put((Object)$$6, (Object)p_120313_);
        $$17.m_120154_(p_194488_ -> {
            p_194488_.m_83650_($$8);
            p_194488_.m_83666_($$7);
            if ($$9 == SoundInstance.Attenuation.LINEAR) {
                p_194488_.m_83673_($$5);
            } else {
                p_194488_.m_83681_();
            }
            p_194488_.m_83663_($$14 && !$$15);
            p_194488_.m_83654_($$11);
            p_194488_.m_83670_($$10);
        });
        if (!$$15) {
            this.f_120222_.m_120202_($$3.m_119790_()).thenAccept(p_194501_ -> $$17.m_120154_(p_194495_ -> {
                p_194495_.m_83656_((SoundBuffer)p_194501_);
                p_194495_.m_83672_();
            }));
        } else {
            this.f_120222_.m_120204_($$3.m_119790_(), $$14).thenAccept(p_194504_ -> $$17.m_120154_(p_194498_ -> {
                p_194498_.m_83658_((AudioStream)p_194504_);
                p_194498_.m_83672_();
            }));
        }
        if (p_120313_ instanceof TickableSoundInstance) {
            this.f_120228_.add((TickableSoundInstance)p_120313_);
        }
    }

    public void m_120282_(TickableSoundInstance p_120283_) {
        this.f_120232_.add(p_120283_);
    }

    public void m_120272_(Sound p_120273_) {
        this.f_120233_.add(p_120273_);
    }

    private float m_120324_(SoundInstance p_120325_) {
        return Mth.m_14036_(p_120325_.m_7783_(), 0.5f, 2.0f);
    }

    private float m_120327_(SoundInstance p_120328_) {
        return this.m_235257_(p_120328_.m_7769_(), p_120328_.m_8070_());
    }

    private float m_235257_(float p_235258_, SoundSource p_235259_) {
        return Mth.m_14036_(p_235258_ * this.m_120258_(p_235259_), 0.0f, 1.0f);
    }

    public void m_120314_() {
        if (this.f_120219_) {
            this.f_120224_.m_120137_(p_194510_ -> p_194510_.forEach(Channel::m_83677_));
        }
    }

    public void m_120317_() {
        if (this.f_120219_) {
            this.f_120224_.m_120137_(p_194508_ -> p_194508_.forEach(Channel::m_83678_));
        }
    }

    public void m_120276_(SoundInstance p_120277_, int p_120278_) {
        this.f_120229_.put(p_120277_, this.f_120225_ + p_120278_);
    }

    public void m_120270_(Camera p_120271_) {
        if (!this.f_120219_ || !p_120271_.m_90593_()) {
            return;
        }
        Vec3 $$1 = p_120271_.m_90583_();
        Vector3f $$2 = p_120271_.m_90596_();
        Vector3f $$3 = p_120271_.m_90597_();
        this.f_120223_.execute(() -> {
            this.f_120221_.m_83739_($$1);
            this.f_120221_.m_83741_($$2, $$3);
        });
    }

    public void m_120299_(@Nullable ResourceLocation p_120300_, @Nullable SoundSource p_120301_) {
        if (p_120301_ != null) {
            for (SoundInstance $$2 : this.f_120227_.get((Object)p_120301_)) {
                if (p_120300_ != null && !$$2.m_7904_().equals(p_120300_)) continue;
                this.m_120274_($$2);
            }
        } else if (p_120300_ == null) {
            this.m_120311_();
        } else {
            for (SoundInstance $$3 : this.f_120226_.keySet()) {
                if (!$$3.m_7904_().equals(p_120300_)) continue;
                this.m_120274_($$3);
            }
        }
    }

    public String m_120320_() {
        return this.f_120220_.m_83701_();
    }

    public List<String> m_194511_() {
        return this.f_120220_.m_193477_();
    }

    static final class DeviceCheckState
    extends Enum<DeviceCheckState> {
        public static final /* enum */ DeviceCheckState ONGOING = new DeviceCheckState();
        public static final /* enum */ DeviceCheckState CHANGE_DETECTED = new DeviceCheckState();
        public static final /* enum */ DeviceCheckState NO_CHANGE = new DeviceCheckState();
        private static final /* synthetic */ DeviceCheckState[] $VALUES;

        public static DeviceCheckState[] values() {
            return (DeviceCheckState[])$VALUES.clone();
        }

        public static DeviceCheckState valueOf(String p_194523_) {
            return Enum.valueOf(DeviceCheckState.class, p_194523_);
        }

        private static /* synthetic */ DeviceCheckState[] m_194521_() {
            return new DeviceCheckState[]{ONGOING, CHANGE_DETECTED, NO_CHANGE};
        }

        static {
            $VALUES = DeviceCheckState.m_194521_();
        }
    }
}

