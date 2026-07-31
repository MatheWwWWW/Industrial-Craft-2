/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.item.reactor.AbstractDamageableReactorComponent;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.ItemName;
import java.util.ArrayDeque;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemReactorUranium
extends AbstractDamageableReactorComponent {
    public final int numberOfCells;

    public ItemReactorUranium(ItemName name, int cells) {
        this(name, cells, 20000);
    }

    protected ItemReactorUranium(ItemName name, int cells, int duration) {
        super(name, duration);
        this.func_77625_d(64);
        this.numberOfCells = cells;
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void registerModels(ItemName name) {
        this.registerModel(0, name, null);
        this.registerModel(1, name, null);
    }

    public int getMetadata(ItemStack stack) {
        return this.getCustomDamage(stack) > 0 ? 1 : 0;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
        if (!reactor.produceEnergy()) {
            return;
        }
        int basePulses = 1 + this.numberOfCells / 2;
        for (int iteration = 0; iteration < this.numberOfCells; ++iteration) {
            int dheat;
            int pulses = basePulses;
            if (!heatRun) {
                for (int i = 0; i < pulses; ++i) {
                    this.acceptUraniumPulse(stack, reactor, stack, x, y, x, y, heatRun);
                }
                pulses += ItemReactorUranium.checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) + ItemReactorUranium.checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) + ItemReactorUranium.checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) + ItemReactorUranium.checkPulseable(reactor, x, y + 1, stack, x, y, heatRun);
                continue;
            }
            int heat = ItemReactorUranium.triangularNumber(pulses += ItemReactorUranium.checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) + ItemReactorUranium.checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) + ItemReactorUranium.checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) + ItemReactorUranium.checkPulseable(reactor, x, y + 1, stack, x, y, heatRun)) * 4;
            ArrayDeque<ItemStackCoord> heatAcceptors = new ArrayDeque<ItemStackCoord>();
            this.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
            this.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
            this.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
            this.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
            for (heat = this.getFinalHeat(stack, reactor, x, y, heat); !heatAcceptors.isEmpty() && heat > 0; heat += dheat) {
                dheat = heat / heatAcceptors.size();
                heat -= dheat;
                ItemStackCoord acceptor = (ItemStackCoord)heatAcceptors.remove();
                IReactorComponent acceptorComp = (IReactorComponent)acceptor.stack.func_77973_b();
                dheat = acceptorComp.alterHeat(acceptor.stack, reactor, acceptor.x, acceptor.y, dheat);
            }
            if (heat <= 0) continue;
            reactor.addHeat(heat);
        }
        if (!heatRun && this.getCustomDamage(stack) >= this.getMaxCustomDamage(stack) - 1) {
            reactor.setItemAt(x, y, this.getDepletedStack(stack, reactor));
        } else if (!heatRun) {
            this.applyCustomDamage(stack, 1, null);
        }
    }

    protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
        return heat;
    }

    protected ItemStack getDepletedStack(ItemStack stack, IReactor reactor) {
        ItemStack ret;
        switch (this.numberOfCells) {
            case 1: {
                ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_uranium);
                break;
            }
            case 2: {
                ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_dual_uranium);
                break;
            }
            case 4: {
                ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_quad_uranium);
                break;
            }
            default: {
                throw new RuntimeException("invalid cell count: " + this.numberOfCells);
            }
        }
        return ret.func_77946_l();
    }

    protected static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey, boolean heatrun) {
        ItemStack other = reactor.getItemAt(x, y);
        if (other != null && other.func_77973_b() instanceof IReactorComponent && ((IReactorComponent)other.func_77973_b()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey, heatrun)) {
            return 1;
        }
        return 0;
    }

    protected static int triangularNumber(int x) {
        return (x * x + x) / 2;
    }

    protected void checkHeatAcceptor(IReactor reactor, int x, int y, Collection<ItemStackCoord> heatAcceptors) {
        ItemStack stack = reactor.getItemAt(x, y);
        if (stack != null && stack.func_77973_b() instanceof IReactorComponent && ((IReactorComponent)stack.func_77973_b()).canStoreHeat(stack, reactor, x, y)) {
            heatAcceptors.add(new ItemStackCoord(stack, x, y));
        }
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            reactor.addOutput(1.0f);
        }
        return true;
    }

    @Override
    public float influenceExplosion(ItemStack stack, IReactor reactor) {
        return 2 * this.numberOfCells;
    }

    public void func_77663_a(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
        EntityLivingBase entityLiving;
        if (entity instanceof EntityLivingBase && !ItemArmorHazmat.hasCompleteHazmat(entityLiving = (EntityLivingBase)entity)) {
            IC2Potion.radiation.applyTo(entityLiving, 200, 100);
        }
    }

    private static class ItemStackCoord {
        public final ItemStack stack;
        public final int x;
        public final int y;

        public ItemStackCoord(ItemStack stack, int x, int y) {
            this.stack = stack;
            this.x = x;
            this.y = y;
        }
    }
}

