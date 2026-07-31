/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
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

public class TextProvider {
    public static ITextProvider of(String text) {
        return text.isEmpty() ? new ConstantEmpty() : new Constant(text);
    }

    public static ITextProvider of(final Supplier<String> supplier) {
        return new AbstractTextProvider(){

            @Override
            public String getRaw(Object base, Map<String, ITextProvider> tokens) {
                return (String)supplier.get();
            }

            @Override
            public String getConstant(Class<?> baseClass) {
                return (String)supplier.get();
            }
        };
    }

    public static ITextProvider ofTranslated(String key) {
        return new Translate(new Constant(key));
    }

    public static ITextProvider parse(String text, Class<?> baseClass) {
        Queue<ArrayList<AbstractTextProvider>> continuations = Collections.asLifoQueue(new ArrayDeque());
        StringBuilder continuationTypes = new StringBuilder();
        char currentType = '\u0000';
        List<AbstractTextProvider> providers = new ArrayList<AbstractTextProvider>();
        StringBuilder part = new StringBuilder(text.length());
        boolean escaped = false;
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (escaped) {
                part.append(c);
                escaped = false;
                continue;
            }
            if (c == '\\') {
                escaped = true;
                continue;
            }
            if (c == '{') {
                TextProvider.finish(part, providers);
                continuations.add((ArrayList<AbstractTextProvider>)providers);
                continuationTypes.append(currentType);
                currentType = c;
                providers = new ArrayList();
                continue;
            }
            if (currentType == '{' && c == ',') {
                TextProvider.finish(part, providers);
                providers.add(null);
                continue;
            }
            if (currentType == '{' && c == '}') {
                int start;
                TextProvider.finish(part, providers);
                AbstractTextProvider format = null;
                ArrayList<AbstractTextProvider> args = new ArrayList<AbstractTextProvider>();
                for (int j = start = 0; j < providers.size(); ++j) {
                    if (providers.get(j) != null) continue;
                    AbstractTextProvider provider = TextProvider.getProvider(providers, start, j);
                    if (format == null) {
                        format = provider;
                    } else {
                        args.add(provider);
                    }
                    start = j + 1;
                }
                AbstractTextProvider provider = TextProvider.getProvider(providers, start, providers.size());
                if (format == null) {
                    format = provider;
                } else {
                    args.add(provider);
                }
                provider = args.isEmpty() ? new Translate(format) : new TranslateFormat(format, args);
                providers = (List)continuations.remove();
                currentType = continuationTypes.charAt(continuationTypes.length() - 1);
                continuationTypes.setLength(continuationTypes.length() - 1);
                providers.add(provider);
                continue;
            }
            if (c == '%') {
                if (currentType != '%') {
                    if (i + 1 < text.length() && text.charAt(i + 1) == '%') {
                        part.append('%');
                        ++i;
                        continue;
                    }
                    TextProvider.finish(part, providers);
                    continuations.add((ArrayList<AbstractTextProvider>)providers);
                    continuationTypes.append(currentType);
                    currentType = c;
                    providers = new ArrayList();
                    continue;
                }
                TextProvider.finish(part, providers);
                AbstractTextProvider provider = TextProvider.getResolver(TextProvider.getProvider(providers, 0, providers.size()), baseClass);
                providers = (List)continuations.remove();
                currentType = continuationTypes.charAt(continuationTypes.length() - 1);
                continuationTypes.setLength(continuationTypes.length() - 1);
                providers.add(provider);
                continue;
            }
            part.append(c);
        }
        TextProvider.finish(part, providers);
        if (currentType != '\u0000') {
            return new Constant("ERROR: unfinished token " + currentType + " in " + text);
        }
        if (escaped) {
            return new Constant("ERROR: unfinished escape sequence in " + text);
        }
        return TextProvider.getProvider(providers, 0, providers.size());
    }

    private static void finish(StringBuilder part, List<AbstractTextProvider> providers) {
        if (part.length() == 0) {
            return;
        }
        providers.add(new Constant(part.toString()));
        part.setLength(0);
    }

    private static AbstractTextProvider getProvider(List<AbstractTextProvider> providers, int start, int end) {
        assert (start <= end);
        if (start == end) {
            return new ConstantEmpty();
        }
        if (start + 1 == end) {
            return providers.get(start);
        }
        return new Merge(new ArrayList<AbstractTextProvider>(providers.subList(start, end)));
    }

    private static AbstractTextProvider getResolver(AbstractTextProvider token, Class<?> baseClass) {
        String staticToken = token.getConstant(baseClass);
        if (staticToken == null) {
            return new TokenResolverDynamic(token);
        }
        String staticResult = TextProvider.resolveToken(staticToken, baseClass, null, TextProvider.emptyTokens());
        if (staticResult != null) {
            return new Constant(staticResult);
        }
        return new TokenResolverStatic(staticToken);
    }

    private static String resolveToken(String token, Class<?> baseClass, Object base, Map<String, ITextProvider> tokens) {
        ITextProvider ret = tokens.get(token);
        if (ret != null) {
            if (ret instanceof AbstractTextProvider) {
                return ((AbstractTextProvider)ret).getRaw(base, tokens);
            }
            return ret.get(base, tokens);
        }
        if (baseClass == null) {
            return null;
        }
        if (token.startsWith("base.")) {
            Object value = TextProvider.retrieve(token, "base.".length(), baseClass, base);
            return TextProvider.toString(value);
        }
        return null;
    }

    private static Object retrieve(String path, int start, Class<?> subjectClass, Object subject) {
        int end;
        do {
            String part;
            if ((end = path.indexOf(46, start)) == -1) {
                end = path.length();
            }
            if ((part = path.substring(start, end)).endsWith("()")) {
                Method method = TextProvider.getMethodOptional(subjectClass, part = part.substring(0, part.length() - "()".length()));
                if (method == null) {
                    return null;
                }
                if ((subject = TextProvider.invokeMethodOptional(method, subject)) == null) {
                    return null;
                }
                subjectClass = subject.getClass();
            } else {
                Field field = TextProvider.getFieldOptional(subjectClass, part);
                if (field == null) {
                    return null;
                }
                if ((subject = TextProvider.getFieldValueOptional(field, subject)) == null) {
                    return null;
                }
                subjectClass = subject.getClass();
            }
            start = end + 1;
        } while (end != path.length());
        return subject;
    }

    private static Method getMethodOptional(Class<?> cls, String name) {
        try {
            return cls.getMethod(name, new Class[0]);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
        catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object invokeMethodOptional(Method method, Object obj) {
        Object ret;
        if (obj == null && !Modifier.isStatic(method.getModifiers())) {
            return null;
        }
        try {
            ret = method.invoke(obj, new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (ret == null) {
            // empty if block
        }
        return ret;
    }

    private static Field getFieldOptional(Class<?> cls, String name) {
        try {
            return cls.getField(name);
        }
        catch (NoSuchFieldException e) {
            return null;
        }
        catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getFieldValueOptional(Field field, Object obj) {
        Object ret;
        if (obj == null && !Modifier.isStatic(field.getModifiers())) {
            return null;
        }
        try {
            ret = field.get(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (ret == null) {
            // empty if block
        }
        return ret;
    }

    private static String toString(Object o) {
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    public static Map<String, ITextProvider> emptyTokens() {
        return Collections.emptyMap();
    }

    private static class TokenResolverStatic
    extends AbstractTextProvider {
        private final String token;

        public TokenResolverStatic(String token) {
            this.token = token;
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            return TextProvider.resolveToken(this.token, base != null ? base.getClass() : null, base, tokens);
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            return TextProvider.resolveToken(this.token, baseClass, null, TextProvider.emptyTokens());
        }
    }

    private static class TokenResolverDynamic
    extends AbstractTextProvider {
        private final AbstractTextProvider token;

        public TokenResolverDynamic(AbstractTextProvider token) {
            this.token = token;
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            String token = this.token.getRaw(base, tokens);
            if (token == null) {
                return null;
            }
            return TextProvider.resolveToken(token, base != null ? base.getClass() : null, base, tokens);
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            String token = this.token.getConstant(baseClass);
            if (token == null) {
                return null;
            }
            return TextProvider.resolveToken(token, baseClass, null, TextProvider.emptyTokens());
        }
    }

    private static class TranslateFormat
    extends AbstractTextProvider {
        private final AbstractTextProvider format;
        private final List<AbstractTextProvider> args;

        public TranslateFormat(AbstractTextProvider format, List<AbstractTextProvider> args) {
            this.format = format;
            this.args = args;
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            String format = this.format.getRaw(base, tokens);
            if (format == null) {
                return null;
            }
            Object[] cArgs = new Object[this.args.size()];
            for (int i = 0; i < this.args.size(); ++i) {
                String arg = this.args.get(i).getRaw(base, tokens);
                if (arg == null) {
                    return null;
                }
                cArgs[i] = arg;
            }
            return Localization.translate(format, cArgs);
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            return null;
        }
    }

    private static class Translate
    extends AbstractTextProvider {
        private final AbstractTextProvider key;

        public Translate(AbstractTextProvider key) {
            this.key = key;
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            String key = this.key.getRaw(base, tokens);
            if (key == null) {
                return null;
            }
            return Localization.translate(key);
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            return null;
        }
    }

    private static class Merge
    extends AbstractTextProvider {
        private final List<AbstractTextProvider> providers;

        public Merge(List<AbstractTextProvider> providers) {
            this.providers = providers;
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            StringBuilder ret = new StringBuilder();
            for (AbstractTextProvider provider : this.providers) {
                String part = provider.getRaw(base, tokens);
                if (part == null) {
                    return null;
                }
                ret.append(part);
            }
            return ret.toString();
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            StringBuilder ret = new StringBuilder();
            for (AbstractTextProvider provider : this.providers) {
                String part = provider.getConstant(baseClass);
                if (part == null) {
                    return null;
                }
                ret.append(part);
            }
            return ret.toString();
        }
    }

    private static class ConstantEmpty
    extends AbstractTextProvider {
        private ConstantEmpty() {
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            return "";
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            return "";
        }
    }

    private static class Constant
    extends AbstractTextProvider {
        private final String text;

        public Constant(String text) {
            this.text = text;
        }

        @Override
        public String getRaw(Object base, Map<String, ITextProvider> tokens) {
            return this.text;
        }

        @Override
        public String getConstant(Class<?> baseClass) {
            return this.text;
        }
    }

    private static abstract class AbstractTextProvider
    implements ITextProvider {
        private AbstractTextProvider() {
        }

        @Override
        public final String get(Object base, Map<String, ITextProvider> tokens) {
            String result = this.getRaw(base, tokens);
            if (result != null) {
                return result;
            }
            return "ERROR";
        }

        @Override
        public final String getOptional(Object base, Map<String, ITextProvider> tokens) {
            return this.getRaw(base, tokens);
        }

        protected abstract String getRaw(Object var1, Map<String, ITextProvider> var2);

        protected abstract String getConstant(Class<?> var1);
    }

    public static interface ITextProvider {
        public String get(Object var1, Map<String, ITextProvider> var2);

        public String getOptional(Object var1, Map<String, ITextProvider> var2);
    }
}

