package ProjectAkhirFinal;

import java.sql.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.control.*;

public class MenuUpdateBarang {
    
    @FXML
    private TextField kotakNamaBarang;
    
    @FXML
    private TextField kotakCatatanBarang;
    
    private FungsiUtama fungsiUtama;
    private String namaBarangLama;
    
    public void setMain(FungsiUtama Main) {
        this.fungsiUtama = Main;
    }
    
    public void setBarangData(String namaBarang, String catatan) {
        this.namaBarangLama = namaBarang;
        kotakNamaBarang.setText(namaBarang);
        kotakCatatanBarang.setText(catatan);
    }
    
    @FXML
    private void updateBarang() {
        String namaBarangBaru = kotakNamaBarang.getText().trim();
        String catatanBaru = kotakCatatanBarang.getText().trim();

        if (namaBarangBaru.isEmpty()) {
            kotakError("Error", "Nama Barang Tidak Boleh Kosong !");
            return;
        }

        if (catatanBaru.isEmpty() || catatanBaru.length() >= 30) {
            kotakError("Error", "Catatan Tidak Boleh Kosong Atau Terlalu Panjang !");
            return;
        }

        try {
            Connection connect = KoneksiDatabase.getConnection();

            if (!namaBarangBaru.equals(namaBarangLama)) {
                PreparedStatement checkState = connect.prepareStatement("SELECT COUNT(*) FROM shoppingList WHERE namaBarang = ?");
                checkState.setString(1, namaBarangBaru);
                ResultSet checkSet = checkState.executeQuery();
                checkSet.next();

                if (checkSet.getInt(1) > 0) {
                    kotakError("Error", "Nama Barang Sudah Ada Dalam Daftar !");
                    return;
                }
            }

            PreparedStatement updateState = connect.prepareStatement("UPDATE shoppingList SET namaBarang = ?, catatanBarang = ? WHERE namaBarang = ?");
            updateState.setString(1, namaBarangBaru);
            updateState.setString(2, catatanBaru);
            updateState.setString(3, namaBarangLama);
            
            int rowsAffected = updateState.executeUpdate();
            
            if (rowsAffected > 0) {
                kotakInfo("Berhasil", "Barang Berhasil Diupdate Di Database !");
                
                if (fungsiUtama != null) {
                    fungsiUtama.databaseJalan();
                }
                menuUtama();
                
            } 
            else {
                kotakError("Error", "Gagal Meng-Update Barang !");
            }
        } 
        catch (Exception e) {
        }
    }

    @FXML
    private void tutupMenu() {
        menuUtama();
    }
    
    private void menuUtama() {
        Stage stage = (Stage) kotakNamaBarang.getScene().getWindow();
        stage.close();
    }
    
    private void kotakInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void kotakError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}