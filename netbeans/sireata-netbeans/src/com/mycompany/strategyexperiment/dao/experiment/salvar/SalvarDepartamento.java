package com.mycompany.strategyexperiment.dao.experiment.salvar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.Departamento;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class SalvarDepartamento implements Salvar {

    @Override
    public int salvar(IEntidadeDAO entidade) throws SQLException {
        Departamento departamento = (Departamento) entidade;
        
        boolean insert = (departamento.getIdDepartamento() == 0);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();

            if (insert) {
                stmt = conn.prepareStatement("INSERT INTO departamentos(idCampus, nome, logo, ativo, site, nomeCompleto) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt = conn.prepareStatement("UPDATE departamentos SET idCampus=?, nome=?, logo=?, ativo=?, site=?, nomeCompleto=? WHERE idDepartamento=?");
            }

            stmt.setInt(1, departamento.getCampus().getIdCampus());
            stmt.setString(2, departamento.getNome());
            if (departamento.getLogo() == null) {
                stmt.setNull(3, Types.BINARY);
            } else {
                stmt.setBytes(3, departamento.getLogo());
            }
            stmt.setInt(4, departamento.isAtivo() ? 1 : 0);
            stmt.setString(5, departamento.getSite());
            stmt.setString(6, departamento.getNomeCompleto());

            if (!insert) {
                stmt.setInt(7, departamento.getIdDepartamento());
            }

            stmt.execute();

            if (insert) {
                rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    departamento.setIdDepartamento(rs.getInt(1));
                }
            }

            return departamento.getIdDepartamento();
        } finally {
            if ((rs != null) && !rs.isClosed()) {
                rs.close();
            }
            if ((stmt != null) && !stmt.isClosed()) {
                stmt.close();
            }
            if ((conn != null) && !conn.isClosed()) {
                conn.close();
            }
        }
    }

}
