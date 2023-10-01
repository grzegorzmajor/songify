package ovh.major.songify.song.domain.postgres.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import ovh.major.songify.song.domain.postgres.SongEntity;

import java.util.List;
import java.util.Optional;

public interface SongRepositoryPostgres extends Repository<SongEntity, Integer> {

    SongEntity save(SongEntity song);

    @Query("SELECT s FROM SongEntity s")
    List<SongEntity> findAll();

    @Query("SELECT s FROM SongEntity s WHERE s.id = :id")
    Optional<SongEntity> findById(Integer id);

    @Modifying
    @Query("DELETE FROM SongEntity s WHERE s.id = :id")
    void deleteById(Integer id);

    @Modifying
    @Query("SELECT true FROM SongEntity s WHERE s.id = :id")
    boolean existsById(Integer id);

    @Modifying
    @Query("UPDATE SongEntity s SET s.name = :#{#song.name}, s.artist = :#{#song.artist} WHERE s.id = :id")
    Integer updateById(Integer id, SongEntity song);

}
