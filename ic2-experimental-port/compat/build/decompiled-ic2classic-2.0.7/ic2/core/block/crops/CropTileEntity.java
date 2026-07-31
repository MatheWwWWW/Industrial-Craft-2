/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BubbleColumnBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.core.block.crops;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.CropDifficulty;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropSeed;
import ic2.api.crops.ICropTile;
import ic2.api.crops.IFarmland;
import ic2.api.crops.ISeedCrop;
import ic2.api.crops.ISubSoil;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkEventListener;
import ic2.api.network.tile.PacketRange;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ICollideable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.rendering.block.CropEntry;
import ic2.core.block.rendering.props.CropProperty;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.base.IC2Item;
import ic2.core.item.misc.CropSeedItem;
import ic2.core.platform.player.CropStatistics;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.config.ic2.CropParticles;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CropTileEntity
extends BaseTileEntity
implements ICropTile,
ITickListener,
IClickable,
IRedstoneListener,
IRedstoneProvider,
ICollideable,
INetworkEventListener {
    @NetworkInfo
    ICrop crop;
    @NetworkInfo
    public boolean breeding;
    @NetworkInfo
    public int stage;
    @NetworkInfo
    public CompoundTag customData = new CompoundTag();
    boolean overgrowth = false;
    int overgrowthTicks = 0;
    boolean restored = false;
    private byte humidity = (byte)-1;
    private byte nutrients = (byte)-1;
    private byte airQuality = (byte)-1;
    private byte scanLevel = 0;
    private IFarmland land = null;
    private int growthPoints = 0;
    private int nutrientStorage = 0;
    private int waterStorage = 0;
    private int exStorage = 0;
    private byte growth = 0;
    private byte gain = 0;
    private byte resistance = 0;
    CropDifficulty diff;
    boolean isSet = false;
    int cachedSpeed = -1;
    UUID breeder = null;
    int lastLight = -1;

    public CropTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.diff = new CropDifficulty(IC2.CONFIG.cropDifficulty.get().ordinal());
        this.clearNetworkFields();
        this.addNetworkFields("crop", "breeding", "stage", "customData");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CROP_STICK;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128473_("active");
        compound.m_128473_("facing");
        compound.m_128473_("customName");
        NBTUtils.putString(compound, "crop", this.crop == null ? "empty" : this.crop.id().toString(), "empty");
        NBTUtils.putBoolean(compound, "breeding", this.breeding, false);
        NBTUtils.putByte(compound, "stage", this.stage, 0);
        NBTUtils.put(compound, "customdata", this.customData);
        NBTUtils.putBoolean(compound, "overgrowth", this.overgrowth, false);
        NBTUtils.putInt(compound, "ticks", this.overgrowthTicks, 0);
        NBTUtils.putBoolean(compound, "restored", this.restored, false);
        NBTUtils.putByte(compound, "scan", (int)this.scanLevel, 0);
        NBTUtils.putInt(compound, "points", this.growthPoints, 0);
        NBTUtils.putShort(compound, "nutrients", this.nutrientStorage, 0);
        NBTUtils.putShort(compound, "water", this.waterStorage, 0);
        NBTUtils.putShort(compound, "ex", this.exStorage, 0);
        NBTUtils.putByte(compound, "growth", (int)this.growth, 0);
        NBTUtils.putByte(compound, "gain", (int)this.gain, 0);
        NBTUtils.putByte(compound, "resistance", (int)this.resistance, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        String s = NBTUtils.getString(compound, "crop", "empty");
        this.crop = s.equalsIgnoreCase("empty") ? null : CropRegistry.INSTANCE.getCrop(new ResourceLocation(s));
        this.breeding = compound.m_128471_("breeding");
        this.stage = compound.m_128445_("stage");
        this.customData = compound.m_128469_("customdata");
        this.overgrowth = compound.m_128471_("overgrowth");
        this.overgrowthTicks = compound.m_128451_("ticks");
        this.restored = compound.m_128471_("restored");
        this.scanLevel = compound.m_128445_("scan");
        this.growthPoints = compound.m_128451_("points");
        this.nutrientStorage = compound.m_128451_("nutrients");
        this.waterStorage = compound.m_128451_("water");
        this.exStorage = compound.m_128451_("ex");
        this.growth = compound.m_128445_("growth");
        this.gain = compound.m_128445_("gain");
        this.resistance = compound.m_128445_("resistance");
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        this.land = null;
        this.getFarmland();
        if (this.land == null) {
            this.pickCrop();
            Block.m_49840_((Level)this.f_58857_, (BlockPos)this.m_58899_(), (ItemStack)new ItemStack((ItemLike)IC2Items.CROP_STICKS));
            this.f_58857_.m_7471_(this.m_58899_(), false);
            this.f_58857_.m_5594_(null, this.m_58899_(), SoundType.f_56740_.m_56775_(), SoundSource.BLOCKS, 1.0f, 1.0f);
            return;
        }
        if (this.isWaterLogged()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)this.f_58857_));
        }
        if (this.crop != null && this.isSimulating()) {
            this.crop.onBlockUpdate(this);
        }
    }

    @Override
    public void onEntityCollided(Entity entity) {
        if (this.crop != null && this.isSimulating()) {
            this.crop.onEntityCollision(this, entity);
        }
    }

    @Override
    public int getCommonSignalStrength(Direction side) {
        return this.crop != null && this.crop.canEmitRedstone(this) ? this.crop.getRedstoneLevel(this) : 0;
    }

    public int getCropLight() {
        return this.crop == null ? 0 : this.crop.getEmittedLight(this);
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return this.crop != null && this.crop.canEmitRedstone(this);
    }

    @Override
    public IClickable.ClickAction getRequiredActions() {
        return IClickable.ClickAction.BOTH;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        if (!stack.m_41619_()) {
            if (this.crop == null) {
                if (!this.breeding && stack.m_41720_() == IC2Items.CROP_STICKS) {
                    if (!player.m_7500_()) {
                        stack.m_41774_(1);
                    }
                    this.breeding = true;
                    this.breeder = player.m_20148_();
                    this.updateTileField("breeding");
                    return true;
                }
                if (this.applyBaseSeed(stack, player, hand)) {
                    return true;
                }
            }
            if (!this.overgrowth && stack.m_41720_() == IC2Items.OVERGROWTH_FERTILIZER) {
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                player.m_36220_(IC2Stats.OVERGROWTH_USED);
                this.overgrowth = true;
                this.overgrowthTicks = 6000;
                return true;
            }
            if (this.overgrowth && this.overgrowthTicks <= 0 && !this.restored && stack.m_41720_() == IC2Items.RESTORING_FERTILIZER) {
                this.restored = true;
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                player.m_36220_(IC2Stats.REVIVED_USED);
                return true;
            }
            if (stack.m_41720_() == IC2Items.CROP_ANALYZER) {
                if (this.isSimulating()) {
                    if (player.m_6144_()) {
                        if (this.crop != null) {
                            if (this.scanLevel >= 4) {
                                player.m_5661_((Component)this.translate("info.item.ic2.crop_analyzer.scan_level_increase.failed_max"), false);
                            } else if (ElectricItem.MANAGER.use(stack, ICropSeed.SCAN_COST[this.scanLevel], (LivingEntity)player)) {
                                this.scanLevel = (byte)(this.scanLevel + 1);
                                player.m_5661_((Component)this.translate("info.item.ic2.crop_analyzer.scan_level_increase.success", new Object[]{this.scanLevel}), false);
                            } else {
                                player.m_5661_((Component)this.translate("info.item.ic2.crop_analyzer.scan_level_increase.failed_energy", new Object[]{ICropSeed.SCAN_COST[this.scanLevel]}), false);
                            }
                        }
                    } else if (this.crop == null || this.stage <= 0 || this.scanLevel < 1 && this.stage < this.crop.getGrowthSteps()) {
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.info.name", new Object[]{this.translate("info.crop.ic2.data.unknown", new ChatFormatting[]{ChatFormatting.YELLOW})}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.qualities.nutrients", new Object[]{this.string(this.getNutrients() + " / 20", new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE})}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.qualities.humidity", new Object[]{this.string(this.getHumidity() + " / 20", new ChatFormatting[]{ChatFormatting.AQUA})}), false);
                        player.m_5661_((Component)this.translate(this.isWaterLogged() ? "info.crop.ic2.data.qualities.water" : "info.crop.ic2.data.qualities.air", new Object[]{this.string(this.getEnvironmentQuality() + " / 10", new ChatFormatting[]{ChatFormatting.YELLOW})}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.storage", this.createStorageStats()), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.info.light", new Object[]{this.string(this.f_58857_.m_46803_(this.m_58899_()) + " / 15", new ChatFormatting[]{ChatFormatting.YELLOW})}), false);
                    } else {
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.info.name", new Object[]{this.string().m_7220_(this.crop.getName()).m_130946_(" (").m_7220_((Component)this.translate("info.crop.ic2.data.info.discovered", new Object[]{this.crop.discoveredBy().m_6881_()})).m_130946_(")").m_130940_(ChatFormatting.YELLOW)}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.info.size", new Object[]{this.string(this.stage + " / " + this.crop.getGrowthSteps(), new ChatFormatting[]{ChatFormatting.GREEN})}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.info.growth", new Object[]{this.string(this.growthPoints + " / " + this.crop.getGrowthDuration(this) + " (").m_7220_((Component)this.translate("info.crop.ic2.data.info.rate", new Object[]{this.calculateGrowthSpeed()})).m_130946_(")").m_130940_(ChatFormatting.AQUA)}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.stats", this.createCropStats()), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.qualities.nutrients", new Object[]{this.string(this.getNutrients() + " / 20", new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE})}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.qualities.humidity", new Object[]{this.string(this.getHumidity() + " / 20", new ChatFormatting[]{ChatFormatting.AQUA})}), false);
                        player.m_5661_((Component)this.translate(this.isWaterLogged() ? "info.crop.ic2.data.qualities.water" : "info.crop.ic2.data.qualities.air", new Object[]{this.string(this.getEnvironmentQuality() + " / 10", new ChatFormatting[]{ChatFormatting.YELLOW})}), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.storage", this.createStorageStats()), false);
                        player.m_5661_((Component)this.translate("info.crop.ic2.data.info.light", new Object[]{this.string(this.f_58857_.m_46803_(this.m_58899_()) + " / 15", new ChatFormatting[]{ChatFormatting.YELLOW})}), false);
                    }
                }
                return true;
            }
            if (stack.m_41720_() == Items.f_42404_ || stack.m_41720_() == Items.f_42578_ || stack.m_41720_() == Items.f_42577_) {
                if (this.nutrientStorage <= 50) {
                    this.nutrientStorage += 25;
                    this.isSet = false;
                    if (!player.m_7500_()) {
                        stack.m_41774_(1);
                    }
                    return true;
                }
                return false;
            }
            if (stack.m_41720_() == IC2Items.FERTILIZER || stack.m_41720_() == Items.f_42499_) {
                if (this.applyFertilizer(true)) {
                    if (!player.m_7500_()) {
                        stack.m_41774_(1);
                    }
                    return true;
                }
            } else {
                if (stack.m_41720_() == IC2Items.HYDRATION_CELL) {
                    return this.applyManualHydration(stack, player, hand);
                }
                if (stack.m_41720_() == IC2Items.WEEDEX && this.applyWeedEx(true)) {
                    if (!player.m_7500_()) {
                        IItemTransporter transporter;
                        stack.m_41622_(1, (LivingEntity)player, IC2Item.get(hand));
                        if (stack.m_41776_() - stack.m_41773_() <= 1 && (transporter = TransporterManager.getTransporter(player)) != null && !transporter.removeItem(new SimpleFilter((ItemLike)IC2Items.GRIN_POWDER), null, 1, false).m_41619_()) {
                            stack.m_41721_(0);
                        }
                    }
                    return true;
                }
            }
        }
        return this.crop != null && this.crop.onRightClick(this, player, hand);
    }

    private Object[] createStorageStats() {
        Object[] components = new Component[]{this.string().m_7220_((Component)this.translate("info.crop.ic2.data.storage.fertilizer")).m_7220_((Component)this.string(": " + this.nutrientStorage + "/300", new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE})), this.string().m_7220_((Component)this.translate("info.crop.ic2.data.storage.water")).m_7220_((Component)this.string(": " + this.waterStorage + "/200", new ChatFormatting[]{ChatFormatting.BLUE})), this.string().m_7220_((Component)this.translate("info.crop.ic2.data.storage.ex")).m_7220_((Component)this.string(": " + this.exStorage + "/150", new ChatFormatting[]{ChatFormatting.GREEN}))};
        return components;
    }

    private Object[] createCropStats() {
        Object[] components = new Component[]{this.string().m_7220_((Component)this.translate("info.crop.ic2.data.stat.growth")).m_7220_((Component)this.string(": " + this.growth + "/31", new ChatFormatting[]{ChatFormatting.GREEN})), this.string().m_7220_((Component)this.translate("info.crop.ic2.data.stat.gain")).m_7220_((Component)this.string(": " + this.gain + "/31", new ChatFormatting[]{ChatFormatting.YELLOW})), this.string().m_7220_((Component)this.translate("info.crop.ic2.data.stat.resistance")).m_7220_((Component)this.string(": " + this.resistance + "/31", new ChatFormatting[]{ChatFormatting.BLUE}))};
        return components;
    }

    private boolean applyBaseSeed(ItemStack stack, Player player, InteractionHand hand) {
        BaseSeed seed = CropRegistry.INSTANCE.getSeedForStack(stack);
        int scanLevel = 1;
        if (seed == null && stack.m_41720_() instanceof ICropSeed) {
            seed = new BaseSeed(stack);
            scanLevel = ((ICropSeed)stack.m_41720_()).getScanLevel(stack);
        }
        if (seed == null) {
            return false;
        }
        if (stack.m_41613_() < seed.stack_size) {
            player.m_5661_((Component)this.translate("info.crop.ic2.plant.placement_error.not_enough", new Object[]{seed.stack_size}), false);
            return false;
        }
        if (this.tryPlantSeed(seed, scanLevel, player)) {
            if (!player.m_7500_()) {
                if (stack.hasCraftingRemainingItem()) {
                    stack = stack.getCraftingRemainingItem();
                }
                stack.m_41774_(seed.stack_size);
            }
            return true;
        }
        return false;
    }

    public boolean tryPlantSeed(BaseSeed seed, int scan, Player player) {
        if (this.crop != null || seed.crop == null || seed.crop == CropRegistry.WEED || seed.crop == CropRegistry.SEA_WEED) {
            player.m_5661_((Component)this.translate("info.crop.ic2.plant.placement_error.error"), false);
            return false;
        }
        if (this.breeding) {
            player.m_5661_((Component)this.translate("info.crop.ic2.plant.placement_error.breeding"), false);
            return false;
        }
        this.requestStateUpdate();
        if (!seed.crop.canGrow(this) || !seed.crop.getCropType().isCompatible(this.isWaterLogged())) {
            seed.crop.onCropPlaceFailed(seed, this, player);
            return false;
        }
        this.removeCrop();
        this.crop = seed.crop;
        this.stage = seed.stage;
        this.growth = (byte)Mth.m_14045_((int)seed.growth, (int)0, (int)31);
        this.gain = (byte)Mth.m_14045_((int)seed.gain, (int)0, (int)31);
        this.resistance = (byte)Mth.m_14045_((int)seed.resistance, (int)0, (int)31);
        this.scanLevel = (byte)scan;
        this.breeding = false;
        this.updateTileFields("breeding", "crop", "stage");
        return true;
    }

    public boolean applyWeedEx(boolean manual) {
        boolean trigger;
        if (this.exStorage >= 100 && manual || this.exStorage >= 150) {
            return false;
        }
        this.exStorage += 50;
        boolean bl = trigger = this.f_58857_.f_46441_.m_188503_(3) == 0;
        if (manual) {
            boolean bl2 = trigger = this.f_58857_.f_46441_.m_188503_(5) == 0;
        }
        if (this.crop != null && this.exStorage >= 75 && trigger) {
            switch (this.f_58857_.f_46441_.m_188503_(5)) {
                case 0: {
                    if (this.growth > 0) {
                        this.growth = (byte)(this.growth - 1);
                    }
                }
                case 1: {
                    if (this.gain <= 0) break;
                    this.gain = (byte)(this.gain - 1);
                }
            }
            if (this.resistance > 0) {
                this.resistance = (byte)(this.resistance - 1);
            }
        }
        return true;
    }

    public boolean applyManualHydration(ItemStack stack, Player player, InteractionHand hand) {
        if (this.waterStorage >= 200) {
            return false;
        }
        int apply = Math.min(stack.m_41776_() - stack.m_41773_(), 200 - this.waterStorage);
        if (stack.m_220157_(apply, this.f_58857_.f_46441_, null)) {
            if (stack.hasCraftingRemainingItem()) {
                player.m_21008_(hand, stack.getCraftingRemainingItem());
            } else {
                stack.m_41774_(1);
            }
        }
        this.waterStorage += apply;
        this.humidity = (byte)-1;
        this.isSet = false;
        return true;
    }

    public boolean applyAutomatedHydration(ItemStack stack) {
        if (this.waterStorage >= 180) {
            return false;
        }
        int apply = Math.min(180 - this.waterStorage, stack.m_41776_() - stack.m_41773_());
        if (stack.m_220157_(apply, this.f_58857_.f_46441_, null)) {
            stack.m_41774_(1);
        }
        this.waterStorage += apply;
        this.humidity = (byte)-1;
        this.isSet = false;
        return true;
    }

    public boolean applyAutomatedHydration(FluidTank tank) {
        if (this.waterStorage >= 180) {
            return false;
        }
        int added = tank.drain(180 - this.waterStorage, IFluidHandler.FluidAction.EXECUTE).getAmount();
        this.waterStorage += added;
        this.humidity = (byte)-1;
        this.isSet = false;
        return added > 0;
    }

    public boolean applyFertilizer(boolean manual) {
        if (this.nutrientStorage > 200) {
            return false;
        }
        this.nutrientStorage += manual ? 100 : 90;
        if (manual) {
            this.nutrients = (byte)-1;
        }
        this.isSet = false;
        return true;
    }

    @Override
    public boolean onLeftClick(Player player, BlockPos pos) {
        if (this.crop != null && this.crop.onLeftClick(this, player)) {
            return true;
        }
        if (this.breeding) {
            this.breeding = false;
            this.updateTileField("breeding");
            if (this.isSimulating()) {
                Block.m_49840_((Level)this.f_58857_, (BlockPos)this.m_58899_(), (ItemStack)new ItemStack((ItemLike)IC2Items.CROP_STICKS));
            }
            return true;
        }
        return false;
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("crop") || fields.contains("stage") || fields.contains("breeding")) {
            this.requestModelDataUpdate();
            this.f_58857_.m_7726_().m_7827_().m_7174_(this.m_58899_());
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onServerDataReceived(int key, int value) {
        CropParticles settings = IC2.CONFIG.cropParticles.get();
        if (settings == CropParticles.DISABLED) {
            return;
        }
        this.f_58857_.m_46796_(2005, this.f_58858_, settings == CropParticles.REDUCED ? (value == 1 ? 0 : 4) : 0);
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        if (this.crop == null) {
            return ModelData.builder().with((ModelProperty)CropProperty.CROP, (Object)new CropEntry(null, this.breeding ? 1 : 0)).build();
        }
        return ModelData.builder().with((ModelProperty)CropProperty.CROP, (Object)new CropEntry(this.crop, this.stage - 1)).build();
    }

    @Override
    public void onTick() {
        if (this.overgrowth) {
            if (this.overgrowthTicks > 0) {
                this.update(8);
                --this.overgrowthTicks;
                int newLight = this.getCropLight();
                if (this.lastLight != newLight) {
                    this.lastLight = newLight;
                    this.onStateChanged();
                }
            }
        } else if (this.clock(256)) {
            this.update(256);
            int newLight = this.getCropLight();
            if (this.lastLight != newLight) {
                this.lastLight = newLight;
                this.onStateChanged();
            }
        }
    }

    protected void update(int tickrate) {
        if (this.clock(tickrate * 4)) {
            this.humidity = this.updateHumidity();
        }
        if (this.clock(tickrate * 3)) {
            this.nutrients = this.updateNutrients();
        }
        if (this.clock(tickrate * 2)) {
            this.airQuality = this.updateAirQuality();
        }
        if (!(this.crop != null || this.breeding && this.attemptCrossing())) {
            if (this.f_58857_.f_46441_.m_188503_(this.diff.getWeedChance()) != 0 || this.hasWeedEx()) {
                if (this.exStorage > 0 && this.f_58857_.f_46441_.m_188503_(10) == 0) {
                    --this.exStorage;
                }
                return;
            }
            this.removeCrop();
            this.crop = this.isWaterLogged() ? CropRegistry.SEA_WEED : CropRegistry.WEED;
            this.stage = 1;
        }
        if (this.crop == null) {
            return;
        }
        this.crop.onTick(this);
        if (!this.crop.getCropType().isCompatible(this.isWaterLogged())) {
            if (this.f_58857_.f_46441_.m_188503_(20) > this.resistance) {
                this.removeCrop();
                return;
            }
        } else if (this.crop.canGrow(this) && this.stage < this.crop.getGrowthSteps()) {
            this.growthPoints += this.calculateGrowthSpeed();
            this.isSet = false;
            boolean grown = this.crop != null && this.growthPoints >= this.crop.getGrowthDuration(this);
            this.sendToClient(0, grown ? 1 : 0, PacketRange.SHORT_RANGE);
            if (grown) {
                this.growthPoints = 0;
                boolean canPerform = this.crop.canEmitRedstone(this);
                ++this.stage;
                if (canPerform || this.crop.canEmitRedstone(this)) {
                    this.notifyChanges(true, DirectionList.ALL);
                }
                if (this.stage >= this.crop.getGrowthSteps() && this.scanLevel == 0) {
                    this.scanLevel = 1;
                }
                this.updateTileField("stage");
            } else if (this.crop == null) {
                return;
            }
        } else if (this.stage > this.crop.getGrowthSteps()) {
            this.stage = this.crop.getGrowthSteps();
            this.updateTileField("stage");
        }
        if (this.nutrientStorage > 0) {
            --this.nutrientStorage;
        }
        if (this.waterStorage > 0) {
            --this.waterStorage;
        }
        if (this.crop.isWeed(this) && this.f_58857_.f_46441_.m_188503_(this.diff.getWeedChance()) - this.growth <= 2) {
            this.generateWeed();
        }
    }

    public boolean attemptCrossing() {
        ServerPlayer player;
        int i;
        if (this.f_58857_.f_46441_.m_188503_(3) != 0) {
            return false;
        }
        ObjectList attempt = CollectionUtils.createList();
        int cropsThere = 0;
        for (Direction facing : DirectionList.HORIZONTAL) {
            cropsThere += this.attemptCrossing(this.m_58899_().m_121945_(facing), (List<ICropTile>)attempt);
        }
        if (attempt.size() < 2) {
            if (attempt.size() > 0 && attempt.size() == cropsThere) {
                this.copyCrop((ICropTile)attempt.get(0));
                return true;
            }
            return false;
        }
        List<ICrop> crops = CropRegistry.INSTANCE.getCrops();
        crops.remove(CropRegistry.WEED);
        crops.remove(CropRegistry.SEA_WEED);
        int[] ratios = new int[crops.size()];
        int total = 0;
        boolean waterlogged = this.isWaterLogged();
        for (i = 0; i < crops.size(); ++i) {
            ICrop crop = crops.get(i);
            if (!crop.canGrow(this) || !crop.getCropType().isCompatible(waterlogged)) continue;
            for (int j = 0; j < attempt.size(); ++j) {
                int chance = CropTileEntity.calculateRatioFor(crop, ((ICropTile)attempt.get(j)).getCrop());
                int n = i;
                ratios[n] = ratios[n] + chance;
                total += chance;
            }
        }
        total = this.f_58857_.f_46441_.m_188503_(total);
        for (i = 0; i < ratios.length; ++i) {
            if (ratios[i] > 0 && ratios[i] > total) {
                total = i;
                break;
            }
            total -= ratios[i];
        }
        if (total >= crops.size()) {
            return false;
        }
        this.breeding = false;
        this.crop = crops.get(total);
        this.stage = 1;
        this.growth = 0;
        this.resistance = 0;
        this.gain = 0;
        this.customData = new CompoundTag();
        int[] value = this.diff.getCropStats((List<ICropTile>)attempt, this.f_58857_.f_46441_);
        this.growth = (byte)value[0];
        this.resistance = (byte)value[1];
        this.gain = (byte)value[2];
        this.updateTileFields("crop", "stage", "customData", "breeding");
        if (!(this.breeder == null || this.overgrowth && this.f_58857_.f_46441_.m_188503_(256) != 0 || (player = IC2.PLATFORM.getServer().m_6846_().m_11259_(this.breeder)) == null)) {
            player.m_36220_(IC2Stats.CROPS_BREED);
            CropStatistics stats = CropStatistics.getStats();
            if (stats != null) {
                stats.onCropReceived(this.breeder, this.crop, this.growth, this.gain, this.resistance);
            }
        }
        return true;
    }

    @Override
    public int calculateGrowthSpeed() {
        int have;
        if (this.isSet) {
            return this.cachedSpeed;
        }
        this.isSet = true;
        if (this.crop == null) {
            this.cachedSpeed = -1;
            return -1;
        }
        int base = 3 + this.f_58857_.f_46441_.m_188503_(7) + this.growth;
        int need = (this.crop.getProperties().getTier() - 1) * 4 + this.growth + this.gain + this.resistance;
        if (need < 0) {
            need = 0;
        }
        if ((have = this.crop.getStatInfluence(this, this.getHumidity(), this.getNutrients(), this.getEnvironmentQuality()) * 5) >= need) {
            base = base * (100 + (have - need)) / 100;
        } else {
            int neg = (need - have) * 4;
            if (neg > 100 && this.f_58857_.f_46441_.m_188503_(32) > this.resistance) {
                this.removeCrop();
                base = 0;
            } else if ((base = base * (100 - neg) / 100) < 0) {
                base = 0;
            }
        }
        this.cachedSpeed = this.diff.getCropGrowth(base);
        return this.cachedSpeed;
    }

    public int attemptCrossing(BlockPos pos, List<ICropTile> list) {
        if (!this.f_58857_.m_46749_(pos)) {
            return 0;
        }
        BlockEntity tile = this.f_58857_.m_7702_(pos);
        if (!(tile instanceof ICropTile)) {
            return 0;
        }
        ICropTile other = (ICropTile)tile;
        ICrop crop = other.getCrop();
        if (crop == null) {
            return 0;
        }
        if (!crop.canGrow(this) || !other.canBreed()) {
            return 1;
        }
        int base = this.diff.getBreedingDifficulty();
        if (other.getGrowthStat() >= 16) {
            ++base;
        }
        if (other.getGrowthStat() >= 30) {
            ++base;
        }
        if (other.getResistanceStat() >= 28) {
            base += 27 - other.getResistanceStat();
        }
        if (base >= this.f_58857_.f_46441_.m_188503_(20)) {
            list.add(other);
        }
        return 1;
    }

    public void generateWeed() {
        Block block;
        BlockEntity tile;
        BlockPos offset = this.m_58899_();
        boolean stop = false;
        Direction last = null;
        while (!stop) {
            stop = last != null;
            int rand = this.f_58857_.f_46441_.m_188503_(4);
            Direction dir = last;
            if (dir == (last = Direction.m_122407_((int)(stop ? 3 - rand : rand))) || dir == last.m_122424_()) break;
            offset = offset.m_121945_(last);
        }
        if ((tile = this.f_58857_.m_7702_(offset)) instanceof CropTileEntity) {
            CropTileEntity crop = (CropTileEntity)tile;
            if (crop.crop == null || !crop.crop.isWeed(crop) && this.f_58857_.f_46441_.m_188503_(32) > crop.resistance && !crop.hasWeedEx()) {
                byte newGrowth = (byte)Math.max(crop.growth, this.growth);
                if (newGrowth < 31 && this.f_58857_.f_46441_.m_188499_()) {
                    newGrowth = (byte)(newGrowth + 1);
                }
                crop.removeCrop();
                crop.crop = crop.isWaterLogged() ? CropRegistry.SEA_WEED : CropRegistry.WEED;
                crop.stage = 1;
                crop.growth = newGrowth;
                if (this.overgrowth) {
                    crop.overgrowth = true;
                    crop.overgrowthTicks = 1000;
                }
            }
        } else if (this.f_58857_.m_46859_(offset) && ((block = this.f_58857_.m_8055_(offset.m_7495_()).m_60734_()) == Blocks.f_50493_ || block == Blocks.f_50440_ || block == Blocks.f_50093_)) {
            this.f_58857_.m_46597_(offset.m_7495_(), Blocks.f_50440_.m_49966_());
            this.f_58857_.m_46597_(offset, Blocks.f_50034_.m_49966_());
        }
    }

    public void copyCrop(ICropTile prov) {
        this.breeding = false;
        this.crop = prov.getCrop();
        this.stage = 1;
        this.customData = new CompoundTag();
        this.growth = (byte)Mth.m_14045_((int)prov.getGrowthStat(), (int)0, (int)31);
        this.resistance = (byte)Mth.m_14045_((int)prov.getResistanceStat(), (int)0, (int)31);
        this.gain = (byte)Mth.m_14045_((int)prov.getGainStat(), (int)0, (int)31);
        this.scanLevel = (byte)prov.getScanLevel();
        this.updateTileFields("crop", "stage", "customData", "breeding");
    }

    public boolean hasWeedEx() {
        if (this.exStorage > 0) {
            this.exStorage = Math.max(0, this.exStorage - 5);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBreed() {
        if (this.crop == null) {
            return false;
        }
        return this.crop.canBreed(this);
    }

    @Override
    public IFarmland getFarmland() {
        if (this.land == null) {
            this.land = CropRegistry.INSTANCE.getFarmland(this.m_58904_().m_8055_(this.m_58899_().m_7495_()).m_60734_());
            if (this.land == null) {
                return new IFarmland(){

                    @Override
                    public int getNutrients(BlockState state) {
                        return 0;
                    }

                    @Override
                    public int getHumidity(BlockState state) {
                        return 0;
                    }
                };
            }
        }
        return this.land;
    }

    @Override
    public boolean isWaterLogged() {
        return (Boolean)this.m_58900_().m_61143_((Property)BlockStateProperties.f_61362_);
    }

    @Override
    public void setWaterlogged(boolean water) {
        this.withState(BlockStateProperties.f_61362_, water);
    }

    @Override
    public ICrop getCrop() {
        return this.crop;
    }

    @Override
    public void setCrop(ICrop crop) {
        this.crop = crop;
        this.updateTileField("crop");
    }

    @Override
    public int getGrowthStage() {
        return this.stage;
    }

    @Override
    public void setGrowthStage(int stage) {
        this.stage = stage;
        this.updateTileField("stage");
    }

    @Override
    public int getGrowthPoints() {
        return this.growthPoints;
    }

    @Override
    public void setGrowthPoints(int points) {
        this.growthPoints = points;
        this.isSet = false;
    }

    @Override
    public int getScanLevel() {
        return this.scanLevel;
    }

    @Override
    public void setScanLevel(int level) {
        this.scanLevel = (byte)level;
    }

    @Override
    public boolean isCrossBreeding() {
        return this.breeding;
    }

    @Override
    public void setCrossBreeding(boolean breed) {
        this.breeding = breed;
        this.updateTileField("breeding");
    }

    @Override
    public int getGainStat() {
        return this.gain;
    }

    @Override
    public void setGainStat(int level) {
        this.gain = (byte)Mth.m_14045_((int)level, (int)0, (int)31);
    }

    @Override
    public int getGrowthStat() {
        return this.growth;
    }

    @Override
    public void setGrowthStat(int level) {
        this.growth = (byte)Mth.m_14045_((int)level, (int)0, (int)31);
    }

    @Override
    public int getResistanceStat() {
        return this.resistance;
    }

    @Override
    public void setResistanceStat(int level) {
        this.resistance = (byte)Mth.m_14045_((int)level, (int)0, (int)31);
    }

    @Override
    public void requestStateUpdate() {
        this.airQuality = (byte)-1;
        this.humidity = (byte)-1;
        this.nutrients = (byte)-1;
    }

    @Override
    public void onCustomDataChanged() {
        this.updateTileField("customData");
    }

    @Override
    public boolean performManualHarvest() {
        List<ItemStack> list = this.performHarvest(true);
        if (list == null || list.isEmpty()) {
            return false;
        }
        if (this.isRendering()) {
            return true;
        }
        for (ItemStack stack : list) {
            Block.m_49840_((Level)this.f_58857_, (BlockPos)this.m_58899_(), (ItemStack)stack);
        }
        return true;
    }

    @Override
    public List<ItemStack> performHarvest(boolean manual) {
        if (this.crop == null || !this.crop.canBeHarvested(this)) {
            return null;
        }
        double chance = this.crop.getDropChance(this);
        for (int i = 0; i < this.gain; ++i) {
            chance *= manual ? 1.1 : 1.03;
        }
        chance -= this.f_58857_.f_46441_.m_188500_();
        int drops = 0;
        while (chance > 0.0) {
            ++drops;
            chance -= this.f_58857_.f_46441_.m_188500_();
        }
        ISeedCrop seed = this.crop instanceof ISeedCrop ? (ISeedCrop)((Object)this.crop) : null;
        boolean doesDropSeed = seed != null && seed.isDroppingSeeds(this);
        ObjectList results = CollectionUtils.createList();
        for (int i = 0; i < drops; ++i) {
            ItemStack[] result;
            ItemStack[] itemStackArray = result = doesDropSeed ? seed.getSeedDrops(this) : this.crop.getDrops(this);
            if (result == null || result.length <= 0) continue;
            for (int x = 0; x < result.length; ++x) {
                ItemStack entry = result[x];
                if (this.f_58857_.f_46441_.m_188503_(100) <= this.gain) {
                    entry.m_41769_(1);
                }
                results.add((ItemStack)entry.m_41777_());
            }
        }
        boolean canPerform = this.crop.canEmitRedstone(this);
        this.stage = this.crop.getAfterHarvestStage(this);
        if (canPerform || this.crop.canEmitRedstone(this)) {
            this.notifyChanges(true, DirectionList.ALL);
        }
        this.updateTileField("stage");
        return results;
    }

    @Override
    public boolean pickCrop() {
        int i;
        if (this.crop == null) {
            return false;
        }
        boolean bonus = this.crop.canBeHarvested(this);
        float firstChance = this.crop.getSeedDropChance(this);
        for (int i2 = 0; i2 < this.resistance; ++i2) {
            firstChance *= 1.1f;
        }
        int drop = 0;
        if (bonus) {
            if (this.f_58857_.f_46441_.m_188501_() <= (firstChance + 1.0f) * 0.8f) {
                ++drop;
            }
            float chance = this.crop.getSeedDropChance(this) + (float)this.growth / 100.0f;
            for (i = 23; i < this.gain; ++i) {
                chance *= 0.95f;
            }
            if (this.f_58857_.f_46441_.m_188501_() <= chance) {
                ++drop;
            }
        } else if (this.f_58857_.f_46441_.m_188501_() <= firstChance * 1.5f) {
            ++drop;
        }
        ItemStack[] result = new ItemStack[drop];
        for (i = 0; i < drop; ++i) {
            result[i] = this.crop.getSeeds(this);
        }
        this.removeCrop();
        if (this.isSimulating() && result.length > 0) {
            for (i = 0; i < result.length; ++i) {
                Block.m_49840_((Level)this.f_58857_, (BlockPos)this.m_58899_(), (ItemStack)result[i]);
            }
        }
        return true;
    }

    @Override
    public void removeCrop() {
        this.crop = null;
        this.stage = 0;
        this.customData = new CompoundTag();
        this.gain = 0;
        this.resistance = 0;
        this.growth = 0;
        this.growthPoints = 0;
        this.breeding = false;
        this.scanLevel = 0;
        this.notifyChanges(true, DirectionList.ALL);
        this.updateTileFields("crop", "stage", "customData", "breeding");
    }

    @Override
    public Set<Block> getBlocksBelow() {
        ObjectSortedSet result = CollectionUtils.createLinkedSet();
        for (int i = 2; i < 6; ++i) {
            BlockPos down = this.m_58899_().m_6625_(i);
            if (this.f_58857_.m_46859_(down)) {
                return result;
            }
            result.add((Block)this.f_58857_.m_8055_(down).m_60734_());
        }
        return result;
    }

    @Override
    public boolean isBlockBelow(BlockState state) {
        for (int i = 2; i < 6; ++i) {
            BlockPos down = this.m_58899_().m_6625_(i);
            if (this.f_58857_.m_46859_(down)) {
                return false;
            }
            if (this.f_58857_.m_8055_(down) != state) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlockBelow(Block block) {
        for (int i = 2; i < 6; ++i) {
            BlockPos down = this.m_58899_().m_6625_(i);
            if (this.f_58857_.m_46859_(down)) {
                return false;
            }
            if (this.f_58857_.m_8055_(down).m_60734_() != block) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlockBelow(TagKey<Block> tag) {
        for (int i = 2; i < 6; ++i) {
            BlockPos down = this.m_58899_().m_6625_(i);
            if (this.f_58857_.m_46859_(down)) {
                return false;
            }
            if (!this.f_58857_.m_8055_(down).m_204336_(tag)) continue;
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag getCustomData() {
        return this.customData;
    }

    @Override
    public int getLightLevel() {
        return this.f_58857_.m_46803_(this.m_58899_());
    }

    @Override
    public int getEnvironmentQuality() {
        if (this.airQuality == -1) {
            this.airQuality = this.updateAirQuality();
        }
        return this.airQuality;
    }

    public byte updateAirQuality() {
        BlockPos.MutableBlockPos target = new BlockPos.MutableBlockPos();
        if (this.isWaterLogged()) {
            int value = ICropRegistry.INSTANCE.getWaterQualityBonus((Holder<Biome>)this.f_58857_.m_204166_(this.m_58899_()));
            for (int x = this.f_58858_.m_123341_() - 1; x < this.f_58858_.m_123341_() + 1; ++x) {
                for (int z = this.f_58858_.m_123343_() - 1; z < this.f_58858_.m_123343_() + 1; ++z) {
                    BlockEntity tile;
                    target.m_122178_(x, this.f_58858_.m_123342_(), z);
                    BlockState state = this.f_58857_.m_8055_((BlockPos)target);
                    FluidState fluid = state.m_60819_();
                    if (state.m_60734_() instanceof BubbleColumnBlock && fluid.m_205070_(FluidTags.f_13131_) || state.m_60734_() == Blocks.f_50129_) {
                        ++value;
                        continue;
                    }
                    if (fluid.m_205070_(FluidTags.f_13131_) && this.f_58857_.m_8055_(target.m_7495_()).m_60734_() == Blocks.f_50129_) {
                        ++value;
                    }
                    if ((tile = this.f_58857_.m_7702_((BlockPos)target)) instanceof CropTileEntity && ((CropTileEntity)tile).isWaterCleaningCrop()) {
                        ++value;
                        continue;
                    }
                    if (fluid.m_205070_(FluidTags.f_13131_) || state.m_60734_() == Blocks.f_50057_ || state.m_60795_()) continue;
                    --value;
                }
            }
            return (byte)Mth.m_14045_((int)value, (int)0, (int)10);
        }
        BlockPos pos = this.m_58899_();
        int value = Mth.m_14045_((int)((pos.m_123342_() - 64) / 15), (int)0, (int)5) + (this.f_58857_.m_46861_(pos.m_7494_()) ? 2 : 0);
        int fresh = 9;
        for (int x = pos.m_123341_() - 1; x < pos.m_123341_() + 1 && fresh > 0; ++x) {
            for (int z = pos.m_123343_() - 1; z < pos.m_123343_() + 1 && fresh > 0; ++z) {
                target.m_122178_(x, pos.m_123342_(), z);
                if (this.f_58857_.m_46859_(pos) || !this.f_58857_.m_8055_(pos).m_60796_((BlockGetter)this.f_58857_, (BlockPos)target) && !(this.f_58857_.m_7702_((BlockPos)target) instanceof CropTileEntity)) continue;
                --fresh;
            }
        }
        return (byte)Mth.m_14045_((int)(value + fresh / 2), (int)0, (int)10);
    }

    @Override
    public int getNutrients() {
        if (this.nutrients == -1) {
            this.nutrients = this.updateNutrients();
        }
        return this.nutrients;
    }

    public byte updateNutrients() {
        ISubSoil soil;
        int value = ICropRegistry.INSTANCE.getNutrientBonus((Holder<Biome>)this.f_58857_.m_204166_(this.m_58899_()));
        value += this.getFarmland().getNutrients(this.f_58857_, this.m_58899_().m_7495_());
        for (int i = 2; i < 5 && (soil = ICropRegistry.INSTANCE.getSubSoil(this.f_58857_.m_8055_(this.m_58899_().m_6625_(i)).m_60734_())) != null; ++i) {
            value += soil.getNutrients(this.m_58904_(), this.m_58899_().m_6625_(i));
        }
        return (byte)Mth.m_14045_((int)(value += Math.min(10, (this.nutrientStorage + 19) / 20)), (int)0, (int)20);
    }

    @Override
    public int getHumidity() {
        if (this.humidity == -1) {
            this.humidity = this.updateHumidity();
        }
        return this.humidity;
    }

    public byte updateHumidity() {
        ISubSoil soil;
        if (this.isWaterLogged()) {
            return 20;
        }
        int value = ICropRegistry.INSTANCE.getHumidityBonus((Holder<Biome>)this.f_58857_.m_204166_(this.m_58899_()));
        value += this.getFarmland().getHumidity(this.f_58857_, this.m_58899_().m_7495_());
        for (int i = 2; i < 5 && (soil = ICropRegistry.INSTANCE.getSubSoil(this.f_58857_.m_8055_(this.m_58899_().m_6625_(i)).m_60734_())) != null; ++i) {
            value += soil.getHumidity(this.m_58904_(), this.m_58899_().m_6625_(i));
        }
        if (this.waterStorage >= 5) {
            value += 2;
        }
        return (byte)Mth.m_14045_((int)(value += (this.waterStorage + 24) / 25), (int)0, (int)20);
    }

    protected boolean isWaterCleaningCrop() {
        return this.crop != null && this.crop.isWaterCleaningCrop(this);
    }

    @Override
    public int getWaterStorage() {
        return this.waterStorage;
    }

    @Override
    public void setWaterStorage(int water) {
        this.waterStorage = water;
    }

    @Override
    public int getFertilizerStorage() {
        return this.nutrientStorage;
    }

    @Override
    public void setFertilizerStorage(int fertilizer) {
        this.nutrientStorage = fertilizer;
    }

    @Override
    public int getWeedExStorage() {
        return this.exStorage;
    }

    @Override
    public void setWeedExStorage(int weedEx) {
        this.exStorage = weedEx;
    }

    public static int calculateRatioFor(ICrop a, ICrop b) {
        int i;
        if (a == b) {
            return 500;
        }
        int value = 0;
        int[] aStats = a.getProperties().getAllProperties();
        int[] bStats = b.getProperties().getAllProperties();
        for (i = 0; i < 5; ++i) {
            int c = Math.abs(aStats[i] - bStats[i]);
            switch (c) {
                default: {
                    --value;
                }
                case 0: {
                    value += 2;
                }
                case 1: {
                    ++value;
                }
                case 2: 
            }
        }
        String[] first = a.getAttributes();
        String[] second = b.getAttributes();
        for (i = 0; i < first.length; ++i) {
            for (int j = 0; j < second.length; ++j) {
                if (!first[i].equalsIgnoreCase(second[j])) continue;
                value += 5;
            }
        }
        if (b.getProperties().getTier() < a.getProperties().getTier() - 1) {
            value -= 2 * (a.getProperties().getTier() - b.getProperties().getTier());
        }
        if (b.getProperties().getTier() - 3 > a.getProperties().getTier()) {
            value -= b.getProperties().getTier() - a.getProperties().getTier();
        }
        if (value < 0) {
            value = 0;
        }
        return value;
    }

    public static int[] calculateRatiosFor(ICrop a, ICrop b, boolean waterlogged) {
        int temp;
        int i;
        if (!a.getCropType().isCompatible(waterlogged)) {
            return new int[]{0, 0, 0, 0};
        }
        if (a == b) {
            return new int[]{500, 0, 0, 0};
        }
        int value = 0;
        int properties = 0;
        int attributes = 0;
        int tier = 0;
        int[] aStats = a.getProperties().getAllProperties();
        int[] bStats = b.getProperties().getAllProperties();
        for (i = 0; i < 5; ++i) {
            int c = Math.abs(aStats[i] - bStats[i]);
            switch (c) {
                default: {
                    --value;
                    --properties;
                }
                case 0: {
                    value += 2;
                    properties += 2;
                }
                case 1: {
                    ++value;
                    ++properties;
                }
                case 2: 
            }
        }
        String[] first = a.getAttributes();
        String[] second = b.getAttributes();
        for (i = 0; i < first.length; ++i) {
            for (int j = 0; j < second.length; ++j) {
                if (!first[i].equalsIgnoreCase(second[j])) continue;
                value += 5;
                attributes += 5;
            }
        }
        if (b.getProperties().getTier() < a.getProperties().getTier() - 1) {
            temp = 2 * (a.getProperties().getTier() - b.getProperties().getTier());
            value -= temp;
            tier -= temp;
        }
        if (b.getProperties().getTier() - 3 > a.getProperties().getTier()) {
            temp = b.getProperties().getTier() - a.getProperties().getTier();
            value -= temp;
            tier -= temp;
        }
        if (value < 0) {
            value = 0;
        }
        return new int[]{value, properties, attributes, tier};
    }

    @Override
    public ItemStack createSeeds(ICrop crop, int growthStat, int gainStat, int resistanceStat, int scanLevel) {
        return CropSeedItem.createStack(crop, growthStat, gainStat, resistanceStat, scanLevel);
    }
}

