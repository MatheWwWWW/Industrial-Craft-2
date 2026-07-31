/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class DebugPackets {
    private static final Logger f_133672_ = LogUtils.getLogger();

    public static void m_133682_(ServerLevel p_133683_, BlockPos p_133684_, String p_133685_, int p_133686_, int p_133687_) {
        FriendlyByteBuf $$5 = new FriendlyByteBuf(Unpooled.buffer());
        $$5.m_130064_(p_133684_);
        $$5.writeInt(p_133686_);
        $$5.m_130070_(p_133685_);
        $$5.writeInt(p_133687_);
        DebugPackets.m_133691_(p_133683_, $$5, ClientboundCustomPayloadPacket.f_132026_);
    }

    public static void m_133674_(ServerLevel p_133675_) {
        FriendlyByteBuf $$1 = new FriendlyByteBuf(Unpooled.buffer());
        DebugPackets.m_133691_(p_133675_, $$1, ClientboundCustomPayloadPacket.f_132027_);
    }

    public static void m_133676_(ServerLevel p_133677_, ChunkPos p_133678_) {
    }

    public static void m_133679_(ServerLevel p_133680_, BlockPos p_133681_) {
        DebugPackets.m_133722_(p_133680_, p_133681_);
    }

    public static void m_133716_(ServerLevel p_133717_, BlockPos p_133718_) {
        DebugPackets.m_133722_(p_133717_, p_133718_);
    }

    public static void m_133719_(ServerLevel p_133720_, BlockPos p_133721_) {
        DebugPackets.m_133722_(p_133720_, p_133721_);
    }

    private static void m_133722_(ServerLevel p_133723_, BlockPos p_133724_) {
    }

    public static void m_133703_(Level p_133704_, Mob p_133705_, @Nullable Path p_133706_, float p_133707_) {
    }

    public static void m_133708_(Level p_133709_, BlockPos p_133710_) {
    }

    public static void m_133711_(WorldGenLevel p_133712_, StructureStart p_133713_) {
    }

    public static void m_133699_(Level p_133700_, Mob p_133701_, GoalSelector p_133702_) {
        if (!(p_133700_ instanceof ServerLevel)) {
            return;
        }
    }

    public static void m_133688_(ServerLevel p_133689_, Collection<Raid> p_133690_) {
    }

    public static void m_133695_(LivingEntity p_133696_) {
    }

    public static void m_133697_(Bee p_133698_) {
    }

    public static void m_237887_(Level p_237888_, GameEvent p_237889_, Vec3 p_237890_) {
    }

    public static void m_179507_(Level p_179508_, GameEventListener p_179509_) {
    }

    public static void m_179510_(Level p_179511_, BlockPos p_179512_, BlockState p_179513_, BeehiveBlockEntity p_179514_) {
    }

    private static void m_179498_(LivingEntity p_179499_, FriendlyByteBuf p_179500_) {
        Brain<Path> $$2 = p_179499_.m_6274_();
        long $$3 = p_179499_.f_19853_.m_46467_();
        if (p_179499_ instanceof InventoryCarrier) {
            SimpleContainer $$4 = ((InventoryCarrier)((Object)p_179499_)).m_35311_();
            p_179500_.m_130070_($$4.m_7983_() ? "" : ((Object)$$4).toString());
        } else {
            p_179500_.m_130070_("");
        }
        p_179500_.m_236835_($$2.m_21874_(MemoryModuleType.f_26377_) ? $$2.m_21952_(MemoryModuleType.f_26377_) : Optional.empty(), (p_237912_, p_237913_) -> p_237913_.m_164704_((FriendlyByteBuf)((Object)p_237912_)));
        if (p_179499_ instanceof Villager) {
            Villager $$5 = (Villager)p_179499_;
            boolean $$6 = $$5.m_35392_($$3);
            p_179500_.writeBoolean($$6);
        } else {
            p_179500_.writeBoolean(false);
        }
        if (p_179499_.m_6095_() == EntityType.f_217015_) {
            Warden $$7 = (Warden)p_179499_;
            p_179500_.writeInt($$7.m_219464_());
        } else {
            p_179500_.writeInt(-1);
        }
        p_179500_.m_236828_($$2.m_147340_(), (p_237909_, p_237910_) -> p_237909_.m_130070_(p_237910_.m_37998_()));
        Set $$8 = $$2.m_21956_().stream().map(Behavior::toString).collect(Collectors.toSet());
        p_179500_.m_236828_($$8, FriendlyByteBuf::m_130070_);
        p_179500_.m_236828_(DebugPackets.m_179495_(p_179499_, $$3), (p_237915_, p_237916_) -> {
            String $$2 = StringUtil.m_144998_(p_237916_, 255, true);
            p_237915_.m_130070_($$2);
        });
        if (p_179499_ instanceof Villager) {
            Set $$9 = Stream.of(MemoryModuleType.f_26360_, MemoryModuleType.f_26359_, MemoryModuleType.f_26362_).map($$2::m_21952_).flatMap(Optional::stream).map(GlobalPos::m_122646_).collect(Collectors.toSet());
            p_179500_.m_236828_($$9, FriendlyByteBuf::m_130064_);
        } else {
            p_179500_.m_130130_(0);
        }
        if (p_179499_ instanceof Villager) {
            Set $$10 = Stream.of(MemoryModuleType.f_26361_).map($$2::m_21952_).flatMap(Optional::stream).map(GlobalPos::m_122646_).collect(Collectors.toSet());
            p_179500_.m_236828_($$10, FriendlyByteBuf::m_130064_);
        } else {
            p_179500_.m_130130_(0);
        }
        if (p_179499_ instanceof Villager) {
            Map<UUID, Object2IntMap<GossipType>> $$11 = ((Villager)p_179499_).m_35517_().m_148159_();
            ArrayList $$12 = Lists.newArrayList();
            $$11.forEach((p_237900_, p_237901_) -> {
                String $$3 = DebugEntityNameGenerator.m_133668_(p_237900_);
                p_237901_.forEach((p_237896_, p_237897_) -> $$12.add($$3 + ": " + p_237896_ + ": " + p_237897_));
            });
            p_179500_.m_236828_($$12, FriendlyByteBuf::m_130070_);
        } else {
            p_179500_.m_130130_(0);
        }
    }

    private static List<String> m_179495_(LivingEntity p_179496_, long p_179497_) {
        Map<MemoryModuleType<?>, Optional<ExpirableValue<?>>> $$2 = p_179496_.m_6274_().m_147339_();
        ArrayList $$3 = Lists.newArrayList();
        for (Map.Entry<MemoryModuleType<?>, Optional<ExpirableValue<?>>> $$4 : $$2.entrySet()) {
            String $$13;
            MemoryModuleType<?> $$5 = $$4.getKey();
            Optional<ExpirableValue<?>> $$6 = $$4.getValue();
            if ($$6.isPresent()) {
                ExpirableValue<?> $$7 = $$6.get();
                Object $$8 = $$7.m_26319_();
                if ($$5 == MemoryModuleType.f_26325_) {
                    long $$9 = p_179497_ - (Long)$$8;
                    String $$10 = $$9 + " ticks ago";
                } else if ($$7.m_26321_()) {
                    String $$11 = DebugPackets.m_179492_((ServerLevel)p_179496_.f_19853_, $$8) + " (ttl: " + $$7.m_148191_() + ")";
                } else {
                    String $$12 = DebugPackets.m_179492_((ServerLevel)p_179496_.f_19853_, $$8);
                }
            } else {
                $$13 = "-";
            }
            $$3.add(Registry.f_122871_.m_7981_($$5).m_135815_() + ": " + $$13);
        }
        $$3.sort(String::compareTo);
        return $$3;
    }

    private static String m_179492_(ServerLevel p_179493_, @Nullable Object p_179494_) {
        if (p_179494_ == null) {
            return "-";
        }
        if (p_179494_ instanceof UUID) {
            return DebugPackets.m_179492_(p_179493_, p_179493_.m_8791_((UUID)p_179494_));
        }
        if (p_179494_ instanceof LivingEntity) {
            Entity $$2 = (Entity)p_179494_;
            return DebugEntityNameGenerator.m_179486_($$2);
        }
        if (p_179494_ instanceof Nameable) {
            return ((Nameable)p_179494_).m_7755_().getString();
        }
        if (p_179494_ instanceof WalkTarget) {
            return DebugPackets.m_179492_(p_179493_, ((WalkTarget)p_179494_).m_26420_());
        }
        if (p_179494_ instanceof EntityTracker) {
            return DebugPackets.m_179492_(p_179493_, ((EntityTracker)p_179494_).m_147481_());
        }
        if (p_179494_ instanceof GlobalPos) {
            return DebugPackets.m_179492_(p_179493_, ((GlobalPos)p_179494_).m_122646_());
        }
        if (p_179494_ instanceof BlockPosTracker) {
            return DebugPackets.m_179492_(p_179493_, ((BlockPosTracker)p_179494_).m_6675_());
        }
        if (p_179494_ instanceof EntityDamageSource) {
            Entity $$3 = ((EntityDamageSource)p_179494_).m_7639_();
            return $$3 == null ? p_179494_.toString() : DebugPackets.m_179492_(p_179493_, $$3);
        }
        if (p_179494_ instanceof Collection) {
            ArrayList $$4 = Lists.newArrayList();
            for (Object $$5 : (Iterable)p_179494_) {
                $$4.add(DebugPackets.m_179492_(p_179493_, $$5));
            }
            return ((Object)$$4).toString();
        }
        return p_179494_.toString();
    }

    private static void m_133691_(ServerLevel p_133692_, FriendlyByteBuf p_133693_, ResourceLocation p_133694_) {
        ClientboundCustomPayloadPacket $$3 = new ClientboundCustomPayloadPacket(p_133694_, p_133693_);
        for (Player player : p_133692_.m_6907_()) {
            ((ServerPlayer)player).f_8906_.m_9829_($$3);
        }
    }

    private static /* synthetic */ void m_237917_(FriendlyByteBuf p_237918_, Path p_237919_) {
        p_237919_.m_164704_(p_237918_);
    }

    private static /* synthetic */ void m_237905_(FriendlyByteBuf p_237906_, Raid p_237907_) {
        p_237906_.m_130064_(p_237907_.m_37780_());
    }

    private static /* synthetic */ void m_237902_(FriendlyByteBuf p_237903_, WrappedGoal p_237904_) {
        p_237903_.writeInt(p_237904_.m_26012_());
        p_237903_.writeBoolean(p_237904_.m_7620_());
        p_237903_.m_130070_(p_237904_.m_26015_().getClass().getSimpleName());
    }

    private static /* synthetic */ String m_237885_(ResourceKey p_237886_) {
        return p_237886_.m_135782_().toString();
    }

    private static /* synthetic */ void m_179489_(ServerLevel p_179490_, PoiRecord p_179491_) {
        DebugPackets.m_133679_(p_179490_, p_179491_.m_27257_());
    }

    private static /* synthetic */ boolean m_237891_(Holder p_237892_) {
        return true;
    }
}

