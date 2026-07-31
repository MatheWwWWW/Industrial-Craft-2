/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.commands.arguments.item;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemInput
implements Predicate<ItemStack> {
    private static final Dynamic2CommandExceptionType f_120972_ = new Dynamic2CommandExceptionType((p_120986_, p_120987_) -> Component.m_237110_("arguments.item.overstacked", p_120986_, p_120987_));
    private final Holder<Item> f_120973_;
    @Nullable
    private final CompoundTag f_120974_;

    public ItemInput(Holder<Item> p_235282_, @Nullable CompoundTag p_235283_) {
        this.f_120973_ = p_235282_;
        this.f_120974_ = p_235283_;
    }

    public Item m_120979_() {
        return this.f_120973_.m_203334_();
    }

    @Override
    public boolean test(ItemStack p_120984_) {
        return p_120984_.m_220165_(this.f_120973_) && NbtUtils.m_129235_(this.f_120974_, p_120984_.m_41783_(), true);
    }

    public ItemStack m_120980_(int p_120981_, boolean p_120982_) throws CommandSyntaxException {
        ItemStack $$2 = new ItemStack(this.f_120973_, p_120981_);
        if (this.f_120974_ != null) {
            $$2.m_41751_(this.f_120974_);
        }
        if (p_120982_ && p_120981_ > $$2.m_41741_()) {
            throw f_120972_.create((Object)this.m_235284_(), (Object)$$2.m_41741_());
        }
        return $$2;
    }

    public String m_120988_() {
        StringBuilder $$0 = new StringBuilder(this.m_235284_());
        if (this.f_120974_ != null) {
            $$0.append(this.f_120974_);
        }
        return $$0.toString();
    }

    private String m_235284_() {
        return this.f_120973_.m_203543_().map(ResourceKey::m_135782_).orElseGet(() -> "unknown[" + this.f_120973_ + "]").toString();
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((ItemStack)object);
    }
}

