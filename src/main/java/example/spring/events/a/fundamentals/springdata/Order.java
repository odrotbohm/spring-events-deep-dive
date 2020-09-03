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
package example.spring.events.a.fundamentals.springdata;

import example.spring.events.util.Aggregate;
import lombok.ToString;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Oliver Drotbohm
 */
@Entity
@Table(name = "MyOrder")
@ToString(callSuper = true)
class Order extends Aggregate<Order> {

	private Status status = Status.OPEN;

	Order complete() {

		this.status = Status.COMPLETED;

		registerEvent(new OrderCompleted(this));

		return this;
	}

	enum Status {
		OPEN, COMPLETED;
	}

	@Value
	static class OrderCompleted {
		Order order;
	}
}
