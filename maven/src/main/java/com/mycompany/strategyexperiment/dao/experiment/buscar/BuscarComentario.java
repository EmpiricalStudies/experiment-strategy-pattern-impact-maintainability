package com.mycompany.strategyexperiment.dao.experiment.buscar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.Comentario;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscarComentario implements Buscar {

    private Comentario carregarObjeto(ResultSet rs) throws SQLException {
        Comentario comentario = new Comentario();

        comentario.setIdComentario(rs.getInt("idComentario"));
        comentario.getPauta().setIdPauta(rs.getInt("idPauta"));
        comentario.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
        comentario.getUsuario().setNome(rs.getString("nomeUsuario"));
        comentario.setSituacao(Comentario.SituacaoComentario.valueOf(rs.getInt("situacao")));
        comentario.setComentarios(rs.getString("comentarios"));
        comentario.setSituacaoComentarios(Comentario.SituacaoComentario.valueOf(rs.getInt("situacaoComentarios")));
        comentario.setMotivo(rs.getString("motivo"));

        return comentario;
    }

    @Override
    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT * FROM comentarios WHERE idComentario = ?");

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
