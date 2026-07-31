/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Overwrite
 */
package ic2.core.platform.corehacks.mixins.server;

import ic2.core.entity.potion.ShakyPredicate;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value={ProjectileUtil.class})
public class ProjectileMixin {
    @Overwrite
    public static EntityHitResult m_37304_(Level worldIn, Entity projectile, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;
        for (Entity entity1 : worldIn.m_6249_(projectile, boundingBox, filter.and(ShakyPredicate.INSTANCE))) {
            double d1;
            AABB axisalignedbb = entity1.m_20191_().m_82400_((double)0.3f);
            Optional optional = axisalignedbb.m_82371_(startVec, endVec);
            if (!optional.isPresent() || !((d1 = startVec.m_82557_((Vec3)optional.get())) < d0)) continue;
            entity = entity1;
            d0 = d1;
        }
        return entity == null ? null : new EntityHitResult(entity);
    }
}

