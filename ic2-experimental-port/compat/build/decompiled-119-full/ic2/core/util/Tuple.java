/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tuple {
    public static <K, V> List<T2<K, V>> fromMap(Map<K, V> map) {
        ArrayList<T2<K, V>> arrayList = new ArrayList<T2<K, V>>(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            arrayList.add(new T2<K, V>(entry.getKey(), entry.getValue()));
        }
        return arrayList;
    }

    public static class T2<TA, TB> {
        public TA a;
        public TB b;

        public T2(TA TA, TB TB) {
            this.a = TA;
            this.b = TB;
        }
    }

    public static class T3<TA, TB, TC> {
        public TA a;
        public TB b;
        public TC c;

        public T3(TA TA, TB TB, TC TC) {
            this.a = TA;
            this.b = TB;
            this.c = TC;
        }
    }
}

