/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.gametest.framework.AfterBatch;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;

public class GameTestRegistry {
    private static final Collection<TestFunction> f_177495_ = Lists.newArrayList();
    private static final Set<String> f_177496_ = Sets.newHashSet();
    private static final Map<String, Consumer<ServerLevel>> f_177497_ = Maps.newHashMap();
    private static final Map<String, Consumer<ServerLevel>> f_177498_ = Maps.newHashMap();
    private static final Collection<TestFunction> f_177499_ = Sets.newHashSet();

    public static void m_177501_(Class<?> p_177502_) {
        Arrays.stream(p_177502_.getDeclaredMethods()).forEach(GameTestRegistry::m_177503_);
    }

    public static void m_177503_(Method p_177504_) {
        GameTestGenerator $$3;
        String $$1 = p_177504_.getDeclaringClass().getSimpleName();
        GameTest $$2 = p_177504_.getAnnotation(GameTest.class);
        if ($$2 != null) {
            f_177495_.add(GameTestRegistry.m_177515_(p_177504_));
            f_177496_.add($$1);
        }
        if (($$3 = p_177504_.getAnnotation(GameTestGenerator.class)) != null) {
            f_177495_.addAll(GameTestRegistry.m_177513_(p_177504_));
            f_177496_.add($$1);
        }
        GameTestRegistry.m_177505_(p_177504_, BeforeBatch.class, BeforeBatch::m_177037_, f_177497_);
        GameTestRegistry.m_177505_(p_177504_, AfterBatch.class, AfterBatch::m_177036_, f_177498_);
    }

    private static <T extends Annotation> void m_177505_(Method p_177506_, Class<T> p_177507_, Function<T, String> p_177508_, Map<String, Consumer<ServerLevel>> p_177509_) {
        String $$5;
        Consumer<?> $$6;
        T $$4 = p_177506_.getAnnotation(p_177507_);
        if ($$4 != null && ($$6 = p_177509_.putIfAbsent($$5 = p_177508_.apply($$4), GameTestRegistry.m_177519_(p_177506_))) != null) {
            throw new RuntimeException("Hey, there should only be one " + p_177507_ + " method per batch. Batch '" + $$5 + "' has more than one!");
        }
    }

    public static Collection<TestFunction> m_127659_(String p_127660_) {
        return f_177495_.stream().filter(p_127674_ -> GameTestRegistry.m_127666_(p_127674_, p_127660_)).collect(Collectors.toList());
    }

    public static Collection<TestFunction> m_127658_() {
        return f_177495_;
    }

    public static Collection<String> m_127669_() {
        return f_177496_;
    }

    public static boolean m_127670_(String p_127671_) {
        return f_177496_.contains(p_127671_);
    }

    @Nullable
    public static Consumer<ServerLevel> m_127676_(String p_127677_) {
        return f_177497_.get(p_127677_);
    }

    @Nullable
    public static Consumer<ServerLevel> m_177517_(String p_177518_) {
        return f_177498_.get(p_177518_);
    }

    public static Optional<TestFunction> m_127679_(String p_127680_) {
        return GameTestRegistry.m_127658_().stream().filter(p_127663_ -> p_127663_.m_128075_().equalsIgnoreCase(p_127680_)).findFirst();
    }

    public static TestFunction m_127681_(String p_127682_) {
        Optional<TestFunction> $$1 = GameTestRegistry.m_127679_(p_127682_);
        if (!$$1.isPresent()) {
            throw new IllegalArgumentException("Can't find the test function for " + p_127682_);
        }
        return $$1.get();
    }

    private static Collection<TestFunction> m_177513_(Method p_177514_) {
        try {
            Object $$1 = p_177514_.getDeclaringClass().newInstance();
            return (Collection)p_177514_.invoke($$1, new Object[0]);
        }
        catch (ReflectiveOperationException $$2) {
            throw new RuntimeException($$2);
        }
    }

    private static TestFunction m_177515_(Method p_177516_) {
        GameTest $$1 = p_177516_.getAnnotation(GameTest.class);
        String $$2 = p_177516_.getDeclaringClass().getSimpleName();
        String $$3 = $$2.toLowerCase();
        String $$4 = $$3 + "." + p_177516_.getName().toLowerCase();
        String $$5 = $$1.m_177046_().isEmpty() ? $$4 : $$3 + "." + $$1.m_177046_();
        String $$6 = $$1.m_177043_();
        Rotation $$7 = StructureUtils.m_127835_($$1.m_177044_());
        return new TestFunction($$6, $$4, $$5, $$7, $$1.m_177042_(), $$1.m_177047_(), $$1.m_177045_(), $$1.m_177049_(), $$1.m_177048_(), GameTestRegistry.m_177519_(p_177516_));
    }

    private static Consumer<?> m_177519_(Method p_177520_) {
        return p_177512_ -> {
            try {
                Object $$2 = p_177520_.getDeclaringClass().newInstance();
                p_177520_.invoke($$2, p_177512_);
            }
            catch (InvocationTargetException $$3) {
                if ($$3.getCause() instanceof RuntimeException) {
                    throw (RuntimeException)$$3.getCause();
                }
                throw new RuntimeException($$3.getCause());
            }
            catch (ReflectiveOperationException $$4) {
                throw new RuntimeException($$4);
            }
        };
    }

    private static boolean m_127666_(TestFunction p_127667_, String p_127668_) {
        return p_127667_.m_128075_().toLowerCase().startsWith(p_127668_.toLowerCase() + ".");
    }

    public static Collection<TestFunction> m_127675_() {
        return f_177499_;
    }

    public static void m_127664_(TestFunction p_127665_) {
        f_177499_.add(p_127665_);
    }

    public static void m_127678_() {
        f_177499_.clear();
    }
}

