package ProjectAkhirFinal;

import javafx.fxml.*;
import javafx.stage.*;
import javafx.scene.control.*;

public class MenuDeveloper {
    @FXML
    private TextArea infoMemori;
    
    @FXML
    private TextArea infoRuntime;
    
    private long totalWaktu;
    
    public void setTotalWaktu(long totalWaktu) {
        this.totalWaktu = totalWaktu;
        infoRuntime();
    }
    
    public long getTotalWaktu() {
        return totalWaktu;
    }
    
    @FXML
    private void kotakInfoMemori() {
        long mulai = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        
        long totalMemoryAwal = runtime.totalMemory();
        long freeMemoryAwal = runtime.freeMemory();
        long usedMemoryAwal = totalMemoryAwal - freeMemoryAwal;
        
        StringBuilder builder = new StringBuilder();
        builder.append("=== Informasi Memori Sebelum Garbage Collection ===\n");
        builder.append(String.format("Total Memory : %.2f MB\n", totalMemoryAwal / (1024.0 * 1024)));
        builder.append(String.format("Free Memory  : %.2f MB\n", freeMemoryAwal / (1024.0 * 1024)));
        builder.append(String.format("Used Memory  : %.2f MB\n", usedMemoryAwal / (1024.0 * 1024)));
        
        builder.append("\nMenjalankan Garbage Collector...\n");
        System.gc();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long totalMemoryAkhir = runtime.totalMemory();
        long freeMemoryAkhir = runtime.freeMemory();
        long usedMemoryAkhir = totalMemoryAkhir - freeMemoryAkhir;
        
        builder.append("\n=== Informasi Memori Setelah Garbage Collection ===\n");
        builder.append(String.format("Total Memory : %.2f MB\n", totalMemoryAkhir / (1024.0 * 1024)));
        builder.append(String.format("Free Memory  : %.2f MB\n", freeMemoryAkhir / (1024.0 * 1024)));
        builder.append(String.format("Used Memory  : %.2f MB\n", usedMemoryAkhir / (1024.0 * 1024)));
        
        infoMemori.setText(builder.toString());
        long selesai = System.currentTimeMillis();
        totalWaktu += (selesai - mulai);
        infoRuntime();
    }
    
    @FXML
    private void menuUtama() {
        Stage stage = (Stage) infoMemori.getScene().getWindow();
        stage.close();
    }
    
    private void infoRuntime() {
        infoRuntime.setText("Total Waktu Logika Program Aktif : " + totalWaktu + " ms");
    }
}

