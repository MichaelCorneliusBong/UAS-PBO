package ProjectAkhirFinal;

import java.io.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.control.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class FungsiUtama {
    @FXML
    private ListView<String> daftarBelanjaan;
    
    @FXML
    private Label infoDaftarBelanjaan;
    
    private FungsiDatabase fungsiDatabase;
    private ObservableList<String> kotakShoppingList;
    private List<FungsiDatabase.DatabaseFile> daftarBarang;
    private long totalWaktu = 0;
    
    @FXML
    public void initialize() {
        try {
            fungsiDatabase = new FungsiDatabase();
            kotakShoppingList = FXCollections.observableArrayList();
            daftarBelanjaan.setItems(kotakShoppingList);
            databaseJalan();
        } 
        catch (Exception e) {
        }
    }
    
    protected void databaseJalan() {
        try {
            long mulai = System.currentTimeMillis();
            
            daftarBarang = fungsiDatabase.daftarBarang();
            kotakShoppingList.clear();
            
            for (int i = 0; i < daftarBarang.size(); i++) {
                FungsiDatabase.DatabaseFile item = daftarBarang.get(i);
                String display = (i + 1) + ". " + item.getNamaBarang() + " (Catatan : " + item.getCatatanBarang() + ")";
                kotakShoppingList.add(display);
            }
            
            infoDaftarBelanjaan.setText("Jumlah Barang : " + daftarBarang.size());
            
            long selesai = System.currentTimeMillis();
            totalWaktu += (selesai - mulai);
        } 
        catch (Exception e) {
        }
    }
    
    @FXML
    private void TambahkanBarang() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TampilanTambahBarang.fxml"));
            Parent root = loader.load();
            
            MenuTambahkanBarang controller = loader.getController();
            controller.setMain(this);
            
            Stage stage = new Stage();
            stage.setTitle("Tambah Barang");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException e) {
        }
    }
    
    @FXML
    private void HapusBarang() {
        try {
            long mulai = System.currentTimeMillis();
            
            if (daftarBarang.isEmpty()) {
                kotakInfo("Peringatan", "Daftar Belanjaanmu Masih Kosong !");
                totalWaktu += (System.currentTimeMillis() - mulai);
                return;
            }
            
            int selectedIndex = daftarBelanjaan.getSelectionModel().getSelectedIndex();
            
            if (selectedIndex == -1) {
                kotakPeringatan("Peringatan", "Silakan Pilih Barang Yang Ingin Dihapus !");
                totalWaktu += (System.currentTimeMillis() - mulai);
                return;
            }
            
            Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
            konfirmasi.setTitle("Konfirmasi");
            konfirmasi.setHeaderText("Hapus Barang");
            konfirmasi.setContentText("Apakah Anda Yakin Ingin Menghapus Barang Ini ?");
            
            if (konfirmasi.showAndWait().get() == ButtonType.OK) {
                FungsiDatabase.DatabaseFile barangYangDihapus = daftarBarang.get(selectedIndex);
                
                if (fungsiDatabase.kurangiBarang(barangYangDihapus.getNamaBarang())) {
                    databaseJalan();
                    kotakInfo("Berhasil", "Barang Berhasil Dihapus Dari Database !");
                } 
                else {
                    kotakError("Error", "Gagal Menghapus Barang Dari Database !");
                }
            }
            
            totalWaktu += (System.currentTimeMillis() - mulai);
        } 
        catch (Exception e) {
        }
    }
    
    @FXML
    private void UpdateBarang() {
        try {
            long mulai = System.currentTimeMillis();
            
            if (daftarBarang.isEmpty()) {
                kotakInfo("Peringatan", "Daftar Belanjaanmu Masih Kosong !");
                totalWaktu += (System.currentTimeMillis() - mulai);
                return;
            }
            
            int selectedIndex = daftarBelanjaan.getSelectionModel().getSelectedIndex();
            
            if (selectedIndex == -1) {
                kotakPeringatan("Peringatan", "Silakan Pilih Barang Yang Ingin Diupdate !");
                totalWaktu += (System.currentTimeMillis() - mulai);
                return;
            }
            
            FungsiDatabase.DatabaseFile selectedItem = daftarBarang.get(selectedIndex);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TampilanUpdateBarang.fxml"));
            Parent root = loader.load();
            
            MenuUpdateBarang controller = loader.getController();
            controller.setMain(this);
            controller.setBarangData(selectedItem.getNamaBarang(), selectedItem.getCatatanBarang());
            
            Stage stage = new Stage();
            stage.setTitle("Update Barang");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            
            totalWaktu += (System.currentTimeMillis() - mulai);
            
        } 
        catch (IOException e) {
        }
    }
    
    @FXML
    private void resetDaftarBelanja() {
        try {
            long mulai = System.currentTimeMillis();
            
            if (daftarBarang.isEmpty()) {
                kotakInfo("Informasi", "Daftar Belanjaanmu Sudah Kosong !");
                totalWaktu += (System.currentTimeMillis() - mulai);
                return;
            }
            
            Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
            konfirmasi.setTitle("Konfirmasi Reset");
            konfirmasi.setHeaderText("Reset Semua Barang");
            konfirmasi.setContentText("Apakah Anda Yakin Ingin Menghapus Semua Barang Dari Database ?");
            
            if (konfirmasi.showAndWait().get() == ButtonType.OK) {
                if (fungsiDatabase.resetDaftarBarang()) {
                    databaseJalan();
                    kotakInfo("Berhasil", "Semua Barang Berhasil Dihapus Dari Database !");
                } else {
                    kotakError("Error", "Gagal Menghapus Semua Barang !");
                }
            }
            totalWaktu += (System.currentTimeMillis() - mulai);
        } 
        catch (Exception e) {
        }
    }
    
    @FXML
    private void menuDeveloper() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TampilanDeveloperMenu.fxml"));
            Parent root = loader.load();
            
            MenuDeveloper controller = loader.getController();
            controller.setTotalWaktu(totalWaktu);
            
            Stage stage = new Stage();
            stage.setTitle("Menu Developer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
            totalWaktu = controller.getTotalWaktu();  
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void keluarProgram() {
        Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
        konfirmasi.setTitle("Keluar");
        konfirmasi.setHeaderText("Keluar Aplikasi");
        konfirmasi.setContentText("Apakah Anda Yakin Ingin Keluar Program ?");
        
        if (konfirmasi.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) daftarBelanjaan.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void unduhPDF() {
        if (daftarBarang == null || daftarBarang.isEmpty()) {
            kotakPeringatan("Peringatan", "Tidak Ada Data Untuk Diexport !");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Simpan PDF");
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF File", "*.pdf")
        );
        chooser.setInitialFileName("Daftar Belanjaan.pdf");

        Stage stage = (Stage) daftarBelanjaan.getScene().getWindow();
        java.io.File file = chooser.showSaveDialog(stage);

        if (file == null) return;

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Paragraph title = new Paragraph("DAFTAR BELANJAAN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            table.addCell("No");
            table.addCell("Nama Barang");
            table.addCell("Catatan");

            int no = 1;
            for (FungsiDatabase.DatabaseFile b : daftarBarang) {
                table.addCell(String.valueOf(no++));
                table.addCell(b.getNamaBarang());
                table.addCell(b.getCatatanBarang());
            }

            document.add(table);
            document.close();

            kotakInfo("Berhasil", "PDF Berhasil Dibuat !");

        } 
        catch (Exception e) {
        }
    }
    
    
    public long getTotalWaktu() {
        return totalWaktu;
    }
    
    private void kotakInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void kotakPeringatan(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
