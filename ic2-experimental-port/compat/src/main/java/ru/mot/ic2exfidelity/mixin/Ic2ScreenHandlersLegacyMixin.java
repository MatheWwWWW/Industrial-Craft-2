package ru.mot.ic2exfidelity.mixin;

import ic2.core.item.tool.ContainerMeter;
import ic2.core.item.tool.ContainerToolbox;
import ic2.core.ref.Ic2ScreenHandlers;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;
import ru.mot.ic2exfidelity.legacy.LegacyContainerCropAnalyzer;
import ru.mot.ic2exfidelity.legacy.LegacyContainerContainmentBox;
import ru.mot.ic2exfidelity.legacy.LegacyContainerTradingTerminal;
import ru.mot.ic2exfidelity.gravisuit.LegacyContainerNuclearJetpack;
import ru.mot.ic2exfidelity.gravisuit.LegacyContainerRelocator;

@Mixin(value = Ic2ScreenHandlers.class, remap = false)
public abstract class Ic2ScreenHandlersLegacyMixin {
    @Shadow
    private static <T extends AbstractContainerMenu> MenuType<T> registerManagedItem(String name) {
        throw new AssertionError();
    }

    @Shadow
    public static <T extends AbstractContainerMenu> MenuType<T> registerManagedBe(String name) {
        throw new AssertionError();
    }

    @Inject(method = "init", at = @At("RETURN"))
    private static void ic2ExperimentalFidelity$restoreMenus(CallbackInfo callback) {
        MenuType<ContainerMeter> meter = registerManagedItem("meter");
        MenuType<ContainerToolbox> toolbox = registerManagedItem("tool_box");
        MenuType<LegacyContainerCropAnalyzer> cropAnalyzer = registerManagedItem("cropnalyzer");
        MenuType<LegacyContainerContainmentBox> containmentBox =
                registerManagedItem("containment_box");
        MenuType<LegacyContainerNuclearJetpack> nuclearJetpack =
                registerManagedItem("nuclear_jetpack");
        MenuType<LegacyContainerRelocator> relocator =
                registerManagedItem("relocator");
        MenuType<LegacyContainerTradingTerminal> tradingTerminal =
                registerManagedBe("trading_terminal");
        RestoredLegacyContent.setMenus(
                meter, toolbox, cropAnalyzer, containmentBox,
                nuclearJetpack, relocator, tradingTerminal);
    }
}
