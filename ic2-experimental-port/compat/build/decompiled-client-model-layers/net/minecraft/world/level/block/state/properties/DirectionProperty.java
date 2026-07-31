/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class DirectionProperty
extends EnumProperty<Direction> {
    protected DirectionProperty(String p_61541_, Collection<Direction> p_61542_) {
        super(p_61541_, Direction.class, p_61542_);
    }

    public static DirectionProperty m_156003_(String p_156004_) {
        return DirectionProperty.m_61546_(p_156004_, p_187558_ -> true);
    }

    public static DirectionProperty m_61546_(String p_61547_, Predicate<Direction> p_61548_) {
        return DirectionProperty.m_61543_(p_61547_, Arrays.stream(Direction.values()).filter(p_61548_).collect(Collectors.toList()));
    }

    public static DirectionProperty m_61549_(String p_61550_, Direction ... p_61551_) {
        return DirectionProperty.m_61543_(p_61550_, Lists.newArrayList((Object[])p_61551_));
    }

    public static DirectionProperty m_61543_(String p_61544_, Collection<Direction> p_61545_) {
        return new DirectionProperty(p_61544_, p_61545_);
    }
}

