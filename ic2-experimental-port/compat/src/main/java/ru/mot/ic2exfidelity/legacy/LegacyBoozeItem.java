package ru.mot.ic2exfidelity.legacy;

import ic2.core.ref.Ic2Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

/** The metadata-packed IC2 2.8 beer and rum mug, stored in NBT on 1.19.2. */
public final class LegacyBoozeItem extends Item {
    private static final String VALUE_TAG = "BoozeValue";
    private static final String[] SOLID_NAMES = {
            "Watery ", "Clear ", "Lite ", "", "Strong ", "Thick ", "Stodge ", "X"
    };
    private static final String[] HOPS_NAMES = {
            "Soup ", "Alcfree ", "White ", "", "Dark ", "Full ", "Black ", "X"
    };
    private static final String[] TIME_NAMES = {
            "Brew", "Youngster", "Beer", "Ale", "Dragonblood", "Black Stuff"
    };
    private static final int[] BASE_DURATION = {300, 600, 900, 1_200, 1_600, 2_000, 2_400};
    private static final float[] BASE_INTENSITY = {0.4F, 0.75F, 1.0F, 1.5F, 2.0F};
    private static final float RUM_STACKABILITY = 2.0F;
    private static final int RUM_DURATION = 600;

    public LegacyBoozeItem(Properties properties) {
        super(properties.m_41487_(1));
    }

    public static ItemStack create(int value) {
        ItemStack stack = new ItemStack(
                ru.mot.ic2exfidelity.integration.RestoredLegacyContent.BOOZE_MUG.get());
        stack.m_41784_().m_128405_(VALUE_TAG, value);
        return stack;
    }

    public static int getValue(ItemStack stack) {
        CompoundTag tag = stack.m_41783_();
        return tag == null ? 0 : tag.m_128451_(VALUE_TAG);
    }

    @Override
    public Component m_7626_(ItemStack stack) {
        int value = getValue(stack);
        int type = getTypeOfValue(value);
        if (type == 1) {
            int time = Math.min(getTimeRatioOfBeerValue(value), TIME_NAMES.length - 1);
            if (time == TIME_NAMES.length - 1) {
                return Component.m_237113_(TIME_NAMES[time]);
            }
            return Component.m_237113_(
                    SOLID_NAMES[getSolidRatioOfBeerValue(value)]
                            + HOPS_NAMES[getHopsRatioOfBeerValue(value)]
                            + TIME_NAMES[time]);
        }
        if (type == 2) {
            return Component.m_237113_("Rum");
        }
        return Component.m_237113_("Zero");
    }

