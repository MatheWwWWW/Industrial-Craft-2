/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class TheEndGatewayBlockEntity
extends TheEndPortalBlockEntity {
    private static final Logger f_59925_ = LogUtils.getLogger();
    private static final int f_155807_ = 200;
    private static final int f_155808_ = 40;
    private static final int f_155809_ = 2400;
    private static final int f_155810_ = 1;
    private static final int f_155811_ = 10;
    private long f_59926_;
    private int f_59927_;
    @Nullable
    private BlockPos f_59928_;
    private boolean f_59929_;

    public TheEndGatewayBlockEntity(BlockPos p_155813_, BlockState p_155814_) {
        super(BlockEntityType.f_58937_, p_155813_, p_155814_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187527_) {
        super.m_183515_(p_187527_);
        p_187527_.m_128356_("Age", this.f_59926_);
        if (this.f_59928_ != null) {
            p_187527_.m_128365_("ExitPortal", NbtUtils.m_129224_(this.f_59928_));
        }
        if (this.f_59929_) {
            p_187527_.m_128379_("ExactTeleport", true);
        }
    }

    @Override
    public void m_142466_(CompoundTag p_155840_) {
        BlockPos $$1;
        super.m_142466_(p_155840_);
        this.f_59926_ = p_155840_.m_128454_("Age");
        if (p_155840_.m_128425_("ExitPortal", 10) && Level.m_46741_($$1 = NbtUtils.m_129239_(p_155840_.m_128469_("ExitPortal")))) {
            this.f_59928_ = $$1;
        }
        this.f_59929_ = p_155840_.m_128471_("ExactTeleport");
    }

    public static void m_155834_(Level p_155835_, BlockPos p_155836_, BlockState p_155837_, TheEndGatewayBlockEntity p_155838_) {
        ++p_155838_.f_59926_;
        if (p_155838_.m_59972_()) {
            --p_155838_.f_59927_;
        }
    }

    public static void m_155844_(Level p_155845_, BlockPos p_155846_, BlockState p_155847_, TheEndGatewayBlockEntity p_155848_) {
        boolean $$4 = p_155848_.m_59971_();
        boolean $$5 = p_155848_.m_59972_();
        ++p_155848_.f_59926_;
        if ($$5) {
            --p_155848_.f_59927_;
        } else {
            List<Entity> $$6 = p_155845_.m_6443_(Entity.class, new AABB(p_155846_), TheEndGatewayBlockEntity::m_59940_);
            if (!$$6.isEmpty()) {
                TheEndGatewayBlockEntity.m_155828_(p_155845_, p_155846_, p_155847_, $$6.get(p_155845_.f_46441_.m_188503_($$6.size())), p_155848_);
            }
            if (p_155848_.f_59926_ % 2400L == 0L) {
                TheEndGatewayBlockEntity.m_155849_(p_155845_, p_155846_, p_155847_, p_155848_);
            }
        }
        if ($$4 != p_155848_.m_59971_() || $$5 != p_155848_.m_59972_()) {
            TheEndGatewayBlockEntity.m_155232_(p_155845_, p_155846_, p_155847_);
        }
    }

    public static boolean m_59940_(Entity p_59941_) {
        return EntitySelector.f_20408_.test(p_59941_) && !p_59941_.m_20201_().m_20092_();
    }

    public boolean m_59971_() {
        return this.f_59926_ < 200L;
    }

    public boolean m_59972_() {
        return this.f_59927_ > 0;
    }

    public float m_59933_(float p_59934_) {
        return Mth.m_14036_(((float)this.f_59926_ + p_59934_) / 200.0f, 0.0f, 1.0f);
    }

    public float m_59967_(float p_59968_) {
        return 1.0f - Mth.m_14036_(((float)this.f_59927_ - p_59968_) / 40.0f, 0.0f, 1.0f);
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    private static void m_155849_(Level p_155850_, BlockPos p_155851_, BlockState p_155852_, TheEndGatewayBlockEntity p_155853_) {
        if (!p_155850_.f_46443_) {
            p_155853_.f_59927_ = 40;
            p_155850_.m_7696_(p_155851_, p_155852_.m_60734_(), 1, 0);
            TheEndGatewayBlockEntity.m_155232_(p_155850_, p_155851_, p_155852_);
        }
    }

    @Override
    public boolean m_7531_(int p_59963_, int p_59964_) {
        if (p_59963_ == 1) {
            this.f_59927_ = 40;
            return true;
        }
        return super.m_7531_(p_59963_, p_59964_);
    }

    public static void m_155828_(Level p_155829_, BlockPos p_155830_, BlockState p_155831_, Entity p_155832_, TheEndGatewayBlockEntity p_155833_) {
        if (!(p_155829_ instanceof ServerLevel) || p_155833_.m_59972_()) {
            return;
        }
        ServerLevel $$5 = (ServerLevel)p_155829_;
        p_155833_.f_59927_ = 100;
        if (p_155833_.f_59928_ == null && p_155829_.m_46472_() == Level.f_46430_) {
            BlockPos $$6 = TheEndGatewayBlockEntity.m_155818_($$5, p_155830_);
            $$6 = $$6.m_6630_(10);
            f_59925_.debug("Creating portal at {}", (Object)$$6);
            TheEndGatewayBlockEntity.m_155821_($$5, $$6, EndGatewayConfiguration.m_67650_(p_155830_, false));
            p_155833_.f_59928_ = $$6;
        }
        if (p_155833_.f_59928_ != null) {
            Entity $$11;
            BlockPos $$7;
            BlockPos blockPos = $$7 = p_155833_.f_59929_ ? p_155833_.f_59928_ : TheEndGatewayBlockEntity.m_155825_(p_155829_, p_155833_.f_59928_);
            if (p_155832_ instanceof ThrownEnderpearl) {
                Entity $$8 = ((ThrownEnderpearl)p_155832_).m_37282_();
                if ($$8 instanceof ServerPlayer) {
                    CriteriaTriggers.f_10570_.m_31269_((ServerPlayer)$$8, p_155831_);
                }
                if ($$8 != null) {
                    Entity $$9 = $$8;
                    p_155832_.m_146870_();
                } else {
                    Entity $$10 = p_155832_;
                }
            } else {
                $$11 = p_155832_.m_20201_();
            }
            $$11.m_20091_();
            $$11.m_20324_((double)$$7.m_123341_() + 0.5, $$7.m_123342_(), (double)$$7.m_123343_() + 0.5);
        }
        TheEndGatewayBlockEntity.m_155849_(p_155829_, p_155830_, p_155831_, p_155833_);
    }

    private static BlockPos m_155825_(Level p_155826_, BlockPos p_155827_) {
        BlockPos $$2 = TheEndGatewayBlockEntity.m_59942_(p_155826_, p_155827_.m_7918_(0, 2, 0), 5, false);
        f_59925_.debug("Best exit position for portal at {} is {}", (Object)p_155827_, (Object)$$2);
        return $$2.m_7494_();
    }

    private static BlockPos m_155818_(ServerLevel p_155819_, BlockPos p_155820_) {
        Vec3 $$2 = TheEndGatewayBlockEntity.m_155841_(p_155819_, p_155820_);
        LevelChunk $$3 = TheEndGatewayBlockEntity.m_59947_(p_155819_, $$2);
        BlockPos $$4 = TheEndGatewayBlockEntity.m_59953_($$3);
        if ($$4 == null) {
            $$4 = new BlockPos($$2.f_82479_ + 0.5, 75.0, $$2.f_82481_ + 0.5);
            f_59925_.debug("Failed to find a suitable block to teleport to, spawning an island on {}", (Object)$$4);
            EndFeatures.f_194986_.m_203334_().m_224953_(p_155819_, p_155819_.m_7726_().m_8481_(), RandomSource.m_216335_($$4.m_121878_()), $$4);
        } else {
            f_59925_.debug("Found suitable block to teleport to: {}", (Object)$$4);
        }
        $$4 = TheEndGatewayBlockEntity.m_59942_(p_155819_, $$4, 16, true);
        return $$4;
    }

    private static Vec3 m_155841_(ServerLevel p_155842_, BlockPos p_155843_) {
        Vec3 $$2 = new Vec3(p_155843_.m_123341_(), 0.0, p_155843_.m_123343_()).m_82541_();
        int $$3 = 1024;
        Vec3 $$4 = $$2.m_82490_(1024.0);
        int $$5 = 16;
        while (!TheEndGatewayBlockEntity.m_155815_(p_155842_, $$4) && $$5-- > 0) {
            f_59925_.debug("Skipping backwards past nonempty chunk at {}", (Object)$$4);
            $$4 = $$4.m_82549_($$2.m_82490_(-16.0));
        }
        $$5 = 16;
        while (TheEndGatewayBlockEntity.m_155815_(p_155842_, $$4) && $$5-- > 0) {
            f_59925_.debug("Skipping forward past empty chunk at {}", (Object)$$4);
            $$4 = $$4.m_82549_($$2.m_82490_(16.0));
        }
        f_59925_.debug("Found chunk at {}", (Object)$$4);
        return $$4;
    }

    private static boolean m_155815_(ServerLevel p_155816_, Vec3 p_155817_) {
        return TheEndGatewayBlockEntity.m_59947_(p_155816_, p_155817_).m_62098_() <= p_155816_.m_141937_();
    }

    private static BlockPos m_59942_(BlockGetter p_59943_, BlockPos p_59944_, int p_59945_, boolean p_59946_) {
        Vec3i $$4 = null;
        for (int $$5 = -p_59945_; $$5 <= p_59945_; ++$$5) {
            block1: for (int $$6 = -p_59945_; $$6 <= p_59945_; ++$$6) {
                if ($$5 == 0 && $$6 == 0 && !p_59946_) continue;
                for (int $$7 = p_59943_.m_151558_() - 1; $$7 > ($$4 == null ? p_59943_.m_141937_() : $$4.m_123342_()); --$$7) {
                    BlockPos $$8 = new BlockPos(p_59944_.m_123341_() + $$5, $$7, p_59944_.m_123343_() + $$6);
                    BlockState $$9 = p_59943_.m_8055_($$8);
                    if (!$$9.m_60838_(p_59943_, $$8) || !p_59946_ && $$9.m_60713_(Blocks.f_50752_)) continue;
                    $$4 = $$8;
                    continue block1;
                }
            }
        }
        return $$4 == null ? p_59944_ : $$4;
    }

    private static LevelChunk m_59947_(Level p_59948_, Vec3 p_59949_) {
        return p_59948_.m_6325_(Mth.m_14107_(p_59949_.f_82479_ / 16.0), Mth.m_14107_(p_59949_.f_82481_ / 16.0));
    }

    @Nullable
    private static BlockPos m_59953_(LevelChunk p_59954_) {
        ChunkPos $$1 = p_59954_.m_7697_();
        BlockPos $$2 = new BlockPos($$1.m_45604_(), 30, $$1.m_45605_());
        int $$3 = p_59954_.m_62098_() + 16 - 1;
        BlockPos $$4 = new BlockPos($$1.m_45608_(), $$3, $$1.m_45609_());
        BlockPos $$5 = null;
        double $$6 = 0.0;
        for (BlockPos $$7 : BlockPos.m_121940_($$2, $$4)) {
            BlockState $$8 = p_59954_.m_8055_($$7);
            BlockPos $$9 = $$7.m_7494_();
            BlockPos $$10 = $$7.m_6630_(2);
            if (!$$8.m_60713_(Blocks.f_50259_) || p_59954_.m_8055_($$9).m_60838_(p_59954_, $$9) || p_59954_.m_8055_($$10).m_60838_(p_59954_, $$10)) continue;
            double $$11 = $$7.m_203198_(0.0, 0.0, 0.0);
            if ($$5 != null && !($$11 < $$6)) continue;
            $$5 = $$7;
            $$6 = $$11;
        }
        return $$5;
    }

    private static void m_155821_(ServerLevel p_155822_, BlockPos p_155823_, EndGatewayConfiguration p_155824_) {
        Feature.f_65734_.m_225028_(p_155824_, p_155822_, p_155822_.m_7726_().m_8481_(), RandomSource.m_216327_(), p_155823_);
    }

    @Override
    public boolean m_6665_(Direction p_59959_) {
        return Block.m_152444_(this.m_58900_(), this.f_58857_, this.m_58899_(), p_59959_, this.m_58899_().m_121945_(p_59959_));
    }

    public int m_59975_() {
        int $$0 = 0;
        for (Direction $$1 : Direction.values()) {
            $$0 += this.m_6665_($$1) ? 1 : 0;
        }
        return $$0;
    }

    public void m_59955_(BlockPos p_59956_, boolean p_59957_) {
        this.f_59929_ = p_59957_;
        this.f_59928_ = p_59956_;
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }
}

