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
package example.spring.events.d.architecture.after.inventory;

import example.spring.events.d.architecture.after.order.Order;
import example.spring.events.d.architecture.after.order.Order.OrderCompleted;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
@Component
public class Inventory {

	private @Setter boolean fail = false;

	@EventListener
	void on(OrderCompleted event) {
		updateInventoryFor(event.order());
	}

	public void updateInventoryFor(Order order) {

		log.info("Updating inventory for order {}.", order);

		if (fail) {
			throw new IllegalStateException("Error!");
		}
	}
}
