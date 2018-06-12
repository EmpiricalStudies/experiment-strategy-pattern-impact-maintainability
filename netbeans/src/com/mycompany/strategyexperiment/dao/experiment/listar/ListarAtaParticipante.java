package com.mycompany.strategyexperiment.dao.experiment.listar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.AtaParticipante;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListarAtaParticipante implements Listar {

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
    public List<? extends IEntidadeDAO> listarPorEntidade(int idEntidade) throws SQLException {
        int idAta = idEntidade;

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT ataparticipantes.*, usuarios.nome AS nomeParticipante FROM ataparticipantes "
                    + "INNER JOIN usuarios ON usuarios.idUsuario=ataparticipantes.idUsuario "
                    + "WHERE idAta=" + String.valueOf(idAta) + " ORDER BY usuarios.nome");

            List<AtaParticipante> list = new ArrayList<AtaParticipante>();

            while (rs.next()) {
                list.add(this.carregarObjeto(rs));
            }

            return list;
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
