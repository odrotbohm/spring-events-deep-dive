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
package example.spring.events.c.architecture.before.order;

import example.spring.events.c.architecture.before.inventory.Inventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Service
@RequiredArgsConstructor
class OrderManagement {

	private final OrderRepository orders;
	private final Inventory inventory;

	@Transactional
	public void completeOrder(Order order) {

		order.complete();

		log.info("Completing order {}.", order);

		Order result = orders.save(order);

		inventory.updateInventoryFor(result);
	}
}
