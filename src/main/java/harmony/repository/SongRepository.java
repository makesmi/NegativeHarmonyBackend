
package harmony.repository;

import harmony.domain.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Song.Identifier> {
    public List<Song> findByUserId(String userId);
}
