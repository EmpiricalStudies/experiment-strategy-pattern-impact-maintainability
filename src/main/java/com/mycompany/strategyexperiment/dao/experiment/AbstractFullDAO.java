package com.mycompany.strategyexperiment.dao.experiment;

import com.mycompany.strategyexperiment.dao.experiment.buscar.Buscar;
import com.mycompany.strategyexperiment.dao.experiment.excluir.Excluir;
import com.mycompany.strategyexperiment.dao.experiment.listar.Listar;
import com.mycompany.strategyexperiment.dao.experiment.salvar.Salvar;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractFullDAO {

    protected Buscar buscar;
    protected Excluir excluir;
    protected Listar listar;
    protected Salvar salvar;

    public int salvar(IEntidadeDAO entidade) throws SQLException {
        return salvar.salvar(entidade);
    }

    public List<? extends IEntidadeDAO> listarPorEntidade(int idEntidade) throws SQLException {
        return listar.listarPorEntidade(idEntidade);
    }

    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        return buscar.buscarPorId(id);
    }

    public void excluir(int id) throws SQLException {
        excluir.excluir(id);
    }

}
