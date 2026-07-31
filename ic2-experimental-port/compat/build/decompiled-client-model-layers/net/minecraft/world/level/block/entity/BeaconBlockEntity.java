/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public class BeaconBlockEntity
extends BlockEntity
implements MenuProvider {
    private static final int f_155085_ = 4;
    public static final MobEffect[][] f_58646_ = new MobEffect[][]{{MobEffects.f_19596_, MobEffects.f_19598_}, {MobEffects.f_19606_, MobEffects.f_19603_}, {MobEffects.f_19600_}, {MobEffects.f_19605_}};
    private static final Set<MobEffect> f_58647_ = Arrays.stream(f_58646_).flatMap(Arrays::stream).collect(Collectors.toSet());
    public static final int f_155081_ = 0;
    public static final int f_155082_ = 1;
    public static final int f_155083_ = 2;
    public static final int f_155084_ = 3;
    private static final int f_155086_ = 10;
    List<BeaconBeamSection> f_58648_ = Lists.newArrayList();
    private List<BeaconBeamSection> f_58649_ = Lists.newArrayList();
    int f_58650_;
    private int f_58651_;
    @Nullable
    MobEffect f_58652_;
    @Nullable
    MobEffect f_58653_;
    @Nullable
    private Component f_58654_;
    private LockCode f_58655_ = LockCode.f_19102_;
    private final ContainerData f_58656_ = new ContainerData(){

        @Override
        public int m_6413_(int p_58711_) {
            return switch (p_58711_) {
                case 0 -> BeaconBlockEntity.this.f_58650_;
                case 1 -> MobEffect.m_216882_(BeaconBlockEntity.this.f_58652_);
                case 2 -> MobEffect.m_216882_(BeaconBlockEntity.this.f_58653_);
                default -> 0;
            };
        }

        @Override
        public void m_8050_(int p_58713_, int p_58714_) {
            switch (p_58713_) {
                case 0: {
                    BeaconBlockEntity.this.f_58650_ = p_58714_;
                    break;
                }
                case 1: {
                    if (!BeaconBlockEntity.this.f_58857_.f_46443_ && !BeaconBlockEntity.this.f_58648_.isEmpty()) {
                        BeaconBlockEntity.m_155103_(BeaconBlockEntity.this.f_58857_, BeaconBlockEntity.this.f_58858_, SoundEvents.f_11739_);
                    }
                    BeaconBlockEntity.this.f_58652_ = BeaconBlockEntity.m_58686_(p_58714_);
                    break;
                }
                case 2: {
                    BeaconBlockEntity.this.f_58653_ = BeaconBlockEntity.m_58686_(p_58714_);
                }
            }
        }

        @Override
        public int m_6499_() {
            return 3;
        }
    };

    public BeaconBlockEntity(BlockPos p_155088_, BlockState p_155089_) {
        super(BlockEntityType.f_58930_, p_155088_, p_155089_);
    }

    public static void m_155107_(Level p_155108_, BlockPos p_155109_, BlockState p_155110_, BeaconBlockEntity p_155111_) {
        BlockPos $$8;
        int $$4 = p_155109_.m_123341_();
        int $$5 = p_155109_.m_123342_();
        int $$6 = p_155109_.m_123343_();
        if (p_155111_.f_58651_ < $$5) {
            BlockPos $$7 = p_155109_;
            p_155111_.f_58649_ = Lists.newArrayList();
            p_155111_.f_58651_ = $$7.m_123342_() - 1;
        } else {
            $$8 = new BlockPos($$4, p_155111_.f_58651_ + 1, $$6);
        }
        BeaconBeamSection $$9 = p_155111_.f_58649_.isEmpty() ? null : p_155111_.f_58649_.get(p_155111_.f_58649_.size() - 1);
        int $$10 = p_155108_.m_6924_(Heightmap.Types.WORLD_SURFACE, $$4, $$6);
        for (int $$11 = 0; $$11 < 10 && $$8.m_123342_() <= $$10; ++$$11) {
            block18: {
                BlockState $$12;
                block16: {
                    float[] $$14;
                    block17: {
                        $$12 = p_155108_.m_8055_($$8);
                        Block $$13 = $$12.m_60734_();
                        if (!($$13 instanceof BeaconBeamBlock)) break block16;
                        $$14 = ((BeaconBeamBlock)((Object)$$13)).m_7988_().m_41068_();
                        if (p_155111_.f_58649_.size() > 1) break block17;
                        $$9 = new BeaconBeamSection($$14);
                        p_155111_.f_58649_.add($$9);
                        break block18;
                    }
                    if ($$9 == null) break block18;
                    if (Arrays.equals($$14, $$9.f_58715_)) {
                        $$9.m_58719_();
                    } else {
                        $$9 = new BeaconBeamSection(new float[]{($$9.f_58715_[0] + $$14[0]) / 2.0f, ($$9.f_58715_[1] + $$14[1]) / 2.0f, ($$9.f_58715_[2] + $$14[2]) / 2.0f});
                        p_155111_.f_58649_.add($$9);
                    }
                    break block18;
                }
                if ($$9 != null && ($$12.m_60739_(p_155108_, $$8) < 15 || $$12.m_60713_(Blocks.f_50752_))) {
                    $$9.m_58719_();
                } else {
                    p_155111_.f_58649_.clear();
                    p_155111_.f_58651_ = $$10;
                    break;
                }
            }
            $$8 = $$8.m_7494_();
            ++p_155111_.f_58651_;
        }
        int $$15 = p_155111_.f_58650_;
        if (p_155108_.m_46467_() % 80L == 0L) {
            if (!p_155111_.f_58648_.isEmpty()) {
                p_155111_.f_58650_ = BeaconBlockEntity.m_155092_(p_155108_, $$4, $$5, $$6);
            }
            if (p_155111_.f_58650_ > 0 && !p_155111_.f_58648_.isEmpty()) {
                BeaconBlockEntity.m_155097_(p_155108_, p_155109_, p_155111_.f_58650_, p_155111_.f_58652_, p_155111_.f_58653_);
                BeaconBlockEntity.m_155103_(p_155108_, p_155109_, SoundEvents.f_11737_);
            }
        }
        if (p_155111_.f_58651_ >= $$10) {
            p_155111_.f_58651_ = p_155108_.m_141937_() - 1;
            boolean $$16 = $$15 > 0;
            p_155111_.f_58648_ = p_155111_.f_58649_;
            if (!p_155108_.f_46443_) {
                boolean $$17;
                boolean bl = $$17 = p_155111_.f_58650_ > 0;
                if (!$$16 && $$17) {
                    BeaconBlockEntity.m_155103_(p_155108_, p_155109_, SoundEvents.f_11736_);
                    for (ServerPlayer $$18 : p_155108_.m_45976_(ServerPlayer.class, new AABB($$4, $$5, $$6, $$4, $$5 - 4, $$6).m_82377_(10.0, 5.0, 10.0))) {
                        CriteriaTriggers.f_10578_.m_148029_($$18, p_155111_.f_58650_);
                    }
                } else if ($$16 && !$$17) {
                    BeaconBlockEntity.m_155103_(p_155108_, p_155109_, SoundEvents.f_11738_);
                }
            }
        }
    }

    private static int m_155092_(Level p_155093_, int p_155094_, int p_155095_, int p_155096_) {
        int $$6;
        int $$4 = 0;
        int $$5 = 1;
        while ($$5 <= 4 && ($$6 = p_155095_ - $$5) >= p_155093_.m_141937_()) {
            boolean $$7 = true;
            block1: for (int $$8 = p_155094_ - $$5; $$8 <= p_155094_ + $$5 && $$7; ++$$8) {
                for (int $$9 = p_155096_ - $$5; $$9 <= p_155096_ + $$5; ++$$9) {
                    if (p_155093_.m_8055_(new BlockPos($$8, $$6, $$9)).m_204336_(BlockTags.f_13079_)) continue;
                    $$7 = false;
                    continue block1;
                }
            }
            if (!$$7) break;
            $$4 = $$5++;
        }
        return $$4;
    }

    @Override
    public void m_7651_() {
        BeaconBlockEntity.m_155103_(this.f_58857_, this.f_58858_, SoundEvents.f_11738_);
        super.m_7651_();
    }

    private static void m_155097_(Level p_155098_, BlockPos p_155099_, int p_155100_, @Nullable MobEffect p_155101_, @Nullable MobEffect p_155102_) {
        if (p_155098_.f_46443_ || p_155101_ == null) {
            return;
        }
        double $$5 = p_155100_ * 10 + 10;
        int $$6 = 0;
        if (p_155100_ >= 4 && p_155101_ == p_155102_) {
            $$6 = 1;
        }
        int $$7 = (9 + p_155100_ * 2) * 20;
        AABB $$8 = new AABB(p_155099_).m_82400_($$5).m_82363_(0.0, p_155098_.m_141928_(), 0.0);
        List<Player> $$9 = p_155098_.m_45976_(Player.class, $$8);
        for (Player $$10 : $$9) {
            $$10.m_7292_(new MobEffectInstance(p_155101_, $$7, $$6, true, true));
        }
        if (p_155100_ >= 4 && p_155101_ != p_155102_ && p_155102_ != null) {
            for (Player $$11 : $$9) {
                $$11.m_7292_(new MobEffectInstance(p_155102_, $$7, 0, true, true));
            }
        }
    }

    public static void m_155103_(Level p_155104_, BlockPos p_155105_, SoundEvent p_155106_) {
        p_155104_.m_5594_(null, p_155105_, p_155106_, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    public List<BeaconBeamSection> m_58702_() {
        return this.f_58650_ == 0 ? ImmutableList.of() : this.f_58648_;
    }

    public ClientboundBlockEntityDataPacket m_58483_() {
        return ClientboundBlockEntityDataPacket.m_195640_(this);
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    @Nullable
    static MobEffect m_58686_(int p_58687_) {
        MobEffect $$1 = MobEffect.m_19453_(p_58687_);
        return f_58647_.contains($$1) ? $$1 : null;
    }

    @Override
    public void m_142466_(CompoundTag p_155113_) {
        super.m_142466_(p_155113_);
        this.f_58652_ = BeaconBlockEntity.m_58686_(p_155113_.m_128451_("Primary"));
        this.f_58653_ = BeaconBlockEntity.m_58686_(p_155113_.m_128451_("Secondary"));
        if (p_155113_.m_128425_("CustomName", 8)) {
            this.f_58654_ = Component.Serializer.m_130701_(p_155113_.m_128461_("CustomName"));
        }
        this.f_58655_ = LockCode.m_19111_(p_155113_);
    }

    @Override
    protected void m_183515_(CompoundTag p_187463_) {
        super.m_183515_(p_187463_);
        p_187463_.m_128405_("Primary", MobEffect.m_216882_(this.f_58652_));
        p_187463_.m_128405_("Secondary", MobEffect.m_216882_(this.f_58653_));
        p_187463_.m_128405_("Levels", this.f_58650_);
        if (this.f_58654_ != null) {
            p_187463_.m_128359_("CustomName", Component.Serializer.m_130703_(this.f_58654_));
        }
        this.f_58655_.m_19109_(p_187463_);
    }

    public void m_58681_(@Nullable Component p_58682_) {
        this.f_58654_ = p_58682_;
    }

    @Override
    @Nullable
    public AbstractContainerMenu m_7208_(int p_58696_, Inventory p_58697_, Player p_58698_) {
        if (BaseContainerBlockEntity.m_58629_(p_58698_, this.f_58655_, this.m_5446_())) {
            return new BeaconMenu(p_58696_, p_58697_, this.f_58656_, ContainerLevelAccess.m_39289_(this.f_58857_, this.m_58899_()));
        }
        return null;
    }

    @Override
    public Component m_5446_() {
        return this.f_58654_ != null ? this.f_58654_ : Component.m_237115_("container.beacon");
    }

    @Override
    public void m_142339_(Level p_155091_) {
        super.m_142339_(p_155091_);
        this.f_58651_ = p_155091_.m_141937_() - 1;
    }

    public /* synthetic */ Packet m_58483_() {
        return this.m_58483_();
    }

    public static class BeaconBeamSection {
        final float[] f_58715_;
        private int f_58716_;

        public BeaconBeamSection(float[] p_58718_) {
            this.f_58715_ = p_58718_;
            this.f_58716_ = 1;
        }

        protected void m_58719_() {
            ++this.f_58716_;
        }

        public float[] m_58722_() {
            return this.f_58715_;
        }

        public int m_58723_() {
            return this.f_58716_;
        }
    }
}

