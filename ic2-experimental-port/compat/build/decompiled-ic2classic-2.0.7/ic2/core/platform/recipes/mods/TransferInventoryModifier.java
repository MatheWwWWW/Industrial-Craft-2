/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class TransferInventoryModifier
implements IRecipeModifier {
    Item input;
    ItemStack originalInput = ItemStack.f_41583_;
    String inventoryTag;

    public TransferInventoryModifier(Item input) {
        this(input, "items");
    }

    public TransferInventoryModifier(Item input, String inventoryTag) {
        this.input = input;
        this.inventoryTag = inventoryTag;
    }

    public TransferInventoryModifier(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())), obj.get("tag").getAsString());
    }

    public TransferInventoryModifier(FriendlyByteBuf buffer) {
        this.input = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
        this.inventoryTag = buffer.m_130136_(Short.MAX_VALUE);
    }

    @Override
    public void reset() {
        this.originalInput = ItemStack.f_41583_;
    }

    @Override
    public boolean isSlotValid(ItemStack input) {
        return true;
    }

    @Override
    public boolean isOutputItem(ItemStack input) {
        if (input.m_41720_() == this.input) {
            this.originalInput = input;
        }
        return false;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        CompoundTag nbt;
        if (!this.originalInput.m_41619_() && (nbt = StackUtil.getNbtData(this.originalInput)).m_128441_(this.inventoryTag)) {
            input = input.m_41777_();
            input.m_41784_().m_128365_(this.inventoryTag, nbt.m_128423_(this.inventoryTag));
        }
        return input;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)this.input);
        buffer.m_130070_(this.inventoryTag);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.input).toString());
        obj.addProperty("tag", this.inventoryTag);
        return obj;
    }
}

