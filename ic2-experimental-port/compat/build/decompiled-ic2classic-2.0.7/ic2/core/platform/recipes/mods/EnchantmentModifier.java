/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.mods.BaseRecipeModifier;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentModifier
extends BaseRecipeModifier {
    Enchantment enchantment;
    int level;

    public EnchantmentModifier(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("tag").getAsString(), (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.m_135820_((String)obj.get("enchantment").getAsString())), obj.get("level").getAsInt());
        if (obj.has("tooltip")) {
            this.setTooltip((Component)Component.Serializer.m_130701_((String)obj.get("tooltip").getAsString()));
        }
        this.usesInput = obj.has("usesInput") && obj.get("usesInput").getAsBoolean();
    }

    public EnchantmentModifier(Item type, String tag, Enchantment ench, int level) {
        super(type, tag);
        this.enchantment = ench;
        this.level = level;
    }

    public EnchantmentModifier(FriendlyByteBuf buffer) {
        super(buffer);
        this.enchantment = (Enchantment)buffer.readRegistryIdUnsafe(ForgeRegistries.ENCHANTMENTS);
        this.level = buffer.m_130242_();
    }

    @Override
    protected boolean isTagUsable(ItemStack input) {
        Map data = EnchantmentHelper.m_44831_((ItemStack)input);
        int level = data.getOrDefault(this.enchantment, 0);
        if (level <= 0) {
            for (Enchantment ench : data.keySet()) {
                if (ench.m_44695_(this.enchantment)) continue;
                return false;
            }
        }
        return level < this.level;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        Map data = EnchantmentHelper.m_44831_((ItemStack)input);
        data.put(this.enchantment, this.level);
        EnchantmentHelper.m_44865_((Map)data, (ItemStack)input);
        return super.applyChanges(input, forDisplay);
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        super.serialize(buffer);
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ENCHANTMENTS, (Object)this.enchantment);
        buffer.m_130130_(this.level);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("enchantment", ForgeRegistries.ENCHANTMENTS.getKey((Object)this.enchantment).toString());
        obj.addProperty("level", (Number)this.level);
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.type).toString());
        obj.addProperty("usesInput", Boolean.valueOf(this.usesInput));
        if (this.tooltip != null) {
            obj.addProperty("tooltip", Component.Serializer.m_130703_((Component)this.tooltip));
        }
        return obj;
    }
}

