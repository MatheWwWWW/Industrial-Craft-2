/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;

public class TreeDecoratorType<P extends TreeDecorator> {
    public static final TreeDecoratorType<TrunkVineDecorator> f_70042_ = TreeDecoratorType.m_70052_("trunk_vine", TrunkVineDecorator.f_70055_);
    public static final TreeDecoratorType<LeaveVineDecorator> f_70043_ = TreeDecoratorType.m_70052_("leave_vine", LeaveVineDecorator.f_69996_);
    public static final TreeDecoratorType<CocoaDecorator> f_70044_ = TreeDecoratorType.m_70052_("cocoa", CocoaDecorator.f_69972_);
    public static final TreeDecoratorType<BeehiveDecorator> f_70045_ = TreeDecoratorType.m_70052_("beehive", BeehiveDecorator.f_69954_);
    public static final TreeDecoratorType<AlterGroundDecorator> f_70046_ = TreeDecoratorType.m_70052_("alter_ground", AlterGroundDecorator.f_69302_);
    public static final TreeDecoratorType<AttachedToLeavesDecorator> f_226071_ = TreeDecoratorType.m_70052_("attached_to_leaves", AttachedToLeavesDecorator.f_225979_);
    private final Codec<P> f_70047_;

    private static <P extends TreeDecorator> TreeDecoratorType<P> m_70052_(String p_70053_, Codec<P> p_70054_) {
        return Registry.m_122961_(Registry.f_122860_, p_70053_, new TreeDecoratorType<P>(p_70054_));
    }

    private TreeDecoratorType(Codec<P> p_70050_) {
        this.f_70047_ = p_70050_;
    }

    public Codec<P> m_70051_() {
        return this.f_70047_;
    }
}

