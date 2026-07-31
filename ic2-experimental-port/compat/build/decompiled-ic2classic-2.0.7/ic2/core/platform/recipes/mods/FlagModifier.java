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
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.mods.BaseRecipeModifier;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FlagModifier
extends BaseRecipeModifier {
    boolean flag;

    public FlagModifier(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("tag").getAsString(), obj.get("flag").getAsBoolean());
        if (obj.has("tooltip")) {
            this.setTooltip((Component)Component.Serializer.m_130701_((String)obj.get("tooltip").getAsString()));
        }
        this.usesInput = obj.has("usesInput") && obj.get("usesInput").getAsBoolean();
    }

    public FlagModifier(Item type, String tag, boolean flag) {
        super(type, tag);
        this.flag = flag;
    }

    public FlagModifier(FriendlyByteBuf buffer) {
        super(buffer);
        this.flag = buffer.readBoolean();
    }

    @Override
    protected boolean isTagUsable(ItemStack input) {
        return StackUtil.getNbtData(input).m_128471_(this.tag) != this.flag;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        input.m_41784_().m_128379_(this.tag, this.flag);
        return super.applyChanges(input, forDisplay);
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        super.serialize(buffer);
        buffer.writeBoolean(this.flag);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("flag", Boolean.valueOf(this.flag));
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.type).toString());
        obj.addProperty("usesInput", Boolean.valueOf(this.usesInput));
        obj.addProperty("tag", this.tag);
        if (this.tooltip != null) {
            obj.addProperty("tooltip", Component.Serializer.m_130703_((Component)this.tooltip));
        }
        return obj;
    }
}

