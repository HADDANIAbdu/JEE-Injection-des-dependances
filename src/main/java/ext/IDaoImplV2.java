package ext;

import dao.IDao;

public class IDaoImplV2 implements IDao {
    @Override
    public double getData(){
        System.out.println("web service Version !");
        double temperature = 67;
        return temperature;
    }
}
