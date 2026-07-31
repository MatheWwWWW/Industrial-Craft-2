/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

@NotClassic
public class ItemReactorMOX
extends ItemReactorUranium {
    public ItemReactorMOX(ItemName name, int cells) {
        super(name, cells, 10000);
    }

    @Override
    protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
        float breedereffectiveness;
        if (reactor.isFluidCooled() && (double)(breedereffectiveness = (float)reactor.getHeat() / (float)reactor.getMaxHeat()) > 0.5) {
            heat *= 2;
        }
        return heat;
    }

    @Override
    protected ItemStack getDepletedStack(ItemStack stack, IReactor reactor) {
        ItemStack ret;
        switch (this.numberOfCells) {
            case 1: {
                ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_mox);
                break;
            }
            case 2: {
                ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_dual_mox);
                break;
            }
            case 4: {
                ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_quad_mox);
                break;
            }
            default: {
                throw new RuntimeException("invalid cell count: " + this.numberOfCells);
            }
        }
        return ret.func_77946_l();
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            float breedereffectiveness = (float)reactor.getHeat() / (float)reactor.getMaxHeat();
            float ReaktorOutput = 4.0f * breedereffectiveness + 1.0f;
            reactor.addOutput(ReaktorOutput);
        }
        return true;
    }
}

