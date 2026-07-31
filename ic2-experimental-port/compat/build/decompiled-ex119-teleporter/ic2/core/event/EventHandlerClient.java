/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Registry
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 */
package ic2.core.event;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.item.ElectricItem;
import ic2.api.item.IEnhancedOverlayProvider;
import ic2.core.IC2;
import ic2.core.audio.AudioManagerClient;
import ic2.core.fluid.FluidHandler;
import ic2.core.item.tool.AbstractItemNanoSaber;
import ic2.core.item.upgrade.ItemUpgradeModule;
import ic2.core.network.RpcHandler;
import ic2.core.proxy.SideProxyClient;
import ic2.core.sound.SoundManagerClient;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EventHandlerClient {
    public static void onClientSetup() {
        SideProxyClient.envProxy.registerModelPredicateProvider(IC2.getIdentifier("charge"), (itemStack, clientLevel, livingEntity, n) -> (float)ElectricItem.manager.getChargeLevel(itemStack));
        for (Direction direction : Util.ALL_DIRS) {
            SideProxyClient.envProxy.registerModelPredicateProvider(IC2.getIdentifier(direction.m_122433_()), (itemStack, clientLevel, livingEntity, n) -> {
                if (itemStack.m_41720_() instanceof ItemUpgradeModule && ItemUpgradeModule.getDirection(itemStack) == direction) {
                    return 1.0f;
                }
                return 0.0f;
            });
        }
        SideProxyClient.envProxy.registerModelPredicateProvider(IC2.getIdentifier("nano_saber_active"), (itemStack, clientLevel, livingEntity, n) -> {
            Item item = itemStack.m_41720_();
            if (item instanceof AbstractItemNanoSaber) {
                AbstractItemNanoSaber abstractItemNanoSaber = (AbstractItemNanoSaber)item;
                return abstractItemNanoSaber.getActiveData();
            }
            return 0.0f;
        });
    }

    public static void onSoundSetup() {
        ((AudioManagerClient)IC2.sideProxy.getAudioManager()).onSoundSetup();
    }

    public static void livingEntityPreRender(LivingEntity livingEntity, LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> livingEntityRenderer) {
    }

    public static void livingEntityPostRender(LivingEntity livingEntity, LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> livingEntityRenderer) {
    }

    public static float onSetupFogDensity(BlockState blockState) {
        Fluid fluid = FluidHandler.getWorldFluid(blockState);
        if (fluid != null && "ic2".equals(Registry.f_122822_.m_7981_((Object)fluid).m_135827_())) {
            int n = FluidHandler.getDensity(fluid);
            return (float)Util.map(Math.abs(n), 20000.0, 2.0);
        }
        return -1.0f;
    }

    public static int onRenderFogColor(BlockState blockState) {
        Fluid fluid = FluidHandler.getWorldFluid(blockState);
        if (fluid != null && "ic2".equals(Registry.f_122822_.m_7981_((Object)fluid).m_135827_())) {
            int n = FluidHandler.getColor(fluid);
            return n;
        }
        return -1;
    }

    public static void onDrawBlockHighlight(Player player, BlockHitResult blockHitResult, float f, PoseStack poseStack, MultiBufferSource multiBufferSource) {
        assert (blockHitResult.m_6662_() == HitResult.Type.BLOCK);
        ItemStack itemStack = StackUtil.get(player, InteractionHand.MAIN_HAND);
        if (itemStack.m_41720_() instanceof IEnhancedOverlayProvider) {
            // empty if block
        }
    }

    public static boolean onDrawBlockHighlightLast(Player player, BlockHitResult blockHitResult, float f, PoseStack poseStack, MultiBufferSource multiBufferSource) {
        assert (blockHitResult.m_6662_() == HitResult.Type.BLOCK);
        Level level = player.m_20193_();
        BlockPos blockPos = blockHitResult.m_82425_();
        if (!level.m_6857_().m_61937_(blockPos)) {
            return false;
        }
        BlockEntity blockEntity = level.m_7702_(blockPos);
        return false;
    }

    public static void onGuiCreate(Screen screen, List<GuiEventListener> list, Consumer<GuiEventListener> consumer) {
    }

    public static void onDrawTooltip(ItemStack itemStack, List<Component> list) {
    }

    public static void onRenderHotBar() {
    }

    public static SoundInstance onSoundPlayed(SoundInstance soundInstance) {
        return SoundManagerClient.onSoundPlayed(soundInstance);
    }

    public static void onDisconnect() {
        RpcHandler.onDisconnect();
    }
}

