package ic2.api.transport;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

/** IC2 pipe API retained for 1.19.2 add-ons. */
public interface IPipe {
    BlockEntity getTile();

    boolean isConnected(Direction side);

    void flipConnection(Direction side);
}
