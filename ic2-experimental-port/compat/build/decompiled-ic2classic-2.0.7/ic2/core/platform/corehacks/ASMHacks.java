/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 */
package ic2.core.platform.corehacks;

import ic2.api.items.armor.ICustomArmor;
import ic2.core.item.base.IC2Item;
import ic2.core.item.wearable.base.IC2ArmorBase;
import ic2.core.platform.events.InternalEventHandler;
import ic2.core.platform.rendering.events.TilesRenderedEvent;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public final class ASMHacks {
    public static float applyArmor(Player e, DamageSource source, float damage) {
        return IC2ArmorBase.applyArmor((LivingEntity)e, source, damage);
    }

    public static void thornsSelfArmorDamage(Map.Entry<EquipmentSlot, ItemStack> entry, int amount, LivingEntity entity) {
        if (entry.getKey() == null || !(entry.getValue().m_41720_() instanceof ICustomArmor)) {
            entry.getValue().m_41622_(2, entity, IC2Item.get(entry.getKey()));
            return;
        }
        ICustomArmor armor = (ICustomArmor)entry.getValue().m_41720_();
        armor.damageArmor(entity, entry.getValue(), DamageSource.m_19335_((Entity)entity), amount, entry.getKey(), ICustomArmor.DamageType.THORNS_SELF);
    }

    public static BlockState applyCamouflage(BlockState ownerState, BlockGetter world, BlockPos position, Direction side, BlockPos offsetPos, BlockState neighborState) {
        return InternalEventHandler.applyCamouflage(ownerState, world, position, side, offsetPos, neighborState);
    }

    public static List<MobEffectInstance> getFixedPotionEffectsFromStack(ItemStack itemIn, float durationFactor) {
        ObjectList results = CollectionUtils.createList();
        CompoundTag nbt = StackUtil.getNbtData(itemIn);
        results.addAll(PotionUtils.m_43577_((CompoundTag)nbt).m_43488_());
        ListTag list = nbt.m_128437_("CustomPotionEffects", 10);
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            MobEffectInstance instance;
            CompoundTag data = list.m_128728_(i);
            if (data.m_128441_("Duration")) {
                data = data.m_6426_();
                data.m_128405_("Duration", (int)((float)data.m_128451_("Duration") / durationFactor));
            }
            if ((instance = MobEffectInstance.m_19560_((CompoundTag)data)) == null) continue;
            results.add((MobEffectInstance)instance);
        }
        return results;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static void onTileRenderFinished(MultiBufferSource.BufferSource renderTypeBuffer, boolean translucencyMode) {
        MinecraftForge.EVENT_BUS.post((Event)new TilesRenderedEvent(arg_0 -> ((MultiBufferSource.BufferSource)renderTypeBuffer).m_109912_(arg_0), translucencyMode));
    }

    public static float getStepHeight(Player player) {
        return player.m_6144_() ? 0.6f : player.getStepHeight();
    }
}

