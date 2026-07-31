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
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.data.DataAccessor;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDataAccessor
implements DataAccessor {
    static final SimpleCommandExceptionType f_139292_ = new SimpleCommandExceptionType((Message)Component.m_237115_("commands.data.block.invalid"));
    public static final Function<String, DataCommands.DataProvider> f_139291_ = p_139305_ -> new DataCommands.DataProvider((String)p_139305_){
        final /* synthetic */ String f_139312_;
        {
            this.f_139312_ = string;
        }

        @Override
        public DataAccessor m_7018_(CommandContext<CommandSourceStack> p_139319_) throws CommandSyntaxException {
            BlockPos $$1 = BlockPosArgument.m_118242_(p_139319_, this.f_139312_ + "Pos");
            BlockEntity $$2 = ((CommandSourceStack)p_139319_.getSource()).m_81372_().m_7702_($$1);
            if ($$2 == null) {
                throw f_139292_.create();
            }
            return new BlockDataAccessor($$2, $$1);
        }

        @Override
        public ArgumentBuilder<CommandSourceStack, ?> m_7621_(ArgumentBuilder<CommandSourceStack, ?> p_139316_, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> p_139317_) {
            return p_139316_.then(Commands.m_82127_("block").then(p_139317_.apply((ArgumentBuilder<CommandSourceStack, ?>)Commands.m_82129_(this.f_139312_ + "Pos", BlockPosArgument.m_118239_()))));
        }
    };
    private final BlockEntity f_139293_;
    private final BlockPos f_139294_;

    public BlockDataAccessor(BlockEntity p_139297_, BlockPos p_139298_) {
        this.f_139293_ = p_139297_;
        this.f_139294_ = p_139298_;
    }

    @Override
    public void m_7603_(CompoundTag p_139307_) {
        BlockState $$1 = this.f_139293_.m_58904_().m_8055_(this.f_139294_);
        this.f_139293_.m_142466_(p_139307_);
        this.f_139293_.m_6596_();
        this.f_139293_.m_58904_().m_7260_(this.f_139294_, $$1, $$1, 3);
    }

    @Override
    public CompoundTag m_6184_() {
        return this.f_139293_.m_187480_();
    }

    @Override
    public Component m_6934_() {
        return Component.m_237110_("commands.data.block.modified", this.f_139294_.m_123341_(), this.f_139294_.m_123342_(), this.f_139294_.m_123343_());
    }

    @Override
    public Component m_7624_(Tag p_139309_) {
        return Component.m_237110_("commands.data.block.query", this.f_139294_.m_123341_(), this.f_139294_.m_123342_(), this.f_139294_.m_123343_(), NbtUtils.m_178061_(p_139309_));
    }

    @Override
    public Component m_6066_(NbtPathArgument.NbtPath p_139301_, double p_139302_, int p_139303_) {
        return Component.m_237110_("commands.data.block.get", p_139301_, this.f_139294_.m_123341_(), this.f_139294_.m_123342_(), this.f_139294_.m_123343_(), String.format(Locale.ROOT, "%.2f", p_139302_), p_139303_);
    }
}

