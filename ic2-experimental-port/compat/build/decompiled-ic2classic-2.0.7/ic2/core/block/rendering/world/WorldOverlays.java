/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 *  net.minecraftforge.client.event.RenderLevelStageEvent$Stage
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.block.rendering.world;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.rendering.world.IWorldOverlay;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.block.rendering.world.impl.ChunkLoaderOverlay;
import ic2.core.block.rendering.world.impl.EuReaderOverlay;
import ic2.core.block.rendering.world.impl.MultiBlockOverlay;
import ic2.core.block.rendering.world.impl.PipeOverlay;
import ic2.core.block.rendering.world.impl.SonarOverlay;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(value=Dist.CLIENT)
public class WorldOverlays {
    public static final WorldOverlays INSTANCE = new WorldOverlays();
    List<IWorldOverlay> overlays = CollectionUtils.createList();
    Level lastWorld;

    public synchronized void addOverlay(IWorldOverlay renderer) {
        this.overlays.add(renderer);
    }

    public void init() {
        this.overlays.add(BlockHighlighter.INSTANCE);
        this.overlays.add(ChunkLoaderOverlay.INSTANCE);
        this.overlays.add(new PipeOverlay());
        this.overlays.add(new EuReaderOverlay());
        this.overlays.add(SonarOverlay.INSTANCE);
        this.overlays.add(MultiBlockOverlay.INSTANCE);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START || this.overlays.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91073_ == null || mc.f_91074_ == null) {
            if (this.lastWorld != null) {
                this.cleanup();
            }
            return;
        }
        ClientLevel world = mc.f_91073_;
        if (world != this.lastWorld) {
            this.cleanup();
            this.lastWorld = world;
        }
        int m = this.overlays.size();
        for (int i = 0; i < m; ++i) {
            this.overlays.get(i).update((Level)world, (Player)mc.f_91074_);
        }
    }

    @SubscribeEvent
    public void onRender(RenderLevelStageEvent event) {
        if (this.overlays.isEmpty() || event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            return;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91073_ == null || mc.f_91074_ == null) {
            return;
        }
        Frustum helper = RenderUtils.createClipper(event);
        PoseStack stack = event.getPoseStack();
        stack.m_85836_();
        stack.m_166856_();
        int m = this.overlays.size();
        for (int i = 0; i < m; ++i) {
            this.overlays.get(i).render((Level)mc.f_91073_, (Player)mc.f_91074_, event, helper);
        }
        stack.m_85849_();
    }

    public void cleanup() {
        int m = this.overlays.size();
        for (int i = 0; i < m; ++i) {
            this.overlays.get(i).cleanup();
        }
        this.lastWorld = null;
    }
}

