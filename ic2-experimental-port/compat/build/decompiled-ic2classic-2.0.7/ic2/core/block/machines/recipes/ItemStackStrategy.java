/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.recipes;

import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.Hash;
import java.util.Objects;
import net.minecraft.world.item.ItemStack;

public class ItemStackStrategy
implements Hash.Strategy<ItemStack> {
    public static final ItemStackStrategy INSTANCE = new ItemStackStrategy();
    public static final ItemStackSimpleStrategy SIMPLE_INSTANCE = new ItemStackSimpleStrategy();
    public static final ItemStackCountStrategy COUNT_INSTANCE = new ItemStackCountStrategy();

    public static Hash.Strategy<ItemStack> getStrategy(boolean nbt) {
        return nbt ? INSTANCE : SIMPLE_INSTANCE;
    }

    public int hashCode(ItemStack stack) {
        return stack == null ? 0 : Objects.hash(stack.m_41720_(), stack.m_41783_());
    }

    public boolean equals(ItemStack key, ItemStack value) {
        key = key == null ? ItemStack.f_41583_ : key;
        value = value == null ? ItemStack.f_41583_ : value;
        return StackUtil.isStackEqual(key, value);
    }

    public static class ItemStackSimpleStrategy
    implements Hash.Strategy<ItemStack> {
        public int hashCode(ItemStack o) {
            return o == null ? 0 : o.m_41720_().hashCode();
        }

        public boolean equals(ItemStack a, ItemStack b) {
            return (a == null ? ItemStack.f_41583_ : a).m_41656_(b == null ? ItemStack.f_41583_ : b);
        }
    }

    public static class ItemStackCountStrategy
    implements Hash.Strategy<ItemStack> {
        public int hashCode(ItemStack stack) {
            return stack == null ? 0 : Objects.hash(stack.m_41720_(), stack.m_41783_(), stack.m_41613_());
        }

        public boolean equals(ItemStack key, ItemStack value) {
            key = key == null ? ItemStack.f_41583_ : key;
            value = value == null ? ItemStack.f_41583_ : value;
            return key.m_41613_() == value.m_41613_() && StackUtil.isStackEqual(key, value);
        }
    }
}

