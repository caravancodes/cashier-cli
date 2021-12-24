
import java.util.Scanner;

public class Final {

    static int SIZE_DEFAULT_ARRAY = 100;

    static String[] dataMenuMakanan = new String[SIZE_DEFAULT_ARRAY];
    static int[] dataHargaMakanan = new int[SIZE_DEFAULT_ARRAY];

    static String[] dataMenuMinuman = new String[SIZE_DEFAULT_ARRAY];
    static int[] dataHargaMinuman = new int[SIZE_DEFAULT_ARRAY];

    static String[] dataMenuSnack = new String[SIZE_DEFAULT_ARRAY];
    static int[] dataHargaSnack = new int[SIZE_DEFAULT_ARRAY];

    static String[] cariMakan = new String[SIZE_DEFAULT_ARRAY];
    static int[] cariHarga = new int[30];

    static String[] beliMenuMakanan = new String[SIZE_DEFAULT_ARRAY];
    static int[] beliHargaMakanan = new int[SIZE_DEFAULT_ARRAY];
    static int[] beliJumlahMakanan = new int[SIZE_DEFAULT_ARRAY];
    static int[] beliSubBiayaTotal = new int[SIZE_DEFAULT_ARRAY];

    static int jumlahBeli = 0;
    static int totalHarga = 0;

    static Scanner sc = new Scanner(System.in);

    static String ITEM_JENIS_MAKANAN = "Makanan"; // Constant untuk pengecekan percabangan ketika item makanan
    static String ITEM_JENIS_MINUMAN = "Minuman"; // Constant untuk pengecekan percabangan ketika item minuman
    static String ITEM_JENIS_SNACK = "Snack"; // Constant untuk pengecekan percabangan ketika item snack

    static int STATE_LOGOUT = 8;
    static int STATE_EXIT = 9;

    static int INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN = 0; // variable index untuk mengetahui posisi terakhir array daftar menu
    static int INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN = 0; // variable index untuk mengetahui posisi terakhir array daftar menu
    static int INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK = 0; // variable index untuk mengetahui posisi terakhir array daftar menu
    static int INDEX_VALUED_ARRAY_DATA_JUAL = 0; // variable index untuk mengetahui posisi terakhir array data penjualan
    static int INDEX_VALUED_ARRAY_DATA_JUAL_TEMP = 0; // variable index untuk mengetahui posisi terakhir array data penjualan sementara

    // Data Penjualan
    static String[][] arrayJual = new String[30][7]; // Array ini untuk mencatat semua penjualan yang ada dan dijadikan untuk acuan laporan penjualan owner
    static String[][] arrayJualTemp = new String[30][7]; // Array ini untuk menampung sementara yang terjadi saat penjualan pada menu kasir
    // Index ke 0 -> Id Penjualan               ---> didapat dari variable ID_PENJUALAN
    // Index ke 1 -> Tanggal Penjualan          ---> didapat dari tanggal saat terjadi transaksi di menu kasir
    // Index ke 2 -> Nama Makanan               ---> didapat dari nama makanan yang dipilih kasir
    // Index ke 3 -> Jenis Makanan              ---> didapat dari jenis makanan yang dipilih kasir
    // Index ke 4 -> Harga Makanan              ---> didapat dari harga makanan yang dipilih kasir
    // Index ke 5 -> Jumlah Makanan             ---> inputan dari kasir berapa jumlah makanan yang di pesan
    // Index ke 6 -> SubTotal Harga Makanan     ---> harga makanan * jumlah makanan

