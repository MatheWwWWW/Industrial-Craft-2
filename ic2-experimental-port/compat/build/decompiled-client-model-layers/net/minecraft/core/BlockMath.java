/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.core;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import org.slf4j.Logger;

public class BlockMath {
    private static final Logger f_121840_ = LogUtils.getLogger();
    public static final Map<Direction, Transformation> f_175256_ = Util.m_137469_(Maps.newEnumMap(Direction.class), p_121851_ -> {
        p_121851_.put(Direction.SOUTH, Transformation.m_121093_());
        p_121851_.put(Direction.EAST, new Transformation(null, Vector3f.f_122225_.m_122240_(90.0f), null, null));
        p_121851_.put(Direction.WEST, new Transformation(null, Vector3f.f_122225_.m_122240_(-90.0f), null, null));
        p_121851_.put(Direction.NORTH, new Transformation(null, Vector3f.f_122225_.m_122240_(180.0f), null, null));
        p_121851_.put(Direction.UP, new Transformation(null, Vector3f.f_122223_.m_122240_(-90.0f), null, null));
        p_121851_.put(Direction.DOWN, new Transformation(null, Vector3f.f_122223_.m_122240_(90.0f), null, null));
    });
    public static final Map<Direction, Transformation> f_175257_ = Util.m_137469_(Maps.newEnumMap(Direction.class), p_121849_ -> {
        for (Direction $$1 : Direction.values()) {
            p_121849_.put($$1, f_175256_.get($$1).m_121103_());
        }
    });

    public static Transformation m_121842_(Transformation p_121843_) {
        Matrix4f $$1 = Matrix4f.m_27653_(0.5f, 0.5f, 0.5f);
        $$1.m_27644_(p_121843_.m_121104_());
        $$1.m_27644_(Matrix4f.m_27653_(-0.5f, -0.5f, -0.5f));
        return new Transformation($$1);
    }

    public static Transformation m_175259_(Transformation p_175260_) {
        Matrix4f $$1 = Matrix4f.m_27653_(-0.5f, -0.5f, -0.5f);
        $$1.m_27644_(p_175260_.m_121104_());
        $$1.m_27644_(Matrix4f.m_27653_(0.5f, 0.5f, 0.5f));
        return new Transformation($$1);
    }

    public static Transformation m_121844_(Transformation p_121845_, Direction p_121846_, Supplier<String> p_121847_) {
        Direction $$3 = Direction.m_122384_(p_121845_.m_121104_(), p_121846_);
        Transformation $$4 = p_121845_.m_121103_();
        if ($$4 == null) {
            f_121840_.warn(p_121847_.get());
            return new Transformation(null, null, new Vector3f(0.0f, 0.0f, 0.0f), null);
        }
        Transformation $$5 = f_175257_.get(p_121846_).m_121096_($$4).m_121096_(f_175256_.get($$3));
        return BlockMath.m_121842_($$5);
    }
}

