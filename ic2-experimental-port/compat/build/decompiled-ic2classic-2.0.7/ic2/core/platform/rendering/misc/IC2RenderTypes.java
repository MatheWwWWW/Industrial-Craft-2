/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Quaternion
 *  com.mojang.math.Vector3f
 *  com.mojang.math.Vector4f
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$LineStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TexturingStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 */
package ic2.core.platform.rendering.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import ic2.core.item.misc.WindmillBladeItem;
import java.util.Objects;
import java.util.OptionalDouble;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class IC2RenderTypes
extends RenderType {
    public static final RenderType FLUID_RENDER = IC2RenderTypes.m_173215_((String)"ic2_fluid", (VertexFormat)DefaultVertexFormat.f_85820_, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)131072, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(InventoryMenu.f_39692_, false, false)).m_173292_(f_173108_).m_110671_(f_110152_).m_110685_(f_110139_).m_110675_(f_110125_).m_110691_(true));
    public static final RenderType TUBE_ITEM_HIGHLIGHT = IC2RenderTypes.m_173215_((String)"tube_item_highlight", (VertexFormat)DefaultVertexFormat.f_85815_, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)131072, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173292_(f_173104_).m_110685_(f_110139_).m_110675_(f_110125_).m_110691_(true));
    public static final RenderType BLOCK_POS_TEX = IC2RenderTypes.m_173215_((String)"ic2_pos_texture", (VertexFormat)DefaultVertexFormat.f_85817_, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(InventoryMenu.f_39692_, false, false)).m_110685_(f_110134_).m_110671_(f_110152_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true));
    public static final RenderType POS_COLOR_TRANSLUCENT = IC2RenderTypes.m_173215_((String)"ic2_pos_color", (VertexFormat)DefaultVertexFormat.f_85815_, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173292_(f_173104_).m_110685_(f_110139_).m_110675_(f_110125_).m_110691_(true));
    public static final RenderType WINDMILL_ROTORS = RenderType.m_110452_((ResourceLocation)WindmillBladeItem.ROTOR_SHEET);
    public static final RenderType LINES = IC2RenderTypes.m_173215_((String)"lines_everywhere", (VertexFormat)DefaultVertexFormat.f_166851_, (VertexFormat.Mode)VertexFormat.Mode.LINES, (int)256, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173292_(f_173095_).m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.empty())).m_110669_(f_110119_).m_110685_(f_110139_).m_110675_(f_110129_).m_110687_(f_110114_).m_110663_(f_110111_).m_110661_(f_110110_).m_110691_(false));

    public IC2RenderTypes(String nameIn, VertexFormat formatIn, VertexFormat.Mode drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
        super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
        throw new IllegalStateException("ONLY USED FOR CUSTOM RENDER TYPES");
    }

    public static RenderType getShieldType(ResourceLocation locationIn, float scale, float rotation, float translation) {
        return IC2RenderTypes.m_173215_((String)"energy_shield", (VertexFormat)DefaultVertexFormat.f_85817_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173292_(f_173080_).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(locationIn, true, false)).m_110687_(f_110115_).m_110661_(f_110110_).m_110663_(f_110112_).m_110685_(f_110137_).m_110683_((RenderStateShard.TexturingStateShard)new ShieldState(scale, rotation, translation)).m_110675_(f_110123_).m_110691_(false));
    }

    public static class ShieldState
    extends RenderStateShard.TexturingStateShard {
        float scale;
        float rotation;
        float translation;

        public ShieldState(float scale, float rotation, float translation) {
            super("offset_texturing", () -> {
                RenderSystem.m_157423_();
                Matrix4f matrix = RenderSystem.m_157207_();
                matrix.m_27644_(Matrix4f.m_27632_((float)scale, (float)scale, (float)scale));
                matrix.m_27646_(Vector3f.f_122227_.m_122240_(rotation * 25.0f));
                Vector4f value = new Vector4f(0.0f, translation * (0.001f + rotation * 0.005f) * 20.0f, 0.0f, 0.0f);
                value.m_123609_(new Quaternion(0.0f, 0.0f, rotation * 25.0f, true));
                matrix.m_27644_(Matrix4f.m_27653_((float)value.m_123601_(), (float)value.m_123615_(), (float)value.m_123616_()));
            }, () -> RenderSystem.m_157423_());
            this.scale = scale;
            this.rotation = rotation;
            this.translation = translation;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ShieldState) {
                ShieldState state = (ShieldState)((Object)obj);
                return Float.compare(state.scale, this.scale) == 0 && Float.compare(state.rotation, this.rotation) == 0 && Float.compare(state.translation, this.translation) == 0;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.scale), Float.valueOf(this.rotation), Float.valueOf(this.translation));
        }
    }
}

