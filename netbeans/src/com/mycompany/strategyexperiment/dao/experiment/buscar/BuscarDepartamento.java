package com.mycompany.strategyexperiment.dao.experiment.buscar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.Departamento;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscarDepartamento implements Buscar {

    private Departamento carregarObjeto(ResultSet rs) throws SQLException {
        Departamento departamento = new Departamento();

        departamento.setIdDepartamento(rs.getInt("idDepartamento"));
        departamento.getCampus().setIdCampus(rs.getInt("idCampus"));
        departamento.setNome(rs.getString("nome"));
        departamento.setNomeCompleto(rs.getString("nomeCompleto"));
        departamento.setLogo(rs.getBytes("logo"));
        departamento.setAtivo(rs.getInt("ativo") == 1);
        departamento.setSite(rs.getString("site"));
        departamento.getCampus().setNome(rs.getString("nomeCampus"));

        return departamento;
    }

    @Override
    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(
                    "SELECT departamentos.*, campus.nome AS nomeCampus "
                    + "FROM departamentos INNER JOIN campus ON campus.idCampus=departamentos.idCampus "
                    + "WHERE idDepartamento = ?");

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return this.carregarObjeto(rs);
            } else {
                return null;
            }
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
