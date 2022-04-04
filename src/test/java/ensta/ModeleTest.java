package ensta;

import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

public class ModeleTest {
    public static void main(String[] args) {
        try {
            LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
            List<Livre> listLivre = livreDao.getList();
            System.out.println(listLivre);
            EmpruntDaoImpl EmpruntDao = EmpruntDaoImpl.getInstance();
            List<Emprunt> listEmprunt = EmpruntDao.getList();
            System.out.println(listEmprunt);
            MembreDaoImpl MembreDao = MembreDaoImpl.getInstance();
            List<Membre> listMembre = MembreDao.getList();
            System.out.println(listMembre);
            System.out.println(livreDao.count());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
