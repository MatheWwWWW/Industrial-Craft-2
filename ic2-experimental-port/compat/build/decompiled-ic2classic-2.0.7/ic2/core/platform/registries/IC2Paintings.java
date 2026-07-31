/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.decoration.PaintingVariant
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.GameData
 */
package ic2.core.platform.registries;

import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

public class IC2Paintings {
    public static final List<PaintingVariant> PAINTINGS = ObjectLists.synchronize(CollectionUtils.createList());

    public static PaintingVariant create(String textureName, int width, int height) {
        PaintingVariant type = new PaintingVariant(width, height);
        ResourceLocation locaiton = GameData.checkPrefix((String)textureName, (boolean)false);
        ForgeRegistries.PAINTING_VARIANTS.register(locaiton, (Object)type);
        PAINTINGS.add(type);
        return type;
    }

    public static void init() {
        IC2Paintings.create("hazard_battery_fumes", 16, 16);
        IC2Paintings.create("hazard_bio", 16, 16);
        IC2Paintings.create("hazard_electrical", 16, 16);
        IC2Paintings.create("hazard_electromagnetic_field", 16, 16);
        IC2Paintings.create("hazard_general", 16, 16);
        IC2Paintings.create("hazard_high_temperature", 16, 16);
        IC2Paintings.create("hazard_low_temperature", 16, 16);
        IC2Paintings.create("hazard_noxious_or_irritating_materials", 16, 16);
        IC2Paintings.create("hazard_open_flame", 16, 16);
        IC2Paintings.create("hazard_radioactive", 16, 16);
        IC2Paintings.create("hazard_slippery_floor", 16, 16);
        IC2Paintings.create("hazard_strong_magnetic_field", 16, 16);
        IC2Paintings.create("hazard_suspended_load", 16, 16);
        IC2Paintings.create("hazard_toxic", 16, 16);
        IC2Paintings.create("hazard_trip", 16, 16);
    }
}

