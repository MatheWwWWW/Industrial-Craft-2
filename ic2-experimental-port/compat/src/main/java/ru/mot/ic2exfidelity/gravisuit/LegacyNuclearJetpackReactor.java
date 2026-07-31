package ru.mot.ic2exfidelity.gravisuit;

import ic2.api.item.ElectricItem;
import ic2.api.reactor.IBaseReactorComponent;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.Ic2DamageSource;
import ic2.core.Ic2Explosion;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

/**
 * IC2 Classic's wearable 5x5 reactor, adapted to Experimental's reactor API.
 * Field and inventory NBT names deliberately match the original jetpack.
 */
public final class LegacyNuclearJetpackReactor implements IReactor {
    public static final int WIDTH = 5;
    public static final int SLOT_COUNT = WIDTH * WIDTH;
    private static final int REACTOR_OUTPUT_MULTIPLIER = 20;
    private static final float EXPLOSION_POWER_LIMIT = 45.0F;

    private final ItemStack jetpack;
    private final Player player;
    private final List<ItemStack> inventory = new ArrayList<>(SLOT_COUNT);
    private int tick;
    private float output;
    private int heat;
    private int componentHeat;
    private int maxHeat;
    private float heatEffectModifier;
    private boolean active;
    private boolean exploded;

    public LegacyNuclearJetpackReactor(ItemStack jetpack, Player player) {
        this.jetpack = jetpack;
        this.player = player;
        for (int i = 0; i < SLOT_COUNT; i++) {
            inventory.add(ItemStack.f_41583_);
        }
        load();
    }

    public void onTick() {
        if (exploded) {
            return;
        }
        if (tick++ % getTickRate() == 0) {
            updateActiveState();
            output = 0.0F;
            maxHeat = 10_000;
            heatEffectModifier = 1.0F;
            processChamber();
            if (calculateHeatEffects()) {
                return;
            }
            updateComponentInfo();
        }
        if (active) {
            ElectricItem.manager.charge(
                    jetpack, getReactorEUEnergyOutput(), Integer.MAX_VALUE,
                    true, false);
            updateActiveState();
        }
        save();
    }

    private void updateActiveState() {
        double capacity = ElectricItem.manager.getMaxCharge(jetpack);
        if (capacity <= 0.0) {
            active = false;
        } else if (!active) {
            if (ElectricItem.manager.getCharge(jetpack) / capacity <= 0.3) {
                active = true;
            }
        } else if (ElectricItem.manager.getCharge(jetpack) >= capacity) {
            active = false;
        }
    }

