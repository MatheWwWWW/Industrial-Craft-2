/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.container.ContainerItemBuffer;
import ic2.core.block.machine.gui.GuiItemBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityItemBuffer
extends TileEntityInventory
implements IHasGui,
IUpgradableBlock {
    public final InvSlot rightcontentSlot = new InvSlot(this, "rightcontent", InvSlot.Access.IO, 24, InvSlot.InvSide.SIDE);
    public final InvSlot leftcontentSlot = new InvSlot(this, "leftcontent", InvSlot.Access.IO, 24, InvSlot.InvSide.NOTSIDE);
    public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
    private boolean tick = true;

    public TileEntityItemBuffer() {
        this.comparator.setUpdate(() -> TileEntityItemBuffer.calcRedstoneFromInvSlots(this.rightcontentSlot, this.leftcontentSlot));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        ItemStack upgradeleft = this.upgradeSlot.get(0);
        ItemStack upgraderight = this.upgradeSlot.get(1);
        if (!StackUtil.isEmpty(upgradeleft) && !StackUtil.isEmpty(upgraderight)) {
            if (this.tick) {
                if (((IUpgradeItem)upgradeleft.func_77973_b()).onTick(upgradeleft, this)) {
                    super.func_70296_d();
                }
            } else if (((IUpgradeItem)upgraderight.func_77973_b()).onTick(upgraderight, this)) {
                super.func_70296_d();
            }
            this.tick = !this.tick;
        } else {
            if (!StackUtil.isEmpty(upgradeleft)) {
                this.tick = true;
                if (((IUpgradeItem)upgradeleft.func_77973_b()).onTick(upgradeleft, this)) {
                    super.func_70296_d();
                }
            }
            if (!StackUtil.isEmpty(upgraderight)) {
                this.tick = false;
                if (((IUpgradeItem)upgraderight.func_77973_b()).onTick(upgraderight, this)) {
                    super.func_70296_d();
                }
            }
        }
    }

    public ContainerBase<TileEntityItemBuffer> getGuiContainer(EntityPlayer player) {
        return new ContainerItemBuffer(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiItemBuffer(new ContainerItemBuffer(player, this));
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemProducing);
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public double getEnergy() {
        return 40.0;
    }

    @Override
    public boolean useEnergy(double amount) {
        return true;
    }
}

