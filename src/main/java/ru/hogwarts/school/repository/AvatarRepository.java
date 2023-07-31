package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByStudent_Id (Long studentId);
}
