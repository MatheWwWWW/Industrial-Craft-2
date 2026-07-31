package ru.mot.ic2exfidelity.legacy;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.item.ElectricItem;
import ic2.core.block.comp.ComparatorEmitter;
import ic2.core.block.comp.Energy;
import ic2.core.block.comp.Redstone;
import ic2.core.block.tileentity.Ic2TileEntity;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** IC2 2.8.222 flat luminator behavior on the retained 1.19 Energy component. */
public final class LegacyLuminatorBlockEntity extends Ic2TileEntity {
    private static final int MANUAL_CHARGE_CAPACITY = 10_000;

    public final Energy energy;
    public final Redstone redstone;
    private final ComparatorEmitter comparator;
    private boolean invertRedstone;

    public LegacyLuminatorBlockEntity(BlockPos pos, BlockState state) {
        super(RestoredLegacyContent.LUMINATOR_BLOCK_ENTITY.get(), pos, state);
        energy = addComponent(Energy.asBasicSink(this, 5.0D));
        redstone = addComponent(new Redstone(this));
        comparator = addComponent(new ComparatorEmitter(this));
        comparator.setUpdate(energy::getComparatorValue);
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        invertRedstone = tag.m_128471_("invert");
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128379_("invert", invertRedstone);
    }

    @Override
    protected void onLoaded() {
        energy.setDirections(Set.of(getFacing().m_122424_()), Set.of());
        super.onLoaded();
        checkPlacement();
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean lit = redstone.hasRedstoneInput() != invertRedstone;
        boolean active = lit && energy.useEnergy(0.25D);
        if (getActive() != active) {
            setActive(active);
        }
    }

    @Override
    protected InteractionResult onActivated(
            Player player, InteractionHand hand, Direction side, Vec3 hit) {
        Level level = m_58904_();
        if (level == null) {
            return InteractionResult.PASS;
        }
        if (!level.f_46443_) {
            ItemStack stack = player.m_21120_(hand);
            double missing = MANUAL_CHARGE_CAPACITY - energy.getEnergy();
            double transferred = missing > 0.0D
                    ? ElectricItem.manager.discharge(
                            stack, missing, energy.getSinkTier(), true, true, false)
                    : 0.0D;
            if (transferred > 0.0D) {
                energy.forceAddEnergy(transferred);
            } else {
                invertRedstone = !invertRedstone;
                m_6596_();
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
        super.onNeighborChange(neighbor, neighborPos);
        checkPlacement();
    }

    private void checkPlacement() {
        Level level = m_58904_();
        if (level == null || level.f_46443_) {
            return;
        }
        Direction facing = getFacing();
        BlockPos support = m_58899_().m_121945_(facing.m_122424_());
        BlockState supportState = level.m_8055_(support);
        boolean solid = supportState.m_60783_(level, support, facing);
        boolean emitter = EnergyNet.instance.getSubTile(level, support) instanceof IEnergyEmitter;
        if (!solid && !emitter) {
            Block.m_49840_(level, m_58899_(), new ItemStack(RestoredLegacyContent.LUMINATOR_FLAT.get()));
            level.m_7471_(m_58899_(), false);
        }
    }

    @Override
    protected int getLightValue() {
        return getActive() ? 15 : 0;
    }

    @Override
    protected void onEntityCollision(Entity entity) {
        super.onEntityCollision(entity);
        if (getActive() && entity instanceof Monster && entity instanceof LivingEntity living) {
            living.m_20254_(living.m_6336_() == MobType.f_21641_ ? 20 : 10);
        }
    }

    @Override
    protected boolean canSetFacingWrench(Direction facing, Player player) {
        return true;
    }

    @Override
    protected boolean setFacingWrench(Level level, Direction facing, Player player) {
        invertRedstone = !invertRedstone;
        m_6596_();
        return true;
    }

    @Override
    protected boolean wrenchCanRemove(Player player) {
        return false;
    }

    @Override
    protected Direction getPlacementFacing(LivingEntity placer, Direction side) {
        return side;
    }

    @Override
    protected Set<Direction> getSupportedFacings() {
        return EnumSet.allOf(Direction.class);
    }

    @Override
    protected List<AABB> getAabbs(boolean collision) {
        return List.of(switch (getFacing()) {
            case NORTH -> new AABB(0.0D, 0.0D, 15.0D / 16.0D, 1.0D, 1.0D, 1.0D);
            case SOUTH -> new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D / 16.0D);
            case WEST -> new AABB(15.0D / 16.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            case EAST -> new AABB(0.0D, 0.0D, 0.0D, 1.0D / 16.0D, 1.0D, 1.0D);
            case DOWN -> new AABB(0.0D, 15.0D / 16.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            case UP -> new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D / 16.0D, 1.0D);
        });
    }

    public boolean isInverted() {
        return invertRedstone;
    }
}
