/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Multimap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ValidationContext {
    private final Multimap<String, String> f_79332_;
    private final Supplier<String> f_79333_;
    private final LootContextParamSet f_79334_;
    private final Function<ResourceLocation, LootItemCondition> f_79335_;
    private final Set<ResourceLocation> f_79336_;
    private final Function<ResourceLocation, LootTable> f_79337_;
    private final Set<ResourceLocation> f_79338_;
    private String f_79339_;

    public ValidationContext(LootContextParamSet p_79349_, Function<ResourceLocation, LootItemCondition> p_79350_, Function<ResourceLocation, LootTable> p_79351_) {
        this((Multimap<String, String>)HashMultimap.create(), () -> "", p_79349_, p_79350_, (Set<ResourceLocation>)ImmutableSet.of(), p_79351_, (Set<ResourceLocation>)ImmutableSet.of());
    }

    public ValidationContext(Multimap<String, String> p_79341_, Supplier<String> p_79342_, LootContextParamSet p_79343_, Function<ResourceLocation, LootItemCondition> p_79344_, Set<ResourceLocation> p_79345_, Function<ResourceLocation, LootTable> p_79346_, Set<ResourceLocation> p_79347_) {
        this.f_79332_ = p_79341_;
        this.f_79333_ = p_79342_;
        this.f_79334_ = p_79343_;
        this.f_79335_ = p_79344_;
        this.f_79336_ = p_79345_;
        this.f_79337_ = p_79346_;
        this.f_79338_ = p_79347_;
    }

    private String m_79364_() {
        if (this.f_79339_ == null) {
            this.f_79339_ = this.f_79333_.get();
        }
        return this.f_79339_;
    }

    public void m_79357_(String p_79358_) {
        this.f_79332_.put((Object)this.m_79364_(), (Object)p_79358_);
    }

    public ValidationContext m_79365_(String p_79366_) {
        return new ValidationContext(this.f_79332_, () -> this.m_79364_() + p_79366_, this.f_79334_, this.f_79335_, this.f_79336_, this.f_79337_, this.f_79338_);
    }

    public ValidationContext m_79359_(String p_79360_, ResourceLocation p_79361_) {
        ImmutableSet $$2 = ImmutableSet.builder().addAll(this.f_79338_).add((Object)p_79361_).build();
        return new ValidationContext(this.f_79332_, () -> this.m_79364_() + p_79360_, this.f_79334_, this.f_79335_, this.f_79336_, this.f_79337_, (Set<ResourceLocation>)$$2);
    }

    public ValidationContext m_79367_(String p_79368_, ResourceLocation p_79369_) {
        ImmutableSet $$2 = ImmutableSet.builder().addAll(this.f_79336_).add((Object)p_79369_).build();
        return new ValidationContext(this.f_79332_, () -> this.m_79364_() + p_79368_, this.f_79334_, this.f_79335_, (Set<ResourceLocation>)$$2, this.f_79337_, this.f_79338_);
    }

    public boolean m_79362_(ResourceLocation p_79363_) {
        return this.f_79338_.contains(p_79363_);
    }

    public boolean m_79370_(ResourceLocation p_79371_) {
        return this.f_79336_.contains(p_79371_);
    }

    public Multimap<String, String> m_79352_() {
        return ImmutableMultimap.copyOf(this.f_79332_);
    }

    public void m_79353_(LootContextUser p_79354_) {
        this.f_79334_.m_81395_(this, p_79354_);
    }

    @Nullable
    public LootTable m_79375_(ResourceLocation p_79376_) {
        return this.f_79337_.apply(p_79376_);
    }

    @Nullable
    public LootItemCondition m_79379_(ResourceLocation p_79380_) {
        return this.f_79335_.apply(p_79380_);
    }

    public ValidationContext m_79355_(LootContextParamSet p_79356_) {
        return new ValidationContext(this.f_79332_, this.f_79333_, p_79356_, this.f_79335_, this.f_79336_, this.f_79337_, this.f_79338_);
    }
}

