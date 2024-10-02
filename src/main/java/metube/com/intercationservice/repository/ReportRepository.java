package metube.com.intercationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<HistoryEntity, UUID> {
}
