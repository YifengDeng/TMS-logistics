package com.yydscm.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by chenzhaopeng on 2017/6/8.
 */
@Aspect
@Component
public class HttpAspect {
    private Logger logger = LoggerFactory.getLogger(HttpAspect.class);

//    @Pointcut("execution(public * com.yydscm.Controller..*.*(..))")
//    public void ParamterVaild() {
//
//    }
//
//    @Before("ParamterVaild()")
//    public void ParamterBefore(JoinPoint joinPoint) throws ParamterException {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        logger.info("url={}", request.getRequestURI());
//        logger.info("userid={}", request.getAttribute("userid"));
//        logger.info("method={}", request.getMethod());
//        logger.info("ip={}", request.getRemoteHost());
//        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        Object[] objects = joinPoint.getArgs();
//        Optional<Map<String, Object>> optional = Optional.empty();
//        for (Object object : objects) {
//            if (object instanceof Map) {
//                Map<String, Object> map = (Map<String, Object>) object;
//                optional = Optional.ofNullable(map);
//            }
//        }
//        if (!optional.isPresent() || optional.get().entrySet().isEmpty()) {
//            throw new ParamterException("参数为空");
//        }
//    }


//    @AfterReturning(returning = "object",pointcut = "ParamterVaild()")
//    public void ParaterAterterReturning(Object object){
//        System.out.println(object);
//    }
}
