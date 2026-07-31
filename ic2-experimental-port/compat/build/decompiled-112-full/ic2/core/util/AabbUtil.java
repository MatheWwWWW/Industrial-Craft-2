/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package ic2.core.util;

import ic2.core.util.Util;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.mutable.MutableObject;

public class AabbUtil {
    public static EnumFacing getIntersection(Vec3d origin, Vec3d direction, AxisAlignedBB bbox, MutableObject<Vec3d> intersection) {
        EnumFacing intersectingDirection;
        double length = Util.square(direction.field_72450_a) + Util.square(direction.field_72448_b) + Util.square(direction.field_72449_c);
        if (Math.abs(length - 1.0) > 1.0E-5) {
            length = Math.sqrt(length);
            direction = new Vec3d(direction.field_72450_a / length, direction.field_72448_b / length, direction.field_72449_c / length);
        }
        if ((intersectingDirection = AabbUtil.intersects(origin, direction, bbox)) == null) {
            return null;
        }
        Vec3d planeOrigin = direction.field_72450_a < 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c < 0.0 ? new Vec3d(bbox.field_72336_d, bbox.field_72337_e, bbox.field_72334_f) : (direction.field_72450_a < 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c >= 0.0 ? new Vec3d(bbox.field_72336_d, bbox.field_72337_e, bbox.field_72339_c) : (direction.field_72450_a < 0.0 && direction.field_72448_b >= 0.0 && direction.field_72449_c < 0.0 ? new Vec3d(bbox.field_72336_d, bbox.field_72338_b, bbox.field_72334_f) : (direction.field_72450_a < 0.0 && direction.field_72448_b >= 0.0 && direction.field_72449_c >= 0.0 ? new Vec3d(bbox.field_72336_d, bbox.field_72338_b, bbox.field_72339_c) : (direction.field_72450_a >= 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c < 0.0 ? new Vec3d(bbox.field_72340_a, bbox.field_72337_e, bbox.field_72334_f) : (direction.field_72450_a >= 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c >= 0.0 ? new Vec3d(bbox.field_72340_a, bbox.field_72337_e, bbox.field_72339_c) : (direction.field_72450_a >= 0.0 && direction.field_72448_b >= 0.0 && direction.field_72449_c < 0.0 ? new Vec3d(bbox.field_72340_a, bbox.field_72338_b, bbox.field_72334_f) : new Vec3d(bbox.field_72340_a, bbox.field_72338_b, bbox.field_72339_c)))))));
        Vec3d planeNormalVector = null;
        switch (intersectingDirection) {
            case WEST: 
            case EAST: {
                planeNormalVector = new Vec3d(1.0, 0.0, 0.0);
                break;
            }
            case DOWN: 
            case UP: {
                planeNormalVector = new Vec3d(0.0, 1.0, 0.0);
                break;
            }
            case NORTH: 
            case SOUTH: {
                planeNormalVector = new Vec3d(0.0, 0.0, 1.0);
            }
        }
        if (intersection != null) {
            intersection.setValue((Object)AabbUtil.getIntersectionWithPlane(origin, direction, planeOrigin, planeNormalVector));
        }
        return intersectingDirection;
    }

