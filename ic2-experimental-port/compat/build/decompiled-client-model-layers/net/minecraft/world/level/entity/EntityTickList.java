/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;

public class EntityTickList {
    private Int2ObjectMap<Entity> f_156903_ = new Int2ObjectLinkedOpenHashMap();
    private Int2ObjectMap<Entity> f_156904_ = new Int2ObjectLinkedOpenHashMap();
    @Nullable
    private Int2ObjectMap<Entity> f_156905_;

    private void m_156907_() {
        if (this.f_156905_ == this.f_156903_) {
            this.f_156904_.clear();
            for (Int2ObjectMap.Entry $$0 : Int2ObjectMaps.fastIterable(this.f_156903_)) {
                this.f_156904_.put($$0.getIntKey(), (Object)((Entity)$$0.getValue()));
            }
            Int2ObjectMap<Entity> $$1 = this.f_156903_;
            this.f_156903_ = this.f_156904_;
            this.f_156904_ = $$1;
        }
    }

    public void m_156908_(Entity p_156909_) {
        this.m_156907_();
        this.f_156903_.put(p_156909_.m_19879_(), (Object)p_156909_);
    }

    public void m_156912_(Entity p_156913_) {
        this.m_156907_();
        this.f_156903_.remove(p_156913_.m_19879_());
    }

    public boolean m_156914_(Entity p_156915_) {
        return this.f_156903_.containsKey(p_156915_.m_19879_());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_156910_(Consumer<Entity> p_156911_) {
        if (this.f_156905_ != null) {
            throw new UnsupportedOperationException("Only one concurrent iteration supported");
        }
        this.f_156905_ = this.f_156903_;
        try {
            for (Entity $$1 : this.f_156903_.values()) {
                p_156911_.accept($$1);
            }
        }
        finally {
            this.f_156905_ = null;
        }
    }
}

