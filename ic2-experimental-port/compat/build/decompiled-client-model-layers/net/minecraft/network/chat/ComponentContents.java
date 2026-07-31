/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;

public interface ComponentContents {
    public static final ComponentContents f_237124_ = new ComponentContents(){

        public String toString() {
            return "empty";
        }
    };

    default public <T> Optional<T> m_213724_(FormattedText.StyledContentConsumer<T> p_237130_, Style p_237131_) {
        return Optional.empty();
    }

    default public <T> Optional<T> m_213874_(FormattedText.ContentConsumer<T> p_237129_) {
        return Optional.empty();
    }

    default public MutableComponent m_213698_(@Nullable CommandSourceStack p_237126_, @Nullable Entity p_237127_, int p_237128_) throws CommandSyntaxException {
        return MutableComponent.m_237204_(this);
    }
}

