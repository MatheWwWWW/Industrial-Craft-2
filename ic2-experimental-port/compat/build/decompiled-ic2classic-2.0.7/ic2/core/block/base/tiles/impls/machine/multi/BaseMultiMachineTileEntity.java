/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.base.tiles.impls.machine.multi;

import ic2.api.core.IC2Classic;
import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.queue.IInputter;
import ic2.api.recipes.ingridients.queue.IStackOutput;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.tiles.ArrayTube;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IInventoryMachine;
import ic2.core.block.base.misc.UpgradeStorage;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.BaseMultiElectricTileEntity;
import ic2.core.block.multi.tiles.MachineLinkingTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.inv.ArrayItemHandler;
import ic2.core.platform.recipes.misc.IngredientRegistry;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public abstract class BaseMultiMachineTileEntity
extends BaseMultiElectricTileEntity
implements IInventoryMachine,
IInputter,
MachineLinkingTileEntity.ICacheListener {
    protected String isWorkingString = "isActive";
    public int defaultEnergyConsume;
    public int defaultOperationLength;
    public int defaultEnergyStorage;
    public float defaultProgressPerTick = 1.0f;
    public int energyConsume;
    public int operationLength;
    public float progressPerTick = 1.0f;
    @NetworkInfo
    public float soundLevel = 1.0f;
    public IAudioSource audio;
    public final int upgradeSlots;
    protected UpgradeStorage storage = new UpgradeStorage();
    protected ICache<IItemHandler> invCache = new FilteredCapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER, this::isValidCacheTarget);
    protected ICache<ITube> tubeCache = new FilteredCapabilityCache<ITube>(this, DirectionList.ALL, IC2Classic.TUBE_CAPABILITY, this::isValidCacheTarget);
    protected Object2IntLinkedOpenHashMap<ResourceLocation> processedRecipes = new Object2IntLinkedOpenHashMap();
    protected List<IStackOutput> outputs = CollectionUtils.createList();
    protected IHasInventory[] inOut;
    protected IItemHandler[] buildInvCache = null;
    protected ITube[] buildTubeCache = null;

    public BaseMultiMachineTileEntity(BlockPos pos, BlockState state, int size, int upgradeSlots, int energyPerTick, int maxProgress, int maxEnergy, int maxInput) {
        super(pos, state, size + upgradeSlots, maxInput, maxEnergy);
        this.defaultEnergyConsume = this.energyConsume = energyPerTick;
        this.defaultOperationLength = this.operationLength = maxProgress;
        this.defaultEnergyStorage = maxEnergy;
        this.upgradeSlots = upgradeSlots;
        this.addCaches(this.invCache, this.tubeCache);
        this.addNetworkFields("soundLevel");
    }

    public boolean canProcess() {
        return !this.isRedstoneSensitive() || this.isRedstonePowered();
    }

    protected ResourceLocation getInterruptSound() {
        return null;
    }

    protected ResourceLocation getStartupSound() {
        return null;
    }

    protected ResourceLocation getWorkingSound() {
        return null;
    }

    protected abstract void createInvCaches();

    protected abstract void handleMods();

    protected boolean isValidCacheTarget(BlockEntity tile) {
        return !(tile instanceof BaseLinkingTileEntity) || ((BaseLinkingTileEntity)tile).getMaster() != this;
    }

    public int getEnergyPerTick() {
        return this.energyConsume;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        for (CompoundTag nbt : NBTListWrapper.wrap(compound.m_128437_("cache", 10), CompoundTag.class)) {
            IStackOutput output = IngredientRegistry.INSTANCE.readQueue(nbt);
            if (output == null) continue;
            this.outputs.add(output);
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        ListTag list = new ListTag();
        for (IStackOutput stack : this.outputs) {
            list.add((Object)IngredientRegistry.INSTANCE.writeQueue(stack));
        }
        NBTUtils.put(compound, "cache", list);
    }

    @Override
    public int getAvailableEnergy() {
        return this.energy;
    }

    @Override
    public boolean useEnergy(int toUse, boolean doUse) {
        if (this.hasEnergy(toUse)) {
            if (doUse) {
                this.useEnergy(toUse);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMachineWorking() {
        return this.isActive();
    }

    @Override
    public void setRedstoneSensitive(boolean flag) {
        this.sensitive = flag;
    }

    @Override
    public boolean isRedstoneSensitive() {
        return this.sensitive;
    }

    @Override
    public IItemHandler getConnectedInventory(Direction dir) {
        InventoryHandler handler;
        if (this.buildInvCache == null) {
            this.generateCache();
        }
        return (handler = this.getInventoryHandler()).isBlockSideUnlocked(dir) ? this.buildInvCache[dir.m_122411_()] : null;
    }

    @Override
    public ITube getConnectedTube(Direction dir) {
        InventoryHandler handler;
        if (this.buildTubeCache == null) {
            this.generateCache();
        }
        return (handler = this.getInventoryHandler()).isBlockSideUnlocked(dir) ? this.buildTubeCache[dir.m_122411_()] : null;
    }

    @Override
    protected void onCachesUpdated() {
        this.buildInvCache = null;
        this.buildTubeCache = null;
    }

    @Override
    public void onChildCacheUpdated() {
        this.buildInvCache = null;
        this.buildTubeCache = null;
        this.needsRedstoneUpdate = this.sensitive;
    }

    protected void generateCache() {
        int i;
        List<K>[] handlers = CollectionUtils.createLists(6);
        List<K>[] tubes = CollectionUtils.createLists(6);
        this.addToList(this.invCache, handlers);
        this.addToList(this.tubeCache, tubes);
        List<BaseLinkingTileEntity> tiles = this.children.getTiles();
        int m = tiles.size();
        for (i = 0; i < m; ++i) {
            BaseLinkingTileEntity tile = tiles.get(i);
            if (!(tile instanceof MachineLinkingTileEntity)) continue;
            MachineLinkingTileEntity link = (MachineLinkingTileEntity)tile;
            this.addToList(link.invCache, handlers);
            this.addToList(link.tubeCache, tubes);
        }
        this.buildInvCache = new IItemHandler[6];
        this.buildTubeCache = new ITube[6];
        for (i = 0; i < 6; ++i) {
            this.buildInvCache[i] = handlers[i].isEmpty() ? null : new ArrayItemHandler(handlers[i]);
            this.buildTubeCache[i] = tubes[i].isEmpty() ? null : new ArrayTube(tubes[i]);
        }
    }

    protected <T> void addToList(ICache<T> cache, List<T>[] handlers) {
        for (Direction dir : cache) {
            T handler = cache.getHandler(dir);
            if (handler == null) continue;
            handlers[dir.m_122411_()].add(handler);
        }
    }

    @Override
    public IHasInventory getInputInventory() {
        if (this.inOut == null) {
            this.createInvCaches();
        }
        return this.inOut[0];
    }

    @Override
    public IHasInventory getOutputInventory() {
        if (this.inOut == null) {
            this.createInvCaches();
        }
        return this.inOut[1];
    }

    public void playSound(boolean interrupt) {
        IC2.AUDIO.playSound(this, interrupt ? this.getInterruptSound() : this.getStartupSound(), AudioManager.SoundType.STATIC, this.soundLevel, 1.0f);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains(this.isWorkingString)) {
            if (this.audio == null || !this.audio.isValid()) {
                this.audio = IC2.AUDIO.createSource(this, this.getWorkingSound(), AudioManager.SoundType.STATIC, this.soundLevel, true, false);
            }
            this.playStop(this.audio, this.isMachineWorking());
        }
        if (fields.contains("soundLevel") && this.audio != null) {
            this.audio.setVolume(this.soundLevel);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.audio = null;
        IC2.AUDIO.removeSource(this);
        super.onUnloaded(chunk);
    }

    protected void onRecipeProcessed(ResourceLocation location) {
        this.processedRecipes.addTo((Object)location, 1);
    }

    @Override
    public void onUpgradesChanged() {
        this.handleUpgrades(false);
    }

    protected void handleUpgrades(boolean defaultSensitive) {
        int extraSpeed = 0;
        double speedMultiplier = 1.0;
        int extraTime = 0;
        double timeMultiplier = 1.0;
        int extraUsage = 0;
        double usageMultiplier = 1.0;
        int extraEnergy = 0;
        double energyMultiplier = 1.0;
        int extraTier = 0;
        float soundMultiplier = 1.0f;
        boolean redstoneInverted = false;
        this.sensitive = defaultSensitive;
        this.storage.clear();
        EnumSet types = this.getSupportedUpgradeTypes();
        for (int i = 0; i < this.upgradeSlots; ++i) {
            IUpgradeItem upgrade;
            ItemStack item = (ItemStack)this.inventory.get(i + this.inventory.size() - this.upgradeSlots);
            if (!(item.m_41720_() instanceof IUpgradeItem) || !types.contains((Object)(upgrade = (IUpgradeItem)item.m_41720_()).getType(item))) continue;
            upgrade.onInstall(item, this);
            this.storage.validate(i + this.inventory.size() - this.upgradeSlots, upgrade.getFunctions(item));
            extraSpeed += upgrade.getExtraProcessingSpeed(item, this) * item.m_41613_();
            speedMultiplier *= Math.pow(upgrade.getProcessingSpeedMultiplier(item, this), item.m_41613_());
            extraTime += upgrade.getExtraProcessingTime(item, this) * item.m_41613_();
            timeMultiplier *= Math.pow(upgrade.getProcessingTimeMultiplier(item, this), item.m_41613_());
            extraUsage += upgrade.getExtraEnergyDemand(item, this) * item.m_41613_();
            usageMultiplier *= Math.pow(upgrade.getEnergyDemandMultiplier(item, this), item.m_41613_());
            extraEnergy += upgrade.getExtraEnergyStorage(item, this) * item.m_41613_();
            energyMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, this), item.m_41613_());
            soundMultiplier = (float)((double)soundMultiplier * Math.pow(upgrade.getSoundMultiplier(item, this), item.m_41613_()));
            extraTier += upgrade.getExtraTier(item, this) * item.m_41613_();
            if (!upgrade.useRedstoneInvertion(item, this)) continue;
            redstoneInverted = true;
        }
        this.soundLevel = soundMultiplier;
        this.inverted = redstoneInverted;
        this.progressPerTick = Math.max(0.01f, RecipeMods.apply(this.defaultProgressPerTick, (float)extraSpeed, speedMultiplier));
        this.energyConsume = Math.max(this.getMinimumEnergyUsage(), RecipeMods.apply(this.defaultEnergyConsume, extraUsage, usageMultiplier));
        this.operationLength = Math.max(1, RecipeMods.apply(this.defaultOperationLength, extraTime, timeMultiplier));
        this.setMaxEnergy(Math.max(1, RecipeMods.apply(this.defaultEnergyStorage, extraEnergy, energyMultiplier)));
        this.setTier(Mth.m_14045_((int)(this.baseTier + extraTier), (int)0, (int)13));
        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
            this.updateGuiFields("energy");
        }
        this.updateTileField("soundLevel");
        this.handleMods();
    }

    protected int getMinimumEnergyUsage() {
        return 1;
    }

    @Override
    public void addItemIntoSlot(int slot, ItemStack stack) {
        ItemStack inv = (ItemStack)this.inventory.get(slot);
        if (inv.m_41619_()) {
            if (stack.m_41613_() > stack.m_41741_()) {
                this.inventory.set(slot, (Object)StackUtil.copyWithSize(stack, stack.m_41741_()));
                stack.m_41774_(stack.m_41741_());
            } else {
                this.inventory.set(slot, (Object)stack.m_41777_());
                stack.m_41764_(0);
            }
        } else {
            if (!StackUtil.isStackEqual(inv, stack)) {
                return;
            }
            int left = StackUtil.getStackSizeLeft(inv);
            if (left > 0) {
                if (left >= stack.m_41613_()) {
                    inv.m_41769_(stack.m_41613_());
                    stack.m_41764_(0);
                } else {
                    stack.m_41774_(left);
                    inv.m_41764_(inv.m_41741_());
                }
            }
        }
    }

    protected boolean addItemsToInventory() {
        if (this.outputs.isEmpty()) {
            return true;
        }
        Iterator<IStackOutput> iter = this.outputs.iterator();
        while (iter.hasNext()) {
            if (!iter.next().addToInventory(this)) continue;
            iter.remove();
        }
        return this.outputs.isEmpty();
    }

    public static enum RecipeResult {
        FAIL,
        PASS,
        SUCCESS,
        IGNORE;

    }
}

