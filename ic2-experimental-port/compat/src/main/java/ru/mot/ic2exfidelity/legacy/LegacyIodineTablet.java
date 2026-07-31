package ru.mot.ic2exfidelity.legacy;

import ic2.core.Ic2Potion;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Consumes one tablet per whole second of radiation removed. */
public final class LegacyIodineTablet extends Item {
    private final Supplier<SoundEvent> eatSound;

    public LegacyIodineTablet(Properties properties, Supplier<SoundEvent> eatSound) {
        super(properties);
        this.eatSound = eatSound;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(
            Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.m_21120_(hand);
        if (level.f_46443_) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        MobEffectInstance radiation = player.m_21124_(Ic2Potion.radiation);
        if (radiation == null) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
        int seconds = radiation.m_19557_() / 20;
        int consumed = Math.min(stack.m_41613_(), seconds);
        if (consumed <= 0) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        player.m_6234_(Ic2Potion.radiation);
        if (consumed < seconds) {
            player.m_7292_(new MobEffectInstance(
                    Ic2Potion.radiation, (seconds - consumed) * 20));
        }
        stack.m_41774_(consumed);
        player.m_5496_(eatSound.get(), 1.0F, 1.0F);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
