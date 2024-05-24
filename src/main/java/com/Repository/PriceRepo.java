package com.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Entity.Price;

@Repository
public interface PriceRepo extends JpaRepository<Price,Integer>
{
	public Price findByStatusAndPack(String status,String pack);
}