package dev.triskele.common.environment;

import com.google.common.collect.Maps;
import com.google.inject.Binder;
import dev.triskele.common.environment.annotation.EnvironmentPath;
import io.lumine.mythic.utils.lib.lang3.Validate;
import javassist.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GuiceEnvironmentProvider implements EnvironmentProvider {

    public static final Map<Class<?>, Function<String, Object>> PRIMITIVE_TO_FUNCTION = Maps.newHashMap();
    private static final Map<Class<?>, Object> CLASS_TO_ENVIRONMENT = Maps.newHashMap();

    static {
        PRIMITIVE_TO_FUNCTION.put(int.class, Integer::valueOf);
        PRIMITIVE_TO_FUNCTION.put(short.class, Short::valueOf);
        PRIMITIVE_TO_FUNCTION.put(long.class, Long::valueOf);
        PRIMITIVE_TO_FUNCTION.put(double.class, Long::valueOf);
        PRIMITIVE_TO_FUNCTION.put(boolean.class, Boolean::valueOf);
        PRIMITIVE_TO_FUNCTION.put(byte.class, Byte::valueOf);
    }

    private final Binder binder;
    private final ClassPool classPool;

    public GuiceEnvironmentProvider(final @NotNull Binder binder) {
        Validate.notNull(binder, "Guice Binder cannot be null.");

        this.binder = binder;
        this.classPool = ClassPool.getDefault();

        this.classPool.appendClassPath(new ClassClassPath(this.getClass()));
    }

    @Override
    @NotNull
    @SuppressWarnings(value = "unchecked")
    public <T> Optional<T> getEnvironment(@NotNull Class<T> environmentClass) {
        T type = (T) CLASS_TO_ENVIRONMENT.computeIfAbsent(environmentClass, env -> {
            T environment;

            try {
                this.classPool.appendClassPath(new ClassClassPath(environmentClass));

                CtClass ctClass = this.classPool.makeClass(
                        environmentClass.getPackageName() + environmentClass.getName() + "Impl"
                );
                ctClass.addInterface(this.classPool.getCtClass(environmentClass.getName()));

                for (Method method : environmentClass.getDeclaredMethods()) {
                    if (!method.isAnnotationPresent(EnvironmentPath.class)) {
                        continue;
                    }

                    EnvironmentPath path = method.getAnnotation(EnvironmentPath.class);
                    String rawMethod = "public " + method.getReturnType().getSimpleName() + " " + method.getName() + "() {" +
                            "String rawValue = System.getenv(\"" + path.value() + "\");" +
                            "if (rawValue == null || rawValue.isEmpty()) {" +
                            "return " + (path.defaultValue().isEmpty() ? PRIMITIVE_TO_FUNCTION.getOrDefault(method.getReturnType(), str -> str).apply("\"\"")
                            : PRIMITIVE_TO_FUNCTION.getOrDefault(method.getReturnType(), str -> str).apply(path.defaultValue())) + ";" +
                            "}";

                    boolean isBoolean = method.getReturnType().equals(boolean.class);
                    boolean isNumber = method.getReturnType().isAssignableFrom(int.class);

                    if (isBoolean) {
                        rawMethod += "return Boolean.valueOf(rawValue);";
                    } else if (isNumber) {
                        rawMethod += "return (" + method.getReturnType().getSimpleName() + ") Double.parseDouble(rawValue);";
                    } else {
                        rawMethod += "return rawValue;";
                    }

                    rawMethod += "}";

                    CtMethod ctMethod = CtMethod.make(rawMethod, ctClass);
                    ctClass.addMethod(ctMethod);
                }

                environment = (T) ctClass.toClass(this.getClass().getClassLoader(), this.getClass().getProtectionDomain()).getConstructor().newInstance();
            } catch (NotFoundException | CannotCompileException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();

                return null;
            }

            return environment;
        });

        if (type != null) {
            Class<? extends T> clazz = (Class<? extends T>) type.getClass();

            this.binder.bind(environmentClass).to(clazz);
        }

        return Optional.ofNullable(type);
    }
}
