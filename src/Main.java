
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

public class Main {

    // Setting Apps Variable Constant ----------------------------------------------------------------------------------
    static String STORE_NAME = "INDOMART";
    static String APP_NAME = "SMART POS";

    static String ADMIN_NAME = "Sakura";
    static String ADMIN_USERNAME = "0";
    static String ADMIN_PASSWORD = "0";

    static String KASIR_NAME = "Sasuke";
    static String KASIR_USERNAME = "1";
    static String KASIR_PASSWORD = "1";

    static String OWNER_NAME = "Naruto";
    static String OWNER_USERNAME = "2";
    static String OWNER_PASSWORD = "2";

    static int SIZE_DEFAULT_ARRAY = 20;

    // Main Variable Constant ------------------------------------------------------------------------------------------
    static String LINE_BORDER = "-------------------------------------------------------";
    static String ITEM_JENIS_MAKANAN = "Makanan";
    static String ITEM_JENIS_MINUMAN = "Minuman";
    static String ITEM_JENIS_SNACK = "Snack";

    static String ROLE_USER_ADMIN = "Admin";
    static String ROLE_USER_KASIR = "Kasir";
    static String ROLE_USER_OWNER = "Owner";

    static String STATE_MENU_MAKANAN = "STATE_MENU_MAKANAN";
    static String STATE_MENU_HARGA_TERMAHAL = "STATE_MENU_HARGA_TERMAHAL";
    static String STATE_MENU_HARGA_TERMURAH = "STATE_MENU_HARGA_TERMURAH";
    static String STATE_MENU_JENIS = "STATE_MENU_JENIS";

    static boolean STATE_LOGIN = false;

    static String UI_MASUKKAN_PILIHAN = "Masukkan Pilihan\t\t\t: ";
    static String SIMPLE_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    static Scanner sc = new Scanner(System.in);
    // -----------------------------------------------------------------------------------------------------------------

    // Initiasi Variable Global ----------------------------------------------------------------------------------------

    // Data Data Menu Utama
    static String[] namaMakanan = new String[SIZE_DEFAULT_ARRAY]; // Nama Makanan
    static String[] jenisMakanan = new String[SIZE_DEFAULT_ARRAY]; // Jenis Makanan, Contoh : Makanan, Makanan, Makanan
    static int[] hargaMakanan = new int[SIZE_DEFAULT_ARRAY];

    static int[] beliHarga = new int[SIZE_DEFAULT_ARRAY];
    static String[] beliJenis = new String[SIZE_DEFAULT_ARRAY];
    static String[] beliMakanan = new String[SIZE_DEFAULT_ARRAY];
    static int[] beliJumlahMakanan = new int[SIZE_DEFAULT_ARRAY];

    static int[] subTotal = new int[SIZE_DEFAULT_ARRAY];

    static int jumlahBeli = 0;
    static int totalHarga = 0;
    static int inputUangTunai, uangKembalian;

    // Main Program
    public static void main(String[] args) {
        startApp();
    }

    // Method Menu Tampilan --------------------------------------------------------------------------------------------

    static void loginUI() {
        System.out.println(LINE_BORDER);
        System.out.println("SELAMAT DATANG DI " + STORE_NAME.toUpperCase());
        System.out.println("UNTUK MEMULAI APLIKASI " + APP_NAME.toUpperCase());
        System.out.println("SILAHKAN LOGIN UNTUK MELANJUTKAN");
        System.out.println(LINE_BORDER);
    }

    static void greetingUI(String name, String role) {
        System.out.println(">> Hallo " + name + " | " + role);
    }

    static void sortUI(String state) {
        System.out.println(LINE_BORDER);
        System.out.println("Berhasil Mengurutkan Menu Berdasarkan " + state);
        System.out.println(LINE_BORDER);
    }

    static void logTimeUI(String status) {
        System.out.println(">> Waktu " + status + " : " + getDateTimeNow());
    }

    static void headerKasirMenuUI() {
        greetingUI(KASIR_NAME, ROLE_USER_KASIR);
        logTimeUI("Login");
        System.out.println(LINE_BORDER);
        System.out.println("SELAMAT DATANG DI " + STORE_NAME.toUpperCase());
        System.out.println("MENYEDIAKAN BERBAGAI MENU MAKANAN");
        System.out.println(LINE_BORDER);
    }

