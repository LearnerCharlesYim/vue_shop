package com.charles.controller;

import com.charles.service.ex.ParamNotFoundException;
import com.charles.service.ex.ServiceException;
import com.charles.util.JsonResult;
import com.charles.util.State;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

public class BaseController {
    @ExceptionHandler({ServiceException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<Void>(e);
        if(e instanceof ParamNotFoundException){
            result.setState(State.NOTFOUND);
            result.setMessage(e.getMessage());
        }else if(e instanceof UsernameNotFoundException){
            result.setState(State.NOTFOUND);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
