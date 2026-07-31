package ic2.core.block.transport.items;

import ic2.api.block.IIdProvider;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.StringRepresentable;

/** Exact size, geometry and throughput multipliers from IC2 2.8.222. */
public enum PipeSize implements IIdProvider, StringRepresentable {
    tiny(1, 0.25F, 1.0F / 6.0F),
    small(4, 0.375F, 1.0F / 3.0F),
    medium(16, 0.5F, 1.0F),
    large(64, 0.625F, 2.0F);

    public static final PipeSize[] values = values();
    private static final Map<String, PipeSize> NAME_MAP = new HashMap<>();

    public final int maxStackSize;
    public final float thickness;
    public final float multiplier;

    PipeSize(int maxStackSize, float thickness, float multiplier) {
        this.maxStackSize = maxStackSize;
        this.thickness = thickness;
        this.multiplier = multiplier;
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
    public String m_7912_() {
        return name();
    }

    public static PipeSize get(String name) {
        return NAME_MAP.get(name);
    }

    public static PipeSize byId(int id) {
        return id >= 0 && id < values.length ? values[id] : small;
    }

    static {
        for (PipeSize value : values) {
            NAME_MAP.put(value.getName(), value);
        }
    }
}
