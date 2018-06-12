package com.mycompany.strategyexperiment.dao.experiment;

import com.mycompany.strategyexperiment.dao.experiment.buscar.Buscar;
import com.mycompany.strategyexperiment.dao.experiment.salvar.Salvar;

public class DepartamentoSalvarBuscarDAO extends AbstractSalvarBuscarDAO {
    
    public DepartamentoSalvarBuscarDAO (Salvar salvar, Buscar buscar) {
        this.salvar = salvar;
        this.buscar = buscar;
    }
    
}
