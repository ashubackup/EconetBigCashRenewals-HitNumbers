package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.TblLogInInfor;

@Repository
public interface LoginInfoRepo extends JpaRepository<TblLogInInfor, Integer>
{

	TblLogInInfor findByStatus(String status);
}
