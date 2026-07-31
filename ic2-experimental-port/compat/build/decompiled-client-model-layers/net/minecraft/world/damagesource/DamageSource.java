/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.BadRespawnPointDamage;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

public class DamageSource {
    public static final DamageSource f_19305_ = new DamageSource("inFire").m_19380_().m_19383_();
    public static final DamageSource f_19306_ = new DamageSource("lightningBolt");
    public static final DamageSource f_19307_ = new DamageSource("onFire").m_19380_().m_19383_();
    public static final DamageSource f_19308_ = new DamageSource("lava").m_19383_();
    public static final DamageSource f_19309_ = new DamageSource("hotFloor").m_19383_();
    public static final DamageSource f_19310_ = new DamageSource("inWall").m_19380_();
    public static final DamageSource f_19311_ = new DamageSource("cramming").m_19380_();
    public static final DamageSource f_19312_ = new DamageSource("drown").m_19380_();
    public static final DamageSource f_19313_ = new DamageSource("starve").m_19380_().m_19382_();
    public static final DamageSource f_19314_ = new DamageSource("cactus");
    public static final DamageSource f_19315_ = new DamageSource("fall").m_19380_().m_146708_();
    public static final DamageSource f_19316_ = new DamageSource("flyIntoWall").m_19380_();
    public static final DamageSource f_19317_ = new DamageSource("outOfWorld").m_19380_().m_19381_();
    public static final DamageSource f_19318_ = new DamageSource("generic").m_19380_();
    public static final DamageSource f_19319_ = new DamageSource("magic").m_19380_().m_19389_();
    public static final DamageSource f_19320_ = new DamageSource("wither").m_19380_();
    public static final DamageSource f_19321_ = new DamageSource("anvil").m_146706_();
    public static final DamageSource f_19322_ = new DamageSource("fallingBlock").m_146706_();
    public static final DamageSource f_19323_ = new DamageSource("dragonBreath").m_19380_();
    public static final DamageSource f_19324_ = new DamageSource("dryout");
    public static final DamageSource f_19325_ = new DamageSource("sweetBerryBush");
    public static final DamageSource f_146701_ = new DamageSource("freeze").m_19380_();
    public static final DamageSource f_146702_ = new DamageSource("fallingStalactite").m_146706_();
    public static final DamageSource f_146703_ = new DamageSource("stalagmite").m_19380_().m_146708_();
    private boolean f_146704_;
    private boolean f_19327_;
    private boolean f_19328_;
    private boolean f_19329_;
    private boolean f_238168_;
    private float f_19330_ = 0.1f;
    private boolean f_19300_;
    private boolean f_19301_;
    private boolean f_19302_;
    private boolean f_19303_;
    private boolean f_19304_;
    private boolean f_146700_;
    private boolean f_181119_;
    public final String f_19326_;

    public static DamageSource m_19364_(LivingEntity p_19365_) {
        return new EntityDamageSource("sting", p_19365_);
    }

    public static DamageSource m_19370_(LivingEntity p_19371_) {
        return new EntityDamageSource("mob", p_19371_);
    }

    public static DamageSource m_19340_(Entity p_19341_, @Nullable LivingEntity p_19342_) {
        return new IndirectEntityDamageSource("mob", p_19341_, p_19342_);
    }

    public static DamageSource m_19344_(Player p_19345_) {
        return new EntityDamageSource("player", p_19345_);
    }

    public static DamageSource m_19346_(AbstractArrow p_19347_, @Nullable Entity p_19348_) {
        return new IndirectEntityDamageSource("arrow", p_19347_, p_19348_).m_19366_();
    }

    public static DamageSource m_19337_(Entity p_19338_, @Nullable Entity p_19339_) {
        return new IndirectEntityDamageSource("trident", p_19338_, p_19339_).m_19366_();
    }

    public static DamageSource m_19352_(FireworkRocketEntity p_19353_, @Nullable Entity p_19354_) {
        return new IndirectEntityDamageSource("fireworks", p_19353_, p_19354_).m_19375_();
    }

    public static DamageSource m_19349_(Fireball p_19350_, @Nullable Entity p_19351_) {
        if (p_19351_ == null) {
            return new IndirectEntityDamageSource("onFire", p_19350_, p_19350_).m_19383_().m_19366_();
        }
        return new IndirectEntityDamageSource("fireball", p_19350_, p_19351_).m_19383_().m_19366_();
    }

    public static DamageSource m_19355_(WitherSkull p_19356_, Entity p_19357_) {
        return new IndirectEntityDamageSource("witherSkull", p_19356_, p_19357_).m_19366_();
    }

