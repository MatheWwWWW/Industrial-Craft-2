package ru.mot.ic2exfidelity.integration;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.mot.ic2exfidelity.Ic2ExperimentalFidelity;
import ru.mot.ic2exfidelity.legacy.LegacyJetpackAttachmentRecipe;
import ru.mot.ic2exfidelity.legacy.LegacyJetpackHandler;

/** Registration and lifecycle wiring for IC2 2.8's omitted jetpack systems. */
public final class LegacyJetpackContent {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(
                    ForgeRegistries.RECIPE_SERIALIZERS, Ic2ExperimentalFidelity.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> JETPACK_ATTACHMENT =
            RECIPE_SERIALIZERS.register(
                    "jetpack_attachment",
                    () -> new SimpleRecipeSerializer<>(LegacyJetpackAttachmentRecipe::new));

    private LegacyJetpackContent() {
    }

    public static void register(IEventBus modBus) {
        RECIPE_SERIALIZERS.register(modBus);
    }

    public static void install() {
        LegacyJetpackHandler.install();
    }
}
