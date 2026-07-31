package ic2.core.block.transport.items;

import ic2.api.block.IIdProvider;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.StringRepresentable;

/** Exact material constants from IC2 Experimental 2.8.222. */
public enum PipeType implements IIdProvider, StringRepresentable {
    bronze(2400, 174, 81, 17),
    steel(4800, 128, 128, 128);

    public static final PipeType[] values = values();
    private static final Map<String, PipeType> NAME_MAP = new HashMap<>();

    public final int transferRate;
    public final int red;
    public final int green;
    public final int blue;

    PipeType(int transferRate, int red, int green, int blue) {
        this.transferRate = transferRate;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public String getName(PipeSize size) {
        return getName() + "_pipe" + (size == null ? "" : "_" + size.name());
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public int getColor() {
        return (red << 16) | (green << 8) | blue;
    }

    @Override
    public String m_7912_() {
        return name();
    }

    public static PipeType get(String name) {
        return NAME_MAP.get(name);
    }

    public static PipeType byId(int id) {
        return id >= 0 && id < values.length ? values[id] : bronze;
    }

    static {
        for (PipeType value : values) {
            NAME_MAP.put(value.getName(), value);
        }
    }
}