    // Main ------------------------------------------------------------------------------------------------------------
    static void subMenuKasir(String subMenu, String[] menu, int[] harga) {
        System.out.println("1. Daftar " + subMenu);
        System.out.println("2. Cari berdasarkan Nama" + subMenu);
        System.out.println("3. Cari berdasarkan Harga" + subMenu);
        System.out.print("Masukkan Pilihan\t\t\t: ");
        int pilihanSubMenu = sc.nextInt();
        if (pilihanSubMenu == 1) {
            sort(menu, harga);
            readMenuMakanan(subMenu, menu, harga);
        } else if (pilihanSubMenu == 2) {
            System.out.print("Cari Menu\t\t\t\t\t: ");
            String inputCari = sc.next();
            String[][] hasilCari = searchMenu(menu, harga, inputCari);
            System.out.println("");
            System.out.println("Hasil Pencarian");
            for (int i = 0; i < hasilCari.length; i++) {
                if (hasilCari[i][0] != null) {
                    System.out.println((i + 1) + ".\t" + hasilCari[i][0] + "\t" + Integer.parseInt(hasilCari[i][1]));
                }
            }
            System.out.println("");
            System.out.print("Masukkan Menu\t\t\t\t: ");
            int pilih2 = sc.nextInt();
            System.out.print("Masukkan Jumlah Pesanan\t\t: ");
            beliJumlahMakanan[jumlahBeli] = sc.nextInt();
            beliMenuMakanan[jumlahBeli] = hasilCari[pilih2 - 1][0];
            beliHargaMakanan[jumlahBeli] = Integer.parseInt(hasilCari[pilih2 - 1][1]);
            beliSubBiayaTotal[jumlahBeli] = beliJumlahMakanan[jumlahBeli] * beliHargaMakanan[jumlahBeli];
            jumlahBeli++;
        } else if (pilihanSubMenu == 3) {
            sort(menu, harga);
            System.out.print("Cari Mulai Dari Harga\t\t: ");
            int hargaAwal = sc.nextInt();
            System.out.print("Cari Sampai Harga\t\t\t: ");
            int hargaAkhir = sc.nextInt();
            int idx = 0;
            for (int i = 0; i < harga.length; i++) {
                if ((hargaAwal <= harga[i]) && (harga[i] <= hargaAkhir)) {
                    cariMakan[idx] = menu[i];
                    cariHarga[idx] = harga[i];
                    idx++;
                    System.out.println((idx) + ".\t" + menu[i] + "\t" + harga[i]);
                }
            }
            //transaksi
            System.out.println("");
            System.out.print("Masukkan Menu\t\t\t\t: ");
            int pilih2 = sc.nextInt();
            System.out.print("Masukkan Jumlah Pesanan\t\t: ");
            beliJumlahMakanan[jumlahBeli] = sc.nextInt();
            beliMenuMakanan[jumlahBeli] = cariMakan[pilih2 - 1];
            beliHargaMakanan[jumlahBeli] = cariHarga[pilih2 - 1];
            beliSubBiayaTotal[jumlahBeli] = beliJumlahMakanan[jumlahBeli] * beliHargaMakanan[jumlahBeli];
            jumlahBeli++;
        } else {
            System.out.println("Pilihan Salah");
        }
    }

