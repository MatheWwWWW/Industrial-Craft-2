/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.util.Mth
 */
package ic2.core.utils.math;

import ic2.api.util.DirectionList;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public final class ConnectionState {
    static final ConnectionState[] STATES = ConnectionState.createArray();
    int code;
    int size;
    Connection[] array;

    private ConnectionState() {
        throw new RuntimeException("NOT ALLOWED!");
    }

    private ConnectionState(int code) {
        this.code = Mth.m_14045_((int)code, (int)0, (int)15);
        ObjectList list = CollectionUtils.createList();
        for (int i = 0; i < 4; ++i) {
            if ((code & 1 << i) == 0) continue;
            list.add((Object)Connection.byIndex(i));
        }
        this.size = list.size();
        this.array = (Connection[])list.toArray((Object[])new Connection[list.size()]);
    }

    public static ConnectionState fromList(DirectionList list, Direction facing) {
        int index = 0;
        for (Direction dir : list.remove(DirectionList.ofAxis(facing.m_122434_()))) {
            index |= 1 << Connection.byFacing(dir, facing).getIndex();
        }
        return STATES[index];
    }

    public static ConnectionState fromList(DirectionList list, Direction.Axis axis) {
        int index = 0;
        for (Direction dir : list.remove(DirectionList.ofAxis(axis))) {
            index |= 1 << Connection.byFacing(dir, axis).getIndex();
        }
        return STATES[index];
    }

    public static ConnectionState fromNumber(int index) {
        return STATES[index];
    }

    public int size() {
        return this.size;
    }

    public int getCode() {
        return this.code;
    }

    public ConnectionState inverse() {
        return STATES[15 - this.code];
    }

    public ConnectionState opposite() {
        int value = 0;
        for (Connection con : this.array) {
            value |= 1 << con.getOpposite().getIndex();
        }
        return STATES[value];
    }

    public String toString() {
        return "ConnectionState: " + ObjectArrayList.wrap((Object[])this.array);
    }

    public String getTypeName() {
        switch (this.size) {
            case 0: {
                return "full";
            }
            case 1: {
                return "end_" + this.array[0].getName();
            }
            case 2: {
                return this.array[0].getOpposite() == this.array[1] ? (this.array[0].isVertical() ? "straight_vertical" : "straight_horizontal") : this.array[0].getCornerName(this.array[1]);
            }
            case 3: {
                return "side_" + ConnectionState.STATES[15 - this.code].array[0].getName();
            }
        }
        return "empty";
    }

    public int getIndex() {
        switch (this.size) {
            case 0: {
                return 0;
            }
            case 1: {
                return this.array[0].getIndex();
            }
            case 2: {
                return this.array[0].getOpposite() == this.array[1] ? (this.array[0].isVertical() ? 0 : 1) : this.array[0].getCornerIndex(this.array[1]);
            }
            case 3: {
                return ConnectionState.STATES[15 - this.code].array[0].getIndex();
            }
        }
        return 0;
    }

    public float[] get3x3UVs() {
        switch (this.size) {
            case 0: {
                return new float[]{0.0f, 0.0f, 16.0f, 16.0f};
            }
            case 1: {
                return this.array[0].getUVs();
            }
            case 2: {
                return this.array[0].getOpposite() == this.array[1] ? this.array[0].getUVs() : this.array[0].getCornerUVs(this.array[1]);
            }
            case 3: {
                return ConnectionState.STATES[15 - this.code].array[0].getUVs();
            }
        }
        return new float[]{0.0f, 0.0f, 16.0f, 16.0f};
    }

    static ConnectionState[] createArray() {
        ConnectionState[] states = new ConnectionState[16];
        for (int i = 0; i < 16; ++i) {
            states[i] = new ConnectionState(i);
        }
        return states;
    }

    public static float[] createQuads(float[][][] uvs, Direction side, int offset, int width, int height, int depth) {
        int x = --offset % width;
        int y = offset / (width * depth) % height;
        int z = height - 1 - offset / width % depth;
        switch (side) {
            case DOWN: {
                return uvs[x][depth - 1 - z];
            }
            case UP: {
                return uvs[x][z];
            }
            case NORTH: {
                return uvs[width - 1 - x][y];
            }
            case SOUTH: {
                return uvs[x][y];
            }
            case EAST: {
                return uvs[depth - 1 - z][y];
            }
            case WEST: {
                return uvs[z][y];
            }
        }
        return new float[]{0.0f, 0.0f, 16.0f, 16.0f};
    }

    public static float[][][] createTextureCutting(int xSize, int ySize) {
        float width = 16.0f / (float)xSize;
        float height = 16.0f / (float)ySize;
        float[][][] array = new float[xSize][ySize][4];
        for (int x = 0; x < xSize; ++x) {
            for (int y = 0; y < ySize; ++y) {
                array[x][y] = new float[]{(float)x * width, (float)y * height, (float)(x + 1) * width, (float)(y + 1) * height};
            }
        }
        return array;
    }

    public static enum Connection implements CollectionUtils.IIndexEnum
    {
        UP(0, 2, "bottom"),
        EAST(1, 3, "left"),
        DOWN(2, 0, "top"),
        WEST(3, 1, "right");

        static final Connection[] MAPPED;
        int index;
        int opposite;
        String name;

        private Connection(int index, int opposite, String name) {
            this.index = index;
            this.opposite = opposite;
            this.name = name;
        }

        public Connection getOpposite() {
            return MAPPED[this.opposite];
        }

        public String getCornerName(Connection other) {
            if (other.getIndex() == this.index || other.getIndex() == this.opposite) {
                throw new IllegalStateException("Illegal State");
            }
            return "corner_" + (this.isVertical() ? this.name + "_" + other.getName() : other.getName() + "_" + this.name);
        }

        public int getCornerIndex(Connection other) {
            if (other.getIndex() == this.index || other.getIndex() == this.opposite) {
                throw new IllegalStateException("Illegal State");
            }
            if (this.isVertical()) {
                return other == WEST ? (this == UP ? 3 : 2) : (this == UP ? 0 : 1);
            }
            return this == WEST ? (other == UP ? 3 : 2) : (other == UP ? 0 : 1);
        }

        public boolean isVertical() {
            return this.index == 0 || this.index == 2;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        public String toString() {
            return this.name;
        }

        public float[] getCornerUVs(Connection other) {
            if (!this.isVertical() && other.isVertical()) {
                return other.getCornerUVs(this);
            }
            float[] result = new float[4];
            switch (other) {
                case EAST: {
                    result[0] = 0.0f;
                    result[2] = 5.3333335f;
                    break;
                }
                case WEST: {
                    result[0] = 10.666667f;
                    result[2] = 16.0f;
                    break;
                }
                default: {
                    result[0] = 0.0f;
                    result[2] = 16.0f;
                }
            }
            switch (this) {
                case DOWN: {
                    result[1] = 0.0f;
                    result[3] = 5.3333335f;
                    break;
                }
                case UP: {
                    result[1] = 10.666667f;
                    result[3] = 16.0f;
                    break;
                }
                default: {
                    result[0] = 0.0f;
                    result[2] = 16.0f;
                }
            }
            return result;
        }

        public float[] getUVs() {
            switch (this) {
                case DOWN: {
                    return new float[]{5.3333335f, 10.666667f, 10.666667f, 16.0f};
                }
                case EAST: {
                    return new float[]{10.666667f, 5.3333335f, 16.0f, 10.666667f};
                }
                case UP: {
                    return new float[]{5.3333335f, 0.0f, 10.666667f, 5.3333335f};
                }
                case WEST: {
                    return new float[]{0.0f, 5.3333335f, 5.3333335f, 10.666667f};
                }
            }
            throw new IllegalStateException();
        }

        public String getName() {
            return this.name;
        }

        public static Connection byFacing(Direction dir, Direction facing) {
            switch (facing.m_122434_()) {
                case X: {
                    switch (dir) {
                        case DOWN: {
                            return DOWN;
                        }
                        case UP: {
                            return UP;
                        }
                        case NORTH: {
                            return facing.m_122421_() == Direction.AxisDirection.POSITIVE ? EAST : WEST;
                        }
                        case SOUTH: {
                            return facing.m_122421_() == Direction.AxisDirection.POSITIVE ? WEST : EAST;
                        }
                    }
                    throw new IllegalStateException("Not a Valid Facing");
                }
                case Y: {
                    return MAPPED[(facing.m_122421_() == Direction.AxisDirection.POSITIVE || dir.m_122434_() == Direction.Axis.X ? dir.m_122424_() : dir).m_122416_()];
                }
                case Z: {
                    switch (dir) {
                        case DOWN: {
                            return DOWN;
                        }
                        case UP: {
                            return UP;
                        }
                        case WEST: {
                            return facing.m_122421_() == Direction.AxisDirection.POSITIVE ? WEST : EAST;
                        }
                        case EAST: {
                            return facing.m_122421_() == Direction.AxisDirection.POSITIVE ? EAST : WEST;
                        }
                    }
                    throw new IllegalStateException("Not a Valid Facing");
                }
            }
            throw new IllegalStateException("Not a Valid Axis");
        }

        public static Connection byFacing(Direction dir, Direction.Axis axis) {
            switch (axis) {
                case X: {
                    switch (dir) {
                        case DOWN: {
                            return DOWN;
                        }
                        case UP: {
                            return UP;
                        }
                        case NORTH: {
                            return WEST;
                        }
                        case SOUTH: {
                            return EAST;
                        }
                    }
                    throw new IllegalStateException("Not a Valid Facing");
                }
                case Y: {
                    return MAPPED[dir.m_122416_()];
                }
                case Z: {
                    switch (dir) {
                        case DOWN: {
                            return DOWN;
                        }
                        case UP: {
                            return UP;
                        }
                        case WEST: {
                            return WEST;
                        }
                        case EAST: {
                            return EAST;
                        }
                    }
                    throw new IllegalStateException("Not a Valid Facing");
                }
            }
            throw new IllegalStateException("Not a Valid Axis");
        }

        public static Connection byIndex(int index) {
            return MAPPED[index & 3];
        }

        static {
            MAPPED = (Connection[])CollectionUtils.createSortedArray((CollectionUtils.IIndexEnum[])Connection.values());
        }
    }
}

