/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.AbstractReactorComponent;
import ic2.core.profile.NotExperimental;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@NotExperimental
public class ItemReactorHeatpack
extends AbstractReactorComponent {
    protected final int maxPer;
    protected final int heatPer;

    public ItemReactorHeatpack(Item.Properties properties, int n, int n2) {
        super(properties);
        this.maxPer = n;
        this.heatPer = n2;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
        if (bl) {
            int n3 = StackUtil.getSize(itemStack);
            this.heat(iReactor, n3, n + 1, n2);
            this.heat(iReactor, n3, n - 1, n2);
            this.heat(iReactor, n3, n, n2 + 1);
            this.heat(iReactor, n3, n, n2 - 1);
        }
    }

    private void heat(IReactor iReactor, int n, int n2, int n3) {
        IReactorComponent iReactorComponent;
        int n4 = this.maxPer * n;
        if (iReactor.getHeat() >= n4) {
            return;
        }
        ItemStack itemStack = iReactor.getItemAt(n2, n3);
        if (!StackUtil.isEmpty(itemStack) && itemStack.m_41720_() instanceof IReactorComponent && (iReactorComponent = (IReactorComponent)itemStack.m_41720_()).canStoreHeat(itemStack, iReactor, n2, n3)) {
            int n5 = this.heatPer * n;
            int n6 = iReactorComponent.getCurrentHeat(itemStack, iReactor, n2, n3);
            if (n5 > n4 - n6) {
                n5 = n4 - n6;
            }
            if (n5 > 0) {
                iReactorComponent.alterHeat(itemStack, iReactor, n2, n3, n5);
            }
        }
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        return (float)StackUtil.getSize(itemStack) / 10.0f;
    }
}

