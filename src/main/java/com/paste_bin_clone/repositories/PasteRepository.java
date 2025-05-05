package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<PasteEntity, Long> {

    List<PasteEntity> findFirst10ByAccessAndDeadTimeAfterOrderByDateCreate(String access, Instant deadTime);

    PasteEntity findByHashCodeAndDeadTimeAfterAndAccessIn(String hashCode, Instant deadTime, ArrayList<String> access);

    List<PasteEntity> findAllByUserIdOrderByDateCreate(long userId);

    List<PasteEntity> findByNameIgnoreCaseContainingAndDeadTimeAfterOrDescriptionIgnoreCaseContainingAndDeadTimeAfter(String searchString, Instant dateNow1, String searchString1, Instant dateNow);

    @Transactional
    void deleteByHashCodeAndUserId(String hashCode, long userId);

}
