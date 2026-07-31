/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ResourceLocationArgument
implements ArgumentType<ResourceLocation> {
    private static final Collection<String> f_106977_ = Arrays.asList("foo", "foo:bar", "012");
    private static final DynamicCommandExceptionType f_106978_ = new DynamicCommandExceptionType(p_107010_ -> Component.m_237110_("advancement.advancementNotFound", p_107010_));
    private static final DynamicCommandExceptionType f_106979_ = new DynamicCommandExceptionType(p_107005_ -> Component.m_237110_("recipe.notFound", p_107005_));
    private static final DynamicCommandExceptionType f_106980_ = new DynamicCommandExceptionType(p_106998_ -> Component.m_237110_("predicate.unknown", p_106998_));
    private static final DynamicCommandExceptionType f_171024_ = new DynamicCommandExceptionType(p_106991_ -> Component.m_237110_("item_modifier.unknown", p_106991_));

    public static ResourceLocationArgument m_106984_() {
        return new ResourceLocationArgument();
    }

    public static Advancement m_106987_(CommandContext<CommandSourceStack> p_106988_, String p_106989_) throws CommandSyntaxException {
        ResourceLocation $$2 = ResourceLocationArgument.m_107011_(p_106988_, p_106989_);
        Advancement $$3 = ((CommandSourceStack)p_106988_.getSource()).m_81377_().m_129889_().m_136041_($$2);
        if ($$3 == null) {
            throw f_106978_.create((Object)$$2);
        }
        return $$3;
    }

    public static Recipe<?> m_106994_(CommandContext<CommandSourceStack> p_106995_, String p_106996_) throws CommandSyntaxException {
        RecipeManager $$2 = ((CommandSourceStack)p_106995_.getSource()).m_81377_().m_129894_();
        ResourceLocation $$3 = ResourceLocationArgument.m_107011_(p_106995_, p_106996_);
        return $$2.m_44043_($$3).orElseThrow(() -> f_106979_.create((Object)$$3));
    }

    public static LootItemCondition m_107001_(CommandContext<CommandSourceStack> p_107002_, String p_107003_) throws CommandSyntaxException {
        ResourceLocation $$2 = ResourceLocationArgument.m_107011_(p_107002_, p_107003_);
        PredicateManager $$3 = ((CommandSourceStack)p_107002_.getSource()).m_81377_().m_129899_();
        LootItemCondition $$4 = $$3.m_79252_($$2);
        if ($$4 == null) {
            throw f_106980_.create((Object)$$2);
        }
        return $$4;
    }

    public static LootItemFunction m_171031_(CommandContext<CommandSourceStack> p_171032_, String p_171033_) throws CommandSyntaxException {
        ResourceLocation $$2 = ResourceLocationArgument.m_107011_(p_171032_, p_171033_);
        ItemModifierManager $$3 = ((CommandSourceStack)p_171032_.getSource()).m_81377_().m_177926_();
        LootItemFunction $$4 = $$3.m_165108_($$2);
        if ($$4 == null) {
            throw f_171024_.create((Object)$$2);
        }
        return $$4;
    }

    public static ResourceLocation m_107011_(CommandContext<CommandSourceStack> p_107012_, String p_107013_) {
        return (ResourceLocation)p_107012_.getArgument(p_107013_, ResourceLocation.class);
    }

    public ResourceLocation parse(StringReader p_106986_) throws CommandSyntaxException {
        return ResourceLocation.m_135818_(p_106986_);
    }

    public Collection<String> getExamples() {
        return f_106977_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

