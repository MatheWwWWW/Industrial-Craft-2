/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraft.util.math.Vec3d
 */
package ic2.core;

import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class EnhancedOverlay {
    private static final Map<EnumFacing, EnhancedOverlay> SIDE_TO_OVERLAY = new EnumMap<EnumFacing, EnhancedOverlay>(EnumFacing.class);
    private final Map<Segment, RawSegment> segmentMap;

    private EnhancedOverlay(Map<Segment, RawSegment> segmentMap) {
        this.segmentMap = segmentMap;
    }

    public static EnhancedOverlay forFace(EnumFacing face) {
        return SIDE_TO_OVERLAY.get(face);
    }

    public static void transformToFace(Entity entity, BlockPos pos, EnumFacing face, float partialTicks) {
        GlStateManager.func_179137_b((double)(-(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)partialTicks)), (double)(-(entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)partialTicks)), (double)(-(entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)partialTicks)));
        GlStateManager.func_179109_b((float)((float)pos.func_177958_n() + 0.5f), (float)((float)pos.func_177956_o() + 0.5f), (float)((float)pos.func_177952_p() + 0.5f));
        switch (face) {
            case UP: {
                GlStateManager.func_179114_b((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case DOWN: {
                break;
            }
            case NORTH: {
                GlStateManager.func_179114_b((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case SOUTH: {
                GlStateManager.func_179114_b((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case EAST: {
                GlStateManager.func_179114_b((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                break;
            }
            case WEST: {
                GlStateManager.func_179114_b((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
        }
        GlStateManager.func_179137_b((double)0.0, (double)-0.501, (double)0.0);
    }

    public static void drawArea(EnumFacing face, Segment ... segments) {
        EnhancedOverlay overlay = EnhancedOverlay.forFace(face);
        BufferBuilder buffer = Tessellator.func_178181_a().func_178180_c();
        for (Segment segment : segments) {
            overlay.drawArea(segment, buffer);
        }
    }

    public static void drawDebug(EnumFacing face) {
        EnhancedOverlay overlay = EnhancedOverlay.forFace(face);
        BufferBuilder buffer = Tessellator.func_178181_a().func_178180_c();
        GlStateManager.func_179090_x();
        overlay.drawArea(Segment.TOP_LEFT, buffer, 255, 0, 0);
        overlay.drawArea(Segment.TOP, buffer, 255, 127, 0);
        overlay.drawArea(Segment.TOP_RIGHT, buffer, 255, 255, 0);
        overlay.drawArea(Segment.LEFT, buffer, 0, 255, 0);
        overlay.drawArea(Segment.CENTRE, buffer, 0, 255, 127);
        overlay.drawArea(Segment.RIGHT, buffer, 0, 255, 255);
        overlay.drawArea(Segment.BOTTOM_LEFT, buffer, 0, 0, 255);
        overlay.drawArea(Segment.BOTTOM, buffer, 127, 0, 255);
        overlay.drawArea(Segment.BOTTOM_RIGHT, buffer, 255, 0, 255);
        GlStateManager.func_179098_w();
    }

    public void drawLines(Segment segment, BufferBuilder buffer) {
        buffer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
        this.segmentMap.get((Object)segment).draw(buffer);
        Tessellator.func_178181_a().func_78381_a();
    }

    public void drawLines(Segment segment, BufferBuilder buffer, int red, int green, int blue) {
        this.drawLines(segment, buffer, red, green, blue, 255);
    }

    public void drawLines(Segment segment, BufferBuilder buffer, int red, int green, int blue, int alpha) {
        buffer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        this.segmentMap.get((Object)segment).drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181669_b(red, green, blue, alpha).func_181675_d());
        Tessellator.func_178181_a().func_78381_a();
    }

    public void drawArea(Segment segment, BufferBuilder buffer) {
        buffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        this.segmentMap.get((Object)segment).draw(buffer);
        Tessellator.func_178181_a().func_78381_a();
    }

    public void drawArea(Segment segment, BufferBuilder buffer, int red, int green, int blue) {
        this.drawArea(segment, buffer, red, green, blue, 127);
    }

    public void drawArea(Segment segment, BufferBuilder buffer, int red, int green, int blue, int alpha) {
        buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        this.segmentMap.get((Object)segment).drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181669_b(red, green, blue, alpha).func_181675_d());
        Tessellator.func_178181_a().func_78381_a();
    }

    public void drawSide(BufferBuilder buffer, int red, int green, int blue) {
        this.drawSide(buffer, red, green, blue, 127);
    }

    public void drawSide(BufferBuilder buffer, int red, int green, int blue, int alpha) {
        buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        buffer.func_181662_b(0.5, 0.0, -0.5).func_181669_b(red, green, blue, alpha).func_181675_d();
        buffer.func_181662_b(-0.5, 0.0, -0.5).func_181669_b(red, green, blue, alpha).func_181675_d();
        buffer.func_181662_b(-0.5, 0.0, 0.5).func_181669_b(red, green, blue, alpha).func_181675_d();
        buffer.func_181662_b(0.5, 0.0, 0.5).func_181669_b(red, green, blue, alpha).func_181675_d();
        Tessellator.func_178181_a().func_78381_a();
    }

    static {
        EnumMap<Segment, RawSegment> segmentMap = new EnumMap<Segment, RawSegment>(Segment.class);
        SIDE_TO_OVERLAY.put(EnumFacing.DOWN, new EnhancedOverlay(segmentMap));
        segmentMap.put(Segment.TOP_LEFT, RawSegment.C_BOX);
        segmentMap.put(Segment.TOP, RawSegment.B_BAR);
        segmentMap.put(Segment.TOP_RIGHT, RawSegment.A_BOX);
        segmentMap.put(Segment.LEFT, RawSegment.F_BAR);
        segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
        segmentMap.put(Segment.RIGHT, RawSegment.D_BAR);
        segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.I_BOX);
        segmentMap.put(Segment.BOTTOM, RawSegment.H_BAR);
        segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.G_BOX);
        segmentMap = new EnumMap(Segment.class);
        SIDE_TO_OVERLAY.put(EnumFacing.UP, new EnhancedOverlay(segmentMap));
        segmentMap.put(Segment.TOP_LEFT, RawSegment.I_BOX);
        segmentMap.put(Segment.TOP, RawSegment.H_BAR);
        segmentMap.put(Segment.TOP_RIGHT, RawSegment.G_BOX);
        segmentMap.put(Segment.LEFT, RawSegment.F_BAR);
        segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
        segmentMap.put(Segment.RIGHT, RawSegment.D_BAR);
        segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.C_BOX);
        segmentMap.put(Segment.BOTTOM, RawSegment.B_BAR);
        segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.A_BOX);
        segmentMap = new EnumMap(Segment.class);
        SIDE_TO_OVERLAY.put(EnumFacing.NORTH, new EnhancedOverlay(segmentMap));
        segmentMap.put(Segment.TOP_LEFT, RawSegment.A_BOX);
        segmentMap.put(Segment.TOP, RawSegment.B_BAR);
        segmentMap.put(Segment.TOP_RIGHT, RawSegment.C_BOX);
        segmentMap.put(Segment.LEFT, RawSegment.D_BAR);
        segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
        segmentMap.put(Segment.RIGHT, RawSegment.F_BAR);
        segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.G_BOX);
        segmentMap.put(Segment.BOTTOM, RawSegment.H_BAR);
        segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.I_BOX);
        segmentMap = new EnumMap(Segment.class);
        SIDE_TO_OVERLAY.put(EnumFacing.SOUTH, new EnhancedOverlay(segmentMap));
        segmentMap.put(Segment.TOP_LEFT, RawSegment.I_BOX);
        segmentMap.put(Segment.TOP, RawSegment.H_BAR);
        segmentMap.put(Segment.TOP_RIGHT, RawSegment.G_BOX);
        segmentMap.put(Segment.LEFT, RawSegment.F_BAR);
        segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
        segmentMap.put(Segment.RIGHT, RawSegment.D_BAR);
        segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.C_BOX);
        segmentMap.put(Segment.BOTTOM, RawSegment.B_BAR);
        segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.A_BOX);
        segmentMap = new EnumMap(Segment.class);
        SIDE_TO_OVERLAY.put(EnumFacing.WEST, new EnhancedOverlay(segmentMap));
        segmentMap.put(Segment.TOP_LEFT, RawSegment.C_BOX);
        segmentMap.put(Segment.TOP, RawSegment.F_BAR);
        segmentMap.put(Segment.TOP_RIGHT, RawSegment.I_BOX);
        segmentMap.put(Segment.LEFT, RawSegment.B_BAR);
        segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
        segmentMap.put(Segment.RIGHT, RawSegment.H_BAR);
        segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.A_BOX);
        segmentMap.put(Segment.BOTTOM, RawSegment.D_BAR);
        segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.G_BOX);
        segmentMap = new EnumMap(Segment.class);
        SIDE_TO_OVERLAY.put(EnumFacing.EAST, new EnhancedOverlay(segmentMap));
        segmentMap.put(Segment.TOP_LEFT, RawSegment.G_BOX);
        segmentMap.put(Segment.TOP, RawSegment.D_BAR);
        segmentMap.put(Segment.TOP_RIGHT, RawSegment.A_BOX);
        segmentMap.put(Segment.LEFT, RawSegment.H_BAR);
        segmentMap.put(Segment.CENTRE, RawSegment.E_CENTRE);
        segmentMap.put(Segment.RIGHT, RawSegment.B_BAR);
        segmentMap.put(Segment.BOTTOM_LEFT, RawSegment.I_BOX);
        segmentMap.put(Segment.BOTTOM, RawSegment.F_BAR);
        segmentMap.put(Segment.BOTTOM_RIGHT, RawSegment.C_BOX);
    }

    private static enum XSegment {
        A_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(0.5, 0.0, -0.5);
                buffer.accept(0.25, 0.0, -0.5);
                buffer.accept(0.5, 0.0, -0.25);
            }
        }
        ,
        B_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.25, 0.0, -0.25);
                buffer.accept(0.25, 0.0, -0.5);
                buffer.accept(-0.25, 0.0, -0.5);
                buffer.accept(0.25, 0.0, -0.25);
            }
        }
        ,
        C_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.5, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, -0.5);
                buffer.accept(-0.5, 0.0, -0.5);
                buffer.accept(-0.25, 0.0, -0.25);
            }
        }
        ,
        D_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.25, 0.0, 0.25);
                buffer.accept(0.5, 0.0, -0.25);
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(0.5, 0.0, 0.25);
            }
        }
        ,
        E_CENTRE{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.25, 0.0, 0.25);
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, -0.25);
                buffer.accept(0.25, 0.0, 0.25);
            }
        }
        ,
        F_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.5, 0.0, 0.25);
                buffer.accept(-0.25, 0.0, -0.25);
                buffer.accept(-0.5, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, 0.25);
            }
        }
        ,
        G_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.25, 0.0, 0.5);
                buffer.accept(0.5, 0.0, 0.25);
                buffer.accept(0.25, 0.0, 0.25);
                buffer.accept(0.5, 0.0, 0.5);
            }
        }
        ,
        H_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.25, 0.0, 0.5);
                buffer.accept(0.25, 0.0, 0.25);
                buffer.accept(-0.25, 0.0, 0.25);
                buffer.accept(0.25, 0.0, 0.5);
            }
        }
        ,
        I_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.5, 0.0, 0.5);
                buffer.accept(-0.25, 0.0, 0.25);
                buffer.accept(-0.5, 0.0, 0.25);
                buffer.accept(-0.25, 0.0, 0.5);
            }
        };


        abstract void drawRaw(TripleDoubleConsumer var1);

        void draw(BufferBuilder buffer) {
            this.drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181675_d());
        }
    }

    private static enum RawSegment {
        A_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.5, 0.0, -0.5);
                buffer.accept(0.25, 0.0, -0.5);
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(0.5, 0.0, -0.25);
            }
        }
        ,
        B_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.25, 0.0, -0.5);
                buffer.accept(0.25, 0.0, -0.5);
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, -0.25);
            }
        }
        ,
        C_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.5, 0.0, -0.5);
                buffer.accept(-0.25, 0.0, -0.5);
                buffer.accept(-0.25, 0.0, -0.25);
                buffer.accept(-0.5, 0.0, -0.25);
            }
        }
        ,
        D_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.5, 0.0, -0.25);
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(0.25, 0.0, 0.25);
                buffer.accept(0.5, 0.0, 0.25);
            }
        }
        ,
        E_CENTRE{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.25, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, 0.25);
                buffer.accept(0.25, 0.0, 0.25);
            }
        }
        ,
        F_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.5, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, -0.25);
                buffer.accept(-0.25, 0.0, 0.25);
                buffer.accept(-0.5, 0.0, 0.25);
            }
        }
        ,
        G_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(0.5, 0.0, 0.5);
                buffer.accept(0.25, 0.0, 0.5);
                buffer.accept(0.25, 0.0, 0.25);
                buffer.accept(0.5, 0.0, 0.25);
            }
        }
        ,
        H_BAR{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.25, 0.0, 0.5);
                buffer.accept(0.25, 0.0, 0.5);
                buffer.accept(0.25, 0.0, 0.25);
                buffer.accept(-0.25, 0.0, 0.25);
            }
        }
        ,
        I_BOX{

            @Override
            void drawRaw(TripleDoubleConsumer buffer) {
                buffer.accept(-0.5, 0.0, 0.5);
                buffer.accept(-0.25, 0.0, 0.5);
                buffer.accept(-0.25, 0.0, 0.25);
                buffer.accept(-0.5, 0.0, 0.25);
            }
        };


        abstract void drawRaw(TripleDoubleConsumer var1);

        void draw(BufferBuilder buffer) {
            this.drawRaw((x, y, z) -> buffer.func_181662_b(x, y, z).func_181675_d());
        }
    }

    @FunctionalInterface
    private static interface TripleDoubleConsumer {
        public void accept(double var1, double var3, double var5);
    }

    public static final class Segment
    extends Enum<Segment> {
        public static final /* enum */ Segment TOP_LEFT = new Segment();
        public static final /* enum */ Segment TOP = new Segment();
        public static final /* enum */ Segment TOP_RIGHT = new Segment();
        public static final /* enum */ Segment LEFT = new Segment();
        public static final /* enum */ Segment CENTRE = new Segment();
        public static final /* enum */ Segment RIGHT = new Segment();
        public static final /* enum */ Segment BOTTOM_LEFT = new Segment();
        public static final /* enum */ Segment BOTTOM = new Segment();
        public static final /* enum */ Segment BOTTOM_RIGHT = new Segment();
        private static final /* synthetic */ Segment[] $VALUES;

        public static Segment[] values() {
            return (Segment[])$VALUES.clone();
        }

        public static Segment valueOf(String name) {
            return Enum.valueOf(Segment.class, name);
        }

        public static Segment forRayTrace(RayTraceResult ray) {
            assert (ray.field_72313_a == RayTraceResult.Type.BLOCK);
            BlockPos pos = ray.func_178782_a();
            Vec3d hit = ray.field_72307_f;
            return Segment.forHit(ray.field_178784_b, hit.field_72450_a - (double)pos.func_177958_n(), hit.field_72448_b - (double)pos.func_177956_o(), hit.field_72449_c - (double)pos.func_177952_p());
        }

        public static Segment forHit(EnumFacing face, double x, double y, double z) {
            switch (face) {
                case DOWN: 
                case UP: {
                    return Segment.forHit(x, 1.0 - z);
                }
                case NORTH: {
                    return Segment.forHit(1.0 - x, y);
                }
                case SOUTH: {
                    return Segment.forHit(x, y);
                }
                case WEST: {
                    return Segment.forHit(z, y);
                }
                case EAST: {
                    return Segment.forHit(1.0 - z, y);
                }
            }
            throw new IllegalArgumentException("Unexpected face: " + face);
        }

        public static Segment forHit(double hitX, double hitY) {
            if (hitX <= 0.25) {
                if (hitY <= 0.25) {
                    return BOTTOM_LEFT;
                }
                if (hitY >= 0.75) {
                    return TOP_LEFT;
                }
                return LEFT;
            }
            if (hitX > 0.25 && hitX < 0.75) {
                if (hitY <= 0.25) {
                    return BOTTOM;
                }
                if (hitY >= 0.75) {
                    return TOP;
                }
                return CENTRE;
            }
            if (hitX >= 0.75) {
                if (hitY <= 0.25) {
                    return BOTTOM_RIGHT;
                }
                if (hitY >= 0.75) {
                    return TOP_RIGHT;
                }
                return RIGHT;
            }
            throw new IllegalArgumentException("Unexpected hit values: [" + hitX + ", " + hitY + ']');
        }

        static {
            $VALUES = new Segment[]{TOP_LEFT, TOP, TOP_RIGHT, LEFT, CENTRE, RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT};
        }
    }
}