    static void subMenuAdmin(String subMenu, String[] menu, int[] harga) {
        int pilihSubMenu = -1;
        do{
            System.out.println("1.Daftar Menu " + subMenu);
            System.out.println("2.Tambah Menu " + subMenu);
            System.out.println("3.Hapus Menu " + subMenu);
            System.out.println("4.Ubah Menu " + subMenu);
            System.out.println("5.Reset Semua Menu [Makanan, Minuman, Snack] ");
            System.out.println("0.Back");
            System.out.print("Pilihan Anda : ");
            pilihSubMenu = sc.nextInt();
            switch (pilihSubMenu) {
                case 1:
                    readMenuMakanan(subMenu, menu, harga);
                    break;
                case 2:
                    System.out.print("Nama = ");
                    String nama = sc.next();
                    System.out.print("Harga = ");
                    int hrg = sc.nextInt();
                    addDataArrayString(menu, nama);
                    addDataArrayInt(harga, hrg);
                    if (subMenu.equals(ITEM_JENIS_MAKANAN)) {
                        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN++;
                    } else if (subMenu.equals(ITEM_JENIS_MINUMAN)) {
                        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN++;
                    } else if (subMenu.equals(ITEM_JENIS_SNACK)) {
                        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK++;
                    }
                    break;
                case 3:
                    readMenuMakanan(subMenu, menu, harga);
                    System.out.print("Nama = ");
                    String deletedNamaMakanan = sc.next();

                    boolean stateCheck = false; // di deklarasikan variabel status checking apakah makanan inputan user ada di daftar menu atau tidak
                    int index = 0; // dideklarasikan variable indexnya

                    for (int i = 0; i < menu.length; i++) { // dilakukan perulangan array data makanan
                        if (menu[i] != null) { // dicek apakah tidak sama dengan null
                            if (menu[i].equalsIgnoreCase(deletedNamaMakanan)) { // kemudian di check apakah ada makanan dengan nama makanan sesuai inputan user
                                index = i; // ketemu indexnya yang akan di hapus
                                stateCheck = true; // ubah status checknya menjadi true artinya nama makanan dari user sudah ada
                                break; // perulangan di paksa berhenti
                            }
                        }
                    }

                    if (stateCheck) {
                        deleteDataArrayString(menu, index);
                        deleteDataArrayInt(harga, index);
                        System.out.println("Menu " + deletedNamaMakanan + " Berhasil di hapus");
                        if (subMenu.equals(ITEM_JENIS_MAKANAN)) {
                            INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN--;
                        } else if (subMenu.equals(ITEM_JENIS_MINUMAN)) {
                            INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN--;
                        } else if (subMenu.equals(ITEM_JENIS_SNACK)) {
                            INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK--;
                        }

                    } else {
                        System.out.println("Menu Tidak ada ....");
                    }
                    break;
                case 4:
                    readMenuMakanan(subMenu, menu, harga);
                    System.out.println("Update Data");
                    System.out.print("Index\t: ");
                    int inputIndexMakanan = sc.nextInt();
                    System.out.print("Nama \t: ");
                    String inputNamaMakanan2 = sc.next();
                    System.out.print("Harga\t: ");
                    int inputHargaMakanan2 = sc.nextInt();
                    int indexMakanan = inputIndexMakanan - 1;
                    menu[indexMakanan] = inputNamaMakanan2;
                    harga[indexMakanan] = inputHargaMakanan2;
                    break;
                case 5:
                    nukeMenuMakanan();
                    break;
            }
        } while (pilihSubMenu !=0);
    }

