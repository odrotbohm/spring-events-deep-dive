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
package example.spring.events.b.transactions;

import example.spring.events.b.transactions.Order.OrderCompleted;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author Oliver Drotbohm
 */
class OrderListeners {

	@Slf4j
	@Component
	static class AnnotatedListener {

		@EventListener
		void on(OrderCompleted event) {
			log.info("Received {}.", event);
		}
	}

	@Slf4j
	@Component
	static class ConfigurableEventListener {

		private @Setter boolean fail = false;

		@EventListener
		void on(OrderCompleted event) {

			log.info("Received {}.", event);

			if (fail) {
				throw new IllegalStateException("Error!");
			}
		}
	}

	@Slf4j
	@Component
	static class TransactionalListener {

		@TransactionalEventListener
		void on(OrderCompleted event) {
			log.info("Received {}.", event);
		}
	}

	@Slf4j
	@Component
	static class ConfigurableTxEventListener {

		private @Setter boolean fail = false;

		@TransactionalEventListener
		void on(OrderCompleted event) {

			log.info("Received {}.", event);

			if (fail) {
				throw new IllegalStateException("Error!");
			}
		}
	}
}
