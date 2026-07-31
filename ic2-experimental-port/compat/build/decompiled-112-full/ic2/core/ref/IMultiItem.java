/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.ref;

import ic2.core.block.state.IIdProvider;
import ic2.core.util.StackUtil;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.item.ItemStack;

public interface IMultiItem<T extends IIdProvider> {
    public ItemStack getItemStack(T var1);

    public ItemStack getItemStack(String var1);

    public String getVariant(ItemStack var1);

    public Set<T> getAllTypes();

    default public Set<ItemStack> getAllStacks() {
        HashSet<ItemStack> ret = new HashSet<ItemStack>();
        for (IIdProvider type : this.getAllTypes()) {
            ret.add(this.getItemStack(type));
        }
        ret.remove(null);
        ret.remove(StackUtil.emptyStack);
        return ret;
    }
}

