package com.mycompany.strategyexperiment.dao.experiment.listar;

import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.SQLException;
import java.util.List;

public interface Listar {

    public List<? extends IEntidadeDAO> listarPorEntidade(int idEntidade) throws SQLException;

}