    private void processChamber() {
        for (int pass = 0; pass < 2; pass++) {
            boolean heatPass = pass == 0;
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    ItemStack componentStack = getItemAt(x, y);
                    if (componentStack != null
                            && componentStack.m_41720_()
                                    instanceof IReactorComponent component) {
                        component.processChamber(
                                componentStack, this, x, y, heatPass);
                    }
                }
            }
        }
    }

    private void updateComponentInfo() {
        double fractionSum = 0.0;
        int count = 0;
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack componentStack = inventory.get(i);
            if (!(componentStack.m_41720_()
                    instanceof IReactorComponent component)) {
                continue;
            }
            int x = i % WIDTH;
            int y = i / WIDTH;
            int maximum = component.getMaxHeat(componentStack, this, x, y);
            if (maximum <= 0) {
                continue;
            }
            fractionSum += (double) component.getCurrentHeat(
                    componentStack, this, x, y) / maximum;
            count++;
        }
        componentHeat = count == 0
                ? 0
                : (int) (fractionSum / count * 100.0);
    }

    private boolean calculateHeatEffects() {
        if (heat < 4_000 || player.f_19853_.f_46443_) {
            return false;
        }
        float power = (float) heat / maxHeat;
        if (power >= 1.0F) {
            explode();
            return true;
        }

        Level level = getWorldObj();
        RandomSource random = level.f_46441_;
        if (power >= 0.85F
                && random.m_188501_() <= 0.2F * heatEffectModifier) {
            BlockPos target = getRandomPosition(random, 2);
            BlockState state = level.m_8055_(target);
            if (state.m_60795_()) {
                level.m_46597_(target, Blocks.f_50083_.m_49966_());
            } else if (state.m_60800_((BlockGetter) level, target) >= 0.0F
                    && level.m_7702_(target) == null) {
                Material material = state.m_60767_();
                if (material == Material.f_76278_
                        || material == Material.f_76279_
                        || material == Material.f_76307_
                        || material == Material.f_76314_
                        || material == Material.f_76313_) {
                    level.m_46597_(target,
                            net.minecraft.world.level.material.Fluids.f_76194_
                                    .m_76145_().m_76188_());
                } else {
                    level.m_46597_(target, Blocks.f_50083_.m_49966_());
                }
            }
        }
        if (power >= 0.7F) {
            AABB area = new AABB(getPosition()).m_82400_(3.0);
            for (LivingEntity entity : level.m_45976_(LivingEntity.class, area)) {
                entity.m_6469_((DamageSource) Ic2DamageSource.radiation,
                        (float) random.m_188503_(4) * heatEffectModifier);
            }
        }
        if (power >= 0.5F
                && random.m_188501_() <= heatEffectModifier) {
            BlockPos target = getRandomPosition(random, 2);
            if (level.m_8055_(target).m_60767_() == Material.f_76305_) {
                level.m_7471_(target, false);
            }
        }
        if (power >= 0.4F
                && random.m_188501_() <= heatEffectModifier) {
            BlockPos target = getRandomPosition(random, 2);
            BlockState state = level.m_8055_(target);
            Material material = state.m_60767_();
            if (level.m_7702_(target) == null
                    && (material == Material.f_76320_
                            || material == Material.f_76274_
                            || material == Material.f_76272_)) {
                level.m_46597_(target, Blocks.f_50083_.m_49966_());
            }
        }
        return false;
    }

    private BlockPos getRandomPosition(RandomSource random, int radius) {
        BlockPos origin = getPosition();
        BlockPos result;
        do {
            result = origin.m_7918_(
                    random.m_188503_(radius * 2 + 1) - radius,
                    random.m_188503_(radius * 2 + 1) - radius,
                    random.m_188503_(radius * 2 + 1) - radius);
        } while (origin.equals(result));
        return result;
    }

    public boolean isUsefulItem(ItemStack stack) {
        return !stack.m_41619_()
                && stack.m_41720_() instanceof IBaseReactorComponent component
                && component.canBePlacedIn(stack, this);
    }

    public ItemStack getSlot(int slot) {
        if (slot < 0 || slot >= SLOT_COUNT) {
            return ItemStack.f_41583_;
        }
        return inventory.get(slot);
    }

    public boolean setSlot(int slot, ItemStack stack) {
        if (slot < 0 || slot >= SLOT_COUNT
                || (!stack.m_41619_() && !isUsefulItem(stack))) {
            return false;
        }
        inventory.set(slot, stack);
        save();
        return true;
    }

    private void load() {
        CompoundTag data = jetpack.m_41784_();
        ListTag items = data.m_128437_(
                data.m_128403_("Items") ? "Items" : "items", 10);
        for (int i = 0; i < items.size(); i++) {
            CompoundTag entry = (CompoundTag) items.get(i);
            int slot = entry.m_128445_(
                    entry.m_128403_("Slot") ? "Slot" : "slot");
            if (slot >= 0 && slot < SLOT_COUNT) {
                inventory.set(slot, ItemStack.m_41712_(entry));
            }
        }
        tick = data.m_128451_("tick");
        output = data.m_128457_("output");
        heat = data.m_128451_("heat");
        componentHeat = data.m_128451_("component_heat");
        maxHeat = data.m_128451_("max");
        heatEffectModifier = data.m_128457_("hem");
        active = data.m_128471_("active");
    }

    public void save() {
        CompoundTag data = jetpack.m_41784_();
        ListTag items = new ListTag();
        ListTag managedItems = new ListTag();
        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            ItemStack componentStack = inventory.get(slot);
            if (componentStack.m_41619_()) {
                continue;
            }
            CompoundTag entry = componentStack.m_41739_(new CompoundTag());
            entry.m_128344_("slot", (byte) slot);
            items.add(entry);
            CompoundTag managedEntry = componentStack.m_41739_(new CompoundTag());
            managedEntry.m_128344_("Slot", (byte) slot);
            managedItems.add(managedEntry);
        }
        data.m_128365_("items", items);
        data.m_128365_("Items", managedItems);
        data.m_128405_("tick", tick);
        data.m_128350_("output", output);
        data.m_128405_("heat", heat);
        data.m_128405_("component_heat", componentHeat);
        data.m_128405_("max", maxHeat);
        data.m_128350_("hem", heatEffectModifier);
        data.m_128379_("active", active);
    }

    public int getComponentHeatPercent() {
        return componentHeat;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public BlockEntity getCoreTe() {
        return null;
    }

    @Override
    public BlockPos getPosition() {
        return player.m_20183_();
    }

    @Override
    public Level getWorldObj() {
        return player.f_19853_;
    }

    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public int addHeat(int amount) {
        heat += amount;
        return heat;
    }

    @Override
    public int getMaxHeat() {
        return maxHeat;
    }

    @Override
    public void setMaxHeat(int maxHeat) {
        this.maxHeat = maxHeat;
    }

    @Override
    public void addEmitHeat(int amount) {
        // A wearable reactor has no external coolant tank; emitted heat is lost.
    }

    @Override
    public float getHeatEffectModifier() {
        return heatEffectModifier;
    }

    @Override
    public void setHeatEffectModifier(float heatEffectModifier) {
        this.heatEffectModifier = heatEffectModifier;
    }

    @Override
    public float getReactorEnergyOutput() {
        return output;
    }

    @Override
    public double getReactorEUEnergyOutput() {
        return output * REACTOR_OUTPUT_MULTIPLIER;
    }

    @Override
    public float addOutput(float amount) {
        output += amount;
        return output;
    }

    @Override
    public ItemStack getItemAt(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= WIDTH) {
            return null;
        }
        return inventory.get(x + y * WIDTH);
    }

    @Override
    public void setItemAt(int x, int y, ItemStack stack) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < WIDTH) {
            inventory.set(x + y * WIDTH,
                    stack == null ? ItemStack.f_41583_ : stack);
        }
    }

    @Override
    public void explode() {
        if (getWorldObj().f_46443_ || exploded) {
            return;
        }
        exploded = true;
        float explosionPower = 10.0F;
        float explosionModifier = 1.0F;
        for (ItemStack componentStack : inventory) {
            if (!(componentStack.m_41720_()
                    instanceof IReactorComponent component)) {
                continue;
            }
            float influence = component.influenceExplosion(
                    componentStack, this);
            if (influence > 0.0F && influence < 1.0F) {
                explosionModifier *= influence;
            } else {
                explosionPower += influence;
            }
        }
        explosionPower = Math.min(EXPLOSION_POWER_LIMIT,
                explosionPower * heatEffectModifier * explosionModifier);
        jetpack.m_41764_(0);
        player.m_8061_(EquipmentSlot.CHEST, ItemStack.f_41583_);
        player.f_36096_.m_38946_();
        new Ic2Explosion(getWorldObj(), player, getPosition(),
                explosionPower, 1.5F, Ic2Explosion.Type.Nuclear)
                        .doExplosion();
    }

    @Override
    public int getTickRate() {
        return 20;
    }

    @Override
    public boolean produceEnergy() {
        return active;
    }

    @Override
    public boolean isFluidCooled() {
        return false;
    }
}
