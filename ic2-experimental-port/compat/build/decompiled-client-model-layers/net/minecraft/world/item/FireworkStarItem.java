/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FireworkStarItem
extends Item {
    public FireworkStarItem(Item.Properties p_41248_) {
        super(p_41248_);
    }

    @Override
    public void m_7373_(ItemStack p_41252_, @Nullable Level p_41253_, List<Component> p_41254_, TooltipFlag p_41255_) {
        CompoundTag $$4 = p_41252_.m_41737_("Explosion");
        if ($$4 != null) {
            FireworkStarItem.m_41256_($$4, p_41254_);
        }
    }

    public static void m_41256_(CompoundTag p_41257_, List<Component> p_41258_) {
        int[] $$4;
        FireworkRocketItem.Shape $$2 = FireworkRocketItem.Shape.m_41237_(p_41257_.m_128445_("Type"));
        p_41258_.add(Component.m_237115_("item.minecraft.firework_star.shape." + $$2.m_41241_()).m_130940_(ChatFormatting.GRAY));
        int[] $$3 = p_41257_.m_128465_("Colors");
        if ($$3.length > 0) {
            p_41258_.add(FireworkStarItem.m_41259_(Component.m_237119_().m_130940_(ChatFormatting.GRAY), $$3));
        }
        if (($$4 = p_41257_.m_128465_("FadeColors")).length > 0) {
            p_41258_.add(FireworkStarItem.m_41259_(Component.m_237115_("item.minecraft.firework_star.fade_to").m_130946_(" ").m_130940_(ChatFormatting.GRAY), $$4));
        }
        if (p_41257_.m_128471_("Trail")) {
            p_41258_.add(Component.m_237115_("item.minecraft.firework_star.trail").m_130940_(ChatFormatting.GRAY));
        }
        if (p_41257_.m_128471_("Flicker")) {
            p_41258_.add(Component.m_237115_("item.minecraft.firework_star.flicker").m_130940_(ChatFormatting.GRAY));
        }
    }

    private static Component m_41259_(MutableComponent p_41260_, int[] p_41261_) {
        for (int $$2 = 0; $$2 < p_41261_.length; ++$$2) {
            if ($$2 > 0) {
                p_41260_.m_130946_(", ");
            }
            p_41260_.m_7220_(FireworkStarItem.m_41249_(p_41261_[$$2]));
        }
        return p_41260_;
    }

    private static Component m_41249_(int p_41250_) {
        DyeColor $$1 = DyeColor.m_41061_(p_41250_);
        if ($$1 == null) {
            return Component.m_237115_("item.minecraft.firework_star.custom_color");
        }
        return Component.m_237115_("item.minecraft.firework_star." + $$1.m_41065_());
    }
}

