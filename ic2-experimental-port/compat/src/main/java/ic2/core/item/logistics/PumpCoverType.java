package ic2.core.item.logistics;

import ic2.api.block.IIdProvider;

/** Exact pump-cover rates and overlay colours from IC2 2.8.222. */
public enum PumpCoverType implements IIdProvider {
    pump_lv(640, 0x00FF00),
    pump_mv(2560, 0xFFFF00);

    public static final PumpCoverType[] values = values();

    public final int transferRate;
    public final int color;

    PumpCoverType(int transferRate, int color) {
        this.transferRate = transferRate;
        this.color = color;
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
        return color;
    }

    @Override
    public String getModelName() {
        return "pump";
    }

    public static PumpCoverType byId(int id) {
        return id >= 0 && id < values.length ? values[id] : pump_lv;
    }
}
