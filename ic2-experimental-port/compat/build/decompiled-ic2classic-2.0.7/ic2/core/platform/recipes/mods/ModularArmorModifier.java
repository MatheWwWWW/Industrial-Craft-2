/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ModularArmorModifier
implements IRecipeModifier {
    ItemStack original;

    public ModularArmorModifier() {
    }

    public ModularArmorModifier(JsonObject obj) {
    }

    public ModularArmorModifier(FriendlyByteBuf buffer) {
    }

    @Override
    public void reset() {
        this.original = null;
    }

    @Override
    public boolean isSlotValid(ItemStack input) {
        if (input.m_41720_() instanceof IC2ModularElectricArmor) {
            if (this.original != null) {
                return false;
            }
            this.original = input.m_41777_();
        }
        return true;
    }

    @Override
    public boolean isOutputItem(ItemStack input) {
        return false;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        if (this.original != null) {
            CompoundTag data = this.original.m_41739_(new CompoundTag());
            data.m_128359_("id", ForgeRegistries.ITEMS.getKey((Object)input.m_41720_()).toString());
            return ItemStack.m_41712_((CompoundTag)data);
        }
        return input;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
    }

    @Override
    public JsonObject serialize() {
        return new JsonObject();
    }
}

