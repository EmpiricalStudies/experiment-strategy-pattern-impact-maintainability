package com.mycompany.strategyexperiment.dao.experiment.buscar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.Campus;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscarCampus implements Buscar {

    private Campus carregarObjeto(ResultSet rs) throws SQLException {
        Campus campus = new Campus();

        campus.setIdCampus(rs.getInt("idCampus"));
        campus.setNome(rs.getString("nome"));
        campus.setEndereco(rs.getString("endereco"));
        campus.setLogo(rs.getBytes("logo"));
        campus.setAtivo(rs.getInt("ativo") == 1);
        campus.setSite(rs.getString("site"));

        return campus;
    }

    @Override
    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT * FROM campus WHERE idCampus = ?");

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
