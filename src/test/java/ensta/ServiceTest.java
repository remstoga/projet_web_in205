package ensta;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;

public class ServiceTest {
    public static void main(String[] args) {
        try {
            EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
            System.out.println(empruntService.getList());
            LivreServiceImpl livreService = LivreServiceImpl.getInstance();
            System.out.println(livreService.count());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
