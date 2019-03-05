package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
@Transactional
public interface TestReportRepository extends JpaRepository<TestReportStatModel, Long> {

    public List<TestReportStatModel> findAll();
    public List<TestReportStatModel> findByTestCaseId(String testCaseId);
}
