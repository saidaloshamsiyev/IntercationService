package metube.com.intercationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReportRepository extends JpaRepository<HistoryRepository, UUID> {

}
