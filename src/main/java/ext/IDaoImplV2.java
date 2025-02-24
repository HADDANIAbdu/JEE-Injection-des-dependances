package ext;

import dao.IDao;
import org.springframework.stereotype.Service;

@Service
public class IDaoImplV2 implements IDao {
    @Override
    public double getData(){
        System.out.println("web service Version !");
        double temperature = 67;
        return temperature;
    }
}
