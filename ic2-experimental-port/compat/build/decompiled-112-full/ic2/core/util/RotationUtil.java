/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 */
package ic2.core.util;

import ic2.core.IC2;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    public static EnumFacing rotateByRay(RayTraceResult ray) {
        assert (ray.field_72313_a == RayTraceResult.Type.BLOCK);
        Vec3d hit = ray.field_72307_f;
        BlockPos pos = ray.func_178782_a();
        return RotationUtil.rotateByHit(ray.field_178784_b, (float)hit.field_72450_a - (float)pos.func_177958_n(), (float)hit.field_72448_b - (float)pos.func_177956_o(), (float)hit.field_72449_c - (float)pos.func_177952_p());
    }

    public static EnumFacing rotateByHit(EnumFacing facingHit, float hitX, float hitY, float hitZ) {
        switch (facingHit) {
            case DOWN: {
                if (hitX <= 0.25f) {
                    if (hitZ > 0.25f && hitZ < 0.75f) {
                        return EnumFacing.WEST;
                    }
                    return EnumFacing.UP;
                }
                if (hitX > 0.25f && hitX < 0.75f) {
                    if (hitZ <= 0.25f) {
                        return EnumFacing.NORTH;
                    }
                    if (hitZ >= 0.75f) {
                        return EnumFacing.SOUTH;
                    }
                    return EnumFacing.DOWN;
                }
                if (!(hitX >= 0.75f)) break;
                if (hitZ > 0.25f && hitZ < 0.75f) {
                    return EnumFacing.EAST;
                }
                return EnumFacing.UP;
            }
            case UP: {
                if (hitX <= 0.25f) {
                    if (hitZ > 0.25f && hitZ < 0.75f) {
                        return EnumFacing.WEST;
                    }
                    return EnumFacing.DOWN;
                }
                if (hitX > 0.25f && hitX < 0.75f) {
                    if (hitZ <= 0.25f) {
                        return EnumFacing.NORTH;
                    }
                    if (hitZ >= 0.75f) {
                        return EnumFacing.SOUTH;
                    }
                    return EnumFacing.UP;
                }
                if (!(hitX >= 0.75f)) break;
                if (hitZ > 0.25f && hitZ < 0.75f) {
                    return EnumFacing.EAST;
                }
                return EnumFacing.DOWN;
            }
            case NORTH: {
                if (hitX <= 0.25f) {
                    if (hitY > 0.25f && hitY < 0.75f) {
                        return EnumFacing.WEST;
                    }
                    return EnumFacing.SOUTH;
                }
                if (hitX > 0.25f && hitX < 0.75f) {
                    if (hitY <= 0.25f) {
                        return EnumFacing.DOWN;
                    }
                    if (hitY >= 0.75f) {
                        return EnumFacing.UP;
                    }
                    return EnumFacing.NORTH;
                }
                if (!(hitX >= 0.75f)) break;
                if (hitY > 0.25f && hitY < 0.75f) {
                    return EnumFacing.EAST;
                }
                return EnumFacing.SOUTH;
            }
            case SOUTH: {
                if (hitX <= 0.25f) {
                    if (hitY > 0.25f && hitY < 0.75f) {
                        return EnumFacing.WEST;
                    }
                    return EnumFacing.NORTH;
                }
                if (hitX > 0.25f && hitX < 0.75f) {
                    if (hitY <= 0.25f) {
                        return EnumFacing.DOWN;
                    }
                    if (hitY >= 0.75f) {
                        return EnumFacing.UP;
                    }
                    return EnumFacing.SOUTH;
                }
                if (!(hitX >= 0.75f)) break;
                if (hitY > 0.25f && hitY < 0.75f) {
                    return EnumFacing.EAST;
                }
                return EnumFacing.NORTH;
            }
            case WEST: {
                if (hitZ <= 0.25f) {
                    if (hitY > 0.25f && hitY < 0.75f) {
                        return EnumFacing.NORTH;
                    }
                    return EnumFacing.EAST;
                }
                if (hitZ > 0.25f && hitZ < 0.75f) {
                    if (hitY <= 0.25f) {
                        return EnumFacing.DOWN;
                    }
                    if (hitY >= 0.75f) {
                        return EnumFacing.UP;
                    }
                    return EnumFacing.WEST;
                }
                if (!(hitZ >= 0.75f)) break;
                if (hitY > 0.25f && hitY < 0.75f) {
                    return EnumFacing.SOUTH;
                }
                return EnumFacing.EAST;
            }
            case EAST: {
                if (hitZ <= 0.25f) {
                    if (hitY > 0.25f && hitY < 0.75f) {
                        return EnumFacing.NORTH;
                    }
                    return EnumFacing.WEST;
                }
                if (hitZ > 0.25f && hitZ < 0.75f) {
                    if (hitY <= 0.25f) {
                        return EnumFacing.DOWN;
                    }
                    if (hitY >= 0.75f) {
                        return EnumFacing.UP;
                    }
                    return EnumFacing.EAST;
                }
                if (!(hitZ >= 0.75f)) break;
                if (hitY > 0.25f && hitY < 0.75f) {
                    return EnumFacing.SOUTH;
                }
                return EnumFacing.WEST;
            }
        }
        return facingHit;
    }

    public static int[] shuffledFacings() {
        int[] ordinals = new int[]{0, 1, 2, 3, 4, 5};
        for (int i = ordinals.length - 1; i > 0; --i) {
            int index = IC2.random.nextInt(i + 1);
            if (index == i) continue;
            int n = index;
            ordinals[n] = ordinals[n] ^ ordinals[i];
            int n2 = i;
            ordinals[n2] = ordinals[n2] ^ ordinals[index];
            int n3 = index;
            ordinals[n3] = ordinals[n3] ^ ordinals[i];
        }
        return ordinals;
    }
}

