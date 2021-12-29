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

public class MainConfig {

    public static final class INSTANCE {
        // Setting Apps Variable Constant ----------------------------------------------------------------------------------
        // Bagian ini merupakan variable yang berisi kontanta untuk program dimana tidak akan diubah nilainya saat program dijalankan
        public static final String DEVELOPER_NAME = "PROGRAMMER"; // Constanta Untuk Nama Pengembang Aplikasi
        public static final String STORE_NAME = "AMIRISBACK"; // Constanta Untuk Nama Toko Program
        public static final String APP_NAME = "SMART POS"; // Constanta Untuk Nama Aplikasi

        public static final String ADMIN_NAME = "Sakura"; // Constanta Untuk Nama Admin
        public static final String ADMIN_USERNAME = "0"; // Constanta Untuk Login Username Admin
        public static final String ADMIN_PASSWORD = "0"; // Constanta Untuk Login Password Admin

        public static final String KASIR_NAME = "Sasuke"; // Constanta Untuk Nama Kasir
        public static final String KASIR_USERNAME = "1"; // Constanta Untuk Login Username Kasir
        public static final String KASIR_PASSWORD = "1"; // Constanta Untuk Login Password Kasir

        public static final String OWNER_NAME = "Naruto"; // Constanta Untuk Login Nama Owner
        public static final String OWNER_USERNAME = "2"; // Constanta Untuk Login UserName Owner
        public static final String OWNER_PASSWORD = "2"; // Constanta Untuk Login Password Owner

        /* Penjelasan : SIZE DEFAULT ARRAY
         * Constanta untuk menentukan semua besar array yang akan digunakan,
         * menggunakan variable ini agar gampang untuk merubah dari semua size array yang akan di deklarasikan
         */
        public static final int SIZE_DEFAULT_ARRAY = 100;

        // cashier.Main Variable Constant ------------------------------------------------------------------------------------------

        // Penjelasan LINE_BORDER : Constanta Untuk Tampilan Border aplikasi, dibuat constanta agar mudah merubah gaya tampilan aplikasi
        public static final String LINE_BORDER = "-------------------------------------------------------";
        public static final String ITEM_JENIS_MAKANAN = "Makanan"; // Constant untuk pengecekan percabangan ketika item makanan
        public static final String ITEM_JENIS_MINUMAN = "Minuman"; // Constant untuk pengecekan percabangan ketika item minuman
        public static final String ITEM_JENIS_SNACK = "Snack"; // Constant untuk pengecekan percabangan ketika item snack

        public static final String ROLE_USER_ADMIN = "Admin"; // Constant untuk pengecekan percabangan ketika item Admin
        public static final String ROLE_USER_KASIR = "Kasir"; // Constant untuk pengecekan percabangan ketika item Kasir
        public static final String ROLE_USER_OWNER = "Owner"; // Constant untuk pengecekan percabangan ketika item Owner

        public static final String STATE_MENU_MAKANAN = "STATE_MENU_MAKANAN"; // Constant untuk pengecekan percabangan ketika item Sorting Berdasakan Nama Makanan
        public static final String STATE_MENU_HARGA_TERMAHAL = "STATE_MENU_HARGA_TERMAHAL"; // Constant untuk pengecekan percabangan ketika item Sorting Berdasakan Harga Termurah
        public static final String STATE_MENU_HARGA_TERMURAH = "STATE_MENU_HARGA_TERMURAH"; // Constant untuk pengecekan percabangan ketika item Sorting Berdasakan Harga Termahal
        public static final String STATE_MENU_JENIS = "STATE_MENU_JENIS"; // Constant untuk pengecekan percabangan ketika item Sorting Berdasakan Jenis Makanan

        public static final String UI_MASUKKAN_PILIHAN = "Masukkan Pilihan\t\t\t: "; // Constant untuk tampilan "Masukkan Pilihan Agar jaraknya sama semua
        public static final String SIMPLE_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"; // Constant Untuk Format Tanggal Saat ini

    }
    
}
