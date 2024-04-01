package com.br.projetoGlobal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.projetoGlobal.models.EmpresaMock;

@Repository
public interface EmpresaMockRepository extends JpaRepository<EmpresaMock, Long>{
}
