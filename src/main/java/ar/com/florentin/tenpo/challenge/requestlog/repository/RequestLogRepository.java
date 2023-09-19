package ar.com.florentin.tenpo.challenge.requestlog.repository;

import ar.com.florentin.tenpo.challenge.requestlog.entity.RequestLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends ReactiveCrudRepository<RequestLog, Long> {
}
