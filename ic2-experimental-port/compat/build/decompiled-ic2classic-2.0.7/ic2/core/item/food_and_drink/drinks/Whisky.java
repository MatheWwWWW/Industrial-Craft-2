/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.food_and_drink.drinks;

import ic2.api.items.IDrinkableFluid;
import ic2.core.item.food_and_drink.drinks.Rum;
import ic2.core.platform.registries.IC2Potions;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Whisky
extends IDrinkableFluid {
    public static final String[] TEXTURES = new String[]{"black_stuff", "whisky_10y", "whisky_12y", "whisky_15y", "whisky_25y", "whisky_50y"};
    public static final String[] YEARS = new String[]{"0", "10", "12", "15", "25", "50"};
    public static final MobEffect[] EFFECTS = new MobEffect[]{MobEffects.f_19617_, MobEffects.f_19606_, MobEffects.f_19604_};
    public static final int[] DURATIONS = new int[]{0, 6000, 7200, 9000, 15000, 30000};
    public static final int[] LEVELS = new int[]{0, 1, 1, 2, 3, 4};
    public static final int[] YEAR_NUMBERS = new int[]{0, 10, 12, 15, 25, 50};

    public Whisky() {
        super(new ResourceLocation("ic2", "whisky"));
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        int year = Whisky.getYear(stack);
        if (year == 0) {
            int rand = world.f_46441_.m_188503_(6);
            if (rand == 5) {
                MobEffects.f_19602_.m_19461_((Entity)player, (Entity)player, (LivingEntity)player, world.f_46441_.m_188503_(4), 0.25);
            } else if (rand != 0) {
                player.m_7292_(new MobEffectInstance(Rum.EFFECTS[rand - 1]));
            }
            return true;
        }
        player.m_36324_().m_38707_(4, (float)(year / 2));
        Whisky.amplifyEffect(player, IC2Potions.SHAKY, DURATIONS[year] * 5, LEVELS[year], DURATIONS[year]);
        Whisky.amplifyEffect(player, EFFECTS[world.f_46441_.m_188503_(256) % EFFECTS.length], DURATIONS[year] * 5, LEVELS[year], DURATIONS[year]);
        return true;
    }

    public static void amplifyEffect(Player player, MobEffect potion, int max, int level, int duration) {
        MobEffectInstance eff = player.m_21124_(potion);
        if (eff == null) {
            player.m_7292_(new MobEffectInstance(potion, duration));
        } else {
            int newDuration = eff.m_19557_();
            player.m_7292_(new MobEffectInstance(potion, Math.min(newDuration + duration, max), level));
        }
    }

    @Override
    public boolean hasSpecialName() {
        return true;
    }

    @Override
    public Component getSpecialName(ItemStack stack) {
        return Component.m_237110_((String)"item.ic2.glass_whisky", (Object[])new Object[]{YEARS[Whisky.getYear(stack)]});
    }

    @Override
    public int getTextureIndex(ItemStack stack) {
        return Whisky.getYear(stack);
    }

    @Override
    public List<ItemStack> generateSubStates(ItemStack base, boolean textures) {
        ObjectList list = CollectionUtils.createList();
        for (int i = 0; i < 6; ++i) {
            list.add((ItemStack)Whisky.createYear(base.m_41777_(), i));
        }
        return list;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/" + TEXTURES[Whisky.getYear(stack)]);
    }

    public static int getYear(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("age");
    }

    public static ItemStack createYear(ItemStack stack, int year) {
        if (!stack.m_41619_()) {
            stack.m_41784_().m_128405_("age", year);
        }
        return stack;
    }
}

