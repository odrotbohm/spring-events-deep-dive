/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.spring.events.util;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author Oliver Drotbohm
 */
@Configuration
public class LoggingConfiguration {

	@Bean
	CustomizableTraceInterceptor interceptor() {

		var interceptor = new CustomizableTraceInterceptor();
		interceptor.setEnterMessage("Entering $[methodName]($[arguments]).");
		interceptor.setExitMessage("Leaving $[methodName](..) with return value $[returnValue], took $[invocationTime]ms.");
		interceptor.setLogExceptionStackTrace(false);

		return interceptor;
	}

	@Bean
	Advisor traceAdvisor() {

		var pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("bean(orderManagement)");

		var advisor = new DefaultPointcutAdvisor(pointcut, interceptor());
		advisor.setOrder(Ordered.LOWEST_PRECEDENCE);

		return advisor;
	}

	@Bean
	static Foo foo() {
		return new Foo();
	}

	static class Foo implements BeanPostProcessor {

		/*
		 * (non-Javadoc)
		 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
		 */
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

			if (!beanName.equals("orderManagement")) {
				return bean;
			}

			var orders = Advised.class.cast(bean);

			var advisors = orders.getAdvisors();

			var foo = advisors[advisors.length - 1];
			var bar = advisors[advisors.length - 2];

			advisors[advisors.length - 2] = foo;
			advisors[advisors.length - 1] = bar;

			return orders;
		}
	}
}
