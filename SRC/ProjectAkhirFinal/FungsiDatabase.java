package ProjectAkhirFinal;

import java.sql.*;
import java.util.*;

public class FungsiDatabase {
        public class DatabaseFile {
        private String namaBarang;
        private String catatanBarang;

        public DatabaseFile(String namaBarang, String catatanBarang) {
            this.namaBarang = namaBarang;
            this.catatanBarang = catatanBarang;
        }

        public String getNamaBarang() {
            return namaBarang;
        }

        public void setNamaBarang(String namaBarang) {
            this.namaBarang = namaBarang;
        }

        public String getCatatanBarang() {
            return catatanBarang;
        }

        public void setCatatanBarang(String catatanBarang) {
            this.catatanBarang = catatanBarang;
        }

        public String catatan() {
            return namaBarang + " (Catatan : " + catatanBarang + ")";
        }
    }
        
    public boolean tambahBarang(String namaBarang, String catatan) {
        String sql = "INSERT INTO shoppingList (namaBarang, catatanBarang) VALUES (?, ?)";
        
        try {
            Connection connect = KoneksiDatabase.getConnection();
            PreparedStatement state = connect.prepareStatement(sql);
            
            state.setString(1, namaBarang);
            state.setString(2, catatan);
            
            int rowsAffected = state.executeUpdate();
            return rowsAffected > 0;
            
        } 
        catch (SQLException | ClassNotFoundException e) {
            return false;
        } 
    }
    
    public List<DatabaseFile> daftarBarang() {
        List<DatabaseFile> items = new ArrayList<DatabaseFile>();
        String sql = "SELECT * FROM shoppingList";
        
        try {
            Connection connect = KoneksiDatabase.getConnection();
            Statement state = connect.createStatement();
            ResultSet set = state.executeQuery(sql);
            
            while (set.next()) {
                DatabaseFile item = new DatabaseFile(
                    set.getString("namaBarang"),
                    set.getString("catatanBarang")
                );
                items.add(item);
            }  
        } 
        catch (SQLException | ClassNotFoundException e) {
        }
        
        return items;
    }
    
    public boolean updateBarang(String namaBarangLama, String namaBarangBaru, String catatanBaru) {
        String sql = "UPDATE shoppingList SET namaBarang = ?, catatanBarang = ? WHERE namaBarang = ?";
        
        try {
            Connection connect = KoneksiDatabase.getConnection();
            PreparedStatement state = connect.prepareStatement(sql);
            
            state.setString(1, namaBarangBaru);
            state.setString(2, catatanBaru);
            state.setString(3, namaBarangLama);
            
            int rowsAffected = state.executeUpdate();
            return rowsAffected > 0;
            
        } 
        catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }
    
    public boolean kurangiBarang(String namaBarang) {
        String sql = "DELETE FROM shoppingList WHERE namaBarang = ?";
        
        try {
            Connection connect = KoneksiDatabase.getConnection();
            PreparedStatement state = connect.prepareStatement(sql);
            
            state.setString(1, namaBarang);
            
            int rowsAffected = state.executeUpdate();
            return rowsAffected > 0;
            
        } 
        catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }
    
    public boolean resetDaftarBarang() {
        String sql = "DELETE FROM shoppingList";
        
        try {
            Connection connect = KoneksiDatabase.getConnection();
            Statement state = connect.createStatement();
            
            int rowsAffected = state.executeUpdate(sql);
            return true;
            
        } 
        catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }
    
    public int hitungBarang() {
        String sql = "SELECT COUNT (*) FROM shoppingList";
        
        try {
            Connection connect = KoneksiDatabase.getConnection();
            Statement state = connect.createStatement();
            ResultSet set = state.executeQuery(sql);
            
            if (set.next()) {
                int count = set.getInt(1);
                return count;
            }
        } 
        catch (SQLException | ClassNotFoundException e) {
        }
        
        return 0;
    }
}
