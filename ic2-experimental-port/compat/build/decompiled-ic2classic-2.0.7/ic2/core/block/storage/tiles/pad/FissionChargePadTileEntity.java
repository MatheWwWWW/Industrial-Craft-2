/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles.pad;

import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.IUsableUranium;
import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.block.storage.container.FissionChargePadContainer;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FissionChargePadTileEntity
extends BaseChargePadTileEntity {
    public int nuclearTicks = 0;
    public int production = 0;

    public FissionChargePadTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, new BaseChargePadTileEntity.ChargePadData(15000000, 4, 2, 3, 1.0f, 1.5f));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FISSION_CHARGEPAD;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FissionChargePadContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "uranium_ticks", this.nuclearTicks, 0);
        NBTUtils.putInt(compound, "production", this.production, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.nuclearTicks = compound.m_128451_("uranium_ticks");
        this.production = compound.m_128451_("production");
    }

    @Override
    public float getEffectHeight() {
        return 0.5f;
    }

    @Override
    protected int getMaxParticleAge() {
        return 14;
    }

    @Override
    protected float[] getParticleColour(RandomSource rand) {
        float red = 0.9804688f;
        float green = 0.835938f;
        float blue = 0.1367188f;
        if ((this.installedUpgrades & 1 << BaseChargePadTileEntity.PadUpgrade.DRAIN.getIndex()) != 0) {
            green /= 2.0f;
            blue /= 2.0f;
        }
        if ((this.installedUpgrades & 1 << BaseChargePadTileEntity.PadUpgrade.DAMAGE.getIndex()) != 0) {
            blue = 0.4f;
            green = 0.4f;
        } else {
            red *= 0.6f + rand.m_188501_() * 0.4f;
        }
        return new float[]{red, green *= 0.6f + rand.m_188501_() * 0.4f, blue *= 0.6f + rand.m_188501_() * 0.4f};
    }

    @Override
    protected double[] getParticleVelocity(RandomSource rand) {
        return new double[]{0.0, 7.6, 0.0};
    }

    @Override
    protected int getParticleAmount(RandomSource rand) {
        return 6;
    }

    @Override
    public void provideEnergy() {
        super.provideEnergy();
        int needed = this.maxEnergy - this.energy;
        if (needed > 0) {
            if (this.nuclearTicks == 0) {
                ItemStack stack = this.inventory.getStackInSlot(0);
                if (!this.useCell(stack)) {
                    return;
                }
                this.nuclearTicks = 20;
                this.production = this.getEnergy(stack);
            } else if (this.nuclearTicks > 0) {
                --this.nuclearTicks;
                this.energy += this.production;
                this.updateGuiField("energy");
            }
        }
    }

    public int getEnergy(ItemStack stack) {
        if (!(stack.m_41720_() instanceof IReactorPlannerComponent)) {
            return 0;
        }
        IReactorPlannerComponent component = (IReactorPlannerComponent)stack.m_41720_();
        if (component.getType(stack) != IReactorPlannerComponent.ComponentType.FUEL_ROD) {
            return 0;
        }
        return (int)component.getReactorStat(IReactorPlannerComponent.ReactorStat.ENERGY_PRODUCTION, stack).m_7057_();
    }

    protected boolean useCell(ItemStack stack) {
        if (!this.isCell(stack)) {
            return false;
        }
        if (stack.m_220157_(1, this.f_58857_.f_46441_, null)) {
            this.inventory.setStackInSlot(0, ((IUsableUranium)stack.m_41720_()).createDepletedUraniumRod());
        }
        return true;
    }

    protected boolean isCell(ItemStack stack) {
        return stack.m_41720_() instanceof IUsableUranium;
    }
}

