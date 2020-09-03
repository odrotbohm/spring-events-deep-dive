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

import static org.assertj.core.api.Assertions.*;

import example.spring.events.b.transactions.OrderListeners.ConfigurableEventListener;
import example.spring.events.b.transactions.OrderListeners.ConfigurableTxEventListener;
import example.spring.events.util.IntegrationTest;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;

/**
 * @author Oliver Drotbohm
 */
@IntegrationTest
@RequiredArgsConstructor
class OrderEventPublicationTests {

	private final OrderManagement orders;

	private final ConfigurableEventListener listener;
	private final ConfigurableTxEventListener txListener;

	@Test
	void publishesEventOnCompletion() {

		assertThatCode(() -> orders.completeOrder(new Order()))
				.doesNotThrowAnyException();
	}

	@Test
	void eventListenerCanBreakTransaction() {

		listener.setFail(true);

		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> orders.completeOrder(new Order()));

		listener.setFail(false);
	}

	@Test
	void doesNotInvokeTransactionalEventListenerOnError() {

		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> orders.failToCompleteOrder(new Order()));
	}

	@Test
	void registersEventPublicationInCaseOfListenerFailure() {

		txListener.setFail(true);

		assertThatCode(() -> orders.completeOrder(new Order()))
				.doesNotThrowAnyException();

		txListener.setFail(true);
	}
}
