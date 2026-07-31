/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.items.IItemHandlerModifiable
 */
package ic2.core.item.logic;

import ic2.api.items.electric.ElectricItem;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.entity.explosion.IC2Explosion;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.item.logic.TickableItemLogic;
import ic2.core.item.wearable.base.IC2ElectricJetpackBase;
import ic2.core.utils.helpers.SanityHelper;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandlerModifiable;

public class NuclearJetpackLogic
extends TickableItemLogic
implements IReactor {
    SimpleInventory inventory;
    public int tick;
    public float output;
    public int heat;
    public int componentHeat;
    public int maxHeat;
    public float hem;
    public boolean active;
    public EquipmentSlot slot;

    public NuclearJetpackLogic(ItemStack stack, Player player, EquipmentSlot type) {
        super(stack, player);
        this.slot = type;
    }

    @Override
    public CompoundTag getNBTData(ItemStack stack, boolean create) {
        CompoundTag data = super.getNBTData(stack, create);
        if (!(stack.m_41720_() instanceof IC2ElectricJetpackBase)) {
            CompoundTag subData = data.m_128469_("jetpack_data");
            if (create) {
                data.m_128365_("jetpack_data", (Tag)subData);
            }
            return subData;
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        this.inventory.save(nbt);
        nbt.m_128405_("tick", this.tick);
        nbt.m_128350_("output", this.output);
        nbt.m_128405_("heat", this.heat);
        nbt.m_128405_("component_heat", this.componentHeat);
        nbt.m_128405_("max", this.maxHeat);
        nbt.m_128350_("hem", this.hem);
        nbt.m_128379_("active", this.active);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.inventory = new SimpleInventory(25);
        this.inventory.load(nbt);
        this.tick = nbt.m_128451_("tick");
        this.output = nbt.m_128457_("output");
        this.heat = nbt.m_128451_("heat");
        this.componentHeat = nbt.m_128451_("component_heat");
        this.maxHeat = nbt.m_128451_("max");
        this.hem = nbt.m_128457_("hem");
        this.active = nbt.m_128471_("active");
    }

    @Override
    public void onTick(ItemStack stack) {
        if (this.tick++ % this.getTickRate() == 0) {
            this.updateComponentState(stack);
            if (this.updateReactor()) {
                return;
            }
            this.updateComponentInfo();
        }
        if (this.active) {
            ElectricItem.MANAGER.charge(stack, (int)this.getEnergyOutput(), Integer.MAX_VALUE, true, false);
            this.updateComponentState(stack);
        }
    }

    protected void updateComponentState(ItemStack stack) {
        double max = ElectricItem.MANAGER.getCapacity(stack);
        if (max <= 0.0) {
            this.active = false;
            return;
        }
        if (!this.active) {
            if ((double)ElectricItem.MANAGER.getCharge(stack) / max <= 0.3) {
                this.active = true;
            }
            return;
        }
        if ((double)ElectricItem.MANAGER.getCharge(stack) >= max) {
            this.active = false;
        }
    }

    protected void updateComponentInfo() {
        double value = 0.0;
        int total = 0;
        for (int i = 0; i < 25; ++i) {
            IReactorComponent comp;
            double max;
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (!(stack.m_41720_() instanceof IReactorComponent) || !((max = (double)(comp = (IReactorComponent)stack.m_41720_()).getMaxStoredHeat(stack, this, i % 5, i / 5)) > 0.0)) continue;
            value += (double)comp.getStoredHeat(stack, this, i % 5, i / 5) / max;
            ++total;
        }
        this.componentHeat = (int)(total > 0 ? value / (double)total * 100.0 : 0.0);
    }

    public boolean updateReactor() {
        this.output = 0.0f;
        this.maxHeat = 10000;
        this.hem = 1.0f;
        this.processChamber();
        return this.calculateHeatEffects();
    }

    public void processChamber() {
        for (int pass = 0; pass < 2; ++pass) {
            boolean tick = pass == 0;
            for (int x = 0; x < 5; ++x) {
                for (int y = 0; y < 5; ++y) {
                    ItemStack stack = this.getStackInReactor(x, y);
                    if (!(stack.m_41720_() instanceof IReactorComponent)) continue;
                    ((IReactorComponent)stack.m_41720_()).processChamber(stack, this, x, y, tick, tick);
                }
            }
        }
    }

    @Override
    public Level getWorldObj() {
        return this.player.f_19853_;
    }

    @Override
    public BlockPos getPosition() {
        return this.player.m_20183_();
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
        RandomSource rand = this.getWorldObj().f_46441_;
        if (power >= 0.85f && rand.m_188501_() <= 0.2f * this.hem && (nextPos = this.getRandomPos(rand, 2)) != null) {
            state = this.getWorldObj().m_8055_((BlockPos)nextPos);
            if (state.m_60734_() == Blocks.f_50016_) {
                this.getWorldObj().m_46597_((BlockPos)nextPos, Blocks.f_50083_.m_49966_());
            } else {
                float hardness = state.m_60800_((BlockGetter)this.getWorldObj(), (BlockPos)nextPos);
                if (hardness >= 0.0f && hardness <= 10.0f && this.getWorldObj().m_7702_(this.getPosition()) == null) {
                    Material mat2 = state.m_60767_();
                    if (mat2 == Material.f_76278_ || mat2 == Material.f_76279_ || mat2 == Material.f_76307_ || mat2 == Material.f_76314_ || mat2 == Material.f_76313_) {
                        this.getWorldObj().m_46597_((BlockPos)nextPos, ((FluidState)Fluids.f_76194_.m_76145_().m_61124_((Property)BlockStateProperties.f_61420_, (Comparable)Integer.valueOf(8))).m_76188_());
                    } else {
                        this.getWorldObj().m_46597_((BlockPos)nextPos, Blocks.f_50083_.m_49966_());
                    }
                }
            }
        }
        if (power >= 7.0f) {
            nextPos = this.getWorldObj().m_45976_(LivingEntity.class, new AABB(this.getPosition()).m_82400_(3.0)).iterator();
            while (nextPos.hasNext()) {
                LivingEntity entity = (LivingEntity)nextPos.next();
                entity.m_6469_((DamageSource)IC2DamageSource.RADIATION, (float)rand.m_188503_(4) * this.hem);
            }
        }
        if (power >= 5.0f && rand.m_188501_() <= this.hem && (nextPos = this.getRandomPos(rand, 2)) != null) {
            state = this.getWorldObj().m_8055_((BlockPos)nextPos);
            Fluid fluid = state.m_60819_().m_76152_();
            if (state.m_60734_() instanceof BucketPickup && fluid == Fluids.f_76193_ || fluid == Fluids.f_76192_) {
                ((BucketPickup)state.m_60734_()).m_142598_((LevelAccessor)this.getWorldObj(), (BlockPos)nextPos, state);
            }
        }
        if (power >= 4.0f && rand.m_188501_() <= this.hem && (nextPos = this.getRandomPos(rand, 2)) != null && ((mat = this.getWorldObj().m_8055_((BlockPos)nextPos).m_60767_()) == Material.f_76320_ || mat == Material.f_76274_ || mat == Material.f_76272_)) {
            this.getWorldObj().m_46597_((BlockPos)nextPos, Blocks.f_50083_.m_49966_());
        }
        return false;
    }

    public BlockPos getRandomPos(RandomSource rand, int radius) {
        BlockPos nextPos;
        if (radius <= 0) {
            return null;
        }
        int x = rand.m_188503_(2 * radius + 1) - radius;
        int y = rand.m_188503_(2 * radius + 1) - radius;
        int z = rand.m_188503_(2 * radius + 1) - radius;
        BlockPos pos = this.getPosition();
        return pos.equals((Object)(nextPos = pos.m_7918_(x, y, z))) ? null : nextPos;
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
        return this.output * (float)IC2.CONFIG.reactorOutput.get();
    }

    @Override
    public void addOutput(float output) {
        this.output += output;
    }

    @Override
    public ItemStack getStackInReactor(int x, int y) {
        return x < 0 || x > 4 || y < 0 || y > 4 ? ItemStack.f_41583_ : this.inventory.getStackInSlot(x + y * 5);
    }

    @Override
    public void setStackInReactor(int x, int y, ItemStack stack) {
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return;
        }
        this.inventory.setStackInSlot(x + y * 5, stack);
    }

    @Override
    public void explode() {
        if (IC2.PLATFORM.isRendering()) {
            return;
        }
        float boomPower = 10.0f;
        float boomMod = 1.0f;
        for (int i = 0; i < 25; ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (!(stack.m_41720_() instanceof IReactorComponent)) continue;
            float value = ((IReactorComponent)stack.m_41720_()).getExplosionInfluence(stack, this);
            if (value > 0.0f && value < 1.0f) {
                boomMod *= value;
                continue;
            }
            boomPower += value;
        }
        Player player = this.getPlayer();
        boomPower = Math.min((float)IC2.CONFIG.reactorDamage.get(), boomPower * this.hem * boomMod);
        IC2.LOGGER.info("Nuclear Jetpack at " + SanityHelper.toPascalCase(player.f_19853_.m_46472_().m_135782_().m_135815_()) + ":(" + player.m_20185_() + "," + player.m_20186_() + "," + player.m_20189_() + ") melted (explosion power " + boomPower + ")");
        this.setStack(player, ItemStack.f_41583_);
        player.f_36096_.m_38946_();
        new IC2Explosion(player.f_19853_, (Entity)player, new Vec3(player.m_20185_(), player.m_20186_(), player.m_20189_()), boomPower, 1.5f, IC2DamageSource.NUKE).doExplosion();
    }

    @Override
    public int getTickRate() {
        return 20;
    }

    @Override
    public boolean isProducingEnergy() {
        return this.active;
    }

    protected void setStack(Player player, ItemStack stack) {
        IItemHandlerModifiable handler;
        int index;
        CompoundTag data = StackUtil.getNbtData(stack);
        if (data.m_128441_("curio_index") && IC2.CURIO_PLUGIN != null && (index = (data = data.m_128469_("curio_index")).m_128451_("index")) < (handler = IC2.CURIO_PLUGIN.getCurioInv(player, data.m_128461_("id"))).getSlots()) {
            handler.setStackInSlot(index, stack);
            return;
        }
        player.m_8061_(this.slot, stack);
    }
}

