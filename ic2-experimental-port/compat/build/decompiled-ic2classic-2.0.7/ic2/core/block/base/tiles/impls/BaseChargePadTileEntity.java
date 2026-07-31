/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.client.particle.ParticleEngine
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.items.IUpgradeItem;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.tiles.IMachine;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.container.ChargePadContainer;
import ic2.core.entity.renderer.particle.AuraParticle;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.item.upgrades.PadUpgradeItem;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;

public abstract class BaseChargePadTileEntity
extends BaseTileEntity
implements IHasInventory,
IMachine,
ITickListener,
IRedstoneListener,
IRedstoneProvider,
ITileGui,
IEUStorage,
IEnergySink,
IWrenchRemovable,
IDropProvider,
ITileActivityProvider {
    static final EnumSet<IUpgradeItem.UpgradeType> TYPES = EnumSet.of(IUpgradeItem.UpgradeType.MACHINE_MOD, IUpgradeItem.UpgradeType.RECIPE_MOD);
    static final Predicate<Entity> PREDICATE = EntitySelector.f_20403_.and(EntitySelector.f_20408_);
    public final ChargePadData data;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int tier;
    @NetworkInfo
    public int maxInput;
    @NetworkInfo
    public int transferLimit;
    @NetworkInfo
    public int maxEnergy;
    @NetworkInfo
    public int energy;
    @NetworkInfo
    public float range;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int installedUpgrades = 0;
    public int activeTime = 0;
    public int inactiveTime = 0;
    public SimpleInventory inventory;
    public boolean charge_slot = false;
    protected boolean addedToEnergyNet = false;

    protected BaseChargePadTileEntity(BlockPos pos, BlockState state, ChargePadData data) {
        super(pos, state);
        this.data = data;
        this.inventory = new SimpleInventory(1 + data.getSlots() + data.getUpgradeSlots());
        this.tier = data.getBaseTier();
        this.transferLimit = this.maxInput = EnergyNet.INSTANCE.getPowerFromTier(this.tier);
        this.maxEnergy = data.getMaxEnergy();
        this.range = data.getBaseRange();
        this.addGuiFields("tier", "maxInput", "transferLimit", "maxEnergy", "energy");
        this.addNetworkFields("range", "installedUpgrades");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.activeTime = compound.m_128451_("activeTime");
        this.inactiveTime = compound.m_128451_("inactiveTime");
        this.energy = compound.m_128451_("energy");
        this.charge_slot = compound.m_128471_("charge_slot");
        this.inventory.load(compound);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "activeTime", this.activeTime, 0);
        NBTUtils.putInt(compound, "inactiveTime", this.inactiveTime, 0);
        NBTUtils.putInt(compound, "energy", this.energy, 0);
        NBTUtils.putBoolean(compound, "charge_slot", this.charge_slot, false);
        this.inventory.save(compound);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ChargePadContainer(this, player, windowID);
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return side.equals((Object)Direction.DOWN);
    }

    @Override
    public int getSinkTier() {
        return this.tier;
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > this.maxEnergy || amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
        }
        return amount - added;
    }

    public abstract float getEffectHeight();

    @OnlyIn(value=Dist.CLIENT)
    protected abstract int getMaxParticleAge();

    @OnlyIn(value=Dist.CLIENT)
    protected abstract float[] getParticleColour(RandomSource var1);

    @OnlyIn(value=Dist.CLIENT)
    protected abstract double[] getParticleVelocity(RandomSource var1);

    @OnlyIn(value=Dist.CLIENT)
    protected abstract int getParticleAmount(RandomSource var1);

    @OnlyIn(value=Dist.CLIENT)
    public void spawnParticles(RandomSource rand) {
        float multPos = this.range * 2.0f + 0.9f;
        double blockX = this.m_58899_().m_123341_();
        double blockZ = this.m_58899_().m_123343_();
        ParticleEngine er = Minecraft.m_91087_().f_91061_;
        for (int i = (int)(1.0f + this.range * 2.0f); i > 0; --i) {
            for (int k = this.getParticleAmount(rand); k > 0; --k) {
                double x = blockX + (double)0.05f + (double)(rand.m_188501_() * multPos) - (double)(this.range * 1.1f);
                double y = (float)this.m_58899_().m_123342_() + 0.2f + rand.m_188501_() * 0.2f;
                double z = blockZ + (double)0.05f + (double)(rand.m_188501_() * multPos) - (double)(this.range * 1.1f);
                double[] velocity = this.getParticleVelocity(rand);
                if (k < 4) {
                    velocity[2] = velocity[2] * 0.55;
                }
                float[] colour = this.getParticleColour(rand);
                er.m_107344_((Particle)new AuraParticle((ClientLevel)this.f_58857_, x, y, z, this.getMaxParticleAge(), velocity, colour));
            }
        }
    }

    @Override
    public int getMaxEU() {
        return this.maxEnergy;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            this.onPadUpgradeChanged();
            this.onUpgradesChanged();
            if (!this.addedToEnergyNet) {
                this.addedToEnergyNet = true;
                EnergyNet.INSTANCE.addTile(this);
            }
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnergyNet) {
            this.addedToEnergyNet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        this.inventory.addToDrops(drops);
    }

    @Override
    public void onTick() {
        this.provideEnergy();
        if (!this.isActive() && this.inactiveTime > 0) {
            --this.inactiveTime;
            return;
        }
        if (this.activeTime <= 0) {
            this.setActive(false);
            return;
        }
        int transfer = Math.min(this.energy, this.transferLimit);
        if (transfer <= 0) {
            this.setActive(false);
            this.inactiveTime = 20;
            this.activeTime = 0;
            return;
        }
        --this.activeTime;
        List livings = this.f_58857_.m_6443_(LivingEntity.class, new AABB(this.m_58899_()).m_82377_((double)this.range, (double)this.getEffectHeight(), (double)this.range), PREDICATE);
        if (livings.isEmpty()) {
            this.setActive(false);
            this.inactiveTime = 20;
            this.activeTime = 0;
            return;
        }
        boolean pvp = IC2.PLATFORM.getServer().m_129799_();
        if ((this.installedUpgrades & 1 << PadUpgrade.DAMAGE.getIndex()) != 0 && pvp) {
            for (LivingEntity living : livings) {
                living.m_6469_((DamageSource)IC2DamageSource.newShockDamage((Entity)living), 1.0f);
                living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, this.data.getBaseTier() * 20, this.data.getBaseTier() * 2));
            }
            this.energy -= transfer;
            this.updateGuiField("energy");
            return;
        }
        boolean drain = this.data.getBaseTier() > 2 && (this.installedUpgrades & 1 << PadUpgrade.DRAIN.getIndex()) != 0 && pvp;
        boolean armor = (this.installedUpgrades & 1 << PadUpgrade.ARMOR_PRIORITY.getIndex()) != 0;
        Object toCharge = CollectionUtils.createList();
        for (LivingEntity living : livings) {
            int last;
            if (!(living instanceof Player)) continue;
            Player player = (Player)living;
            if (armor) {
                last = toCharge.size();
                this.addPlayerArmor(player, (List<ItemStack>)toCharge, drain);
                if (last != toCharge.size()) continue;
                this.addPlayerHotbar(player, (List<ItemStack>)toCharge, drain);
                continue;
            }
            last = toCharge.size();
            this.addPlayerHotbar(player, (List<ItemStack>)toCharge, drain);
            if (last != toCharge.size()) continue;
            this.addPlayerArmor(player, (List<ItemStack>)toCharge, drain);
        }
        if (toCharge.size() <= 0) {
            this.setActive(false);
            this.activeTime = 0;
            this.inactiveTime = 20;
            return;
        }
        if (toCharge.size() > 1) {
            Collections.shuffle(toCharge);
            if (toCharge.size() > transfer) {
                toCharge = toCharge.subList(0, transfer);
            }
        }
        int size = toCharge.size();
        Iterator iterator = toCharge.iterator();
        while (iterator.hasNext()) {
            ItemStack stack = (ItemStack)iterator.next();
            int toAdd = (int)Math.ceil(transfer / size--);
            int added = drain ? ElectricItem.MANAGER.discharge(stack, toAdd, this.tier, false, false, false) : ElectricItem.MANAGER.charge(stack, toAdd, this.tier, false, false);
            transfer -= added;
            this.energy -= added;
        }
        this.updateGuiField("energy");
    }

    public void provideEnergy() {
        if (!this.charge_slot) {
            return;
        }
        int needed = this.maxEnergy - this.energy;
        if (needed <= 0) {
            return;
        }
        ItemStack stack = this.inventory.getStackInSlot(0);
        if (stack.m_41619_()) {
            return;
        }
        if (stack.m_41720_() == Items.f_42451_) {
            this.energy += 500;
            stack.m_41774_(1);
        } else if (stack.m_41720_() == Items.f_42153_) {
            this.energy += 4500;
            stack.m_41774_(1);
        } else if (stack.m_41720_() == IC2Items.SU_BATTERY) {
            this.energy += 1000;
            stack.m_41774_(1);
        } else {
            int added = ElectricItem.MANAGER.discharge(stack, needed, this.tier, false, true, false);
            if (added > 0) {
                this.energy += added;
            } else {
                this.charge_slot = false;
            }
        }
        this.updateGuiField("energy");
    }

    @Override
    public int getSlotCount() {
        return this.inventory.getSlotCount();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inventory.setStackInSlot(slot, stack);
        if (this.isSimulating() && slot == 0) {
            this.charge_slot = !stack.m_41619_();
        }
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85 - 0.05 * (double)this.data.getBaseTier();
    }

    protected void addPlayerHotbar(Player player, List<ItemStack> list, boolean drain) {
        ItemStack stack;
        int start;
        int end = start = player.m_150109_().f_35977_;
        if ((this.installedUpgrades & 1 << PadUpgrade.PROXIMITY.getIndex()) != 0) {
            start = Math.max(0, start - 1);
            end = Math.min(8, end + 1);
        } else if ((this.installedUpgrades & 1 << PadUpgrade.WIDE_BAND.getIndex()) != 0) {
            start = 0;
            end = 8;
        }
        if (start < 0 || end > 8) {
            return;
        }
        for (int i = start; i <= end; ++i) {
            ItemStack stack2;
            if (!(drain ? ElectricItemFilter.DISCHARGE_INTERNAL_FILTER : ElectricItemFilter.CHARGE_FILTER).matches(stack2 = player.m_150109_().m_8020_(i))) continue;
            list.add(stack2);
        }
        if ((drain ? ElectricItemFilter.DISCHARGE_INTERNAL_FILTER : ElectricItemFilter.CHARGE_FILTER).matches(stack = player.m_21120_(InteractionHand.OFF_HAND))) {
            list.add(stack);
        }
    }

    protected void addPlayerArmor(Player player, List<ItemStack> list, boolean drain) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack;
            if (slot.m_20743_() == EquipmentSlot.Type.HAND) continue;
            if (!(drain ? ElectricItemFilter.DISCHARGE_INTERNAL_FILTER : ElectricItemFilter.CHARGE_FILTER).matches(stack = player.m_6844_(slot))) continue;
            list.add(stack);
        }
        if (IC2.CURIO_PLUGIN != null) {
            IItemHandler handler = IC2.CURIO_PLUGIN.getCurioHandler(player);
            int m = handler.getSlots();
            for (int i = 0; i < m; ++i) {
                ItemStack stack;
                if (!(drain ? ElectricItemFilter.DISCHARGE_INTERNAL_FILTER : ElectricItemFilter.CHARGE_FILTER).matches(stack = handler.getStackInSlot(i))) continue;
                list.add(stack);
            }
        }
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        return 0;
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return TYPES;
    }

    @Override
    public int getAvailableEnergy() {
        return this.energy;
    }

    @Override
    public boolean useEnergy(int toUse, boolean doUse) {
        if (this.energy >= toUse) {
            if (doUse) {
                this.energy -= toUse;
                this.updateGuiField("energy");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMachineWorking() {
        return false;
    }

    @Override
    public boolean isRedstoneSensitive() {
        return false;
    }

    @Override
    public void setRedstoneSensitive(boolean flag) {
    }

    public void onPadUpgradeChanged() {
        this.installedUpgrades = 0;
        for (int i = 0; i < this.data.getSlots(); ++i) {
            ItemStack stack = this.inventory.getStackInSlot(1 + i);
            if (!(stack.m_41720_() instanceof PadUpgradeItem)) continue;
            this.installedUpgrades |= 1 << ((PadUpgradeItem)stack.m_41720_()).getUpgrade().getIndex();
        }
        this.updateTileField("installedUpgrades");
    }

    @Override
    public void onUpgradesChanged() {
        int extraSpeed = 0;
        double speedMultiplier = 1.0;
        int extraEnergy = 0;
        double energyMultiplier = 1.0;
        int extraTier = 0;
        int slotOffset = this.data.getBaseTier() > 1 ? 3 : 1;
        for (int i = 0; i < this.data.getUpgradeSlots() && i + slotOffset < this.inventory.getSlotCount(); ++i) {
            ItemStack item = this.inventory.getStackInSlot(i + slotOffset);
            if (!(item.m_41720_() instanceof IUpgradeItem)) continue;
            IUpgradeItem upgrade = (IUpgradeItem)item.m_41720_();
            upgrade.onInstall(item, this);
            extraSpeed += upgrade.getExtraProcessingSpeed(item, this) * item.m_41613_();
            speedMultiplier *= Math.pow(upgrade.getProcessingSpeedMultiplier(item, this), item.m_41613_());
            extraEnergy += upgrade.getExtraEnergyStorage(item, this) * item.m_41613_();
            energyMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, this), item.m_41613_());
            extraTier += upgrade.getExtraTier(item, this) * item.m_41613_();
        }
        this.transferLimit = RecipeMods.apply(EnergyNet.INSTANCE.getPowerFromTier(this.data.getBaseTier()), extraSpeed, speedMultiplier);
        this.maxEnergy = RecipeMods.apply(this.data.getMaxEnergy(), extraEnergy, energyMultiplier);
        this.tier = Mth.m_14045_((int)(this.data.getBaseTier() + extraTier), (int)1, (int)13);
        this.maxInput = EnergyNet.INSTANCE.getPowerFromTier(this.tier);
        this.updateGuiFields("transferLimit", "maxEnergy", "tier", "maxInput");
    }

    @Override
    public IItemHandler getConnectedInventory(Direction dir) {
        return null;
    }

    @Override
    public ITube getConnectedTube(Direction dir) {
        return null;
    }

    @Override
    public int getCommonSignalStrength(Direction side) {
        return side == Direction.DOWN ? 0 : (this.isActive() ? 15 : 0);
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }

    public static class ChargePadData {
        int maxEnergy;
        int baseTier;
        int slots;
        int upgradeSlots;
        float baseRange;
        float maxImpact;

        public ChargePadData(int maxEnergy, int baseTier, int slots, int upgradeSlots, float baseRange, float maxImpact) {
            this.maxEnergy = maxEnergy;
            this.baseTier = baseTier;
            this.slots = slots;
            this.upgradeSlots = upgradeSlots;
            this.baseRange = baseRange;
            this.maxImpact = maxImpact;
        }

        public int getBaseTier() {
            return this.baseTier;
        }

        public int getMaxEnergy() {
            return this.maxEnergy;
        }

        public float getMaxImpact() {
            return this.maxImpact;
        }

        public int getSlots() {
            return this.slots;
        }

        public int getUpgradeSlots() {
            return this.upgradeSlots;
        }

        public float getBaseRange() {
            return this.baseRange;
        }
    }

    public static enum PadUpgrade implements CollectionUtils.IIndexEnum
    {
        DAMAGE(false, true, 2, 0),
        DRAIN(false, false, 2, 1),
        PROXIMITY(true, false, 2, 2),
        WIDE_BAND(true, true, 2, 3),
        ARMOR_PRIORITY(false, false, 2, 4),
        FIELD_MK1(true, false, 2, 5),
        FIELD_MK2(true, false, 3, 6),
        FIELD_MK3(true, true, 3, 7);

        static PadUpgrade[] UPGRADES;
        boolean isProjection;
        boolean isRare;
        int baseTier;
        int index;

        private PadUpgrade(boolean isProjection, boolean isRare, int baseTier, int index) {
            this.isProjection = isProjection;
            this.isRare = isRare;
            this.baseTier = baseTier;
            this.index = index;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        public static PadUpgrade byIndex(int index) {
            return UPGRADES[index % UPGRADES.length];
        }

        static {
            UPGRADES = (PadUpgrade[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])PadUpgrade.values());
        }
    }
}

