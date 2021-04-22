package ca.bc.gov.educ.api.common.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bc.gov.educ.api.common.model.entity.StudentNoteEntity;

@Repository
public interface StudentNoteRepository extends JpaRepository<StudentNoteEntity, UUID> {

	List<StudentNoteEntity> findByPen(String pen);
}
