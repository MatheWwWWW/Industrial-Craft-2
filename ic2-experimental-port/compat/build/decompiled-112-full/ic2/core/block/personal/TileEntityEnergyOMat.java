/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTUtil
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotConsumableLinked;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.personal.ContainerEnergyOMatClosed;
import ic2.core.block.personal.ContainerEnergyOMatOpen;
import ic2.core.block.personal.GuiEnergyOMatClosed;
import ic2.core.block.personal.GuiEnergyOMatOpen;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityEnergyOMat
extends TileEntityInventory
implements IPersonalBlock,
IHasGui,
IEnergySink,
IEnergySource,
INetworkClientTileEntityEventListener,
IUpgradableBlock {
    public int euOffer = 1000;
    private GameProfile owner = null;
    private boolean addedToEnergyNet = false;
    public int paidFor;
    public double euBuffer;
    private int euBufferMax = 10000;
    private int tier = 1;
    public final InvSlot demandSlot = new InvSlot(this, "demand", InvSlot.Access.NONE, 1);
    public final InvSlotConsumableLinked inputSlot = new InvSlotConsumableLinked(this, "input", 1, this.demandSlot);
    public final InvSlotCharge chargeSlot = new InvSlotCharge(this, 1);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 1);

    @Override
    public void func_145839_a(NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        if (nbttagcompound.func_74764_b("ownerGameProfile")) {
            this.owner = NBTUtil.func_152459_a((NBTTagCompound)nbttagcompound.func_74775_l("ownerGameProfile"));
        }
        this.euOffer = nbttagcompound.func_74762_e("euOffer");
        this.paidFor = nbttagcompound.func_74762_e("paidFor");
        try {
            this.euBuffer = nbttagcompound.func_74769_h("euBuffer");
        }
        catch (Exception e) {
            this.euBuffer = nbttagcompound.func_74762_e("euBuffer");
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        if (this.owner != null) {
            NBTTagCompound ownerNbt = new NBTTagCompound();
            NBTUtil.func_180708_a((NBTTagCompound)ownerNbt, (GameProfile)this.owner);
            nbt.func_74782_a("ownerGameProfile", (NBTBase)ownerNbt);
        }
        nbt.func_74768_a("euOffer", this.euOffer);
        nbt.func_74768_a("paidFor", this.paidFor);
        nbt.func_74780_a("euBuffer", this.euBuffer);
        return nbt;
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer player) {
        return this.permitsAccess(player.func_146103_bH());
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K) {
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }
        super.onUnloaded();
    }

    @Override
    protected void updateEntityServer() {
        double sent;
        int transferred;
        ItemStack tradedIn;
        super.updateEntityServer();
        boolean invChanged = false;
        this.euBufferMax = 10000;
        this.tier = 1;
        this.chargeSlot.setTier(1);
        if (!this.upgradeSlot.isEmpty()) {
            this.euBufferMax = this.upgradeSlot.getEnergyStorage(10000, 0, 0);
            this.tier = 1 + this.upgradeSlot.extraTier;
            this.chargeSlot.setTier(this.tier);
        }
        if ((tradedIn = this.inputSlot.consumeLinked(true)) != null && (transferred = StackUtil.distribute(this, tradedIn, true)) == StackUtil.getSize(tradedIn)) {
            StackUtil.distribute(this, this.inputSlot.consumeLinked(false), false);
            this.paidFor += this.euOffer;
            invChanged = true;
        }
        if (this.euBuffer >= 1.0 && (sent = this.chargeSlot.charge(this.euBuffer)) > 0.0) {
            this.euBuffer -= sent;
            invChanged = true;
        }
        if (invChanged) {
            this.func_70296_d();
        }
    }

    @Override
    public boolean permitsAccess(GameProfile profile) {
        return TileEntityPersonalChest.checkAccess(this, profile);
    }

    @Override
    public IInventory getPrivilegedInventory(GameProfile accessor) {
        return this;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add("owner");
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    @Override
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return false;
    }

    @Override
    protected boolean canSetFacingWrench(EnumFacing facing, EntityPlayer player) {
        if (player == null || !this.permitsAccess(player.func_146103_bH())) {
            return false;
        }
        return super.canSetFacingWrench(facing, player);
    }

    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
        return !this.facingMatchesDirection(direction);
    }

    public boolean facingMatchesDirection(EnumFacing direction) {
        return direction == this.getFacing();
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing direction) {
        return this.facingMatchesDirection(direction);
    }

    @Override
    public double getOfferedEnergy() {
        return this.euBuffer;
    }

    @Override
    public void drawEnergy(double amount) {
        this.euBuffer -= amount;
    }

    @Override
    public double getDemandedEnergy() {
        return Math.min((double)this.paidFor, (double)this.euBufferMax - this.euBuffer);
    }

    @Override
    public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
        double toAdd = Math.min(Math.min(amount, (double)this.paidFor), (double)this.euBufferMax - this.euBuffer);
        this.paidFor = (int)((double)this.paidFor - toAdd);
        this.euBuffer += toAdd;
        return amount - toAdd;
    }

    @Override
    public int getSourceTier() {
        return this.tier;
    }

    @Override
    public int getSinkTier() {
        return Integer.MAX_VALUE;
    }

    public ContainerBase<TileEntityEnergyOMat> getGuiContainer(EntityPlayer player) {
        if (this.permitsAccess(player.func_146103_bH())) {
            return new ContainerEnergyOMatOpen(player, this);
        }
        return new ContainerEnergyOMatClosed(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        if (isAdmin || this.permitsAccess(player.func_146103_bH())) {
            return new GuiEnergyOMatOpen(new ContainerEnergyOMatOpen(player, this));
        }
        return new GuiEnergyOMatClosed(new ContainerEnergyOMatClosed(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (!this.permitsAccess(player.func_146103_bH())) {
            return;
        }
        switch (event) {
            case 0: {
                this.attemptSet(-100000);
                break;
            }
            case 1: {
                this.attemptSet(-10000);
                break;
            }
            case 2: {
                this.attemptSet(-1000);
                break;
            }
            case 3: {
                this.attemptSet(-100);
                break;
            }
            case 4: {
                this.attemptSet(100000);
                break;
            }
            case 5: {
                this.attemptSet(10000);
                break;
            }
            case 6: {
                this.attemptSet(1000);
                break;
            }
            case 7: {
                this.attemptSet(100);
            }
        }
    }

    private void attemptSet(int amount) {
        this.euOffer += amount;
        if (this.euOffer < 100) {
            this.euOffer = 100;
        }
    }

    @Override
    public double getEnergy() {
        return this.euBuffer;
    }

    @Override
    public boolean useEnergy(double amount) {
        if (amount <= this.euBuffer) {
            amount -= this.euBuffer;
            return true;
        }
        return false;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.EnergyStorage, UpgradableProperty.Transformer);
    }
}

