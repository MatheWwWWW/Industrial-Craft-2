/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.reactor.IChamberReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorProduct;
import ic2.api.util.DirectionList;
import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.special.HeatComparator;
import ic2.core.block.base.misc.readers.IHeatProvider;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.generators.containers.ReactorContainer;
import ic2.core.entity.explosion.IC2Explosion;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.handler.slots.ReactorSlots;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.MathUtils;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class BaseNuclearReactorTileEntity
extends BaseInventoryTileEntity
implements ITickListener,
IChamberReactor,
ITileGui,
IHeatProvider,
IWrenchableTile,
ITileActivityProvider {
    public static final int[][] SLOT_ARRAY = BaseNuclearReactorTileEntity.createArray();
    @NetworkInfo
    public float output;
    @NetworkInfo
    public int heat;
    @NetworkInfo
    public int maxHeat = 10000;
    public float hem = 1.0f;
    public int size = 3;
    protected boolean refreshChambers = true;
    protected boolean inventoryChanged = false;
    IAudioSource mainSource;
    IAudioSource[] subSources = new IAudioSource[3];

    public BaseNuclearReactorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 54);
        this.addNetworkFields("heat", "maxHeat", "output");
        this.addComparator(new HeatComparator("heat", ComparatorNames.HEAT, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        int[] slots = MathUtils.fromTo(0, 54);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerCustomSlot(new ReactorSlots(this));
        handler.registerInputFilter(this::isUsefulItem, slots);
        handler.registerSlotAccess(AccessRule.BOTH, slots);
        handler.registerNamedSlot(SlotType.REACTOR, slots);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putFloat(compound, "output", this.output, 0.0f);
        NBTUtils.putInt(compound, "heat", this.heat, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.output = compound.m_128457_("output");
        this.heat = compound.m_128451_("heat");
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.mainSource = null;
        int i = 0;
        while (i < 3) {
            this.subSources[i++] = null;
        }
        IC2.AUDIO.removeSource(this);
        super.onUnloaded(chunk);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("output")) {
            if (this.mainSource == null || !this.mainSource.isValid()) {
                this.mainSource = this.getSounds().createSource(this, IC2Sounds.REACTOR_PROCESSING);
            }
            for (int i = 0; i < 3; ++i) {
                if (this.subSources[i] != null && this.subSources[i].isValid()) continue;
                this.subSources[i] = this.getSounds().createSource(this, this.getGeigerSound(i));
            }
            this.playStop(this.mainSource, this.output > 0.0f);
            this.playStop(this.subSources[0], this.output > 0.0f && this.output < 40.0f);
            this.playStop(this.subSources[1], this.output >= 40.0f && this.output < 80.0f);
            this.playStop(this.subSources[2], this.output >= 80.0f);
        }
    }

    protected ResourceLocation getGeigerSound(int level) {
        switch (level) {
            case 1: {
                return IC2Sounds.REACTOR_MEDIUM;
            }
            case 2: {
                return IC2Sounds.REACTOR_HIGH;
            }
        }
        return IC2Sounds.REACTOR_LOW;
    }

    @Override
    public boolean isActivated() {
        return this.isProducingEnergy();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ReactorContainer(this, player, windowID);
    }

    static int[][] createArray() {
        int[][] array = new int[10][];
        for (int size = 1; size < 10; ++size) {
            array[size] = new int[6 * size];
            int slot = 0;
            for (int y = 0; y < 6; ++y) {
                for (int x = 0; x < size; ++x) {
                    array[size][slot] = x + y * 9;
                    ++slot;
                }
            }
        }
        return array;
    }

    @Override
    public int getTickRate() {
        return 20;
    }

    public boolean isDamageTick() {
        return true;
    }

    @Override
    protected boolean needsInitialRedstoneCheck() {
        return true;
    }

    @Override
    public void onTick() {
        this.handleComparators();
        if (this.f_58857_.m_46467_() % (long)this.getTickRate() != 0L) {
            return;
        }
        if (!this.isAreaLoaded(1)) {
            this.output = 0.0f;
            this.updateTileField("output");
            return;
        }
        this.handleRedstone();
        this.dropUselessItems();
        this.output = 0.0f;
        this.maxHeat = 10000;
        this.hem = 1.0f;
        this.processChambers();
        if (this.calculateHeatEffects()) {
            return;
        }
        this.setActive(this.heat >= 1000 || this.output > 0.0f);
        this.updateTileFields("heat", "output", "maxHeat");
    }

    @Override
    protected boolean handleComparators(boolean ignoreTick) {
        boolean result = super.handleComparators(ignoreTick);
        if (result) {
            for (Direction dir : DirectionList.ALL) {
                this.f_58857_.m_46717_(this.m_58899_().m_121945_(dir), this.m_58900_().m_60734_());
            }
        }
        return result;
    }

    @Override
    public void handleRedstone() {
        if (this.needsRedstoneUpdate && this.sensitive) {
            this.needsRedstoneUpdate = false;
            if (this.directionalSignal) {
                byte max = 0;
                for (Direction dir : DirectionList.ALL) {
                    byte strength = (byte)Mth.m_14045_((int)this.f_58857_.m_46681_(this.m_58899_(), dir), (int)0, (int)15);
                    max = (byte)Math.max(max, strength);
                    this.sidedSignals[dir.m_122411_()] = strength;
                }
                this.signal = max;
                return;
            }
            this.signal = (byte)Mth.m_14045_((int)this.f_58857_.m_46755_(this.f_58858_), (int)0, (int)15);
            for (Direction dir : DirectionList.ALL) {
                BlockEntity tile = DirectionList.getNeighborTile(this, dir);
                if (!this.isValidChamber(tile)) continue;
                this.signal = (byte)Math.max(this.signal, Mth.m_14045_((int)this.f_58857_.m_46755_(tile.m_58899_()), (int)0, (int)15));
            }
        }
    }

    public void processChambers() {
        int size = this.getWidth();
        boolean isDamageTick = this.isDamageTick();
        for (int pass = 0; pass < 2; ++pass) {
            for (int y = 0; y < 6; ++y) {
                for (int x = 0; x < size; ++x) {
                    ItemStack thing = this.getStackInReactor(x, y);
                    if (!(thing.m_41720_() instanceof IReactorComponent)) continue;
                    IReactorComponent comp = (IReactorComponent)thing.m_41720_();
                    comp.processChamber(thing, this, x, y, pass == 0, pass == 1 && isDamageTick);
                }
            }
        }
    }

    public boolean calculateHeatEffects() {
        Material mat;
        BlockState state;
        Object nextPos;
        if (this.heat < 4000) {
            return false;
        }
        if (IC2.CONFIG.reactorDamage.get() <= 0.0) {
            return false;
        }
        float power = (float)this.heat / (float)this.maxHeat;
        if (power >= 1.0f) {
            this.explode();
            return true;
        }
        RandomSource rand = this.f_58857_.f_46441_;
        if (power >= 0.85f && rand.m_188501_() <= 0.2f * this.hem && (nextPos = this.getRandomPos(rand, 2)) != null) {
            state = this.f_58857_.m_8055_((BlockPos)nextPos);
            if (state.m_60734_() == Blocks.f_50016_) {
                this.f_58857_.m_46597_((BlockPos)nextPos, Blocks.f_50083_.m_49966_());
            } else {
                float hardness = state.m_60800_((BlockGetter)this.f_58857_, (BlockPos)nextPos);
                if (hardness >= 0.0f && hardness <= 10.0f && this.f_58857_.m_7702_(this.f_58858_) == null) {
                    Material mat2 = state.m_60767_();
                    if (mat2 == Material.f_76278_ || mat2 == Material.f_76279_ || mat2 == Material.f_76307_ || mat2 == Material.f_76314_ || mat2 == Material.f_76313_) {
                        this.f_58857_.m_46597_((BlockPos)nextPos, ((FluidState)Fluids.f_76194_.m_76145_().m_61124_((Property)BlockStateProperties.f_61420_, (Comparable)Integer.valueOf(8))).m_76188_());
                    } else {
                        this.f_58857_.m_46597_((BlockPos)nextPos, Blocks.f_50083_.m_49966_());
                    }
                }
            }
        }
        if (power >= 7.0f) {
            nextPos = this.f_58857_.m_45976_(LivingEntity.class, new AABB(this.f_58858_).m_82400_(3.0)).iterator();
            while (nextPos.hasNext()) {
                LivingEntity entity = (LivingEntity)nextPos.next();
                entity.m_6469_((DamageSource)IC2DamageSource.RADIATION, (float)rand.m_188503_(4) * this.hem);
            }
        }
        if (power >= 5.0f && rand.m_188501_() <= this.hem && (nextPos = this.getRandomPos(rand, 2)) != null) {
            state = this.f_58857_.m_8055_((BlockPos)nextPos);
            Fluid fluid = state.m_60819_().m_76152_();
            if (state.m_60734_() instanceof BucketPickup && fluid == Fluids.f_76193_ || fluid == Fluids.f_76192_) {
                ((BucketPickup)state.m_60734_()).m_142598_((LevelAccessor)this.m_58904_(), (BlockPos)nextPos, state);
            }
        }
        if (power >= 4.0f && rand.m_188501_() <= this.hem && (nextPos = this.getRandomPos(rand, 2)) != null && ((mat = this.f_58857_.m_8055_((BlockPos)nextPos).m_60767_()) == Material.f_76320_ || mat == Material.f_76274_ || mat == Material.f_76272_)) {
            this.f_58857_.m_46597_((BlockPos)nextPos, Blocks.f_50083_.m_49966_());
        }
        return false;
    }

    public BlockPos getRandomPos(RandomSource rand, int radius) {
        int z;
        int y;
        if (radius <= 0) {
            return null;
        }
        int x = rand.m_188503_(2 * radius + 1) - radius;
        BlockPos nextPos = this.f_58858_.m_7918_(x, y = rand.m_188503_(2 * radius + 1) - radius, z = rand.m_188503_(2 * radius + 1) - radius);
        if (this.f_58858_.equals((Object)nextPos)) {
            return null;
        }
        return nextPos;
    }

    public void dropUselessItems() {
        int size = this.getWidth();
        if (!this.inventoryChanged) {
            return;
        }
        this.inventoryChanged = false;
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 6; ++y) {
                ItemStack stack = this.getStackInReactor(x, y);
                if (stack.m_41619_() || x < size && this.isUsefulItem(stack)) continue;
                this.eject(stack);
                this.setStackInReactor(x, y, ItemStack.f_41583_);
            }
        }
    }

    public boolean isUsefulItem(ItemStack stack) {
        if (stack.m_41619_()) {
            return false;
        }
        Item item = stack.m_41720_();
        if (item instanceof IReactorComponent) {
            return true;
        }
        if (item instanceof IReactorProduct) {
            return ((IReactorProduct)item).isValidForReactor(stack, this);
        }
        return false;
    }

    @Override
    public int getHeat() {
        return this.heat;
    }

    @Override
    public void setHeat(int newHeat) {
        this.heat = newHeat;
    }

    @Override
    public void addHeat(int heat) {
        this.heat += heat;
    }

    @Override
    public int getMaxHeat() {
        return this.maxHeat;
    }

    @Override
    public void setMaxHeat(int heat) {
        this.maxHeat = heat;
    }

    @Override
    public float getHeatEffectModifier() {
        return this.hem;
    }

    @Override
    public void setHeatEffectModifier(float hem) {
        this.hem = hem;
    }

    @Override
    public double getEnergyOutput() {
        return this.output;
    }

    @Override
    public void addOutput(float output) {
        this.output += output;
    }

    @Override
    public ItemStack getStackInReactor(int x, int y) {
        if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight()) {
            return ItemStack.f_41583_;
        }
        return this.getStackInSlot(y * 9 + x);
    }

    @Override
    public void setStackInReactor(int x, int y, ItemStack stack) {
        if (x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight()) {
            return;
        }
        this.setStackInSlot(y * 9 + x, stack);
    }

    @Override
    public void explode() {
        float boomPower = 10.0f;
        float boomMod = 1.0f;
        int m = this.getHeight();
        for (int y = 0; y < m; ++y) {
            int n = this.getWidth();
            for (int x = 0; x < n; ++x) {
                ItemStack stack = this.getStackInReactor(x, y);
                if (stack.m_41720_() instanceof IReactorComponent) {
                    float f = ((IReactorComponent)stack.m_41720_()).getExplosionInfluence(stack, this);
                    if (f > 0.0f && f < 1.0f) {
                        boomMod *= f;
                    } else {
                        boomPower += f;
                    }
                }
                this.setStackInReactor(x, y, ItemStack.f_41583_);
            }
        }
        IC2.LOGGER.info("Nuclear Reactor at " + this.f_58857_.m_46472_().m_135782_() + ":(" + this.f_58858_.m_123341_() + "," + this.f_58858_.m_123342_() + "," + this.f_58858_.m_123343_() + ") melted (explosion power " + (boomPower *= this.hem * boomMod) + ")");
        boomPower = (float)Math.min((double)boomPower, IC2.CONFIG.reactorDamage.get());
        for (Direction direction : DirectionList.ALL) {
            BlockEntity target = DirectionList.getNeighborTile(this, direction);
            if (!(target instanceof IReactorChamber)) continue;
            this.f_58857_.m_7471_(this.f_58858_, false);
        }
        this.f_58857_.m_7471_(this.f_58858_, false);
        IC2Explosion explosion = new IC2Explosion(this.f_58857_, null, Vec3.m_82528_((Vec3i)this.f_58858_), boomPower, 0.01f, IC2DamageSource.NUKE);
        explosion.doExplosion();
    }

    @Override
    public boolean isProducingEnergy() {
        return this.isRedstonePowered();
    }

    @Override
    public int getHeight() {
        return 6;
    }

    @Override
    public int getWidth() {
        this.onChamberRefresh();
        return this.size;
    }

    public int[] getSlotArray() {
        return SLOT_ARRAY[this.getWidth()];
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.75;
    }

    @Override
    public void refreshChambers() {
        this.refreshChambers = true;
    }

    public void onChamberRefresh() {
        if (!this.refreshChambers) {
            return;
        }
        this.refreshChambers = false;
        int lastSize = this.size;
        this.size = 3;
        for (Direction dir : DirectionList.ALL) {
            BlockEntity tile = DirectionList.getNeighborTile(this, dir);
            if (!this.isValidChamber(tile)) continue;
            ++this.size;
        }
        if (lastSize != this.size) {
            this.inventoryChanged = true;
            if (this.size < lastSize) {
                for (int x = this.size; x < 9; ++x) {
                    for (int y = 0; y < 6; ++y) {
                        ItemStack stack = this.getStackInSlot(y * 9 + x);
                        if (stack.m_41619_()) continue;
                        this.eject(stack);
                        this.setStackInSlot(y * 9 + x, ItemStack.f_41583_);
                    }
                }
            }
        }
    }

    public void eject(ItemStack stack) {
        if (this.isSimulating()) {
            Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)stack);
        }
    }

    public boolean isValidChamber(BlockEntity tile) {
        return tile instanceof IReactorChamber;
    }
}

