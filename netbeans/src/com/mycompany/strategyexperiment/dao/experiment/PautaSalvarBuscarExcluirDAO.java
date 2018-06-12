package com.mycompany.strategyexperiment.dao.experiment;

import com.mycompany.strategyexperiment.dao.experiment.buscar.Buscar;
import com.mycompany.strategyexperiment.dao.experiment.excluir.Excluir;
import com.mycompany.strategyexperiment.dao.experiment.salvar.Salvar;

public class PautaSalvarBuscarExcluirDAO extends AbstractSalvarBuscarExcluirDAO {
    
    public PautaSalvarBuscarExcluirDAO (Salvar salvar, Buscar buscar, Excluir excluir) {
        this.salvar = salvar;
        this.buscar = buscar;
        this.excluir = excluir;
    }
    
}
