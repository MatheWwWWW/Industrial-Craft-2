/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFarmland
 *  net.minecraft.block.SoundType
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.EnumSkyBlock
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.Biome
 *  net.minecraftforge.common.EnumPlantType
 *  net.minecraftforge.common.property.IUnlistedProperty
 *  net.minecraftforge.fluids.FluidRegistry
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandlerItem
 *  net.minecraftforge.oredict.OreDictionary
 */
package ic2.core.crop;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import ic2.api.network.NetworkHelper;
import ic2.core.IC2;
import ic2.core.Ic2Player;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.state.Ic2BlockState;
import ic2.core.block.state.UnlistedProperty;
import ic2.core.crop.IC2Crops;
import ic2.core.item.ItemCropSeed;
import ic2.core.item.type.CropResItemType;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.util.BiomeUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCrop
extends TileEntityBlock
implements ICropTile {
    private long ticker = 0L;
    public boolean dirty = true;
    public static int tickrate = 256;
    private CropCard crop = null;
    private byte biomeHumidityBonus = 0;
    private byte statGrowth;
    private byte statGain;
    private byte statResistance;
    private short storageNutrients;
    private short storageWater;
    private short storageWeedEX;
    private byte terrainAirQuality;
    private byte terrainHumidity;
    private byte terrainNutrients;
    private byte currentSize;
    private short growthPoints = 0;
    private byte scanLevel;
    private boolean crossingBase;
    private NBTTagCompound customData = new NBTTagCompound();
    public static final IUnlistedProperty<CropRenderState> renderStateProperty = new UnlistedProperty<CropRenderState>("renderstate", CropRenderState.class);
    private volatile CropRenderState cropRenderState;
    public static final boolean debug = System.getProperty("ic2.crops.debug") != null;
    public static final boolean debugChance = debug && System.getProperty("ic2.crops.debug").contains("chance");
    public static final boolean debugGrowth = debug && System.getProperty("ic2.crops.debug").contains("growth");
    public static final boolean debugWeedWork = debug && System.getProperty("ic2.crops.debug").contains("weedwork");
    public static final boolean debugCollision = debug && System.getProperty("ic2.crops.debug").contains("collision");
    public static final boolean debugTerrain = debug && System.getProperty("ic2.crops.debug").contains("terrain");

    public TileEntityCrop() {
        if (debug) {
            IC2.log.info(LogCategory.Crop, "Debug mode is running");
        }
    }

    @Override
    public void func_145839_a(NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.crossingBase = nbt.func_74767_n("crossingBase");
        if (nbt.func_74764_b("cropOwner") && nbt.func_74764_b("cropId")) {
            this.crop = Crops.instance.getCropCard(nbt.func_74779_i("cropOwner"), nbt.func_74779_i("cropId"));
            this.statGrowth = nbt.func_74771_c("statGrowth");
            this.statGain = nbt.func_74771_c("statGain");
            this.statResistance = nbt.func_74771_c("statResistance");
            this.storageNutrients = nbt.func_74765_d("storageNutrients");
            this.storageWater = nbt.func_74765_d("storageWater");
            this.storageWeedEX = nbt.func_74765_d("storageWeedEX");
            this.terrainHumidity = nbt.func_74771_c("terrainHumidity");
            this.terrainNutrients = nbt.func_74771_c("terrainNutrients");
            this.terrainAirQuality = nbt.func_74771_c("terrainAirQuality");
            this.currentSize = nbt.func_74771_c("currentSize");
            this.growthPoints = nbt.func_74765_d("growthPoints");
            this.scanLevel = nbt.func_74771_c("scanLevel");
            this.customData = nbt.func_74775_l("customData");
        }
    }

    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
        super.func_189515_b(nbt);
        nbt.func_74757_a("crossingBase", this.crossingBase);
        if (this.crop != null) {
            nbt.func_74778_a("cropOwner", this.crop.getOwner());
            nbt.func_74778_a("cropId", this.crop.getId());
            nbt.func_74774_a("statGrowth", this.statGrowth);
            nbt.func_74774_a("statGain", this.statGain);
            nbt.func_74774_a("statResistance", this.statResistance);
            nbt.func_74777_a("storageNutrients", this.storageNutrients);
            nbt.func_74777_a("storageWater", this.storageWater);
            nbt.func_74777_a("storageWeedEX", this.storageWeedEX);
            nbt.func_74774_a("terrainHumidity", this.terrainHumidity);
            nbt.func_74774_a("terrainNutrients", this.terrainNutrients);
            nbt.func_74774_a("terrainAirQuality", this.terrainAirQuality);
            nbt.func_74774_a("currentSize", this.currentSize);
            nbt.func_74777_a("growthPoints", this.growthPoints);
            nbt.func_74774_a("scanLevel", this.scanLevel);
            nbt.func_74782_a("customData", (NBTBase)this.customData.func_74737_b());
        }
        return nbt;
    }

    @Override
    public List<String> getNetworkedFields() {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add("crop");
        ret.add("currentSize");
        ret.add("statGrowth");
        ret.add("statGain");
        ret.add("statResistance");
        ret.add("storageNutrients");
        ret.add("storageWater");
        ret.add("storageWeedEX");
        ret.add("terrainHumidity");
        ret.add("terrainNutrients");
        ret.add("terrainAirQuality");
        ret.add("currentSize");
        ret.add("growthPoints");
        ret.add("scanLevel");
        ret.add("crossingBase");
        ret.add("customData");
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    @Override
    public void onNetworkUpdate(String field) {
        this.updateRenderState();
        this.rerender();
        super.onNetworkUpdate(field);
    }

    @Override
    protected EnumPlantType getPlantType() {
        return EnumPlantType.Crop;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.func_145837_r()) {
            this.ticker = IC2.random.nextInt(256);
        }
        this.updateBiomeHumidityBonus();
        if (this.func_145831_w().field_72995_K) {
            this.updateRenderState();
        }
    }

    @Override
    public void updateEntityServer() {
        super.updateEntityServer();
        if (!this.func_145837_r()) {
            ++this.ticker;
        }
        if (this.ticker % (long)tickrate == 0L) {
            this.performTick();
        }
        if (this.dirty) {
            this.dirty = false;
            IBlockState state = this.field_145850_b.func_180495_p(this.field_174879_c);
            this.field_145850_b.func_184138_a(this.field_174879_c, state, state, 3);
            this.field_145850_b.func_190522_c(this.field_174879_c, (Block)this.getBlockType());
            this.field_145850_b.func_180500_c(EnumSkyBlock.BLOCK, this.field_174879_c);
            if (!this.field_145850_b.field_72995_K) {
                for (String field : this.getNetworkedFields()) {
                    IC2.network.get(true).updateTileEntityField(this, field);
                }
            }
        }
    }

    public void performTick() {
        assert (!this.field_145850_b.field_72995_K);
        if (this.ticker % (long)(tickrate * 10 << 2) == 0L) {
            this.updateBiomeHumidityBonus();
            if (debug) {
                IC2.log.info(LogCategory.Crop, "Crop at %s - biomeHumidityBonus: %s", this.field_174879_c, this.biomeHumidityBonus);
            }
        }
        if (this.ticker % (long)(tickrate << 2) == 0L) {
            this.updateTerrainHumidity();
            if (debug) {
                IC2.log.info(LogCategory.Crop, "Crop at %s - terrain humidity: %s", this.field_174879_c, this.terrainHumidity);
            }
        }
        if ((this.ticker + (long)tickrate) % (long)(tickrate << 2) == 0L) {
            this.updateTerrainNutrients();
            if (debug) {
                IC2.log.info(LogCategory.Crop, "Crop at %s - terrain nutrients: %s", this.field_174879_c, this.terrainNutrients);
            }
        }
        if ((this.ticker + (long)(tickrate * 2)) % (long)(tickrate << 2) == 0L) {
            this.updateTerrainAirQuality();
            if (debug) {
                IC2.log.info(LogCategory.Crop, "Crop at %s - terrain air quality: %s", this.field_174879_c, this.terrainAirQuality);
            }
        }
        if (!(this.crop != null || this.isCrossingBase() && this.attemptCrossing() || this.isCrossingBase() && this.attemptSpreading())) {
            if (IC2.random.nextInt(100) == 0 && this.getStorageWeedEX() <= 0) {
                this.reset();
                this.crop = IC2Crops.weed;
                this.setCurrentSize(1);
            } else {
                if (this.getStorageWeedEX() > 0 && IC2.random.nextInt(10) == 0) {
                    this.storageWeedEX = (short)(this.storageWeedEX - 1);
                }
                return;
            }
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
                this.setCurrentSize(this.getCurrentSize() + 1);
                this.dirty = true;
            }
        }
        if (this.storageNutrients > 0) {
            this.storageNutrients = (short)(this.storageNutrients - 1);
        }
        if (this.storageWater > 0) {
            this.storageWater = (short)(this.storageWater - 1);
        }
        if (this.crop.isWeed(this) && IC2.random.nextInt(50) - this.getStatGrowth() <= 2) {
            this.performWeedWork();
        }
    }

    public void performGrowthTick() {
        if (this.crop == null) {
            return;
        }
        if (debugGrowth) {
            IC2.log.info(LogCategory.Crop, "Crop at %s - growth points (before): %s", this.field_174879_c, this.growthPoints);
        }
        int totalGrowth = 0;
        int baseGrowth = 3 + IC2.random.nextInt(7) + this.getStatGrowth();
        int minimumQuality = (this.crop.getProperties().getTier() - 1) * 4 + this.getStatGrowth() + this.statGain + this.statResistance;
        minimumQuality = minimumQuality < 0 ? 0 : minimumQuality;
        int providedQuality = this.crop.getWeightInfluences(this, this.getTerrainHumidity(), this.getTerrainNutrients(), this.getTerrainAirQuality()) * 5;
        if (providedQuality >= minimumQuality) {
            totalGrowth = baseGrowth * (100 + (providedQuality - minimumQuality)) / 100;
        } else {
            int aux = (minimumQuality - providedQuality) * 4;
            if (aux > 100 && IC2.random.nextInt(32) > this.statResistance) {
                this.reset();
                totalGrowth = 0;
            } else {
                totalGrowth = baseGrowth * (100 - aux) / 100;
                totalGrowth = totalGrowth < 0 ? 0 : totalGrowth;
            }
        }
        this.growthPoints = (short)(this.growthPoints + totalGrowth);
        if (debugGrowth) {
            IC2.log.info(LogCategory.Crop, "Crop at %s - base growth: %s", this.field_174879_c, baseGrowth);
            IC2.log.info(LogCategory.Crop, "Crop at %s - minimum quality: %s", this.field_174879_c, minimumQuality);
            IC2.log.info(LogCategory.Crop, "Crop at %s - provided quality: %s", this.field_174879_c, providedQuality);
            IC2.log.info(LogCategory.Crop, "Crop at %s - total growth: %s", this.field_174879_c, totalGrowth);
            IC2.log.info(LogCategory.Crop, "Crop at %s - growth points (after): %s", this.field_174879_c, this.growthPoints);
        }
    }

    public void performWeedWork() {
        BlockPos dstPos;
        World world = this.func_145831_w();
        TileEntity dstRaw = world.func_175625_s(dstPos = this.field_174879_c.func_177972_a(EnumFacing.field_176754_o[IC2.random.nextInt(4)]));
        if (dstRaw instanceof TileEntityCrop) {
            TileEntityCrop tileEntityCrop;
            CropCard neighborCrop;
            if (debugWeedWork) {
                IC2.log.info(LogCategory.Crop, "Crop at %s - trying to generate weed", dstPos);
            }
            if ((neighborCrop = (tileEntityCrop = (TileEntityCrop)dstRaw).getCrop()) == null || !neighborCrop.isWeed(tileEntityCrop) && IC2.random.nextInt(32) >= tileEntityCrop.getStatResistance() && !tileEntityCrop.hasWeedEX()) {
                int newGrowth;
                if (debugWeedWork) {
                    IC2.log.info(LogCategory.Crop, "Crop at %s - weed generated", dstPos);
                }
                if ((newGrowth = Math.max(this.getStatGrowth(), tileEntityCrop.getStatGrowth())) < 31 && IC2.random.nextBoolean()) {
                    ++newGrowth;
                }
                tileEntityCrop.reset();
                tileEntityCrop.crop = Crops.weed;
                tileEntityCrop.setCurrentSize(1);
                tileEntityCrop.setStatGrowth(newGrowth);
            }
        } else if (world.func_175623_d(dstPos)) {
            BlockPos soilPos;
            Block block;
            if (debugWeedWork) {
                IC2.log.info(LogCategory.Crop, "Block at %s - trying to generate grass", dstPos);
            }
            if ((block = world.func_180495_p(soilPos = dstPos.func_177977_b()).func_177230_c()) == Blocks.field_150346_d || block == Blocks.field_150349_c || block == Blocks.field_150458_ak) {
                world.func_180501_a(soilPos, Blocks.field_150349_c.func_176223_P(), 7);
                world.func_180501_a(dstPos, Blocks.field_150329_H.func_176203_a(1), 7);
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
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (this.func_145831_w().field_72995_K) {
            return true;
        }
        return this.rightClick(player, hand);
    }

    @Override
    protected void onClicked(EntityPlayer player) {
        if (this.crop != null) {
            this.crop.onLeftClick(this, player);
        } else if (this.crossingBase && !this.func_145831_w().field_72995_K) {
            this.crossingBase = false;
            this.dirty = true;
            StackUtil.dropAsEntity(this.func_145831_w(), this.field_174879_c, ItemName.crop_stick.getItemStack());
        }
    }

    @Override
    protected SoundType getBlockSound(Entity entity) {
        return SoundType.field_185850_c;
    }

    @Override
    protected void onBlockBreak() {
        if (!this.func_145831_w().field_72995_K) {
            this.pick();
        }
    }

    @Override
    protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
        ArrayList<AxisAlignedBB> ret = new ArrayList<AxisAlignedBB>();
        if (forCollision) {
            ret.add(new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        } else {
            ret.add(new AxisAlignedBB((double)0.2f, -0.0625, (double)0.2f, (double)0.8f, (double)0.85f, (double)0.8f));
        }
        return ret;
    }

    public boolean rightClick(EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = StackUtil.get(player, hand);
        boolean creative = player.field_71075_bZ.field_75098_d;
        if (!StackUtil.isEmpty(heldItem)) {
            if (this.crop == null && !this.crossingBase && heldItem.func_77973_b() == ItemName.crop_stick.getInstance()) {
                if (!creative) {
                    StackUtil.consumeOrError(player, hand, 1);
                }
                this.crossingBase = true;
                this.dirty = true;
                return true;
            }
            if (this.crop != null && StackUtil.checkItemEquality(heldItem, ItemName.crop_res.getItemStack(CropResItemType.fertilizer))) {
                if (this.applyFertilizer(true)) {
                    this.dirty = true;
                }
                if (!creative) {
                    StackUtil.consumeOrError(player, hand, 1);
                }
                return true;
            }
            IFluidHandlerItem handler = FluidUtil.getFluidHandler((ItemStack)heldItem);
            if (handler != null) {
                if (this.applyHydration((IFluidHandler)handler) || this.applyWeedEx((IFluidHandler)handler, true)) {
                    this.dirty = true;
                }
                return true;
            }
            if (this.crop == null && !this.crossingBase && Crops.instance.getBaseSeed(heldItem) != null) {
                this.reset();
                BaseSeed baseSeed = Crops.instance.getBaseSeed(heldItem);
                this.setCrop(baseSeed.crop);
                this.currentSize = (byte)baseSeed.size;
                this.statGain = (byte)baseSeed.statGain;
                this.statGrowth = (byte)baseSeed.statGrowth;
                this.statResistance = (byte)baseSeed.statResistance;
                if (!creative) {
                    StackUtil.consumeOrError(player, hand, 1);
                }
                return true;
            }
        }
        if (this.crop == null) {
            return false;
        }
        return this.crop.onRightClick(this, player);
    }

    public boolean tryPlantIn(CropCard crop, int size, int statGr, int statGa, int statRe, int scan) {
        if (crop == null || crop == IC2Crops.weed || this.isCrossingBase()) {
            return false;
        }
        if (!crop.canGrow(this)) {
            return false;
        }
        this.reset();
        this.setCrop(crop);
        this.setCurrentSize(size);
        this.setStatGain(statGa);
        this.setStatGrowth(statGr);
        this.setStatResistance(statRe);
        this.setScanLevel(scan);
        NetworkHelper.sendInitialData(this);
        return true;
    }

    @Override
    public void onEntityCollision(Entity entity) {
        if (this.crop == null) {
            return;
        }
        if (this.crop.onEntityCollision(this, entity)) {
            if (this.field_145850_b.field_72995_K) {
                return;
            }
            if (IC2.random.nextInt(100) == 0 && IC2.random.nextInt(40) > this.statResistance) {
                this.reset();
                this.field_145850_b.func_180501_a(this.field_174879_c.func_177977_b(), Blocks.field_150346_d.func_176223_P(), 7);
                if (debugCollision) {
                    IC2.log.info(LogCategory.Crop, "Crop at %s - crop was trampled", this.field_174879_c);
                }
            }
        }
    }

    public void updateTerrainAirQuality() {
        int value = 0;
        int height = (int)Math.floor((double)(this.field_174879_c.func_177956_o() - 40) / 15.0);
        if (height > 2) {
            height = 2;
        }
        if (height < 0) {
            height = 0;
        }
        value += height;
        int fresh = 9;
        for (int x = this.field_174879_c.func_177958_n() - 1; x < this.field_174879_c.func_177958_n() + 1 && fresh > 0; ++x) {
            for (int z = this.field_174879_c.func_177952_p() - 1; z < this.field_174879_c.func_177952_p() + 1 && fresh > 0; ++z) {
                if (!this.field_145850_b.func_175677_d(new BlockPos(x, this.field_174879_c.func_177956_o(), z), false) && !(this.field_145850_b.func_175625_s(new BlockPos(x, this.field_174879_c.func_177956_o(), z)) instanceof TileEntityCrop)) continue;
                --fresh;
            }
        }
        value += fresh / 2;
        if (this.field_145850_b.func_175678_i(this.field_174879_c.func_177984_a())) {
            value += 4;
        }
        this.setTerrainAirQuality(value);
    }

    public void updateTerrainHumidity() {
        int humidity = this.biomeHumidityBonus;
        if ((Integer)this.field_145850_b.func_180495_p(this.field_174879_c.func_177977_b()).func_177229_b((IProperty)BlockFarmland.field_176531_a) >= 7) {
            humidity += 2;
        }
        if (this.storageWater >= 5) {
            humidity += 2;
        }
        this.setTerrainHumidity(humidity += (this.storageWater + 24) / 25);
    }

    public void updateTerrainNutrients() {
        int nutrients = Crops.instance.getNutrientBiomeBonus(BiomeUtil.getBiome(this.field_145850_b, this.field_174879_c));
        for (int i = 1; i < 5 && this.field_145850_b.func_180495_p(this.field_174879_c.func_177979_c(i)).func_177230_c() == Blocks.field_150346_d; ++i) {
            ++nutrients;
        }
        this.setTerrainNutrients(nutrients += (this.storageNutrients + 19) / 20);
    }

    @Override
    public CropCard getCrop() {
        return this.crop;
    }

    @Override
    public void setCrop(CropCard crop) {
        this.crop = crop;
        this.updateTerrainHumidity();
        this.updateTerrainNutrients();
        this.updateTerrainAirQuality();
    }

    @Override
    public int getCurrentSize() {
        return this.currentSize;
    }

    @Override
    public void setCurrentSize(int size) {
        this.currentSize = (byte)size;
    }

    @Override
    public int getStatGrowth() {
        return this.statGrowth;
    }

    @Override
    public void setStatGrowth(int growth) {
        this.statGrowth = (byte)growth;
    }

    @Override
    public int getStatGain() {
        return this.statGain;
    }

    @Override
    public void setStatGain(int gain) {
        this.statGain = (byte)gain;
    }

    @Override
    public int getStatResistance() {
        return this.statResistance;
    }

    @Override
    public void setStatResistance(int resistance) {
        this.statResistance = (byte)resistance;
    }

    @Override
    public int getStorageNutrients() {
        return this.storageNutrients;
    }

    @Override
    public void setStorageNutrients(int nutrients) {
        this.storageNutrients = (short)nutrients;
    }

    @Override
    public int getStorageWater() {
        return this.storageWater;
    }

    @Override
    public void setStorageWater(int water) {
        this.storageWater = (short)water;
    }

    @Override
    public int getStorageWeedEX() {
        return this.storageWeedEX;
    }

    @Override
    public void setStorageWeedEX(int weedEX) {
        this.storageWeedEX = (short)weedEX;
    }

    @Override
    public int getTerrainAirQuality() {
        return this.terrainAirQuality;
    }

    public void setTerrainAirQuality(int value) {
        this.terrainAirQuality = (byte)value;
    }

    @Override
    public int getTerrainHumidity() {
        return this.terrainHumidity;
    }

    public void setTerrainHumidity(int humidity) {
        this.terrainHumidity = (byte)humidity;
    }

    @Override
    public int getTerrainNutrients() {
        return this.terrainNutrients;
    }

    public void setTerrainNutrients(int nutrients) {
        this.terrainNutrients = (byte)nutrients;
    }

    @Override
    public int getScanLevel() {
        return this.scanLevel;
    }

    @Override
    public void setScanLevel(int scanLevel) {
        this.scanLevel = (byte)scanLevel;
    }

    @Override
    public int getGrowthPoints() {
        return this.growthPoints;
    }

    @Override
    public void setGrowthPoints(int growthPoints) {
        this.growthPoints = (short)growthPoints;
    }

    @Override
    public boolean isCrossingBase() {
        return this.crossingBase;
    }

    @Override
    public void setCrossingBase(boolean crossingBase) {
        this.crossingBase = crossingBase;
    }

    @Override
    public NBTTagCompound getCustomData() {
        return this.customData;
    }

    @Override
    public BlockPos getPosition() {
        return this.field_174879_c;
    }

    @Override
    public World getWorldObj() {
        return this.field_145850_b;
    }

    @Override
    @Deprecated
    public BlockPos getLocation() {
        return this.field_174879_c;
    }

    @Override
    public int getLightLevel() {
        return this.field_145850_b.func_175699_k(this.field_174879_c);
    }

    @Override
    public int getLightValue() {
        return this.crop == null ? 0 : this.crop.getEmittedLight(this);
    }

    @Override
    public boolean pick() {
        int index;
        if (this.crop == null) {
            return false;
        }
        boolean bonus = this.crop.canBeHarvested(this);
        float firstchance = this.crop.dropSeedChance(this);
        firstchance = (float)((double)firstchance * Math.pow(1.1, this.statResistance));
        int dropCount = 0;
        if (bonus) {
            if (this.field_145850_b.field_73012_v.nextFloat() <= (firstchance + 1.0f) * 0.8f) {
                ++dropCount;
            }
            float chance = this.crop.dropSeedChance(this) + (float)this.getStatGrowth() / 100.0f;
            for (index = 23; index < this.statGain; ++index) {
                chance *= 0.95f;
            }
            if (this.field_145850_b.field_73012_v.nextFloat() <= chance) {
                ++dropCount;
            }
        } else if (this.field_145850_b.field_73012_v.nextFloat() <= firstchance * 1.5f) {
            ++dropCount;
        }
        ItemStack[] drops = new ItemStack[dropCount];
        for (index = 0; index < dropCount; ++index) {
            drops[index] = this.crop.getSeeds(this);
        }
        this.reset();
        if (!this.field_145850_b.field_72995_K && drops.length > 0) {
            for (ItemStack drop : drops) {
                if (drop.func_77973_b() != ItemName.crop_seed_bag.getInstance()) {
                    drop.func_77982_d(null);
                }
                StackUtil.dropAsEntity(this.field_145850_b, this.field_174879_c, drop);
            }
        }
        return true;
    }

    @Override
    public boolean performManualHarvest() {
        List<ItemStack> dropItems = this.performHarvest();
        if (dropItems != null && !dropItems.isEmpty()) {
            for (ItemStack stack : dropItems) {
                StackUtil.dropAsEntity(this.field_145850_b, this.field_174879_c, stack);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<ItemStack> performHarvest() {
        if (this.crop == null || !this.crop.canBeHarvested(this)) {
            return null;
        }
        double chance = this.crop.dropGainChance();
        chance *= Math.pow(1.03, this.getStatGain());
        if (debug) {
            System.out.println("chance: " + chance);
            int simCount = 200;
            int sum = 0;
            for (int i2 = 0; i2 < 200; ++i2) {
                int dropCount = (int)Math.max(0L, Math.round(IC2.random.nextGaussian() * chance * 0.6827 + chance));
                sum += dropCount;
                System.out.print(dropCount + " ");
            }
            System.out.println();
            System.out.println("sum: " + sum + ", avg: " + (double)sum / 200.0);
        }
        int dropCount = (int)Math.max(0L, Math.round(IC2.random.nextGaussian() * chance * 0.6827 + chance));
        List<ItemStack> ret = IntStream.range(0, dropCount).mapToObj(i -> {
            ItemStack[] drops = this.crop.getGains(this);
            return Arrays.stream(drops).map(drop -> !StackUtil.isEmpty(drop) && IC2.random.nextInt(100) <= this.getStatGain() ? StackUtil.incSize(drop) : drop);
        }).flatMap(Function.identity()).collect(Collectors.toList());
        this.setCurrentSize(this.crop.getSizeAfterHarvest(this));
        this.dirty = true;
        return ret;
    }

    @Override
    public void reset() {
        this.crop = null;
        this.customData = new NBTTagCompound();
        this.statGain = 0;
        this.statResistance = 0;
        this.statGrowth = 0;
        this.terrainAirQuality = (byte)-1;
        this.terrainHumidity = (byte)-1;
        this.terrainNutrients = (byte)-1;
        this.growthPoints = 0;
        this.scanLevel = 0;
        this.currentSize = 1;
        this.dirty = true;
    }

    @Override
    public void updateState() {
        this.field_145850_b.func_175704_b(this.field_174879_c, this.field_174879_c);
    }

    @Override
    public boolean isBlockBelow(Block required) {
        if (this.crop == null) {
            return false;
        }
        for (int index = 1; index < this.crop.getRootsLength(this); ++index) {
            BlockPos blockPos = this.field_174879_c.func_177979_c(index);
            IBlockState state = this.field_145850_b.func_180495_p(blockPos);
            Block block = state.func_177230_c();
            if (block.isAir(state, (IBlockAccess)this.field_145850_b, blockPos)) {
                return false;
            }
            if (block != required) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlockBelow(String oreDictionaryEntry) {
        if (this.crop == null) {
            return false;
        }
        for (int index = 1; index < this.crop.getRootsLength(this); ++index) {
            BlockPos blockPos = this.field_174879_c.func_177979_c(index);
            IBlockState state = this.field_145850_b.func_180495_p(blockPos);
            Block block = state.func_177230_c();
            if (block.isAir(state, (IBlockAccess)this.field_145850_b, blockPos)) {
                return false;
            }
            ItemStack stackBelow = StackUtil.getPickStack(this.field_145850_b, blockPos, state, Ic2Player.get(this.field_145850_b));
            for (ItemStack stack : OreDictionary.getOres((String)oreDictionaryEntry)) {
                if (!StackUtil.checkItemEquality(stackBelow, stack)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack generateSeeds(CropCard crop, int growth, int gain, int resistance, int scan) {
        return ItemCropSeed.generateItemStackFromValues(crop, growth, gain, resistance, scan);
    }

    @Override
    protected int getLightOpacity() {
        return 0;
    }

    @Override
    public Ic2BlockState.Ic2BlockStateInstance getExtendedState(Ic2BlockState.Ic2BlockStateInstance state) {
        state = super.getExtendedState(state);
        CropRenderState renderState = this.cropRenderState;
        if (renderState != null) {
            state = state.withProperties(renderStateProperty, renderState);
        }
        return state;
    }

    private void updateRenderState() {
        this.cropRenderState = new CropRenderState(this.crop, this.getCurrentSize(), this.crossingBase);
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer player) {
        return false;
    }

    @Override
    protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
        return this.crop == null ? ItemName.crop_stick.getItemStack() : this.generateSeeds(this.crop, this.statGrowth, this.statGain, this.statResistance, this.scanLevel);
    }

    private boolean attemptCrossing() {
        if (IC2.random.nextInt(3) != 0) {
            return false;
        }
        ArrayList<TileEntityCrop> neighbours = new ArrayList<TileEntityCrop>(4);
        this.checkCrossingAvailability(this.field_174879_c.func_177978_c(), neighbours);
        this.checkCrossingAvailability(this.field_174879_c.func_177968_d(), neighbours);
        this.checkCrossingAvailability(this.field_174879_c.func_177974_f(), neighbours);
        this.checkCrossingAvailability(this.field_174879_c.func_177976_e(), neighbours);
        if (debug) {
            System.out.print("Attempted cross with " + neighbours.size() + " plants: ");
            for (TileEntityCrop neighbour : neighbours) {
                System.out.println(neighbour.getCrop().getUnlocalizedName());
            }
            System.out.println();
        }
        if (neighbours.size() < 2) {
            return false;
        }
        CropCard[] crops = Crops.instance.getCrops().toArray(new CropCard[0]);
        if (crops.length == 0) {
            return false;
        }
        int[] ratios = new int[crops.length];
        int total = 0;
        for (int index = 0; index < ratios.length; ++index) {
            CropCard crop = crops[index];
            if (crop.canGrow(this)) {
                for (TileEntityCrop neighbour : neighbours) {
                    total += this.calculateRatioFor(crop, neighbour.getCrop());
                }
            }
            ratios[index] = total;
        }
        if (debugChance) {
            int lastChance = 0;
            for (int i = 0; i < crops.length; ++i) {
                int currentChance = ratios[i];
                System.out.println(String.format("%s: %.1f%% %d%n", crops[i].getUnlocalizedName(), (double)(currentChance - lastChance) * 100.0 / (double)total, ratios[i]));
                lastChance = currentChance;
            }
        }
        int search = IC2.random.nextInt(total);
        if (debug) {
            System.out.printf("rnd: %d / %d%n", search, total);
        }
        int min = 0;
        int max = ratios.length - 1;
        while (min < max) {
            int cur = (min + max) / 2;
            int value = ratios[cur];
            if (debug) {
                System.out.printf("min: %d, max: %d, cur: %d, value: %d%n", min, max, cur, value);
            }
            if (search < value) {
                max = cur;
                continue;
            }
            min = cur + 1;
        }
        if (debug) {
            System.out.printf("result: %s (%d %d)%n", crops[min].getUnlocalizedName(), min, max);
        }
        assert (min == max);
        assert (min >= 0 && min < ratios.length);
        assert (ratios[min] > search);
        assert (min == 0 || ratios[min - 1] <= search);
        this.setCrossingBase(false);
        this.setCrop(crops[min]);
        this.dirty = true;
        this.setCurrentSize(1);
        this.statGrowth = 0;
        this.statResistance = 0;
        this.statGain = 0;
        for (TileEntityCrop neighbour : neighbours) {
            this.statGrowth = (byte)(this.statGrowth + neighbour.statGrowth);
            this.statResistance = (byte)(this.statResistance + neighbour.statResistance);
            this.statGain = (byte)(this.statGain + neighbour.statGain);
        }
        int count = neighbours.size();
        this.statGrowth = (byte)(this.statGrowth / count);
        this.statResistance = (byte)(this.statResistance / count);
        this.statGain = (byte)(this.statGain / count);
        this.statGrowth = (byte)(this.statGrowth + (IC2.random.nextInt(1 + 2 * count) - count));
        this.statGain = (byte)(this.statGain + (IC2.random.nextInt(1 + 2 * count) - count));
        this.statResistance = (byte)(this.statResistance + (IC2.random.nextInt(1 + 2 * count) - count));
        this.statGrowth = (byte)Util.limit(this.statGrowth, 0, 31);
        this.statGain = (byte)Util.limit(this.statGain, 0, 31);
        this.statResistance = (byte)Util.limit(this.statResistance, 0, 31);
        return true;
    }

    private boolean attemptSpreading() {
        ArrayList<TileEntityCrop> neighbours = new ArrayList<TileEntityCrop>(4);
        for (EnumFacing direction : EnumFacing.field_176754_o) {
            TileEntity tileEntity = this.func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(direction));
            if (!(tileEntity instanceof TileEntityCrop)) continue;
            TileEntityCrop sideCrop = (TileEntityCrop)tileEntity;
            neighbours.add(sideCrop);
        }
        if (neighbours.size() != 1) {
            return false;
        }
        TileEntityCrop sideCrop = (TileEntityCrop)neighbours.get(0);
        CropCard neighborCrop = sideCrop.getCrop();
        if (neighborCrop == null) {
            return false;
        }
        if (!neighborCrop.canGrow(this) || !neighborCrop.canCross(sideCrop)) {
            return false;
        }
        int base = 4;
        if (sideCrop.statGrowth >= 16) {
            ++base;
        }
        if (sideCrop.statGrowth >= 30) {
            ++base;
        }
        if (sideCrop.statResistance >= 28) {
            base += 27 - sideCrop.statResistance;
        }
        if (base < IC2.random.nextInt(16)) {
            return false;
        }
        this.setCrossingBase(false);
        this.setCrop(sideCrop.crop);
        this.dirty = true;
        this.setCurrentSize(1);
        this.statGrowth = sideCrop.statGrowth;
        this.statResistance = sideCrop.statResistance;
        this.statGain = sideCrop.statGain;
        return true;
    }

    private int calculateRatioFor(CropCard newCrop, CropCard oldCrop) {
        if (newCrop == oldCrop) {
            return 500;
        }
        int value = 0;
        int[] nArray = oldCrop.getProperties().getAllProperties();
        int[] propNew = newCrop.getProperties().getAllProperties();
        assert (nArray.length == propNew.length);
        for (int i = 0; i < 5; ++i) {
            int delta = Math.abs(nArray[i] - propNew[i]);
            value += -delta + 2;
        }
        for (String attributeNew : newCrop.getAttributes()) {
            for (String attributeOld : oldCrop.getAttributes()) {
                if (!attributeNew.equalsIgnoreCase(attributeOld)) continue;
                value += 5;
            }
        }
        int n = newCrop.getProperties().getTier() - oldCrop.getProperties().getTier();
        if (n > 1) {
            value -= 2 * n;
        }
        if (n < -3) {
            value -= -n;
        }
        return Math.max(value, 0);
    }

    private void checkCrossingAvailability(BlockPos pos, List<TileEntityCrop> crops) {
        TileEntity tile = this.func_145831_w().func_175625_s(pos);
        if (!(tile instanceof TileEntityCrop)) {
            return;
        }
        TileEntityCrop sideCrop = (TileEntityCrop)tile;
        CropCard neighborCrop = sideCrop.getCrop();
        if (neighborCrop == null) {
            return;
        }
        if (!neighborCrop.canGrow(this) || !neighborCrop.canCross(sideCrop)) {
            return;
        }
        int base = 4;
        if (sideCrop.statGrowth >= 16) {
            ++base;
        }
        if (sideCrop.statGrowth >= 30) {
            ++base;
        }
        if (sideCrop.statResistance >= 28) {
            base += 27 - sideCrop.statResistance;
        }
        if (base >= IC2.random.nextInt(16)) {
            crops.add(sideCrop);
        }
    }

    private void checkSpreadingAvailability(BlockPos pos, TileEntityCrop crop) {
        TileEntity tile = this.func_145831_w().func_175625_s(pos);
        if (!(tile instanceof TileEntityCrop)) {
            return;
        }
        TileEntityCrop sideCrop = (TileEntityCrop)tile;
        CropCard neighborCrop = sideCrop.getCrop();
        if (neighborCrop == null) {
            return;
        }
        if (!neighborCrop.canGrow(this) || !neighborCrop.canCross(sideCrop)) {
            return;
        }
        int base = 4;
        if (sideCrop.statGrowth >= 16) {
            ++base;
        }
        if (sideCrop.statGrowth >= 30) {
            ++base;
        }
        if (sideCrop.statResistance >= 28) {
            base += 27 - sideCrop.statResistance;
        }
        if (base >= IC2.random.nextInt(16)) {
            crop = sideCrop;
        }
    }

    @Override
    protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
        if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177977_b()).func_177230_c() != Blocks.field_150458_ak) {
            this.pick();
            this.field_145850_b.func_175698_g(this.field_174879_c);
        }
    }

    public boolean applyHydration(IFluidHandler handler) {
        short limit = 200;
        if (this.storageWater >= limit) {
            return false;
        }
        FluidStack stack = handler.drain(new FluidStack(FluidRegistry.WATER, limit - this.storageWater), true);
        if (stack == null || stack.amount <= 0) {
            return false;
        }
        this.storageWater = (short)(this.storageWater + stack.amount);
        return true;
    }

    public boolean applyWeedEx(IFluidHandler handler, boolean manual) {
        short limit;
        short s = limit = manual ? (short)100 : 150;
        if (this.storageWeedEX >= limit) {
            return false;
        }
        FluidStack stack = handler.drain(new FluidStack(FluidName.weed_ex.getInstance(), limit - this.storageWeedEX), true);
        if (stack == null || stack.amount <= 0) {
            return false;
        }
        this.storageWeedEX = (short)(this.storageWeedEX + stack.amount);
        return true;
    }

    public boolean applyFertilizer(boolean manual) {
        if (this.storageNutrients >= 100) {
            return false;
        }
        this.storageNutrients = (short)(this.storageNutrients + (manual ? 100 : 90));
        return true;
    }

    private void updateBiomeHumidityBonus() {
        Biome biome = BiomeUtil.getBiome(this.field_145850_b, this.field_174879_c);
        float rainfall = biome.func_76727_i();
        int rainfallBonus = (int)((double)(25.0f * rainfall) - 12.5);
        rainfallBonus = rainfallBonus > 10 ? 10 : (rainfallBonus < -10 ? -10 : rainfallBonus);
        float temperature = biome.func_180626_a(this.field_174879_c);
        int coefficientBonus = (int)((double)Math.abs(rainfallBonus) * (-2.0 * Math.pow(temperature, 2.0) + (double)(4.0f * temperature) - 1.0));
        int n = coefficientBonus > 10 ? 10 : (coefficientBonus = coefficientBonus < -10 ? -10 : coefficientBonus);
        if (debug) {
            IC2.log.info(LogCategory.Crop, "Crop at %s - r bonus %d, t/r coefficient bonus %d", this.field_174879_c, rainfallBonus, coefficientBonus);
        }
        this.biomeHumidityBonus = (byte)(rainfallBonus + coefficientBonus);
    }

    public static class CropRenderState {
        public final CropCard crop;
        public final int size;
        public final boolean crosscrop;

        public CropRenderState(CropCard crop, int size, boolean crosscrop) {
            this.crop = crop;
            this.size = size;
            this.crosscrop = crosscrop;
        }

        public int hashCode() {
            int ret = this.crop != null ? this.crop.hashCode() : 1;
            ret = ret * 31 + (this.size + 1) * 5;
            ret = ret * 31 + (this.crosscrop ? 1 : 0);
            return ret;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CropRenderState)) {
                return false;
            }
            CropRenderState other = (CropRenderState)obj;
            return other.crop == this.crop && other.size == this.size && other.crosscrop == this.crosscrop;
        }

        public String toString() {
            return "CropState<" + this.crop + ", " + this.size + ", " + this.crosscrop + '>';
        }
    }
}

