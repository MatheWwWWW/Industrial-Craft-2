/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.decoration;

import java.util.ArrayList;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Painting
extends HangingEntity {
    private static final EntityDataAccessor<Holder<PaintingVariant>> f_218870_ = SynchedEntityData.m_135353_(Painting.class, EntityDataSerializers.f_238116_);
    private static final ResourceKey<PaintingVariant> f_218871_ = PaintingVariants.f_218914_;

    private static Holder<PaintingVariant> m_218902_() {
        return Registry.f_235728_.m_206081_(f_218871_);
    }

    public Painting(EntityType<? extends Painting> p_31904_, Level p_31905_) {
        super((EntityType<? extends HangingEntity>)p_31904_, p_31905_);
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_218870_, Painting.m_218902_());
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_218896_) {
        if (f_218870_.equals(p_218896_)) {
            this.m_7087_();
        }
    }

    private void m_218891_(Holder<PaintingVariant> p_218892_) {
        this.f_19804_.m_135381_(f_218870_, p_218892_);
    }

    public Holder<PaintingVariant> m_218901_() {
        return this.f_19804_.m_135370_(f_218870_);
    }

    public static Optional<Painting> m_218887_(Level p_218888_, BlockPos p_218889_, Direction p_218890_) {
        Painting $$3 = new Painting(p_218888_, p_218889_);
        ArrayList<Holder> $$4 = new ArrayList<Holder>();
        Registry.f_235728_.m_206058_(PaintingVariantTags.f_215870_).forEach($$4::add);
        if ($$4.isEmpty()) {
            return Optional.empty();
        }
        $$3.m_6022_(p_218890_);
        $$4.removeIf(p_218886_ -> {
            $$3.m_218891_((Holder<PaintingVariant>)p_218886_);
            return !$$3.m_7088_();
        });
        if ($$4.isEmpty()) {
            return Optional.empty();
        }
        int $$5 = $$4.stream().mapToInt(Painting::m_218898_).max().orElse(0);
        $$4.removeIf(p_218883_ -> Painting.m_218898_(p_218883_) < $$5);
        Optional $$6 = Util.m_214676_($$4, $$3.f_19796_);
        if ($$6.isEmpty()) {
            return Optional.empty();
        }
        $$3.m_218891_((Holder)$$6.get());
        $$3.m_6022_(p_218890_);
        return Optional.of($$3);
    }

    private static int m_218898_(Holder<PaintingVariant> p_218899_) {
        return p_218899_.m_203334_().m_218908_() * p_218899_.m_203334_().m_218909_();
    }

    private Painting(Level p_218874_, BlockPos p_218875_) {
        super(EntityType.f_20506_, p_218874_, p_218875_);
    }

    public Painting(Level p_218877_, BlockPos p_218878_, Direction p_218879_, Holder<PaintingVariant> p_218880_) {
        this(p_218877_, p_218878_);
        this.m_218891_(p_218880_);
        this.m_6022_(p_218879_);
    }

    @Override
    public void m_7380_(CompoundTag p_31935_) {
        p_31935_.m_128359_("variant", this.m_218901_().m_203543_().orElse(f_218871_).m_135782_().toString());
        p_31935_.m_128344_("facing", (byte)this.f_31699_.m_122416_());
        super.m_7380_(p_31935_);
    }

    @Override
    public void m_7378_(CompoundTag p_31927_) {
        ResourceKey<PaintingVariant> $$1 = ResourceKey.m_135785_(Registry.f_235743_, ResourceLocation.m_135820_(p_31927_.m_128461_("variant")));
        this.m_218891_(Registry.f_235728_.m_203636_($$1).orElseGet(Painting::m_218902_));
        this.f_31699_ = Direction.m_122407_(p_31927_.m_128445_("facing"));
        super.m_7378_(p_31927_);
        this.m_6022_(this.f_31699_);
    }

    @Override
    public int m_7076_() {
        return this.m_218901_().m_203334_().m_218908_();
    }

    @Override
    public int m_7068_() {
        return this.m_218901_().m_203334_().m_218909_();
    }

    @Override
    public void m_5553_(@Nullable Entity p_31925_) {
        if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46137_)) {
            return;
        }
        this.m_5496_(SoundEvents.f_12175_, 1.0f, 1.0f);
        if (p_31925_ instanceof Player) {
            Player $$1 = (Player)p_31925_;
            if ($$1.m_150110_().f_35937_) {
                return;
            }
        }
        this.m_19998_(Items.f_42487_);
    }

    @Override
    public void m_7084_() {
        this.m_5496_(SoundEvents.f_12176_, 1.0f, 1.0f);
    }

    @Override
    public void m_7678_(double p_31929_, double p_31930_, double p_31931_, float p_31932_, float p_31933_) {
        this.m_6034_(p_31929_, p_31930_, p_31931_);
    }

    @Override
    public void m_6453_(double p_31917_, double p_31918_, double p_31919_, float p_31920_, float p_31921_, int p_31922_, boolean p_31923_) {
        this.m_6034_(p_31917_, p_31918_, p_31919_);
    }

    @Override
    public Vec3 m_213870_() {
        return Vec3.m_82528_(this.f_31698_);
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this, this.f_31699_.m_122411_(), this.m_31748_());
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_218894_) {
        super.m_141965_(p_218894_);
        this.m_6022_(Direction.m_122376_(p_218894_.m_131509_()));
    }

    @Override
    public ItemStack m_142340_() {
        return new ItemStack(Items.f_42487_);
    }
}

