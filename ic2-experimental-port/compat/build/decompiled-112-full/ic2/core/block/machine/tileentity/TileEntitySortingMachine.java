/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.EnumFacing
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerSortingMachine;
import ic2.core.block.machine.gui.GuiSortingMachine;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.profile.NotClassic;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntitySortingMachine
extends TileEntityElectricMachine
implements IHasGui,
INetworkClientTileEntityEventListener,
IUpgradableBlock {
    public static final int defaultTier = 2;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlot buffer;
    private final ItemStack[][] filters;
    public EnumFacing defaultRoute = EnumFacing.DOWN;

    public TileEntitySortingMachine() {
        super(15000, 2, false);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3);
        this.buffer = new InvSlot(this, "Buffer", InvSlot.Access.IO, 11);
        this.filters = new ItemStack[6][7];
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        NBTTagList filtersTag = nbt.func_150295_c("filters", 10);
        for (int i = 0; i < filtersTag.func_74745_c(); ++i) {
            ItemStack stack;
            NBTTagCompound filterTag = filtersTag.func_150305_b(i);
            int index = filterTag.func_74771_c("index") & 0xFF;
            this.filters[index / 7][index % 7] = stack = new ItemStack(filterTag);
        }
        byte defaultRouteIdx = nbt.func_74771_c("defaultroute");
        if (defaultRouteIdx >= 0 && defaultRouteIdx < EnumFacing.field_82609_l.length) {
            this.defaultRoute = EnumFacing.field_82609_l[defaultRouteIdx];
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        NBTTagList filtersTag = new NBTTagList();
        for (int i = 0; i < 42; ++i) {
            ItemStack stack = this.filters[i / 7][i % 7];
            if (stack == null) continue;
            NBTTagCompound contentTag = new NBTTagCompound();
            contentTag.func_74774_a("index", (byte)i);
            stack.func_77955_b(contentTag);
            filtersTag.func_74742_a((NBTBase)contentTag);
        }
        nbt.func_74782_a("filters", (NBTBase)filtersTag);
        nbt.func_74774_a("defaultroute", (byte)this.defaultRoute.ordinal());
        return nbt;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            this.setUpgradableBlock();
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        block0: for (int index = 0; index < this.buffer.size(); ++index) {
            if (this.energy.getEnergy() < 20.0) {
                return;
            }
            ItemStack stack = this.buffer.get(index);
            if (StackUtil.isEmpty(stack)) continue;
            block1: for (StackUtil.AdjacentInv inv : StackUtil.getAdjacentInventories(this)) {
                if (inv.dir != this.defaultRoute) {
                    for (ItemStack filterStack : this.getFilterSlots(inv.dir)) {
                        if (StackUtil.isEmpty(filterStack)) continue;
                        int filterSize = StackUtil.getSize(filterStack);
                        if (StackUtil.getSize(stack) < filterSize || !StackUtil.checkItemEquality(filterStack, stack) || !this.energy.canUseEnergy(filterSize * 20)) continue;
                        ItemStack transferStack = StackUtil.copyWithSize(stack, filterSize);
                        int amount = StackUtil.putInInventory(inv.te, inv.dir, transferStack, true);
                        if (amount != filterSize) continue block1;
                        amount = StackUtil.putInInventory(inv.te, inv.dir, transferStack, false);
                        stack = StackUtil.decSize(stack, amount);
                        this.buffer.put(index, stack);
                        this.energy.useEnergy(amount * 20);
                        if (!StackUtil.isEmpty(stack)) continue block1;
                        continue block0;
                    }
                    continue;
                }
                boolean inFilter = false;
                ItemStack[][] itemStackArray = this.filters;
                int n = itemStackArray.length;
                block3: for (int i = 0; i < n; ++i) {
                    ItemStack[] sideFilters;
                    for (ItemStack filter : sideFilters = itemStackArray[i]) {
                        if (!StackUtil.checkItemEquality(filter, stack)) continue;
                        inFilter = true;
                        break block3;
                    }
                }
                if (inFilter) continue;
                int amount = StackUtil.putInInventory(inv.te, inv.dir, StackUtil.copyWithSize(stack, 1), false);
                if (amount <= 0) continue block0;
                stack = StackUtil.decSize(stack, amount);
                this.buffer.put(index, stack);
                this.energy.useEnergy(20.0);
                if (StackUtil.isEmpty(stack)) continue block0;
            }
        }
    }

    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (event >= 0 && event <= 5) {
            this.defaultRoute = EnumFacing.field_82609_l[event];
        }
    }

    public ContainerBase<TileEntitySortingMachine> getGuiContainer(EntityPlayer player) {
        return new ContainerSortingMachine(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiSortingMachine(new ContainerSortingMachine(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer);
    }

    @Override
    public void func_70296_d() {
        super.func_70296_d();
        if (IC2.platform.isSimulating()) {
            this.setUpgradableBlock();
        }
    }

    public void setUpgradableBlock() {
        this.energy.setSinkTier(this.upgradeSlot.getTier(2));
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    public ItemStack[] getFilterSlots(EnumFacing side) {
        return this.filters[side.ordinal()];
    }
}

