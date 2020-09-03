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
package example.spring.events.a.fundamentals.quickstart;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Component
@RequiredArgsConstructor
class OrderManagement {

	private final OrderRepository orders;
	private final ApplicationEventPublisher publisher;

	public void completeOrder(Order order) {

		publisher.publishEvent(new SomeOtherEvent());

		order.complete();

		log.info("Completing order {}.", order);

		orders.save(order);

		publisher.publishEvent(new OrderCompleted(order));
	}

	@EqualsAndHashCode(callSuper = false)
	static class OrderCompleted extends ApplicationEvent {

		private static final long serialVersionUID = 2905002063026818136L;

		public OrderCompleted(Object source) {
			super(source);
		}
	}

	static class SomeOtherEvent {}
}
