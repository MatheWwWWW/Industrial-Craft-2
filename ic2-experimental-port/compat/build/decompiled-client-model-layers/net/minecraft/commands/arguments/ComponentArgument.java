/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 */
package net.minecraft.commands.arguments;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ComponentArgument
implements ArgumentType<Component> {
    private static final Collection<String> f_87111_ = Arrays.asList("\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]");
    public static final DynamicCommandExceptionType f_87110_ = new DynamicCommandExceptionType(p_87121_ -> Component.m_237110_("argument.component.invalid", p_87121_));

    private ComponentArgument() {
    }

    public static Component m_87117_(CommandContext<CommandSourceStack> p_87118_, String p_87119_) {
        return (Component)p_87118_.getArgument(p_87119_, Component.class);
    }

    public static ComponentArgument m_87114_() {
        return new ComponentArgument();
    }

    public Component parse(StringReader p_87116_) throws CommandSyntaxException {
        try {
            MutableComponent $$1 = Component.Serializer.m_130699_(p_87116_);
            if ($$1 == null) {
                throw f_87110_.createWithContext((ImmutableStringReader)p_87116_, (Object)"empty");
            }
            return $$1;
        }
        catch (Exception $$2) {
            String $$3 = $$2.getCause() != null ? $$2.getCause().getMessage() : $$2.getMessage();
            throw f_87110_.createWithContext((ImmutableStringReader)p_87116_, (Object)$$3);
        }
    }

    public Collection<String> getExamples() {
        return f_87111_;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

