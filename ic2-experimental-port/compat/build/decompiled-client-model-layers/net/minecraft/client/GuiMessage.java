/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import javax.annotation.Nullable;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.FormattedCharSequence;

public record GuiMessage(int f_90786_, Component f_240363_, @Nullable MessageSignature f_240905_, @Nullable GuiMessageTag f_240352_) {
    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{GuiMessage.class, "addedTime;content;headerSignature;tag", "f_90786_", "f_240363_", "f_240905_", "f_240352_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{GuiMessage.class, "addedTime;content;headerSignature;tag", "f_90786_", "f_240363_", "f_240905_", "f_240352_"}, this);
    }

    @Override
    public final boolean equals(Object p_240638_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{GuiMessage.class, "addedTime;content;headerSignature;tag", "f_90786_", "f_240363_", "f_240905_", "f_240352_"}, this, p_240638_);
    }

    public record Line(int f_240350_, FormattedCharSequence f_240339_, @Nullable GuiMessageTag f_240351_, boolean f_240367_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Line.class, "addedTime;content;tag;endOfEntry", "f_240350_", "f_240339_", "f_240351_", "f_240367_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Line.class, "addedTime;content;tag;endOfEntry", "f_240350_", "f_240339_", "f_240351_", "f_240367_"}, this);
        }

        @Override
        public final boolean equals(Object p_240535_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Line.class, "addedTime;content;tag;endOfEntry", "f_240350_", "f_240339_", "f_240351_", "f_240367_"}, this, p_240535_);
        }
    }
}

