/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.inv.inventory;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorProduct;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.NuclearJetpackContainer;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class NuclearJetpackInventory
extends PortableInventory
implements IReactor {
    public NuclearJetpackInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new NuclearJetpackContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 25;
    }

    @Override
    public CompoundTag getNBTData(ItemStack stack, boolean create) {
        CompoundTag data = super.getNBTData(stack, create);
        if (!(stack.m_41720_() instanceof IC2ElectricJetpackBase)) {
            CompoundTag subData = data.m_128469_("jetpack_data");
            if (create) {
                data.m_128365_("jetpack_data", (Tag)subData);
            }
            return subData;
        }
        return data;
    }

    @Override
    public int getHeat() {
        return 0;
    }

    @Override
    public void setHeat(int newHeat) {
    }

    @Override
    public void addHeat(int heat) {
    }

    @Override
    public int getMaxHeat() {
        return 0;
    }

    @Override
    public void setMaxHeat(int heat) {
    }

    @Override
    public float getHeatEffectModifier() {
        return 0.0f;
    }

    @Override
    public void setHeatEffectModifier(float hem) {
    }

    @Override
    public double getEnergyOutput() {
        return 0.0;
    }

    @Override
    public void addOutput(float output) {
    }

    @Override
    public ItemStack getStackInReactor(int x, int y) {
        return x < 0 || x > 4 || y < 0 || y > 4 ? ItemStack.f_41583_ : (ItemStack)this.inventory.get(x + y * 5);
    }

    @Override
    public void setStackInReactor(int x, int y, ItemStack stack) {
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return;
        }
        this.inventory.set(x + y * 5, (Object)stack);
    }

    @Override
    public void explode() {
    }

    @Override
    public int getTickRate() {
        return 0;
    }

    @Override
    public boolean isProducingEnergy() {
        return false;
    }

    public boolean isUsefulItem(ItemStack stack) {
        if (stack.m_41619_()) {
            return false;
        }
        Item item = stack.m_41720_();
        if (item instanceof IReactorComponent) {
            return true;
        }
        if (item instanceof IReactorProduct) {
            return ((IReactorProduct)item).isValidForReactor(stack, this);
        }
        return false;
    }
}

