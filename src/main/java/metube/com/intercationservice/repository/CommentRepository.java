package metube.com.intercationservice.repository;

import metube.com.intercationservice.domian.entity.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommitEntity, UUID> {
}
