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
import ic2.core.profile.NotClassic;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@NotClassic
public class ItemReactorIridiumReflector
extends AbstractReactorComponent {
    public ItemReactorIridiumReflector(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        if (!bl) {
            IReactorComponent iReactorComponent = (IReactorComponent)itemStack2.m_41720_();
            iReactorComponent.acceptUraniumPulse(itemStack2, iReactor, itemStack, n3, n4, n, n2, bl);
        }
        return true;
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        return -1.0f;
    }
}

