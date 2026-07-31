/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

public record BlockDataSource(String f_237309_, @Nullable Coordinates f_237310_) implements DataSource
{
    public BlockDataSource(String p_237312_) {
        this(p_237312_, BlockDataSource.m_237317_(p_237312_));
    }

    @Nullable
    private static Coordinates m_237317_(String p_237318_) {
        try {
            return BlockPosArgument.m_118239_().parse(new StringReader(p_237318_));
        }
        catch (CommandSyntaxException $$1) {
            return null;
        }
    }

    @Override
    public Stream<CompoundTag> m_213601_(CommandSourceStack p_237323_) {
        BlockEntity $$3;
        BlockPos $$2;
        ServerLevel $$1;
        if (this.f_237310_ != null && ($$1 = p_237323_.m_81372_()).m_46749_($$2 = this.f_237310_.m_119568_(p_237323_)) && ($$3 = $$1.m_7702_($$2)) != null) {
            return Stream.of($$3.m_187480_());
        }
        return Stream.empty();
    }

    @Override
    public String toString() {
        return "block=" + this.f_237309_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object p_237321_) {
        if (this == p_237321_) {
            return true;
        }
        if (!(p_237321_ instanceof BlockDataSource)) return false;
        BlockDataSource $$1 = (BlockDataSource)p_237321_;
        if (!this.f_237309_.equals($$1.f_237309_)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.f_237309_.hashCode();
    }
}

