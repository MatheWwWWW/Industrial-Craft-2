/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class EnchantmentNames {
    private static final ResourceLocation f_98727_ = new ResourceLocation("minecraft", "alt");
    private static final Style f_98728_ = Style.f_131099_.m_131150_(f_98727_);
    private static final EnchantmentNames f_98729_ = new EnchantmentNames();
    private final RandomSource f_98730_ = RandomSource.m_216327_();
    private final String[] f_98731_ = new String[]{"the", "elder", "scrolls", "klaatu", "berata", "niktu", "xyzzy", "bless", "curse", "light", "darkness", "fire", "air", "earth", "water", "hot", "dry", "cold", "wet", "ignite", "snuff", "embiggen", "twist", "shorten", "stretch", "fiddle", "destroy", "imbue", "galvanize", "enchant", "free", "limited", "range", "of", "towards", "inside", "sphere", "cube", "self", "other", "ball", "mental", "physical", "grow", "shrink", "demon", "elemental", "spirit", "animal", "creature", "beast", "humanoid", "undead", "fresh", "stale", "phnglui", "mglwnafh", "cthulhu", "rlyeh", "wgahnagl", "fhtagn", "baguette"};

    private EnchantmentNames() {
    }

    public static EnchantmentNames m_98734_() {
        return f_98729_;
    }

    public FormattedText m_98737_(Font p_98738_, int p_98739_) {
        StringBuilder $$2 = new StringBuilder();
        int $$3 = this.f_98730_.m_188503_(2) + 3;
        for (int $$4 = 0; $$4 < $$3; ++$$4) {
            if ($$4 != 0) {
                $$2.append(" ");
            }
            $$2.append(Util.m_214670_(this.f_98731_, this.f_98730_));
        }
        return p_98738_.m_92865_().m_92389_(Component.m_237113_($$2.toString()).m_130948_(f_98728_), p_98739_, Style.f_131099_);
    }

    public void m_98735_(long p_98736_) {
        this.f_98730_.m_188584_(p_98736_);
    }
}

