/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;

public final class Direction
extends Enum<Direction>
implements StringRepresentable {
    public static final /* enum */ Direction DOWN = new Direction(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0));
    public static final /* enum */ Direction UP = new Direction(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0));
    public static final /* enum */ Direction NORTH = new Direction(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1));
    public static final /* enum */ Direction SOUTH = new Direction(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1));
    public static final /* enum */ Direction WEST = new Direction(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0));
    public static final /* enum */ Direction EAST = new Direction(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));
    public static final StringRepresentable.EnumCodec<Direction> f_175356_;
    public static final Codec<Direction> f_194527_;
    private final int f_122339_;
    private final int f_122340_;
    private final int f_122341_;
    private final String f_122342_;
    private final Axis f_122343_;
    private final AxisDirection f_122344_;
    private final Vec3i f_122345_;
    private static final Direction[] f_122346_;
    private static final Direction[] f_122348_;
    private static final Direction[] f_122349_;
    private static final Long2ObjectMap<Direction> f_122350_;
    private static final /* synthetic */ Direction[] $VALUES;

    public static Direction[] values() {
        return (Direction[])$VALUES.clone();
    }

    public static Direction valueOf(String p_122439_) {
        return Enum.valueOf(Direction.class, p_122439_);
    }

    private Direction(int p_122356_, int p_122357_, int p_122358_, String p_122359_, AxisDirection p_122360_, Axis p_122361_, Vec3i p_122362_) {
        this.f_122339_ = p_122356_;
        this.f_122341_ = p_122358_;
        this.f_122340_ = p_122357_;
        this.f_122342_ = p_122359_;
        this.f_122343_ = p_122361_;
        this.f_122344_ = p_122360_;
        this.f_122345_ = p_122362_;
    }

    public static Direction[] m_122382_(Entity p_122383_) {
        Direction $$17;
        float $$1 = p_122383_.m_5686_(1.0f) * ((float)Math.PI / 180);
        float $$2 = -p_122383_.m_5675_(1.0f) * ((float)Math.PI / 180);
        float $$3 = Mth.m_14031_($$1);
        float $$4 = Mth.m_14089_($$1);
        float $$5 = Mth.m_14031_($$2);
        float $$6 = Mth.m_14089_($$2);
        boolean $$7 = $$5 > 0.0f;
        boolean $$8 = $$3 < 0.0f;
        boolean $$9 = $$6 > 0.0f;
        float $$10 = $$7 ? $$5 : -$$5;
        float $$11 = $$8 ? -$$3 : $$3;
        float $$12 = $$9 ? $$6 : -$$6;
        float $$13 = $$10 * $$4;
        float $$14 = $$12 * $$4;
        Direction $$15 = $$7 ? EAST : WEST;
        Direction $$16 = $$8 ? UP : DOWN;
        Direction direction = $$17 = $$9 ? SOUTH : NORTH;
        if ($$10 > $$12) {
            if ($$11 > $$13) {
                return Direction.m_122398_($$16, $$15, $$17);
            }
            if ($$14 > $$11) {
                return Direction.m_122398_($$15, $$17, $$16);
            }
            return Direction.m_122398_($$15, $$16, $$17);
        }
        if ($$11 > $$14) {
            return Direction.m_122398_($$16, $$17, $$15);
        }
        if ($$13 > $$11) {
            return Direction.m_122398_($$17, $$15, $$16);
        }
        return Direction.m_122398_($$17, $$16, $$15);
    }

    private static Direction[] m_122398_(Direction p_122399_, Direction p_122400_, Direction p_122401_) {
        return new Direction[]{p_122399_, p_122400_, p_122401_, p_122401_.m_122424_(), p_122400_.m_122424_(), p_122399_.m_122424_()};
    }

    public static Direction m_122384_(Matrix4f p_122385_, Direction p_122386_) {
        Vec3i $$2 = p_122386_.m_122436_();
        Vector4f $$3 = new Vector4f($$2.m_123341_(), $$2.m_123342_(), $$2.m_123343_(), 0.0f);
        $$3.m_123607_(p_122385_);
        return Direction.m_122372_($$3.m_123601_(), $$3.m_123615_(), $$3.m_123616_());
    }

    public static Collection<Direction> m_235667_(RandomSource p_235668_) {
        return Util.m_214681_(Direction.values(), p_235668_);
    }

    public static Stream<Direction> m_235666_() {
        return Stream.of(f_122346_);
    }

    public Quaternion m_122406_() {
        Quaternion $$0 = Vector3f.f_122223_.m_122240_(90.0f);
        return switch (this) {
            default -> throw new IncompatibleClassChangeError();
            case DOWN -> Vector3f.f_122223_.m_122240_(180.0f);
            case UP -> Quaternion.f_80118_.m_80161_();
            case NORTH -> {
                $$0.m_80148_(Vector3f.f_122227_.m_122240_(180.0f));
                yield $$0;
            }
            case SOUTH -> $$0;
            case WEST -> {
                $$0.m_80148_(Vector3f.f_122227_.m_122240_(90.0f));
                yield $$0;
            }
            case EAST -> {
                $$0.m_80148_(Vector3f.f_122227_.m_122240_(-90.0f));
                yield $$0;
            }
        };
    }

    public int m_122411_() {
        return this.f_122339_;
    }

    public int m_122416_() {
        return this.f_122341_;
    }

    public AxisDirection m_122421_() {
        return this.f_122344_;
    }

    public static Direction m_175357_(Entity p_175358_, Axis p_175359_) {
        return switch (p_175359_) {
            default -> throw new IncompatibleClassChangeError();
            case Axis.X -> {
                if (EAST.m_122370_(p_175358_.m_5675_(1.0f))) {
                    yield EAST;
                }
                yield WEST;
            }
            case Axis.Z -> {
                if (SOUTH.m_122370_(p_175358_.m_5675_(1.0f))) {
                    yield SOUTH;
                }
                yield NORTH;
            }
            case Axis.Y -> p_175358_.m_5686_(1.0f) < 0.0f ? UP : DOWN;
        };
    }

    public Direction m_122424_() {
        return Direction.m_122376_(this.f_122340_);
    }

    public Direction m_175362_(Axis p_175363_) {
        return switch (p_175363_) {
            default -> throw new IncompatibleClassChangeError();
            case Axis.X -> {
                if (this == WEST || this == EAST) {
                    yield this;
                }
                yield this.m_175366_();
            }
            case Axis.Y -> {
                if (this == UP || this == DOWN) {
                    yield this;
                }
                yield this.m_122427_();
            }
            case Axis.Z -> this == NORTH || this == SOUTH ? this : this.m_175368_();
        };
    }

    public Direction m_175364_(Axis p_175365_) {
        return switch (p_175365_) {
            default -> throw new IncompatibleClassChangeError();
            case Axis.X -> {
                if (this == WEST || this == EAST) {
                    yield this;
                }
                yield this.m_175367_();
            }
            case Axis.Y -> {
                if (this == UP || this == DOWN) {
                    yield this;
                }
                yield this.m_122428_();
            }
            case Axis.Z -> this == NORTH || this == SOUTH ? this : this.m_175369_();
        };
    }

    public Direction m_122427_() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        };
    }

    private Direction m_175366_() {
        return switch (this) {
            case UP -> NORTH;
            case NORTH -> DOWN;
            case DOWN -> SOUTH;
            case SOUTH -> UP;
            default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        };
    }

    private Direction m_175367_() {
        return switch (this) {
            case UP -> SOUTH;
            case SOUTH -> DOWN;
            case DOWN -> NORTH;
            case NORTH -> UP;
            default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
        };
    }

    private Direction m_175368_() {
        return switch (this) {
            case UP -> EAST;
            case EAST -> DOWN;
            case DOWN -> WEST;
            case WEST -> UP;
            default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
        };
    }

    private Direction m_175369_() {
        return switch (this) {
            case UP -> WEST;
            case WEST -> DOWN;
            case DOWN -> EAST;
            case EAST -> UP;
            default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
        };
    }

    public Direction m_122428_() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            default -> throw new IllegalStateException("Unable to get CCW facing of " + this);
        };
    }

    public int m_122429_() {
        return this.f_122345_.m_123341_();
    }

    public int m_122430_() {
        return this.f_122345_.m_123342_();
    }

    public int m_122431_() {
        return this.f_122345_.m_123343_();
    }

    public Vector3f m_122432_() {
        return new Vector3f(this.m_122429_(), this.m_122430_(), this.m_122431_());
    }

    public String m_122433_() {
        return this.f_122342_;
    }

    public Axis m_122434_() {
        return this.f_122343_;
    }

    @Nullable
    public static Direction m_122402_(@Nullable String p_122403_) {
        return f_175356_.m_216455_(p_122403_);
    }

    public static Direction m_122376_(int p_122377_) {
        return f_122348_[Mth.m_14040_(p_122377_ % f_122348_.length)];
    }

    public static Direction m_122407_(int p_122408_) {
        return f_122349_[Mth.m_14040_(p_122408_ % f_122349_.length)];
    }

    @Nullable
    public static Direction m_175360_(BlockPos p_175361_) {
        return (Direction)f_122350_.get(p_175361_.m_121878_());
    }

    @Nullable
    public static Direction m_122378_(int p_122379_, int p_122380_, int p_122381_) {
        return (Direction)f_122350_.get(BlockPos.m_121882_(p_122379_, p_122380_, p_122381_));
    }

    public static Direction m_122364_(double p_122365_) {
        return Direction.m_122407_(Mth.m_14107_(p_122365_ / 90.0 + 0.5) & 3);
    }

    public static Direction m_122387_(Axis p_122388_, AxisDirection p_122389_) {
        return switch (p_122388_) {
            default -> throw new IncompatibleClassChangeError();
            case Axis.X -> {
                if (p_122389_ == AxisDirection.POSITIVE) {
                    yield EAST;
                }
                yield WEST;
            }
            case Axis.Y -> {
                if (p_122389_ == AxisDirection.POSITIVE) {
                    yield UP;
                }
                yield DOWN;
            }
            case Axis.Z -> p_122389_ == AxisDirection.POSITIVE ? SOUTH : NORTH;
        };
    }

    public float m_122435_() {
        return (this.f_122341_ & 3) * 90;
    }

    public static Direction m_235672_(RandomSource p_235673_) {
        return Util.m_214670_(f_122346_, p_235673_);
    }

    public static Direction m_122366_(double p_122367_, double p_122368_, double p_122369_) {
        return Direction.m_122372_((float)p_122367_, (float)p_122368_, (float)p_122369_);
    }

    public static Direction m_122372_(float p_122373_, float p_122374_, float p_122375_) {
        Direction $$3 = NORTH;
        float $$4 = Float.MIN_VALUE;
        for (Direction $$5 : f_122346_) {
            float $$6 = p_122373_ * (float)$$5.f_122345_.m_123341_() + p_122374_ * (float)$$5.f_122345_.m_123342_() + p_122375_ * (float)$$5.f_122345_.m_123343_();
            if (!($$6 > $$4)) continue;
            $$4 = $$6;
            $$3 = $$5;
        }
        return $$3;
    }

    public String toString() {
        return this.f_122342_;
    }

    @Override
    public String m_7912_() {
        return this.f_122342_;
    }

    private static DataResult<Direction> m_194528_(Direction p_194529_) {
        return p_194529_.m_122434_().m_122478_() ? DataResult.success((Object)p_194529_) : DataResult.error((String)"Expected a vertical direction");
    }

    public static Direction m_122390_(AxisDirection p_122391_, Axis p_122392_) {
        for (Direction $$2 : f_122346_) {
            if ($$2.m_122421_() != p_122391_ || $$2.m_122434_() != p_122392_) continue;
            return $$2;
        }
        throw new IllegalArgumentException("No such direction: " + p_122391_ + " " + p_122392_);
    }

    public Vec3i m_122436_() {
        return this.f_122345_;
    }

    public boolean m_122370_(float p_122371_) {
        float $$1 = p_122371_ * ((float)Math.PI / 180);
        float $$2 = -Mth.m_14031_($$1);
        float $$3 = Mth.m_14089_($$1);
        return (float)this.f_122345_.m_123341_() * $$2 + (float)this.f_122345_.m_123343_() * $$3 > 0.0f;
    }

    private static /* synthetic */ Direction[] m_175370_() {
        return new Direction[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
    }

    static {
        $VALUES = Direction.m_175370_();
        f_175356_ = StringRepresentable.m_216439_(Direction::values);
        f_194527_ = f_175356_.flatXmap(Direction::m_194528_, Direction::m_194528_);
        f_122346_ = Direction.values();
        f_122348_ = (Direction[])Arrays.stream(f_122346_).sorted(Comparator.comparingInt(p_235687_ -> p_235687_.f_122339_)).toArray(Direction[]::new);
        f_122349_ = (Direction[])Arrays.stream(f_122346_).filter(p_235685_ -> p_235685_.m_122434_().m_122479_()).sorted(Comparator.comparingInt(p_235683_ -> p_235683_.f_122341_)).toArray(Direction[]::new);
        f_122350_ = (Long2ObjectMap)Arrays.stream(f_122346_).collect(Collectors.toMap(p_235679_ -> new BlockPos(p_235679_.m_122436_()).m_121878_(), p_235675_ -> p_235675_, (p_235670_, p_235671_) -> {
            throw new IllegalArgumentException("Duplicate keys");
        }, Long2ObjectOpenHashMap::new));
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class Axis
    extends Enum<Axis>
    implements StringRepresentable,
    Predicate<Direction> {
        public static final /* enum */ Axis X = new Axis("x"){

            @Override
            public int m_7863_(int p_122496_, int p_122497_, int p_122498_) {
                return p_122496_;
            }

            @Override
            public double m_6150_(double p_122492_, double p_122493_, double p_122494_) {
                return p_122492_;
            }

            @Override
            public /* synthetic */ boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        };
        public static final /* enum */ Axis Y = new Axis("y"){

            @Override
            public int m_7863_(int p_122510_, int p_122511_, int p_122512_) {
                return p_122511_;
            }

            @Override
            public double m_6150_(double p_122506_, double p_122507_, double p_122508_) {
                return p_122507_;
            }

            @Override
            public /* synthetic */ boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        };
        public static final /* enum */ Axis Z = new Axis("z"){

            @Override
            public int m_7863_(int p_122524_, int p_122525_, int p_122526_) {
                return p_122526_;
            }

            @Override
            public double m_6150_(double p_122520_, double p_122521_, double p_122522_) {
                return p_122522_;
            }

            @Override
            public /* synthetic */ boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        };
        public static final Axis[] f_122448_;
        public static final StringRepresentable.EnumCodec<Axis> f_122447_;
        private final String f_122450_;
        private static final /* synthetic */ Axis[] $VALUES;

        public static Axis[] values() {
            return (Axis[])$VALUES.clone();
        }

        public static Axis valueOf(String p_122485_) {
            return Enum.valueOf(Axis.class, p_122485_);
        }

        Axis(String p_122456_) {
            this.f_122450_ = p_122456_;
        }

        @Nullable
        public static Axis m_122473_(String p_122474_) {
            return f_122447_.m_216455_(p_122474_);
        }

        public String m_122477_() {
            return this.f_122450_;
        }

        public boolean m_122478_() {
            return this == Y;
        }

        public boolean m_122479_() {
            return this == X || this == Z;
        }

        public String toString() {
            return this.f_122450_;
        }

        public static Axis m_235688_(RandomSource p_235689_) {
            return Util.m_214670_(f_122448_, p_235689_);
        }

        @Override
        public boolean test(@Nullable Direction p_122472_) {
            return p_122472_ != null && p_122472_.m_122434_() == this;
        }

        public Plane m_122480_() {
            return switch (this) {
                default -> throw new IncompatibleClassChangeError();
                case X, Z -> Plane.HORIZONTAL;
                case Y -> Plane.VERTICAL;
            };
        }

        @Override
        public String m_7912_() {
            return this.f_122450_;
        }

        public abstract int m_7863_(int var1, int var2, int var3);

        public abstract double m_6150_(double var1, double var3, double var5);

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((Direction)object);
        }

        private static /* synthetic */ Axis[] m_175371_() {
            return new Axis[]{X, Y, Z};
        }

        static {
            $VALUES = Axis.m_175371_();
            f_122448_ = Axis.values();
            f_122447_ = StringRepresentable.m_216439_(Axis::values);
        }
    }

    public static final class AxisDirection
    extends Enum<AxisDirection> {
        public static final /* enum */ AxisDirection POSITIVE = new AxisDirection(1, "Towards positive");
        public static final /* enum */ AxisDirection NEGATIVE = new AxisDirection(-1, "Towards negative");
        private final int f_122531_;
        private final String f_122532_;
        private static final /* synthetic */ AxisDirection[] $VALUES;

        public static AxisDirection[] values() {
            return (AxisDirection[])$VALUES.clone();
        }

        public static AxisDirection valueOf(String p_122544_) {
            return Enum.valueOf(AxisDirection.class, p_122544_);
        }

        private AxisDirection(int p_122538_, String p_122539_) {
            this.f_122531_ = p_122538_;
            this.f_122532_ = p_122539_;
        }

        public int m_122540_() {
            return this.f_122531_;
        }

        public String m_175372_() {
            return this.f_122532_;
        }

        public String toString() {
            return this.f_122532_;
        }

        public AxisDirection m_122541_() {
            return this == POSITIVE ? NEGATIVE : POSITIVE;
        }

        private static /* synthetic */ AxisDirection[] m_175373_() {
            return new AxisDirection[]{POSITIVE, NEGATIVE};
        }

        static {
            $VALUES = AxisDirection.m_175373_();
        }
    }

    public static final class Plane
    extends Enum<Plane>
    implements Iterable<Direction>,
    Predicate<Direction> {
        public static final /* enum */ Plane HORIZONTAL = new Plane(new Direction[]{NORTH, EAST, SOUTH, WEST}, new Axis[]{Axis.X, Axis.Z});
        public static final /* enum */ Plane VERTICAL = new Plane(new Direction[]{UP, DOWN}, new Axis[]{Axis.Y});
        private final Direction[] f_122548_;
        private final Axis[] f_122549_;
        private static final /* synthetic */ Plane[] $VALUES;

        public static Plane[] values() {
            return (Plane[])$VALUES.clone();
        }

        public static Plane valueOf(String p_122568_) {
            return Enum.valueOf(Plane.class, p_122568_);
        }

        private Plane(Direction[] p_122555_, Axis[] p_122556_) {
            this.f_122548_ = p_122555_;
            this.f_122549_ = p_122556_;
        }

        public Direction m_235690_(RandomSource p_235691_) {
            return Util.m_214670_(this.f_122548_, p_235691_);
        }

        public Axis m_235692_(RandomSource p_235693_) {
            return Util.m_214670_(this.f_122549_, p_235693_);
        }

        @Override
        public boolean test(@Nullable Direction p_122559_) {
            return p_122559_ != null && p_122559_.m_122434_().m_122480_() == this;
        }

        @Override
        public Iterator<Direction> iterator() {
            return Iterators.forArray((Object[])this.f_122548_);
        }

        public Stream<Direction> m_122557_() {
            return Arrays.stream(this.f_122548_);
        }

        public List<Direction> m_235694_(RandomSource p_235695_) {
            return Util.m_214681_(this.f_122548_, p_235695_);
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((Direction)object);
        }

        private static /* synthetic */ Plane[] m_175374_() {
            return new Plane[]{HORIZONTAL, VERTICAL};
        }

        static {
            $VALUES = Plane.m_175374_();
        }
    }
}

