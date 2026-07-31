package ru.mot.ic2exfidelity.advancedsolars;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.mot.ic2exfidelity.friends.LegacyFriendManager;

/** Stores the owner of IC2 Classic's player-protected iridium stone. */
public final class LegacyIridiumStoneBlockEntity extends BlockEntity {
    private UUID owner;

    public LegacyIridiumStoneBlockEntity(BlockPos position, BlockState state) {
        super(LegacyAdvancedSolarsPrerequisites.IRIDIUM_STONE_BLOCK_ENTITY.get(),
                position, state);
    }

    public void setOwner(UUID owner) {
        if (this.owner == null) {
            this.owner = owner;
            m_6596_();
        }
    }

    public boolean isOwner(UUID candidate) {
        return owner != null && owner.equals(candidate);
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean canBreak(UUID candidate, boolean isOperator) {
        if (owner == null) {
            setOwner(candidate);
            return true;
        }
        if (owner.equals(candidate) || isOperator) {
            return true;
        }
        return f_58857_ instanceof ServerLevel serverLevel
                && LegacyFriendManager.get(serverLevel.m_7654_()).canApply(
                        owner, candidate, LegacyFriendManager.BREAK_IRIDIUM);
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        owner = tag.m_128403_("owner") ? tag.m_128342_("owner") : null;
    }

    @Override
    protected void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        if (owner != null) {
            tag.m_128362_("owner", owner);
        }
    }
}
