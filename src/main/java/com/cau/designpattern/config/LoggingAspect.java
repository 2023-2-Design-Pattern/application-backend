package com.cau.designpattern.config;

import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

/**
 * 모든 Component의 Method가 호출될 때 Log를 찍는 Class (AOP)
 */
@Component
@Aspect
@Log4j2
public class LoggingAspect {

	@Around("within(com.cau.designpattern..*)")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {

		String params = getRequestParams();

		long startAt = System.currentTimeMillis();

		log.info("----------> REQUEST : {}({}) = {}", pjp.getSignature().getDeclaringTypeName(),
			pjp.getSignature().getName(), params);

		Object result = pjp.proceed();

		long endAt = System.currentTimeMillis();

		log.info("----------> RESPONSE : {}({}) = {} ({}ms)", pjp.getSignature().getDeclaringTypeName(),
			pjp.getSignature().getName(), result, endAt - startAt);

		return result;

	}

	private String getRequestParams() {

		String params = "";

		RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();

		if (requestAttribute != null) {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

			Map<String, String[]> paramMap = request.getParameterMap();

			if (!paramMap.isEmpty())
				params = " [" + paramMapToString(paramMap) + "]";
		}
		return params;
	}

	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
			.map(entry -> {
				StringJoiner joiner = new StringJoiner(",");
				for (String value : entry.getValue())
					joiner.add(value);
				return String.format("%s -> (%s)", entry.getKey(), joiner);
			})
			.collect(Collectors.joining(", "));
	}
}