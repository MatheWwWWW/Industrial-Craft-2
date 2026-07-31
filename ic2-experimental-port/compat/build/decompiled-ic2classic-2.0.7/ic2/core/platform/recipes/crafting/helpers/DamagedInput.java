/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.nbt.ByteTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.crafting.helpers;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IInput;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class DamagedInput
implements IInput {
    Item item;
    boolean undamaged = false;

    public DamagedInput(JsonObject obj) {
        this((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.m_135820_((String)obj.get("item").getAsString())));
        this.undamaged = obj.get("undamaged").getAsBoolean();
    }

    public DamagedInput(FriendlyByteBuf buffer) {
        this.item = Item.m_41445_((int)buffer.m_130242_());
        this.undamaged = buffer.readBoolean();
    }

    public DamagedInput(Item item) {
        this.item = item;
    }

    public DamagedInput setUndamaged() {
        this.undamaged = true;
        return this;
    }

    @Override
    public List<ItemStack> getComponents() {
        ItemStack stack = new ItemStack((ItemLike)this.item);
        stack.m_41700_("damage", (Tag)ByteTag.m_128273_((boolean)true));
        return ObjectLists.singleton((Object)stack);
    }

    @Override
    public int getInputSize() {
        return 1;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.m_41720_() == this.item && stack.m_41768_() != this.undamaged;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130130_(Item.m_41393_((Item)this.item));
        buffer.writeBoolean(this.undamaged);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey((Object)this.item).toString());
        obj.addProperty("undamaged", Boolean.valueOf(this.undamaged));
        return obj;
    }
}

