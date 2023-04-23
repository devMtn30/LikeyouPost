package com.likeyoupost.back.argumentresolver;

import com.likeyoupost.back.dto.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static com.likeyoupost.back.config.LoginConst.ACCOUNT_TYPE;
import static com.likeyoupost.back.config.LoginConst.USER_ID;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasLoginUserClass = LoginUser.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasLoginUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String accountType = String.valueOf(request.getAttribute(ACCOUNT_TYPE));
        String userId = String.valueOf(request.getAttribute(USER_ID));

        if (userId.equals("null") || accountType.equals("null")) {
            return null;
        }
        return new LoginUser(Long.parseLong(userId), accountType);
    }
}
