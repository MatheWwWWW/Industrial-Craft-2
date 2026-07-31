/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.network.ClientModifiable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.container.ContainerWeightedItemDistributor;
import ic2.core.block.machine.gui.GuiWeightedItemDistributor;
import ic2.core.block.machine.tileentity.IWeightedDistributor;
import ic2.core.profile.NotClassic;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityWeightedItemDistributor
extends TileEntityInventory
implements IHasGui,
IWeightedDistributor {
    @ClientModifiable
    protected List<EnumFacing> priority = new ArrayList<EnumFacing>(5);
    public final InvSlot buffer = new InvSlot(this, "buffer", InvSlot.Access.I, 9);

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        if (!this.priority.isEmpty()) {
            int[] indexes = new int[this.priority.size()];
            for (int i = 0; i < indexes.length; ++i) {
                indexes[i] = this.priority.get(i).func_176745_a();
            }
            nbt.func_74783_a("priority", indexes);
        }
        return nbt;
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        int[] indexes = nbt.func_74759_k("priority");
        if (indexes.length > 0) {
            for (int index : indexes) {
                this.priority.add(EnumFacing.func_82600_a((int)index));
            }
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("priority");
        return ret;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.updateConnectivity();
    }

    @Override
    protected void setFacing(EnumFacing facing) {
        super.setFacing(facing);
        this.updateConnectivity();
    }

    protected void updateConnectivity() {
        if (!this.func_145831_w().field_72995_K && !this.priority.isEmpty() && this.priority.remove(this.getFacing())) {
            this.updatePriority(true);
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (!this.priority.isEmpty() && !this.buffer.isEmpty()) {
            World world = this.func_145831_w();
            boolean hasChanged = false;
            for (EnumFacing facing : this.priority) {
                EnumFacing side;
                TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(facing));
                if (!StackUtil.isInventoryTile(te, side = facing.func_176734_d())) continue;
                boolean empty = true;
                for (int index = 0; index < this.buffer.size(); ++index) {
                    ItemStack stack;
                    ItemStack transferStack;
                    int amount;
                    if (this.buffer.isEmpty(index) || (amount = StackUtil.putInInventory(te, side, transferStack = StackUtil.copy(stack = this.buffer.get(index)), true)) <= 0) continue;
                    amount = StackUtil.putInInventory(te, side, transferStack, false);
                    stack = StackUtil.decSize(stack, amount);
                    this.buffer.put(index, stack);
                    hasChanged = true;
                    empty &= StackUtil.isEmpty(stack);
                }
                if (!hasChanged || !empty) continue;
                break;
            }
            if (hasChanged) {
                this.func_70296_d();
            }
        }
    }

    @Override
    public ContainerBase<?> getGuiContainer(EntityPlayer player) {
        return new ContainerWeightedItemDistributor(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiWeightedItemDistributor(new ContainerWeightedItemDistributor(player, this));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public List<EnumFacing> getPriority() {
        return this.priority;
    }

    @Override
    public void updatePriority(boolean server) {
        IC2.network.get(server).updateTileEntityField(this, "priority");
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }
}

