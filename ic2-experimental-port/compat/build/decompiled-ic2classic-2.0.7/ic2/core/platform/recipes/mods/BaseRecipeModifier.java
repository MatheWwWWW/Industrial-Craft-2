/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.mods;

import ic2.core.platform.recipes.mods.IRecipeModifier;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class BaseRecipeModifier
implements IRecipeModifier {
    protected Item type;
    protected String tag;
    protected boolean usesInput = false;
    protected Component tooltip;

    public BaseRecipeModifier(FriendlyByteBuf buffer) {
        this.type = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
        this.tag = buffer.m_130136_(Short.MAX_VALUE);
        this.usesInput = buffer.readBoolean();
        if (buffer.readBoolean()) {
            this.tooltip = buffer.m_130238_();
        }
    }

    public BaseRecipeModifier(Item type, String tag) {
        this.type = type;
        this.tag = tag;
    }

    public BaseRecipeModifier setUsesInput() {
        this.usesInput = true;
        return this;
    }

    public BaseRecipeModifier setTooltip(Component tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    protected abstract boolean isTagUsable(ItemStack var1);

    @Override
    public void reset() {
    }

    @Override
    public boolean isSlotValid(ItemStack input) {
        return input.m_41720_() != this.type || this.isTagUsable(input);
    }

    @Override
    public boolean isOutputItem(ItemStack input) {
        return this.usesInput && input.m_41720_() == this.type;
    }

    @Override
    public ItemStack applyChanges(ItemStack input, boolean forDisplay) {
        if (forDisplay && this.tooltip != null) {
            input = input.m_41777_();
            StackUtil.addTooltip(input, this.tooltip);
        }
        return input;
    }

    @Override
    public void serialize(FriendlyByteBuf buffer) {
        buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)this.type);
        buffer.m_130070_(this.tag);
        buffer.writeBoolean(this.usesInput);
        buffer.writeBoolean(this.tooltip != null);
        if (this.tooltip != null) {
            buffer.m_130083_(this.tooltip);
        }
    }
}

