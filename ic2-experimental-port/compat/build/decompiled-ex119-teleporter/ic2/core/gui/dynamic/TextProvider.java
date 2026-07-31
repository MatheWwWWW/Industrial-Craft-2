/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 */
package ic2.core.gui.dynamic;

import com.google.common.base.Supplier;
import ic2.core.init.Localization;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.minecraft.network.chat.Component;

public class TextProvider {
    public static ITextProvider of(String string) {
        return string.isEmpty() ? new ConstantEmpty() : new Constant(string);
    }

    public static ITextProvider of(final Supplier<String> supplier) {
        return new AbstractTextProvider(){

            @Override
            public String getRaw(Object object, Map<String, ITextProvider> map) {
                return (String)supplier.get();
            }

            @Override
            public String getConstant(Class<?> clazz) {
                return (String)supplier.get();
            }
        };
    }

    public static ITextProvider of(Component component) {
        return new Translate(new Constant(component.getString()));
    }

    public static ITextProvider ofTranslated(String string) {
        return new Translate(new Constant(string));
    }

    public static ITextProvider parse(String string, Class<?> clazz) {
        Queue<ArrayList<AbstractTextProvider>> queue = Collections.asLifoQueue(new ArrayDeque());
        StringBuilder stringBuilder = new StringBuilder();
        char c = '\u0000';
        List<AbstractTextProvider> list = new ArrayList<AbstractTextProvider>();
        StringBuilder stringBuilder2 = new StringBuilder(string.length());
        boolean bl = false;
        for (int i = 0; i < string.length(); ++i) {
            AbstractTextProvider abstractTextProvider;
            char c2 = string.charAt(i);
            if (bl) {
                stringBuilder2.append(c2);
                bl = false;
                continue;
            }
            if (c2 == '\\') {
                bl = true;
                continue;
            }
            if (c2 == '{') {
                TextProvider.finish(stringBuilder2, list);
                queue.add((ArrayList<AbstractTextProvider>)list);
                stringBuilder.append(c);
                c = c2;
                list = new ArrayList();
                continue;
            }
            if (c == '{' && c2 == ',') {
                TextProvider.finish(stringBuilder2, list);
                list.add(null);
                continue;
            }
            if (c == '{' && c2 == '}') {
                int n;
                TextProvider.finish(stringBuilder2, list);
                abstractTextProvider = null;
                ArrayList<AbstractTextProvider> arrayList = new ArrayList<AbstractTextProvider>();
                for (int j = n = 0; j < list.size(); ++j) {
                    if (list.get(j) != null) continue;
                    AbstractTextProvider abstractTextProvider2 = TextProvider.getProvider(list, n, j);
                    if (abstractTextProvider == null) {
                        abstractTextProvider = abstractTextProvider2;
                    } else {
                        arrayList.add(abstractTextProvider2);
                    }
                    n = j + 1;
                }
                AbstractTextProvider abstractTextProvider3 = TextProvider.getProvider(list, n, list.size());
                if (abstractTextProvider == null) {
                    abstractTextProvider = abstractTextProvider3;
                } else {
                    arrayList.add(abstractTextProvider3);
                }
                abstractTextProvider3 = arrayList.isEmpty() ? new Translate(abstractTextProvider) : new TranslateFormat(abstractTextProvider, arrayList);
                list = (List)queue.remove();
                c = stringBuilder.charAt(stringBuilder.length() - 1);
                stringBuilder.setLength(stringBuilder.length() - 1);
                list.add(abstractTextProvider3);
                continue;
            }
            if (c2 == '%') {
                if (c != '%') {
                    if (i + 1 < string.length() && string.charAt(i + 1) == '%') {
                        stringBuilder2.append('%');
                        ++i;
                        continue;
                    }
                    TextProvider.finish(stringBuilder2, list);
                    queue.add((ArrayList<AbstractTextProvider>)list);
                    stringBuilder.append(c);
                    c = c2;
                    list = new ArrayList();
                    continue;
                }
                TextProvider.finish(stringBuilder2, list);
                abstractTextProvider = TextProvider.getResolver(TextProvider.getProvider(list, 0, list.size()), clazz);
                list = (List)queue.remove();
                c = stringBuilder.charAt(stringBuilder.length() - 1);
                stringBuilder.setLength(stringBuilder.length() - 1);
                list.add(abstractTextProvider);
                continue;
            }
            stringBuilder2.append(c2);
        }
        TextProvider.finish(stringBuilder2, list);
        if (c != '\u0000') {
            return new Constant("ERROR: unfinished token " + c + " in " + string);
        }
        if (bl) {
            return new Constant("ERROR: unfinished escape sequence in " + string);
        }
        return TextProvider.getProvider(list, 0, list.size());
    }

