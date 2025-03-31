package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.PasteEntity;
import com.paste_bin_clone.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<PasteEntity,Long> {

    List<PasteEntity> findFirst10ByAccessAndDeadTimeAfterOrderByDateCreate(String access, LocalDateTime deadTime);
    PasteEntity findByHashCodeAndDeadTimeAfter(String hashCode,LocalDateTime deadTime);
    PasteEntity findByHashCode(String hashCode);
    List<PasteEntity> findByUser(UserEntity user);
    List<PasteEntity> findByNameIgnoreCaseContainingAndDeadTimeAfterOrDescriptionIgnoreCaseContainingAndDeadTimeAfter(String searchString,LocalDateTime dateNow1,String searchString1,LocalDateTime dateNow);

}
