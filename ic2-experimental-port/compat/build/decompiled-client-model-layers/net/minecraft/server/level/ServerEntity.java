/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class ServerEntity {
    private static final Logger f_8508_ = LogUtils.getLogger();
    private static final int f_143241_ = 1;
    private final ServerLevel f_8509_;
    private final Entity f_8510_;
    private final int f_8511_;
    private final boolean f_8512_;
    private final Consumer<Packet<?>> f_8513_;
    private final VecDeltaCodec f_214995_ = new VecDeltaCodec();
    private int f_8517_;
    private int f_8518_;
    private int f_8519_;
    private Vec3 f_8520_ = Vec3.f_82478_;
    private int f_8521_;
    private int f_8522_;
    private List<Entity> f_8523_ = Collections.emptyList();
    private boolean f_8524_;
    private boolean f_8525_;

    public ServerEntity(ServerLevel p_8528_, Entity p_8529_, int p_8530_, boolean p_8531_, Consumer<Packet<?>> p_8532_) {
        this.f_8509_ = p_8528_;
        this.f_8513_ = p_8532_;
        this.f_8510_ = p_8529_;
        this.f_8511_ = p_8530_;
        this.f_8512_ = p_8531_;
        this.f_214995_.m_238033_(p_8529_.m_213870_());
        this.f_8517_ = Mth.m_14143_(p_8529_.m_146908_() * 256.0f / 360.0f);
        this.f_8518_ = Mth.m_14143_(p_8529_.m_146909_() * 256.0f / 360.0f);
        this.f_8519_ = Mth.m_14143_(p_8529_.m_6080_() * 256.0f / 360.0f);
        this.f_8525_ = p_8529_.m_20096_();
    }

    public void m_8533_() {
        Entity entity;
        List<Entity> $$0 = this.f_8510_.m_20197_();
        if (!$$0.equals(this.f_8523_)) {
            this.f_8523_ = $$0;
            this.f_8513_.accept(new ClientboundSetPassengersPacket(this.f_8510_));
        }
        if ((entity = this.f_8510_) instanceof ItemFrame) {
            ItemFrame $$1 = (ItemFrame)entity;
            if (this.f_8521_ % 10 == 0) {
                Integer $$3;
                MapItemSavedData $$4;
                ItemStack $$2 = $$1.m_31822_();
                if ($$2.m_41720_() instanceof MapItem && ($$4 = MapItem.m_151128_($$3 = MapItem.m_151131_($$2), this.f_8509_)) != null) {
                    for (ServerPlayer $$5 : this.f_8509_.m_6907_()) {
                        $$4.m_77918_($$5, $$2);
                        Packet<?> $$6 = $$4.m_164796_($$3, $$5);
                        if ($$6 == null) continue;
                        $$5.f_8906_.m_9829_($$6);
                    }
                }
                this.m_8543_();
            }
        }
        if (this.f_8521_ % this.f_8511_ == 0 || this.f_8510_.f_19812_ || this.f_8510_.m_20088_().m_135352_()) {
            if (this.f_8510_.m_20159_()) {
                boolean $$9;
                int $$7 = Mth.m_14143_(this.f_8510_.m_146908_() * 256.0f / 360.0f);
                int $$8 = Mth.m_14143_(this.f_8510_.m_146909_() * 256.0f / 360.0f);
                boolean bl = $$9 = Math.abs($$7 - this.f_8517_) >= 1 || Math.abs($$8 - this.f_8518_) >= 1;
                if ($$9) {
                    this.f_8513_.accept(new ClientboundMoveEntityPacket.Rot(this.f_8510_.m_19879_(), (byte)$$7, (byte)$$8, this.f_8510_.m_20096_()));
                    this.f_8517_ = $$7;
                    this.f_8518_ = $$8;
                }
                this.f_214995_.m_238033_(this.f_8510_.m_213870_());
                this.m_8543_();
                this.f_8524_ = true;
            } else {
                Vec3 $$21;
                double $$22;
                boolean $$16;
                ++this.f_8522_;
                int $$10 = Mth.m_14143_(this.f_8510_.m_146908_() * 256.0f / 360.0f);
                int $$11 = Mth.m_14143_(this.f_8510_.m_146909_() * 256.0f / 360.0f);
                Vec3 $$12 = this.f_8510_.m_213870_();
                boolean $$13 = this.f_214995_.m_238031_($$12).m_82556_() >= 7.62939453125E-6;
                Packet<ClientGamePacketListener> $$14 = null;
                boolean $$15 = $$13 || this.f_8521_ % 60 == 0;
                boolean bl = $$16 = Math.abs($$10 - this.f_8517_) >= 1 || Math.abs($$11 - this.f_8518_) >= 1;
                if (this.f_8521_ > 0 || this.f_8510_ instanceof AbstractArrow) {
                    boolean $$20;
                    long $$17 = this.f_214995_.m_238025_($$12);
                    long $$18 = this.f_214995_.m_238027_($$12);
                    long $$19 = this.f_214995_.m_238029_($$12);
                    boolean bl2 = $$20 = $$17 < -32768L || $$17 > 32767L || $$18 < -32768L || $$18 > 32767L || $$19 < -32768L || $$19 > 32767L;
                    if ($$20 || this.f_8522_ > 400 || this.f_8524_ || this.f_8525_ != this.f_8510_.m_20096_()) {
                        this.f_8525_ = this.f_8510_.m_20096_();
                        this.f_8522_ = 0;
                        $$14 = new ClientboundTeleportEntityPacket(this.f_8510_);
                    } else if ($$15 && $$16 || this.f_8510_ instanceof AbstractArrow) {
                        $$14 = new ClientboundMoveEntityPacket.PosRot(this.f_8510_.m_19879_(), (short)$$17, (short)$$18, (short)$$19, (byte)$$10, (byte)$$11, this.f_8510_.m_20096_());
                    } else if ($$15) {
                        $$14 = new ClientboundMoveEntityPacket.Pos(this.f_8510_.m_19879_(), (short)$$17, (short)$$18, (short)$$19, this.f_8510_.m_20096_());
                    } else if ($$16) {
                        $$14 = new ClientboundMoveEntityPacket.Rot(this.f_8510_.m_19879_(), (byte)$$10, (byte)$$11, this.f_8510_.m_20096_());
                    }
                }
                if ((this.f_8512_ || this.f_8510_.f_19812_ || this.f_8510_ instanceof LivingEntity && ((LivingEntity)this.f_8510_).m_21255_()) && this.f_8521_ > 0 && (($$22 = ($$21 = this.f_8510_.m_20184_()).m_82557_(this.f_8520_)) > 1.0E-7 || $$22 > 0.0 && $$21.m_82556_() == 0.0)) {
                    this.f_8520_ = $$21;
                    this.f_8513_.accept(new ClientboundSetEntityMotionPacket(this.f_8510_.m_19879_(), this.f_8520_));
                }
                if ($$14 != null) {
                    this.f_8513_.accept($$14);
                }
                this.m_8543_();
                if ($$15) {
                    this.f_214995_.m_238033_($$12);
                }
                if ($$16) {
                    this.f_8517_ = $$10;
                    this.f_8518_ = $$11;
                }
                this.f_8524_ = false;
            }
            int $$23 = Mth.m_14143_(this.f_8510_.m_6080_() * 256.0f / 360.0f);
            if (Math.abs($$23 - this.f_8519_) >= 1) {
                this.f_8513_.accept(new ClientboundRotateHeadPacket(this.f_8510_, (byte)$$23));
                this.f_8519_ = $$23;
            }
            this.f_8510_.f_19812_ = false;
        }
        ++this.f_8521_;
        if (this.f_8510_.f_19864_) {
            this.m_8538_(new ClientboundSetEntityMotionPacket(this.f_8510_));
            this.f_8510_.f_19864_ = false;
        }
    }

    public void m_8534_(ServerPlayer p_8535_) {
        this.f_8510_.m_6452_(p_8535_);
        p_8535_.f_8906_.m_9829_(new ClientboundRemoveEntitiesPacket(this.f_8510_.m_19879_()));
    }

    public void m_8541_(ServerPlayer p_8542_) {
        this.m_8536_(p_8542_.f_8906_::m_9829_);
        this.f_8510_.m_6457_(p_8542_);
    }

    public void m_8536_(Consumer<Packet<?>> p_8537_) {
        Mob $$9;
        if (this.f_8510_.m_213877_()) {
            f_8508_.warn("Fetching packet for removed entity {}", (Object)this.f_8510_);
        }
        Packet<?> $$1 = this.f_8510_.m_5654_();
        this.f_8519_ = Mth.m_14143_(this.f_8510_.m_6080_() * 256.0f / 360.0f);
        p_8537_.accept($$1);
        if (!this.f_8510_.m_20088_().m_135388_()) {
            p_8537_.accept(new ClientboundSetEntityDataPacket(this.f_8510_.m_19879_(), this.f_8510_.m_20088_(), true));
        }
        boolean $$2 = this.f_8512_;
        if (this.f_8510_ instanceof LivingEntity) {
            Collection<AttributeInstance> $$3 = ((LivingEntity)this.f_8510_).m_21204_().m_22170_();
            if (!$$3.isEmpty()) {
                p_8537_.accept(new ClientboundUpdateAttributesPacket(this.f_8510_.m_19879_(), $$3));
            }
            if (((LivingEntity)this.f_8510_).m_21255_()) {
                $$2 = true;
            }
        }
        this.f_8520_ = this.f_8510_.m_20184_();
        if ($$2 && !(this.f_8510_ instanceof LivingEntity)) {
            p_8537_.accept(new ClientboundSetEntityMotionPacket(this.f_8510_.m_19879_(), this.f_8520_));
        }
        if (this.f_8510_ instanceof LivingEntity) {
            ArrayList $$4 = Lists.newArrayList();
            for (EquipmentSlot $$5 : EquipmentSlot.values()) {
                ItemStack $$6 = ((LivingEntity)this.f_8510_).m_6844_($$5);
                if ($$6.m_41619_()) continue;
                $$4.add(Pair.of((Object)((Object)$$5), (Object)$$6.m_41777_()));
            }
            if (!$$4.isEmpty()) {
                p_8537_.accept(new ClientboundSetEquipmentPacket(this.f_8510_.m_19879_(), $$4));
            }
        }
        if (this.f_8510_ instanceof LivingEntity) {
            LivingEntity $$7 = (LivingEntity)this.f_8510_;
            for (MobEffectInstance $$8 : $$7.m_21220_()) {
                p_8537_.accept(new ClientboundUpdateMobEffectPacket(this.f_8510_.m_19879_(), $$8));
            }
        }
        if (!this.f_8510_.m_20197_().isEmpty()) {
            p_8537_.accept(new ClientboundSetPassengersPacket(this.f_8510_));
        }
        if (this.f_8510_.m_20159_()) {
            p_8537_.accept(new ClientboundSetPassengersPacket(this.f_8510_.m_20202_()));
        }
        if (this.f_8510_ instanceof Mob && ($$9 = (Mob)this.f_8510_).m_21523_()) {
            p_8537_.accept(new ClientboundSetEntityLinkPacket($$9, $$9.m_21524_()));
        }
    }

    private void m_8543_() {
        SynchedEntityData $$0 = this.f_8510_.m_20088_();
        if ($$0.m_135352_()) {
            this.m_8538_(new ClientboundSetEntityDataPacket(this.f_8510_.m_19879_(), $$0, false));
        }
        if (this.f_8510_ instanceof LivingEntity) {
            Set<AttributeInstance> $$1 = ((LivingEntity)this.f_8510_).m_21204_().m_22145_();
            if (!$$1.isEmpty()) {
                this.m_8538_(new ClientboundUpdateAttributesPacket(this.f_8510_.m_19879_(), $$1));
            }
            $$1.clear();
        }
    }

    private void m_8538_(Packet<?> p_8539_) {
        this.f_8513_.accept(p_8539_);
        if (this.f_8510_ instanceof ServerPlayer) {
            ((ServerPlayer)this.f_8510_).f_8906_.m_9829_(p_8539_);
        }
    }
}

