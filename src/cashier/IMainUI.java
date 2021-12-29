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

public interface IMainUI {

    // Ini adalah method untuk menu tampilan ketika login
    void loginUI();

    // Ini adalah method untuk menu tampilan ketika aplikasi di berhentikan
    void exitUI();

    // Ini adalah method untuk menu tampilan saat user berhasil login, memunculkan sapaan
    void greetingUI(String name, String role);

    // Ini adalah method untuk menu tampilan saat berhasil mensorting
    void sortUI(String state);

    // Ini adalah method untuk menu tampilan Log atau history dari user saat login
    void logTimeUI(String status);

}
