/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.Long2ObjectFunction
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.entity;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.level.entity.EntityLookup;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.LevelEntityGetterAdapter;
import net.minecraft.world.level.entity.Visibility;
import org.slf4j.Logger;

public class TransientEntitySectionManager<T extends EntityAccess> {
    static final Logger f_157635_ = LogUtils.getLogger();
    final LevelCallback<T> f_157636_;
    final EntityLookup<T> f_157637_;
    final EntitySectionStorage<T> f_157638_;
    private final LongSet f_157639_ = new LongOpenHashSet();
    private final LevelEntityGetter<T> f_157640_;

    public TransientEntitySectionManager(Class<T> p_157643_, LevelCallback<T> p_157644_) {
        this.f_157637_ = new EntityLookup();
        this.f_157638_ = new EntitySectionStorage<T>(p_157643_, (Long2ObjectFunction<Visibility>)((Long2ObjectFunction)p_157647_ -> this.f_157639_.contains(p_157647_) ? Visibility.TICKING : Visibility.TRACKED));
        this.f_157636_ = p_157644_;
        this.f_157640_ = new LevelEntityGetterAdapter<T>(this.f_157637_, this.f_157638_);
    }

    public void m_157651_(ChunkPos p_157652_) {
        long $$1 = p_157652_.m_45588_();
        this.f_157639_.add($$1);
        this.f_157638_.m_156888_($$1).forEach(p_157663_ -> {
            Visibility $$1 = p_157663_.m_156838_(Visibility.TICKING);
            if (!$$1.m_157691_()) {
                p_157663_.m_156845_().filter(p_157666_ -> !p_157666_.m_142389_()).forEach(this.f_157636_::m_141987_);
            }
        });
    }

    public void m_157658_(ChunkPos p_157659_) {
        long $$1 = p_157659_.m_45588_();
        this.f_157639_.remove($$1);
        this.f_157638_.m_156888_($$1).forEach(p_157656_ -> {
            Visibility $$1 = p_157656_.m_156838_(Visibility.TRACKED);
            if ($$1.m_157691_()) {
                p_157656_.m_156845_().filter(p_157661_ -> !p_157661_.m_142389_()).forEach(this.f_157636_::m_141983_);
            }
        });
    }

    public LevelEntityGetter<T> m_157645_() {
        return this.f_157640_;
    }

    public void m_157653_(T p_157654_) {
        this.f_157637_.m_156814_(p_157654_);
        long $$1 = SectionPos.m_175568_(p_157654_.m_20183_());
        EntitySection<T> $$2 = this.f_157638_.m_156893_($$1);
        $$2.m_188346_(p_157654_);
        p_157654_.m_141960_(new Callback(this, p_157654_, $$1, $$2));
        this.f_157636_.m_141989_(p_157654_);
        this.f_157636_.m_141985_(p_157654_);
        if (p_157654_.m_142389_() || $$2.m_156848_().m_157691_()) {
            this.f_157636_.m_141987_(p_157654_);
        }
    }

    @VisibleForDebug
    public int m_157657_() {
        return this.f_157637_.m_156821_();
    }

    void m_157648_(long p_157649_, EntitySection<T> p_157650_) {
        if (p_157650_.m_156833_()) {
            this.f_157638_.m_156897_(p_157649_);
        }
    }

    @VisibleForDebug
    public String m_157664_() {
        return this.f_157637_.m_156821_() + "," + this.f_157638_.m_156887_() + "," + this.f_157639_.size();
    }

    class Callback
    implements EntityInLevelCallback {
        private final T f_157668_;
        private long f_157669_;
        private EntitySection<T> f_157670_;
        final /* synthetic */ TransientEntitySectionManager f_157667_;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        Callback(T t, long p_157675_, EntitySection<T> entitySection) {
            void var3_3;
            void p_157673_;
            this.f_157667_ = (TransientEntitySectionManager)l;
            this.f_157668_ = p_157673_;
            this.f_157669_ = var3_3;
            this.f_157670_ = (EntitySection)p_157675_;
        }

        @Override
        public void m_142044_() {
            BlockPos $$0 = this.f_157668_.m_20183_();
            long $$1 = SectionPos.m_175568_($$0);
            if ($$1 != this.f_157669_) {
                Visibility $$2 = this.f_157670_.m_156848_();
                if (!this.f_157670_.m_188355_(this.f_157668_)) {
                    f_157635_.warn("Entity {} wasn't found in section {} (moving to {})", new Object[]{this.f_157668_, SectionPos.m_123184_(this.f_157669_), $$1});
                }
                this.f_157667_.m_157648_(this.f_157669_, this.f_157670_);
                EntitySection $$3 = this.f_157667_.f_157638_.m_156893_($$1);
                $$3.m_188346_(this.f_157668_);
                this.f_157670_ = $$3;
                this.f_157669_ = $$1;
                this.f_157667_.f_157636_.m_214006_(this.f_157668_);
                if (!this.f_157668_.m_142389_()) {
                    boolean $$4 = $$2.m_157691_();
                    boolean $$5 = $$3.m_156848_().m_157691_();
                    if ($$4 && !$$5) {
                        this.f_157667_.f_157636_.m_141983_(this.f_157668_);
                    } else if (!$$4 && $$5) {
                        this.f_157667_.f_157636_.m_141987_(this.f_157668_);
                    }
                }
            }
        }

        @Override
        public void m_142472_(Entity.RemovalReason p_157678_) {
            Visibility $$1;
            if (!this.f_157670_.m_188355_(this.f_157668_)) {
                f_157635_.warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[]{this.f_157668_, SectionPos.m_123184_(this.f_157669_), p_157678_});
            }
            if (($$1 = this.f_157670_.m_156848_()).m_157691_() || this.f_157668_.m_142389_()) {
                this.f_157667_.f_157636_.m_141983_(this.f_157668_);
            }
            this.f_157667_.f_157636_.m_141981_(this.f_157668_);
            this.f_157667_.f_157636_.m_141986_(this.f_157668_);
            this.f_157667_.f_157637_.m_156822_(this.f_157668_);
            this.f_157668_.m_141960_(f_156799_);
            this.f_157667_.m_157648_(this.f_157669_, this.f_157670_);
        }
    }
}

