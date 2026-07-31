/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.functions;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public interface LootItemFunction
extends LootContextUser,
BiFunction<ItemStack, LootContext, ItemStack> {
    public LootItemFunctionType m_7162_();

    public static Consumer<ItemStack> m_80724_(BiFunction<ItemStack, LootContext, ItemStack> p_80725_, Consumer<ItemStack> p_80726_, LootContext p_80727_) {
        return p_80732_ -> p_80726_.accept((ItemStack)p_80725_.apply((ItemStack)p_80732_, p_80727_));
    }

    public static interface Builder {
        public LootItemFunction m_7453_();
    }
}

