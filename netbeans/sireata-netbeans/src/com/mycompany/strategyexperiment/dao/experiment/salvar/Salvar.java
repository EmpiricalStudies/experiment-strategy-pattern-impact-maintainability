package com.mycompany.strategyexperiment.dao.experiment.salvar;

import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.SQLException;

public interface Salvar {
    
    	public int salvar(IEntidadeDAO entidade) throws SQLException;
    
}
