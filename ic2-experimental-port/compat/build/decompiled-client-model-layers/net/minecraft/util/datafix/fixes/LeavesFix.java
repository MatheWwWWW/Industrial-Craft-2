/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.PackedBitStorage;
import net.minecraft.util.datafix.fixes.References;

public class LeavesFix
extends DataFix {
    private static final int f_145445_ = 128;
    private static final int f_145446_ = 64;
    private static final int f_145447_ = 32;
    private static final int f_145448_ = 16;
    private static final int f_145449_ = 8;
    private static final int f_145450_ = 4;
    private static final int f_145451_ = 2;
    private static final int f_145452_ = 1;
    private static final int[][] f_16200_ = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
    private static final int f_145453_ = 7;
    private static final int f_145454_ = 12;
    private static final int f_145455_ = 4096;
    static final Object2IntMap<String> f_16201_ = (Object2IntMap)DataFixUtils.make((Object)new Object2IntOpenHashMap(), p_16235_ -> {
        p_16235_.put((Object)"minecraft:acacia_leaves", 0);
        p_16235_.put((Object)"minecraft:birch_leaves", 1);
        p_16235_.put((Object)"minecraft:dark_oak_leaves", 2);
        p_16235_.put((Object)"minecraft:jungle_leaves", 3);
        p_16235_.put((Object)"minecraft:oak_leaves", 4);
        p_16235_.put((Object)"minecraft:spruce_leaves", 5);
    });
    static final Set<String> f_16202_ = ImmutableSet.of((Object)"minecraft:acacia_bark", (Object)"minecraft:birch_bark", (Object)"minecraft:dark_oak_bark", (Object)"minecraft:jungle_bark", (Object)"minecraft:oak_bark", (Object)"minecraft:spruce_bark", (Object[])new String[]{"minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log"});

    public LeavesFix(Schema p_16205_, boolean p_16206_) {
        super(p_16205_, p_16206_);
    }

    protected TypeRewriteRule makeRule() {
        Type $$0 = this.getInputSchema().getType(References.f_16773_);
        OpticFinder $$1 = $$0.findField("Level");
        OpticFinder $$2 = $$1.type().findField("Sections");
        Type $$3 = $$2.type();
        if (!($$3 instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        Type $$4 = ((List.ListType)$$3).getElement();
        OpticFinder $$5 = DSL.typeFinder((Type)$$4);
        return this.fixTypeEverywhereTyped("Leaves fix", $$0, p_16220_ -> p_16220_.updateTyped($$1, p_145461_ -> {
            int[] $$3 = new int[]{0};
            Typed $$4 = p_145461_.updateTyped($$2, p_145465_ -> {
                Int2ObjectOpenHashMap $$3 = new Int2ObjectOpenHashMap(p_145465_.getAllTyped($$5).stream().map(p_145467_ -> new LeavesSection((Typed<?>)p_145467_, this.getInputSchema())).collect(Collectors.toMap(Section::m_16301_, p_145457_ -> p_145457_)));
                if ($$3.values().stream().allMatch(Section::m_16298_)) {
                    return p_145465_;
                }
                ArrayList $$4 = Lists.newArrayList();
                for (int $$5 = 0; $$5 < 7; ++$$5) {
                    $$4.add(new IntOpenHashSet());
                }
                for (LeavesSection $$6 : $$3.values()) {
                    if ($$6.m_16298_()) continue;
                    for (int $$7 = 0; $$7 < 4096; ++$$7) {
                        int $$8 = $$6.m_16302_($$7);
                        if ($$6.m_16257_($$8)) {
                            ((IntSet)$$4.get(0)).add($$6.m_16301_() << 12 | $$7);
                            continue;
                        }
                        if (!$$6.m_16276_($$8)) continue;
                        int $$9 = this.m_16208_($$7);
                        int $$10 = this.m_16247_($$7);
                        p_145464_[0] = $$3[0] | LeavesFix.m_16236_($$9 == 0, $$9 == 15, $$10 == 0, $$10 == 15);
                    }
                }
                for (int $$11 = 1; $$11 < 7; ++$$11) {
                    IntSet $$12 = (IntSet)$$4.get($$11 - 1);
                    IntSet $$13 = (IntSet)$$4.get($$11);
                    IntIterator $$14 = $$12.iterator();
                    while ($$14.hasNext()) {
                        int $$15 = $$14.nextInt();
                        int $$16 = this.m_16208_($$15);
                        int $$17 = this.m_16245_($$15);
                        int $$18 = this.m_16247_($$15);
                        for (int[] $$19 : f_16200_) {
                            int $$26;
                            int $$24;
                            int $$25;
                            LeavesSection $$23;
                            int $$20 = $$16 + $$19[0];
                            int $$21 = $$17 + $$19[1];
                            int $$22 = $$18 + $$19[2];
                            if ($$20 < 0 || $$20 > 15 || $$22 < 0 || $$22 > 15 || $$21 < 0 || $$21 > 255 || ($$23 = (LeavesSection)$$3.get($$21 >> 4)) == null || $$23.m_16298_() || !$$23.m_16276_($$25 = $$23.m_16302_($$24 = LeavesFix.m_16210_($$20, $$21 & 0xF, $$22))) || ($$26 = $$23.m_16278_($$25)) <= $$11) continue;
                            $$23.m_16259_($$24, $$25, $$11);
                            $$13.add(LeavesFix.m_16210_($$20, $$21, $$22));
                        }
                    }
                }
                return p_145465_.updateTyped($$5, arg_0 -> LeavesFix.m_145468_((Int2ObjectMap)$$3, arg_0));
            });
            if ($$3[0] != 0) {
                $$4 = $$4.update(DSL.remainderFinder(), p_145473_ -> {
                    Dynamic $$2 = (Dynamic)DataFixUtils.orElse((Optional)p_145473_.get("UpgradeData").result(), (Object)p_145473_.emptyMap());
                    return p_145473_.set("UpgradeData", $$2.set("Sides", p_145473_.createByte((byte)($$2.get("Sides").asByte((byte)0) | $$3[0]))));
                });
            }
            return $$4;
        }));
    }

    public static int m_16210_(int p_16211_, int p_16212_, int p_16213_) {
        return p_16212_ << 8 | p_16213_ << 4 | p_16211_;
    }

    private int m_16208_(int p_16209_) {
        return p_16209_ & 0xF;
    }

    private int m_16245_(int p_16246_) {
        return p_16246_ >> 8 & 0xFF;
    }

    private int m_16247_(int p_16248_) {
        return p_16248_ >> 4 & 0xF;
    }

    public static int m_16236_(boolean p_16237_, boolean p_16238_, boolean p_16239_, boolean p_16240_) {
        int $$4 = 0;
        if (p_16239_) {
            $$4 = p_16238_ ? ($$4 |= 2) : (p_16237_ ? ($$4 |= 0x80) : ($$4 |= 1));
        } else if (p_16240_) {
            $$4 = p_16237_ ? ($$4 |= 0x20) : (p_16238_ ? ($$4 |= 8) : ($$4 |= 0x10));
        } else if (p_16238_) {
            $$4 |= 4;
        } else if (p_16237_) {
            $$4 |= 0x40;
        }
        return $$4;
    }

    private static /* synthetic */ Typed m_145468_(Int2ObjectMap p_145469_, Typed p_145470_) {
        return ((LeavesSection)p_145469_.get(((Dynamic)p_145470_.get(DSL.remainderFinder())).get("Y").asInt(0))).m_16288_(p_145470_);
    }

    public static final class LeavesSection
    extends Section {
        private static final String f_145474_ = "persistent";
        private static final String f_145475_ = "decayable";
        private static final String f_145476_ = "distance";
        @Nullable
        private IntSet f_16250_;
        @Nullable
        private IntSet f_16251_;
        @Nullable
        private Int2IntMap f_16252_;

        public LeavesSection(Typed<?> p_16254_, Schema p_16255_) {
            super(p_16254_, p_16255_);
        }

        @Override
        protected boolean m_7969_() {
            this.f_16250_ = new IntOpenHashSet();
            this.f_16251_ = new IntOpenHashSet();
            this.f_16252_ = new Int2IntOpenHashMap();
            for (int $$0 = 0; $$0 < this.f_16281_.size(); ++$$0) {
                Dynamic $$1 = (Dynamic)this.f_16281_.get($$0);
                String $$2 = $$1.get("Name").asString("");
                if (f_16201_.containsKey((Object)$$2)) {
                    boolean $$3 = Objects.equals($$1.get("Properties").get(f_145475_).asString(""), "false");
                    this.f_16250_.add($$0);
                    this.f_16252_.put(this.m_16292_($$2, $$3, 7), $$0);
                    this.f_16281_.set($$0, this.m_16271_($$1, $$2, $$3, 7));
                }
                if (!f_16202_.contains($$2)) continue;
                this.f_16251_.add($$0);
            }
            return this.f_16250_.isEmpty() && this.f_16251_.isEmpty();
        }

        private Dynamic<?> m_16271_(Dynamic<?> p_16272_, String p_16273_, boolean p_16274_, int p_16275_) {
            Dynamic $$4 = p_16272_.emptyMap();
            $$4 = $$4.set(f_145474_, $$4.createString(p_16274_ ? "true" : "false"));
            $$4 = $$4.set(f_145476_, $$4.createString(Integer.toString(p_16275_)));
            Dynamic $$5 = p_16272_.emptyMap();
            $$5 = $$5.set("Properties", $$4);
            $$5 = $$5.set("Name", $$5.createString(p_16273_));
            return $$5;
        }

        public boolean m_16257_(int p_16258_) {
            return this.f_16251_.contains(p_16258_);
        }

        public boolean m_16276_(int p_16277_) {
            return this.f_16250_.contains(p_16277_);
        }

        int m_16278_(int p_16279_) {
            if (this.m_16257_(p_16279_)) {
                return 0;
            }
            return Integer.parseInt(((Dynamic)this.f_16281_.get(p_16279_)).get("Properties").get(f_145476_).asString(""));
        }

        void m_16259_(int p_16260_, int p_16261_, int p_16262_) {
            boolean $$5;
            Dynamic $$3 = (Dynamic)this.f_16281_.get(p_16261_);
            String $$4 = $$3.get("Name").asString("");
            int $$6 = this.m_16292_($$4, $$5 = Objects.equals($$3.get("Properties").get(f_145474_).asString(""), "true"), p_16262_);
            if (!this.f_16252_.containsKey($$6)) {
                int $$7 = this.f_16281_.size();
                this.f_16250_.add($$7);
                this.f_16252_.put($$6, $$7);
                this.f_16281_.add(this.m_16271_($$3, $$4, $$5, p_16262_));
            }
            int $$8 = this.f_16252_.get($$6);
            if (1 << this.f_16283_.m_14567_() <= $$8) {
                PackedBitStorage $$9 = new PackedBitStorage(this.f_16283_.m_14567_() + 1, 4096);
                for (int $$10 = 0; $$10 < 4096; ++$$10) {
                    $$9.m_14564_($$10, this.f_16283_.m_14562_($$10));
                }
                this.f_16283_ = $$9;
            }
            this.f_16283_.m_14564_(p_16260_, $$8);
        }
    }

    public static abstract class Section {
        protected static final String f_145477_ = "BlockStates";
        protected static final String f_145478_ = "Name";
        protected static final String f_145479_ = "Properties";
        private final Type<Pair<String, Dynamic<?>>> f_16284_ = DSL.named((String)References.f_16783_.typeName(), (Type)DSL.remainderType());
        protected final OpticFinder<List<Pair<String, Dynamic<?>>>> f_16280_ = DSL.fieldFinder((String)"Palette", (Type)DSL.list(this.f_16284_));
        protected final List<Dynamic<?>> f_16281_;
        protected final int f_16282_;
        @Nullable
        protected PackedBitStorage f_16283_;

        public Section(Typed<?> p_16286_, Schema p_16287_) {
            if (!Objects.equals(p_16287_.getType(References.f_16783_), this.f_16284_)) {
                throw new IllegalStateException("Block state type is not what was expected.");
            }
            Optional $$2 = p_16286_.getOptional(this.f_16280_);
            this.f_16281_ = $$2.map(p_16297_ -> p_16297_.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse((List)ImmutableList.of());
            Dynamic $$3 = (Dynamic)p_16286_.get(DSL.remainderFinder());
            this.f_16282_ = $$3.get("Y").asInt(0);
            this.m_16290_($$3);
        }

        protected void m_16290_(Dynamic<?> p_16291_) {
            if (this.m_7969_()) {
                this.f_16283_ = null;
            } else {
                long[] $$1 = p_16291_.get(f_145477_).asLongStream().toArray();
                int $$2 = Math.max(4, DataFixUtils.ceillog2((int)this.f_16281_.size()));
                this.f_16283_ = new PackedBitStorage($$2, 4096, $$1);
            }
        }

        public Typed<?> m_16288_(Typed<?> p_16289_) {
            if (this.m_16298_()) {
                return p_16289_;
            }
            return p_16289_.update(DSL.remainderFinder(), p_16305_ -> p_16305_.set(f_145477_, p_16305_.createLongList(Arrays.stream(this.f_16283_.m_14561_())))).set(this.f_16280_, this.f_16281_.stream().map(p_16300_ -> Pair.of((Object)References.f_16783_.typeName(), (Object)p_16300_)).collect(Collectors.toList()));
        }

        public boolean m_16298_() {
            return this.f_16283_ == null;
        }

        public int m_16302_(int p_16303_) {
            return this.f_16283_.m_14562_(p_16303_);
        }

        protected int m_16292_(String p_16293_, boolean p_16294_, int p_16295_) {
            return f_16201_.get((Object)p_16293_) << 5 | (p_16294_ ? 16 : 0) | p_16295_;
        }

        int m_16301_() {
            return this.f_16282_;
        }

        protected abstract boolean m_7969_();
    }
}

