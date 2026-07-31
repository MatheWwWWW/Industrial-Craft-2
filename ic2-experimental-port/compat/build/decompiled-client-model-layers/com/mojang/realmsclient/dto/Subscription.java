/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import org.slf4j.Logger;

public class Subscription
extends ValueObject {
    private static final Logger f_87669_ = LogUtils.getLogger();
    public long f_87666_;
    public int f_87667_;
    public SubscriptionType f_87668_ = SubscriptionType.NORMAL;

    public static Subscription m_87672_(String p_87673_) {
        Subscription $$1 = new Subscription();
        try {
            JsonParser $$2 = new JsonParser();
            JsonObject $$3 = $$2.parse(p_87673_).getAsJsonObject();
            $$1.f_87666_ = JsonUtils.m_90157_("startDate", $$3, 0L);
            $$1.f_87667_ = JsonUtils.m_90153_("daysLeft", $$3, 0);
            $$1.f_87668_ = Subscription.m_87674_(JsonUtils.m_90161_("subscriptionType", $$3, SubscriptionType.NORMAL.name()));
        }
        catch (Exception $$4) {
            f_87669_.error("Could not parse Subscription: {}", (Object)$$4.getMessage());
        }
        return $$1;
    }

    private static SubscriptionType m_87674_(String p_87675_) {
        try {
            return SubscriptionType.valueOf(p_87675_);
        }
        catch (Exception $$1) {
            return SubscriptionType.NORMAL;
        }
    }

    public static final class SubscriptionType
    extends Enum<SubscriptionType> {
        public static final /* enum */ SubscriptionType NORMAL = new SubscriptionType();
        public static final /* enum */ SubscriptionType RECURRING = new SubscriptionType();
        private static final /* synthetic */ SubscriptionType[] $VALUES;

        public static SubscriptionType[] values() {
            return (SubscriptionType[])$VALUES.clone();
        }

        public static SubscriptionType valueOf(String p_87684_) {
            return Enum.valueOf(SubscriptionType.class, p_87684_);
        }

        private static /* synthetic */ SubscriptionType[] m_167323_() {
            return new SubscriptionType[]{NORMAL, RECURRING};
        }

        static {
            $VALUES = SubscriptionType.m_167323_();
        }
    }
}

