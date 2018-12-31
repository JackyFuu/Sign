package com.sign.jacky.validator;

import com.sign.jacky.enums.CaseMode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The ConstraintValidator interface defines two type parameters which
 * are set in the implementation. 
 * The first one specifies the annotation type to be validated (CheckCase), 
 * the second one the type of elements, which the validator can handle (String)
 */
public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {
    private CaseMode caseMode;
    
    //gives you access to the attribute values of the validated 
    // constraint and allows you to store them in a field of the validator
    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    //The isValid() method contains the actual validation logic

    //Using the passed ConstraintValidatorContext object, 
    // 1、it is possible to either add additional error messages 
    // 2、or completely disable the default error message generation 
    // 3、and solely define custom error messages.
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;
        }

        boolean isValid;
        if ( caseMode == CaseMode.UPPER ) {
            isValid = value.equals( value.toUpperCase() );
        }
        else {
            isValid = value.equals( value.toLowerCase() );
        }

        if ( !isValid ) {
            //禁止默认的约束错误信息
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate( //添加自定义的错误信息，通过特定的信息模板
                    "{org.hibernate.validator.referenceguide.chapter06." +
                            "constraintvalidatorcontext.CheckCase.message}"
            ).addConstraintViolation(); //只有添加之后，新的约束才能被创建
        }
        return isValid;
    }
}
