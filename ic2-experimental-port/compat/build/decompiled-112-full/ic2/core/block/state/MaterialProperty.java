/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Optional
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.properties.PropertyHelper
 */
package ic2.core.block.state;

import com.google.common.base.Optional;
import ic2.core.block.ITeBlock;
import ic2.core.block.state.ISkippableProperty;
import ic2.core.ref.IC2Material;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyHelper;

public class MaterialProperty
extends PropertyHelper<WrappedMaterial>
implements ISkippableProperty {
    private final int length;
    private final List<WrappedMaterial> values;

    public MaterialProperty(Collection<Material> materials) {
        super("material", WrappedMaterial.class);
        this.values = new ArrayList<WrappedMaterial>(materials.size());
        for (Material material : materials) {
            this.values.add(WrappedMaterial.get(material));
        }
        this.length = this.values.size();
    }

    public Collection<WrappedMaterial> func_177700_c() {
        return this.values;
    }

    public Optional<WrappedMaterial> func_185929_b(String value) {
        for (WrappedMaterial material : WrappedMaterial.MATERIAL_TO_WRAP.values()) {
            if (!material.getName().equals(value)) continue;
            return Optional.of((Object)material);
        }
        return Optional.absent();
    }

    public String getName(WrappedMaterial value) {
        return value.getName();
    }

    public int getId(WrappedMaterial material) {
        assert (this.values.contains(material));
        return this.values.indexOf(material);
    }

    public WrappedMaterial getMaterial(int ID) {
        assert (ID >= 0 && ID < this.length);
        return this.values.get(ID % this.length);
    }

    public static final class WrappedMaterial
    implements Comparable<WrappedMaterial> {
        private final int id;
        private final String name;
        private final Material material;
        private static int nextId;
        private static final Map<Material, WrappedMaterial> MATERIAL_TO_WRAP;

        private WrappedMaterial(Material material, String name) {
            if (material instanceof IC2Material) {
                name = ((IC2Material)material).name;
            }
            this.material = material;
            this.name = name.toLowerCase(Locale.ENGLISH);
            this.id = nextId++;
        }

        public Material getMaterial() {
            return this.material;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public int compareTo(WrappedMaterial other) {
            return this.id - other.id;
        }

        public static WrappedMaterial get(Material material) {
            WrappedMaterial ret = MATERIAL_TO_WRAP.get(material);
            if (ret == null) {
                ret = new WrappedMaterial(material, material.getClass().getName());
                MATERIAL_TO_WRAP.put(material, ret);
            }
            return ret;
        }

        public static boolean check(WrappedMaterial state, ITeBlock teBlock) {
            return teBlock.getMaterial() == state.getMaterial();
        }

        static {
            MATERIAL_TO_WRAP = new HashMap<Material, WrappedMaterial>();
            try {
                for (Field field : Material.class.getFields()) {
                    if (field.getType() != Material.class) continue;
                    Material material = (Material)field.get(null);
                    MATERIAL_TO_WRAP.put(material, new WrappedMaterial(material, field.getName()));
                }
                assert (!MATERIAL_TO_WRAP.isEmpty());
            }
            catch (Exception e) {
                throw new RuntimeException("Error building materials name map", e);
            }
        }
    }
}

