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

import java.util.Scanner;

public interface IMain {

    // Method ini berisi semua fungsi dari Kasir
    void mainKasir();

    // Method ini berisi semua fungsi dari Admin
    void mainAdmin();

    // Method ini berisi semua fungsi dari Owner
    void mainOwner();

    // Method ini untuk mengatur login dan merupakan inti dari aplikasi
    void mainLogin();

}