    public static DamageSource m_19361_(Entity p_19362_, @Nullable Entity p_19363_) {
        return new IndirectEntityDamageSource("thrown", p_19362_, p_19363_).m_19366_();
    }

    public static DamageSource m_19367_(Entity p_19368_, @Nullable Entity p_19369_) {
        return new IndirectEntityDamageSource("indirectMagic", p_19368_, p_19369_).m_19380_().m_19389_();
    }

    public static DamageSource m_19335_(Entity p_19336_) {
        return new EntityDamageSource("thorns", p_19336_).m_19402_().m_19389_();
    }

    public static DamageSource m_19358_(@Nullable Explosion p_19359_) {
        return DamageSource.m_19373_(p_19359_ != null ? p_19359_.m_46079_() : null);
    }

    public static DamageSource m_19373_(@Nullable LivingEntity p_19374_) {
        if (p_19374_ != null) {
            return new EntityDamageSource("explosion.player", p_19374_).m_19386_().m_19375_();
        }
        return new DamageSource("explosion").m_19386_().m_19375_();
    }

    public static DamageSource m_216876_(Entity p_216877_) {
        return new EntityDamageSource("sonic_boom", p_216877_).m_19380_().m_238403_().m_19389_();
    }

    public static DamageSource m_19334_() {
        return new BadRespawnPointDamage();
    }

    public String toString() {
        return "DamageSource (" + this.f_19326_ + ")";
    }

    public boolean m_19360_() {
        return this.f_19301_;
    }

    public DamageSource m_19366_() {
        this.f_19301_ = true;
        return this;
    }

    public boolean m_19372_() {
        return this.f_19304_;
    }

    public DamageSource m_19375_() {
        this.f_19304_ = true;
        return this;
    }

    public boolean m_19376_() {
        return this.f_19327_;
    }

    public boolean m_146705_() {
        return this.f_146704_;
    }

    public float m_19377_() {
        return this.f_19330_;
    }

    public boolean m_19378_() {
        return this.f_19328_;
    }

    public boolean m_19379_() {
        return this.f_19329_;
    }

    public boolean m_238340_() {
        return this.f_238168_;
    }

    protected DamageSource(String p_19333_) {
        this.f_19326_ = p_19333_;
    }

    @Nullable
    public Entity m_7640_() {
        return this.m_7639_();
    }

    @Nullable
    public Entity m_7639_() {
        return null;
    }

    protected DamageSource m_19380_() {
        this.f_19327_ = true;
        this.f_19330_ = 0.0f;
        return this;
    }

    protected DamageSource m_146706_() {
        this.f_146704_ = true;
        return this;
    }

    protected DamageSource m_19381_() {
        this.f_19328_ = true;
        return this;
    }

    protected DamageSource m_19382_() {
        this.f_19329_ = true;
        this.f_19330_ = 0.0f;
        return this;
    }

    protected DamageSource m_238403_() {
        this.f_238168_ = true;
        return this;
    }

    protected DamageSource m_19383_() {
        this.f_19300_ = true;
        return this;
    }

    public DamageSource m_181120_() {
        this.f_181119_ = true;
        return this;
    }

    public Component m_6157_(LivingEntity p_19343_) {
        LivingEntity $$1 = p_19343_.m_21232_();
        String $$2 = "death.attack." + this.f_19326_;
        String $$3 = $$2 + ".player";
        if ($$1 != null) {
            return Component.m_237110_($$3, p_19343_.m_5446_(), $$1.m_5446_());
        }
        return Component.m_237110_($$2, p_19343_.m_5446_());
    }

    public boolean m_19384_() {
        return this.f_19300_;
    }

    public boolean m_181121_() {
        return this.f_181119_;
    }

    public String m_19385_() {
        return this.f_19326_;
    }

    public DamageSource m_19386_() {
        this.f_19302_ = true;
        return this;
    }

    public boolean m_7986_() {
        return this.f_19302_;
    }

    public boolean m_19387_() {
        return this.f_19303_;
    }

    public DamageSource m_19389_() {
        this.f_19303_ = true;
        return this;
    }

    public boolean m_146707_() {
        return this.f_146700_;
    }

    public DamageSource m_146708_() {
        this.f_146700_ = true;
        return this;
    }

    public boolean m_19390_() {
        Entity $$0 = this.m_7639_();
        return $$0 instanceof Player && ((Player)$$0).m_150110_().f_35937_;
    }

    @Nullable
    public Vec3 m_7270_() {
        return null;
    }
}

