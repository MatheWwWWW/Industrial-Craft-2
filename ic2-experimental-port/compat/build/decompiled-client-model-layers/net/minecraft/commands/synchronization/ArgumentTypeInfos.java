/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.LongArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 */
package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import java.util.Locale;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
import net.minecraft.commands.arguments.OperationArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.commands.arguments.TemplateMirrorArgument;
import net.minecraft.commands.arguments.TemplateRotationArgument;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.DoubleArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.FloatArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.IntegerArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.LongArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.StringArgumentSerializer;
import net.minecraft.core.Registry;
import net.minecraft.gametest.framework.TestClassNameArgument;
import net.minecraft.gametest.framework.TestFunctionArgument;

public class ArgumentTypeInfos {
    private static final Map<Class<?>, ArgumentTypeInfo<?, ?>> f_235379_ = Maps.newHashMap();

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> ArgumentTypeInfo<A, T> m_235386_(Registry<ArgumentTypeInfo<?, ?>> p_235387_, String p_235388_, Class<? extends A> p_235389_, ArgumentTypeInfo<A, T> p_235390_) {
        f_235379_.put(p_235389_, p_235390_);
        return Registry.m_122961_(p_235387_, p_235388_, p_235390_);
    }

    public static ArgumentTypeInfo<?, ?> m_235384_(Registry<ArgumentTypeInfo<?, ?>> p_235385_) {
        ArgumentTypeInfos.m_235386_(p_235385_, "brigadier:bool", BoolArgumentType.class, SingletonArgumentInfo.m_235451_(BoolArgumentType::bool));
        ArgumentTypeInfos.m_235386_(p_235385_, "brigadier:float", FloatArgumentType.class, new FloatArgumentInfo());
        ArgumentTypeInfos.m_235386_(p_235385_, "brigadier:double", DoubleArgumentType.class, new DoubleArgumentInfo());
        ArgumentTypeInfos.m_235386_(p_235385_, "brigadier:integer", IntegerArgumentType.class, new IntegerArgumentInfo());
        ArgumentTypeInfos.m_235386_(p_235385_, "brigadier:long", LongArgumentType.class, new LongArgumentInfo());
        ArgumentTypeInfos.m_235386_(p_235385_, "brigadier:string", StringArgumentType.class, new StringArgumentSerializer());
        ArgumentTypeInfos.m_235386_(p_235385_, "entity", EntityArgument.class, new EntityArgument.Info());
        ArgumentTypeInfos.m_235386_(p_235385_, "game_profile", GameProfileArgument.class, SingletonArgumentInfo.m_235451_(GameProfileArgument::m_94584_));
        ArgumentTypeInfos.m_235386_(p_235385_, "block_pos", BlockPosArgument.class, SingletonArgumentInfo.m_235451_(BlockPosArgument::m_118239_));
        ArgumentTypeInfos.m_235386_(p_235385_, "column_pos", ColumnPosArgument.class, SingletonArgumentInfo.m_235451_(ColumnPosArgument::m_118989_));
        ArgumentTypeInfos.m_235386_(p_235385_, "vec3", Vec3Argument.class, SingletonArgumentInfo.m_235451_(Vec3Argument::m_120841_));
        ArgumentTypeInfos.m_235386_(p_235385_, "vec2", Vec2Argument.class, SingletonArgumentInfo.m_235451_(Vec2Argument::m_120822_));
        ArgumentTypeInfos.m_235386_(p_235385_, "block_state", BlockStateArgument.class, SingletonArgumentInfo.m_235449_(BlockStateArgument::m_234650_));
        ArgumentTypeInfos.m_235386_(p_235385_, "block_predicate", BlockPredicateArgument.class, SingletonArgumentInfo.m_235449_(BlockPredicateArgument::m_234627_));
        ArgumentTypeInfos.m_235386_(p_235385_, "item_stack", ItemArgument.class, SingletonArgumentInfo.m_235449_(ItemArgument::m_235279_));
        ArgumentTypeInfos.m_235386_(p_235385_, "item_predicate", ItemPredicateArgument.class, SingletonArgumentInfo.m_235449_(ItemPredicateArgument::m_235353_));
        ArgumentTypeInfos.m_235386_(p_235385_, "color", ColorArgument.class, SingletonArgumentInfo.m_235451_(ColorArgument::m_85463_));
        ArgumentTypeInfos.m_235386_(p_235385_, "component", ComponentArgument.class, SingletonArgumentInfo.m_235451_(ComponentArgument::m_87114_));
        ArgumentTypeInfos.m_235386_(p_235385_, "message", MessageArgument.class, SingletonArgumentInfo.m_235451_(MessageArgument::m_96832_));
        ArgumentTypeInfos.m_235386_(p_235385_, "nbt_compound_tag", CompoundTagArgument.class, SingletonArgumentInfo.m_235451_(CompoundTagArgument::m_87657_));
        ArgumentTypeInfos.m_235386_(p_235385_, "nbt_tag", NbtTagArgument.class, SingletonArgumentInfo.m_235451_(NbtTagArgument::m_100659_));
        ArgumentTypeInfos.m_235386_(p_235385_, "nbt_path", NbtPathArgument.class, SingletonArgumentInfo.m_235451_(NbtPathArgument::m_99487_));
        ArgumentTypeInfos.m_235386_(p_235385_, "objective", ObjectiveArgument.class, SingletonArgumentInfo.m_235451_(ObjectiveArgument::m_101957_));
        ArgumentTypeInfos.m_235386_(p_235385_, "objective_criteria", ObjectiveCriteriaArgument.class, SingletonArgumentInfo.m_235451_(ObjectiveCriteriaArgument::m_102555_));
        ArgumentTypeInfos.m_235386_(p_235385_, "operation", OperationArgument.class, SingletonArgumentInfo.m_235451_(OperationArgument::m_103269_));
        ArgumentTypeInfos.m_235386_(p_235385_, "particle", ParticleArgument.class, SingletonArgumentInfo.m_235451_(ParticleArgument::m_103931_));
        ArgumentTypeInfos.m_235386_(p_235385_, "angle", AngleArgument.class, SingletonArgumentInfo.m_235451_(AngleArgument::m_83807_));
        ArgumentTypeInfos.m_235386_(p_235385_, "rotation", RotationArgument.class, SingletonArgumentInfo.m_235451_(RotationArgument::m_120479_));
        ArgumentTypeInfos.m_235386_(p_235385_, "scoreboard_slot", ScoreboardSlotArgument.class, SingletonArgumentInfo.m_235451_(ScoreboardSlotArgument::m_109196_));
        ArgumentTypeInfos.m_235386_(p_235385_, "score_holder", ScoreHolderArgument.class, new ScoreHolderArgument.Info());
        ArgumentTypeInfos.m_235386_(p_235385_, "swizzle", SwizzleArgument.class, SingletonArgumentInfo.m_235451_(SwizzleArgument::m_120807_));
        ArgumentTypeInfos.m_235386_(p_235385_, "team", TeamArgument.class, SingletonArgumentInfo.m_235451_(TeamArgument::m_112088_));
        ArgumentTypeInfos.m_235386_(p_235385_, "item_slot", SlotArgument.class, SingletonArgumentInfo.m_235451_(SlotArgument::m_111276_));
        ArgumentTypeInfos.m_235386_(p_235385_, "resource_location", ResourceLocationArgument.class, SingletonArgumentInfo.m_235451_(ResourceLocationArgument::m_106984_));
        ArgumentTypeInfos.m_235386_(p_235385_, "mob_effect", MobEffectArgument.class, SingletonArgumentInfo.m_235451_(MobEffectArgument::m_98426_));
        ArgumentTypeInfos.m_235386_(p_235385_, "function", FunctionArgument.class, SingletonArgumentInfo.m_235451_(FunctionArgument::m_120907_));
        ArgumentTypeInfos.m_235386_(p_235385_, "entity_anchor", EntityAnchorArgument.class, SingletonArgumentInfo.m_235451_(EntityAnchorArgument::m_90350_));
        ArgumentTypeInfos.m_235386_(p_235385_, "int_range", RangeArgument.Ints.class, SingletonArgumentInfo.m_235451_(RangeArgument::m_105404_));
        ArgumentTypeInfos.m_235386_(p_235385_, "float_range", RangeArgument.Floats.class, SingletonArgumentInfo.m_235451_(RangeArgument::m_105405_));
        ArgumentTypeInfos.m_235386_(p_235385_, "item_enchantment", ItemEnchantmentArgument.class, SingletonArgumentInfo.m_235451_(ItemEnchantmentArgument::m_95260_));
        ArgumentTypeInfos.m_235386_(p_235385_, "entity_summon", EntitySummonArgument.class, SingletonArgumentInfo.m_235451_(EntitySummonArgument::m_93335_));
        ArgumentTypeInfos.m_235386_(p_235385_, "dimension", DimensionArgument.class, SingletonArgumentInfo.m_235451_(DimensionArgument::m_88805_));
        ArgumentTypeInfos.m_235386_(p_235385_, "time", TimeArgument.class, SingletonArgumentInfo.m_235451_(TimeArgument::m_113037_));
        ArgumentTypeInfos.m_235386_(p_235385_, "resource_or_tag", ArgumentTypeInfos.m_235395_(ResourceOrTagLocationArgument.class), new ResourceOrTagLocationArgument.Info());
        ArgumentTypeInfos.m_235386_(p_235385_, "resource", ArgumentTypeInfos.m_235395_(ResourceKeyArgument.class), new ResourceKeyArgument.Info());
        ArgumentTypeInfos.m_235386_(p_235385_, "template_mirror", TemplateMirrorArgument.class, SingletonArgumentInfo.m_235451_(TemplateMirrorArgument::m_234343_));
        ArgumentTypeInfos.m_235386_(p_235385_, "template_rotation", TemplateRotationArgument.class, SingletonArgumentInfo.m_235451_(TemplateRotationArgument::m_234414_));
        if (SharedConstants.f_136183_) {
            ArgumentTypeInfos.m_235386_(p_235385_, "test_argument", TestFunctionArgument.class, SingletonArgumentInfo.m_235451_(TestFunctionArgument::m_128088_));
            ArgumentTypeInfos.m_235386_(p_235385_, "test_class", TestClassNameArgument.class, SingletonArgumentInfo.m_235451_(TestClassNameArgument::m_127917_));
        }
        return ArgumentTypeInfos.m_235386_(p_235385_, "uuid", UuidArgument.class, SingletonArgumentInfo.m_235451_(UuidArgument::m_113850_));
    }

    private static <T extends ArgumentType<?>> Class<T> m_235395_(Class<? super T> p_235396_) {
        return p_235396_;
    }

    public static boolean m_235391_(Class<?> p_235392_) {
        return f_235379_.containsKey(p_235392_);
    }

    public static <A extends ArgumentType<?>> ArgumentTypeInfo<A, ?> m_235382_(A p_235383_) {
        ArgumentTypeInfo<?, ?> $$1 = f_235379_.get(p_235383_.getClass());
        if ($$1 == null) {
            throw new IllegalArgumentException(String.format(Locale.ROOT, "Unrecognized argument type %s (%s)", p_235383_, p_235383_.getClass()));
        }
        return $$1;
    }

    public static <A extends ArgumentType<?>> ArgumentTypeInfo.Template<A> m_235393_(A p_235394_) {
        return ArgumentTypeInfos.m_235382_(p_235394_).m_214163_(p_235394_);
    }
}

