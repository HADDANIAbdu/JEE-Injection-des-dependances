package metier;
import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IMetierImpl implements IMetier{
    @Autowired
    private IDao Idao; //le couplage faible

    public IMetierImpl() {
        //constructeur sans paramètres pour utiliser le setter
        //dans l'injection des dépendances
    }

    public IMetierImpl(IDao dao) {
        this.Idao = dao;
        //constructeur avec paramètre pour faire l'injection
        //des dépendances avec le constructeur directement
    }
    @Override
    public double calcul() {
        double temp =  Idao.getData();
        double res = (temp - 32) * 5/9;
        return res;
    }

    //Injection des dépendances via le setter
    public void setIdao(IDao Idao) {
        this.Idao = Idao;
    }
}

