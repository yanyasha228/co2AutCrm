package com.co2AutomaticCrm.Dao.ManDaos;

import com.co2AutomaticCrm.Models.HelpModels.GenProductManipulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenProductManipulationDao extends JpaRepository<GenProductManipulation, Long>, ProductManipulationRepExtension {

}

