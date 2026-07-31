/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.fluids.IFluidBlock
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import ic2.api.item.ElectricItem;
import ic2.api.item.IMiningDrill;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2Player;
import ic2.core.InvSlotConsumableBlock;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotConsumableId;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.BlockMiningPipe;
import ic2.core.block.machine.container.ContainerMiner;
import ic2.core.block.machine.gui.GuiMiner;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.init.MainConfig;
import ic2.core.init.OreValues;
import ic2.core.item.tool.ItemScanner;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;
import ic2.core.util.Ic2BlockPos;
import ic2.core.util.LiquidUtil;
import ic2.core.util.StackUtil;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMiner
extends TileEntityElectricMachine
implements IHasGui,
IUpgradableBlock {
    private Mode lastMode = Mode.None;
    public int progress = 0;
    private int scannedLevel = -1;
    private int scanRange = 0;
    private int lastX;
    private int lastZ;
    public boolean pumpMode = false;
    public boolean canProvideLiquid = false;
    public BlockPos liquidPos;
    private AudioSource audioSource;
    public final InvSlot buffer;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotConsumable drillSlot = new InvSlotConsumableClass(this, "drill", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP, IMiningDrill.class){

        @Override
        public boolean canOutput() {
            return !TileEntityMiner.this.tickingUpgrades && super.canOutput();
        }
    };
    public final InvSlotConsumable pipeSlot = new InvSlotConsumableBlock(this, "pipe", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP){

        @Override
        public boolean canOutput() {
            return !TileEntityMiner.this.tickingUpgrades && super.canOutput();
        }
    };
    public final InvSlotConsumable scannerSlot = new InvSlotConsumableId(this, "scanner", InvSlot.Access.IO, 1, InvSlot.InvSide.BOTTOM, new Item[]{ItemName.scanner.getInstance(), ItemName.advanced_scanner.getInstance()}){

        @Override
        public boolean canOutput() {
            return !TileEntityMiner.this.tickingUpgrades && super.canOutput();
        }
    };
    boolean tickingUpgrades = false;

    public TileEntityMiner() {
        super(1000, ConfigUtil.getInt(MainConfig.get(), "balance/minerDischargeTier"), false);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 1);
        this.buffer = new InvSlot(this, "buffer", InvSlot.Access.IO, 15, InvSlot.InvSide.SIDE);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        this.scannedLevel = -1;
        this.lastX = this.field_174879_c.func_177958_n();
        this.lastZ = this.field_174879_c.func_177952_p();
        this.canProvideLiquid = false;
    }

    @Override
    protected void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    @Override
    public void func_145839_a(NBTTagCompound nbtTagCompound) {
        super.func_145839_a(nbtTagCompound);
        this.lastMode = Mode.values()[nbtTagCompound.func_74762_e("lastMode")];
        this.progress = nbtTagCompound.func_74762_e("progress");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74768_a("lastMode", this.lastMode.ordinal());
        nbt.func_74768_a("progress", this.progress);
        return nbt;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.chargeTools();
        this.tickingUpgrades = true;
        this.upgradeSlot.tick();
        this.tickingUpgrades = false;
        if (this.work()) {
            this.func_70296_d();
            this.setActive(true);
        } else {
            this.setActive(false);
        }
    }

    private void chargeTools() {
        if (!this.scannerSlot.isEmpty()) {
            this.energy.useEnergy(ElectricItem.manager.charge(this.scannerSlot.get(), this.energy.getEnergy(), 2, false, false));
        }
        if (!this.drillSlot.isEmpty()) {
            this.energy.useEnergy(ElectricItem.manager.charge(this.drillSlot.get(), this.energy.getEnergy(), 3, false, false));
        }
    }

    private boolean work() {
        Ic2BlockPos operatingPos = this.getOperationPos();
        if (this.drillSlot.isEmpty()) {
            return this.withDrawPipe(operatingPos);
        }
        if (!operatingPos.isBelowMap()) {
            World world = this.func_145831_w();
            IBlockState state = world.func_180495_p((BlockPos)operatingPos);
            if (state != BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.tip)) {
                if (operatingPos.func_177956_o() > 0) {
                    return this.digDown(operatingPos, state, false);
                }
                return false;
            }
            MineResult result = this.mineLevel(operatingPos.func_177956_o());
            if (result == MineResult.Done) {
                operatingPos.moveDown();
                state = world.func_180495_p((BlockPos)operatingPos);
                return this.digDown(operatingPos, state, true);
            }
            return result == MineResult.Working;
        }
        return false;
    }

    private Ic2BlockPos getOperationPos() {
        Ic2BlockPos ret = new Ic2BlockPos((Vec3i)this.field_174879_c).moveDown();
        World world = this.func_145831_w();
        IBlockState pipeState = BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.pipe);
        while (!ret.isBelowMap()) {
            IBlockState state = ret.getBlockState((IBlockAccess)world);
            if (state != pipeState) {
                return ret;
            }
            ret.moveDown();
        }
        return ret;
    }

    private boolean withDrawPipe(Ic2BlockPos operatingPos) {
        if (this.lastMode != Mode.Withdraw) {
            this.lastMode = Mode.Withdraw;
            this.progress = 0;
        }
        if (operatingPos.isBelowMap() || this.func_145831_w().func_180495_p((BlockPos)operatingPos) != BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.tip)) {
            operatingPos.moveUp();
        }
        if (operatingPos.func_177956_o() != this.field_174879_c.func_177956_o() && this.energy.getEnergy() >= 3.0) {
            if (this.progress < 20) {
                this.energy.useEnergy(3.0);
                ++this.progress;
            } else {
                this.progress = 0;
                this.removePipe(operatingPos);
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removePipe(Ic2BlockPos operatingPos) {
        World world = this.func_145831_w();
        world.func_175698_g((BlockPos)operatingPos);
        this.storeDrop(BlockName.mining_pipe.getItemStack(BlockMiningPipe.MiningPipeType.pipe));
        ItemStack pipe = this.pipeSlot.consume(1, true, false);
        if (pipe != null && !StackUtil.checkItemEquality(pipe, BlockName.mining_pipe.getItemStack(BlockMiningPipe.MiningPipeType.pipe))) {
            ItemStack filler = this.pipeSlot.consume(1);
            Item fillerItem = filler.func_77973_b();
            EntityPlayer player = Ic2Player.get(world);
            player.func_184611_a(EnumHand.MAIN_HAND, filler);
            try {
                if (fillerItem instanceof ItemBlock) {
                    ((ItemBlock)fillerItem).func_180614_a(player, world, operatingPos.func_177984_a(), EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
                }
            }
            finally {
                player.func_184611_a(EnumHand.MAIN_HAND, StackUtil.emptyStack);
            }
        }
    }

    private boolean digDown(Ic2BlockPos operatingPos, IBlockState state, boolean removeTipAbove) {
        ItemStack pipe = this.pipeSlot.consume(1, true, false);
        if (pipe == null || !StackUtil.checkItemEquality(pipe, BlockName.mining_pipe.getItemStack(BlockMiningPipe.MiningPipeType.pipe))) {
            return false;
        }
        if (operatingPos.isBelowMap()) {
            if (removeTipAbove) {
                this.func_145831_w().func_175656_a((BlockPos)operatingPos.setY(0), BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.pipe));
            }
            return false;
        }
        MineResult result = this.mineBlock(operatingPos, state);
        if (result == MineResult.Failed_Temp || result == MineResult.Failed_Perm) {
            if (removeTipAbove) {
                this.func_145831_w().func_175656_a((BlockPos)operatingPos.moveUp(), BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.pipe));
            }
            return false;
        }
        if (result == MineResult.Done) {
            if (removeTipAbove) {
                this.func_145831_w().func_175656_a(operatingPos.func_177984_a(), BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.pipe));
            }
            this.pipeSlot.consume(1);
            this.func_145831_w().func_175656_a((BlockPos)operatingPos, BlockName.mining_pipe.getBlockState(BlockMiningPipe.MiningPipeType.tip));
        }
        return true;
    }

    private MineResult mineLevel(int y) {
        if (this.scannerSlot.isEmpty()) {
            return MineResult.Done;
        }
        if (this.scannedLevel != y) {
            this.scanRange = ((ItemScanner)this.scannerSlot.get().func_77973_b()).startLayerScan(this.scannerSlot.get());
        }
        if (this.scanRange > 0) {
            this.scannedLevel = y;
            BlockPos.MutableBlockPos target = new BlockPos.MutableBlockPos();
            World world = this.func_145831_w();
            EntityPlayer player = Ic2Player.get(world);
            for (int x = this.field_174879_c.func_177958_n() - this.scanRange; x <= this.field_174879_c.func_177958_n() + this.scanRange; ++x) {
                for (int z = this.field_174879_c.func_177952_p() - this.scanRange; z <= this.field_174879_c.func_177952_p() + this.scanRange; ++z) {
                    LiquidUtil.LiquidData liquid;
                    target.func_181079_c(x, y, z);
                    IBlockState state = world.func_180495_p((BlockPos)target);
                    boolean isValidTarget = false;
                    if ((OreValues.get(StackUtil.getDrops((IBlockAccess)world, (BlockPos)target, state, 0)) > 0 || OreValues.get(StackUtil.getPickStack(world, (BlockPos)target, state, player)) > 0) && this.canMine((BlockPos)target, state)) {
                        isValidTarget = true;
                    } else if (this.pumpMode && (liquid = LiquidUtil.getLiquid(world, (BlockPos)target)) != null && this.canPump((BlockPos)target)) {
                        isValidTarget = true;
                    }
                    if (!isValidTarget) continue;
                    MineResult result = this.mineTowards((BlockPos)target);
                    if (result == MineResult.Done) {
                        return MineResult.Working;
                    }
                    if (result == MineResult.Failed_Perm) continue;
                    return result;
                }
            }
            return MineResult.Done;
        }
        return MineResult.Failed_Temp;
    }

    private MineResult mineTowards(BlockPos dst) {
        int dx = Math.abs(dst.func_177958_n() - this.field_174879_c.func_177958_n());
        int sx = this.field_174879_c.func_177958_n() < dst.func_177958_n() ? 1 : -1;
        int dz = -Math.abs(dst.func_177952_p() - this.field_174879_c.func_177952_p());
        int sz = this.field_174879_c.func_177952_p() < dst.func_177952_p() ? 1 : -1;
        int err = dx + dz;
        BlockPos.MutableBlockPos target = new BlockPos.MutableBlockPos();
        int cx = this.field_174879_c.func_177958_n();
        int cz = this.field_174879_c.func_177952_p();
        while (cx != dst.func_177958_n() || cz != dst.func_177952_p()) {
            LiquidUtil.LiquidData liquid;
            boolean isCurrentPos = cx == this.lastX && cz == this.lastZ;
            int e2 = 2 * err;
            if (e2 > dz) {
                err += dz;
                cx += sx;
            } else if (e2 < dx) {
                err += dx;
                cz += sz;
            }
            target.func_181079_c(cx, dst.func_177956_o(), cz);
            World world = this.func_145831_w();
            IBlockState state = world.func_180495_p((BlockPos)target);
            boolean isBlocking = false;
            if (isCurrentPos) {
                isBlocking = true;
            } else if (!state.func_177230_c().isAir(state, (IBlockAccess)world, (BlockPos)target) && ((liquid = LiquidUtil.getLiquid(world, (BlockPos)target)) == null || liquid.isSource || this.pumpMode && this.canPump((BlockPos)target))) {
                isBlocking = true;
            }
            if (!isBlocking) continue;
            MineResult result = this.mineBlock((BlockPos)target, state);
            if (result == MineResult.Done) {
                this.lastX = cx;
                this.lastZ = cz;
            }
            return result;
        }
        this.lastX = this.field_174879_c.func_177958_n();
        this.lastZ = this.field_174879_c.func_177952_p();
        return MineResult.Done;
    }

    private MineResult mineBlock(BlockPos target, IBlockState state) {
        int duration;
        int energyPerTick;
        Mode mode;
        World world = this.func_145831_w();
        Block block = state.func_177230_c();
        boolean isAirBlock = true;
        if (!block.isAir(state, (IBlockAccess)world, target)) {
            isAirBlock = false;
            LiquidUtil.LiquidData liquidData = LiquidUtil.getLiquid(world, target);
            if (liquidData != null) {
                if (liquidData.isSource || this.pumpMode && this.canPump(target)) {
                    this.liquidPos = new BlockPos((Vec3i)target);
                    this.canProvideLiquid = true;
                    return this.pumpMode || this.canMine(target, state) ? MineResult.Failed_Temp : MineResult.Failed_Perm;
                }
            } else if (!this.canMine(target, state)) {
                return MineResult.Failed_Perm;
            }
        }
        this.canProvideLiquid = false;
        if (isAirBlock) {
            mode = Mode.MineAir;
            energyPerTick = 3;
            duration = 20;
        } else if (this.drillSlot.get().func_77973_b() == ItemName.drill.getInstance()) {
            mode = Mode.MineDrill;
            energyPerTick = 6;
            duration = 200;
        } else if (this.drillSlot.get().func_77973_b() == ItemName.diamond_drill.getInstance()) {
            mode = Mode.MineDDrill;
            energyPerTick = 20;
            duration = 50;
        } else if (this.drillSlot.get().func_77973_b() == ItemName.iridium_drill.getInstance()) {
            mode = Mode.MineIDrill;
            energyPerTick = 200;
            duration = 20;
        } else if (this.drillSlot.get().func_77973_b() instanceof IMiningDrill) {
            mode = Mode.MineCustomDrill;
            IMiningDrill drill = (IMiningDrill)this.drillSlot.get().func_77973_b();
            energyPerTick = drill.energyUse(this.drillSlot.get(), world, target, state);
            duration = drill.breakTime(this.drillSlot.get(), world, target, state);
        } else {
            throw new IllegalStateException("invalid drill: " + this.drillSlot.get());
        }
        if (this.lastMode != mode) {
            this.lastMode = mode;
            this.progress = 0;
        }
        if (this.progress < duration) {
            if (this.energy.useEnergy(energyPerTick)) {
                ++this.progress;
                return MineResult.Working;
            }
        } else if (isAirBlock || this.harvestBlock(target, state)) {
            this.progress = 0;
            return MineResult.Done;
        }
        return MineResult.Failed_Temp;
    }

    private boolean harvestBlock(BlockPos target, IBlockState state) {
        int energyCost = 2 * (this.field_174879_c.func_177956_o() - target.func_177956_o());
        if (this.energy.getEnergy() < (double)energyCost) {
            return false;
        }
        World world = this.func_145831_w();
        switch (this.lastMode) {
            case MineDrill: {
                if (ElectricItem.manager.use(this.drillSlot.get(), 50.0, null)) break;
                return false;
            }
            case MineDDrill: {
                if (ElectricItem.manager.use(this.drillSlot.get(), 80.0, null)) break;
                return false;
            }
            case MineIDrill: {
                if (ElectricItem.manager.use(this.drillSlot.get(), 800.0, null)) break;
                return false;
            }
            case MineCustomDrill: {
                if (((IMiningDrill)this.drillSlot.get().func_77973_b()).breakBlock(this.drillSlot.get(), world, target, state)) break;
                return false;
            }
            default: {
                throw new IllegalStateException("Invalid mode " + (Object)((Object)this.lastMode) + " with drill: " + this.drillSlot.get());
            }
        }
        this.energy.useEnergy(energyCost);
        for (ItemStack drop : StackUtil.getDrops((IBlockAccess)world, target, state, this.lastMode == Mode.MineIDrill ? 3 : 0)) {
            this.storeDrop(drop);
        }
        world.func_175698_g(target);
        return true;
    }

    private void storeDrop(ItemStack stack) {
        if (StackUtil.putInInventory((TileEntity)this, EnumFacing.WEST, stack, true) == 0) {
            StackUtil.dropAsEntity(this.func_145831_w(), this.field_174879_c, stack);
        } else {
            StackUtil.putInInventory((TileEntity)this, EnumFacing.WEST, stack, false);
        }
    }

    public boolean canPump(BlockPos target) {
        return false;
    }

    public boolean canMine(BlockPos target, IBlockState state) {
        Block block = state.func_177230_c();
        if (block.isAir(state, (IBlockAccess)this.func_145831_w(), target)) {
            return true;
        }
        if (block == BlockName.mining_pipe.getInstance() || block == Blocks.field_150486_ae) {
            return false;
        }
        if (block instanceof IFluidBlock && this.isPumpConnected(target)) {
            return true;
        }
        if ((block == Blocks.field_150355_j || block == Blocks.field_150358_i || block == Blocks.field_150353_l || block == Blocks.field_150356_k) && this.isPumpConnected(target)) {
            return true;
        }
        World world = this.func_145831_w();
        if (state.func_185887_b(world, target) < 0.0f) {
            return false;
        }
        if (block.func_176209_a(state, false) && state.func_185904_a().func_76229_l()) {
            return true;
        }
        if (block == Blocks.field_150321_G) {
            return true;
        }
        if (!this.drillSlot.isEmpty()) {
            return ForgeHooks.canToolHarvestBlock((IBlockAccess)world, (BlockPos)target, (ItemStack)this.drillSlot.get()) || this.drillSlot.get().func_150998_b(state);
        }
        return false;
    }

    public boolean isPumpConnected(BlockPos target) {
        World world = this.func_145831_w();
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(te instanceof TileEntityPump) || ((TileEntityPump)te).pump(target, true, this) == null) continue;
            return true;
        }
        return false;
    }

    public boolean isAnyPumpConnected() {
        World world = this.func_145831_w();
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(te instanceof TileEntityPump)) continue;
            return true;
        }
        return false;
    }

    public ContainerBase<TileEntityMiner> getGuiContainer(EntityPlayer player) {
        return new ContainerMiner(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiMiner(new ContainerMiner(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public void onNetworkUpdate(String field) {
        if (field.equals("active")) {
            if (this.audioSource == null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/MinerOp.ogg", true, false, IC2.audioManager.getDefaultVolume());
            }
            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }
        super.onNetworkUpdate(field);
    }

    @Override
    public double getEnergy() {
        return this.energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return this.energy.useEnergy(amount);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

    static enum MineResult {
        Working,
        Done,
        Failed_Temp,
        Failed_Perm;

    }

    static enum Mode {
        None,
        Withdraw,
        MineAir,
        MineDrill,
        MineDDrill,
        MineIDrill,
        MineCustomDrill;

    }
}

