/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentPredicate {
    public static final EnchantmentPredicate f_30464_ = new EnchantmentPredicate();
    public static final EnchantmentPredicate[] f_30465_ = new EnchantmentPredicate[0];
    @Nullable
    private final Enchantment f_30466_;
    private final MinMaxBounds.Ints f_30467_;

    public EnchantmentPredicate() {
        this.f_30466_ = null;
        this.f_30467_ = MinMaxBounds.Ints.f_55364_;
    }

    public EnchantmentPredicate(@Nullable Enchantment p_30471_, MinMaxBounds.Ints p_30472_) {
        this.f_30466_ = p_30471_;
        this.f_30467_ = p_30472_;
    }

    public boolean m_30476_(Map<Enchantment, Integer> p_30477_) {
        if (this.f_30466_ != null) {
            if (!p_30477_.containsKey(this.f_30466_)) {
                return false;
            }
            int $$1 = p_30477_.get(this.f_30466_);
            if (this.f_30467_ != MinMaxBounds.Ints.f_55364_ && !this.f_30467_.m_55390_($$1)) {
                return false;
            }
        } else if (this.f_30467_ != MinMaxBounds.Ints.f_55364_) {
            for (Integer $$2 : p_30477_.values()) {
                if (!this.f_30467_.m_55390_($$2)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public JsonElement m_30473_() {
        if (this == f_30464_) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$0 = new JsonObject();
        if (this.f_30466_ != null) {
            $$0.addProperty("enchantment", Registry.f_122825_.m_7981_(this.f_30466_).toString());
        }
        $$0.add("levels", this.f_30467_.m_55328_());
        return $$0;
    }

    public static EnchantmentPredicate m_30474_(@Nullable JsonElement p_30475_) {
        if (p_30475_ == null || p_30475_.isJsonNull()) {
            return f_30464_;
        }
        JsonObject $$1 = GsonHelper.m_13918_(p_30475_, "enchantment");
        Enchantment $$2 = null;
        if ($$1.has("enchantment")) {
            ResourceLocation $$3 = new ResourceLocation(GsonHelper.m_13906_($$1, "enchantment"));
            $$2 = Registry.f_122825_.m_6612_($$3).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + $$3 + "'"));
        }
        MinMaxBounds.Ints $$4 = MinMaxBounds.Ints.m_55373_($$1.get("levels"));
        return new EnchantmentPredicate($$2, $$4);
    }

    public static EnchantmentPredicate[] m_30480_(@Nullable JsonElement p_30481_) {
        if (p_30481_ == null || p_30481_.isJsonNull()) {
            return f_30465_;
        }
        JsonArray $$1 = GsonHelper.m_13924_(p_30481_, "enchantments");
        EnchantmentPredicate[] $$2 = new EnchantmentPredicate[$$1.size()];
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            $$2[$$3] = EnchantmentPredicate.m_30474_($$1.get($$3));
        }
        return $$2;
    }
}

