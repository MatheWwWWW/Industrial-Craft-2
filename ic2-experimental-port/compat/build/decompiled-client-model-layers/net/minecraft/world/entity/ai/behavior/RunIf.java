/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class RunIf<E extends LivingEntity>
extends Behavior<E> {
    private final Predicate<E> f_23795_;
    private final Behavior<? super E> f_23796_;
    private final boolean f_23797_;

    public RunIf(Map<MemoryModuleType<?>, MemoryStatus> p_23799_, Predicate<E> p_23800_, Behavior<? super E> p_23801_, boolean p_23802_) {
        super(RunIf.m_23815_(p_23799_, p_23801_.f_22522_));
        this.f_23795_ = p_23800_;
        this.f_23796_ = p_23801_;
        this.f_23797_ = p_23802_;
    }

    private static Map<MemoryModuleType<?>, MemoryStatus> m_23815_(Map<MemoryModuleType<?>, MemoryStatus> p_23816_, Map<MemoryModuleType<?>, MemoryStatus> p_23817_) {
        HashMap $$2 = Maps.newHashMap();
        $$2.putAll(p_23816_);
        $$2.putAll(p_23817_);
        return $$2;
    }

    public RunIf(Predicate<E> p_147868_, Behavior<? super E> p_147869_, boolean p_147870_) {
        this((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), (Predicate<? super E>)p_147868_, p_147869_, p_147870_);
    }

    public RunIf(Predicate<E> p_23804_, Behavior<? super E> p_23805_) {
        this((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), (Predicate<? super E>)p_23804_, p_23805_, false);
    }

    public RunIf(Map<MemoryModuleType<?>, MemoryStatus> p_147865_, Behavior<? super E> p_147866_) {
        this(p_147865_, p_147872_ -> true, p_147866_, false);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23809_, E p_23810_) {
        return this.f_23795_.test(p_23810_) && this.f_23796_.m_6114_(p_23809_, p_23810_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23819_, E p_23820_, long p_23821_) {
        return this.f_23797_ && this.f_23795_.test(p_23820_) && this.f_23796_.m_6737_(p_23819_, p_23820_, p_23821_);
    }

    @Override
    protected boolean m_7773_(long p_23807_) {
        return false;
    }

    @Override
    protected void m_6735_(ServerLevel p_23812_, E p_23813_, long p_23814_) {
        this.f_23796_.m_6735_(p_23812_, p_23813_, p_23814_);
    }

    @Override
    protected void m_6725_(ServerLevel p_23827_, E p_23828_, long p_23829_) {
        this.f_23796_.m_6725_(p_23827_, p_23828_, p_23829_);
    }

    @Override
    protected void m_6732_(ServerLevel p_23823_, E p_23824_, long p_23825_) {
        this.f_23796_.m_6732_(p_23823_, p_23824_, p_23825_);
    }

    @Override
    public String toString() {
        return "RunIf: " + this.f_23796_;
    }
}

