/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.Ic2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.item.reactor.AbstractDamageableReactorComponent;
import ic2.core.ref.Ic2Items;
import java.util.ArrayDeque;
import java.util.Collection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ItemReactorUranium
extends AbstractDamageableReactorComponent {
    public final int numberOfCells;

    public ItemReactorUranium(Item.Properties properties, int n) {
        this(properties, n, 20000);
    }

    protected ItemReactorUranium(Item.Properties properties, int n, int n2) {
        super(properties, n2);
        this.numberOfCells = n;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int n, int n2, boolean bl) {
        if (!iReactor.produceEnergy()) {
            return;
        }
        int n3 = 1 + this.numberOfCells / 2;
        for (int i = 0; i < this.numberOfCells; ++i) {
            int n4;
            int n5;
            int n6 = n3;
            if (!bl) {
                for (n5 = 0; n5 < n6; ++n5) {
                    this.acceptUraniumPulse(itemStack, iReactor, itemStack, n, n2, n, n2, bl);
                }
                n6 += ItemReactorUranium.checkPulseable(iReactor, n - 1, n2, itemStack, n, n2, bl) + ItemReactorUranium.checkPulseable(iReactor, n + 1, n2, itemStack, n, n2, bl) + ItemReactorUranium.checkPulseable(iReactor, n, n2 - 1, itemStack, n, n2, bl) + ItemReactorUranium.checkPulseable(iReactor, n, n2 + 1, itemStack, n, n2, bl);
                continue;
            }
            n5 = ItemReactorUranium.triangularNumber(n6 += ItemReactorUranium.checkPulseable(iReactor, n - 1, n2, itemStack, n, n2, bl) + ItemReactorUranium.checkPulseable(iReactor, n + 1, n2, itemStack, n, n2, bl) + ItemReactorUranium.checkPulseable(iReactor, n, n2 - 1, itemStack, n, n2, bl) + ItemReactorUranium.checkPulseable(iReactor, n, n2 + 1, itemStack, n, n2, bl)) * 4;
            ArrayDeque<ItemStackCoord> arrayDeque = new ArrayDeque<ItemStackCoord>();
            this.checkHeatAcceptor(iReactor, n - 1, n2, arrayDeque);
            this.checkHeatAcceptor(iReactor, n + 1, n2, arrayDeque);
            this.checkHeatAcceptor(iReactor, n, n2 - 1, arrayDeque);
            this.checkHeatAcceptor(iReactor, n, n2 + 1, arrayDeque);
            for (n5 = this.getFinalHeat(itemStack, iReactor, n, n2, n5); !arrayDeque.isEmpty() && n5 > 0; n5 += n4) {
                n4 = n5 / arrayDeque.size();
                n5 -= n4;
                ItemStackCoord itemStackCoord = (ItemStackCoord)arrayDeque.remove();
                IReactorComponent iReactorComponent = (IReactorComponent)itemStackCoord.stack.m_41720_();
                n4 = iReactorComponent.alterHeat(itemStackCoord.stack, iReactor, itemStackCoord.x, itemStackCoord.y, n4);
            }
            if (n5 <= 0) continue;
            iReactor.addHeat(n5);
        }
        if (!bl && this.getUse(itemStack) >= this.getMaxUse() - 1) {
            iReactor.setItemAt(n, n2, this.getDepletedStack(itemStack, iReactor));
        } else if (!bl) {
            this.incrementUse(itemStack);
        }
    }

    protected int getFinalHeat(ItemStack itemStack, IReactor iReactor, int n, int n2, int n3) {
        return n3;
    }

    protected ItemStack getDepletedStack(ItemStack itemStack, IReactor iReactor) {
        return new ItemStack((ItemLike)(switch (this.numberOfCells) {
            case 1 -> Ic2Items.DEPLETED_URANIUM_FUEL_ROD;
            case 2 -> Ic2Items.DEPLETED_DUAL_URANIUM_FUEL_ROD;
            case 4 -> Ic2Items.DEPLETED_QUAD_URANIUM_FUEL_ROD;
            default -> throw new RuntimeException("invalid cell count: " + this.numberOfCells);
        }));
    }

    protected static int checkPulseable(IReactor iReactor, int n, int n2, ItemStack itemStack, int n3, int n4, boolean bl) {
        ItemStack itemStack2 = iReactor.getItemAt(n, n2);
        if (itemStack2 != null && itemStack2.m_41720_() instanceof IReactorComponent && ((IReactorComponent)itemStack2.m_41720_()).acceptUraniumPulse(itemStack2, iReactor, itemStack, n, n2, n3, n4, bl)) {
            return 1;
        }
        return 0;
    }

    protected static int triangularNumber(int n) {
        return (n * n + n) / 2;
    }

    protected void checkHeatAcceptor(IReactor iReactor, int n, int n2, Collection<ItemStackCoord> collection) {
        ItemStack itemStack = iReactor.getItemAt(n, n2);
        if (itemStack != null && itemStack.m_41720_() instanceof IReactorComponent && ((IReactorComponent)itemStack.m_41720_()).canStoreHeat(itemStack, iReactor, n, n2)) {
            collection.add(new ItemStackCoord(itemStack, n, n2));
        }
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        if (!bl) {
            iReactor.addOutput(1.0f);
        }
        return true;
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        return 2 * this.numberOfCells;
    }

    public void m_6883_(ItemStack itemStack, Level level, Entity entity, int n, boolean bl) {
        LivingEntity livingEntity;
        if (entity instanceof LivingEntity && !ItemArmorHazmat.hasCompleteHazmat(livingEntity = (LivingEntity)entity)) {
            Ic2Potion.radiation.applyTo(livingEntity, 200, 100);
        }
    }

    private static class ItemStackCoord {
        public final ItemStack stack;
        public final int x;
        public final int y;

        public ItemStackCoord(ItemStack itemStack, int n, int n2) {
            this.stack = itemStack;
            this.x = n;
            this.y = n2;
        }
    }
}

