/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.world.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ShapedRecipe
implements CraftingRecipe {
    final int f_44146_;
    final int f_44147_;
    final NonNullList<Ingredient> f_44148_;
    final ItemStack f_44149_;
    private final ResourceLocation f_44150_;
    final String f_44151_;

    public ShapedRecipe(ResourceLocation p_44153_, String p_44154_, int p_44155_, int p_44156_, NonNullList<Ingredient> p_44157_, ItemStack p_44158_) {
        this.f_44150_ = p_44153_;
        this.f_44151_ = p_44154_;
        this.f_44146_ = p_44155_;
        this.f_44147_ = p_44156_;
        this.f_44148_ = p_44157_;
        this.f_44149_ = p_44158_;
    }

    @Override
    public ResourceLocation m_6423_() {
        return this.f_44150_;
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44076_;
    }

    @Override
    public String m_6076_() {
        return this.f_44151_;
    }

    @Override
    public ItemStack m_8043_() {
        return this.f_44149_;
    }

    @Override
    public NonNullList<Ingredient> m_7527_() {
        return this.f_44148_;
    }

    @Override
    public boolean m_8004_(int p_44161_, int p_44162_) {
        return p_44161_ >= this.f_44146_ && p_44162_ >= this.f_44147_;
    }

    @Override
    public boolean m_5818_(CraftingContainer p_44176_, Level p_44177_) {
        for (int $$2 = 0; $$2 <= p_44176_.m_39347_() - this.f_44146_; ++$$2) {
            for (int $$3 = 0; $$3 <= p_44176_.m_39346_() - this.f_44147_; ++$$3) {
                if (this.m_44170_(p_44176_, $$2, $$3, true)) {
                    return true;
                }
                if (!this.m_44170_(p_44176_, $$2, $$3, false)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean m_44170_(CraftingContainer p_44171_, int p_44172_, int p_44173_, boolean p_44174_) {
        for (int $$4 = 0; $$4 < p_44171_.m_39347_(); ++$$4) {
            for (int $$5 = 0; $$5 < p_44171_.m_39346_(); ++$$5) {
                int $$6 = $$4 - p_44172_;
                int $$7 = $$5 - p_44173_;
                Ingredient $$8 = Ingredient.f_43901_;
                if ($$6 >= 0 && $$7 >= 0 && $$6 < this.f_44146_ && $$7 < this.f_44147_) {
                    $$8 = p_44174_ ? this.f_44148_.get(this.f_44146_ - $$6 - 1 + $$7 * this.f_44146_) : this.f_44148_.get($$6 + $$7 * this.f_44146_);
                }
                if ($$8.test(p_44171_.m_8020_($$4 + $$5 * p_44171_.m_39347_()))) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack m_5874_(CraftingContainer p_44169_) {
        return this.m_8043_().m_41777_();
    }

    public int m_44220_() {
        return this.f_44146_;
    }

    public int m_44221_() {
        return this.f_44147_;
    }

    static NonNullList<Ingredient> m_44202_(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
        NonNullList<Ingredient> $$4 = NonNullList.m_122780_(p_44205_ * p_44206_, Ingredient.f_43901_);
        HashSet $$5 = Sets.newHashSet(p_44204_.keySet());
        $$5.remove(" ");
        for (int $$6 = 0; $$6 < p_44203_.length; ++$$6) {
            for (int $$7 = 0; $$7 < p_44203_[$$6].length(); ++$$7) {
                String $$8 = p_44203_[$$6].substring($$7, $$7 + 1);
                Ingredient $$9 = p_44204_.get($$8);
                if ($$9 == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + $$8 + "' but it's not defined in the key");
                }
                $$5.remove($$8);
                $$4.set($$7 + p_44205_ * $$6, $$9);
            }
        }
        if (!$$5.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + $$5);
        }
        return $$4;
    }

    @VisibleForTesting
    static String[] m_44186_(String ... p_44187_) {
        int $$1 = Integer.MAX_VALUE;
        int $$2 = 0;
        int $$3 = 0;
        int $$4 = 0;
        for (int $$5 = 0; $$5 < p_44187_.length; ++$$5) {
            String $$6 = p_44187_[$$5];
            $$1 = Math.min($$1, ShapedRecipe.m_44184_($$6));
            int $$7 = ShapedRecipe.m_44200_($$6);
            $$2 = Math.max($$2, $$7);
            if ($$7 < 0) {
                if ($$3 == $$5) {
                    ++$$3;
                }
                ++$$4;
                continue;
            }
            $$4 = 0;
        }
        if (p_44187_.length == $$4) {
            return new String[0];
        }
        String[] $$8 = new String[p_44187_.length - $$4 - $$3];
        for (int $$9 = 0; $$9 < $$8.length; ++$$9) {
            $$8[$$9] = p_44187_[$$9 + $$3].substring($$1, $$2 + 1);
        }
        return $$8;
    }

    @Override
    public boolean m_142505_() {
        NonNullList<Ingredient> $$0 = this.m_7527_();
        return $$0.isEmpty() || $$0.stream().filter(p_151277_ -> !p_151277_.m_43947_()).anyMatch(p_151273_ -> p_151273_.m_43908_().length == 0);
    }

    private static int m_44184_(String p_44185_) {
        int $$1;
        for ($$1 = 0; $$1 < p_44185_.length() && p_44185_.charAt($$1) == ' '; ++$$1) {
        }
        return $$1;
    }

    private static int m_44200_(String p_44201_) {
        int $$1;
        for ($$1 = p_44201_.length() - 1; $$1 >= 0 && p_44201_.charAt($$1) == ' '; --$$1) {
        }
        return $$1;
    }

    static String[] m_44196_(JsonArray p_44197_) {
        String[] $$1 = new String[p_44197_.size()];
        if ($$1.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        }
        if ($$1.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }
        for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
            String $$3 = GsonHelper.m_13805_(p_44197_.get($$2), "pattern[" + $$2 + "]");
            if ($$3.length() > 3) {
                throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }
            if ($$2 > 0 && $$1[0].length() != $$3.length()) {
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }
            $$1[$$2] = $$3;
        }
        return $$1;
    }

    static Map<String, Ingredient> m_44210_(JsonObject p_44211_) {
        HashMap $$1 = Maps.newHashMap();
        for (Map.Entry $$2 : p_44211_.entrySet()) {
            if (((String)$$2.getKey()).length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)$$2.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals($$2.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            $$1.put((String)$$2.getKey(), Ingredient.m_43917_((JsonElement)$$2.getValue()));
        }
        $$1.put(" ", Ingredient.f_43901_);
        return $$1;
    }

    public static ItemStack m_151274_(JsonObject p_151275_) {
        Item $$1 = ShapedRecipe.m_151278_(p_151275_);
        if (p_151275_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }
        int $$2 = GsonHelper.m_13824_(p_151275_, "count", 1);
        if ($$2 < 1) {
            throw new JsonSyntaxException("Invalid output count: " + $$2);
        }
        return new ItemStack($$1, $$2);
    }

    public static Item m_151278_(JsonObject p_151279_) {
        String $$1 = GsonHelper.m_13906_(p_151279_, "item");
        Item $$2 = Registry.f_122827_.m_6612_(new ResourceLocation($$1)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + $$1 + "'"));
        if ($$2 == Items.f_41852_) {
            throw new JsonSyntaxException("Invalid item: " + $$1);
        }
        return $$2;
    }

    public static class Serializer
    implements RecipeSerializer<ShapedRecipe> {
        @Override
        public ShapedRecipe m_6729_(ResourceLocation p_44236_, JsonObject p_44237_) {
            String $$2 = GsonHelper.m_13851_(p_44237_, "group", "");
            Map<String, Ingredient> $$3 = ShapedRecipe.m_44210_(GsonHelper.m_13930_(p_44237_, "key"));
            String[] $$4 = ShapedRecipe.m_44186_(ShapedRecipe.m_44196_(GsonHelper.m_13933_(p_44237_, "pattern")));
            int $$5 = $$4[0].length();
            int $$6 = $$4.length;
            NonNullList<Ingredient> $$7 = ShapedRecipe.m_44202_($$4, $$3, $$5, $$6);
            ItemStack $$8 = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44237_, "result"));
            return new ShapedRecipe(p_44236_, $$2, $$5, $$6, $$7, $$8);
        }

        @Override
        public ShapedRecipe m_8005_(ResourceLocation p_44239_, FriendlyByteBuf p_44240_) {
            int $$2 = p_44240_.m_130242_();
            int $$3 = p_44240_.m_130242_();
            String $$4 = p_44240_.m_130277_();
            NonNullList<Ingredient> $$5 = NonNullList.m_122780_($$2 * $$3, Ingredient.f_43901_);
            for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
                $$5.set($$6, Ingredient.m_43940_(p_44240_));
            }
            ItemStack $$7 = p_44240_.m_130267_();
            return new ShapedRecipe(p_44239_, $$4, $$2, $$3, $$5, $$7);
        }

        @Override
        public void m_6178_(FriendlyByteBuf p_44227_, ShapedRecipe p_44228_) {
            p_44227_.m_130130_(p_44228_.f_44146_);
            p_44227_.m_130130_(p_44228_.f_44147_);
            p_44227_.m_130070_(p_44228_.f_44151_);
            for (Ingredient $$2 : p_44228_.f_44148_) {
                $$2.m_43923_(p_44227_);
            }
            p_44227_.m_130055_(p_44228_.f_44149_);
        }

        @Override
        public /* synthetic */ Recipe m_8005_(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return this.m_8005_(resourceLocation, friendlyByteBuf);
        }

        @Override
        public /* synthetic */ Recipe m_6729_(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return this.m_6729_(resourceLocation, jsonObject);
        }
    }
}

