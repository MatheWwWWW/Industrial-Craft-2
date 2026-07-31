/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class EmptyInventoryModifier
implements IRecipeModifier {
    Item item;
    String tag;

    public EmptyInventoryModifier(Item item) {
        this(item, "items");
    }

    public EmptyInventoryModifier(Item item, String tag) {
        this.item = item;
        this.tag = tag;
    }

    public EmptyInventoryModifier(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("tag").getAsString());
    }

    public EmptyInventoryModifier(FriendlyByteBuf buffer) {
        this.item = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
        this.tag = buffer.m_130136_(Short.MAX_VALUE);
    }

    @Override
    public void reset() {
    }

    @Override
    public boolean isSlotValid(ItemStack input) {
        if (input.m_41720_() == this.item) {
            return NBTUtils.getItems(input, this.tag).isEmpty();
        }
        return true;
    }

    @Override
    public boolean isOutputItem(ItemStack input) {
        return false;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        return input;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)this.item);
        buffer.m_130070_(this.tag);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.item).toString());
        obj.addProperty("tag", this.tag);
        return obj;
    }
}

