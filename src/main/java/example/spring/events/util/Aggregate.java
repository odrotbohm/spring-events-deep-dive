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

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.domain.Persistable;

/**
 * @author Oliver Drotbohm
 */
@ToString
@MappedSuperclass
public abstract class Aggregate<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T>
		implements Persistable<UUID> {

	private @Id @Getter UUID id = UUID.randomUUID();
	private @Transient boolean isNew = true;

	@PrePersist
	@PostLoad
	void notNew() {
		this.isNew = false;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}
}
