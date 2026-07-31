/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FarmBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  org.apache.commons.lang3.mutable.Mutable
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.crop;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.CropCard;
import ic2.api.crops.CropSoilType;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import ic2.api.network.NetworkHelper;
import ic2.core.IC2;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.block.tileentity.Ic2TileEntityBlock;
import ic2.core.crop.Ic2Crops;
import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.item.ItemCropSeed;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2Items;
import ic2.core.util.BiomeUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class TileEntityCrop
extends Ic2TileEntity
implements ICropTile {
    public boolean dirty = true;
    public static int tickRate = 256;
    private CropCard crop = null;
    private byte statGrowth;
    private byte statGain;
    private byte statResistance;
    private short storageNutrients;
    private short storageWater;
    private short storageWeedEX;
    private byte terrainAirQuality;
    private byte terrainHumidity;
    private byte terrainNutrients;
    private byte currentAge;
    private short growthPoints = 0;
    private byte scanLevel;
    private CompoundTag customData = new CompoundTag();
    public static final boolean debug = System.getProperty("ic2.crops.debug") != null;
    public static final boolean debugChance = debug && System.getProperty("ic2.crops.debug").contains("chance");
    public static final boolean debugGrowth = debug && System.getProperty("ic2.crops.debug").contains("growth");
    public static final boolean debugWeedWork = debug && System.getProperty("ic2.crops.debug").contains("weedwork");
    public static final boolean debugCollision = debug && System.getProperty("ic2.crops.debug").contains("collision");
    public static final boolean debugTerrain = debug && System.getProperty("ic2.crops.debug").contains("terrain");

    public TileEntityCrop(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.get(Registry.f_122824_.m_7981_((Object)blockState.m_60734_())), blockPos, blockState);
        this.crop = Crops.instance.getCropCard(this.getBlockType());
        if (debug) {
            IC2.log.info(LogCategory.Block, "Debug mode is running");
        }
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        if (compoundTag.m_128441_("statGrowth") && compoundTag.m_128441_("statGain")) {
            this.statGrowth = compoundTag.m_128445_("statGrowth");
            this.statGain = compoundTag.m_128445_("statGain");
            this.statResistance = compoundTag.m_128445_("statResistance");
            this.storageNutrients = compoundTag.m_128448_("storageNutrients");
            this.storageWater = compoundTag.m_128448_("storageWater");
            this.storageWeedEX = compoundTag.m_128448_("storageWeedEX");
            this.terrainHumidity = compoundTag.m_128445_("terrainHumidity");
            this.terrainNutrients = compoundTag.m_128445_("terrainNutrients");
            this.terrainAirQuality = compoundTag.m_128445_("terrainAirQuality");
            this.currentAge = compoundTag.m_128445_("currentAge");
            this.growthPoints = compoundTag.m_128448_("growthPoints");
            this.scanLevel = compoundTag.m_128445_("scanLevel");
            this.customData = compoundTag.m_128469_("customData");
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (this.crop != null) {
            compoundTag.m_128344_("statGrowth", this.statGrowth);
            compoundTag.m_128344_("statGain", this.statGain);
            compoundTag.m_128344_("statResistance", this.statResistance);
            compoundTag.m_128376_("storageNutrients", this.storageNutrients);
            compoundTag.m_128376_("storageWater", this.storageWater);
            compoundTag.m_128376_("storageWeedEX", this.storageWeedEX);
            compoundTag.m_128344_("terrainHumidity", this.terrainHumidity);
            compoundTag.m_128344_("terrainNutrients", this.terrainNutrients);
            compoundTag.m_128344_("terrainAirQuality", this.terrainAirQuality);
            compoundTag.m_128344_("currentAge", this.currentAge);
            compoundTag.m_128376_("growthPoints", this.growthPoints);
            compoundTag.m_128344_("scanLevel", this.scanLevel);
            compoundTag.m_128365_("customData", (Tag)this.customData.m_6426_());
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("crop");
        arrayList.add("currentAge");
        arrayList.add("statGrowth");
        arrayList.add("statGain");
        arrayList.add("statResistance");
        arrayList.add("storageNutrients");
        arrayList.add("storageWater");
        arrayList.add("storageWeedEX");
        arrayList.add("terrainHumidity");
        arrayList.add("terrainNutrients");
        arrayList.add("terrainAirQuality");
        arrayList.add("currentAge");
        arrayList.add("growthPoints");
        arrayList.add("scanLevel");
        arrayList.add("customData");
        arrayList.addAll(super.getNetworkedFields());
        return arrayList;
    }

    @Override
    public void onNetworkUpdate(String string) {
        this.rerender();
        super.onNetworkUpdate(string);
    }

    @Override
    public void updateEntityServer() {
        if (this.f_58857_.m_46467_() % (long)tickRate == 0L) {
            this.performTick();
        }
        if (this.dirty) {
            this.dirty = false;
            Level level = this.m_58904_();
            if (level == null) {
                return;
            }
            BlockState blockState = level.m_8055_(this.f_58858_);
            level.m_7260_(this.f_58858_, blockState, blockState, 3);
            level.m_6289_(this.f_58858_, (Block)this.getBlockType());
            level.m_7726_().m_7827_().m_7174_(this.f_58858_);
            if (!level.f_46443_) {
                for (String string : this.getNetworkedFields()) {
                    IC2.network.get(true).updateTileEntityField(this, string);
                }
            }
        }
    }

    public void performTick() {
        assert (!this.m_58904_().f_46443_);
        long l = this.f_58857_.m_46467_();
        if (l % (long)(tickRate << 2) == 0L) {
            this.updateTerrainHumidity();
            if (debug) {
                IC2.log.info(LogCategory.Block, "Crop at %s - terrain humidity: %s", this.f_58858_, this.terrainHumidity);
            }
        }
        if ((l + (long)tickRate) % (long)(tickRate << 2) == 0L) {
            this.updateTerrainNutrients();
            if (debug) {
                IC2.log.info(LogCategory.Block, "Crop at %s - terrain nutrients: %s", this.f_58858_, this.terrainNutrients);
            }
        }
        if ((l + (long)(tickRate * 2)) % (long)(tickRate << 2) == 0L) {
            this.updateTerrainAirQuality();
            if (debug) {
                IC2.log.info(LogCategory.Block, "Crop at %s - terrain air quality: %s", this.f_58858_, this.terrainAirQuality);
            }
        }
        if (!(this.crop != null || this.isCrossingBase() && this.attemptCrossing() || this.isCrossingBase() && this.attemptSpreading())) {
            if (IC2.random.m_188503_(100) == 0 && this.getStorageWeedEX() <= 0) {
                this.transformCropBlock(Ic2Crops.weed, 0);
            } else {
                if (this.getStorageWeedEX() > 0 && IC2.random.m_188503_(10) == 0) {
                    this.storageWeedEX = (short)(this.storageWeedEX - 1);
                }
                return;
            }
        }
        if (this.crop == null) {
            return;
        }
        this.crop.tick(this);
        if (debug) {
            System.out.println("Plant: " + this.getCrop().getUnlocalizedName());
        }
        if (this.crop.canGrow(this)) {
            this.performGrowthTick();
            if (this.crop == null) {
                return;
            }
            if (this.growthPoints >= this.crop.getGrowthDuration(this)) {
                this.growthPoints = 0;
                this.setCurrentAge(this.getCurrentAge() + 1);
                this.dirty = true;
            }
        }
        if (this.storageNutrients > 0) {
            this.storageNutrients = (short)(this.storageNutrients - 1);
        }
        if (this.storageWater > 0) {
            this.storageWater = (short)(this.storageWater - 1);
        }
        if (this.crop.isWeed(this) && IC2.random.m_188503_(50) - this.getStatGrowth() <= 2) {
            this.performWeedWork();
        }
    }

    public void performGrowthTick() {
        if (this.crop == null) {
            return;
        }
        if (debugGrowth) {
            IC2.log.info(LogCategory.Block, "Crop at %s - growth points (before): %s", this.f_58858_, this.growthPoints);
        }
        int n = 0;
        int n2 = 3 + IC2.random.m_188503_(7) + this.getStatGrowth();
        int n3 = (this.crop.getProperties().getTier() - 1) * 4 + this.getStatGrowth() + this.statGain + this.statResistance;
        n3 = Math.max(n3, 0);
        int n4 = this.crop.getWeightInfluences(this, this.getTerrainHumidity(), this.getTerrainNutrients(), this.getTerrainAirQuality()) * 5;
        if (n4 >= n3) {
            n = n2 * (100 + (n4 - n3)) / 100;
        } else {
            int n5 = (n3 - n4) * 4;
            if (n5 > 100 && IC2.random.m_188503_(32) > this.statResistance) {
                this.reset();
                n = 0;
            } else {
                n = n2 * (100 - n5) / 100;
                n = Math.max(n, 0);
            }
        }
        this.growthPoints = (short)(this.growthPoints + n);
        if (debugGrowth) {
            IC2.log.info(LogCategory.Block, "Crop at %s - base growth: %s", this.f_58858_, n2);
            IC2.log.info(LogCategory.Block, "Crop at %s - minimum quality: %s", this.f_58858_, n3);
            IC2.log.info(LogCategory.Block, "Crop at %s - provided quality: %s", this.f_58858_, n4);
            IC2.log.info(LogCategory.Block, "Crop at %s - total growth: %s", this.f_58858_, n);
            IC2.log.info(LogCategory.Block, "Crop at %s - growth points (after): %s", this.f_58858_, this.growthPoints);
        }
    }

    public void performWeedWork() {
        Level level = this.m_58904_();
        if (level == null) {
            return;
        }
        BlockPos blockPos = this.f_58858_.m_121945_(Util.HORIZONTAL_DIRS[IC2.random.m_188503_(4)]);
        BlockEntity blockEntity = level.m_7702_(blockPos);
        if (blockEntity instanceof TileEntityCrop) {
            CropCard cropCard;
            TileEntityCrop tileEntityCrop = (TileEntityCrop)blockEntity;
            if (debugWeedWork) {
                IC2.log.info(LogCategory.Block, "Crop at %s - trying to generate weed", blockPos);
            }
            if ((cropCard = tileEntityCrop.getCrop()) == null || !cropCard.isWeed(tileEntityCrop) && IC2.random.m_188503_(32) >= tileEntityCrop.getStatResistance() && !tileEntityCrop.hasWeedEX()) {
                int n;
                if (debugWeedWork) {
                    IC2.log.info(LogCategory.Block, "Crop at %s - weed generated", blockPos);
                }
                if ((n = Math.max(this.getStatGrowth(), tileEntityCrop.getStatGrowth())) < 31 && IC2.random.m_188499_()) {
                    ++n;
                }
                tileEntityCrop = tileEntityCrop.transformCropBlock(Crops.weed, 0);
                tileEntityCrop.setStatGrowth(n);
            }
        } else if (level.m_46859_(blockPos)) {
            BlockPos blockPos2;
            Block block;
            if (debugWeedWork) {
                IC2.log.info(LogCategory.Block, "Block at %s - trying to generate grass", blockPos);
            }
            if ((block = level.m_8055_(blockPos2 = blockPos.m_7495_()).m_60734_()) == Blocks.f_50493_ || block == Blocks.f_50034_ || block == Blocks.f_50093_) {
                level.m_7731_(blockPos2, Blocks.f_50034_.m_49966_(), 7);
                level.m_7731_(blockPos, Blocks.f_50359_.m_49966_(), 7);
            }
        }
    }

    public boolean hasWeedEX() {
        if (this.storageWeedEX > 0) {
            this.storageWeedEX = (short)(this.storageWeedEX - 5);
            return true;
        }
        return false;
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        if (this.m_58904_().f_46443_) {
            return InteractionResult.CONSUME;
        }
        return this.rightClick(player, interactionHand) ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    @Override
    protected InteractionResult onClicked(Player player) {
        if (player.m_150110_().f_35937_) {
            return InteractionResult.PASS;
        }
        if (this.crop != null) {
            System.out.println();
            return this.crop.onLeftClick(this, player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        if (this.isCrossingBase() && !this.m_58904_().f_46443_) {
            this.setCrossingBase(false);
            this.dirty = true;
            StackUtil.dropAsEntity(this.m_58904_(), this.f_58858_, new ItemStack((ItemLike)Ic2Items.CROP_STICK));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onBlockBreak() {
        if (!this.m_58904_().f_46443_) {
            this.pick();
        }
    }

    @Override
    protected List<AABB> getAabbs(boolean bl) {
        ArrayList<AABB> arrayList = new ArrayList<AABB>();
        if (bl) {
            arrayList.add(new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        } else {
            arrayList.add(new AABB((double)0.2f, 0.0, (double)0.2f, (double)0.8f, (double)0.85f, (double)0.8f));
        }
        return arrayList;
    }

    public boolean rightClick(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        boolean bl = player.m_150110_().f_35937_;
        if (!StackUtil.isEmpty(itemStack)) {
            if (this.crop == null && !this.isCrossingBase() && itemStack.m_41720_() == Ic2Items.CROP_STICK) {
                if (!bl) {
                    StackUtil.consumeOrError(player, interactionHand, 1);
                }
                this.setCrossingBase(true);
                this.dirty = true;
                return true;
            }
            if (this.crop != null && StackUtil.checkItemEquality(itemStack, Ic2Items.FERTILIZER)) {
                if (this.applyFertilizer(true)) {
                    this.dirty = true;
                }
                if (!bl) {
                    StackUtil.consumeOrError(player, interactionHand, 1);
                }
                return true;
            }
            Ic2FluidStack ic2FluidStack = Ic2FluidStack.create((Fluid)Fluids.f_76193_, Integer.MAX_VALUE);
            int n = FluidHandler.drainMb(itemStack, ic2FluidStack, true, null);
            if (n > 0) {
                if ((n = this.applyHydration(n, true)) > 0) {
                    ic2FluidStack.setAmountMb(n);
                    MutableObject mutableObject = new MutableObject();
                    n = FluidHandler.drainMb(itemStack, ic2FluidStack, false, (Mutable<ItemStack>)mutableObject);
                    this.applyHydration(n, false);
                    StackUtil.set(player, interactionHand, (ItemStack)mutableObject.getValue());
                    this.dirty = true;
                }
                return true;
            }
            ic2FluidStack = Ic2FluidStack.create(Ic2Fluids.WEED_EX.still, Integer.MAX_VALUE);
            n = FluidHandler.drainMb(itemStack, ic2FluidStack, true, null);
            if (n > 0) {
                if ((n = this.applyWeedEx(n, false, true, true)) > 0) {
                    ic2FluidStack.setAmountMb(n);
                    MutableObject mutableObject = new MutableObject();
                    n = FluidHandler.drainMb(itemStack, ic2FluidStack, false, (Mutable<ItemStack>)mutableObject);
                    this.applyWeedEx(n, false, true, false);
                    StackUtil.set(player, interactionHand, (ItemStack)mutableObject.getValue());
                    this.dirty = true;
                }
                return true;
            }
            if (this.crop == null && !this.isCrossingBase() && Crops.instance.getBaseSeed(itemStack) != null) {
                BaseSeed baseSeed = Crops.instance.getBaseSeed(itemStack);
                if (!bl) {
                    StackUtil.consumeOrError(player, interactionHand, 1);
                }
                TileEntityCrop tileEntityCrop = this.transformCropBlock(baseSeed.crop, baseSeed.size);
                tileEntityCrop.setStatGain(baseSeed.statGain);
                tileEntityCrop.setStatGrowth(baseSeed.statGrowth);
                tileEntityCrop.setStatResistance(baseSeed.statResistance);
                return true;
            }
        }
        if (this.crop == null) {
            return false;
        }
        return this.crop.onRightClick(this, player);
    }

    public boolean tryPlantIn(CropCard cropCard, int n, int n2, int n3, int n4, int n5) {
        if (cropCard == null || cropCard == Ic2Crops.weed || this.isCrossingBase()) {
            return false;
        }
        if (!cropCard.canGrow(this)) {
            return false;
        }
        TileEntityCrop tileEntityCrop = this.transformCropBlock(cropCard, n);
        tileEntityCrop.setStatGain(n3);
        tileEntityCrop.setStatGrowth(n2);
        tileEntityCrop.setStatResistance(n4);
        tileEntityCrop.setScanLevel(n5);
        NetworkHelper.sendInitialData(this);
        return true;
    }

    @Override
    public void onEntityCollision(Entity entity) {
        if (this.crop == null) {
            return;
        }
        if (this.crop.onEntityCollision(this, entity)) {
            Level level = this.m_58904_();
            if (level.f_46443_) {
                return;
            }
            if (IC2.random.m_188503_(100) == 0 && IC2.random.m_188503_(40) > this.statResistance) {
                this.reset();
                level.m_7731_(this.f_58858_.m_7495_(), Blocks.f_50493_.m_49966_(), 7);
                if (debugCollision) {
                    IC2.log.info(LogCategory.Block, "Crop at %s - crop was trampled", this.f_58858_);
                }
            }
        }
    }

    public void updateTerrainAirQuality() {
        Level level = this.m_58904_();
        int n = 0;
        int n2 = (int)Math.floor((double)(this.f_58858_.m_123342_() - 40) / 15.0);
        if (n2 > 2) {
            n2 = 2;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        n += n2;
        int n3 = 9;
        for (int i = this.f_58858_.m_123341_() - 1; i < this.f_58858_.m_123341_() + 1 && n3 > 0; ++i) {
            for (int j = this.f_58858_.m_123343_() - 1; j < this.f_58858_.m_123343_() + 1 && n3 > 0; ++j) {
                BlockPos blockPos = new BlockPos(i, this.f_58858_.m_123342_(), j);
                if (!level.m_8055_(blockPos).m_60838_((BlockGetter)level, blockPos) && !(level.m_7702_(new BlockPos(i, this.f_58858_.m_123342_(), j)) instanceof TileEntityCrop)) continue;
                --n3;
            }
        }
        n += n3 / 2;
        if (level.m_45527_(this.f_58858_.m_7494_())) {
            n += 4;
        }
        this.setTerrainAirQuality(n);
    }

    public void updateTerrainHumidity() {
        Level level = this.m_58904_();
        int n = Crops.instance.getHumidityBiomeBonus(BiomeUtil.getBiome((LevelReader)level, this.f_58858_));
        if ((Integer)level.m_8055_(this.f_58858_.m_7495_()).m_61143_((Property)FarmBlock.f_53243_) >= 7) {
            n += 2;
        }
        if (this.getStorageWater() >= 5) {
            n += 2;
        }
        this.setTerrainHumidity(n += (this.getStorageWater() + 24) / 25);
    }

    public void updateTerrainNutrients() {
        Level level = this.m_58904_();
        int n = Crops.instance.getNutrientBiomeBonus(BiomeUtil.getBiome((LevelReader)level, this.f_58858_));
        for (int i = 1; i < 5 && level.m_8055_(this.f_58858_.m_6625_(i)).m_60734_() == Blocks.f_50493_; ++i) {
            ++n;
        }
        this.setTerrainNutrients(n += (this.getStorageNutrients() + 19) / 20);
    }

    @Override
    public CropCard getCrop() {
        return this.crop;
    }

    @Override
    public void setCrop(CropCard cropCard) {
        TileEntityCrop tileEntityCrop = this.transformCropBlock(cropCard.getCropBlock());
        tileEntityCrop.updateTerrainHumidity();
        tileEntityCrop.updateTerrainNutrients();
        tileEntityCrop.updateTerrainNutrients();
    }

    @Override
    public int getCurrentAge() {
        if (this.crop == null) {
            return 0;
        }
        return (Integer)this.m_58900_().m_61143_((Property)this.getBlockType().getAgeProperty());
    }

    @Override
    public void setCurrentAge(int n) {
        this.currentAge = (byte)n;
        this.withCropAge(n);
    }

    @Override
    public int getStatGrowth() {
        return this.statGrowth;
    }

    @Override
    public void setStatGrowth(int n) {
        this.statGrowth = (byte)n;
    }

    @Override
    public int getStatGain() {
        return this.statGain;
    }

    @Override
    public void setStatGain(int n) {
        this.statGain = (byte)n;
    }

    @Override
    public int getStatResistance() {
        return this.statResistance;
    }

    @Override
    public void setStatResistance(int n) {
        this.statResistance = (byte)n;
    }

    @Override
    public int getStorageNutrients() {
        return this.storageNutrients;
    }

    @Override
    public void setStorageNutrients(int n) {
        this.storageNutrients = (short)n;
    }

    @Override
    public int getStorageWater() {
        return this.storageWater;
    }

    @Override
    public void setStorageWater(int n) {
        this.storageWater = (short)n;
    }

    @Override
    public int getStorageWeedEX() {
        return this.storageWeedEX;
    }

    @Override
    public void setStorageWeedEX(int n) {
        this.storageWeedEX = (short)n;
    }

    @Override
    public int getTerrainAirQuality() {
        return this.terrainAirQuality;
    }

    public void setTerrainAirQuality(int n) {
        this.terrainAirQuality = (byte)n;
    }

    @Override
    public int getTerrainHumidity() {
        return this.terrainHumidity;
    }

    public void setTerrainHumidity(int n) {
        this.terrainHumidity = (byte)n;
    }

    @Override
    public int getTerrainNutrients() {
        return this.terrainNutrients;
    }

    public void setTerrainNutrients(int n) {
        this.terrainNutrients = (byte)n;
    }

    @Override
    public int getScanLevel() {
        return this.scanLevel;
    }

    @Override
    public void setScanLevel(int n) {
        this.scanLevel = (byte)n;
    }

    @Override
    public int getGrowthPoints() {
        return this.growthPoints;
    }

    @Override
    public void setGrowthPoints(int n) {
        this.growthPoints = (short)n;
    }

    @Override
    public boolean isCrossingBase() {
        if (this.crop != null) {
            return false;
        }
        if (this.f_58857_ == null) {
            return false;
        }
        return (Boolean)this.f_58857_.m_8055_(this.f_58858_).m_61143_((Property)Ic2TileEntityBlock.CROSSING_BASE);
    }

    @Override
    public void setCrossingBase(boolean bl) {
        if (this.crop != null) {
            return;
        }
        if (this.f_58857_ == null) {
            return;
        }
        this.f_58857_.m_46597_(this.f_58858_, (BlockState)Ic2Blocks.CROP_STICK.m_49966_().m_61124_((Property)Ic2TileEntityBlock.CROSSING_BASE, (Comparable)Boolean.valueOf(bl)));
    }

    @Override
    public CompoundTag getCustomData() {
        return this.customData;
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
    @Deprecated
    public BlockPos getLocation() {
        return this.f_58858_;
    }

    @Override
    public int getLightLevel() {
        return this.m_58904_().m_46803_(this.f_58858_);
    }

    @Override
    public int getLightValue() {
        return this.crop == null ? 0 : this.crop.getEmittedLight(this);
    }

    @Override
    public boolean pick() {
        int n;
        if (this.crop == null) {
            return false;
        }
        Level level = this.m_58904_();
        if (level == null) {
            return false;
        }
        boolean bl = this.crop.canBeHarvested(this);
        float f = this.crop.dropSeedChance(this);
        f = (float)((double)f * Math.pow(1.1, this.statResistance));
        int n2 = 0;
        if (bl) {
            if (level.f_46441_.m_188501_() <= (f + 1.0f) * 0.8f) {
                ++n2;
            }
            float f2 = this.crop.dropSeedChance(this) + (float)this.getStatGrowth() / 100.0f;
            for (n = 23; n < this.statGain; ++n) {
                f2 *= 0.95f;
            }
            if (level.f_46441_.m_188501_() <= f2) {
                ++n2;
            }
        } else if (level.f_46441_.m_188501_() <= f * 1.5f) {
            ++n2;
        }
        ItemStack[] itemStackArray = new ItemStack[n2];
        for (n = 0; n < n2; ++n) {
            itemStackArray[n] = this.crop.getSeeds(this);
        }
        this.reset();
        if (!level.f_46443_) {
            for (ItemStack itemStack : itemStackArray) {
                if (itemStack.m_41720_() != Ic2Items.CROP_SEED_BACK) {
                    itemStack.m_41751_(null);
                }
                StackUtil.dropAsEntity(level, this.f_58858_, itemStack);
            }
        }
        return true;
    }

    @Override
    public boolean performManualHarvest() {
        List<ItemStack> list = this.performHarvest();
        if (list != null && !list.isEmpty()) {
            Level level = this.m_58904_();
            list.forEach(itemStack -> StackUtil.dropAsEntity(level, this.f_58858_, itemStack));
            return true;
        }
        return false;
    }

    @Override
    public List<ItemStack> performHarvest() {
        int n2;
        if (this.crop == null || !this.crop.canBeHarvested(this)) {
            return null;
        }
        double d = this.crop.dropGainChance();
        d *= Math.pow(1.03, this.getStatGain());
        if (debug) {
            System.out.println("chance: " + d);
            n2 = 0;
            for (int i = 0; i < 200; ++i) {
                int n3 = (int)Math.max(0L, Math.round(IC2.random.m_188583_() * d * 0.6827 + d));
                n2 += n3;
                System.out.print(n3 + " ");
            }
            System.out.println();
            System.out.println("sum: " + n2 + ", avg: " + (double)n2 / 200.0);
        }
        n2 = (int)Math.max(0L, Math.round(IC2.random.m_188583_() * d * 0.6827 + d));
        List<ItemStack> list = IntStream.range(0, n2).mapToObj(n -> {
            ItemStack[] itemStackArray = this.crop.getGains(this);
            return Arrays.stream(itemStackArray).map(itemStack -> !StackUtil.isEmpty(itemStack) && IC2.random.m_188503_(100) <= this.getStatGain() ? StackUtil.incSize(itemStack) : itemStack);
        }).flatMap(Function.identity()).collect(Collectors.toList());
        this.setCurrentAge(this.crop.getAgeAfterHarvest(this));
        this.dirty = true;
        return list;
    }

    @Override
    public void reset() {
        if (this.f_58857_ == null) {
            return;
        }
        this.f_58857_.m_46597_(this.f_58858_, (BlockState)Ic2Blocks.CROP_STICK.m_49966_().m_61124_((Property)Ic2TileEntityBlock.CROSSING_BASE, (Comparable)Boolean.valueOf(false)));
    }

    public void resetData() {
        this.customData = new CompoundTag();
        this.statGain = 0;
        this.statResistance = 0;
        this.statGrowth = 0;
        this.terrainAirQuality = (byte)-1;
        this.terrainHumidity = (byte)-1;
        this.terrainNutrients = (byte)-1;
        this.growthPoints = 0;
        this.scanLevel = 0;
        this.currentAge = 0;
        this.dirty = true;
    }

    @Override
    public void updateState() {
        BlockState blockState = this.m_58900_();
        Objects.requireNonNull(this.m_58904_()).m_7260_(this.f_58858_, blockState, blockState, 2);
    }

    @Override
    public boolean isBlockBelow(Block block) {
        if (this.crop == null) {
            return false;
        }
        Level level = this.m_58904_();
        for (int i = 1; i < this.crop.getRootsLength(this); ++i) {
            BlockPos blockPos = this.f_58858_.m_6625_(i);
            BlockState blockState = level.m_8055_(blockPos);
            Block block2 = blockState.m_60734_();
            if (blockState.m_60795_()) {
                return false;
            }
            if (block2 != block) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlockBelow(TagKey<Block> tagKey) {
        if (this.crop == null) {
            return false;
        }
        Level level = this.m_58904_();
        for (int i = 1; i < this.crop.getRootsLength(this); ++i) {
            BlockPos blockPos = this.f_58858_.m_6625_(i);
            BlockState blockState = level.m_8055_(blockPos);
            if (blockState.m_60795_()) {
                return false;
            }
            if (!blockState.m_204336_(tagKey)) continue;
            return true;
        }
        return false;
    }

    @Override
    public ItemStack generateSeeds(CropCard cropCard, int n, int n2, int n3, int n4) {
        return ItemCropSeed.generateItemStackFromValues(cropCard, n, n2, n3, n4);
    }

    @Override
    protected int getLightOpacity() {
        return 0;
    }

    public TileEntityCrop transformCropBlock(CropCard cropCard, int n) {
        if (this.f_58857_ == null) {
            return null;
        }
        Block block = cropCard.getCropBlock();
        if (!(block instanceof Ic2TileEntityBlock)) {
            return null;
        }
        Ic2TileEntityBlock ic2TileEntityBlock = (Ic2TileEntityBlock)block;
        block = ic2TileEntityBlock.m_49966_();
        if (!block.m_60713_(Ic2Blocks.CROP_STICK)) {
            block = (BlockState)block.m_61124_((Property)ic2TileEntityBlock.getAgeProperty(), (Comparable)Integer.valueOf(n));
        }
        this.f_58857_.m_46597_(this.f_58858_, (BlockState)block);
        return (TileEntityCrop)this.f_58857_.m_7702_(this.f_58858_);
    }

    public TileEntityCrop transformCropBlock(Block block) {
        if (this.f_58857_ == null) {
            return null;
        }
        if (!(block instanceof Ic2TileEntityBlock)) {
            return null;
        }
        Ic2TileEntityBlock ic2TileEntityBlock = (Ic2TileEntityBlock)block;
        BlockState blockState = block.m_49966_();
        if (!blockState.m_60713_(Ic2Blocks.CROP_STICK)) {
            blockState = (BlockState)blockState.m_61124_((Property)ic2TileEntityBlock.getAgeProperty(), (Comparable)Integer.valueOf(this.currentAge));
        }
        this.f_58857_.m_46597_(this.f_58858_, blockState);
        return (TileEntityCrop)this.f_58857_.m_7702_(this.f_58858_);
    }

    public boolean resetCropBlock(Block block) {
        if (this.f_58857_ == null) {
            return false;
        }
        this.f_58857_.m_46597_(this.f_58858_, block.m_49966_());
        return true;
    }

    public boolean withCropAge(Integer n) {
        if (this.f_58857_ == null) {
            return false;
        }
        this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_((Property)this.getBlockType().getAgeProperty(), (Comparable)n));
        return true;
    }

    @Override
    public boolean wrenchCanRemove(Player player) {
        return false;
    }

    @Override
    protected ItemStack getPickBlock(Player player, BlockHitResult blockHitResult) {
        if (this.crop == null) {
            return new ItemStack((ItemLike)Ic2Items.CROP_STICK);
        }
        return this.generateSeeds(this.crop, this.statGrowth, this.statGain, this.statResistance, this.scanLevel);
    }

    private boolean attemptCrossing() {
        int n;
        if (IC2.random.m_188503_(3) != 0) {
            return false;
        }
        ArrayList<TileEntityCrop> arrayList = new ArrayList<TileEntityCrop>(4);
        this.checkCrossingAvailability(this.f_58858_.m_122012_(), arrayList);
        this.checkCrossingAvailability(this.f_58858_.m_122019_(), arrayList);
        this.checkCrossingAvailability(this.f_58858_.m_122029_(), arrayList);
        this.checkCrossingAvailability(this.f_58858_.m_122024_(), arrayList);
        if (debug) {
            System.out.print("Attempted cross with " + arrayList.size() + " plants: ");
            arrayList.stream().map(tileEntityCrop -> tileEntityCrop.getCrop().getUnlocalizedName() + " ").forEach(System.out::print);
            System.out.println();
        }
        if (arrayList.size() < 2) {
            return false;
        }
        CropCard[] cropCardArray = Crops.instance.getCrops().toArray(new CropCard[0]);
        if (cropCardArray.length == 0) {
            return false;
        }
        int[] nArray = new int[cropCardArray.length];
        int n2 = 0;
        for (n = 0; n < nArray.length; ++n) {
            CropCard cropCard = cropCardArray[n];
            if (cropCard.canGrow(this)) {
                for (TileEntityCrop tileEntityCrop2 : arrayList) {
                    n2 += this.calculateRatioFor(cropCard, tileEntityCrop2.getCrop());
                }
            }
            nArray[n] = n2;
        }
        if (debugChance) {
            n = 0;
            for (int i = 0; i < cropCardArray.length; ++i) {
                int n3 = nArray[i];
                System.out.println(String.format("%s: %.1f%% %d%n", cropCardArray[i].getUnlocalizedName(), (double)(n3 - n) * 100.0 / (double)n2, nArray[i]));
                n = n3;
            }
        }
        n = IC2.random.m_188503_(n2);
        if (debug) {
            System.out.printf("rnd: %d / %d%n", n, n2);
        }
        int n4 = 0;
        int n5 = nArray.length - 1;
        while (n4 < n5) {
            int n6 = (n4 + n5) / 2;
            int n7 = nArray[n6];
            if (debug) {
                System.out.printf("min: %d, max: %d, cur: %d, value: %d%n", n4, n5, n6, n7);
            }
            if (n < n7) {
                n5 = n6;
                continue;
            }
            n4 = n6 + 1;
        }
        if (debug) {
            System.out.printf("result: %s (%d %d)%n", cropCardArray[n4].getUnlocalizedName(), n4, n5);
        }
        assert (n4 == n5);
        assert (n4 >= 0 && n4 < nArray.length);
        assert (nArray[n4] > n);
        assert (n4 == 0 || nArray[n4 - 1] <= n);
        this.statGrowth = 0;
        this.statResistance = 0;
        this.statGain = 0;
        for (TileEntityCrop tileEntityCrop3 : arrayList) {
            this.statGrowth = (byte)(this.statGrowth + tileEntityCrop3.statGrowth);
            this.statResistance = (byte)(this.statResistance + tileEntityCrop3.statResistance);
            this.statGain = (byte)(this.statGain + tileEntityCrop3.statGain);
        }
        int n8 = arrayList.size();
        this.statGrowth = (byte)(this.statGrowth / n8);
        this.statResistance = (byte)(this.statResistance / n8);
        this.statGain = (byte)(this.statGain / n8);
        this.statGrowth = (byte)(this.statGrowth + (IC2.random.m_188503_(1 + 2 * n8) - n8));
        this.statGain = (byte)(this.statGain + (IC2.random.m_188503_(1 + 2 * n8) - n8));
        this.statResistance = (byte)(this.statResistance + (IC2.random.m_188503_(1 + 2 * n8) - n8));
        this.statGrowth = (byte)Util.limit(this.statGrowth, 0, 31);
        this.statGain = (byte)Util.limit(this.statGain, 0, 31);
        this.statResistance = (byte)Util.limit(this.statResistance, 0, 31);
        TileEntityCrop tileEntityCrop4 = this.transformCropBlock(cropCardArray[n4], 0);
        tileEntityCrop4.setCurrentAge(0);
        tileEntityCrop4.setStatResistance(this.statResistance);
        tileEntityCrop4.setStatGain(this.statGain);
        tileEntityCrop4.setStatGrowth(this.statGrowth);
        this.dirty = true;
        return true;
    }

    private boolean attemptSpreading() {
        ArrayList<TileEntityCrop> arrayList = new ArrayList<TileEntityCrop>(4);
        for (Direction object2 : Util.HORIZONTAL_DIRS) {
            BlockEntity blockEntity = this.m_58904_().m_7702_(this.f_58858_.m_121945_(object2));
            if (!(blockEntity instanceof TileEntityCrop)) continue;
            TileEntityCrop tileEntityCrop = (TileEntityCrop)blockEntity;
            arrayList.add(tileEntityCrop);
        }
        if (arrayList.size() != 1) {
            return false;
        }
        TileEntityCrop tileEntityCrop = (TileEntityCrop)arrayList.get(0);
        CropCard cropCard = tileEntityCrop.getCrop();
        if (cropCard == null) {
            return false;
        }
        if (!cropCard.canGrow(this) || !cropCard.canCross(tileEntityCrop)) {
            return false;
        }
        int n = 4;
        if (tileEntityCrop.statGrowth >= 16) {
            ++n;
        }
        if (tileEntityCrop.statGrowth >= 30) {
            ++n;
        }
        if (tileEntityCrop.statResistance >= 28) {
            n += 27 - tileEntityCrop.statResistance;
        }
        if (n < IC2.random.m_188503_(16)) {
            return false;
        }
        TileEntityCrop tileEntityCrop2 = this.transformCropBlock(tileEntityCrop.crop, 0);
        tileEntityCrop2.setStatGrowth(tileEntityCrop.statGrowth);
        tileEntityCrop2.setStatResistance(tileEntityCrop.statResistance);
        tileEntityCrop2.setStatGain(tileEntityCrop.statGain);
        this.dirty = true;
        return true;
    }

    private int calculateRatioFor(CropCard cropCard, CropCard cropCard2) {
        if (cropCard == cropCard2) {
            return 500;
        }
        int n = 0;
        int[] objectArray = cropCard2.getProperties().getAllProperties();
        int[] nArray = cropCard.getProperties().getAllProperties();
        assert (objectArray.length == nArray.length);
        for (int i = 0; i < 5; ++i) {
            int n3 = Math.abs(objectArray[i] - nArray[i]);
            n += -n3 + 2;
        }
        for (String string : cropCard.getAttributes()) {
            for (String string2 : cropCard2.getAttributes()) {
                if (!string.equalsIgnoreCase(string2)) continue;
                n += 5;
            }
        }
        int n2 = cropCard.getProperties().getTier() - cropCard2.getProperties().getTier();
        if (n2 > 1) {
            n -= 2 * n2;
        }
        if (n2 < -3) {
            n -= -n2;
        }
        return Math.max(n, 0);
    }

    private void checkCrossingAvailability(BlockPos blockPos, List<TileEntityCrop> list) {
        BlockEntity blockEntity = this.m_58904_().m_7702_(blockPos);
        if (!(blockEntity instanceof TileEntityCrop)) {
            return;
        }
        TileEntityCrop tileEntityCrop = (TileEntityCrop)blockEntity;
        CropCard cropCard = tileEntityCrop.getCrop();
        if (cropCard == null) {
            return;
        }
        if (!cropCard.canGrow(this) || !cropCard.canCross(tileEntityCrop)) {
            return;
        }
        int n = 4;
        if (tileEntityCrop.statGrowth >= 16) {
            ++n;
        }
        if (tileEntityCrop.statGrowth >= 30) {
            ++n;
        }
        if (tileEntityCrop.statResistance >= 28) {
            n += 27 - tileEntityCrop.statResistance;
        }
        if (n >= IC2.random.m_188503_(16)) {
            list.add(tileEntityCrop);
        }
    }

    private void checkSpreadingAvailability(BlockPos blockPos, TileEntityCrop tileEntityCrop) {
        BlockEntity blockEntity = this.m_58904_().m_7702_(blockPos);
        if (!(blockEntity instanceof TileEntityCrop)) {
            return;
        }
        TileEntityCrop tileEntityCrop2 = (TileEntityCrop)blockEntity;
        CropCard cropCard = tileEntityCrop2.getCrop();
        if (cropCard == null) {
            return;
        }
        if (!cropCard.canGrow(this) || !cropCard.canCross(tileEntityCrop2)) {
            return;
        }
        int n = 4;
        if (tileEntityCrop2.statGrowth >= 16) {
            ++n;
        }
        if (tileEntityCrop2.statGrowth >= 30) {
            ++n;
        }
        if (tileEntityCrop2.statResistance >= 28) {
            n += 27 - tileEntityCrop2.statResistance;
        }
        if (n >= IC2.random.m_188503_(16)) {
            tileEntityCrop = tileEntityCrop2;
        }
    }

    @Override
    protected void onNeighborChange(Block block, BlockPos blockPos) {
        super.onNeighborChange(block, blockPos);
        Level level = this.m_58904_();
        if (!CropSoilType.contains(level.m_8055_(this.f_58858_.m_7495_()).m_60734_())) {
            this.pick();
            level.m_7471_(this.f_58858_, false);
        }
    }

    public int applyHydration(int n, boolean bl) {
        int n2 = 200 - this.storageWater;
        if (n2 <= 0) {
            return 0;
        }
        n = Math.min(n, n2);
        if (!bl) {
            this.storageWater = (short)(this.storageWater + n);
        }
        return n;
    }

    public int applyWeedEx(int n, boolean bl, boolean bl2, boolean bl3) {
        int n2 = (bl2 ? 100 : 150) - this.storageWeedEX;
        if (bl) {
            if (n2 <= n) {
                return 0;
            }
        } else {
            if (n2 <= 0) {
                return 0;
            }
            n = Math.min(n, n2);
        }
        if (!bl3) {
            this.storageWeedEX = (short)(this.storageWeedEX + n);
        }
        return n;
    }

    public boolean applyFertilizer(boolean bl) {
        if (this.storageNutrients >= 100) {
            return false;
        }
        this.storageNutrients = (short)(this.storageNutrients + (bl ? 100 : 90));
        return true;
    }
}
