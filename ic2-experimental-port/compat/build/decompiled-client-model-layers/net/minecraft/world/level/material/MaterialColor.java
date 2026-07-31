/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package net.minecraft.world.level.material;

import com.google.common.base.Preconditions;

public class MaterialColor {
    private static final MaterialColor[] f_76387_ = new MaterialColor[64];
    public static final MaterialColor f_76398_ = new MaterialColor(0, 0);
    public static final MaterialColor f_76399_ = new MaterialColor(1, 8368696);
    public static final MaterialColor f_76400_ = new MaterialColor(2, 16247203);
    public static final MaterialColor f_76401_ = new MaterialColor(3, 0xC7C7C7);
    public static final MaterialColor f_76402_ = new MaterialColor(4, 0xFF0000);
    public static final MaterialColor f_76403_ = new MaterialColor(5, 0xA0A0FF);
    public static final MaterialColor f_76404_ = new MaterialColor(6, 0xA7A7A7);
    public static final MaterialColor f_76405_ = new MaterialColor(7, 31744);
    public static final MaterialColor f_76406_ = new MaterialColor(8, 0xFFFFFF);
    public static final MaterialColor f_76407_ = new MaterialColor(9, 10791096);
    public static final MaterialColor f_76408_ = new MaterialColor(10, 9923917);
    public static final MaterialColor f_76409_ = new MaterialColor(11, 0x707070);
    public static final MaterialColor f_76410_ = new MaterialColor(12, 0x4040FF);
    public static final MaterialColor f_76411_ = new MaterialColor(13, 9402184);
    public static final MaterialColor f_76412_ = new MaterialColor(14, 0xFFFCF5);
    public static final MaterialColor f_76413_ = new MaterialColor(15, 14188339);
    public static final MaterialColor f_76414_ = new MaterialColor(16, 11685080);
    public static final MaterialColor f_76415_ = new MaterialColor(17, 6724056);
    public static final MaterialColor f_76416_ = new MaterialColor(18, 0xE5E533);
    public static final MaterialColor f_76417_ = new MaterialColor(19, 8375321);
    public static final MaterialColor f_76418_ = new MaterialColor(20, 15892389);
    public static final MaterialColor f_76419_ = new MaterialColor(21, 0x4C4C4C);
    public static final MaterialColor f_76420_ = new MaterialColor(22, 0x999999);
    public static final MaterialColor f_76421_ = new MaterialColor(23, 5013401);
    public static final MaterialColor f_76422_ = new MaterialColor(24, 8339378);
    public static final MaterialColor f_76361_ = new MaterialColor(25, 3361970);
    public static final MaterialColor f_76362_ = new MaterialColor(26, 6704179);
    public static final MaterialColor f_76363_ = new MaterialColor(27, 6717235);
    public static final MaterialColor f_76364_ = new MaterialColor(28, 0x993333);
    public static final MaterialColor f_76365_ = new MaterialColor(29, 0x191919);
    public static final MaterialColor f_76366_ = new MaterialColor(30, 16445005);
    public static final MaterialColor f_76367_ = new MaterialColor(31, 6085589);
    public static final MaterialColor f_76368_ = new MaterialColor(32, 4882687);
    public static final MaterialColor f_76369_ = new MaterialColor(33, 55610);
    public static final MaterialColor f_76370_ = new MaterialColor(34, 8476209);
    public static final MaterialColor f_76371_ = new MaterialColor(35, 0x700200);
    public static final MaterialColor f_76372_ = new MaterialColor(36, 13742497);
    public static final MaterialColor f_76373_ = new MaterialColor(37, 10441252);
    public static final MaterialColor f_76374_ = new MaterialColor(38, 9787244);
    public static final MaterialColor f_76375_ = new MaterialColor(39, 7367818);
    public static final MaterialColor f_76376_ = new MaterialColor(40, 12223780);
    public static final MaterialColor f_76377_ = new MaterialColor(41, 6780213);
    public static final MaterialColor f_76378_ = new MaterialColor(42, 10505550);
    public static final MaterialColor f_76379_ = new MaterialColor(43, 0x392923);
    public static final MaterialColor f_76380_ = new MaterialColor(44, 8874850);
    public static final MaterialColor f_76381_ = new MaterialColor(45, 0x575C5C);
    public static final MaterialColor f_76382_ = new MaterialColor(46, 8014168);
    public static final MaterialColor f_76383_ = new MaterialColor(47, 4996700);
    public static final MaterialColor f_76384_ = new MaterialColor(48, 4993571);
    public static final MaterialColor f_76385_ = new MaterialColor(49, 5001770);
    public static final MaterialColor f_76386_ = new MaterialColor(50, 9321518);
    public static final MaterialColor f_76388_ = new MaterialColor(51, 2430480);
    public static final MaterialColor f_76389_ = new MaterialColor(52, 12398641);
    public static final MaterialColor f_76390_ = new MaterialColor(53, 9715553);
    public static final MaterialColor f_76391_ = new MaterialColor(54, 6035741);
    public static final MaterialColor f_76392_ = new MaterialColor(55, 1474182);
    public static final MaterialColor f_76393_ = new MaterialColor(56, 3837580);
    public static final MaterialColor f_76394_ = new MaterialColor(57, 5647422);
    public static final MaterialColor f_76395_ = new MaterialColor(58, 1356933);
    public static final MaterialColor f_164534_ = new MaterialColor(59, 0x646464);
    public static final MaterialColor f_164535_ = new MaterialColor(60, 14200723);
    public static final MaterialColor f_164536_ = new MaterialColor(61, 8365974);
    public final int f_76396_;
    public final int f_76397_;

