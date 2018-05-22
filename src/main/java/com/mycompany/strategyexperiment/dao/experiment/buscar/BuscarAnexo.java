package com.mycompany.strategyexperiment.dao.experiment.buscar;

import com.mycompany.strategyexperiment.dao.control.ConnectionDAO;
import com.mycompany.strategyexperiment.model.Anexo;
import com.mycompany.strategyexperiment.model.IEntidadeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscarAnexo implements Buscar {

    private Anexo carregarObjeto(ResultSet rs) throws SQLException {
        Anexo anexo = new Anexo();

        anexo.setIdAnexo(rs.getInt("idAnexo"));
        anexo.getAta().setIdAta(rs.getInt("idAta"));
        anexo.setDescricao(rs.getString("descricao"));
        anexo.setOrdem(rs.getInt("ordem"));
        anexo.setArquivo(rs.getBytes("arquivo"));

        return anexo;
    }

    @Override
    public IEntidadeDAO buscarPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT anexos.* FROM anexos "
                    + "WHERE idAnexo = ?");

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
