/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package doomies.HerramientasEntradaSalida;

import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Se conecta a la base de datos e intenta descargar los mapas, si no hay
 * conexion da los errores correspondientes
 *
 * @author Víctor y Nestor
 * @version 4
 * @since 4
 */
public class ConectionDatabase {

    /**
     * Devuelve una conexion a la base de datos, base para los demas metodos
     */
    private static Connection crearConexion() {
        Connection conn = null;
        try {
            /**
             * Se conecta con el driver devuelve ClassNotFoundException
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
            /**
             * Se conecta con la bdd devuelve SQLException
             */
            try {
                String url = "jdbc:mysql://localhost:3307/Doomies";
                String user = "cliente";
                String pass = "12345";
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                String url = "jdbc:mysql://localhost:3306/Doomies";
                String user = "cliente";
                String pass = "12345";
                conn = DriverManager.getConnection(url, user, pass);
            }
            return conn;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(new Frame(), "Driver no cargado", "Error", 1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new Frame(), "" + (((e + "").contains("Communications link failure")) ? "No hay conexion con la base de datos\n" : "Conexion a la base de datos fallida, compruebe que la base de datos esta operativa: " + e), "Conexion fallida", 2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new Frame(), "ERROR DESCONOCIDO: " + e, "Conexion fallida", 2);
        }
        String ruta = "";
        try {
            ruta = ConectionDatabase.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        if (!new File(ruta + "/mapas").exists() || !new File(ruta + "/mapas/mapa1.txt").exists()) {
            JOptionPane.showMessageDialog(new Frame(), "Error fatal, fallo en conectar a la bdd y no hay ficheros de juego\n"
                    + "Consiga conexion a la bdd, si el problema persiste contacte con el administrador.", "ERROR FATAL", 1);
            System.exit(0);
        }
        return conn;
    }

    /**
     * Descarga los mapas de la bdd
     *
     * @param conn Conexion a la bdd
     * @param fecha fecha de la actualizacion que se esta descargando
     * @throws SQLException
     */
    private static void descargarMapas(Connection conn, String fecha) throws SQLException {
        int i = 1;
        boolean morelvl = true;
        while (morelvl) {
            try {
                String tiles = getAllMapTiles(i, conn);
                String enemies = getAllEnemiesFromLvl(i, conn);
                String nivel = tiles + ":\n" + enemies;
                exportarNivelAFichero(nivel, i);
            } catch (Exception e) {
                morelvl = false;
            }
            i++;
        }
        String ruta = "";
        try {
            ruta = ConectionDatabase.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        try {
            FileWriter fw = new FileWriter(new File(ruta + "/mapas/update.date"));
            fw.write(fecha);
            fw.close();
        } catch (IOException e) {
            System.out.println("ERROR FATAL");
        }
    }

    /**
     * Exporta un nivel a un fichero
     *
     * @param nivel Informacion a escribir
     * @param lvlNum numero del nivel
     */
    private static void exportarNivelAFichero(String nivel, int lvlNum) {
        String ruta = "";
        try {
            ruta = ConectionDatabase.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = new File(ruta + "/mapas/mapa" + lvlNum + ".txt");
        try {
            if (!new File(ruta + "/mapas").exists()) {
                new File(ruta + "/mapas").mkdir();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            fw.write(nivel);
            fw.close();
        } catch (IOException e) {
            System.out.println("ERROR FATAL");
        }

    }

    /**
     * Extrae los enemigos de un mapa de la bdd
     *
     * @param lvl Nivel a sacar
     * @param conn Conexion con la bdd
     * @return Todos los enemigos en un string
     * @throws SQLException
     */
    private static String getAllEnemiesFromLvl(int lvl, Connection conn) throws SQLException {
        String enemigos = "";
        Statement stmt = conn.createStatement();
        ResultSet rsEnemigos = stmt.executeQuery("SELECT * FROM enemigo WHERE id_nivel=" + lvl + ";");
        try {
            while (rsEnemigos.next()) {
                enemigos += rsEnemigos.getString(2) + ";" + rsEnemigos.getString(3) + ";" + rsEnemigos.getString(4);
                enemigos += "\n";
            }
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
        if (enemigos.length() <= 0) {
            throw new SQLException();
        }
        stmt.close();
        return enemigos;
    }

    /**
     * Extrae los los tiles de un mapa de la bdd
     *
     * @param lvl nivel a sacar
     * @param conn Conexion con la bdd
     * @return Todos los tiles en un string
     * @throws SQLException
     */
    private static String getAllMapTiles(int lvl, Connection conn) throws SQLException {
        String tiles = "";
        Statement stmt = conn.createStatement();
        ResultSet rsTiles = stmt.executeQuery("SELECT * FROM mapa WHERE id=" + lvl + ";");
        rsTiles.next();
        for (int i = 2; i <= 14; i++) {
            tiles += reemplazarAX(rsTiles.getString(i)) + "\n";
        }
        return tiles;
    }

    /**
     *
     * @param cadena Linea no formateada
     * @return String convertido a formato nuevo
     */
    public static String reemplazarAX(String cadena) {
        int i = 0;
        if (!cadena.substring(i, i + 1).equals("-")) {//Se comprueba que lo que esta en primera posicion sea distinto de "-"

            try {
                int block = Integer.parseInt(cadena.substring(0, 2));//Se comprueba si lo que se mete al principio es una pareja de numeros y no otras cosas
                i += 2;//Se salta 2 para que no se vuelva a comprobar 
            } catch (Exception e) {//si lo primero no son numeros se pone i = 0 para que se empieze a comprobar desde el principio
                i = 0;
            }
        }
        while (i < cadena.length()) {
            if (!cadena.substring(i, i + 1).equals("-")) {//Se comprueba que la primera posicion es distinta a "-", si es distinta da un error porque ya se habia comprobado antes si era un numero
                System.err.println("DA ERROR");//
                System.exit(0);
            } else if ((cadena.length() - 2) > (i) && cadena.substring(i + 1, i + 2).equals("-")) {//Se comprueba que si no es el final del String y hay un "-" seguido de otro "-" sigue con el bucle
                i++;
                continue;
            } else if (cadena.length() - 2 > (i)) {//Se comprueba otra vez si no es el final del String

                try {
                    int block = Integer.parseInt(cadena.substring(i + 1, i + 3));//Se comprueba que lo que se mete es una pareja de numeros y no otras cosas, sino da error
                } catch (Exception e) {
                    System.err.println("DA ERROR");
                    System.exit(0);
                }
                i += 3;//Se le suma 3 a la i para que no se vuelva a comprobar la pareja de numeros
            } else {
                break;
            }
        }
        if (cadena.substring(0, 1).equals("-")) {//Se comprueba si el String empieza por "-" y si lo hace pone "xx" al principio
            cadena = "xx" + cadena;
        }
        while (cadena.contains("--")) {//Se comprueba que hay "--" y si las hay mete "xx" entre ellas
            cadena = cadena.replaceAll("--", "-xx-");
        }
        if (cadena.substring((cadena.length() - 1), cadena.length()).equals("-")) {//Se comprueba si acaba en "-" y si acaba asi añade "xx" al final
            cadena = cadena + "xx";
        }
        return cadena;
    }

    public static void intentarCargarMapas() {
        try {
            String fecha = "";
            Connection conn = crearConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fecha_actualizacion FROM nivel limit 1;");
            while (rs.next()) {
                fecha = rs.getString(1).split(" ")[0];
            }
            rs.close();
            stmt.close();
            String fechaInterna = getFecha();
            try {
                int oldAño = Integer.parseInt(fechaInterna.split("-")[0]);
                int oldMes = Integer.parseInt(fechaInterna.split("-")[1]);
                int oldDia = Integer.parseInt(fechaInterna.split("-")[2]);

                int año = Integer.parseInt(fecha.split("-")[0]);
                int mes = Integer.parseInt(fecha.split("-")[1]);
                int dia = Integer.parseInt(fecha.split("-")[2]);

                if (año > oldAño) {
                    descargarMapas(conn, fecha);
                } else if (año < oldAño) {
                    cerrarConexion(conn);
                    JOptionPane.showMessageDialog(new Frame(), "Base desactualizada o archivos corruptos");
                    return;
                } else if (mes > oldMes) {
                    descargarMapas(conn, fecha);
                } else if (mes < oldMes) {
                    JOptionPane.showMessageDialog(new Frame(), "Base desactualizada o archivos corruptos");
                    cerrarConexion(conn);
                    return;
                } else if (dia > oldDia) {
                    descargarMapas(conn, fecha);
                } else if (dia < oldDia) {
                    JOptionPane.showMessageDialog(new Frame(), "Base desactualizada o archivos corruptos");
                    cerrarConexion(conn);
                    return;
                }
                cerrarConexion(conn);
            } catch (Exception e) {
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        } catch (Exception e) {
        }
    }

    private static void cerrarConexion(Connection conn) throws SQLException {
        conn.close();
    }

    private static String getFecha() {
        String ruta = "";
        try {
            ruta = ConectionDatabase.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString().substring(1).replaceAll("Doomies.jar|%20", "");
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        File f = new File(ruta + "/mapas/update.date");
        String fecha = "0-0-0";
        try {
            if (!f.exists()) {
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                fw.write("0-0-0");
                fw.close();
            }
            Scanner sc = new Scanner(f);
            fecha = sc.next();
            sc.close();
        } catch (Exception e) {
            return "0-0-0";
        }
        return fecha;
    }
}
