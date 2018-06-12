package com.mycompany.strategyexperiment.dao.experiment.salvar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.Comentario;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalvarComentario implements Salvar {

    @Override
    public int salvar(IEntidadeDAO entidade) throws SQLException {
        Comentario comentario = (Comentario) entidade;
        
        boolean insert = (comentario.getIdComentario() == 0);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();

            if (insert) {
                stmt = conn.prepareStatement("INSERT INTO comentarios(idPauta, idUsuario, situacao, comentarios, situacaoComentarios, motivo) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt = conn.prepareStatement("UPDATE comentarios SET idPauta=?, idUsuario=?, situacao=?, comentarios=?, situacaoComentarios=?, motivo=? WHERE idComentario=?");
            }

            stmt.setInt(1, comentario.getPauta().getIdPauta());
            stmt.setInt(2, comentario.getUsuario().getIdUsuario());
            stmt.setInt(3, comentario.getSituacao().getValue());
            stmt.setString(4, comentario.getComentarios());
            stmt.setInt(5, comentario.getSituacaoComentarios().getValue());
            stmt.setString(6, comentario.getMotivo());

            if (!insert) {
                stmt.setInt(7, comentario.getIdComentario());
            }

            stmt.execute();

            if (insert) {
                rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    comentario.setIdComentario(rs.getInt(1));
                }
            }

            return comentario.getIdComentario();
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