    private static void finish(StringBuilder stringBuilder, List<AbstractTextProvider> list) {
        if (stringBuilder.length() == 0) {
            return;
        }
        list.add(new Constant(stringBuilder.toString()));
        stringBuilder.setLength(0);
    }

    private static AbstractTextProvider getProvider(List<AbstractTextProvider> list, int n, int n2) {
        assert (n <= n2);
        if (n == n2) {
            return new ConstantEmpty();
        }
        if (n + 1 == n2) {
            return list.get(n);
        }
        return new Merge(new ArrayList<AbstractTextProvider>(list.subList(n, n2)));
    }

    private static AbstractTextProvider getResolver(AbstractTextProvider abstractTextProvider, Class<?> clazz) {
        String string = abstractTextProvider.getConstant(clazz);
        if (string == null) {
            return new TokenResolverDynamic(abstractTextProvider);
        }
        String string2 = TextProvider.resolveToken(string, clazz, null, TextProvider.emptyTokens());
        if (string2 != null) {
            return new Constant(string2);
        }
        return new TokenResolverStatic(string);
    }

    private static String resolveToken(String string, Class<?> clazz, Object object, Map<String, ITextProvider> map) {
        ITextProvider iTextProvider = map.get(string);
        if (iTextProvider != null) {
            if (iTextProvider instanceof AbstractTextProvider) {
                return ((AbstractTextProvider)iTextProvider).getRaw(object, map);
            }
            return iTextProvider.get(object, map);
        }
        if (clazz == null) {
            return null;
        }
        if (string.startsWith("base.")) {
            Object object2 = TextProvider.retrieve(string, "base.".length(), clazz, object);
            return TextProvider.toString(object2);
        }
        return null;
    }

    private static Object retrieve(String string, int n, Class<?> clazz, Object object) {
        int n2;
        do {
            String string2;
            if ((n2 = string.indexOf(46, n)) == -1) {
                n2 = string.length();
            }
            if ((string2 = string.substring(n, n2)).endsWith("()")) {
                var6_6 = TextProvider.getMethodOptional(clazz, string2 = string2.substring(0, string2.length() - "()".length()));
                if (var6_6 == null) {
                    return null;
                }
                if ((object = TextProvider.invokeMethodOptional((Method)var6_6, object)) == null) {
                    return null;
                }
                clazz = object.getClass();
            } else {
                var6_6 = TextProvider.getFieldOptional(clazz, string2);
                if (var6_6 == null) {
                    return null;
                }
                if ((object = TextProvider.getFieldValueOptional(var6_6, object)) == null) {
                    return null;
                }
                clazz = object.getClass();
            }
            n = n2 + 1;
        } while (n2 != string.length());
        return object;
    }

