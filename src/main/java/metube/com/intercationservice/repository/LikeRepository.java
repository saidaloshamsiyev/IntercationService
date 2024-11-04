package metube.com.intercationservice.repository;

import metube.com.intercationservice.domian.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
    LikeEntity findByUserIdAndVideoId(UUID userId, UUID videoId);

    List<LikeEntity> findAllByVideoId(UUID videoId);

    List<LikeEntity> findAllByUserId(UUID userId);
}
