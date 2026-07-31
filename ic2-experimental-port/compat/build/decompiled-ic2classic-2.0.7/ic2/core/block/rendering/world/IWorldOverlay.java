/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 */
package ic2.core.block.rendering.world;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public interface IWorldOverlay {
    public void cleanup();

    public void update(Level var1, Player var2);

    public void render(Level var1, Player var2, RenderLevelStageEvent var3, Frustum var4);
}

