/**
 * @(#)LogAspect.java, 2018-04-10.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.aop;

import com.google.common.base.Strings;
import com.youdao.sortinghat.annotation.*;
import com.youdao.sortinghat.dao.mysql.model.AdminUserEntity;
import com.youdao.sortinghat.dao.mysql.model.SchoolEntity;
import com.youdao.sortinghat.dao.mysql.model.TeacherEntity;
import com.youdao.sortinghat.data.common.Constants;
import com.youdao.sortinghat.data.request.Result;
import com.youdao.sortinghat.service.AdminUserService;
import com.youdao.sortinghat.service.SchoolService;
import com.youdao.sortinghat.service.TeacherService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import outfox.account.conf.AccConst;
import outfox.account.data.user.UserInfoWritable;
import outlog.toolbox.analyzer.Filter;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * LogAspect
 *
 * @author 
 *
 */
@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SchoolService schoolService;

    // 20s
    private static final long SLOWREQUEST = 20000;

    @Around("execution(* com.youdao.sortinghat.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Object controller = point.getTarget();
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        if (isAnnotationPresent(method, NoLog.class)) {
            // 如果有nolog就不记录 直接返回
            return point.proceed();
        }

        // 记录日志并捕获异常
        long start = System.currentTimeMillis();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        Object result = null;
        Result checkResult = Result.success();

        // 获取用户IP
        String ip = request.getHeader("x-forwarded-for");
        ip = ip == null ? request.getRemoteAddr() : ip;

        // 执行请求并捕获异常
        try {
            checkResult = permissionCheck(method, request, ip);
            if (checkResult.isSuccess()) {
                result = point.proceed();
            }
        } catch (Throwable throwable) {
            checkResult = Result.error("内部错误");
            throwable.printStackTrace();
            logger.error("[LogAspect] " + request.getRequestURI(), throwable);
        }

        // log
        long end = System.currentTimeMillis();
        logger.info("[LogAspect] " + request.getRequestURI() + " " + (end - start) + "ms");
        if ((end - start) > SLOWREQUEST) {
            logger.warn("[SLOW] [LogAspect] " + request.getRequestURI() + " " + (end - start) + "ms");
        }

        if (result == null || !checkResult.isSuccess()) {
            if (method.isAnnotationPresent(ResponseBody.class) ||
                    controller.getClass().isAnnotationPresent(RestController.class)) {
                result = checkResult;
            } else {
                if (checkResult.isOverdue()) {
                    return new ModelAndView("redirect:http://c.youdao.com/dict/commons/login.html?back_url="
                            + URLEncoder.encode(request.getRequestURL() + "?" + request.getQueryString()));
                } else {
                    result = new ModelAndView("/error").addObject("message",
                            checkResult.getMessage());
                }
            }
        }
        return result;
    }

    private Result permissionCheck(Method method, HttpServletRequest request, String ip) {
        UserInfoWritable userInfoWritable = (UserInfoWritable) request.getAttribute("DICT" +
                AccConst.ATTR_PART_USER_ID_WRITABLE);
        String userId = null;
        if (userInfoWritable != null) {
            userId = userInfoWritable.userId;
        }
        // 本地访问限制
        boolean isLocalHost = isAnnotationPresent(method, LocalHost.class);
        if (isLocalHost && !ip.contains("127.0.0.1") && !ip.contains("0:0:0:0:0:0:0:1")) {
            return Result.failure("未授权");
        }
        // 公司内网访问限制
        boolean isInnerIp = isAnnotationPresent(method, InnerIp.class);
        if (isInnerIp && !Filter.isCompanyIp(ip)) {
            return Result.failure("未授权");
        }
        // 登陆限制
        boolean isLoginRequired = isAnnotationPresent(method, LoginRequired.class);
        if (isLoginRequired && (userInfoWritable == null || Strings.isNullOrEmpty(userId))) {
            return Result.overdue();
        }
        // 管理员限制
        boolean isAdminRequired = isAnnotationPresent(method, AdminRequired.class);
        boolean isNoAdmin = isAnnotationPresent(method, NoAdmin.class);
        if (isAdminRequired && !isNoAdmin) {
            AdminUserEntity adminUser = adminUserService.getAdminByUserId(userId);
            if (adminUser == null) {
                return Result.failure("未授权");
            }
        }
        // 教务限制
        boolean isManagerRequired = isAnnotationPresent(method, ManagerRequired.class);
        if (isManagerRequired) {
            if (!userInfoWritable.from.startsWith("urs-phone")) {
                return Result.failure("未授权");
            }
            String mobile = userInfoWritable.userName;
            TeacherEntity teacherEntity = teacherService.getActivateTeacherByMobile(mobile);
            if (teacherEntity == null) {
                return Result.failure("未授权");
            }
            SchoolEntity schoolEntity = schoolService.getSchoolById(teacherEntity.getSchoolId());
            if (schoolEntity == null || schoolEntity.isNotActivate()) {
                return Result.failure("未授权");
            }
            request.setAttribute(Constants.MANAGER_SCHOOL_ID, teacherEntity.getSchoolId());
        }
        return Result.success();
    }

    private boolean isAnnotationPresent(Method method, Class<? extends Annotation> annotationClass) {
        return method.getDeclaringClass().isAnnotationPresent(annotationClass) || method.isAnnotationPresent(annotationClass);
    }

}