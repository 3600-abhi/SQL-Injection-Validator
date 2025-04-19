package com.safesql.validator.validation;

import com.safesql.validator.exceptions.SqlInjectionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Pattern;


@Aspect
@Component
public class SqlInjectionValidator {

    private static final Logger logger = LoggerFactory.getLogger(SqlInjectionValidator.class);

    private final Environment environment;

    private static final String DEFAULT_SQL_INJECTION_PATTERN = "(?i)(\\\\b(SELECT|INSERT|UPDATE|DELETE|DROP|CREATE|ALTER|TRUNCATE|EXEC|UNION|JOIN|--|;|/\\\\*|\\\\*/|xp_)\\\\b)";

    private String sqlInjectionPattern;

    private Pattern pattern;

    public SqlInjectionValidator(Environment environment) {
        this.environment = environment;
    }

    @Before("execution(* *(..)) && within(@org.springframework.web.bind.annotation.RestController *)")
    public void validateSqlInjection(JoinPoint joinPoint) throws SqlInjectionException {

        try {

            String validateSqlInjection = this.environment.getProperty("custom.sql_injection.validate", "false");

            if (!Boolean.parseBoolean(validateSqlInjection)) {
                return;
            }

            this.sqlInjectionPattern = this.environment.getProperty("custom.sql_injection.pattern",
                    DEFAULT_SQL_INJECTION_PATTERN);

            this.pattern = Pattern.compile(sqlInjectionPattern,
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

            logger.info("Validating for SQL Injection");

            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Object[] args = joinPoint.getArgs();

            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(RequestBody.class)) {
                    validateFields(args[i]);
                }
            }
        } catch (SqlInjectionException e) {
            logger.info("SQL Injection Detected in Payload");
            throw e;
        } catch (Exception e) {
            logger.error("Exception occured while validating SQL Injection {}", e.getMessage());
            e.printStackTrace();
        }

        logger.info("No SQL Injection Found");
    }

    private void validateFields(Object obj) throws SqlInjectionException {

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            Object value = null;

            try {
                value = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (value != null) {
                if (value instanceof String) {
                    if (this.pattern.matcher(value.toString()).find()) {
                        throw new SqlInjectionException("SQL Injection detected in field: " + field.getName(),
                                HttpStatus.BAD_REQUEST);
                    }
                } else if (isCustomClass(value)) {
                    validateFields(value);
                }
            }
        }

    }

    private boolean isCustomClass(Object obj) {
        String customPackageName = "com.safesql";
        String packageName = obj.getClass().getPackage().getName();
        return packageName.startsWith(customPackageName);
    }

}