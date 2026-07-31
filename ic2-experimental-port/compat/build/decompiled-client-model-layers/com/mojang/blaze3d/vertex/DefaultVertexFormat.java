/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

public class DefaultVertexFormat {
    public static final VertexFormatElement f_85804_ = new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.POSITION, 3);
    public static final VertexFormatElement f_85805_ = new VertexFormatElement(0, VertexFormatElement.Type.UBYTE, VertexFormatElement.Usage.COLOR, 4);
    public static final VertexFormatElement f_85806_ = new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.UV, 2);
    public static final VertexFormatElement f_85807_ = new VertexFormatElement(1, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.UV, 2);
    public static final VertexFormatElement f_85808_ = new VertexFormatElement(2, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.UV, 2);
    public static final VertexFormatElement f_85809_ = new VertexFormatElement(0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.NORMAL, 3);
    public static final VertexFormatElement f_85810_ = new VertexFormatElement(0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 1);
    public static final VertexFormatElement f_166849_ = f_85806_;
    public static final VertexFormat f_166850_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"UV", (Object)f_166849_).put((Object)"Color", (Object)f_85805_).build());
    public static final VertexFormat f_85811_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).put((Object)"UV0", (Object)f_85806_).put((Object)"UV2", (Object)f_85808_).put((Object)"Normal", (Object)f_85809_).put((Object)"Padding", (Object)f_85810_).build());
    public static final VertexFormat f_85812_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).put((Object)"UV0", (Object)f_85806_).put((Object)"UV1", (Object)f_85807_).put((Object)"UV2", (Object)f_85808_).put((Object)"Normal", (Object)f_85809_).put((Object)"Padding", (Object)f_85810_).build());
    public static final VertexFormat f_85813_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"UV0", (Object)f_85806_).put((Object)"Color", (Object)f_85805_).put((Object)"UV2", (Object)f_85808_).build());
    public static final VertexFormat f_85814_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).build());
    public static final VertexFormat f_85815_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).build());
    public static final VertexFormat f_166851_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).put((Object)"Normal", (Object)f_85809_).put((Object)"Padding", (Object)f_85810_).build());
    public static final VertexFormat f_85816_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).put((Object)"UV2", (Object)f_85808_).build());
    public static final VertexFormat f_85817_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"UV0", (Object)f_85806_).build());
    public static final VertexFormat f_85818_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).put((Object)"UV0", (Object)f_85806_).build());
    public static final VertexFormat f_85819_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"UV0", (Object)f_85806_).put((Object)"Color", (Object)f_85805_).build());
    public static final VertexFormat f_85820_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"Color", (Object)f_85805_).put((Object)"UV0", (Object)f_85806_).put((Object)"UV2", (Object)f_85808_).build());
    public static final VertexFormat f_85821_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"UV0", (Object)f_85806_).put((Object)"UV2", (Object)f_85808_).put((Object)"Color", (Object)f_85805_).build());
    public static final VertexFormat f_85822_ = new VertexFormat((ImmutableMap<String, VertexFormatElement>)ImmutableMap.builder().put((Object)"Position", (Object)f_85804_).put((Object)"UV0", (Object)f_85806_).put((Object)"Color", (Object)f_85805_).put((Object)"Normal", (Object)f_85809_).put((Object)"Padding", (Object)f_85810_).build());
}

