package com.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Entity.SendLogs;

@Repository
public interface SendLogsRepo extends JpaRepository<SendLogs,Integer>
{

}