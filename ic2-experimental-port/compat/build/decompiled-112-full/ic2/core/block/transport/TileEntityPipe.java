/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.common.property.IUnlistedProperty
 */
package ic2.core.block.transport;

import ic2.api.transport.IPipe;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.state.Ic2BlockState;
import ic2.core.block.state.UnlistedProperty;
import ic2.core.block.transport.cover.CoverProperty;
import ic2.core.block.transport.cover.Covers;
import ic2.core.block.transport.cover.ICoverHolder;
import ic2.core.block.transport.cover.ICoverItem;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;

public abstract class TileEntityPipe
extends TileEntityBlock
implements IPipe,
ICoverHolder {
    public static final IUnlistedProperty<PipeRenderState> renderStateProperty = new UnlistedProperty<PipeRenderState>("renderstate", PipeRenderState.class);
    protected volatile PipeRenderState renderState;
    protected byte connectivity = 0;
    protected byte covers = 0;
    protected final Covers coversComponent = this.addComponent(new Covers(this));

    @Override
    public TileEntity getTile() {
        return this;
    }

    @Override
    public boolean isConnected(EnumFacing facing) {
        return (this.connectivity & 1 << facing.ordinal()) != 0;
    }

    @Override
    public abstract void flipConnection(EnumFacing var1);

    @Override
    public Set<CoverProperty> getCoverProperties() {
        return Collections.emptySet();
    }

    @Override
    public boolean canPlaceCover(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
        Item rawItem = stack.func_77973_b();
        if (!(rawItem instanceof ICoverItem)) {
            return false;
        }
        ICoverItem item = (ICoverItem)rawItem;
        return item.isSuitableFor(stack, this.getCoverProperties()) && (this.covers & 1 << side.ordinal()) == 0;
    }

    @Override
    public void placeCover(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
        this.coversComponent.addCover(side, stack);
        this.covers = (byte)(this.covers ^ 1 << side.ordinal());
        IC2.network.get(true).updateTileEntityField(this, "covers");
    }

    @Override
    public boolean canRemoveCover(World world, BlockPos pos, EnumFacing side) {
        return (this.covers & 1 << side.ordinal()) != 0;
    }

    @Override
    public void removeCover(World world, BlockPos pos, EnumFacing side) {
        ItemStack ret = this.coversComponent.removeCover(side);
        this.covers = (byte)(this.covers ^ 1 << side.ordinal());
        IC2.network.get(true).updateTileEntityField(this, "covers");
        StackUtil.dropAsEntity(this.func_145831_w(), this.func_174877_v(), StackUtil.copyWithSize(ret, 1));
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (this.func_145831_w().field_72995_K) {
            this.updateRenderState();
        }
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.connectivity = nbt.func_74771_c("connectivity");
        this.covers = nbt.func_74771_c("covers");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74774_a("connectivity", this.connectivity);
        nbt.func_74774_a("covers", this.covers);
        return nbt;
    }

    @Override
    protected void onUnloaded() {
        super.onUnloaded();
    }

    @Override
    public void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
    }

    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        if (this.field_145850_b.field_72995_K) {
            this.updateRenderState();
        }
        super.onPlaced(stack, placer, facing);
    }

    protected abstract void updateConnectivity();

    @Override
    public Ic2BlockState.Ic2BlockStateInstance getExtendedState(Ic2BlockState.Ic2BlockStateInstance state) {
        state = super.getExtendedState(state);
        PipeRenderState pipeRenderState = this.renderState;
        if (pipeRenderState != null) {
            state = state.withProperties(renderStateProperty, pipeRenderState);
        }
        return state;
    }

    @Override
    protected SoundType getBlockSound(Entity entity) {
        return SoundType.field_185852_e;
    }

    @Override
    protected boolean isNormalCube() {
        return false;
    }

    @Override
    protected boolean isSideSolid(EnumFacing side) {
        return false;
    }

    @Override
    protected boolean doesSideBlockRendering(EnumFacing side) {
        return false;
    }

    @Override
    protected int getLightOpacity() {
        return 0;
    }

    @Override
    protected boolean clientNeedsExtraModelInfo() {
        return true;
    }

    @Override
    public void onNetworkUpdate(String field) {
        this.updateRenderState();
        this.rerender();
        super.onNetworkUpdate(field);
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("connectivity");
        ret.add("covers");
        return ret;
    }

    @Override
    protected List<ItemStack> getAuxDrops(int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (EnumFacing facing : EnumFacing.field_82609_l) {
            if (!this.coversComponent.hasCover(facing)) continue;
            ret.add(this.coversComponent.removeCover(facing));
        }
        return ret;
    }

    protected abstract void updateRenderState();

    public static class PipeRenderState {
        public final PipeType type;
        public final PipeSize size;
        public final int connectivity;
        public final int covers;
        public final int facing;

        public PipeRenderState(PipeType type, PipeSize size, int connectivity, int covers, int facing) {
            this.type = type;
            this.size = size;
            this.connectivity = connectivity;
            this.covers = covers;
            this.facing = facing;
        }

        public int hashCode() {
            int ret = this.type.hashCode();
            ret = ret * 31 + this.size.hashCode();
            ret = ret * 31 + this.connectivity;
            ret = ret * 31 + this.covers;
            ret = ret * 31 + this.facing;
            return ret;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PipeRenderState)) {
                return false;
            }
            PipeRenderState o = (PipeRenderState)obj;
            return o.type == this.type && o.size == this.size && o.connectivity == this.connectivity && o.covers == this.covers && o.facing == this.facing;
        }

        public String toString() {
            return "PipeState<" + this.type + ", " + this.size + ", " + this.connectivity + ", " + this.covers + ", " + this.facing + '>';
        }
    }
}

