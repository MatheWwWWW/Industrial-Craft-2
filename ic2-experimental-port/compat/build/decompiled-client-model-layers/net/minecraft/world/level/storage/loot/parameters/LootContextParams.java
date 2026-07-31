/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.parameters;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.phys.Vec3;

public class LootContextParams {
    public static final LootContextParam<Entity> f_81455_ = LootContextParams.m_81466_("this_entity");
    public static final LootContextParam<Player> f_81456_ = LootContextParams.m_81466_("last_damage_player");
    public static final LootContextParam<DamageSource> f_81457_ = LootContextParams.m_81466_("damage_source");
    public static final LootContextParam<Entity> f_81458_ = LootContextParams.m_81466_("killer_entity");
    public static final LootContextParam<Entity> f_81459_ = LootContextParams.m_81466_("direct_killer_entity");
    public static final LootContextParam<Vec3> f_81460_ = LootContextParams.m_81466_("origin");
    public static final LootContextParam<BlockState> f_81461_ = LootContextParams.m_81466_("block_state");
    public static final LootContextParam<BlockEntity> f_81462_ = LootContextParams.m_81466_("block_entity");
    public static final LootContextParam<ItemStack> f_81463_ = LootContextParams.m_81466_("tool");
    public static final LootContextParam<Float> f_81464_ = LootContextParams.m_81466_("explosion_radius");

    private static <T> LootContextParam<T> m_81466_(String p_81467_) {
        return new LootContextParam(new ResourceLocation(p_81467_));
    }
}

