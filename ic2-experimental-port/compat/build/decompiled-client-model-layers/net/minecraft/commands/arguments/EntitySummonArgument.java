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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class EntitySummonArgument
implements ArgumentType<ResourceLocation> {
    private static final Collection<String> f_93332_ = Arrays.asList("minecraft:pig", "cow");
    public static final DynamicCommandExceptionType f_93331_ = new DynamicCommandExceptionType(p_93342_ -> Component.m_237110_("entity.notFound", p_93342_));

    public static EntitySummonArgument m_93335_() {
        return new EntitySummonArgument();
    }

    public static ResourceLocation m_93338_(CommandContext<CommandSourceStack> p_93339_, String p_93340_) throws CommandSyntaxException {
        return EntitySummonArgument.m_93343_((ResourceLocation)p_93339_.getArgument(p_93340_, ResourceLocation.class));
    }

    private static ResourceLocation m_93343_(ResourceLocation p_93344_) throws CommandSyntaxException {
        Registry.f_122826_.m_6612_(p_93344_).filter(EntityType::m_20654_).orElseThrow(() -> f_93331_.create((Object)p_93344_));
        return p_93344_;
    }

    public ResourceLocation parse(StringReader p_93337_) throws CommandSyntaxException {
        return EntitySummonArgument.m_93343_(ResourceLocation.m_135818_(p_93337_));
    }

    public Collection<String> getExamples() {
        return f_93332_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

