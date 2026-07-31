/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWCharModsCallbackI
 *  org.lwjgl.glfw.GLFWCursorPosCallbackI
 *  org.lwjgl.glfw.GLFWDropCallbackI
 *  org.lwjgl.glfw.GLFWKeyCallbackI
 *  org.lwjgl.glfw.GLFWMouseButtonCallbackI
 *  org.lwjgl.glfw.GLFWScrollCallbackI
 */
package com.mojang.blaze3d.platform;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.util.LazyLoadedValue;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public class InputConstants {
    @Nullable
    private static final MethodHandle f_166354_;
    private static final int f_84824_;
    public static final int f_166279_ = 48;
    public static final int f_166332_ = 49;
    public static final int f_166355_ = 50;
    public static final int f_166356_ = 51;
    public static final int f_166357_ = 52;
    public static final int f_166358_ = 53;
    public static final int f_166359_ = 54;
    public static final int f_166360_ = 55;
    public static final int f_166361_ = 56;
    public static final int f_166362_ = 57;
    public static final int f_166363_ = 65;
    public static final int f_166364_ = 66;
    public static final int f_166365_ = 67;
    public static final int f_166366_ = 68;
    public static final int f_166367_ = 69;
    public static final int f_166368_ = 70;
    public static final int f_166369_ = 71;
    public static final int f_166370_ = 72;
    public static final int f_166371_ = 73;
    public static final int f_166372_ = 74;
    public static final int f_166373_ = 75;
    public static final int f_166374_ = 76;
    public static final int f_166375_ = 77;
    public static final int f_166376_ = 78;
    public static final int f_166377_ = 79;
    public static final int f_166378_ = 80;
    public static final int f_166253_ = 81;
    public static final int f_166254_ = 82;
    public static final int f_166255_ = 83;
    public static final int f_166256_ = 84;
    public static final int f_166257_ = 85;
    public static final int f_166258_ = 86;
    public static final int f_166259_ = 87;
    public static final int f_166260_ = 88;
    public static final int f_166261_ = 89;
    public static final int f_166262_ = 90;
    public static final int f_166263_ = 290;
    public static final int f_166264_ = 291;
    public static final int f_166265_ = 292;
    public static final int f_166266_ = 293;
    public static final int f_166267_ = 294;
    public static final int f_166268_ = 295;
    public static final int f_166269_ = 296;
    public static final int f_166270_ = 297;
    public static final int f_166271_ = 298;
    public static final int f_166272_ = 299;
    public static final int f_166273_ = 300;
    public static final int f_166274_ = 301;
    public static final int f_166275_ = 302;
    public static final int f_166276_ = 303;
    public static final int f_166277_ = 304;
    public static final int f_166278_ = 305;
    public static final int f_166306_ = 306;
    public static final int f_166307_ = 307;
    public static final int f_166308_ = 308;
    public static final int f_166309_ = 309;
    public static final int f_166310_ = 310;
    public static final int f_166311_ = 311;
    public static final int f_166312_ = 312;
    public static final int f_166313_ = 313;
    public static final int f_166314_ = 314;
    public static final int f_166315_ = 282;
    public static final int f_166316_ = 320;
    public static final int f_166317_ = 321;
    public static final int f_166318_ = 322;
    public static final int f_166319_ = 323;
    public static final int f_166320_ = 324;
    public static final int f_166321_ = 325;
    public static final int f_166322_ = 326;
    public static final int f_166323_ = 327;
    public static final int f_166324_ = 328;
    public static final int f_166325_ = 329;
    public static final int f_166326_ = 330;
    public static final int f_166327_ = 335;
    public static final int f_166328_ = 336;
    public static final int f_166329_ = 264;
    public static final int f_166330_ = 263;
    public static final int f_166331_ = 262;
    public static final int f_166280_ = 265;
    public static final int f_166281_ = 334;
    public static final int f_166282_ = 39;
    public static final int f_166283_ = 92;
    public static final int f_166284_ = 44;
    public static final int f_166285_ = 61;
    public static final int f_166286_ = 96;
    public static final int f_166287_ = 91;
    public static final int f_166288_ = 45;
    public static final int f_166289_ = 332;
    public static final int f_166290_ = 46;
    public static final int f_166291_ = 93;
    public static final int f_166292_ = 59;
    public static final int f_166293_ = 47;
    public static final int f_166294_ = 32;
    public static final int f_166295_ = 258;
    public static final int f_166296_ = 342;
    public static final int f_166297_ = 341;
    public static final int f_166298_ = 340;
    public static final int f_166299_ = 343;
    public static final int f_166300_ = 346;
    public static final int f_166301_ = 345;
    public static final int f_166302_ = 344;
    public static final int f_166303_ = 347;
    public static final int f_166304_ = 257;
    public static final int f_166305_ = 256;
    public static final int f_166333_ = 259;
    public static final int f_166334_ = 261;
    public static final int f_166335_ = 269;
    public static final int f_166336_ = 268;
    public static final int f_166337_ = 260;
    public static final int f_166338_ = 267;
    public static final int f_166339_ = 266;
    public static final int f_166340_ = 280;
    public static final int f_166341_ = 284;
    public static final int f_166342_ = 281;
    public static final int f_166343_ = 283;
    public static final int f_166344_ = 1;
    public static final int f_166345_ = 0;
    public static final int f_166346_ = 2;
    public static final int f_166347_ = 0;
    public static final int f_166348_ = 2;
    public static final int f_166349_ = 1;
    public static final int f_166350_ = 2;
    public static final int f_166351_ = 208897;
    public static final int f_166352_ = 212995;
    public static final int f_166353_ = 212993;
    public static final Key f_84822_;

    public static Key m_84827_(int p_84828_, int p_84829_) {
        if (p_84828_ == -1) {
            return Type.SCANCODE.m_84895_(p_84829_);
        }
        return Type.KEYSYM.m_84895_(p_84828_);
    }

    public static Key m_84851_(String p_84852_) {
        if (Key.f_84857_.containsKey(p_84852_)) {
            return Key.f_84857_.get(p_84852_);
        }
        for (Type $$1 : Type.values()) {
            if (!p_84852_.startsWith($$1.f_84886_)) continue;
            String $$2 = p_84852_.substring($$1.f_84886_.length() + 1);
            int $$3 = Integer.parseInt($$2);
            if ($$1 == Type.MOUSE) {
                --$$3;
            }
            return $$1.m_84895_($$3);
        }
        throw new IllegalArgumentException("Unknown key name: " + p_84852_);
    }

    public static boolean m_84830_(long p_84831_, int p_84832_) {
        return GLFW.glfwGetKey((long)p_84831_, (int)p_84832_) == 1;
    }

    public static void m_84844_(long p_84845_, GLFWKeyCallbackI p_84846_, GLFWCharModsCallbackI p_84847_) {
        GLFW.glfwSetKeyCallback((long)p_84845_, (GLFWKeyCallbackI)p_84846_);
        GLFW.glfwSetCharModsCallback((long)p_84845_, (GLFWCharModsCallbackI)p_84847_);
    }

    public static void m_84838_(long p_84839_, GLFWCursorPosCallbackI p_84840_, GLFWMouseButtonCallbackI p_84841_, GLFWScrollCallbackI p_84842_, GLFWDropCallbackI p_84843_) {
        GLFW.glfwSetCursorPosCallback((long)p_84839_, (GLFWCursorPosCallbackI)p_84840_);
        GLFW.glfwSetMouseButtonCallback((long)p_84839_, (GLFWMouseButtonCallbackI)p_84841_);
        GLFW.glfwSetScrollCallback((long)p_84839_, (GLFWScrollCallbackI)p_84842_);
        GLFW.glfwSetDropCallback((long)p_84839_, (GLFWDropCallbackI)p_84843_);
    }

    public static void m_84833_(long p_84834_, int p_84835_, double p_84836_, double p_84837_) {
        GLFW.glfwSetCursorPos((long)p_84834_, (double)p_84836_, (double)p_84837_);
        GLFW.glfwSetInputMode((long)p_84834_, (int)208897, (int)p_84835_);
    }

    public static boolean m_84826_() {
        try {
            return f_166354_ != null && f_166354_.invokeExact();
        }
        catch (Throwable $$0) {
            throw new RuntimeException($$0);
        }
    }

    public static void m_84848_(long p_84849_, boolean p_84850_) {
        if (InputConstants.m_84826_()) {
            GLFW.glfwSetInputMode((long)p_84849_, (int)f_84824_, (int)(p_84850_ ? 1 : 0));
        }
    }

    static {
        MethodHandles.Lookup $$0 = MethodHandles.lookup();
        MethodType $$1 = MethodType.methodType(Boolean.TYPE);
        MethodHandle $$2 = null;
        int $$3 = 0;
        try {
            $$2 = $$0.findStatic(GLFW.class, "glfwRawMouseMotionSupported", $$1);
            MethodHandle $$4 = $$0.findStaticGetter(GLFW.class, "GLFW_RAW_MOUSE_MOTION", Integer.TYPE);
            $$3 = $$4.invokeExact();
        }
        catch (NoSuchFieldException | NoSuchMethodException $$4) {
        }
        catch (Throwable $$5) {
            throw new RuntimeException($$5);
        }
        f_166354_ = $$2;
        f_84824_ = $$3;
        f_84822_ = Type.KEYSYM.m_84895_(-1);
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type KEYSYM = new Type("key.keyboard", (p_84914_, p_84915_) -> {
            String $$2 = GLFW.glfwGetKeyName((int)p_84914_, (int)-1);
            return $$2 != null ? Component.m_237113_($$2) : Component.m_237115_(p_84915_);
        });
        public static final /* enum */ Type SCANCODE = new Type("scancode", (p_84911_, p_84912_) -> {
            String $$2 = GLFW.glfwGetKeyName((int)-1, (int)p_84911_);
            return $$2 != null ? Component.m_237113_($$2) : Component.m_237115_(p_84912_);
        });
        public static final /* enum */ Type MOUSE = new Type("key.mouse", (p_84904_, p_84905_) -> Language.m_128107_().m_6722_((String)p_84905_) ? Component.m_237115_(p_84905_) : Component.m_237110_("key.mouse", p_84904_ + 1));
        private final Int2ObjectMap<Key> f_84885_ = new Int2ObjectOpenHashMap();
        final String f_84886_;
        final BiFunction<Integer, String, Component> f_84887_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_84917_) {
            return Enum.valueOf(Type.class, p_84917_);
        }

        private static void m_84899_(Type p_84900_, String p_84901_, int p_84902_) {
            Key $$3 = new Key(p_84901_, p_84900_, p_84902_);
            p_84900_.f_84885_.put(p_84902_, (Object)$$3);
        }

        private Type(String p_84893_, BiFunction<Integer, String, Component> p_84894_) {
            this.f_84886_ = p_84893_;
            this.f_84887_ = p_84894_;
        }

        public Key m_84895_(int p_84896_) {
            return (Key)this.f_84885_.computeIfAbsent(p_84896_, p_84907_ -> {
                int $$1 = p_84907_;
                if (this == MOUSE) {
                    ++$$1;
                }
                String $$2 = this.f_84886_ + "." + $$1;
                return new Key($$2, this, p_84907_);
            });
        }

        private static /* synthetic */ Type[] m_166380_() {
            return new Type[]{KEYSYM, SCANCODE, MOUSE};
        }

        static {
            $VALUES = Type.m_166380_();
            Type.m_84899_(KEYSYM, "key.keyboard.unknown", -1);
            Type.m_84899_(MOUSE, "key.mouse.left", 0);
            Type.m_84899_(MOUSE, "key.mouse.right", 1);
            Type.m_84899_(MOUSE, "key.mouse.middle", 2);
            Type.m_84899_(MOUSE, "key.mouse.4", 3);
            Type.m_84899_(MOUSE, "key.mouse.5", 4);
            Type.m_84899_(MOUSE, "key.mouse.6", 5);
            Type.m_84899_(MOUSE, "key.mouse.7", 6);
            Type.m_84899_(MOUSE, "key.mouse.8", 7);
            Type.m_84899_(KEYSYM, "key.keyboard.0", 48);
            Type.m_84899_(KEYSYM, "key.keyboard.1", 49);
            Type.m_84899_(KEYSYM, "key.keyboard.2", 50);
            Type.m_84899_(KEYSYM, "key.keyboard.3", 51);
            Type.m_84899_(KEYSYM, "key.keyboard.4", 52);
            Type.m_84899_(KEYSYM, "key.keyboard.5", 53);
            Type.m_84899_(KEYSYM, "key.keyboard.6", 54);
            Type.m_84899_(KEYSYM, "key.keyboard.7", 55);
            Type.m_84899_(KEYSYM, "key.keyboard.8", 56);
            Type.m_84899_(KEYSYM, "key.keyboard.9", 57);
            Type.m_84899_(KEYSYM, "key.keyboard.a", 65);
            Type.m_84899_(KEYSYM, "key.keyboard.b", 66);
            Type.m_84899_(KEYSYM, "key.keyboard.c", 67);
            Type.m_84899_(KEYSYM, "key.keyboard.d", 68);
            Type.m_84899_(KEYSYM, "key.keyboard.e", 69);
            Type.m_84899_(KEYSYM, "key.keyboard.f", 70);
            Type.m_84899_(KEYSYM, "key.keyboard.g", 71);
            Type.m_84899_(KEYSYM, "key.keyboard.h", 72);
            Type.m_84899_(KEYSYM, "key.keyboard.i", 73);
            Type.m_84899_(KEYSYM, "key.keyboard.j", 74);
            Type.m_84899_(KEYSYM, "key.keyboard.k", 75);
            Type.m_84899_(KEYSYM, "key.keyboard.l", 76);
            Type.m_84899_(KEYSYM, "key.keyboard.m", 77);
            Type.m_84899_(KEYSYM, "key.keyboard.n", 78);
            Type.m_84899_(KEYSYM, "key.keyboard.o", 79);
            Type.m_84899_(KEYSYM, "key.keyboard.p", 80);
            Type.m_84899_(KEYSYM, "key.keyboard.q", 81);
            Type.m_84899_(KEYSYM, "key.keyboard.r", 82);
            Type.m_84899_(KEYSYM, "key.keyboard.s", 83);
            Type.m_84899_(KEYSYM, "key.keyboard.t", 84);
            Type.m_84899_(KEYSYM, "key.keyboard.u", 85);
            Type.m_84899_(KEYSYM, "key.keyboard.v", 86);
            Type.m_84899_(KEYSYM, "key.keyboard.w", 87);
            Type.m_84899_(KEYSYM, "key.keyboard.x", 88);
            Type.m_84899_(KEYSYM, "key.keyboard.y", 89);
            Type.m_84899_(KEYSYM, "key.keyboard.z", 90);
            Type.m_84899_(KEYSYM, "key.keyboard.f1", 290);
            Type.m_84899_(KEYSYM, "key.keyboard.f2", 291);
            Type.m_84899_(KEYSYM, "key.keyboard.f3", 292);
            Type.m_84899_(KEYSYM, "key.keyboard.f4", 293);
            Type.m_84899_(KEYSYM, "key.keyboard.f5", 294);
            Type.m_84899_(KEYSYM, "key.keyboard.f6", 295);
            Type.m_84899_(KEYSYM, "key.keyboard.f7", 296);
            Type.m_84899_(KEYSYM, "key.keyboard.f8", 297);
            Type.m_84899_(KEYSYM, "key.keyboard.f9", 298);
            Type.m_84899_(KEYSYM, "key.keyboard.f10", 299);
            Type.m_84899_(KEYSYM, "key.keyboard.f11", 300);
            Type.m_84899_(KEYSYM, "key.keyboard.f12", 301);
            Type.m_84899_(KEYSYM, "key.keyboard.f13", 302);
            Type.m_84899_(KEYSYM, "key.keyboard.f14", 303);
            Type.m_84899_(KEYSYM, "key.keyboard.f15", 304);
            Type.m_84899_(KEYSYM, "key.keyboard.f16", 305);
            Type.m_84899_(KEYSYM, "key.keyboard.f17", 306);
            Type.m_84899_(KEYSYM, "key.keyboard.f18", 307);
            Type.m_84899_(KEYSYM, "key.keyboard.f19", 308);
            Type.m_84899_(KEYSYM, "key.keyboard.f20", 309);
            Type.m_84899_(KEYSYM, "key.keyboard.f21", 310);
            Type.m_84899_(KEYSYM, "key.keyboard.f22", 311);
            Type.m_84899_(KEYSYM, "key.keyboard.f23", 312);
            Type.m_84899_(KEYSYM, "key.keyboard.f24", 313);
            Type.m_84899_(KEYSYM, "key.keyboard.f25", 314);
            Type.m_84899_(KEYSYM, "key.keyboard.num.lock", 282);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.0", 320);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.1", 321);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.2", 322);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.3", 323);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.4", 324);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.5", 325);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.6", 326);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.7", 327);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.8", 328);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.9", 329);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.add", 334);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.decimal", 330);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.enter", 335);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.equal", 336);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.multiply", 332);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.divide", 331);
            Type.m_84899_(KEYSYM, "key.keyboard.keypad.subtract", 333);
            Type.m_84899_(KEYSYM, "key.keyboard.down", 264);
            Type.m_84899_(KEYSYM, "key.keyboard.left", 263);
            Type.m_84899_(KEYSYM, "key.keyboard.right", 262);
            Type.m_84899_(KEYSYM, "key.keyboard.up", 265);
            Type.m_84899_(KEYSYM, "key.keyboard.apostrophe", 39);
            Type.m_84899_(KEYSYM, "key.keyboard.backslash", 92);
            Type.m_84899_(KEYSYM, "key.keyboard.comma", 44);
            Type.m_84899_(KEYSYM, "key.keyboard.equal", 61);
            Type.m_84899_(KEYSYM, "key.keyboard.grave.accent", 96);
            Type.m_84899_(KEYSYM, "key.keyboard.left.bracket", 91);
            Type.m_84899_(KEYSYM, "key.keyboard.minus", 45);
            Type.m_84899_(KEYSYM, "key.keyboard.period", 46);
            Type.m_84899_(KEYSYM, "key.keyboard.right.bracket", 93);
            Type.m_84899_(KEYSYM, "key.keyboard.semicolon", 59);
            Type.m_84899_(KEYSYM, "key.keyboard.slash", 47);
            Type.m_84899_(KEYSYM, "key.keyboard.space", 32);
            Type.m_84899_(KEYSYM, "key.keyboard.tab", 258);
            Type.m_84899_(KEYSYM, "key.keyboard.left.alt", 342);
            Type.m_84899_(KEYSYM, "key.keyboard.left.control", 341);
            Type.m_84899_(KEYSYM, "key.keyboard.left.shift", 340);
            Type.m_84899_(KEYSYM, "key.keyboard.left.win", 343);
            Type.m_84899_(KEYSYM, "key.keyboard.right.alt", 346);
            Type.m_84899_(KEYSYM, "key.keyboard.right.control", 345);
            Type.m_84899_(KEYSYM, "key.keyboard.right.shift", 344);
            Type.m_84899_(KEYSYM, "key.keyboard.right.win", 347);
            Type.m_84899_(KEYSYM, "key.keyboard.enter", 257);
            Type.m_84899_(KEYSYM, "key.keyboard.escape", 256);
            Type.m_84899_(KEYSYM, "key.keyboard.backspace", 259);
            Type.m_84899_(KEYSYM, "key.keyboard.delete", 261);
            Type.m_84899_(KEYSYM, "key.keyboard.end", 269);
            Type.m_84899_(KEYSYM, "key.keyboard.home", 268);
            Type.m_84899_(KEYSYM, "key.keyboard.insert", 260);
            Type.m_84899_(KEYSYM, "key.keyboard.page.down", 267);
            Type.m_84899_(KEYSYM, "key.keyboard.page.up", 266);
            Type.m_84899_(KEYSYM, "key.keyboard.caps.lock", 280);
            Type.m_84899_(KEYSYM, "key.keyboard.pause", 284);
            Type.m_84899_(KEYSYM, "key.keyboard.scroll.lock", 281);
            Type.m_84899_(KEYSYM, "key.keyboard.menu", 348);
            Type.m_84899_(KEYSYM, "key.keyboard.print.screen", 283);
            Type.m_84899_(KEYSYM, "key.keyboard.world.1", 161);
            Type.m_84899_(KEYSYM, "key.keyboard.world.2", 162);
        }
    }

    public static final class Key {
        private final String f_84853_;
        private final Type f_84854_;
        private final int f_84855_;
        private final LazyLoadedValue<Component> f_84856_;
        static final Map<String, Key> f_84857_ = Maps.newHashMap();

        Key(String p_84860_, Type p_84861_, int p_84862_) {
            this.f_84853_ = p_84860_;
            this.f_84854_ = p_84861_;
            this.f_84855_ = p_84862_;
            this.f_84856_ = new LazyLoadedValue<Component>(() -> p_84870_.f_84887_.apply(p_84862_, p_84860_));
            f_84857_.put(p_84860_, this);
        }

        public Type m_84868_() {
            return this.f_84854_;
        }

        public int m_84873_() {
            return this.f_84855_;
        }

        public String m_84874_() {
            return this.f_84853_;
        }

        public Component m_84875_() {
            return this.f_84856_.m_13971_();
        }

        public OptionalInt m_84876_() {
            if (this.f_84855_ >= 48 && this.f_84855_ <= 57) {
                return OptionalInt.of(this.f_84855_ - 48);
            }
            if (this.f_84855_ >= 320 && this.f_84855_ <= 329) {
                return OptionalInt.of(this.f_84855_ - 320);
            }
            return OptionalInt.empty();
        }

        public boolean equals(Object p_84878_) {
            if (this == p_84878_) {
                return true;
            }
            if (p_84878_ == null || this.getClass() != p_84878_.getClass()) {
                return false;
            }
            Key $$1 = (Key)p_84878_;
            return this.f_84855_ == $$1.f_84855_ && this.f_84854_ == $$1.f_84854_;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.f_84854_, this.f_84855_});
        }

        public String toString() {
            return this.f_84853_;
        }
    }
}