    static void footerKasirMenuUI() {
        System.out.println(LINE_BORDER);
        System.out.println("Terima Kasih Sudah Berbelanja");
        System.out.println("Di " + STORE_NAME);
        System.out.println("Semoga Anda Puas ");
    }

    // -----------------------------------------------------------------------------------------------------------------

    static String getDateTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    static void mainKasir() {
        initAutoMenu(0);
        int pilihMenu = 0;
        headerKasirMenuUI();
        do {
            System.out.println("Menu Kasir " + STORE_NAME.toUpperCase());
            System.out.println("1. Daftar Makanan");
            System.out.println("2. Urutkan Berdasarkan Nama Makanan");
            System.out.println("3. Urutkan Berdasarkan Jenis Makanan");
            System.out.println("4. Urutkan Berdasarkan Harga Makanan Termurah");
            System.out.println("5. Urutkan Berdasarkan Harga Makanan Termahal");
            System.out.println("6. Cari Makanan");
            System.out.println("7. Logout");
            System.out.print(UI_MASUKKAN_PILIHAN);

            pilihMenu = sc.nextInt();

            switch (pilihMenu) {
                case 1 -> {
                    System.out.println(LINE_BORDER);
                    System.out.println(">> Daftar Menu " + STORE_NAME);
                    System.out.println(LINE_BORDER);
                    showDaftarMenu(namaMakanan, hargaMakanan, jenisMakanan);
                    System.out.println(LINE_BORDER);
                }
                case 2 -> {
                    sort(namaMakanan, hargaMakanan, jenisMakanan, STATE_MENU_MAKANAN);
                    sortUI("Nama Makanan");
                }
                case 3 -> {
                    sort(namaMakanan, hargaMakanan, jenisMakanan, STATE_MENU_JENIS);
                    sortUI("Jenis Makanan");
                }
                case 4 -> {
                    sort(namaMakanan, hargaMakanan, jenisMakanan, STATE_MENU_HARGA_TERMURAH);
                    sortUI("Harga Termurah");
                }
                case 5 -> {
                    sort(namaMakanan, hargaMakanan, jenisMakanan, STATE_MENU_HARGA_TERMAHAL);
                    sortUI("Harga Termahal");
                }
                case 6 -> {
                    System.out.println("Cari Bedasarkan : ");
                    System.out.println("1. Nama Makanan");
                    System.out.println("2. Jenis Makanan");
                    System.out.print(UI_MASUKKAN_PILIHAN);
                    int inSortPilihan = sc.nextInt();
                    if (inSortPilihan == 1) {
                        kasirSearchedMenu(STATE_MENU_MAKANAN);
                    } else if (inSortPilihan == 2) {
                        kasirSearchedMenu(STATE_MENU_JENIS);
                    }
                }
                case 7 -> {
                    STATE_LOGIN = !STATE_LOGIN;
                    logTimeUI("Logout");
                }
            }
        } while (pilihMenu != 7);
        System.out.println("---------------------------------------------");
        System.out.println("DAFTAR BELANJAAN DI " + STORE_NAME.toUpperCase());
        System.out.println("---------------------------------------------");
        System.out.println("No" + ".\t" + "Menu Makanan\t" + "\t" + "Harga" + "\t" + "Jumlah");
        for (int i = 0; i < jumlahBeli; i++) {
            if (beliMakanan[i] != null) {
                System.out.println((i + 1) + ".\t" + beliMakanan[i] + "\t" + beliHarga[i] + "\t" + beliJumlahMakanan[i]);
                totalHarga = totalHarga + subTotal[i];
            }
        }
        System.out.println("---------------------------------------------");
        System.out.println("Grand Total\t\t\t\t\t: Rp " + totalHarga);
        System.out.println("---------------------------------------------");
        System.out.print("Tunai\t\t\t\t\t\t: Rp ");
        Scanner sc = new Scanner(System.in);
        inputUangTunai = sc.nextInt();
        if (inputUangTunai == totalHarga) {
            System.out.println("\t\t\t\t\tUANG ANDA PAS");
        } else if (inputUangTunai < totalHarga) {
            System.out.println("\t\t\t\t\tUANG ANDA KURANG");
            System.out.println("Uang Kurang\t\t\t\t\t: Rp " + (totalHarga - inputUangTunai));
        } else {
            uangKembalian = inputUangTunai - totalHarga;
            System.out.println("Kembali\t\t\t\t\t\t: Rp " + uangKembalian);
        }
        footerKasirMenuUI();
    }

