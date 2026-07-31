package ru.mot.ic2exfidelity.integration;

import ic2.core.item.tool.GuiToolMeter;
import ic2.core.item.tool.GuiToolbox;
import ic2.core.proxy.SideProxyClient;
import ic2.core.block.transport.BlockFluidPipe;
import ic2.core.item.block.ItemFluidPipe;
import ic2.core.item.logistics.ItemPumpCover;
import java.lang.reflect.Method;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;
import ru.mot.ic2exfidelity.legacy.LegacyGuiCropAnalyzer;
import ru.mot.ic2exfidelity.legacy.LegacyGuiContainmentBox;
import ru.mot.ic2exfidelity.legacy.LegacyBoozeItem;
import ru.mot.ic2exfidelity.legacy.LegacyGuiTradingTerminal;
import ru.mot.ic2exfidelity.gravisuit.LegacyGuiNuclearJetpack;
import ru.mot.ic2exfidelity.gravisuit.LegacyGuiRelocator;

@Mod.EventBusSubscriber(
        modid = Ic2ExperimentalFidelity.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT)
public final class RestoredLegacyClient {
    private RestoredLegacyClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.toolboxMenu(), GuiToolbox::new);
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.meterMenu(), GuiToolMeter::new);
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.cropAnalyzerMenu(), LegacyGuiCropAnalyzer::new);
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.containmentBoxMenu(), LegacyGuiContainmentBox::new);
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.nuclearJetpackMenu(), LegacyGuiNuclearJetpack::new);
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.relocatorMenu(), LegacyGuiRelocator::new);
            SideProxyClient.envProxy.registerScreen(
                    RestoredLegacyContent.tradingTerminalMenu(), LegacyGuiTradingTerminal::new);
            registerBoozeModelProperty();
            registerPipeModelProperty();
        });
    }

    @SubscribeEvent
    public static void onItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> ItemFluidPipe.getPipeType(stack).getColor(),
                RestoredLegacyContent.PIPE.get());
        event.register(
                (stack, tintIndex) -> tintIndex == 1
                        ? ItemPumpCover.getType(stack).getColor()
                        : 0xFFFFFF,
                RestoredLegacyContent.COVER.get());
    }

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> state.m_61143_(BlockFluidPipe.TYPE).getColor(),
                RestoredLegacyContent.FLUID_PIPE_BLOCK.get());
    }

    private static void registerBoozeModelProperty() {
        try {
            Method register = ItemProperties.class.getDeclaredMethod(
                    "m_174570_",
                    net.minecraft.world.item.Item.class,
                    ResourceLocation.class,
                    ClampedItemPropertyFunction.class);
            register.setAccessible(true);
            ClampedItemPropertyFunction function =
                    (stack, level, entity, seed) -> LegacyBoozeItem.getModelVariant(stack);
            register.invoke(
                    null,
                    RestoredLegacyContent.BOOZE_MUG.get(),
                    new ResourceLocation("ic2", "booze_variant"),
                    function);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to register booze mug model property", exception);
        }
    }

    private static void registerPipeModelProperty() {
        try {
            Method register = ItemProperties.class.getDeclaredMethod(
                    "m_174570_",
                    net.minecraft.world.item.Item.class,
                    ResourceLocation.class,
                    ClampedItemPropertyFunction.class);
            register.setAccessible(true);
            ClampedItemPropertyFunction function =
                    (stack, level, entity, seed) -> ItemFluidPipe.getPipeSize(stack).ordinal() / 4.0F;
            register.invoke(
                    null,
                    RestoredLegacyContent.PIPE.get(),
                    new ResourceLocation("ic2", "pipe_variant"),
                    function);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to register fluid-pipe model property", exception);
        }
    }
}
