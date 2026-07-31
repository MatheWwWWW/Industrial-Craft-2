/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

@NotClassic
public class ItemReactorMOX
extends ItemReactorUranium {
    public ItemReactorMOX(Item.Properties properties, int n) {
        super(properties, n, 10000);
    }

    @Override
    protected int getFinalHeat(ItemStack itemStack, IReactor iReactor, int n, int n2, int n3) {
        float f;
        if (iReactor.isFluidCooled() && (double)(f = (float)iReactor.getHeat() / (float)iReactor.getMaxHeat()) > 0.5) {
            n3 *= 2;
        }
        return n3;
    }

    @Override
    protected ItemStack getDepletedStack(ItemStack itemStack, IReactor iReactor) {
        return new ItemStack((ItemLike)(switch (this.numberOfCells) {
            case 1 -> Ic2Items.DEPLETED_MOX_FUEL_ROD;
            case 2 -> Ic2Items.DEPLETED_DUAL_MOX_FUEL_ROD;
            case 4 -> Ic2Items.DEPLETED_QUAD_MOX_FUEL_ROD;
            default -> throw new RuntimeException("invalid cell count: " + this.numberOfCells);
        }));
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack2, int n, int n2, int n3, int n4, boolean bl) {
        if (!bl) {
            float f = (float)iReactor.getHeat() / (float)iReactor.getMaxHeat();
            float f2 = 4.0f * f + 1.0f;
            iReactor.addOutput(f2);
        }
        return true;
    }
}

