/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.shaders.FogShape
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.client.event.RenderGuiOverlayEvent$Post
 *  net.minecraftforge.client.event.RenderHighlightEvent$Block
 *  net.minecraftforge.client.event.RenderLivingEvent$Post
 *  net.minecraftforge.client.event.RenderLivingEvent$Pre
 *  net.minecraftforge.client.event.ScreenEvent$Init
 *  net.minecraftforge.client.event.ViewportEvent$ComputeFogColor
 *  net.minecraftforge.client.event.ViewportEvent$RenderFog
 *  net.minecraftforge.client.event.sound.PlaySoundEvent
 *  net.minecraftforge.client.event.sound.SoundEngineLoadEvent
 *  net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.event.entity.player.PlayerEvent$PlayerLoggedOutEvent
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.forge;

import com.mojang.blaze3d.shaders.FogShape;
import ic2.core.event.EventHandlerClient;
import ic2.core.event.TickHandler;
import ic2.core.proxy.SideProxyClient;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundEngineLoadEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ClientEventHandlerForge {
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent clientTickEvent) {
        if (clientTickEvent.phase == TickEvent.Phase.START) {
            TickHandler.onClientTick();
        }
    }

    @SubscribeEvent
    public void onSoundSetup(SoundEngineLoadEvent soundEngineLoadEvent) {
        EventHandlerClient.onSoundSetup();
    }

    @SubscribeEvent
    public void livingEntityPreRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> pre) {
        EventHandlerClient.livingEntityPreRender(pre.getEntity(), (LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>)pre.getRenderer());
    }

    @SubscribeEvent
    public void livingEntityPostRender(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> post) {
        EventHandlerClient.livingEntityPostRender(post.getEntity(), (LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>)post.getRenderer());
    }

    @SubscribeEvent
    public void onSetupFogDensity(ViewportEvent.RenderFog renderFog) {
        float f = EventHandlerClient.onSetupFogDensity(renderFog.getCamera().getBlockAtCamera());
        if (f >= 0.0f) {
            renderFog.setCanceled(true);
            renderFog.setNearPlaneDistance(-8.0f);
            renderFog.setFarPlaneDistance(f * 0.5f);
            renderFog.setFogShape(FogShape.SPHERE);
        }
    }

    @SubscribeEvent
    public void onRenderFogColor(ViewportEvent.ComputeFogColor computeFogColor) {
        int n = EventHandlerClient.onRenderFogColor(computeFogColor.getCamera().getBlockAtCamera());
        if (n >= 0) {
            computeFogColor.setRed((float)(n >>> 16 & 0xFF) / 255.0f);
            computeFogColor.setGreen((float)(n >>> 8 & 0xFF) / 255.0f);
            computeFogColor.setBlue((float)(n & 0xFF) / 255.0f);
        }
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(RenderHighlightEvent.Block block) {
        EventHandlerClient.onDrawBlockHighlight((Player)SideProxyClient.mc.f_91074_, block.getTarget(), block.getPartialTick(), block.getPoseStack(), block.getMultiBufferSource());
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onDrawBlockHighlightLast(RenderHighlightEvent.Block block) {
        if (EventHandlerClient.onDrawBlockHighlightLast((Player)SideProxyClient.mc.f_91074_, block.getTarget(), block.getPartialTick(), block.getPoseStack(), block.getMultiBufferSource())) {
            block.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onGuiCreate(ScreenEvent.Init init) {
        EventHandlerClient.onGuiCreate(init.getScreen(), init.getListenersList(), arg_0 -> ((ScreenEvent.Init)init).addListener(arg_0));
    }

    @SubscribeEvent
    public void onDrawTooltip(ItemTooltipEvent itemTooltipEvent) {
        EventHandlerClient.onDrawTooltip(itemTooltipEvent.getItemStack(), itemTooltipEvent.getToolTip());
    }

    @SubscribeEvent
    public void onRenderHotBar(RenderGuiOverlayEvent.Post post) {
        if (post.getOverlay() == VanillaGuiOverlay.HOTBAR.type()) {
            EventHandlerClient.onRenderHotBar();
        }
    }

    @SubscribeEvent
    public void onSoundPlayed(PlaySoundEvent playSoundEvent) {
        SoundInstance soundInstance = playSoundEvent.getSound();
        SoundInstance soundInstance2 = EventHandlerClient.onSoundPlayed(soundInstance);
        if (soundInstance2 != soundInstance) {
            playSoundEvent.setSound(soundInstance2);
        }
    }

    @SubscribeEvent
    public void onDisconnect(PlayerEvent.PlayerLoggedOutEvent playerLoggedOutEvent) {
        EventHandlerClient.onDisconnect();
    }
}

