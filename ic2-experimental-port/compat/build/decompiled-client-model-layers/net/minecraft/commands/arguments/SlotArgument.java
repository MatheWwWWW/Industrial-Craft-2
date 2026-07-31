/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.commands.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;

public class SlotArgument
implements ArgumentType<Integer> {
    private static final Collection<String> f_111271_ = Arrays.asList("container.5", "12", "weapon");
    private static final DynamicCommandExceptionType f_111272_ = new DynamicCommandExceptionType(p_111283_ -> Component.m_237110_("slot.unknown", p_111283_));
    private static final Map<String, Integer> f_111273_ = Util.m_137469_(Maps.newHashMap(), p_111285_ -> {
        for (int $$1 = 0; $$1 < 54; ++$$1) {
            p_111285_.put("container." + $$1, $$1);
        }
        for (int $$2 = 0; $$2 < 9; ++$$2) {
            p_111285_.put("hotbar." + $$2, $$2);
        }
        for (int $$3 = 0; $$3 < 27; ++$$3) {
            p_111285_.put("inventory." + $$3, 9 + $$3);
        }
        for (int $$4 = 0; $$4 < 27; ++$$4) {
            p_111285_.put("enderchest." + $$4, 200 + $$4);
        }
        for (int $$5 = 0; $$5 < 8; ++$$5) {
            p_111285_.put("villager." + $$5, 300 + $$5);
        }
        for (int $$6 = 0; $$6 < 15; ++$$6) {
            p_111285_.put("horse." + $$6, 500 + $$6);
        }
        p_111285_.put("weapon", EquipmentSlot.MAINHAND.m_147068_(98));
        p_111285_.put("weapon.mainhand", EquipmentSlot.MAINHAND.m_147068_(98));
        p_111285_.put("weapon.offhand", EquipmentSlot.OFFHAND.m_147068_(98));
        p_111285_.put("armor.head", EquipmentSlot.HEAD.m_147068_(100));
        p_111285_.put("armor.chest", EquipmentSlot.CHEST.m_147068_(100));
        p_111285_.put("armor.legs", EquipmentSlot.LEGS.m_147068_(100));
        p_111285_.put("armor.feet", EquipmentSlot.FEET.m_147068_(100));
        p_111285_.put("horse.saddle", 400);
        p_111285_.put("horse.armor", 401);
        p_111285_.put("horse.chest", 499);
    });

    public static SlotArgument m_111276_() {
        return new SlotArgument();
    }

    public static int m_111279_(CommandContext<CommandSourceStack> p_111280_, String p_111281_) {
        return (Integer)p_111280_.getArgument(p_111281_, Integer.class);
    }

    public Integer parse(StringReader p_111278_) throws CommandSyntaxException {
        String $$1 = p_111278_.readUnquotedString();
        if (!f_111273_.containsKey($$1)) {
            throw f_111272_.create((Object)$$1);
        }
        return f_111273_.get($$1);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_111288_, SuggestionsBuilder p_111289_) {
        return SharedSuggestionProvider.m_82970_(f_111273_.keySet(), p_111289_);
    }

    public Collection<String> getExamples() {
        return f_111271_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

