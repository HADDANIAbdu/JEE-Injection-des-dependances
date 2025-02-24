package metier;
import dao.IDao;

public class IMetierImpl implements IMetier{
    private IDao Idao; //le couplage faible

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

