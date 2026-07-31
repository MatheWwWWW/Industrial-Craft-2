/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
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
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Beer
extends IDrinkableFluid {
    public static final MobEffect[] EFFECTS = new MobEffect[]{MobEffects.f_19600_, MobEffects.f_19597_, MobEffects.f_19606_, MobEffects.f_19604_};
    public static final int[] DURATIONS = new int[]{600, 900, 1200, 1600, 2000, 2400, 2800};
    public static final float[] INTENSITY = new float[]{0.4f, 0.75f, 1.0f, 1.5f, 2.0f};
    public static final String[] TEXTURES = new String[]{"brew", "youngster", "beer", "ale", "dragonblood", "black_stuff"};
    public static final String[] SOLIDS = new String[]{"subitem.ic2.beer_solid_watery", "subitem.ic2.beer_solid_clear", "subitem.ic2.beer_solid_lite", "", "subitem.ic2.beer_solid_strong", "subitem.ic2.beer_solid_thick", "subitem.ic2.beer_solid_stodge"};
    public static final String[] HOPS = new String[]{"subitem.ic2.beer_hops_soup", "subitem.ic2.beer_hops_alcfree", "subitem.ic2.beer_hops_white", "", "subitem.ic2.beer_hops_normal", "subitem.ic2.beer_hops_dark", "subitem.ic2.beer_hops_full", "subitem.ic2.beer_hops_black"};
    public static final String[] TIME = new String[]{"subitem.ic2.beer_time_brew", "subitem.ic2.beer_time_youngster", "subitem.ic2.beer_time_beer", "subitem.ic2.beer_time_ale", "subitem.ic2.beer_time_dragonblood", "subitem.ic2.beer_time_black_stuff"};

    public Beer(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        int data = StackUtil.getNbtData(stack).m_128451_("data");
        int time = Beer.getTime(data);
        if (time >= 5) {
            int rand = world.f_46441_.m_188503_(6);
            if (rand == 5) {
                MobEffects.f_19602_.m_19461_((Entity)player, (Entity)player, (LivingEntity)player, world.f_46441_.m_188503_(4), 0.25);
            } else if (rand != 0) {
                player.m_7292_(new MobEffectInstance(Rum.EFFECTS[rand - 1]));
            }
            return true;
        }
        int hops = Beer.getHops(data);
        int solid = Beer.getSolid(data);
        int duration = DURATIONS[solid];
        float intensity = INTENSITY[time];
        player.m_36324_().m_38707_(6 - hops, (float)solid * 0.15f);
        int max = (int)(intensity * (float)hops * 0.5f);
        MobEffectInstance effect = player.m_21124_(MobEffects.f_19599_);
        int lvl = effect != null ? effect.m_19564_() : -1;
        Rum.amplifyEffect(player, MobEffects.f_19599_, max, intensity, duration);
        int i = 0;
        while (lvl > -1) {
            if (i > 3) {
                MobEffects.f_19602_.m_19461_((Entity)player, (Entity)player, (LivingEntity)player, world.f_46441_.m_188503_(3), 0.25);
            } else {
                Rum.amplifyEffect(player, EFFECTS[i], this.getMax(i, max), intensity, duration);
            }
            --lvl;
            ++i;
        }
        return true;
    }

    @Override
    public boolean hasSpecialName() {
        return true;
    }

    @Override
    public Component getSpecialName(ItemStack stack) {
        int data = StackUtil.getNbtData(stack).m_128451_("data");
        int time = Beer.getTime(data);
        int hops = Beer.getHops(data);
        int solid = Beer.getSolid(data);
        MutableComponent comp = Component.m_237119_();
        String s = SOLIDS[Mth.m_14045_((int)solid, (int)0, (int)(SOLIDS.length - 1))];
        if (s.length() > 0) {
            comp.m_7220_((Component)Component.m_237115_((String)s)).m_7220_((Component)Component.m_237113_((String)" "));
        }
        if ((s = HOPS[Mth.m_14045_((int)hops, (int)0, (int)(HOPS.length - 1))]).length() > 0) {
            comp.m_7220_((Component)Component.m_237115_((String)s)).m_7220_((Component)Component.m_237113_((String)" "));
        }
        if ((s = TIME[Mth.m_14045_((int)time, (int)0, (int)(TIME.length - 1))]).length() > 0) {
            comp.m_7220_((Component)Component.m_237115_((String)s));
        }
        return comp;
    }

    @Override
    public int getTextureIndex(ItemStack stack) {
        return Beer.getTime(StackUtil.getNbtData(stack).m_128451_("data"));
    }

    @Override
    public List<ItemStack> generateSubStates(ItemStack base, boolean textures) {
        if (textures) {
            ObjectList list = CollectionUtils.createList();
            for (int i = 0; i < 6; ++i) {
                list.add((ItemStack)Beer.createData(base.m_41777_(), i, 0, 0));
            }
            return list;
        }
        ObjectList list = CollectionUtils.createList();
        for (int x = 0; x < 6; ++x) {
            for (int y = 0; y < 7; ++y) {
                for (int z = 0; z < 7; ++z) {
                    list.add((ItemStack)Beer.createData(base.m_41777_(), x, y, z));
                }
            }
        }
        return list;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + "/" + TEXTURES[Beer.getTime(StackUtil.getNbtData(stack).m_128451_("data")) % TEXTURES.length]);
    }

    public static int getTime(int data) {
        return data >> 6 & 7;
    }

    public static int getSolid(int data) {
        return data >> 3 & 7;
    }

    public static int getHops(int data) {
        return data & 7;
    }

    public int getMax(int lvl, int max) {
        switch (lvl) {
            case 1: {
                return max / 2;
            }
            case 2: {
                return max - 1;
            }
            case 3: {
                return 0;
            }
        }
        return max;
    }

    public static int createData(int time, int solid, int hops) {
        return (time & 7) << 6 | (solid & 7) << 3 | hops & 7;
    }

    public static ItemStack createData(ItemStack stack, int time, int solid, int hops) {
        if (!stack.m_41619_()) {
            stack.m_41784_().m_128405_("data", (time & 7) << 6 | (solid & 7) << 3 | hops & 7);
        }
        return stack;
    }
}