    public static void main(String[] args) {
        int pilihMenuKasir = 0;
        int pilihMenuUser = 0;
        int pilihMenuAdmin = 0;
        int pilihMenuOwner = 0;

        initMakanan();
        initMinuman();
        initSnack();

        System.out.println("      Selamat Datang di " + "LUXURY KEYFI");
        System.out.println("      Menyediakan Berbagai Menu Makanan");
        System.out.println("---------------------------------------------");

        int jumlah = 20;
        do {
            System.out.println("USER");
            System.out.println("1. Kasir");
            System.out.println("2. Admin");
            System.out.println("3. Owner");
            System.out.println("4. Bayar");
            System.out.println("9. Exit");
            System.out.print("Pilihan Anda : ");
            pilihMenuUser = sc.nextInt();
            switch (pilihMenuUser) {
                case 1:
                    do {
                        System.out.println("MENU LUXURY KEYFI");
                        System.out.println("1. Makanan");
                        System.out.println("2. Minuman");
                        System.out.println("3. Snack");
                        System.out.println("8. Logout");
                        System.out.println("9. Exit");
                        System.out.print("Masukkan Pilihan\t\t\t: ");
                        pilihMenuKasir = sc.nextInt();
                        switch (pilihMenuKasir) {
                            case 1:
                                subMenuKasir(ITEM_JENIS_MAKANAN, dataMenuMakanan, dataHargaMakanan);
                                break;
                            case 2:
                                subMenuKasir(ITEM_JENIS_MINUMAN, dataMenuMinuman, dataHargaMinuman);
                                break;
                            case 3:
                                subMenuKasir(ITEM_JENIS_SNACK, dataMenuSnack, dataHargaSnack);
                                break;
                            case 4:
                                int uangKembalian = 0;
                                int inputUangTunai = 0;
                                System.out.println("---------------------------------------------");
                                System.out.println("DAFTAR BELANJAAN DI LUXURY KEYFI");
                                System.out.println("---------------------------------------------");
                                System.out.println("No" + ".\t" + "Menu Makanan\t" + "\t" + "Harga" + "\t" + "Jumlah");
                                for (int i = 0; i < jumlahBeli; i++) {
                                    if (beliMenuMakanan[i] != null) {
                                        System.out.println((i + 1) + ".\t" + beliMenuMakanan[i] + "\t" + beliHargaMakanan[i] + "\t" + beliJumlahMakanan[i]);
                                        totalHarga = totalHarga + beliSubBiayaTotal[i];
                                    }
                                }
                                System.out.println("---------------------------------------------");
                                System.out.println("Grand Total\t\t\t\t\t: Rp " + totalHarga);
                                System.out.println("---------------------------------------------");
                                System.out.print("Tunai\t\t\t\t\t\t: Rp ");
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
                                System.out.println("---------------------------------------------");
                                System.out.println("        Terima Kasih Sudah Berbelanja");
                                System.out.println("             di " + "LUXURY KEYFI");
                                System.out.println("      Semoga Anda Puas Dengan Makanannya");
                                break;
                            case 8:
                                pilihMenuKasir = STATE_LOGOUT;
                                break;
                            case 9:
                                pilihMenuKasir = STATE_LOGOUT;
                                pilihMenuUser = STATE_EXIT;
                                break;
                        }
                    } while (pilihMenuKasir != STATE_LOGOUT);
                    break;
                case 2:
                    do {
                        System.out.println("USER ADMIN");
                        System.out.println("1. Makanan");
                        System.out.println("2. Minuman");
                        System.out.println("3. Snack");
                        System.out.println("8. Logout");
                        System.out.println("9. Exit");
                        System.out.print("Pilihan Anda : ");
                        pilihMenuAdmin = sc.nextInt();
                        switch (pilihMenuAdmin) {
                            case 1:
                                subMenuAdmin(ITEM_JENIS_MAKANAN, dataMenuMakanan, dataHargaMakanan);
                                break;
                            case 2:
                                subMenuAdmin(ITEM_JENIS_MINUMAN, dataMenuMinuman, dataHargaMinuman);
                                break;
                            case 3:
                                subMenuAdmin(ITEM_JENIS_SNACK, dataMenuSnack, dataHargaSnack);
                                break;
                            case 4:
                                break;
                            case 8:
                                pilihMenuAdmin = STATE_LOGOUT;
                                break;
                            case 9:
                                pilihMenuAdmin = STATE_LOGOUT;
                                pilihMenuUser = STATE_EXIT;
                                break;
                        }
                    } while (pilihMenuAdmin != STATE_LOGOUT);
                    break;
                case 3:
                    do {
                        System.out.println("USER OWNER");
                        System.out.println("1. Laporan Penjualan");
                        System.out.println("2. Menu Makanan Paling Laris");
                        System.out.println("8. Logout");
                        System.out.println("9. Exit");
                        System.out.print("Pilihan Anda : ");
                        pilihMenuOwner = sc.nextInt();
                        switch (pilihMenuOwner) {
                            case 1:
                                readPenjualan(arrayJual);
                                break;
                            case 2:
                                readPenjualanLaris(arrayJual);
                                break;
                            case 8:
                                pilihMenuOwner = STATE_LOGOUT;
                                break;
                            case 9:
                                pilihMenuOwner = STATE_LOGOUT;
                                pilihMenuUser = STATE_EXIT;
                                break;
                        }
                    } while (pilihMenuOwner != STATE_LOGOUT);
                    break;
            }
        } while (pilihMenuUser != STATE_EXIT);
    }

