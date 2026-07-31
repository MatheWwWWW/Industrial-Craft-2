/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.PathNavigationRegion
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.logging.log4j.Level
 */
package ic2.core.block.reactor.tileentity;

import ic2.api.energy.EnergyNet;
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
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2DamageSource;
import ic2.core.Ic2Explosion;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotReactor;
import ic2.core.block.reactor.container.ContainerNuclearReactor;
import ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric;
import ic2.core.block.reactor.tileentity.TileEntityReactorRedstonePort;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.MainConfig;
import ic2.core.item.reactor.ItemReactorHeatStorage;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.sound.Sound;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.util.WorldUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class TileEntityNuclearReactorElectric
extends TileEntityInventory
implements IHasGui,
IReactor,
IEnergySource,
IMetaDelegate,
IGuiValueProvider {
    public Sound soundMain;
    public Sound soundGeiger;
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
    public int updateTicker = IC2.random.m_188503_(this.getTickRate());
    public int heat = 0;
    public int maxHeat = 10000;
    public float hem = 1.0f;
    private int EmitHeatbuffer = 0;
    public int EmitHeat = 0;
    private boolean fluidCooled = false;
    public boolean addedToEnergyNet = false;
    private static final float huOutputModifier = 40.0f * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/FluidReactor/outputModifier");

    public TileEntityNuclearReactorElectric(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.NUCLEAR_REACTOR, blockPos, blockState);
        this.fluids = this.addComponent(new Fluids(this));
        this.inputTank = this.fluids.addTank("inputTank", 10000, InvSlot.Access.NONE, InvSlot.InvSide.ANY, Fluids.fluidPredicate(Recipes.liquidHeatupManager));
        this.outputTank = this.fluids.addTank("outputTank", 10000, InvSlot.Access.NONE);
        this.reactorSlot = new InvSlotReactor(this, "reactor", 54);
        this.coolantinputSlot = new InvSlotConsumableLiquidByManager(this, "coolantinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Drain, Recipes.liquidHeatupManager);
        this.hotcoolinputSlot = new InvSlotConsumableLiquidByTank(this, "hotcoolinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Fill, this.outputTank);
        this.coolantoutputSlot = new InvSlotOutput(this, "coolantoutputSlot", 1);
        this.hotcoolantoutputSlot = new InvSlotOutput(this, "hotcoolantoutputSlot", 1);
        this.redstone = this.addComponent(new Redstone(this));
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.m_58904_().f_46443_ && !this.isFluidCooled()) {
            this.refreshChambers();
            EnergyNet.instance.addBlockEntityTile(this);
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
        if (IC2.sideProxy.isRendering()) {
            IC2.soundManager.removeAllSound(this);
            this.soundMain = null;
            this.soundGeiger = null;
        }
        if (IC2.sideProxy.isSimulating() && this.addedToEnergyNet) {
            EnergyNet.instance.removeTile(this);
            this.addedToEnergyNet = false;
        }
        super.onUnloaded();
    }

    public int gaugeHeatScaled(int n) {
        return n * this.heat / (this.maxHeat / 100 * 85);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        this.heat = compoundTag.m_128451_("heat");
        this.output = compoundTag.m_128448_("output");
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        compoundTag.m_128405_("heat", this.heat);
        compoundTag.m_128376_("output", (short)this.getReactorEnergyOutput());
    }

    @Override
    protected void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        if (this.addedToEnergyNet) {
            this.refreshChambers();
        }
    }

    @Override
    public void drawEnergy(double d) {
    }

    @Override
    public boolean emitsEnergyTo(IEnergyAcceptor iEnergyAcceptor, Direction direction) {
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
        this.coolantinputSlot.processIntoTank(this.inputTank, this.coolantoutputSlot);
        this.hotcoolinputSlot.processFromTank(this.outputTank, this.hotcoolantoutputSlot);
    }

    public void refreshChambers() {
        Level level = this.m_58904_();
        ArrayList<Ic2TileEntity> arrayList = new ArrayList<Ic2TileEntity>();
        arrayList.add(this);
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof TileEntityReactorChamberElectric) || blockEntity.m_58901_()) continue;
            arrayList.add((TileEntityReactorChamberElectric)blockEntity);
        }
        if (!arrayList.equals(this.subTiles)) {
            if (this.addedToEnergyNet) {
                EnergyNet.instance.removeTile(this);
            }
            this.subTiles.clear();
            this.subTiles.addAll(arrayList);
            if (this.addedToEnergyNet) {
                EnergyNet.instance.addBlockEntityTile(this);
            }
        }
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (this.updateTicker++ % this.getTickRate() != 0) {
            return;
        }
        if (!Util.isAreaLoaded((LevelReader)this.m_58904_(), this.f_58858_, 8)) {
            this.output = 0.0f;
        } else {
            boolean bl = this.isFluidReactor();
            if (this.fluidCooled != bl) {
                if (bl) {
                    this.enableFluidMode();
                } else {
                    this.disableFluidMode();
                }
                this.fluidCooled = bl;
            }
            this.dropAllUnfittingStuff();
            this.output = 0.0f;
            this.maxHeat = 10000;
            this.hem = 1.0f;
            this.processChambers();
            if (this.fluidCooled) {
                this.processfluidsSlots();
                Ic2FluidStack ic2FluidStack = this.inputTank.getFluidStack();
                assert (ic2FluidStack == null || Recipes.liquidHeatupManager.acceptsFluid(this.inputTank.getFluidStack().getFluid()));
                int n = (int)(huOutputModifier * (float)this.EmitHeatbuffer);
                int n2 = this.outputTank.getCapacity() - this.outputTank.getFluidAmount();
                this.EmitHeatbuffer = 0;
                if (n2 > 0 && ic2FluidStack != null) {
                    Ic2FluidStack ic2FluidStack2;
                    ILiquidHeatExchangerManager.HeatExchangeProperty heatExchangeProperty = Recipes.liquidHeatupManager.getHeatExchangeProperty(ic2FluidStack.getFluid());
                    int n3 = n / heatExchangeProperty.huPerMB;
                    if (n3 < n2) {
                        this.EmitHeatbuffer = (int)((float)(n % heatExchangeProperty.huPerMB) / huOutputModifier);
                        this.EmitHeat = (int)((float)n / huOutputModifier);
                        ic2FluidStack2 = this.inputTank.drainMbUnchecked(n3, true);
                    } else {
                        this.EmitHeat = n2 * heatExchangeProperty.huPerMB;
                        ic2FluidStack2 = this.inputTank.drainMbUnchecked(n2, true);
                    }
                    if (ic2FluidStack2 != null) {
                        this.EmitHeat = ic2FluidStack2.getAmountMb() * heatExchangeProperty.huPerMB;
                        n -= this.inputTank.drainMbUnchecked(ic2FluidStack2.getAmountMb(), false).getAmountMb() * heatExchangeProperty.huPerMB;
                        this.outputTank.fillMbUnchecked(Ic2FluidStack.create(heatExchangeProperty.outputFluid, ic2FluidStack2.getAmountMb()), false);
                    } else {
                        this.EmitHeat = 0;
                    }
                } else {
                    this.EmitHeat = 0;
                }
                this.addHeat((int)((float)n / huOutputModifier));
            }
            if (this.calculateHeatEffects()) {
                return;
            }
            this.setActive(this.heat >= 1000 || this.output > 0.0f);
            this.m_6596_();
        }
        IC2.network.get(true).updateTileEntityField(this, "output");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected void updateEntityClient() {
        super.updateEntityClient();
        TileEntityNuclearReactorElectric.showHeatEffects(this.m_58904_(), this.f_58858_, this.heat);
    }

    public static void showHeatEffects(Level level, BlockPos blockPos, int n) {
        RandomSource randomSource = level.f_46441_;
        if (randomSource.m_188503_(8) != 0) {
            return;
        }
        int n2 = n / 1000;
        if (n2 > 0) {
            int n3;
            n2 = randomSource.m_188503_(n2);
            for (n3 = 0; n3 < n2; ++n3) {
                level.m_7106_((ParticleOptions)ParticleTypes.f_123762_, (double)((float)blockPos.m_123341_() + randomSource.m_188501_()), (double)((float)blockPos.m_123342_() + 0.95f), (double)((float)blockPos.m_123343_() + randomSource.m_188501_()), 0.0, 0.0, 0.0);
            }
            n2 -= randomSource.m_188503_(4) + 3;
            for (n3 = 0; n3 < n2; ++n3) {
                level.m_7106_((ParticleOptions)ParticleTypes.f_123744_, (double)((float)blockPos.m_123341_() + randomSource.m_188501_()), (double)(blockPos.m_123342_() + 1), (double)((float)blockPos.m_123343_() + randomSource.m_188501_()), 0.0, 0.0, 0.0);
            }
        }
    }

    public void dropAllUnfittingStuff() {
        ItemStack itemStack;
        int n;
        for (n = 0; n < this.reactorSlot.size(); ++n) {
            itemStack = this.reactorSlot.get(n);
            if (itemStack == null || this.isUsefulItem(itemStack, false)) continue;
            this.reactorSlot.put(n, null);
            this.eject(itemStack);
        }
        for (n = this.reactorSlot.size(); n < this.reactorSlot.rawSize(); ++n) {
            itemStack = this.reactorSlot.get(n);
            this.reactorSlot.put(n, null);
            this.eject(itemStack);
        }
    }

    public boolean isUsefulItem(ItemStack itemStack, boolean bl) {
        Item item = itemStack.m_41720_();
        if (item == null) {
            return false;
        }
        if (bl && this.fluidCooled && item.getClass() == ItemReactorHeatStorage.class && itemStack.m_41773_() > 0) {
            return false;
        }
        return item instanceof IBaseReactorComponent && (!bl || ((IBaseReactorComponent)item).canBePlacedIn(itemStack, this));
    }

    public void eject(ItemStack itemStack) {
        if (!IC2.sideProxy.isSimulating() || itemStack == null) {
            return;
        }
        StackUtil.dropAsEntity(this.m_58904_(), this.f_58858_, itemStack);
    }

    public boolean calculateHeatEffects() {
        Block block;
        Object object;
        Object object2;
        if (this.heat < 4000 || !IC2.sideProxy.isSimulating() || ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit") <= 0.0f) {
            return false;
        }
        float f = (float)this.heat / (float)this.maxHeat;
        if (f >= 1.0f) {
            this.explode();
            return true;
        }
        Level level = this.m_58904_();
        if (f >= 0.85f && level.f_46441_.m_188501_() <= 0.2f * this.hem) {
            object2 = this.getRandCoord(2);
            object = level.m_8055_((BlockPos)object2);
            block = object.m_60734_();
            if (object.m_60795_()) {
                level.m_46597_((BlockPos)object2, Blocks.f_50083_.m_49966_());
            } else if (object.m_60800_((BlockGetter)level, (BlockPos)object2) >= 0.0f && level.m_7702_((BlockPos)object2) == null) {
                Material material = object.m_60767_();
                if (material == Material.f_76278_ || material == Material.f_76279_ || material == Material.f_76307_ || material == Material.f_76314_ || material == Material.f_76313_) {
                    level.m_46597_((BlockPos)object2, net.minecraft.world.level.material.Fluids.f_76194_.m_76145_().m_76188_());
                } else {
                    level.m_46597_((BlockPos)object2, Blocks.f_50083_.m_49966_());
                }
            }
        }
        if (f >= 0.7f) {
            object2 = level.m_6443_(LivingEntity.class, new AABB((double)(this.f_58858_.m_123341_() - 3), (double)(this.f_58858_.m_123342_() - 3), (double)(this.f_58858_.m_123343_() - 3), (double)(this.f_58858_.m_123341_() + 4), (double)(this.f_58858_.m_123342_() + 4), (double)(this.f_58858_.m_123343_() + 4)), null);
            object = object2.iterator();
            while (object.hasNext()) {
                block = (LivingEntity)object.next();
                block.m_6469_((DamageSource)Ic2DamageSource.radiation, (float)((int)((float)level.f_46441_.m_188503_(4) * this.hem)));
            }
        }
        if (f >= 0.5f && level.f_46441_.m_188501_() <= this.hem && (object = level.m_8055_((BlockPos)(object2 = this.getRandCoord(2)))).m_60767_() == Material.f_76305_) {
            level.m_7471_((BlockPos)object2, false);
        }
        if (f >= 0.4f && level.f_46441_.m_188501_() <= this.hem && level.m_7702_((BlockPos)(object2 = this.getRandCoord(2))) == null && ((block = (object = level.m_8055_((BlockPos)object2)).m_60767_()) == Material.f_76320_ || block == Material.f_76274_ || block == Material.f_76272_)) {
            level.m_46597_((BlockPos)object2, Blocks.f_50083_.m_49966_());
        }
        return false;
    }

    public BlockPos getRandCoord(int n) {
        BlockPos blockPos;
        if (n <= 0) {
            return null;
        }
        Level level = this.m_58904_();
        while ((blockPos = this.f_58858_.m_7918_(level.f_46441_.m_188503_(2 * n + 1) - n, level.f_46441_.m_188503_(2 * n + 1) - n, level.f_46441_.m_188503_(2 * n + 1) - n)).equals((Object)this.f_58858_)) {
        }
        return blockPos;
    }

    public void processChambers() {
        int n = this.getReactorSize();
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 6; ++j) {
                for (int k = 0; k < n; ++k) {
                    ItemStack itemStack = this.reactorSlot.get(k, j);
                    if (itemStack == null || !(itemStack.m_41720_() instanceof IReactorComponent)) continue;
                    IReactorComponent iReactorComponent = (IReactorComponent)itemStack.m_41720_();
                    iReactorComponent.processChamber(itemStack, this, k, j, i == 0);
                }
            }
        }
    }

    @Override
    public boolean produceEnergy() {
        return this.redstone.hasRedstoneInput() && ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear") > 0.0f;
    }

    public int getReactorSize() {
        Level level = this.m_58904_();
        if (level == null) {
            return 9;
        }
        int n = 3;
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof TileEntityReactorChamberElectric)) continue;
            ++n;
        }
        return n;
    }

    private boolean isFullSize() {
        return this.getReactorSize() == 9;
    }

    @Override
    public int getTickRate() {
        return 20;
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        if (StackUtil.checkItemEquality(StackUtil.get(player, interactionHand), new ItemStack((ItemLike)Ic2Items.REACTOR_CHAMBER))) {
            return InteractionResult.PASS;
        }
        return super.onActivated(player, interactionHand, direction, vec3);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerNuclearReactor(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerNuclearReactor(n, inventory, this);
    }

    @Override
    public void onNetworkUpdate(String string) {
        if (string.equals("output")) {
            if (this.output > 0.0f) {
                if (this.lastOutput <= 0.0f) {
                    if (this.soundMain == null) {
                        this.soundMain = IC2.soundManager.createSound((Object)this, Ic2SoundEvents.GENERATOR_NUCLEAR_LOOP, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
                    }
                    if (this.soundMain != null) {
                        this.soundMain.play();
                    }
                }
                if (this.output < 40.0f) {
                    if (this.lastOutput <= 0.0f || this.lastOutput >= 40.0f) {
                        if (this.soundGeiger != null) {
                            IC2.soundManager.removeSound(this, this.soundGeiger);
                        }
                        this.soundGeiger = IC2.soundManager.createSound((Object)this, Ic2SoundEvents.GENERATOR_NUCLEAR_LOW_POWER, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
                        if (this.soundGeiger != null) {
                            this.soundGeiger.play();
                        }
                    }
                } else if (this.output < 80.0f) {
                    if (this.lastOutput < 40.0f || this.lastOutput >= 80.0f) {
                        if (this.soundGeiger != null) {
                            IC2.soundManager.removeSound(this, this.soundGeiger);
                        }
                        this.soundGeiger = IC2.soundManager.createSound((Object)this, Ic2SoundEvents.GENERATOR_NUCLEAR_MEDIUM_POWER, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
                        if (this.soundGeiger != null) {
                            this.soundGeiger.play();
                        }
                    }
                } else if (this.output >= 80.0f && this.lastOutput < 80.0f) {
                    if (this.soundGeiger != null) {
                        IC2.soundManager.removeSound(this, this.soundGeiger);
                    }
                    this.soundGeiger = IC2.soundManager.createSound((Object)this, Ic2SoundEvents.GENERATOR_NUCLEAR_HIGH_POWER, SoundSource.BLOCKS, this.m_58899_(), 1.0f, 1.0f);
                    if (this.soundGeiger != null) {
                        this.soundGeiger.play();
                    }
                }
            } else if (this.lastOutput > 0.0f) {
                if (this.soundMain != null) {
                    this.soundMain.stop();
                }
                if (this.soundGeiger != null) {
                    this.soundGeiger.stop();
                }
            }
            this.lastOutput = this.output;
        }
        super.onNetworkUpdate(string);
    }

    @Override
    public BlockEntity getCoreTe() {
        return this;
    }

    @Override
    public BlockPos getPosition() {
        return this.f_58858_;
    }

    @Override
    public Level getWorldObj() {
        return this.m_58904_();
    }

    @Override
    public int getHeat() {
        return this.heat;
    }

    @Override
    public void setHeat(int n) {
        this.heat = n;
    }

    @Override
    public int addHeat(int n) {
        this.heat += n;
        return this.heat;
    }

    @Override
    public ItemStack getItemAt(int n, int n2) {
        if (n < 0 || n >= this.getReactorSize() || n2 < 0 || n2 >= 6) {
            return null;
        }
        return this.reactorSlot.get(n, n2);
    }

    @Override
    public void setItemAt(int n, int n2, ItemStack itemStack) {
        if (n < 0 || n >= this.getReactorSize() || n2 < 0 || n2 >= 6) {
            return;
        }
        this.reactorSlot.put(n, n2, itemStack);
    }

    @Override
    public void explode() {
        Object object;
        float f = 10.0f;
        float f2 = 1.0f;
        for (int i = 0; i < this.reactorSlot.size(); ++i) {
            object = this.reactorSlot.get(i);
            if (object != null && object.m_41720_() instanceof IReactorComponent) {
                float f3 = ((IReactorComponent)object.m_41720_()).influenceExplosion((ItemStack)object, this);
                if (f3 > 0.0f && f3 < 1.0f) {
                    f2 *= f3;
                } else {
                    f += f3;
                }
            }
            this.reactorSlot.put(i, null);
        }
        IC2.log.log(LogCategory.PlayerActivity, org.apache.logging.log4j.Level.INFO, "Nuclear Reactor at %s melted (raw explosion power %f)", Util.formatPosition(this), Float.valueOf(f *= this.hem * f2));
        f = Math.min(f, ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit"));
        Level level = this.m_58904_();
        for (Direction direction : Util.ALL_DIRS) {
            BlockEntity blockEntity = level.m_7702_(this.f_58858_.m_121945_(direction));
            if (!(blockEntity instanceof TileEntityReactorChamberElectric)) continue;
            level.m_7471_(blockEntity.m_58899_(), false);
        }
        level.m_7471_(this.f_58858_, false);
        object = new Ic2Explosion(level, null, this.f_58858_, f, 0.01f, Ic2Explosion.Type.Nuclear);
        ((Ic2Explosion)((Object)object)).doExplosion();
    }

    @Override
    public void addEmitHeat(int n) {
        this.EmitHeatbuffer += n;
    }

    @Override
    public int getMaxHeat() {
        return this.maxHeat;
    }

    @Override
    public void setMaxHeat(int n) {
        this.maxHeat = n;
    }

    @Override
    public float getHeatEffectModifier() {
        return this.hem;
    }

    @Override
    public void setHeatEffectModifier(float f) {
        this.hem = f;
    }

    @Override
    public float getReactorEnergyOutput() {
        return this.output;
    }

    @Override
    public float addOutput(float f) {
        return this.output += f;
    }

    @Override
    public boolean isFluidCooled() {
        return this.fluidCooled;
    }

    private void createChamberRedstoneLinks() {
        Level level = this.m_58904_();
        for (Direction direction : Util.ALL_DIRS) {
            BlockPos blockPos = this.f_58858_.m_121945_(direction);
            BlockEntity blockEntity = level.m_7702_(blockPos);
            if (!(blockEntity instanceof TileEntityReactorChamberElectric)) continue;
            TileEntityReactorChamberElectric tileEntityReactorChamberElectric = (TileEntityReactorChamberElectric)blockEntity;
            if (tileEntityReactorChamberElectric.redstone.isLinked() && tileEntityReactorChamberElectric.redstone.getLinkReceiver() != this.redstone) {
                tileEntityReactorChamberElectric.destoryChamber(true);
                continue;
            }
            tileEntityReactorChamberElectric.redstone.linkTo(this.redstone);
        }
    }

    private void createCasingRedstoneLinks() {
        WorldUtil.findTileEntities(this.m_58904_(), this.f_58858_, 2, new WorldUtil.ITileEntityResultHandler(){

            @Override
            public boolean onMatch(BlockEntity blockEntity) {
                if (blockEntity instanceof TileEntityReactorRedstonePort) {
                    ((TileEntityReactorRedstonePort)blockEntity).redstone.linkTo(TileEntityNuclearReactorElectric.this.redstone);
                }
                return false;
            }
        });
    }

    private void removeCasingRedstoneLinks() {
        for (Redstone redstone : this.redstone.getLinkedOrigins()) {
            if (!(redstone.getParent() instanceof TileEntityReactorRedstonePort)) continue;
            redstone.unlinkOutbound();
        }
    }

    private void enableFluidMode() {
        if (this.addedToEnergyNet) {
            EnergyNet.instance.removeTile(this);
            this.addedToEnergyNet = false;
        }
        this.createCasingRedstoneLinks();
        this.openTanks();
    }

    private void disableFluidMode() {
        if (!this.addedToEnergyNet) {
            this.refreshChambers();
            EnergyNet.instance.addBlockEntityTile(this);
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
        final MutableBoolean mutableBoolean = new MutableBoolean();
        WorldUtil.findTileEntities(this.m_58904_(), this.f_58858_, 4, new WorldUtil.ITileEntityResultHandler(){

            @Override
            public boolean onMatch(BlockEntity blockEntity) {
                if (!(blockEntity instanceof TileEntityNuclearReactorElectric)) {
                    return false;
                }
                if (blockEntity == TileEntityNuclearReactorElectric.this) {
                    return false;
                }
                TileEntityNuclearReactorElectric tileEntityNuclearReactorElectric = (TileEntityNuclearReactorElectric)blockEntity;
                if (tileEntityNuclearReactorElectric.isFullSize() && tileEntityNuclearReactorElectric.hasFluidChamber()) {
                    mutableBoolean.setTrue();
                    return true;
                }
                return false;
            }
        });
        return mutableBoolean.getValue() == false;
    }

    private boolean hasFluidChamber() {
        int n;
        int n2;
        int n3;
        int n4;
        PathNavigationRegion pathNavigationRegion = new PathNavigationRegion(this.m_58904_(), this.f_58858_.m_7918_(-2, -2, -2), this.f_58858_.m_7918_(2, 2, 2));
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (n4 = 0; n4 < 2; ++n4) {
            n3 = this.f_58858_.m_123342_() + 2 * (n4 * 2 - 1);
            for (n2 = this.f_58858_.m_123343_() - 2; n2 <= this.f_58858_.m_123343_() + 2; ++n2) {
                for (n = this.f_58858_.m_123341_() - 2; n <= this.f_58858_.m_123341_() + 2; ++n) {
                    mutableBlockPos.m_122178_(n, n3, n2);
                    if (TileEntityNuclearReactorElectric.isFluidChamberBlock((BlockGetter)pathNavigationRegion, (BlockPos)mutableBlockPos)) continue;
                    return false;
                }
            }
        }
        for (n4 = 0; n4 < 2; ++n4) {
            n3 = this.f_58858_.m_123343_() + 2 * (n4 * 2 - 1);
            for (n2 = this.f_58858_.m_123342_() - 2 + 1; n2 <= this.f_58858_.m_123342_() + 2 - 1; ++n2) {
                for (n = this.f_58858_.m_123341_() - 2; n <= this.f_58858_.m_123341_() + 2; ++n) {
                    mutableBlockPos.m_122178_(n, n2, n3);
                    if (TileEntityNuclearReactorElectric.isFluidChamberBlock((BlockGetter)pathNavigationRegion, (BlockPos)mutableBlockPos)) continue;
                    return false;
                }
            }
        }
        for (n4 = 0; n4 < 2; ++n4) {
            n3 = this.f_58858_.m_123341_() + 2 * (n4 * 2 - 1);
            for (n2 = this.f_58858_.m_123342_() - 2 + 1; n2 <= this.f_58858_.m_123342_() + 2 - 1; ++n2) {
                for (n = this.f_58858_.m_123343_() - 2 + 1; n <= this.f_58858_.m_123343_() + 2 - 1; ++n) {
                    mutableBlockPos.m_122178_(n3, n2, n);
                    if (TileEntityNuclearReactorElectric.isFluidChamberBlock((BlockGetter)pathNavigationRegion, (BlockPos)mutableBlockPos)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isFluidChamberBlock(BlockGetter blockGetter, BlockPos blockPos) {
        BlockState blockState = blockGetter.m_8055_(blockPos);
        if (blockState.m_60734_() == Ic2Blocks.REACTOR_VESSEL) {
            return true;
        }
        BlockEntity blockEntity = blockGetter.m_7702_(blockPos);
        if (blockEntity == null) {
            return false;
        }
        return blockEntity instanceof IReactorChamber && ((IReactorChamber)blockEntity).isWall();
    }

    @Override
    public double getGuiValue(String string) {
        if ("heat".equals(string)) {
            return this.maxHeat == 0 ? 0.0 : (double)this.heat / (double)this.maxHeat;
        }
        throw new IllegalArgumentException("Invalid value: " + string);
    }

    public int gaugeLiquidScaled(int n, int n2) {
        switch (n2) {
            case 0: {
                if (this.inputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.inputTank.getFluidAmount() * n / this.inputTank.getCapacity();
            }
            case 1: {
                if (this.outputTank.getFluidAmount() <= 0) {
                    return 0;
                }
                return this.outputTank.getFluidAmount() * n / this.outputTank.getCapacity();
            }
        }
        return 0;
    }

    public Ic2FluidTank getinputtank() {
        return this.inputTank;
    }

    public Ic2FluidTank getoutputtank() {
        return this.outputTank;
    }

    @Override
    public int m_6893_() {
        return 1;
    }
}

