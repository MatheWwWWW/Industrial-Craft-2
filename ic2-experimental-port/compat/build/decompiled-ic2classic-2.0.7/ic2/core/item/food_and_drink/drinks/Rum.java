/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
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
import ic2.core.platform.registries.IC2Potions;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
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

public class Rum
extends IDrinkableFluid {
    public static final MobEffectInstance[] EFFECTS = new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19604_, 1200, 0), new MobEffectInstance(MobEffects.f_19610_, 2400, 0), new MobEffectInstance(MobEffects.f_19614_, 2400, 0), new MobEffectInstance(MobEffects.f_19614_, 200, 2)};

    public Rum(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean drink(ItemStack stack, Level world, Player player) {
        int progress = StackUtil.getNbtData(stack).m_128451_("progress");
        if (progress < 100) {
            int rand = world.f_46441_.m_188503_(6);
            if (rand == 5) {
                MobEffects.f_19602_.m_19461_((Entity)player, (Entity)player, (LivingEntity)player, world.f_46441_.m_188503_(4), 0.25);
            } else if (rand != 0) {
                player.m_7292_(new MobEffectInstance(EFFECTS[rand - 1]));
            }
            return true;
        }
        Rum.amplifyEffect(player, MobEffects.f_19607_, 0, 2.0f, 2400);
        MobEffectInstance effect = player.m_21124_(MobEffects.f_19606_);
        int lvl = effect != null ? effect.m_19564_() : -1;
        Rum.amplifyEffect(player, MobEffects.f_19606_, 2, 2.0f, 2400);
        if (lvl >= 0) {
            Rum.amplifyEffect(player, MobEffects.f_19610_, 0, 1.0f, 2400);
            Rum.amplifyEffect(player, IC2Potions.SHAKY, 0, 2.0f, 2400);
        }
        if (lvl >= 1) {
            Rum.amplifyEffect(player, MobEffects.f_19604_, 0, 0.5f, 2400);
        }
        return true;
    }

    @Override
    public List<ItemStack> generateSubStates(ItemStack base, boolean textures) {
        ObjectList list = CollectionUtils.createList();
        ItemStack rum = base.m_41777_();
        rum.m_41784_().m_128344_("progress", (byte)100);
        list.add((ItemStack)rum);
        rum = base.m_41777_();
        rum.m_41784_().m_128344_("progress", (byte)0);
        list.add((ItemStack)rum);
        return list;
    }

    @Override
    public int getTextureIndex(ItemStack stack) {
        return StackUtil.getNbtData(stack).m_128451_("progress") >= 100 ? 0 : 1;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, String baseFolder) {
        return new ResourceLocation(baseFolder + (StackUtil.getNbtData(stack).m_128451_("progress") < 100 ? "/black_stuff" : "/rum"));
    }

    public static ItemStack createData(ItemStack base, int progress) {
        if (!base.m_41619_()) {
            base.m_41784_().m_128344_("progress", (byte)Mth.m_14045_((int)progress, (int)0, (int)100));
        }
        return base;
    }

    public static void amplifyEffect(Player player, MobEffect potion, int max, float intensity, int duration) {
        MobEffectInstance eff = player.m_21124_(potion);
        if (eff == null) {
            player.m_7292_(new MobEffectInstance(potion, duration, 0));
        } else {
            int newDuration = eff.m_19557_();
            player.m_7292_(new MobEffectInstance(potion, newDuration + (int)Math.min(Math.max(0.0f, ((float)duration * (1.0f + intensity * 2.0f) - (float)newDuration) / 2.0f), (float)duration), Math.min(max, eff.m_19564_() + 1)));
        }
    }
}

