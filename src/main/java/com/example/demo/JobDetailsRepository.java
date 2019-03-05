package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
@Transactional
public interface JobDetailsRepository extends JpaRepository<JobDetails, Long> {

    public List<JobDetails> findAll();
    public List<JobDetails> findByjobId(String jobID);
}
