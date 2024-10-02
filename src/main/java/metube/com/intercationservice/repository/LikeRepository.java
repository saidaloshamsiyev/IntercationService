package metube.com.intercationservice.repository;

import metube.com.intercationservice.domian.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {

}
