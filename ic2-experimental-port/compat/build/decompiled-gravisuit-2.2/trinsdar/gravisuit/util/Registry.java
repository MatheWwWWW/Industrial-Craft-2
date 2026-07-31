/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.item.wearable.armor.electric.ElectricPackArmor
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Rarity
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 */
package trinsdar.gravisuit.util;

import ic2.core.item.wearable.armor.electric.ElectricPackArmor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import trinsdar.gravisuit.block.BlockEntityPlasmaPortal;
import trinsdar.gravisuit.block.BlockPlasmaPortal;
import trinsdar.gravisuit.entity.PlasmaBall;
import trinsdar.gravisuit.items.ItemComponents;
import trinsdar.gravisuit.items.armor.ItemAdvancedElectricJetpack;
import trinsdar.gravisuit.items.armor.ItemAdvancedLappack;
import trinsdar.gravisuit.items.armor.ItemAdvancedNuclearJetpack;
import trinsdar.gravisuit.items.armor.ItemGravitationJetpack;
import trinsdar.gravisuit.items.armor.ItemNuclearGravitationJetpack;
import trinsdar.gravisuit.items.tools.ItemRelocator;
import trinsdar.gravisuit.items.tools.ItemToolGravitool;
import trinsdar.gravisuit.items.tools.ItemToolVajra;
import trinsdar.gravisuit.util.GravisuitConfig;

public class Registry {
    public static final Map<ResourceLocation, Item> REGISTRY = new Object2ObjectArrayMap();
    public static final ItemAdvancedElectricJetpack ADVANCED_ELECTRIC_JETPACK = new ItemAdvancedElectricJetpack();
    public static final ItemAdvancedNuclearJetpack ADVANCED_NUCLEAR_JETPACK = new ItemAdvancedNuclearJetpack();
    public static final ElectricPackArmor ADVANCED_LAPPACK = new ItemAdvancedLappack("advanced_lappack", Rarity.UNCOMMON, () -> GravisuitConfig.POWER_VALUES.ADVANCED_LAPPACK_STORAGE, 2, () -> GravisuitConfig.POWER_VALUES.ADVANCED_LAPPACK_TRANSFER);
    public static final ElectricPackArmor ULTIMATE_LAPPACK = new ItemAdvancedLappack("ultimate_lappack", Rarity.EPIC, () -> GravisuitConfig.POWER_VALUES.ULTIMATE_LAPPACK_STORAGE, 3, () -> GravisuitConfig.POWER_VALUES.ULTIMATE_LAPPACK_TRANSFER);
    public static final ItemGravitationJetpack GRAVITATION_JETPACK = new ItemGravitationJetpack();
    public static final ItemNuclearGravitationJetpack NUCLEAR_GRAVITATION_JETPACK = new ItemNuclearGravitationJetpack();
    public static final ItemToolGravitool GRAVITOOL = new ItemToolGravitool();
    public static final ItemToolVajra VAJRA = new ItemToolVajra();
    public static final ItemRelocator RELOCATOR = new ItemRelocator();
    public static final ItemComponents SUPER_CONDUCTOR_COVER = new ItemComponents("super_conductor_cover");
    public static final ItemComponents SUPER_CONDUCTOR = new ItemComponents("super_conductor");
    public static final ItemComponents COOLING_CORE = new ItemComponents("cooling_core");
    public static final ItemComponents GRAVITATION_ENGINE = new ItemComponents("gravitation_engine");
    public static final ItemComponents MAGNETRON = new ItemComponents("magnetron");
    public static final ItemComponents VAJRA_CORE = new ItemComponents("vajra_core");
    public static final ItemComponents ENGINE_BOOST = new ItemComponents("engine_boost");
    public static final EntityType<PlasmaBall> PLASMA_BALL_ENTITY_TYPE = EntityType.Builder.m_20704_(PlasmaBall::new, (MobCategory)MobCategory.MISC).m_20699_(1.0f, 1.0f).setShouldReceiveVelocityUpdates(true).m_20702_(4).m_20717_(20).setCustomClientFactory(PlasmaBall::new).m_20712_("gravisuit:plasma_ball");
    public static final Block PLASMA_PORTAL = new BlockPlasmaPortal();
    public static final BlockEntityType<?> PLASMA_PORTAL_BLOCK_ENTITY = BlockEntityType.Builder.m_155273_(BlockEntityPlasmaPortal::new, (Block[])new Block[]{PLASMA_PORTAL}).m_58966_(null);

    public static void init() {
    }
}

