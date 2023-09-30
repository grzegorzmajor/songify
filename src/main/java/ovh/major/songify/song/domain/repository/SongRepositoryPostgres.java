package ovh.major.songify.song.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import ovh.major.songify.song.domain.model.SongEntity;

import java.util.List;
import java.util.Optional;
//ToDo will require the implementation of other methods;

public interface SongRepositoryPostgres extends Repository<SongEntity, Long> {

    SongEntity save(SongEntity song);
    List<SongEntity> findAll();
    Optional<SongEntity> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);

    @Modifying
    @Query("UPDATE SongEntity s SET s.name = :#{#song.name}, s.artist = :#{#song.artist} WHERE s.id = :id")
    SongEntity updateById(Long id, SongEntity song);

}
