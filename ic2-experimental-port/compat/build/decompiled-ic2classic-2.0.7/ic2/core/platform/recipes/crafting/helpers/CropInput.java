/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.recipes.crafting.helpers;

import com.google.gson.JsonObject;
import ic2.api.crops.BaseSeed;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.core.item.misc.CropSeedItem;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CropInput
implements IInput {
    ICrop crop;
    int size;

    public CropInput(JsonObject obj) {
        this.crop = ICropRegistry.INSTANCE.getCrop(ResourceLocation.m_135820_((String)obj.get("crop").getAsString()));
        this.size = obj.get("size").getAsInt();
    }

    public CropInput(ICrop crop) {
        this(crop, 1);
    }

    public CropInput(ICrop crop, int size) {
        this.crop = crop;
        this.size = size;
    }

    public CropInput(BaseSeed seed) {
        this.crop = seed.crop;
        this.size = seed.stack_size;
    }

    public CropInput(FriendlyByteBuf buffer) {
        this.crop = ICropRegistry.INSTANCE.getCrop(buffer.m_130281_());
        this.size = buffer.readByte();
    }

    @Override
    public List<ItemStack> getComponents() {
        return ObjectLists.singleton((Object)CropSeedItem.createStack(this.crop, 1, 1, 1, 4));
    }

    @Override
    public int getInputSize() {
        return this.size;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return this.crop == ICropRegistry.INSTANCE.getCrop(stack);
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130085_(this.crop.id());
        buffer.writeByte(this.size);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("crop", this.crop.id().toString());
        obj.addProperty("size", (Number)this.size);
        return obj;
    }
}

