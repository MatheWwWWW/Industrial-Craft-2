package ru.mot.ic2exfidelity.iu;

import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.init.Localization;
import ic2.core.network.GrowingBuffer;
import ic2.core.util.Util;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;

/** IC2 energy delegates, persistent FE/QE buffers and native inventory/menu support. */
public final class NativeMachine extends TileEntityInventory implements IHasGui, IUpgradableBlock, ic2.api.network.INetworkClientTileEntityEventListener, net.minecraftforge.common.capabilities.ICapabilityProvider {
    public final Energy energy;
    public final NativeIUContent.Machine specification;
    public final InvSlotUpgrade upgrade;
    public final InvSlot modules;
    public final InvSlotOutput output;
    public double quantum;
    public double quantumCapacity;
    public int forgeEnergy;
    public int forgeCapacity = 400000;
    public boolean forward = true;
    public boolean running = true;
    public int installedCores;
    public int outputPercent = 100;
    public int experience;
    public int minY = -64;
    public int maxY = 256;
    public long cursor;
    public boolean complete;
    private LazyOptional<IEnergyStorage> feCapability = LazyOptional.of(() -> new ForgeBuffer());
    private LazyOptional<net.minecraftforge.items.IItemHandler> itemCapability = LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));

    public NativeMachine(BlockPos pos, BlockState state) {
        super(NativeIUContent.TYPES.get(ForgeRegistries.BLOCKS.getKey(state.m_60734_())).get(), pos, state);
        specification = NativeIUContent.MACHINES.get(ForgeRegistries.BLOCKS.getKey(state.m_60734_()));
        boolean generator = kind("generator");
        boolean quarry = kind("quarry");
        energy = addComponent(new Energy(this, quarry ? 50000000 : 40000,
                generator ? Collections.emptySet() : Util.allFacings,
                quarry || generator ? Collections.emptySet() : Util.allFacings, quarry ? 14 : 5, 5, false));
        upgrade = quarry || generator ? null : new InvSlotUpgrade(this, "upgrade", 4);
        modules = quarry ? new InvSlot(this, "modules", InvSlot.Access.IO, Math.min(4, specification.tier()), InvSlot.InvSide.ANY) {
            @Override public boolean accepts(ItemStack stack) { return stack.m_41720_() instanceof QuarryModule; }
            @Override public int getStackSizeLimit() { return 1; }
        } : null;
        output = quarry ? new InvSlotOutput(this, "output", 24, InvSlot.InvSide.ANY) : null;
        quantumCapacity = quarry ? 200000 : generator ? generation() * 32 : 2500;
        applyDirections();
    }
    public boolean kind(String value) { return specification.kind().equals(value); }
    public double generation() { return 5 * Math.pow(3, specification.tier() - 1) / 16 * (1 + installedCores * .25); }
    private void applyDirections() {
        if (kind("qe") || kind("fe")) {
            energy.setReceivingEnabled(forward);
            energy.setSendingEnabled(!forward);
        }
    }
    @Override protected void updateEntityServer() {
        super.updateEntityServer();
        if (kind("fe") || kind("qe")) {
            upgrade.tick();
            int tier = Math.min(14, upgrade.getTier(5));
            double capacity = Math.max(40000, upgrade.getEnergyStorage(40000, 1, 0));
            energy.setCapacity(capacity);
            energy.setSinkTier(tier);
            energy.setSourceTier(tier);
            forgeCapacity = (int)Math.min(Integer.MAX_VALUE, 400000 + (capacity - 40000) * 4);
            quantumCapacity = capacity / 16;
            applyDirections();
            if (kind("fe")) {
                if (forward) {
                    int amount = (int)Math.min(forgeCapacity - forgeEnergy, Math.floor(energy.getEnergy() * 4));
                    if (amount > 0 && energy.useEnergy(amount / 4.0)) forgeEnergy += amount;
                    pushForgeEnergy();
                } else {
                    double amount = Math.min(energy.getFreeEnergy(), forgeEnergy / 4.0);
                    int consumed = (int)Math.floor(amount * 4);
                    if (consumed > 0) { energy.addEnergy(consumed / 4.0); forgeEnergy -= consumed; }
                }
            } else if (forward) {
                double amount = Math.min(quantumCapacity - quantum, energy.getEnergy() / 16);
                if (amount > 0 && energy.useEnergy(amount * 16)) quantum += amount;
                pushQuantum();
            } else {
                double amount = Math.min(energy.getFreeEnergy(), quantum * 10);
                if (amount > 0) { energy.addEnergy(amount); quantum -= amount / 10; }
            }
        } else if (kind("generator")) {
            quantumCapacity = generation() * 32;
            if (running) quantum = Math.min(quantumCapacity, quantum + generation() * outputPercent / 100.0);
            pushQuantum();
        } else if (kind("quarry")) NativeQuarry.tick(this);
        if (f_58857_.m_46467_() % 20 == 0) m_6596_();
    }
    private void pushForgeEnergy() {
        for (Direction side : Direction.values()) {
            if (forgeEnergy <= 0) break;
            BlockPos target = f_58858_.m_121945_(side);
            if (!f_58857_.m_46805_(target)) continue;
            BlockEntity tile = f_58857_.m_7702_(target);
            if (tile == null) continue;
            IEnergyStorage receiver = tile instanceof net.minecraftforge.common.capabilities.ICapabilityProvider provider ? provider.getCapability(ForgeCapabilities.ENERGY, side.m_122424_()).orElse(null) : null;
            if (receiver != null && receiver.canReceive()) forgeEnergy -= Math.max(0, Math.min(forgeEnergy, receiver.receiveEnergy(forgeEnergy, false)));
        }
    }
    private void pushQuantum() {
        if (quantum <= 0) return;
        Set<BlockPos> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        visited.add(f_58858_);
        queue.add(f_58858_);
        while (!queue.isEmpty() && visited.size() < 4096 && quantum > 0) {
            BlockPos pos = queue.remove();
            for (Direction side : Direction.values()) {
                BlockPos target = pos.m_121945_(side);
                if (!visited.add(target) || !f_58857_.m_46805_(target)) continue;
                ResourceLocation block = ForgeRegistries.BLOCKS.getKey(f_58857_.m_8055_(target).m_60734_());
                if (new ResourceLocation("powerutils", "quantum_cable").equals(block)) queue.add(target);
                else if (f_58857_.m_7702_(target) instanceof NativeMachine receiver && (receiver.kind("quarry") || receiver.kind("qe") && !receiver.forward)) {
                    double amount = Math.max(0, Math.min(quantum, receiver.quantumCapacity - receiver.quantum));
                    if (amount > 0) { quantum -= amount; receiver.quantum += amount; receiver.m_6596_(); }
                }
            }
        }
    }
    @Override protected InteractionResult onActivated(Player player, InteractionHand hand, Direction side, Vec3 hit) {
        if (kind("generator")) {
            ItemStack stack = player.m_21120_(hand);
            ResourceLocation block = ForgeRegistries.BLOCKS.getKey(getBlockType());
            ResourceLocation core = new ResourceLocation("quantumgenerators", "core_" + block.m_135815_().substring(3));
            if (core.equals(ForgeRegistries.ITEMS.getKey(stack.m_41720_())) && installedCores < 8) {
                if (!f_58857_.f_46443_) { installedCores++; if (!player.m_150110_().f_35937_) stack.m_41774_(1); m_6596_(); }
                return InteractionResult.SUCCESS;
            }
            if (player.m_6144_() && stack.m_41619_() && installedCores > 0) {
                if (!f_58857_.f_46443_) { installedCores--; player.m_150109_().m_36054_(new ItemStack(NativeIUContent.ITEMS.get(core).get())); m_6596_(); }
                return InteractionResult.SUCCESS;
            }
        }
        return super.onActivated(player, hand, side, hit);
    }
    @Override public void onNetworkEvent(Player player, int event) {
        if (!m_6542_(player)) return;
        if (kind("quarry") && event == 6) { player.m_6756_(experience); experience = 0; m_6596_(); return; }
        onNetworkEvent(event);
    }
    public void onNetworkEvent(int event) {
        if (kind("fe") || kind("qe")) { if (event == 0) forward = !forward; applyDirections(); }
        else if (kind("generator")) {
            if (event == 0) running = !running;
            if (event == 1) outputPercent = Math.max(0, outputPercent - 10);
            if (event == 2) outputPercent = Math.min(100, outputPercent + 10);
        } else if (kind("quarry")) {
            if (event == 0) running = !running;
            if (event == 1) { cursor = 0; complete = false; }
            if (event == 2) minY = Math.min(maxY, minY + 10);
            if (event == 3) minY = Math.max(f_58857_.m_141937_(), minY - 10);
            if (event == 4) maxY = Math.min(f_58857_.m_151558_() - 1, maxY + 10);
            if (event == 5) maxY = Math.max(minY, maxY - 10);
        }
        m_6596_();
    }
    public String getModeText() { return Localization.translate(kind("quarry") && complete ? "iu_native.finished" : running ? "iu_native.running" : "iu_native.paused"); }
    public String getConversionText() { return kind("fe") ? forward ? "EU → FE (1:4)" : "FE → EU (4:1)" : forward ? "EU → QE (16:1)" : "QE → EU (1:10)"; }
    public String getEnergyText() { return String.format(java.util.Locale.ROOT, "%.1f / %.1f EU", energy.getEnergy(), energy.getCapacity()); }
    public String getQuantumText() { return String.format(java.util.Locale.ROOT, "%.2f / %.2f QE", quantum, quantumCapacity); }
    public String getForgeText() { return forgeEnergy + " / " + forgeCapacity + " FE"; }
    public String getGenerationText() { return String.format(java.util.Locale.ROOT, "%.2f QE/t (%d%%)", generation() * outputPercent / 100.0, outputPercent); }
    public String getRangeText() { return "Y: " + minY + " … " + maxY; }
    public String getExperienceText() { return "XP: " + experience; }
    @Override public List<String> getNetworkedFields() {
        List<String> fields = new ArrayList<>(super.getNetworkedFields());
        Collections.addAll(fields, "quantum", "quantumCapacity", "forgeEnergy", "forgeCapacity", "forward", "running", "installedCores", "outputPercent", "experience", "minY", "maxY", "complete");
        return fields;
    }
    @Override public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        quantum = Math.max(0, tag.m_128459_("quantum"));
        forgeEnergy = Math.max(0, tag.m_128451_("forgeEnergy"));
        forward = !tag.m_128441_("forward") || tag.m_128471_("forward");
        running = !tag.m_128441_("running") || tag.m_128471_("running");
        installedCores = Math.max(0, Math.min(8, tag.m_128451_("installedCores")));
        outputPercent = tag.m_128441_("outputPercent") ? Math.max(0, Math.min(100, tag.m_128451_("outputPercent"))) : 100;
        cursor = Math.max(0, tag.m_128454_("cursor"));
        complete = tag.m_128471_("complete");
        experience = Math.max(0, tag.m_128451_("experience"));
        if (tag.m_128441_("minY")) { minY = tag.m_128451_("minY"); maxY = Math.max(minY, tag.m_128451_("maxY")); }
        applyDirections();
    }
    @Override public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128347_("quantum", quantum); tag.m_128405_("forgeEnergy", forgeEnergy);
        tag.m_128379_("forward", forward); tag.m_128379_("running", running);
        tag.m_128405_("installedCores", installedCores); tag.m_128405_("outputPercent", outputPercent);
        tag.m_128356_("cursor", cursor); tag.m_128379_("complete", complete);
        tag.m_128405_("experience", experience); tag.m_128405_("minY", minY); tag.m_128405_("maxY", maxY);
    }
    @Override protected ItemStack adjustDrop(ItemStack stack, boolean wrench) {
        ItemStack drop = super.adjustDrop(stack, wrench);
        if (drop != null) {
            CompoundTag data = new CompoundTag();
            m_183515_(data);
            data.m_128473_("InvSlots");
            data.m_128473_("components");
            data.m_128347_("storedEU", energy.getEnergy());
            drop.m_41784_().m_128365_("ic2NativeMachine", data);
        }
        return drop;
    }
    @Override public void onPlaced(ItemStack stack, net.minecraft.world.entity.LivingEntity placer, Direction direction) {
        super.onPlaced(stack, placer, direction);
        if (stack.m_41783_() != null && stack.m_41783_().m_128425_("ic2NativeMachine", 10)) {
            CompoundTag data = stack.m_41783_().m_128469_("ic2NativeMachine");
            m_142466_(data);
            double stored = data.m_128459_("storedEU");
            if (Double.isFinite(stored) && stored > 0) energy.addEnergy(stored);
        }
    }
    @Override public Set<UpgradableProperty> getUpgradableProperties() { return EnumSet.of(UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage); }
    @Override public double getEnergy() { return energy.getEnergy(); }
    @Override public boolean useEnergy(double amount) { return amount >= 0 && energy.useEnergy(amount); }
    @Override public ContainerBase<?> createServerScreenHandler(int id, Player player) { return DynamicContainer.create(id, player.m_150109_(), this); }
    @Override public ContainerBase<?> createClientScreenHandler(int id, Inventory inventory, GrowingBuffer buffer) { return DynamicContainer.create(id, inventory, this); }
    @Override public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (kind("fe") && cap == ForgeCapabilities.ENERGY) return feCapability.cast();
        if (kind("quarry") && cap == ForgeCapabilities.ITEM_HANDLER) return itemCapability.cast();
        return LazyOptional.empty();
    }
    @Override protected void onUnloaded() { super.onUnloaded(); feCapability.invalidate(); itemCapability.invalidate(); }
    @Override protected void onLoaded() {
        super.onLoaded();
        if (!feCapability.isPresent()) feCapability = LazyOptional.of(() -> new ForgeBuffer());
        if (!itemCapability.isPresent()) itemCapability = LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this));
    }
    private final class ForgeBuffer implements IEnergyStorage {
        @Override public int receiveEnergy(int max, boolean simulate) {
            if (forward || max <= 0) return 0;
            int amount = Math.max(0, Math.min(max, forgeCapacity - forgeEnergy));
            if (!simulate && amount > 0) { forgeEnergy += amount; m_6596_(); }
            return amount;
        }
        @Override public int extractEnergy(int max, boolean simulate) {
            if (!forward || max <= 0) return 0;
            int amount = Math.min(max, forgeEnergy);
            if (!simulate && amount > 0) { forgeEnergy -= amount; m_6596_(); }
            return amount;
        }
        @Override public int getEnergyStored() { return forgeEnergy; }
        @Override public int getMaxEnergyStored() { return forgeCapacity; }
        @Override public boolean canExtract() { return forward; }
        @Override public boolean canReceive() { return !forward; }
    }
}