    // -----------------------------------------------------------------------------------------------------------------

    // Method ini berfungsi untuk menambahkan data array dengan tipe array String
    static void addDataArrayString(String[] array, String value) {
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
    static void addDataArrayInt(int[] array, int value) { // Parameternya Array dan value
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
    static void deleteDataArrayInt(int[] array, int index) { // Parameter array dan index dimana data ingin di hapus
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
    static void deleteDataArrayString(String[] array, int index) { // Parameter array dan index dimana data ingin di hapus
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

    // Method ini berfungsi untuk menhapus semua menu makanan
    static void nukeMenuMakanan() {
        // dikarenakan menu makanan sendiri terdapat 3 array, jadi memanggil 3x array untuk di kosongkan nilainya
        for (int i = 0; i <= INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN; i++) {
            dataMenuMakanan[i] = null;
            dataHargaMakanan[i] = 0;
        }
        for (int i = 0; i <= INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN; i++) {
            dataMenuMinuman[i] = null;
            dataHargaMinuman[i] = 0;
        }
        for (int i = 0; i <= INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK; i++) {
            dataMenuSnack[i] = null;
            dataHargaSnack[i] = 0;
        }
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN = 0; // indexnya dirubah menjadi 0 semua
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN = 0; // indexnya dirubah menjadi 0 semua
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK = 0; // indexnya dirubah menjadi 0 semua
    }

    // Method ini berfungsi untuk menhapus semua data penjualan
    static void nukeArrayTempJual() {
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
    static void readMenuMakanan(String subMenu, String[] menuMakanan, int[] hargaMakanan) {

        if (subMenu.equals(ITEM_JENIS_MAKANAN)) {
            if (INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN == 0) {
                System.out.println("Daftar Menu Kosong");
            }
        } else if (subMenu.equals(ITEM_JENIS_MINUMAN)) {
            if (INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN == 0) {
                System.out.println("Daftar Menu Kosong");
            }
        } else if (subMenu.equals(ITEM_JENIS_SNACK)) {
            if (INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK == 0) {
                System.out.println("Daftar Menu Kosong");
            }
        }

        System.out.println(">> Daftar Menu " + subMenu);
        for (int i = 0; i < menuMakanan.length; i++) {
            if (menuMakanan[i] != null) {
                System.out.println((i + 1) + ".\t" + hargaMakanan[i] + "\t - " + menuMakanan[i]);
            }
        }

    }

    static void sort(String[] menu, int[] hargaMenu) {
        for (int i = 0; i < menu.length; i++) {
            for (int j = 0; j < menu.length - 1; j++) {
                if (menu[i] != null) {
                    if (menu[j] != null) {
                        if (menu[j + 1] != null) {
                            if (menu[j].compareToIgnoreCase(menu[j + 1]) > 0) {
                                String t = menu[j];
                                menu[j] = menu[j + 1];
                                menu[j + 1] = t;
                                int h = hargaMenu[j];
                                hargaMenu[j] = hargaMenu[j + 1];
                                hargaMenu[j + 1] = h;
                            }
                        }
                    }
                }
            }
        }
    }

    static String[][] searchMenu(String[] menu, int harga[], String cari) {
        String[][] hasil = new String[10][2];
        int idx = 0;
        for (int i = 0; i < menu.length; i++) {
            if (menu[i].indexOf(cari) >= 0) {
                hasil[idx][0] = menu[i];
                hasil[idx][1] = String.valueOf(harga[i]);
                idx++;
            }
        }
        return hasil;
    }

    // Method ini berfungsi untuk menampilkan laporan penjualan pada menu owner
    static void readPenjualan(String[][] arr) {
        int totalKeuntungan = 0;
        if (INDEX_VALUED_ARRAY_DATA_JUAL == 0) {
            System.out.println("Tidak Ada Penjualan Saat Ini");
        } else {
            System.out.println(">> Laporan Penjualan");
            for (int i = 0; i < arr.length; i++) {

                // Index ke 0 -> Id Penjualan
                // Index ke 1 -> Tanggal Penjualan
                // Index ke 2 -> Nama Makanan
                // Index ke 3 -> Jenis Makanan
                // Index ke 4 -> Harga Makanan
                // Index ke 5 -> Jumlah Makanan
                // Index ke 6 -> SubTotal Harga Makanan

                if (arr[i][1] != null) {
                    System.out.println((i + 1) + ".\t [" + arr[i][1] + "] \t [" + arr[i][5] + "] \t - " + arr[i][2] + " \t - " + arr[i][6]);
                    totalKeuntungan = totalKeuntungan + Integer.parseInt(arr[i][6]);
                }
            }
            System.out.println("Total Keuntungan : " + totalKeuntungan);
        }
    }

    // Method ini berfungsi untuk menampilkan makanan paling sering di beli
    static void readPenjualanLaris(String[][] arr) {

        if (INDEX_VALUED_ARRAY_DATA_JUAL == 0) {
            System.out.println("Tidak Ada Menu Paling Laris");
        } else {
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = 0; j < arr.length - 1; j++) {
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
    }

    static void initMakanan() {
        dataMenuMakanan[0] = "NasiGorengBiasa";
        dataHargaMakanan[0] = 27000;
        dataMenuMakanan[1] = "NasiGorengSeafood";
        dataHargaMakanan[1] = 35000;
        dataMenuMakanan[2] = "NasiGorengPedas";
        dataHargaMakanan[2] = 29000;
        dataMenuMakanan[3] = "NasiGorengJumbo";
        dataHargaMakanan[3] = 33000;
        dataMenuMakanan[4] = "NasiGorengSosis";
        dataHargaMakanan[4] = 31000;
        dataMenuMakanan[5] = "MieGorengBiasa";
        dataHargaMakanan[5] = 27000;
        dataMenuMakanan[6] = "MieGorengSeafood";
        dataHargaMakanan[6] = 32000;
        dataMenuMakanan[7] = "MieKuah";
        dataHargaMakanan[7] = 28000;
        dataMenuMakanan[8] = "KwetiauGoreng";
        dataHargaMakanan[8] = 30000;
        dataMenuMakanan[9] = "FuyungHay";
        dataHargaMakanan[9] = 35000;
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN = INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MAKANAN + 9;
    }

    static void initMinuman() {
        dataMenuMinuman[0] = "CoffeLatte";
        dataHargaMinuman[0] = 9000;
        dataMenuMinuman[1] = "CoffeChoco";
        dataHargaMinuman[1] = 12000;
        dataMenuMinuman[2] = "CoffeVanilla";
        dataHargaMinuman[2] = 11000;
        dataMenuMinuman[3] = "RedVelvet";
        dataHargaMinuman[3] = 17000;
        dataMenuMinuman[4] = "GreenTea";
        dataHargaMinuman[4] = 15000;
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN = INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_MINUMAN + 4;
    }

    static void initSnack() {
        dataMenuSnack[0] = "FrenchFries";
        dataHargaSnack[0] = 9000;
        dataMenuSnack[1] = "ToastCoKeju";
        dataHargaSnack[1] = 15000;
        dataMenuSnack[2] = "CroffleSyrup";
        dataHargaSnack[2] = 13000;
        dataMenuSnack[3] = "GelatoDurian";
        dataHargaSnack[3] = 11000;
        dataMenuSnack[4] = "CheeseCake";
        dataHargaSnack[4] = 21000;
        INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK = INDEX_VALUED_ARRAY_DATA_DAFTAR_MENU_SNACK + 4;
    }
}