/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package ic2.api.network.tile;

import ic2.api.network.IPlayerPacket;
import java.util.Set;
import net.minecraft.world.entity.player.Player;

public interface INetworkFieldNotifier
extends IPlayerPacket {
    public void onNetworkFieldChanged(Set<String> var1, Player var2);

    public void onGuiFieldChanged(Set<String> var1, Player var2);
}

