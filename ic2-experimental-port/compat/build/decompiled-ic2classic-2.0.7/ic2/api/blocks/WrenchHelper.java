/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 */
package ic2.api.blocks;

import ic2.api.items.readers.IWrenchTool;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class WrenchHelper {
    public static boolean hasWrench(Player player) {
        return WrenchHelper.isWrench(player.m_21205_()) || WrenchHelper.isWrench(player.m_21206_());
    }

    public static boolean isWrench(ItemStack stack) {
        IWrenchTool tool;
        Item item = stack.m_41720_();
        return item instanceof IWrenchTool && (tool = (IWrenchTool)item).shouldRenderOverlay(stack);
    }

    public static int getDirectionIndex(BlockHitResult result) {
        return WrenchHelper.getDirectionIndex(result.m_82434_(), result.m_82450_().m_82546_(Vec3.m_82528_((Vec3i)result.m_82425_())));
    }

    public static int getDirectionIndex(UseOnContext context) {
        return WrenchHelper.getDirectionIndex(context.m_43719_(), context.m_43720_().m_82546_(Vec3.m_82528_((Vec3i)context.m_8083_())));
    }

    public static int getDirectionIndex(Direction direction, Vec3 sideHit) {
        switch (direction.m_122434_()) {
            case X: {
                return WrenchHelper.calculateIndex(direction == Direction.EAST ? 1.0 - sideHit.m_7094_() : sideHit.m_7094_(), 1.0 - sideHit.m_7098_());
            }
            case Y: {
                return WrenchHelper.calculateIndex(sideHit.m_7096_(), direction == Direction.DOWN ? 1.0 - sideHit.m_7094_() : sideHit.m_7094_());
            }
            case Z: {
                return WrenchHelper.calculateIndex(direction == Direction.NORTH ? 1.0 - sideHit.m_7096_() : sideHit.m_7096_(), 1.0 - sideHit.m_7098_());
            }
        }
        return 0;
    }

    public static Direction getFacingFromIndex(Direction base, int index, Player player) {
        if (base.m_122434_().m_122479_()) {
            return switch (index) {
                case 1 -> {
                    if (player.m_6144_()) {
                        yield base.m_122424_();
                    }
                    yield base;
                }
                case 2 -> Direction.DOWN;
                case 4 -> Direction.UP;
                case 8 -> base.m_122427_();
                case 16 -> base.m_122428_();
                default -> null;
            };
        }
        return switch (index) {
            case 1 -> {
                if (player.m_6144_()) {
                    yield base.m_122424_();
                }
                yield base;
            }
            case 2 -> {
                if (base == Direction.DOWN) {
                    yield Direction.NORTH;
                }
                yield Direction.SOUTH;
            }
            case 4 -> {
                if (base == Direction.DOWN) {
                    yield Direction.SOUTH;
                }
                yield Direction.NORTH;
            }
            case 8 -> Direction.WEST;
            case 16 -> Direction.EAST;
            default -> null;
        };
    }

    private static int calculateIndex(double x, double z) {
        if (x >= 0.2 && x <= 0.8 && z >= 0.2 && z <= 0.8) {
            return 1;
        }
        if ((x < 0.2 || x > 0.8) && (z >= 0.2 && z <= 0.8 || x > 0.8 && (!(z > 0.5) ? z >= 1.0 - x : z <= x) || x < 0.2 && (z > 0.5 ? z <= 1.0 - x : z >= x))) {
            return x < 0.5 ? 8 : 16;
        }
        if ((z < 0.2 || z > 0.8) && (x >= 0.2 && x <= 0.8 || z > 0.8 && (!(x > 0.5) ? x >= 1.0 - z : x <= z) || z < 0.2 && (x > 0.5 ? x <= 1.0 - z : x >= z))) {
            return z < 0.5 ? 4 : 2;
        }
        return 0;
    }
}

