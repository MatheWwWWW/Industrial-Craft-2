package ic2.core.block.wiring;

/**
 * IC2 Experimental cable catalogue with IC2 Classic conductors appended
 * without changing any existing enum ordinal.
 */
public enum CableType {
    copper(1, 1, 0.25F, 0.2D, 128),
    glass(0, 0, 0.25F, 0.025D, 8192),
    gold(2, 1, 0.1875F, 0.4D, 512),
    iron(3, 1, 0.375F, 0.8D, 2048),
    tin(1, 1, 0.25F, 0.2D, 32),
    detector(0, Integer.MAX_VALUE, 0.5F, 0.5D, 8192),
    splitter(0, Integer.MAX_VALUE, 0.5F, 0.5D, 8192),
    bronze(2, 1, 0.125F, 0.7D, 128),
    plasma(0, 0, 0.375F, 1.2D, 32768);

    public static final float insulationThickness = 0.0625F;
    public final int maxInsulation;
    public final int minColoredInsulation;
    public final float thickness;
    public final double loss;
    public final int capacity;
    public static final CableType[] values;

    CableType(int maxInsulation, int minColoredInsulation, float thickness,
            double loss, int capacity) {
        this.maxInsulation = maxInsulation;
        this.minColoredInsulation = minColoredInsulation;
        this.thickness = thickness;
        this.loss = loss;
        this.capacity = capacity;
    }

    public float getThickness(int insulation) {
        if (this == bronze) {
            return switch (insulation) {
                case 0 -> 2.0F / 16.0F;
                case 1 -> 3.0F / 16.0F;
                case 2 -> 3.5F / 16.0F;
                default -> throw new IllegalArgumentException(
                        "invalid bronze insulation " + insulation);
            };
        }
        return 0.125F * insulation + thickness;
    }

    static {
        values = CableType.values();
    }
}