    public static EnumFacing intersects(Vec3d origin, Vec3d direction, AxisAlignedBB bbox) {
        double[] ray = AabbUtil.getRay(origin, direction);
        if (direction.field_72450_a < 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c < 0.0) {
            if (origin.field_72450_a < bbox.field_72340_a) {
                return null;
            }
            if (origin.field_72448_b < bbox.field_72338_b) {
                return null;
            }
            if (origin.field_72449_c < bbox.field_72339_c) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EF, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EH, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DH, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DC, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BC, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BF, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.HG, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.FG, bbox)) < 0.0) {
                return EnumFacing.SOUTH;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.CG, bbox)) < 0.0) {
                return EnumFacing.UP;
            }
            return EnumFacing.EAST;
        }
        if (direction.field_72450_a < 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c >= 0.0) {
            if (origin.field_72450_a < bbox.field_72340_a) {
                return null;
            }
            if (origin.field_72448_b < bbox.field_72338_b) {
                return null;
            }
            if (origin.field_72449_c > bbox.field_72334_f) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.HG, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DH, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AD, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AB, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BF, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.FG, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DC, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.CG, bbox)) > 0.0) {
                return EnumFacing.EAST;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BC, bbox)) < 0.0) {
                return EnumFacing.UP;
            }
            return EnumFacing.NORTH;
        }
        if (direction.field_72450_a < 0.0 && direction.field_72448_b >= 0.0 && direction.field_72449_c < 0.0) {
            if (origin.field_72450_a < bbox.field_72340_a) {
                return null;
            }
            if (origin.field_72448_b > bbox.field_72337_e) {
                return null;
            }
            if (origin.field_72449_c < bbox.field_72339_c) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.FG, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EF, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AE, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AD, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DC, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.CG, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EH, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.HG, bbox)) > 0.0) {
                return EnumFacing.SOUTH;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DH, bbox)) < 0.0) {
                return EnumFacing.EAST;
            }
            return EnumFacing.DOWN;
        }
        if (direction.field_72450_a < 0.0 && direction.field_72448_b >= 0.0 && direction.field_72449_c >= 0.0) {
            if (origin.field_72450_a < bbox.field_72340_a) {
                return null;
            }
            if (origin.field_72448_b > bbox.field_72337_e) {
                return null;
            }
            if (origin.field_72449_c > bbox.field_72334_f) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EH, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AE, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AB, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BC, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.CG, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.HG, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AD, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DH, bbox)) > 0.0) {
                return EnumFacing.DOWN;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DC, bbox)) < 0.0) {
                return EnumFacing.NORTH;
            }
            return EnumFacing.EAST;
        }
        if (direction.field_72450_a >= 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c < 0.0) {
            if (origin.field_72450_a > bbox.field_72336_d) {
                return null;
            }
            if (origin.field_72448_b < bbox.field_72338_b) {
                return null;
            }
            if (origin.field_72449_c < bbox.field_72339_c) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AB, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AE, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EH, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.HG, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.CG, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BC, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EF, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BF, bbox)) < 0.0) {
                return EnumFacing.WEST;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.FG, bbox)) < 0.0) {
                return EnumFacing.SOUTH;
            }
            return EnumFacing.UP;
        }
        if (direction.field_72450_a >= 0.0 && direction.field_72448_b < 0.0 && direction.field_72449_c >= 0.0) {
            if (origin.field_72450_a > bbox.field_72336_d) {
                return null;
            }
            if (origin.field_72448_b < bbox.field_72338_b) {
                return null;
            }
            if (origin.field_72449_c > bbox.field_72334_f) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DC, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AD, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AE, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EF, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.FG, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.CG, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AB, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BC, bbox)) > 0.0) {
                return EnumFacing.NORTH;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BF, bbox)) < 0.0) {
                return EnumFacing.WEST;
            }
            return EnumFacing.UP;
        }
        if (direction.field_72450_a >= 0.0 && direction.field_72448_b >= 0.0 && direction.field_72449_c < 0.0) {
            if (origin.field_72450_a > bbox.field_72336_d) {
                return null;
            }
            if (origin.field_72448_b > bbox.field_72337_e) {
                return null;
            }
            if (origin.field_72449_c < bbox.field_72339_c) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BF, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AB, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AD, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DH, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.HG, bbox)) < 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.FG, bbox)) > 0.0) {
                return null;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AE, bbox)) > 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EF, bbox)) > 0.0) {
                return EnumFacing.WEST;
            }
            if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EH, bbox)) < 0.0) {
                return EnumFacing.DOWN;
            }
            return EnumFacing.SOUTH;
        }
        if (origin.field_72450_a > bbox.field_72336_d) {
            return null;
        }
        if (origin.field_72448_b > bbox.field_72337_e) {
            return null;
        }
        if (origin.field_72449_c > bbox.field_72334_f) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EF, bbox)) < 0.0) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.EH, bbox)) > 0.0) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DH, bbox)) < 0.0) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.DC, bbox)) > 0.0) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BC, bbox)) < 0.0) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.BF, bbox)) > 0.0) {
            return null;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AB, bbox)) < 0.0 && AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AE, bbox)) > 0.0) {
            return EnumFacing.WEST;
        }
        if (AabbUtil.side(ray, AabbUtil.getEdgeRay(Edge.AD, bbox)) < 0.0) {
            return EnumFacing.NORTH;
        }
        return EnumFacing.DOWN;
    }

    private static double[] getRay(Vec3d origin, Vec3d direction) {
        double[] ret = new double[]{origin.field_72450_a * direction.field_72448_b - direction.field_72450_a * origin.field_72448_b, origin.field_72450_a * direction.field_72449_c - direction.field_72450_a * origin.field_72449_c, -direction.field_72450_a, origin.field_72448_b * direction.field_72449_c - direction.field_72448_b * origin.field_72449_c, -direction.field_72449_c, direction.field_72448_b};
        return ret;
    }

    private static double[] getEdgeRay(Edge edge, AxisAlignedBB bbox) {
        switch (edge) {
            case AD: {
                return new double[]{-bbox.field_72338_b, -bbox.field_72339_c, -1.0, 0.0, 0.0, 0.0};
            }
            case AB: {
                return new double[]{bbox.field_72340_a, 0.0, 0.0, -bbox.field_72339_c, 0.0, 1.0};
            }
            case AE: {
                return new double[]{0.0, bbox.field_72340_a, 0.0, bbox.field_72338_b, -1.0, 0.0};
            }
            case DC: {
                return new double[]{bbox.field_72336_d, 0.0, 0.0, -bbox.field_72339_c, 0.0, 1.0};
            }
            case DH: {
                return new double[]{0.0, bbox.field_72336_d, 0.0, bbox.field_72338_b, -1.0, 0.0};
            }
            case BC: {
                return new double[]{-bbox.field_72337_e, -bbox.field_72339_c, -1.0, 0.0, 0.0, 0.0};
            }
            case BF: {
                return new double[]{0.0, bbox.field_72340_a, 0.0, bbox.field_72337_e, -1.0, 0.0};
            }
            case EH: {
                return new double[]{-bbox.field_72338_b, -bbox.field_72334_f, -1.0, 0.0, 0.0, 0.0};
            }
            case EF: {
                return new double[]{bbox.field_72340_a, 0.0, 0.0, -bbox.field_72334_f, 0.0, 1.0};
            }
            case CG: {
                return new double[]{0.0, bbox.field_72336_d, 0.0, bbox.field_72337_e, -1.0, 0.0};
            }
            case FG: {
                return new double[]{-bbox.field_72337_e, -bbox.field_72334_f, -1.0, 0.0, 0.0, 0.0};
            }
            case HG: {
                return new double[]{bbox.field_72336_d, 0.0, 0.0, -bbox.field_72334_f, 0.0, 1.0};
            }
        }
        return new double[0];
    }

    private static double side(double[] ray1, double[] ray2) {
        return ray1[2] * ray2[3] + ray1[5] * ray2[1] + ray1[4] * ray2[0] + ray1[1] * ray2[5] + ray1[0] * ray2[4] + ray1[3] * ray2[2];
    }

    private static Vec3d getIntersectionWithPlane(Vec3d origin, Vec3d direction, Vec3d planeOrigin, Vec3d planeNormalVector) {
        double distance = AabbUtil.getDistanceToPlane(origin, direction, planeOrigin, planeNormalVector);
        return new Vec3d(origin.field_72450_a + direction.field_72450_a * distance, origin.field_72448_b + direction.field_72448_b * distance, origin.field_72449_c + direction.field_72449_c * distance);
    }

    private static double getDistanceToPlane(Vec3d origin, Vec3d direction, Vec3d planeOrigin, Vec3d planeNormalVector) {
        Vec3d base = new Vec3d(planeOrigin.field_72450_a - origin.field_72450_a, planeOrigin.field_72448_b - origin.field_72448_b, planeOrigin.field_72449_c - origin.field_72449_c);
        return AabbUtil.dotProduct(base, planeNormalVector) / AabbUtil.dotProduct(direction, planeNormalVector);
    }

    private static double dotProduct(Vec3d a, Vec3d b) {
        return a.field_72450_a * b.field_72450_a + a.field_72448_b * b.field_72448_b + a.field_72449_c * b.field_72449_c;
    }

    static enum Edge {
        AD,
        AB,
        AE,
        DC,
        DH,
        BC,
        BF,
        EH,
        EF,
        CG,
        FG,
        HG;

    }
}

