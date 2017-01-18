/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.pflima92.plyshare.gateway.persistance.jdbc;

import static org.jspare.core.container.Environment.my;

import java.util.Optional;

import io.github.pflima92.plyshare.gateway.entity.Audit;
import io.github.pflima92.plyshare.gateway.entity.Gateway;
import io.github.pflima92.plyshare.gateway.persistance.GatewayPersistance;
import io.vertx.core.Future;

public class GatewayPersistanceJDBC implements GatewayPersistance {

	public GatewayPersistanceJDBC() {

		// Load JDBCProvider
		my(JDBCProvider.class);
	}

	@Override
	public Future<Optional<Gateway>> findGateway(String profile) {

		return JDBCExecutor.create().execute(session -> {

			return session.createNamedQuery("Gateway.findByProfile", Gateway.class).setParameter("profile", profile).getResultList()
					.stream().findFirst();
		});
	}

	@Override
	public Future<Gateway> persistGateway(Gateway gateway) {

		return JDBCExecutor.create().execute(session -> {

			session.persist(gateway);
			return gateway;
		});
	}

	@Override
	public Future<Audit> persistAudit(Audit audit) {

		return JDBCExecutor.create().execute(session -> {

			session.persist(audit);
			return audit;
		});
	}

	@Override
	public Future<Optional<Audit>> findAuditByTid(String tid) {
		
		return JDBCExecutor.create().execute(session -> {
			
			return session.createNamedQuery("Audit.findByTid", Audit.class).setParameter("tid", tid).getResultList().stream().findFirst();
		});
	}
}