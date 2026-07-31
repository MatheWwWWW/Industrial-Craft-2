/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.network.chat.Style
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.recipes.mods;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public class InfoModifier
implements IRecipeModifier {
    Component tooltip;

    public InfoModifier(JsonObject obj) {
        this.tooltip = Component.Serializer.m_130701_((String)obj.get("tooltip").getAsString());
    }

    public InfoModifier(String name) {
        this((Component)Component.m_237115_((String)name).m_130948_(Style.f_131099_.m_131155_(Boolean.valueOf(false)).m_131140_(ChatFormatting.GRAY)));
    }

    public InfoModifier(Component tooltip) {
        this.tooltip = tooltip;
    }

    public InfoModifier(FriendlyByteBuf buffer) {
        this.tooltip = buffer.m_130238_();
    }

    @Override
    public void reset() {
    }

    @Override
    public boolean isSlotValid(ItemStack input) {
        return true;
    }

    @Override
    public boolean isOutputItem(ItemStack input) {
        return false;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        if (forDisplay) {
            input = input.m_41777_();
            StackUtil.addTooltip(input, this.tooltip);
        }
        return input;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.m_130083_(this.tooltip);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("tooltip", Component.Serializer.m_130703_((Component)this.tooltip));
        return obj;
    }
}

