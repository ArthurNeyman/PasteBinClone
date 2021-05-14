package com.good_three.repositories;

import com.good_three.entities.PasteEntity;
import com.good_three.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository<PasteEntity,Long> {

    List<PasteEntity> findFirst10ByAccessIdAndDeadTimeAfterOrderByDateCreate(long accessId,Date deadTime);
    PasteEntity findByHashCodeAndDeadTimeAfter(String hashCode,Date deadTime);
    PasteEntity findByHashCode(String hashCode);
    List<PasteEntity> findByUser(UserEntity user);
    List<PasteEntity> findByNameIgnoreCaseContainingAndDeadTimeAfterOrDescriptionIgnoreCaseContainingAndDeadTimeAfter(String searchString,Date dateNow1,String searchString1,Date dateNow);

}
