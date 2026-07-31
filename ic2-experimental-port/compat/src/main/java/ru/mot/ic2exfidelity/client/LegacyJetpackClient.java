package ru.mot.ic2exfidelity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;
import ru.mot.ic2exfidelity.legacy.LegacyJetpackHandler;

/** Adds the electric-jetpack armor model over chest armor carrying the attachment marker. */
@Mod.EventBusSubscriber(
        modid = Ic2ExperimentalFidelity.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public final class LegacyJetpackClient {
    private static final Method ADD_LAYER = findAddLayer();

    private LegacyJetpackClient() {
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        int installed = 0;
        for (String skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            if (renderer == null) {
                continue;
            }
            try {
                ADD_LAYER.invoke(
                        renderer,
                        new AttachedJetpackLayer(renderer, event.getEntityModels()));
                installed++;
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new IllegalStateException(
                        "Unable to install the IC2 attached-jetpack render layer", exception);
            }
        }
        System.out.println("[IC2-FIDELITY-JETPACK-RENDER] layers=" + installed);
    }

    private static Method findAddLayer() {
        try {
            Method method = LivingEntityRenderer.class.getDeclaredMethod(
                    "m_115326_", RenderLayer.class);
            method.setAccessible(true);
            return method;
        } catch (ReflectiveOperationException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static final class AttachedJetpackLayer
            extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
        private final HumanoidArmorLayer<
                        AbstractClientPlayer,
                        PlayerModel<AbstractClientPlayer>,
                        HumanoidModel<AbstractClientPlayer>>
                armorLayer;

        private AttachedJetpackLayer(PlayerRenderer parent, EntityModelSet models) {
            super(parent);
            armorLayer = new HumanoidArmorLayer<>(
                    parent,
                    new HumanoidModel<>(models.m_171103_(ModelLayers.f_171164_)),
                    new HumanoidModel<>(models.m_171103_(ModelLayers.f_171165_)));
        }

        @Override
        public void m_6494_(
                PoseStack poseStack,
                MultiBufferSource buffers,
                int packedLight,
                AbstractClientPlayer player,
                float limbSwing,
                float limbSwingAmount,
                float partialTick,
                float ageInTicks,
                float netHeadYaw,
                float headPitch) {
            ItemStack attachedArmor = player.m_6844_(EquipmentSlot.CHEST);
            if (!LegacyJetpackHandler.hasJetpackAttached(attachedArmor)) {
                return;
            }

            ItemStack head = player.m_6844_(EquipmentSlot.HEAD);
            ItemStack legs = player.m_6844_(EquipmentSlot.LEGS);
            ItemStack feet = player.m_6844_(EquipmentSlot.FEET);
            try {
                player.m_8061_(EquipmentSlot.HEAD, ItemStack.f_41583_);
                player.m_8061_(EquipmentSlot.CHEST,
                        new ItemStack(RestoredLegacyContent.ELECTRIC_JETPACK.get()));
                player.m_8061_(EquipmentSlot.LEGS, ItemStack.f_41583_);
                player.m_8061_(EquipmentSlot.FEET, ItemStack.f_41583_);
                armorLayer.m_6494_(
                        poseStack,
                        buffers,
                        packedLight,
                        player,
                        limbSwing,
                        limbSwingAmount,
                        partialTick,
                        ageInTicks,
                        netHeadYaw,
                        headPitch);
            } finally {
                player.m_8061_(EquipmentSlot.HEAD, head);
                player.m_8061_(EquipmentSlot.CHEST, attachedArmor);
                player.m_8061_(EquipmentSlot.LEGS, legs);
                player.m_8061_(EquipmentSlot.FEET, feet);
            }
        }
    }
}
