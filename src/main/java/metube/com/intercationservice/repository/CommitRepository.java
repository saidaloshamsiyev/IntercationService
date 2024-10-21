package metube.com.intercationservice.repository;

import metube.com.intercationservice.domian.dto.response.CommitRes;
import metube.com.intercationservice.domian.entity.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommitRepository extends JpaRepository<CommitEntity, UUID> {
    List<CommitRes> findAllBy();
}