    static void mainAdmin() {
        greetingUI(ADMIN_NAME, ROLE_USER_ADMIN);
        logTimeUI("Login");
        System.out.println(LINE_BORDER);
    }

    static void mainOwner() {
        greetingUI(OWNER_NAME, ROLE_USER_OWNER);
        logTimeUI("Login");
        System.out.println(LINE_BORDER);
    }

    static void mainApp(String username, String password) {
        if (username.equals(KASIR_USERNAME) && (password.equals(KASIR_PASSWORD))) {
            mainKasir();
        } else if (username.equals(ADMIN_USERNAME) && (password.equals(ADMIN_PASSWORD))) {
            mainAdmin();
        } else if (username.equals(OWNER_USERNAME) && (password.equals(OWNER_PASSWORD))) {
            mainOwner();
        }
    }

    static void startApp() {
        loginUI();
        Scanner inputLogin = new Scanner(System.in);
        System.out.print("Username : ");
        String username = inputLogin.nextLine();
        System.out.print("Password : ");
        String password = inputLogin.next();
        System.out.println(LINE_BORDER);
        mainApp(username, password);
    }

    static void initAutoMenu(int startIndex) {
        namaMakanan[startIndex] = "Martabak Sapi\t";
        hargaMakanan[startIndex] = 31000;
        jenisMakanan[startIndex] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 1] = "Martabak SaSpesial";
        hargaMakanan[startIndex + 1] = 39000;
        jenisMakanan[startIndex + 1] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 2] = "Martabak Ayam\t";
        hargaMakanan[startIndex + 2] = 29000;
        jenisMakanan[startIndex + 2] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 3] = "Martabak AySpesial";
        hargaMakanan[startIndex + 3] = 33000;
        jenisMakanan[startIndex + 3] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 4] = "Terang Bulan Coklat";
        hargaMakanan[startIndex + 4] = 23000;
        jenisMakanan[startIndex + 4] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 5] = "Terang Bulan CoKeju";
        hargaMakanan[startIndex + 5] = 27000;
        jenisMakanan[startIndex + 5] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 6] = "Terang Bulan CoCang";
        hargaMakanan[startIndex + 6] = 25000;
        jenisMakanan[startIndex + 6] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 7] = "Terang Bulan SpCial";
        hargaMakanan[startIndex + 7] = 35000;
        jenisMakanan[startIndex + 7] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 8] = "Terang Bulan Matcha";
        hargaMakanan[startIndex + 8] = 37000;
        jenisMakanan[startIndex + 8] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 9] = "Terang Bulan Mini";
        hargaMakanan[startIndex + 9] = 21000;
        jenisMakanan[startIndex + 9] = ITEM_JENIS_MAKANAN;

        namaMakanan[startIndex + 10] = "Coffe Latte\t\t";
        hargaMakanan[startIndex + 10] = 9000;
        jenisMakanan[startIndex + 10] = ITEM_JENIS_MINUMAN;

        namaMakanan[startIndex + 11] = "Coffe Choco\t\t";
        hargaMakanan[startIndex + 11] = 12000;
        jenisMakanan[startIndex + 11] = ITEM_JENIS_MINUMAN;

        namaMakanan[startIndex + 12] = "Coffe Vanilla\t";
        hargaMakanan[startIndex + 12] = 11000;
        jenisMakanan[startIndex + 12] = ITEM_JENIS_MINUMAN;

        namaMakanan[startIndex + 13] = "Red Velvet\t\t";
        hargaMakanan[startIndex + 13] = 17000;
        jenisMakanan[startIndex + 13] = ITEM_JENIS_MINUMAN;

        namaMakanan[startIndex + 14] = "Green Tea\t\t";
        hargaMakanan[startIndex + 14] = 15000;
        jenisMakanan[startIndex + 14] = ITEM_JENIS_MINUMAN;

        namaMakanan[startIndex + 15] = "French Fries\t";
        hargaMakanan[startIndex + 15] = 9000;
        jenisMakanan[startIndex + 15] = ITEM_JENIS_SNACK;

        namaMakanan[startIndex + 16] = "Toast CoKeju\t";
        hargaMakanan[startIndex + 16] = 15000;
        jenisMakanan[startIndex + 16] = ITEM_JENIS_SNACK;

        namaMakanan[startIndex + 17] = "Croffle Syrup\t";
        hargaMakanan[startIndex + 17] = 13000;
        jenisMakanan[startIndex + 17] = ITEM_JENIS_SNACK;

        namaMakanan[startIndex + 18] = "Gelato Durian\t";
        hargaMakanan[startIndex + 18] = 11000;
        jenisMakanan[startIndex + 18] = ITEM_JENIS_SNACK;

        namaMakanan[startIndex + 19] = "Cheese Cake \t";
        hargaMakanan[startIndex + 19] = 21000;
        jenisMakanan[startIndex + 19] = ITEM_JENIS_SNACK;
    }

    static void sort(String[] menuMakanan, int[] hargaMakanan, String[] jenisMakanan, String stateMenu) {
        for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
            for (int j = 0; j < SIZE_DEFAULT_ARRAY - 1; j++) {

                if (stateMenu.equals(STATE_MENU_MAKANAN)) {
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
                } else if (stateMenu.equals(STATE_MENU_HARGA_TERMURAH)) {
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
                } else if (stateMenu.equals(STATE_MENU_HARGA_TERMAHAL)) {
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
                } else if (stateMenu.equals(STATE_MENU_JENIS)) {
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

    static String[][] search(String[] menuMakanan, int[] hargaMakanan, String[] jenisMakanan, String stateMenu, String keyword) {
        String[][] searchedArray = new String[SIZE_DEFAULT_ARRAY][3];
        int idx = 0;
        for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
            if (stateMenu.equals(STATE_MENU_MAKANAN)) {
                if (menuMakanan[i].contains(keyword)) {
                    searchedArray[idx][0] = menuMakanan[i];
                    searchedArray[idx][1] = String.valueOf(hargaMakanan[i]);
                    searchedArray[idx][2] = jenisMakanan[i];
                    idx++;
                }
            } else if (stateMenu.equals(STATE_MENU_JENIS)) {
                if (menuMakanan[i].contains(keyword)) {
                    searchedArray[idx][0] = menuMakanan[i];
                    searchedArray[idx][1] = String.valueOf(hargaMakanan[i]);
                    searchedArray[idx][2] = jenisMakanan[i];
                    idx++;
                }
            }
        }
        return searchedArray;
    }

    static void showDaftarMenu(String[] menuMakanan, int[] hargaMakanan, String[] jenisMakanan) {
        for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
            System.out.println((i + 1) + ".\t [" + jenisMakanan[i] + "] \t" + hargaMakanan[i] + "\t - " + menuMakanan[i]);
        }
    }

    static void showSearchedMenu(String[][] sortedMenu) {
        for (int i = 0; i < SIZE_DEFAULT_ARRAY; i++) {
            if (sortedMenu[i][0] != null) {
                System.out.println((i + 1) + ".\t [" + sortedMenu[i][2] + "] \t" + sortedMenu[i][1] + "\t - " + sortedMenu[i][0]);
            }
        }
    }

    static void kasirSearchedMenu(String state) {
        System.out.print("Cari \t\t\t\t\t\t: ");
        String inKeyword = sc.next();

        String[][] hasilCari = search(namaMakanan, hargaMakanan, jenisMakanan, state, inKeyword);

        System.out.println(LINE_BORDER);
        System.out.println(">> Hasil Pencarian");
        showSearchedMenu(hasilCari);
        System.out.println(LINE_BORDER);

        System.out.print("Masukkan Menu\t\t\t\t: ");
        int inBeli = sc.nextInt();
        System.out.print("Masukkan Jumlah Pesanan\t\t: ");
        beliJumlahMakanan[jumlahBeli] = sc.nextInt();

        beliMakanan[jumlahBeli] = hasilCari[inBeli - 1][0];
        beliHarga[jumlahBeli] = Integer.parseInt(hasilCari[inBeli - 1][1]);
        beliJenis[jumlahBeli] = hasilCari[inBeli - 1][2];

        subTotal[jumlahBeli] = beliJumlahMakanan[jumlahBeli] * beliHarga[jumlahBeli];
        jumlahBeli++;
    }

}
