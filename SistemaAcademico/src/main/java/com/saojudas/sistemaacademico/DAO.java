package com.saojudas.sistemaacademico;

import java.sql.*;

public class DAO {
    public boolean existe (Usuario usuario) throws Exception{
        String sql = "SELECT * FROM tb_usuario WHERE nome = ? AND senha = ?";
        ConnectionFactory factory = new ConnectionFactory();
        try(Connection conn = factory.obtemConexao();
                PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setString(1,usuario.getNome());
                    ps.setString(2,usuario.getSenha());
                    try(ResultSet rs = ps.executeQuery()){
                        return rs.next();
                    }
                }
    }
    public Curso [] obterCursos() throws Exception {
        String sql = "SELECT * FROM tb_curso";
        ConnectionFactory factory = new ConnectionFactory();
        try(Connection conn = factory.obtemConexao();
            PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
                ResultSet rs = ps.executeQuery()){
            
            int totalDeCursos = rs.last() ? rs.getRow() : 0;
            Curso [] cursos = new Curso[totalDeCursos];
            rs.beforeFirst();
            int contador = 0;
            while(rs.next()){
                int id = rs.getInt("id");                    
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                cursos[contador++] = new Curso(id, nome, tipo);
                }
            return cursos;
            }
    }
}
