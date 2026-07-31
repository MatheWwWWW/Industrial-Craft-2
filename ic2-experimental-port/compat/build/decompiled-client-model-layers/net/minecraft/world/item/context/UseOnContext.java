/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.context;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class UseOnContext {
    @Nullable
    private final Player f_43703_;
    private final InteractionHand f_43704_;
    private final BlockHitResult f_43705_;
    private final Level f_43706_;
    private final ItemStack f_43707_;

    public UseOnContext(Player p_43709_, InteractionHand p_43710_, BlockHitResult p_43711_) {
        this(p_43709_.f_19853_, p_43709_, p_43710_, p_43709_.m_21120_(p_43710_), p_43711_);
    }

    protected UseOnContext(Level p_43713_, @Nullable Player p_43714_, InteractionHand p_43715_, ItemStack p_43716_, BlockHitResult p_43717_) {
        this.f_43703_ = p_43714_;
        this.f_43704_ = p_43715_;
        this.f_43705_ = p_43717_;
        this.f_43707_ = p_43716_;
        this.f_43706_ = p_43713_;
    }

    protected final BlockHitResult m_43718_() {
        return this.f_43705_;
    }

    public BlockPos m_8083_() {
        return this.f_43705_.m_82425_();
    }

    public Direction m_43719_() {
        return this.f_43705_.m_82434_();
    }

    public Vec3 m_43720_() {
        return this.f_43705_.m_82450_();
    }

    public boolean m_43721_() {
        return this.f_43705_.m_82436_();
    }

    public ItemStack m_43722_() {
        return this.f_43707_;
    }

    @Nullable
    public Player m_43723_() {
        return this.f_43703_;
    }

    public InteractionHand m_43724_() {
        return this.f_43704_;
    }

    public Level m_43725_() {
        return this.f_43706_;
    }

    public Direction m_8125_() {
        return this.f_43703_ == null ? Direction.NORTH : this.f_43703_.m_6350_();
    }

    public boolean m_7078_() {
        return this.f_43703_ != null && this.f_43703_.m_36341_();
    }

    public float m_7074_() {
        return this.f_43703_ == null ? 0.0f : this.f_43703_.m_146908_();
    }
}

