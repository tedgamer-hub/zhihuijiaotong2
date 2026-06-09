package com.parent.springai.intellidetect.repository;

import com.parent.springai.intellidetect.entity.AccidentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentInfoRepository extends JpaRepository<AccidentInfo, Long> {
}
