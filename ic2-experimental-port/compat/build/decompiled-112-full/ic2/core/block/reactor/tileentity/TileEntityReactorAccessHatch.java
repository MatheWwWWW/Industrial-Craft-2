/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.world.World
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.items.CapabilityItemHandler
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.wrapper.InvWrapper
 */
package ic2.core.block.reactor.tileentity;

import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorVessel;
import ic2.core.profile.NotClassic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

@NotClassic
public class TileEntityReactorAccessHatch
extends TileEntityReactorVessel
implements IInventory {
    private IItemHandler itemHandler;

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        if (reactor != null) {
            World world = this.func_145831_w();
            return reactor.getBlockType().func_180639_a(world, reactor.func_174877_v(), world.func_180495_p(reactor.func_174877_v()), player, hand, side, hitX, hitY, hitZ);
        }
        return false;
    }

    public String func_70005_c_() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70005_c_() : "<null>";
    }

    public boolean func_145818_k_() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_145818_k_() : false;
    }

    public ITextComponent func_145748_c_() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_145748_c_() : new TextComponentString("<null>");
    }

    public int func_70302_i_() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70302_i_() : 0;
    }

    public boolean func_191420_l() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_191420_l() : true;
    }

    public ItemStack func_70301_a(int index) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70301_a(index) : null;
    }

    public ItemStack func_70298_a(int index, int count) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70298_a(index, count) : null;
    }

    public ItemStack func_70304_b(int index) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70304_b(index) : null;
    }

    public void func_70299_a(int index, ItemStack stack) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        if (reactor != null) {
            reactor.func_70299_a(index, stack);
        }
    }

    public int func_70297_j_() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70297_j_() : 0;
    }

    public boolean func_70300_a(EntityPlayer player) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_70300_a(player) : false;
    }

    public void func_174889_b(EntityPlayer player) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        if (reactor != null) {
            reactor.func_174889_b(player);
        }
    }

    public void func_174886_c(EntityPlayer player) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        if (reactor != null) {
            reactor.func_174886_c(player);
        }
    }

    public boolean func_94041_b(int index, ItemStack stack) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_94041_b(index, stack) : false;
    }

    public int func_174887_a_(int id) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_174887_a_(id) : 0;
    }

    public void func_174885_b(int id, int value) {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        if (reactor != null) {
            reactor.func_174885_b(id, value);
        }
    }

    public int func_174890_g() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        return reactor != null ? reactor.func_174890_g() : 0;
    }

    public void func_174888_l() {
        TileEntityNuclearReactorElectric reactor = this.getReactorInstance();
        if (reactor != null) {
            reactor.func_174888_l();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (this.itemHandler == null) {
                this.itemHandler = new InvWrapper((IInventory)this);
            }
            return (T)CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast((Object)this.itemHandler);
        }
        return super.getCapability(capability, facing);
    }
}

