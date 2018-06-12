package com.mycompany.strategyexperiment.dao.experiment;

import com.mycompany.strategyexperiment.dao.experiment.buscar.Buscar;
import com.mycompany.strategyexperiment.dao.experiment.excluir.Excluir;
import com.mycompany.strategyexperiment.dao.experiment.listar.Listar;
import com.mycompany.strategyexperiment.dao.experiment.salvar.Salvar;

public class AtaParticipanteFullDAO extends AbstractFullDAO {
    
    public AtaParticipanteFullDAO (Salvar salvar, Listar listar, Buscar buscar, Excluir excluir) {
        this.salvar = salvar;
        this.listar = listar;
        this.buscar = buscar;
        this.excluir = excluir;
    }
    
}
