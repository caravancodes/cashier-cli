package cashier;
/*
 * Created by faisalamir on 15/12/21
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static cashier.MainConfig.INSTANCE.*;

public class Main implements IMain, IMainUI, IMainController {

    private boolean STATE_LOGIN = false; // variable untuk pengecekan aplikasi sedang login atau tidak
    private final Scanner sc = new Scanner(System.in); // scanner inputan library dari java

    private int INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU = 0; // variable index untuk mengetahui posisi terakhir array daftar menu
    private int INDEX_VALUED_ARRAY_DATA_JUAL = 0; // variable index untuk mengetahui posisi terakhir array data penjualan
    private int INDEX_VALUED_ARRAY_DATA_JUAL_TEMP = 0; // variable index untuk mengetahui posisi terakhir array data penjualan sementara
    private int ID_PENJUALAN = 0; // variable index untuk mengetahui sudah berapa kali transaksi penjualan yang sudah di lakukan oleh kasir
    // -----------------------------------------------------------------------------------------------------------------

    // Initiasi Variable Global ----------------------------------------------------------------------------------------

    // Data Daftar Menu Utama
    private final String[] arrayDataNamaMakanan = new String[SIZE_DEFAULT_ARRAY]; // Nama Makanan, Contoh : Martabak Manis
    private final String[] arrayDataJenisMakanan = new String[SIZE_DEFAULT_ARRAY]; // Jenis Makanan, Contoh : Makanan, Minuman, Snack
    private final int[] arrayDataHargaMakanan = new int[SIZE_DEFAULT_ARRAY]; // Harga Makanan, Contoh : 1000000

    // Data Penjualan
    private final String[][] arrayJual = new String[SIZE_DEFAULT_ARRAY][7]; // Array ini untuk mencatat semua penjualan yang ada dan dijadikan untuk acuan laporan penjualan owner
    private final String[][] arrayJualTemp = new String[SIZE_DEFAULT_ARRAY][7]; // Array ini untuk menampung sementara yang terjadi saat penjualan pada menu kasir
    // Index ke 0 -> Id Penjualan               ---> didapat dari variable ID_PENJUALAN
    // Index ke 1 -> Tanggal Penjualan          ---> didapat dari tanggal saat terjadi transaksi di menu kasir
    // Index ke 2 -> Nama Makanan               ---> didapat dari nama makanan yang dipilih kasir
    // Index ke 3 -> Jenis Makanan              ---> didapat dari jenis makanan yang dipilih kasir
    // Index ke 4 -> Harga Makanan              ---> didapat dari harga makanan yang dipilih kasir
    // Index ke 5 -> Jumlah Makanan             ---> inputan dari kasir berapa jumlah makanan yang di pesan
    // Index ke 6 -> SubTotal Harga Makanan     ---> harga makanan * jumlah makanan

    // cashier.Main Program ----------------------------------------------------------------------------------------------------
    // Dimana Semua Method Kode Program di jalan disini
    public static void main(String[] args) {
        /*
         * Memanggil Fungsi cashier.Main Login
         * Alasan kenapa main hanya 1 fungsi saja, karena mainLogin() bersifat flexible bisa di panggil dimana saja
         * mainLogin() di panggil dibanyak tempat
         */
        new Main().mainLogin();
    }

    // Method Menu Tampilan --------------------------------------------------------------------------------------------
    // Disini tempatnya Untuk Tampilan tampilan saja

    // Ini adalah method untuk menu tampilan ketika login
    public void loginUI() {
        System.out.println(LINE_BORDER);
        System.out.println("SELAMAT DATANG DI " + STORE_NAME.toUpperCase());
        System.out.println("UNTUK MEMULAI APLIKASI " + APP_NAME.toUpperCase());
        System.out.println("SILAHKAN LOGIN UNTUK MELANJUTKAN");
        System.out.println(LINE_BORDER);
    }

    // Ini adalah method untuk menu tampilan ketika aplikasi di berhentikan
    public void exitUI() {
        System.out.println(LINE_BORDER);
        System.out.println("APLIKASI BERHENTI !!!");
        System.out.println("TERIMA KASIH TELAH MENGGUNAKAN APLIKASI");
        System.out.println("SALAM DARI " + DEVELOPER_NAME.toUpperCase() + " DEVELOPER PROGRAM INI");
        System.out.println(LINE_BORDER);
    }

    // Ini adalah method untuk menu tampilan saat user berhasil login, memunculkan sapaan
    public void greetingUI(String name, String role) {
        System.out.println(">> Hallo " + name + " | " + role);
    }

    // Ini adalah method untuk menu tampilan saat berhasil mensorting
    public void sortUI(String state) {
        System.out.println(LINE_BORDER);
        System.out.println("Berhasil Mengurutkan Menu Berdasarkan " + state);
        readMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan);
    }

    // Ini adalah method untuk menu tampilan Log atau history dari user saat login
    public void logTimeUI(String status) {
        System.out.println(LINE_BORDER);
        System.out.println(">> Waktu " + status + " : " + getDateTimeNow());
    }

    // Method Operator Array -------------------------------------------------------------------------------------------
    // Disini tempatnya method untuk segala macam operasi array yang sudah dibuat, dari menambah, menghapus, mengubah, dan membaca

    // Method Dari Laporan untuk mencari index dengan parameter array dan kata
    public int mCari(String[] array, String kata) {
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                if (array[i].equalsIgnoreCase(kata)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    // Method ini berfungsi untuk menambahkan data array dengan tipe array String
    public void addDataArrayString(String[] array, String value) {
        int index = 0; // disini deklarasi dulu didalam method index awalnya 0
        for (int i = 0; i < array.length; i++) { // melakukan perulangan
            if (array[i] != null) { // di check apakah array di posisi i / diposisi perulangan tidak null
                // maka nilai index di ubah menjadi posisi di tambah 1, karena kita akan menambah data baru di paling akhir dari array yang sudah terisi
                // Contoh : ada Array {0, 1, 9, null, null}
                // i berada di angka 9, dimana itu adalah index ke 2, karena ingin menambahkan maka index harus 3 yaitu i+1
                index = i + 1;
            }
        }
        array[index] = value; // array dengan index yang sudah di temukan di isi dengan value dari parameter method
    }

    // Method ini berfungsi untuk menambahkan data array dengan tipe array int
    public void addDataArrayInt(int[] array, int value) { // Parameternya Array dan value
        int index = 0; // disini deklarasi dulu didalam method index awalnya 0
        for (int i = 0; i < array.length; i++) { // melakukan perulangan
            if (array[i] != 0) { // di check apakah array di posisi i / diposisi perulangan tidak null
                /* maka nilai index di ubah menjadi posisi di tambah 1, karena kita akan menambah data baru di paling akhir dari array yang sudah terisi
                 * Contoh : ada Array {0, 1, 9, null, null}
                 * i berada di angka 9, dimana itu adalah index ke 2, karena ingin menambahkan maka index harus 3 yaitu i+1
                 */
                index = i + 1;
            }
        }
        array[index] = value; // array dengan index yang sudah di temukan di isi dengan value dari parameter method
    }

    // Method ini berfungsi untuk menghapus data array int
    public void deleteDataArrayInt(int[] array, int index) { // Parameter array dan index dimana data ingin di hapus
        for (int i = index; i < array.length - 1; i++) { // dilakukan perulangan sesuai panjang dari array - 1
            /*
             * Karena dilakukan delete data array, sebenarnya kita tidak menghapus isi dari array, kita hanya menggeser dan menimpanya
             * Contoh : {a, b, c, d, e, f, g}
             * Kita ingin menghapus huruf d, kita perlu tau huruf d ada di index mana,
             * Ketemu Index Huruf d ada di index 3, sehingga kita melakukan perulangan dengan dimulai dari index 3, sampai index terakhir - 1 yaitu index ke 5
             * Setelah perulangan dan penimpaan jadinya {a, b, c, e, f, g, g}
             */
            array[i] = array[i + 1];
        }
        array[array.length - 1] = 0; // Kemudian Kita Mengganti Nilai Array paling akhirnya dengan null untuk string array, 0 untuk int array
    }

    // Method ini berfungsi untuk menghapus data array String
    public void deleteDataArrayString(String[] array, int index) { // Parameter array dan index dimana data ingin di hapus
        for (int i = index; i < array.length - 1; i++) { // dilakukan perulangan sesuai panjang dari array - 1
            /*
             * Karena dilakukan delete data array, sebenarnya kita tidak menghapus isi dari array, kita hanya menggeser dan menimpanya
             * Contoh : {a, b, c, d, e, f, g}
             * Kita ingin menghapus huruf d, kita perlu tau huruf d ada di index mana,
             * Ketemu Index Huruf d ada di index 3, sehingga kita melakukan perulangan dengan dimulai dari index 3, sampai index terakhir - 1 yaitu index ke 5
             * Setelah perulangan dan penimpaan jadinya {a, b, c, e, f, g, g}
             */
            array[i] = array[i + 1];
        }
        array[array.length - 1] = null; // Kemudian Kita Mengganti Nilai Array paling akhirnya dengan null untuk string array, 0 untuk int array
    }

    // Method ini berfungsi untuk mengubah data array String
    public void updateDataArrayString(String[] array, int index, String value) {
        array[index] = value;
    }

    // Method ini berfungsi untuk mengubah data array String
    public void updateDataArrayInt(int[] array, int index, int value) {
        array[index] = value;
    }

    // Method ini berfungsi untuk menambahkan menu makanan
    public void addMenuMakanan(String valueNamaMakanan, String valueJenisMakanan, int valueHargaMakanan) {
        // dikarenakan menu makanan sendiri terdapat 3 array, jadi memanggil 3x method fungsi addDataArrayString
        addDataArrayString(arrayDataNamaMakanan, valueNamaMakanan);
        addDataArrayString(arrayDataJenisMakanan, valueJenisMakanan);
        addDataArrayInt(arrayDataHargaMakanan, valueHargaMakanan);
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU++; // Indexnya bertambah sebagai pengingat sudah ada 1 daftar menu di tambahkan
    }

    // Method ini berfungsi untuk menghapus menu makanan
    public void deleteMenuMakanan(String valueMakanan) {

        boolean stateCheck = false; // di deklarasikan variabel status checking apakah makanan inputan user ada di daftar menu atau tidak
        int index = 0; // dideklarasikan variable indexnya

        for (int i = 0; i < arrayDataNamaMakanan.length; i++) { // dilakukan perulangan array data makanan
            if (arrayDataNamaMakanan[i] != null) { // dicek apakah tidak sama dengan null
                if (arrayDataNamaMakanan[i].equalsIgnoreCase(valueMakanan)) { // kemudian di check apakah ada makanan dengan nama makanan sesuai inputan user
                    index = i; // ketemu indexnya yang akan di hapus
                    stateCheck = true; // ubah status checknya menjadi true artinya nama makanan dari user sudah ada
                    break; // perulangan di paksa berhenti
                }
            }
        }

        System.out.println(LINE_BORDER);
        if (stateCheck) {
            // dikarenakan menu makanan sendiri terdapat 3 array, jadi memanggil 3x method fungsi deleteDataArrayString
            deleteDataArrayString(arrayDataNamaMakanan, index);
            deleteDataArrayString(arrayDataJenisMakanan, index);
            deleteDataArrayInt(arrayDataHargaMakanan, index);
            System.out.println("Menu " + valueMakanan + " Berhasil di hapus");
        } else {
            System.out.println("Menu Tidak ada ....");
        }
        System.out.println(LINE_BORDER);
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU--;

    }

    // Method ini berfungsi untuk mengubah menu makanan
    public void updateMenuMakanan(int index, String valueNamaMakanan, String valueJenisMakanan, int valueHargaMakanan) {
        // dikarenakan menu makanan sendiri terdapat 3 array, jadi memanggil 3x method fungsi updateDataArrayString
        updateDataArrayString(arrayDataNamaMakanan, index, valueNamaMakanan);
        updateDataArrayString(arrayDataJenisMakanan, index, valueJenisMakanan);
        updateDataArrayInt(arrayDataHargaMakanan, index, valueHargaMakanan);
    }

    // Method ini berfungsi untuk menhapus semua menu makanan
    public void nukeMenuMakanan() {
        // dikarenakan menu makanan sendiri terdapat 3 array, jadi memanggil 3x array untuk di kosongkan nilainya
        for (int i = 0; i <= INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU; i++) {
            arrayDataNamaMakanan[i] = null;
            arrayDataJenisMakanan[i] = null;
            arrayDataHargaMakanan[i] = 0;
        }
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU = 0; // indexnya dirubah menjadi 0 semua
    }

    // Method ini berfungsi untuk menhapus semua data penjualan
    public void nukeArrayTempJual() {
        for (int i = 0; i <= INDEX_VALUED_ARRAY_DATA_JUAL_TEMP; i++) {
            arrayJualTemp[i][0] = null;
            arrayJualTemp[i][1] = null;
            arrayJualTemp[i][2] = null;
            arrayJualTemp[i][3] = null;
            arrayJualTemp[i][4] = null;
            arrayJualTemp[i][5] = null;
            arrayJualTemp[i][6] = null;
        }
        INDEX_VALUED_ARRAY_DATA_JUAL_TEMP = 0;
    }

    // Method ini berfungsi untuk menampikan daftar menu makanan
    public void readMenuMakanan(String[] menuMakanan, String[] jenisMakanan, int[] hargaMakanan) {
        System.out.println(LINE_BORDER);
        if (INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU == 0) {
            System.out.println("Daftar Menu Kosong");
        } else {
            System.out.println(">> Daftar Menu " + STORE_NAME);
            for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
                if (menuMakanan[i] != null) {
                    System.out.println((i + 1) + ".\t [" + jenisMakanan[i] + "] \t" + hargaMakanan[i] + "\t - " + menuMakanan[i]);
                }
            }
        }
        System.out.println(LINE_BORDER);
    }

    // Method ini berfungsi untuk menampikan daftar menu makanan yang sudah di cari
    public void readMenuMakananSearched(String[][] sortedMenu) {
        for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
            if (sortedMenu[i][0] != null) {
                System.out.println((i + 1) + ".\t [" + sortedMenu[i][2] + "] \t" + sortedMenu[i][1] + "\t - " + sortedMenu[i][0]);
            }
        }
    }

    // Method ini berfungsi untuk menampikan daftar menu makanan yang sudah di sortir
    public void sortMenuMakanan(String[] menuMakanan, String[] jenisMakanan, int[] hargaMakanan, String stateMenu) {
        for (int i = 0; i < SIZE_DEFAULT_ARRAY - 1; i++) {
            for (int j = 0; j < SIZE_DEFAULT_ARRAY - 1; j++) {

                if (stateMenu.equals(STATE_MENU_MAKANAN)) {
                    if (menuMakanan[i] != null) {
                        if (menuMakanan[j] != null) {
                            if (menuMakanan[j + 1] != null) {
                                if (menuMakanan[j].compareToIgnoreCase(menuMakanan[j + 1]) > 0) {
                                    String pickedMenuMakanan = menuMakanan[j];
                                    String pickedJenisMakanan = jenisMakanan[j];
                                    int pickedHargaMakanan = hargaMakanan[j];

                                    menuMakanan[j] = menuMakanan[j + 1];
                                    jenisMakanan[j] = jenisMakanan[j + 1];
                                    hargaMakanan[j] = hargaMakanan[j + 1];

                                    menuMakanan[j + 1] = pickedMenuMakanan;
                                    jenisMakanan[j + 1] = pickedJenisMakanan;
                                    hargaMakanan[j + 1] = pickedHargaMakanan;
                                }
                            }
                        }

                    }

                } else if (stateMenu.equals(STATE_MENU_HARGA_TERMURAH)) {
                    if (hargaMakanan[i] != 0) {
                        if (hargaMakanan[j] != 0) {
                            if (hargaMakanan[j + 1] != 0) {
                                if (hargaMakanan[j] > hargaMakanan[j + 1]) {
                                    String pickedMenuMakanan = menuMakanan[j];
                                    String pickedJenisMakanan = jenisMakanan[j];
                                    int pickedHargaMakanan = hargaMakanan[j];

                                    menuMakanan[j] = menuMakanan[j + 1];
                                    jenisMakanan[j] = jenisMakanan[j + 1];
                                    hargaMakanan[j] = hargaMakanan[j + 1];

                                    menuMakanan[j + 1] = pickedMenuMakanan;
                                    jenisMakanan[j + 1] = pickedJenisMakanan;
                                    hargaMakanan[j + 1] = pickedHargaMakanan;
                                }
                            }
                        }
                    }
                } else if (stateMenu.equals(STATE_MENU_HARGA_TERMAHAL)) {
                    if (hargaMakanan[i] != 0) {
                        if (hargaMakanan[j] != 0) {
                            if (hargaMakanan[j + 1] != 0) {
                                if (hargaMakanan[j] < hargaMakanan[j + 1]) {
                                    String pickedMenuMakanan = menuMakanan[j];
                                    String pickedJenisMakanan = jenisMakanan[j];
                                    int pickedHargaMakanan = hargaMakanan[j];

                                    menuMakanan[j] = menuMakanan[j + 1];
                                    jenisMakanan[j] = jenisMakanan[j + 1];
                                    hargaMakanan[j] = hargaMakanan[j + 1];

                                    menuMakanan[j + 1] = pickedMenuMakanan;
                                    jenisMakanan[j + 1] = pickedJenisMakanan;
                                    hargaMakanan[j + 1] = pickedHargaMakanan;
                                }
                            }
                        }
                    }
                } else if (stateMenu.equals(STATE_MENU_JENIS)) {
                    if (jenisMakanan[i] != null) {
                        if (jenisMakanan[j] != null) {
                            if (jenisMakanan[j + 1] != null) {
                                if (jenisMakanan[j].compareToIgnoreCase(jenisMakanan[j + 1]) > 0) {
                                    String pickedMenuMakanan = menuMakanan[j];
                                    String pickedJenisMakanan = jenisMakanan[j];
                                    int pickedHargaMakanan = hargaMakanan[j];

                                    menuMakanan[j] = menuMakanan[j + 1];
                                    jenisMakanan[j] = jenisMakanan[j + 1];
                                    hargaMakanan[j] = hargaMakanan[j + 1];

                                    menuMakanan[j + 1] = pickedMenuMakanan;
                                    jenisMakanan[j + 1] = pickedJenisMakanan;
                                    hargaMakanan[j + 1] = pickedHargaMakanan;
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    // Method ini berfungsi untuk mencari menu
    public void searchMenu(String state) {
        System.out.print("Cari {String Makanan} \t\t: ");
        String inKeyword = sc.next();

        String[][] hasilCari = searchMenuMakanan(arrayDataNamaMakanan, arrayDataHargaMakanan, arrayDataJenisMakanan, state, inKeyword);

        System.out.println(LINE_BORDER);
        System.out.println(">> Hasil Pencarian");
        readMenuMakananSearched(hasilCari);
        System.out.println(LINE_BORDER);

        System.out.print("Masukkan Menu\t\t\t\t: ");
        int inBeli = sc.nextInt();
        System.out.print("Masukkan Jumlah Pesanan\t\t: ");
        int jumlahMakanan = sc.nextInt();

        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][0] = String.valueOf(ID_PENJUALAN);
        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][1] = getDateTimeNow();
        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][2] = hasilCari[inBeli - 1][0];
        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][3] = hasilCari[inBeli - 1][2];
        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][4] = hasilCari[inBeli - 1][1];

        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][5] = String.valueOf(jumlahMakanan);

        int hargaMakanan = Integer.parseInt(arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][4]);
        int subTotal = hargaMakanan * jumlahMakanan;
        arrayJual[INDEX_VALUED_ARRAY_DATA_JUAL][6] = String.valueOf(subTotal);
        INDEX_VALUED_ARRAY_DATA_JUAL++;

        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][0] = String.valueOf(ID_PENJUALAN);
        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][1] = getDateTimeNow();
        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][2] = hasilCari[inBeli - 1][0];
        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][3] = hasilCari[inBeli - 1][2];
        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][4] = hasilCari[inBeli - 1][1];
        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][5] = String.valueOf(jumlahMakanan);
        arrayJualTemp[INDEX_VALUED_ARRAY_DATA_JUAL_TEMP][6] = String.valueOf(subTotal);
        INDEX_VALUED_ARRAY_DATA_JUAL_TEMP++;

    }

    // Method ini berfungsi untuk mencari menu pada menu kasir
    public String[][] searchMenuMakanan(String[] menuMakanan, int[] hargaMakanan, String[] jenisMakanan, String stateMenu, String keyword) {
        String[][] searchedArray = new String[SIZE_DEFAULT_ARRAY][3];
        int idx = 0;
        for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
            if (stateMenu.equals(STATE_MENU_MAKANAN)) {
                if (menuMakanan[i] != null) {
                    if (menuMakanan[i].contains(keyword)) {
                        searchedArray[idx][0] = menuMakanan[i];
                        searchedArray[idx][1] = String.valueOf(hargaMakanan[i]);
                        searchedArray[idx][2] = jenisMakanan[i];
                        idx++;
                    }
                }

            } else if (stateMenu.equals(STATE_MENU_JENIS)) {
                if (jenisMakanan[i] != null) {
                    if (jenisMakanan[i].contains(keyword)) {
                        searchedArray[idx][0] = menuMakanan[i];
                        searchedArray[idx][1] = String.valueOf(hargaMakanan[i]);
                        searchedArray[idx][2] = jenisMakanan[i];
                        idx++;
                    }
                }
            }
        }
        return searchedArray;
    }

    // Method ini berfungsi untuk menampilkan laporan penjualan pada menu owner
    public void readPenjualan() {
        int totalKeuntungan = 0;
        System.out.println(LINE_BORDER);
        if (INDEX_VALUED_ARRAY_DATA_JUAL == 0) {
            System.out.println("Tidak Ada Penjualan Saat Ini");
        } else {
            System.out.println(">> Laporan Penjualan");
            for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {

                // Index ke 0 -> Id Penjualan
                // Index ke 1 -> Tanggal Penjualan
                // Index ke 2 -> Nama Makanan
                // Index ke 3 -> Jenis Makanan
                // Index ke 4 -> Harga Makanan
                // Index ke 5 -> Jumlah Makanan
                // Index ke 6 -> SubTotal Harga Makanan

                if (arrayJual[i][1] != null) {
                    System.out.println((i + 1) + ".\t [" + arrayJual[i][1] + "] \t [" + arrayJual[i][5] + "] \t - " + arrayJual[i][2] + " \t - " + arrayJual[i][6]);
                    totalKeuntungan = totalKeuntungan + Integer.parseInt(arrayJual[i][6]);
                }
            }
            System.out.println(LINE_BORDER);
            System.out.println("Total Keuntungan : " + totalKeuntungan);
        }
        System.out.println(LINE_BORDER);
    }

    // Method ini berfungsi untuk menampilkan makanan paling sering di beli
    public void readPenjualanLaris(String[][] arr) {

        System.out.println(LINE_BORDER);
        if (INDEX_VALUED_ARRAY_DATA_JUAL == 0) {
            System.out.println("Tidak Ada Menu Paling Laris");
        } else {
            for (int i = 0; i < SIZE_DEFAULT_ARRAY - 1; i++) {
                for (int j = 0; j < SIZE_DEFAULT_ARRAY - 1; j++) {
                    if (arr[i][5] != null) {
                        if (arr[j][5] != null) {
                            if (arr[j + 1][5] != null) {
                                if (Integer.parseInt(arr[j][5]) < Integer.parseInt(arr[j + 1][5])) {

                                    // Index ke 0 -> Id Penjualan               ---> didapat dari variable ID_PENJUALAN
                                    // Index ke 1 -> Tanggal Penjualan          ---> didapat dari tanggal saat terjadi transaksi di menu kasir
                                    // Index ke 2 -> Nama Makanan               ---> didapat dari nama makanan yang dipilih kasir
                                    // Index ke 3 -> Jenis Makanan              ---> didapat dari jenis makanan yang dipilih kasir
                                    // Index ke 4 -> Harga Makanan              ---> didapat dari harga makanan yang dipilih kasir
                                    // Index ke 5 -> Jumlah Makanan             ---> inputan dari kasir berapa jumlah makanan yang di pesan
                                    // Index ke 6 -> SubTotal Harga Makanan     ---> harga makanan * jumlah makanan

                                    String pickedArr0 = arr[j][0];
                                    String pickedArr1 = arr[j][1];
                                    String pickedArr2 = arr[j][2];
                                    String pickedArr3 = arr[j][3];
                                    String pickedArr4 = arr[j][4];
                                    String pickedArr5 = arr[j][5];
                                    String pickedArr6 = arr[j][6];

                                    arr[j][0] = arr[j + 1][0];
                                    arr[j][1] = arr[j + 1][1];
                                    arr[j][2] = arr[j + 1][2];
                                    arr[j][3] = arr[j + 1][3];
                                    arr[j][4] = arr[j + 1][4];
                                    arr[j][5] = arr[j + 1][5];
                                    arr[j][6] = arr[j + 1][6];

                                    arr[j + 1][0] = pickedArr0;
                                    arr[j + 1][1] = pickedArr1;
                                    arr[j + 1][2] = pickedArr2;
                                    arr[j + 1][3] = pickedArr3;
                                    arr[j + 1][4] = pickedArr4;
                                    arr[j + 1][5] = pickedArr5;
                                    arr[j + 1][6] = pickedArr6;

                                }
                            }
                        }

                    }
                }
            }
        }

        System.out.println(">> Menu Makanan Paling Laris");
        for (int i = 0; i < 5; i++) {
            System.out.println((i + 1) + ". " + arr[i][2] + " dengan pembelian sebanyak : " + arr[i][5] + ". Pada tanggal : " + arr[i][1]);
        }
        System.out.println(LINE_BORDER);
    }

    // Method Role cashier.Main Menu -------------------------------------------------------------------------------------------

    // Method ini berisi semua fungsi dari Kasir
    public void mainKasir() {
        greetingUI(KASIR_NAME, ROLE_USER_KASIR);
        logTimeUI("Login");
        System.out.println(LINE_BORDER);
        System.out.println("SELAMAT DATANG DI " + STORE_NAME.toUpperCase());
        System.out.println("MENYEDIAKAN BERBAGAI MENU MAKANAN");
        System.out.println(LINE_BORDER);
        while (STATE_LOGIN) {
            System.out.println("Menu Kasir " + STORE_NAME.toUpperCase());
            System.out.println("1. Daftar Makanan");
            System.out.println("2. Urutkan Berdasarkan Nama Makanan");
            System.out.println("3. Urutkan Berdasarkan Jenis Makanan");
            System.out.println("4. Urutkan Berdasarkan Harga Makanan Termurah");
            System.out.println("5. Urutkan Berdasarkan Harga Makanan Termahal");
            System.out.println("6. Jual Makanan");
            System.out.println("8. Logout");
            System.out.println("9. Exit Program");
            System.out.print(UI_MASUKKAN_PILIHAN);

            int pilihMenu = sc.nextInt();

            switch (pilihMenu) {
                case 1:
                    readMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan);
                    break;

                case 2:
                    sortMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan, STATE_MENU_MAKANAN);
                    sortUI("Nama Makanan");
                    break;

                case 3:
                    sortMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan, STATE_MENU_JENIS);
                    sortUI("Jenis Makanan");
                    break;

                case 4:
                    sortMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan, STATE_MENU_HARGA_TERMURAH);
                    sortUI("Harga Termurah");
                    break;

                case 5:
                    sortMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan, STATE_MENU_HARGA_TERMAHAL);
                    sortUI("Harga Termahal");
                    break;

                case 6:
                    String pembelian = "";
                    int totalHarga = 0;
                    do {
                        System.out.println("Cari Bedasarkan : ");
                        System.out.println("1. Nama Makanan");
                        System.out.println("2. Jenis Makanan");
                        System.out.print(UI_MASUKKAN_PILIHAN);
                        int inSortPilihan = sc.nextInt();
                        if (inSortPilihan == 1) {
                            searchMenu(STATE_MENU_MAKANAN);
                        } else if (inSortPilihan == 2) {
                            searchMenu(STATE_MENU_JENIS);
                        }
                        System.out.print("Lanjut Pembelian [y/n]\t\t: ");
                        pembelian = sc.next();
                    } while (pembelian.equalsIgnoreCase("y"));
                    ID_PENJUALAN++;
                    System.out.println(LINE_BORDER);
                    System.out.println("DAFTAR BELANJAAN DI " + STORE_NAME.toUpperCase());
                    System.out.println(LINE_BORDER);
                    System.out.println("No" + ".\t" + "Menu Makanan\t" + "\t" + "Harga" + "\t" + "Jumlah");
                    for (int i = 0; i < INDEX_VALUED_ARRAY_DATA_JUAL; i++) {
                        if (arrayJualTemp[i][2] != null) {
                            System.out.println((i + 1) + ".\t" + arrayJualTemp[i][2] + "\t" + arrayJualTemp[i][4] + "\t" + arrayJualTemp[i][5]);
                            totalHarga = totalHarga + Integer.parseInt(arrayJualTemp[i][6]);
                        }
                    }
                    System.out.println(LINE_BORDER);
                    System.out.println("Grand Total\t\t\t\t\t: Rp " + totalHarga);
                    System.out.println(LINE_BORDER);
                    System.out.print("Tunai\t\t\t\t\t\t: Rp ");
                    Scanner sc = new Scanner(System.in);
                    int inputUangTunai = sc.nextInt();
                    if (inputUangTunai == totalHarga) {
                        System.out.println("\t\t\t\t\tUANG ANDA PAS");
                    } else if (inputUangTunai < totalHarga) {
                        System.out.println("\t\t\t\t\tUANG ANDA KURANG");
                        System.out.println("Uang Kurang\t\t\t\t\t: Rp " + (totalHarga - inputUangTunai));
                    } else {
                        int uangKembalian = inputUangTunai - totalHarga;
                        System.out.println("Kembali\t\t\t\t\t\t: Rp " + uangKembalian);
                    }
                    System.out.println(LINE_BORDER);
                    System.out.println("Terima Kasih Sudah Berbelanja");
                    System.out.println("Di " + STORE_NAME);
                    System.out.println("Semoga Anda Puas");
                    System.out.println(LINE_BORDER);
                    nukeArrayTempJual();
                    break;
                case 8:
                    STATE_LOGIN = !STATE_LOGIN;
                    logTimeUI("Logout");
                    mainLogin();
                    break;

                case 9:
                    STATE_LOGIN = !STATE_LOGIN;
                    exitUI();
                    break;

            }
        }
    }

    // Method ini berisi semua fungsi dari Admin
    public void mainAdmin() {
        greetingUI(ADMIN_NAME, ROLE_USER_ADMIN);
        logTimeUI("Login");
        System.out.println(LINE_BORDER);
        System.out.println("SELAMAT DATANG DI " + STORE_NAME.toUpperCase());
        System.out.println(LINE_BORDER);
        while (STATE_LOGIN) {
            System.out.println("Menu Admin " + STORE_NAME.toUpperCase());
            System.out.println("1. Daftar Makanan");
            System.out.println("2. Menambah Daftar Makanan");
            System.out.println("3. Mengubah Daftar Makanan");
            System.out.println("4. Menghapus Daftar Makanan");
            System.out.println("5. Mereset Daftar Menu");
            System.out.println("6. Menambah Otomatis dari Sistem");
            System.out.println("8. Logout");
            System.out.println("9. Exit Program");
            System.out.print(UI_MASUKKAN_PILIHAN);
            int pilihMenu = sc.nextInt();
            switch (pilihMenu) {
                case 1:
                    readMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan);
                    break;

                case 2:
                    System.out.println("Masukkan Data Daftar Makanan");
                    System.out.println(LINE_BORDER);
                    System.out.println("Pilihan Jenis Makanan");
                    System.out.println("1. Makanan");
                    System.out.println("2. Minuman");
                    System.out.println("3. Snack");
                    System.out.println(LINE_BORDER);
                    System.out.print("Nama \t: ");
                    String inputNamaMakanan = sc.next();
                    System.out.print("Jenis\t: ");
                    int inputJenisMakanan = sc.nextInt();
                    System.out.print("Harga\t: ");
                    int inputHargaMakanan = sc.nextInt();

                    switch (inputJenisMakanan) {
                        case 1:
                            addMenuMakanan(inputNamaMakanan, ITEM_JENIS_MAKANAN, inputHargaMakanan);
                            break;

                        case 2:
                            addMenuMakanan(inputNamaMakanan, ITEM_JENIS_MINUMAN, inputHargaMakanan);
                            break;

                        case 3:
                            addMenuMakanan(inputNamaMakanan, ITEM_JENIS_SNACK, inputHargaMakanan);
                            break;
                    }

                    break;

                case 3:
                    System.out.println("Masukkan Data Daftar Makanan");
                    System.out.println(LINE_BORDER);
                    System.out.println("Pilihan Jenis Makanan");
                    System.out.println("1. Makanan");
                    System.out.println("2. Minuman");
                    System.out.println("3. Snack");
                    readMenuMakanan(arrayDataNamaMakanan, arrayDataJenisMakanan, arrayDataHargaMakanan);
                    System.out.print("Index\t: ");
                    int inputIndexMakanan = sc.nextInt();
                    System.out.print("Nama \t: ");
                    String inputNamaMakanan2 = sc.next();
                    System.out.print("Jenis\t: ");
                    int inputJenisMakanan2 = sc.nextInt();
                    System.out.print("Harga\t: ");
                    int inputHargaMakanan2 = sc.nextInt();
                    int indexMakanan = inputIndexMakanan - 1;
                    switch (inputJenisMakanan2) {
                        case 1:
                            updateMenuMakanan(indexMakanan, inputNamaMakanan2, ITEM_JENIS_MAKANAN, inputHargaMakanan2);
                            break;
                        case 2:
                            updateMenuMakanan(indexMakanan, inputNamaMakanan2, ITEM_JENIS_MINUMAN, inputHargaMakanan2);
                            break;
                        case 3:
                            updateMenuMakanan(indexMakanan, inputNamaMakanan2, ITEM_JENIS_SNACK, inputHargaMakanan2);
                            break;
                    }
                    break;
                case 4:
                    System.out.println(LINE_BORDER);
                    System.out.print("Menu yang mau di hapus : ");
                    String namaMakanan = sc.next();
                    deleteMenuMakanan(namaMakanan);
                    break;
                case 5:
                    nukeMenuMakanan();
                    System.out.println(LINE_BORDER);
                    System.out.println("Daftar Menu Berhasil Di Reset");
                    System.out.println(LINE_BORDER);
                    break;
                case 6:
                    initAutoMenu(INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU);
                    System.out.println(LINE_BORDER);
                    System.out.println("Pengisian Menu Secara Automatis");
                    System.out.println(LINE_BORDER);
                    break;
                case 8:
                    STATE_LOGIN = !STATE_LOGIN;
                    logTimeUI("Logout");
                    mainLogin();
                    break;
                case 9:
                    STATE_LOGIN = !STATE_LOGIN;
                    exitUI();
                    break;
            }

        }
    }

    // Method ini berisi semua fungsi dari Owner
    public void mainOwner() {
        greetingUI(OWNER_NAME, ROLE_USER_OWNER);
        logTimeUI("Login");
        System.out.println(LINE_BORDER);
        System.out.println("SELAMAT DATANG DI " + STORE_NAME.toUpperCase());
        System.out.println(LINE_BORDER);
        while (STATE_LOGIN) {
            System.out.println("Menu Owner " + STORE_NAME.toUpperCase());
            System.out.println("1. Laporan Penjualan");
            System.out.println("2. Menu Makanan Paling Laris");
            System.out.println("8. Logout");
            System.out.println("9. Exit Program");
            System.out.print(UI_MASUKKAN_PILIHAN);
            int pilihMenu = sc.nextInt();
            switch (pilihMenu) {
                case 1:
                    readPenjualan();
                    break;
                case 2:
                    readPenjualanLaris(arrayJual);
                    break;
                case 8:
                    STATE_LOGIN = !STATE_LOGIN;
                    logTimeUI("Logout");
                    mainLogin();
                    break;
                case 9:
                    STATE_LOGIN = !STATE_LOGIN;
                    exitUI();
                    break;
            }
        }
    }

    // Method ini untuk mengatur login dan merupakan inti dari aplikasi
    public void mainLogin() {
        int loopLogin = 0;
        do {
            loginUI();
            Scanner inputLogin = new Scanner(System.in);
            System.out.print("Username : ");
            String username = inputLogin.nextLine();
            System.out.print("Password : ");
            String password = inputLogin.next();
            System.out.println(LINE_BORDER);
            if (username.equals(KASIR_USERNAME) && (password.equals(KASIR_PASSWORD))) {
                STATE_LOGIN = !STATE_LOGIN;
                mainKasir();
                loopLogin = 2;
            } else if (username.equals(ADMIN_USERNAME) && (password.equals(ADMIN_PASSWORD))) {
                STATE_LOGIN = !STATE_LOGIN;
                mainAdmin();
                loopLogin = 2;
            } else if (username.equals(OWNER_USERNAME) && (password.equals(OWNER_PASSWORD))) {
                STATE_LOGIN = !STATE_LOGIN;
                loopLogin = 2;
                mainOwner();
            } else {
                System.out.println(">> Username Atau Password Salah <<");
                System.out.println(LINE_BORDER);
                System.out.println("1. Lanjut Login");
                System.out.println("2. Exit Program");
                System.out.print("Masukkan Pilihan : ");
                int pilih = sc.nextInt();
                if (pilih == 1) {
                    loopLogin = 1;
                } else if (pilih == 2) {
                    loopLogin = 2;
                    exitUI();
                } else {
                    System.out.println(LINE_BORDER);
                    System.out.println(">> Pilihan Salah !!! <<");
                    loopLogin = 2;
                    exitUI();
                }
            }
        } while (loopLogin != 2);
    }

    // Method Campur Campur --------------------------------------------------------------------------------------------

    // Method ini untuk mendapatkan tanggal hari ini
    public String getDateTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Method ini untuk menambahkan data menu secara otomatis
    public void initAutoMenu(int startIndex) {
        arrayDataNamaMakanan[startIndex] = "MartabakSapi";
        arrayDataHargaMakanan[startIndex] = 31000;
        arrayDataJenisMakanan[startIndex] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 1] = "MartabakSapiSpesial";
        arrayDataHargaMakanan[startIndex + 1] = 39000;
        arrayDataJenisMakanan[startIndex + 1] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 2] = "MartabakAyam\t";
        arrayDataHargaMakanan[startIndex + 2] = 29000;
        arrayDataJenisMakanan[startIndex + 2] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 3] = "MartabakAyamSpesial";
        arrayDataHargaMakanan[startIndex + 3] = 33000;
        arrayDataJenisMakanan[startIndex + 3] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 4] = "TerangBulanCoklat";
        arrayDataHargaMakanan[startIndex + 4] = 23000;
        arrayDataJenisMakanan[startIndex + 4] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 5] = "TerangBulanCoklatKeju";
        arrayDataHargaMakanan[startIndex + 5] = 27000;
        arrayDataJenisMakanan[startIndex + 5] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 6] = "TerangBulanCoklatKacang";
        arrayDataHargaMakanan[startIndex + 6] = 25000;
        arrayDataJenisMakanan[startIndex + 6] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 7] = "TerangBulanSpecial";
        arrayDataHargaMakanan[startIndex + 7] = 35000;
        arrayDataJenisMakanan[startIndex + 7] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 8] = "TerangBulanMatcha";
        arrayDataHargaMakanan[startIndex + 8] = 37000;
        arrayDataJenisMakanan[startIndex + 8] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 9] = "TerangBulanMini";
        arrayDataHargaMakanan[startIndex + 9] = 21000;
        arrayDataJenisMakanan[startIndex + 9] = ITEM_JENIS_MAKANAN;

        arrayDataNamaMakanan[startIndex + 10] = "CoffeLatte";
        arrayDataHargaMakanan[startIndex + 10] = 9000;
        arrayDataJenisMakanan[startIndex + 10] = ITEM_JENIS_MINUMAN;

        arrayDataNamaMakanan[startIndex + 11] = "CoffeChoco";
        arrayDataHargaMakanan[startIndex + 11] = 12000;
        arrayDataJenisMakanan[startIndex + 11] = ITEM_JENIS_MINUMAN;

        arrayDataNamaMakanan[startIndex + 12] = "CoffeVanilla";
        arrayDataHargaMakanan[startIndex + 12] = 11000;
        arrayDataJenisMakanan[startIndex + 12] = ITEM_JENIS_MINUMAN;

        arrayDataNamaMakanan[startIndex + 13] = "RedVelvet";
        arrayDataHargaMakanan[startIndex + 13] = 17000;
        arrayDataJenisMakanan[startIndex + 13] = ITEM_JENIS_MINUMAN;

        arrayDataNamaMakanan[startIndex + 14] = "GreenTea";
        arrayDataHargaMakanan[startIndex + 14] = 15000;
        arrayDataJenisMakanan[startIndex + 14] = ITEM_JENIS_MINUMAN;

        arrayDataNamaMakanan[startIndex + 15] = "FrenchFries";
        arrayDataHargaMakanan[startIndex + 15] = 9000;
        arrayDataJenisMakanan[startIndex + 15] = ITEM_JENIS_SNACK;

        arrayDataNamaMakanan[startIndex + 16] = "ToastCoklatKeju";
        arrayDataHargaMakanan[startIndex + 16] = 15000;
        arrayDataJenisMakanan[startIndex + 16] = ITEM_JENIS_SNACK;

        arrayDataNamaMakanan[startIndex + 17] = "CroffleSyrup";
        arrayDataHargaMakanan[startIndex + 17] = 13000;
        arrayDataJenisMakanan[startIndex + 17] = ITEM_JENIS_SNACK;

        arrayDataNamaMakanan[startIndex + 18] = "GelatoDurian";
        arrayDataHargaMakanan[startIndex + 18] = 11000;
        arrayDataJenisMakanan[startIndex + 18] = ITEM_JENIS_SNACK;

        arrayDataNamaMakanan[startIndex + 19] = "CheeseCake";
        arrayDataHargaMakanan[startIndex + 19] = 21000;
        arrayDataJenisMakanan[startIndex + 19] = ITEM_JENIS_SNACK;

        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU = INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU + 19;
    }

}