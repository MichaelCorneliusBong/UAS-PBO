package ProjectAkhirFinal;

import java.sql.*;
import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.control.*;

public class MenuTambahkanBarang {
    
    
    @FXML
    private TextField kotakNamaBarang;
    
    @FXML
    private TextField kotakCatatanBarang;
    
    private FungsiUtama fungsiUtama;
    
    public void setMain(FungsiUtama Main) {
        this.fungsiUtama = Main;
    }
    
    @FXML
    private void tambahBarang() {
        String namaBarang = kotakNamaBarang.getText().trim();
        String catatan = kotakCatatanBarang.getText().trim();

        if (namaBarang.isEmpty()) {
            kotakError("Error", "Nama Barang Tidak Boleh Kosong !");
            return;
        }

        if (catatan.isEmpty() || catatan.length() >= 30) {
            kotakError("Error", "Catatan Tidak Boleh Kosong Atau Terlalu Panjang !");
            return;
        }

        try {
            Connection connect = KoneksiDatabase.getConnection();

            PreparedStatement state = connect.prepareStatement("SELECT COUNT(*) FROM shoppingList WHERE namaBarang = ?");
            state.setString(1, namaBarang);
            ResultSet set = state.executeQuery();
            set.next();

            if (set.getInt(1) > 0) {
                kotakError("Error", "Barang Sudah Ada Dalam Daftar Belanjaanmu !");
            } 
            else {
                PreparedStatement insert = connect.prepareStatement("INSERT INTO shoppingList (namaBarang, catatanBarang) VALUES (?, ?)");
                insert.setString(1, namaBarang);
                insert.setString(2, catatan);
                insert.executeUpdate();
                kotakInfo("Berhasil", "Barang Berhasil Ditambahkan Ke Database !");
                
                if (fungsiUtama != null) {
                    fungsiUtama.databaseJalan();
                }
                menuUtama();
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
