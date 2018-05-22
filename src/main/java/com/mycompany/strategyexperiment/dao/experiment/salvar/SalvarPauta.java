package com.mycompany.strategyexperiment.dao.experiment.salvar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import com.mycompany.strategyexperiment.model.Pauta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalvarPauta implements Salvar {

    @Override
    public int salvar(IEntidadeDAO entidade) throws SQLException {
        Pauta pauta = (Pauta) entidade;
        
        boolean insert = (pauta.getIdPauta() == 0);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();

            if (insert) {
                stmt = conn.prepareStatement("INSERT INTO pautas(idAta, ordem, titulo, descricao) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt = conn.prepareStatement("UPDATE pautas SET idAta=?, ordem=?, titulo=?, descricao=? WHERE idPauta=?");
            }

            stmt.setInt(1, pauta.getAta().getIdAta());
            stmt.setInt(2, pauta.getOrdem());
            stmt.setString(3, pauta.getTitulo());
            stmt.setString(4, pauta.getDescricao());

            if (!insert) {
                stmt.setInt(5, pauta.getIdPauta());
            }

            stmt.execute();

            if (insert) {
                rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    pauta.setIdPauta(rs.getInt(1));
                }
            }

            return pauta.getIdPauta();
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