    private MaterialColor(int p_76425_, int p_76426_) {
        if (p_76425_ < 0 || p_76425_ > 63) {
            throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
        }
        this.f_76397_ = p_76425_;
        this.f_76396_ = p_76426_;
        MaterialColor.f_76387_[p_76425_] = this;
    }

    public int m_192921_(Brightness p_192922_) {
        if (this == f_76398_) {
            return 0;
        }
        int $$1 = p_192922_.f_192934_;
        int $$2 = (this.f_76396_ >> 16 & 0xFF) * $$1 / 255;
        int $$3 = (this.f_76396_ >> 8 & 0xFF) * $$1 / 255;
        int $$4 = (this.f_76396_ & 0xFF) * $$1 / 255;
        return 0xFF000000 | $$4 << 16 | $$3 << 8 | $$2;
    }

    public static MaterialColor m_192919_(int p_192920_) {
        Preconditions.checkPositionIndex((int)p_192920_, (int)f_76387_.length, (String)"material id");
        return MaterialColor.m_192927_(p_192920_);
    }

    private static MaterialColor m_192927_(int p_192928_) {
        MaterialColor $$1 = f_76387_[p_192928_];
        return $$1 != null ? $$1 : f_76398_;
    }

    public static int m_192923_(int p_192924_) {
        int $$1 = p_192924_ & 0xFF;
        return MaterialColor.m_192927_($$1 >> 2).m_192921_(Brightness.m_192946_($$1 & 3));
    }

    public byte m_192925_(Brightness p_192926_) {
        return (byte)(this.f_76397_ << 2 | p_192926_.f_192933_ & 3);
    }

    public static final class Brightness
    extends Enum<Brightness> {
        public static final /* enum */ Brightness LOW = new Brightness(0, 180);
        public static final /* enum */ Brightness NORMAL = new Brightness(1, 220);
        public static final /* enum */ Brightness HIGH = new Brightness(2, 255);
        public static final /* enum */ Brightness LOWEST = new Brightness(3, 135);
        private static final Brightness[] f_192935_;
        public final int f_192933_;
        public final int f_192934_;
        private static final /* synthetic */ Brightness[] $VALUES;

        public static Brightness[] values() {
            return (Brightness[])$VALUES.clone();
        }

        public static Brightness valueOf(String p_192949_) {
            return Enum.valueOf(Brightness.class, p_192949_);
        }

        private Brightness(int p_192941_, int p_192942_) {
            this.f_192933_ = p_192941_;
            this.f_192934_ = p_192942_;
        }

        public static Brightness m_192944_(int p_192945_) {
            Preconditions.checkPositionIndex((int)p_192945_, (int)f_192935_.length, (String)"brightness id");
            return Brightness.m_192946_(p_192945_);
        }

        static Brightness m_192946_(int p_192947_) {
            return f_192935_[p_192947_];
        }

        private static /* synthetic */ Brightness[] m_192943_() {
            return new Brightness[]{LOW, NORMAL, HIGH, LOWEST};
        }

        static {
            $VALUES = Brightness.m_192943_();
            f_192935_ = new Brightness[]{LOW, NORMAL, HIGH, LOWEST};
        }
    }
}

