package App;

import View.*;
import model.*;

/**
 * Hier wird die App ausgeführt
 *
 * @author Ciro Brodmann
 */
public class NotizenApp {

    public static void main(String[] args) {

        KategorieModel katModel;
        NotizenModel notizenModel;
        MainWindow view;

        katModel = new KategorieModel();
        notizenModel = new NotizenModel();
        view = new MainWindow(notizenModel, katModel);
    }
}
