package com.mycompany.strategyexperiment.dao.experiment;

import com.mycompany.strategyexperiment.dao.experiment.buscar.Buscar;
import com.mycompany.strategyexperiment.dao.experiment.excluir.Excluir;
import com.mycompany.strategyexperiment.dao.experiment.salvar.Salvar;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.SQLException;

public abstract class AbstractSalvarBuscarExcluirDAO {

    protected Buscar buscar;
    protected Salvar salvar;
    protected Excluir excluir;

    public int salvar(IEntidadeDAO entidade) throws SQLException {
        return salvar.salvar(entidade);
    }

    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        return buscar.buscarPorId(id);
    }

    public void excluir(int id) throws SQLException {
        excluir.excluir(id);
    }
}
