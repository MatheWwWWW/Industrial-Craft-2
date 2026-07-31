/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

public final class EquipmentSlot
extends Enum<EquipmentSlot> {
    public static final /* enum */ EquipmentSlot MAINHAND = new EquipmentSlot(Type.HAND, 0, 0, "mainhand");
    public static final /* enum */ EquipmentSlot OFFHAND = new EquipmentSlot(Type.HAND, 1, 5, "offhand");
    public static final /* enum */ EquipmentSlot FEET = new EquipmentSlot(Type.ARMOR, 0, 1, "feet");
    public static final /* enum */ EquipmentSlot LEGS = new EquipmentSlot(Type.ARMOR, 1, 2, "legs");
    public static final /* enum */ EquipmentSlot CHEST = new EquipmentSlot(Type.ARMOR, 2, 3, "chest");
    public static final /* enum */ EquipmentSlot HEAD = new EquipmentSlot(Type.ARMOR, 3, 4, "head");
    private final Type f_20730_;
    private final int f_20731_;
    private final int f_20732_;
    private final String f_20733_;
    private static final /* synthetic */ EquipmentSlot[] $VALUES;

    public static EquipmentSlot[] values() {
        return (EquipmentSlot[])$VALUES.clone();
    }

    public static EquipmentSlot valueOf(String p_20753_) {
        return Enum.valueOf(EquipmentSlot.class, p_20753_);
    }

    private EquipmentSlot(Type p_20739_, int p_20740_, int p_20741_, String p_20742_) {
        this.f_20730_ = p_20739_;
        this.f_20731_ = p_20740_;
        this.f_20732_ = p_20741_;
        this.f_20733_ = p_20742_;
    }

    public Type m_20743_() {
        return this.f_20730_;
    }

    public int m_20749_() {
        return this.f_20731_;
    }

    public int m_147068_(int p_147069_) {
        return p_147069_ + this.f_20731_;
    }

    public int m_20750_() {
        return this.f_20732_;
    }

    public String m_20751_() {
        return this.f_20733_;
    }

    public static EquipmentSlot m_20747_(String p_20748_) {
        for (EquipmentSlot $$1 : EquipmentSlot.values()) {
            if (!$$1.m_20751_().equals(p_20748_)) continue;
            return $$1;
        }
        throw new IllegalArgumentException("Invalid slot '" + p_20748_ + "'");
    }

    public static EquipmentSlot m_20744_(Type p_20745_, int p_20746_) {
        for (EquipmentSlot $$2 : EquipmentSlot.values()) {
            if ($$2.m_20743_() != p_20745_ || $$2.m_20749_() != p_20746_) continue;
            return $$2;
        }
        throw new IllegalArgumentException("Invalid slot '" + p_20745_ + "': " + p_20746_);
    }

    private static /* synthetic */ EquipmentSlot[] m_147070_() {
        return new EquipmentSlot[]{MAINHAND, OFFHAND, FEET, LEGS, CHEST, HEAD};
    }

    static {
        $VALUES = EquipmentSlot.m_147070_();
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type HAND = new Type();
        public static final /* enum */ Type ARMOR = new Type();
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_20763_) {
            return Enum.valueOf(Type.class, p_20763_);
        }

        private static /* synthetic */ Type[] m_147071_() {
            return new Type[]{HAND, ARMOR};
        }

        static {
            $VALUES = Type.m_147071_();
        }
    }
}

