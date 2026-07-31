/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.DrawBlockHighlightEvent
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.capability.CapabilityFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidTankProperties
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.transport;

import ic2.api.transport.IFluidPipe;
import ic2.core.IC2;
import ic2.core.block.transport.TileEntityPipe;
import ic2.core.block.transport.cover.CoverProperty;
import ic2.core.block.transport.cover.ICoverItem;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.item.block.ItemPipe;
import ic2.core.util.LiquidUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid="ic2", value={Side.CLIENT})
public class TileEntityFluidPipe
extends TileEntityPipe
implements IFluidPipe {
    protected PipeType type = PipeType.bronze;
    protected PipeSize size = PipeSize.small;
    protected PipeFluidTank tank;
    private boolean debug = false;

    public TileEntityFluidPipe() {
    }

    public TileEntityFluidPipe(PipeType type, PipeSize size) {
        this();
        this.type = type;
        this.size = size;
        this.tank = new PipeFluidTank(Util.allFacings, Util.allFacings, fluid -> true, (int)((float)type.transferRate * size.multiplier));
    }

    @Override
    public Set<CoverProperty> getCoverProperties() {
        return EnumSet.of(CoverProperty.FluidConsuming);
    }

    @Override
    public int getTransferRate() {
        return this.type.transferRate;
    }

    @Override
    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    public int getCurrentInnerCapacity() {
        return this.tank.getFluidAmount();
    }

    @Override
    public int getMaxInnerCapacity() {
        return this.tank.getCapacity();
    }

    @Override
    public void flipConnection(EnumFacing facing) {
        World world = this.func_145831_w();
        BlockPos pos = this.func_174877_v();
        if (!world.field_72995_K) {
            this.connectivity = (byte)(this.connectivity ^ 1 << facing.ordinal());
            IC2.network.get(true).updateTileEntityField(this, "connectivity");
            world.func_175685_c(pos, (Block)this.getBlockType(), true);
            this.func_70296_d();
        }
    }

    @Override
    protected void updateEntityServer() {
        block16: {
            LiquidUtil.AdjacentFluidHandler target;
            super.updateEntityServer();
            if (this.tank.getFluid() == null || this.tank.getFluidAmount() <= 0) break block16;
            int availableFluidAmount = this.tank.getFluidAmount();
            int cPipes = 1;
            ArrayList<LiquidUtil.AdjacentFluidHandler> adjacentFluidHandlers = new ArrayList<LiquidUtil.AdjacentFluidHandler>();
            for (EnumFacing facing : EnumFacing.field_82609_l) {
                LiquidUtil.AdjacentFluidHandler target2;
                if (LiquidUtil.drainTile(this, facing, Integer.MAX_VALUE, true) == null || (target2 = LiquidUtil.getAdjacentHandler(this, facing)) == null) continue;
                if (target2.handler instanceof IFluidPipe) {
                    int targetAmount = ((IFluidPipe)target2.handler).getTank().getFluidAmount();
                    if (this.tank.getFluidAmount() < targetAmount) continue;
                    availableFluidAmount += targetAmount;
                    ++cPipes;
                    adjacentFluidHandlers.add(target2);
                    continue;
                }
                if (LiquidUtil.fillTile(target2.handler, facing.func_176734_d(), this.tank.getFluid(), true) <= 0) continue;
                adjacentFluidHandlers.add(target2);
            }
            if (this.debug) {
                IC2.log.warn(LogCategory.Transport, "Number of valid adjacentFluidHandlers: %s", adjacentFluidHandlers.size());
            }
            int extraFluid = availableFluidAmount % cPipes;
            availableFluidAmount /= cPipes;
            ArrayList<LiquidUtil.AdjacentFluidHandler> adjacentPipes = new ArrayList<LiquidUtil.AdjacentFluidHandler>();
            if (!adjacentFluidHandlers.isEmpty()) {
                Iterator it = adjacentFluidHandlers.iterator();
                while (it.hasNext()) {
                    target = (LiquidUtil.AdjacentFluidHandler)it.next();
                    if (!(target.handler instanceof IFluidPipe)) continue;
                    FluidStack ret = LiquidUtil.transfer(this, target.dir, target.handler, availableFluidAmount - ((IFluidPipe)target.handler).getTank().getFluidAmount());
                    if (this.debug) {
                        IC2.log.warn(LogCategory.Transport, "Split with pipe: %s to facing %s", ret != null ? ret.amount : 0, target.dir);
                    }
                    adjacentPipes.add(target);
                    it.remove();
                }
            }
            if (adjacentFluidHandlers.isEmpty()) {
                while (extraFluid > 0) {
                    int bullet = IC2.random.nextInt(adjacentPipes.size());
                    target = (LiquidUtil.AdjacentFluidHandler)adjacentPipes.get(bullet);
                    LiquidUtil.transfer(this, target.dir, target.handler, 1);
                    if (this.debug) {
                        IC2.log.warn(LogCategory.Transport, "Split with pipe: 1 to facing %s", target.dir);
                    }
                    --extraFluid;
                    adjacentPipes.remove(bullet);
                }
            } else {
                availableFluidAmount += extraFluid;
            }
            if (!adjacentFluidHandlers.isEmpty()) {
                int maxTransfer = Math.min(availableFluidAmount, (int)((float)this.type.transferRate * this.size.multiplier / 20.0f));
                int maxAmountPerOutput = (int)Math.floor((float)maxTransfer / (float)adjacentFluidHandlers.size());
                if (maxAmountPerOutput <= 0) {
                    while (maxTransfer > 0) {
                        int bullet = IC2.random.nextInt(adjacentFluidHandlers.size());
                        LiquidUtil.AdjacentFluidHandler target3 = (LiquidUtil.AdjacentFluidHandler)adjacentFluidHandlers.get(bullet);
                        LiquidUtil.transfer(this, target3.dir, target3.handler, 1);
                        if (this.debug) {
                            IC2.log.warn(LogCategory.Transport, "Transferred: 1 to facing %s", target3.dir);
                        }
                        --maxTransfer;
                        adjacentFluidHandlers.remove(bullet);
                    }
                } else {
                    for (LiquidUtil.AdjacentFluidHandler target4 : adjacentFluidHandlers) {
                        FluidStack ret = LiquidUtil.transfer(this, target4.dir, target4.handler, maxAmountPerOutput);
                        if (!this.debug) continue;
                        IC2.log.warn(LogCategory.Transport, "Transferred: %s to facing %s", ret != null ? ret.amount : 0, target4.dir);
                    }
                }
            }
        }
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.type = PipeType.values[nbt.func_74771_c("type") & 0xFF];
        this.size = PipeSize.values()[nbt.func_74771_c("size") & 0xFF];
        this.tank = new PipeFluidTank(Util.allFacings, Util.allFacings, fluid -> true, (int)((float)this.type.transferRate * this.size.multiplier));
        this.tank.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74774_a("type", (byte)this.type.ordinal());
        nbt.func_74774_a("size", (byte)this.size.ordinal());
        this.tank.writeToNBT(nbt);
        return nbt;
    }

    @Override
    protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
        return ItemPipe.getPipe(this.type, this.size);
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

    @Override
    protected void updateConnectivity() {
        byte newConnectivity = this.connectivity;
        for (EnumFacing direction : EnumFacing.field_82609_l) {
            TileEntity tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(direction));
            if (tile == null) continue;
            if (tile instanceof IFluidPipe) {
                if (!((IFluidPipe)tile).isConnected(direction.func_176734_d())) continue;
                newConnectivity = (byte)(newConnectivity | 1 << direction.ordinal());
                continue;
            }
            if (!LiquidUtil.isFluidTile(tile, direction.func_176734_d())) continue;
            newConnectivity = (byte)(newConnectivity | this.connectivity & 1 << direction.ordinal());
        }
        if (this.connectivity != newConnectivity) {
            this.connectivity = newConnectivity;
            IC2.network.get(true).updateTileEntityField(this, "connectivity");
        }
    }

    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        TileEntity tile;
        super.onPlaced(stack, placer, facing);
        if (!this.field_145850_b.field_72995_K && (tile = this.field_145850_b.func_175625_s(this.field_174879_c.func_177972_a(facing.func_176734_d()))) != null && (tile instanceof IFluidPipe || LiquidUtil.isFluidTile(tile, facing))) {
            IFluidPipe other;
            this.flipConnection(facing.func_176734_d());
            if (tile instanceof IFluidPipe && !(other = (IFluidPipe)tile).isConnected(facing)) {
                other.flipConnection(facing);
            }
        }
    }

    @Override
    protected void onBlockBreak() {
        super.onBlockBreak();
        if (this.tank.getFluidAmount() > 1000 && LiquidUtil.fillBlock(this.tank.getFluid(), this.field_145850_b, this.field_174879_c, true)) {
            LiquidUtil.fillBlock(this.tank.getFluid(), this.field_145850_b, this.field_174879_c, false);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? facing != null && this.isConnected(facing) : super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)(facing != null && this.isConnected(facing) ? new PipeFluidHandler(facing) : null);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
        if (!forCollision) {
            return super.getAabbs(false);
        }
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
            float cs = 1.0f;
            float ch = 0.1f;
            boolean hasCover = (this.covers & 1 << facing.ordinal()) != 0;
            zS = 0.0f;
            yS = 0.0f;
            xS = 0.0f;
            zE = 1.0f;
            yE = 1.0f;
            xE = 1.0f;
            if (!hasCover) continue;
            switch (facing) {
                case DOWN: {
                    yS = 0.0f;
                    yE = ch;
                    break;
                }
                case UP: {
                    yS = cs - ch;
                    yE = 1.0f;
                    break;
                }
                case NORTH: {
                    zS = 0.0f;
                    zE = ch;
                    break;
                }
                case SOUTH: {
                    zS = cs - ch;
                    zE = 1.0f;
                    break;
                }
                case WEST: {
                    xS = 0.0f;
                    xE = ch;
                    break;
                }
                case EAST: {
                    xS = cs - ch;
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
    public AxisAlignedBB getVisualBoundingBox() {
        return this.getPhysicsBoundingBox();
    }

    @Override
    protected AxisAlignedBB getOutlineBoundingBox() {
        return super.getVisualBoundingBox();
    }

    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.LOWEST)
    public static void drawBetter(DrawBlockHighlightEvent event) {
        if (event.getSubID() != 0) {
            return;
        }
        RayTraceResult rayTrace = event.getTarget();
        if (rayTrace.field_72313_a != RayTraceResult.Type.BLOCK) {
            return;
        }
        EntityPlayer player = event.getPlayer();
        World world = player.func_130014_f_();
        BlockPos pos = rayTrace.func_178782_a();
        if (!world.func_175723_af().func_177746_a(pos)) {
            return;
        }
        TileEntity te = world.func_175625_s(pos);
        if (!(te instanceof TileEntityFluidPipe)) {
            return;
        }
        GlStateManager.func_179147_l();
        GlStateManager.func_187428_a((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.func_187441_d((float)2.0f);
        GlStateManager.func_179090_x();
        GlStateManager.func_179132_a((boolean)false);
        double xOffset = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)event.getPartialTicks();
        double yOffset = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)event.getPartialTicks();
        double zOffset = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)event.getPartialTicks();
        RenderGlobal.func_189697_a((AxisAlignedBB)((TileEntityFluidPipe)te).getVisualBoundingBox().func_186670_a(pos).func_186662_g(0.002).func_72317_d(-xOffset, -yOffset, -zOffset), (float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        event.setCanceled(true);
    }

    private class PipeFluidTank
    extends FluidTank {
        private final Predicate<Fluid> acceptedFluids;
        private Collection<EnumFacing> inputSides;
        private Collection<EnumFacing> outputSides;

        protected PipeFluidTank(Collection<EnumFacing> inputSides, Collection<EnumFacing> outputSides, Predicate<Fluid> acceptedFluids, int capacity) {
            super(capacity);
            this.acceptedFluids = acceptedFluids;
            this.inputSides = inputSides;
            this.outputSides = outputSides;
        }

        public boolean canFillFluidType(FluidStack fluid) {
            return fluid != null && this.acceptsFluid(fluid.getFluid());
        }

        public boolean canDrainFluidType(FluidStack fluid) {
            return fluid != null && this.acceptsFluid(fluid.getFluid());
        }

        public boolean acceptsFluid(Fluid fluid) {
            return this.acceptedFluids.test(fluid);
        }

        IFluidTankProperties getTankProperties(final EnumFacing side) {
            assert (side == null || this.inputSides.contains(side) || this.outputSides.contains(side));
            return new IFluidTankProperties(){

                public FluidStack getContents() {
                    return PipeFluidTank.this.getFluid();
                }

                public int getCapacity() {
                    return PipeFluidTank.this.capacity;
                }

                public boolean canFillFluidType(FluidStack fluidStack) {
                    if (fluidStack == null || fluidStack.amount <= 0) {
                        return false;
                    }
                    return PipeFluidTank.this.acceptsFluid(fluidStack.getFluid()) && (side == null || PipeFluidTank.this.canFill(side));
                }

                public boolean canFill() {
                    return PipeFluidTank.this.canFill(side);
                }

                public boolean canDrainFluidType(FluidStack fluidStack) {
                    if (fluidStack == null || fluidStack.amount <= 0) {
                        return false;
                    }
                    return PipeFluidTank.this.acceptsFluid(fluidStack.getFluid()) && (side == null || PipeFluidTank.this.canDrain(side));
                }

                public boolean canDrain() {
                    return PipeFluidTank.this.canDrain(side);
                }
            };
        }

        public boolean canFill(EnumFacing side) {
            return this.inputSides.contains(side);
        }

        public boolean canDrain(EnumFacing side) {
            return this.outputSides.contains(side);
        }
    }

    private class PipeFluidHandler
    implements IFluidHandler {
        private final EnumFacing side;

        public PipeFluidHandler(EnumFacing side) {
            this.side = side;
        }

        public IFluidTankProperties[] getTankProperties() {
            return TileEntityFluidPipe.this.tank.getTankProperties();
        }

        public int fill(FluidStack resource, boolean doFill) {
            ICoverItem cover;
            if (TileEntityFluidPipe.this.coversComponent.hasCover(this.side) && !(cover = TileEntityFluidPipe.this.coversComponent.getCoverItem(this.side)).allowsInput(resource)) {
                return 0;
            }
            return TileEntityFluidPipe.this.tank.fill(resource, doFill);
        }

        @Nullable
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            ICoverItem cover;
            if (TileEntityFluidPipe.this.coversComponent.hasCover(this.side) && !(cover = TileEntityFluidPipe.this.coversComponent.getCoverItem(this.side)).allowsOutput(resource)) {
                return null;
            }
            return TileEntityFluidPipe.this.tank.drain(resource, doDrain);
        }

        @Nullable
        public FluidStack drain(int maxDrain, boolean doDrain) {
            ICoverItem cover;
            if (TileEntityFluidPipe.this.coversComponent.hasCover(this.side) && !(cover = TileEntityFluidPipe.this.coversComponent.getCoverItem(this.side)).allowsOutput(new FluidStack(Objects.requireNonNull(TileEntityFluidPipe.this.tank.getFluid()), maxDrain))) {
                return null;
            }
            return TileEntityFluidPipe.this.tank.drain(maxDrain, doDrain);
        }
    }
}

