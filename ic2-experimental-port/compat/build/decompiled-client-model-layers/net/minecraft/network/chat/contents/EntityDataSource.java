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
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.world.entity.Entity;

public record EntityDataSource(String f_237327_, @Nullable EntitySelector f_237328_) implements DataSource
{
    public EntityDataSource(String p_237330_) {
        this(p_237330_, EntityDataSource.m_237335_(p_237330_));
    }

    @Nullable
    private static EntitySelector m_237335_(String p_237336_) {
        try {
            EntitySelectorParser $$1 = new EntitySelectorParser(new StringReader(p_237336_));
            return $$1.m_121377_();
        }
        catch (CommandSyntaxException $$2) {
            return null;
        }
    }

    @Override
    public Stream<CompoundTag> m_213601_(CommandSourceStack p_237341_) throws CommandSyntaxException {
        if (this.f_237328_ != null) {
            List<? extends Entity> $$1 = this.f_237328_.m_121160_(p_237341_);
            return $$1.stream().map(NbtPredicate::m_57485_);
        }
        return Stream.empty();
    }

    @Override
    public String toString() {
        return "entity=" + this.f_237327_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object p_237339_) {
        if (this == p_237339_) {
            return true;
        }
        if (!(p_237339_ instanceof EntityDataSource)) return false;
        EntityDataSource $$1 = (EntityDataSource)p_237339_;
        if (!this.f_237327_.equals($$1.f_237327_)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return this.f_237327_.hashCode();
    }
}

