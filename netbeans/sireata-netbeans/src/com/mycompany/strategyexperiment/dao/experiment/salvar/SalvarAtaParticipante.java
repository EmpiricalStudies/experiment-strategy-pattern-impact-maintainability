package com.mycompany.strategyexperiment.dao.experiment.salvar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.AtaParticipante;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalvarAtaParticipante implements Salvar {

    @Override
    public int salvar(IEntidadeDAO entidade) throws SQLException {
        AtaParticipante participante = (AtaParticipante) entidade;
        
        boolean insert = (participante.getIdAtaParticipante() == 0);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();

            if (insert) {
                stmt = conn.prepareStatement("INSERT INTO ataparticipantes(idAta, idUsuario, presente, motivo, designacao) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt = conn.prepareStatement("UPDATE ataparticipantes SET idAta=?, idUsuario=?, presente=?, motivo=?, designacao=? WHERE idAtaParticipante=?");
            }

            stmt.setInt(1, participante.getAta().getIdAta());
            stmt.setInt(2, participante.getParticipante().getIdUsuario());
            stmt.setInt(3, (participante.isPresente() ? 1 : 0));
            stmt.setString(4, participante.getMotivo());
            stmt.setString(5, participante.getDesignacao());

            if (!insert) {
                stmt.setInt(6, participante.getIdAtaParticipante());
            }

            stmt.execute();

            if (insert) {
                rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    participante.setIdAtaParticipante(rs.getInt(1));
                }
            }

            return participante.getIdAtaParticipante();
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
