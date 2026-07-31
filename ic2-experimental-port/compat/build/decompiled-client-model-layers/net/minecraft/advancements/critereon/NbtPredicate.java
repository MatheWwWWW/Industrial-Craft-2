/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NbtPredicate {
    public static final NbtPredicate f_57471_ = new NbtPredicate(null);
    @Nullable
    private final CompoundTag f_57472_;

    public NbtPredicate(@Nullable CompoundTag p_57475_) {
        this.f_57472_ = p_57475_;
    }

    public boolean m_57479_(ItemStack p_57480_) {
        if (this == f_57471_) {
            return true;
        }
        return this.m_57483_(p_57480_.m_41783_());
    }

    public boolean m_57477_(Entity p_57478_) {
        if (this == f_57471_) {
            return true;
        }
        return this.m_57483_(NbtPredicate.m_57485_(p_57478_));
    }

    public boolean m_57483_(@Nullable Tag p_57484_) {
        if (p_57484_ == null) {
            return this == f_57471_;
        }
        return this.f_57472_ == null || NbtUtils.m_129235_(this.f_57472_, p_57484_, true);
    }

    public JsonElement m_57476_() {
        if (this == f_57471_ || this.f_57472_ == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(this.f_57472_.toString());
    }

    /*
     * WARNING - void declaration
     */
    public static NbtPredicate m_57481_(@Nullable JsonElement p_57482_) {
        void $$3;
        if (p_57482_ == null || p_57482_.isJsonNull()) {
            return f_57471_;
        }
        try {
            CompoundTag $$1 = TagParser.m_129359_(GsonHelper.m_13805_(p_57482_, "nbt"));
        }
        catch (CommandSyntaxException $$2) {
            throw new JsonSyntaxException("Invalid nbt tag: " + $$2.getMessage());
        }
        return new NbtPredicate((CompoundTag)$$3);
    }

    public static CompoundTag m_57485_(Entity p_57486_) {
        ItemStack $$2;
        CompoundTag $$1 = p_57486_.m_20240_(new CompoundTag());
        if (p_57486_ instanceof Player && !($$2 = ((Player)p_57486_).m_150109_().m_36056_()).m_41619_()) {
            $$1.m_128365_("SelectedItem", $$2.m_41739_(new CompoundTag()));
        }
        return $$1;
    }
}

