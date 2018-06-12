package com.mycompany.strategyexperiment.dao.experiment.buscar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.AtaParticipante;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscarAtaParticipante implements Buscar {

    private AtaParticipante carregarObjeto(ResultSet rs) throws SQLException {
        AtaParticipante participante = new AtaParticipante();

        participante.setIdAtaParticipante(rs.getInt("idAtaParticipante"));
        participante.getAta().setIdAta(rs.getInt("idAta"));
        participante.getParticipante().setIdUsuario(rs.getInt("idUsuario"));
        participante.getParticipante().setNome(rs.getString("nomeParticipante"));
        participante.setPresente(rs.getInt("presente") == 1);
        participante.setMotivo(rs.getString("motivo"));
        participante.setDesignacao(rs.getString("designacao"));

        return participante;
    }

    @Override
    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT ataparticipantes.*, usuarios.nome AS nomeParticipante FROM ataparticipantes "
                    + "INNER JOIN usuarios ON usuarios.idUsuario=ataparticipantes.idUsuario "
                    + "WHERE idAtaParticipante = ?");

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
