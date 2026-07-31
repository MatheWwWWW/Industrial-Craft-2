/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BoatItem
extends Item {
    private static final Predicate<Entity> f_40615_ = EntitySelector.f_20408_.and(Entity::m_6087_);
    private final Boat.Type f_40616_;
    private final boolean f_220011_;

    public BoatItem(boolean p_220013_, Boat.Type p_220014_, Item.Properties p_220015_) {
        super(p_220015_);
        this.f_220011_ = p_220013_;
        this.f_40616_ = p_220014_;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_40622_, Player p_40623_, InteractionHand p_40624_) {
        ItemStack $$3 = p_40623_.m_21120_(p_40624_);
        BlockHitResult $$4 = BoatItem.m_41435_(p_40622_, p_40623_, ClipContext.Fluid.ANY);
        if (((HitResult)$$4).m_6662_() == HitResult.Type.MISS) {
            return InteractionResultHolder.m_19098_($$3);
        }
        Vec3 $$5 = p_40623_.m_20252_(1.0f);
        double $$6 = 5.0;
        List<Entity> $$7 = p_40622_.m_6249_(p_40623_, p_40623_.m_20191_().m_82369_($$5.m_82490_(5.0)).m_82400_(1.0), f_40615_);
        if (!$$7.isEmpty()) {
            Vec3 $$8 = p_40623_.m_146892_();
            for (Entity $$9 : $$7) {
                AABB $$10 = $$9.m_20191_().m_82400_($$9.m_6143_());
                if (!$$10.m_82390_($$8)) continue;
                return InteractionResultHolder.m_19098_($$3);
            }
        }
        if (((HitResult)$$4).m_6662_() == HitResult.Type.BLOCK) {
            Boat $$11 = this.m_220016_(p_40622_, $$4);
            $$11.m_38332_(this.f_40616_);
            $$11.m_146922_(p_40623_.m_146908_());
            if (!p_40622_.m_45756_($$11, $$11.m_20191_())) {
                return InteractionResultHolder.m_19100_($$3);
            }
            if (!p_40622_.f_46443_) {
                p_40622_.m_7967_($$11);
                p_40622_.m_220400_(p_40623_, GameEvent.f_157810_, $$4.m_82450_());
                if (!p_40623_.m_150110_().f_35937_) {
                    $$3.m_41774_(1);
                }
            }
            p_40623_.m_36246_(Stats.f_12982_.m_12902_(this));
            return InteractionResultHolder.m_19092_($$3, p_40622_.m_5776_());
        }
        return InteractionResultHolder.m_19098_($$3);
    }

    private Boat m_220016_(Level p_220017_, HitResult p_220018_) {
        if (this.f_220011_) {
            return new ChestBoat(p_220017_, p_220018_.m_82450_().f_82479_, p_220018_.m_82450_().f_82480_, p_220018_.m_82450_().f_82481_);
        }
        return new Boat(p_220017_, p_220018_.m_82450_().f_82479_, p_220018_.m_82450_().f_82480_, p_220018_.m_82450_().f_82481_);
    }
}