    @Override
    public ItemStack m_5922_(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.f_46443_) {
            drink(entity, getValue(stack));
        }
        return new ItemStack(Ic2Items.EMPTY_MUG);
    }

    private void drink(LivingEntity entity, int value) {
        int type = getTypeOfValue(value);
        if (type == 1) {
            int timeRatio = getTimeRatioOfBeerValue(value);
            if (timeRatio == 5) {
                drinkBlackStuff(entity);
                return;
            }
            int solidRatio = getSolidRatioOfBeerValue(value);
            int hopsRatio = getHopsRatioOfBeerValue(value);
            int duration = BASE_DURATION[solidRatio];
            float intensity = BASE_INTENSITY[timeRatio];
            if (entity instanceof Player player) {
                player.m_36324_().m_38707_(6 - hopsRatio, solidRatio * 0.15F);
            }

            MobEffect fatigue = effect("mining_fatigue");
            MobEffectInstance previous = entity.m_21124_(fatigue);
            int previousAmplifier = previous == null ? -1 : previous.m_19564_();
            int amplifier = (int) (intensity * hopsRatio * 0.5F);
            amplifyEffect(entity, fatigue, amplifier, intensity, duration);
            if (previousAmplifier > -1) {
                amplifyEffect(entity, effect("strength"), amplifier, intensity, duration);
            }
            if (previousAmplifier > 0) {
                amplifyEffect(entity, effect("slowness"), amplifier / 2, intensity, duration);
            }
            if (previousAmplifier > 1) {
                amplifyEffect(entity, effect("resistance"), amplifier - 1, intensity, duration);
            }
            if (previousAmplifier > 2) {
                amplifyEffect(entity, effect("nausea"), 0, intensity, duration);
            }
            if (previousAmplifier > 3) {
                entity.m_7292_(new MobEffectInstance(
                        effect("instant_damage"), 1, entity.f_19853_.f_46441_.m_188503_(3)));
            }
        } else if (type == 2) {
            if (getProgressOfRumValue(value) < 100) {
                drinkBlackStuff(entity);
                return;
            }
            amplifyEffect(entity, effect("fire_resistance"), 0, RUM_STACKABILITY, RUM_DURATION);
            MobEffect resistance = effect("resistance");
            MobEffectInstance previous = entity.m_21124_(resistance);
            int previousAmplifier = previous == null ? -1 : previous.m_19564_();
            amplifyEffect(entity, resistance, 2, RUM_STACKABILITY, RUM_DURATION);
            if (previousAmplifier >= 0) {
                amplifyEffect(entity, effect("blindness"), 0, RUM_STACKABILITY, RUM_DURATION);
            }
            if (previousAmplifier >= 1) {
                amplifyEffect(entity, effect("nausea"), 0, RUM_STACKABILITY, RUM_DURATION);
            }
        }
    }

    private static void amplifyEffect(
            LivingEntity entity,
            MobEffect effect,
            int amplifierLimit,
            float stackability,
            int duration) {
        MobEffectInstance current = entity.m_21124_(effect);
        if (current == null) {
            entity.m_7292_(new MobEffectInstance(effect, duration, 0));
            return;
        }

        int oldDuration = current.m_19557_();
        int added = (int) ((duration * (1.0F + stackability * 2.0F) - oldDuration) / 2.0F);
        added = Math.max(0, Math.min(added, duration));
        int amplifier = current.m_19564_();
        if (amplifier < amplifierLimit) {
            amplifier++;
        }
        entity.m_7292_(new MobEffectInstance(effect, oldDuration + added, amplifier));
    }

    private static void drinkBlackStuff(LivingEntity entity) {
        switch (entity.f_19853_.f_46441_.m_188503_(6)) {
            case 1 -> entity.m_7292_(new MobEffectInstance(effect("nausea"), 1_200, 0));
            case 2 -> entity.m_7292_(new MobEffectInstance(effect("blindness"), 2_400, 0));
            case 3 -> entity.m_7292_(new MobEffectInstance(effect("poison"), 2_400, 0));
            case 4 -> entity.m_7292_(new MobEffectInstance(effect("poison"), 200, 2));
            case 5 -> entity.m_7292_(new MobEffectInstance(
                    effect("instant_damage"), 1, entity.f_19853_.f_46441_.m_188503_(4)));
            default -> {
            }
        }
    }

    private static MobEffect effect(String path) {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("minecraft", path));
        if (effect == null) {
            throw new IllegalStateException("Missing vanilla effect: " + path);
        }
        return effect;
    }

    @Override
    public int m_8105_(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim m_6164_(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        player.m_6672_(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.m_21120_(hand));
    }

    public static int getTypeOfValue(int value) {
        return unpackValue(value, 0, 2);
    }

    public static int getAmountOfValue(int value) {
        return getTypeOfValue(value) == 0 ? 0 : unpackValue(value, 2, 5) + 1;
    }

    public static int getSolidRatioOfBeerValue(int value) {
        return unpackValue(value, 7, 3);
    }

    public static int getHopsRatioOfBeerValue(int value) {
        return unpackValue(value, 10, 3);
    }

    public static int getTimeRatioOfBeerValue(int value) {
        return unpackValue(value, 13, 3);
    }

    public static int getProgressOfRumValue(int value) {
        return unpackValue(value, 7, 7);
    }

    public static int getModelVariant(ItemStack stack) {
        int value = getValue(stack);
        int type = getTypeOfValue(value);
        if (type == 2) {
            return 7;
        }
        return type == 1 ? Math.min(6, getTimeRatioOfBeerValue(value) + 1) : 1;
    }

    private static int unpackValue(int value, int offset, int length) {
        return (value >>> offset) & ((1 << length) - 1);
    }
}
