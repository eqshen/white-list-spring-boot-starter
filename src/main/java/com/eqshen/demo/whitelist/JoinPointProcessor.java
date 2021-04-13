package com.eqshen.demo.whitelist;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.eqshen.demo.whitelist.annotation.ApplyWhiteList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 切面处理类
 * @author eqshen
 * @description
 * @date 2021/4/13
 */
@Aspect
@Component
public class JoinPointProcessor {

    private Logger log = LoggerFactory.getLogger(JoinPointProcessor.class);

    @Resource
    private String whiteListConfig;

    @Pointcut("@annotation(com.eqshen.demo.whitelist.annotation.ApplyWhiteList)")
    public void aopPoint(){
    }

    @Around("aopPoint()")
    public Object doRoute(ProceedingJoinPoint jp) throws Throwable {
        Method method = this.getMethod(jp);
        ApplyWhiteList annotation = method.getAnnotation(ApplyWhiteList.class);

        Map<String, Object> nameAndValue = this.getNameAndValue(jp);
        Object fieldValue = nameAndValue.get(annotation.key());

        //拿到参数值
        log.info("======>白名单拦截处理：{}",fieldValue);
        //如果参数中没有需要验证的字段，则放过
        if(StringUtils.isEmpty(fieldValue)){
            return jp.proceed();
        }

        String[] whitelistArray = whiteListConfig.split(",");
        Set<String> whitelistSet = new HashSet<>(CollectionUtils.arrayToList(whitelistArray));
        if(whitelistSet.contains(fieldValue)){
            return jp.proceed();
        }

        //拦截并返回
        Class<?> returnType = method.getReturnType();
        String returnResult = annotation.returnResult();
        if(StringUtils.isEmpty(returnResult)){
            return returnType.newInstance();
        }else{
            return JSONUtil.toBean(JSONUtil.parseObj(returnResult),returnType);
        }

    }

    /**
     * 获取拦截方法的 参数名和参数值
     * @param joinPoint
     * @return
     */
    Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }
        return param;
    }


    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

}
