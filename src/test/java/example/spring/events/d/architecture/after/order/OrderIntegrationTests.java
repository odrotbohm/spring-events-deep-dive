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
package example.spring.events.d.architecture.after.order;

import static org.assertj.core.api.Assertions.*;

import example.spring.events.d.architecture.after.inventory.Inventory;
import example.spring.events.util.IntegrationTest;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;

/**
 * @author Oliver Drotbohm
 */
@IntegrationTest
@RequiredArgsConstructor
class OrderIntegrationTests {

	private final OrderManagement orders;
	private final Inventory inventory;

	@Test
	void transactionRollbackIfInventoryFails() {

		inventory.setFail(true);

		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> orders.completeOrder(new Order()));

		inventory.setFail(false);
	}
}
