/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public interface Bucketable {
    public boolean m_27487_();

    public void m_27497_(boolean var1);

    public void m_6872_(ItemStack var1);

    public void m_142278_(CompoundTag var1);

    public ItemStack m_28282_();

    public SoundEvent m_142623_();

    @Deprecated
    public static void m_148822_(Mob p_148823_, ItemStack p_148824_) {
        CompoundTag $$2 = p_148824_.m_41784_();
        if (p_148823_.m_8077_()) {
            p_148824_.m_41714_(p_148823_.m_7770_());
        }
        if (p_148823_.m_21525_()) {
            $$2.m_128379_("NoAI", p_148823_.m_21525_());
        }
        if (p_148823_.m_20067_()) {
            $$2.m_128379_("Silent", p_148823_.m_20067_());
        }
        if (p_148823_.m_20068_()) {
            $$2.m_128379_("NoGravity", p_148823_.m_20068_());
        }
        if (p_148823_.m_146886_()) {
            $$2.m_128379_("Glowing", p_148823_.m_146886_());
        }
        if (p_148823_.m_20147_()) {
            $$2.m_128379_("Invulnerable", p_148823_.m_20147_());
        }
        $$2.m_128350_("Health", p_148823_.m_21223_());
    }

    @Deprecated
    public static void m_148825_(Mob p_148826_, CompoundTag p_148827_) {
        if (p_148827_.m_128441_("NoAI")) {
            p_148826_.m_21557_(p_148827_.m_128471_("NoAI"));
        }
        if (p_148827_.m_128441_("Silent")) {
            p_148826_.m_20225_(p_148827_.m_128471_("Silent"));
        }
        if (p_148827_.m_128441_("NoGravity")) {
            p_148826_.m_20242_(p_148827_.m_128471_("NoGravity"));
        }
        if (p_148827_.m_128441_("Glowing")) {
            p_148826_.m_146915_(p_148827_.m_128471_("Glowing"));
        }
        if (p_148827_.m_128441_("Invulnerable")) {
            p_148826_.m_20331_(p_148827_.m_128471_("Invulnerable"));
        }
        if (p_148827_.m_128425_("Health", 99)) {
            p_148826_.m_21153_(p_148827_.m_128457_("Health"));
        }
    }

    public static <T extends LivingEntity> Optional<InteractionResult> m_148828_(Player p_148829_, InteractionHand p_148830_, T p_148831_) {
        ItemStack $$3 = p_148829_.m_21120_(p_148830_);
        if ($$3.m_41720_() == Items.f_42447_ && p_148831_.m_6084_()) {
            p_148831_.m_5496_(((Bucketable)((Object)p_148831_)).m_142623_(), 1.0f, 1.0f);
            ItemStack $$4 = ((Bucketable)((Object)p_148831_)).m_28282_();
            ((Bucketable)((Object)p_148831_)).m_6872_($$4);
            ItemStack $$5 = ItemUtils.m_41817_($$3, p_148829_, $$4, false);
            p_148829_.m_21008_(p_148830_, $$5);
            Level $$6 = p_148831_.f_19853_;
            if (!$$6.f_46443_) {
                CriteriaTriggers.f_10576_.m_38772_((ServerPlayer)p_148829_, $$4);
            }
            p_148831_.m_146870_();
            return Optional.of(InteractionResult.m_19078_($$6.f_46443_));
        }
        return Optional.empty();
    }
}

