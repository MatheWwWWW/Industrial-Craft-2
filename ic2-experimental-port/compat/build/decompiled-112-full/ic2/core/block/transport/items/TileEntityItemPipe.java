/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.RayTraceResult
 */
package ic2.core.block.transport.items;

import ic2.api.transport.IItemTransportTile;
import ic2.core.block.transport.TileEntityPipe;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.item.block.ItemPipe;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

public class TileEntityItemPipe
extends TileEntityPipe
implements IItemTransportTile {
    protected PipeType type = PipeType.bronze;
    protected PipeSize size = PipeSize.small;
    protected ItemStack contents;

    public TileEntityItemPipe() {
    }

    @Override
    public void flipConnection(EnumFacing facing) {
    }

    public TileEntityItemPipe(PipeType type, PipeSize size) {
        this();
        this.type = type;
        this.size = size;
    }

    @Override
    public int putItems(ItemStack stack, EnumFacing facing, boolean simulate) {
        if (StackUtil.isEmpty(stack)) {
            return 0;
        }
        if (!StackUtil.isEmpty(this.contents)) {
            return 0;
        }
        if (stack.func_190916_E() > this.getMaxStackSizeAllowed()) {
            return 0;
        }
        if (!simulate) {
            this.contents = StackUtil.copy(stack);
        }
        return stack.func_190916_E();
    }

    @Override
    public int getMaxStackSizeAllowed() {
        return this.size.maxStackSize;
    }

    @Override
    public int getTransferRate() {
        return this.type.transferRate;
    }

    @Override
    public ItemStack getContents() {
        return this.contents;
    }

    @Override
    public void setContents(ItemStack stack) {
        this.contents = stack;
    }

    @Override
    protected void updateEntityServer() {
        EnumFacing facing;
        TileEntity target;
        super.updateEntityServer();
        boolean needsInventoryUpdate = false;
        if (!StackUtil.isEmpty(this.contents) && (target = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(facing = this.getFacing()))) instanceof IItemTransportTile && ((IItemTransportTile)target).putItems(this.contents, facing.func_176734_d(), true) > 0) {
            int amount = ((IItemTransportTile)target).putItems(this.contents, facing.func_176734_d(), false);
            ItemStack newStack = StackUtil.copyShrunk(this.contents, amount);
            assert (newStack.func_190926_b());
            this.contents = null;
            needsInventoryUpdate = true;
        }
        if (needsInventoryUpdate) {
            this.func_70296_d();
        }
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.type = PipeType.values[nbt.func_74771_c("type") & 0xFF];
        this.size = PipeSize.values()[nbt.func_74771_c("size") & 0xFF];
        NBTTagList contentsTag = nbt.func_150295_c("contents", 10);
        for (int i = 0; i < contentsTag.func_74745_c(); ++i) {
            NBTTagCompound contentTag = contentsTag.func_150305_b(i);
            ItemStack stack = new ItemStack(contentTag);
            if (StackUtil.isEmpty(stack)) continue;
            this.contents = stack;
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74774_a("type", (byte)this.type.ordinal());
        nbt.func_74774_a("size", (byte)this.size.ordinal());
        NBTTagList contentsTag = new NBTTagList();
        if (!StackUtil.isEmpty(this.contents)) {
            NBTTagCompound contentTag = new NBTTagCompound();
            this.contents.func_77955_b(contentTag);
            contentsTag.func_74742_a((NBTBase)contentTag);
            nbt.func_74782_a("contents", (NBTBase)contentsTag);
        }
        return nbt;
    }

    @Override
    protected void updateConnectivity() {
    }

    @Override
    protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
        return ItemPipe.getPipe(this.type, this.size);
    }

    @Override
    protected List<ItemStack> getAuxDrops(int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>(super.getAuxDrops(fortune));
        if (!StackUtil.isEmpty(this.contents)) {
            ret.add(this.contents);
        }
        return ret;
    }

    @Override
    protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
        float th = this.size.thickness;
        float sp = (1.0f - th) / 2.0f;
        ArrayList<AxisAlignedBB> ret = new ArrayList<AxisAlignedBB>(7);
        ret.add(new AxisAlignedBB((double)sp, (double)sp, (double)sp, (double)(sp + th), (double)(sp + th), (double)(sp + th)));
        for (EnumFacing facing : EnumFacing.field_82609_l) {
            float zE;
            float zS;
            boolean hasConnection;
            boolean bl = hasConnection = (this.connectivity & 1 << facing.ordinal()) != 0;
            if (!hasConnection) continue;
            float yS = zS = sp;
            float xS = zS;
            float yE = zE = sp + th;
            float xE = zE;
            switch (facing) {
                case DOWN: {
                    yS = 0.0f;
                    yE = sp;
                    break;
                }
                case UP: {
                    yS = sp + th;
                    yE = 1.0f;
                    break;
                }
                case NORTH: {
                    zS = 0.0f;
                    zE = sp;
                    break;
                }
                case SOUTH: {
                    zS = sp + th;
                    zE = 1.0f;
                    break;
                }
                case WEST: {
                    xS = 0.0f;
                    xE = sp;
                    break;
                }
                case EAST: {
                    xS = sp + th;
                    xE = 1.0f;
                    break;
                }
                default: {
                    throw new RuntimeException();
                }
            }
            ret.add(new AxisAlignedBB((double)xS, (double)yS, (double)zS, (double)xE, (double)yE, (double)zE));
        }
        return ret;
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("type");
        ret.add("size");
        return ret;
    }

    @Override
    protected void updateRenderState() {
        this.renderState = new TileEntityPipe.PipeRenderState(this.type, this.size, this.connectivity, this.covers, this.getFacing().ordinal());
    }
}

