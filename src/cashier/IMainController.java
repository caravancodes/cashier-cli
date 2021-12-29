package cashier;
/*
 * Created by faisalamir on 29/12/21
 * cashier-cli
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2021 FrogoBox Inc.
 * All rights reserved
 *
 */

public interface IMainController {

    // Method Dari Laporan untuk mencari index dengan parameter array dan kata
    int mCari(String[] array, String kata);

    // Method ini berfungsi untuk menambahkan data array dengan tipe array String
    void addDataArrayString(String[] array, String value);

    // Method ini berfungsi untuk menambahkan data array dengan tipe array int
    void addDataArrayInt(int[] array, int value);

    // Method ini berfungsi untuk menghapus data array int
    void deleteDataArrayInt(int[] array, int index);

    // Method ini berfungsi untuk menghapus data array String
    void deleteDataArrayString(String[] array, int index);

    // Method ini berfungsi untuk mengubah data array String
    void updateDataArrayString(String[] array, int index, String value);

    // Method ini berfungsi untuk mengubah data array String
    void updateDataArrayInt(int[] array, int index, int value);

    // Method ini berfungsi untuk menambahkan menu makanan
    void addMenuMakanan(String valueNamaMakanan, String valueJenisMakanan, int valueHargaMakanan);

    // Method ini berfungsi untuk menghapus menu makanan
    void deleteMenuMakanan(String valueMakanan);

    // Method ini berfungsi untuk mengubah menu makanan
    void updateMenuMakanan(int index, String valueNamaMakanan, String valueJenisMakanan, int valueHargaMakanan);

    // Method ini berfungsi untuk menhapus semua menu makanan
    void nukeMenuMakanan();

    // Method ini berfungsi untuk menhapus semua data penjualan
    void nukeArrayTempJual();

    // Method ini berfungsi untuk menampikan daftar menu makanan
    void readMenuMakanan(String[] menuMakanan, String[] jenisMakanan, int[] hargaMakanan);

    // Method ini berfungsi untuk menampikan daftar menu makanan yang sudah di cari
    void readMenuMakananSearched(String[][] sortedMenu);

    // Method ini berfungsi untuk menampikan daftar menu makanan yang sudah di sortir
    void sortMenuMakanan(String[] menuMakanan, String[] jenisMakanan, int[] hargaMakanan, String stateMenu);

    // Method ini berfungsi untuk mencari menu
    void searchMenu(String state);

    // Method ini berfungsi untuk mencari menu pada menu kasir
    String[][] searchMenuMakanan(String[] menuMakanan, int[] hargaMakanan, String[] jenisMakanan, String stateMenu, String keyword);

    // Method ini berfungsi untuk menampilkan laporan penjualan pada menu owner
    void readPenjualan();

    // Method ini berfungsi untuk menampilkan makanan paling sering di beli
    void readPenjualanLaris(String[][] arr);

    // -----------------------------------------------------------------------------------------------------------------

    // Method ini untuk mendapatkan tanggal hari ini
    String getDateTimeNow();

    // Method ini untuk menambahkan data menu secara otomatis
    void initAutoMenu(int startIndex);

}
