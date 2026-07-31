/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 */
package ic2.core.platform.registries;

import ic2.api.util.DirectionList;
import ic2.core.utils.math.ConnectionState;
import java.util.function.Predicate;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class IC2Properties {
    public static final BooleanProperty ACTIVE = BooleanProperty.m_61465_((String)"active");
    public static final IntegerProperty ACTIVE_0_2 = IntegerProperty.m_61631_((String)"active", (int)0, (int)2);
    public static final IntegerProperty ACTIVE_0_3 = IntegerProperty.m_61631_((String)"active", (int)0, (int)3);
    public static final BooleanProperty LAVA_LOGGED = BooleanProperty.m_61465_((String)"lavalogged");
    public static final DirectionProperty ALL_FACINGS = DirectionProperty.m_61546_((String)"facing", (Predicate)DirectionList.ALL);
    public static final DirectionProperty HORIZONTAL_FACINGS = DirectionProperty.m_61543_((String)"facing", DirectionList.HORIZONTAL.toFacings());
    public static final EnumProperty<DyeColor> MAIN_COLOR = EnumProperty.m_61587_((String)"main_color", DyeColor.class);
    public static final EnumProperty<DyeColor> SECOND_COLOR = EnumProperty.m_61587_((String)"second_color", DyeColor.class);
    public static final BooleanProperty LIGHT = BooleanProperty.m_61465_((String)"light");
    public static final IntegerProperty LIGHT_0_15 = IntegerProperty.m_61631_((String)"light", (int)0, (int)15);
    public static final BooleanProperty REDSTONE = BooleanProperty.m_61465_((String)"redstone");
    public static final BooleanProperty STRUCTURE_FORMED = BooleanProperty.m_61465_((String)"formed");
    public static final IntegerProperty FORMED_STATE2X2 = IntegerProperty.m_61631_((String)"state", (int)0, (int)7);
    public static final IntegerProperty FORMED_STATE3X3 = IntegerProperty.m_61631_((String)"state", (int)0, (int)26);
    public static final IntegerProperty FORMED_STATE4X4 = IntegerProperty.m_61631_((String)"state", (int)0, (int)63);
    public static final IntegerProperty FORMED_STATE3X3_FLAT = IntegerProperty.m_61631_((String)"state", (int)0, (int)9);
    public static final IntegerProperty DYNAMIC_STATE = IntegerProperty.m_61631_((String)"size", (int)0, (int)2);
    public static final float[][][][] UVS_2_4 = new float[][][][]{ConnectionState.createTextureCutting(2, 2), ConnectionState.createTextureCutting(3, 3), ConnectionState.createTextureCutting(4, 4)};
}

