/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen;

import java.util.OptionalLong;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;

public class DimensionTypes {
    public static Holder<DimensionType> m_236473_(Registry<DimensionType> p_236474_) {
        BuiltinRegistries.m_206384_(p_236474_, BuiltinDimensionTypes.f_223538_, new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0, true, false, -64, 384, 384, BlockTags.f_13058_, BuiltinDimensionTypes.f_223542_, 0.0f, new DimensionType.MonsterSettings(false, true, UniformInt.m_146622_(0, 7), 0)));
        BuiltinRegistries.m_206384_(p_236474_, BuiltinDimensionTypes.f_223539_, new DimensionType(OptionalLong.of(18000L), false, true, true, false, 8.0, false, true, 0, 256, 128, BlockTags.f_13059_, BuiltinDimensionTypes.f_223543_, 0.1f, new DimensionType.MonsterSettings(true, false, ConstantInt.m_146483_(11), 15)));
        BuiltinRegistries.m_206384_(p_236474_, BuiltinDimensionTypes.f_223540_, new DimensionType(OptionalLong.of(6000L), false, false, false, false, 1.0, false, false, 0, 256, 256, BlockTags.f_13060_, BuiltinDimensionTypes.f_223544_, 0.0f, new DimensionType.MonsterSettings(false, true, UniformInt.m_146622_(0, 7), 0)));
        return BuiltinRegistries.m_206384_(p_236474_, BuiltinDimensionTypes.f_223541_, new DimensionType(OptionalLong.empty(), true, true, false, true, 1.0, true, false, -64, 384, 384, BlockTags.f_13058_, BuiltinDimensionTypes.f_223542_, 0.0f, new DimensionType.MonsterSettings(false, true, UniformInt.m_146622_(0, 7), 0)));
    }
}

