package com.mycompany.strategyexperiment.dao.experiment.buscar;

import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.SQLException;

public interface Buscar {
    
        public IEntidadeDAO buscarPorId(int id) throws SQLException;
    
}
