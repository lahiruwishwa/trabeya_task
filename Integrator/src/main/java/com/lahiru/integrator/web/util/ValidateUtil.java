package com.lahiru.integrator.web.util;

import com.lahiru.integrator.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ValidateUtil {

    @Autowired
    private MessageSource messageSource;

    public void validateStringField(String value,String errorMessage,String errorCode) throws DataAccessException {
        if(value==null || value.equalsIgnoreCase("")){
            throw new DataAccessException(messageSource.getMessage(errorMessage,null, Locale.getDefault()),
                    messageSource.getMessage(errorCode,null, Locale.getDefault()));
        }
    }
}
