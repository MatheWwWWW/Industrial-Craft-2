/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class ArmorItem
extends Item
implements Wearable {
    private static final UUID[] f_40380_ = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public static final DispenseItemBehavior f_40376_ = new DefaultDispenseItemBehavior(){

        @Override
        protected ItemStack m_7498_(BlockSource p_40408_, ItemStack p_40409_) {
            return ArmorItem.m_40398_(p_40408_, p_40409_) ? p_40409_ : super.m_7498_(p_40408_, p_40409_);
        }
    };
    protected final EquipmentSlot f_40377_;
    private final int f_40381_;
    private final float f_40382_;
    protected final float f_40378_;
    protected final ArmorMaterial f_40379_;
    private final Multimap<Attribute, AttributeModifier> f_40383_;

    public static boolean m_40398_(BlockSource p_40399_, ItemStack p_40400_) {
        BlockPos $$2 = p_40399_.m_7961_().m_121945_(p_40399_.m_6414_().m_61143_(DispenserBlock.f_52659_));
        List<Entity> $$3 = p_40399_.m_7727_().m_6443_(LivingEntity.class, new AABB($$2), EntitySelector.f_20408_.and(new EntitySelector.MobCanWearArmorEntitySelector(p_40400_)));
        if ($$3.isEmpty()) {
            return false;
        }
        LivingEntity $$4 = (LivingEntity)$$3.get(0);
        EquipmentSlot $$5 = Mob.m_147233_(p_40400_);
        ItemStack $$6 = p_40400_.m_41620_(1);
        $$4.m_8061_($$5, $$6);
        if ($$4 instanceof Mob) {
            ((Mob)$$4).m_21409_($$5, 2.0f);
            ((Mob)$$4).m_21530_();
        }
        return true;
    }

    public ArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Item.Properties p_40388_) {
        super(p_40388_.m_41499_(p_40386_.m_7366_(p_40387_)));
        this.f_40379_ = p_40386_;
        this.f_40377_ = p_40387_;
        this.f_40381_ = p_40386_.m_7365_(p_40387_);
        this.f_40382_ = p_40386_.m_6651_();
        this.f_40378_ = p_40386_.m_6649_();
        DispenserBlock.m_52672_(this, f_40376_);
        ImmutableMultimap.Builder $$3 = ImmutableMultimap.builder();
        UUID $$4 = f_40380_[p_40387_.m_20749_()];
        $$3.put((Object)Attributes.f_22284_, (Object)new AttributeModifier($$4, "Armor modifier", (double)this.f_40381_, AttributeModifier.Operation.ADDITION));
        $$3.put((Object)Attributes.f_22285_, (Object)new AttributeModifier($$4, "Armor toughness", (double)this.f_40382_, AttributeModifier.Operation.ADDITION));
        if (p_40386_ == ArmorMaterials.NETHERITE) {
            $$3.put((Object)Attributes.f_22278_, (Object)new AttributeModifier($$4, "Armor knockback resistance", (double)this.f_40378_, AttributeModifier.Operation.ADDITION));
        }
        this.f_40383_ = $$3.build();
    }

    public EquipmentSlot m_40402_() {
        return this.f_40377_;
    }

    @Override
    public int m_6473_() {
        return this.f_40379_.m_6646_();
    }

    public ArmorMaterial m_40401_() {
        return this.f_40379_;
    }

    @Override
    public boolean m_6832_(ItemStack p_40392_, ItemStack p_40393_) {
        return this.f_40379_.m_6230_().test(p_40393_) || super.m_6832_(p_40392_, p_40393_);
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_40395_, Player p_40396_, InteractionHand p_40397_) {
        ItemStack $$3 = p_40396_.m_21120_(p_40397_);
        EquipmentSlot $$4 = Mob.m_147233_($$3);
        ItemStack $$5 = p_40396_.m_6844_($$4);
        if ($$5.m_41619_()) {
            p_40396_.m_8061_($$4, $$3.m_41777_());
            if (!p_40395_.m_5776_()) {
                p_40396_.m_36246_(Stats.f_12982_.m_12902_(this));
            }
            $$3.m_41764_(0);
            return InteractionResultHolder.m_19092_($$3, p_40395_.m_5776_());
        }
        return InteractionResultHolder.m_19100_($$3);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot p_40390_) {
        if (p_40390_ == this.f_40377_) {
            return this.f_40383_;
        }
        return super.m_7167_(p_40390_);
    }

    public int m_40404_() {
        return this.f_40381_;
    }

    public float m_40405_() {
        return this.f_40382_;
    }

    @Override
    @Nullable
    public SoundEvent m_142602_() {
        return this.m_40401_().m_7344_();
    }
}

