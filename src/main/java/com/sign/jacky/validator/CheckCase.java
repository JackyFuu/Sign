package com.sign.jacky.validator;


import com.sign.jacky.enums.CaseMode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

//Defines the supported target element types for the constraint. 
// 字段级：FIELD
// JavaBean的属性级和方法返回值：METHOD,
// method/constructor parameters：PARAMETER
// type argument of parameterized types：TYPE_USE
// The element type ANNOTATION_TYPE allows for the creation of composed constraints based on @CheckCase.
// When creating a class-level constraint, the element type TYPE
@Target({ElementType.FIELD,ElementType.METHOD,
        ElementType.PARAMETER,ElementType.ANNOTATION_TYPE,ElementType.TYPE_USE})
//Specifies, that annotations of this type will be available at 
// runtime by the means of reflection
@Retention(RUNTIME)
//Marks the annotation type as constraint annotation and specifies 
// the validator to be used to validate elements annotated with @CheckCase.
// If a constraint may be used on several data types, 
// several validators may be specified, one for each data type.
@Constraint(validatedBy = CheckCaseValidator.class)
//Says, that the use of @CheckCase will be contained 
// in the JavaDoc of elements annotated with it
@Documented
//@Repeatable(List.class): Indicates that the annotation can 
// be repeated several times at the same place
// It allows to specify several @CheckCase annotations on the same element
//@Repeatable(List.class)
public @interface CheckCase {
    
    //出现错误时的错误消息,必要
    String message() default "{org.hibernate.validator.referenceguide.chapter06.CheckCase."+"message}";

    //指定验证组，必要
    Class<?>[] groups() default { };

    //可以被客户端用来分配自定义负载对象给一个约束，必要
    Class<? extends Payload>[] payload() default { };

    //可以省略，用来指定CaseMode的用例模式。比如@CheckMode(CaseMode.UPPER)  @CheckMode(CaseMode.LOWER)
    CaseMode value();
}
