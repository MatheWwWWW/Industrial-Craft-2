/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.commands.data;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.data.DataAccessor;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EntityDataAccessor
implements DataAccessor {
    private static final SimpleCommandExceptionType f_139506_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.data.entity.invalid"));
    public static final Function<String, DataCommands.DataProvider> f_139505_ = p_139517_ -> new DataCommands.DataProvider((String)p_139517_){
        final /* synthetic */ String f_139523_;
        {
            this.f_139523_ = string;
        }

        @Override
        public DataAccessor m_7018_(CommandContext<CommandSourceStack> p_139530_) throws CommandSyntaxException {
            return new EntityDataAccessor(EntityArgument.m_91452_(p_139530_, this.f_139523_));
        }

        @Override
        public ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> p_139527_, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> p_139528_) {
            return p_139527_.then(Commands.m_82127_("entity").then(p_139528_.apply((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_(this.f_139523_, EntityArgument.m_91449_()))));
        }
    };
    private final Entity f_139507_;

    public EntityDataAccessor(Entity p_139510_) {
        this.f_139507_ = p_139510_;
    }

    @Override
    public void m_7603_(CompoundTag p_139519_) throws CommandSyntaxException {
        if (this.f_139507_ instanceof Player) {
            throw f_139506_.create();
        }
        UUID $$1 = this.f_139507_.m_20148_();
        this.f_139507_.m_20258_(p_139519_);
        this.f_139507_.m_20084_($$1);
    }

    @Override
    public CompoundTag m_6184_() {
        return NbtPredicate.m_57485_(this.f_139507_);
    }

    @Override
    public Component m_6934_() {
        return Component.m_237110_("commands.data.entity.modified", this.f_139507_.m_5446_());
    }

    @Override
    public Component m_7624_(Tag p_139521_) {
        return Component.m_237110_("commands.data.entity.query", this.f_139507_.m_5446_(), NbtUtils.m_178061_(p_139521_));
    }

    @Override
    public Component m_6066_(NbtPathArgument.NbtPath p_139513_, double p_139514_, int p_139515_) {
        return Component.m_237110_("commands.data.entity.get", p_139513_, this.f_139507_.m_5446_(), String.format(Locale.ROOT, "%.2f", p_139514_), p_139515_);
    }
}

