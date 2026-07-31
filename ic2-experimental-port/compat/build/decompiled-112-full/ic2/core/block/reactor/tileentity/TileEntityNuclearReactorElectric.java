/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.world.ChunkCache
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidTank
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.logging.log4j.Level
 */
package ic2.core.block.reactor.tileentity;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMetaDelegate;
import ic2.api.reactor.IBaseReactorComponent;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.api.reactor.IReactorComponent;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.IC2DamageSource;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotReactor;
import ic2.core.block.reactor.container.ContainerNuclearReactor;
import ic2.core.block.reactor.gui.GuiNuclearReactor;
import ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorRedstonePort;
import ic2.core.block.type.ResourceBlock;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.item.reactor.ItemReactorHeatStorage;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.util.WorldSearchUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.Level;

public class TileEntityNuclearReactorElectric
extends TileEntityInventory
implements IHasGui,
IReactor,
IEnergySource,
IMetaDelegate,
IGuiValueProvider {
    public AudioSource audioSourceMain;
    public AudioSource audioSourceGeiger;
    private float lastOutput = 0.0f;
    public final Fluids.InternalFluidTank inputTank;
    public final Fluids.InternalFluidTank outputTank;
    private final List<IEnergyTile> subTiles = new ArrayList<IEnergyTile>();
    public final InvSlotReactor reactorSlot;
    public final InvSlotOutput coolantoutputSlot;
    public final InvSlotOutput hotcoolantoutputSlot;
    public final InvSlotConsumableLiquidByManager coolantinputSlot;
    public final InvSlotConsumableLiquidByTank hotcoolinputSlot;
    public final Redstone redstone;
    protected final Fluids fluids;
    public float output = 0.0f;
    public int updateTicker = IC2.random.nextInt(this.getTickRate());
    public int heat = 0;
    public int maxHeat = 10000;
    public float hem = 1.0f;
    private int EmitHeatbuffer = 0;
    public int EmitHeat = 0;
    private boolean fluidCooled = false;
    public boolean addedToEnergyNet = false;
    private static final float huOutputModifier = 40.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/FluidReactor/outputModifier");

    public TileEntityNuclearReactorElectric() {
        this.fluids = this.addComponent(new Fluids(this));
        this.inputTank = this.fluids.addTank("inputTank", 10000, InvSlot.Access.NONE, InvSlot.InvSide.ANY, Fluids.fluidPredicate(Recipes.liquidHeatupManager));
        this.outputTank = this.fluids.addTank("outputTank", 10000, InvSlot.Access.NONE);
        this.reactorSlot = new InvSlotReactor(this, "reactor", 54);
        this.coolantinputSlot = new InvSlotConsumableLiquidByManager(this, "coolantinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Drain, Recipes.liquidHeatupManager);
        this.hotcoolinputSlot = new InvSlotConsumableLiquidByTank(this, "hotcoolinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
        this.coolantoutputSlot = new InvSlotOutput(this, "coolantoutputSlot", 1);
        this.hotcoolantoutputSlot = new InvSlotOutput(this, "hotcoolantoutputSlot", 1);
        this.redstone = this.addComponent(new Redstone(this));
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145831_w().field_72995_K && !this.isFluidCooled()) {
            this.refreshChambers();
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
        this.createChamberRedstoneLinks();
        if (this.isFluidCooled()) {
            this.createCasingRedstoneLinks();
            this.openTanks();
        }
    }

    @Override
    protected void onUnloaded() {
        if (IC2.platform.isRendering()) {
            IC2.audioManager.removeSources(this);
            this.audioSourceMain = null;
            this.audioSourceGeiger = null;
        }
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }
        super.onUnloaded();
    }

    public int gaugeHeatScaled(int i) {
        return i * this.heat / (this.maxHeat / 100 * 85);
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.heat = nbt.func_74762_e("heat");
        this.output = nbt.func_74765_d("output");
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        nbt = super.func_189515_b(nbt);
        nbt.func_74768_a("heat", this.heat);
        nbt.func_74777_a("output", (short)this.getReactorEnergyOutput());
        return nbt;
    }

    @Override
    protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
        if (this.addedToEnergyNet) {
            this.refreshChambers();
        }
    }

    @Override
    public void drawEnergy(double amount) {
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing direction) {
        return true;
    }

    @Override
    public double getOfferedEnergy() {
        return this.getReactorEnergyOutput() * 5.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear");
    }

    @Override
    public int getSourceTier() {
        return 5;
    }

    @Override
    public double getReactorEUEnergyOutput() {
        return this.getOfferedEnergy();
    }

    @Override
    public List<IEnergyTile> getSubTiles() {
        return Collections.unmodifiableList(new ArrayList<IEnergyTile>(this.subTiles));
    }

    private void processfluidsSlots() {
        this.coolantinputSlot.processIntoTank((IFluidTank)this.inputTank, this.coolantoutputSlot);
        this.hotcoolinputSlot.processFromTank((IFluidTank)this.outputTank, this.hotcoolantoutputSlot);
    }

    public void refreshChambers() {
        World world = this.func_145831_w();
        ArrayList<TileEntityBlock> newSubTiles = new ArrayList<TileEntityBlock>();
        newSubTiles.add(this);
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(te instanceof TileEntityReactorChamberElectric) || te.func_145837_r()) continue;
            newSubTiles.add((TileEntityReactorChamberElectric)te);
        }
        if (!newSubTiles.equals(this.subTiles)) {
            if (this.addedToEnergyNet) {
                MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
            }
            this.subTiles.clear();
            this.subTiles.addAll(newSubTiles);
            if (this.addedToEnergyNet) {
                MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
            }
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.updateTicker++ % this.getTickRate() != 0) {
            return;
        }
        if (!this.func_145831_w().func_175697_a(this.field_174879_c, 8)) {
            this.output = 0.0f;
        } else {
            boolean toFluidCooled = this.isFluidReactor();
            if (this.fluidCooled != toFluidCooled) {
                if (toFluidCooled) {
                    this.enableFluidMode();
                } else {
                    this.disableFluidMode();
                }
                this.fluidCooled = toFluidCooled;
            }
            this.dropAllUnfittingStuff();
            this.output = 0.0f;
            this.maxHeat = 10000;
            this.hem = 1.0f;
            this.processChambers();
            if (this.fluidCooled) {
                this.processfluidsSlots();
                FluidStack inputFluid = this.inputTank.getFluid();
                assert (inputFluid == null || Recipes.liquidHeatupManager.acceptsFluid(this.inputTank.getFluid().getFluid()));
                int huOtput = (int)(huOutputModifier * (float)this.EmitHeatbuffer);
                int outputroom = this.outputTank.getCapacity() - this.outputTank.getFluidAmount();
                this.EmitHeatbuffer = 0;
                if (outputroom > 0 && inputFluid != null) {
                    ILiquidHeatExchangerManager.HeatExchangeProperty prop = Recipes.liquidHeatupManager.getHeatExchangeProperty(inputFluid.getFluid());
                    int fluidOutput = huOtput / prop.huPerMB;
                    FluidStack add = new FluidStack(prop.outputFluid, fluidOutput);
                    if (this.outputTank.canFillFluidType(add)) {
                        FluidStack draincoolant;
                        if (fluidOutput < outputroom) {
                            this.EmitHeatbuffer = (int)((float)(huOtput % prop.huPerMB) / huOutputModifier);
                            this.EmitHeat = (int)((float)huOtput / huOutputModifier);
                            draincoolant = this.inputTank.drainInternal(fluidOutput, false);
                        } else {
                            this.EmitHeat = outputroom * prop.huPerMB;
                            draincoolant = this.inputTank.drainInternal(outputroom, false);
                        }
                        if (draincoolant != null) {
                            this.EmitHeat = draincoolant.amount * prop.huPerMB;
                            huOtput -= this.inputTank.drainInternal((int)draincoolant.amount, (boolean)true).amount * prop.huPerMB;
                            this.outputTank.fillInternal(new FluidStack(prop.outputFluid, draincoolant.amount), true);
                        } else {
                            this.EmitHeat = 0;
                        }
                    }
                } else {
                    this.EmitHeat = 0;
                }
                this.addHeat((int)((float)huOtput / huOutputModifier));
            }
            if (this.calculateHeatEffects()) {
                return;
            }
            this.setActive(this.heat >= 1000 || this.output > 0.0f);
            this.func_70296_d();
        }
        IC2.network.get(true).updateTileEntityField(this, "output");
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        TileEntityNuclearReactorElectric.showHeatEffects(this.func_145831_w(), this.field_174879_c, this.heat);
    }

    public static void showHeatEffects(World world, BlockPos pos, int heat) {
        Random rnd = world.field_73012_v;
        if (rnd.nextInt(8) != 0) {
            return;
        }
        int puffs = heat / 1000;
        if (puffs > 0) {
            int n;
            puffs = rnd.nextInt(puffs);
            for (n = 0; n < puffs; ++n) {
                world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (double)((float)pos.func_177958_n() + rnd.nextFloat()), (double)((float)pos.func_177956_o() + 0.95f), (double)((float)pos.func_177952_p() + rnd.nextFloat()), 0.0, 0.0, 0.0, new int[0]);
            }
            puffs -= rnd.nextInt(4) + 3;
            for (n = 0; n < puffs; ++n) {
                world.func_175688_a(EnumParticleTypes.FLAME, (double)((float)pos.func_177958_n() + rnd.nextFloat()), (double)(pos.func_177956_o() + 1), (double)((float)pos.func_177952_p() + rnd.nextFloat()), 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }

    public void dropAllUnfittingStuff() {
        ItemStack stack;
        int i;
        for (i = 0; i < this.reactorSlot.size(); ++i) {
            stack = this.reactorSlot.get(i);
            if (stack == null || this.isUsefulItem(stack, false)) continue;
            this.reactorSlot.put(i, null);
            this.eject(stack);
        }
        for (i = this.reactorSlot.size(); i < this.reactorSlot.rawSize(); ++i) {
            stack = this.reactorSlot.get(i);
            this.reactorSlot.put(i, null);
            this.eject(stack);
        }
    }

    public boolean isUsefulItem(ItemStack stack, boolean forInsertion) {
        Item item = stack.func_77973_b();
        if (item == null) {
            return false;
        }
        if (forInsertion && this.fluidCooled && item.getClass() == ItemReactorHeatStorage.class && ((ItemReactorHeatStorage)item).getCustomDamage(stack) > 0) {
            return false;
        }
        return item instanceof IBaseReactorComponent && (!forInsertion || ((IBaseReactorComponent)item).canBePlacedIn(stack, this));
    }

    public void eject(ItemStack drop) {
        if (!IC2.platform.isSimulating() || drop == null) {
            return;
        }
        StackUtil.dropAsEntity(this.func_145831_w(), this.field_174879_c, drop);
    }

    public boolean calculateHeatEffects() {
        Material mat;
        Object state;
        BlockPos coord;
        if (this.heat < 4000 || !IC2.platform.isSimulating() || ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit") <= 0.0f) {
            return false;
        }
        float power = (float)this.heat / (float)this.maxHeat;
        if (power >= 1.0f) {
            this.explode();
            return true;
        }
        World world = this.func_145831_w();
        if (power >= 0.85f && world.field_73012_v.nextFloat() <= 0.2f * this.hem) {
            coord = this.getRandCoord(2);
            state = world.func_180495_p(coord);
            Block block = state.func_177230_c();
            if (block.isAir((IBlockState)state, (IBlockAccess)world, coord)) {
                world.func_175656_a(coord, Blocks.field_150480_ab.func_176223_P());
            } else if (state.func_185887_b(world, coord) >= 0.0f && world.func_175625_s(coord) == null) {
                Material mat2 = state.func_185904_a();
                if (mat2 == Material.field_151576_e || mat2 == Material.field_151573_f || mat2 == Material.field_151587_i || mat2 == Material.field_151578_c || mat2 == Material.field_151571_B) {
                    world.func_175656_a(coord, Blocks.field_150356_k.func_176223_P());
                } else {
                    world.func_175656_a(coord, Blocks.field_150480_ab.func_176223_P());
                }
            }
        }
        if (power >= 0.7f) {
            List nearByEntities = world.func_72872_a(EntityLivingBase.class, new AxisAlignedBB((double)(this.field_174879_c.func_177958_n() - 3), (double)(this.field_174879_c.func_177956_o() - 3), (double)(this.field_174879_c.func_177952_p() - 3), (double)(this.field_174879_c.func_177958_n() + 4), (double)(this.field_174879_c.func_177956_o() + 4), (double)(this.field_174879_c.func_177952_p() + 4)));
            state = nearByEntities.iterator();
            while (state.hasNext()) {
                EntityLivingBase entity = (EntityLivingBase)state.next();
                entity.func_70097_a((DamageSource)IC2DamageSource.radiation, (float)((int)((float)world.field_73012_v.nextInt(4) * this.hem)));
            }
        }
        if (power >= 0.5f && world.field_73012_v.nextFloat() <= this.hem && (state = world.func_180495_p(coord = this.getRandCoord(2))).func_185904_a() == Material.field_151586_h) {
            world.func_175698_g(coord);
        }
        if (power >= 0.4f && world.field_73012_v.nextFloat() <= this.hem && world.func_175625_s(coord = this.getRandCoord(2)) == null && ((mat = (state = world.func_180495_p(coord)).func_185904_a()) == Material.field_151575_d || mat == Material.field_151584_j || mat == Material.field_151580_n)) {
            world.func_175656_a(coord, Blocks.field_150480_ab.func_176223_P());
        }
        return false;
    }

    public BlockPos getRandCoord(int radius) {
        BlockPos ret;
        if (radius <= 0) {
            return null;
        }
        World world = this.func_145831_w();
        while ((ret = this.field_174879_c.func_177982_a(world.field_73012_v.nextInt(2 * radius + 1) - radius, world.field_73012_v.nextInt(2 * radius + 1) - radius, world.field_73012_v.nextInt(2 * radius + 1) - radius)).equals((Object)this.field_174879_c)) {
        }
        return ret;
    }

    public void processChambers() {
        int size = this.getReactorSize();
        for (int pass = 0; pass < 2; ++pass) {
            for (int y = 0; y < 6; ++y) {
                for (int x = 0; x < size; ++x) {
                    ItemStack stack = this.reactorSlot.get(x, y);
                    if (stack == null || !(stack.func_77973_b() instanceof IReactorComponent)) continue;
                    IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
                    comp.processChamber(stack, this, x, y, pass == 0);
                }
            }
        }
    }

    @Override
    public boolean produceEnergy() {
        return this.redstone.hasRedstoneInput() && ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear") > 0.0f;
    }

    public int getReactorSize() {
        World world = this.func_145831_w();
        if (world == null) {
            return 9;
        }
        int cols = 3;
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(target instanceof TileEntityReactorChamberElectric)) continue;
            ++cols;
        }
        return cols;
    }

    private boolean isFullSize() {
        return this.getReactorSize() == 9;
    }

    @Override
    public int getTickRate() {
        return 20;
    }

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (StackUtil.checkItemEquality(StackUtil.get(player, hand), BlockName.te.getItemStack(TeBlock.reactor_chamber))) {
            return false;
        }
        return super.onActivated(player, hand, side, hitX, hitY, hitZ);
    }

    public ContainerBase<TileEntityNuclearReactorElectric> getGuiContainer(EntityPlayer player) {
        return new ContainerNuclearReactor(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiNuclearReactor(new ContainerNuclearReactor(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    @Override
    public void onNetworkUpdate(String field) {
        if (field.equals("output")) {
            if (this.output > 0.0f) {
                if (this.lastOutput <= 0.0f) {
                    if (this.audioSourceMain == null) {
                        this.audioSourceMain = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/NuclearReactorLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }
                    if (this.audioSourceMain != null) {
                        this.audioSourceMain.play();
                    }
                }
                if (this.output < 40.0f) {
                    if (this.lastOutput <= 0.0f || this.lastOutput >= 40.0f) {
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.remove();
                        }
                        this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerLowEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.play();
                        }
                    }
                } else if (this.output < 80.0f) {
                    if (this.lastOutput < 40.0f || this.lastOutput >= 80.0f) {
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.remove();
                        }
                        this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerMedEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.play();
                        }
                    }
                } else if (this.output >= 80.0f && this.lastOutput < 80.0f) {
                    if (this.audioSourceGeiger != null) {
                        this.audioSourceGeiger.remove();
                    }
                    this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerHighEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    if (this.audioSourceGeiger != null) {
                        this.audioSourceGeiger.play();
                    }
                }
            } else if (this.lastOutput > 0.0f) {
                if (this.audioSourceMain != null) {
                    this.audioSourceMain.stop();
                }
                if (this.audioSourceGeiger != null) {
                    this.audioSourceGeiger.stop();
                }
            }
            this.lastOutput = this.output;
        }
        super.onNetworkUpdate(field);
    }

    @Override
    public TileEntity getCoreTe() {
        return this;
    }

    @Override
    public BlockPos getPosition() {
        return this.field_174879_c;
    }

    @Override
    public World getWorldObj() {
        return this.func_145831_w();
    }

    @Override
    public int getHeat() {
        return this.heat;
    }

    @Override
    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public int addHeat(int amount) {
        this.heat += amount;
        return this.heat;
    }

    @Override
    public ItemStack getItemAt(int x, int y) {
        if (x < 0 || x >= this.getReactorSize() || y < 0 || y >= 6) {
            return null;
        }
        return this.reactorSlot.get(x, y);
    }

    @Override
    public void setItemAt(int x, int y, ItemStack item) {
        if (x < 0 || x >= this.getReactorSize() || y < 0 || y >= 6) {
            return;
        }
        this.reactorSlot.put(x, y, item);
    }

    @Override
    public void explode() {
        float boomPower = 10.0f;
        float boomMod = 1.0f;
        for (int i = 0; i < this.reactorSlot.size(); ++i) {
            EnumFacing[] stack = this.reactorSlot.get(i);
            if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
                float f = ((IReactorComponent)stack.func_77973_b()).influenceExplosion((ItemStack)stack, this);
                if (f > 0.0f && f < 1.0f) {
                    boomMod *= f;
                } else {
                    boomPower += f;
                }
            }
            this.reactorSlot.put(i, null);
        }
        IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Nuclear Reactor at %s melted (raw explosion power %f)", Util.formatPosition(this), Float.valueOf(boomPower *= this.hem * boomMod));
        boomPower = Math.min(boomPower, ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit"));
        World world = this.func_145831_w();
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
            if (!(target instanceof TileEntityReactorChamberElectric)) continue;
            world.func_175698_g(target.func_174877_v());
        }
        world.func_175698_g(this.field_174879_c);
        ExplosionIC2 explosion = new ExplosionIC2(world, null, this.field_174879_c, boomPower, 0.01f, ExplosionIC2.Type.Nuclear);
        explosion.doExplosion();
    }

    @Override
    public void addEmitHeat(int heat) {
        this.EmitHeatbuffer += heat;
    }

    @Override
    public int getMaxHeat() {
        return this.maxHeat;
    }

    @Override
    public void setMaxHeat(int newMaxHeat) {
        this.maxHeat = newMaxHeat;
    }

    @Override
    public float getHeatEffectModifier() {
        return this.hem;
    }

    @Override
    public void setHeatEffectModifier(float newHEM) {
        this.hem = newHEM;
    }

    @Override
    public float getReactorEnergyOutput() {
        return this.output;
    }

    @Override
    public float addOutput(float energy) {
        return this.output += energy;
    }

    @Override
    public boolean isFluidCooled() {
        return this.fluidCooled;
    }

    private void createChamberRedstoneLinks() {
        World world = this.func_145831_w();
        for (EnumFacing facing : EnumFacing.field_82609_l) {
            BlockPos cPos = this.field_174879_c.func_177972_a(facing);
            TileEntity te = world.func_175625_s(cPos);
            if (!(te instanceof TileEntityReactorChamberElectric)) continue;
            TileEntityReactorChamberElectric chamber = (TileEntityReactorChamberElectric)te;
            if (chamber.redstone.isLinked() && chamber.redstone.getLinkReceiver() != this.redstone) {
                chamber.destoryChamber(true);
                continue;
            }
            chamber.redstone.linkTo(this.redstone);
        }
    }

    private void createCasingRedstoneLinks() {
        WorldSearchUtil.findTileEntities(this.func_145831_w(), this.field_174879_c, 2, new WorldSearchUtil.ITileEntityResultHandler(){

            @Override
            public boolean onMatch(TileEntity te) {
                if (te instanceof TileEntityReactorRedstonePort) {
                    ((TileEntityReactorRedstonePort)te).redstone.linkTo(TileEntityNuclearReactorElectric.this.redstone);
                }
                return false;
            }
        });
    }

    private void removeCasingRedstoneLinks() {
        for (Redstone rs : this.redstone.getLinkedOrigins()) {
            if (!(rs.getParent() instanceof TileEntityReactorRedstonePort)) continue;
            rs.unlinkOutbound();
        }
    }

    private void enableFluidMode() {
        if (this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }
        this.createCasingRedstoneLinks();
        this.openTanks();
    }

    private void disableFluidMode() {
        if (!this.addedToEnergyNet) {
            this.refreshChambers();
            MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
        this.removeCasingRedstoneLinks();
        this.closeTanks();
    }

    private void openTanks() {
        this.fluids.changeConnectivity(this.inputTank, InvSlot.Access.I, InvSlot.InvSide.ANY);
        this.fluids.changeConnectivity(this.outputTank, InvSlot.Access.O, InvSlot.InvSide.ANY);
    }

    private void closeTanks() {
        this.fluids.changeConnectivity(this.inputTank, InvSlot.Access.NONE, InvSlot.InvSide.ANY);
        this.fluids.changeConnectivity(this.outputTank, InvSlot.Access.NONE, InvSlot.InvSide.ANY);
    }

    private boolean isFluidReactor() {
        if (!this.isFullSize()) {
            return false;
        }
        if (!this.hasFluidChamber()) {
            return false;
        }
        int range = 2;
        final MutableBoolean foundConflict = new MutableBoolean();
        WorldSearchUtil.findTileEntities(this.func_145831_w(), this.field_174879_c, 4, new WorldSearchUtil.ITileEntityResultHandler(){

            @Override
            public boolean onMatch(TileEntity te) {
                if (!(te instanceof TileEntityNuclearReactorElectric)) {
                    return false;
                }
                if (te == TileEntityNuclearReactorElectric.this) {
                    return false;
                }
                TileEntityNuclearReactorElectric reactor = (TileEntityNuclearReactorElectric)te;
                if (reactor.isFullSize() && reactor.hasFluidChamber()) {
                    foundConflict.setTrue();
                    return true;
                }
                return false;
            }
        });
        return foundConflict.getValue() == false;
    }

    private boolean hasFluidChamber() {
        int y;
        int x;
        int i;
        int range = 2;
        ChunkCache cache = new ChunkCache(this.func_145831_w(), this.field_174879_c.func_177982_a(-2, -2, -2), this.field_174879_c.func_177982_a(2, 2, 2), 0);
        BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
        for (i = 0; i < 2; ++i) {
            int y2 = this.field_174879_c.func_177956_o() + 2 * (i * 2 - 1);
            for (int z = this.field_174879_c.func_177952_p() - 2; z <= this.field_174879_c.func_177952_p() + 2; ++z) {
                for (x = this.field_174879_c.func_177958_n() - 2; x <= this.field_174879_c.func_177958_n() + 2; ++x) {
                    cPos.func_181079_c(x, y2, z);
                    if (TileEntityNuclearReactorElectric.isFluidChamberBlock((IBlockAccess)cache, (BlockPos)cPos)) continue;
                    return false;
                }
            }
        }
        for (i = 0; i < 2; ++i) {
            int z = this.field_174879_c.func_177952_p() + 2 * (i * 2 - 1);
            for (y = this.field_174879_c.func_177956_o() - 2 + 1; y <= this.field_174879_c.func_177956_o() + 2 - 1; ++y) {
                for (x = this.field_174879_c.func_177958_n() - 2; x <= this.field_174879_c.func_177958_n() + 2; ++x) {
                    cPos.func_181079_c(x, y, z);
                    if (TileEntityNuclearReactorElectric.isFluidChamberBlock((IBlockAccess)cache, (BlockPos)cPos)) continue;
                    return false;
                }
            }
        }
        for (i = 0; i < 2; ++i) {
            int x2 = this.field_174879_c.func_177958_n() + 2 * (i * 2 - 1);
            for (y = this.field_174879_c.func_177956_o() - 2 + 1; y <= this.field_174879_c.func_177956_o() + 2 - 1; ++y) {
                for (int z = this.field_174879_c.func_177952_p() - 2 + 1; z <= this.field_174879_c.func_177952_p() + 2 - 1; ++z) {
                    cPos.func_181079_c(x2, y, z);
                    if (TileEntityNuclearReactorElectric.isFluidChamberBlock((IBlockAccess)cache, (BlockPos)cPos)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isFluidChamberBlock(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.func_180495_p(pos);
        if (state == BlockName.resource.getBlockState(ResourceBlock.reactor_vessel)) {
            return true;
        }
        TileEntity te = world.func_175625_s(pos);
        if (te == null) {
            return false;
        }
        return te instanceof IReactorChamber && ((IReactorChamber)te).isWall();
    }

    @Override
    public double getGuiValue(String name) {
        if ("heat".equals(name)) {
            return this.maxHeat == 0 ? 0.0 : (double)this.heat / (double)this.maxHeat;
        }
        throw new IllegalArgumentException("Invalid value: " + name);
    }

    public int gaugeLiquidScaled(int i, int tank) {
        switch (tank) {
            case 0: {
                if (this.inputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * i / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    public FluidTank getinputtank() {
        return this.inputTank;
    }

    public FluidTank getoutputtank() {
        return this.outputTank;
    }

    @Override
    public int func_70297_j_() {
        return 1;
    }
}