    private static Method getMethodOptional(Class<?> clazz, String string) {
        try {
            return clazz.getMethod(string, new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
        catch (SecurityException securityException) {
            throw new RuntimeException(securityException);
        }
    }

    private static Object invokeMethodOptional(Method method, Object object) {
        Object object2;
        if (object == null && !Modifier.isStatic(method.getModifiers())) {
            return null;
        }
        try {
            object2 = method.invoke(object, new Object[0]);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        if (object2 == null) {
            // empty if block
        }
        return object2;
    }

    private static Field getFieldOptional(Class<?> clazz, String string) {
        try {
            return clazz.getField(string);
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return null;
        }
        catch (SecurityException securityException) {
            throw new RuntimeException(securityException);
        }
    }

    private static Object getFieldValueOptional(Field field, Object object) {
        Object object2;
        if (object == null && !Modifier.isStatic(field.getModifiers())) {
            return null;
        }
        try {
            object2 = field.get(object);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        if (object2 == null) {
            // empty if block
        }
        return object2;
    }

    private static String toString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    public static Map<String, ITextProvider> emptyTokens() {
        return Collections.emptyMap();
    }

    private static class ConstantEmpty
    extends AbstractTextProvider {
        private ConstantEmpty() {
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            return "";
        }

        @Override
        public String getConstant(Class<?> clazz) {
            return "";
        }
    }

    private static class Constant
    extends AbstractTextProvider {
        private final String text;

        public Constant(String string) {
            this.text = string;
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            return this.text;
        }

        @Override
        public String getConstant(Class<?> clazz) {
            return this.text;
        }
    }

    private static class Translate
    extends AbstractTextProvider {
        private final AbstractTextProvider key;

        public Translate(AbstractTextProvider abstractTextProvider) {
            this.key = abstractTextProvider;
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            String string = this.key.getRaw(object, map);
            if (string == null) {
                return null;
            }
            return Localization.translate(string);
        }

        @Override
        public String getConstant(Class<?> clazz) {
            return null;
        }
    }

    private static abstract class AbstractTextProvider
    implements ITextProvider {
        private AbstractTextProvider() {
        }

        @Override
        public final String get(Object object, Map<String, ITextProvider> map) {
            String string = this.getRaw(object, map);
            if (string != null) {
                return string;
            }
            return "ERROR";
        }

        @Override
        public final String getOptional(Object object, Map<String, ITextProvider> map) {
            return this.getRaw(object, map);
        }

        protected abstract String getRaw(Object var1, Map<String, ITextProvider> var2);

        protected abstract String getConstant(Class<?> var1);
    }

    private static class TranslateFormat
    extends AbstractTextProvider {
        private final AbstractTextProvider format;
        private final List<AbstractTextProvider> args;

        public TranslateFormat(AbstractTextProvider abstractTextProvider, List<AbstractTextProvider> list) {
            this.format = abstractTextProvider;
            this.args = list;
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            String string = this.format.getRaw(object, map);
            if (string == null) {
                return null;
            }
            Object[] objectArray = new Object[this.args.size()];
            for (int i = 0; i < this.args.size(); ++i) {
                String string2 = this.args.get(i).getRaw(object, map);
                if (string2 == null) {
                    return null;
                }
                objectArray[i] = string2;
            }
            return Localization.translate(string, objectArray);
        }

        @Override
        public String getConstant(Class<?> clazz) {
            return null;
        }
    }

    private static class Merge
    extends AbstractTextProvider {
        private final List<AbstractTextProvider> providers;

        public Merge(List<AbstractTextProvider> list) {
            this.providers = list;
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            StringBuilder stringBuilder = new StringBuilder();
            for (AbstractTextProvider abstractTextProvider : this.providers) {
                String string = abstractTextProvider.getRaw(object, map);
                if (string == null) {
                    return null;
                }
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        }

        @Override
        public String getConstant(Class<?> clazz) {
            StringBuilder stringBuilder = new StringBuilder();
            for (AbstractTextProvider abstractTextProvider : this.providers) {
                String string = abstractTextProvider.getConstant(clazz);
                if (string == null) {
                    return null;
                }
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        }
    }

    private static class TokenResolverDynamic
    extends AbstractTextProvider {
        private final AbstractTextProvider token;

        public TokenResolverDynamic(AbstractTextProvider abstractTextProvider) {
            this.token = abstractTextProvider;
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            String string = this.token.getRaw(object, map);
            if (string == null) {
                return null;
            }
            return TextProvider.resolveToken(string, object != null ? object.getClass() : null, object, map);
        }

        @Override
        public String getConstant(Class<?> clazz) {
            String string = this.token.getConstant(clazz);
            if (string == null) {
                return null;
            }
            return TextProvider.resolveToken(string, clazz, null, TextProvider.emptyTokens());
        }
    }

    private static class TokenResolverStatic
    extends AbstractTextProvider {
        private final String token;

        public TokenResolverStatic(String string) {
            this.token = string;
        }

        @Override
        public String getRaw(Object object, Map<String, ITextProvider> map) {
            return TextProvider.resolveToken(this.token, object != null ? object.getClass() : null, object, map);
        }

        @Override
        public String getConstant(Class<?> clazz) {
            return TextProvider.resolveToken(this.token, clazz, null, TextProvider.emptyTokens());
        }
    }

    public static interface ITextProvider {
        public String get(Object var1, Map<String, ITextProvider> var2);

        public String getOptional(Object var1, Map<String, ITextProvider> var2);
    }
}

